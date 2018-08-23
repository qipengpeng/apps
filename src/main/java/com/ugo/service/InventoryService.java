/**
 * 
 */
package com.ugo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.InventoryDao;
import com.ugo.entity.InventoryDetails;
import com.ugo.entity.ReplenishmentTasks;

/**
 * @author sunshangfeng
 *
 */
@Service
public class InventoryService {
	
	@Autowired
	private InventoryDao inventoryDao;
	
	/**
	 * 插入清货列表
	 * */
	@Transactional
	public void addInventory(ReplenishmentTasks replenishmentTasks) {
		inventoryDao.addInventory(replenishmentTasks);
	}
	
	/**
	 * 添加清货详情
	 * */
	public void addInventoryDetails(InventoryDetails inventoryDetails) {
		inventoryDao.addInventoryDetails(inventoryDetails);
	}
}
