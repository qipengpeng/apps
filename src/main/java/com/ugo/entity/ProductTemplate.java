/**
 * 
 */
package com.ugo.entity;

import java.util.List;

/**
 * @author sunshangfeng
 *商品模板
 */
public class ProductTemplate {
	private int id;
	private String name;//模板名称
	private int channelsType;//货道类型
	private int variety;//种类
	private int amount;//总数
	private List<ProductTemplateTetails> productList;//商品模板详情
	
	
	public List<ProductTemplateTetails> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductTemplateTetails> productList) {
		this.productList = productList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getChannelsType() {
		return channelsType;
	}
	public void setChannelsType(int channelsType) {
		this.channelsType = channelsType;
	}
	public int getVariety() {
		return variety;
	}
	public void setVariety(int variety) {
		this.variety = variety;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "ProductTemplate [id=" + id + ", name=" + name + ", channelsType=" + channelsType + ", variety="
				+ variety + ", amount=" + amount + "]";
	}
	
	
	
}
