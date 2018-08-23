/**
 * 
 */
package com.ugo.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thoughtworks.xstream.XStream;
import com.uboxpay.UboxHttp;
import com.uboxpay.common.UboxConfigure;
import com.ugo.entity.Orders;
import com.ugo.entity.RefundLogs;
import com.ugo.entity.UboxPayResult;
import com.ugo.service.OrdersService;
import com.ugo.service.PayResultService;
import com.ugo.service.RefundLogsService;
import com.ugo.service.RefundService;
import com.weixinpay.RefundReqData;
import com.weixinpay.RefundRequest;
import com.weixinpay.model.OrderRefundInfo;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *退款
 */
@RequestMapping("/PC/order")
@Controller
public class WXRefundController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(WeiXinPayController.class);
	
	@Autowired
	private PayResultService payResultService;
	
	@Autowired
	private RefundLogsService refundLogsService;
	
	@Autowired
	private OrdersService orderService;
	
	@Autowired
	private RefundService refundService;
	/**
     * 处理退款请求
     * @param request
	 * @return 
     * @return
	 * @throws IllegalAccessException 
	 * @throws IOException 
     * @throws Exception
     */
	@ResponseBody
    @RequestMapping("/refund")
    public  Map<String,Object> refund(int orderId,int reason,HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, IOException {
		logger.info("手动退款orderId:"+orderId);
		Map<String,Object> map= new HashMap<String,Object>();
		RefundLogs refundLogs = new RefundLogs();
		int payMethod = orderService.findPayMethod(orderId);
		if(payMethod ==2) {
			logger.info("友宝钱包退款-----------");
			UboxPayResult findPayInfo = payResultService.findPayInfo(orderId);
			if(findPayInfo == null) {
				map.put("code", "201");
				map.put("msg", "退款数据有误!");
				return map;
			}
			TreeMap<String,Object> treeMap = new TreeMap<String,Object>();
			treeMap.put("app_id", UboxConfigure.appId);
			treeMap.put("trade_no", findPayInfo.getTradeNo());
			treeMap.put("order_id", orderId);
			treeMap.put("pay_amount", findPayInfo.getPayFee());
			try {
				Map<String, Object> example = UboxHttp.example(treeMap, UboxConfigure.uboxOrderRefund);
				int return_code = (int) example.get("return_code");
				if(return_code==200) {
					map.put("code", "200");
					map.put("msg", "SUCCESS");
					Orders orders = orderService.queryOrders(orderId);
	                refundLogs.setOrderId(orderId);
	                refundLogs.setNodeId(orders.getNodeId());
	                refundLogs.setRefundFee(findPayInfo.getPayFee());
	                refundLogs.setReason(reason);
	                refundLogs.setOrderDate(orders.getCreatedAt());
	                //保存退款信息
	                logger.info("保存退款信息-------------");
	                refundLogsService.addRefundLogs(refundLogs);
	                Orders orders1 = new Orders();
	                orders1.setId(orderId);
	                orders1.setPayStatus(4);
	                logger.info("修改订单状态信息-------------");
	                orderService.updateOrderState(orders1);
				}else {
					map.put("code", "201");
					map.put("msg", return_code+(String)example.get("return_msg"));
					logger.error("退款失败:"+return_code+"--"+(String)example.get("return_msg"));
				}
			} catch (Exception e) {
				logger.error("友宝钱包退款异常:"+e);
				e.printStackTrace();
				map.put("code","201");
				map.put("msg", "友宝钱包退款异常");
				return map;
			}
			return map;
		}
		
		//获得当前目录
        String path = request.getSession().getServletContext().getRealPath("/");
		logger.trace(path);
        
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
        String outRefundNo = "NO" + dateFormat.format( now );

        //获得退款的传入参数
        String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
        String transactionID = payResultService.getTransactionID(orderId);
        String outTradeNo = "";
        Integer totalFee = payResultService.getTotalFee(orderId);
        Integer refundFee = totalFee;

        RefundReqData refundReqData = new RefundReqData(transactionID,outTradeNo,outRefundNo,totalFee,refundFee);
        try {
            RefundRequest refundRequest = new RefundRequest();
            String result = refundRequest.httpsRequest(url, refundReqData, path);
            
            logger.info("微信退款通知-------------"+result);
            logger.trace(result);
            XStream xStream = new XStream();
			xStream.alias("xml", OrderRefundInfo.class); 
			OrderRefundInfo returnInfo = (OrderRefundInfo)xStream.fromXML(result);

            if("SUCCESS".equals(returnInfo.getReturn_code()) && "OK".equals(returnInfo.getReturn_msg()) && "SUCCESS".equals(returnInfo.getResult_code())){
            	logger.info("退款成功-------------");
            	map.put("code", "200");
                map.put("msg", returnInfo.getReturn_msg());
                Orders orders = orderService.queryOrders(orderId);
                refundLogs.setOrderId(orderId);
                refundLogs.setNodeId(orders.getNodeId());
                refundLogs.setRefundFee(returnInfo.getRefund_fee());
                refundLogs.setReason(reason);
                refundLogs.setOrderDate(orders.getCreatedAt());
                //保存退款信息
                logger.info("保存退款信息");
                refundLogsService.addRefundLogs(refundLogs);
                Orders orders1 = new Orders();
                orders1.setId(orderId);
                orders1.setPayStatus(4);
                logger.info("修改订单状态信息");
                orderService.updateOrderState(orders1);
                //更新订单
                //更新库存
            }else{
                //返回错误描述
            	logger.error("退款失败");
                String err_code_des = returnInfo.getErr_code_des();
                map.put("code", "201");
                map.put("msg", err_code_des);
            }

        }catch(Exception e){
            logger.error("异常信息-------------"+e);
            e.printStackTrace();
            response.setContentType("text/xml");
    		response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
        }
		return map;
    }
	
	/**
	 * 自动退款处理
	 * @throws IllegalAccessException 
	 * */
	@RequestMapping("/autoRefund")
	public synchronized void autoRefund(int orderId,int reason){
		logger.info("自动退款处理-----------"+orderId);
		RefundLogs refundLogs = new RefundLogs();
		int payMethod = orderService.findPayMethod(orderId);
		if(payMethod ==2) {
			logger.info("友宝钱包退款-----------");
			UboxPayResult findPayInfo = payResultService.findPayInfo(orderId);
			TreeMap<String,Object> treeMap = new TreeMap<String,Object>();
			treeMap.put("app_id", UboxConfigure.appId);
			treeMap.put("trade_no", findPayInfo.getTradeNo());
			treeMap.put("order_id", orderId);
			treeMap.put("pay_amount", findPayInfo.getPayFee());
			try {
				Map<String, Object> example = UboxHttp.example(treeMap, UboxConfigure.uboxOrderRefund);
				int return_code = (int) example.get("return_code");
				if(return_code==200) {
					Orders orders = orderService.queryOrders(orderId);
	                refundLogs.setOrderId(orderId);
	                refundLogs.setNodeId(orders.getNodeId());
	                refundLogs.setRefundFee(findPayInfo.getPayFee());
	                refundLogs.setReason(reason);
	                refundLogs.setOrderDate(orders.getCreatedAt());
	                //保存退款信息
	                logger.info("保存退款信息");
	                refundLogsService.addRefundLogs(refundLogs);
	                Orders orders1 = new Orders();
	                orders1.setId(orderId);
	                orders1.setPayStatus(4);
	                orderService.updateOrderState(orders1);
	                logger.info("修改订单状态信息");
				}else {
					logger.error("退款失败:"+return_code+"--"+(String)example.get("return_msg"));
				}
			} catch (Exception e) {
				logger.error("友宝钱包退款异常:"+e);
				e.printStackTrace();
			}
			return;
		}
		
		String path = refundService.getPath();
        logger.trace(path);
        
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
        String outRefundNo = "NO" + dateFormat.format( now );

        //获得退款的传入参数
        String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
        String transactionID = payResultService.getTransactionID(orderId);
        String outTradeNo = "";
        Integer totalFee = payResultService.getTotalFee(orderId);
        Integer refundFee = totalFee;
        System.out.println("初始化退款接口");
        RefundReqData refundReqData = null;
		try {
			refundReqData = new RefundReqData(transactionID,outTradeNo,outRefundNo,totalFee,refundFee);
		} catch (IllegalAccessException e1) {
			logger.error("初始化退款接口=====失败!"+e1);
			e1.printStackTrace();
		}
        try {
            RefundRequest refundRequest = new RefundRequest();
            logger.info("调用微信接口----");
            String result = refundRequest.httpsRequest(url, refundReqData, path);
            
            logger.info("退款通知-------------"+result);
            logger.trace(result);
            XStream xStream = new XStream();
			xStream.alias("xml", OrderRefundInfo.class); 
			OrderRefundInfo returnInfo = (OrderRefundInfo)xStream.fromXML(result);

            if("SUCCESS".equals(returnInfo.getReturn_code()) && "OK".equals(returnInfo.getReturn_msg()) && "SUCCESS".equals(returnInfo.getResult_code())){
            	logger.info("退款成功-------------");
                Orders orders = orderService.queryOrders(orderId);
                refundLogs.setOrderId(orderId);
                refundLogs.setNodeId(orders.getNodeId());
                refundLogs.setRefundFee(returnInfo.getRefund_fee());
                refundLogs.setReason(reason);
                refundLogs.setOrderDate(orders.getCreatedAt());
                //保存退款信息
                logger.info("保存自动退款信息-------------");
                refundLogsService.addRefundLogs(refundLogs);
                Orders orders1 = new Orders();
                orders1.setId(orderId);
                orders1.setPayStatus(4);
                orderService.updateOrderState(orders1);
                logger.info("修改订单状态信息-------------"+orderId);
                //更新订单
                //更新库存
            }else{
                //返回错误描述
            	logger.error("退款错误提示-------------"+returnInfo.getErr_code_des());
            }

        }catch(Exception e){
        	logger.error("异常信息-------------");
            e.printStackTrace();
        }
    }
}
