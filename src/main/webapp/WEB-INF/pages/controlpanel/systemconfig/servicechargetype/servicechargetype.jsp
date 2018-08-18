<%-- 
    Document   : servicechargetype
    Created on : Jul 10, 2018, 2:59:16 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf"%>

        <script type="text/javascript">
            function editformatter(cellvalue) {
                a = $("#addButton").is(':disabled');
                u = $("#updateButton").is(':disabled');
                return "<a href='#' title='Edit' onClick='javascript:editServiceChargeTypeInit(&#34;"
                        + cellvalue
                        + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function deleteformatter(cellvalue) {
                return "<a href='#' title='Delete' onClick='javascript:deleteServiceChargeTypeInit(&#34;"
                        + cellvalue
                        + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }
            
            function editServiceChargeTypeInit(keyval) {
                $("#updatedialog").data('serviceChargeTypeCode', keyval).dialog('open');
            }
            
            $.subscribe('openviewservicechargetopage', function (event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailServiceChargeType.ipg?serviceChargeTypeCode=" + $led.data('serviceChargeTypeCode'));
            });
            
            function editServiceChargeType(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findServiceChargeType.ipg',
                    data: {
                        serviceChargeTypeCode: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {

                            $('#serviceChargeTypeCode').val(
                                    data.serviceChargeTypeCode);
                            $('#serviceChargeTypeCode').attr('readOnly', true);
                            $('#description').val(data.description);
                            $('#sortKey').val(data.sortKey);
                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");
                        }
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html(
                                "Error occurred while processing.").dialog(
                                'open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function deleteServiceChargeTypeInit(keyval) {
                $('#divmsg').empty();
                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html(
                        'Are you sure you want to delete service charge type ' + keyval
                        + ' ?');
                return false;
            }

            function deleteServiceChargeType(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteServiceChargeType.ipg',
                    data: {
                        serviceChargeTypeCode: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
                    },
                    error: function (data) {
                        $("#deleteerrordialog").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function resetAllData() {
                $('#serviceChargeTypeCode').val("");
                $('#description').val("");
                $('#sortKey').val("");
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        serviceChargeTypeCode: '',
                        description: '',
                        sortKey: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid"); 
            }

            function resetFieldData() {
                $('#iserviceChargeTypeCode').val("");
                $('#idescription').val("");
                $('#isortKey').val("");
                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }
            
            function searchServiceChargeType(){
                var serviceChargeTypeCode = $('#serviceChargeTypeCode').val();
                var description = $('#description').val();
                var sortKey = $('#sortKey').val();
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        serviceChargeTypeCode: serviceChargeTypeCode,
                        description: description,
                        sortKey: sortKey,
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

                    <s:set id="vadd" var="vadd">
                        <s:property value="vadd" default="true" />
                    </s:set>
                    <s:set var="vupdatebutt">
                        <s:property value="vupdatebutt" default="true" />
                    </s:set>
                    <s:set var="vupdatelink">
                        <s:property value="vupdatelink" default="true" />
                    </s:set>
                    <s:set var="vdelete">
                        <s:property value="vdelete" default="true" />
                    </s:set>
                    <s:set var="vsearch">
                        <s:property value="vsearch" default="true" /> 
                    </s:set>

                    <!-- Form -->
                    <div class="content-form">
                        <s:form id="servicechargetype" method="post" action="ServiceChargeType" theme="simple">

                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Service Charge Type Code</label>
                                        <s:textfield cssClass="form-control" name="serviceChargeTypeCode"
                                                     id="serviceChargeTypeCode" maxLength="16"
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Description</label>
                                        <s:textfield cssClass="form-control" name="description" id="description"
                                                     maxLength="64"
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Sort Key </label>
                                        <s:textfield cssClass="form-control" name="sortKey" id="sortKey" maxLength="3"
                                                     onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                                    </div>
                                </div>
                            </div>
                        </s:form>



                            <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" id="search"  disabled="#vsearch" onclick="searchServiceChargeType()"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                            <div class="col-sm-3"></div>
                            <div class="col-sm-3 text-right">
                                <s:url var="addurlLink" action="viewpopupServiceChargeType"/>
                                <div class="form-group">
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurlLink}"
                                        buttonIcon="ui-icon-newwin"
                                        disabled="#vadd"
                                        value="Add Service Charge Type"
                                        id="addButton_new"
                                        cssClass="btn btn-sm btn-functions" 
                                        />
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Start delete confirm dialog box -->
                    <sj:dialog id="deletedialog"
                               buttons="{ 
                               'OK':function() { deleteServiceChargeType($(this).data('keyval'));$( this ).dialog( 'close' ); },
                               'Cancel':function() { $( this ).dialog( 'close' );} 
                               }"
                               autoOpen="false" modal="true" title="Delete Service Charge Type" />
                    <!-- Start delete process dialog box -->
                    <sj:dialog id="deletesuccdialog"
                               buttons="{
                               'OK':function() { $( this ).dialog( 'close' );}
                               }"
                               autoOpen="false" modal="true" title="Deleting Process." />
                    <!-- Start delete error dialog box -->
                    <sj:dialog id="deleteerrordialog"
                               buttons="{
                               'OK':function() { $( this ).dialog( 'close' );}                                    
                               }"
                               autoOpen="false" modal="true" title="Delete error." />
                     <sj:dialog                                     
                    id="remotedialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    title="Add Service Charge Type"                            
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
                    title="Update Service Charge Type"
                    onOpenTopics="openviewservicechargetopage" 
                    loadingText="Loading .."
                    width="1000"
                    height="500"
                    cssStyle="overflow: visible;"
                    dialogClass= "dialogclass"
                    />    

                    <div id="tablediv">
                        <div class="content-table">
                            <!--this div for triangles: don't delete-->
                            <div></div>
                            <s:url var="listurl" action="listServiceChargeType" />
                            <sjg:grid 
                                id="gridtable" 
                                caption="Service Charge Type"
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
                                <sjg:gridColumn name="servicechargetypecode" index="servicechargetypecode" title="Edit" width="25"
                                                align="center" formatter="editformatter" hidden="#vupdatelink" />
                                <sjg:gridColumn name="servicechargetypecode" index="servicechargetypecode" title="Delete" width="40"
                                                align="center" formatter="deleteformatter" hidden="#vdelete" />
                                <sjg:gridColumn name="servicechargetypecode" index="servicechargetypecode"
                                                title="Service Charge Type Code" sortable="true" />
                                <sjg:gridColumn name="description" index="description"
                                                title="Description" sortable="true" />
                                <sjg:gridColumn name="sortkey" index="sortkey"
                                                title="Sort Key" sortable="true" />
                                <sjg:gridColumn name="createdtime" index="createdtime"
                                                title="Created Time" sortable="true" />
                            </sjg:grid>
                        </div>
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

