/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import com.ugo.entity.NodeVms;

/**
 * @author sunshangfeng
 *点位对应机器Dao
 */
public interface NodeVmsDao {
	/**
	 * 添加机器
	 * */
	void addNodeVms(NodeVms nodeVms);
	
	/**
	 * 根据点位ID获取点位机器信息
	 * */
	List<NodeVms>getNodeVmsList(int nodeID);
	
	/**
	 * 修改点位设备信息
	 * @author qipeng 2018/8/14
	 * @param nodeVms
	 * */
	void updateNodeVms(NodeVms nodeVms);
	
	/**
	 * 获取点位包含的设备
	 * */
	List<NodeVms>getVendors(int nodeId);
	
	/**
	 * 修改点位设备信息
	 * @author qipeng 2018/8/15
	 * @param 
	 * */
	void deleteNodeVms(int nodeId);
}
