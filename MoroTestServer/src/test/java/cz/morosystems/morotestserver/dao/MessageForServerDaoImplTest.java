/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestserver.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Tomas
 */
public class MessageForServerDaoImplTest {
    
    private MessageForServerDaoImpl daoImpl;    
        
    public MessageForServerDaoImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        HibernateUtil.Init();
    }
    
    @AfterClass
    public static void tearDownClass() {
        HibernateUtil.Close();
    }   
    
    @Before
    public void setUp() {
        daoImpl = new MessageForServerDaoImpl();
    }
    
    @After
    public void tearDown() {
    }    

    /**
     * Test of addMessage method, of class MessageForServerDaoImpl.
     * Also tested methods getMessage, getAllMessages and remMessage when deleting test messages which is added in this test.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddMessage() throws Exception {
        System.out.println("addMessage");        
        for(int i=1;i < 4;i++)
        {
            MessageForServer message = new MessageForServer("test HibernateCallback" + i, new Date());
            Integer id = daoImpl.addMessage(message);
            //Test of testGetAllMessages() method.
            List<MessageForServer> allMessages = daoImpl.getAllMessages(); 
            if(allMessages.isEmpty())
                fail("Test of getAllMessages method not return any message.");                
            //Check if message exist in database.
            message = daoImpl.getMessage(id);
            if(message == null)            
                fail("Message with ID=" + id + " not found.");            
            else            
            {               
                //Delete test message from database.
                if(!daoImpl.remMessage(id))
                {
                    fail("Message with ID=" + id + " delete failed.");
                }
                message = daoImpl.getMessage(id);
                if(message != null)
                    fail("Message with ID=" + id + " delete failed.");                    
            }
        }
    }

    /**
     * Test of getMessage method, of class MessageForServerDaoImpl.
     * This is not necessary because this funcionality already tested by testAddMessage() method.
     */
    //@Test
    public void testGetMessage() throws Exception {
        System.out.println("getMessage");
        for(int msgId=1;msgId < 4;msgId++)
        {
            MessageForServer message = daoImpl.getMessage(msgId);
            if(message == null)            
                fail("Message with ID=" + msgId + " not found.");            
            else            
                System.out.println("Message with ID=" + msgId + " is \"" + message.getMessage() + "\"");            
        }
    }

    /**
     * Test of getAllMessages method, of class MessageForServerDaoImpl.
     * This is not necessary because this funcionality already tested by testAddMessage() method.
     */
    //@Test
    public void testGetAllMessages() throws Exception {
        System.out.println("testGetAllMessages");
        List<MessageForServer> allMessages = daoImpl.getAllMessages();  
        if(allMessages.isEmpty())
            fail("No messages");
        else
            System.out.println("testGetAllMessages get " + allMessages.size() + " rows");
    }      
    
}
