/**
 * 
 */
package com.ugo.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author sunshangfeng
 *交割单详情
 */
public class DeliveryDetails {
	private int id;
	private int deliveryId;//交割单ID
	private int nodeId;//点位ID
	private String nodeVmSeqName;//点位机器名称
	private int vendorId;//设备ID
	private int channelId;//货道id
	private int productId;//商品ID
	private String productName;//商品名称
	private int unitPrice;//采购价格
	private int deliveryNum;//交割数量
	private String remark;//
	private String produceDate;//生产日期
	private String createdAt;//
	
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
	public int getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(int deliveryId) {
		this.deliveryId = deliveryId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
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
	public int getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getDeliveryNum() {
		return deliveryNum;
	}
	public void setDeliveryNum(int deliveryNum) {
		this.deliveryNum = deliveryNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProduceDate() {
		return produceDate;
	}
	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = "";
		try {
			 format = fmt.format(fmt.parse(createdAt));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.createdAt = format;
	}
	@Override
	public String toString() {
		return "DeliveryDetails [id=" + id + ", deliveryId=" + deliveryId + ", nodeId=" + nodeId + ", nodeVmSeqName="
				+ nodeVmSeqName + ", vendorId=" + vendorId + ", channelId=" + channelId + ", productId=" + productId
				+ ", productName=" + productName + ", unitPrice=" + unitPrice + ", deliveryNum=" + deliveryNum
				+ ", remark=" + remark + ", produceDate=" + produceDate + ", createdAt=" + createdAt + "]";
	}
	
}
