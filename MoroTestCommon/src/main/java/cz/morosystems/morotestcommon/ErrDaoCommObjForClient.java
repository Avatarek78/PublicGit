/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestcommon;

/**
 * Class of error message object for sending from server to client over Hessian.<BR>
 * This object will be sent to the client if the server fails when saving messag <BR>
 * to a database. (MessageCommObjForServer).
 * @author Tomas
 */
public class ErrDaoCommObjForClient extends MessageCommObjForClient{
    
    /**
     * Constructor.
     * @param errMessage String describing the error.
     */
    public ErrDaoCommObjForClient(String errMessage) {
        super(errMessage);
    }
    
}
