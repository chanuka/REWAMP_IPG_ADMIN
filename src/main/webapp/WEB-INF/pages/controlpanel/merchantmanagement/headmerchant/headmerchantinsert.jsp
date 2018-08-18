<%-- 
    Document   : headmerchantinsert
    Created on : Jul 16, 2018, 4:25:24 PM
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
            $(document).ready(function() {
//                $("#iexpirydate").val('${PASSWORDEXPIRYPERIOD}');
            });

            function cancelDataInsert(key) {
                if (key == 'div1') {
                    $('#imerchantcustomerid').val("");
                    $('#imerchantid').val("");
                    $('#imerchantname').val("");
                    $('#iaddress1').val("");
                    $('#iaddress2').val("");
                    $('#icity').val("");
                    $('#icountry').val("");
                    $('#istatecode').val("");
                    $('#ipostalcode').val("");
                    $('#iprovince').val("");
                    $('#imobile').val("");
                    $('#itel').val("");
                    $('#ifax').val("");
                    $('#iemail').val("");
                    $('#iprimaryurl').val("");
                    $('#isecondaryurl').val("");
                } else if (key == 'div2') {
                    $('#idateofregistry').val("");
                    $('#idateofexpiry').val("");
                    $('#istatus').val("");
                    $('#icontactperson').val("");
                    $('#iremarks').val("");
                    $('#iriskprofile').val("");
                    $('#iauthreqstatus').val("");
                    $('#itxninitstatus').val("");
                    $('#idynamicreturnsuccessurl').val("");
                    $('#idynamicreturnerrorurl').val("");
                    $('[id="isecuritymechanismDigitallySign"][value="DigitallySign"]').prop('checked', true);
                    $('[id="isecuritymechanismDigitallySign"][value="SymmetricKey"]').prop('checked', false);
                }
                $("#divmsginsert").empty();
            }
            function  backDivInsert(){
                $('#itabone').show();
                $('#itabtwo').hide();
            }
            
            function nextDivInsert(){
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
            <s:form id="headmerchantsinsert" method="post" action="HeadMerchants" theme="simple" cssClass="form" enctype="multipart/form-data">   
                <div id="itabone" >
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Merchant Customer ID <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="merchantcustomerid" id="imerchantcustomerid" 
                                             maxLength="15" 
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Merchant ID <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="merchantid" id="imerchantid" 
                                             maxLength="15" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Merchant Name <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="merchantname" id="imerchantname" 
                                             maxLength="255" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Address1 <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="address1" id="iaddress1" maxLength="255" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Address2</label>
                                <s:textfield cssClass="form-control" name="address2" id="iaddress2" maxLength="255" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >City <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="city" id="icity" 
                                             maxLength="64" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Country <span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="icountry" list="countryList"
                                          name="country" headerKey=""
                                          headerValue="--Select Country--" listKey="countrycode"
                                          listValue="description"  />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >State Code</label>
                                <s:textfield cssClass="form-control" name="statecode" id="istatecode" 
                                             maxLength="16" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Postal Code</label>
                                <s:textfield cssClass="form-control" name="postalcode" id="ipostalcode" 
                                             maxLength="64" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Province</label>
                                <s:textfield cssClass="form-control" name="province" id="iprovince" 
                                             maxLength="64" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Mobile <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="mobile" id="imobile" 
                                             maxLength="16" 
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Tel No <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="tel" id="itel" 
                                             maxLength="16" 
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Fax <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="fax" id="ifax" 
                                             maxLength="16" 
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >E-Mail <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="email" id="iemail" maxLength="255" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Primary URL <span style="color: red">*</span></label>                                
                                <s:textarea cssClass="form-control" name="primaryurl" id="iprimaryurl" maxLength="255" cols="10" rows="1" wrap="off" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Secondary URL <span style="color: red">*</span></label>
                                <s:textarea cssClass="form-control" name="secondaryurl" id="iprimaryurl" maxLength="255" cols="10" rows="1" wrap="off" />
                            </div>
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
                                <label class="control-label" >Dynamic Return Success URL <span style="color: red">*</span></label>
                                <s:textarea cssClass="form-control" name="dynamicreturnsuccessurl" id="idynamicreturnsuccessurl" maxLength="255" cols="10" rows="1" wrap="off" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Dynamic Return Error URL <span style="color: red">*</span></label>
                                <s:textarea cssClass="form-control" name="dynamicreturnerrorurl" id="idynamicreturnerrorurl" maxLength="255" cols="10" rows="1" wrap="off" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Date of Registry <span style="color: red">*</span></label>
                                <sj:datepicker cssClass="form-control" id="idateofregistry" name="dateofregistry" readonly="true" maxDate="+1d" changeYear="true"
                                               buttonImageOnly="true" displayFormat="dd/mm/yy" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Date of Expiry <span style="color: red">*</span></label>
                                <sj:datepicker cssClass="form-control" id="idateofexpiry" name="dateofexpiry" readonly="true"  changeYear="true"
                                               minDate="d" buttonImageOnly="true" displayFormat="dd/mm/yy" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Status <span style="color: red">*</span></label>
                                <s:select id="istatus" list="statusList"
                                          name="status" headerKey="" cssClass="form-control"
                                          headerValue="--Select Status--" listKey="statuscode"
                                          listValue="description"  />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Contact Person <span style="color: red">*</span></label>
                                <s:textfield name="contactperson" id="icontactperson" 
                                             maxLength="255" cssClass="form-control"
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Remarks</label>
                                <s:textfield name="remarks" id="iremarks" cssClass="form-control"
                                             maxLength="255" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
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
                                <label class="control-label" >Transaction Initiated Status <span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="itxninitstatus" list="txninitstatusList"
                                          name="txninitstatus" headerKey=""
                                          headerValue="--Select Status--" listKey="statuscode"
                                          listValue="description"  />
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label" >Security Mechanism <span style="color: red">*</span></label><br>
                                <s:radio label="securitymechanism" id="isecuritymechanism" name="securitymechanism" list="{'DigitallySign','SymmetricKey'}" cssClass="radioClass"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                        </div>
                    </div> 
                    <s:url var="addurl" action="addHeadMerchants"/>
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

