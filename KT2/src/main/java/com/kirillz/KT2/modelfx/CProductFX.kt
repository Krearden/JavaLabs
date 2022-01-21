package com.kirillz.KT2.modelfx

import tornadofx.getProperty
import tornadofx.property
import java.time.LocalDate
import java.time.Period
import java.util.*

class CProductFX(
        val id : UUID? = null,
        category : String = "",
        price : Int = 0,
        name : String = "",
        val orderCount : Int = 0
)
{
    var category by property(category)
    fun propertyCategory() = getProperty(CProductFX::category)

    var price by property(price)
    fun propertyPrice() = getProperty(CProductFX::price)

    var name by property(name)
    fun propertyName() = getProperty(CProductFX::name)
}