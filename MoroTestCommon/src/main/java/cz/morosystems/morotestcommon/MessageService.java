/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestcommon;

/**
 * Service for sending message from client to server and for receiving reply message from the server.
 * Common for server and client application.
 * @author Tomas
 */
public interface MessageService {
    
    /**
     * Send message from client to server.
     * @param requestMessage The message from client.
     * @return Reply message from the server to the client.
     */
    public MessageCommObjForClient sendMessage(MessageCommObjForServer requestMessage);
    
}
