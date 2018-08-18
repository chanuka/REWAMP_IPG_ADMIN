<%-- 
    Document   : headmerchant
    Created on : Aug 13, 2018, 1:13:02 PM
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

                return "<a href='#' title='Edit' onClick='javascript:editHeadMerchantsInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function deleteformatter(cellvalue) {
                return "<a href='#' title='Delete' onClick='javascript:deleteHeadMerchantsInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function editHeadMerchantsInit(keyval) {
                $("#updatedialog").data('merchantcustomerid', keyval).dialog('open');
            }

            $.subscribe('openviewheadmerchantstopage', function (event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailHeadMerchants.ipg?merchantcustomerid=" + $led.data('merchantcustomerid'));
            });

            function deleteHeadMerchantsInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete Head Merchant ' + keyval + ' ?');
                return false;
            }

            function deleteHeadMerchants(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteHeadMerchants.ipg',
                    data: {merchantcustomerid: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        jQuery("#gridtable").trigger("reloadGrid");
                    },
                    error: function (data) {
                        $("#deleteerrordialog").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            function hideform() {

                $.ajax('#addform')
            }

            function resetAllData() {
                $('#merchantcustomerid').val("");
                $('#merchantname').val("");
                $('#email').val("");
                $('#status').val("");
                $('#txninitstatus').val("");
                $('#divmsg').text("");
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        merchantcustomerid: '',
                        merchantname: '',
                        email: '',
                        status: '',
                        txninitstatus: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {
                $('#imerchantcustomerid').val("");
                $('#imerchantname').val("");
                $('#iemail').val("");
                $('#istatus').val("");
                $('#iremarks').val("");
                $('#itxninitstatus').val("");

                jQuery("#gridtable").trigger("reloadGrid");

            }

            function searchHeadMerchants() {
                $('#divmsg').text("");
                var merchantcustomerid = $('#merchantcustomerid').val();
                var merchantname = $('#merchantname').val();
                var email = $('#email').val();
                var status = $('#status').val();
                var txninitstatus = $('#txninitstatus').val();



                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        merchantcustomerid: merchantcustomerid,
                        merchantname: merchantname,
                        email: email,
                        status: status,
                        txninitstatus: txninitstatus,
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
                        <s:form id="headmerchants" method="post" action="HeadMerchants" theme="simple">
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Merchant Customer ID </label>
                                        <s:textfield cssClass="form-control" name="merchantcustomerid" id="merchantcustomerid" 
                                                     maxLength="15" 
                                                     onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" 
                                                     onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Merchant Customer Name </label>
                                        <s:textfield cssClass="form-control" name="merchantname" id="merchantname" 
                                                     maxLength="255" 
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >E-Mail</label>
                                        <s:textfield cssClass="form-control" name="email" id="email" maxLength="255" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Status</label>
                                        <s:select cssClass="form-control" id="status" list="statusList" name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description"  />
                                    </div>
                                </div>
                            </div>
                             <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Transaction Initiated Status </label>
                                        <s:select cssClass="form-control" id="txninitstatus" list="txninitstatusList" name="txninitstatus" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description"  />
                                    </div>
                                </div>
                            </div>
                        </s:form>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" id="search"  disabled="#vsearch" onclick="searchHeadMerchants()"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                            <div class="col-sm-3"></div>
                            <div class="col-sm-3 text-right">
                                <s:url var="addurlLink" action="viewpopupHeadMerchants"/>
                                <div class="form-group">
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurlLink}"
                                        buttonIcon="ui-icon-newwin"
                                        disabled="#vadd"
                                        value="Add Head Merchant"
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
                        'OK':function() { deleteHeadMerchants($(this).data('keyval'));$( this ).dialog( 'close' ); },
                        'Cancel':function() { $( this ).dialog( 'close' );} 
                        }" 
                        autoOpen="false" 
                        modal="true" 
                        title="Delete Head Merchant"                            
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
                        title="Add Head Merchant"                            
                        loadingText="Loading .."                            
                        position="center"                            
                        width="1000"
                        height="500"
                        dialogClass= "dialogclass"

                        />
                    <sj:dialog                                     
                        id="updatedialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        position="center"
                        title="Update Head Merchant"
                        onOpenTopics="openviewheadmerchantstopage" 
                        loadingText="Loading .."
                        width="1000"
                        height="500"
                        dialogClass= "dialogclass"
                        />
                    <div class="content-table">
                        <!--this div for triangles: don't delete-->
                        <div></div>
                        <s:url var="listurl" action="listHeadMerchants"/>
                        <sjg:grid
                            id="gridtable"
                            caption="Head Merchant Management"
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
                            <sjg:gridColumn name="merchantcustomerid" index="u.merchantcustomerid" title="Edit" width="40" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                            <sjg:gridColumn name="merchantcustomerid" index="u.merchantcustomerid" title="Delete" width="50" align="center" formatter="deleteformatter" hidden="#vdelete"/> 
                            <sjg:gridColumn name="merchantcustomerid" index="u.merchantcustomerid" title="Merchant Customer ID"  sortable="true"/>
                            <sjg:gridColumn name="merchantname" index="u.merchantname" title="Merchant Customer Name"  sortable="true"/> 
                            <sjg:gridColumn name="email" index="u.ipgaddress.email" title="E-Mail"  sortable="true"/>
                            <sjg:gridColumn name="status" index="u.ipgstatusByStatuscode.statuscode" title="Status"  sortable="true"/>
                            <sjg:gridColumn name="txninitstatus" index="u.ipgstatusByTransactioninitaiatedstatus.statuscode" title="Transaction Initiated Status"  sortable="true"/>
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

