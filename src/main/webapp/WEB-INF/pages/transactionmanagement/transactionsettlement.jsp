<%-- 
    Document   : transactionsettlement
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

            function resetAllData() {
                $('#divmsg').text("");
                $('#mid').val("");
                $("#gridtable").jqGrid('clearGridData', true);

            }

            function resetFieldData() {

                $('#mid').val("");
                jQuery("#gridtable").trigger("reloadGrid");
            }
            
            function clearMessage(){
                $('#divmsg').text("");
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


                <s:set id="vsettlement" var="vsearch"><s:property value="vsettlement" default="true"/></s:set>
                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="txnsettlement" method="post" action="IPGTransactionSettlement" theme="simple">


                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Merchant ID <span style="color: red">*</span></label>
                                    <s:select cssClass="form-control" id="mid" list="%{merchantList}" name="mid" headerKey="" headerValue="--Select Id--" listKey="midkey" listValue="midvalue" />
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                            </div>
                        </div>

                        <s:url var="settlementurl" action="SettleIPGTransactionSettlement"/>

                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{settlementurl}" value="Settlement" targets="divmsg" id="settlementButton"  disabled="#vsettlement" onclick="clearMessage()"/> 
                                </div>
                            </div>
                        </div>
                    </s:form>
                </div>

                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <s:url var="listurl" action="SearchIPGTransactionSettlement"/>
                    <sjg:grid
                        id="gridtable"
                        caption="Transaction Settlement"
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
                        <sjg:gridColumn name="mid"  title="Merchant Id"  sortable="false"/>
                        <sjg:gridColumn name="merchantname"  title="Merchant Name"  sortable="false"/>
                        <sjg:gridColumn name="batchnumber"  title="Batch Number"  sortable="false"/>
                        <sjg:gridColumn name="numoftxn"  title="No_Of_Transaction"  sortable="false"/>
                        <sjg:gridColumn name="status"  title="Description"  sortable="false"/>                             
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



