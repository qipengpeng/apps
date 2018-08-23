/**
 * 
 */
package com.ugo.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ugo.entity.WeixinProductList;

/**
 * @author sunshangfeng
 *
 */
public interface WeiXinClientSideDao {

	/**
	 * 获取商品列表
	 * */
	List<WeixinProductList> getProductList(int nodeId);
	
	/**
	 * 获取商品详情 ->>ProdictsDao
	 * */
	
	/**
	 * 获取商品剩余数量
	 * */
	int getProductNum(@Param("id") int id,@Param("nodeId") int nodeId );
	
	/**
	 * 获取banner
	 * */
	List<String> queryBannerList();
	
	/**
	 * 获取折扣比例数量
	 * */
	int getDiscountCount(int nodeId);
	
	/**
	 * 获取折扣比例
	 * */
	int getDiscount(int nodeId);
}
