/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.morosystems.morotestclient.controller;

import cz.morosystems.morotestclient.model.MessageHistory;
import cz.morosystems.morotestclient.model.MessageHistoryItem;
import java.util.Date;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tomas
 */
@Controller
public class QuartzMsgHistoryController {
    
    private final Logger LOG=LogManager.getLogger(QuartzMsgHistoryController.class);
    
    @RequestMapping("/quartz_msg_history")
    public ModelAndView helloAjaxTest() {
        return new ModelAndView();
    }
    
    
    @RequestMapping(value = "/update_quartz_msg_history", method = RequestMethod.GET)
    @ResponseBody
    public String updateQuartzMsgHistory() {
        
        MessageHistory msgHistory = SendMessageWorker.getInstanceForScheduler().getMsgHistory();
        String strHtml = ""; 
        
        /** 
         *  <div style="margin: 0em 0em 0em 0em;">
            <label style="float:left;margin-right: 5px;">                    
                Quartz message history (last ${schedulerMessageHistoryListSize} messages. Maximum history size is ${schedulerMessageHistoryListMax})
            </label >
            <input type="button" onclick="location.href='/MoroTestClient'" value="Refresh history" style="float:left;font-weight: bold">
            </div>
            <table style="float: next;" width="650" cellspacing="1" cellpadding="1">                                        
                <th width="150">request date time</th>
                <th>request message</th>
                <th width="150">answer date time</th>
                <th>answer message</th>                
                <c:forEach items="${schedulerMessageHistoryList}" var="item">
                <tr>                    
                    <td><spring:eval expression="item.requestMessage.sentDate" /></td>
                    <td><c:out value="${item.requestMessage.strMessage}" /></td>
                    <td><spring:eval expression="item.answerMessage.sentDate" /></td>
                    <td><c:out value="${item.answerMessage.strMessage}" /></td>                       
                </tr>
                </c:forEach>                
            </table>
         */
        
        //This is terrible to build HTML manualy as a string. 
        //But I can't find a suitable component for build HTML.
        StringBuilder sb = new StringBuilder();
        sb.append("<div style=\"margin: 0em 0em 0em 0em;\">\r\n");
            sb.append("<label style=\"float:left;margin-right: 5px;\">\r\n");
            sb.append("Quartz message history (last ");
            sb.append("" + msgHistory.getHistorySize());
            sb.append(" messages. Maximum history size is ");
            sb.append("" + msgHistory.getHistoryMaxSize());
            sb.append(")\r\n");
            sb.append("</label >\r\n");
            sb.append("</div>\r\n");
            sb.append("<table style=\"float: next;\" width=\"650\" cellspacing=\"1\" cellpadding=\"1\">\r\n");
                sb.append("<th width=\"150\">request date time</th>\r\n");
                sb.append("<th>request message</th>\r\n");
                sb.append("<th width=\"150\">answer date time</th>\r\n");
                sb.append("<th>answer message</th>\r\n");        
                for(MessageHistoryItem itm:msgHistory.getHistory())
                {
                    sb.append("<tr>\r\n");
                    sb.append("<td>").append(itm.getRequestMessage().getFormatedSentDate()).append("</td>\r\n");
                    sb.append("<td>").append(itm.getRequestMessage().getStrMessage()).append("</td>\r\n");
                    sb.append("<td>").append(itm.getAnswerMessage().getFormatedSentDate()).append("</td>\r\n");
                    sb.append("<td>").append(itm.getAnswerMessage().getStrMessage()).append("</td>\r\n");
                    sb.append("</tr>\r\n");
                }
            sb.append("</table>\r\n");
        sb.append("</div>\r\n");
        
        strHtml = sb.toString();
        //LOG.info("updateQuartzMsgHistory" + new Date().toString());        
        return strHtml;
    }
    
}
