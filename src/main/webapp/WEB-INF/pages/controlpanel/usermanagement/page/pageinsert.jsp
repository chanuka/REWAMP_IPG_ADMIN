<%-- 
    Document   : pageinsert
    Created on : Jul 23, 2018, 10:45:36 AM
    Author     : harini_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Insert Task</title>
        <script type="text/javascript">
            function resetFieldData() {
                $('#ipageCode').val("");
                $('#idescription').val("");
                $('#iurl').val("");
                $('#ipageRoot').val("");
                $('#isortKey').val("");
                $('#istatus').val("");

                jQuery("#gridtable").trigger("reloadGrid");

            }
            $.subscribe('resetAddButton', function (event, data) {
                $('#ipageCode').val("");
                $('#idescription').val("");
                $('#iurl').val("");
                $('#ipageRoot').val("");
                $('#isortKey').val("");
                $('#istatus').val("");
                $('#divmsginsert').empty();
            });

        </script>

    </head>
    <body>
        <div class="content-message">
            <s:div id="divmsginsert">
                <s:actionerror theme="jquery"/>
                <s:actionmessage theme="jquery"/>
            </s:div>
        </div>
        <div class="content-form">
            <s:form id="pageinsert" method="post" action="Page" theme="simple" cssClass="form">   
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Page Code <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="pageCode" id="ipageCode" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Description <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="description" id="idescription" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >URL <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="url" id="iurl" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^\/a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^\/a-zA-Z0-9 ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Root <span style="color: red">*</span></label>
                            <s:textfield  cssClass="form-control" name="pageRoot" id="ipageRoot" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Sort Key<span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="sortKey" id="isortKey" maxLength="3" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Status<span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="istatus" list="%{statusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                        </div>
                    </div>
                </div>               
                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 
                <s:url var="addurl" action="addPage"/>        
                <div class="row">
                    <div class="col-md-12 text-right">
                        <div class="form-group">
                            <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClickTopics="resetAddButton" />
                            <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{addurl}" value="Add" targets="divmsginsert" id="addButton" />
                        </div>
                    </div>
                </div>
            </s:form>        
        </div>
    </body>

</html>