package com.ugo.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.client.ClientProtocolException;

import com.uboxpay.UboxHttp;

public class Test3 {
//	//连接超时时间，默认10秒
//    private static final int socketTimeout = 10000;
//
//    //传输超时时间，默认30秒
//    private static final int connectTimeout = 30000;
    
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		TreeMap<String, Object> obj = new TreeMap<String, Object>();
		TreeMap<String, Object> obj1 = new TreeMap<String, Object>();
		 String urlRefund= "http://uboxapi.ubox.cn/opentrade/uboxOrderRefund";
		 String urlPay= "http://uboxapi.dev.uboxol.com/opentrade/uboxOrderPay";
		 	String url= "http://uboxapi.dev.uboxol.com/opentrade/uboxBalance";
			String app_id="0d0e915948c8db9695c763adf504475d";
			String app_id1="765b54c1dad08464a6fd90242ba94bda";
			String phone="13243797679";
			String open_id="test_openid";
			String product_id="10270";
			String subject="测试商品";
			String body="测试商品001";
			int product_price=1;
			int pay_amount=1;
			String order_id="1341234123413";
			String sign="9b87663bfc9b3f9b04c6ff1d523770c5efe9d8df";
			obj.put("app_id", app_id);
			obj1.put("app_id", app_id1);
			obj.put("open_id", open_id);
			obj.put("phone", phone);
			obj.put("product_id", product_id);
			obj.put("subject", subject);
			obj.put("body", body);
			obj.put("product_price", product_price);
			obj.put("pay_amount", pay_amount);
			obj1.put("pay_amount", pay_amount);
			obj.put("order_id", order_id);
			obj1.put("order_id", 2730);
			obj1.put("trade_no", "TF013ubox_p180809181017p997921");
			System.out.println("urlPay:"+urlPay+"\r\napp_id:"+app_id+"\r\nphone:"+phone+"\r\nopen_id:"+open_id+"\r\nproduct_id:"+product_id+"\r\nsubject:"+subject+"\r\nbody:"+body+"\r\nproduct_price:"+product_price+"\r\npay_amount:"+pay_amount+"\r\norder_id:"+order_id);
			System.out.println("文档sign="+sign);
			//obj.put("sign", sign);
			//String b = "app_id=0d0e915948c8db9695c763adf504475d&phone=13243797679&open_id=test_openid&sign=e22a2e7fa6754879e7bc2e0937628e79dc3d0901";
			//String sendPost = sendPost(url, b);
			//System.out.println(sendPost);
			//System.out.println("obj:"+obj);
			//-------------------------------------------------TF013ubox_p180402152345p466243
			try {
				String sign2 = UboxHttp.sign(obj, "0b95ad11bc2fa2880334634ae71122ce");
				System.out.println("sign:"+sign2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				//Map<String, Object> example = UboxHttp.example(obj,urlPay);
				Map<String, Object> example = UboxHttp.example(obj1,urlRefund);
				System.out.println("example="+example);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * 向指定URL发送GET方法的请求
	 * @param url 发送请求的URL
	 * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
 
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = url + "?" + param;
			URL realUrl = new URL(urlName);
			//打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			//设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			//建立实际的连接
			conn.connect();
			//获取所有响应头字段
			Map <String, List< String >> map = conn.getHeaderFields();
			//遍历所有的响应头字段
			for (String key: map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			//定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in .readLine()) != null) {
				result += "/n" + line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		//使用finally块来关闭输入流
		finally {
			try {
				if ( in != null) { in .close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	
	/**
	 * 向指定URL发送POST方法的请求
	 * @param url 发送请求的URL
	 * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			//打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			//设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			//发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			//获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			//发送请求参数
			out.print(param);
			//flush输出流的缓冲
			out.flush();
			//定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in .readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if ( in != null) { in .close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	
	
}
