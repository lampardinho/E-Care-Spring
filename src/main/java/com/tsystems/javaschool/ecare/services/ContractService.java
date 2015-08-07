package com.tsystems.javaschool.ecare.services;

import com.tsystems.javaschool.ecare.dao.ContractDAO;
import com.tsystems.javaschool.ecare.entities.Contract;
import com.tsystems.javaschool.ecare.entities.User;
import com.tsystems.javaschool.ecare.util.AppException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * This class is the implementation of IContractService for working with contract DAO
 * and contract entities. Class ContractService is a singleton.
 */
@Service("contractService")
public class ContractService
{
    /*SQL contract implementations of DAO class*/
    private ContractDAO cnDao;

    /*Client service instance for some methods of working with client amount in contract service*/
    private final UserService clientService;

    /*Tariff service instance for some methods of working with tariff service*/
    private final TariffService tariffService;

    /*Option service instance for some methods of working with option service*/
    private final OptionService optionService;

    /*Logger for contract service operations*/
    private static Logger logger = Logger.getLogger(ContractService.class);

    /*Constructor of Contract Service class*/
    @Autowired
    public ContractService(ContractDAO cnDAO, UserService clientService, OptionService optionService, TariffService tariffService) {
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
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during saving or updating of entity
     *                        and DAO returns null.
     */
    @Transactional
    public Contract saveOrUpdateContract(Contract cn) throws AppException
    {
        logger.info("Save/update contract " + cn + " in DB.");

        Contract contract = cnDao.saveOrUpdate(cn);
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

    }

    /**
     * This method implements loading of contracts from the database.
     *
     * @param id contract id for search that contract in the database.
     * @return loaded contract entity.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during loading of entity
     *                        and DAO returns null.
     */
    @Transactional
    public Contract loadContract(int id) throws AppException
    {
        logger.info("Load contract with id: " + id + " from DB.");
        Contract cn = cnDao.load(id);
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
    @Transactional
    public Contract getContractByPhoneNumber(int number) throws AppException
    {
        logger.info("Find contract by telephone number: " + number + " in DB.");
        Contract cn = null;
        try {
            // Search of contract in the database by DAO method.
            cn = cnDao.findContractByNumber(number);
            // If contract does not exist in database, block try catches the NoResultException and
            // throws an ECareException.
        } catch (NoResultException nrx)
        {
            AppException ecx = new AppException("Contract with number: " + number + " not found.", nrx);
            logger.warn(ecx.getMessage(), nrx);
            throw ecx;
        }
        logger.info("Contract " + cn + " found and loaded from DB.");
        return cn;

    }

    /**
     * This method implements deleting of contract from the database.
     *
     * @param id contract id for deleting that contract from the database.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during intermediate loading
     *                        of entity and DAO returns null.
     */
    @Transactional
    public void deleteContract(int id) throws AppException
    {
        logger.info("Delete contract with id: " + id + " from DB.");
        Contract cn = cnDao.load(id);
        //If DAO returns null method will throws an ECareException.
        if (cn == null)
        {
            AppException ecx = new AppException("Contract with id = " + id + " not exist.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        // Else contract will be deleted from the database.
        cnDao.delete(cn);

        logger.info("Contract " + cn + " deleted from DB.");

    }

    /**
     * This method implements receiving of all contracts from the database.
     *
     * @return list of received contracts.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during receiving of entities
     *                        and DAO returns null.
     */
    @Transactional
    public List<Contract> getAllContracts() throws AppException
    {
        logger.info("Get all contracts from DB.");
        List<Contract> contracts = cnDao.getAll();
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

    }

    /**
     * This method implements receiving of all contracts for client from the database.
     *
     * @param user client id for searching of all contracts for this client.
     * @return list of received contracts.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during receiving of entities
     *                        and DAO returns null.
     */
    @Transactional
    public List<Contract> getUserContracts(User user) throws AppException
    {
        logger.info("Get all contracts from DB for client with id: " + user.getUserId() + ".");
        List<Contract> contracts = cnDao.getAllContractsForClient(user.getUserId());
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
