package com.kirillz.KT2.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class COrder {
    private UUID user_id;
    @Id
//    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
//    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID product_id;

    @Column(name = "purchase_date", columnDefinition = "DATE")
    private LocalDate purchase_date;

    @ManyToOne
    @JoinColumn(name="owner", nullable=false)
    CUser owner;

    @ManyToMany
    @JoinTable(
            name = "products_in_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    List<CProduct> products;

    //геттеры и сеттеры
    public UUID getUser_id() {return user_id;}
    public void setUser_id(UUID user_id) {this.user_id = user_id;}
    public UUID getProduct_id() {return product_id;}
    public void setProduct_id(UUID product_id) {this.product_id = product_id;}
    public LocalDate getPurchase_date() {return purchase_date;}
    public void setPurchase_date(LocalDate purchase_date)
    {
        //нужна проверка, что покупка сделана не в будущем
        LocalDate now = LocalDate.now();
        if (purchase_date.isBefore(now))
        {
            this.purchase_date = purchase_date;
        }
    }

    //путой конструктор
    public COrder()
    {
        user_id = null;
        product_id = null;
        purchase_date = null;

    }

    //конструктор
    public COrder(UUID user_id, UUID product_id, LocalDate purchase_date)
    {
        setUser_id(user_id);
        setProduct_id(product_id);
        setPurchase_date(purchase_date);
    }
}
