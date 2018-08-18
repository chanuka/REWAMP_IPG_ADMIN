<%-- 
    Document   : merchantcategory
    Created on : Jul 12, 2018, 2:40:54 PM
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
                return "<a href='#' title='Edit' onClick='javascript:editMerchantCategoryInit(&#34;"
                        + cellvalue
                        + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function deleteformatter(cellvalue) {
                return "<a href='#' title='Delete' onClick='javascript:deleteMerchantCategoryInit(&#34;"
                        + cellvalue
                        + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function editMerchantCategoryInit(keyval) {
                $("#updatedialog").data('merchantCategoryCode', keyval).dialog('open');
            }

            $.subscribe('openviewmerchantcategorytopage', function(event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailMerchantCategory.ipg?merchantCategoryCode=" + $led.data('merchantCategoryCode'));
            });

            function editMerchantCategory(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findMerchantCategory.ipg',
                    data: {
                        merchantCategoryCode: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {

                            $('#merchantCategoryCode').val(
                                    data.merchantCategoryCode);
                            $('#merchantCategoryCode').attr('readOnly', true);
                            $('#description').val(data.description);
                            $('#status').val(data.status);
                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");
                        }
                    },
                    error: function(data) {
                        $("#deleteerrordialog").html(
                                "Error occurred while processing.").dialog(
                                'op        en');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function deleteMerchantCategoryInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html(
                        'Are you sure you want to delete Merchant Category ' + keyval
                        + ' ?');
                return false;
            }

            function deleteMerchantCategory(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteMerchantCategory.ipg',
                    data: {
                        merchantCategoryCode: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
                    },
                    error: function(data) {
                        $("#deleteerrordialog").dialog('op        en');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function resetAllData() {
                $('#merchantCategoryCode').val("");
                $('#description').val("");
                $('#status').val("");
                $('#divmsg').text("");
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        merchantCategoryCode: '',
                        description: '',
                        status: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {
                $('#imerchantCategoryCode').val("");
                $('#idescription').val("");
                $('#istatus').val("");

                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function searchMerchantCategory() {
                var merchantCategoryCode = $('#merchantCategoryCode').val();
                var description = $('#description').val();
                var status = $('#status').val();

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        merchantCategoryCode: merchantCategoryCode,
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

                    <s:set id="vadd" var="vadd"><s:property value="vadd" default="true" /></s:set>
                    <s:set var="vupdatebutt"><s:property value="vupdatebutt" default="true" /></s:set>
                    <s:set var="vupdatelink"><s:property value="vupdatelink" default="true" /></s:set>
                    <s:set var="vdelete"><s:property value="vdelete" default="true" /></s:set>
                    <s:set var="vsearch"><s:property value="vsearch" default="true" /></s:set>
                        <!-- Form -->
                        <div class="content-form">
                        <s:form id="merchantcategory" method="post" action="MerchantCategory" theme="simple">

                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Merchant Category Code</label>
                                        <s:textfield cssClass="form-control" name="merchantCategoryCode"
                                                     id="merchantCategoryCode" maxLength="4"
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Description</label>
                                        <s:textfield cssClass="form-control" name="description" id="description"
                                                     maxLength="64"
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Status</label>
                                        <s:select cssClass="form-control" id="status" list="%{statusList}"
                                                  name="status" headerKey="" headerValue="--Select Status--"
                                                  listKey="statuscode" listValue="description" />
                                    </div>
                                </div>
                            </div>
                        </s:form>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" id="search"  disabled="#vsearch" onclick="searchMerchantCategory()"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                            <div class="col-sm-3"></div>
                            <div class="col-sm-3 text-right">
                                <s:url var="addurlLink" action="viewpopupMerchantCategory"/>
                                <div class="form-group">
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurlLink}"
                                        buttonIcon="ui-icon-newwin"
                                        disabled="#vadd"
                                        value="Add Merchant Category"
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
                               'OK':function() { deleteMerchantCategory($(this).data('keyval'));$( this ).dialog( 'close' ); },
                               'Cancel':function() { $( this ).dialog( 'close' );} 
                               }"
                               autoOpen="false" modal="true" title="Delete Merchant Category" />
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
                        title="Add Merchant Category"                            
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
                        title="Update Merchant Category"
                        onOpenTopics="openviewmerchantcategorytopage" 
                        loadingText="Loading .."
                        width="1000"
                        height="500"
                        cssStyle="overflow: visible;"
                        dialogClass= "dialogclass"
                        /> 

                    <div class="content-table">
                        <!--this div for triangles: don't delete-->
                        <div></div>
                        <s:url var="listurl" action="listMerchantCategory" />
                        <sjg:grid id="gridtable" caption="Merchant Category Management"
                                  dataType="json" href="%{listurl}" pager="true"
                                  gridModel="gridModel" rowList="10,15,20" rowNum="10"
                                  autowidth="true" rownumbers="true"
                                  onCompleteTopics="completetopics" rowTotal="false"
                                  viewrecords="true">
                            <sjg:gridColumn name="mcc" index="mcc" title="Edit" width="25"
                                            align="center" formatter="editformatter" hidden="#vupdatelink" />
                            <sjg:gridColumn name="mcc" index="mcc" title="Delete" width="40"
                                            align="center" formatter="deleteformatter" hidden="#vdelete" />
                            <sjg:gridColumn name="mcc" index="mcc"
                                            title="Merchant Category Code" sortable="true" />
                            <sjg:gridColumn name="description" index="description"
                                            title="Description" sortable="true" />
                            <sjg:gridColumn name="statusdes" index="ipgstatus.statuscode"
                                            title="Status" sortable="true" />
                            <sjg:gridColumn name="createdtime" index="createtime"
                                            title="Created Time" sortable="true" />
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

