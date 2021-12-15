package com.kirillz.KT2.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
public class CProduct {
    @Id
//    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
//    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "price")
    private int price;

    @Column(name = "category")
    private String category;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<COrder> orders;

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

    //путой конструктор
    public CProduct()
    {
        id = null;
        product_name = "undifined";
        price = 100;
        category = "undifined";
    }

    //коструктор класса
    public CProduct(UUID id, String product_name, int price, String category)
    {
        setId(id);
        setProduct_name(product_name);
        setPrice(price);
        setCategory(category);
    }
}
