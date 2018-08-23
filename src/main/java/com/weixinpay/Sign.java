package com.weixinpay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixinpay.common.Configure;
import com.weixinpay.common.RandomStringGenerator;
import com.weixinpay.common.Signature;
import com.weixinpay.model.SignInfo;

/**
 * 再签名
 */
public class Sign{
	private static final long serialVersionUID = 1L;
	private static final Logger L = LoggerFactory.getLogger(Sign.class);

	/**
	 * @see prepay_id
	 */
	public static Map<String,Object> getSign(String prepay_id) throws ServletException, IOException {
		Map<String,Object> signMap = new HashMap<String,Object>();
	    try {
			SignInfo signInfo = new SignInfo();
			signInfo.setAppId(Configure.getAppID());
			long time = System.currentTimeMillis()/1000;
			signInfo.setTimeStamp(String.valueOf(time));
			signInfo.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
			signInfo.setRepay_id("prepay_id="+prepay_id);
			signInfo.setSignType("MD5");
			//生成签名
			String sign = Signature.getSign(signInfo);
			System.out.println("sign--------"+sign);
			signMap.put("appId", Configure.getAppID());
			signMap.put("timeStamp", signInfo.getTimeStamp());
			signMap.put("nonceStr", signInfo.getNonceStr());
			signMap.put("package", signInfo.getRepay_id());
			signMap.put("signType", signInfo.getSignType());
			signMap.put("paySign", sign);
			L.info("-------再签名:"+signMap.toString());
			//response.getWriter().append(json.toJSONString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			L.error("-------", e);
		}
	    return signMap;
	}

}
