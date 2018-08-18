<%-- 
    Document   : index_new
    Created on : Sep 14, 2017, 12:54:05 PM
    Author     : prathibha_s
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Project</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--===============================================================================================-->	
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/nav/images/favicon.png">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/fonts/iconic/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/css/util.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/css/main.css">


        <!--===============================================================================================-->

        <script>

            window.localStorage.removeItem('link');
            window.localStorage.removeItem('sublink');
            window.localStorage.removeItem("scroll");

            function clearCookie() {
                window.localStorage.removeItem("selectedsec");
                window.localStorage.removeItem("selectedpage");
                window.localStorage.removeItem("scroll");
            }
            clearCookie();


            function formSubmit() {
                window.localStorage.removeItem("item");
                $("#formES").submit();
            }
//            var ids = window.localStorage.getItem("item");
//            alert("login "+ ids);
//            $("#" + ids).removeClass("active");


            function encryp() {
                if ($('#password').val() != "") {
                    var ps = $('#password').val();
                    $('#password').val(CryptoJS.MD5(ps));
                    //                    var value = '&lt;%= request.getMethod() %&gt;';
                    //                    alert(CryptoJS.MD5(ps));

                }
            }

//            function sss(obj) {
//                $(obj).html("AUTHENTICATING <i style='margin-left:5px;' class='zmdi zmdi zmdi-chart-donut zmdi-hc-1x zmdi-hc-spin'></i>");
//            }



        </script>
    </head>
    <body>

        <div class="limiter">
            <div class="container-login100">
                <div class="wrap-login100">
                    <form class="login100-form validate-form" id="formES" novalidate="novalidate" action="CheckUserLogin.ipg" method="post">
                        <span class="login100-form-title ">
                            IPG
                        </span>
                        <span class="login100-form-title2 p-b-48">
                            Epic Internet Payment Gateway
                        </span>

                        <div class="wrap-input100 validate-input" data-validate = "Enter Username">
                            <input class="input100" type="text" name="username">
                            <span class="focus-input100" data-placeholder="Username"></span>
                        </div>

                        <div class="wrap-input100 validate-input" data-validate="Enter password">
                            <input class="input100" type="password" name="password">
                            <span class="focus-input100" data-placeholder="Password"></span>
                        </div>

                        <div class="container-login100-form-btn">
                            <div class="wrap-login100-form-btn">
                                <div class="login100-form-bgbtn"></div>
                                <button class="login100-form-btn">
                                    Login
                                </button>
                            </div>
                        </div>

                        <div class="text-center p-t-115">
                            <span class="txt1" style="color: #f32020">
                                <s:if test="hasActionErrors()">
                                    <div class="error-dis">
                                        <i class="zmdi zmdi-alert-triangle mdc-text-red"></i>
                                        <s:actionerror cssStyle="list-style:none;"/></i> 
                                    </div>
                                </s:if>
                                <s:if test="hasActionMessages()">
                                    <div class="error-dis">
                                        <i class="zmdi zmdi-alert-triangle mdc-text-red"></i>
                                        <s:actionmessage cssStyle="list-style:none"/></i>
                                    </div>  
                                </s:if>
                            </span>
                        </div>
                    </form>
                </div>
                <div style="text-align: center;position: absolute;bottom: 20px;font-size: 10px" >Epic Lanka (Pvt) Ltd</div>
            </div>

        </div>


        <div id="dropDownSelect1"></div>

        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/jquery/jquery-3.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/js/main.js"></script>


    </body>
</html>
