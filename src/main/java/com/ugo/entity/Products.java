/**
 * 
 */
package com.ugo.entity;

import java.util.Date;

/**
 * @author sunshangfeng
 *商品表
 */
public class Products {
	private int id;							//商品ID
	private String name;					//商品名称
	private int brandId;			//供应商id
	private int durationPeriod;			//保质期,单位:天
	private int price;					//零售价,单位:分
	private int purchasePrice;			//采购价,单位:分
	private String checklist;				//-菜品清单
	private String ingredient;				//-配料
	private String netweight;				//净重
	private String ingredientImg;					//营养成分img  file
	private String listImg;					//商品列表img  file1
	private String detailsImg;					//商品详情img	file2
	private String description;					//商品描述
	private String fitPeople;				//适合人群
	private String taste;					//-口味
	private Date createdAt;				//创建时间
	private Date updatedAt;				//修改时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public int getDurationPeriod() {
		return durationPeriod;
	}
	public void setDurationPeriod(int durationPeriod) {
		this.durationPeriod = durationPeriod;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getChecklist() {
		return checklist;
	}
	public void setChecklist(String checklist) {
		this.checklist = checklist;
	}
	public String getIngredient() {
		return ingredient;
	}
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	public String getNetweight() {
		return netweight;
	}
	public void setNetweight(String netweight) {
		this.netweight = netweight;
	}
	
	public String getIngredientImg() {
		return ingredientImg;
	}
	public void setIngredientImg(String ingredientImg) {
		this.ingredientImg = ingredientImg;
	}
	public String getListImg() {
		return listImg;
	}
	public void setListImg(String listImg) {
		this.listImg = listImg;
	}
	public String getDetailsImg() {
		return detailsImg;
	}
	public void setDetailsImg(String detailsImg) {
		this.detailsImg = detailsImg;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFitPeople() {
		return fitPeople;
	}
	public void setFitPeople(String fitPeople) {
		this.fitPeople = fitPeople;
	}
	public String getTaste() {
		return taste;
	}
	public void setTaste(String taste) {
		this.taste = taste;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
		return "Products [id=" + id + ", name=" + name + ", brandId=" + brandId + ", durationPeriod=" + durationPeriod
				+ ", price=" + price + ", purchasePrice=" + purchasePrice + ", checklist=" + checklist + ", ingredient="
				+ ingredient + ", netweight=" + netweight + ", ingredientImg=" + ingredientImg + ", listImg=" + listImg
				+ ", detailsImg=" + detailsImg + ", description=" + description + ", fitPeople=" + fitPeople
				+ ", taste=" + taste + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
