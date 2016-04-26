package cz.morosystems.morotestserver;


import cz.morosystems.morotestserver.dao.HibernateUtil;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Web application lifecycle listener.
 *
 * @author Tomas
 */
public class ServletListener implements ServletContextListener {

    private static final Logger LOG=LogManager.getLogger(ServletListener.class);   
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {        
        HibernateUtil.Init();
        LOG.info("*** Servlet initialized ***");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.Close();
        LOG.info("*** Servlet destroyed ***");
    }
}
