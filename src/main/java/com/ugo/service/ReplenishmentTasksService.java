/**
 * 
 */
package com.ugo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.ReplenishmentDetailsDao;
import com.ugo.dao.ReplenishmentTasksDao;
import com.ugo.entity.ReplenishmentDetails;
import com.ugo.entity.ReplenishmentTasks;


/**
 * @author sunshangfeng
 *
 */
@Service
@Transactional
public class ReplenishmentTasksService {
	
	@Autowired
	private ReplenishmentTasksDao replenishmentTasksDao;
	@Autowired
	private ReplenishmentDetailsDao replenishmentDetailsDao;
	//添加补货任务并返回任务ID
	public int addReplenishmentTasks(ReplenishmentTasks replenishmentTasks) {
		replenishmentTasksDao.addReplenishmentTasks(replenishmentTasks);
		int id = replenishmentTasks.getId();
		return id;
	}
	
	//添加补货任务详情
	public void addReplenishmentDetails(ReplenishmentDetails replenishmentDetails) {
		replenishmentTasksDao.addReplenishmentDetails(replenishmentDetails);
	}
	
	//修改补货详情状态
	public void updateReplenishmentDetailsState(ReplenishmentDetails replenishmentDetails) {
		replenishmentDetailsDao.updateReplenishmentDetailsState(replenishmentDetails);
	}
	//修改补货任务状态
	public void updateState(int id) {
		replenishmentTasksDao.updateState(id);
	}
	
}
