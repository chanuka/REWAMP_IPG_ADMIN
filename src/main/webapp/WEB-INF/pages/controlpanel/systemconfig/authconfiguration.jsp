<%-- 
    Document   : authconfiguration
    Created on : 02/10/2014, 3:59:11 PM
    Author     : badrika
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
                return "<a href='#' title='Edit' onClick='javascript:editAuthConfig(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }


            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Delete' onClick='javascript:deleteAuthConfigInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function editAuthConfig(keyval) {
                if (keyval === "null") {
                    keyval = "";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/findAuthConfig.ipg',
                    data: {pkey: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {
                            $('#pkey').val(data.pkey);
                            $('#cardassociation').val(data.cardassociation);
                            $('#cardassociation').attr('disabled', true);
                            $('#txstatus').val(data.txstatus);
                            $('#txstatus').attr('disabled', true);
                            $('#ecivalue').val(data.ecivalue);
                            $('#ecivalue').attr('disabled', true);
                            $('#status').val(data.status);
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
//                var s =  $("#addButton").is(':disabled');
//                if(s == false){
//                    $('#cardassociation').val("");
//                    $('#txstatus').val("");                
//                    $('#ecivalue').val("");                    
//                    $('#status').val("");                    
//                    $('#divmsg').text("");
//                    $('#addButton').button("enable");
//                    $('#updateButton').button("disable");
//                }else{
//                    var pkey =  $('#pkey').val();
//                    editAuthConfig(pkey);
//                }
//                

                a = $("#addButton").is(':disabled');
                u = $("#updateButton").is(':disabled');

                if (a == true && u == true) {
                    $('#cardassociation').val("");
                    $('#txstatus').val("");
                    $('#ecivalue').val("");
                    $('#status').val("");
                    $('#divmsg').text("");
                    $('#addButton').button("enable");
                    $('#updateButton').button("disable");
                } else if (a == true && u == false) {
                    var pkey = $('#pkey').val();
                    editAuthConfig(pkey);
                } else if (a == false && u == true) {
                    $('#cardassociation').val("");
                    $('#txstatus').val("");
                    $('#ecivalue').val("");
                    $('#status').val("");
                    $('#divmsg').text("");
                    $('#addButton').button("enable");
                    $('#updateButton').button("disable");
                }
            }

            function resetFieldData() {

                $('#cardassociation').val("");
                $('#cardassociation').attr('disabled', false);
                $('#txstatus').val("");
                $('#txstatus').attr('disabled', false);
                $('#ecivalue').val("");
                $('#ecivalue').attr('disabled', false);
                $('#status').val("");
                $('#status').attr('disabled', false);
                $('#addButton').button("enable");
                $('#updateButton').button("disable");

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

                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="authconfig" method="post" action="" theme="simple" >

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Card Association <span style="color: red">*</span></label>
                                    <s:select cssClass="form-control" id="cardassociation" list="%{cardassociationList}"  name="cardassociation" headerKey="" headerValue="--Select Card Association--" listKey="cardassociationcode" listValue="description" />
                                    <s:hidden name="pkey" id="pkey" ></s:hidden>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Transaction Status <span style="color: red">*</span></label>
                                    <s:select cssClass="form-control" id="txstatus" list="%{txstatusMap}"  name="txstatus" headerKey="" headerValue="--Select Authentication Status--" listKey="key" listValue="value" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >ECI Value <span style="color: red">*</span></label>
                                    <s:select cssClass="form-control" id="ecivalue" list="%{ecivalueList}"  name="ecivalue" headerKey="" headerValue="--Select ECI Value--" listKey="value" listValue="value" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Authentication Status <span style="color: red">*</span></label>
                                    <s:select cssClass="form-control" id="status" list="%{authstatusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                            </div>
                        </div> 

                        <s:url var="addurl" action="addAuthConfig"/>
                        <s:url var="updateurl" action="updateAuthConfig"/>

                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{addurl}" value="Add" targets="divmsg" id="addButton"  disabled="#vadd"/>
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{updateurl}" value="Update" targets="divmsg"   disabled="#vupdatebutt" id="updateButton"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()"  />
                                </div>
                            </div>
                        </div>
                    </s:form>
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


                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <s:url var="listurl" action="listAuthConfig"/>
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
                        <sjg:gridColumn name="cardassociation" index="u.id.cardassociationcode" title="Card Association"  sortable="true"/>
                        <sjg:gridColumn name="txstatus" index="u.id.txstatus" title="Transaction Status"  sortable="true"/>
                        <sjg:gridColumn name="ecivalue" index="u.id.ecivalue" title="ECI Value"  sortable="true"/>                            
                        <sjg:gridColumn name="status" index="u.status" title="Authentication Status"  sortable="true"/>                            
                        <sjg:gridColumn name="pkey" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                        <sjg:gridColumn name="pkey" title="Delete" width="25" align="center" formatter="deleteformatter" hidden="#vdelete"/>
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



