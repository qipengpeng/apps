/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ugo.entity.Nodes;

/**
 * @author sunshangfeng
 *点位信息
 */
public interface NodesDao {
	
	/**
	 * 获取点位信息列表
	 * */
	List<Nodes> getList();
	
	/**
	 * 获取点位信息
	 * */
	Nodes getNode(int id);
	
	/**
	 * 新增点位信息
	 * */
	int addNode(Nodes node);
	
	/**
	 * 修改点位信息
	 * */
	void updateNode(Nodes node);
	
	/**
	 * 修改运营状态
	 * */
	void updateNodeState(@Param("id")int id,@Param("state")int state);
	
	/**
	 * 获取点位折扣
	 * */
	int getDiscount(int nodeId);
	
	/**
	 * 新增点位折扣
	 * */
	void addDiscount(int nodeId);
	
	/**
	 * 修改折扣
	 * */
	void updateDiscount(@Param("nodeId")int nodeId,@Param("percentage")int percentage);
}
