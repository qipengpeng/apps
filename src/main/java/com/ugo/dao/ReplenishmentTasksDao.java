/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import com.ugo.entity.DeliveryDetails;
import com.ugo.entity.DemandTemplateDetails;
import com.ugo.entity.NodeVms;
import com.ugo.entity.Nodes;
import com.ugo.entity.Principal;
import com.ugo.entity.ProductTemplateTetails;
import com.ugo.entity.ReplenishmentDetails;
import com.ugo.entity.ReplenishmentTasks;

/**
 * @author sunshangfeng
 *补货任务
 */
public interface ReplenishmentTasksDao {
	
	/**
	 * 添加任务列表
	 * */
	void addReplenishmentTasks(ReplenishmentTasks replenishmentTasks);
	
	/**
	 * 添加任务详情
	 * */
	void addReplenishmentDetails(ReplenishmentDetails replenishmentDetails);
	    
	
	/**
	 * 获取未执行的补货商品详情
	 * */
	List<ReplenishmentDetails> getReplenishmentDetailsList(ReplenishmentTasks replenishmentTasks);
		
	/**
	 * 获取最新补货任务
	 * */
	ReplenishmentTasks getReplenishmentTasks(int operatorId);
	
	
	/**
	 * *******************
	 * */
	
	/**
	 * 获取补货信息
	 * */
	ReplenishmentTasks queryReplenishmentTasks(int deliveriesId);

	/**
	 * 根据点位获取对应的补货员
	 * */
	ReplenishmentTasks getOperatorId(int nodeId);
	
	/**
	 * 根据交割id和人员id查询对应人员补货任务是否已存在
	 * */
	int queryOperator(ReplenishmentTasks replenishmentTasks);
	
	/**
	 * 所有配送人员*******************
	 * */
	List<Principal> queryPrincipalList();

	/**
	 * 对应负责的点位
	 * */
	List<Nodes> queryNodesList(int principalId);
	
	/**
	 * 判断点位是否存在
	 * */
	int queryNodes(DeliveryDetails deliveryDetails);
	
	/**
	 * 获取包含的设备信息
	 * */
	List<NodeVms> queryNodeVmsList(DeliveryDetails deliveryDetails);
	
	/**
	 * 获取交割的商品信息
	 * */
	List<DemandTemplateDetails> queryDemandTemplateDetails(DeliveryDetails deliveryDetails);

	/**
	 * 获取对应的模板详细信息
	 * */
	List<ProductTemplateTetails> queryProductTemplateTetails(DeliveryDetails deliveryDetails);
	
	/**
	 * 修改补货任务状态
	 * */
	void updateState(int id);
}
