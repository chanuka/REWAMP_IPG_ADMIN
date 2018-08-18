<%-- 
    Document   : multiaccess
    Created on : Aug 15, 2013, 10:16:56 AM
    Author     : chanuka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <script type="text/javascript">
            function loadonstart(){
                window.location="${pageContext.request.contextPath}/LogoutUserLogin.ipg?message=error2";
            }
            window.onload=loadonstart(); 
        </script>
    </head>
    <body>
    </body>
</html>
