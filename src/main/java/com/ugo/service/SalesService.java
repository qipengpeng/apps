/**
 * 
 */
package com.ugo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.InventoryDao;
import com.ugo.dao.ProductsDao;
import com.ugo.dao.ReplenishmentDetailsDao;
import com.ugo.dao.ReplenishmentLogsDao;
import com.ugo.dao.SalesDao;
import com.ugo.entity.InventoryDetails;
import com.ugo.entity.ReplenishmentDetails;
import com.ugo.entity.ReplenishmentLogDetails;
import com.ugo.entity.ReplenishmentLogs;
import com.ugo.entity.Sales;

/**
 * @author sunshangfeng
 *
 */
@Service
public class SalesService {
	
	@Autowired
	private SalesDao salesDao;
	
	@Autowired
	private ProductsDao productDao;
	
	@Autowired
	private InventoryDao inventoryDao;
	
	@Autowired
	private ReplenishmentLogsDao replenishmentLogsDao;
	
	@Autowired
	private ReplenishmentDetailsDao replenishmentDetailsDao;
	
	@Transactional
	public void addSales(Sales Sales) {
		salesDao.addSales(Sales);
	}
	
	/**
	 * 获取货道类型 	20180620
	 * */
	public int getChannlesType(int orderId) {
		int channlesType = salesDao.getChannlesType(orderId);
		return channlesType;
	}
	
	/**
	 * 获取出货时间 	20180620
	 * */
	public int getHeatUpTime(int orderId) {
		int heatUpTime = salesDao.getHeatUpTime(orderId);
		return heatUpTime;
	}
	
	@Transactional
	public void updateSales(ReplenishmentDetails sales) {
		//修改库存
		salesDao.updateSales(sales);
		//修改补货详情状态
		replenishmentDetailsDao.updateReplenishmentDetailsState(sales);
	}
	
	/**
	 *1. 更新库存并保存补货记录
	 *2. 记录清货信息
	 * */
	@Transactional
	public void saveUpdateAndReplenishmentLog(Map<String, Object> salesMap) {
		int taskId = Integer.parseInt((String) salesMap.get("task_id"));
		int nodeId = Integer.parseInt((String) salesMap.get("node_id"));
		int vendorId = Integer.parseInt((String) salesMap.get("device_id"));
		String remark = (String) salesMap.get("remark");
		
		int inventoryId = inventoryDao.getInventoryId(taskId);
		//获取当前库存信息
		List<InventoryDetails> inventoryDetailsList = inventoryDao.getSales(nodeId, vendorId);
		for(InventoryDetails inventoryDetails:inventoryDetailsList) {
			inventoryDetails.setInventoryId(inventoryId);
			//生成清货详情记录
			inventoryDao.addInventoryDetails(inventoryDetails);
			System.out.println("1生成清货详情记录");
		}
		
		ReplenishmentDetails sales = new ReplenishmentDetails();
		sales.setReplenishmentTaskId(taskId);
		sales.setNodeId(nodeId);
		sales.setVendorId(vendorId);
		
		//保存补货记录列表
		ReplenishmentLogs replenishmentLogs =new ReplenishmentLogs();
		replenishmentLogs.setReplenishmentTaskId(taskId);
		replenishmentLogs.setNodeId(nodeId);
		replenishmentLogs.setVendorId(vendorId);
		replenishmentLogs.setRemark(remark);
		replenishmentLogsDao.addReplenishmentLogs(replenishmentLogs);
		System.out.println("2插入补货记录列表");
		//更新库存
		List<Map<String, Object>> salesList= (List<Map<String, Object>>) salesMap.get("data");
		for(int i=0;i<salesList.size();i++) {
			Map<String, Object> map = salesList.get(i);
			System.out.println(map.toString());
			int productId = Integer.parseInt((String) map.get("p_id"));
			String productName = (java.lang.String) map.get("p_name");
			int num = (int) map.get("p_num");
			int channelId = (int) map.get("seq");
			sales.setChannelId(channelId);
			sales.setProductId(productId);
			sales.setProductName(productName);
			int price = productDao.getProductPrice(productId);
			sales.setPrice(price);
			sales.setNum(num);
			// 获取加热时间
			int heatUpTime = replenishmentDetailsDao.getHeatUpTime(sales);
			System.out.println("获取加热时间"+heatUpTime);
			sales.setHeatUpTime(heatUpTime);
			//修改库存
			salesDao.updateSales(sales);
			System.out.println("3修改库存");
			//修改补货详情状态
			replenishmentDetailsDao.updateReplenishmentDetailsState(sales);
			System.out.println("4修改补货详情状态存");
			//补货任务单个商品数量----记录商品需求数量
			int demandNum = replenishmentDetailsDao.queryReplenishmentDetailsNum(sales);
			//保存补货记录详情   start
			ReplenishmentLogDetails replenishmentLogDetails = new ReplenishmentLogDetails();
			replenishmentLogDetails.setReplenishmentLogId(replenishmentLogs.getId());
			replenishmentLogDetails.setChannelId(channelId);
			replenishmentLogDetails.setProductId(productId);
			replenishmentLogDetails.setProductName(productName);
			replenishmentLogDetails.setNum(num);
			replenishmentLogDetails.setDemandNum(demandNum);
			replenishmentLogsDao.addReplenishmentLogDetails(replenishmentLogDetails);
			System.out.println("5保存补货记录详情   ");
			//保存补货记录详情 end
		}
	}
	
	/**
	 * 更新库存出货状态
	 * */
	@Transactional
	public void updateSalesState(int vendorId,int channelId,int state) {
		int [] channels1= {1,2};
		int [] channels2= {3,4};
		int[] channels3 = {1,2,3,4};
		if(channelId == 1 ||channelId ==2) {
			for(int i=0;i<channels1.length;i++) {
				salesDao.updateSalesState(vendorId,channels1[i],state);
			}
		}else if(channelId == 3 ||channelId ==4 ||channelId == 5) {
			for(int i=0;i<channels2.length;i++) {
				salesDao.updateSalesState(vendorId,channels2[i],state);
			}
		}else if(channelId ==0) {
			for(int i=0;i<channels3.length;i++) {
				salesDao.updateSalesState(vendorId,channels3[i],state);
			}
		}
	}
	
	/**
	 * 获取指定库存状态
	 * */
	public int getSalesState(int id) {
		int salesState = salesDao.getSalesState(id);
		return salesState;
	}
	
	/**
	 * 减少库存数量
	 * */
	@Transactional
	public void subStock(int salesId) {
		salesDao.subStock(salesId);
	}
	
	/**
	 * 回退库存数量
	 * */
	@Transactional
	public void addStock(int salesId) {
		salesDao.addStock(salesId);
	}
	
	/**
	 *  手动调整库存增减
	 * */
	@Transactional
	public void updateSalesNum(int vendorId,int seq,int num) {
		salesDao.updateSalesNum(vendorId, seq, num);
	}
	
	/**
	 * 获取库存数量
	 * */
	public List<Sales> existingNum(int vendorId) {
		List<Sales> salesList = salesDao.existingNum(vendorId);
		return salesList;
	}
}
