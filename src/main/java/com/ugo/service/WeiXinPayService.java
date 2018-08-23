/**
 * 
 */
package com.ugo.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.ugo.bus.MsgBus;
import com.ugo.dao.NodesDao;
import com.ugo.dao.OrdersDao;
import com.ugo.dao.PayResultDao;
import com.ugo.dao.ProductsDao;
import com.ugo.dao.SalesDao;
import com.ugo.dao.WeiXinClientSideDao;
import com.ugo.entity.Nodes;
import com.ugo.entity.Orders;
import com.ugo.entity.PayResultInfo;
import com.ugo.entity.Products;
import com.ugo.entity.WeixinProductList;
import com.ugo.web.SpringTool;
import com.ugo.web.WXRefundController;
import com.weixinpay.PayResult;
import com.weixinpay.Sign;
import com.weixinpay.Xiadan;
import com.weixinpay.common.Configure;
import com.weixinpay.common.StreamUtil;
import com.weixinpay.common.XmlMapUtils;
import com.weixinpay.model.OrderInfo;
import com.weixinpay.model.XmlPayResultInfo;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *
 */
@Service
public class WeiXinPayService {
	private static final Logger L = (Logger) LoggerFactory.getLogger(PayResult.class);
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private WeiXinClientSideDao weiXinClientSideDao;
	
	@Autowired
	private ProductsDao productsDao;
	
	@Autowired
	private NodesDao nodesDao;
	
	@Autowired
	private SalesDao salesDao;
	
