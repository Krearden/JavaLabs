package com.kirillz.KT2.dao;

import com.kirillz.KT2.config.CHibernateConfig;
import com.kirillz.KT2.model.COrder;
import com.kirillz.KT2.model.CProduct;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CTestDAOOrders {

    @Test
    void TEST_getOrderByID() {
        CDAOOrders  daoOrders = new CDAOOrders(CHibernateConfig.getSessionFactory());
        COrder cOrder = daoOrders.get(UUID.fromString("adae0ca0-5967-4ab7-8436-31c64cb9dc68"));
        assertNotNull(cOrder, "Тест daoOrders.get(). Не найден заказ по идентификатору adae0ca0-5967-4ab7-8436-31c64cb9dc68");
        UUID expected_id = UUID.fromString("1dcc17de-1e64-4d8e-816c-5788804835fc");
        assertEquals(expected_id, cOrder.getOwner().getId(), "Тест daoOrders.get(). Идентификатор владельца заказа с идентификатором adae0ca0-5967-4ab7-8436-31c64cb9dc68 не соответсвует ожидаемому результату");
    }
}