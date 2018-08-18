<%-- 
    Document   : rule
    Created on : Jul 11, 2018, 3:16:21 PM
    Author     : sivaganesan_t
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

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Edit' onClick='javascript:editRuleInit(" + cellvalue + ")'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }


            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Delete' onClick='javascript:deleteRuleInit(" + cellvalue + ")'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }
            
            function editRuleInit(keyval) {
                $("#updatedialog").data('ruleId', keyval).dialog('open');
            }

            $.subscribe('openviewruletopage', function(event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailRule.ipg?ruleId=" + $led.data('ruleId'));
            });
            
            function editRule(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findRule.ipg',
                    data: {ruleId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {
                            $('#ruleId').val(data.ruleId);
                            $('#ruleScope').val(data.ruleScope);
                            $('#operator').val(data.operator);
                            $('#ruleType').val(data.ruleType);
                            $('#startValue').val(data.startValue);
                            $('#endValue').val(data.endValue);
                            $('#priority').val(data.priority);
                            $('#securityLevel').val(data.securityLevel);
                            $('#description').val(data.description);
                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");

                        }
                    },
                    error: function (data) {
                        $("#editerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function deleteRuleInit(keyval) {
                $('#divmsg').empty();
                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete Rule id :' + keyval + ' ?');
                return false;
            }

            function deleteRule(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteRule.ipg',
                    data: {ruleId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
                        //                        jQuery("#gridtable").trigger("reloadGrid");                      
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');

                    }
                });
            }

            function resetAllData() {
                $('#ruleScope').val("");
                $('#operator').val("");
                $('#ruleType').val("");
                $('#startValue').val("");
                $('#endValue').val("");
                $('#priority').val("");
                $('#securityLevel').val("");
                $('#description').val("");
                $('#triggersequence').val("");
                $('#status').val("");
                $('#divmsg').text("");   
                
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        ruleScope: '',
                        operator: '',
                        ruleType: '',
                        startValue: '',
                        endValue: '',
                        priority: '',
                        securityLevel: '',
                        description: '',
                        triggersequence: '',
                        status: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid"); 
            }

            function resetFieldData() {

                $('#iruleScope').val("");
                $('#ioperator').val("");
                $('#iruleType').val("");
                $('#ioperator').attr("disabled",true);
                $('#iruleType').attr("disabled",true);
                $('#istartValue').val("");
                $('#iendValue').val("");
                $('#isecurityLevel').val("");
                $('#idescription').val("");
                $('#itriggersequence').val("");
                $('#istatus').val("");

                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }
            
            function searchRule(){
                var ruleScope = $('#ruleScope').val();
                var operator = $('#operator').val();
                var ruleType = $('#ruleType').val();
                var startValue = $('#startValue').val();
                var endValue = $('#endValue').val();
                var priority = $('#priority').val();
                var securityLevel = $('#securityLevel').val();
                var description = $('#description').val();
                var triggersequence = $('#triggersequence').val();
                var status = $('#status').val();
                
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        ruleScope: ruleScope,
                        operator: operator,
                        ruleType: ruleType,
                        startValue: startValue,
                        endValue: endValue,
                        priority: priority,
                        securityLevel: securityLevel,
                        description: description,
                        triggersequence: triggersequence,
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
                <s:set var="vsearch"><s:property value="vsearch" default="true" /></s:set>

                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="rule" method="post" action="Rule" theme="simple" >
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Rule Scope Code </label>
                                    <s:select cssClass="form-control" id="ruleScope" list="%{rulescopeList}"  name="ruleScope" headerKey="" headerValue="--Select Rule Scope Code--" listKey="rulescopecode" listValue="description" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Operator </label>
                                    <s:textfield cssClass="form-control" id="operator" name="operator"  onkeyup="$(this).val($(this).val().replace(/[^<>=]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^<>=]/g,''))" maxLength="2"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Rule Type </label>
                                    <s:select cssClass="form-control" id="ruleType" list="%{ruletypeSerchList}"  name="ruleType" headerKey="" headerValue="--Select Rule Type--" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Start Value </label>
                                    <s:textfield cssClass="form-control" name="startValue" id="startValue" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9:/?._ ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9:/?._ ]/g,''))" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >End Value </label>
                                    <s:textfield cssClass="form-control" id="endValue"   name="endValue" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9:/?._  ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9:/?._  ]/g,''))"  />
                                </div>
                            </div>
