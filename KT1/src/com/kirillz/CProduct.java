package com.kirillz;

import java.util.UUID;

public class CProduct {
    private UUID id;
    private String product_name;
    private int price;
    private String category;

    //геттеры, сеттеры и функции
    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}
    public String getProduct_name() {return product_name;}
    public void setProduct_name(String product_name) {this.product_name = product_name;}
    public int getPrice() {return price;}
    public void setPrice(int price)
    {
        if (price <= 10000)
            this.price = price;
    }
    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}

    //коструктор класса
    public CProduct(UUID id, String product_name, int price, String category)
    {
        setId(id);
        setProduct_name(product_name);
        setPrice(price);
        setCategory(category);
    }
}
