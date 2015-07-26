package com.tsystems.javaschool.ecare.dao;


import com.tsystems.javaschool.ecare.entities.Tariff;
import com.tsystems.javaschool.ecare.util.EntityManagerUtil;

import java.util.List;


public class TariffDAO implements IAbstractDAO<Tariff>
{
    private static volatile TariffDAO instance;

    private TariffDAO()
    {
    }

    public static TariffDAO getInstance()
    {
        TariffDAO localInstance = instance;
        if (localInstance == null)
        {
            synchronized (TariffDAO.class)
            {
                localInstance = instance;
                if (localInstance == null)
                {
                    instance = localInstance = new TariffDAO();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Tariff saveOrUpdate(Tariff tr)
    {
        return EntityManagerUtil.getEntityManager().merge(tr);
    }

    @Override
    public Tariff load(int id)
    {
        return EntityManagerUtil.getEntityManager().find(Tariff.class, id);
    }

    @Override
    public void delete(Tariff tr)
    {
        EntityManagerUtil.getEntityManager().remove(tr);
    }

    @Override
    public List<Tariff> getAll()
    {
        return EntityManagerUtil.getEntityManager().createNamedQuery("Tariff.getAllTariffs", Tariff.class).getResultList();
    }

    @Override
    public void deleteAll()
    {
        EntityManagerUtil.getEntityManager().createNamedQuery("Tariff.deleteAllTariffs").executeUpdate();
    }

    @Override
    public long getCount()
    {
        return ((Number) EntityManagerUtil.getEntityManager().createNamedQuery("Tariff.size").getSingleResult()).longValue();
    }
}
