package com.tsystems.javaschool.ecare.dao;


import com.tsystems.javaschool.ecare.entities.Option;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Repository("optionDao")
public class OptionDAO implements IAbstractDAO<Option>
{

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Option saveOrUpdate(Option op)
    {
        return em.merge(op);
    }

    @Override
    public Option load(int id)
    {
        return em.find(Option.class, id);
    }

    public Option findOptionByTitle(String title)
    {
        Query query = em.createNamedQuery("Option.findOptionByTitle", Option.class);
        query.setParameter("title", title);
        //query.setParameter("id", id);
        return (Option) query.getSingleResult();
    }

    @Override
    public void delete(Option op)
    {
        em.remove(op);
    }

    @Override
    public Set<Option> getAll()
    {
        return new HashSet<>(em.createNamedQuery("Option.getAllOptions", Option.class).getResultList());
    }

    @Override
    public void deleteAll()
    {
        em.createNamedQuery("Option.deleteAllOptions").executeUpdate();
    }

    public Set<Option> getAllOptionsForTariff(long id)
    {
        Query query = em.createNamedQuery("Option.getAllOptionsForTariff", Option.class);
        //query.setParameter("id", id);
        return new HashSet<>(query.getResultList());
    }

    public void deleteAllOptionsForTariff(long id)
    {
        Query query = em.createNamedQuery("Option.deleteAllOptionsForTariff");
        //query.setParameter(1, id);
        query.executeUpdate();
    }

    @Override
    public long getCount()
    {
        return ((Number) em.createNamedQuery("Option.size").getSingleResult()).longValue();
    }
}
