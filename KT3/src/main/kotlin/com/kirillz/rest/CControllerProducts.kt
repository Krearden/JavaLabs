package com.kirillz.rest

import com.kirillz.model.CProduct
import com.kirillz.repositories.IRepositoryOrders
import com.kirillz.repositories.IRepositoryProducts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("products")
class CControllerProducts {
    @Autowired
    private lateinit var repositoryProducts    : IRepositoryProducts
    //подключаем репозиторий заказов для чистки связей в БД перед удалением товара
    @Autowired
    private lateinit var repositoryOrders    : IRepositoryOrders

    @GetMapping("")
    fun getAll()                            : List<CProduct>
    {
        return repositoryProducts.findAll()
    }

    @GetMapping(params = ["id"])
    fun getById(@RequestParam id : UUID) : CProduct?
    {
        return repositoryProducts.findByIdOrNull(id)
    }

    @PostMapping
    fun saveProduct(@RequestBody cproduct : CProduct)
    {
        repositoryProducts.save(cproduct)
    }

    @DeleteMapping
    fun deleteProduct(@RequestBody cproduct : CProduct)
    {
        val cproduct = repositoryProducts.getById(cproduct.id)
        for (order in cproduct.orders)
        {
            val iterator = order.products.iterator()
            while (iterator.hasNext())
            {
                val pr = iterator.next()
                if (pr.id == cproduct.id)
                {
                    iterator.remove()
                }
            }
            repositoryOrders.save(order)
        }
        repositoryProducts.delete(cproduct)
    }

}