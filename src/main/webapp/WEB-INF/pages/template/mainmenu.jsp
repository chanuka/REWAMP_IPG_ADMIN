<%-- 
    Document   : mainmenu
    Created on : Sep 8, 2017, 2:20:27 PM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/pages/template/header_top.jspf" %>
        <style>
            #mainmenu-img {
                margin: 23vh 0px 0 0vw;
                animation: rotate 10s infinite;
            }
            @-webkit-keyframes rotate{
                0% {transform: rotateY(0deg);}
                50% {transform: rotateY(360deg);}
            }
            
        </style>
    </head>
    <body>
        <div class="background-wallpaper"></div>
        <jsp:include page="/WEB-INF/pages/template/subheader.jsp"/>
        <jsp:include page="/WEB-INF/pages/template/tree.jsp"/>
        <section>
            <div class="container-main">
                <div class="content-main">
                    <div class="content-message">
                        <s:div id="divmsg" cssStyle="marging-top: -10px;">
                            <s:actionmessage theme="jquery"/>
                            <s:actionerror theme="jquery"/>
                        </s:div>
                    </div>
                    
                    <div style="text-align: center">
                        <img id="mainmenu-img" src="resources/nav/images/ipg.png" width="100" height="auto"/>
                        <!--<h4 style="margin:0">INTERNET PAYMENT GATEWAY  </h4>-->
                        <h6 style="margin:0">powered by</h6>
                        <h5 style="margin:0">Epic Lanka (Pvt) Ltd</h5>
                    </div>
                    
                </div>
            </div>
        </section>
        <footer>
            <jsp:include page="/WEB-INF/pages/template/footer.jsp"/>
        </footer>
    </body>        
</html>