<!--                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Priority </label>
                                    <%--<s:textfield cssClass="form-control" id="priority" name="priority" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />--%>
                                </div>
                            </div>-->
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Security Level </label>
                                    <s:select cssClass="form-control" id="securityLevel" list="%{securitylevelList}"  name="securityLevel" headerKey="" headerValue="--Select Security Level--" listKey="securitylevel" listValue="description" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Description </label>
                                    <s:textfield cssClass="form-control" id="description" name="description" maxLength="128" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                </div>
                            </div>    
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Trigger Sequence </label>
                                    <s:select cssClass="form-control" id="triggersequence"   name="triggersequence" list="%{triggersequenceList}" headerKey="" headerValue="--Select Trigger Sequence--" listKey="triggersequenceid" listValue="description" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                     <label class="control-label">Status </label>
                                        <s:select  cssClass="form-control" id="status" list="%{statusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                                </div>
                            </div>
                        </div>
                    </s:form>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" id="search"  disabled="#vsearch" onclick="searchRule()"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                            <div class="col-sm-3"></div>
                            <div class="col-sm-3 text-right">
                                <s:url var="addurlLink" action="viewpopupRule"/>
                                <div class="form-group">
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurlLink}"
                                        buttonIcon="ui-icon-newwin"
                                        disabled="#vadd"
                                        value="Add Rule"
                                        id="addButton_new"
                                        cssClass="btn btn-sm btn-functions" 
                                        />
                                </div>
                            </div>
                        </div>
                </div>

                <!-- Start delete confirm dialog box -->
                <sj:dialog 
                    id="deletedialog" 
                    buttons="{ 
                    'OK':function() { deleteRule($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Delete Rule"                            
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

                <!-- Start edit process dialog box -->
                <sj:dialog 
                    id="editerrordialog" 
                    buttons="{
                    'OK':function() { $( this ).dialog( 'close' );}                                    
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Edit error."
                    />   
                <sj:dialog                                     
                    id="remotedialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    title="Add Rule"                            
                    loadingText="Loading .."                            
                    position="center"                            
                    width="1000"
                    height="500"
                    dialogClass= "dialogclass"
                    cssStyle="overflow: visible;"
                    />
                <sj:dialog                                     
                    id="updatedialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    position="center"
                    title="Update Rule"
                    onOpenTopics="openviewruletopage" 
                    loadingText="Loading .."
                    width="1000"
                    height="500"
                    cssStyle="overflow: visible;"
                    dialogClass= "dialogclass"
                    /> 

                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <s:url var="listurl" action="listRule"/>
                    <sjg:grid
                        id="gridtable"
                        caption="IPG Rule"
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
                        <sjg:gridColumn name="ruleid" index="u.ruleid" title="Edit" width="60" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                        <sjg:gridColumn name="ruleid" index="u.ruleid" title="Delete" width="70" align="center" formatter="deleteformatter" hidden="#vdelete"/>
                        <sjg:gridColumn name="rulescope" index="u.ipgrulescope.description" title="Rule Scope"  sortable="true"/>
                        <sjg:gridColumn name="operator" index="u.operator" title="Operator"  sortable="true"/>
                        <sjg:gridColumn name="ruletype" index="u.ruletypecode" title="Rule Type"  sortable="true"/>
                        <sjg:gridColumn name="startvalue" index="u.startvalue" title="Start Value"  sortable="true"/>
                        <sjg:gridColumn name="endvalue" index="u.endvalue" title="End Value"  sortable="true"/>
                        <%--<sjg:gridColumn name="priority" index="u.priority" title="Priority"  sortable="true"/>--%>
                        <sjg:gridColumn name="securitylevel" index="u.ipgsecuritylevel.description" title="Security Level"  sortable="true"/>
                        <sjg:gridColumn name="description" index="u.description" title="Description"  sortable="true"/>
                        <sjg:gridColumn name="triggersequenceid" index="u.triggersequenceid" title="Trigger Sequence"  sortable="true"/>
                        <sjg:gridColumn name="status" index="u.ipgstatus.description" title="Status"  sortable="true"/>
                        <sjg:gridColumn name="createdtime" index="u.createdtime" title="Created Time"  sortable="true"/>
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
