<%-- 
    Document   : merchantaddonedit
    Created on : Jul 20, 2018, 9:28:00 AM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>

            $(document).ready(function() {
                jscolor.init();
              });
              
            function enablefile1() {
                var status = $("#eimageid").is(":checked");
                if (status) {
                    $("#eimage").attr('disabled', false);
                } else {
                    $("#eimage").attr('disabled', true);
                }
            }
            
            function editMerchantAddon(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findMerchantAddon.ipg',
                    data: {
                        merchantID: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {
                            $('#emerchantID').val(data.merchantID);
                            $('#emerchantNameID').val(data.merchantID);
                            $("#emerchantNameID option").attr('disabled', true);
                            $('#eimage').val(""); 
                            $("#eimage").attr('disabled', true);
                            $("#eimageid").attr("checked", false);
                            $('#ecordinateX').val(data.cordinateX);
                            $('#ecordinateY').val(data.cordinateY);
                            $('#edisplayText').val(data.displayText);
                            document.getElementById('ethemeColor').color.fromString(data.themeColor);
                            $('#efontFamily').val(data.fontFamily);
                            $('#efontStyle').val(data.fontStyle);
                            $('#efontSize').val(data.fontSize);
                            $('#eremarks').val(data.remarks);
                        }
                    },
                    error: function(data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            function cancelUpateData() {
                var merchantID = $('#emerchantID').val();
                editMerchantAddon(merchantID);
            }

            function isNumber(evt) {
                evt = (evt) ? evt : window.event;
                var charCode = (evt.which) ? evt.which : evt.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                return true;
            }

            function alpha(e) {
                var k;
                document.all ? k = e.keyCode : k = e.which;
                return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57) || (k == 13));
            }

        </script>

    </head>
    <body >
        <div class="content-message">
            <s:div id="divmsgupdate">
                <s:actionerror theme="jquery"/>
                <s:actionmessage theme="jquery"/>
            </s:div>
        </div>

        <div class="content-form">
            <s:form id="merchantaddonedit" method="post" action="MerchantAddon"  theme="simple" cssClass="form" enctype="multipart/form-data"> 
                <s:hidden id="emerchantID" name="merchantID" /> 
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Merchant ID <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="emerchantNameID" list="%{merchantList}"  name="merchantNameID" headerKey="" headerValue="--Select Merchant Name--"  listKey="merchantid" listValue="merchantid" disabled="true"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Tag Line <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" id="edisplayText" name="displayText" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >X Cordinate <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" id="ecordinateX" name="cordinateX" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Y Cordinate <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" id="ecordinateY" name="cordinateY" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Theme Color <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control color" id="ethemeColor" name="themeColor" readonly="true" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Font Family<span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="efontFamily" list="%{fontfamilyList}"  name="fontFamily" headerKey="" headerValue="--Select Font Family--"  />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Font Style<span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="efontStyle" list="%{fontstyleList}"  name="fontStyle" headerKey="" headerValue="--Select Font Style--"   />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Font Size<span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="efontSize" list="%{fontsizeList}"  name="fontSize" headerKey="" headerValue="--Select Font Size--"   />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Remarks <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" id="eremarks" name="remarks" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Logo Path <span style="color: red">*</span></label>
                            <s:file name="image" id="eimage" cssClass="choosefileClass" disabled="true"/>
                            <s:checkbox name="imageid" id="eimageid" onclick="enablefile1()"/> 
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 
                <div class="row" style="display: none; visibility: hidden;">
                    <div class="col-md-12">
                        <div class="form-group">
                            <s:url var="updateurl" action="updateMerchantAddon"/>
                            <sj:submit button="true" href="%{updateurl}" value="Update" targets="divmsgupdate"  id="updateButton"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 text-right">
                        <div class="form-group">
                            <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="cancelUpateData()" />
                            <sj:submit cssClass="btn btn-sm btn-functions" button="true" onclick="validateUpdateMerchantAddonUserInputs()" value="Update" targets="divmsgupdate" id="updateButton_submit" />
                        </div>
                    </div>
                </div>
            </s:form>        
        </div>
    </body>
</html>
