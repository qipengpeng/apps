/**
 * 
 */
package com.ugo.service;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.NodeVmsDao;
import com.ugo.dao.NodesDao;
import com.ugo.dao.ProductTempDao;
import com.ugo.dao.SalesDao;
import com.ugo.entity.NodeVms;
import com.ugo.entity.Nodes;
import com.ugo.entity.ProductTemplateTetails;
import com.ugo.entity.Sales;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *
 */
@Service
@Transactional
public class NodesService {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(NodesService.class);
	
	@Autowired
	private NodesDao nodesDao;	
	@Autowired
	private NodeVmsDao nodeVmsDao;
	@Autowired
	private ProductTempDao productTempDao;
	@Autowired
	private SalesDao salesDao;
	
	/**
	 * 点位列表
	 * */
	public List<Nodes> getList(){
		List<Nodes> list = nodesDao.getList();
		for(Nodes nodes:list) {
			int id = nodes.getId();
			List<NodeVms> nodeVmsList = nodeVmsDao.getNodeVmsList(id);
			nodes.setNodeVmsList(nodeVmsList);
		}
		return list;
	}
	
	/**
	 * 点位信息
	 * */
	public Nodes getNode(int id) {
		Nodes node = nodesDao.getNode(id);
		return node;
	}
	
	/**
	 * 插入点位信息
	 * */
	public void addNode(Nodes node) {
		//保存点位列表
		 logger.info("新增点位");
		 nodesDao.addNode(node);
		 logger.info("新增点位折扣");
		 nodesDao.addDiscount(node.getId());
		 List<NodeVms> nodeVmsList = node.getNodeVmsList();
			Sales Sales = new Sales();
			for(NodeVms nodeVms:nodeVmsList) {
				nodeVms.setNodeId(node.getId());
				nodeVms.setNodeName(node.getName());
				//保存点位设备详情
				nodeVmsDao.addNodeVms(nodeVms);
				Sales.setNodeId(nodeVms.getNodeId());
				Sales.setNodeName(nodeVms.getNodeName());
				Sales.setVendorId(nodeVms.getVendorId());
				Sales.setVendorName(nodeVms.getNodeVmName());
				int tempId = nodeVms.getTempId();
				List<ProductTemplateTetails> tetailsList = productTempDao.getProductTempTetailsList(tempId);
				for(ProductTemplateTetails productTemplateTetails:tetailsList) {
					Sales.setChannelId(productTemplateTetails.getChannelsId());
					Sales.setProductId(productTemplateTetails.getProductsId());
					Sales.setProductName(productTemplateTetails.getProductsName());
					Sales.setSalePrice(productTemplateTetails.getBrandPrice());
					Sales.setDefaultNum(productTemplateTetails.getNum());
					Sales.setChannelsType(productTemplateTetails.getChannelsType());
					Sales.setHeatUpTime(productTemplateTetails.getHeatUpTime());
					//建立实际点位库存
					salesDao.addSales(Sales);
				}
			}
	}
	
		/**
		 * 修改点位信息
		 * */
		public void updateNode(Nodes node) {
			Nodes node2 = new Nodes();
			node2.setId(node.getId());
			node2.setName(node.getName());
			nodesDao.updateNode(node2);
			List<NodeVms> nodeVmsList = node.getNodeVmsList();
			if(nodeVmsList.size()==0) {
				nodeVmsDao.deleteNodeVms(node.getId());
			}
			for(NodeVms nodeVms:nodeVmsList) {
				nodeVms.setNodeId(node.getId());
				nodeVms.setNodeName(node.getName());
				nodeVmsDao.updateNodeVms(nodeVms);
			}
		}
		
		/**
		 * 修改运营状态
		 * */
		public void updateNodeState(int id,int state) {
			nodesDao.updateNodeState(id,state);
		}
		
		/**
		 * 获取点位折扣
		 * */
		public int getDiscount(int nodeId) {
			int discount = 0;
			try {
				discount = nodesDao.getDiscount(nodeId);
			} catch (Exception e) {
				return discount;
			}
			return discount;
		}
		
		/**
		 * 新增点位折扣
		 * */
		public void addDiscount(int nodeId) {
			nodesDao.addDiscount(nodeId);
		}
		
		/**
		 * 修改折扣
		 * */
		public void updateDiscount(int nodeId,int percentage) {
			nodesDao.updateDiscount(nodeId, percentage);
		}
	
}
