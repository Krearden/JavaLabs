package com.kirillz.rest

import com.kirillz.model.COrder
import com.kirillz.repositories.IRepositoryOrders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("orders")
class CControllerOrders {
    @Autowired
    private lateinit var repositoryOrders    : IRepositoryOrders

    @GetMapping("")
    fun getAll()                            : List<COrder>
    {
        return repositoryOrders.findAll()
    }

    @GetMapping(params = ["id"])
    fun getById(@RequestParam id : UUID) : COrder?
    {
        return repositoryOrders.findByIdOrNull(id)
    }

    @PostMapping
    fun saveOrder(@RequestBody corder : COrder)
    {
        repositoryOrders.save(corder)
    }

    @DeleteMapping
    fun deleteOrder(@RequestParam id : UUID)
    {
        repositoryOrders.deleteById(id)
    }
}