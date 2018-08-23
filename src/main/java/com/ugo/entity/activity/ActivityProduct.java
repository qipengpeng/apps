package com.ugo.entity.activity;

import java.math.BigDecimal;

public class ActivityProduct {

    private Integer id; //商品id

    private String name; //商品名称

    private BigDecimal price; //价格

    private Integer discount; //折扣

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price.divide(new BigDecimal(100));
    }

    public void setPriceDTO(BigDecimal priceDTO) {
        this.price = priceDTO;
    }
    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "ActivityProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
