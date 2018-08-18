<%-- 
    Document   : welcomepage
    Created on : Oct 2, 2014, 12:20:55 PM
    Author     : thushanth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/nav/images/favicon.png">

        <title>Welcome To EPIC Internet Payment Gateway</title>

        <style>
            @import "resources/nav/css/fonts.css";
            body,html{
                background: #E7E6E6;
                margin: 0;
                padding: 0;
                height: 100%;
            }
            .main{
                margin: 0 auto;
                background: #E7E6E6;
                display: table;
                height: 100%;
            }
            .box{
                display: table-cell;
                vertical-align: middle;
                text-align: center;
            }
            .box > img{
                cursor: pointer;
            }
            .box > div{
                padding: 10px;
                font-family: Raleway;
                font-size: 13px;
                color: gray;
            }
        </style>
        <script type="text/javascript">

            //        window.onload=function(){
            //           var pathArray = window.location.pathname.split( '//' );
            //        var version= pathArray[0];
            //        version=version.replace(/\//g, "");
            //
            //        document.getElementById("versionno").innerHTML=version;
            ////        alert(secondLevelLocation);
            //        }

            function myFunction() {
                window.open("${pageContext.request.contextPath}/index.jsp", "MsgWindow", "width=1350, height=660,scrollbars=1,resizable=yes");
            }

        </script>
    </head>
    <body>
        <div class="main">
            <div class="box">
                <img src="${pageContext.request.contextPath}/resources/nav/images/gateway.png" width="100%" height="auto" alt="IPG Image" onclick="myFunction()"/>
                <div>Internet Payment Gateway</div>
            </div>
        </div>
    </body>
</html>
