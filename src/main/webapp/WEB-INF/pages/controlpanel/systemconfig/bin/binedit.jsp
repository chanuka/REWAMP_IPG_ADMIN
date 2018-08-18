<%-- 
    Document   : binedit
    Created on : Jul 9, 2018, 5:28:24 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            
             function editBin(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findBin.ipg',
                    data: {
                        bincode: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {
                            $('#ebincode').val(data.bincode);
                            $('#ebincode').attr('readOnly', true);
                            $('#edescription').val(data.description);
                            $('#eonusstatus').val(data.onusstatus);
                            $('#ecardassociation').val(data.cardassociation);
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
                var bincode = $('#ebincode').val();
                editBin(bincode);
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
            <s:form id="binedit" method="post" action="Bin"  theme="simple" cssClass="form" enctype="multipart/form-data"> 
            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label">BIN Number <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" name="bincode" id="ebincode" maxLength="6" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" readonly="true"/>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label">Description <span style="color: red">*</span></label>
                        <s:textfield  cssClass="form-control" name="description" id="edescription" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label">On Us Status <span style="color: red">*</span></label>
                        <s:select  cssClass="form-control" id="eonusstatus" list="%{onusstatusList}"  name="onusstatus" headerKey="" headerValue="--Select On Us Status--" listKey="statuscode" listValue="description" />
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label">Card Association <span style="color: red">*</span></label>
                        <s:select  cssClass="form-control" id="ecardassociation" list="%{cardassociationList}"  name="cardassociation" headerKey="" headerValue="--Select Card Association--" listKey="cardassociationcode" listValue="description" />
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
            <s:url var="updateurl" action="updateBin"/>
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
