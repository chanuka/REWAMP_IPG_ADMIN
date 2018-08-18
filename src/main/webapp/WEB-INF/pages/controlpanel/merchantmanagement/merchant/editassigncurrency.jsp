<%-- 
    Document   : assigncurrency
    Created on : Jun 26, 2018, 10:05:48 AM
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
            function loadMerchantCurrency() {
                var merID = $('#merchantId_ac').val();

                $.ajax({
                    url: '${pageContext.request.contextPath}/detailsCMerchant.ipg',
                    data: {
                        merchantId_ac: merID
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgC').empty();

                        var msg = data.message;

                        if (msg) {
                            alert(data.message)
                        } else {

                            $('#newBoxc').empty();
                            $('#currentBoxc').empty();

                            $.each(data.newList, function (key, value) {
                                $('#newBoxc').append(
                                        $('<option></option>').val(key).html(
                                        value));
                            });
                            $.each(data.currentList, function (key, value) {
                                $('#currentBoxc').append(
                                        $('<option></option>').val(key).html(
                                        value));
                            });

                        }
                    },
                    error: function (data) {
                        alert("Error occurd while loadin        g.");
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }



            function toleft() {
                $("#newBoxc option:selected").each(function () {

                    $("#currentBoxc").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function toright() {
                $("#currentBoxc option:selected").each(function () {

                    $("#newBoxc").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function toleftall() {
                $("#newBoxc option").each(function () {

                    $("#currentBoxc").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function torightall() {
                $("#currentBoxc option").each(function () {

                    $("#newBoxc").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function clickAssign() {
                $("#currentBoxc option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#newBoxc option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#assignbutc").click();
            }

            function resetFieldData() {

//                $('#merchantId_ac').val("");
//                $('#newBox').empty();
//                $('#currentBox').empty();

            }
            function resetAllData() {
                $('#divmsgC').text("");
                loadMerchantCurrency();
            }
        </script>
    </head>

    <body >
        <div class="content-message">
            <s:div id="divmsgC">
                <s:actionmessage theme="jquery"/>
                <s:actionerror theme="jquery"/>
            </s:div>
        </div>

        <s:set id="vassignc" var="vassign">
            <s:property value="vassignc" default="true" />
        </s:set>

        <!-- Form -->
        <div class="content-form">
            <s:form action="assignMerchantCurrencies" theme="simple" method="post" id="assignformc" name="assignform" target="divmsgC">

                <div class="row">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label">Merchant ID <span style="color: red">*</span></label>
                            <s:textfield name="merchantId_ac" id="merchantId_ac"  maxLength="16" readonly="true" cssClass="form-control"  onkeypress="return alpha(event)"/>
                        </div>
                    </div>
                </div>
                
                <div class="move-change-side">
                    <table>
                        <tr>
                            <td style="float: left;">
                                <h6><b>Available Currency</b></h6>
                                <s:select cssClass="move-selectClass" multiple="true" name="currentBox" id="currentBoxc" list="currentList" ondblclick="toright()" />
                            </td>
                            <td class="move-buttonClassWithText">
                                <sj:a id="rightc" onClick="toright()" button="true" > <i class="fa fa-chevron-right" aria-hidden="true"></i> </sj:a> </br> 
                                <sj:a id="rightallc" onClick="torightall()" button="true" > <i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i> </sj:a></br> 
                                <sj:a id="leftc" onClick="toleft()" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                <sj:a id="leftallc" onClick="toleftall()" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                            </td>
                            <td style="float: right;">
                                 <h6><b>Assigned Currency</b></h6>   
                                <s:select cssClass="move-selectClass" multiple="true"  name="newBox" id="newBoxc" list="newList" ondblclick="toleft()" />
                            </td> 
                        </tr>

                        <tr style="display: none; visibility: hidden;">
                            <td><s:url var="assignurl" action="assignCurrencyMerchant" />
                                <sj:submit button="true" href="%{assignurl}" id="assignbutc"
                                           targets="divmsgC" /></td>
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
                            <sj:submit cssClass="btn btn-sm btn-functions" button="true" id="assignbutac" value="Assign" onclick="clickAssign()" disabled="#vassignc" />
                            <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                        </div>
                    </div>
                </div>  
            </s:form>
        </div>
    </body>
</html>
