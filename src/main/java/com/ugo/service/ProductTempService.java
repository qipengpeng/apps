/**
 * 
 */
package com.ugo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.ProductTempDao;
import com.ugo.entity.ProductTemplate;
import com.ugo.entity.ProductTemplateTetails;

/**
 * @author sunshangfeng
 * 模板service
 */
@Service
public class ProductTempService {
	
		@Autowired
		private ProductTempDao productTempDao;
		
		/**
		 * 获取列表
		 * */
		public List<ProductTemplate> getList(){
			List<ProductTemplate> list = productTempDao.getList();
			return list;
		}
		
		/**
		 * @qipeng 20180717 add
		 * 根据货道类型获取模板列表  
		 * */
		public List<ProductTemplate> queryChannelsTypeList(int channelsType){
			List<ProductTemplate> channelsTypeList = productTempDao.queryChannelsTypeList(channelsType);
			return channelsTypeList;
		}
		
		/**
		 * 获取模板及详情数据
		 * */
		public ProductTemplate getProductTemplate(int id) {
			ProductTemplate productTemplate = productTempDao.getProductTemplate(id);
			return productTemplate;
		}
		
		/**
		 * 新增模板及模板详情
		 * */
		@Transactional
		public int addProductTemplate(ProductTemplate productTemplate) {
			int productTemplateID = productTempDao.addProductTemplate(productTemplate);
			//获取商品详情并持久化
			List<ProductTemplateTetails> productList = productTemplate.getProductList();
			if(productList != null) {
				for(ProductTemplateTetails productTemplateTetails:productList) {
					//将商品模板ID同步到商品详情
					productTemplateTetails.setTemplateId(productTemplate.getId());
					System.out.println(productTemplateTetails.toString());
					productTempDao.addProductTemplateTetails(productTemplateTetails);
				}
			}
			return productTemplateID;
		}
		
		/**
		 * 获取模板详情
		 * */
		public List<ProductTemplateTetails> getTetailsList(int templateID){
			List<ProductTemplateTetails> tetailsList = productTempDao.getTetailsList(templateID);
			return tetailsList;
		}
		
		/**
		 * 新增模板详情
		 * */
		@Transactional
		public void addProductTemplateTetails(ProductTemplateTetails productTemplateTetails) {
			productTempDao.addProductTemplateTetails(productTemplateTetails);
		}
		
		/**
		 * 修改模板及模板详情
		 * */
		@Transactional
		public void updateProductTemplate(ProductTemplate productTemplate) {
		    productTempDao.updateProductTemplate(productTemplate);
			//获取商品详情并持久化
			List<ProductTemplateTetails> productList = productTemplate.getProductList();
			if(productList != null) {
				for(ProductTemplateTetails productTemplateTetails:productList) {
					//将商品模板ID同步到商品详情
					productTemplateTetails.setTemplateId(productTemplate.getId());
					System.out.println(productTemplateTetails.toString());
					productTempDao.updateProductTemplateTetails(productTemplateTetails);
				}
			}
		}
		
}
