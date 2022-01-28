package com.kirillz.repositories

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CTestIRepositoryOrders {
    @Autowired
    private lateinit var repositoryOrders   : IRepositoryOrders

    @Test
    fun getCUserByID()
    {
        val cOrder = repositoryOrders.getById(UUID.fromString("5e5d8191-45bf-4bd8-b24d-1e42d32fdb07"))
        assertNotNull(cOrder, "Тест IRepositoryOrders: getById(). 5e5d8191-45bf-4bd8-b24d-1e42d32fdb07 Не найден товар по идентификатору 5e5d8191-45bf-4bd8-b24d-1e42d32fdb07")
    }
}