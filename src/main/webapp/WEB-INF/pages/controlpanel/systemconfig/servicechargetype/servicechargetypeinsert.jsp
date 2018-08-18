<%-- 
    Document   : servicechargetypeinsert
    Created on : Jul 10, 2018, 4:11:22 PM
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
                $('#iserviceChargeTypeCode').val("");
                $('#idescription').val("");
                $('#isortKey').val("");

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
        <s:set id="vadd" var="vadd">
            <s:property value="vadd" default="true" />
        </s:set>
        <div class="content-form">
            <s:form id="servicechargetypeinsert" method="post" action="ServiceChargeType" theme="simple" cssClass="form" enctype="multipart/form-data">   
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Service Charge Type Code <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="serviceChargeTypeCode"
                                         id="iserviceChargeTypeCode" maxLength="16"
                                         onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                         onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Description <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="description" id="idescription"
                                         maxLength="64"
                                         onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                         onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Sort Key <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="sortKey" id="isortKey" maxLength="3"
                                         onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"
                                         onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                        </div>
                    </div>
                </div>  
                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 
                <s:url var="addurl" action="addServiceChargeType"/>
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
