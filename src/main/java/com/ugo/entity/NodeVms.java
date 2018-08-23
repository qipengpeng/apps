
package com.ugo.entity;

import java.util.List;

/**
 * @author sunshangfeng
 *点位机器关系
 */
public class NodeVms{
	private int id;//点位机器ID
	private int nodeId;//点位ID
	private String nodeName;//点位名称
	private int vendorId;//设备ID
	private int seq;//-点位机器顺序编号
	private String nodeVmName;//点位机器名称
	private	String vendorPassword;//设备密码
	private int vendorProducerId;//设备厂商id
	private int vendorTypeId;//设备类型id
	private	int tempId;//模板ID
	private String tempName;//模版名称
	private int state;//点位机器状态
	private List<DemandTemplateDetails>demandTemplateDetailsList;
	
	
	public String getTempName() {
		return tempName;
	}
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	public List<DemandTemplateDetails> getDemandTemplateDetailsList() {
		return demandTemplateDetailsList;
	}
	public void setDemandTemplateDetailsList(List<DemandTemplateDetails> demandTemplateDetailsList) {
		this.demandTemplateDetailsList = demandTemplateDetailsList;
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
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getNodeVmName() {
		return nodeVmName;
	}
	public void setNodeVmName(String nodeVmName) {
		this.nodeVmName = nodeVmName;
	}
	public String getVendorPassword() {
		return vendorPassword;
	}
	public void setVendorPassword(String vendorPassword) {
		this.vendorPassword = vendorPassword;
	}
	public int getVendorProducerId() {
		return vendorProducerId;
	}
	public void setVendorProducerId(int vendorProducerId) {
		this.vendorProducerId = vendorProducerId;
	}
	public int getVendorTypeId() {
		return vendorTypeId;
	}
	public void setVendorTypeId(int vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}
	
	public int getTempId() {
		return tempId;
	}
	public void setTempId(int tempId) {
		this.tempId = tempId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "NodeVms [id=" + id + ", nodeId=" + nodeId + ", nodeName=" + nodeName + ", vendorId=" + vendorId
				+ ", seq=" + seq + ", nodeVmName=" + nodeVmName + ", vendorPassword=" + vendorPassword
				+ ", vendorProducerId=" + vendorProducerId + ", vendorTypeId=" + vendorTypeId + ", tempId=" + tempId
				+ ", tempName=" + tempName + ", state=" + state + ", demandTemplateDetailsList="
				+ demandTemplateDetailsList + "]";
	}
	
}
