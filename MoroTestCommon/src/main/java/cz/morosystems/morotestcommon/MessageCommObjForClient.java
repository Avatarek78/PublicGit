/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestcommon;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;

/**
 * Class of message object for sending from server to client over Hessian.<BR>
 * This message tells the client "your request has been processed successfully".<BR>
 * Class structure is the same as MessageCommObjForClient. I created another class <BR>
 * for future ability to change its structure completely different.
 * @author Tomas
 */
public class MessageCommObjForClient implements Serializable{
            
    /**
     * Text content of message.
     */
    private String strMessage = "";
    
    /**
     * Prefered date time format pattern.
     */
    public static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy HH:mm:ss:SSS";
    
    /**
     * Date when the message was sent.
     */
    @DateTimeFormat(pattern=DATE_FORMAT_PATTERN)
    private Date sentDate;
    
    /**
     * Message constructor.
     * @param strMessage String content of the message.
     */
    public MessageCommObjForClient(String strMessage) { 
        this.strMessage = strMessage;
    } 

    /**
     * 
     * @return Text content of message.
     */
    public String getStrMessage() {
        return strMessage;
    }

    /**
     * 
     * @return Date when the message was sent.
     */
    public Date getSentDate() {
        return sentDate;
    }    
    
    /**
     * Formated string representation of the sent date. I do not know why, but annotation <BR>
     * himself "@DateTimeFormat" does not work as I expected.
     * @return String representation of the sent date formated by DATE_FORMAT_PATTERN.
     */
    public String getFormatedSentDate() {
        String strFormatedDate = "";
        if(sentDate != null)
        {
            DateFormatter df = new DateFormatter(DATE_FORMAT_PATTERN);
            strFormatedDate = df.print(sentDate, Locale.getDefault());
        }
        return strFormatedDate;
    }

    /**
     * Set the date when the message was sent to recipient.
     * @param sentDate Date when the message was sent.
     */
    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }    
}
