/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.listener;

import com.epic.epay.ipg.util.common.HibernateInit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author chanuka
 */
public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        try {
            HibernateInit hi = new HibernateInit();
            hi.initialize();
            System.out.println("New Session Factory Initialized.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            HibernateInit.sessionFactory.close();
            System.out.println("Session Factory Destroyed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
