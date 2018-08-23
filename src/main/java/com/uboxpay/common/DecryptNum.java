/**
 * 
 */
package com.uboxpay.common;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import ch.qos.logback.classic.Logger;

/**
 * @author qipeng 2018/8/7
 *	base64解密  
 *	获取手机号码
 */
public class DecryptNum {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(DecryptNum.class);
	
	public static JSONObject decryptJson(String encrypData,String ivData,String sessionKey) throws Exception {
		byte[] encryp_data = Base64.decode(encrypData);
        byte[] iv_data = Base64.decode(ivData);
        byte[] session_key = Base64.decode(sessionKey);
        String decrypt = decrypt(session_key,iv_data,encryp_data);
       /* try {
			 
		} catch (Exception e) {
			logger.error("Base64未解析成功!");
		}*/
        logger.info("Base64解析"+decrypt);
		return JSONObject.parseObject(decrypt);
	}
	
	/**
	 * Base64解析
	 * */
	public static String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        //解析解密后的字符串
        return new String(cipher.doFinal(encData),"UTF-8");
    }
}
