/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestserver.servlet;

import cz.morosystems.morotestcommon.MessageCommObjForServer;
import javax.servlet.ServletConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 * I don't know how to test Servlet.
 * @author Tomas
 */
public class MessageServletTest extends Mockito{
    
    MessageServlet msgServlet;
    
    public MessageServletTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        //This also not working.
//        try
//        {
//            msgServlet = mock(MessageServlet.class);
//        }
//        catch(Exception ex)
//        {
//            fail(ex.getMessage());
//        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of sendMessage method, of class MessageServlet.
     */
    //@Test
    public void testSendMessage() {
        System.out.println("sendMessage");
        MessageCommObjForServer requestMessage = new MessageCommObjForServer("test");
        msgServlet.sendMessage(requestMessage);
    }

    /**
     * Test of getServletInfo method, of class MessageServlet.
     */
    //@Test
    public void testGetServletInfo() {
        System.out.println("getServletInfo");
        MessageServlet instance = new MessageServlet();
        String expResult = "";
        String result = instance.getServletInfo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of init method, of class MessageServlet.
     */
    //@Test
    public void testInit() throws Exception {
        System.out.println("init");
        ServletConfig config = null;
        MessageServlet instance = new MessageServlet();
        instance.init(config);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }    

    /**
     * Test of destroy method, of class MessageServlet.
     */
    //@Test
    public void testDestroy() {
        System.out.println("destroy");
        MessageServlet instance = new MessageServlet();
        instance.destroy();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
