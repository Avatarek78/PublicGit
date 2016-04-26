/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestserver.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Object representation of the database table "MessageForServer".
 * @author Tomas
 */
@Entity
@Table(name = "MessageForServer")
@NamedQueries(@NamedQuery(name = "MessageForServer.getAll",query = "SELECT e FROM MessageForServer e"))
public class MessageForServer implements Serializable{    
    
    private static final Logger LOG=LogManager.getLogger(HibernateUtil.class);
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer ID;
    @Column
    private Date dateTime;
    
    private static final int MESSAGE_MAX_LEN = 128;
    @Column(length = MESSAGE_MAX_LEN)    
    private String message;

    public MessageForServer() {
        
    }
        
    public MessageForServer(String message, Date dateTime) {        
        this.dateTime = dateTime;
        this.message = message;        
    }
    
    public Integer getId() {
        return ID;
    }    

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dt) {
        this.dateTime = dt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }     
    
    /**
     * Get maximum length of the "message" field.
     * @return
     */
    public static int getMessageMaxLen() 
    {        
        return MESSAGE_MAX_LEN;
    }

    @Override
    public String toString() {
        return "MessageForServer{" + "ID=" + ID + ", dateTime=" + dateTime + ", message=" + message + '}';
    }    
}
