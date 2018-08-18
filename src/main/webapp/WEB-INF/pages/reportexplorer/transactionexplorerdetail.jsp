<!--
        Created on : Sep 16, 2014, 5:25:56 PM
        author     : thushanth
-->

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


        <script type="text/javascript">

            function backToMain() {
                window.location = "${pageContext.request.contextPath}/viewTransactionExplorer.ipg";
            }
            function todox() {
                form = document.getElementById('tranEx2');
                form.action = 'individualReportTransactionExplorer.ipg';
            }

        </script>
    </head>
    <body>



        <div class="content-message">
            <s:div id="divmsg">
                <s:actionmessage theme="jquery"/>
                <s:actionerror theme="jquery"/>
            </s:div>
        </div>
        <!-- Form -->
        <div class="content-form">
            <s:form id="tranEx2" method="post" action="*TransactionExplorer" theme="simple">


                <table border="0" cellspacing="5" class="table table-newtheme">

                    <tbody>

                        <tr>
                            <td></td>
                            <td>
                            </td>
                        </tr>

                        <tr>
                            <td></td>
                            <td>
                            </td>
                        </tr>
                        <tr>
                            <td><b>Transaction ID</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="txnid"  value="%{txnDataBean.txnid}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Transaction Created Date Time</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="transactioncreateddatetime"  value="%{txnDataBean.transactioncreateddatetime}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Transaction Category</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="txncategory"  value="%{txnDataBean.txncategory}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Status</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="status"  value="%{txnDataBean.status}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Amount</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="amount"  value="%{txnDataBean.amount}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Currency</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="currency"  value="%{txnDataBean.currency}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Country</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="country"  value="%{txnDataBean.country}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Card Association</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="associationcode"  value="%{txnDataBean.associationcode}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Card Name</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="cardname"  value="%{txnDataBean.cardname}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Card Holder Pan</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="cardholderpan"  value="%{txnDataBean.cardholderpan}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Purchased Date Time</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="purchaseddatetime"  value="%{txnDataBean.purchaseddatetime}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Expiry Date</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="expirydate"  value="%{txnDataBean.expirydate}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Client IP</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="clientip"  value="%{txnDataBean.clientip}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Merchant Name</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="merchantname"  value="%{txnDataBean.merchantname}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Merchant Remarks</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="merchantremarks"  value="%{txnDataBean.merchantremarks}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Merchant Reference No</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="merchantreferanceno"  value="%{txnDataBean.merchantreferanceno}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Merchant Type</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="merchanttype"  value="%{txnDataBean.merchanttype}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Terminal Id</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="terminalid"  value="%{txnDataBean.terminalid}" />
                            </td>
                        </tr>

                        <tr>
                            <td><b>RRN</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="rrn"  value="%{txnDataBean.rrn}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Approval Code</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="approvalcode"  value="%{txnDataBean.approvalcode}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Batch Number</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="batchnumber"  value="%{txnDataBean.batchnumber}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Last Updated Time</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="lastupdatedtime"  value="%{txnDataBean.lastupdatedtime}" />
                            </td>
                        </tr>
                        <tr>
                            <td><b>Created Time</b></td>
                            <td><b>:</b></td>
                            <td><s:label name="createdtime"  value="%{txnDataBean.createdtime}" />
                            </td>
                        </tr>
                        <tr>

                            <td>
                                <br />
                                <sj:submit 
                                    button="true" 
                                    id="viewindi" 
                                    onclick="todox()" 
                                    disabled="#vgenerate"
                                    value="View PDF"
                                    cssClass="btn btn-sm btn-functions"
                                    />
                            </td>
                        </tr>

                    </tbody>
                </table>

<!--                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <%--<sj:a cssClass="btn btn-sm btn-functions" href="#" name="back" button="true" onclick="backToMain()">Back</sj:a>--%>
                            </div>
                        </div>
                    </div>-->

            </s:form>
        </div>

        <div class="content-table">
            <!--this div for triangles: don't delete-->
            <div></div>
            <s:url var="listurl" action="detailListTransactionExplorer" />
            <sjg:grid id="gridtablePopup" caption="Transactions Explorer"
                      dataType="json" href="%{listurl}" pager="true"
                      gridModel="txnGridModel" rowList="10,15,20" rowNum="10"
                      autowidth="true" rownumbers="true"
                      onCompleteTopics="completetopics" rowTotal="false"
                      viewrecords="true">
                <sjg:gridColumn name="txnhisid" index="ipgtransactionhistoryid"
                                title="Transaction History Id" sortable="true" />                                                   
                <sjg:gridColumn name="status" index="description"
                                title="Status" sortable="true" />
                <sjg:gridColumn name="remarks" index="remarks"
                                title="Remarks" sortable="true" />
                <sjg:gridColumn name="lastupuser" index="lastupdateduser"
                                title="Last Updated User" sortable="true" />
                <sjg:gridColumn name="lastuptime" index="lastupdatedtime"
                                title="Last Updated Time" sortable="true" />
                <sjg:gridColumn name="createdtime" index="createdtime"
                                title="Created Time" sortable="true" />    

            </sjg:grid>
        </div>
    </body>
</html>
