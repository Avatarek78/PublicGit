/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestclient.controller;

import com.caucho.hessian.client.HessianProxyFactory;
import cz.morosystems.morotestclient.model.MessageHistory;
import cz.morosystems.morotestclient.model.MessageHistoryItem;
import cz.morosystems.morotestcommon.MessageCommObjForClient;
import cz.morosystems.morotestcommon.MessageCommObjForServer;
import cz.morosystems.morotestcommon.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for sending messages to MoroTestServer over Hessian.<BR>
 * Holds instances for user and scheduler. Every instance also <BR>
 * contains own message history.
 * @author Tomas
 */
public class SendMessageWorker {
    
    //Instance of this class for sending messages by user.
    private static final SendMessageWorker USER_INSTANCE = new SendMessageWorker();
    //Instance of this class for sending messages by scheduler.
    private static final SendMessageWorker SCHEDULER_INSTANCE = new SendMessageWorker();
    
    private final Logger LOG=LogManager.getLogger(SendMessageWorker.class);
    
    //URL where is the messageService.
    //private final String url = "http://localhost:8084/MoroTestServer/MessageServlet/messageService";
    //URL where is the messageService on SSL
    private final String url = "https://localhost:8443/MoroTestServer/MessageServlet/messageService";
    //GlassFish test.
    //private final String url =   "http://localhost:44642/MoroTestServer/MessageServlet/messageService";
    
    //Mesage service for sending messages over hessian.
    private MessageService messageService = null;
    //Messages history limited to 100 messages (request and answer).
    private MessageHistory msgHistory = new MessageHistory(100);

    private SendMessageWorker() {
    }    

    /**
     * Get instance of this class for sending messages by user.
     * @return 
     */
    public static SendMessageWorker getInstanceForUser() {
        return USER_INSTANCE;
    }    
    
    /**
     * Get instance of this class for sending messages by scheduler.
     * @return 
     */
    public static SendMessageWorker getInstanceForScheduler() {
        return SCHEDULER_INSTANCE;
    }
    
    /**
     * Send message to MoroTestServer 
     * @param requestMessage The message to sent.
     * @param storeToHistory True if the message is supposed to be saved in history.
     * @return Return ansert for client.
     * @throws Exception 
     */
    public MessageCommObjForClient sendMessage(MessageCommObjForServer requestMessage, boolean storeToHistory) throws Exception{
        MessageCommObjForClient answMessage = null;
        //If the service does not exist create it.
        if(messageService == null)
        {
            HessianProxyFactory factory = new HessianProxyFactory();
            messageService = (MessageService) factory.create(MessageService.class, url);
        }

        LOG.info("Client send the request: " + requestMessage.getStrMessage());
        //Send message over Hessian.
        answMessage = messageService.sendMessage(requestMessage);
        LOG.info("Client receive the asnwer: " + answMessage.getStrMessage()); 
        //Store sended message to the history.
        storeMesagesToHistory(requestMessage, answMessage);
        return answMessage;        
    }
    
    /**
    * Store last request and aswer message to the history and put history data 
    * for the message history table in web form.
    * @param requestMessage Request message which was sent to the server
    * @param answerMessage Answer message which was received from server.
    * @return Actual list of the messages in history.
    */
   private void storeMesagesToHistory(MessageCommObjForServer requestMessage,
                                      MessageCommObjForClient answerMessage)
   {
       //Store message to the history in memory
       MessageHistoryItem histItem = new MessageHistoryItem(requestMessage, answerMessage);
       msgHistory.addMessageToHistory(histItem);       
   }

   public MessageHistory getMsgHistory() {
       return msgHistory;
   }   
    
}
