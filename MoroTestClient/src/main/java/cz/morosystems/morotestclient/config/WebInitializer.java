package cz.morosystems.morotestclient.config;

import javax.servlet.ServletContext;  
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;  
import javax.servlet.ServletRegistration.Dynamic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
  
import org.springframework.web.WebApplicationInitializer; 
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;  
import org.springframework.web.servlet.DispatcherServlet;  
  
public class WebInitializer implements WebApplicationInitializer {
    
    private final Logger LOG = LogManager.getLogger(WebInitializer.class);
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {        
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();  
        ctx.register(Config.class);  
        ctx.setServletContext(servletContext);    
        Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));  
        servlet.addMapping("/");  
        servlet.setLoadOnStartup(1);
        //Add application context listener.
        addApplicationContextListener(servletContext, ctx);        
    } 
    
    private void addApplicationContextListener(ServletContext servletContext, AnnotationConfigWebApplicationContext ctx) {
        servletContext.addListener(new ContextLoaderListener(ctx) {            
            @Override
            public void contextInitialized(ServletContextEvent event) {
                super.contextInitialized(event); 
                LOG.info("************* contextInitialized *************");                
            }  
            
            @Override
            public void contextDestroyed(ServletContextEvent event) {
                super.contextDestroyed(event); 
                LOG.info("************* contextDestroyed *************");  
            }                      
        });  
    }
            
}
