<%-- 
    Document   : ruletypeinsert
    Created on : Jul 11, 2018, 2:38:07 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<link rel="stylesheet" href="resouces/css/common/common_popup.css">-->

        <title>Insert System User</title>
        <script type="text/javascript">
            $(document).ready(function() {
//                $("#iexpirydate").val('${PASSWORDEXPIRYPERIOD}');
            });

            $.subscribe('resetAddButton', function(event, data) {
                $('#iruletypeCode').val("");
                $('#irulescope').val("");
                $('#idescription').val("");
                $('#istatus').val("");

                $("#divmsginsert").empty();
            });
            function isNumberinsert(evt) {
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

            $(document).ready(function() {
//                $.each($('.tooltip'), function (index, element) {
//                    $(this).remove();
//                });
//                $('[data-toggle="tooltip"]').tooltip({
//                    'placement': 'right'
//
//                });
            });

        </script>
        <style>
            .tooltip {

                background-color: black;
                color: #fff;
                border-radius: 6px;
                padding: 5px 0;
                white-space: pre-line;
                height: auto;
            }
            .tooltip-inner {
                min-width: 375%;
                max-width: 100%; 
                text-align: left;
            }

        </style>

    </head>
    <body  >
        <div class="content-message">
            <s:div id="divmsginsert">
                <s:actionerror theme="jquery"/>
                <s:actionmessage theme="jquery"/>
            </s:div>
        </div>
        <div class="content-form">
            <s:form id="ruletypeinsert" method="post" action="RuleType" theme="simple" cssClass="form" enctype="multipart/form-data">   
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">Rule Type Code<span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="ruletypeCode" id="iruletypeCode" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Rule Scope<span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="irulescope" list="%{rulescopeList}"  name="rulescope" headerKey="" headerValue="--Select Rule Scope Code--" listKey="rulescopecode" listValue="description" />
                            
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">Description<span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" id="idescription"   name="description" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"  />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">Status <span style="color: red">*</span></label>
                            <s:select  cssClass="form-control" id="istatus" list="%{statusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 
                <s:url var="addurl" action="addRuleType"/>
                <div class="row">
                    <div class="col-md-12 text-right" >
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
