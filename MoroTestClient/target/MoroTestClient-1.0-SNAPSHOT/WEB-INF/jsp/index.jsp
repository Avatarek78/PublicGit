
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="cp" value="${pageContext.request.servletContext.contextPath}" scope="request" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- This seting refresh page every 5 second but this is horible solution -->
        <!-- <meta http-equiv="Refresh" Content="5"> -->
        <title>MoroTestClient</title> 
        <link rel="stylesheet" type="text/css" href="${cp}/resources/css/site.css" />
    </head>
    <body>
        <h2>MoroTestClient</h2>        
        <form name="InputForm" method="POST"> 
            <label>Type a message for server:</label>
            <div>                
                <input type="text" name="TextInput" value="" size="100" maxlength="128" />
                <input type="submit" value="Send message" name="SendButt" style="font-weight: bold"/>
                <span class="${usermsgclass}">${usermessage}</span>
            </div> 
        </form>
        <div style="float: left;margin-top: 10px">   
            <label>
                User message history (last ${userMessageHistoryListSize} messages. Maximum history size is ${userMessageHistoryListMax})
            </label>
            <table width="650" cellspacing="1" cellpadding="1">                    
                <th width="150">request date time</th>
                <th>request message</th>
                <th width="150">answer date time</th>
                <th>answer message</th>                
                <c:forEach items="${userMessageHistoryList}" var="item">
                <tr>                    
                    <td><spring:eval expression="item.requestMessage.sentDate" /></td>
                    <td><c:out value="${item.requestMessage.strMessage}" /></td>
                    <td><spring:eval expression="item.answerMessage.sentDate" /></td>
                    <td><c:out value="${item.answerMessage.strMessage}" /></td>                       
                </tr>
                </c:forEach>                
            </table>  
        </div>
        <div style="float: left;"> 
            <jsp:include page="/quartz_msg_history.html" />
        </div> 
    </body>
</html>
