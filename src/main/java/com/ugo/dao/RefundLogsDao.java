/**
 * 
 */
package com.ugo.dao;

import com.ugo.entity.RefundLogs;

/**
 * @author sunshangfeng
 *退款记录
 */
public interface RefundLogsDao {
	
	/**
	 * 保存退款记录
	 * */
	void addRefundLogs(RefundLogs refundLogs);
}
