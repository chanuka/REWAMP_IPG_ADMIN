<%-- 
    Document   : servicechargeedit
    Created on : Jul 11, 2018, 9:37:33 AM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            
             function editServiceCharge(keyval) {
                 $.ajax({
                    url: '${pageContext.request.contextPath}/findServiceCharge.ipg',
                    data: {servicechargecode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {
                            $('#eservicechargecode').val(data.servicechargecode);
                            $('#echargetype').val(data.chargetype);
                            $('#edescription').val(data.description);
                            $('#evalue').val(data.value);
                            $('#erate').val(data.rate);
                            $('#ecurrency').val(data.currency);
                        }
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            function cancelUpateData() {
                var servicechargecode = $('#eservicechargecode').val();
                editServiceCharge(servicechargecode);
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
            <s:form id="servicechargeedit" method="post" action="ServiceCharge"  theme="simple" cssClass="form" enctype="multipart/form-data"> 
                <s:textfield type="hidden"  name="servicechargecode" id="eservicechargecode" />
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">Service Charge Type <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" name="chargetype" id="echargetype" list="%{chargeList}" headerKey="" headerValue="--Select Charge Type--" listKey="servicechargetypecode" listValue="description"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">Description <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="description" id="edescription" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">Value <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="value" id="evalue" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">Rate <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="rate" id="erate" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">Currency <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" name="currency" id="ecurrency" list="%{currencyList}" headerKey="" headerValue="--Select Currency Type--" listKey="currencyisocode" listValue="description"/>
                        </div>
                    </div>
                </div>   
                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 
                <s:url var="updateurl" action="updateServiceCharge"/>
                <div class="row">
                    <div class="col-md-12 text-right">
                        <div class="form-group">
                            <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="cancelUpateData()" />
                            <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{updateurl}" value="Update" targets="divmsgupdate" id="updateButton" />
                        </div>
                    </div>
                </div>
            </s:form>        
        </div>
    </body>
</html>
