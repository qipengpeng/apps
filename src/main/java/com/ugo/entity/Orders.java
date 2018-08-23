/**
 * 
 */
package com.ugo.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author sunshangfeng
 *订单列表
 */
public class Orders {
	private int id;//订单ID
	private String openId;
	private int salesId;//库存id
	private int userId;//用户ID
	private int nodeId;//点位ID
	private String nodeName;//点位名称
	private int vendorId;//设备ID
	private int channelsType;//货道类型
	private int productId;//商品ID
	private String productName;//商品名称
	private int totalPrice;//原价
	private int payPrice;//支付价格
	private int orderStatus;//订单状态
	private int payStatus;//支付状态
	private int payMethod;//支付方式
	private Long payTime;//支付时间
	private int channelId;//货道Id
	private String createdAt;
	private String updatedAt;
	private String sImg;
	private String transactionId;//交易单号
	private int returnCode;
	
	public int getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public int getSalesId() {
		return salesId;
	}
	public void setSalesId(int salesId) {
		this.salesId = salesId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public int getChannelsType() {
		return channelsType;
	}
	public void setChannelsType(int channelsType) {
		this.channelsType = channelsType;
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
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(int payPrice) {
		this.payPrice = payPrice;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public int getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	public int getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}
	public Long getPayTime() {
		return payTime;
	}
	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		if(createdAt !=null && createdAt != "" && !createdAt.isEmpty()) {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = "";
			try {
				 format = fmt.format(fmt.parse(createdAt));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			createdAt = format;
		}
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		if(updatedAt !=null && updatedAt != "" && !updatedAt.isEmpty()) {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = "";
			try {
				 format = fmt.format(fmt.parse(updatedAt));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			updatedAt = format;
		}
		this.updatedAt = updatedAt;
	}
	public String getsImg() {
		return sImg;
	}
	public void setsImg(String sImg) {
		this.sImg = sImg;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/*转译字段*/
	public String getChannelsTypeTransition(){
		if (channelsType==1){
			return "格子柜";
		}else{
			return "冰山机";
		}
	}

	public String getTotalPriceTransition(){
		BigDecimal b = BigDecimal.valueOf(totalPrice).divide(new BigDecimal(100));
		return b.toString();
	}

	public String getPayPriceTransition(){
		BigDecimal b = BigDecimal.valueOf(payPrice).divide(new BigDecimal(100));
		return b.toString();
	}

	public String getOrderStatusTransition(){
		if (orderStatus==1){
			return "未出货";
		}
		if (orderStatus==2){
			return "出货成功";
		}
		if (orderStatus==3){
			return "出货失败";
		}
		if (orderStatus==4){
			return "超时取消";
		}
		if (orderStatus==5){
			return "商品制作中";
		}
		return  Integer.toString(orderStatus);
	}


	public String getPayStatusTransition(){
		if (payStatus==1){
			return "待支付";
		}
		if (payStatus==2){
			return "支付成功";
		}
		if (payStatus==3){
			return "支付失败";
		}
		if (payStatus==4){
			return "退款";
		}
		return Integer.toString(payStatus);
	}

	public String getPayMethodTransition(){
		if (payMethod==1){
			return "微信支付";
		}
		if (payMethod==2){
			return "有宝钱包";
		}
		return Integer.toString(payMethod);
	}
	/*转译字段结束*/

	@Override
	public String toString() {
		return "Orders [id=" + id + ", openId=" + openId + ", salesId=" + salesId + ", userId=" + userId + ", nodeId="
				+ nodeId + ", nodeName=" + nodeName + ", vendorId=" + vendorId + ", channelsType=" + channelsType
				+ ", productId=" + productId + ", productName=" + productName + ", totalPrice=" + totalPrice
				+ ", payPrice=" + payPrice + ", orderStatus=" + orderStatus + ", payStatus=" + payStatus
				+ ", payMethod=" + payMethod + ", payTime=" + payTime + ", channelId=" + channelId + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", sImg=" + sImg + ", transactionId=" + transactionId + "]";
	}
	
}
