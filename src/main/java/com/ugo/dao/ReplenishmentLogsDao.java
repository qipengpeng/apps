/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import com.ugo.entity.ReplenishmentLogDetails;
import com.ugo.entity.ReplenishmentLogs;

/**
 * @author sunshangfeng
 *
 */
public interface ReplenishmentLogsDao {
	//获取补货列表
	List<ReplenishmentLogs>getRepList();
	
	//插入补货记录列表
	void addReplenishmentLogs(ReplenishmentLogs replenishmentLogs);
	//保存补货记录详情
	void addReplenishmentLogDetails(ReplenishmentLogDetails ReplenishmentLogDetails);
	
	//
	ReplenishmentLogs getReplenishmentLogs(int id);
	//
	List<ReplenishmentLogDetails> getReplenishmentLogDetails(int id);
	
}
