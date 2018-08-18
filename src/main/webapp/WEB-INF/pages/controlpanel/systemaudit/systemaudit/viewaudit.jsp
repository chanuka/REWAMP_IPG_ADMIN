<%-- 
    Document   : viewaudit
    Created on : Aug 6, 2018, 4:26:57 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML>

<html>
    <head>

        <script type="text/javascript">
            function todox() {
                form = document.getElementById('viewaudit');
                form.action = 'individualReportSystemAudit.ipg';
            }

            function backToMain() {
                window.location = "${pageContext.request.contextPath}/viewSystemAudit.ipg?";
            }
        </script>
    </head>

    <body>
        <div class="content-message">
            <s:div id="divmsg">
                <s:actionmessage theme="jquery"/>
                <s:actionerror theme="jquery"/>
            </s:div>
        </div>

        <!-- Form -->
        <div class="content-form">
            <s:form id="viewaudit" method="post" action="SystemAudit" theme="simple" >
                <div class="row"> 
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label">Audit ID</label>
                            <s:label style="margin-bottom: 0px;" name="auditId"  value="%{auditDataBean.id}" cssClass="form-control"/>
                        </div>  
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label">User Role</label>
                            <s:label style="margin-bottom: 0px;" name="userRole"  value="%{auditDataBean.userRole}"  cssClass="form-control"/>
                        </div>
                    </div> 
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label">IP Address</label>
                            <s:label style="margin-bottom: 0px;" name="ip"  value="%{auditDataBean.ip}" cssClass="form-control"/>
                        </div>
                    </div>
                </div>
                <div class="row"> 
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label">Last Updated User</label>
                            <s:label style="margin-bottom: 0px;" name="user"  value="%{auditDataBean.lastUpdatedUser}" cssClass="form-control"/>
                        </div>  
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label">Last Updated Time</label>
                            <s:label style="margin-bottom: 0px;" name="created Date"  value="%{auditDataBean.lastUpdatedDate}" cssClass="form-control"/>
                        </div>
                    </div>    
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label">Task</label>
                            <s:label style="margin-bottom: 0px;" name="task"  value="%{auditDataBean.task}" cssClass="form-control"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="control-label">Section</label>
                            <s:label style="margin-bottom: 0px;" name="section"  value="%{auditDataBean.section}" cssClass="form-control"/>
                        </div> 
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="control-label">Page</label>
                            <s:label style="margin-bottom: 0px;" name="page"  value="%{auditDataBean.page}" cssClass="form-control"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label class="control-label">Description</label>
                            <s:textarea readonly="true" style="margin-bottom: 0px; word-break: break-all;background-color: white;" name="description"  value="%{auditDataBean.description}" cssClass="form-control"/>
                        </div>
                    </div>
                </div>        
                <div class="row"> 
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label class="control-label">Old Values</label>
                            <s:textarea readonly="true" style="margin-bottom: 0px; word-break: break-all;background-color: white;" name="oldvalue"  value="%{auditDataBean.oldvalue}" cssClass="form-control"/>
                        </div>
                    </div>
                </div>
                <div class="row"> 
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label class="control-label">New Values</label>
                            <s:textarea readonly="true" style="margin-bottom: 0px; word-break: break-all;background-color: white;" name="newvalue"  value="%{auditDataBean.newvalue}" cssClass="form-control"/>
                        </div>
                    </div>
                </div>

                <div class="row ">
                    <div class="col-sm-3">

                        <sj:submit 
                            button="true" 
                            id="viewindi" 
                            onclick="todox()" 
                            disabled="#vgenerate"
                            value="View PDF"
                            cssClass="btn btn-sm btn-functions"
                            />
                    </div>
                </div>
            </s:form>
        </div>
    </body>
</html>

