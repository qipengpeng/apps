/**
 * 
 */
package com.ugo.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ugo.bus.MsgBus;
import com.ugo.dao.ReplenishmentDetailsDao;
import com.ugo.dao.ReplenishmentTasksDao;
import com.ugo.dao.UsersAdminDao;
import com.ugo.dto.Constant;
import com.ugo.dto.DeliveryOrder;
import com.ugo.dto.ReplenishNode;
import com.ugo.entity.Login;
import com.ugo.entity.NodeVms;
import com.ugo.entity.Nodes;
import com.ugo.entity.ReplenishmentDetails;
import com.ugo.entity.ReplenishmentTasks;
import com.ugo.entity.Sales;
import com.ugo.entity.UsersAdmin;
import com.ugo.service.ReplenishmentTasksService;
import com.ugo.service.SalesService;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *
 */
@RequestMapping("/api/")
@Controller
public class MaintenanceAPI {
		
	private static final Logger logger = (Logger) LoggerFactory.getLogger(MaintenanceAPI.class); 
	@Autowired
	private ReplenishmentTasksDao replenishmentTasksDao;
	
	@Autowired
	private ReplenishmentTasksService replenishmentTasksService;
	
	@Autowired
	private ReplenishmentDetailsDao replenishmentDetailsDao;
	
	@Autowired
	private SalesService salesService; 
	
	@Autowired
	private UsersAdminDao usersAdminDao;
	
	private ReplenishmentTasks replenishmentTasks = new ReplenishmentTasks();
	
	/**
	 * 登录
	 * */
	@RequestMapping("login")
	@ResponseBody
	public Login login(String phone,String pwd) {
		System.out.println(phone+"------"+pwd+"--------------");
		logger.debug("运维登录人员...............");
		UsersAdmin user = new UsersAdmin();
		user.setPhone(phone);
		user.setPassword(pwd);
		Login login = new Login();
		Map<String,Object> map = new HashMap<String,Object>();
		int userCount = usersAdminDao.getUserCount(user);
		if(userCount == 0) {
			return null;
		}
		int token = usersAdminDao.getId(user);
		map.put("token",token);
		login.setData(map);
		return login;
	}
	
	
	/**
	 * 提货单
	 * */
	@ResponseBody
	@RequestMapping("deliveryOrder/list")
	public DeliveryOrder getDeliveryOrder(int token) {
		DeliveryOrder deliveryOrder = new DeliveryOrder();
		
		replenishmentTasks = replenishmentTasksDao.getReplenishmentTasks(token);
		if(replenishmentTasks == null) {
			deliveryOrder.setDemand_code(1);
			System.out.println(deliveryOrder.toString());
			return deliveryOrder;
		}
		deliveryOrder.setDemand_code(replenishmentTasks.getDemandId());
		deliveryOrder.setDemand_date(replenishmentTasks.getDemandDate());
		
		List<ReplenishmentDetails> replenishmentDetailsList = replenishmentTasksDao.getReplenishmentDetailsList(replenishmentTasks);
		List<Map<String,Object>> productsMap= new ArrayList<Map<String,Object>>();
		for(ReplenishmentDetails replenishmentDetails:replenishmentDetailsList) {
			Map<String,Object> map = new HashMap<String,Object>();
			int productId = replenishmentDetails.getProductId();
			String productName = replenishmentDetails.getProductName();
			int num = replenishmentDetails.getNum();
			map.put("p_id", productId);
			map.put("p_name", productName);
			map.put("p_num", num);
			productsMap.add(map);
		}
		deliveryOrder.setProducts(productsMap);
		System.out.println(deliveryOrder.toString());
		return deliveryOrder;
	}
	
