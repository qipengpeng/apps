/**
 * 
 */
package com.ugo.dao;

import java.util.List;

import com.ugo.entity.ProductTemplate;
import com.ugo.entity.ProductTemplateTetails;

/**
 * @author sunshangfeng
 *商品模板列表
 */
public interface ProductTempDao {
	
		/**
		 * 获取模板列表
		 * */
		List<ProductTemplate> getList();
		
		/**
		 * @qipeng 20180717 add
		 * 根据货道类型获取模板列表  
		 * */
		List<ProductTemplate> queryChannelsTypeList(int channelsType);
		
		/**
		 * 获取模板详情
		 * */
		ProductTemplate getProductTemplate(int id);
		
		/**
		 * 获取模板详情列表
		 * */
		List<ProductTemplateTetails> getTetailsList(int templateId);
		
		/**
		 * 获取模板详情---生成库存
		 * */
		List<ProductTemplateTetails> getProductTempTetailsList(int templateId);
		
		/**
		 * 新增模板
		 * */
		int addProductTemplate(ProductTemplate productTemplate);
		
		/**
		 * 新增模板详情
		 * */
		void addProductTemplateTetails(ProductTemplateTetails productTemplateTetails);
		
		int getQuantity(int templateId);
		int getNum(int tempId);
		
		/**
		 * 获取模板内所有商品ID,不重复
		 * */
		List<ProductTemplateTetails> getTetailsIdList(int tempId);
		
		/**
		 * 根据商品ID,获取该商品的总数
		 * */
		int demandsAmount(ProductTemplateTetails tetails);
		
		/**
		 * 修改模板
		 * */
		void updateProductTemplate(ProductTemplate productTemplate);
		
		/**
		 * 修改模板详情
		 * */
		void updateProductTemplateTetails(ProductTemplateTetails productTemplateTetails);
		
}
