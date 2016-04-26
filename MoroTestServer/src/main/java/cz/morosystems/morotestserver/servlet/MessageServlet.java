/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestserver.servlet;

import com.caucho.hessian.server.HessianServlet;
import cz.morosystems.morotestcommon.ErrDaoCommObjForClient;
import cz.morosystems.morotestcommon.ErrInvalidDataCommObjForClient;
import cz.morosystems.morotestcommon.MessageCommObjForClient;
import cz.morosystems.morotestcommon.MessageCommObjForServer;
import cz.morosystems.morotestcommon.MessageService;
import cz.morosystems.morotestserver.dao.MessageForServer;
import cz.morosystems.morotestserver.dao.MessageForServerDaoImpl;
import java.util.Date;
import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

/**
 * Main implementation of the message service.
 * @author Tomas
 */
public class MessageServlet extends HessianServlet implements MessageService {   
    
    private MessageForServerDaoImpl messageForServerDaoImpl;
    
    private final Logger LOG=LogManager.getLogger(HessianServlet.class);

    @Override
    public MessageCommObjForClient sendMessage(MessageCommObjForServer requestMessage) {
        LOG.info("Server received this message: " + requestMessage.getStrMessage());
        //Create default OK answer object.
        MessageCommObjForClient answer = new MessageCommObjForClient("OK");  
        
        //Do server validation of the message content.
        String strCheckResult = checkRequestContent(requestMessage);
        if(!strCheckResult.isEmpty())
        {
            LOG.info("Validation of message failed! (" + strCheckResult + ")");
            //If fails send error message back to the client.
            answer = new ErrInvalidDataCommObjForClient(strCheckResult);
            answer.setSentDate(new Date());
            return answer;
        }
        
        //If message content validation is OK store message into database.
        try
        {
            storeMessageIntoDatabase(requestMessage);
        }
        catch(HibernateException ex)
        {
            LOG.info("Storing message into the database failed! (" + ex.getMessage() + ")");
            //If fails send error message back to the client.
            answer = new ErrDaoCommObjForClient(ex.getMessage());
            answer.setSentDate(new Date());
            return answer;            
        }
        //Set date and time of the answer message.
        answer.setSentDate(new Date());
        //Return default OK answer to client.
        return answer;
    }
    
    /**
     * Helper function for check request content.
     * @param requestMessage
     * @return Return empty string if request is OK otherwise return string with error description.
     */
    private String checkRequestContent(MessageCommObjForServer requestMessage)
    {               
        //If message content is empty answer is...
        if(requestMessage.getStrMessage().isEmpty())             
             return "Message is empty!";
        
        //If message content is longer than maximum for the messsage content field in 
        //"cz.morosystems.morotestserver.dao.MessageForServer"...        
        if(requestMessage.getStrMessage().length() > MessageForServer.getMessageMaxLen())                    
             return "Message is longer than " + MessageForServer.getMessageMaxLen() + "!";
        
        return "";
    }
    
    /**
     * Helper function for store message into the database.
     * @param requestMessage
     * @throws HibernateException 
     */
    private void storeMessageIntoDatabase(MessageCommObjForServer requestMessage) throws HibernateException
    {
        MessageForServer dbMsg = new MessageForServer();
        dbMsg.setMessage(requestMessage.getStrMessage());
        dbMsg.setDateTime(requestMessage.getSentDate());
        messageForServerDaoImpl.addMessage(dbMsg);                
    }    
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "MoroTestServer - Message service. Use \"MoroTestClient\" the web application for send messages to the server.";
    }    

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); //To change body of generated methods, choose Tools | Templates.
        LOG.info("MessageServlet.init");
        //List the servlet configuration.
        Enumeration<String> paramNames = config.getInitParameterNames();
        while(paramNames.hasMoreElements())
        {
            String paramName = paramNames.nextElement();
            LOG.info(paramName + "=" + config.getInitParameter(paramName));            
        }
        //DAO initialization.
        messageForServerDaoImpl = new MessageForServerDaoImpl();
    }    

    @Override
    public void destroy() {
        super.destroy(); 
        LOG.info("MessageServlet.destroy");
    }
    
    

}
