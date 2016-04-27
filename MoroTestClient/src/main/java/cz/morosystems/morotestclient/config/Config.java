package cz.morosystems.morotestclient.config;  
  
import cz.morosystems.morotestclient.scheduler.AutoMessageJob;
import java.util.Properties;
import org.quartz.plugins.management.ShutdownHookPlugin;
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.ComponentScan;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;  
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;  
import org.springframework.web.servlet.view.UrlBasedViewResolver;  
  
@Configuration
@ComponentScan("cz.morosystems.morotestclient")
@EnableWebMvc   
@EnableScheduling
public class Config extends WebMvcConfigurerAdapter {  
      
    @Bean  
    public UrlBasedViewResolver setupViewResolver() {  
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();  
        resolver.setPrefix("/WEB-INF/jsp/");  
        resolver.setSuffix(".jsp");  
        resolver.setViewClass(JstlView.class);        
        return resolver;  
    } 

    //*********** Quartz scheduler configuration ***********
    /**
     * Create and configure autoMessage job details for Quartz .
     * @return 
     */
    @Bean
    public JobDetailFactoryBean autoMessage(){
        JobDetailFactoryBean bean=new JobDetailFactoryBean();
        bean.setJobClass(AutoMessageJob.class);
        bean.setName("autoMessage");
        bean.setGroup("autoMessages");
        bean.setDurability(true);        
        /**
         * I still have problem with stop the quartz thread at the end of application.
         * The web application [MoroTestClient] appears to have started a thread named [schedulerFactoryBean_Worker-1] 
         * but has failed to stop it. This is very likely to create a memory leak. Stack trace of thread:
         * java.lang.Object.wait(Native Method)
         * org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:568)
         * I discover this is probably problem in Apache Tomcat 8.0.27.0 because on GlassFish 
         * Quartz stop correctly with message "Shutting down Quartz Scheduler"
         */
        return bean;
    }    
    /**
     * Create and configure autoMessage Quartz trigger. Set repeat interval for sending 
     * automatic message.
     * @return 
     */
    @Bean
    public SimpleTriggerFactoryBean autoMessageTrigger(){
        SimpleTriggerFactoryBean bean=new SimpleTriggerFactoryBean();
        bean.setJobDetail(autoMessage().getObject());
        bean.setStartDelay(1000);
        bean.setRepeatInterval(5000);        
        bean.setBeanName("autoMessageTrigger");
        bean.setGroup("autoMessages");
        return bean;
    }
    /**
     * Create and configure autoMessage Quartz scheduler.
     * @return 
     */
    @Bean
    public SchedulerFactoryBean autoMessageScheduler() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(autoMessageTrigger().getObject()); 
        //I try to set "Quartz" properties for abilitty to be correctly stopped at the end of application.
        //But without success.
        Properties quartzProperties = new Properties();
        quartzProperties.put("org.quartz.threadPool.threadCount", "1");        
        quartzProperties.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        quartzProperties.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
        quartzProperties.put("org.quartz.scheduler.interruptJobsOnShutdownWithWait", "true");
        scheduler.setQuartzProperties(quartzProperties);          
        return scheduler;
    }
    
    /**
     * Web resources css,..
     * @param registry 
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/*");
    }
}  
