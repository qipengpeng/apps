/**
 * 
 */
package com.ugo.entity;


/**
 * @author sunshangfeng
 *需求模板详情
 */
public class DemandTemplateDetails {
	private int id;//ID
	private int demandTemplateId;//需求ID
	private int productId;//商品ID
	private String productName;//商品名
	private int num;//商品数量
	private String createdAt;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDemandTemplateId() {
		return demandTemplateId;
	}
	public void setDemandTemplateId(int demandTemplateId) {
		this.demandTemplateId = demandTemplateId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "DemandTemplateDetails [id=" + id + ", demandTemplateId=" + demandTemplateId + ", productId=" + productId
				+ ", productName=" + productName + ", num=" + num + ", createdAt=" + createdAt + "]";
	}
	
}
