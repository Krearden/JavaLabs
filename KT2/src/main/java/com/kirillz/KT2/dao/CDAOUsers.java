package com.kirillz.KT2.dao;

import com.kirillz.KT2.model.COrder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.kirillz.KT2.model.CUser;

public class CDAOUsers implements IDAO<CUser> {
    private SessionFactory sessionFactory;
    public CDAOUsers(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CUser get(UUID id)
    {
        CUser user = null;
        try(Session session = sessionFactory.openSession())
        {
            user = session.get(CUser.class, id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return user;
    }

    public List<COrder> getUserOrders(UUID id)
    {
        CUser user = null;
        List<COrder> orders_to_return = new ArrayList<>();;
        try(Session session = sessionFactory.openSession())
        {
            user = session.get(CUser.class, id);
            orders_to_return = user.getOrders();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return orders_to_return;
    }

    @Override
    public List<CUser> getAll(){
        List<CUser> users;
        try(Session session = sessionFactory.openSession())
        {
            users = session.createQuery("from CUser").list();
        }
        catch(Exception e)
        {
            users = new ArrayList<>();
            e.printStackTrace();
        }
        return users;
    }
    @Override
    public void save(CUser user)
    {
        try(Session session = sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //сохранение списка пользователей в базу данных с учетом оптимального количества объектов класса в одной транзакции
    //в нашем случае из-за небольшого количества данных не имеет особого занчения
    public void saveList(List<CUser> users)
    {
        int objects_in_one_transaction = 1000;
        try(Session session = sessionFactory.openSession())
        {
            for (int i = 0; i < users.size(); i++) {
                session.beginTransaction();
                for (int j = 0; j < objects_in_one_transaction && i < users.size(); j++, i++)
                    session.save(users.get(i));
                session.getTransaction().commit();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateList(List<CUser> users)
    {
        int objects_in_one_transaction = 1000;
        try(Session session = sessionFactory.openSession())
        {
            for (int i = 0; i < users.size(); i++) {
                session.beginTransaction();
                for (int j = 0; j < objects_in_one_transaction && i < users.size(); j++, i++)
                    session.update(users.get(i));
                session.getTransaction().commit();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CUser user)
    {
        try(Session session = sessionFactory.openSession())
        {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(CUser user)
    {
        //создается сессия
        try(Session session = sessionFactory.openSession())
        {
            //начинается транзакция
            session.beginTransaction();
            //выполняется удаление
            session.delete(user);
            //применение изменений
            session.getTransaction().commit();
        }
        catch(Exception e)
        {
            //ошибка, если не удалось открыть сессию
            e.printStackTrace();
        }
    }
}
