package com.kirillz.rest

import dbloader.CDatabaseLoader
import com.kirillz.model.CUser
import com.kirillz.repositories.IRepositoryOrders
import com.kirillz.repositories.IRepositoryUsers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("users")
class CControllerUsers {
    @Autowired
    private lateinit var repositoryUsers    : IRepositoryUsers
    //подключаем репозиторий заказов для чистки связей в БД перед удалением пользователя
    @Autowired
    private lateinit var repositoryOrders    : IRepositoryOrders

    @GetMapping("")
    fun getAll()                            : List<CUser>
    {
        return repositoryUsers.findAll()
    }

    @GetMapping(params = ["id"])
    fun getById(@RequestParam id : UUID) : CUser?
    {
        return repositoryUsers.findByIdOrNull(id)
    }

    @PostMapping
    fun saveUser(@RequestBody cuser : CUser)
    {
        repositoryUsers.save(cuser)
    }

    @DeleteMapping
    fun deleteUser(@RequestBody cuser : CUser)
    {
        val ord = repositoryOrders.findAll();
        if (ord.size != 0)
        {
            for (order in ord) {
                if (order.owner.id == cuser.id)
                    repositoryOrders.delete(order)
            }
        }
        repositoryUsers.delete(cuser)
    }
}