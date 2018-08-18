<%-- 
    Document   : defaultaccesscontroler
    Created on : Aug 15, 2013, 12:20:34 PM
    Author     : chanuka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <script type="text/javascript">
            function loadonstart(){
                window.location="${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
            }
            window.onload=loadonstart(); 
        </script>
    </head>
    <body>
        
    </body>
</html>
