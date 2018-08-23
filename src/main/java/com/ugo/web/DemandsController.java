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
import org.springframework.web.servlet.ModelAndView;

import com.ugo.dao.DemandsDao;
import com.ugo.entity.Delivers;
import com.ugo.entity.DeliversNode;
import com.ugo.entity.DemandDetails;
import com.ugo.entity.DemandTemplateDetails;
import com.ugo.entity.Demands;
import com.ugo.entity.NodeVms;
import com.ugo.entity.Nodes;
import com.ugo.service.DemandsService;

import ch.qos.logback.classic.Logger;


/**
 * @author sunshangfeng
 *设备
 */
@Controller
@RequestMapping("/PC")
public class DemandsController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(DemandsController.class); 
	
	@Autowired
	private DemandsService demandsService;
	@Autowired
	private DemandsDao demandsDao;

	/**
	 * 获取需求列表
	 * */
	@RequestMapping("/getDemandsList")
	public String getDemandsList(Model model) {
		List<Demands> demandsList = demandsService.getDemandsList();
		model.addAttribute("demandsList",demandsList);
		return "/demands";
	}
	
	/**
	 * 添加需求信息
	 * */
	@ResponseBody
	@RequestMapping("/addDemands")
	public void addDemands(@RequestBody Demands demands) {
			
		try {
			demandsService.addDemands(demands);
			logger.debug("添加需求信息............");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//添加需求信息
		@ResponseBody
		@RequestMapping("/addDemandsList")
		public void addDemandsList(@RequestBody Demands demands) {
			if(demands == null 
					|| demands.getNodesList() == null
					|| demands.getNodesList().size()<=0) {
				return;
			}
			demandsService.addDemands(demands);
			DemandDetails demandDetails =new DemandDetails();
			demandDetails.setDemandId(demands.getId());
			//DemandDetails.setNodeId(nodeId);
			
			List<Nodes> nodesList = demands.getNodesList();
			for(Nodes nodes:nodesList) {
				int id = nodes.getId();
				demandDetails.setNodeId(id);
				List<NodeVms> nodeVmsList = nodes.getNodeVmsList();
				for(NodeVms nodeVms:nodeVmsList) {
					String nodeVmName = nodeVms.getNodeVmName();
					int vendorId = nodeVms.getId();
					demandDetails.setNodeVmSeqName(nodeVmName);
					demandDetails.setVendorId(vendorId);
					List<DemandTemplateDetails> demandTemplateDetailsList = nodeVms.getDemandTemplateDetailsList();
					for(DemandTemplateDetails demandTemplateDetails:demandTemplateDetailsList) {
						int productId = demandTemplateDetails.getProductId();
						String productName = demandTemplateDetails.getProductName();
						int num = demandTemplateDetails.getNum();
						demandDetails.setProductId(productId);
						demandDetails.setProductName(productName);
						demandDetails.setNum(num);
						demandsService.addDemandDetails(demandDetails);
					}
				}
			}
		}
	
	/**
	 * 需求详情
	 * */
	 @RequestMapping("/getDemandDetailsList")
	public ModelAndView getDemandDetailsList(int id) {
		ModelAndView model = new ModelAndView("demandsDetails");
		Demands demand = demandsService.getDemand(id);
		model.addObject("demand", demand);
		return model;
	}
	 
	/**
	 * 首先获取需求详情
	 * */
    @RequestMapping("/getDemands")
	public ModelAndView getDemands(Demands demands,int[] nodesId){
		 ModelAndView model = new ModelAndView("demandsAdd");
		 Demands demand = demandsService.getDemands(demands,nodesId);
		//logger.info(demand.toString());
		model.addObject("demand", demand);
		return model;
	}
	 
	
	//获取点位信息和配送线路
	 @ResponseBody
	 @RequestMapping("/getNodeDelivers")
	public Map<String,Object> getNodeDelivers() {
		 List<DeliversNode> deliversNode = demandsDao.getDeliversNode();
		 List<Delivers> delivers = demandsDao.getDelivers();
		 Map<String,Object> nodeDeliversMap =new HashMap<String,Object>();
		 nodeDeliversMap.put("node", deliversNode);
		 nodeDeliversMap.put("line", delivers);
		return nodeDeliversMap;
	}
	 
	/**
	 * 需求取消
	 * */
	@RequestMapping("/updateDemandStatus")
	public String updateDemandStatus(int id) {
		 demandsService.abrogateDemand(id);
		return "redirect:getDemandsList";
	}
	
	/**
	 * 获取详情汇总
	 * */
	@ResponseBody
	@RequestMapping("/checkList")
	public Map<String, Object> checkList(int demandId) {
		Map<String, Object> checkList = demandsService.checkList(demandId);
		return checkList;
	}
}
