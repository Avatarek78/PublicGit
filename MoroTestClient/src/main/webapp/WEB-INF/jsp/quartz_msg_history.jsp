<%-- 
    Document   : ajax
    Created on : 25.3.2016, 21:27:57
    Author     : Tomas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <TITLE>Quartz message history</TITLE>

        <spring:url value="/resources/js/jquery.1.10.2.min.js"
                var="jqueryJs" />
        <script src="${jqueryJs}"></script>
        <script type="text/javascript">
            function updateQuartzMsgHistory() {                
                $.ajax({
                    url : 'update_quartz_msg_history.html',
                    success : function(data) {
                        $('#result').html(data);
                    }
                });
            }
        </script>
        <script type="text/javascript">
            $.ajaxSetup({ cache: false });
            var intervalId = 0;
            intervalId = setInterval(updateQuartzMsgHistory, 3000);
            updateQuartzMsgHistory();
        </script>
    </head>

    <body>        
        <div id="result"></div>
    </body>
</html>
