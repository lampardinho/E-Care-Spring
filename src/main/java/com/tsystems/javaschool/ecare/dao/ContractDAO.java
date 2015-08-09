package com.tsystems.javaschool.ecare.dao;


import com.tsystems.javaschool.ecare.entities.Contract;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Repository("contractDao")
public class ContractDAO implements IAbstractDAO<Contract>
{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Contract saveOrUpdate(Contract cn)
    {
        return em.merge(cn);
    }

    @Override
    public Contract load(int id)
    {
        return em.find(Contract.class, id);
    }

    public Contract findContractByNumber(int number)
    {
        Query query = em.createNamedQuery("Contract.findContractByNumber", Contract.class);
        query.setParameter("number", number);
        return (Contract) query.getSingleResult();
    }

    @Override
    public void delete(Contract cn)
    {
        em.remove(cn);
    }

    @Override
    public Set<Contract> getAll()
    {
        return new HashSet<>(em.createNamedQuery("Contract.getAllContracts", Contract.class).getResultList());
    }

    public Set<Contract> getAllContractsForClient(int id)
    {
        Query query = em.createNamedQuery("Contract.getAllContractsForClient", Contract.class);
        query.setParameter("id", id);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public void deleteAll()
    {
        em.createNamedQuery("Contract.deleteAllContracts").executeUpdate();
    }

    public void deleteAllContractsForClient(long id)
    {
        Query query = em.createNamedQuery("Contract.deleteAllContractsForClient");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    @Override
    public long getCount()
    {
        return ((Number) em.createNamedQuery("Contract.size").getSingleResult()).longValue();
    }
}
