/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ugo.entity.ReplenishmentDetails;
import com.ugo.entity.Sales;

/**
 * @author sunshangfeng
 *点位设备商品销售详情
 */
public interface SalesDao {
	
	/**
	 * 创建销售模版
	 * */
	void addSales(Sales Sales);
	
	/**
	 * 更新库存
	 * */
	void updateSales(ReplenishmentDetails sales);
	
	/**
	 * 减少库存数量
	 * */
	int subStock(int salesId);
	
	/**
	 * 回退库存数量
	 * */
	int addStock(int salesId);
	
	/**
	 * 获取货道类型 	20180620
	 * */
	int getChannlesType(int orderId);
	
	/**
	 * 获取出货时间 	20180620
	 * */
	int getHeatUpTime(int orderId);
	
	/**
	 * 更新库存出货状态
	 * */
	void updateSalesState(@Param("vendorId")int vendorId,@Param("channelId")int channelId,@Param("state")int state);
	
	/**
	 * 获取指定库存状态
	 * */
	int getSalesState(int id);
	
	/**
	 *  手动调整库存增减
	 * */
	void updateSalesNum(@Param("vendorId")int vendorId,@Param("seq")int seq,@Param("num")int num);
	
	/**
	 * 获取库存-数量核对
	 * */
	List<Sales> existingNum(int vendorId);
}
