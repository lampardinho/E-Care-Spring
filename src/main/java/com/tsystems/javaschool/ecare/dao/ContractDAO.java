package com.tsystems.javaschool.ecare.dao;


import com.tsystems.javaschool.ecare.entities.Contract;
import com.tsystems.javaschool.ecare.util.EntityManagerUtil;

import javax.persistence.Query;
import java.util.List;


public class ContractDAO implements IAbstractDAO<Contract>
{
    private static volatile ContractDAO instance;

    private ContractDAO()
    {
    }

    public static ContractDAO getInstance()
    {
        ContractDAO localInstance = instance;
        if (localInstance == null)
        {
            synchronized (ContractDAO.class)
            {
                localInstance = instance;
                if (localInstance == null)
                {
                    instance = localInstance = new ContractDAO();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Contract saveOrUpdate(Contract cn)
    {
        return EntityManagerUtil.getEntityManager().merge(cn);
    }

    @Override
    public Contract load(int id)
    {
        return EntityManagerUtil.getEntityManager().find(Contract.class, id);
    }

    public Contract findContractByNumber(int number)
    {
        Query query = EntityManagerUtil.getEntityManager().createNamedQuery("Contract.findContractByNumber", Contract.class);
        query.setParameter("number", number);
        return (Contract) query.getSingleResult();
    }

    @Override
    public void delete(Contract cn)
    {
        EntityManagerUtil.getEntityManager().remove(cn);
    }

    @Override
    public List<Contract> getAll()
    {
        return EntityManagerUtil.getEntityManager().createNamedQuery("Contract.getAllContracts", Contract.class).getResultList();
    }

    public List<Contract> getAllContractsForClient(int id)
    {
        Query query = EntityManagerUtil.getEntityManager().createNamedQuery("Contract.getAllContractsForClient", Contract.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public void deleteAll()
    {
        EntityManagerUtil.getEntityManager().createNamedQuery("Contract.deleteAllContracts").executeUpdate();
    }

    public void deleteAllContractsForClient(long id)
    {
        Query query = EntityManagerUtil.getEntityManager().createNamedQuery("Contract.deleteAllContractsForClient");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    @Override
    public long getCount()
    {
        return ((Number) EntityManagerUtil.getEntityManager().createNamedQuery("Contract.size").getSingleResult()).longValue();
    }
}
