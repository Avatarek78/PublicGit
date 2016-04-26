/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestserver.dao;

import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * Better version of dao with using HibernateCallack.
 * @author Tomas
 */
@Stateless
public class MessageForServerDaoImpl extends HibernateDaoSupport implements MessageForServerDao {    
        
    private static final Logger LOG=LogManager.getLogger(HibernateUtil.class);

    public MessageForServerDaoImpl() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        setSessionFactory(sessionFactory);
    }    
    
    /**
     * Storing message into the database.
     * @param message Object to store.
     * @return database id of the inserted message.
     * @throws HibernateException When error occured.
     */
    @Override    
    public Integer addMessage(MessageForServer message) throws DataAccessException {
        Integer id=-1;
        //I try to use lambda expression for call doInHibernate.
        //A little confusing to me.        
        id = getHibernateTemplate().execute((Session session) -> {                 
            Integer msgid = (Integer) session.save(message);   
            //Unfortunately I do't know how to do it without worry about flushing data to database manually.
            session.flush();                     
            return msgid;
        });        
        if(id != -1)
            LOG.info("Message with ID=" + id + " added.");
        else
            LOG.error("addMessage to database error");
        
        return id;
    } 

    /**
     * Get message from database by database message Id.
     * @param msgId
     * @return 
     */
    @Override
    public MessageForServer getMessage(int msgId) throws DataAccessException {      
        MessageForServer message = (MessageForServer) getHibernateTemplate().execute(new HibernateCallback<MessageForServer>() {
            @Override
            public MessageForServer doInHibernate(Session session) throws HibernateException {                
                MessageForServer message = (MessageForServer) session.get(MessageForServer.class, msgId);                
                return message;
            }
        });
        return message;
    }

    /**
     * Get all messages from database.
     * @return 
     */
    @Override
    public List<MessageForServer> getAllMessages() throws DataAccessException {
        List<MessageForServer> messages = new LinkedList<>();
        messages = (List<MessageForServer>) getHibernateTemplate().execute(new HibernateCallback<List<MessageForServer>>() {
            @Override
            public List<MessageForServer> doInHibernate(Session session) throws HibernateException {                
                List<MessageForServer> messages = session.createQuery("FROM MessageForServer").list();
                return messages;
            }
        });                
        return messages;
    } 

    /**
     * Delete specified message from database.
     * @param msgId Message ID which is suppossed to be delete.
     * @return true if message with specified ID exists and message was deleted.
     * @throws DataAccessException 
     */
    @Override
    public boolean remMessage(int msgId) throws DataAccessException {
        Boolean success=false;
        //I try to use lambda expression for call doInHibernate.
        //A little confusing to me.        
        success = getHibernateTemplate().execute((Session session) -> {                 
            MessageForServer message = (MessageForServer) session.get(MessageForServer.class, msgId);
            if(message != null)
            {
                //If message exists delete it.
                session.delete(message);
                //Unfortunately I do't know how to do it without worry about flushing data to database manually.
                session.flush(); 
                return true;
            }
            else
            {
                return false;                
            }
            
        });        
        if(success)
            LOG.info("Message with ID=" + msgId + " deleted successfully.");
        else
            LOG.error("remMessage with ID=" + msgId + " failed.");
        
        return success;
    }
    
}
