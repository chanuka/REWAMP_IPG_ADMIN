<%-- 
    Document   : merchantaddoninsert
    Created on : Jul 20, 2018, 9:15:57 AM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<link rel="stylesheet" href="resouces/css/common/common_popup.css">-->

        <title>Insert System User</title>
        <script type="text/javascript">
            $(document).ready(function() {
//                $("#iexpirydate").val('${PASSWORDEXPIRYPERIOD}');
            });
            $(document).ready(function() {
                jscolor.init();
              });
            $.subscribe('resetAddButton', function(event, data) {
                $('#imerchantID').val("");
                $('#imerchantNameID').val("");
                $('#iimage').val("");
                $('#icordinateX').val("");
                $('#icordinateY').val("");
                $('#idisplayText').val("");
                document.getElementById('ithemeColor').color.fromString('FFFFFF');
                $('#ifontFamily').val("");
                $('#ifontStyle').val("");
                $('#ifontSize').val("");
                $('#iremarks').val("");
                $("#divmsginsert").empty();
            });
            function isNumberinsert(evt) {
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

            $(document).ready(function() {
//                $.each($('.tooltip'), function (index, element) {
//                    $(this).remove();
//                });
//                $('[data-toggle="tooltip"]').tooltip({
//                    'placement': 'right'
//
//                });
            });

        </script>
        <style>
            .tooltip {

                background-color: black;
                color: #fff;
                border-radius: 6px;
                padding: 5px 0;
                white-space: pre-line;
                height: auto;
            }
            .tooltip-inner {
                min-width: 375%;
                max-width: 100%; 
                text-align: left;
            }

        </style>

    </head>
    <body  >
        <div class="content-message">
            <s:div id="divmsginsert">
                <s:actionerror theme="jquery"/>
                <s:actionmessage theme="jquery"/>
            </s:div>
        </div>
        <div class="content-form">
            <s:form id="merchantaddoninsert" method="post" action="MerchantAddon" theme="simple" cssClass="form" enctype="multipart/form-data">   
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Merchant ID <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="imerchantNameID" list="%{merchantList}"  name="merchantNameID" headerKey="" headerValue="--Select Merchant Name--"  listKey="merchantid" listValue="merchantid" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Tag Line <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" id="idisplayText" name="displayText" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >X Cordinate <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" id="icordinateX" name="cordinateX" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Y Cordinate <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" id="icordinateY" name="cordinateY" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Theme Color <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control color" id="ithemeColor" name="themeColor" readonly="true" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Font Family<span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="ifontFamily" list="%{fontfamilyList}"  name="fontFamily" headerKey="" headerValue="--Select Font Family--"  />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Font Style<span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="ifontStyle" list="%{fontstyleList}"  name="fontStyle" headerKey="" headerValue="--Select Font Style--"   />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Font Size<span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="ifontSize" list="%{fontsizeList}"  name="fontSize" headerKey="" headerValue="--Select Font Size--"   />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Remarks <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" id="iremarks" name="remarks" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Logo Path <span style="color: red">*</span></label>
                            <s:file name="image" id="iimage" cssClass="choosefileClass"/>
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
                            <s:url var="addurl" action="addMerchantAddon"/>
                            <sj:submit button="true" href="%{addurl}" value="Add" targets="divmsginsert" id="addButton"  />
                        </div>
                    </div>
                </div>        
                <div class="row">
                    <div class="col-md-12 text-right">
                        <div class="form-group">
                            <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClickTopics="resetAddButton" />
                            <sj:submit cssClass="btn btn-sm btn-functions" button="true" onclick="validateAddMerchantAddonUserInputs()"  value="Add"  id="addButton" />
                        </div>
                    </div>
                </div>
            </s:form>        
        </div>
    </body>

</html>