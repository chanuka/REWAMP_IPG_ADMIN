<%-- 
    Document   : refundtransaction
    Created on : 15/09/2014, 9:14:50 AM
    Author     : asela indika 
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

            function refundtxnformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Refund' onClick='javascript:refundTxnInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-arrowreturnthick-1-w' style='display:inline-table;border:none;'/></a>";
            }

            function refundTxnInit(keyval) {
                $('#divmsg').empty();

                $("#refunddialog").data('keyval', keyval).dialog('open');
                $("#refunddialog").html('Are you sure you want to continue the refund process ' + keyval + ' ?');
                return false;
            }

            function searchTxn(param) {
                var cardno = $('#cardno').val();
                var orderid = $('#orderid').val();
                var fromDate = $('#fromDate').val();
                var toDate = $('#toDate').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        cardno: cardno,
                        orderid: orderid,
                        fromDate: fromDate,
                        toDate: toDate,
                        search: param
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});

                jQuery("#gridtable").trigger("reloadGrid");

            }

            function refundTxn(keyval) {

                var refundAmount = "";
                while (refundAmount == "" || refundAmount == null) {
                    refundAmount = prompt("Enter Refund Amount : ", "");
                    if (refundAmount == "" || refundAmount == null) {
                        alert("Refund Amount can not be empty.");
                    }
                }

                var reson = "";
                //  while (reson == "" || reson == null) {
                reson = prompt("Enter Reson : ", "");
//                    if (reson == "" || reson == null) {
//                        alert("Refund Amount can not be empty.");
//                    }
                //   }
                $.ajax({
                    url: '${pageContext.request.contextPath}/RefundIPGRefundTransactionServlet.ipg',
                    data: {txnid: keyval, refundAmount: refundAmount, reson: reson},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#reversalsuccdialog").dialog('open');
                        $("#reversalsuccdialog").html(data.message);
                        resetFieldData();
                    },
                    error: function (data) {
                        $("#reversalerrordialog").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function resetAllData() {

                $('#cardno').val("");
                $('#orderid').val("");
                $('#fromDate').val("");
                $('#toDate').val("");
                $("#gridtable").jqGrid('clearGridData', true);
                searchTxn(false);

            }

            function resetFieldData() {

                $('#cardno').val("");
                $('#orderid').val("");
                $('#fromDate').val("");
                $('#toDate').val("");

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

                <s:set id="vsearch" var="vsearch"><s:property value="vsearch" default="true"/></s:set>
                <s:set var="vrefund"><s:property value="vrefund" default="true"/></s:set>
                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="txnrefund" method="post" action="IPGRefundTransactionServlet" theme="simple">


                        <table border="0" cellspacing="5">

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
                                    <td>From Date </td>
                                    <td><sj:datepicker id="fromDate" name="fromDate" readonly="true"
                                                   maxDate="+1d" changeYear="true" buttonImageOnly="true"
                                                   displayFormat="dd/mm/yy" /></td>
                                </tr>

                                <tr>
                                    <td>To Date </td>
                                    <td><sj:datepicker id="toDate" name="toDate" readonly="true"
                                                   maxDate="+1d" changeYear="true" buttonImageOnly="true"
                                                   displayFormat="dd/mm/yy" /></td>
                                </tr>

                                <tr>
                                    <td>Card Number </td>
                                    <td><s:textfield name="cardno" id="cardno" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Order ID </td>
                                    <td><s:textfield  name="orderid" id="orderid" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                    </td>                                      
                                </tr>


                                <tr>
                                    <td></td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td> <s:url var="searchurl" action="SearchIPGRefundTransactionServlet"/>
                                    <td>

                                        <sj:a button="true" value="Search"  href="#" id="searchButton"  disabled="#vsearch" onclick="searchTxn(true)">Search</sj:a> 

                                        <sj:a button="true" value="Reset" name="reset" onClick="resetAllData()" >Reset</sj:a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>

                    </s:form>
                </div>

                <!-- Start delete confirm dialog box -->
                <sj:dialog 
                    id="refunddialog" 
                    buttons="{ 
                    'OK':function() { refundTxn($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Refund Transaction"                            
                    />
                <!-- Start delete process dialog box -->
                <sj:dialog 
                    id="refundsuccdialog" 
                    buttons="{
                    'OK':function() { $( this ).dialog( 'close' );}
                    }"  
                    autoOpen="false" 
                    modal="true" 
                    title="Processing Refund." 
                    />
                <!-- Start delete error dialog box -->
                <sj:dialog 
                    id="refunderrordialog" 
                    buttons="{
                    'OK':function() { $( this ).dialog( 'close' );}                                    
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Refund error."
                    />

                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <s:url var="listurl" action="SearchIPGRefundTransactionServlet"/>
                    <sjg:grid
                        id="gridtable"
                        caption="Transaction Refund"
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
                        <sjg:gridColumn name="txnid"  title="Transaction ID"  sortable="false"/>
                        <sjg:gridColumn name="orderid"  title="Order ID"  sortable="false"/>
                        <sjg:gridColumn name="merchantname"  title="Merchant Name"  sortable="false"/>
                        <sjg:gridColumn name="cardNumber"  title="Card Number"  sortable="false"/>
                        <sjg:gridColumn name="cardassociationcode"  title="Card Association Code"  sortable="false"/>                            
                        <%--<sjg:gridColumn name="cardnumber"  title="Card Number"  sortable="false"/>--%>
                        <sjg:gridColumn name="amount"  title="Amount"  sortable="false"/>
                        <sjg:gridColumn name="status"  title="Status"  sortable="false"/>

                        <sjg:gridColumn name="txnid"  title="Refund" width="40" align="center" formatter="refundtxnformatter" hidden="#vrefund"/>  
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



