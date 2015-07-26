package com.tsystems.javaschool.ecare.services;

import com.tsystems.javaschool.ecare.dao.ContractDAO;
import com.tsystems.javaschool.ecare.dao.IAbstractDAO;
import com.tsystems.javaschool.ecare.entities.Contract;
import com.tsystems.javaschool.ecare.entities.User;
import com.tsystems.javaschool.ecare.util.AppException;
import com.tsystems.javaschool.ecare.util.EntityManagerUtil;
import org.apache.log4j.Logger;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * This class is the implementation of IContractService for working with contract DAO
 * and contract entities. Class ContractService is a singleton.
 */

public class ContractService
{

    /*Instance of the singleton class*/
    private static volatile ContractService instance;
    /*Logger for contract service operations*/
    private static Logger logger = Logger.getLogger(ContractService.getInstance().getClass());
    /*SQL contract implementations of abstract DAO class*/
    private IAbstractDAO<Contract> DAO = ContractDAO.getInstance();
    private ContractDAO cnDAO = ContractDAO.getInstance();
    /*Client service instance for some methods of working with client amount in contract service*/
    private UserService userService = UserService.getInstance();

    /*Private constructor of singleton class*/
    private ContractService()
    {
    }

    /**
     * This method return instance of singleton class ContractService.
     *
     * @return instance of class.
     */
    public static ContractService getInstance()
    {
        ContractService localInstance = instance;
        if (localInstance == null)
        {
            synchronized (ContractService.class)
            {
                localInstance = instance;
                if (localInstance == null)
                {
                    instance = localInstance = new ContractService();
                }
            }
        }
        return localInstance;
    }

    /**
     * Method implements saving or updating of contracts in the database.
     *
     * @param cn contract entity to be saved or updated.
     * @return saved or updated contract entity.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during saving or updating of entity
     *                        and DAO returns null.
     */
    public Contract saveOrUpdateContract(Contract cn) throws AppException
    {
        logger.info("Save/update contract " + cn + " in DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            Contract contract = DAO.saveOrUpdate(cn);
            EntityManagerUtil.commit();
            //If DAO returns null method will throws an ECareException
            if (contract == null)
            {
                AppException ecx = new AppException("Failed to save/update contract " + cn + " in DB.");
                logger.error(ecx.getMessage(), ecx);
                throw ecx;
            }
            logger.info("Contract " + contract + " saved/updated in DB.");
            //else contract will be saved and method returns contract entity
            return contract;
        } catch (RuntimeException re)
        {
            if (EntityManagerUtil.getEntityManager() != null && EntityManagerUtil.getEntityManager().isOpen())
                EntityManagerUtil.rollback();
            throw re;
        } finally
        {
            EntityManagerUtil.closeEntityManager();
        }
    }

    /**
     * This method implements loading of contracts from the database.
     *
     * @param id contract id for search that contract in the database.
     * @return loaded contract entity.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during loading of entity
     *                        and DAO returns null.
     */
    public Contract loadContract(int id) throws AppException
    {
        logger.info("Load contract with id: " + id + " from DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            Contract cn = DAO.load(id);
            EntityManagerUtil.commit();
            //If DAO returns null method will throws an ECareException
            if (cn == null)
            {
                AppException ecx = new AppException("Contract with id = " + id + " not found in DB.");
                logger.warn(ecx.getMessage(), ecx);
                throw ecx;
            }
            logger.info("Contract " + cn + " loaded from DB.");
            //else method returns contract entity
            return cn;
        } catch (RuntimeException re)
        {
            if (EntityManagerUtil.getEntityManager() != null && EntityManagerUtil.getEntityManager().isOpen())
                EntityManagerUtil.rollback();
            throw re;
        } finally
        {
            EntityManagerUtil.closeEntityManager();
        }
    }

    /**
     * This method implements finding of contracts by telephone number in
     * the database.
     *
     * @param number contract number for search that contract in the database.
     * @return found contract entity.
     * @throws com.tsystems.javaschool.ecare.util.AppException if DAO returns NoResultException during finding of contract
     *                        in the database.
     */
    public Contract getContractByPhoneNumber(int number) throws AppException
    {
        logger.info("Find contract by telephone number: " + number + " in DB.");
        Contract cn = null;
        try
        {
            EntityManagerUtil.beginTransaction();
            try
            {
                // Search of contract in the database by DAO method.
                cn = cnDAO.findContractByNumber(number);
                // If contract does not exist in database, block try catches the NoResultException and
                // throws an ECareException.
            } catch (NoResultException nrx)
            {
                AppException ecx = new AppException("Contract with number: " + number + " not found.", nrx);
                logger.warn(ecx.getMessage(), nrx);
                throw ecx;
            }
            EntityManagerUtil.commit();
            logger.info("Contract " + cn + " found and loaded from DB.");
            return cn;
        } catch (RuntimeException re)
        {
            if (EntityManagerUtil.getEntityManager() != null && EntityManagerUtil.getEntityManager().isOpen())
                EntityManagerUtil.rollback();
            throw re;
        } finally
        {
            EntityManagerUtil.closeEntityManager();
        }
    }

