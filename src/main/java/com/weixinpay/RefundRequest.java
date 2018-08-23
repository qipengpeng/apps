/**
 * 
 */
package com.weixinpay;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.ugo.web.DeliveriesController;
import com.weixinpay.common.Configure;

/**
 * @author sunshangfeng
 *RefundRequest类中httpsRequest方法调用微信接口，触发请求。
 */

public class RefundRequest {
	private static final Logger logger = LoggerFactory.getLogger(DeliveriesController.class);
    //连接超时时间，默认10秒
    private int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private int connectTimeout = 30000;

    //请求器的配置
    private RequestConfig requestConfig;

    //HTTP请求器
    private CloseableHttpClient httpClient;

    /**
     * 加载证书
     * @param path
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private void initCert(String path) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        //拼接证书的路径
        path = path + Configure.certLocalPath;
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
//        String mch_id = Configure.getMch_id();
//        System.out.println(mch_id);
        //加载本地的证书进行https加密传输
        FileInputStream instream = new FileInputStream(new File(path));
        try {
            keyStore.load(instream, Configure.getMch_id().toCharArray());  //加载证书密码，默认为商户ID
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, Configure.getMch_id().toCharArray())       //加载证书密码，默认为商户ID
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

    }


    /**
     * 通过Https往API post xml数据
     * @param url   API地址
     * @param xmlObj   要提交的XML数据对象
     * @param path    当前目录，用于加载证书
     * @return
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String httpsRequest(String url, Object xmlObj, String path) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        //加载证书
        initCert(path);

        String result = null;

        HttpPost httpPost = new HttpPost(url);

      //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStreamForRequestPostData.alias("xml", xmlObj.getClass());
        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        //设置请求器的配置
        httpPost.setConfig(requestConfig);

        try {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ConnectionPoolTimeoutException e) {
        	logger.trace("http get throw ConnectionPoolTimeoutException(wait time out)");

        } catch (ConnectTimeoutException e) {
        	logger.trace("http get throw ConnectTimeoutException");

        } catch (SocketTimeoutException e) {
        	logger.trace("http get throw SocketTimeoutException");

        } catch (Exception e) {
        	logger.trace("http get throw Exception");

        } finally {
            httpPost.abort();
        }

        return result;
    }
}