	@Autowired
	private PayResultDao payResultDao;
	/**
	 * 获取openid
	 * */
	public String getOpenId(HttpServletRequest request) throws ParseException, IOException {
		String code = request.getParameter("code");
		L.info("微信登录请求参数code---"+code);
		HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session?appid="+Configure.getAppID()+"&secret="+Configure.getSecret()+"&js_code="+code+"&grant_type=authorization_code");
        //设置请求器的配置
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse res = null;
		try {
			res = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        HttpEntity entity = res.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        L.info("用户登录--获取openId"+result);
		return result;
	}
	
	
	/**
	 * 获取签名sign及参数
	 * */
	public  Map<String, Object> getPrepayId(OrderInfo orderInfo,HttpServletRequest request) throws ServletException, IOException {
	
		String prepayId = Xiadan.getPrepayId(orderInfo,request);
		L.info("prepayId-----"+prepayId);
		if(prepayId == "") {
			return null;
		}
		L.info("获取签名数据");
		//再签名
		Map<String, Object> signMap = Sign.getSign(prepayId);
		System.out.println("MAP--"+signMap.toString());
		return signMap;
	}
	
	/**
	 * 友宝钱包支付成功-调用出货
	 * @author qipeng   2018/8/8
	 * */
	public void UboxPayResult(Orders orders) {	
		//发送出货指令
		L.info("支付成功-----------------调用出货指令,订单:"+orders.getId());
		send(orders);
	}
	
	/**
	 * 获取微信通知
	 * @throws IOException
	 * @author qipeng  
	 * */
	@Transactional
	public void getPayResult(HttpServletRequest request,HttpServletResponse response) throws IOException{
		L.info("微信通知结果下达--------------!");
		String reqParams = null;
		try {
			reqParams = StreamUtil.read(request.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			L.error("未获取返回结果流!");
		}
		L.info("reqParams---"+reqParams);
		XStream xStream = new XStream();
		XmlPayResultInfo payResult = null;
		try {
			xStream.alias("xml", XmlPayResultInfo.class); 
		    payResult = (XmlPayResultInfo) xStream.fromXML(reqParams);
		} catch (Exception e) {
			response.setContentType("text/xml");
			response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
			L.error("支付结果转换错误--更改转换方式");
			Map<String, Object> map = XmlMapUtils.xmlToMap(reqParams);
			Map<String, Object> map1= (Map<String, Object>) map.get("xml");
			PayResultInfo payResultInfo = new PayResultInfo();
			payResultInfo.setOrderId(Integer.parseInt((String) map1.get("attach")));
			payResultInfo.setWxTxnId((String)map1.get("transaction_id"));
			payResultInfo.setWxId((String)map1.get("openid"));
			payResultInfo.setTotalFee(Integer.parseInt((String)map1.get("total_fee")));
			payResultInfo.setCashFee(Integer.parseInt((String)map1.get("cash_fee")));
			payResultInfo.setFeeType((String)map1.get("fee_type"));
			payResultInfo.setNonceStr((String)map1.get("nonce_str"));
			payResultInfo.setAttach((String)map1.get("attach"));
			payResultInfo.setTimeEnd((String)map1.get("time_end"));
			payResultInfo.setResultCode((String)map1.get("result_code"));
			
			Orders orders1 = ordersDao.queryOrders(Integer.parseInt(payResultInfo.getAttach()));
			Orders orders = new Orders();
			orders.setId(payResultInfo.getOrderId());
			if("SUCCESS".equals(payResultInfo.getResultCode())) {
				L.info("支付成功-----------------修改订单状态");
				orders.setPayStatus(2);
				orders.setPayMethod(1);
				ordersDao.updateOrder(orders);	
				//发送出货指令
				L.info("支付成功-----------------调用出货指令");
				send(orders1);
			}else if("FAIL".equals(payResultInfo.getResultCode())){
				L.info("支付返回失败信息");
				orders.setPayStatus(1);
				orders.setPayMethod(1);
				ordersDao.updateOrder(orders);
			}
			payResultDao.addPayResultInfo(payResultInfo);
		}finally {
			response.setContentType("text/xml");
			response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
		}
		L.info("支付结果payResult--------"+payResult.toString());
		String result_code = payResult.getResult_code();
		String orderId = payResult.getAttach();
		L.info("原样返回的订单Id--------------"+orderId);
		System.out.println("result_code---------"+result_code);
		
		Orders orders1 = ordersDao.queryOrders(Integer.parseInt(orderId));
		payResult.setOrderId(orders1.getId());
		L.info("支付返回结果类型转换");
		PayResultInfo payResultInfo = XmlPayResultInfoToPayResultInfo(payResult);
		Orders orders = new Orders();
		orders.setId(orders1.getId());
		if("SUCCESS".equals(result_code)) {
			L.info("支付成功-----------------修改订单状态"+orderId);
			orders.setPayStatus(2);
			ordersDao.updateOrder(orders);	
			L.info("支付返回结果保存记录！");
			payResultDao.addPayResultInfo(payResultInfo);
			//发送出货指令
			L.info("支付成功-----------------调用出货指令"+orderId);
			send(orders1);
		}else if("FAIL".equals(result_code)){
			L.info(orderId+"支付返回失败信息-----------------"+result_code);
			orders.setPayStatus(1);
			ordersDao.updateOrder(orders);
			L.info("支付返回结果保存记录！");
			payResultDao.addPayResultInfo(payResultInfo);
		}
	}
	
	/**
	 * 向设备发送指令
	 * @return 
	 * */
	@Transactional
	private synchronized void send(Orders orders) {
		Orders orders1 = new Orders();
		L.info("准备发送指令-------------------orders"+orders.toString());
		int id = orders.getId();
		int channelsType = orders.getChannelsType();
		int salesId = orders.getSalesId();
		orders1.setId(id);
		L.info("货道类型---------------"+channelsType);
		/**
		 * 出货指令
		 * */
		if(channelsType == 1) {//格子柜出货指令
			L.info("格子柜>>>>>>>");
				try {
					L.info("向格子柜发送出货指令");
					MsgBus.getInstance().box.deliver(id+"",orders.getVendorId()+"", orders.getChannelId());
					L.info("发送指令完成--更改库存状态");
					updateSalesState(orders.getVendorId(), orders.getChannelId(), 3);
				} catch (MqttException e) {
					// TODO Auto-generated catch block
					L.error("1发送出货指令失败!!!!!------------"+id+"---"+e);
					orders1.setOrderStatus(3);
					L.error("修改订单状态！");
					ordersDao.updateOrder(orders1);
					L.error("恢复库存数量！");
					salesDao.addStock(salesId);
					WXRefundController refund = SpringTool.getBean(WXRefundController.class);
					refund.autoRefund(id, 5);
					e.printStackTrace();
				}
				
		}else if (channelsType == 2) {//冰山4货道出货指令
			L.info("冰山机>>>>>");
			int hotTime = salesDao.getHeatUpTime(id);
			int vendorId = orders.getVendorId();
			//货道对应-----
			int channelId = orders.getChannelId();
			if(channelId>=3) {
				channelId =channelId+1;
			}
			try {
				L.info("向冰山机发送出货指令");
				MsgBus.getInstance().oven.deliver(id+"", vendorId+"", channelId, hotTime);
				L.info("发送指令完成--更改库存状态");
				updateSalesState(vendorId, orders.getChannelId(), 3);
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				L.error("2发送出货指令失败!!!!!-----------"+id+"---"+e);
				orders1.setOrderStatus(3);
				L.error("修改订单状态！");
				ordersDao.updateOrder(orders1);
				L.error("恢复库存数量！");
				salesDao.addStock(salesId);
				WXRefundController refund = SpringTool.getBean(WXRefundController.class);
				refund.autoRefund(id, 5);
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 更新库存出货状态
	 * 重复--》salesService
	 * */
	@Transactional
	public void updateSalesState(int vendorId,int channelId,int state) {
		int [] channels1= {1,2};
		int [] channels2= {3,4};
		int[] channels3 = {1,2,3,4};
		if(channelId == 1 ||channelId ==2) {
			for(int i=0;i<channels1.length;i++) {
				salesDao.updateSalesState(vendorId,channels1[i],state);
			}
		}else if(channelId == 3 ||channelId ==4 ||channelId == 5) {
			for(int i=0;i<channels2.length;i++) {
				salesDao.updateSalesState(vendorId,channels2[i],state);
			}
		}else if(channelId ==0) {
			for(int i=0;i<channels3.length;i++) {
				salesDao.updateSalesState(vendorId,channels3[i],state);
			}
		}
	}
	
	/**
	 * 获取点位列表
	 * @return 
	 * */
	public List<Map<String,Object>> getNodesList() {
		List<Nodes> list = nodesDao.getList();
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
		for(Nodes nodes :list) {
			Map<String,Object>map = new HashMap<String,Object>();
			map.put("name", nodes.getName());
			map.put("id", nodes.getId());
			map.put("address", nodes.getAddress());
			map.put("distance_in_km", 0);
			nodeList.add(map);
		}
		return nodeList;
	}
	
	/**
	 * 获取商品列表
	 * @return 
	 * */
	public List<Map<String,Object>> getProductList(HttpServletRequest request) {
		String nodeId = request.getParameter("nodeId");
		int nodeId1 = Integer.parseInt(nodeId);
		
		String url = getUrl(request);
		List<WeixinProductList> productList = weiXinClientSideDao.getProductList(nodeId1);
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		for(WeixinProductList weixinProductList:productList) {
			Map<String,Object> map = new HashMap<String,Object>();
			if(weixinProductList !=null) {
				int productNum = weiXinClientSideDao.getProductNum(weixinProductList.getId(), nodeId1);
				map.put("count", productNum);
				map.put("product_id", weixinProductList.getId());
				map.put("name", weixinProductList.getName());
				map.put("brand_id", weixinProductList.getBrandId());
				map.put("s_img", url+weixinProductList.getListImg());
				map.put("b_img", url+weixinProductList.getDetailsImg());
				map.put("i_img", url+weixinProductList.getIngredientImg());
				map.put("desc", weixinProductList.getDescription());
				map.put("sale_price", weixinProductList.getPrice());
				map.put("discount_price", rounding(nodeId1, weixinProductList.getPrice()));
			}
			listMap.add(map);
		}
		return listMap;
	}
	
	/**
	 * 获取商品详情
	 * @return 
	 * */
	public Map<String, Object> getProductDetails(HttpServletRequest request) {
		String id = request.getParameter("id");
		String nodeId = request.getParameter("nodeId");
		int id1 = Integer.parseInt(id);
		int nodeId1 = Integer.parseInt(nodeId);
		Products product = productsDao.getProduct(id1);
		int productNum = weiXinClientSideDao.getProductNum(id1, nodeId1);
		String url = getUrl(request);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", product.getId());
		map.put("name", product.getName());
		map.put("brand_id", product.getBrandId());
		map.put("duration_period", product.getDurationPeriod());
		map.put("checklist", product.getChecklist());
		map.put("ingredient", product.getIngredient());
		map.put("netweight", product.getNetweight());
		map.put("s_img", url+product.getListImg());
		map.put("b_img", url+product.getDetailsImg());
		map.put("i_img", url+product.getIngredientImg());
		map.put("desc", product.getDescription());
		map.put("fit_people", product.getFitPeople());
		map.put("taste", product.getTaste());
		map.put("brand_name", "");
		map.put("count", productNum);
		map.put("sale_price", product.getPrice());
		map.put("discount_price", rounding(nodeId1, product.getPrice()));
		map.put("begin_hour", "");
		map.put("begin_minute", "");
		
		return map;
	}
	
	/**
	 * 获取微信订单列表
	 * @return 
	 * */
	public List<Map<String,Object>> getWXOrderList(HttpServletRequest request) {
		String openId = request.getParameter("openId");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageLimit = Integer.parseInt(request.getParameter("pageLimit"));
		int pages = page*pageLimit;
		List<Orders> wxOrders = ordersDao.getWXOrders(openId,pages,pageLimit);
		List<Map<String,Object>> wxOrdersList = new ArrayList<Map<String,Object>>();
		String url = getUrl(request);
		for(Orders orders :wxOrders) {
			Map<String,Object>map = new HashMap<String,Object>();
			map.put("id", orders.getId());
			map.put("node_id", orders.getNodeId());
			map.put("node_name", orders.getNodeName());
			map.put("vendor_id", orders.getVendorId());
			map.put("channel_id", orders.getChannelId());
			map.put("created_at", orders.getCreatedAt());
			map.put("order_status", orders.getOrderStatus());
			map.put("pay_method", orders.getPayMethod());
			map.put("pay_price", orders.getPayPrice());
			map.put("pay_status", orders.getPayStatus());
			map.put("pay_time", orders.getPayTime());
			map.put("product_id", orders.getProductId());
			map.put("product_name", orders.getProductName());
			map.put("s_img", url+orders.getsImg());
			map.put("total_price", rounding(orders.getNodeId(), orders.getTotalPrice()));
			if(orders.getChannelsType() == 2 && orders.getChannelId()>2) {
				map.put("channel_name", "下取餐口");
			}else if (orders.getChannelsType() == 2 && orders.getChannelId()<3) {
				map.put("channel_name", "上取餐口");
			}else {
				map.put("channel_name", orders.getChannelId()+"号柜");
			}
			wxOrdersList.add(map);
		}
		return wxOrdersList;
	}
	
	//获取客户端Ip
	public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
	
	/**
	 * 获取banner
	 * */
	public List<String> getBannerList(HttpServletRequest request) {
		List<String> list = new ArrayList<>();
		List<String> bannerList = weiXinClientSideDao.queryBannerList();
		String url = getUrl(request);
		for(int i=0;i<bannerList.size();i++) {
			list.add(url+bannerList.get(i));
		}
		return list;
	}
	
	//取服务器IP
	public static String getIp2() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress().toString();
		return ip;
	}
	
	//
	public String getUrl(HttpServletRequest request) {
		//获取服务器地址
		String scheme = request.getScheme();
		String requestURI = request.getServerName();
		String contextPath = request.getContextPath();
		String url = scheme+"://"+requestURI+contextPath+"/";
		return url;
	}
	
	/**
	 * 微信返回支付结果类型转换
	 * */
	public PayResultInfo XmlPayResultInfoToPayResultInfo(XmlPayResultInfo payResult) {
		
		PayResultInfo payResultInfo = new PayResultInfo();
		payResultInfo.setOrderId(payResult.getOrderId());
		payResultInfo.setWxTxnId(payResult.getTransaction_id());
		payResultInfo.setWxId(payResult.getOpenid());
		payResultInfo.setAttach(payResult.getAttach());
		payResultInfo.setBankType(payResult.getBank_type());
		payResultInfo.setCashFee(payResult.getCash_fee());
		payResultInfo.setCashFeeType(payResult.getCash_fee_type());
		payResultInfo.setCouponCount(payResult.getCoupon_count());
		payResultInfo.setCouponFee(payResult.getCoupon_fee());
		payResultInfo.setErrCode(payResult.getErr_code());
		payResultInfo.setErrCodeDes(payResult.getErr_code_des());
		payResultInfo.setFeeType(payResult.getFee_type());
		payResultInfo.setNonceStr(payResult.getNonce_str());
		payResultInfo.setResultCode(payResult.getResult_code());
		payResultInfo.setTimeEnd(payResult.getTime_end());
		payResultInfo.setTotalFee(payResult.getTotal_fee());
		payResultInfo.setTradeType(payResult.getTrade_type());
		L.info("类型转换完成！！！");
		return payResultInfo;
	}
	
	/**
	 * 获取折扣比例数量
	 * */
	public int getDiscountCount(int nodeId) {
		int count = weiXinClientSideDao.getDiscountCount(nodeId);
		return count;
	}
	
	/**
	 * 获取折扣比例
	 * */
	public int getDiscount(int nodeId) {
		int discount = weiXinClientSideDao.getDiscount(nodeId);
		return discount;
	}
	
	/**
	 * 商品折扣价 四舍五入
	 * */
	public int rounding(int nodeId,int totalPrice) {
		int discount = 100;
		int discountCount = weiXinClientSideDao.getDiscountCount(nodeId);
		if(discountCount>0) {
			discount = weiXinClientSideDao.getDiscount(nodeId);
		}
		//商品折扣价 四舍五入  start
		String scale = new BigDecimal(discount*totalPrice/100).setScale(0, BigDecimal.ROUND_HALF_UP)+"";
        int payPrice =Integer.parseInt(scale);
        //商品折扣价 四舍五入  end
		return payPrice;
	}
}
