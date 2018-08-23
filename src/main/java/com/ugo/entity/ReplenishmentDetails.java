/**
 * 
 */
package com.ugo.entity;

/**
 * @author sunshangfeng
 *补货任务详情
 */
public class ReplenishmentDetails {
	private int id;//
	private int replenishmentTaskId;//补货任务ID
	private int nodeId;//点位Id
	private String nodeName;//点位名称
	private int nodeVmSeq;//机器在点位顺序
	private String nodeVmSeqName;//机器在点位名称
	private int vendorId;//设备ID
	private int channelId;//货道
	private int productId;//商品ID
	private String productName;//商品名称
	private int num;//商品数量
	private int price;//价格
	private int heatUpTime;//加热时间
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getHeatUpTime() {
		return heatUpTime;
	}
	public void setHeatUpTime(int heatUpTime) {
		this.heatUpTime = heatUpTime;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReplenishmentTaskId() {
		return replenishmentTaskId;
	}
	public void setReplenishmentTaskId(int replenishmentTaskId) {
		this.replenishmentTaskId = replenishmentTaskId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getNodeVmSeq() {
		return nodeVmSeq;
	}
	public void setNodeVmSeq(int nodeVmSeq) {
		this.nodeVmSeq = nodeVmSeq;
	}
	public String getNodeVmSeqName() {
		return nodeVmSeqName;
	}
	public void setNodeVmSeqName(String nodeVmSeqName) {
		this.nodeVmSeqName = nodeVmSeqName;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "ReplenishmentDetails [id=" + id + ", replenishmentTaskId=" + replenishmentTaskId + ", nodeId=" + nodeId
				+ ", nodeName=" + nodeName + ", nodeVmSeq=" + nodeVmSeq + ", nodeVmSeqName=" + nodeVmSeqName
				+ ", vendorId=" + vendorId + ", channelId=" + channelId + ", productId=" + productId + ", productName="
				+ productName + ", num=" + num + ", price=" + price + ", heatUpTime=" + heatUpTime + "]";
	}
	
}
