<!--
        Created on : Dec 13, 2013, 10:47:15 AM
        author     : thushanth
-->

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="/struts-jquery-tags" prefix="sj" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/pages/template/header.jspf" %>

        <script type="text/javascript">
            function resetFieldData() {
                $('#txnType').val("");
                $('#file').val("");
            }

            function resetData() {
                $('#txnType').val("");
                $('#file').val("");
                $('#divmsg').text("");
            }

        </script>

        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Transaction Batch Upload</title>
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

                    <s:set id="vupload" var="vupload"><s:property value="vupload" default="true"/></s:set>

                        <!-- Form -->
                        <div class="content-form">
                        <s:form id="TransactionBatchUploadmgt" method="post" action="uploadTransactionBatchUpload" theme="simple" enctype="multipart/form-data">
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
                                        <td>Transaction Type <span style="color: red">*</span></td>
                                        <td>
                                            <s:select id="txnType"
                                                      list="%{statusList}"
                                                      name="txnType"
                                                      headerKey=""
                                                      headerValue="--Select--"
                                                      listKey="description"
                                                      listValue="description"/>
                                        </td>

                                    </tr>

                                    <tr>
                                        <td>Upload file <span style="color: red">*</span></td>
                                        <td>
                                            <s:file name="file" label="file" size="80" id="file"/>
                                        </td>

                                    </tr>

                                    <tr>
                                        <td><span class="mandatoryfield">Mandatory fields are marked with *</span></td>
                                        <td>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><s:url var="uploadurl" action="uploadTransactionBatchUpload"/>
                                        <td>

                                            <sj:submit button="true" href="%{uploadurl}" value="Upload" id="uploadButton" disabled="#vupload"  />

                                            <sj:submit button="true" value="Reset" name="reset" onClick="resetData()"/>

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
                </div>
            </div>
        </section>
        <footer>
            <!--for footer-->
            <jsp:include page="/WEB-INF/pages/template/footer.jsp"/>
        </footer>
    </body>
</html>
