<%-- 
    Document   : systemuseredit
    Created on : Jun 29, 2018, 10:33:52 AM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            function checkuseredit(key) {
                key = key.value;
                if (key) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/checkSystemUser.ipg',
                        data: {mainuserrole: key},
                        dataType: "json",
                        type: "POST",
                        success: function(data) {
                            $('#divmsginsert').empty();
                            var msg = data.message;
                            if (msg) {
//                                alert(data.message);
                                if (data.message === "true") {
                                    $("#e_mername").prop('disabled', false);
                                } else {
                                    $("#e_mername").val("");
                                    $("#e_mername").prop('disabled', true);
                                }
                            } else {
                                $("#errordialog").html("Error occurred while processing.").dialog('open');

                            }
                        },
                        error: function(data) {
                            $("#editerrordialog").html("Error occurred while processing.").dialog('open');
                            window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                        }
                    });
                }
            }
            function editSystemUser(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findSystemUser.ipg',
                    data: {username: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            //                                                        alert(data.message)
                            $('#e_username').val("");
                            $('#e_username').attr('readOnly', false);
                            $('#e_userrole').val("");
//                            $('#e_dualAuth').val("");
                            $('#e_status').val("");
                            $('#e_empid').val("");
                            $('#e_expirydate').val("");
                            $('#e_fullname').val("");
                            $('#e_address1').val("");
                            $('#e_address2').val("");
                            $('#e_city').val("");
                            $('#e_mobile').val("");
                            $('#e_telno').val("");
                            $('#e_fax').val("");
                            $('#e_mail').val("");
                            $("#e_mername").prop('disabled', false);
                            $('#e_mername').val("");
                            $('#divmsgupdate').text("");

                        } else {
                            $('#divmsgupdate').text("");
                            $('#e_username').val(data.username);
                            $('#e_username').attr('readOnly', true);
                            $('#e_userrole').val(data.userrole);
//                            $('#e_dualAuth').val(data.dualAuth);
                            $('#e_status').val(data.status);
                            $('#e_empid').val(data.empid);
                            $('#e_expirydate').val(data.expirydate);
                            $('#e_fullname').val(data.fullname);
                            $('#e_address1').val(data.address1);
                            $('#e_address2').val(data.address2);
                            $('#e_city').val(data.city);
                            $('#e_mobile').val(data.mobile);
                            $('#e_telno').val(data.telno);
                            $('#e_fax').val(data.fax);
                            $('#e_mail').val(data.mail);

                            //check selected user is a merchant
                            if (data.mername) {
                                $("#e_mername").prop('disabled', false);
                                $('#e_mername').val(data.mername);
                            } else {
                                $("#e_mername").prop('disabled', true);
                                $('#e_mername').val("");
                            }
                        }
                    },
                    error: function(data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }
            function cancelData() {
                var id = $('#e_username').val();
                editSystemUser(id);
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
            <s:form id="systemuseredit" method="post" action="SystemUser"  theme="simple" cssClass="form"> 
                <%--<s:hidden name="oldvalue" id="oldvalue"/>--%>

                <div class="row "> 
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >User Name <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="username" id="e_username" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" autocomplete="off" readonly="true" />
                        </div>
                    </div>
                    <div class="col-md-3" >
                        <div class="form-group">
                            <label class="control-label" >User Role <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="e_userrole" list="%{userroleList}"  name="userrole" headerKey="" headerValue="--Select User Role--" listKey="userrolecode" listValue="description" onchange="checkuseredit(this);" />
                        </div>
                    </div>
<!--                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Dual Authentication User Role <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="e_dualAuth" list="%{userroleList}"  name="dualAuth" headerKey="" headerValue="--Select User Role--" listKey="userrolecode" listValue="description" />
                        </div>
                    </div>-->
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Status <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="e_status" list="%{statusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                        </div>
                    </div>
                </div>

                <div class="row ">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Merchant ID <span style="color: red">*</span></label>
                            <s:select cssClass="form-control" id="e_mername" list="%{merchantList}"  name="mername" headerKey="" headerValue="--Select Merchant--" listKey="merchantid" listValue="merchantid" disabled="true" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Employee ID  <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="empid" id="e_empid" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Expiry Date  <span style="color: red">*</span></label>
                            <sj:datepicker cssClass="form-control" id="e_expirydate" name="expirydate" readonly="true" changeYear="true" buttonImageOnly="true" displayFormat="dd/mm/yy" minDate="d+1" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Full Name  <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="fullname" id="e_fullname" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z ]/g,''))"/>
                        </div>
                    </div>
                </div>

                <div class="row ">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Address Line 1 <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="address1" id="e_address1" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9,//.-' ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9,//.-' ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Address Line 2 <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="address2" id="e_address2" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9,//.-' ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9,//.-' ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >City <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="city" id="e_city" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9,//.-' ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9,//.-' ]/g,''))"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Mobile No <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="mobile" id="e_mobile" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                        </div>
                    </div>
                </div>

                <div class="row ">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Telephone No <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="telno" id="e_telno" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"  onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Fax <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="fax" id="e_fax" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Email <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="mail" id="e_mail" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9_@. ]/g,''))"  onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9_@. ]/g,''))"/>
                        </div>
                    </div>
                </div>   
                <div class="row">
                    <div class="horizontal_line_popup"></div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div>
                <s:url action="updateSystemUser" var="updateturl"/>
                <div class="row">
                    <div class="col-md-12 text-right">
                        <div class="form-group">
                            <sj:submit 
                                button="true" 
                                value="Reset" 
                                name="reset" 
                                cssClass="btn btn-sm btn-reset"
                                onClick="cancelData()"
                                /> 

                            <sj:submit
                                button="true"
                                value="Update"
                                href="%{updateturl}"
                                targets="divmsgupdate"
                                id="updatebtn"
                                cssClass="btn btn-sm btn-functions"
                                />  
                        </div>
                    </div>
                </div> 
            </s:form>        
        </div>
    </body>
</html>
