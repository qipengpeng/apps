/**
 * 
 */
package com.ugo.entity;

/**
 * @author sunshangfeng
 *商品模板详情
 */
public class ProductTemplateTetails {
	private int id;
	private int templateId;//模板ID
	private int templateName;//模板名称
	private int channelsType;//供应商ID	
	private int channelsId;//货道ID
	private int productsId;//商品ID
	private String productsName;//商品名称
	private int brandId; //品牌ID
	private int brandPrice;//销售价格
	private int num;//数量
	private int heatUpTime;//加热时间
	
	public int getHeatUpTime() {
		return heatUpTime;
	}
	public void setHeatUpTime(int heatUpTime) {
		this.heatUpTime = heatUpTime;
	}
	public int getTemplateName() {
		return templateName;
	}
	public void setTemplateName(int templateName) {
		this.templateName = templateName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public int getChannelsType() {
		return channelsType;
	}
	public void setChannelsType(int channelsType) {
		this.channelsType = channelsType;
	}
	public int getChannelsId() {
		return channelsId;
	}
	public void setChannelsId(int channelsId) {
		this.channelsId = channelsId;
	}
	public int getProductsId() {
		return productsId;
	}
	public void setProductsId(int productsId) {
		this.productsId = productsId;
	}
	
	public String getProductsName() {
		return productsName;
	}
	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	
	
	public int getBrandPrice() {
		return brandPrice;
	}
	public void setBrandPrice(int brandPrice) {
		this.brandPrice = brandPrice;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "ProductTemplateTetails [id=" + id + ", templateId=" + templateId + ", templateName=" + templateName
				+ ", channelsType=" + channelsType + ", channelsId=" + channelsId + ", productsId=" + productsId
				+ ", productsName=" + productsName + ", brandId=" + brandId + ", brandPrice=" + brandPrice + ", num="
				+ num + ", heatUpTime=" + heatUpTime + "]";
	}
	
}
