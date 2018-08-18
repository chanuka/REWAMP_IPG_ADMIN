<%-- 
    Document   : headmerchantedit
    Created on : Jul 16, 2018, 4:57:21 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            
             
            function cancelDataEdit(key) {
                var merchantcustomerid = $('#emerchantcustomerid').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/findHeadMerchants.ipg',
                    data: {
                        merchantcustomerid: merchantcustomerid
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
                                $('#emerchantcustomerid').val(data.merchantcustomerid);
                                $('#emerchantcustomerid').attr('readOnly', true);
                                $('#emerchantid').val(data.merchantid);
                                $('#emerchantid').attr('readOnly', true);
                                $('#emerchantname').val(data.merchantname);
                                $('#eaddress1').val(data.address1);
                                $('#eaddress2').val(data.address2);
                                $('#ecity').val(data.city);
                                $('#ecountry').val(data.country);
                                $('#estatecode').val(data.statecode);
                                $('#epostalcode').val(data.postalcode);
                                $('#eprovince').val(data.province);
                                $('#emobile').val(data.mobile);
                                $('#etel').val(data.tel);
                                $('#efax').val(data.fax);
                                $('#eemail').val(data.email);
                                $('#eprimaryurl').val(data.primaryurl);
                                $('#esecondaryurl').val(data.secondaryurl);
                            } else if (key == 'div2') {
                                $('#edateofregistry').val(data.dateofregistry);
                                $('#edateofexpiry').val(data.dateofexpiry);
                                $('#estatus').val(data.status);
                                $('#econtactperson').val(data.contactperson);
                                $('#eremarks').val(data.remarks);
                                $('#eriskprofile').val(data.riskprofile);
                                $('#eauthreqstatus').val(data.authreqstatus);
                                $('#etxninitstatus').val(data.txninitstatus);
//                                $('#edynamicreturnsuccessurl').val(data.dynamicreturnsuccessurl);
//                                $('#edynamicreturnerrorurl').val(data.dynamicreturnerrorurl);
                                if(data.securitymechanism =='DigitallySign'){
                                    $('[value="SymmetricKey"][id="esecuritymechanismSymmetricKey"]').prop('checked', false);
                                    $('[value="DigitallySign"][id="esecuritymechanismDigitallySign"]').prop('checked', true);
                                }else if(data.securitymechanism =='SymmetricKey'){
                                    $('[value="DigitallySign"][id="esecuritymechanismDigitallySign"]').prop('checked', false);
                                    $('[value="SymmetricKey"][id="esecuritymechanismSymmetricKey"]').prop('checked', true);
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
            
            function  backDivEdit(){
                $('#etabone').show();
                $('#etabtwo').hide();
            }
            
            function nextDivEdit(){
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
            <s:form id="headmerchantsedit" method="post" action="HeadMerchants"  theme="simple" cssClass="form" enctype="multipart/form-data"> 
                <div id="etabone" >
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Merchant Customer ID <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="merchantcustomerid" id="emerchantcustomerid" 
                                             maxLength="15" 
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                             readonly="true"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Merchant ID <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="merchantid" id="emerchantid" 
                                             maxLength="15" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                             readonly="true"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Merchant Name <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="merchantname" id="emerchantname" 
                                             maxLength="255" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Address1 <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="address1" id="eaddress1" maxLength="255" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Address2</label>
                                <s:textfield cssClass="form-control" name="address2" id="eaddress2" maxLength="255" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >City <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="city" id="ecity" 
                                             maxLength="64" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Country <span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="ecountry" list="countryList"
                                          name="country" headerKey=""
                                          headerValue="--Select Country--" listKey="countrycode"
                                          listValue="description"  />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >State Code</label>
                                <s:textfield cssClass="form-control" name="statecode" id="estatecode" 
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
                                <s:textfield cssClass="form-control" name="postalcode" id="epostalcode" 
                                             maxLength="64" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Province</label>
                                <s:textfield cssClass="form-control" name="province" id="eprovince" 
                                             maxLength="64" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Mobile <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="mobile" id="emobile" 
                                             maxLength="16" 
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Tel No <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="tel" id="etel" 
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
                                <s:textfield cssClass="form-control" name="fax" id="efax" 
                                             maxLength="16" 
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >E-Mail <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="email" id="eemail" maxLength="255" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Primary URL <span style="color: red">*</span></label>
                                <s:textarea cssClass="form-control" name="primaryurl" id="eprimaryurl" cols="10" maxLength="255" rows="1" wrap="off" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Secondary URL <span style="color: red">*</span></label>
                                <s:textarea cssClass="form-control" name="secondaryurl" id="esecondaryurl" maxLength="255" cols="10" rows="1" wrap="off" />
                            </div>
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
<!--                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Dynamic Return Success URL <span style="color: red">*</span></label>
                                <%--<s:textfield cssClass="form-control"  name="dynamicreturnsuccessurl" id="edynamicreturnsuccessurl" maxLength="64" />--%>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Dynamic Return Error URL <span style="color: red">*</span></label>
                                <%--<s:textfield cssClass="form-control" name="dynamicreturnerrorurl" id="edynamicreturnerrorurl"  maxLength="64" />--%>
                            </div>
                        </div>-->
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Date of Registry <span style="color: red">*</span></label>
                                <sj:datepicker cssClass="form-control" id="edateofregistry" name="dateofregistry" readonly="true" maxDate="+1d" changeYear="true"
                                               buttonImageOnly="true" displayFormat="dd/mm/yy" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Date of Expiry <span style="color: red">*</span></label>
                                <sj:datepicker cssClass="form-control" id="edateofexpiry" name="dateofexpiry" readonly="true"  changeYear="true"
                                               minDate="d" buttonImageOnly="true" displayFormat="dd/mm/yy" />
                            </div>
                        </div>
                         <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Status <span style="color: red">*</span></label>
                                <s:select id="estatus" list="statusList"
                                          name="status" headerKey="" cssClass="form-control"
                                          headerValue="--Select Status--" listKey="statuscode"
                                          listValue="description"  />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Contact Person <span style="color: red">*</span></label>
                                <s:textfield name="contactperson" id="econtactperson" 
                                             maxLength="255" cssClass="form-control"
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>    
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Remarks</label>
                                <s:textfield name="remarks" id="eremarks" cssClass="form-control"
                                             maxLength="255" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
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
                                <label class="control-label" >Transaction Initiated Status <span style="color: red">*</span></label>
                                <s:select cssClass="form-control" id="etxninitstatus" list="txninitstatusList"
                                          name="txninitstatus" headerKey=""
                                          headerValue="--Select Status--" listKey="statuscode"
                                          listValue="description"  />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label" >Security Mechanism <span style="color: red">*</span></label><br>
                                <s:radio label="securitymechanism" id="esecuritymechanism" name="securitymechanism" list="{'DigitallySign','SymmetricKey'}" cssClass="radioClass"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                        </div>
                    </div> 
                    <s:url var="updateurl" action="updateHeadMerchants"/>
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
