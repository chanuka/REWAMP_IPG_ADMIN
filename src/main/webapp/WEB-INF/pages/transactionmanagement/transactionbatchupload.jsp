<%-- 
    Document   : transactionbatchupload
    Created on : 17 Sep, 2014, 1:49:43 PM
    Author     : asela
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

            function viewformatter(cellvalue) {
                return "<a href='ShowIPGTxnBatchUploadServlet.ipg?fileid=" + cellvalue + "' title='View Batch File' ><img class='ui-icon ui-icon-newwin' style='display:inline-table;border:none;'/></a>";
            }

            function resetAllData() {
                $('#divmsg').text("");
                $('#transactiontype').val("");
                $("#gridtable").jqGrid('clearGridData', true);

            }

            function resetFieldData() {

                $('#transactiontype').val("");
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

                <s:set id="vupload" var="vsearch"><s:property value="vupload" default="true"/></s:set>
                <s:set id="vview" var="vview"><s:property value="vview" default="true"/></s:set>
                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="txnbtchupload" method="post" action="IPGTxnBatchUploadServlet" enctype="multipart/form-data" theme="simple">


                        <table border="0" cellspacing="5">

                            <tbody>

                                <tr>
                                    <td></td>
                                    <td>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Transaction Type <span style="color: red">*</span></td>
                                    <td><s:select id="transactiontype" list="%{txnTypeList}"
                                              name="transactiontype" headerKey="" headerValue="--Select Type--"
                                              listKey="txntypecode" listValue="txntypedes" /></td>
                                </tr>

                                <tr>
                                    <td></td>
                                    <td>
                                        <s:file name="file"></s:file>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td><span class="mandatoryfield">Mandatory fields are marked with *</span></td>
                                        <td>
                                        </td>
                                    </tr>


                                    <tr>
                                        <td> <s:url var="uploadurl" action="UploadIPGTxnBatchUploadServlet"/>
                                    <td>
                                        <sj:submit button="true" href="%{uploadurl}" value="Upload" targets="divmsg" id="uploadButton"  disabled="#vupload" /> 
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
                    <s:url var="listurl" action="SearchIPGTxnBatchUploadServlet"/>
                    <sjg:grid
                        id="gridtable"
                        caption="Transaction Batch Upload"
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
                        <sjg:gridColumn name="fileid" index="F.FILEID"  title="File Id"  sortable="true"/>
                        <sjg:gridColumn name="filename" index="F.FILENAME" title="File Name"  sortable="true"/>
                        <sjg:gridColumn name="filetype" index="S1.DESCRIPTION" title="File Type"  sortable="true"/>
                        <sjg:gridColumn name="status"  index="S2.DESCRIPTION" title="Status"  sortable="true"/>
                        <sjg:gridColumn name="fileid" title="View" width="25" align="center" 
                                        formatter="viewformatter" hidden="#vview"/>                          
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
