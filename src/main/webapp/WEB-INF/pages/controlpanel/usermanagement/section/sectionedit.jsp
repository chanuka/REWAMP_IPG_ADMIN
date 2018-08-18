<%-- 
    Document   : sectionedit
    Created on : Jul 24, 2018, 10:15:43 AM
    Author     : harini_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>

            function editSection(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findSection.ipg',
                    data: {
                        sectioncode: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {
                            $('#esectioncode').val(data.sectioncode);
                            $('#esectioncode').attr('readOnly', true);
                            $('#edescription').val(data.description);
                            $('#esortKey').val(data.sortKey);
                            $('#estatus').val(data.status);
                        }
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            function cancelUpdateData() {
                var sectioncode = $('#esectioncode').val();
                editSection(sectioncode);
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
            <s:form id="sectionedit" method="post" action="Section"  theme="simple" cssClass="form"> 
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label"  >Section Code<span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="sectioncode" id="esectioncode" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z]/g, ''))" readonly="true" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label"  >Description<span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="description" id="edescription" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label"  >Status<span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="estatus" list="%{statusList}" name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label"  >Sort Key<span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="sortKey" id="esortKey" maxLength="3" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g, ''))" />                           
                        </div>
                    </div>
                </div>                            
                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 
                <s:url var="updateurl" action="updateSection"/>
                <div class="row">
                    <div class="col-md-12 text-right">
                        <div class="form-group">
                            <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="cancelUpdateData()" />
                            <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{updateurl}" value="Update" targets="divmsgupdate" id="updateButton" />
                        </div>
                    </div>
                </div>
            </s:form>        
        </div>
    </body>
</html>