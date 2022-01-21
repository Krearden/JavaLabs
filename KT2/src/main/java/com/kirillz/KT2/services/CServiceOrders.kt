package com.kirillz.KT2.services

import com.kirillz.KT2.config.CHibernateConfig
import com.kirillz.KT2.dao.CDAOOrders
import com.kirillz.KT2.dao.CDAOUsers
import com.kirillz.KT2.model.COrder
import com.kirillz.KT2.modelfx.COrderFX
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Controller

class CServiceOrders : Controller() {
    private var daoOrders = CDAOOrders(CHibernateConfig.getSessionFactory())
    private var daoUsers = CDAOUsers(CHibernateConfig.getSessionFactory())
    val orders = FXCollections.observableArrayList<COrderFX>()

    //достать все заказы из базы данных и преобразовать в COrderFX для отображения в интерфейсе программы
    fun getAll() : ObservableList<COrderFX>
    {
        orders.clear()
        orders.addAll(daoOrders.all.map {order-> COrderFX(order.order_id, order.purchase_date, order.owner.id, order.products.size)});

        return orders
    }

    fun save(orders : List<COrderFX>)
    {

    }

    fun delete(orderfx : COrderFX)
    {
        val order = daoOrders.get(orderfx.order_id)
        daoOrders.delete(order)
        orders.remove(orderfx)
    }
}