/**
 * 
 */
package com.ugo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ugo.dao.DeliveriesDao;
import com.ugo.dao.DemandsDao;
import com.ugo.dao.ReplenishmentTasksDao;
import com.ugo.entity.Deliveries;
import com.ugo.entity.DeliveryDetails;
import com.ugo.entity.DemandTemplateDetails;
import com.ugo.entity.Demands;
import com.ugo.entity.NodeVms;
import com.ugo.entity.Nodes;
import com.ugo.entity.Principal;
import com.ugo.entity.ProductTemplateTetails;
import com.ugo.entity.ReplenishmentDetails;
import com.ugo.entity.ReplenishmentTasks;
import com.ugo.service.DeliveriesService;
import com.ugo.service.DemandsService;
import com.ugo.service.InventoryService;
import com.ugo.service.ReplenishmentTasksService;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *设备
 */
@Controller
@RequestMapping("/PC")
public class DeliveriesController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(DeliveriesController.class);
	
	@Autowired
	private DeliveriesService deliveriesService;
	
	@Autowired
	private DemandsDao demandsDao;
	
	@Autowired
	private DeliveriesDao deliveriesDao;
	
	@Autowired
	private DemandsService demandsService;
	
	@Autowired
	private ReplenishmentTasksDao replenishmentTasksDao;
	
	@Autowired
	private ReplenishmentTasksService replenishmentTasksService;
	
	@Autowired
	private InventoryService inventoryService;
	/**
	 * 获取所以设备信息
	 * */
	@RequestMapping("/getDeliveriesList")
	public String getDeliveriesList(Model model) {
		List<Deliveries> deliveriesList = deliveriesService.getDeliveriesList();
		model.addAttribute("deliveriesList",deliveriesList);
		return "/deliveries";
	}
	
	/**
	 * 交割详情
	 * */
	@RequestMapping("/getDeliveryDetails")
	public String getDeliveryDetails(int demandId,Model model) {
		try {
			Demands demands = demandsDao.getDemand(demandId);
			if(demands.getId() == 0) {
				model.addAttribute("msg", "订单不存在");
				return "/status";
			}
			int state = demands.getState();
			if(state == 3 ) {
				model.addAttribute("msg", "订单已取消");
				return "/status";
			}else if (state == 2) {
				model.addAttribute("msg", "订单已执行");
				return "/status";
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("msg", "订单不存在");
			return "/status";
		}
		
		
		Demands demand = demandsService.getDemand(demandId);
		
		model.addAttribute("demand",demand);
		return "/deliveriesAdd";
	}
	
	@RequestMapping("/QueryDeliveryDetailsList")
	public String getDeliveryDetailsList(Model model,int deliveryId) {
		Deliveries deliveries = deliveriesDao.getDeliveries(deliveryId);
		try {
			int deliveryTotal = deliveriesDao.getDeliveryTotal(deliveryId);
			deliveries.setTotal(deliveryTotal);
			List<DeliveryDetails> queryDeliveryDetailsList = deliveriesDao.QueryDeliveryDetailsList(deliveryId);
			for(DeliveryDetails deliveryDetails :queryDeliveryDetailsList) {
				int deliveryNum = deliveriesDao.getDeliveryNum(deliveryDetails);
				deliveryDetails.setDeliveryNum(deliveryNum);
			}
			deliveries.setDeliveryDetailsList(queryDeliveryDetailsList);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		model.addAttribute("deliveries",deliveries);
		return "/deliveriesDetails";
	}
	
	/**
	 * 添加交割详情
	 * */
	@ResponseBody
	@RequestMapping("/addDeliveries")
	public Map<String,Object> addDeliveries(@RequestBody Deliveries deliveries) {
		Map<String,Object> map = new HashMap<>();
		try {
			//添加交割信息
			Deliveries delivery = deliveriesService.addDeliveries(deliveries);
			//添加补货任务信息
			DeliveryDetails deliveryDetails = new DeliveryDetails();
			deliveryDetails.setDeliveryId(delivery.getId());
			logger.debug("添加交割信息.............");
			
			List<Principal> principalList = replenishmentTasksDao.queryPrincipalList();
			for(Principal principal:principalList) {
				int principalId = principal.getId();
				ReplenishmentDetails ReplenishmentDetails = new ReplenishmentDetails();
				ReplenishmentTasks replenishmentTasks = new ReplenishmentTasks();
				replenishmentTasks.setDemandId(delivery.getId());
				replenishmentTasks.setOperatorId(principalId);
				replenishmentTasks.setOperator(principal.getName());
				List<Nodes> nodeList = replenishmentTasksDao.queryNodesList(principalId);
				//principal.setNodeList(nodeList);
				for(Nodes nodes:nodeList) {
					int nodeId = nodes.getId();
					String nodeName = nodes.getName();
					deliveryDetails.setNodeId(nodeId);
					int count = replenishmentTasksDao.queryNodes(deliveryDetails);
					if(count>0) {
						int count2 = replenishmentTasksDao.queryOperator(replenishmentTasks);
						if(count2==0) {
							ReplenishmentTasks replenishmentTasks2 = replenishmentTasksDao.queryReplenishmentTasks(delivery.getId());
							replenishmentTasks.setDemandId(replenishmentTasks2.getDemandId());
							replenishmentTasks.setDemandDate(replenishmentTasks2.getDemandDate());
							//生成补货任务
							replenishmentTasksService.addReplenishmentTasks(replenishmentTasks);
							//生成清货任务
							inventoryService.addInventory(replenishmentTasks);
						}
						ReplenishmentDetails.setReplenishmentTaskId(replenishmentTasks.getId());
						ReplenishmentDetails.setNodeId(nodeId);
						ReplenishmentDetails.setNodeName(nodeName);
						List<NodeVms> nodeVmsList = replenishmentTasksDao.queryNodeVmsList(deliveryDetails);
						for(NodeVms nodeVms :nodeVmsList) {
							String nodeVmName = nodeVms.getNodeVmName();
							int vendorId = nodeVms.getVendorId();
							ReplenishmentDetails.setNodeVmSeqName(nodeVmName);
							ReplenishmentDetails.setVendorId(vendorId);
							deliveryDetails.setVendorId(vendorId);
							List<DemandTemplateDetails> qemandTemplateDetails = replenishmentTasksDao.queryDemandTemplateDetails(deliveryDetails);
							for(DemandTemplateDetails demandTemplateDetails :qemandTemplateDetails) {
								int productId = demandTemplateDetails.getProductId();
								int amount = demandTemplateDetails.getNum();
								deliveryDetails.setProductId(productId);
								List<ProductTemplateTetails> productTemplateTetails = replenishmentTasksDao.queryProductTemplateTetails(deliveryDetails);
								for(ProductTemplateTetails productTemplateTetail:productTemplateTetails) {
									ReplenishmentDetails.setChannelId(productTemplateTetail.getChannelsId());
									ReplenishmentDetails.setProductId(productTemplateTetail.getProductsId());
									ReplenishmentDetails.setProductName(productTemplateTetail.getProductsName());
									ReplenishmentDetails.setHeatUpTime(productTemplateTetail.getHeatUpTime());
									int num = productTemplateTetail.getNum();
									if(amount>num) {
										amount -= num;
										ReplenishmentDetails.setNum(num);
									}else {
										ReplenishmentDetails.setNum(amount);
									}
									//生成补货详情
									replenishmentTasksService.addReplenishmentDetails(ReplenishmentDetails);
								}
							}
						}
					}
					
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "交割数据异常");
			return map;
		}
		map.put("code", 0);
		map.put("msg", "SUCCESS");
		return map;
	}
	
	
}
