<%-- 
    Document   : systemuserinsert
    Created on : Jun 28, 2018, 3:48:19 PM
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
                $('#iusername').val("");
                $('#ipassword').val("");
                $('#iconfirmpassword').val("");
                $('#iuserrole').val("");
//                $('#idualAuth').val("");
                $('#istatus').val("");
                $('#iempid').val("");
                $('#iexpirydate').val("");
                $('#ifullname').val("");
                $('#iaddress1').val("");
                $('#iaddress2').val("");
                $('#icity').val("");
                $('#imobile').val("");
                $('#itelno').val("");
                $('#ifax').val("");
                $('#imail').val("");
                $("#imername").val("");
                $("#imername").prop('disabled', true);

                $("#divmsginsert").empty();
            });
            function checkuser(key) {
                key = key.value;
                if (key) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/checkSystemUser.ipg',
                        data: {mainuserrole: key},
                        dataType: "json",
                        type: "POST",
                        success: function (data) {
                            $('#divmsginsert').empty();
                            var msg = data.message;
                            if (msg) {
//                                alert(data.message);
                                if (data.message === "true") {
                                    $("#imername").prop('disabled', false);
                                } else {
                                    $("#imername").val("");
                                    $("#imername").prop('disabled', true);
                                }
                            } else {
                                $("#errordialog").html("Error occurred while processing.").dialog('open');

                            }
                        },
                        error: function (data) {
                            $("#editerrordialog").html("Error occurred while processing.").dialog('open');
                            window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                        }
                    });
                }
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
                $.each($('.tooltip'), function (index, element) {
                    $(this).remove();
                });
                $('[data-toggle="tooltip"]').tooltip({
                    'placement': 'right'

                });
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

        <s:div id="divmsginsert">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>

        <s:form id="systemuserinsert" method="post" action="SystemUser" theme="simple" cssClass="form">   

            <div class="row "> 
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >User Name <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" name="username" id="iusername" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" autocomplete="off"  />
                    </div>
                </div>
                <div class="col-md-3 hideme" >
                    <div class="form-group">
                        <label class="control-label" >Password <span style="color: red">*</span></label>
                        <s:password cssClass="form-control" name="password" id="ipassword" maxLength="256" data-toggle="tooltip" data-html="true" title="%{pwtooltip}"/>
                    </div>
                </div>
                <div class="col-md-3 hideme" >
                    <div class="form-group">
                        <label class="control-label" >Confirm Password <span style="color: red">*</span></label>
                        <s:password cssClass="form-control" name="confirmpassword" id="iconfirmpassword" maxLength="256" autocomplete="off" />
                    </div>
                </div>
                <div class="col-md-3" >
                    <div class="form-group">
                        <label class="control-label" >User Role <span style="color: red">*</span></label>
                        <s:select cssClass="form-control" id="iuserrole" list="%{userroleList}"  name="userrole" headerKey="" headerValue="--Select User Role--" listKey="userrolecode" listValue="description" onchange="checkuser(this);" />
                    </div>
                </div>
            </div>

            <div class="row ">
<!--                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Dual Authentication User Role <span style="color: red">*</span></label>
                        <s:select cssClass="form-control" id="idualAuth" list="%{userroleList}"  name="dualAuth" headerKey="" headerValue="--Select User Role--" listKey="userrolecode" listValue="description" />
                    </div>
                </div>-->
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Status <span style="color: red">*</span></label>
                        <s:select cssClass="form-control" id="istatus" list="%{statusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Merchant ID <span style="color: red">*</span></label>
                        <s:select cssClass="form-control" id="imername" list="%{merchantList}"  name="mername" headerKey="" headerValue="--Select Merchant--" listKey="merchantid" listValue="merchantid" disabled="true" />
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Employee ID  <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" name="empid" id="iempid" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                    </div>
                </div>
            </div> 

            <div class="row ">
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Expiry Date  <span style="color: red">*</span></label>
                        <sj:datepicker cssClass="form-control" id="iexpirydate" name="expirydate" readonly="true" changeYear="true" buttonImageOnly="true" displayFormat="dd/mm/yy" minDate="d+1" />
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Full Name  <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" name="fullname" id="ifullname" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z ]/g,''))"/>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Address Line 1 <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" name="address1" id="iaddress1" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9,//.-' ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9,//.-' ]/g,''))"/>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Address Line 2 <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" name="address2" id="iaddress2" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9,//.-' ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9,//.-' ]/g,''))"/>
                    </div>
                </div>
            </div>

            <div class="row"> 
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >City <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" name="city" id="icity" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9,//.-' ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9,//.-' ]/g,''))"/>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Mobile No <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" name="mobile" id="imobile" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Telephone No <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" name="telno" id="itelno" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"  onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Fax <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" name="fax" id="ifax" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Email <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" name="mail" id="imail" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9_@. ]/g,''))"  onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9_@. ]/g,''))"/>
                    </div>
                </div>
            </div>  
            <div class="row ">
                <div class="horizontal_line_popup"></div>
            </div>
            <div class="row form-inline">
                <div class="col-sm-6">
                    <div class="form-group">
                        <span class="mandatoryfield">Mandatory fields are marked with <span>*</span></span>
                    </div>
                </div>
                <div class="col-sm-6 text-right">
                    <div class="form-group" >
                        <sj:submit 
                            button="true" 
                            value="Reset" 
                            name="reset" 
                            cssClass="btn btn-sm btn-reset"
                            onClickTopics="resetAddButton"
                            />                        
                    
                        <s:url action="addSystemUser" var="inserturl"/>
                        <sj:submit
                            button="true"
                            value="Add"
                            href="%{inserturl}"
                            onClickTopics=""
                            targets="divmsginsert"
                            id="addbtn"
                            cssClass="btn btn-sm btn-functions" 
                            />                        
                    </div>
                </div>
            </div>  
        </s:form>        

    </body>
</html>
