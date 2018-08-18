<%-- 
    Document   : ruletypeedit
    Created on : Jul 11, 2018, 2:38:41 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            
             function editRuleType(ruletypeCode,rulescope) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findRuleType.ipg',
                    data: {ruletypeCode: ruletypeCode,rulescope: rulescope },
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {
                            $('#eruletypeCode').val(data.ruletypeCode);
                            $('#eruletypeCode').attr('readOnly', true);
                            $('#erulescope').val(data.rulescope);
                            $('#erulescope').attr('disabled', true);
                            $('#edescription').val(data.description);
                            $('#estatus').val(data.status);
                            $('#erulescope2').val(data.rulescope);

                        }
                    },
                    error: function(data) {
                        $("#editerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            function cancelUpateData() {
                var ruletypeCode = $('#eruletypeCode').val();
                 var rulescope = $('#erulescope2').val();
                editRuleType(ruletypeCode,rulescope);
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
            <s:form id="ruletypeedit" method="post" action="RuleType"  theme="simple" cssClass="form" enctype="multipart/form-data"> 
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">Rule Type Code<span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="ruletypeCode" id="eruletypeCode" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" readonly="true"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Rule Scope<span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="erulescope" list="%{rulescopeList}"  name="rulescope" headerKey="" headerValue="--Select Rule Scope Code--" listKey="rulescopecode" listValue="description" disabled="true"/>
                            <s:hidden name ="rulescope2" id="erulescope2"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">Description<span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" id="edescription"   name="description" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"  />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">Status <span style="color: red">*</span></label>
                            <s:select  cssClass="form-control" id="estatus" list="%{statusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                        </div>
                    </div>
                </div>  
                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 
                <s:url var="updateurl" action="updateRuleType"/>
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

