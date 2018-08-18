<%-- 
    Document   : authconfigurationedit
    Created on : Jul 9, 2018, 2:08:14 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            
             function editAuthConfig(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findAuthConfig.ipg',
                    data: {
                        pkey: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {
                            $('#pkey').val(data.pkey);
                            $('#ecardassociation').val(data.cardassociation);
                            $('#ecardassociation').attr('disabled', true);
                            $('#etxstatus').val(data.txstatus);
                            $('#etxstatus').attr('disabled', true);
                            $('#eecivalue').val(data.ecivalue);
                            $('#eecivalue').attr('disabled', true);
                            $('#estatus').val(data.status);
                        }
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            function cancelUpateData() {
                var pkey = $('#pkey').val();
                editAuthConfig(pkey);
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
            <s:form id="cardassociationedit" method="post" action="AuthConfig"  theme="simple" cssClass="form" enctype="multipart/form-data"> 
            <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Card Association <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="ecardassociation" list="%{cardassociationList}"  name="cardassociation" headerKey="" headerValue="--Select Card Association--" listKey="cardassociationcode" listValue="description" disabled="true"/>
                             <s:hidden name="pkey" id="pkey" ></s:hidden>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Transaction Status <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="etxstatus" list="%{txstatusMap}"  name="txstatus" headerKey="" headerValue="--Select Authentication Status--" listKey="key" listValue="value" disabled="true"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >ECI Value <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="eecivalue" list="%{ecivalueList}"  name="ecivalue" headerKey="" headerValue="--Select ECI Value--" listKey="value" listValue="value" disabled="true"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Authentication Status <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="estatus" list="%{authstatusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 
                <s:url var="updateurl" action="updateAuthConfig"/>
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