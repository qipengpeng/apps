/**
 * 
 */
package com.ugo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ugo.dao.ProductTempDao;
import com.ugo.entity.ProductTemplate;
import com.ugo.entity.ProductTemplateTetails;
import com.ugo.service.ProductTempService;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *
 */
@RequestMapping("/PC")
@Controller
public class ProductTempController {
	
		private static final Logger logger = (Logger) LoggerFactory.getLogger(ProductTempController.class);
		@Autowired
		private ProductTempService productTempService;
		
		@Autowired
		private ProductTempDao productTempDao;
		
		/*
		 * 获取商品模板列表信息
		 * */
		@RequestMapping("/getProductTempList")
		public String getList(Model model) {
			List<ProductTemplate> productTempList = productTempService.getList();
			for(ProductTemplate productTemplate:productTempList) {
				int tempId = productTemplate.getId();
				int quantity = productTempDao.getQuantity(tempId);
				int num = productTempDao.getNum(tempId);
				
				if(quantity !=0 && num !=0) {
					productTemplate.setVariety(quantity);
					productTemplate.setAmount(num);
				}
			}
			model.addAttribute("productTempList", productTempList);
			return "/producttemplate";
		}
		/*@RequestMapping("/getProductTemplate")
		public ModelAndView getProductTemplate(int id) {
			ProductTemplate productTemplate = productTempService.getProductTemplate(id);
			ModelAndView model = new ModelAndView("producttemplateUpdate");
			model.addObject("productTemplate", productTemplate);
			return model;
		}*/
		
		/**
		 * 添加模板详情
		 * */
		@RequestMapping("/addProductTemplateTetails")
		@ResponseBody
		public Map<String,Object> addProductTemplateTetails(@RequestBody ProductTemplate productTemplate) {
			//添加商品模板列表
			productTempService.addProductTemplate(productTemplate);
			logger.debug("添加模板信息.................");
			Map<String,Object> map = new HashMap<>();
			map.put("msg", "SUCCESS");
			return map;
		}
		
		/**
		 * 获取模板详情
		 * */
		@RequestMapping("/getProductTempTetailsList")
		public ModelAndView getTetailsList(int templateId) {
			ProductTemplate productTemplate = productTempService.getProductTemplate(templateId);
			List<ProductTemplateTetails> productTempTetailsList = productTempService.getTetailsList(templateId);
			productTemplate.setProductList(productTempTetailsList);
			ModelAndView model = new ModelAndView("producttemplateUpdate");
			model.addObject("productTemplate", productTemplate);
			return model;
		}
		
		/**
		 * 修改模板数据
		 * */
		@RequestMapping("/updateProductTemplateTetails")
		@ResponseBody
		public Map<String,Object> updateProductTemplateTetails(@RequestBody ProductTemplate productTemplate) {
			//修改商品模板列表及模板详情
			productTempService.updateProductTemplate(productTemplate);
			logger.debug("修改模板信息.................");
			Map<String,Object> map = new HashMap<>();
			map.put("msg", "SUCCESS");
			return map;
		}
		
		//ajax获取模板id+模板名称
		@ResponseBody
		@RequestMapping("/findProductTemp")
		public List<ProductTemplate> findProductTemp(int channelsType) {
			List<ProductTemplate> productTempList = productTempService.queryChannelsTypeList(channelsType);
			System.out.println(productTempList.toString());
			return productTempList;
		}
}
