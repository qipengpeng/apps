/**
 * 
 */
package com.ugo.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author sunshangfeng
 *需求针对点位详情
 */
public class DemandDetails {
	private int id;
	private int demandId;//需求ID
	private String demandDate;//需求时间
	private int nodeId;//点位ID
	private int nodeVmSeq;//点位机器顺序
	private String nodeVmSeqName;//点位机器名
	private int vendorId;//机器ID
	private int productId;//商品ID
	private String productName;//商品名称
	private int num;//商品数量
	private String createdAt;
	private List<NodeVms>nodesList;
	
	public String getDemandDate() {
		return demandDate;
	}
	public void setDemandDate(String demandDate) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = "";
		try {
			 format = fmt.format(fmt.parse(demandDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.demandDate = format;
	}
	public List<NodeVms> getNodesList() {
		return nodesList;
	}
	public void setNodesList(List<NodeVms> nodesList) {
		this.nodesList = nodesList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDemandId() {
		return demandId;
	}
	public void setDemandId(int demandId) {
		this.demandId = demandId;
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
		return "DemandDetails [id=" + id + ", demandId=" + demandId + ", demandDate=" + demandDate + ", nodeId="
				+ nodeId + ", nodeVmSeq=" + nodeVmSeq + ", nodeVmSeqName=" + nodeVmSeqName + ", vendorId=" + vendorId
				+ ", productId=" + productId + ", productName=" + productName + ", num=" + num + ", createdAt="
				+ createdAt + ", nodesList=" + nodesList + "]";
	}
	
}
