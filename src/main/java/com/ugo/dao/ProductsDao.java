/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import com.ugo.entity.Products;

/**
 * @author sunshangfeng
 *	商品Dao
 */
public interface ProductsDao {
	//获取商品详情
	List<Products> getList();
	//新增商品
	void add(Products products);
	
	/**
	 * 获取单个商品
	 * */
	Products getProduct(int id);
	
	/**
	 * 获取单个商品价格
	 * */
	int getProductPrice(int id);
	
	/**
	 * 修改商品信息
	 * */
	void updateProduct(Products products);
	
	/**
	 * 获取商品名
	 * */
	String getName(int id);
}
