/**
 * 
 */
package com.ugo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.RefundLogsDao;
import com.ugo.entity.RefundLogs;

/**
 * @author sunshangfeng
 *退款记录
 */
@Service
@Transactional
public class RefundLogsService {
	
	@Autowired
	private RefundLogsDao refundLogsDao;
	
	
	/**
	 * 保存退款记录
	 * */
	
	public void addRefundLogs(RefundLogs refundLogs) {
		refundLogsDao.addRefundLogs(refundLogs);
	}
	
}
