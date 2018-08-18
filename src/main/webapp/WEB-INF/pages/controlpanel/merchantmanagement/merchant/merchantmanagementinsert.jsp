<%-- 
    Document   : merchantmanagementinsert
    Created on : Jul 17, 2018, 2:54:22 PM
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


        <script type="text/javascript">
            $(document).ready(function () {
//                $("#iexpirydate").val('${PASSWORDEXPIRYPERIOD}');
            });

            function cancelDataInsert(key) {
                if (key == 'div1') {
                    $('#imerchantId').val("");
                    $('#iheadmerchant').val("");
                    $('#iviewmerchantstatus').attr('hidden',true);
                    $('#imerchantName').val("");
                    $('#iemail').val("");
                    $('#iaddress1').val("");
                    $('#iaddress2').val("");
                    $('#iprimaryURL').val("");
                    $('#isecondaryURL').val("");
                    $('#icity').val("");
                    $('#istateCode').val("");
                    $('#idrsURL').val("");
                    $('#idreURL').val("");
                    $('#ipostalCode').val("");
                    $('#iprovince').val("");
                    $('#idateOfRegistry').val("");
                } else if (key == 'div2') {
                    $('#idateOfExpire').val("");
                    $('#icountry').val("");
                    $('#imobile').val("");
                    $('#istatus').val("");
                    $('#icontactPerson').val("");
                    $('#itelNo').val("");
                    $('#ifax').val("");
                    $('#iremarks').val("");
                    $('#iauthreqstatus').val("");
                    $('#iriskprofile').val("");
                    $('[id="isecurityMechanismDigitallySign"][value="DigitallySign"]').prop('checked', false);
                    $('[id="isecurityMechanismSymmetricKey"][value="SymmetricKey"]').prop('checked', false);
                }
                $("#divmsginsert").empty();
            }
            function  backDivInsert() {
                $('#itabone').show();
                $('#itabtwo').hide();
            }

            function nextDivInsert() {
                $('#itabone').hide();
                $('#itabtwo').show();
            }

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

            $(document).ready(function () {
//                $.each($('.tooltip'), function (index, element) {
//                    $(this).remove();
//                });
//                $('[data-toggle="tooltip"]').tooltip({
//                    'placement': 'right'
//
//                });
            });
            
            function chekMerchantCustomer(){
                var headmerchant = $('#iheadmerchant').val();
                if(headmerchant == ''){
                    $('#iviewmerchantstatus').attr('hidden',true);
                }else{
                    $.ajax({
                        url: '${pageContext.request.contextPath}/findDefaultMerchant.ipg',
                        data: {
                            headmerchant: headmerchant
                        },
                        dataType: "json",
                        type: "POST",
                        success: function (data) {
                            $('#divmsgupdate').empty();
                            var msg = data.message;
                            if (msg) {
                                alert(data.message)
                            } else {
                                $('#iviewmerchantstatus').attr('hidden',false);
                                $('#idefaultmerchant').val(data.defaultmerchant);
                                $('#idefaultmerchant').attr('disabled',true);
                                $('#iisdefaultmerchany').val(data.defaultmerchant);
                            }
                        },
                        error: function (data) {
                            $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                            window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                        }
                    });
                }
            }

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
            <s:form id="merchantinsert" method="post" action="Merchant" theme="simple" cssClass="form" enctype="multipart/form-data">   
                <div id="itabone" >
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Merchant Customer Name<span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="iheadmerchant" list="%{headmerchantList}"
                                          name="headmerchant" headerKey="" headerValue="--Select--"
                                          listKey="merchantcustomerid" listValue="merchantcustomername" onchange="chekMerchantCustomer()"/>
                            </div>
                        </div>
                        <div class="col-md-3" id="iviewmerchantstatus" hidden>
                            <div class="form-group">
                                <label class="control-label">Default Merchant<span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="idefaultmerchant" list="%{defaultmerchantList}"
                                          name="defaultmerchant" headerKey="" headerValue="--Select--" disabled="true" 
                                          listKey="statuscode" listValue="description" />
                                <s:hidden name="isdefaultmerchany" id="iisdefaultmerchany" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Merchant ID <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="merchantId" id="imerchantId"
                                             maxLength="15"
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Merchant Name<span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="merchantName" id="imerchantName"
                                             maxLength="255"
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Email<span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="email" id="iemail" maxLength="100"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Address 1<span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="address1" id="iaddress1" maxLength="255" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Address 2<span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="address2" id="iaddress2" maxLength="255" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Primary URL<span style="color: red">*</span></label>
                                <s:textarea cssClass="form-control" name="primaryURL" id="iprimaryURL" cols="10" rows="1" wrap="off" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Secondary URL<span style="color: red">*</span></label>
                                <s:textarea cssClass="form-control" name="secondaryURL" id="isecondaryURL" cols="10" rows="1" wrap="off" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">City<span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control"  name="city" id="icity"
                                             maxLength="64"
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">State Code<span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="stateCode" id="istateCode"
                                             maxLength="16"
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Dynamic Return Success URL<span style="color: red">*</span></label>
                                <s:textarea cssClass="form-control" name="drsURL" id="idrsURL" cols="10" rows="1" wrap="off" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Dynamic Return Error URL<span style="color: red">*</span></label>
                                <s:textarea cssClass="form-control" name="dreURL" id="idreURL" cols="10" rows="1" wrap="off" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Postal Code</label>
                                <s:textfield cssClass="form-control" name="postalCode" id="ipostalCode"
                                             maxLength="16"
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Province</label>
                                <s:textfield cssClass="form-control" name="province" id="iprovince"
                                             maxLength="16"
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Date of Registry <span style="color: red">*</span></label>
                                <sj:datepicker cssClass="form-control" id="idateOfRegistry" name="dateOfRegistry" readonly="true"
                                               maxDate="+1d" changeYear="true" buttonImageOnly="true"
                                               displayFormat="dd/mm/yy" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                        </div>
                    </div> 
                    <div class="row">
                        <div class="col-sm-12 text-right">
                            <div class="form-group">
                                <sj:submit 
                                    button="true" 
                                    value="Reset" 
                                    onClick="cancelDataInsert('div1')"
                                    cssClass="btn btn-sm btn-reset"
                                    />                          
                                <sj:submit
                                    button="true"
                                    value="Next"
                                    id="ifnextbtn"
                                    onclick="nextDivInsert()"
                                    cssClass="btn btn-sm btn-functions" 
                                    />                        
                            </div>
                        </div>
                    </div>
                </div>
                <div id="itabtwo" hidden>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Date of Expiry <span style="color: red">*</span></label>
                                <sj:datepicker cssClass="form-control" id="idateOfExpire" name="dateOfExpire" readonly="true"
                                               minDate="d" changeYear="true" buttonImageOnly="true"
                                               displayFormat="dd/mm/yy" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Country<span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="icountry" list="%{countryList}"
                                          name="country" headerKey="" headerValue="--Select--"
                                          listKey="countrycode" listValue="description" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Mobile</label>
                                <s:textfield cssClass="form-control" name="mobile" id="imobile" maxLength="11"
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Status<span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="istatus" list="%{statusList}"
                                          name="status" headerKey="" headerValue="--Select--"
                                          listKey="statuscode" listValue="description" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Contact Person<span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="contactPerson" id="icontactPerson"
                                             maxLength="255"
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Tel No</label>
                                <s:textfield cssClass="form-control" name="telNo" id="itelNo" maxLength="11"
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Fax</label>
                                <s:textfield cssClass="form-control" name="fax" id="ifax" maxLength="11"
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Remarks</label>
                                <s:textfield cssClass="form-control" name="remarks" id="iremarks"
                                             maxLength="255"
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Authentication Required Status<span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="iauthreqstatus" list="authreqstatusList"
                                          name="authreqstatus" headerKey=""
                                          headerValue="--Select Status--" listKey="statuscode"
                                          listValue="description"  />
                            </div>
                        </div>    
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Risk Profile <span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="iriskprofile" list="riskprofileList"
                                          name="riskprofile" headerKey=""
                                          headerValue="--Select Risk profile--" listKey="riskprofilecode"
                                          listValue="description"  />
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label">Security Mechanism<span style="color: red">*</span></label><br>
                                <s:radio name="securityMechanism" id="isecurityMechanism"  list="%{securitymechamismList}" cssClass="radioClass"/>
                            </div>
                        </div> 
                    </div>        

                    <div class="row">
                        <div class="col-md-12">
                            <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                        </div>
                    </div> 
                    <s:url var="addurl" action="addMerchant"/>
                    <div class="row">
                        <div class="col-md-12 text-right" >
                            <div class="form-group">
                                <sj:submit 
                                    button="true" 
                                    value="Reset" 
                                    onClick="cancelDataInsert('div2')"
                                    cssClass="btn btn-sm btn-reset"
                                    /> 
                                <sj:submit 
                                    button="true" 
                                    value="Back" 
                                    onClick="backDivInsert()"
                                    cssClass="btn btn-sm btn-functions"
                                    /> 
                                <sj:submit 
                                    cssClass="btn btn-sm btn-functions" 
                                    button="true" 
                                    href="%{addurl}" 
                                    value="Add" 
                                    targets="divmsginsert" 
                                    id="addButton" />
                            </div>
                        </div>
                    </div>
                </div>
            </s:form>        
        </div>
    </body>

</html>