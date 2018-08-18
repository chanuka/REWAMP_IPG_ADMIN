<%-- 
    Document   : audit
    Created on : Oct 30, 2013, 12:09:11 PM
    Author     : chalitha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="/struts-jquery-tags" prefix="sj" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE HTML>

<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf" %>

        <script type="text/javascript">

            function viewformatter(cellvalue) {
                return "<a href='#' onClick='javascript:viewAuditTraceInit(&#34;" + cellvalue + "&#34;)' title='View Audit Record' ><img class='ui-icon ui-icon-newwin' style='display:inline-table;border:none;'/></a>";
            }
            
            function viewAuditTraceInit(auditId) {
                $("#viewdialog").data('auditId', auditId).dialog('open');
            }
            
            function searchAudit(param) {
                var userRole = $('#userRole').val();
                var description = $('#description').val();
                var section = $('#section').val();
                var ipgpage = $('#ipgpage').val();
                var task = $('#task').val();
                var ip = $('#ip').val();
                var fdate = $('#fdate').val();
                var tdate = $('#tdate').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        userRole: userRole,
                        description: description,
                        section: section,
                        ipgpage: ipgpage,
                        task: task,
                        ip: ip,
                        fdate: fdate,
                        tdate: tdate,
                        search: param
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});

                jQuery("#gridtable").trigger("reloadGrid");

            }
            ;
            function setdate() {
                  $("#fdate").datepicker("setDate", new Date());
                   $("#tdate").datepicker("setDate", new Date());
            }
            $.subscribe('completetopics', function (event, data) {
                var isGenerate = <s:property value="vgenerate"/>;
                var recors = $("#gridtable").jqGrid('getGridParam', 'records');
                if (recors > 0 && isGenerate == false) {
                    $('#view').button("enable");
                    $('#view1').button("enable");
                } else {
                    $('#view').button("disable");
                    $('#view1').button("disable");
                }
            });

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#viewdialog");
                $led.html("Loading..");
                $led.load("viewDetailSystemAudit.ipg?auditId=" + $led.data('auditId'));
            });

            function resetAllData() {
                $('#userRole').val("");
                $('#description').val("");
                $('#section').val("");
                $('#ipgpage').val("");
                $('#task').val("");
                $('#ip').val("");
                $('#fdate').val("");
                $('#tdate').val("");
                $("#gridtable").jqGrid('clearGridData', true);
                searchAudit(false);
            }
            
            function todoexel() {
                $('#reporttype').val("exel");
                form = document.getElementById('auditform');
                form.action = 'generateSystemAudit.ipg';
                form.submit();
                $('#view').button("disable");
                $('#view1').button("disable");
            }

            function todo() {
                $('#reporttype').val("pdf");
                form = document.getElementById('auditform');
                form.action = 'generateSystemAudit.ipg';
                form.submit();
                $('#view').button("disable");
                $('#view1').button("disable");
            }

        </script>
    </head>

    <body onload="setdate()">
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
                <s:set id="vgenerate" var="vgenerate"><s:property value="vgenerate" default="true"/></s:set>
                <s:set id="vsearch" var="vsearch"><s:property value="vsearch" default="true"/></s:set>
                <s:set id="vviewlink" var="vviewlink"><s:property value="vviewlink" default="true"/></s:set>
                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="auditform" method="post" action="*SystemAudit" theme="simple">
                        <s:hidden name="reporttype" id="reporttype"></s:hidden>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">From Date</label>
                                    <sj:datepicker cssClass="form-control" id="fdate" name="fdate" readonly="true" maxDate="+1d" changeYear="true"
                                                       buttonImageOnly="true" displayFormat="dd/mm/yy" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">To Date</label>
                                    <sj:datepicker cssClass="form-control" id="tdate" name="tdate" readonly="true" maxDate="+1d" changeYear="true"
                                                       buttonImageOnly="true" displayFormat="dd/mm/yy" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">User Role</label>
                                    <s:select cssClass="form-control" id="userRole" list="%{userRoleList}" name="userRole"
                                              headerKey="" headerValue="--Select--"
                                              listKey="userrolecode" listValue="description"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Description</label>
                                    <s:textfield cssClass="form-control" id="description" name="description"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Section</label>
                                    <s:select cssClass="form-control" id="section" list="%{sectionList}" name="section"
                                                  headerKey="" headerValue="--Select--"
                                                  listKey="sectioncode" listValue="description"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Page</label>
                                    <s:select cssClass="form-control" id="ipgpage" list="%{pageList}" name="ipgpage"
                                                  headerKey="" headerValue="--Select--"
                                                  listKey="pagecode" listValue="description"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Task</label>
                                    <s:select cssClass="form-control" id="task" list="%{taskList}" name="task"
                                                  headerKey="" headerValue="--Select--"
                                                  listKey="taskcode" listValue="description"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">IP Address</label>
                                    <s:textfield cssClass="form-control" id="ip" name="ip"/>
                                </div>
                            </div>
                        </div>
                    </s:form>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" onclick="searchAudit(true)"
                                               id="searchButton" disabled="#vsearch"/>
                                <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()"/>
                                <sj:submit cssClass="btn btn-sm btn-functions" button="true" value="View PDF"  name="view" id="view" onClick="todo()"  disabled="#vgenerate" />
                                <sj:submit cssClass="btn btn-sm btn-functions" button="true" value="View Excel"  name="view1" id="view1" onClick="todoexel()"  disabled="#vgenerate" />
                            </div>
                        </div>
                    </div>    
                </div>
                <sj:dialog                                     
                    id="viewdialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    position="center"
                    title="View Audit Traces"
                    onOpenTopics="openviewtasktopage" 
                    loadingText="Loading .."
                    width="900"
                    height="600"
                    dialogClass= "dialogclass"

                    /> 
                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>

                    <s:url var="listurl" action="listSystemAudit"/>
                    <sjg:grid 
                        id="gridtable"
                        caption="Audit Traces"
                        dataType="json"
                        href="%{listurl}"
                        pager="true"
                        gridModel="gridModel"
                        rowList="10,15,20"
                        rowNum="10"
                        autowidth="true"
                        rownumbers="true"
                        onCompleteTopics="completetopics"
                        rowTotal="false"
                        viewrecords="true">
                        <sjg:gridColumn name="id" title="View" width="60" align="center" formatter="viewformatter" hidden="#vviewlink"/>
                        <sjg:gridColumn name="id" index="systemauditid" title="ID" sortable="true"/>
                        <sjg:gridColumn name="userRole" index="ipguserrole.userrolecode" title="User Role" sortable="true"/>
                        <sjg:gridColumn name="description" index="description" title="Description" sortable="true"/>
                        <sjg:gridColumn name="section" index="ipgsection.sectioncode" title="Section" sortable="true"/>
                        <sjg:gridColumn name="page" index="ipgpage.pagecode" title="Page" sortable="true"/>
                        <sjg:gridColumn name="task" index="ipgtask.taskcode" title="Task" sortable="true"/>
                        <sjg:gridColumn name="ip" index="ip" title="IP Address" sortable="true"/>
                        <sjg:gridColumn name="lastUpdatedUser" index="lastupdateduser" title="Created User" sortable="true"/>
                        <sjg:gridColumn name="createdtime" index="createdtime" title="Created Time" sortable="true"/>
                        
                    </sjg:grid>
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