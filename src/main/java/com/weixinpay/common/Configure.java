package com.weixinpay.common;

public class Configure {
	private static String key = "NIA0ysdMqf5BLPdBvqcBvcLIIgMftxgH";

	//小程序ID	
	private static String appID = "wxb91d31be653d1fad";
	//商户号
	private static String mch_id = "1453683402";
	//
	private static String secret = "4ca0ea0bf5b7ac94577382e2d5a3416f";
	
	public static String certLocalPath = "WEB-INF/cert/ugo/apiclient_cert.p12";  
	
	//微信支付回调地址
	public static String notify_url = "https://tfan.ugobao.cn/front/PayResult";//测试
	//public static String notify_url = "https://f.ugobao.cn/front/PayResult";
	
	
	public static String getCertLocalPath() {
		return certLocalPath;
	}

	public static void setCertLocalPath(String certLocalPath) {
		Configure.certLocalPath = certLocalPath;
	}

	public static String getNotify_url() {
		return notify_url;
	}

	public static void setNotify_url(String notify_url) {
		Configure.notify_url = notify_url;
	}

	public static String getSecret() {
		return secret;
	}

	public static void setSecret(String secret) {
		Configure.secret = secret;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		Configure.key = key;
	}

	public static String getAppID() {
		return appID;
	}

	public static void setAppID(String appID) {
		Configure.appID = appID;
	}

	public static String getMch_id() {
		return mch_id;
	}

	public static void setMch_id(String mch_id) {
		Configure.mch_id = mch_id;
	}

}
