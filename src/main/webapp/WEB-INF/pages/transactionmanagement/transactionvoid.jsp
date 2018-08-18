<%-- 
    Document   : transactionvoid
    Created on : 13/12/2013, 3:21:39 PM
    Author     : badrika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML
>

<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf" %>

        <script type="text/javascript">

            function voidformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Void' onClick='javascript:voidTxnInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-cancel' style='display:inline-table;border:none;'/></a>";
            }

            function voidTxnInit(keyval) {
                $('#divmsg').empty();

                $("#voiddialog").data('keyval', keyval).dialog('open');
                $("#voiddialog").html('Are you sure you want to void Transaction ' + keyval + ' ?');
                return false;
            }

            function searchTxn(param) {
                var txnId = $('#txnId').val();
                var merchantName = $('#merchantName').val();
                var cardHolderName = $('#cardHolderName').val();
                var cardNumber = $('#cardNumber').val();
                var status = $('#status').val();
                var cardType = $('#cardType').val();
                var fromDate = $('#fromDate').val();
                var toDate = $('#toDate').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        txnId: txnId,
                        merchantName: merchantName,
                        cardHolderName: cardHolderName,
                        cardNumber: cardNumber,
                        status: status,
                        cardType: cardType,
                        fromDate: fromDate,
                        toDate: toDate,
                        search: param
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});

                jQuery("#gridtable").trigger("reloadGrid");

            }

            function voidTxn(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/VoidTransactionVoid.ipg',
                    data: {txnId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#voidsuccdialog").dialog('open');
                        $("#voidsuccdialog").html(data.message);
                        resetFieldData();
                    },
                    error: function (data) {
                        $("#voiderrordialog").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function resetAllData() {

                $("#fromDate").datepicker("setDate", new Date());
                $("#toDate").datepicker("setDate", new Date());
                $('#txnId').val("");
                $('#merchantName').val("");
                $('#cardHolderName').val("");
                $('#cardNumber').val("");
                $('#status').val("");
                $('#cardType').val("");
                $('#divmsg').text("");
                $("#gridtable").jqGrid('clearGridData', true);
                searchTxn(false);

            }

            function resetFieldData() {

                $("#fromDate").datepicker("setDate", new Date());
                $("#toDate").datepicker("setDate", new Date());
                $('#txnId').val("");
                $('#merchantName').val("");
                $('#cardHolderName').val("");
                $('#cardNumber').val("");
                $('#status').val("");
                $('#cardType').val("");

                jQuery("#gridtable").trigger("reloadGrid");
            }
            
            function setdate() {
                $("#fromDate").datepicker("setDate", new Date());
                $("#toDate").datepicker("setDate", new Date());
            }
        </script>
    </head>

    <body onload="setdate()">


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

                <s:set id="vsearch" var="vsearch"><s:property value="vsearch" default="true"/></s:set>
                <s:set var="vvoid"><s:property value="vvoid" default="true"/></s:set>
                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="txnvoid" method="post" action="TransactionVoid" theme="simple" >

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">From Date</label>
                                    <sj:datepicker cssClass="form-control" id="fromDate" name="fromDate" readonly="true"
                                                   maxDate="+1d" changeYear="true" buttonImageOnly="true"
                                                   displayFormat="dd/mm/yy" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">To Date</label>
                                    <sj:datepicker cssClass="form-control" id="toDate" name="toDate" readonly="true"
                                                   maxDate="+1d" changeYear="true" buttonImageOnly="true"
                                                   displayFormat="dd/mm/yy" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Transaction ID</label>
                                    <s:textfield cssClass="form-control" name="txnId" id="txnId" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Merchant Name</label>
                                    <s:textfield cssClass="form-control" name="merchantName" id="merchantName" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Card Holder Name</label>
                                    <s:textfield cssClass="form-control" name="cardHolderName" id="cardHolderName" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z ]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Card Number</label>
                                    <s:textfield cssClass="form-control" name="cardNumber" id="cardNumber" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Status</label>
                                    <s:select cssClass="form-control" id="status" list="%{statusList}"  name="status" headerKey="" headerValue="--Select--" listKey="statuscode" listValue="description" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Select Card</label>
                                    <s:select cssClass="form-control" id="cardType" list="%{cardTypeList}"  name="cardType" headerKey="" headerValue="--Select--" listKey="cardassociationcode" listValue="description" />
                                </div>
                            </div>
                        </div>

                        <s:url var="searchurl" action="SearchTransactionVoid"/>

                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" targets="divmsg" id="searchButton"  disabled="#vsearch" onclick="searchTxn(true)"/> 
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()"  />
                                </div>
                            </div>
                        </div>
                    </s:form>
                </div>

                <!-- Start delete confirm dialog box -->
                <sj:dialog 
                    id="voiddialog" 
                    buttons="{ 
                    'OK':function() { voidTxn($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Void Transaction"                            
                    />
                <!-- Start delete process dialog box -->
                <sj:dialog 
                    id="voidsuccdialog" 
                    buttons="{
                    'OK':function() { $( this ).dialog( 'close' );}
                    }"  
                    autoOpen="false" 
                    modal="true" 
                    title="Processing Void." 
                    />
                <!-- Start delete error dialog box -->
                <sj:dialog 
                    id="voiderrordialog" 
                    buttons="{
                    'OK':function() { $( this ).dialog( 'close' );}                                    
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Void error."
                    />

                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <s:url var="listurl" action="SearchTransactionVoid"/>
                    <sjg:grid
                        id="gridtable"
                        caption="Transaction Void"
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
                        <sjg:gridColumn name="txnId" index="IPGTRANSACTIONID" title="Transaction ID"  sortable="true"/>
                        <sjg:gridColumn name="merchantName" index="merchantname" title="Merchant Name"  sortable="true"/>
                        <sjg:gridColumn name="cardType" index="CARDASSOCIATION" title="Card Association Code"  sortable="true"/>
                        <sjg:gridColumn name="cardNumber" index="cardholderpan" title="Card Number"  sortable="true"/>
                        <sjg:gridColumn name="amount" index="amount" title="Amount"  sortable="true"/>                            
                        <sjg:gridColumn name="status" index="STATUS" title="Status"  sortable="true"/>

                        <sjg:gridColumn name="txnId" index="u.taskcode" title="Void" width="40" align="center" formatter="voidformatter" hidden="#vvoid"/>  
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


