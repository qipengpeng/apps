/**
 * 
 */
package com.ugo.entity;

/**
 * @author sunshangfeng
 *点位设备商品销售详情
 */
public class Sales {
	private int id;
	private int	nodeId;//点位ID
	private String nodeName;//点位名称
	private int productId;//商品ID
	private String productName;//商品名称
	private int salePrice;//单位:分
	private int vendorId;//设备ID
	private String vendorName;//设备名称
	private int channelId;//货道ID
	private int defaultNum;//默认数量
	private int existingNum;//现有数量
	private int status;//货位状态
	private int channelsType;//货道类型
	private int heatUpTime;//加热时间
	
	public int getChannelsType() {
		return channelsType;
	}
	public void setChannelsType(int channelsType) {
		this.channelsType = channelsType;
	}
	public int getHeatUpTime() {
		return heatUpTime;
	}
	public void setHeatUpTime(int heatUpTime) {
		this.heatUpTime = heatUpTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public int getDefaultNum() {
		return defaultNum;
	}
	public void setDefaultNum(int defaultNum) {
		this.defaultNum = defaultNum;
	}
	public int getExistingNum() {
		return existingNum;
	}
	public void setExistingNum(int existingNum) {
		this.existingNum = existingNum;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Sales [id=" + id + ", nodeId=" + nodeId + ", nodeName=" + nodeName + ", productId=" + productId
				+ ", productName=" + productName + ", salePrice=" + salePrice + ", vendorId=" + vendorId
				+ ", vendorName=" + vendorName + ", channelId=" + channelId + ", defaultNum=" + defaultNum
				+ ", existingNum=" + existingNum + ", status=" + status + ", channelsType=" + channelsType
				+ ", heatUpTime=" + heatUpTime + "]";
	}
	
}
