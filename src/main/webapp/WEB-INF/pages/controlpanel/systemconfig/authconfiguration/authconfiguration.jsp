<%-- 
    Document   : authconfiguration
    Created on : Jul 9, 2018, 10:19:08 AM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf" %>


        <script type="text/javascript">

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Edit' onClick='javascript:editAuthConfigInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }


            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Delete' onClick='javascript:deleteAuthConfigInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function editAuthConfigInit(keyval) {
                $("#updatedialog").data('pkey', keyval).dialog('open');
            }

            $.subscribe('openviewauthconfigtopage', function(event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailAuthConfig.ipg?pkey=" + $led.data('pkey'));
            });


            function deleteAuthConfigInit(keyval) {
                $('#divmsg').empty();
                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete Auth Config ' + keyval + ' ?');
                return false;
            }

            function deleteAuthConfig(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteAuthConfig.ipg',
                    data: {pkey: keyval},
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
                $('#cardassociation').val("");
                $('#txstatus').val("");
                $('#ecivalue').val("");
                $('#status').val("");
                $('#divmsg').text("");

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        cardassociation: '',
                        txstatus: '',
                        ecivalue: '',
                        status: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {

                $('#icardassociation').val("");
                $('#itxstatus').val("");
                $('#iecivalue').val("");
                $('#istatus').val("");

                jQuery("#gridtable").trigger("reloadGrid");
            }
            function searchAuthConfig() {
                $('#divmsg').text("");
                var cardassociation = $('#cardassociation').val();
                var txstatus = $('#txstatus').val();
                var ecivalue = $('#ecivalue').val();
                var status = $('#status').val();
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        cardassociation: cardassociation,
                        txstatus: txstatus,
                        ecivalue: ecivalue,
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
                <s:set var="vupdatelink"> <s:property value="vupdatelink" default="true" /></s:set>
                <s:set var="vdelete"><s:property value="vdelete" default="true" /></s:set>
                <s:set var="vsearch"><s:property value="vsearch" default="true" /></s:set>

                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="authconfig" method="post" action="AuthConfig" theme="simple" >

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Card Association </label>
                                    <s:select cssClass="form-control" id="cardassociation" list="%{cardassociationList}"  name="cardassociation" headerKey="" headerValue="--Select Card Association--" listKey="cardassociationcode" listValue="description" />

                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Transaction Status </label>
                                    <s:select cssClass="form-control" id="txstatus" list="%{txstatusMap}"  name="txstatus" headerKey="" headerValue="--Select Authentication Status--" listKey="key" listValue="value" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >ECI Value </label>
                                    <s:select cssClass="form-control" id="ecivalue" list="%{ecivalueList}"  name="ecivalue" headerKey="" headerValue="--Select ECI Value--" listKey="value" listValue="value" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Authentication Status </label>
                                    <s:select cssClass="form-control" id="status" list="%{authstatusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                                </div>
                            </div>
                        </div>
                    </s:form>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" id="search"  disabled="#vsearch" onclick="searchAuthConfig()"/>
                                <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                            </div>
                        </div>
                        <div class="col-sm-3"></div>
                        <div class="col-sm-3 text-right">
                            <s:url var="addurlLink" action="viewpopupAuthConfig"/>
                            <div class="form-group">
                                <sj:submit 
                                    openDialog="remotedialog"
                                    button="true"
                                    href="%{addurlLink}"
                                    buttonIcon="ui-icon-newwin"
                                    disabled="#vadd"
                                    value="Add Authentication Configuration"
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
                    'OK':function() { deleteAuthConfig($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Delete Authentication Configuration"                            
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
                    title="Add Authentication Configuration"                            
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
                    title="Update Authentication Configuration"
                    onOpenTopics="openviewauthconfigtopage" 
                    loadingText="Loading .."
                    width="1000"
                    height="500"
                    cssStyle="overflow: visible;"
                    dialogClass= "dialogclass"
                    /> 

                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <s:url var="listurl" action="listAuthConfig"/>
                    <sjg:grid
                        id="gridtable"
                        caption="IPG Authentication Configuration"
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
                        <sjg:gridColumn name="pkey" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                        <sjg:gridColumn name="pkey" title="Delete" width="25" align="center" formatter="deleteformatter" hidden="#vdelete"/>
                        <sjg:gridColumn name="cardassociation" index="u.id.cardassociationcode" title="Card Association"  sortable="true"/>
                        <sjg:gridColumn name="txstatus" index="u.id.txstatus" title="Transaction Status"  sortable="true"/>
                        <sjg:gridColumn name="ecivalue" index="u.id.ecivalue" title="ECI Value"  sortable="true"/>                            
                        <sjg:gridColumn name="status" index="u.status" title="Authentication Status"  sortable="true"/>
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

