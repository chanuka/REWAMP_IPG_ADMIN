<%-- 
    Document   : passwordreset
    Created on : Aug 22, 2013, 3:47:46 PM
    Author     : chanuka
--%>

<%-- 
    Document   : task
    Created on : Aug 7, 2013, 11:41:49 AM
    Author     : chanuka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML>

<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf" %>

        <script type="text/javascript">

            function resetAllData() {

                $('#newpwd').val("");
                $('#renewpwd').val("");
                $('#currpwd').val("");
                $('#divmsg').text("");
            }

            function resetFieldData() {

                $('#newpwd').val("");
                $('#renewpwd').val("");
                $('#currpwd').val("");
            }
            $(document).ready(function () {
                $.each($('.tooltip'), function (index, element) {
                    $(this).remove();
                });
                $('[data-toggle="tooltip"]').tooltip({
                    'placement': 'right'

                });
            });

        </script>
        
        <style>
            .tooltip {
               
                background-color: black;
                color: #fff;
                border-radius: 6px;
                padding: 5px 0;
                white-space: pre-line;
                height: auto;
            }
            .tooltip-inner {
                min-width: 375%;
                max-width: 100%; 
                text-align: left;
            }
            
        </style>
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

                    <s:set var="vupdatepwd"><s:property value="vupdatepwd" default="true"/></s:set>
                        <!-- Form -->
                        <div class="content-form">
                        <s:form action="PasswordReset" theme="simple" method="post" id="pwdResetform" autocomplete="off">
                            <s:hidden name="husername" id="husername" />
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >User Name <span style="color: red">*</span></label>
                                        <s:textfield cssClass="form-control" name="username" id="username" disabled="true"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >User Role <span style="color: red">*</span></label>
                                        <s:textfield cssClass="form-control" id="userrole"  name="userrole" disabled="true"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Current Password<span style="color: red">*</span></label>
                                        <s:password cssClass="form-control" name="currpwd" id="currpwd" autocomplete="off"/>                           
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >New Password<span style="color: red">*</span></label>
                                        <s:password cssClass="form-control" name="newpwd" id="newpwd" data-toggle="tooltip" data-html="true" title="%{pwtooltip}"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Retype New Password<span style="color: red">*</span></label>
                                        <s:password cssClass="form-control" name="renewpwd" id="renewpwd"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                                </div>
                            </div>

                            <s:url var="pwreseturl" action="UpdateChangePassword"/>      
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <sj:submit 
                                            button="true"
                                            href="%{pwreseturl}"
                                            disabled="#vupdatepwd"
                                            value="Accept"
                                            targets="divmsg"
                                            cssClass="btn btn-sm btn-functions"
                                            />      
                                        <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" onClick="resetAllData()"/>     
                                    </div>
                                </div>
                            </div>
                        </s:form>
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


