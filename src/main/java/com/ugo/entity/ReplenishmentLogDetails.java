/**
 * 
 */
package com.ugo.entity;

/**
 * @author sunshangfeng
 *
 */
public class ReplenishmentLogDetails {
		private int id;//
		private int replenishmentLogId;//补货记录Id
		private int channelId;//货道号
		private int productId;//商品ID
		private String productName;//商品名称
		private int num;//数量
		private int demandNum;//需求数量
		
		public int getDemandNum() {
			return demandNum;
		}
		public void setDemandNum(int demandNum) {
			this.demandNum = demandNum;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getReplenishmentLogId() {
			return replenishmentLogId;
		}
		public void setReplenishmentLogId(int replenishmentLogId) {
			this.replenishmentLogId = replenishmentLogId;
		}
		public int getChannelId() {
			return channelId;
		}
		public void setChannelId(int channelId) {
			this.channelId = channelId;
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
		@Override
		public String toString() {
			return "ReplenishmentLogDetails [id=" + id + ", replenishmentLogId=" + replenishmentLogId + ", channelId="
					+ channelId + ", productId=" + productId + ", productName=" + productName + ", num=" + num + "]";
		}
		
		
}
