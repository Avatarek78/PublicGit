/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestclient.scheduler;

import cz.morosystems.morotestclient.controller.SendMessageWorker;
import cz.morosystems.morotestcommon.ErrDaoCommObjForClient;
import cz.morosystems.morotestcommon.ErrInvalidDataCommObjForClient;
import cz.morosystems.morotestcommon.MessageCommObjForClient;
import cz.morosystems.morotestcommon.MessageCommObjForServer;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Class for automatically message sending over Quartz.
 * @author Tomas
 */
public class AutoMessageJob extends QuartzJobBean{
    
    private final Logger LOG=LogManager.getLogger(AutoMessageJob.class);
        
    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {        
        //Create MessageCommObjForServer object and send him over hessian.   
        MessageCommObjForServer requestMessage = new MessageCommObjForServer("Automatic message from Quartz");
        requestMessage.setSentDate(new Date());        
        try
        {
            //Sending message to the server with storing it into the history.
            SendMessageWorker.getInstanceForScheduler().sendMessage(requestMessage, true);
        }
        catch(Exception ex)
        {
            String msg = "Error while sending message";
            //Log message with exception.
            LOG.error(msg,ex);
        }     
    }   
}
