package com.kirillz.KT2.services

import com.kirillz.KT2.Main
import com.kirillz.KT2.config.CHibernateConfig
import com.kirillz.KT2.dao.CDAOProducts
import com.kirillz.KT2.dao.CDAOOrders
import com.kirillz.KT2.model.COrder
import com.kirillz.KT2.model.CProduct
import com.kirillz.KT2.modelfx.CProductFX
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Controller

class CServiceProducts : Controller(){

    private var daoProducts = CDAOProducts(CHibernateConfig.getSessionFactory())
    val products = FXCollections.observableArrayList<CProductFX>()

    //достать все товары из базы данных и преобразовать в CProductFX для отображения в интерфейсе программы
    fun getAll() : ObservableList<CProductFX>
    {
        products.clear()
        products.addAll(daoProducts.all.map {product-> CProductFX(product.id, product.category, product.price, product.product_name, product.orders.size)});

        return products
    }

    //обновить внесенные в программе изменения в базу данных
    fun save(products : List<CProductFX>)
    {
        val seq = products.asSequence().map {product -> CProduct(product.id, product.name, product.price, product.category, daoProducts.getProductOrders(product.id)) }
        val existingProducts = seq.filter {it.id != null}.toList()
        daoProducts.updateList(existingProducts)
        getAll()
    }

    //реализация удаления товара
    fun delete(productfx : CProductFX)
    {
        val cproduct = daoProducts.get(productfx.id)
        daoProducts.deleteProduct(cproduct)
        products.remove(productfx)
    }
}