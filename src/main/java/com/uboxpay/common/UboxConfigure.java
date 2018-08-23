/**
 * 
 */
package com.uboxpay.common;

/**
 * @author qipeng
 * 2018/8/6
 * 友宝钱包接口数据
 */
public class UboxConfigure {
	
	public static String appId="765b54c1dad08464a6fd90242ba94bda";
	public static String appKey="7d1e4f3decb406d24707d11fb3e35629";
	public static String uboxBalance="http://uboxapi.ubox.cn/opentrade/uboxBalance";
	public static String uboxOrderPay="http://uboxapi.ubox.cn/opentrade/uboxOrderPay";
	public static String uboxOrderRefund="http://uboxapi.ubox.cn/opentrade/uboxOrderRefund";
//	测试
//	public static String appId="0d0e915948c8db9695c763adf504475d";
//	public static String appKey="0b95ad11bc2fa2880334634ae71122ce";
	
	public static String getAppId() {
		return appId;
	}

	public static void setAppId(String appId) {
		UboxConfigure.appId = appId;
	}

	public static String getAppKey() {
		return appKey;
	}

	public static void setAppKey(String appKey) {
		UboxConfigure.appKey = appKey;
	}
			
}
