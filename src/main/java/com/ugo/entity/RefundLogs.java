/**
 * 
 */
package com.ugo.entity;

/**
 * @author sunshangfeng
 *退款记录
 */
public class RefundLogs {
	private int id;//
	private int orderId;//
	private int nodeId;//
	private int refundFee;
	private String refundDate;//
	private int reason;
	private String note;//
	private String operator;
	private String orderDate;//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(int refundFee) {
		this.refundFee = refundFee;
	}
	public String getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}
	public int getReason() {
		return reason;
	}
	public void setReason(int reason) {
		this.reason = reason;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	@Override
	public String toString() {
		return "RefundLogs [id=" + id + ", orderId=" + orderId + ", nodeId=" + nodeId + ", refundFee=" + refundFee
				+ ", refundDate=" + refundDate + ", reason=" + reason + ", note=" + note + ", operator=" + operator
				+ ", orderDate=" + orderDate + "]";
	}
	
}
