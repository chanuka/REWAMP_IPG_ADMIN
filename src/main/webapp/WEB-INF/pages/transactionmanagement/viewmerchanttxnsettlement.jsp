<%-- 
    Document   : viewmerchanttxnsettlement
    Created on : 14 Oct, 2014, 8:32:13 AM
    Author     : asela
--%>

<%@page import="com.epic.epay.ipg.bean.transactionmanagement.MerchantTracsactionSettlementInputBean"%>
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

            function invokeSettle() {

                var r = confirm("Are you sure, You want to settle this batch ?");
                if (r == true)
                {
                    authinit();
                }
            }

            function backToMain() {
                window.location = "${pageContext.request.contextPath}/viewMerchantTransactionSettlement.ipg?";
            }

            function authinit() {
                $("#remotedialog").dialog('open');
                return false;
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

                <s:set var="vprocess"><s:property value="vprocess" default="true"/></s:set>
                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="viewmarchanttxn" method="post" action="MerchantTransactionSettlement" theme="simple" >


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
                                    <td><b>Merchant ID</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label name="merchantId"  value="%{bean.merchantid}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>Terminal ID</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label name="terminalId"  value="%{bean.terminalid}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><b>Batch ID</b></td>
                                    <td><b>:</b></td>
                                    <td><s:label name="batchId"  value="%{bean.batchid}" />
                                    </td>
                                </tr>

                                <tr>
                                    <td></td>
                                    <td><s:url var="processurl" action="ProcessMerchantTransactionSettlement"/></td>
                                    <td>
                                        <sj:submit button="true" value="Process" targets="divmsg" id="processButton"  disabled="#vprocess" onclick="invokeSettle()"/> 
                                        <sj:a href="#" name="back" button="true" onclick="backToMain()">Back</sj:a>

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

                <s:url var="authurls" action="UserPwdAuthMerchantTransactionSettlement?key=%{bean.merchantid}|%{bean.terminalid}|%{bean.batchid}"/>  
                <sj:dialog                                     
                    id="remotedialog"                                 
                    autoOpen="false" 
                    modal="true"
                    href="%{authurls}"
                    title="Dual Auth User."
                    onOpenTopics="openRemoteDialog" 
                    loadingText="Loading .."
                    width="400"
                    height="250"
                    position="center"
                    />
                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>

                    <s:url var="listurl" action="ListMerchantTransactionSettlement?key=%{bean.merchantid}|%{bean.terminalid}|%{bean.batchid}"/>
                    <sjg:grid 
                        id="gridtable"
                        caption="Merchant Transactions Settlement"
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

                        <sjg:gridColumn name="txnid"  title="Transaction ID" sortable="false"/>
                        <sjg:gridColumn name="currency" title="Currency" sortable="false"/> 
                        <sjg:gridColumn name="txnamount"  title="Transaction Amount" sortable="false"/>
                        <sjg:gridColumn name="cardNumber"  title="Card Number" sortable="false"/>
                        <sjg:gridColumn name="status"  title="Status" sortable="false"/>
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

