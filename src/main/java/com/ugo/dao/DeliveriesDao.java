/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import com.ugo.entity.Deliveries;
import com.ugo.entity.DeliveryDetails;

/**
 * @author sunshangfeng
 *交割单
 */
public interface DeliveriesDao {
		
	/**
	 * 获取交割列表
	 * */
		List<Deliveries> getDeliveriesList();

	/**
	 * 获取交割信息
	 * */
		Deliveries getDeliveries(int id);
	/**
	 * 获取交割商品详情
	 * */
		List<DeliveryDetails>QueryDeliveryDetailsList(int deliveryId);
	/**
	 * 获取商品总数
	 * */
		int getDeliveryNum(DeliveryDetails deliveryDetails);
	/**
	 * 获取商品总价
	 * */
		int getDeliveryTotal(int deliveryId);
		
	/**
	 * 添加交割信息
	 * */
		void addDeliveries(Deliveries deliveries);
	/**
	 * 添加交割详情
	 * */
		void addDeliveryDetails(DeliveryDetails deliveryDetails);	
	
}
