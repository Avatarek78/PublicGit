/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestclient.model;

import cz.morosystems.morotestcommon.MessageCommObjForClient;
import cz.morosystems.morotestcommon.MessageCommObjForServer;

/**
 * Class for item data in the queue with a messages history.
 * @author Tomas
 */
public class MessageHistoryItem {
    
    private final MessageCommObjForServer requestMessage;
    private final MessageCommObjForClient answerMessage;  

    public MessageHistoryItem(MessageCommObjForServer requestMessage, MessageCommObjForClient answerMessage) {
        this.requestMessage = requestMessage;
        this.answerMessage = answerMessage;
    } 

    public MessageCommObjForServer getRequestMessage() {
        return requestMessage;
    }

    public MessageCommObjForClient getAnswerMessage() {
        return answerMessage;
    }    
}
