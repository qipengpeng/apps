/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ugo.entity.NodeDelivers;
import com.ugo.entity.Nodes;
import com.ugo.entity.RouteDetails;
import com.ugo.entity.UsersAdmin;

/**
 * @author sunshangfeng
 *点位配送人员线路
 */
public interface NodeDeliversDao {
	
	/**
	 * 配送点位列表
	 * */
	List<RouteDetails> getRouteDetailsList();
	
	/**
	 * 查询线路未包含
	 * */
	List<Nodes>getNodesId();
	
	/**
	 * 获取未分配人员信息
	 * */
	List<UsersAdmin> queryUserList();
	
	/**
	 * 查询线路包含
	 * */
	String[] getNodeDeliversId(int routeId);
	List<String> getNodeDeliversName(int routeId);
	
	/**
	 * 获取单条线路信息
	 * */
	RouteDetails getRouteDetails(int routeId);
	
	/**
	 * 增添配送点位信息
	 * */
	int addRouteDetails(RouteDetails routeDetails);
	/**
	 * 增添线路点位关联
	 * */
	void addNodeDelivers(NodeDelivers nodeDelivers);
	
	/**
	 * 批量增添线路点位关联
	 **/
	void addNodeDeliversList(@Param("nodeDeliversList") List<NodeDelivers> nodeDeliversList);
	/**
	 * 删除线路点位关联
	 * */
	void deleteNodeDelivers(int routeId);
	
	/**
	 * 修改配送点位
	 * */
	void updateRouteDetails(RouteDetails routeDetails);
}
