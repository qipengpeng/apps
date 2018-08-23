package com.ugo.test;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.weixinpay.common.XmlMapUtils;


public class Test4 {

	public static void main(String[] args) throws UnknownHostException, IllegalAccessException {
		// TODO Auto-generated method stub
		String randomUUID = UUID.randomUUID().toString().replace("-", "");
		System.out.println(randomUUID);
		InetAddress addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress().toString();
		System.out.println(ip);
		
		//XML转换成对象
		
		String a = "<xml><appid><![CDATA[wxb91d31be653d1fad]]></appid>\r\n" + 
				"<bank_type><![CDATA[CFT]]></bank_type>\r\n" + 
				"<cash_fee><![CDATA[1]]></cash_fee>\r\n" + 
				"<fee_type><![CDATA[CNY]]></fee_type>\r\n" + 
				"<is_subscribe><![CDATA[N]]></is_subscribe>\r\n" + 
				"<mch_id><![CDATA[1453683402]]></mch_id>\r\n" + 
				"<nonce_str><![CDATA[apws4jquw59fr9cngsvhmopj709c90kh]]></nonce_str>\r\n" + 
				"<openid><![CDATA[oXFA95exxPbLwf20UoyX00OKtrqg]]></openid>\r\n" + 
				"<out_trade_no><![CDATA[ztwqvaloy8nhwt8o5beoedl5aw3s09un]]></out_trade_no>\r\n" + 
				"<result_code><![CDATA[SUCCESS]]></result_code>\r\n" + 
				"<return_code><![CDATA[SUCCESS]]></return_code>\r\n" + 
				"<sign><![CDATA[3167A31F35713DD30B709125D2E90EB5]]></sign>\r\n" + 
				"<time_end><![CDATA[20180608172515]]></time_end>\r\n" + 
				"<total_fee>1</total_fee>\r\n" + 
				"<total_fee_0>1</total_fee_0>\r\n" + 
				"<trade_type><![CDATA[JSAPI]]></trade_type>\r\n" + 
				"<transaction_id><![CDATA[4200000150201806083350903604]]></transaction_id>\r\n" + 
				"</xml>";
		String a1 = a.replace("<xml>", "");
		String a2 = a1.replace("</xml>", "");
		Map<String, Object> map = XmlMapUtils.xmlToMap(a);
		System.out.println(map);
		Map<String, Object> map1 = (Map<String, Object>) map.get("xml");
		System.out.println(map1.get("out_trade_no"));
	}

}
