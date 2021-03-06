<%-- 
    Document   : task
    Created on : Jul 18, 2018, 4:34:39 PM
    Author     : harini_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf" %>

        <script type="text/javascript">

            function editformatter(cellvalue) {
                return "<a href='#' title='Edit' onClick='javascript:editTaskInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function deleteformatter(cellvalue) {
                return "<a href='#' title='Delete' onClick='javascript:deleteTaskInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function editTaskInit(keyval) {
                $("#updatedialog").data('taskCode', keyval).dialog('open');
            }

            //load pop up for update
            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailTask.ipg?taskCode=" + $led.data('taskCode'));
            });

            function deleteTaskInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete task ' + keyval + ' ?');
                return false;
            }

            function deleteTask(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteTask.ipg',
                    data: {taskCode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
                        ;
                    },
                    error: function (data) {
                        $("#deleteerrordialog").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function resetAllData() {
                $('#taskCode').val("");
                $('#description').val("");
                $('#sortKey').val("");
                $('#status').val("");
                $('#divmsg').text("");
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        taskCode: '',
                        description: '',
                        sortKey: '',
                        status: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {
                $('#taskCode').val("");
                $('#description').val("");
                $('#sortKey').val("");
                $('#status').val("");

                jQuery("#gridtable").trigger("reloadGrid");

            }
            function searchTask() {
                $('#divmsg').text("");
                var taskCode = $('#taskCode').val();
                var description = $('#description').val();
                var sortKey = $('#sortKey').val();
                var status = $('#status').val();

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        taskCode: taskCode,
                        description: description,
                        sortKey: sortKey,
                        status: status,
                        search: true
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
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
                        <s:div id="divmsg">
                            <s:actionmessage theme="jquery"/>
                            <s:actionerror theme="jquery"/>
                        </s:div>
                    </div>
                    <s:set id="vadd" var="vadd"><s:property value="vadd" default="true"/></s:set>
                    <s:set var="vupdatebutt"><s:property value="vupdatebutt" default="true"/></s:set>
                    <s:set var="vupdatelink"><s:property value="vupdatelink" default="true"/></s:set>
                    <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>
                    <s:set var="vsearch"><s:property value="vsearch" default="true"/></s:set>
                        <!-- Form -->
                        <div class="content-form">
                        <s:form id="tasksearch" method="post" action="Task" theme="simple" >

                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Task Code</label>
                                        <s:textfield cssClass="form-control" name="searchTaskCode" id="taskCode" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Description</label>
                                        <s:textfield cssClass="form-control" name="searchDescription" id="description" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Sort Key</label>
                                        <s:textfield cssClass="form-control" name="searchSortKey" id="sortKey" maxLength="3" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Status</label>
                                        <s:select cssClass="form-control" name="searchStatus" id="status" list="%{statusList}" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" targets="divmsg" id="search"  disabled="#vsearch" onclick="searchTask()"/>
                                        <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                    </div>
                                </div>
                                <div class="col-sm-3"></div>
                                <div class="col-sm-3 text-right">
                                    <s:url var="addurlLink" action="viewpopupTask"/>
                                    <div class="form-group">
                                        <sj:submit 
                                            openDialog="remotedialog"
                                            button="true"
                                            href="%{addurlLink}"
                                            buttonIcon="ui-icon-newwin"
                                            disabled="#vadd"
                                            value="Add Task"
                                            id="addButton_new"
                                            cssClass="btn btn-sm btn-functions" 
                                            />
                                    </div>
                                </div>
                            </div>
                        </s:form>
                    </div>

                    <!-- Start delete confirm dialog box -->
                    <sj:dialog 
                        id="deletedialog" 
                        buttons="{ 
                        'OK':function() { deleteTask($(this).data('keyval'));$( this ).dialog( 'close' ); },
                        'Cancel':function() { $( this ).dialog( 'close' );} 
                        }" 
                        autoOpen="false" 
                        modal="true" 
                        title="Delete Task"                            
                        />
                    <!-- Start delete process dialog box -->
                    <sj:dialog 
                        id="deletesuccdialog" 
                        buttons="{
                        'OK':function() { $( this ).dialog( 'close' );}
                        }"  
                        autoOpen="false" 
                        modal="true" 
                        title="Deleting Process." 
                        />
                    <!-- Start delete error dialog box -->
                    <sj:dialog 
                        id="deleteerrordialog" 
                        buttons="{
                        'OK':function() { $( this ).dialog( 'close' );}                                    
                        }" 
                        autoOpen="false" 
                        modal="true" 
                        title="Delete error."
                        />
                    <!-- Start update error dialog box -->
                    <sj:dialog 
                        id="editerrordialog" 
                        buttons="{
                        'OK':function() { $( this ).dialog( 'close' );}                                    
                        }" 
                        autoOpen="false" 
                        modal="true" 
                        title="Edit error."
                        />   
                    <!-- Start add dialog box -->
                    <sj:dialog                                     
                        id="remotedialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        title="Add Task"                            
                        loadingText="Loading .."                            
                        position="center"                            
                        width="1000"
                        height="500"
                        dialogClass= "dialogclass"
                        cssStyle="overflow: visible;"
                        />
                    <!-- Start update dialog box -->
                    <sj:dialog                                     
                        id="updatedialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        position="center"
                        title="Update Task"
                        onOpenTopics="openviewtasktopage" 
                        loadingText="Loading .."
                        width="1000"
                        height="500"
                        cssStyle="overflow: visible;"
                        dialogClass= "dialogclass"
                        /> 

                    <div class="content-table">
                        <!--this div for triangles: don't delete-->
                        <div></div>
                        <s:url var="listurl" action="listTask"/>
                        <sjg:grid
                            id="gridtable"
                            caption="Task Management"
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
                            viewrecords="true"
                            >
                            <sjg:gridColumn name="taskCode" index="t.taskcode" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                            <sjg:gridColumn name="taskCode" index="t.taskcode" title="Delete" width="40" align="center" formatter="deleteformatter" hidden="#vdelete"/>
                            <sjg:gridColumn name="taskCode" index="t.taskcode" title="Task Code"  sortable="true"/>
                            <sjg:gridColumn name="description" index="t.description" title="Description"  sortable="true"/>
                            <sjg:gridColumn name="sortKey" index="t.sortkey" title="Sort Key"  sortable="true"/>                            
                            <sjg:gridColumn name="status" index="t.ipgstatus.description" title="Status"  sortable="true"/>
                            <sjg:gridColumn name="createdTime" index="t.createdtime" title="Created Time"  sortable="true"/>                              
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
