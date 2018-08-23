/**
 * 
 */
package com.ugo.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author sunshangfeng
 *清货详情
 */
public class InventoryDetails {
	private int id;
	private int inventoryId;//清货ID
	private int nodeId;//点位ID
	private String nodeName;//点位名称
	private int vendorId;//设备Id
	private String nodeVmSeqName;//设备名称
	private int productId;//商品Id
	private String productName;//商品名称
	private int num;//清货数量
	private String createdAt;//
	private String updatedAt;//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
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
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public String getNodeVmSeqName() {
		return nodeVmSeqName;
	}
	public void setNodeVmSeqName(String nodeVmSeqName) {
		this.nodeVmSeqName = nodeVmSeqName;
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
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
		return "InventoryDetails [id=" + id + ", inventoryId=" + inventoryId + ", nodeId=" + nodeId + ", nodeName="
				+ nodeName + ", vendorId=" + vendorId + ", nodeVmSeqName=" + nodeVmSeqName + ", productId=" + productId
				+ ", productName=" + productName + ", num=" + num + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
	
	
}
