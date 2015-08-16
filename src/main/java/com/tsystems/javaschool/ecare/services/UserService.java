package com.tsystems.javaschool.ecare.services;

import com.tsystems.javaschool.ecare.dao.UserDAO;
import com.tsystems.javaschool.ecare.entities.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Set;

/**
 * This class is the implementation of IClientService for working with client DAO
 * and client entities. Class ClientService is a singleton.
 */
@Service("userService")
public class UserService
{
    /*SQL client implementations of DAO class*/
    private UserDAO clDao;

    /*Logger for client service operations*/
    private static Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    public UserService(UserDAO clDAO) {
        this.clDao = clDAO;
    }

    /**
     * Method implements saving or updating of clients in the database.
     *
     * @param cl client entity to be saved or updated.
     * @return saved or updated client entity.
     *
     */
    @Transactional
    public User saveOrUpdateClient(User cl)
    {
        logger.info("Save/update client " + cl + " in DB.");

        User client = clDao.saveOrUpdate(cl);
        //If DAO returns null method will throws an ECareException
        if (client == null)
        {
            logger.error("Failed to save/update client " + cl + " in DB.");
        }
        logger.info("Client " + client + " saved/updated in DB.");
        //else client will be saved and method returns client entity
        return client;

    }

    /**
     * Method implements loading of clients from the database.
     *
     * @param id client id for search that client in the database.
     * @return loaded client entity.
     *
     */
    @Transactional
    public User loadClient(int id)
    {
        logger.info("Load client with id: " + id + " from DB.");

        User cl = clDao.load(id);
        //If DAO returns null method will throws an ECareException
        if (cl == null)
        {
            logger.warn("Client with id = " + id + " not found in DB.");
        }
        logger.info("Client " + cl + " loaded from DB.");
        //else method returns client entity
        return cl;

    }

    /**
     * Method implements finding of clients by their login and password in
     * the database.
     *
     * @param login    client login for search that client in the database.
     * @return found client entity.
     *
     */
    @Transactional
    public User findClient(String login)
    {
        logger.info("Find client with login: " + login + " in DB.");
        User cl = null;
        try
        {
            // Searching of client in the database by DAO method.
            cl = clDao.findUserByLogin(login);
            // If client does not exist in database, block try catches the NoResultException and
            // throws an ECareException.
        } catch (NoResultException nrx)
        {
            logger.warn("Incorrect login/password or client does not exist.", nrx);
        }
        logger.info("Client " + cl + " found and loaded from DB.");
        return cl;

    }

    /**
     * Method implements finding of clients by their telephone number in the database.
     *
     * @param number telephone number of client for search that client in the database.
     * @return found client entity.
     *
     */
    @Transactional
    public User findClientByNumber(int number)
    {
        logger.info("Find client with telephone number: " + number + " in DB.");
        User cl = null;
        try
        {
            // Search of client in the database by DAO method.
            cl = clDao.findUserByNumber(number);
            // If client does not exist in database, block try catches the NoResultException and
            // throws an ECareException.
        } catch (NoResultException nrx)
        {
            logger.warn("Client with number: " + number + " not found.", nrx);
        }
        logger.info("Client " + cl + " found and loaded from DB.");
        return cl;

    }

    /**
     * Method implements deleting of clients from the database.
     *
     * @param id client id for deleting that client from the database.
     *
     */
    @Transactional
    public void deleteClient(int id)
    {
        logger.info("Delete client with id: " + id + " from DB.");
        User cl = clDao.load(id);
        //If DAO returns null method will throws an ECareException.
        if (cl == null)
        {
            logger.warn("Client with id = " + id + " not exist.");
            return;
        }
        // Else client will be deleted from the database.
        clDao.delete(cl);
        logger.info("Client " + cl + " deleted from DB.");

    }

    /**
     * Method implements receiving of all clients from the database.
     *
     * @return list of received clients.
     *
     */
    @Transactional
    public Set<User> getAllClients()
    {
        logger.info("Get all clients from DB.");
        Set<User> clients = clDao.getAll();
        //If DAO returns null method will throws an ECareException.
        if (clients == null)
        {
            logger.error("Failed to get all clients from DB.");
        }
        logger.info("All clients obtained from DB.");
        // Else method returns list of client entities
        return clients;

    }


    @Transactional
    public Set<User> getUsersByTariff(String tariffName)
    {
        logger.info("Get all clients from DB.");
        Set<User> clients = clDao.getUsersByTariff(tariffName);
        if (clients == null)
        {
            logger.error("Failed to get all clients from DB.");
        }
        logger.info("All clients obtained from DB.");
        // Else method returns list of client entities
        return clients;

    }

    /**
     * Method implements deleting of all clients from the database.
     */
    @Transactional
    public void deleteAllClients()
    {
        logger.info("Delete all clients from DB.");
        clDao.deleteAll();
        logger.info("All clients deleted from DB.");

    }

    /**
     * Method implements receiving number of all clients from the database.
     *
     * @return number of clients in the database.
     */
    @Transactional
    public long getNumberOfClients()
    {
        logger.info("Get number of clients in DB.");
        long number = clDao.getCount();
        logger.info(number + "of clients obtained fromDB.");
        return number;

    }

    /**
     * This method implements searching of client in database by client login.
     *
     * @param login client login
     * @return true if client with input login exist, or false if client not exist.
     */
    @Transactional
    public boolean existLogin(String login)
    {
        logger.info("Find client with login: " + login + " in DB.");
        User cl = null;
        try
        {
            // Search of client in the database by DAO method.
            cl = clDao.findUserByLogin(login);
            // If client does not exist in database, block try catches the NoResultException and
            // return false.
        } catch (NoResultException nrx)
        {
            logger.warn("Client with login: " + login + " does not exist.");
            return false;
        }
        logger.info("Client " + cl + " found in DB.");
        // Else, if client exist and loaded, method return true.
        return true;

    }
}
