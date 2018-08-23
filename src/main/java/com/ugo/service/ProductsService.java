/**
 * 
 */
package com.ugo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.ProductsDao;
import com.ugo.entity.Products;

/**
 * @author sunshangfeng
 *
 */

@Service
public class ProductsService{
	
	@Autowired
	private	ProductsDao productsDao;
	
	public List<Products> getList() {
		// TODO Auto-generated method stub
		List<Products> list = productsDao.getList();
		return list;
	}
	
	@Transactional
	public void add(Products products) {
		// TODO Auto-generated method stub
		productsDao.add(products);
	}

	public Products getProduct(int id) {
		// TODO Auto-generated method stub
		Products product = productsDao.getProduct(id);
		return product;
	}
	@Transactional
	public void updateProduct(Products products) {
		// TODO Auto-generated method stub
		Products products2 = new Products();
		products2.setId(products.getId());
		products2.setName(products.getName());
		products2.setDurationPeriod(products.getDurationPeriod());
		products2.setPrice(products.getPrice());
		products2.setPurchasePrice(products.getPurchasePrice());
		products2.setNetweight(products.getNetweight());
		products2.setListImg(products.getListImg());
		products2.setDetailsImg(products.getDetailsImg());
		products2.setIngredientImg(products.getIngredientImg());
		products2.setDescription(products.getDescription());
		productsDao.updateProduct(products2);
	}
}