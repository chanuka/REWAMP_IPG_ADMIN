<%-- 
    Document   : servicecharge
    Created on : Jul 10, 2018, 5:02:01 PM
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

            function editformatter(cellvalue) {
                return "<a href='#' title='Edit' onClick='javascript:editServiceChargeInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function deleteformatter(cellvalue) {
                return "<a href='#' title='Delete' onClick='javascript:deleteServiceChargeInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function editServiceChargeInit(keyval) {
                $("#updatedialog").data('servicechargecode', keyval).dialog('open');
            }

            $.subscribe('openviewservicechargetopage', function(event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailServiceCharge.ipg?servicechargecode=" + $led.data('servicechargecode'));
            });

            function editServiceCharge(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findServiceCharge.ipg',
                    data: {servicechargecode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
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
                    error: function(data) {
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
                    success: function(data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
                        //                        jQuery("#gridtable").trigger("reloadGrid");                      
                    },
                    error: function(data) {
                        $("#deleteerrordialog").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            //            var a =  $("#addButton").is(':disabled');
            //            var u =  $("#updateButton").is(':disabled');  
            function resetAllData() {

                $('#chargetype').val("");
                $('#description').val("");
                $('#value').val("");
                $('#rate').val("");
                $('#currency').val("");
                $('#divmsg').text("");

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        chargetype: '',
                        description: '',
                        value: '',
                        rate: '',
                        currency: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {
                $('#ichargetype').val("");
                $('#idescription').val("");
                $('#ivalue').val("");
                $('#irate').val("");
                $('#icurrency').val("");
                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }
            function searchServiceCharge() {
                var chargetype = $('#chargetype').val();
                var description = $('#description').val();
                var value = $('#value').val();
                var rate = $('#rate').val();
                var currency = $('#currency').val();
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        chargetype: chargetype,
                        description: description,
                        value: value,
                        rate: rate,
                        currency: currency,
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
                        <s:form id="servicechargeadd" method="post" action="ServiceCharge" theme="simple" >
                            <%--<s:textfield type="hidden"  name="servicechargecode" id="servicechargecode" />--%>

                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Service Charge Type</label>
                                        <s:select cssClass="form-control" name="chargetype" id="chargetype" list="%{chargeList}" headerKey="" headerValue="--Select Charge Type--" listKey="servicechargetypecode" listValue="description"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Description </label>
                                        <s:textfield cssClass="form-control" name="description" id="description" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Value </label>
                                        <s:textfield cssClass="form-control" name="value" id="value" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Rate </label>
                                        <s:textfield cssClass="form-control" name="rate" id="rate" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Currency </label>
                                        <s:select cssClass="form-control" name="currency" id="currency" list="%{currencyList}" headerKey="" headerValue="--Select Currency Type--" listKey="currencyisocode" listValue="description"/>
                                    </div>
                                </div>
                            </div>
                        </s:form>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" id="search"  disabled="#vsearch" onclick="searchServiceCharge()"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                            <div class="col-sm-3"></div>
                            <div class="col-sm-3 text-right">
                                <s:url var="addurlLink" action="viewpopupServiceCharge"/>
                                <div class="form-group">
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurlLink}"
                                        buttonIcon="ui-icon-newwin"
                                        disabled="#vadd"
                                        value="Add Service Charge"
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
                    <sj:dialog                                     
                        id="remotedialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        title="Add Service Charge"                            
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
                        title="Update Service Charge"
                        onOpenTopics="openviewservicechargetopage" 
                        loadingText="Loading .."
                        width="1000"
                        height="500"
                        cssStyle="overflow: visible;"
                        dialogClass= "dialogclass"
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
                            <sjg:gridColumn name="servicechargecode" index="u.servicechargeid" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                            <sjg:gridColumn name="servicechargecode" index="u.servicechargeid" title="Delete" width="40" align="center" formatter="deleteformatter" hidden="#vdelete"/>  
                            <sjg:gridColumn name="chargetype" index="u.ipgservicechargetype.description" title="Charge Type"  sortable="true"/>
                            <sjg:gridColumn name="description" index="u.description" title="Description"  sortable="true"/>
                            <sjg:gridColumn name="value" index="u.value" title="Value"  sortable="true"/>
                            <sjg:gridColumn name="rate" index="u.rate" title="Rate"  sortable="true"/>
                            <sjg:gridColumn name="currency" index="u.ipgcurrency.description" title="Currency"  sortable="true"/>
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