	/**
	 * 补货点位
	 * */
	@ResponseBody
	@RequestMapping("replenish/node")
	public ReplenishNode getReplenishNodeList() {
		try {
			int task_id = replenishmentTasks.getId();
			
			ReplenishNode replenishNode = new ReplenishNode();
			replenishNode.setTask_id(task_id+"");
			List<Map<String, Object>> nodesMap = new ArrayList<Map<String, Object>>();
			List<Nodes> nodesList = replenishmentDetailsDao.queryNodesList(task_id);
			if(nodesList == null || nodesList.size()<0) {
				return null;
			}
			for(Nodes nodes:nodesList) {
				Map<String,Object> nodeMap = new HashMap<String, Object>();
				int id = nodes.getId();
				String name = nodes.getName();
				nodeMap.put("n_id", id);
				nodeMap.put("n_name", name);
				nodesMap.add(nodeMap);
			}
			replenishNode.setData(nodesMap);
			return replenishNode;
			
		} catch (Exception e) {
			// TODO: handle exception
			ReplenishNode replenishNode = new ReplenishNode();
			return replenishNode;
		}
	}
	
	/**
	 * 货道
	 * */
	@ResponseBody
	@RequestMapping("replenish/device")
	public Constant getVendorsList(int task_id, int node_id) {
		System.out.println(task_id+"-------------");
		int task_id1 = replenishmentTasks.getId();
		
		ReplenishmentDetails replenishmentDetails = new ReplenishmentDetails();
		replenishmentDetails.setReplenishmentTaskId(task_id1);
		replenishmentDetails.setNodeId(node_id);
		
		Constant replenishDevice = new Constant();
		
		List<Map<String, Object>> vendorsMap = new ArrayList<Map<String, Object>>();
		List<NodeVms> vmsList = replenishmentDetailsDao.queryNodeVmsList(replenishmentDetails);
		for(NodeVms nodeVms:vmsList) {
			Map<String,Object> vendorMap = new HashMap<String, Object>();
			int id = nodeVms.getVendorId();
			String name = nodeVms.getNodeVmName();
			int channelType = replenishmentDetailsDao.getChannelType(id);
			vendorMap.put("d_name", name);
			vendorMap.put("d_id", id);
			vendorMap.put("d_type", channelType+"");
			vendorsMap.add(vendorMap);
		}
		replenishDevice.setData(vendorsMap);
		System.out.println(replenishDevice.toString());
		return replenishDevice;
	}
	
	/**
	 * 商品
	 * */
	@ResponseBody
	@RequestMapping("replenish/list")
	public Constant getReplenishmentDetails(int task_id, int node_id,int device_id) {
		Constant replenishList = new Constant();
		ReplenishmentDetails replenishmentDetails = new ReplenishmentDetails();
		replenishmentDetails.setReplenishmentTaskId(task_id);
		replenishmentDetails.setNodeId(node_id);
		replenishmentDetails.setVendorId(device_id);
		
		List<Map<String, Object>> productsMap = new ArrayList<Map<String, Object>>();
		List<ReplenishmentDetails> replenishmentDetailsList = replenishmentDetailsDao.queryReplenishmentDetails(replenishmentDetails);
		for(ReplenishmentDetails replenishmentDetails2:replenishmentDetailsList) {
			Map<String,Object> productMap = new HashMap<String, Object>();
			int channelId = replenishmentDetails2.getChannelId();
			int productId = replenishmentDetails2.getProductId();
			String productName = replenishmentDetails2.getProductName();
			int num = replenishmentDetails2.getNum();
			replenishmentDetails.setChannelId(channelId);
			ReplenishmentDetails sales = replenishmentDetailsDao.querySales(replenishmentDetails);
			productMap.put("seq", channelId);
			productMap.put("s_pid", sales.getProductId());
			productMap.put("s_pname", sales.getProductName());
			productMap.put("s_num", sales.getNum());
			productMap.put("s_destory", sales.getNum());
			productMap.put("r_pid", productId);
			productMap.put("r_pname", productName);
			productMap.put("r_num", num);
			productsMap.add(productMap);
		}
		replenishList.setData(productsMap);
		return replenishList;
	}
	
