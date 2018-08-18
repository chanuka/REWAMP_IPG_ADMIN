<%-- 
    Document   : headmerchantinsert
    Created on : Aug 13, 2018, 1:16:15 PM
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


        <script type="text/javascript">
            $(document).ready(function() {
//                $("#iexpirydate").val('${PASSWORDEXPIRYPERIOD}');
            });

            function cancelDataInsert() {
                $('#imerchantcustomerid').val("");
                $('#imerchantname').val("");
                $('#iemail').val("");
                $('#istatus').val("");
                $('#iremarks').val("");
                $('#itxninitstatus').val("");

                $("#divmsginsert").empty();
            }

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
            <s:form id="headmerchantsinsert" method="post" action="HeadMerchants" theme="simple" cssClass="form" enctype="multipart/form-data">   
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Merchant Customer ID <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="merchantcustomerid" id="imerchantcustomerid" 
                                         maxLength="15" 
                                         onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" 
                                         onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Merchant Customer Name <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="merchantname" id="imerchantname" 
                                         maxLength="255" 
                                         onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                         onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >E-Mail <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="email" id="iemail" maxLength="255" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Status <span style="color: red">*</span></label>
                                <s:select id="istatus" list="statusList"
                                          name="status" headerKey="" cssClass="form-control"
                                          headerValue="--Select Status--" listKey="statuscode"
                                          listValue="description"  />
                            </div>
                        </div>
                    </div>
                    <div  class="row">
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Remarks</label>
                            <s:textfield name="remarks" id="iremarks" cssClass="form-control"
                                         maxLength="255" 
                                         onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                         onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Transaction Initiated Status <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="itxninitstatus" list="txninitstatusList"
                                      name="txninitstatus" headerKey=""
                                      headerValue="--Select Status--" listKey="statuscode"
                                      listValue="description"  />
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 
                <s:url var="addurl" action="addHeadMerchants"/>
                <div class="row">
                    <div class="col-md-12 text-right" >
                        <div class="form-group">
                            <sj:submit 
                                button="true" 
                                value="Reset" 
                                onClick="cancelDataInsert()"
                                cssClass="btn btn-sm btn-reset"
                                /> 
                            <sj:submit 
                                cssClass="btn btn-sm btn-functions" 
                                button="true" 
                                href="%{addurl}" 
                                value="Add" 
                                targets="divmsginsert" 
                                id="addButton" />
                        </div>
                    </div>
                </div>
            </s:form>        
        </div>
    </body>

</html>

