package com.tsystems.javaschool.ecare.listeners;

import com.tsystems.javaschool.ecare.util.EntityManagerUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class EntityManagerFactoryListener implements ServletContextListener
{

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        EntityManagerUtil.closeEntityManagerFactory();
    }

    @Override
    public void contextInitialized(ServletContextEvent e) {}

}