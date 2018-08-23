/**
 * 
 */
package com.ugo.entity;

/**
 * @author sunshangfeng
 *
 */
public class WeixinProductList {
	private int id;//商品ID
	private String name;//商品名称
	private int count;//数量
	private String listImg;//小图
	private String detailsImg;//大图
	private String ingredientImg;//详情图
	private int brandId;//厂商
	private String description;//描述
	private int price;//销售价格
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
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
	public String getIngredientImg() {
		return ingredientImg;
	}
	public void setIngredientImg(String ingredientImg) {
		this.ingredientImg = ingredientImg;
	}
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "WeixinProductList [id=" + id + ", name=" + name + ", count=" + count + ", listImg=" + listImg
				+ ", detailsImg=" + detailsImg + ", ingredientImg=" + ingredientImg + ", brandId=" + brandId
				+ ", description=" + description + ", price=" + price + "]";
	}
	
}
