/**
 * 
 */
package com.ugo.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author sunshangfeng
 *补货记录
 */
public class ReplenishmentLogs {
	private int id;//补货单ID
	private int replenishmentTaskId;//补货任务ID
	private int nodeId;//点位ID
	private String nodeName;//点位名称
	private int vendorId;//设备ID
	private String vmNodeSeqName;//设备名称
	private String createdAt;//补货时间
	private int operatorId;//人员ID
	private String operatorName;//补货人员
	private String remark;//备注
	private List<ReplenishmentLogDetails> replenishmentLogDetailsList;
	
	public List<ReplenishmentLogDetails> getReplenishmentLogDetailsList() {
		return replenishmentLogDetailsList;
	}
	public void setReplenishmentLogDetailsList(List<ReplenishmentLogDetails> replenishmentLogDetailsList) {
		this.replenishmentLogDetailsList = replenishmentLogDetailsList;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
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
	public String getVmNodeSeqName() {
		return vmNodeSeqName;
	}
	public void setVmNodeSeqName(String vmNodeSeqName) {
		this.vmNodeSeqName = vmNodeSeqName;
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
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	@Override
	public String toString() {
		return "ReplenishmentLogs [id=" + id + ", replenishmentTaskId=" + replenishmentTaskId + ", nodeId=" + nodeId
				+ ", nodeName=" + nodeName + ", vendorId=" + vendorId + ", vmNodeSeqName=" + vmNodeSeqName
				+ ", createdAt=" + createdAt + ", operatorId=" + operatorId + ", operatorName=" + operatorName
				+ ", remark=" + remark + "]";
	}
	
}
