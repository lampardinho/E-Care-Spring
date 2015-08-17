package com.tsystems.javaschool.ecare.services;

import com.tsystems.javaschool.ecare.dao.ContractDAO;
import com.tsystems.javaschool.ecare.entities.Contract;
import com.tsystems.javaschool.ecare.entities.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Set;

/**
 * This class is the implementation of IContractService for working with contract DAO
 * and contract entities. Class ContractService is a singleton.
 */
@Service("contractService")
public class ContractService
{
    /*Logger for contract service operations*/
    private static Logger logger = Logger.getLogger(ContractService.class);
    /*Client service instance for some methods of working with client amount in contract service*/
    private final UserService clientService;

    /*Tariff service instance for some methods of working with tariff service*/
    private final TariffService tariffService;

    /*Option service instance for some methods of working with option service*/
    private final OptionService optionService;
    /*SQL contract implementations of DAO class*/
    private ContractDAO cnDao;

    /*Constructor of Contract Service class*/
    @Autowired
    public ContractService(ContractDAO cnDAO, UserService clientService, OptionService optionService, TariffService tariffService)
    {
        this.cnDao = cnDAO;
        this.clientService = clientService;
        this.optionService = optionService;
        this.tariffService = tariffService;
    }

    /**
     * Method implements saving or updating of contracts in the database.
     *
     * @param cn contract entity to be saved or updated.
     * @return saved or updated contract entity.
     */
    @Transactional
    public Contract saveOrUpdateContract(Contract cn)
    {
        logger.info("Save/update contract " + cn + " in DB.");

        Contract contract = cnDao.saveOrUpdate(cn);

        logger.info("Contract " + contract + " saved/updated in DB.");
        //else contract will be saved and method returns contract entity
        return contract;

    }

    /**
     * This method implements loading of contracts from the database.
     *
     * @param id contract id for search that contract in the database.
     * @return loaded contract entity.
     */
    @Transactional
    public Contract loadContract(int id)
    {
        logger.info("Load contract with id: " + id + " from DB.");
        Contract cn = cnDao.load(id);

        logger.info("Contract " + cn + " loaded from DB.");
        //else method returns contract entity
        return cn;
    }

    /**
     * This method implements finding of contracts by telephone number in
     * the database.
     *
     * @param number contract number for search that contract in the database.
     * @return found contract entity.
     */
    @Transactional
    public Contract getContractByPhoneNumber(int number)
    {
        logger.info("Find contract by telephone number: " + number + " in DB.");
        Contract cn = null;
        try
        {
            // Search of contract in the database by DAO method.
            cn = cnDao.findContractByNumber(number);
            // If contract does not exist in database, block try catches the NoResultException and
            // throws an ECareException.
        } catch (NoResultException nrx)
        {
            logger.warn(nrx.getMessage(), nrx);
            return null;
        }
        logger.info("Contract " + cn + " found and loaded from DB.");
        return cn;

    }

    /**
     * This method implements deleting of contract from the database.
     *
     * @param id contract id for deleting that contract from the database.
     */
    @Transactional
    public void deleteContract(int id)
    {
        logger.info("Delete contract with id: " + id + " from DB.");
        Contract cn = cnDao.load(id);
        //If DAO returns null method will throws an ECareException.
        if (cn == null)
        {
            logger.warn("Contract with id = " + id + " not exist.");
            return;
        }
        // Else contract will be deleted from the database.
        cnDao.delete(cn);

        logger.info("Contract " + cn + " deleted from DB.");

    }

    /**
     * This method implements receiving of all contracts from the database.
     *
     * @return list of received contracts.
     */
    @Transactional
    public Set<Contract> getAllContracts()
    {
        logger.info("Get all contracts from DB.");
        Set<Contract> contracts = cnDao.getAll();
        //If DAO returns null method will throws an ECareException
        if (contracts == null)
        {
            logger.error("Failed to get all contracts from DB.");

        }
        logger.info("All contracts obtained from DB.");
        // Else method returns list of contract entities.
        return contracts;

    }

    /**
     * This method implements receiving of all contracts for client from the database.
     *
     * @param user client id for searching of all contracts for this client.
     * @return list of received contracts.
     */
    @Transactional
    public Set<Contract> getUserContracts(User user)
    {
        logger.info("Get all contracts from DB for client with id: " + user.getUserId() + ".");
        Set<Contract> contracts = cnDao.getAllContractsForClient(user.getUserId());
        if (contracts == null)
        {
            logger.error("Failed to get all contracts from DB.");

        }
        logger.info("All contracts for client id: " + user.getUserId() + " obtained from DB.");
        // Else method returns list of contract entities
        return contracts;
    }

    /**
     * This method implements deleting of all contracts from the database.
     */
    @Transactional
    public void deleteAllContracts()
    {
        logger.info("Delete all contracts from DB.");
        cnDao.deleteAll();
        logger.info("All contracts deleted from DB.");

    }

    /**
     * This method implements deleting of all contracts for client from the database.
     *
     * @param id client id for deleting of all contracts for this client
     */
    @Transactional
    public void deleteAllContractsForClient(long id)
    {
        logger.info("Delete all contracts from DB for client with id: " + id + ".");
        cnDao.deleteAllContractsForClient(id);
        logger.info("All contracts for client id: " + id + " deleted from DB.");

    }

    /**
     * This method implements receiving number of all contracts from the database.
     *
     * @return number of contracts in the database.
     */
    @Transactional
    public long getNumberOfContracts()
    {
        logger.info("Get number of contracts in DB.");
        long number = cnDao.getCount();
        logger.info(number + "of contracts obtained from DB.");
        return number;

    }


}
