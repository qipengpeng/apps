/**
 * 
 */
package com.ugo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.NodeDeliversDao;
import com.ugo.entity.NodeDelivers;
import com.ugo.entity.Nodes;
import com.ugo.entity.RouteDetails;
import com.ugo.entity.UsersAdmin;

/**
 * @author sunshangfeng
 *
 */
@Service
@Transactional
public class NodeDeliversService {
	
		@Autowired
		private NodeDeliversDao nodeDeliversDao;
		
		/**
		 * 配送点位列表
		 * */
		public List<RouteDetails> getRouteDetailsList(){
			List<RouteDetails> routeDetailsList = nodeDeliversDao.getRouteDetailsList();
			return routeDetailsList;
		}
		
		/**
		 * 查询线路未包含
		 * */
		public List<Nodes>getNodesId(){
			List<Nodes> nodesIdList = nodeDeliversDao.getNodesId();
			return nodesIdList;
		}
		
		/**
		 * 获取未分配人员信息
		 * */
		public List<UsersAdmin> queryUserList(){
			List<UsersAdmin> userList = nodeDeliversDao.queryUserList();
			
			return userList;
		}
		
		/**
		 * 查询线路包含
		 * */
		public String[] getNodeDeliversId(int routeId){
			String[] nodeDeliversId = nodeDeliversDao.getNodeDeliversId(routeId);
			return nodeDeliversId;
		}
		
		/**
		 * 获取单条线路信息
		 * */
		public RouteDetails getRouteDetails(int routeId) {
			RouteDetails routeDetails = nodeDeliversDao.getRouteDetails(routeId);
			return routeDetails;
		}
		
		/**
		 * 增添配送点位信息
		 * */
		public void addRouteDetails(RouteDetails routeDetails){
			
			if(routeDetails == null 
					|| routeDetails.getNodeIdList() == null
					|| routeDetails.getNodeIdList().size()<=0) {
				return;
			}
			nodeDeliversDao.addRouteDetails(routeDetails);
			if(routeDetails.getName() != null) {
				List<NodeDelivers> nodeIdList = routeDetails.getNodeIdList();
				for(NodeDelivers delivers: nodeIdList) {
				nodeDeliversDao.addNodeDelivers(new NodeDelivers(routeDetails.getId(),delivers.getNodeId(),delivers.getNodeName(),routeDetails.getPrincipalId(),routeDetails.getPrincipal()));
				}
			}
		}
		/**
		 * 增添线路点位关联
		 * */
		public void addNodeDelivers(NodeDelivers nodeDelivers){
			nodeDeliversDao.addNodeDelivers(nodeDelivers);
		}
		
		/**
		 * 增添线路点位关联
		 * */
		public void addNodeDelivers(List<NodeDelivers> nodeDeliversList){
			nodeDeliversDao.addNodeDeliversList(nodeDeliversList);
		}
		
		/**
		 * 删除线路点位关联
		 * */
		public void deleteNodeDelivers(int routeId){
			nodeDeliversDao.deleteNodeDelivers(routeId);
		}
		
		/**
		 * 修改配送点位
		 * */
		public void updateRouteDetails(RouteDetails routeDetails) {
			nodeDeliversDao.updateRouteDetails(routeDetails);
		}
}
