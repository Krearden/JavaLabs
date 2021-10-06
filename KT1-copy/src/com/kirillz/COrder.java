package com.kirillz;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class COrder {
    private UUID user_id;
    private UUID product_id;
    private LocalDateTime purchase_date_time;

    //геттеры и сеттеры
    public UUID getUser_id() {return user_id;}
    public void setUser_id(UUID user_id) {this.user_id = user_id;}
    public UUID getProduct_id() {return product_id;}
    public void setProduct_id(UUID product_id) {this.product_id = product_id;}
    public LocalDateTime getPurchase_date_time() {return purchase_date_time;}
    public void setPurchase_date_time(LocalDateTime purchase_date_time)
    {
        //нужна проверка, что покупка сделана не в будущем
        LocalDate now = LocalDate.now();
        LocalDate date_purchase_date_time = purchase_date_time.toLocalDate();
        if (date_purchase_date_time.isBefore(now))
        {
            this.purchase_date_time = purchase_date_time;
        }
    }

    //конструктор
    COrder(UUID user_id, UUID product_id, LocalDateTime purchase_date_time)
    {
        setUser_id(user_id);
        setProduct_id(product_id);
        setPurchase_date_time(purchase_date_time);
    }
}
