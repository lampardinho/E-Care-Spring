package com.tsystems.javaschool.ecare.dao;


import com.tsystems.javaschool.ecare.entities.User;
import com.tsystems.javaschool.ecare.util.EntityManagerUtil;

import javax.persistence.Query;
import java.util.List;


public class UserDAO implements IAbstractDAO<User>
{
    private static volatile UserDAO instance;

    private UserDAO()
    {
    }

    public static UserDAO getInstance()
    {
        UserDAO localInstance = instance;
        if (localInstance == null)
        {
            synchronized (UserDAO.class)
            {
                localInstance = instance;
                if (localInstance == null)
                {
                    instance = localInstance = new UserDAO();
                }
            }
        }
        return localInstance;
    }

    @Override
    public User saveOrUpdate(User cl)
    {
        return EntityManagerUtil.getEntityManager().merge(cl);
    }

    @Override
    public User load(int id)
    {
        return EntityManagerUtil.getEntityManager().find(User.class, id);
    }

    public User findUserByLoginAndPassword(String login, String password)
    {
        Query query = EntityManagerUtil.getEntityManager().createNamedQuery("User.findUserByLoginAndPassword", User.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        return (User) query.getSingleResult();
    }

    public User findUserByNumber(int number)
    {
        Query query = EntityManagerUtil.getEntityManager().createNamedQuery("User.findUserByPhoneNumber", User.class);
        query.setParameter("number", number);
        return (User) query.getSingleResult();
    }

    @Override
    public void delete(User cl)
    {
        EntityManagerUtil.getEntityManager().remove(cl);
    }

    @Override
    public List<User> getAll()
    {
        return EntityManagerUtil.getEntityManager().createNamedQuery("User.getAllUsers", User.class).getResultList();
    }

    @Override
    public void deleteAll()
    {
        EntityManagerUtil.getEntityManager().createNamedQuery("Client.deleteAllClients").executeUpdate();
    }

    @Override
    public long getCount()
    {
        return ((Number) EntityManagerUtil.getEntityManager().createNamedQuery("Client.size").getSingleResult()).longValue();
    }

    public User findUserByLogin(String login)
    {
        Query query = EntityManagerUtil.getEntityManager().createNamedQuery("Client.findClientByLogin", User.class);
        query.setParameter("login", login);
        return (User) query.getSingleResult();
    }
}
