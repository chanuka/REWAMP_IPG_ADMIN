<%-- 
    Document   : uploadcertificate
    Created on : Jul 5, 2018, 12:09:39 PM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="/struts-jquery-tags" prefix="sj" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript">
            function resetFieldData() {
//                $('#merchantId').val("");
                $('#file').val("");
            }
            function resetData() {
//                $('#merchantId').val("");
                $('#file').val("");
                $('#divmsgu').text("");
            }
            function validateMerchantCertificateInputs() {
                var merID = $('#merchantId_uc').val();
                var file = $('#file').val();
                var filesufix = file.substring(file.length - 3, file.length);
                if (!merID) {
                    $('#divmsgu').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Merchant ID cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!file) {
                    $('#divmsgu').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>File cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (filesufix != 'cer') {
                    $('#divmsgu').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Select valid file.</span></p>\
                </div>\
                </div>');
                    return;
                }

                $("#upButton").click();
            }
        </script>
    </head>

    <body>

        <div class="content-message">
            <s:div id="divmsgu">
                <s:actionmessage theme="jquery"/>
                <s:actionerror theme="jquery"/>
            </s:div>
        </div>

        <s:set id="vupload" var="vupload"><s:property value="vupload" default="true"/></s:set>

            <!-- Form -->
            <div class="content-form">
            <s:form name="uploadmercertificate" id="uploadmercertificate" method="post" action="uploadMerchantCertificate" theme="simple" enctype="multipart/form-data">

                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label"  >Merchant ID <span style="color: red">*</span></label>
                            <s:textfield name="merchantId_uc" id="merchantId_uc"  maxLength="16" readonly="true" cssClass="form-control"/>

                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">Certificate <span style="color: red">*</span></label>
                            <s:file cssClass="choosefileClass" name="file" label="file" size="80" id="file"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div>

                <s:url var="upurl" action="uploadCertMerchant"/>

                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <sj:submit button="true" href="%{upurl}"  id="upButton"  targets="divmsgu" cssStyle="display: none; visibility: hidden;"/>

                            <sj:a cssClass="btn btn-sm btn-functions" button="true" id="triButton" onclick="validateMerchantCertificateInputs()" disabled="#vupload">Upload</sj:a> 
                            <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetData()"/>
                        </div>
                    </div>
                </div>
            </s:form>
        </div>
    </body>
</html>
