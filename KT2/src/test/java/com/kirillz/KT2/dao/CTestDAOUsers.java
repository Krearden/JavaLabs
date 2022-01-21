package com.kirillz.KT2.dao;

import com.kirillz.KT2.config.CHibernateConfig;
import com.kirillz.KT2.model.CUser;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CTestDAOUsers {

    @Test
    void TEST_getUserById() {
        CDAOUsers daoUsers = new CDAOUsers(CHibernateConfig.getSessionFactory());
        CUser cUser_Alexander = daoUsers.get(UUID.fromString("6720a44c-af02-41e9-9d19-c4bb1c38c9a9"));
        assertNotNull(cUser_Alexander, "Тест daoUsers.get(). Не найден пользователь по идентификатору 6720a44c-af02-41e9-9d19-c4bb1c38c9a9");
        assertEquals("Alexander", cUser_Alexander.getLogin(), "Тест daoUsers.get(). Логин пользователя с идентификатором 6720a44c-af02-41e9-9d19-c4bb1c38c9a9 не соответствует ожидаемому результату");
    }
}