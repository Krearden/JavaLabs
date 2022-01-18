package com.kirillz.KT2.services

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import  com.kirillz.KT2.config.CHibernateConfig
import com.kirillz.KT2.dao.CDAOUsers
import com.kirillz.KT2.modelfx.CUserFX
import com.kirillz.KT2.model.CUser
import tornadofx.Controller

class CServiceUsers : Controller()
{
    private var daoUsers = CDAOUsers(CHibernateConfig.getSessionFactory())
    val users = FXCollections.observableArrayList<CUserFX>()

    //достать всех пользователей из базы данных и преобразовать в CUserFX для отображения в интерфейсе программы
    fun getAll() : ObservableList<CUserFX>
    {
        users.clear()
        users.addAll(daoUsers.all.map {user-> CUserFX(user.id, user.login, user.name, user.dateOfBirth, user.gender, user.orders.size)})

        return users
    }

    //сохранение изменений в базу данных.
    //нужны исправления.
    fun save(user : CUserFX)
    {
        val cuser_nofx = CUser(user.id, user.login, user.name, user.gender, user.dateOfBirth, daoUsers.getUserOrders(user.id))
        daoUsers.update(cuser_nofx)
    }
}