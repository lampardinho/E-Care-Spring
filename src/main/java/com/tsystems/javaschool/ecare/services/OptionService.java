package com.tsystems.javaschool.ecare.services;

import com.tsystems.javaschool.ecare.dao.IAbstractDAO;
import com.tsystems.javaschool.ecare.dao.OptionDAO;
import com.tsystems.javaschool.ecare.entities.Option;
import com.tsystems.javaschool.ecare.util.AppException;
import com.tsystems.javaschool.ecare.util.EntityManagerUtil;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * This class is the implementation of IOptionService for working with option DAO
 * and option entities. Class OptionService is a singleton.
 */

public class OptionService
{

    /*Instance of the singleton class*/
    private static volatile OptionService instance;
    /*Logger for option service operations*/
    private static Logger logger = Logger.getLogger(OptionService.getInstance().getClass());
    /*SQL option implementations of abstract DAO class*/
    private IAbstractDAO<Option> DAO = OptionDAO.getInstance();
    private OptionDAO opDAO = OptionDAO.getInstance();

    /*Private constructor of singleton class*/
    private OptionService()
    {
    }

    /**
     * This method return instance of singleton class OptionService.
     *
     * @return instance of class.
     */
    public static OptionService getInstance()
    {
        OptionService localInstance = instance;
        if (localInstance == null)
        {
            synchronized (OptionService.class)
            {
                localInstance = instance;
                if (localInstance == null)
                {
                    instance = localInstance = new OptionService();
                }
            }
        }
        return localInstance;
    }

    /**
     * This method implements saving or updating of option in the database.
     *
     * @param op option entity to be saved or updated.
     * @return saved or updated option entity.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during saving or updating of entity
     *                        and DAO returns null.
     */
    public Option saveOrUpdateOption(Option op) throws AppException
    {
        logger.info("Save/update option " + op + " in DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            Option option = DAO.saveOrUpdate(op);
            EntityManagerUtil.commit();
            //If DAO returns null method will throws an ECareException
            if (option == null)
            {
                AppException ecx = new AppException("Failed to save/update option " + op + " in DB.");
                logger.error(ecx.getMessage(), ecx);
                throw ecx;
            }
            logger.info("Option " + option + " saved in DB.");
            //else option will be saved and method returns option entity
            return option;
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
     * This method implements loading of option from the database.
     *
     * @param id option id for search that option in the database.
     * @return loaded option entity.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during loading of entity
     *                        and DAO returns null.
     */
    public Option loadOption(int id) throws AppException
    {
        logger.info("Load option with id: " + id + " from DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            Option op = DAO.load(id);
            EntityManagerUtil.commit();
            //If DAO returns null method will throws an ECareException
            if (op == null)
            {
                AppException ecx = new AppException("Option with id = " + id + " not found in DB.");
                logger.warn(String.valueOf(ecx), ecx);
                throw ecx;
            }
            logger.info("Options " + op + " loaded from DB.");
            //else method returns option entity
            return op;
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
     * This method implements deleting of option from the database.
     *
     * @param id option id for deleting that option from the database.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during intermediate loading
     *                        of entity and DAO returns null.
     */
    public void deleteOption(int id) throws AppException
    {
        logger.info("Delete option with id: " + id + " from DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            Option op = DAO.load(id);
            //If DAO returns null method will throws an ECareException
            if (op == null)
            {
                AppException ecx = new AppException("Option with id = " + id + " not exist.");
                logger.warn(ecx.getMessage(), ecx);
                throw ecx;
            }
            // Else option will be deleted from the database.
            DAO.delete(op);
            EntityManagerUtil.commit();
            logger.info("Option " + op + " deleted from DB.");
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
     * @return list of received options.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during receiving of entities
     *                        and DAO returns null.
     */
    public List<Option> getAllOptions() throws AppException
    {
        logger.info("Get all options from DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            List<Option> options = DAO.getAll();
            EntityManagerUtil.commit();
            //If DAO returns null method will throws an ECareException
            if (options == null)
            {
                AppException ecx = new AppException("Failed to get all options from DB.");
                logger.error(ecx.getMessage(), ecx);
                throw ecx;
            }
            logger.info("All options obtained from DB.");
            // Else method returns list of option entities
            return options;
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
     * This method implements receiving of all options for tariff from the database.
     *
     * @param id contract id for searching of all options for this contract.
     * @return list of received options.
     * @throws com.tsystems.javaschool.ecare.util.AppException if an error occurred during receiving of entities
     *                        and DAO returns null.
     */
    public List<Option> getAllOptionsForTariff(long id) throws AppException
    {
        logger.info("Get all options from DB for tariff with id: " + id + ".");
        try
        {
            EntityManagerUtil.beginTransaction();
            List<Option> options = opDAO.getAllOptionsForTariff(id);
            EntityManagerUtil.commit();
            //If DAO returns null method will throws an ECareException
            if (options == null)
            {
                AppException ecx = new AppException("Failed to get all options from DB for tariff id: " + id + ".");
                logger.error(ecx.getMessage(), ecx);
                throw ecx;
            }
            logger.info("All options for tariff id: " + id + " obtained from DB.");
            // Else method returns list of option entities
            return options;
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
     * This method implements deleting of all options for tariff from the database.
     *
     * @param id tariff id for deleting of all options for this tariff.
     */
    public void deleteAllOptionsForTariff(long id)
    {
        logger.info("Delete all options from DB for tariff with id: " + id + ".");
        try
        {
            EntityManagerUtil.beginTransaction();
            opDAO.deleteAllOptionsForTariff(id);
            EntityManagerUtil.commit();
            logger.info("All options for tariff id: " + id + " deleted from DB.");
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
     * This method implements receiving number of all options from the database.
     *
     * @return number of options in the storage.
     */
    public long getNumberOfOptions()
    {
        logger.info("Get number of options in DB.");
        try
        {
            EntityManagerUtil.beginTransaction();
            long number = DAO.getCount();
            EntityManagerUtil.commit();
            logger.info(number + "of options obtained from DB.");
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
