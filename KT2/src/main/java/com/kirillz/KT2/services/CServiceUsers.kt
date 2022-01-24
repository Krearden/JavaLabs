package com.kirillz.KT2.services

import com.kirillz.KT2.config.CHibernateConfig
import com.kirillz.KT2.dao.CDAOOrders
import com.kirillz.KT2.dao.CDAOUsers
import com.kirillz.KT2.model.CUser
import com.kirillz.KT2.modelfx.CUserFX
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Controller

class CServiceUsers : Controller()
{
    private var daoUsers = CDAOUsers(CHibernateConfig.getSessionFactory())
    private var daoOrders = CDAOOrders(CHibernateConfig.getSessionFactory())
    val users = FXCollections.observableArrayList<CUserFX>()

    //достать всех пользователей из базы данных и преобразовать в CUserFX для отображения в интерфейсе программы
    fun getAll() : ObservableList<CUserFX>
    {
        users.clear()
        //каждый пользователь (CUser) из всех, что находятся в базе данных преобразуется в CUserFX для отображения в интерфейсе
        //и добавляется в список users
        //для чистоты кода и более удобного процесса конвертации данных используяется встроенный в Kotlin интерфейс Map
        users.addAll(daoUsers.all.map {user-> CUserFX(user.id, user.login, user.name, user.dateOfBirth, user.gender, user.orders.size)})

        return users
    }

    //сохранение изменений в базу данных
    //преобразуем список в последовательньсть и сохраняем
    fun save(users : List<CUserFX>)
    {
        val seq = users.asSequence().map {user -> CUser(user.id, user.login, user.name, user.gender, user.dateOfBirth, daoUsers.getUserOrders(user.id))}
        val existingUsers = seq.filter {it.id != null}.toList()
        daoUsers.updateList(existingUsers)
        getAll()
    }

    fun delete(userfx : CUserFX)
    {
        //нужно удалить те заказы, у которых в овнере стоит айди юзера, которого надо удлаить
        val cuser = CUser(userfx.id, userfx.login, userfx.name, userfx.gender, userfx.dateOfBirth, daoUsers.getUserOrders(userfx.id));
        for (order in cuser.getOrders()) {
            daoOrders.delete(order)
        }
        daoUsers.delete(cuser)
        users.remove(userfx)
    }
}