/**
 * 
 */
package com.ugo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.DeliveriesDao;
import com.ugo.dao.ProductsDao;
import com.ugo.entity.Deliveries;
import com.ugo.entity.DeliveryDetails;
import com.ugo.entity.DemandTemplateDetails;
import com.ugo.entity.Demands;
import com.ugo.entity.NodeVms;
import com.ugo.entity.Nodes;
import com.ugo.entity.Products;

/**
 * @author sunshangfeng
 *
 */
@Service
@Transactional
public class DeliveriesService {
	@Autowired
	private DeliveriesDao deliveriesDao;

	
	@Autowired
	private ProductsDao productsDao;
	@Autowired
	private DemandsService demandsService;
	
	
	public List<Deliveries> getDeliveriesList(){
		List<Deliveries> deliveriesList = deliveriesDao.getDeliveriesList();
		return deliveriesList;
	}
	
	/**
	 * 添加交割信息
	 * */
	public	Deliveries addDeliveries(Deliveries deliveries) {
		if(deliveries == null 
				|| deliveries.getNodesList() == null
				|| deliveries.getNodesList().size()<=0) {
			return deliveries;
		}
		deliveries.setOperator("test");
		deliveriesDao.addDeliveries(deliveries);
		Demands demands = new Demands();
		demands.setId(deliveries.getDemandId());
		demands.setState(2);
		//修改需求状态
		demandsService.updateDemandStatus(demands);
		List<Nodes> nodesList = deliveries.getNodesList();
		//生成交割数据
		DeliveryDetails deliveryDetails = new DeliveryDetails();
		deliveryDetails.setDeliveryId(deliveries.getId());
		for(int i=0;i<nodesList.size();i++) {
			Nodes nodes = nodesList.get(i);
			int nodeId = nodes.getId();
			deliveryDetails.setNodeId(nodeId);
			//点位详情
			List<NodeVms> nodeVmsList = nodes.getNodeVmsList();
			for(int j=0;j<nodeVmsList.size();j++) {
				NodeVms nodeVms = nodeVmsList.get(j);
				int vendorId = nodeVms.getVendorId();
				String nodeVmName = nodeVms.getNodeVmName();
				deliveryDetails.setNodeVmSeqName(nodeVmName);
				deliveryDetails.setVendorId(vendorId);
					//商品模板详情
				List<DemandTemplateDetails> tetailsIdList = nodeVms.getDemandTemplateDetailsList();
					for(int k=0;k<tetailsIdList.size();k++) {
						DemandTemplateDetails tetails = tetailsIdList.get(k);
						int productsId = tetails.getProductId();
						String productsName = tetails.getProductName();
						int demandsAmount = tetails.getNum();
						deliveryDetails.setProductId(productsId);
						deliveryDetails.setProductName(productsName);
						//获取商品价格
						Products product = productsDao.getProduct(productsId);
						deliveryDetails.setUnitPrice(product.getPurchasePrice());
						//deliveryDetails.setProduceDate(produceDate);
						deliveryDetails.setDeliveryNum(demandsAmount);
						//添加交割详情
						deliveriesDao.addDeliveryDetails(deliveryDetails);
					}
			}
		}
		return deliveries;
		
	}
	
	/**
	 * 添加交割详情
	 * */
	public void addDeliveryDetails(DeliveryDetails deliveryDetails) {
		deliveriesDao.addDeliveryDetails(deliveryDetails);
	}
}
