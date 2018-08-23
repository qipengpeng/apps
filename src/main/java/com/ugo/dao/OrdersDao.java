/**
 * 
 */
package com.ugo.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ugo.entity.Orders;
import com.ugo.entity.Sales;

/**
 * @author sunshangfeng
 *订单列表
 */
public interface OrdersDao {
	/**
	 * 订单列表
	 * */
	List<Orders> getOrderList(@Param("begin") int begin,@Param("end") int end);
	
	/**
	 * 订单列表总条数
	 * */
	long findOrderCount();
	
	/**
	 * 根据条件获取订单
	 * */
	List<Orders> getOrders2(@Param("orders") Orders orders);

	/**
	 * 根据时间差订单
	 * @return 订单集合
	 */
	List<Orders> getOrdersByDate(@Param("orders") Orders orders,@Param("startDate") String startDate,@Param("endDate") String endDate);

	/**
	 * 查询一个月内的订单
	 * @return
	 */
	List<Orders> getOrdersByMonth(@Param("date") Date date);
	
	/**
	 * 根据openId获取订单
	 * */
	List<Orders> getWXOrders(@Param("openId")String openId,@Param("page")int page,@Param("pageLimit")int pageLimit);
	
	/**
	 * 生成订单--获取可售库存
	 * @param sales
	 * @author qipeng
	 * */
	List<Sales> getSales(Sales sales);
	
	/**
	 * 生成订单--获取库存
	 * @param vendorId
	 * @param productId
	 * @author qipeng  2018/8/8
	 * */
	Sales findSales(@Param("vendorId")int vendorId,@Param("productId")int productId);
	
	/**
	 * 生成订单
	 * */
	void addOrders(Orders orders);
	
	/**
	 * 生成订单商品关联
	 * */
	void addOrdersDetails(Orders orders);
	
	/**
	 * 查询订单是否覆盖
	 * */
	int getOrderStatus(String openId);
	
	/**
	 * 根据openid获取订单ID并更新
	 * */
	Orders getOrderId(String openId);
	
	/**
	 * 修改订单支付状态
	 * @param orders
	 * @author qipeng 2018/8/8
	 * */
	int updateOrder(Orders orders);
	
	/**
	 * 修改订单对应的商品
	 * @param orders
	 * @author qipeng 2018/8/8
	 * */
	void updateOrdersDetails(Orders orders);
	
	/**
	 * 更新订单状态
	 * */
	void updatePayStataus(int id);
	
	/**
	 * 获取指定订单信息
	 * */
	Orders queryOrders(int id);
	
	/**
	 * 获取指定订单支付方式
	 * */
	int findPayMethod(int id);
	
}
