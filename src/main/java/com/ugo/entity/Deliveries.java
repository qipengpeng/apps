package com.ugo.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author sunshangfeng
 *交割单
 */
public class Deliveries {
	private int id;//交割单ID
	private int demandId;//需求单ID
	private String demandDate;//需求时间
	private String operator;//交割人
	private String createdAt;//交割时间
	private int total;//总价
	private List<DeliveryDetails> deliveryDetailsList;
	private List<Nodes> nodesList;
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
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
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<DeliveryDetails> getDeliveryDetailsList() {
		return deliveryDetailsList;
	}
	public void setDeliveryDetailsList(List<DeliveryDetails> deliveryDetailsList) {
		this.deliveryDetailsList = deliveryDetailsList;
	}
	public List<Nodes> getNodesList() {
		return nodesList;
	}
	public void setNodesList(List<Nodes> nodesList) {
		this.nodesList = nodesList;
	}
	@Override
	public String toString() {
		return "Deliveries [id=" + id + ", demandId=" + demandId + ", demandDate=" + demandDate + ", operator="
				+ operator + ", createdAt=" + createdAt + ", total=" + total + ", deliveryDetailsList="
				+ deliveryDetailsList + ", nodesList=" + nodesList + "]";
	}
	
}
