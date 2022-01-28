package com.kirillz.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@JsonIdentityInfo(
        generator                           = ObjectIdGenerators.PropertyGenerator.class,
        property                            = "order_id"
)
public class COrder {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID order_id;

    @Column(name = "purchase_date", columnDefinition = "DATE")
    private LocalDate purchase_date;

    @ManyToOne
    @JoinColumn(name="owner", nullable=false)
    @JsonIdentityReference(alwaysAsId = true)
    CUser owner;

    @ManyToMany
    @JoinTable(name = "products_in_orders", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonIdentityReference(alwaysAsId = true)
    List<CProduct> products;

    //геттеры и сеттеры
    public CUser getOwner()
    {
        return owner;
    }
    public void setOwner(CUser owner)
    {
        this.owner = owner;
    }
    public UUID getOrder_id() {return order_id;}
    public void setOrder_id(UUID product_id) {this.order_id = order_id;}
    public LocalDate getPurchase_date() {return purchase_date;}
    public void setPurchase_date(LocalDate purchase_date)
    {
        LocalDate now = LocalDate.now();
        if (purchase_date.isBefore(now))
        {
            this.purchase_date = purchase_date;
        }
    }
    public List<CProduct> getProducts()
    {
        return products;
    }
    public void setProducts(List<CProduct> products)
    {
        this.products = products;
    }
    public COrder()
    {
        order_id = null;
        owner = null;
        purchase_date = LocalDate.now();
        products = new ArrayList<>();
    }

    public COrder(UUID order_id, CUser owner, LocalDate purchase_date, List <CProduct> products)
    {
        setOrder_id(order_id);
        setOwner(owner);
        setPurchase_date(purchase_date);
        this.products = products;
    }
}
