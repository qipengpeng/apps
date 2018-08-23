/**
 * 
 */
package com.ugo.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ugo.entity.NodeVms;
import com.ugo.entity.Nodes;
import com.ugo.entity.Vendors;
import com.ugo.service.NodeVmsService;
import com.ugo.service.NodesService;
import com.ugo.service.VendorsService;



/**
 * @author sunshangfeng
 *
 */
@RequestMapping("/PC")
@Controller
public class NodesController {
	
		private static final Logger logger = (Logger) LoggerFactory.getLogger(NodesController.class);
		@Autowired
		private NodesService nodesService;
		
		@Autowired
		private NodeVmsService nodeVmsService;
		
		@Autowired
		private VendorsService vendorsService;
		
		/**
		 * 获取点位列表
		 * */
		@RequestMapping("/getNodesList")
		public String getListNodes(Model model) {
			List<Nodes> nodesList = nodesService.getList();
			model.addAttribute("nodesList",nodesList);
			return "/nodes";
		}
		
		/**
		 * 获取点位详情
		 * */
		@RequestMapping("/getNodes")
		public ModelAndView getNodes(int id) {
			Nodes nodes = nodesService.getNode(id);
			int discount = nodesService.getDiscount(id);
			nodes.setPercentage(discount);
			List<NodeVms> nodeVmslist = nodeVmsService.getNodeVmsList(id);
			for(NodeVms nodeVms :nodeVmslist) {
				int vendorId = nodeVms.getVendorId();
				Vendors vendors = vendorsService.getVendors(vendorId);
				nodeVms.setVendorPassword(vendors.getPassword());
				nodeVms.setVendorTypeId(vendors.getType());
			}
			nodes.setNodeVmsList(nodeVmslist);
			ModelAndView model = new ModelAndView("nodesUpdate"); 
			model.addObject("nodes", nodes);
			return model;
		}
		
		/**
		 * 添加点位信息及详情
		 * */
		@RequestMapping("/addNodeVms")
		@ResponseBody
		public String addNodeVms(@RequestBody Nodes node) {
			try {
				System.out.println(node.toString());
				nodesService.addNode(node);
				logger.debug("点位新增.......");
				return "true";
			} catch (Exception e) {
				// TODO: handle exception
			}
			return "false";
		}
		
		/**
		 * 修改点位信息
		 * */
		@ResponseBody
		@RequestMapping("/updateNodeVms")
		public String updateNodeVms(@RequestBody Nodes node) {
			logger.info("修改点位信息");
			nodesService.updateNode(node);
			return "SUCCESS";
		}
		
		/**
		 * 点位状态开关
		 * */
		@ResponseBody
		@RequestMapping("/updateNodeState")
		public String setNodeState(int id,int state) {
			logger.info("设置点位设备状态!");
			nodesService.updateNodeState(id,state);
			if(state == 1) {
				logger.info("点位开启状态");
			}else {
				logger.info("点位关闭状态");
			}
			return "SUCCESS";
		}
		
		/**
		 * 修改点位折扣
		 * */
		@ResponseBody
		@RequestMapping("/updateDiscount")
		public String updateDiscount(int id,int percentage) {
			nodesService.updateDiscount(id, percentage);
			return "SUCCESS";
		}
}
