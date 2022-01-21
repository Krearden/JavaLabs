package com.kirillz.KT2.modelfx

import tornadofx.getProperty
import tornadofx.property
import java.time.LocalDate
import java.time.Period
import java.util.*

class COrderFX (
        val order_id : UUID? = null,
        purchase_date : LocalDate? = LocalDate.now(),
        val owner_id : UUID? = null,
        val productCount : Int = 0
)
{
    var purchase_date by property(purchase_date)
    fun propertyPurchase_date() = getProperty(COrderFX::purchase_date)

}