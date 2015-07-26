package com.tsystems.javaschool.ecare.services;

import com.tsystems.javaschool.ecare.dao.IAbstractDAO;
import com.tsystems.javaschool.ecare.dao.TariffDAO;
import com.tsystems.javaschool.ecare.entities.Tariff;
import com.tsystems.javaschool.ecare.util.AppException;
import com.tsystems.javaschool.ecare.util.EntityManagerUtil;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * This class is the implementation of ITariffService for working with tariff DAO
 * and tariff entities. Class TariffService is a singleton.
 */

public class TariffService
{

    /*Instance of the singleton class*/
    private static volatile TariffService instance;
    /*Logger for tariff service operations*/
    private static Logger logger = Logger.getLogger(TariffService.getInstance().getClass());
    /*SQL tariff implementations of abstract DAO class*/
    private IAbstractDAO<Tariff> trDAO = TariffDAO.getInstance();

    /*Private constructor of singleton class*/
    private TariffService()
    {
    }

    /**
     * This method return instance of singleton class TariffService.
     *
     * @return instance of class.
     */
    public static TariffService getInstance()
    {
        TariffService localInstance = instance;
        if (localInstance == null)
        {
            synchronized (TariffService.class)
            {
                localInstance = instance;
                if (localInstance == null)
                {
                    instance = localInstance = new TariffService();
                }
            }
        }
        return localInstance;
    }

    /**
     * This method implements saving or updating of tariff in the database.
     *
     * @param tr tariff entity to be saved or updated.
     * @return saved or updated tariff entity.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during saving or updating of entity
     *                        and DAO returns null.
     */
    public Tariff saveOrUpdateTariff(Tariff tr) throws AppException
    {
        logger.info("Save/update tariff " + tr + " in DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            Tariff tariff = trDAO.saveOrUpdate(tr);
            EntityManagerUtil.commit();
            //If DAO returns null method will throws an ECareException.
            if (tariff == null)
            {
                AppException ecx = new AppException("Failed to save/update tariff " + tr + " in DB.");
                logger.error(ecx.getMessage(), ecx);
                throw ecx;
            }
            logger.info("Tariff " + tariff + " saved in DB.");
            //Else tariff will be saved and method returns tariff entity.
            return tariff;
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
     * This method implements loading of tariff from the database.
     *
     * @param id tariff id for search that tariff in the database.
     * @return loaded tariff entity.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during loading of entity
     *                        and DAO returns null.
     */
    public Tariff loadTariff(int id) throws AppException
    {
        logger.info("Load tariff with id: " + id + " from DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            Tariff tr = trDAO.load(id);
            EntityManagerUtil.commit();
            //If DAO returns null method will throws an ECareException.
            if (tr == null)
            {
                AppException ecx = new AppException("Tariff with id = " + id + " not found.");
                logger.warn(ecx.getMessage(), ecx);
                throw ecx;
            }
            logger.info("Tariff " + tr + " loaded from DB.");
            //Else method returns tariff entity.
            return tr;
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
     * This method implements deleting of tariff from the database.
     *
     * @param id tariff id for deleting that tariff from the database.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during intermediate loading
     *                        of entity and DAO returns null.
     */
    public void deleteTariff(int id) throws AppException
    {
        logger.info("Delete tariff with id: " + id + " from DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            Tariff tr = trDAO.load(id);
            //If DAO returns null method will throws an ECareException.
            if (tr == null)
            {
                AppException ecx = new AppException("Tariff with id = " + id + " not exist.");
                logger.warn(ecx.getMessage(), ecx);
                throw ecx;
            }
            // Else tariff will be deleted from the database.
            trDAO.delete(tr);
            EntityManagerUtil.commit();
            logger.info("Tariff " + tr + " deleted from DB.");
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
     * This method implements receiving of all options from the database.
     *
     * @return list of received tariffs.
     */
    public List<Tariff> getAllTariffs() throws AppException
    {
        logger.info("Get all tariffs from DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            List<Tariff> tariffs = trDAO.getAll();
            EntityManagerUtil.commit();
            //If DAO returns null method will throws an ECareException.
            if (tariffs == null)
            {
                AppException ecx = new AppException("Failed to get all tariffs from DB.");
                logger.warn(ecx.getMessage(), ecx);
                throw ecx;
            }
            logger.info("All tariffs obtained from DB.");
            // Else method returns list of tariff entities.
            return tariffs;
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
     * This method implements deleting of all tariffs from the database.
     */
    public void deleteAllTariffs()
    {
        logger.info("Delete all tariffs from DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            trDAO.deleteAll();
            EntityManagerUtil.commit();
            logger.info("All tariffs deleted from DB.");
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
     * This method implements receiving number of all tariffs from the storage.
     *
     * @return number of tariffs in the storage.
     */
    public long getNumberOfTariffs()
    {
        logger.info("Get number of tariffs in DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            long number = trDAO.getCount();
            EntityManagerUtil.commit();
            logger.info(number + " of tariffs obtained fromDB.");
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
