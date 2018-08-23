/**
 * 
 */
package com.ugo.dto;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *
 */
public class VmHttpRequest {
		
		private static final Logger logger = (Logger) LoggerFactory.getLogger(VmHttpRequest.class);
		//连接超时时间，默认10秒
		private static final int socketTimeout = 10000;
		
		//传输超时时间，默认30秒
		private static final int connectTimeout = 30000;
		
		private static String corpid = "wx701e25f96a5aade8";
		private static String AgentId = "1000006";
		private static String toparty = "33";//部门ID
		private static String Secret = "Dsq4Gxl9INjgOIdUXWMs5L9XQb3l1S3f32TuSW7_KgI";
		private static String touser = "13522334959|18710050798|13439342423";//成员ID
		
		private static String getUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?";
		private static String postUrl ="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";
		/**
		 * post请求
		 * @throws ParseException 
		 * @throws IOException 
		 * @throws ClientProtocolException 
		 * @throws NoSuchAlgorithmException 
		 * @throws KeyStoreException 
		 * @throws KeyManagementException 
		 * @throws UnrecoverableKeyException 
		 */
		public static JSONObject sendPost(String token,Map<String,String> map) throws IOException {
			 HttpPost httpPost = new HttpPost(postUrl+token);
			 JSONObject json = new JSONObject();
			 json.put("touser", touser);
			 json.put("toparty", toparty);
			 json.put("msgtype", "text");
			 json.put("agentid", AgentId);
			 json.put("text", map);
			 json.put("safe", 0);
			 StringEntity postEntity = new StringEntity(json.toJSONString(), "UTF-8");
	         httpPost.addHeader("Content-Type", "application/json");
	         httpPost.setEntity(postEntity);
		        
	        //设置请求器的配置
	        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
	        httpPost.setConfig(requestConfig);
	        
	        HttpClient httpClient = HttpClients.createDefault();
	        HttpResponse response = httpClient.execute(httpPost);
	        HttpEntity entity = response.getEntity();
	        String result = EntityUtils.toString(entity, "UTF-8");
	        JSONObject parse = (JSONObject) JSON.parse(result);
	        logger.info("企业微信推送返回结果:"+parse.toJSONString());
	        return parse;
		}
		
		/**
		 * 企业微信 post请求 发送图片
		 * @throws IOException 
		 */
		public static JSONObject sendPostIMG(String token,Map<String,String> map) throws IOException {
			 HttpPost httpPost = new HttpPost(postUrl+token);
			 JSONObject json = new JSONObject();
			 json.put("touser", "13240114101");
			 json.put("toparty", "");
			 json.put("msgtype", "image");
			 json.put("agentid", AgentId);
			 json.put("image", map);
			 json.put("safe", 0);
			 StringEntity postEntity = new StringEntity(json.toJSONString(), "UTF-8");
	         httpPost.addHeader("Content-Type", "application/json");
	         httpPost.setEntity(postEntity);
		        
	        //设置请求器的配置
	        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
	        httpPost.setConfig(requestConfig);
	        
	        HttpClient httpClient = HttpClients.createDefault();
	        HttpResponse response = httpClient.execute(httpPost);
	        HttpEntity entity = response.getEntity();
	        String result = EntityUtils.toString(entity, "UTF-8");
	        JSONObject parse = (JSONObject) JSON.parse(result);
	        logger.info("企业微信推送截屏返回结果:"+parse.toJSONString());
	        return parse;
		}
		
		/**
		 * Get请求
		 * @throws IOException
		 * @throws ClientProtocolException
		 * @throws NoSuchAlgorithmException
		 * @throws KeyStoreException
		 * @throws KeyManagementException
		 * @throws UnrecoverableKeyException
		 */
		public static JSONObject sendGet() throws IOException {
			 
			String url = getUrl+"corpid="+corpid+"&corpsecret="+Secret;
			HttpGet httpGet = new HttpGet(url);
			 
			 HttpClient httpClient = HttpClients.createDefault();
			// 发送请求
			 HttpResponse httpResponse = httpClient.execute(httpGet);
		    // 获取返回的数据
			 HttpEntity entity = httpResponse.getEntity();
	         String result = EntityUtils.toString(entity, "UTF-8");
	         JSONObject parse = (JSONObject) JSON.parse(result);
	         return parse;
		}

    /**
     * @desc ：微信上传素材的请求方法
     * @param : file    要上传的文件
     * @return String  上传成功后，微信服务器返回的消息
     */
    public static String httpRequest(MultipartFile file1,String token) throws HttpException {
        StringBuffer buffer = new StringBuffer();
        File file = null;
        try {
            file=File.createTempFile("tmp", "");
            file1.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String type = "image";
        try{
            //1.建立连接
            URL url = new URL("https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token="+token+"&type="+type);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  //打开链接

            //1.1输入输出设置
            httpUrlConn.setDoInput(true);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setUseCaches(false); // post方式不能使用缓存
            //1.2设置请求头信息
            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
            //1.3设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            httpUrlConn.setRequestProperty("Content-Type","multipart/form-data; boundary="+ BOUNDARY);

            // 请求正文信息
            // 第一部分：
            //2.将文件头输出到微信服务器
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length()
                    + "\";filename=\""+ file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            // 获得输出流
            OutputStream outputStream = new DataOutputStream(httpUrlConn.getOutputStream());
            // 将表头写入输出流中：输出表头
            outputStream.write(head);

            //3.将文件正文部分输出到微信服务器
            // 把文件以流文件的方式 写入到微信服务器中
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                outputStream.write(bufferOut, 0, bytes);
            }
            in.close();
            //4.将结尾部分输出到微信服务器
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            outputStream.write(foot);
            outputStream.flush();
            outputStream.close();


            //5.将微信服务器返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
                System.out.println(buffer.toString());
            }

            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();


        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        JSONObject parse = (JSONObject) JSON.parse(buffer.toString());
        String errcode = parse.getString("errcode");
        if(!"0".equals(errcode) ) {
            return "EEROR";
        }
        return parse.getString("media_id");
    }
}
