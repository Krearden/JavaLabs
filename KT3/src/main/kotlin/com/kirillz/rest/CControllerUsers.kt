package com.kirillz.rest

import com.kirillz.model.CUser
import com.kirillz.repositories.IRepositoryUsers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.annotation.PostConstruct

@RestController
@RequestMapping("users")
class CControllerUsers {
    @Autowired
    private lateinit var repositoryUsers    : IRepositoryUsers

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
        repositoryUsers.delete(cuser)
    }
}