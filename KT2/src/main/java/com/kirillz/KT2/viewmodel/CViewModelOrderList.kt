package com.kirillz.KT2.viewmodel

import com.kirillz.KT2.modelfx.COrderFX
import com.kirillz.KT2.services.CServiceOrders
import tornadofx.ViewModel

class CViewModelOrderList : ViewModel(){
    val serviceOrders : CServiceOrders by inject()
    val orders = serviceOrders.getAll()

    fun save()
    {
        serviceOrders.save(orders)
    }

    fun delete(orderfx : COrderFX?)
    {
        orderfx?:return
        serviceOrders.delete(orderfx)
    }
}