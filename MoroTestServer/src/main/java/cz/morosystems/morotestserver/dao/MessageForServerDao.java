/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestserver.dao;

import java.util.List;
import javax.ejb.Local;
import org.springframework.dao.DataAccessException;

/**
 * Interface for dao.
 * @author Tomas
 */
@Local
public interface MessageForServerDao {

    /**
     * Storing message into the database.
     * @param message Object to store.
     * @return database id of the inserted message.
     * @throws HibernateException When error occured.
     */
    Integer addMessage(MessageForServer message) throws DataAccessException;

    /**
     * Get message from database by database message Id.
     * @param msgId
     * @return 
     */
    MessageForServer getMessage(int msgId) throws DataAccessException;
    
    /**
     * Delete specified message from database.
     * @param msgId Message ID which is suppossed to be delete.
     * @return true if message with specified ID exists and message was deleted.
     * @throws DataAccessException 
     */
    boolean remMessage(int msgId) throws DataAccessException;

    /**
     * Get all messages from database.
     * @return 
     */
    List<MessageForServer> getAllMessages() throws DataAccessException;
    
}
