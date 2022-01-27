package com.kirillz.rest

import com.kirillz.model.CProduct
import com.kirillz.model.CUser
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
        repositoryProducts.delete(cproduct)
    }

}