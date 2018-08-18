<%-- 
    Document   : pageedit
    Created on : Jul 23, 2018, 10:45:54 AM
    Author     : harini_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>

            function editPage(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findPage.ipg',
                    data: {
                        pageCode: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {
                            $('#epageCode').val(data.pageCode);
                            $('#epageCode').attr('readOnly', true);
                            $('#edescription').val(data.description);
                            $('#eurl').val(data.url);
                            $('#epageRoot').val(data.pageRoot);
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
                var pageCode = $('#epageCode').val();
                editPage(pageCode);
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
            <s:form id="pageedit" method="post" action="Page"  theme="simple" cssClass="form"> 
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Page Code <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="pageCode" id="epageCode" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))" readonly="true"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Description <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="description" id="edescription" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/></div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >URL <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="url" id="eurl" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^\/a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^\/a-zA-Z0-9 ]/g,''))"/></div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Root <span style="color: red">*</span></label>
                            <s:textfield  cssClass="form-control" name="pageRoot" id="epageRoot" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Sort Key<span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="sortKey" id="esortKey" maxLength="3" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Status<span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="estatus" list="%{statusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                        </div>
                    </div>
                </div>                
                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 
                <s:url var="updateurl" action="updatePage"/>
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