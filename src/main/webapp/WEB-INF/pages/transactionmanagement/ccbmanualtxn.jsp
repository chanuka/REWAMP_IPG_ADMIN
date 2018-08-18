<%-- 
    Document   : ccbmanualtxn
    Created on : Sep 15, 2014, 2:22:22 PM
    Author     : nalin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="/struts-jquery-tags" prefix="sj" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE HTML
>

<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf" %>

        <script type="text/javascript">

            function searchManualTransactions(param) {
                var cardNumber = $('#cardNumber').val();
                var nic = $('#nic').val();
                var merchantId = $('#merchantId').val();
                var currency = $('#currency').val();
                var fromDate = $('#fromDate').val();
                var toDate = $('#toDate').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        cardNumber: cardNumber,
                        nic: nic,
                        merchantId: merchantId,
                        currency: currency,
                        fromDate: fromDate,
                        toDate: toDate,
                        search: param
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});

                jQuery("#gridtable").trigger("reloadGrid");
                $('#processButton').button("enable");

            }

            $.subscribe('rowselect', function (event, data) {
                alert('Selected Row : ' + event.originalEvent.id);
            });


            function resetAllData() {
                $('#cardNumber').val("");
                $('#nic').val("");
                $('#merchantId').val("");
                $('#currency').val("");
                $('#fromDate').val("");
                $('#toDate').val("");
                $("#gridtable").jqGrid('clearGridData', true);
                searchManualTransactions(false);
                $('#processButton').button("disable");
            }

            $.subscribe('test', function (event, data) {
                var checks = $("#gridtable").find('input[type=checkbox]');
                var va = new Array();
                // alert(checks.length);
                for (var i = 0; i < checks.length; i++)
                {
                    if (checks[i].checked)
                    {
                        var p = checks[i].parentNode.parentNode;
                        var id = p.id;

                        va.push($("#gridtable").jqGrid('getCell', id, 'ccbaTransactionId'));

                    }
                }
                //                $('#selectedId').val(va);

                $.ajax({
                    url: '${pageContext.request.contextPath}/processCCBManualTxn.ipg',
                    data: {selectedId: va},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.istag) {
                            $("#successdialog").dialog('open');
                            $("#successdialog").html(data.message);
                            resetAllData();
                        } else {
                            $("#successdialog").dialog('open');
                            $("#successdialog").html(data.message);

                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/processCCBManualTxn.ipg";
                    }
                });


            });





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
                <s:set var="vprocessbutt"><s:property value="vprocessbutt" default="true"/></s:set>
                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="manualtxnform" method="post" action="CCBManualTxn" theme="simple">
                        <%--<s:hidden name="selectedId" id="selectedId" />--%>

                        <!-- Start delete process dialog box -->
                        <sj:dialog 
                            id="successdialog" 
                            buttons="{
                            'OK':function() { $( this ).dialog( 'close' );}
                            }"  
                            autoOpen="false" 
                            modal="true" 
                            title="Processing Manual Transaction." 
                            />
                        <!-- Start delete error dialog box -->
                        <sj:dialog 
                            id="errordialog" 
                            buttons="{
                            'OK':function() { $( this ).dialog( 'close' );}                                    
                            }" 
                            autoOpen="false" 
                            modal="true" 
                            title="Reversal error."
                            />
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
                                    <td>Card Number </td>
                                    <td><s:textfield id="cardNumber" name="cardNumber"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>NIC </td>
                                    <td><s:textfield id="nic" name="nic"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Merchant Id </td>
                                    <td><s:textfield id="merchantId" name="merchantId"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Currency </td>
                                    <td>
                                        <s:select id="currency" list="%{currencyList}" name="currency"
                                                  headerKey="" headerValue="--Select--"
                                                  listKey="currencyisocode" listValue="description"/>
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
                                    <td></td>
                                    <td>

                                        <sj:submit button="true" href="#" value="Search" onclick="searchManualTransactions(true)" id="searchButton" />

                                        <sj:submit button="true" value="Reset" name="reset" onClick="resetAllData()"/>

                                        <%--<s:url var="test" action=""/>--%>
                                        <sj:submit button="true" value="Process" targets="divmsg" disabled="#vprocessbutt" id="processButton" onClickTopics="test"/>
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

                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>

                    <s:url var="listurl" action="searchCCBManualTxn"/>

                    <sjg:grid 
                        id="gridtable"
                        caption="Merchant Transactions"
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

                        <sjg:gridColumn name="check" index="check" title=" " sortable="false" formatter="checkbox" editable="true" formatoptions="{disabled:'false'}" edittype="checkbox" value="false"  timeout="100" width="30" align="center" />

                        <sjg:gridColumn name="ccbaTransactionId" index="carccbaTransactionIddnumber" title="" sortable="true" hidden="true"/>
                        <sjg:gridColumn name="cardnumber" index="cardnumber" title="Card Number" sortable="true"/>
                        <sjg:gridColumn name="nicNo" index="nicNo" title="NIC" sortable="true"/>
                        <sjg:gridColumn name="merchantid" index="merchantid" title="Merchant Id" sortable="true"/>
                        <sjg:gridColumn name="merchantname" index="merchantid" title="Merchant Name" sortable="true"/>
                        <sjg:gridColumn name="currency" index="currency" title="Currency" sortable="true"/>
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
