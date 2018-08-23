/**
 * 
 */
package com.ugo.entity;

/**
 * @author sunshangfeng
 *
 */
public class PayResultInfo {
	private int orderId;
	private String wxTxnId;
	private String wxId;
	private int totalFee;//
	private int cashFee;//
	private String cashFeeType;//
	private int couponFee;//
	private int couponCount;//
	private String bankType;//
	private String feeType;//
	private String tradeType;//
	private String nonceStr;//
	private String attach;
	private String timeEnd;//
	private String resultCode;//
	private String errCode;//
	private String errCodeDes;//
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getWxTxnId() {
		return wxTxnId;
	}
	public void setWxTxnId(String wxTxnId) {
		this.wxTxnId = wxTxnId;
	}
	public String getWxId() {
		return wxId;
	}
	public void setWxId(String wxId) {
		this.wxId = wxId;
	}
	public int getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}
	public int getCashFee() {
		return cashFee;
	}
	public void setCashFee(int cashFee) {
		this.cashFee = cashFee;
	}
	public String getCashFeeType() {
		return cashFeeType;
	}
	public void setCashFeeType(String cashFeeType) {
		this.cashFeeType = cashFeeType;
	}
	public int getCouponFee() {
		return couponFee;
	}
	public void setCouponFee(int couponFee) {
		this.couponFee = couponFee;
	}
	public int getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(int couponCount) {
		this.couponCount = couponCount;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrCodeDes() {
		return errCodeDes;
	}
	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}
	@Override
	public String toString() {
		return "PayResultInfo [orderId=" + orderId + ", wxTxnId=" + wxTxnId + ", wxId=" + wxId + ", totalFee="
				+ totalFee + ", cashFee=" + cashFee + ", cashFeeType=" + cashFeeType + ", couponFee=" + couponFee
				+ ", couponCount=" + couponCount + ", bankType=" + bankType + ", feeType=" + feeType + ", tradeType="
				+ tradeType + ", nonceStr=" + nonceStr + ", attach=" + attach + ", timeEnd=" + timeEnd + ", resultCode="
				+ resultCode + ", errCode=" + errCode + ", errCodeDes=" + errCodeDes + "]";
	}
	
}
