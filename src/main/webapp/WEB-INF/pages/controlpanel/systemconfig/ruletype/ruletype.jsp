<%-- 
    Document   : ruletype
    Created on : Jul 11, 2018, 1:37:00 PM
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
                return "<a href='#' title='Edit' onClick='javascript:editRuleTypeInit(&#34;" + cellvalue + "&#34;&#44;&#34;" + rowObject.rulescope +"&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }


            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Delete' onClick='javascript:deleteRuleTypeInit(&#34;" + cellvalue + "&#34;&#44;&#34;" + rowObject.rulescope + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }
            
            function editRuleTypeInit(keyval,rulescope) {
                $("#updatedialog").data('ruletypeCode', keyval).data('rulescope', rulescope).dialog('open');
            }

            $.subscribe('openviewruletypetopage', function(event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailRuleType.ipg?ruletypeCode=" + $led.data('ruletypeCode') +"&rulescope=" + $led.data('rulescope'));
            });

            function editRuleType(keyval,rulescope) {
                if (keyval === "null") {
                    keyval = "";
                }
                if (rulescope === "null") {
                    rulescope = "";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/findRuleType.ipg',
                    data: {ruletypeCode: keyval,rulescope: rulescope},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {
                            $('#ruletypeCode').val(data.ruletypeCode);
                            $('#ruletypeCode').attr('readOnly', true);
                            $('#description').val(data.description);
                            $('#sortKey').val(data.sortKey);
                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");

                        }
                    },
                    error: function(data) {
                        $("#editerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function deleteRuleTypeInit(keyval,rulescope) {
                $('#divmsg').empty();
                $("#deletedialog").data('keyval', keyval).data('rulescope', rulescope).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete Rule Type ' + keyval + ' and Rule Scope '+rulescope+' ?');
                return false;
            }

            function deleteRuleType(keyval,rulescope) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteRuleType.ipg',
                    data: {ruletypeCode: keyval,rulescope: rulescope},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
//                        jQuery("#gridtable").trigger("reloadGrid");                      
                    },
                    error: function(data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');

                    }
                });
            }

            function resetAllData() {
                $('#ruletypeCode').val("");
                $('#rulescope').val("");
                $('#description').val("");
                $('#status').val("");
                $('#divmsg').text("");
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        ruletypeCode: '',
                        rulescope: '',
                        description: '',
                        status: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid"); 
            }

            function resetFieldData() {

                $('#iruletypeCode').val("");
                $('#irulescope').val("");
                $('#idescription').val("");
                $('#istatus').val("");
                
                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }
            function searchRuleType(){
                var ruletypeCode = $('#ruletypeCode').val();
                var rulescope = $('#rulescope').val();
                var description = $('#description').val();
                var status = $('#status').val();
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        ruletypeCode: ruletypeCode,
                        rulescope: rulescope,
                        description: description,
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
                    <s:form id="ruletype" method="post" action="RuleType" theme="simple" >

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Rule Type Code</label>
                                    <s:textfield cssClass="form-control" name="ruletypeCode" id="ruletypeCode" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Rule Scop</label>
                                    <s:select cssClass="form-control" id="rulescope" list="%{rulescopeList}"  name="ruleScope" headerKey="" headerValue="--Select Rule Scope Code--" listKey="rulescopecode" listValue="description" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Description</label>
                                    <s:textfield cssClass="form-control" id="description"   name="description" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"  />
                                </div>
                            </div>
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
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" id="search"  disabled="#vsearch" onclick="searchRuleType()"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                            <!--<div class="col-sm-3"></div>-->
<!--                            <div class="col-sm-3 text-right">
                                <%--<s:url var="addurlLink" action="viewpopupRuleType"/>--%>
                                <div class="form-group">
                                    <%--<sj:submit--%> 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurlLink}"
                                        buttonIcon="ui-icon-newwin"
                                        disabled="#vadd"
                                        value="Add Rule Type"
                                        id="addButton_new"
                                        cssClass="btn btn-sm btn-functions" 
                                        />
                                </div>
                            </div>-->
                        </div>
                </div>
                <!-- Start delete confirm dialog box -->
                <sj:dialog 
                    id="deletedialog" 
                    buttons="{ 
                    'OK':function() { deleteRuleType($(this).data('keyval'),$(this).data('rulescope'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Delete Rule Type"                            
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
                        title="Add Rule Type"                            
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
                        title="Update Rule Type"
                        onOpenTopics="openviewruletypetopage" 
                        loadingText="Loading .."
                        width="1000"
                        height="500"
                        cssStyle="overflow: visible;"
                        dialogClass= "dialogclass"
                        /> 

                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <s:url var="listurl" action="listRuleType"/>
                    <sjg:grid
                        id="gridtable"
                        caption="IPG Rule Type"
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
                        <sjg:gridColumn name="ruletypecode" index="u.id.ruletypecode" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                        <%--<sjg:gridColumn name="ruletypecode" index="u.id.ruletypecode" title="Delete" width="25" align="center" formatter="deleteformatter" hidden="#vdelete"/>--%>
                        <sjg:gridColumn name="ruletypecode" index="u.id.ruletypecode" title="Rule Type Code"  sortable="true"/>
                        <sjg:gridColumn name="rulescopeDes" index="u.id.rulescopecode" title="Rule Scope"  sortable="true"/>
                        <sjg:gridColumn name="description" index="u.description" title="Description"  sortable="true"/>
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

