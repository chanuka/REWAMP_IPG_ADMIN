<%-- 
    Document   : ruleandservice
    Created on : Jun 26, 2018, 10:05:38 AM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML>

<html>
    <head>
        <script type="text/javascript">
            function loadMerchantRule() {
                var merID = $('#merchantId_rs').val();

                $.ajax({
                    url: '${pageContext.request.contextPath}/detailsRMerchant.ipg',
                    data: {
                        merchantId_rs: merID
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
//                        alert(data.message);

                        $('#divmsgr').empty();

                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {

                            $('#newBoxr').empty();
                            $('#newBoxr').empty();
                            $('#currentBoxr').empty();

                            $.each(data.newList, function (key, value) {
                                $('#newBoxr').append(
                                        $('<option></option>').val(key).html(
                                        value));
                            });
                            $.each(data.currentList, function (key, value) {
                                $('#currentBoxr').append(
                                        $('<option></option>').val(key).html(
                                        value));
                            });
                            $('#servicechargeID').val(data.servicechargeID);

                        }
                    },
                    error: function (data) {
                        alert("Error occurd while loadin        g." + data.message);
//                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }


            function toleft() {
                $("#newBoxr option:selected").each(function () {

                    $("#currentBoxr").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function toright() {
                $("#currentBoxr option:selected").each(function () {

                    $("#newBoxr").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function toleftall() {
                $("#newBoxr option").each(function () {

                    $("#currentBoxr").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function torightall() {
                $("#currentBoxr option").each(function () {

                    $("#newBoxr").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function clickAssign() {
                $("#currentBoxr option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#newBoxr option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#assignbutr").click();
            }

            function resetFieldData() {

//                $('#merchantId_rs').val("");
//                $('#newBox').empty();
//                $('#currentBox').empty();

            }
            function resetAllData() {
                $('#divmsgr').text("");
                loadMerchantRule();
            }
        </script>
    </head>

    <body >
        <div class="content-message">
            <s:div id="divmsgr">
                <s:actionmessage theme="jquery"/>
                <s:actionerror theme="jquery"/>
            </s:div>
        </div>

        <s:set id="vassignr" var="vassignr">
            <s:property value="vassignr" default="true" />
        </s:set>

        <!-- Form -->
        <div class="content-form">
            <s:form action="assignMerchantRules" theme="simple" method="post" id="assignformr" name="assignform" target="divmsgr">

                <div class="row">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label">Merchant ID <span style="color: red">*</span></label>
                            <s:textfield name="merchantId_rs" id="merchantId_rs"  maxLength="16" readonly="true" cssClass="form-control"  onkeypress="return alpha(event)"/>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label class="control-label" >Service Charge <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="servicechargeID" list="%{servicechargeList}"  name="servicechargeID" headerKey="" headerValue="--Select Service Charge--" listKey="servicechargeid" listValue="description"  />
                        </div>
                    </div>
                </div>

                <div class="move-change-side">
                    <table>
                        <tr>
                            <td style="float: left;">
                                <h6><b>Available Merchant Rule</b></h6>
                                <s:select cssClass="move-selectClass" multiple="true" name="currentBox" id="currentBoxr" list="currentList" ondblclick="toright()" />
                            </td>
                            <td class="move-buttonClassWithText">
                                <sj:a id="rightr" onClick="toright()" button="true" > <i class="fa fa-chevron-right" aria-hidden="true"></i> </sj:a> </br> 
                                <sj:a id="rightallr" onClick="torightall()" button="true" > <i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i> </sj:a></br> 
                                <sj:a id="leftr" onClick="toleft()" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                <sj:a id="leftallr" onClick="toleftall()" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                            </td>
                            <td style="float: right;">
                                <h6><b>Assigned Merchant Rule</b></h6>    
                                <s:select cssClass="move-selectClass" multiple="true"  name="newBox" id="newBoxr" list="newList" ondblclick="toleft()" />
                            </td> 
                        </tr>

                        <tr style="display: none; visibility: hidden;">
                            <td><s:url var="assignurl" action="assignRuleMerchant" />
                                <sj:submit button="true" href="%{assignurl}" id="assignbutr"
                                           targets="divmsgr" /></td>
                        </tr>
                    </table>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 


                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <sj:submit cssClass="btn btn-sm btn-functions" button="true" id="assignbutar" onclick="clickAssign()" value="Assign" disabled="#vassignr" />
                            <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                        </div>
                    </div>
                </div>  
            </s:form>
        </div>
    </body>
</html>


