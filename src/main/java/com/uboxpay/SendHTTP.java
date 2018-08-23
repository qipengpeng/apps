/**
 * 
 */
package com.uboxpay;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;

/**
 * @author qipeng 2018/8/13
 * 发送短信验证
 */
public class SendHTTP {
	//短信接口
	public static String url="http://101.200.228.238/NewSmsPort/default.asmx/SendSms";
	//账户密码
	public static String username="ygb001";
	public static String password="841123";
	
	public static boolean sendShortMsg(String phonelist,String msg) {
		TreeMap<String, Object> obj = new TreeMap<String, Object>();
		obj.put("username", username);
		obj.put("password", password);
		obj.put("longnum", "");
		obj.put("phonelist", phonelist);
		obj.put("msg", "【友饭】 绑定友宝钱包验证码为："+msg+"，如非本人操作请忽略。");
		boolean sendPost = sendPost(url,obj);
		return sendPost;
	}
	
	public static boolean sendPost(String url,TreeMap<String,Object> sortedMap)  {
	      //拼接url参数
	        String datas = "";
	        for(String s:sortedMap.keySet()){
	            datas+=s+"="+sortedMap.get(s)+"&";
	        }
	        
	        try {
	        	//请求地址
	        	URL obj = new URL(url);
	        	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        	// http正文内，因此需要设为true, 默认情况下是false; 
	        	con.setDoOutput(true); 
	        	
	        	// 设置是否从httpUrlConnection读入，默认情况下是true; 
	        	con.setDoInput(true); 
	        	
	        	// Post 请求不能使用缓存 
	        	con.setUseCaches(false); 
	        	con.setRequestProperty("Content-type", "application/x-www-form-urlencoded"); 
	        	con.setRequestMethod("POST");
	        	//发送post请求  必须
	        	con.setDoOutput(true);
	        	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.write(datas.getBytes());
				wr.flush();
				wr.close();
				
				BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		        String inputLine;
		        StringBuffer response = new StringBuffer();

		        while ((inputLine = in.readLine()) != null) {
		            response.append(inputLine);
		        }
		        System.out.println(response.toString());
		        in.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
}
