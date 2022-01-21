package com.kirillz.KT2.viewmodel

import com.kirillz.KT2.services.CServiceProducts
import com.kirillz.KT2.modelfx.CProductFX
import tornadofx.ViewModel

class CViewModelProductList : ViewModel() {
    val serviceProducts : CServiceProducts by inject()
    val products = serviceProducts.getAll()

    fun save()
    {
        serviceProducts.save(products)
    }

    fun delete(productfx : CProductFX?)
    {
        productfx?:return
        serviceProducts.delete(productfx)
    }
}