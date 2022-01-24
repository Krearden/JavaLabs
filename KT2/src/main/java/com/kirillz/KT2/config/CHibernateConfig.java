package com.kirillz.KT2.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import com.kirillz.KT2.model.CUser;
import com.kirillz.KT2.model.CProduct;
import com.kirillz.KT2.model.COrder;

import java.util.Properties;

public class CHibernateConfig {
    private static SessionFactory sessionFactory;

    private CHibernateConfig() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();//конфигурация

                // Настройки Hibernate
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                //ссылка на базу данных
                settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/KT2_Database");
                //логин и пароль специально настроенного для работы приложения пользователя БД
                settings.put(Environment.USER, "KT2_User");
                settings.put(Environment.PASS, "kt2pass");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
                //параметр отображения в консоли запросов
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                //сохраняются настройки
                configuration.setProperties(settings);
                //добавляются классы, работа с которыми будет осуществляться в БД
                configuration.addAnnotatedClass(CUser.class);
                configuration.addAnnotatedClass(COrder.class);
                configuration.addAnnotatedClass(CProduct.class);
                //sessionFactory - фабрика для подключений к БД
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    public static void shutdown() {
        getSessionFactory().close();
    }
}
