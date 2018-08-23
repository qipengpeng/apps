/**
 * 
 */
package com.ugo.dao;

import com.ugo.entity.PayResultInfo;
import com.ugo.entity.UboxPayResult;

/**
 * @author sunshangfeng
 *
 */
public interface PayResultDao {
	
	/**
	 * 保存支付通知
	 * */
	void addPayResultInfo(PayResultInfo payResult);
	
	/**
	 * 获取微信支付信息
	 * */
	String getTransactionID(int orderId);
	/**
	 * 获取微信支付
	 * */
	int getTotalFee(int orderId);
	
	/**
	 * @author qipeng 2018/8/9
	 * 保存钱包支付号
	 * @param result
	 * */
	void saveUboxPayResult(UboxPayResult result);
	
	/**
	 * @author qipeng 2018/8/9
	 * 获取钱包支付信息
	 * @param result
	 * */
	UboxPayResult findPayInfo(int orderId);
}
