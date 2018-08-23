/**
 * 
 */
package com.ugo.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.uboxpay.SendHTTP;
import com.uboxpay.UboxHttp;
import com.uboxpay.common.DecryptNum;
import com.uboxpay.common.UboxConfigure;
import com.ugo.dto.ListConstant;
import com.ugo.dto.LoginConstant;
import com.ugo.dto.MapConstant;
import com.ugo.entity.Nodes;
import com.ugo.entity.Orders;
import com.ugo.entity.UboxPayResult;
import com.ugo.entity.Users;
import com.ugo.service.NodesService;
import com.ugo.service.OrdersService;
import com.ugo.service.PayResultService;
import com.ugo.service.SalesService;
import com.ugo.service.UsersService;
import com.ugo.service.VendorsService;
import com.ugo.service.WeiXinPayService;
import com.weixinpay.model.OrderInfo;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 * 微信客户端控制层
 */
@RequestMapping("/front")
@Controller
public class WeiXinPayController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(WeiXinPayController.class);
	
	@Autowired
	private WeiXinPayService weiXinPayService;

	@Autowired
	private PayResultService payResultService;
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private OrdersService orderService;
	
	@Autowired
	private SalesService salesService;
	
	@Autowired
	private NodesService nodesService;
	
	@Autowired
	private VendorsService vendorsService;
	/**
	 *  @author qipeng
	 * 微信登录
	 * */
	@ResponseBody
	@RequestMapping("/login")
	public Map<String,Object> getOpenId(HttpServletRequest request,HttpServletResponse response) {
		//LoginConstant loginConstant = new LoginConstant();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String result = weiXinPayService.getOpenId(request);
			logger.info("返回微信登录结果,取openID:"+result);
			JSONObject parse = JSONObject.parseObject(result);
			String openId = parse.getString("openid");
			String session_key = parse.getString("session_key");
			if(openId==null ||openId ==""||openId.isEmpty()) {
				map.put("code", "101");
				map.put("msg", "参数错误");
				//response.getWriter().append(result);
				return map;
			}
			map.put("code", "200");
			map.put("msg", "成功");
			map.put("openId", openId);
			map.put("sessionKey", session_key);
			//response.getWriter().append(result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * @author qipeng 2018/8/7
	 * Ubox>>>解析获取手机号码
	 * 弃用
	 * */
	@ResponseBody
	@RequestMapping("/bindUboxPay")
	public Map<String,Object> getDecrypt(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		String openId = request.getParameter("openId");
		String encrypData = request.getParameter("encrypData");
		String sessionKey = request.getParameter("sessionKey");
		String ivData = request.getParameter("ivData");

		JSONObject decryptJson = null;
		String phone = null;
		try {
			decryptJson = DecryptNum.decryptJson(encrypData, ivData, sessionKey);
		    phone = (String) decryptJson.get("phoneNumber");
			map.put("code", 0);
			map.put("msg", "SUCCESS");
			map.put("phone",phone);
		} catch (Exception e) {
			logger.error("Base64!");
			map.put("code", 1);
			map.put("msg", "解析失败");
			return map;
		}
		Users user = new Users();
		user.setOpenId(openId);
		user.setPhone(phone);
		userService.addUser(user);
		return map;
	}
	
	/**
	 * @author qipeng 2018/8/13
	 * Ubox>>>发送短信验证码
	 * */
	@ResponseBody
	@RequestMapping("/sendSMSCode")
	public Map<String,Object> sendSMSCode(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<>();
		String openId = request.getParameter("openId");
		if(openId.isEmpty()) {
			logger.info("openID为空！");
			map.put("code", 9);
			map.put("msg", "openID为空！");
			return map;
		}
		String phone = request.getParameter("phone");
		logger.info("短信验证"+"openId:"+openId+"phone:"+phone);
		if(phone.isEmpty()) {
			logger.info("手机号码为空！");
			map.put("code", 9);
			map.put("msg", "手机号码为空！");
			return map;
		}
		System.out.println("openId:"+openId+"---phone:"+phone);
		String valueOf = String.valueOf((new Random().nextInt(8999) + 1000));
		logger.info("发送验证码："+valueOf);
		boolean shortMsg = SendHTTP.sendShortMsg(phone,valueOf);
		
		if(shortMsg) {
			session.setAttribute("uboxPhone", phone);
			session.setAttribute("shortMsg", valueOf);
			session.setAttribute("time", System.currentTimeMillis());
			map.put("code", 0);
			map.put("msg", "SUCCESS");
			map.put("sessionId", session.getId());
		}else {
			map.put("code", 1);
			map.put("msg", "ERROR");
		}
		return map;
	}
	
	/**
	 * @author qipeng 2018/8/13
	 * Ubox>>>绑定友宝手机号码
	 * */
	@ResponseBody
	@RequestMapping("/bindUbox")
	public Map<String,Object> bindUbox(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<>();
		String openId = request.getParameter("openId");
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		System.out.println("openId:"+openId+"---phone:"+phone+"code:"+code);
		if(openId.isEmpty()||phone.isEmpty()||code.isEmpty()) {
			map.put("code", 1);
			map.put("msg", "参数不能为空！");
			return map;
		}
	    String uboxPhone = (String) session.getAttribute("uboxPhone");
	    String code_session = (String) session.getAttribute("shortMsg");
	    Long time = (Long) session.getAttribute("time");
//	    session.removeAttribute("uboxPhone");
//	    session.removeAttribute("shortMsg");
//	    session.removeAttribute("time");
	    System.out.println("uboxPhone:"+uboxPhone+"code_session:"+code_session);
		if(code_session == null) {
			map.put("code", 1);
			map.put("msg", "请重新获取验证码！");
			return map;
		}
		if((System.currentTimeMillis()-time)/1000/60 >=5) {
			map.put("code", 1);
			map.put("msg", "验证码已失效！");
			return map;
		}
		if(!uboxPhone.trim().equalsIgnoreCase(phone)) {
			map.put("code", 1);
			map.put("msg", "手机号码不一致！");
			return map;
		}
		if(code_session.trim().equalsIgnoreCase(code)) {
			logger.info("绑定钱包前，获取余额");
			Map<String, Object> uboxBalance = uboxBalance(openId, phone);
			int result = (int) uboxBalance.get("code");
			if(result == 0) {
				map.put("balance", uboxBalance.get("balance"));
				map.put("code", 0);
				map.put("msg", "绑定成功");
				Users user = new Users();
				user.setOpenId(openId);
				user.setUboxPhone(phone);
				userService.addUser(user);
				logger.info("绑定成功！");
			}else {
				map.put("code", 1);
				map.put("msg",uboxBalance.get("msg"));
				return map;
			}
		}else {
			map.put("code", 1);
			map.put("msg","验证码错误");
			return map;
		}
		return map;
	}
	
	/**
	 * @author qipeng
	 * 保存用户信息
	 * */
	@ResponseBody
	@RequestMapping("/setUserInfo")
	public LoginConstant setUserInfo(HttpServletRequest request,HttpServletResponse response) {
		LoginConstant loginConstant = new LoginConstant();
		String openId = request.getParameter("openId");
		String nickName = request.getParameter("nickName");
		String avatar = request.getParameter("avatar");
		String phone = request.getParameter("phone");
		Users user = new Users();
		user.setOpenId(openId);
		user.setNickname(nickName);
		user.setHeadimgurl(avatar);
		user.setPhone(phone);
		logger.info("保存用户信息");
		userService.addUser(user);
		logger.info("保存用户信息完成");
		return loginConstant;
	}
	
	/**
	 * @author qipeng 2018/8/7
	 * Ubox>>>查询钱包余额
	 * */
	@ResponseBody
	@RequestMapping("/uboxBalance")
	public Map<String, Object> uboxBalance(String openId,String phone) {
		System.out.println("openId:"+openId+"---phone:"+phone);
//		测试接口
//		String openIds="test_openid";
//		String phones="13243797679";

		TreeMap<String,Object> treeMap = new TreeMap<String,Object>();
		treeMap.put("app_id", UboxConfigure.appId);
		treeMap.put("open_id", openId);
		treeMap.put("phone", phone);
		Map<String, Object> map = new HashMap<>();
		try {
			Map<String, Object> example = UboxHttp.example(treeMap, UboxConfigure.uboxBalance);
			int return_code = (int) example.get("return_code");
			if(return_code==200) {
				int balance = (int) example.get("balance");
				map.put("code", 0);
				map.put("msg", "SUCCESS");
				map.put("balance", balance);
			}else {
				map.put("code", 1);
				map.put("msg", return_code+(String)example.get("return_msg"));
			}
		} catch (Exception e) {
			logger.error("查询钱包余额调用接口失败"+e);
			e.printStackTrace();
			map.put("code",3);
			map.put("msg", "查询钱包余额调用接口失败");
			return map;
		}
		return map;
	}
	
	/**
	 * @author qipeng 2018/8/8
	 * Ubox>>>友宝钱包支付
	 * */
	@ResponseBody
	@RequestMapping("/uboxOrderPay")
	public synchronized Map<String, Object> uboxOrderPay(String openId,String phone,String productId,String nodeId) {
		System.out.println("openId:"+openId+"---productId:"+productId+"---deviceId:"+nodeId);
//		测试数据
//		String openIds="test_openid";
//		String phones="13243797679";
		
		
		Map<String, Object> map = new HashMap<>();
		if (openId.isEmpty() ||productId.isEmpty() ||nodeId.isEmpty()) {
			map.put("code", 2);
			map.put("msg", "错误参数");
		}
		Orders orders = orderService.querySales(nodeId, productId);
		//Sales sales = orderService.findSales(Integer.parseInt(deviceId), Integer.parseInt(productId));
		if(orders.getReturnCode() == 201) {
			map.put("code","201");
			map.put("msg","商品已售罄");
			return map;
		}else if(orders.getReturnCode() == 203){
			map.put("code","203");
			map.put("msg","商品制作中,请稍后购买");
			return map;
		}else if(orders.getReturnCode() == 204){
			map.put("code","204");
			map.put("msg","该货道出货故障,请选择其他货道购买!");
			return map;
		}
		
		//获取设备状态 1离线 2在线
		logger.info("查看设备状态！");
		int vendorState = vendorsService.getVendorState(orders.getVendorId());
		if(vendorState == 1) {
			map.put("code", "301");
			map.put("msg", "设备离线");
			return map;
		}
		//获取点位设备状态 1运营中2停售中
		int nodeVmsState = vendorsService.getVendorsNodeState(orders.getVendorId());
		if(nodeVmsState == 2) {
			map.put("code","302");
			map.put("msg","停止售卖");
			return map;
		}
		
		//Orders orders = orderService.salesToOrders(sales);
		orders.setPayMethod(2);
		//折扣后的支付价格
		int payPrice = weiXinPayService.rounding(Integer.parseInt(nodeId), orders.getTotalPrice());
		orders.setPayPrice(payPrice);
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
		String format = fmt.format(new Date());
		long parseLong = Long.parseLong(format);
		int orderId = orderService.saveOrders(orders,openId,parseLong);
		orders.setId(orderId);
		TreeMap<String,Object> treeMap = new TreeMap<String,Object>();
		treeMap.put("app_id", UboxConfigure.appId);
		treeMap.put("open_id", openId);
		treeMap.put("phone", phone);
		treeMap.put("product_id", productId);
		treeMap.put("subject", orders.getProductName());
		treeMap.put("body", orders.getProductName());
		treeMap.put("product_price", orders.getTotalPrice());
		treeMap.put("pay_amount", orders.getPayPrice());
		treeMap.put("order_id", orderId);
		Orders ods = new Orders();
		ods.setId(orderId);
		try {
			Map<String, Object> example = UboxHttp.example(treeMap, UboxConfigure.uboxOrderPay);
			int return_code = (int) example.get("return_code");
			if(return_code==200) {
				map.put("code", 0);
				map.put("msg", "SUCCESS");
				if(orders.getChannelsType()==2) {
					if(orders.getChannelId()>2) {
						map.put("name", "下取餐口");
					}else {
						map.put("name", "上取餐口");
					}
				}
				map.put("orderId", orderId);
				logger.info("钱包支付成功-----------------修改订单状态"+orderId);
				ods.setPayStatus(2);
				orderService.updateOrder(ods);
				logger.info("钱包支付成功-----------------保存支付单号"+orderId);
				UboxPayResult uboxPayResult = new UboxPayResult();
				uboxPayResult.setOrderId(orderId);
				uboxPayResult.setOpenId(openId);
				String tradeNo = (String) example.get("trade_no");
				uboxPayResult.setTradeNo(tradeNo);
				uboxPayResult.setTotalFee(orders.getTotalPrice());
				uboxPayResult.setPayFee(orders.getPayPrice());
				payResultService.saveUboxPayResult(uboxPayResult);
			}else {
				map.put("code", 1);
				map.put("msg", return_code+(String)example.get("return_msg"));
				logger.info("钱包支付失败-----------------修改订单状态"+orderId);
				ods.setPayStatus(3);
				orderService.updateOrder(ods);
			}
		} catch (Exception e) {
			logger.error("友宝钱包支付调用接口失败"+e);
			e.printStackTrace();
			map.put("code",3);
			map.put("msg", "友宝钱包支付调用接口失败");
			logger.info("钱包支付失败-----------------修改订单状态"+orderId);
			ods.setPayStatus(3);
			orderService.updateOrder(ods);
			return map;
		}
		weiXinPayService.UboxPayResult(orders);
		return map;
	}
	
	/**
	 * @author qipeng
	 * 微信支付接口
	 * */
	@ResponseBody
	@RequestMapping("/preBuy")
	public synchronized MapConstant setPreBuy(HttpServletRequest request,HttpServletResponse response) {
		MapConstant constant = new MapConstant();
		
		String openId = request.getParameter("openId");
		String nodeId = request.getParameter("nodeId");
		String productId = request.getParameter("productId");
		logger.info("用户："+openId+"支付请求！点位ID："+nodeId+"商品ID："+productId);
		
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOpenid(openId);
		//调用微信支付前先查询库存
		logger.info("获取可售订单信息！");
		Orders orders = orderService.querySales(nodeId, productId);
		if(orders.getReturnCode() == 201) {
			constant.setCode("201");
			constant.setMsg("商品已售罄");
			return constant;
		}else if(orders.getReturnCode()== 203){
			constant.setCode("203");
			constant.setMsg("商品制作中,请稍后购买");
			return constant;
		}else if(orders.getReturnCode()== 204){
			constant.setCode("204");
			constant.setMsg("该货道出货故障,请选择其他货道购买!");
			return constant;
		}
		//获取设备状态 1离线 2在线
		logger.info("查看设备状态！");
		int vendorState = vendorsService.getVendorState(orders.getVendorId());
		if(vendorState == 1) {
			constant.setCode("301");
			constant.setMsg("设备离线");
			return constant;
		}
		//获取点位设备状态 1运营中2停售中
		int nodeVmsState = vendorsService.getVendorsNodeState(orders.getVendorId());
		if(nodeVmsState == 2) {
			constant.setCode("302");
			constant.setMsg("停止售卖");
			return constant;
		}
		
		int discount = 100;
		int discountCount = weiXinPayService.getDiscountCount(orders.getNodeId());
		if(discountCount>0) {
			discount = weiXinPayService.getDiscount(orders.getNodeId());
		}
		//商品折扣价 四舍五入  start
		int totalPrice = orders.getTotalPrice();
		String scale = new BigDecimal(discount*totalPrice/100).setScale(0, BigDecimal.ROUND_HALF_UP)+"";
        int payPrice =Integer.parseInt(scale);
        //商品折扣价 四舍五入  end
		
        orders.setPayPrice(payPrice);
        orders.setPayMethod(1);
		//调用微信支付接口
		orderInfo.setTotal_fee(payPrice);
		logger.info("生成订单---------！");
		int orderId = orderService.saveOrders(orders,openId,(long) 0);
		orderInfo.setAttach(orderId+"");
		Map<String, Object> sign = null;
		try {
			logger.info("微信接口交互！");
			sign = weiXinPayService.getPrepayId(orderInfo,request);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			logger.error("ServletException!",e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("微信接口交互失败!",e);
			e.printStackTrace();
		}
		if(sign == null || sign.isEmpty()) {
			constant.setCode("202");
			constant.setMsg("订单生成失败");
			return constant;
		}
		String timeStamp = (String) sign.get("timeStamp");
		Orders orders1 = new Orders();
		orders1.setId(orderId);
		
		
		if(timeStamp!=null) {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
			String format = fmt.format(new Date(Long.parseLong(timeStamp)*1000));
			long parseLong = Long.parseLong(format);
			orders1.setPayTime(parseLong);
			logger.info("插入订单生成时间戳！"+parseLong);
			orderService.updateOrderState(orders1);
		}
		if(orderId ==0) {
			constant.setCode("201");
			return constant;
		}else {
			sign.put("openId", openId);
			sign.put("orderId", orderId);
			constant.setCode("200");
			constant.setData(sign);
		}
		return constant;
	}
	
	/**
	 * @author qipeng
	 * 未付款订单支付
	 * */
	@ResponseBody
	@RequestMapping("/rePay")
	public MapConstant setRePay(HttpServletRequest request,HttpServletResponse response) {
		MapConstant constant = new MapConstant();
		String orderId = request.getParameter("orderId");
		logger.info("用户重新发起支付请求！订单ID:"+orderId);
		int id = Integer.parseInt(orderId);
		Orders orders = orderService.queryOrders(id);

		//获取设备状态
		logger.info("查看设备状态！");
		int vendorState = vendorsService.getVendorState(orders.getVendorId());
		if(vendorState == 1) {
			constant.setCode("301");
			constant.setMsg("设备离线");
			return constant;
		}
		//判断订单是否取消
		if(orders.getPayStatus() == 3) {
			constant.setCode("201");
			constant.setMsg("订单已取消");
			return constant;
		}
		logger.info("库存运行状态！");
		int salesState = salesService.getSalesState(orders.getSalesId());
		if(salesState == 3) {
			constant.setCode("203");
			constant.setMsg("货道占用中");
			return constant;
		}else if(salesState == 4){
			constant.setCode("204");
			constant.setMsg("该货道故障,请选择其他货道商品");
			return constant;
		}
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOpenid(orders.getOpenId());
		orderInfo.setTotal_fee(orders.getPayPrice());
		orderInfo.setAttach(orderId);
		try {
			//调用微信支付接口
			logger.info("微信接口交互！");
			Map<String, Object> sign = weiXinPayService.getPrepayId(orderInfo,request);
			if(sign == null) {
				constant.setCode("202");
			}else {
				sign.put("orderId", id);
				constant.setCode("200");
				constant.setData(sign);
			}
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			logger.error("ServletException!",e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("微信接口交互失败!",e);
			e.printStackTrace();
		}
		Orders orders1 = orderService.queryOrders(id);
		//再次判断订单是否取消
		if(orders1.getPayStatus() == 3) {
			constant.setCode("201");
			return constant;
		}
		return constant;
	}
	/**
	 * @author qipeng
	 * 返回支付结果
	 * @throws IOException 
	 * */
	@RequestMapping("/PayResult")
	public synchronized void getPayResult(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/xml");
		try {
			response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			weiXinPayService.getPayResult(request,response);
			response.setContentType("text/xml");
			response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug("微信支付结果处理失败!");
		}finally {
			response.setContentType("text/xml");
			try {
				response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @author qipeng
	 * 查询支付状态
	 * @throws MqttException 
	 * */
	@ResponseBody
	@RequestMapping("/getOrderStatus")
	public Map<String,Object> getOrderStatus(int orderId) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		//String orderId = request.getParameter("orderId");
		//int id = Integer.parseInt(orderId);
		Orders orders = orderService.queryOrders(orderId);
		System.out.println("订单信息------------"+orders.toString());
		if(orders.getPayStatus() == 2) {
			System.out.println("支付成功处理-------------------------");
			int channelsType = orders.getChannelsType();
			System.out.println("货道类型---------------"+channelsType);
			/**
			 * 出货指令
			 * */
			if(channelsType == 1) {//格子柜出货指令
					map.put("code", "200");
					map.put("order_status", orders.getOrderStatus());
					map.put("pay_status", orders.getPayStatus());
					map.put("name", orders.getChannelId());
					map.put("isLongTime", false);
			}else if (channelsType == 2) {//冰山4货道出货指令
				//货道对应-----
				int channelId = orders.getChannelId();
				if(channelId>2) {
					channelId =channelId+1;
					map.put("name", "下取餐口");
				}else {
					map.put("name", "上取餐口");
				}
				map.put("code", "200");
				map.put("order_status", orders.getOrderStatus());
				map.put("pay_status", orders.getPayStatus());
				
			}
		}else if (orders.getPayStatus() == 3) {
			map.put("code", "202");
			map.put("pay_status", orders.getPayStatus());
		}else {
			map.put("code", "203");
		}
		return map;	
	}	
	
	/**
	 * @author qipeng
	 * 查询设备出货状态
	 * @throws IOException 
	 * */
	@ResponseBody
	@RequestMapping("/getVmStatus")
	public Map<String,Object> getVmStatus(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		String orderId = request.getParameter("orderId");
		int id = Integer.parseInt(orderId);
		Orders orders = orderService.queryOrders(id);
		int orderStatus = orders.getOrderStatus();
		if(orderStatus == 1) {
			//未出货
			map.put("code", "201");
		}else if (orderStatus == 2) {
			//成功
			map.put("code", "200");
			map.put("msg", "成功");
		}else if (orderStatus == 3) {
			//设备故障
			map.put("code", "202");
			map.put("msg", "设备故障");
		}else if (orderStatus == 4) {
			//超时取消
			map.put("code", "203");
			map.put("msg", "超时取消");
		}else if (orderStatus == 5) {
			//设备占用
			map.put("code", "204");
			map.put("msg", "设备被占用");
		}
		return map;
	}
	
	/**
	 * @author qipeng
	 * 取消订单
	 * @throws IOException 
	 * */
	@ResponseBody
	@RequestMapping("/orderCancel")
	public Map<String,Object>  setOrderCancel(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		logger.info("手动取消订单!");
		String orderIds = request.getParameter("orderId");
		int orderId = Integer.parseInt(orderIds);
		Orders orders = new Orders();
		orders.setId(orderId);
		orders.setPayStatus(3);
		int updateOrder = orderService.updateOrderAndSalesNum(orders);
		if(updateOrder > 0) {
			map.put("code", "200");
		}
		return map;
	}
	
	/**
	 * @author qipeng
	 * 获取点位详情
	 * */
	@ResponseBody
	@RequestMapping("/nodeDetail")
	public MapConstant getNodeDetail(HttpServletRequest request) {
		System.out.println("获取点位详情-------------");
		String nodeId = request.getParameter("nodeId");
		System.out.println("nodeId----------------"+nodeId);
		MapConstant constant = new MapConstant();
		Map<String,Object> map = new HashMap<String,Object>();
		Nodes nodes = nodesService.getNode(Integer.parseInt(nodeId));
		System.out.println("点位信息---------"+nodes.toString());
		map.put("name", nodes.getName());
		map.put("address", nodes.getAddress());
		constant.setCode("0");
		constant.setMsg("");
		constant.setData(map);
		return constant;
	}
	
	/**
	 * @author qipeng
	 * 获取商品列表
	 * */
	@ResponseBody
	@RequestMapping("/productList")
	public ListConstant getProductList(HttpServletRequest request) {
		List<Map<String, Object>> productList = weiXinPayService.getProductList(request);
		
		ListConstant constant = new ListConstant();
		constant.setCode("200");
		constant.setMsg("成功");
		constant.setData(productList);
		return constant;
	}
	
	/**
	 * @author qipeng
	 * 获取商品详情
	 * */
	@ResponseBody
	@RequestMapping("/productDetail")
	public MapConstant getProductDetails(HttpServletRequest request) {

		Map<String, Object> productDetails = weiXinPayService.getProductDetails(request);
		MapConstant constant = new MapConstant();
		constant.setCode("200");
		constant.setMsg("成功");
		constant.setData(productDetails);
		return constant;
	}
	
	/**
	 * @author qipeng
	 * 获取点位信息
	 * */
	@ResponseBody
	@RequestMapping("/nodeByGPS")
	public Map<String,Object> getNodes(HttpServletRequest request) {

		List<Map<String,Object>> nodeList = weiXinPayService.getNodesList();
		Map<String,Object> constant = new HashMap<String,Object>();
		constant.put("code", "200");
		constant.put("msg", "成功");
		constant.put("list", nodeList);
	
		return constant;
	}
	
	/**
	 * @author qipeng
	 * 获取订单信息
	 * */
	@ResponseBody
	@RequestMapping("/orderList")
	public Map<String,Object> getOrders(HttpServletRequest request) {
		String openId = request.getParameter("openId");
		if(openId == "" || openId.isEmpty()) {
			return null;
		}
		List<Map<String,Object>> getWXOrderList = weiXinPayService.getWXOrderList(request);
		Map<String,Object> constant = new HashMap<String,Object>();
		constant.put("code", "200");
		constant.put("msg", "成功");
		constant.put("list", getWXOrderList);
		//当前系统时间
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = dateFormat.format( now );
		constant.put("serverTime", format);
		return constant;
	}
	
	/**
	 * @author qipeng
	 * banner
	 * */
	@ResponseBody
	@RequestMapping("/banner")
	public Map<String,Object> getBanner(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> list = weiXinPayService.getBannerList(request);
		if(list.size()>0) {
			map.put("code", "200");
			map.put("count", list.size());
			map.put("list", list);
		}
		return map;
	}
	
}
