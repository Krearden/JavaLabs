package com.kirillz.KT2.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.kirillz.KT2.model.CProduct;
import com.kirillz.KT2.model.CUser;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CDAOProducts implements IDAO<CProduct>{
    private SessionFactory sessionFactory;
    public CDAOProducts(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CProduct get(UUID id)
    {
        CProduct user = null;
        try(Session session = sessionFactory.openSession())
        {
            user = session.get(CProduct.class, id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return user;
    }
    @Override
    public List<CProduct> getAll(){
        List<CProduct> users;
        try(Session session = sessionFactory.openSession())
        {
            users = session.createQuery("from CProduct").list();
        }
        catch(Exception e)
        {
            users = new ArrayList<>();
            e.printStackTrace();
        }
        return users;
    }

    public List<CProduct> getAllByUser(CUser user){
        List<CProduct> products;
        try(Session session = sessionFactory.openSession())
        {
            Query<CProduct> q = session.createQuery("Select p from CProduct p JOIN p.orders o WHERE o.owner=:user");
            q.setParameter("user", user);
            products = q.list();
        }
        catch(Exception e)
        {
            products = new ArrayList<>();
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void save(CProduct user)
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
    public void update(CProduct user)
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
    public void delete(CProduct user)
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
