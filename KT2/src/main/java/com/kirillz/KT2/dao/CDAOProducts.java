package com.kirillz.KT2.dao;

import com.kirillz.KT2.config.CHibernateConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.kirillz.KT2.model.CProduct;
import com.kirillz.KT2.model.COrder;
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
        CProduct product = null;
        try(Session session = sessionFactory.openSession())
        {
            product = session.get(CProduct.class, id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return product;
    }
    @Override
    public List<CProduct> getAll(){
        List<CProduct> products;
        try(Session session = sessionFactory.openSession())
        {
            products = session.createQuery("from CProduct").list();
        }
        catch(Exception e)
        {
            products = new ArrayList<>();
            e.printStackTrace();
        }
        return products;
    }

    public List<COrder> getProductOrders(UUID product_id)
    {
        CProduct product = null;
        List<COrder> orders;
        try(Session session = sessionFactory.openSession())
        {
            product = session.get(CProduct.class, product_id);
            orders = product.getOrders();
        }
        catch(Exception e)
        {
            orders = new ArrayList<>();
            e.printStackTrace();
        }
        return orders;
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

    public void saveList(List<CProduct> products)
    {
        int objects_in_one_transaction = 1000;
        try(Session session = sessionFactory.openSession())
        {
            for (int i = 0; i < products.size(); i++) {
                session.beginTransaction();
                for (int j = 0; j < objects_in_one_transaction && i < products.size(); j++, i++)
                    session.save(products.get(i));
                session.getTransaction().commit();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateList(List<CProduct> products)
    {
        int objects_in_one_transaction = 1000;
        try(Session session = sessionFactory.openSession())
        {
            for (int i = 0; i < products.size(); i++) {
                session.beginTransaction();
                for (int j = 0; j < objects_in_one_transaction && i < products.size(); j++, i++)
                    session.update(products.get(i));
                session.getTransaction().commit();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CProduct product)
    {
        try(Session session = sessionFactory.openSession())
        {
            session.beginTransaction();
            session.update(product);
            session.getTransaction().commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(CProduct product)
    {
        try(Session session = sessionFactory.openSession())
        {
            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteProduct(CProduct product)
    {
        CDAOOrders daoOrders = new CDAOOrders(CHibernateConfig.getSessionFactory());
        CDAOProducts daoProducts = new CDAOProducts(CHibernateConfig.getSessionFactory());
        List<COrder> products_orders = product.getOrders();
        for (COrder order : products_orders)
        {
            for (int i = 0; i < order.getProducts().size(); i++)
            {
                if (order.getProducts().get(i).equals(product))
                {
                    order.getProducts().remove(i);
                }
            }
        }
        daoOrders.updateList(products_orders);
        daoProducts.delete(product);
    }

}
