/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import com.ugo.entity.NodeVms;
import com.ugo.entity.Nodes;
import com.ugo.entity.ReplenishmentDetails;
import com.ugo.entity.Sales;

/**
 * @author sunshangfeng
 *任务详情
 */
public interface ReplenishmentDetailsDao {
	
	/**
	 * 获取点位信息
	 * */
	List<Nodes> queryNodesList(int taskId);
	
	/**
	 * 获取设备信息
	 * */
	List<NodeVms> queryNodeVmsList(ReplenishmentDetails replenishmentDetails);
		
	/**
	 * 获取任务商品信息
	 * */
	List<ReplenishmentDetails> queryReplenishmentDetails(ReplenishmentDetails replenishmentDetails);
	
	/**
	 * 获取实际商品信息
	 * */
	ReplenishmentDetails querySales(ReplenishmentDetails replenishmentDetails);
	
	/**
	 * 获取模版类型
	 * */
	int getChannelType(int vendorId);
	
	/**
	 * 库存展示
	 * *************/
	List<Nodes> querySalesNodeList();
	/**
	 * 库存展示
	 * *************/
	List<NodeVms> querySalesNodeVmsList(int id);
	/**
	 * 库存展示
	 * *************/
	List<Sales> getSales(Sales sales);
	
	
	/**
	 * 库存更新
	 * *************/
	
	/**
	 * 修改补货详情状态
	 * */
	void updateReplenishmentDetailsState(ReplenishmentDetails replenishmentDetails);
	
	/**
	 * 获取任务单个商品数量--至补货记录
	 * */
	int queryReplenishmentDetailsNum(ReplenishmentDetails replenishmentDetails);
	
	/**
	 * 获取加热时间
	 * */
	int getHeatUpTime(ReplenishmentDetails replenishmentDetails);
}
