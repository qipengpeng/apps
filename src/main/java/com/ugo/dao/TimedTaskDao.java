/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import com.ugo.entity.Orders;

/**
 * @author sunshangfeng
 *	定时任务处理
 */
public interface TimedTaskDao {
	
	/**
	 * 获取超时订单
	 * */
	List<Orders> queryOrderList();
	
	/**
	 * @author qipeng 2018/8/17
	 * 定时点位开关
	 * */
	void updateNodeStateList(int state);
	
	/**
	 * @author qipeng 2018/8/17
	 * 获取离线超时设备
	 * */
	List<Integer>findOfflineVendorList(int time);
}
