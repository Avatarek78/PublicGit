/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestclient.model;

import java.util.LinkedList;

/**
 * Message history class.
 * @author Tomas
 */
public class MessageHistory {
    
    /**List of history messages*/
    private final LinkedList<MessageHistoryItem> history = new LinkedList<>();
    /**Maximum size of history*/    
    private int historyMaxSize = 100;

    /**
     * 
     * @param historySize Maximum size of history.
     */
    public MessageHistory(int historySize) {
        this.historyMaxSize = historySize;
    }    
    
    /**
     * Add message to the history. If history reached the maximum size oldest <BR>
     * message will be removed from history before adding new one.
     * @param histMessage 
     */
    public synchronized void addMessageToHistory(MessageHistoryItem histMessage)
    {
        if(history.size() == historyMaxSize)        
            history.removeLast();
        
        history.addFirst(histMessage);
    }

    public LinkedList<MessageHistoryItem> getHistory() {
        return history;
    }    

    /**
     * Actual size of history.
     * @return 
     */
    public int getHistorySize() {
        return history.size();
    }
    
    /**
     * Maximum size of history.
     * @return 
     */
    public int getHistoryMaxSize() {
        return historyMaxSize;
    }
    
}
