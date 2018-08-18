<%-- 
    Document   : index
    Created on : Jun 7, 2013, 3:58:53 PM
    Author     : chanuka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
    String errorMessage = null;

    errorMessage = request.getParameter("message");

%>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IPG Login Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css" />
        

        <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/resources/css/niceforms-default.css" />
                <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ddaccordion.js"></script>
        <script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/niceforms.js"></script>
        
              <style type="text/css">
            .imgbutton:hover{                
/*                border:1px solid rgb(0,159,194);
                border-radius:6px;*/
            }
            
            .imgbutton:active{
                padding-top: 1px;
                padding-left: 1px;

            }
            .login_main{
                width: 35%;
                /*margin-top: 90px;*/
                margin-top: 10%;
            }
            
            .login_screen {
                
                margin-left: 34%;
            }
            .footer_login{
                    bottom: 0;
                    left: 0;
                    position: fixed;
                    right: 0;
                    z-index: 1000;
                    border-top-width: 1px;
                    text-align: center;
                }
          
        </style>  
        
        
        
    </head>
    <body>

        <div class="login_main login_screen">
<!--            <div class="header_login">
                <div class="logo"><a href="#"><img src="${pageContext.request.contextPath}/resources/img/logo.jpg" alt="" title="" border="0" /></a></div>
            </div>-->


<!--            <div class="login_form">
                <h3 align="center">IPG - Admin Panel Login</h3>
                <noscript>&nbsp;&nbsp;<div align="center"><font class="errormessage">JavaScript Disabled</font></div></noscript>-->
               

                
                
                           <form novalidate="novalidate" action="CheckUserLogin.ipg" method="post">

                <div  style="background-image: url('resources/img/login.png'); 
                      background-size: 447px 280px;
                      background-repeat:no-repeat;
                      height: 280px;
                      width: 447px; 
                      color: rgb(0, 0, 0); 
                      /*margin-top: 0px;*/                      
/*                      padding-left: 122px; 
                      padding-top: 139.2px;*/"
                      >
                    <div style="padding-left:0px; padding-top:0px; width:447px;  height:280px; ">
                    <input type="text" name="username" style="background-image: 'resources/img/login_98.gif'; width:284px; height: 17px; margin-left: 110px; margin-top: 145px;" />
                    <input type="password" id="password" name="password" style="background-image: 'resources/img/login_98.gif'; width:284px; height: 17px; margin-left: 110px; margin-top: 19px" /> 
                    <input type="image" src="resources/img/login_99.png" alt="Submit Form" style="width: 113px; height: 30px; margin-top: 14px; margin-left: 286px;" class="imgbutton" />
                    
                    </div>
                    <!--<button type="submit"  style="background:url(./resouces/images/login_img/login_99.png) center no-repeat; background-size: 100% 100%;  width: 142px; height: 28px; margin-top: 18px; margin-left: 143px;"/>-->
                </div>
</form>       
                
               
                                <s:if test="hasActionErrors()">
                    <div class="error_box_log">
                        <s:property default="errormessage" value="errormessage"></s:property>
                    </div>
                </s:if>
                
                <s:if test="hasActionMessages()">
                    <div class="valid_box">
                        <s:property default="errormessage" value="errormessage"></s:property>
                    </div>
                </s:if>
                
                


<!--                <form name="loginform" method="POST" action='CheckUserLogin.ipg' class="niceform">
                    <fieldset>
                        <dl>
                            <dt><label for="email">Username:</label></dt>
                            <dd><input type="text" name="username" size="54" /></dd>
                        </dl>
                        <dl>
                            <dt><label for="password">Password:</label></dt>
                            <dd><input type="password" name="password"  size="54" /></dd>
                        </dl>
                        <dl class="submit">
                            <input type="submit" name="submit" value="Enter" />
                        </dl>

                    </fieldset>
                </form>-->



            </div>
            <div class="footer_login">

                <div class="left_footer_login">IPG | Powered by <a href="http://www.epiclanka.net">EPIC Lanka Technologies</a></div>
                <!--<div class="right_footer_login"></div>-->

            </div>

        <!--</div>-->
        <script src="${pageContext.request.contextPath}/resources/common/jquery.js"></script>
        <!--<script src="${pageContext.request.contextPath}/resources/loginmain/js/login.js"></script>-->
    </body>
    
</html>

