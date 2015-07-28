package com.tsystems.javaschool.ecare.dao;


import com.tsystems.javaschool.ecare.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository("userDao")
public class UserDAO implements IAbstractDAO<User>
{

    @PersistenceContext
    private EntityManager em;

    @Override
    public User saveOrUpdate(User cl)
    {
        return em.merge(cl);
    }

    @Override
    public User load(int id)
    {
        return em.find(User.class, id);
    }

    public User findUserByLoginAndPassword(String login, String password)
    {
        Query query = em.createNamedQuery("User.findUserByLoginAndPassword", User.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        return (User) query.getSingleResult();
    }

    public User findUserByNumber(int number)
    {
        Query query = em.createNamedQuery("User.findUserByPhoneNumber", User.class);
        query.setParameter("number", number);
        return (User) query.getSingleResult();
    }

    @Override
    public void delete(User cl)
    {
        em.remove(cl);
    }

    @Override
    public List<User> getAll()
    {
        return em.createNamedQuery("User.getAllUsers", User.class).getResultList();
    }

    @Override
    public void deleteAll()
    {
        em.createNamedQuery("Client.deleteAllClients").executeUpdate();
    }

    @Override
    public long getCount()
    {
        return ((Number) em.createNamedQuery("Client.size").getSingleResult()).longValue();
    }

    public User findUserByLogin(String login)
    {
        Query query = em.createNamedQuery("Client.findClientByLogin", User.class);
        query.setParameter("login", login);
        return (User) query.getSingleResult();
    }
}
