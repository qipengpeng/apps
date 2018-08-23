/**
 * 
 */
package com.ugo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.ReplenishmentLogsDao;
import com.ugo.entity.ReplenishmentLogDetails;
import com.ugo.entity.ReplenishmentLogs;

/**
 * @author sunshangfeng
 *
 */
@Service
public class ReplenishmentLogsService {
		
	@Autowired
	private ReplenishmentLogsDao repDao;
	
	//获取补货列表
	public List<ReplenishmentLogs>getRepList(){
		List<ReplenishmentLogs> repList = repDao.getRepList();
		return repList;
	}
	
	//插入补货任务列表
	@Transactional
	public void addReplenishmentLogs(ReplenishmentLogs replenishmentLogs) {
		repDao.addReplenishmentLogs(replenishmentLogs);
	}
	//插入补货记录详情
	@Transactional
	public void addReplenishmentLogDetails(ReplenishmentLogDetails ReplenishmentLogDetails) {
		repDao.addReplenishmentLogDetails(ReplenishmentLogDetails);
	}
}
