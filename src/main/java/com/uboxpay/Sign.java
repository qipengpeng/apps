package com.uboxpay;

import java.security.MessageDigest;
import java.util.Map;


/**
 * 签名
 */
public class Sign{

	/*
     * 生产sign（签名字符串）
     * @param map 请求的参数数组(TreeMap类型，使获取的数组符合ascii顺序)
     * @param appKey 分配给第三方的app_key
     */
	//获取签名sign字符串方法
    public String sign(Map<String,Object> map,String appKey) throws Exception{
        String datasSign="";
        for(String s:map.keySet()){
        	datasSign+=s+"="+map.get(s);
        }
        datasSign+="_"+appKey;

        //签名算法
        MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
        digest.update(datasSign.getBytes());
        byte messageDigest[] = digest.digest();

        StringBuffer hexString = new StringBuffer();
        // 字节数组转换为 十六进制 数
        for (int i = 0; i < messageDigest.length; i++) {
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        return hexString.toString();
    }

}
