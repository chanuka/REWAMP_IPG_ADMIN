/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.common;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 *
 * @author chanuka
 */
public class HibernateInit {
    public static SessionFactory sessionFactory;

    public SessionFactory initialize() {
        //            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); //for hibernate 3.0
        if (sessionFactory == null || sessionFactory.isClosed()) {
            Configuration configuration = new Configuration();
            configuration.configure();
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
