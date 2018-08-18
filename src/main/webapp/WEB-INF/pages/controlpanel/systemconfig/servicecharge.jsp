<%-- 
    Document   : servicecharge
    Created on : Oct 31, 2013, 11:29:59 AM
    Author     : chalitha
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

            function editformatter(cellvalue) {
                a = $("#addButton").is(':disabled');
                u = $("#updateButton").is(':disabled');
                return "<a href='#' title='Edit' onClick='javascript:editServiceCharge(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function deleteformatter(cellvalue) {
                return "<a href='#' title='Delete' onClick='javascript:deleteServiceChargeInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }


            function editServiceCharge(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findServiceCharge.ipg',
                    data: {servicechargecode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {
                            $('#servicechargecode').val(data.servicechargecode);
                            $('#chargetype').val(data.chargetype);
                            $('#description').val(data.description);
                            $('#value').val(data.value);
                            $('#rate').val(data.rate);
                            $('#currency').val(data.currency);
                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");
                        }
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function deleteServiceChargeInit(keyval) {
                $('#divmsg').empty();
                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete Service Charge ' + keyval + ' ?');
                return false;
            }

            function deleteServiceCharge(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteServiceCharge.ipg',
                    data: {servicechargecode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
                        //                        jQuery("#gridtable").trigger("reloadGrid");                      
                    },
                    error: function (data) {
                        $("#deleteerrordialog").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            //            var a =  $("#addButton").is(':disabled');
            //            var u =  $("#updateButton").is(':disabled');  
            function resetAllData() {
                a = $("#addButton").is(':disabled');
                u = $("#updateButton").is(':disabled');
                //                alert("add status " + a  +" and updated stauts " + u);

                if (a == true && u == true) {
                    //$('#servicechargecode').val("");
                    $('#servicechargecode').attr('readOnly', false);
                    $('#servicechargecode').val("");
                    $('#chargetype').val("");
                    $('#description').val("");
                    $('#value').val("");
                    $('#rate').val("");
                    $('#currency').val("");
                    $('#divmsg').text("");
                    $('#addButton').button("disable");
                    $('#updateButton').button("disable");
                } else if (a == true && u == false) {
                    var servicechargecode = $('#servicechargecode').val();

                    $('#servicechargecode').attr('readOnly', true);

                    editServiceCharge(servicechargecode);
                } else if (a == false && u == true) {

                    // $('#bincode').val("");
                    $('#servicechargecode').attr('readOnly', false);
                    $('#servicechargecode').val("");
                    $('#chargetype').val("");
                    $('#description').val("");
                    $('#value').val("");
                    $('#rate').val("");
                    $('#currency').val("");
                    $('#divmsg').text("");
                    $('#addButton').button("enable");
                    $('#updateButton').button("disable");
                }
            }

            function resetFieldData() {
                var startStatus = '<s:property value="vadd" />'
                $('#servicechargecode').val("");
                $('#servicechargecode').attr('readOnly', false);
                $('#chargetype').val("");
                $('#description').val("");
                $('#value').val("");
                $('#rate').val("");
                $('#currency').val("");

                if (a == true && u == true) {
                    $('#addButton').button("disable");
                    $('#updateButton').button("disable");
                } else if (a == true && u == false && startStatus == 'false') {
                    $('#addButton').button("enable");
                    $('#updateButton').button("disable");
                } else if (a == true && u == false && startStatus == 'true') {
                    $('#addButton').button("disable");
                    $('#updateButton').button("disable");
                } else if (a == false && u == true) {
                    $('#addButton').button("enable");
                    $('#updateButton').button("disable");
                }
                jQuery("#gridtable").trigger("reloadGrid");
                // }
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
                    <s:form id="servicechargeadd" method="post" action="ServiceCharge" theme="simple" >
                        <s:textfield type="hidden"  name="servicechargecode" id="servicechargecode" />
                        
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Service Charge Type <span style="color: red">*</span></label>
                                    <s:select cssClass="form-control" name="chargetype" id="chargetype" list="%{chargeList}" headerKey="" headerValue="--Select Charge Type--" listKey="servicechargetypecode" listValue="description"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Description <span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="description" id="description" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Value <span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="value" id="value" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Rate <span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="rate" id="rate" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Currency <span style="color: red">*</span></label>
                                    <s:select cssClass="form-control" name="currency" id="currency" list="%{currencyList}" headerKey="" headerValue="--Select Currency Type--" listKey="currencyisocode" listValue="description"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                            </div>
                        </div>
                                
                        <s:url var="addurl" action="addServiceCharge"/>
                        <s:url var="updateurl" action="updateServiceCharge"/>
                        
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{addurl}" value="Add" targets="divmsg" id="addButton"  disabled="#vadd"/>

                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{updateurl}" value="Update" targets="divmsg"   disabled="#vupdatebutt" id="updateButton" />

                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                        </div>
                    </s:form>
                </div>

                <!-- Start delete confirm dialog box -->
                <sj:dialog 
                    id="deletedialog" 
                    buttons="{ 
                    'OK':function() { deleteServiceCharge($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Delete Service Charge"                            
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

                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <s:url var="listurl" action="listServiceCharge"/>
                    <sjg:grid
                        id="gridtable"
                        caption="Service Charge Management"
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
                        <sjg:gridColumn name="chargetype" index="u.ipgservicechargetype.description" title="Charge Type"  sortable="true"/>
                        <sjg:gridColumn name="description" index="u.description" title="Description"  sortable="true"/>
                        <sjg:gridColumn name="value" index="u.value" title="Value"  sortable="true"/>
                        <sjg:gridColumn name="rate" index="u.rate" title="Rate"  sortable="true"/>
                        <sjg:gridColumn name="currency" index="u.ipgcurrency.description" title="Currency"  sortable="true"/>
                        <sjg:gridColumn name="servicechargecode" index="u.servicechargeid" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                        <sjg:gridColumn name="servicechargecode" index="u.servicechargeid" title="Delete" width="40" align="center" formatter="deleteformatter" hidden="#vdelete"/>  
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