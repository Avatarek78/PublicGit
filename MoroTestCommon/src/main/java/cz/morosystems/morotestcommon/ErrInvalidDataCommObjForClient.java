/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestcommon;

/**
 * Class of error message object for sending from server to client over Hessian.<BR>
 * This object will be sent on the client if server will be detect validation error <BR>
 * in received  message. (MessageCommObjForServer).
 * @author Tomas
 */
public class ErrInvalidDataCommObjForClient extends MessageCommObjForClient{
    
    /**
     * Constructor.
     * @param errMessage String describing the error.
     */
    public ErrInvalidDataCommObjForClient(String errMessage) {
        super(errMessage);
    }
    
}
