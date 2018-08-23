/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ugo.entity.DemandTemplateDetails;
import com.ugo.entity.Inventory;
import com.ugo.entity.InventoryDetails;
import com.ugo.entity.NodeVms;
import com.ugo.entity.Nodes;
import com.ugo.entity.ReplenishmentTasks;

/**
 * @author sunshangfeng
 *清货报损dao
 */
public interface InventoryDao {
	
	/**
	 * 查询清货列表
	 * */
	List<Inventory>queryInventoryList();
	
	/**
	 * 查询清货详情
	 * */
	Inventory getInventory(int id);
	List<Nodes> getNodes(int inventoryId);
	List<NodeVms> getNodeVms(InventoryDetails inventoryDetails);
	List<DemandTemplateDetails>getDemandTemplateDetails(InventoryDetails inventoryDetails);
			
	/**
	 * 查询库存剩余
	 * */
	List<InventoryDetails> getSales(@Param("nodeId")int nodeId,@Param("vendorId")int vendorId);
	
	/**
	 * 查询清货id
	 * */
	int getInventoryId(int taskId);
	
	/**
	 * 添加清货列表
	 * */
	void addInventory(ReplenishmentTasks replenishmentTasks);
	
	/**
	 * 添加清货详情
	 * */
	void addInventoryDetails(InventoryDetails inventoryDetails);
	
}
