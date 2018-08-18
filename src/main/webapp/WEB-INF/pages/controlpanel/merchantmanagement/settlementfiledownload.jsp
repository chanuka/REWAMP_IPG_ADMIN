<%-- 
    Document   : settlementfiledownload
    Created on : Oct 13, 2014, 6:26:20 PM
    Author     : nalin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML>

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

            function selectMerchantBatch() {


                var mid = $('#mid').val();
                $("#gridtable").jqGrid('setGridParam', {postData: {mid: mid}});
                jQuery("#gridtable").trigger("reloadGrid");

            }
            $.subscribe('searchcomtopics', function (event, data) {
                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                var records = $("#gridtable").jqGrid('getGridParam', 'records');
                if (records > 0) {
//                    enableButtons();
                    $('#settlementButton').button("enable");
                } else {
//                    disableButtons();
                    $('#settlementButton').button("disable");
                }
//                enabledisableSearchButton();
            });

            function enabledisableSearchButton() {
                var vsearch = "<s:property value='vsettlement'/>"
                if (vsearch === "true") {
                    document.getElementById('searchbutdisabled').style.display = '';
                    document.getElementById('searchbut').style.display = 'none';
                } else {
                    document.getElementById('searchbutdisabled').style.display = 'none';
                    document.getElementById('searchbut').style.display = '';
                }
            }

            function disableButtons() {
                $('#excelbutton').button("disable");
                $('#pdfbutton').button("disable");
            }

            function enableButtons() {
                var vreport = "<s:property value='vreport'/>"
                if (vreport === "true") {
                    disableButtons();
                } else {
                    $('#excelbutton').button("enable");
                    $('#pdfbutton').button("enable");
                }
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
                    <s:form id="txnsettlement" method="post" action="DownloadSettlementFileDownload" theme="simple">
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label"  >Merchant ID <span style="color: red">*</span></label>
                                    <s:select cssClass="form-control" id="mid" list="%{merchantList}"
                                              name="mid" headerKey="" headerValue="-- All --"
                                              listKey="midkey" listValue="midvalue" onchange="selectMerchantBatch()" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                            </div>
                        </div>


                        <s:url var="settlementurl" action="DownloadSettlementFileDownload"/>

                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" value="Settlement File Download" id="settlementButton"  disabled="#vsettlement" /> 
                                    <sj:submit cssClass="btn btn-sm btn-functions" cssStyle="visibility:hidden;" />
                                </div>
                            </div>
                        </div>
                    </s:form>
                </div>

                <!-- Start delete confirm dialog box -->
                <%--   <sj:dialog 
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
                       />--%>

                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <s:url var="listurl" action="SearchSettlementFileDownload"/>
                    <sjg:grid
                        id="gridtable"
                        caption="Settlement File Download"
                        dataType="json"
                        href="%{listurl}"
                        pager="true"
                        gridModel="gridModel"
                        rowList="10,15,20"
                        rowNum="10"
                        autowidth="true"
                        rownumbers="true"
                        onCompleteTopics="searchcomtopics"
                        rowTotal="false"
                        viewrecords="true"
                        > 
                        <sjg:gridColumn name="batchId"  title="Batch Number"  sortable="false"/>
                        <sjg:gridColumn name="mid"  title="Merchant Id"  sortable="false"/>
                        <sjg:gridColumn name="merchantname"  title="Merchant Name"  sortable="false"/>
                        <sjg:gridColumn name="tid"  title="Terminal Id"  sortable="false"/>
                        <%--<sjg:gridColumn name="status"  title="Description"  sortable="false"/>--%>                             
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