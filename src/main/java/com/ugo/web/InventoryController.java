/**
 * 
 */
package com.ugo.web;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ugo.dao.InventoryDao;
import com.ugo.entity.DemandTemplateDetails;
import com.ugo.entity.Inventory;
import com.ugo.entity.InventoryDetails;
import com.ugo.entity.NodeVms;
import com.ugo.entity.Nodes;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *
 */
@Controller
@RequestMapping("/PC")
public class InventoryController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(MaintenanceAPI.class); 
	
	@Autowired
	private InventoryDao inventoryDao;
	
	/**
	 * 获取清货列表
	 * */
	@RequestMapping("/getInventoryList")
	public ModelAndView getInventoryList() {
		ModelAndView model = new ModelAndView("/inventory");
		List<Inventory> queryInventoryList = inventoryDao.queryInventoryList();
		model.addObject("inventory", queryInventoryList);
		logger.debug("获取清货信息.........");
		return model;
	}
	
	/**
	 * 获取清货详情
	 * */
	@RequestMapping("/getInventoryDetails")
	public ModelAndView getInventoryDetails(int id) {
		ModelAndView model = new ModelAndView("/inventoryDetails");
		InventoryDetails inventoryDetails = new InventoryDetails();
		inventoryDetails.setInventoryId(id);
		Inventory inventory = inventoryDao.getInventory(id);
		List<Nodes> nodesList = inventoryDao.getNodes(id);
		for(Nodes nodes:nodesList) {
			inventoryDetails.setNodeId(nodes.getId());
			List<NodeVms> nodeVmsList = inventoryDao.getNodeVms(inventoryDetails);
			for(NodeVms nodeVms:nodeVmsList) {
				inventoryDetails.setVendorId(nodeVms.getVendorId());
				List<DemandTemplateDetails> demandTemplateDetails = inventoryDao.getDemandTemplateDetails(inventoryDetails);
				System.out.println("清货商品详情--------------"+demandTemplateDetails.toString());
				nodeVms.setDemandTemplateDetailsList(demandTemplateDetails);
			}
			nodes.setNodeVmsList(nodeVmsList);
		}
		inventory.setNodesList(nodesList);
		model.addObject("inventory", inventory);
		return model;
	}
}
