package com.tsystems.javaschool.ecare.services;

import com.tsystems.javaschool.ecare.dao.OptionDAO;
import com.tsystems.javaschool.ecare.entities.Option;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This class is the implementation of IOptionService for working with option DAO
 * and option entities. Class OptionService is a singleton.
 */
@Service("optionService")
public class OptionService
{
    /*SQL option implementations of DAO class*/
    private OptionDAO opDao;

    /*Tariff service instance for some methods of working with that service*/
    private final TariffService tariffService;

    /*Logger for option service operations*/
    private static Logger logger = Logger.getLogger(OptionService.class);

    /*Constructor of Client Service class*/
    @Autowired
    public OptionService(OptionDAO opDAO, TariffService tariffService) {
        this.opDao = opDAO;
        this.tariffService = tariffService;
    }

    /**
     * This method implements saving or updating of option in the database.
     *
     * @param op option entity to be saved or updated.
     * @return saved or updated option entity.
     *
     */
    @Transactional
    public Option saveOrUpdateOption(Option op)
    {
        logger.info("Save/update option " + op + " in DB.");
        Option option = opDao.saveOrUpdate(op);
        //If DAO returns null method will throws an ECareException
        if (option == null)
        {
            logger.error("Failed to save/update option " + op + " in DB.");
        }
        logger.info("Option " + option + " saved in DB.");
        //else option will be saved and method returns option entity
        return option;

    }

    /**
     * This method implements loading of option from the database.
     *
     * @param id option id for search that option in the database.
     * @return loaded option entity.
     *
     */
    @Transactional
    public Option loadOption(int id)
    {
        logger.info("Load option with id: " + id + " from DB.");
        Option op = opDao.load(id);
        //If DAO returns null method will throws an ECareException
        if (op == null)
        {
            logger.warn("Option with id = " + id + " not found in DB.");
        }
        logger.info("Options " + op + " loaded from DB.");
        //else method returns option entity
        return op;
    }

    /**
     * This method implements deleting of option from the database.
     *
     * @param id option id for deleting that option from the database.
     *
     */
    @Transactional
    public void deleteOption(int id)
    {
        logger.info("Delete option with id: " + id + " from DB.");
        Option op = opDao.load(id);
        //If DAO returns null method will throws an ECareException
        if (op == null)
        {
            logger.warn("Option with id = " + id + " not exist.");
            return;
        }
        // Else option will be deleted from the database.
        opDao.delete(op);
        logger.info("Option " + op + " deleted from DB.");

    }

    /**
     * This method implements receiving of all options from the database.
     *
     * @return list of received options.
     *
     */
    @Transactional
    public List<Option> getAllOptions()
    {
        logger.info("Get all options from DB.");
        List<Option> options = opDao.getAll();
        //If DAO returns null method will throws an ECareException
        if (options == null)
        {
            logger.error("Failed to get all options from DB.");
        }
        logger.info("All options obtained from DB.");
        // Else method returns list of option entities
        return options;

    }

    /**
     * This method implements receiving of all options for tariff from the database.
     *
     * @param id contract id for searching of all options for this contract.
     * @return list of received options.
     *
     */
    @Transactional
    public List<Option> getAllOptionsForTariff(long id)
    {
        logger.info("Get all options from DB for tariff with id: " + id + ".");
        List<Option> options = opDao.getAllOptionsForTariff(id);
        //If DAO returns null method will throws an ECareException
        if (options == null)
        {
            logger.error("Failed to get all options from DB for tariff id: " + id + ".");
        }
        logger.info("All options for tariff id: " + id + " obtained from DB.");
        // Else method returns list of option entities
        return options;

    }

    /**
     * This method implements deleting of all options for tariff from the database.
     *
     * @param id tariff id for deleting of all options for this tariff.
     */
    @Transactional
    public void deleteAllOptionsForTariff(long id)
    {
        logger.info("Delete all options from DB for tariff with id: " + id + ".");
        opDao.deleteAllOptionsForTariff(id);
        logger.info("All options for tariff id: " + id + " deleted from DB.");

    }

    /**
     * This method implements receiving number of all options from the database.
     *
     * @return number of options in the storage.
     */
    @Transactional
    public long getNumberOfOptions()
    {
        logger.info("Get number of options in DB.");
        long number = opDao.getCount();
        logger.info(number + "of options obtained from DB.");
        return number;

    }


}