		/**
		 * 实际点位
		 * */
		@ResponseBody
		@RequestMapping("node/list")
		public Constant getSalesNodes() {
			Constant constant = new Constant();
			List<Nodes> salesNodeList = replenishmentDetailsDao.querySalesNodeList();
			List<Map<String, Object>> salesNodeMap = new ArrayList<>();
			for(Nodes nodes:salesNodeList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("n_id", nodes.getId());
				map.put("n_name", nodes.getName());
				salesNodeMap.add(map);
			}
			constant.setData(salesNodeMap);
			return constant;
		}
		
		/**
		 * 实际点位设备
		 * */
		@ResponseBody
		@RequestMapping("node/device/list")
		public Constant getSalesNodeVmsList(int node_id) {
			Constant constant = new Constant();
			List<NodeVms> salesNodeVmsList = replenishmentDetailsDao.querySalesNodeVmsList(node_id);
			List<Map<String, Object>> salesNodeVmsMap = new ArrayList<>();
			for(NodeVms nodeVms:salesNodeVmsList) {
				Map<String, Object> map = new HashMap<String, Object>();
				int vendorId = nodeVms.getVendorId();
				int channelType = replenishmentDetailsDao.getChannelType(vendorId);
				map.put("d_id",vendorId);
				map.put("d_name", nodeVms.getNodeVmName());
				map.put("d_type",channelType);
				salesNodeVmsMap.add(map);
			}
			constant.setData(salesNodeVmsMap);
			return constant;
		}
		
		/**
		 * 实际点位商品
		 * */
		@ResponseBody
		@RequestMapping("node/inventory/list")
		public Constant getSales(int device_id, int node_id) {
			Constant constant = new Constant();
			
			Sales sales = new Sales();
			sales.setVendorId(device_id);
			sales.setNodeId(node_id);
			List<Sales> salesList = replenishmentDetailsDao.getSales(sales);
			List<Map<String, Object>> salesMap = new ArrayList<>();
			for(Sales sales1:salesList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("seq", sales1.getChannelId());
				map.put("p_id",sales1.getProductId());
				map.put("p_name",sales1.getProductName());
				map.put("p_num",sales1.getExistingNum());
				map.put("exp_date","");
				salesMap.add(map);
			}
			constant.setData(salesMap);
			return constant;
		}
		
		/**
		 * 补货确认
		 * */
		@RequestMapping("replenish/submit")
		@ResponseBody
		public Constant saveSales(@RequestBody Map<String, Object> salesMap) {
			int taskId = Integer.parseInt((String) salesMap.get("task_id"));
			String deviceId = (String) salesMap.get("device_id");
			try {
				salesService.saveUpdateAndReplenishmentLog(salesMap);
				MsgBus.getInstance().updateList(deviceId);
			} catch (Exception e) {
				System.out.println("更新失败");
			}
			
			//修改任务列表状态
			List<Nodes> nodesList = replenishmentDetailsDao.queryNodesList(taskId);
			if(nodesList == null || nodesList.size()==0) {
				replenishmentTasksService.updateState(taskId);
			}
			Constant constant = new Constant();
			constant.setError_code(1);
			constant.setErr_msg("这是测试的消息");
			return constant;
		}
		
		/**
		 *  手动调整库存增减
		 * */
		@ResponseBody
		@RequestMapping("replenish/manumotive")
		public Map<String, Object> setSales(@RequestBody Map<String, Object> Manumotive,int token) {
			Map<String, Object> map1 = new HashMap<String, Object>();
			logger.info("运维手动调整库存-----");
			int vendorId = Integer.parseInt((String) Manumotive.get("device_id"));
			List<Map<String,Object>> data = (List<Map<String, Object>>) Manumotive.get("data");
			for(Map<String,Object> map : data) {
				 int seq = Integer.parseInt((String) map.get("seq"));
				 int num = (int) map.get("p_num");
				 logger.info("人员ID:"+token+"--设备ID:"+vendorId+"--货道ID:"+seq+"--调整数量:"+num);
				 salesService.updateSalesNum(vendorId, seq, num);
			}
			logger.info("运维手动调整库存-----完成");
			map1.put("code", 0);
			map1.put("msg", "完成");
			return map1;
		}
		
}

