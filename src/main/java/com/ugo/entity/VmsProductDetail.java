/**
 * 
 */
package com.ugo.entity;

/**
 * @author sunshangfeng
 * 设备端商品详情
 */
public class VmsProductDetail {
	private int nodeId;
	private int channelsType;
	private int channelId;
	private int productId;
	private String productName;
	private int salePrice;
	private int discountPrice;
	private int heatUpTime;
	private String listImg;
	private String description;
	private String fitPeople;
	private String netweight;
	
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	
	public int getChannelsType() {
		return channelsType;
	}
	public void setChannelsType(int channelsType) {
		this.channelsType = channelsType;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	public int getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}
	public int getHeatUpTime() {
		return heatUpTime;
	}
	public void setHeatUpTime(int heatUpTime) {
		this.heatUpTime = heatUpTime;
	}
	public String getListImg() {
		return listImg;
	}
	public void setListImg(String listImg) {
		this.listImg = listImg;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFitPeople() {
		return fitPeople;
	}
	public void setFitPeople(String fitPeople) {
		this.fitPeople = fitPeople;
	}
	public String getNetweight() {
		return netweight;
	}
	public void setNetweight(String netweight) {
		this.netweight = netweight;
	}
	
}
