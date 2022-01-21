package com.kirillz.KT2.dao;

import com.kirillz.KT2.config.CHibernateConfig;
import com.kirillz.KT2.model.CProduct;
import com.kirillz.KT2.model.CUser;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CTestDAOProducts {

    @Test
    void TEST_getProductByID() {
        CDAOProducts  daoProducts = new CDAOProducts(CHibernateConfig.getSessionFactory());
        CProduct cProduct_bicycle = daoProducts.get(UUID.fromString("c5ae6430-e706-4128-91d4-84dd164f9d57"));
        assertNotNull(cProduct_bicycle, "Тест daoProducts.get(). Не найден товар по идентификатору c5ae6430-e706-4128-91d4-84dd164f9d57");
        assertEquals("Велосипед", cProduct_bicycle.getProduct_name(), "Тест daoProducts.get(). Наименования товара с идентификатором c5ae6430-e706-4128-91d4-84dd164f9d57 не соответствует ожидаемому результату");
    }
}