<%-- 
    Document   : merchantmanagementedit
    Created on : Jul 18, 2018, 4:40:19 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            $(document).ready(function () {
                var isdefaultmerchany = $('#eisdefaultmerchany').val();
                if (isdefaultmerchany == "YES") {
                    $('#eheadmerchant').attr('disabled', true);
                }
            });
            
            function checkDisable(){
                var isdefaultmerchany = $('#eisdefaultmerchany').val();
                if (isdefaultmerchany == "YES") {
                    $('#eheadmerchant').attr('disabled', true);
                }
            }

            function cancelDataEdit(key) {
                var merchantId = $('#emerchantId').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/findMerchant.ipg',
                    data: {
                        merchantId: merchantId
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {
                            if (key == 'div1') {
                                $('#emerchantId').val(data.merchantId);
                                $('#emerchantId').attr('readOnly', true);
                                $('#eheadmerchant').val(data.headmerchant);
                                $('#eheadmerchant2').val(data.headmerchant);
                                if (data.isdefaultmerchany == "YES") {
                                    $('#eheadmerchant').attr('disabled', true);
                                } else {
                                    $('#eheadmerchant').attr('disabled', false);
                                }
                                $('#edefaultmerchant').val(data.isdefaultmerchany);
                                $('#eisdefaultmerchany').val(data.isdefaultmerchany);
                                $('#emerchantName').val(data.merchantName);
                                $('#eemail').val(data.email);
                                $('#eaddress1').val(data.address1);
                                $('#eaddress2').val(data.address2);
                                $('#eprimaryURL').val(data.primaryURL);
                                $('#esecondaryURL').val(data.secondaryURL);
                                $('#ecity').val(data.city);
                                $('#estateCode').val(data.stateCode);
                                $('#edrsURL').val(data.drsURL);
                                $('#edreURL').val(data.dreURL);
                                $('#epostalCode').val(data.postalCode);
                                $('#eprovince').val(data.province);
                                $('#edateOfRegistry').val(data.dateOfRegistry);
                            } else if (key == 'div2') {
                                $('#edateOfExpire').val(data.dateOfExpire);
                                $('#ecountry').val(data.country);
                                $('#emobile').val(data.mobile);
                                $('#estatus').val(data.status);
                                $('#econtactPerson').val(data.contactPerson);
                                $('#etelNo').val(data.telNo);
                                $('#efax').val(data.fax);
                                $('#eremarks').val(data.remarks);
                                $('#eauthreqstatus').val(data.authreqstatus);
                                $('#eriskprofile').val(data.riskprofile);
                                if (data.securityMechanism == 'DigitallySign') {
                                    $('[value="SymmetricKey"][id="esecurityMechanismSymmetricKey"]').prop('checked', false);
                                    $('[value="DigitallySign"][id="esecurityMechanismDigitallySign"]').prop('checked', true);
                                } else if (data.securityMechanism == 'SymmetricKey') {
                                    $('[value="DigitallySign"][id="esecurityMechanismDigitallySign"]').prop('checked', false);
                                    $('[value="SymmetricKey"][id="esecurityMechanismSymmetricKey"]').prop('checked', true);
                                }
                            }
                        }
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            function chekMerchantCustomerEdit(){
                 var headmerchant = $('#eheadmerchant').val();
                if(headmerchant == ''){
                    $('#eviewmerchantstatus').attr('hidden',true);
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
                                $('#eviewmerchantstatus').attr('hidden',false);
                                $('#edefaultmerchant').val(data.defaultmerchant);
                                $('#edefaultmerchant').attr('disabled',true);
                                $('#eisdefaultmerchany').val(data.defaultmerchant);
                            }
                        },
                        error: function (data) {
                            $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                            window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                        }
                    });
                }
            }

            function  backDivEdit() {
                $('#etabone').show();
                $('#etabtwo').hide();
            }

            function nextDivEdit() {
                $('#etabone').hide();
                $('#etabtwo').show();
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
            <s:form id="merchantedit" method="post" action="Merchant"  theme="simple" cssClass="form" enctype="multipart/form-data"> 
                <div id="etabone" >
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Merchant Customer Name<span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="eheadmerchant" list="%{headmerchantList}"
                                          name="headmerchant" headerKey="" headerValue="--Select--"
                                          listKey="merchantcustomerid" listValue="merchantcustomername" onchange="chekMerchantCustomerEdit()" 
                                          />
                                <s:hidden name="headmerchant2" id="eheadmerchant2" />
                            </div>
                        </div>
                        <div class="col-md-3" id="eviewmerchantstatus" >
                            <div class="form-group">
                                <label class="control-label">Default Merchant<span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="edefaultmerchant" list="%{defaultmerchantList}"
                                          name="defaultmerchant" headerKey="" headerValue="--Select--" disabled="true" 
                                          listKey="statuscode" listValue="description" />
                                <s:hidden name="isdefaultmerchany" id="eisdefaultmerchany" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Merchant ID <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="merchantId" id="emerchantId"
                                             maxLength="15"
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" 
                                             readonly="true"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Merchant Name<span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="merchantName" id="emerchantName"
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
                                <s:textfield cssClass="form-control" name="email" id="eemail" maxLength="100"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Address 1<span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="address1" id="eaddress1" maxLength="255" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Address 2<span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="address2" id="eaddress2" maxLength="255" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Primary URL<span style="color: red">*</span></label>
                                <s:textarea cssClass="form-control" name="primaryURL" id="eprimaryURL" cols="10" rows="1" wrap="off" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Secondary URL<span style="color: red">*</span></label>
                                <s:textarea cssClass="form-control" name="secondaryURL" id="esecondaryURL" cols="10" rows="1" wrap="off" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">City<span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control"  name="city" id="ecity"
                                             maxLength="64"
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">State Code<span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="stateCode" id="estateCode"
                                             maxLength="16"
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Dynamic Return Success URL<span style="color: red">*</span></label>
                                <s:textarea cssClass="form-control" name="drsURL" id="edrsURL" cols="10" rows="1" wrap="off" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Dynamic Return Error URL<span style="color: red">*</span></label>
                                <s:textarea cssClass="form-control" name="dreURL" id="edreURL" cols="10" rows="1" wrap="off" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Postal Code</label>
                                <s:textfield cssClass="form-control" name="postalCode" id="epostalCode"
                                             maxLength="64"
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Province</label>
                                <s:textfield cssClass="form-control" name="province" id="eprovince"
                                             maxLength="64"
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Date of Registry <span style="color: red">*</span></label>
                                <sj:datepicker cssClass="form-control" id="edateOfRegistry" name="dateOfRegistry" readonly="true"
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
                                    onClick="cancelDataEdit('div1')"
                                    cssClass="btn btn-sm btn-reset"
                                    />                          
                                <sj:submit
                                    button="true"
                                    value="Next"
                                    id="efnextbtn"
                                    onclick="nextDivEdit()"
                                    cssClass="btn btn-sm btn-functions" 
                                    />                        
                            </div>
                        </div>
                    </div>
                </div>
                <div id="etabtwo" hidden>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Date of Expiry <span style="color: red">*</span></label>
                                <sj:datepicker cssClass="form-control" id="edateOfExpire" name="dateOfExpire" readonly="true"
                                               minDate="d" changeYear="true" buttonImageOnly="true"
                                               displayFormat="dd/mm/yy" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Country<span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="ecountry" list="%{countryList}"
                                          name="country" headerKey="" headerValue="--Select--"
                                          listKey="countrycode" listValue="description" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Mobile</label>
                                <s:textfield cssClass="form-control" name="mobile" id="emobile" maxLength="11"
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Status<span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="estatus" list="%{statusList}"
                                          name="status" headerKey="" headerValue="--Select--"
                                          listKey="statuscode" listValue="description" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Contact Person<span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="contactPerson" id="econtactPerson"
                                             maxLength="255"
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Tel No</label>
                                <s:textfield cssClass="form-control" name="telNo" id="etelNo" maxLength="11"
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Fax</label>
                                <s:textfield cssClass="form-control" name="fax" id="efax" maxLength="11"
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label">Remarks</label>
                                <s:textfield cssClass="form-control" name="remarks" id="eremarks"
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
                                <s:select cssClass="form-control" id="eauthreqstatus" list="authreqstatusList"
                                          name="authreqstatus" headerKey=""
                                          headerValue="--Select Status--" listKey="statuscode"
                                          listValue="description"  />
                            </div>
                        </div>    
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Risk Profile <span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="eriskprofile" list="riskprofileList"
                                          name="riskprofile" headerKey=""
                                          headerValue="--Select Risk profile--" listKey="riskprofilecode"
                                          listValue="description"  />
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label">Security Mechanism<span style="color: red">*</span></label><br>
                                <s:radio name="securityMechanism" id="esecurityMechanism"  list="%{securitymechamismList}" cssClass="radioClass"/>
                            </div>
                        </div> 
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                        </div>
                    </div> 
                    <s:url var="updateurl" action="updateMerchant"/>
                    <div class="row">
                        <div class="col-md-12 text-right" >
                            <div class="form-group">
                                <sj:submit 
                                    button="true" 
                                    value="Reset" 
                                    onClick="cancelDataEdit('div2')"
                                    cssClass="btn btn-sm btn-reset"
                                    /> 
                                <sj:submit 
                                    button="true" 
                                    value="Back" 
                                    onClick="backDivEdit()"
                                    cssClass="btn btn-sm btn-functions"
                                    /> 
                                <sj:submit 
                                    cssClass="btn btn-sm btn-functions" 
                                    button="true" 
                                    onclick="checkDisable()"
                                    href="%{updateurl}" 
                                    value="Update" 
                                    targets="divmsgupdate" 
                                    id="updateButton" />
                            </div>
                        </div>
                    </div>
                </div>
            </s:form>        
        </div>
    </body>
</html>

