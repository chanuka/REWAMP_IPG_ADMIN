<%-- 
    Document   : index
    Author     : chanuka
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML>
<html>
    <head>
        <!--<script src="${pageContext.request.contextPath}/resources/common/jquery.js"></script>-->
        <%@include file="/WEB-INF/pages/template/header.jspf" %>
    </head>
    <body>
        <div class="container">
            <div class="header"></div>
            <div class="main">
                <div class="content">
                    <jsp:include page="tree.jsp"/>
                </div>
                <div id="content1">

                    <jsp:include page="/WEB-INF/pages/template/subheader.jsp"/>
                                      
                </div>
                <div class="clearer"><span></span></div>
            </div>
        </div>

        <jsp:include page="footer.jsp"/>


    </body>
</html>
