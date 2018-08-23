/**
 * 
 */
package com.ugo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.PayResultDao;
import com.ugo.entity.UboxPayResult;

/**
 * @author sunshangfeng
 * 
 */
@Service
@Transactional
public class PayResultService {
	
	@Autowired
	private PayResultDao payResultDao;
	
	/**
	 * 获取微信支付信息
	 * */
	public String getTransactionID(int orderId) {
		String transactionID = payResultDao.getTransactionID(orderId);
		return transactionID;
	}
	/**
	 * 获取微信支付
	 * */
	public int getTotalFee(int orderId) {
		int totalFee = payResultDao.getTotalFee(orderId);
		return totalFee;
	}
	
	/**
	 * 保存钱包支付号
	 * */
	public void saveUboxPayResult(UboxPayResult result) {
		payResultDao.saveUboxPayResult(result);
	}
	
	/**
	 * @author qipeng 2018/8/9
	 * 获取钱包支付信息
	 * @param result
	 * */
	public UboxPayResult findPayInfo(int orderId) {
		UboxPayResult payInfo = payResultDao.findPayInfo(orderId);
		return payInfo;
	}
	
}
