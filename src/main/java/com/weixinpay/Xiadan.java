package com.weixinpay;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.weixinpay.common.Configure;
import com.weixinpay.common.HttpRequest;
import com.weixinpay.common.RandomStringGenerator;
import com.weixinpay.common.Signature;
import com.weixinpay.model.OrderInfo;
import com.weixinpay.model.OrderReturnInfo;

/**
 * 统一下单接口
 */
public class Xiadan {
	private static final long serialVersionUID = 1L;
	private static final Logger L = LoggerFactory.getLogger(Xiadan.class);

	/**
	 * @return 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public static String getPrepayId(OrderInfo order,HttpServletRequest request) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			//String openid = request.getParameter("openId");
//			System.out.println("openid-----"+openid);
//			OrderInfo order = new OrderInfo();
			order.setAppid(Configure.getAppID());
			order.setMch_id(Configure.getMch_id());
			order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			order.setBody("商品");
			order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(32));
			//order.setTotal_fee(1);
			String ip = getIp(request);
			order.setSpbill_create_ip(ip);
			order.setNotify_url(Configure.notify_url);
			order.setTrade_type("JSAPI");
//			order.setOpenid(openid);
			order.setSign_type("MD5");
			//生成签名
			System.out.println("order-----------"+order.toString());
			String sign = Signature.getSign(order);
			System.out.println("xiadan-sign+++"+sign);
			order.setSign(sign);
			
			String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
			System.out.println("result--------"+result);
			L.info("---------下单返回:"+result);
			XStream xStream = new XStream();
			xStream.alias("xml", OrderReturnInfo.class); 

			OrderReturnInfo returnInfo = (OrderReturnInfo)xStream.fromXML(result);
			String prepayId = returnInfo.getPrepay_id();
			return prepayId;
		} catch (Exception e) {
			e.printStackTrace();
			L.error("获取prepayId失败-------", e);
		}
		return "";
	}

	//取服务器IP
		public static String getIp() throws UnknownHostException {
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress().toString();
			return ip;
		}
		//获取客户端Ip
		public static String getIp(HttpServletRequest request) {
			System.out.println("获取客户端IP--------"+request.getRemoteAddr());
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
}
