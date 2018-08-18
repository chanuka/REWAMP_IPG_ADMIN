<!--
        Created on : Sep 16, 2014, 10:13:36 AM
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

            function viewformatter(cellvalue, options, rowObject) {

//                return "<a href='detailViewTransactionExplorer.ipg?merID=" + rowObject.merchantid + "&txnid=" + rowObject.txnid + "' title='View Detail Record' ><img class='ui-icon ui-icon-newwin' style='display:inline-table;border:none;'/></a>";
                  return "<a href='#' title='View Transaction' onClick='javascript:viewTranInit(&#34;" + cellvalue + "&#34;,&#34;"+rowObject.txnid+"&#34;)'><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";   
            }
            function viewTranInit(mid,tid) {
                $("#viewdialog").data('txnid', tid).data('merID', mid).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#viewdialog");
                $led.html("Loading..");
                $led.load("detailViewTransactionExplorer.ipg?txnid=" + $led.data('txnid')+"&merID=" +$led.data('merID'));
            });
            
            function setdate() {
                  $("#fromdate").datepicker("setDate", new Date());
                   $("#todate").datepicker("setDate", new Date());
            }

//            window.onload = function () {
//                if ($('#merID').val() !== "") {
//                    $('#merID').attr('readOnly', true);
//                }
//                if ($('#merName').val() !== "") {
//                    $('#merName').attr('readOnly', true);
//                }
//            }
           
            function searchTxn(param) {

                var fromdate = $('#fromdate').val();
                var todate = $('#todate').val();
                var txnid = $('#txnid').val();
                var cardholder = $('#cardholder').val();
                var cardno = $('#cardno').val();
                var status = $('#status').val();
                var cardtype = $('#cardtype').val();
                var merID = $('#merID').val();
                var merName = $('#merName').val();
                var purDate = $('#purDate').val();
                var ECIval = $('#ECIval').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        fromdate: fromdate, todate: todate,
                        search: param, txnid: txnid, cardholder: cardholder,
                        cardno: cardno, status: status, cardtype: cardtype,
                        merID: merID, merName: merName, purDate: purDate, ECIval: ECIval,
                        search: param

                    }});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            }
            ;
            $.subscribe('completetopics', function (event, data) {
                var isGenerate = <s:property value="vgenerate"/>;
                var recors = $("#gridtable").jqGrid('getGridParam', 'records');
                if (recors > 0 && isGenerate == false) {
                    $('#view').button("enable");
                    $('#view1').button("enable");
                } else {
                    $('#view').button("disable");
                    $('#view1').button("disable");
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
                $('#purDate').val("");
                $('#ECIval').val("");
                $('#merID').val("");
                $('#merName').val("");

            }


            function resetAllData() {
                $('#fromdate').val("");
                $('#todate').val("");
                $('#txnid').val("");
                $('#cardholder').val("");
                $('#cardno').val("");
                $('#status').val("");
                $('#cardtype').val("");
                $('#purDate').val("");
                $('#ECIval').val("");
                $('#merID').val("");
                $('#merName').val("");
                $('#divmsg').text("");
                setdate();
                $('#generateButton').button("disable");
                $("#gridtable").jqGrid('clearGridData', true);
                searchTxn(false);
            }

            function todoexel() {
                $('#reporttype').val("exel");
                form = document.getElementById('tranexpo');
                form.action = 'generateTransactionExplorer.ipg';
                form.submit();
                $('#view').button("disable");
                $('#view1').button("disable");
            }

            function todo() {
                $('#reporttype').val("pdf");
                form = document.getElementById('tranexpo');
                form.action = 'generateTransactionExplorer.ipg';
                form.submit();
                $('#view').button("disable");
                $('#view1').button("disable");
            }
            
            $(document).ready()( function() {
                if ($('#merID').val() !== "") {
                    $('#merID').attr('readOnly', true);
                }
                if ($('#merName').val() !== "") {
                    $('#merName').attr('readOnly', true);
                }
            });
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
                        <s:form method="post" action="generateTransactionExplorer" theme="simple" id="tranexpo">
                            <s:hidden name="reporttype" id="reporttype"></s:hidden>
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
                                        <label class="control-label">Merchant ID</label>
                                        <s:textfield cssClass="form-control" name="merID" value="%{merID}"
                                                     id="merID" maxLength="24"
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Card Holder Name</label>
                                        <s:textfield cssClass="form-control" name="cardholder" id="cardholder"
                                                     maxLength="32"
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Merchant Name</label>
                                        <s:textfield cssClass="form-control" name="merName" id="merName" 
                                                     maxLength="25"
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>
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
                                        <label class="control-label">Purchase Date</label>
                                        <sj:datepicker cssClass="form-control" id="purDate" name="purDate" readonly="true" maxDate="+1d" changeYear="true"
                                                       buttonImageOnly="true" displayFormat="dd/mm/yy" />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
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
                                        <label class="control-label">ECI Value</label>
                                        <s:textfield id="ECIval" cssClass="form-control"
                                                     name="ECIval" maxLength="4"
                                                     onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Card Association</label>
                                        <s:select cssClass="form-control" id="cardtype" list="%{cardList}"
                                                  name="cardtype" headerKey="" headerValue="--Select Card Type--"
                                                  listKey="cardassociationcode" listValue="description" />
                                    </div>
                                </div>
                            </div>
                            </s:form>
                            <s:url var="testurl" action=""/>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <sj:submit cssClass="btn btn-sm btn-functions" button="true"  id="searchButton" onClick="searchTxn(true)" disabled="#vsearch" value="Search" />
                                        <sj:submit cssClass="btn btn-sm btn-reset" button="true"  name="reset"  onclick="resetAllData()" value="Reset" />
                                        <sj:submit cssClass="btn btn-sm btn-functions" button="true" value="View PDF"  name="view" id="view" onClick="todo()"  disabled="#vgenerate" />
                                        <sj:submit cssClass="btn btn-sm btn-functions" button="true" value="View Excel"  name="view1" id="view1" onClick="todoexel()"  disabled="#vgenerate" />
                                    </div>
                                </div>
                            </div>
                        
                    </div>


                    <!-- Start error dialog box -->
                    <sj:dialog 
                        id="errordialog" 
                        buttons="{
                        'OK':function() { $( this ).dialog( 'close' );}                                    
                        }" 
                        autoOpen="false" 
                        modal="true" 
                        title="Common Error."
                        />
                    <sj:dialog                                     
                        id="viewdialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        position="center"
                        title="View Transaction Explorer"
                        onOpenTopics="openviewtasktopage" 
                        loadingText="Loading .."
                        width="900"
                        height="600"
                        dialogClass= "dialogclass"

                        />  

                    <div class="content-table">
                        <!--this div for triangles: don't delete-->
                        <div></div>
                        <s:url var="listurl" action="listTransactionExplorer" />
                        <sjg:grid id="gridtable" caption="Transactions Explorer"
                                  dataType="json" href="%{listurl}" pager="true"
                                  gridModel="gridModel" rowList="10,15,20" rowNum="10"
                                  autowidth="true" rownumbers="true"
                                  onCompleteTopics="completetopics" rowTotal="false"
                                  viewrecords="true" 
                                  shrinkToFit="false" >
                            <sjg:gridColumn name="merchantid" title="View" width="25" align="center" 
                                            formatter="viewformatter" hidden="#vviewlink" sortable="false" frozen="true" />
                            <sjg:gridColumn name="txnid" index="ipgtransactionid"
                                            title="Transaction Id" sortable="true" frozen="true" />
                            <sjg:gridColumn name="merchantid" index="merchantid"
                                            title="Merchant ID" sortable="true" frozen="true" />
                            <sjg:gridColumn name="cardholder" index="cardholder"
                                            title="Card Holder Name" sortable="true" />
                            <sjg:gridColumn name="merchantname" index="merchantname"
                                            title="Merchant Name" sortable="true" />
                            <sjg:gridColumn name="cardnumber" index="cardholderpan"
                                            title="Card Number" sortable="true" />
                            <sjg:gridColumn name="purDate" index="purDate"
                                            title="Purchase Date" sortable="true" />
                            <sjg:gridColumn name="status" index="statuscode"
                                            title="Status" sortable="true" />
                            <sjg:gridColumn name="ECIval" index="ECIval"
                                            title="ECIval" sortable="true" />
                            <sjg:gridColumn name="associationcode" index="description"
                                            title="Card Association Code" sortable="true" />
                            <sjg:gridColumn name="amount" index="amount"
                                            title="Amount" sortable="true" />
                            <sjg:gridColumn name="createdtime" index="createdtime"
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
