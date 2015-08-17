package com.tsystems.javaschool.ecare.services;

import com.tsystems.javaschool.ecare.dao.TariffDAO;
import com.tsystems.javaschool.ecare.entities.Tariff;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * This class is the implementation of ITariffService for working with tariff DAO
 * and tariff entities. Class TariffService is a singleton.
 */
@Service("tariffService")
public class TariffService
{
    /*Logger for tariff service operations*/
    private static Logger logger = Logger.getLogger(TariffService.class);
    /*SQL tariff implementations of abstract DAO class*/
    private TariffDAO trDao;

    /*Constructor of Tariff Service class*/
    @Autowired
    public TariffService(TariffDAO trDAO)
    {
        this.trDao = trDAO;
    }


    /**
     * This method implements saving or updating of tariff in the database.
     *
     * @param tr tariff entity to be saved or updated.
     * @return saved or updated tariff entity.
     */
    @Transactional
    public Tariff saveOrUpdateTariff(Tariff tr)
    {
        logger.info("Save/update tariff " + tr + " in DB.");
        Tariff tariff = trDao.saveOrUpdate(tr);
        //If DAO returns null method will throws an ECareException.
        if (tariff == null)
        {
            logger.error("Failed to save/update tariff " + tr + " in DB.");
        }
        logger.info("Tariff " + tariff + " saved in DB.");
        //Else tariff will be saved and method returns tariff entity.
        return tariff;

    }

    /**
     * This method implements loading of tariff from the database.
     *
     * @param id tariff id for search that tariff in the database.
     * @return loaded tariff entity.
     */
    @Transactional
    public Tariff loadTariff(int id)
    {
        logger.info("Load tariff with id: " + id + " from DB.");
        Tariff tr = trDao.load(id);
        //If DAO returns null method will throws an ECareException.
        if (tr == null)
        {
            logger.warn("Tariff with id = " + id + " not found.");
        }
        logger.info("Tariff " + tr + " loaded from DB.");
        //Else method returns tariff entity.
        return tr;

    }

    /**
     * This method implements deleting of tariff from the database.
     *
     * @param id tariff id for deleting that tariff from the database.
     */
    @Transactional
    public void deleteTariff(int id)
    {
        logger.info("Delete tariff with id: " + id + " from DB.");
        Tariff tr = trDao.load(id);
        //If DAO returns null method will throws an ECareException.
        if (tr == null)
        {
            logger.warn("Tariff with id = " + id + " not exist.");
            return;
        }
        // Else tariff will be deleted from the database.
        trDao.delete(tr);
        logger.info("Tariff " + tr + " deleted from DB.");

    }

    /**
     * This method implements receiving of all options from the database.
     *
     * @return list of received tariffs.
     */
    @Transactional
    public Set<Tariff> getAllTariffs()
    {
        logger.info("Get all tariffs from DB.");
        Set<Tariff> tariffs = trDao.getAll();
        //If DAO returns null method will throws an ECareException.
        if (tariffs == null)
        {
            logger.warn("Failed to get all tariffs from DB.");
        }
        logger.info("All tariffs obtained from DB.");
        // Else method returns list of tariff entities.
        return tariffs;

    }

    /**
     * This method implements deleting of all tariffs from the database.
     */
    @Transactional
    public void deleteAllTariffs()
    {
        logger.info("Delete all tariffs from DB.");
        trDao.deleteAll();
        logger.info("All tariffs deleted from DB.");

    }

    /**
     * This method implements receiving number of all tariffs from the storage.
     *
     * @return number of tariffs in the storage.
     */
    @Transactional
    public long getNumberOfTariffs()
    {
        logger.info("Get number of tariffs in DB.");
        long number = trDao.getCount();
        logger.info(number + " of tariffs obtained fromDB.");
        return number;

    }
}
