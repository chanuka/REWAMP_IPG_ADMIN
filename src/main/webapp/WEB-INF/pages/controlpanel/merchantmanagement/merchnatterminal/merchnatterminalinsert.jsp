<%-- 
    Document   : merchnatterminalinsert
    Created on : Jul 20, 2018, 2:32:52 PM
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
            $(document).ready(function () {
//                $("#iexpirydate").val('${PASSWORDEXPIRYPERIOD}');
            });

            $.subscribe('resetAddButton', function (event, data) {
                $('#itid').val("");
                $('#imerchantId').val("");
                $('#icurrencyCode').empty();
                $('#icurrencyCode').append("<option value=''>--Select--</option>");
                $('#istatus').val("");
                $("#divmsginsert").empty();
            });

            function selectCurrencyListInsert() {

                var mid = $('#imerchantId').val();

                $.ajax({
                    url: '${pageContext.request.contextPath}/GetCurrencyMerchantTerminal.ipg',
                    data: {merchantId: mid},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsginsert').empty();

                        var msg = data.message;
                        if (msg) {
                            $('#divmsginsert').append(
                                    $('<div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;padding-bottom:10px !important">')
                                    .html(data.message).append($('<span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>')));
                        } else {

                            $('#icurrencyCode').empty();
                            $('#icurrencyCode').append(
                                    $('<option></option>').val("").html("--Select--")
                                    );
                            $.each(data.currencyMap, function (key, value) {
                                $('#icurrencyCode').append(
                                        $('<option></option>').val(key).html(value)
                                        );
                            });

                        }
                    },
                    error: function (data) {
                        alert("Error occurd while loading.");
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
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

            $(document).ready(function () {
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
            <s:form id="merchantterminalinsert" method="post" action="MerchantTerminal" theme="simple" cssClass="form" enctype="multipart/form-data">   
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Merchant ID <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" onchange="selectCurrencyListInsert()" id="imerchantId" list="%{merchantList}"  name="merchantId" headerKey="" headerValue="--Select--" listKey="merchantid" listValue="merchantid" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Currency <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="icurrencyCode" list="%{currencyMap}"  name="currencyCode" headerKey="" headerValue="--Select--" listKey="key" listValue="value" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Terminal ID <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="tid" id="itid" maxLength="8" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Status <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="istatus" list="%{statusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 
                <s:url var="addurl" action="AddMerchantTerminal"/>        
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
