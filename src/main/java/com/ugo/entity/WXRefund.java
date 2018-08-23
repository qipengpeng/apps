/**
 * 
 */
package com.ugo.entity;

/**
 * @author sunshangfeng
 *
 */
public class WXRefund {
	private int id;//
	private int order_id;//
	private int node_id;
	private int refund_fee;
	private String refund_date;
	private int reason;//原因
	private String note;//
	private String operator;//操作员
	private String order_date;//订单时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getNode_id() {
		return node_id;
	}
	public void setNode_id(int node_id) {
		this.node_id = node_id;
	}
	public int getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(int refund_fee) {
		this.refund_fee = refund_fee;
	}
	public String getRefund_date() {
		return refund_date;
	}
	public void setRefund_date(String refund_date) {
		this.refund_date = refund_date;
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
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	@Override
	public String toString() {
		return "WXRefund [id=" + id + ", order_id=" + order_id + ", node_id=" + node_id + ", refund_fee=" + refund_fee
				+ ", refund_date=" + refund_date + ", reason=" + reason + ", note=" + note + ", operator=" + operator
				+ ", order_date=" + order_date + "]";
	}
	
}
