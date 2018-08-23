/**
 * 
 */
package com.ugo.entity;

/**
 * @author qipeng 2018/8/9
 * 友宝钱包支付返回
 */
public class UboxPayResult {
	private int id;
	private int orderId;
	private String openId;
	private String tradeNo;
	private int totalFee;
	private int payFee;
	private String createdAt;
	private String updatedAt;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
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
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public int getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}
	public int getPayFee() {
		return payFee;
	}
	public void setPayFee(int payFee) {
		this.payFee = payFee;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
		return "UboxPayResult [id=" + id + ", orderId=" + orderId + ", tradeNo=" + tradeNo + ", totalFee=" + totalFee
				+ ", payFee=" + payFee + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
