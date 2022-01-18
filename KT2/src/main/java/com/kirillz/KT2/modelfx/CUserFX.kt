package com.kirillz.KT2.modelfx

import tornadofx.getProperty
import tornadofx.property
import java.time.LocalDate
import java.time.Period
import java.util.*

class CUserFX (
        val id : UUID? = null,
        login : String = "",
        name : String = "",
        dayOfBirth : LocalDate? = LocalDate.now(),
        gender : String = "Male",
        val orderCount : Int = 0)
{
    var login by property(login)
    fun propertyLogin() = getProperty(CUserFX::login)

    var name by property(name)
    fun propertyName() = getProperty(CUserFX::name)

    var gender by property(gender)
    fun propertyGender() = getProperty(CUserFX::gender)

    var dateOfBirth by property(dayOfBirth)
    fun propertyDateOfBirth() = getProperty(CUserFX::dateOfBirth)

    //если в БД не указана дата рождения, то возраст равен нулю лет
    val age : Int get() {
        if (dateOfBirth == null)
            return 0
        else
            return Period.between(dateOfBirth, LocalDate.now()).years
    }
}