/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestserver.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author tomas.langer
 */
public class HibernateUtil
{
    private static SessionFactory sessionFactory;
    
    private static final Logger LOG=LogManager.getLogger(HibernateUtil.class);
    
    public static synchronized void Init()
    {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            LOG.info("HibernateUtil.Init");
        } catch (Throwable ex) {
            LOG.error("Initial Hibernate SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
    
    public static synchronized void Close()
    {
        if(sessionFactory != null)
        {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}
