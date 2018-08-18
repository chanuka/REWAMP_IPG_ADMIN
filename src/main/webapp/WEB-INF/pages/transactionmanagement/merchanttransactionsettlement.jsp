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

            function processformatter(cellvalue, options, rowObject) {
                return "<a href='showMerchantTransactionSettlement.ipg?key=" + cellvalue + "' title='View Merchant Transactions' ><img class='ui-icon ui-icon-newwin' style='display:inline-table;border:none;'/></a>";
            }

            function searchMerchantTransactions(param) {
                var headMerchantId = $('#headMerchantId').val();
                var headMerchantName = $('#headMerchantName').val();
                var merchantId = $('#merchantId').val();
                var merchantName = $('#merchantName').val();
                var terminalId = $('#terminalId').val();
                var status = $('#status').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        headMerchantId: headMerchantId,
                        headMerchantName: headMerchantName,
                        merchantId: merchantId,
                        merchantName: merchantName,
                        terminalId: terminalId,
                        status: status,
                        search: param
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});

                jQuery("#gridtable").trigger("reloadGrid");

            }

            function resetAllData() {
                $('#headMerchantId').val("");
                $('#headMerchantName').val("");
                $('#merchantId').val("");
                $('#merchantName').val("");
                $('#terminalId').val("");
                $('#status').val("");
                $("#gridtable").jqGrid('clearGridData', true);
                searchMerchantTransactions(false);
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
                <s:set var="vsettle"><s:property value="vsettle" default="true"/></s:set>
                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="txnsettlementform" method="post" action="MerchantTransactionSettlement" theme="simple">


                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Head Merchant ID</label>
                                    <s:textfield cssClass="form-control" id="headMerchantId" name="headMerchantId"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Head Merchant Name</label>
                                    <s:textfield cssClass="form-control" id="headMerchantName" name="headMerchantName"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Merchant ID</label>
                                    <s:textfield cssClass="form-control" id="merchantId" name="merchantId"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Merchant Name</label>
                                    <s:textfield cssClass="form-control" id="merchantName" name="merchantName"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Terminal ID</label>
                                    <s:textfield cssClass="form-control" id="terminalId" name="terminalId"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Status</label>
                                    <s:select cssClass="form-control" id="status" list="%{statusList}" name="status" headerKey="" headerValue="--Select--" listKey="statuscode" listValue="description"/>
                                </div>
                            </div>
                        </div>

                       

                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" onclick="searchMerchantTransactions(true)" targets="divmsg" id="searchButton" />
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()"/>
                                </div>
                            </div>
                        </div>
                    </s:form>
                </div>

                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>

                    <s:url var="listurl" action="searchMerchantTransactionSettlement"/>
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

                        <sjg:gridColumn name="headmerchant"  title="Head Merchant" sortable="false"/>
                        <sjg:gridColumn name="merchantid"  title="Merchant ID" sortable="false"/>
                        <sjg:gridColumn name="merchantname"  title="Merchant Name" sortable="false"/>
                        <sjg:gridColumn name="currency"  title="Currency" sortable="false"/>
                        <sjg:gridColumn name="terminalid"  title="Terminal ID" sortable="false"/>
                        <sjg:gridColumn name="batchid"   title="Batch ID" sortable="false"/>
                        <sjg:gridColumn name="txncount"  title="Transaction Count" sortable="false"/>
                        <sjg:gridColumn name="txnamount"  title="Transaction Amount" sortable="false"/>
                        <sjg:gridColumn name="status"  title="Status" sortable="false"/>
                        <sjg:gridColumn name="key"  title="Process" width="40" align="center" formatter="processformatter" hidden="#vsettle"/>  
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