    /**
     * This method implements deleting of contract from the database.
     *
     * @param id contract id for deleting that contract from the database.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during intermediate loading
     *                        of entity and DAO returns null.
     */
    public void deleteContract(int id) throws AppException
    {
        logger.info("Delete contract with id: " + id + " from DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            Contract cn = DAO.load(id);
            //If DAO returns null method will throws an ECareException.
            if (cn == null)
            {
                AppException ecx = new AppException("Contract with id = " + id + " not exist.");
                logger.warn(ecx.getMessage(), ecx);
                throw ecx;
            }
            // Else contract will be deleted from the database.
            DAO.delete(cn);
            EntityManagerUtil.commit();
            logger.info("Contract " + cn + " deleted from DB.");
        } catch (RuntimeException re)
        {
            if (EntityManagerUtil.getEntityManager() != null && EntityManagerUtil.getEntityManager().isOpen())
                EntityManagerUtil.rollback();
            throw re;
        } finally
        {
            EntityManagerUtil.closeEntityManager();
        }
    }

    /**
     * This method implements receiving of all contracts from the database.
     *
     * @return list of received contracts.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during receiving of entities
     *                        and DAO returns null.
     */
    public List<Contract> getAllContracts() throws AppException
    {
        logger.info("Get all contracts from DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            List<Contract> contracts = DAO.getAll();
            EntityManagerUtil.commit();
            //If DAO returns null method will throws an ECareException
            if (contracts == null)
            {
                AppException ecx = new AppException("Failed to get all contracts from DB.");
                logger.error(ecx.getMessage(), ecx);
                throw ecx;
            }
            logger.info("All contracts obtained from DB.");
            // Else method returns list of contract entities.
            return contracts;
        } catch (RuntimeException re)
        {
            if (EntityManagerUtil.getEntityManager() != null && EntityManagerUtil.getEntityManager().isOpen())
                EntityManagerUtil.rollback();
            throw re;
        } finally
        {
            EntityManagerUtil.closeEntityManager();
        }
    }

    /**
     * This method implements receiving of all contracts for client from the database.
     *
     * @param user client id for searching of all contracts for this client.
     * @return list of received contracts.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during receiving of entities
     *                        and DAO returns null.
     */
    public List<Contract> getUserContracts(User user) throws AppException
    {
        logger.info("Get all contracts from DB for client with id: " + user.getUserId() + ".");
        try
        {
            EntityManagerUtil.beginTransaction();
            List<Contract> contracts = cnDAO.getAllContractsForClient(user.getUserId());
            EntityManagerUtil.commit();
            //If DAO returns null method will throws an ECareException
            if (contracts == null)
            {
                AppException ecx = new AppException("Failed to get all contracts from DB.");
                logger.error(ecx.getMessage(), ecx);
                throw ecx;
            }
            logger.info("All contracts for client id: " + user.getUserId() + " obtained from DB.");
            // Else method returns list of contract entities
            return contracts;
        } catch (RuntimeException re)
        {
            if (EntityManagerUtil.getEntityManager() != null && EntityManagerUtil.getEntityManager().isOpen())
                EntityManagerUtil.rollback();
            throw re;
        } finally
        {
            EntityManagerUtil.closeEntityManager();
        }
    }

    /**
     * This method implements deleting of all contracts from the database.
     */
    public void deleteAllContracts()
    {
        logger.info("Delete all contracts from DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            DAO.deleteAll();
            EntityManagerUtil.commit();
            logger.info("All contracts deleted from DB.");
        } catch (RuntimeException re)
        {
            if (EntityManagerUtil.getEntityManager() != null && EntityManagerUtil.getEntityManager().isOpen())
                EntityManagerUtil.rollback();
            throw re;
        } finally
        {
            EntityManagerUtil.closeEntityManager();
        }
    }

    /**
     * This method implements deleting of all contracts for client from the database.
     *
     * @param id client id for deleting of all contracts for this client
     */
    public void deleteAllContractsForClient(long id)
    {
        logger.info("Delete all contracts from DB for client with id: " + id + ".");
        try
        {
            EntityManagerUtil.beginTransaction();
            cnDAO.deleteAllContractsForClient(id);
            EntityManagerUtil.commit();
            logger.info("All contracts for client id: " + id + " deleted from DB.");
        } catch (RuntimeException re)
        {
            if (EntityManagerUtil.getEntityManager() != null && EntityManagerUtil.getEntityManager().isOpen())
                EntityManagerUtil.rollback();
            throw re;
        } finally
        {
            EntityManagerUtil.closeEntityManager();
        }
    }

    /**
     * This method implements receiving number of all contracts from the database.
     *
     * @return number of contracts in the database.
     */
    public long getNumberOfContracts()
    {
        logger.info("Get number of contracts in DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            long number = DAO.getCount();
            EntityManagerUtil.commit();
            logger.info(number + "of contracts obtained from DB.");
            return number;
        } catch (RuntimeException re)
        {
            if (EntityManagerUtil.getEntityManager() != null && EntityManagerUtil.getEntityManager().isOpen())
                EntityManagerUtil.rollback();
            throw re;
        } finally
        {
            EntityManagerUtil.closeEntityManager();
        }
    }


}
