/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestclient.model;

import cz.morosystems.morotestcommon.MessageCommObjForClient;
import cz.morosystems.morotestcommon.MessageCommObjForServer;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomas
 */
public class MessageHistoryTest {
    
    public MessageHistoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addMessageToHistory method, of class MessageHistory.
     */
    @Test
    public void testAddMessageToHistory() {
        System.out.println("addMessageToHistory");
        MessageHistoryItem histMessage = new MessageHistoryItem(new MessageCommObjForServer(""), new MessageCommObjForClient(""));
        MessageHistory instance = new MessageHistory(1);
        instance.addMessageToHistory(histMessage);
        instance.addMessageToHistory(histMessage);
        if(instance.getHistorySize() == 0)
            fail("History is empty!");
        else if(instance.getHistorySize() > 1)
            fail("History is not limited to expected maximum size!");
    }
    
}
