<!--
        Created on : Sep 15, 2014, 3:15:24 PM
        author     : thushanth
-->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf"%>

        <script type="text/javascript">

            function searchTxn(param) {

                var fromdate = $('#fromdate').val();
                var todate = $('#todate').val();
                var txnid = $('#txnid').val();
                var cardholder = $('#cardholder').val();
                var cardno = $('#cardno').val();
                var status = $('#status').val();
                var cardtype = $('#cardtype').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        fromdate: fromdate, todate: todate,
                        search: param, txnid: txnid, cardholder: cardholder,
                        cardno: cardno, status: status, cardtype: cardtype,
                        search: param

                    }});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            }
            ;
            $.subscribe('completetopics', function (event, data) {

                var recors = $("#gridtable").jqGrid('getGridParam', 'records');
                if (recors > 0) {
                    $('#generateButton').button("enable");
                } else {
                    $('#generateButton').button("disable");
                }
            });

            function resetFieldData() {

                $('#fromdate').val("");
                $('#todate').val("");
                $('#txnid').val("");
                $('#cardholder').val("");
                $('#cardno').val("");
                $('#status').val("");
                $('#cardtype').val("");

            }


            function resetAllData() {
                $('#fromdate').val("");
                $('#todate').val("");
                $('#txnid').val("");
                $('#cardholder').val("");
                $('#cardno').val("");
                $('#status').val("");
                $('#cardtype').val("");
                $('#divmsg').text("");
                $('#generateButton').button("disable");
                $("#gridtable").jqGrid('clearGridData', true);
                searchTxn(false);
            }
            function setdate() {
                $("#fromdate").datepicker("setDate", new Date());
                $("#todate").datepicker("setDate", new Date());
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
                    <s:set id="vgenerate" var="vgenerate"><s:property value="vgenerate" default="true"/></s:set>
                    <s:set id="vsearch" var="vsearch"><s:property value="vsearch" default="true"/></s:set>

                        <!-- Form -->
                        <div class="content-form">
                        <s:form method="post" action="generateTransactionSummary" theme="simple">
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">From Date</label>
                                        <sj:datepicker cssClass="form-control" id="fromdate" name="fromdate" readonly="true" maxDate="d" changeYear="true"
                                                       buttonImageOnly="true" displayFormat="dd/mm/yy" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">To Date</label>
                                        <sj:datepicker cssClass="form-control" id="todate" name="todate" readonly="true" maxDate="+1d" changeYear="true"
                                                       buttonImageOnly="true" displayFormat="dd/mm/yy" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Transaction ID</label>
                                        <s:textfield cssClass="form-control" name="txnid"
                                                     id="txnid" maxLength="16"
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Card Holder Name</label>
                                        <s:textfield cssClass="form-control" name="cardholder" id="cardholder"
                                                     maxLength="32"
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Card Number</label>
                                        <s:textfield cssClass="form-control" id="cardno" 
                                                     name="cardno" maxLength="32"
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
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Select Card</label>
                                        <s:select cssClass="form-control" id="cardtype" list="%{cardList}"
                                                  name="cardtype" headerKey="" headerValue="--Select Card Type--"
                                                  listKey="cardassociationcode" listValue="description" />
                                    </div>
                                </div>
                            </div>

                            <s:url var="testurl" action=""/>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{testurl}" id="searchButton" onClick="searchTxn(true)" disabled="#vsearch" value="Search" />
                                        <sj:submit cssClass="btn btn-sm btn-functions" button="true" value="Generate"  id="generateButton" disabled="#vgenerate" />
                                        <sj:submit cssClass="btn btn-sm btn-reset" button="true"  href="%{testurl}" name="reset"  onclick="resetAllData()" value="Reset" />
                                    </div>
                                </div>
                            </div>
                        </s:form>
                    </div>



                    <div class="content-table">
                        <!--this div for triangles: don't delete-->
                        <div></div>
                        <s:url var="listurl" action="listTransactionSummary" />
                        <sjg:grid id="gridtable" 
                                  caption="Transactions Summary"
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
                            <sjg:gridColumn name="txnid" index="ipgtransactionid" title="Transaction Id" sortable="true" />
                            <sjg:gridColumn name="merchantname" index="merchantname" title="Merchant Name" sortable="true" />
                            <sjg:gridColumn name="associationcode" index="description" title="Card Association Code" sortable="true" />
                            <sjg:gridColumn name="cardnumber" index="cardholderpan" title="Card Number" sortable="true" />
                            <sjg:gridColumn name="amount" index="amount" title="Amount" sortable="true" />
                            <sjg:gridColumn name="status" index="statuscode" title="Status" sortable="true" />
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
