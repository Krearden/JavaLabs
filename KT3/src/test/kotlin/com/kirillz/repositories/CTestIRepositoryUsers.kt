package com.kirillz.repositories

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*

import org.junit.jupiter.api.Assertions.*


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CTestIRepositoryUsers {
    @Autowired
    private lateinit var repositoryUsers    : IRepositoryUsers

    @Test
    fun getCUserByID()
    {
        val cuser = repositoryUsers.getById(UUID.fromString("927c2824-a538-4386-8912-f1eedea71616"))
        assertNotNull(cuser, "Тест IRepositoryUsers: getByID(). Не найден пользователь по идентификатору 927c2824-a538-4386-8912-f1eedea71616")
        assertEquals("Andrey", cuser.login, "Логин пользователя с идентификатором 927c2824-a538-4386-8912-f1eedea71616 не соответствует ожидаемому результату")
    }
}