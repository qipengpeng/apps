/**
 * 
 */
package com.ugo.web;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ugo.dao.NodeDeliversDao;
import com.ugo.entity.Nodes;
import com.ugo.entity.RouteDetails;
import com.ugo.entity.UsersAdmin;
import com.ugo.service.NodeDeliversService;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *
 */
@Controller
@RequestMapping("/PC")
public class NodeDeliversController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(NodeDeliversController.class);
	
	@Autowired
	private NodeDeliversService nodeDeliversService;
	
	@Autowired
	private NodeDeliversDao nodeDeliversDao;
	
	
	@RequestMapping("/getRouteDetailsList")
	public ModelAndView getRouteDetailsList() {
		List<RouteDetails> detailsList = nodeDeliversService.getRouteDetailsList();
		for(int i=0;i<detailsList.size();i++) {
			RouteDetails routeDetails = detailsList.get(i);
			int id = routeDetails.getId();
			String[] nodeDeliversId = nodeDeliversService.getNodeDeliversId(id);
			 List<String> nodeDeliversName = nodeDeliversDao.getNodeDeliversName(id);
			System.out.println(nodeDeliversId);
			System.out.println(nodeDeliversName);
			String includeSite = String.join(",", nodeDeliversId);
			String nodeDeliversName1 = String.join(",", nodeDeliversName);
			routeDetails.setIncludeSite(includeSite);
			routeDetails.setPrincipal(nodeDeliversName1);
		}
		System.out.println(detailsList.toString());
		ModelAndView model = new ModelAndView("nodeDelivers");
		model.addObject("detailsList", detailsList);
		return model;
	}
	
	@ResponseBody
	@RequestMapping("/getNodesId")
	public List<Nodes> getNodesId() {
		List<Nodes> nodesId = nodeDeliversDao.getNodesId();
		System.out.println(nodesId.toString());
		return nodesId;
	}
	
	@ResponseBody
	@RequestMapping("/addRouteDetails")
	public boolean addRouteDetails(@RequestBody RouteDetails route) {
		try {
			nodeDeliversService.addRouteDetails(route);
			logger.debug("新增线路......");
			return true;
		}catch (Exception e) {
			logger.debug("线路添加异常......");
		}
		return false;
	}
	
	/**
	 * 获取未分配点位人员信息
	 * */
	@ResponseBody
	@RequestMapping("/getUser")
	public List<UsersAdmin> getUser() {
		List<UsersAdmin> userList = nodeDeliversService.queryUserList();
		System.out.println(userList.toString());
		return userList;
	}
}
