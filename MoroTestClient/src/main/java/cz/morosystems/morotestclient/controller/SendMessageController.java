/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestclient.controller;

import cz.morosystems.morotestclient.model.MessageHistory;
import cz.morosystems.morotestcommon.MessageCommObjForClient;
import cz.morosystems.morotestcommon.ErrInvalidDataCommObjForClient;
import cz.morosystems.morotestcommon.ErrDaoCommObjForClient;
import cz.morosystems.morotestcommon.MessageCommObjForServer;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class for sending messages to the MoroTestServer.
 * @author tomas
 */
@Controller
public class SendMessageController {
   
    private final Logger LOG=LogManager.getLogger(SendMessageController.class);
      
   /**
    * Main page.
    * @param map
    * @return 
    */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap map) {    
        //Fill user and scheduler message history.
        setWebFormMessageHistory(map);
        return "index";
    }   

    /**
    * Method for sending message from web form in main page.
    * @param strMessage Text content of the message.
    * @param map
    * @return 
    */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String sendMessage(@RequestParam("TextInput") String strMessage,ModelMap map) {       
        //Empty validation.
        if(strMessage.isEmpty())
        {
            //Set message for user.
            setWebFormMessage(map, "Error checking data on the client: message is empty!", "errors");
            //Fill user and scheduler message history.
            setWebFormMessageHistory(map);
            return "index";
        }

        LOG.info("User input: " + strMessage);

        //Create MessageCommObjForServer object and send him over hessian.   
        MessageCommObjForServer requestMessage = new MessageCommObjForServer(strMessage);
        requestMessage.setSentDate(new Date());
        MessageCommObjForClient answMessage = null;
        try
        {
            //Sending message to the server with storing it into the history.
            answMessage = SendMessageWorker.getInstanceForUser().sendMessage(requestMessage, true);

            //Check answer from the server...
            //If server detected data validation error...
            if(answMessage instanceof ErrInvalidDataCommObjForClient)
            {
                setWebFormMessage(map, "Error checking data on the server: " + answMessage.getStrMessage(), "errors");
            }
            //If server detect data storage error...
            else if(answMessage instanceof ErrDaoCommObjForClient)
            {
                setWebFormMessage(map, "Error storing data on the server: " + answMessage.getStrMessage(), "errors");                
            }
            else
            {
                setWebFormMessage(map, "Message sent", "ok");                
            }            
        }
        catch(Exception ex)
        {
            String msg = "Error while sending message";
            //Log message with exception.
            LOG.error(msg,ex);
            //Set error mesage for user back to the web form.
            setWebFormMessage(map, msg + " (" + ex.getMessage() + ")", "errors");
        }  
        
        //Fill user and scheduler message history.
        setWebFormMessageHistory(map);

        return "index";
    }     

    /**
     * Set user and scheduler message history.
     * @param map data model for web form.
     */   
    private void setWebFormMessageHistory(ModelMap map) {
        MessageHistory userMsgHistory = SendMessageWorker.getInstanceForUser().getMsgHistory();
        //Publish message history to the web.
        map.put("userMessageHistoryList", userMsgHistory.getHistory());
        //Publish message history actual and maximum size
        map.put("userMessageHistoryListSize", userMsgHistory.getHistorySize());
        map.put("userMessageHistoryListMax", userMsgHistory.getHistoryMaxSize());
        //Also update scheduler messages history.
        MessageHistory schedulerMsgHistory = SendMessageWorker.getInstanceForScheduler().getMsgHistory();
        //Publish message history to the web.
        map.put("schedulerMessageHistoryList", schedulerMsgHistory.getHistory());
        //Publish message history actual and maximum size
        map.put("schedulerMessageHistoryListSize", schedulerMsgHistory.getHistorySize());
        map.put("schedulerMessageHistoryListMax", schedulerMsgHistory.getHistoryMaxSize());
    }
   
    /**
     * Set user message to the web form.
     * @param map data for web form.
     * @param usermessage message to show in web form.
     * @param usermsgclass name of the css style (possible values is in "WEB-INF/resources/css/site.css".
     */
    private void setWebFormMessage(ModelMap map,String usermessage,String usermsgclass)
    {
        //Set style for user message.
        map.put("usermsgclass", usermsgclass);
        //Set user error message.
        map.put("usermessage", usermessage); 
    }
   
   
    
}
