<%-- 
    Document   : userroleprivilege
    Created on : Jul 4, 2018, 1:27:52 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/pages/template/header.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>User Role Privilege</title>
        <script type="text/javascript">

            function assigndes() {
                var e = document.getElementById("userRole");
                var strUser = e.options[e.selectedIndex].text;
                $("#userRoleDes").val(strUser);
                $('#amessage').text("");
            }

        </script>
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
                        <s:div id="amessage">
                            <s:actionerror theme="jquery"/>
                            <s:actionmessage theme="jquery"/>
                        </s:div>
                        <div class="content-form">
                            <s:form id="userrolemgtmanage" method="post" action="ManageUserRolePrivilege" theme="simple" >
                                <div class="row">
                                    <div class="col-sm-3">
                                        <div class="form-group">
                                            <label class="control-label"  >User Role<span style="color: red">*</span></label>
                                            <s:hidden id="userRoleDes" name="userRoleDes"/>
                                            <s:select cssClass="form-control" id="userRole" list="%{userRoleList}"  name="userRole" listKey="userrolecode" listValue="description" />
                                        </div>
                                    </div>
                                </div>
                                <div class="row ">
                                    <div class="col-sm-3">
                                        <div class="form-group ">
                                            <s:radio id="Category" cssClass="radioClass" list="{'Sections'}" name="Category" value="'Sections'" />                                                
                                        </div>
                                    </div>
                                </div>
                                <div style="margin-bottom: 10px"></div>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <div class="form-group ">
                                            <s:radio id="Category" cssClass="radioClass" list="{'Pages'}" name="Category"  />
                                        </div>
                                    </div>
                                </div>
                                <div style="margin-bottom: 10px"></div>
                                <div class="row ">
                                    <div class="col-sm-3">
                                        <div class="form-group ">
                                            <s:radio id="Category" cssClass="radioClass" list="{'Operations'}" name="Category" />
                                        </div>
                                    </div>
                                </div>
                                <div style="margin-bottom: 10px"></div>
                                <div class="row" style="margin-bottom: 20px">
                                    <div class="col-md-12">
                                        <!--<label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>-->
                                    </div>
                                </div>        
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group"> 
                                            <s:url var="manageurl" action="ManageUserRolePrivilege"/> 
                                            <sj:submit 
                                                openDialog="remotedialog"
                                                button="true"
                                                onclick="assigndes()"
                                                href="%{manageurl}"
                                                value="Manage User Privilege"
                                                id="addButton"
                                                targets="remotedialog"   
                                                cssClass="btn btn-sm btn-functions"
                                                />
                                            <sj:submit cssClass="btn btn-sm btn-functions" cssStyle="visibility:hidden;" />

                                        </div>
                                    </div>
                                </div>
                                <!-- Start add dialog box -->
                                <sj:dialog                                     
                                    id="remotedialog"                                 
                                    autoOpen="false" 
                                    modal="true" 
                                    title="Manage User Privilege"                            
                                    loadingText="Loading .."                            
                                    position="center"                            
                                    width="900"
                                    height="550"
                                    dialogClass= "dialogclass"
                                    />  
                            </s:form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- end: PAGE -->
        </section>
        <footer>
            <!--for footer-->
            <jsp:include page="/WEB-INF/pages/template/footer.jsp"/>
        </footer>
    </body>
</html>
