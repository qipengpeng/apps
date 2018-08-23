/**
 * 
 */
package com.ugo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.DemandsDao;
import com.ugo.dao.ProductTempDao;
import com.ugo.entity.DemandDetails;
import com.ugo.entity.DemandTemplateDetails;
import com.ugo.entity.Demands;
import com.ugo.entity.NodeVms;
import com.ugo.entity.Nodes;
import com.ugo.entity.ProductTemplateTetails;

/**
 * @author sunshangfeng
 *
 */
@Service
public class DemandsService {
	@Autowired
	private DemandsDao demandsDao;
	@Autowired
	private NodeVmsService nodeVmsService;

	
	@Autowired
	private ProductTempDao productTempDao;
	@Autowired
	private DemandsService demandsService;
	
	/**
	 * 获取需求列表
	 * */
	public List<Demands> getDemandsList(){
		List<Demands> demandsList = demandsDao.getDemandsList();
		return demandsList;
	}
	
	/**
	 * 获取需求商品详情单信息
	 * */
	public List<DemandDetails> getDemandDetails(int demandId){
		List<DemandDetails> demandDetails = demandsDao.getDemandDetails(demandId);
		return demandDetails;
	}
	
	/**
	 * 根据点位详情获取需求信息
	 * */
	public Demands getDemands(Demands demands,int[]nodesId) {
		List<Nodes> nodesList = new ArrayList<Nodes>();
		//生成需求数据
		for(int i=0;i<nodesId.length;i++) {
			Nodes nodes = new Nodes();
			int nodeId = nodesId[i];
			nodes.setId(nodeId);
			//点位详情
			List<NodeVms> nodeVmsList = nodeVmsService.getNodeVmsList(nodeId);
			for(NodeVms nodeVms: nodeVmsList) {
			
				int tempId = nodeVms.getTempId();
					//商品模板详情
				List<DemandTemplateDetails>demandTemplateDetailsList = new ArrayList<DemandTemplateDetails>();
					List<ProductTemplateTetails> tetailsIdList = productTempDao.getTetailsIdList(tempId);
					for(ProductTemplateTetails productTemplateTetails:tetailsIdList) {
						if(productTemplateTetails.getProductsId()!=0) {
							DemandTemplateDetails demandTemplateDetails = new DemandTemplateDetails();
							demandTemplateDetails.setProductId(productTemplateTetails.getProductsId());
							demandTemplateDetails.setProductName(productTemplateTetails.getProductsName());
							demandTemplateDetails.setNum(productTempDao.demandsAmount(productTemplateTetails));
							demandTemplateDetailsList.add(demandTemplateDetails);
						}
					}
					nodeVms.setDemandTemplateDetailsList(demandTemplateDetailsList);
			}
			nodes.setNodeVmsList(nodeVmsList);
			nodesList.add(nodes);
		}
		demands.setNodesList(nodesList);
		return demands;
		}
	
	/**
	 * 添加需求信息
	 * */
	@Transactional
	public void addDemands(Demands demands) {
		if(demands == null 
				|| demands.getNodesList() == null
				|| demands.getNodesList().size()<=0) {
			return;
		}
		demandsDao.addDemands(demands);
		
		List<Nodes> nodesList = demands.getNodesList();
				
		//生成需求数据
		DemandDetails demandDetails = new DemandDetails();
		demandDetails.setDemandId(demands.getId());
		for(int i=0;i<nodesList.size();i++) {
			Nodes nodes = nodesList.get(i);
			int nodeId = nodes.getId();
			demandDetails.setNodeId(nodeId);
			//点位详情
			List<NodeVms> nodeVmsList = nodes.getNodeVmsList();
			for(int j=0;j<nodeVmsList.size();j++) {
				NodeVms nodeVms = nodeVmsList.get(j);
				int vendorId = nodeVms.getVendorId();
				int seq = nodeVms.getSeq();
				String nodeVmName = nodeVms.getNodeVmName();
				demandDetails.setNodeVmSeq(seq);
				demandDetails.setNodeVmSeqName(nodeVmName);
				demandDetails.setVendorId(vendorId);
					//商品模板详情
					List<DemandTemplateDetails> tetailsIdList = nodeVms.getDemandTemplateDetailsList();
					for(int k=0;k<tetailsIdList.size();k++) {
						DemandTemplateDetails tetails = tetailsIdList.get(k);
						int productsId = tetails.getProductId();
						String productsName = tetails.getProductName();
						int demandsAmount = tetails.getNum();
						demandDetails.setProductId(productsId);
						demandDetails.setProductName(productsName);
						demandDetails.setNum(demandsAmount);
						demandsService.addDemandDetails(demandDetails);
					}
							
			}
		}
	}
	//添加需求详情
	@Transactional
	public void addDemandDetails(DemandDetails demandDetails) {
		 demandsDao.addDemandDetails(demandDetails);
	}
	
	/**
	 * ---根据当前需求详情获取需求信息----------
	 * */
	public Demands getDemand(int demandId) {
		Demands demands = demandsDao.getDemand(demandId);
		demands.setId(demandId);
		
		DemandDetails demandDetails = new DemandDetails();
		demandDetails.setDemandId(demandId);
		
		List<Nodes> demandNodes = demandsDao.getDemandNodes(demandId);
		for(Nodes nodes : demandNodes) {
			int nodeId = nodes.getId();
			demandDetails.setNodeId(nodeId);
			List<NodeVms> vmsList = demandsDao.getVmsList(demandDetails);
			for(NodeVms nodeVms : vmsList) {
				int vendorId = nodeVms.getVendorId();
				String nodeVmName = nodeVms.getNodeVmName();
				demandDetails.setVendorId(vendorId);
				demandDetails.setNodeVmSeqName(nodeVmName);
				List<DemandTemplateDetails> productList = demandsDao.getProductList(demandDetails);
				for(DemandTemplateDetails demandTemplateDetails : productList) {
					int productId = demandTemplateDetails.getProductId();
					String productName = demandTemplateDetails.getProductName();
					demandDetails.setProductId(productId);
					demandDetails.setProductName(productName);
					int amountNum = demandsDao.getAmountNum(demandDetails);
					demandTemplateDetails.setNum(amountNum);
					demandDetails.setNum(amountNum);
				}
				nodeVms.setDemandTemplateDetailsList(productList);
			}
			nodes.setNodeVmsList(vmsList);
		}
		demands.setNodesList(demandNodes);						
		return demands;
	}
	
	/**
	 * 需求取消
	 * */
	@Transactional
	public void abrogateDemand(int id) {
		demandsDao.abrogateDemand(id);
	}
	
	/**
	 * 修改需求状态
	 * */
	@Transactional
	public void updateDemandStatus(Demands demands) {
		demandsDao.updateDemandStatus(demands);
	}
	
	/**
	 * 需求汇总
	 * */
	public Map<String,Object> checkList(int demandId){
		List<DemandTemplateDetails> checkList = demandsDao.checkList(demandId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", checkList);
		return map;
	}
}
