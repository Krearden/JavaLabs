package com.kirillz.KT2.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
        try(Session session = sessionFactory.openSession())
        {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
