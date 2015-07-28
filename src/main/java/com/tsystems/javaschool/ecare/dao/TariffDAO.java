package com.tsystems.javaschool.ecare.dao;


import com.tsystems.javaschool.ecare.entities.Tariff;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("tariffDao")
public class TariffDAO implements IAbstractDAO<Tariff>
{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Tariff saveOrUpdate(Tariff tr)
    {
        return em.merge(tr);
    }

    @Override
    public Tariff load(int id)
    {
        return em.find(Tariff.class, id);
    }

    @Override
    public void delete(Tariff tr)
    {
        em.remove(tr);
    }

    @Override
    public List<Tariff> getAll()
    {
        return em.createNamedQuery("Tariff.getAllTariffs", Tariff.class).getResultList();
    }

    @Override
    public void deleteAll()
    {
        em.createNamedQuery("Tariff.deleteAllTariffs").executeUpdate();
    }

    @Override
    public long getCount()
    {
        return ((Number) em.createNamedQuery("Tariff.size").getSingleResult()).longValue();
    }
}
