<%-- 
    Document   : merchanttransactions
    Created on : Dec 13, 2013, 9:53:18 AM
    Author     : chalitha
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

                    <s:set id="vgenerate" var="vgenerate"><s:property value="vgenerate" default="true"/></s:set>
                    <s:set id="vsearch" var="vsearch"><s:property value="vsearch" default="true"/></s:set>

                        <!-- Form -->
                        <div class="content-form">
                        <s:form method="post" action="generateMerchantTransaction"
                                theme="simple">


                            <table border="0" cellspacing="5">

                                <tbody>

                                    <tr>
                                        <td></td>
                                        <td></td>
                                    </tr>

                                    <tr>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>From Date </td>
                                        <td>
                                            <sj:datepicker id="fromdate" name="fromdate" readonly="true" maxDate="-1d" changeYear="true"
                                                           buttonImageOnly="true" displayFormat="dd/mm/yy" />
                                        </td>


                                    </tr>
                                    <tr>
                                        <td>To Date </td>
                                        <td>
                                            <sj:datepicker id="todate" name="todate" readonly="true" maxDate="+1d" changeYear="true"
                                                           buttonImageOnly="true" displayFormat="dd/mm/yy" />
                                        </td>


                                    </tr>
                                    <tr>
                                        <td>Transaction ID</td>
                                        <td><s:textfield name="txnid"
                                                     id="txnid" maxLength="16"
                                                     onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Card Holder Name </td>
                                        <td><s:textfield name="cardholder" id="cardholder"
                                                     maxLength="32"
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z ]/g,''))" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Card Number </td>
                                        <td><s:textfield id="cardno" 
                                                     name="cardno" maxLength="32"
                                                     onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" /></td>

                                    </tr>
                                    <tr>
                                        <td>Status </td>
                                        <td><s:select id="status" list="%{statusList}"
                                                  name="status" headerKey="" headerValue="--Select Status--"
                                                  listKey="statuscode" listValue="description" /></td>

                                    </tr>
                                    <tr>
                                        <td>Select Card </td>
                                        <td><s:select id="cardtype" list="%{cardList}"
                                                  name="cardtype" headerKey="" headerValue="--Select Card Type--"
                                                  listKey="cardassociationcode" listValue="description" /></td>

                                    </tr>

                                    <tr>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td></td>


                                        <td>
                                            <s:url var="testurl" action=""/>
                                            <sj:submit button="true" href="%{testurl}" id="searchButton" onClick="searchTxn(true)" disabled="#vsearch" value="Search" />


                                            <sj:submit button="true" value="Generate"  id="generateButton" disabled="#vgenerate" />


                                            <sj:submit button="true"  href="%{testurl}" name="reset"  onclick="resetAllData()" value="Reset" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                </tbody>
                            </table>

                        </s:form>
                    </div>



                    <div class="content-table">
                        <!--this div for triangles: don't delete-->
                        <div></div>
                        <s:url var="listurl" action="listMerchantTransaction" />
                        <sjg:grid id="gridtable" caption="Merchant Transactions Management"
                                  dataType="json" href="%{listurl}" pager="true"
                                  gridModel="gridModel" rowList="10,15,20" rowNum="10"
                                  autowidth="true" rownumbers="true"
                                  onCompleteTopics="completetopics" rowTotal="false"
                                  viewrecords="true">
                            <sjg:gridColumn name="txnid" index="mcc"
                                            title="Transaction Id" sortable="true" />
                            <sjg:gridColumn name="merchantname" index="description"
                                            title="Merchant Name" sortable="true" />
                            <sjg:gridColumn name="associationcode" index="ipgstatus.statuscode"
                                            title="Card Association Code" sortable="true" />
                            <sjg:gridColumn name="cardnumber" index="ipgstatus.statuscode"
                                            title="Card Number" sortable="true" />
                            <sjg:gridColumn name="amount" index="ipgstatus.statuscode"
                                            title="Amount" sortable="true" />
                            <sjg:gridColumn name="status" index="ipgstatus.statuscode"
                                            title="Status" sortable="true" />
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
