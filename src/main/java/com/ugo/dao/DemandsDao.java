/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import com.ugo.entity.Delivers;
import com.ugo.entity.DeliversNode;
import com.ugo.entity.DemandDetails;
import com.ugo.entity.DemandTemplateDetails;
import com.ugo.entity.Demands;
import com.ugo.entity.NodeVms;
import com.ugo.entity.Nodes;

/**
 * @author sunshangfeng
 *点位需求列表
 */
public interface DemandsDao {
	
	/**
	 * 获取需求列表
	 * */
	List<Demands> getDemandsList();
	
	/**
	 * 根据Id获取需求列表
	 * */
	Demands getDemand(int id);
	
	/**
	 * 根据需求ID获取点位ID
	 * */
	List<DemandDetails> getProductIdList(int demandId);
	
	/**
	 * 添加需求信息
	 * */
	int addDemands(Demands demands);
	
	/**
	 * 添加需求详情信息
	 * */
	void addDemandDetails(DemandDetails demandDetails);
	
	/**
	 * 获取需求商品详情单信息
	 * */
	List<DemandDetails> getDemandDetails(int demandId);
	
	/**
	 * 根据ID需求获取需求对应的点位
	 * */
	List<Nodes> getDemandNodes(int demandId);
	
	List<NodeVms> getVmsList(DemandDetails demandDetails);
	
	List<DemandTemplateDetails> getProductList(DemandDetails demandDetails);
	
	/**
	 * 根据需求详情获取指定商品总数
	 * */
	int getAmountNum(DemandDetails demandDetails);
	
	/**
	 * 需求详情编辑
	 * */
	void updateDemandDetails();
	
	//获取点位配送信息1
	List<DeliversNode> getDeliversNode();
	//获取点位配送信息2
	List<Delivers> getDelivers();
	//List<String>getNodeDelivers();
	
	/**
	 * 需求取消
	 * */
	void abrogateDemand(int id);
	
	/**
	 * 需求状态修改
	 * */
	void updateDemandStatus(Demands demands);
	
	/**
	 * 需求汇总
	 * */
	List<DemandTemplateDetails> checkList(int demandId);
}
