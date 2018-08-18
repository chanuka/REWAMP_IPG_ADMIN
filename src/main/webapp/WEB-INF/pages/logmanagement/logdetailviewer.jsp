<%-- 
    Document   : logdetailviewer
    Created on : 22/10/2014, 12:31:31 PM
    Author     : asela
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML>

<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf" %>
    </head>

    <body>

        <!--for background wallpaper-->
        <div class="background-wallpaper"></div>
        <!--for header-->
        <jsp:include page="/WEB-INF/pages/template/subheader.jsp"/>
        <!--for side navigation bar-->
        <jsp:include page="/WEB-INF/pages/template/tree.jsp"/>

        <!--section > main content-->
    <section> 
        <div class="container-main">
            <div class="content-main">

                <!--for bread Crumb-->
                <jsp:include page="/WEB-INF/pages/template/breadcrumb.jsp"/>

                <div class="content-message">
                    <s:div id="divmsg">
                        <s:actionmessage theme="jquery"/>
                        <s:actionerror theme="jquery"/>
                    </s:div>
                </div>

                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <div class="bodytext" style="padding:12px;" align="left">
                        <div id-="data">
                            <h1 style="background-color: #0094D6; color: white">

                                <s:property value="typeDes"/> &rarr; <s:property value="logFileCategory"/> &rarr; <s:property value="date"/></h1>

                        </div>
                        <textarea id="fileText" readonly="true" rows="30%" style="border: none; width: 100%; font-family: Courier New,Courier, monospace;">
                            <s:property value="content"/> 
                        </textarea>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <footer>
        <!--for footer-->
        <jsp:include page="/WEB-INF/pages/template/footer.jsp"/>
    </footer>
</body>
</html>


