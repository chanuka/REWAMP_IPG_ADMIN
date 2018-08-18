<%-- 
    Document   : headmerchantedit
    Created on : Aug 13, 2018, 1:14:55 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>


            function cancelDataEdit() {
                var merchantcustomerid = $('#emerchantcustomerid').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/findHeadMerchants.ipg',
                    data: {
                        merchantcustomerid: merchantcustomerid
                    },
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {
                            $('#emerchantcustomerid').val(data.merchantcustomerid);
                            $('#emerchantcustomerid').attr('readOnly', true);
                            $('#emerchantname').val(data.merchantname);
                            $('#eemail').val(data.email);
                            $('#estatus').val(data.status);
                            $('#eremarks').val(data.remarks);
                            $('#etxninitstatus').val(data.txninitstatus);
                        }
                    },
                    error: function(data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
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
            <s:form id="headmerchantsedit" method="post" action="HeadMerchants"  theme="simple" cssClass="form" enctype="multipart/form-data"> 

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
                            <label class="control-label" >Merchant Customer Name <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="merchantname" id="emerchantname" 
                                         maxLength="255" 
                                         onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                         onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
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
                            <label class="control-label" >Status <span style="color: red">*</span></label>
                            <s:select id="estatus" list="statusList"
                                      name="status" headerKey="" cssClass="form-control"
                                      headerValue="--Select Status--" listKey="statuscode"
                                      listValue="description"  />
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
                            <label class="control-label" >Transaction Initiated Status <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="etxninitstatus" list="txninitstatusList"
                                      name="txninitstatus" headerKey=""
                                      headerValue="--Select Status--" listKey="statuscode"
                                      listValue="description"  />
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
                                onClick="cancelDataEdit()"
                                cssClass="btn btn-sm btn-reset"
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
            </s:form>        
        </div>
    </body>
</html>
