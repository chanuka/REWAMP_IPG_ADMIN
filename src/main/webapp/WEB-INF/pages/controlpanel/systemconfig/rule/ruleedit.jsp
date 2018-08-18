<%-- 
    Document   : ruleedit
    Created on : Jul 11, 2018, 4:52:21 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            
             function editRule(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findRule.ipg',
                    data: {ruleId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {
                            $('#eoperator').empty();
                            $('#eruleType').empty();
                            $('#eoperator').append($('<option></option>').val("").html("--Select Opporator--"));
                            $('#eruleType').append($('<option></option>').val("").html( "--Select Rule Type--"));
                            
                            
                            $.each(data.operationList, function (index, item) {
                                $('#eoperator').append(
                                        $('<option></option>').val(item.key).html(
                                        item.value));
                            });
                            $.each(data.ruletypeList, function (index, item) {
                                $('#eruleType').append(
                                        $('<option></option>').val(item.key).html(
                                        item.value));
                            });
                            
                            
                            $('#eruleId').val(data.ruleId);
                            $('#eruleScope').val(data.ruleScope);
                            $('#eruleScope').attr("disabled",true);
                            $('#eruleScope2').val(data.ruleScope);
                            $('#eoperator').val(data.operator);
                            $('#eruleType').val(data.ruleType);
                            $('#estartValue').val(data.startValue);
                            $('#eendValue').val(data.endValue);
                            $('#epriority').val(data.priority);
                            $('#esecurityLevel').val(data.securityLevel);
                            $('#edescription').val(data.description);
                            $('#etriggersequence').val(data.triggersequence);
                            $('#estatus').val(data.status);
                        }
                    },
                    error: function (data) {
                        $("#editerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            
            function setListedit(){
                var ruleScope=$('#eruleScope').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/findListRule.ipg',
                    data: {ruleScope: ruleScope},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#eoperator').empty();
                            $('#eruleType').empty();
                        } else {
                            $('#eoperator').empty();
                            $('#eruleType').empty();
                            $('#eoperator').append($('<option></option>').val("").html("--Select Opporator--"));
                            $('#eruleType').append($('<option></option>').val("").html( "--Select Rule Type--"));
                            
                            
                            $.each(data.operationList, function (index, item) {
                                $('#eoperator').append(
                                        $('<option></option>').val(item.key).html(
                                        item.value));
                            });
                            $.each(data.ruletypeList, function (index, item) {
                                $('#eruleType').append(
                                        $('<option></option>').val(item.key).html(
                                        item.value));
                            });
                        }
                    },
                    error: function (data) {
                        $("#editerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            
            function cancelUpateData() {
                var ruleId = $('#eruleId').val();
                editRule(ruleId);
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
            <s:form id="ruleedit" method="post" action="Rule"  theme="simple" cssClass="form" enctype="multipart/form-data"> 
                <s:hidden  name="ruleId" id="eruleId" />
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Rule Scope Code <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="eruleScope" list="%{rulescopeList}"  name="ruleScope" headerKey="" headerValue="--Select Rule Scope Code--" listKey="rulescopecode" listValue="description" onchange="setListedit()" disabled="true"/>
                            <s:hidden  name="ruleScope2" id="eruleScope2" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Operator <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="eoperator" name="operator" list="%{operationList}"  headerKey="" headerValue="--Select Opporator--"  listKey="key" listValue="value"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Rule Type <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="eruleType" list="%{ruletypeList}"  name="ruleType" headerKey="" headerValue="--Select Rule Type--" listKey="key" listValue="value" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Start Value <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="startValue" id="estartValue" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9:/?._ ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9:/?._ ]/g,''))" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >End Value <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" id="eendValue"   name="endValue" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9:/?._  ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9:/?._  ]/g,''))"  />
                        </div>
                    </div>
<!--                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Priority <span style="color: red">*</span></label>
                            <%--<s:textfield cssClass="form-control" id="epriority" name="priority" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />--%>
                        </div>
                    </div>-->
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Security Level <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="esecurityLevel" list="%{securitylevelList}"  name="securityLevel" headerKey="" headerValue="--Select Security Level--" listKey="securitylevel" listValue="description" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Description <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" id="edescription" name="description" maxLength="128" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Trigger Sequence <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="etriggersequence"   name="triggersequence" list="%{triggersequenceList}" headerKey="" headerValue="--Select Trigger Sequence--" listKey="triggersequenceid" listValue="description" />
                        </div>
                    </div>
                </div>  
                <div class="row">
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
                <s:url var="updateurl" action="updateRule"/>
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
