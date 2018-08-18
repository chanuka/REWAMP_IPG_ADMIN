<%-- 
    Document   : merchantcredentialedit
    Created on : Jul 19, 2018, 2:52:36 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            
             function editMerchantCredential(merID, cardAss) {
                if (merID === "null") {
                    merID = "";
                }
                if (cardAss === "null") {
                    cardAss = "";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/findMerchantCredential.ipg',
                    data: {merchantID: merID, cardAssociation: cardAss},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {

                            $('#emerchantID').val(data.merchantID);
                            $('#emerchantID2').val(data.merchantID);
                            $('#emerchantID').attr('disabled', true);
                            $('#ecardAssociation').val(data.cardAssociation);
                            $('#ecardAssociation2').val(data.cardAssociation);
                            $('#ecardAssociation').attr('disabled', true);
                            $('#euserName').val(data.userName);
                            $('#epassword').val("");
                            $('#erePassword').val("");
                        }
                    },
                    error: function (data) {
                        $("#editerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            function cancelUpateData() {
                var merchantID = $('#emerchantID2').val();
                var cardAssociation = $('#ecardAssociation2').val();
                editMerchantCredential(merchantID,cardAssociation);
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
            <s:form id="merchantcredentialedit" method="post" action="MerchantCredential"  theme="simple" cssClass="form" enctype="multipart/form-data"> 
            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Merchant ID <span style="color: red">*</span></label>
                        <s:select cssClass="form-control" id="emerchantID" list="%{merchantList}"  name="merchantID" headerKey="" headerValue="--Select Merchant--"  listKey="merchantid" listValue="merchantid" disabled="true"/>
                        <s:hidden name="merchantID2" id="emerchantID2" />
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Card Association  <span style="color: red">*</span></label>
                        <s:select cssClass="form-control" id="ecardAssociation" list="%{cardAssociationList}"  name="cardAssociation" headerKey="" headerValue="--Select Card Association--" listKey="cardassociationcode" listValue="description" disabled="true" />
                        <s:hidden name="cardAssociation2" id="ecardAssociation2" />
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >User Name  <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" id="euserName" name="userName" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Password <span style="color: red">*</span></label>
                        <s:password cssClass="form-control" id="epassword" name="password" maxLength="255"  />
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Re-type Password <span style="color: red">*</span></label>
                        <s:password cssClass="form-control" id="erePassword" name="rePassword" maxLength="255"  />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                </div>
            </div> 
            <s:url var="updateurl" action="updateMerchantCredential"/>
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

