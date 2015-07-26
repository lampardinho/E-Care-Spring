package com.tsystems.javaschool.ecare.dao;


import com.tsystems.javaschool.ecare.entities.Option;
import com.tsystems.javaschool.ecare.util.EntityManagerUtil;

import javax.persistence.Query;
import java.util.List;


public class OptionDAO implements IAbstractDAO<Option>
{
    private static volatile OptionDAO instance;

    private OptionDAO()
    {
    }

    public static OptionDAO getInstance()
    {
        OptionDAO localInstance = instance;
        if (localInstance == null)
        {
            synchronized (OptionDAO.class)
            {
                localInstance = instance;
                if (localInstance == null)
                {
                    instance = localInstance = new OptionDAO();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Option saveOrUpdate(Option op)
    {
        return EntityManagerUtil.getEntityManager().merge(op);
    }

    @Override
    public Option load(int id)
    {
        return EntityManagerUtil.getEntityManager().find(Option.class, id);
    }

    public Option findOptionByTitleAndTariffId(String title, long id)
    {
        Query query = EntityManagerUtil.getEntityManager().createNamedQuery("Option.findOptionByTitleAndTariffId", Option.class);
        query.setParameter("title", title);
        //query.setParameter("id", id);
        return (Option) query.getSingleResult();
    }

    @Override
    public void delete(Option op)
    {
        EntityManagerUtil.getEntityManager().remove(op);
    }

    @Override
    public List<Option> getAll()
    {
        return EntityManagerUtil.getEntityManager().createNamedQuery("Option.getAllOptions", Option.class).getResultList();
    }

    @Override
    public void deleteAll()
    {
        EntityManagerUtil.getEntityManager().createNamedQuery("Option.deleteAllOptions").executeUpdate();
    }

    public List<Option> getAllOptionsForTariff(long id)
    {
        Query query = EntityManagerUtil.getEntityManager().createNamedQuery("Option.getAllOptionsForTariff", Option.class);
        //query.setParameter("id", id);
        return query.getResultList();
    }

    public void deleteAllOptionsForTariff(long id)
    {
        Query query = EntityManagerUtil.getEntityManager().createNamedQuery("Option.deleteAllOptionsForTariff");
        //query.setParameter(1, id);
        query.executeUpdate();
    }

    @Override
    public long getCount()
    {
        return ((Number) EntityManagerUtil.getEntityManager().createNamedQuery("Option.size").getSingleResult()).longValue();
    }
}
