/**
 * 
 */
package com.ugo.entity;

/**
 * @author sunshangfeng
 *	销售统计
 */
public class Saleroom {
	private int sales;//金额
	private String name;//商品名称
	private int amount;//数量
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Saleroom [sales=" + sales + ", name=" + name + ", amount=" + amount + "]";
	}
	
}
