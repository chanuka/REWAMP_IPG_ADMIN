<%-- 
    Document   : commonconfigedit
    Created on : Jul 13, 2018, 9:37:20 AM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>

            function editCommonconfig(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findCommonConfig.ipg',
                    data: {code: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {
                            $('#ecode').val(data.code);
                            $('#edescription').val(data.description);
                            $('#evalue').val(data.value);
                        }
                    },
                    error: function (data) {
                        $("#editerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            function cancelUpateData() {
                var code = $('#ecode').val();
                editCommonconfig(code);
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
            <s:form id="commonconfigedit" method="post" action="CommonConfig"  theme="simple" cssClass="form" enctype="multipart/form-data">
                <div class="row">
                    <div class="col-md-4">
                        <div class="form-group">
                            <label class="control-label" >Code </label>
                            <s:textfield cssClass="form-control" name="code" id="ecode" maxLength="15" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))" readonly="true"/>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label class="control-label" >Description</label>
                            <s:textfield  cssClass="form-control" id="edescription"   name="description" maxLength="20" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                        </div>
                    </div>
                </div> 
                <div class="row">   
                    <div class="col-md-12">
                        <div class="form-group">
                            <label class="control-label" >Value</label>
                            <s:textarea cssClass="form-control" id="evalue" name="value"  maxLength="128" cols="10" rows="3" wrap="off"  onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>                            
                        </div>
                    </div>                            
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 
                <s:url var="updateurl" action="updateCommonConfig"/>
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
