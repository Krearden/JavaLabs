package com.kirillz.repositories

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CTestIRepositoryProducts {
    @Autowired
    private lateinit var repositoryProducts    : IRepositoryProducts

    @Test
    fun getCUserByID()
    {
        val cProduct_bicycle = repositoryProducts.getById(UUID.fromString("c5ae6430-e706-4128-91d4-84dd164f9d57"))
        assertNotNull(cProduct_bicycle, "c5ae6430-e706-4128-91d4-84dd164f9d57 Не найден товар по идентификатору c5ae6430-e706-4128-91d4-84dd164f9d57")
        assertEquals("Велосипед", cProduct_bicycle.product_name, "c5ae6430-e706-4128-91d4-84dd164f9d57 Наименования товара с идентификатором c5ae6430-e706-4128-91d4-84dd164f9d57 не соответствует ожидаемому результату")
    }
}