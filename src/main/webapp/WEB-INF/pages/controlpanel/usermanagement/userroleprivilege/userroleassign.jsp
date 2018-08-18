<%-- 
    Document   : userroleassign
    Created on : Jul 4, 2018, 3:45:18 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <script type="text/javascript">
            $(document).ready(function () {
                if ($("#userRole").val() && $("#userRoleDes").val() && $("#Category").val()) {
                    document.getElementById('uRole').innerHTML = $("#userRoleDes").val();
                    if ($("#Category").val() === "Sections") {
                        $(".hideme1").hide();
                        $(".hideme2").hide();
                        $(".la2").hide();
                        $(".la3").hide();
                        //               $(".mandatoryfield").hide();               
                        loadsection($("#userRole").val());

                    } else if ($("#Category").val() === "Pages") {
                        $(".hideme2").hide();
                        $(".la1").hide();
                        $(".la3").hide();

                        if (!$("#section").val()) {
                            document.getElementById('pa').innerHTML = "No Section Selected";
                        }

                        loaddropdownSections($("#userRole").val());

                    } else if ($("#Category").val() === "Operations") {
                        $(".hideme1").hide();
                        $(".la1").hide();
                        $(".la2").hide();

                        if (!$("#page").val()) {
                            document.getElementById('ta').innerHTML = "No Page Selected";
                        }
                        loaddropdownSectionpages($("#userRole").val());
                    }
                } else {
                    //               alert("Error occured while loading.");
                    window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                }
            });

            function loadsection(key) {

                $.ajax({
                    url: '${pageContext.request.contextPath}/FindUserRolePrivilege.ipg',
                    data: {
                        userRole: key
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();

                        var msg = data.message;

                        if (msg) {

                            $('#currentBox').empty();
                            $('#newBox').empty();
                            alert("1 " + data.message);
                        } else {
                            $('#oldvalue').val(data.oldvalue);
                            $('#newBox').empty();
                            $('#currentBox').empty();

                            $.each(data.newList, function (index, item) {
                                $('#newBox').append(
                                        $('<option></option>').val(item.key).html(
                                        item.value));
                            });
                            $.each(data.currentList, function (index, item) {
                                $('#currentBox').append(
                                        $('<option></option>').val(item.key).html(
                                        item.value));
                            });
                        }
                    },
                    error: function (data) {
                        //                alert("Error occurd while loading.");
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function loaddropdownSections(keyval) {

                if (!keyval) {
                    $('#currentBox').empty();
                    $('#newBox').empty();
                    $('#section').empty();
                    $('#section').append("<option value=''>--Select Section--</option>");
                    alert("Empty user role ");
                    return;
                }

                $.ajax({
                    url: '${pageContext.request.contextPath}/LoadSectionUserRolePrivilege.ipg',
                    data: {userRole: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#section').empty();
                            $('#currentBox').empty();
                            $('#newBox').empty();
                            $('#section').append("<option value=''>--Select Section--</option>");
                            alert("2 " + data.message);
                        } else {

                            $('#section').empty();
                            $('#currentBox').empty();
                            $('#newBox').empty();
                            $('#section').append("<option value=''>--Select Section--</option>")

                            $.each(data.sectionMap, function (index, item) {
                                $('#section').append(
                                        $('<option></option>').val(item.key).html(item.value)
                                        );
                            });
                        }
                    },
                    error: function (data) {
                        //                        $("#errordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }


            function loaddropdownSectionpages(keyval) {

                if (!keyval) {
                    $('#currentBox').empty();
                    $('#newBox').empty();
                    $('#sectionpage').empty();
                    $('#sectionpage').append("<option value=''>--Select Section--</option>");
                    alert("Empty user role ");
                    return;
                }


                $.ajax({
                    url: '${pageContext.request.contextPath}/LoadSectionUserRolePrivilege.ipg',
                    data: {userRole: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#sectionpage').empty();
                            $('#currentBox').empty();
                            $('#newBox').empty();
                            $('#sectionpage').append("<option value=''>--Select Section--</option>");
                            alert("3" + data.message);
                        } else {

                            $('#sectionpage').empty();
                            $('#currentBox').empty();
                            $('#newBox').empty();
                            $('#sectionpage').append("<option value=''>--Select Section--</option>")

                            $.each(data.sectionMap, function (index, item) {
                                $('#sectionpage').append(
                                        $('<option></option>').val(item.key).html(item.value)
                                        );
                            });
                        }
                    },
                    error: function (data) {
                        //                        $("#errordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }




            function loadPage(keyval) {
                var userRole = $('#userRole').val();



                if ($("#section").val()) {
                    var e = document.getElementById("section");
                    var se = e.options[e.selectedIndex].text;
                    document.getElementById('pa').innerHTML = se + ". ";
                } else {
                    document.getElementById('pa').innerHTML = "No Section Selected";

                }

                if (!keyval && keyval !== "") {
                    //           $('#section').val("");
                    $('#currentBox').empty();
                    $('#newBox').empty();
                    alert("Empty section");
                    return;
                } else if (keyval === "") {
                    $('#currentBox').empty();
                    $('#newBox').empty();
                    logout(false);//logout method
                    return;
                }



                if (!userRole) {

                    $('#currentBox').empty();
                    $('#newBox').empty();
                    $('#section').empty();
                    $('#section').append("<option value=''>--Select Section--</option>");
                    alert("Empty user role");
                    return;
                }

                $.ajax({
                    url: '${pageContext.request.contextPath}/FindPageUserRolePrivilege.ipg',
                    data: {section: keyval, userRole: userRole},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#section').val("");
                            $('#currentBox').empty();
                            $('#newBox').empty();
                            alert("4 " + data.message);
                        } else {
                            $('#oldvalue').val(data.oldvalue);

                            $('#newBox').empty();
                            $('#currentBox').empty();

                            $.each(data.newList, function (index, item) {
                                $('#newBox').append(
                                        $('<option></option>').val(item.key).html(
                                        item.value));
                            });
                            $.each(data.currentList, function (index, item) {
                                $('#currentBox').append(
                                        $('<option></option>').val(item.key).html(
                                        item.value));
                            });

                        }
                    },
                    error: function (data) {
                        //                        $("#errordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function loaddropdownPages(keyval) {

                var userRole = $('#userRole').val();
                ;


                if (!$("#sectionpage").val()) {
                    document.getElementById('ta').innerHTML = "No Page Selected";
                }

                if (!keyval && keyval !== "") {
                    $('#page').empty();
                    $('#page').append("<option value=''>--Select Page--</option>");
                    $('#currentBox').empty();
                    $('#newBox').empty();
                    alert("Empty section ");
                    return;

                } else if (keyval === "") {
                    $('#page').empty();
                    $('#page').append("<option value=''>--Select Page--</option>");
                    $('#currentBox').empty();
                    $('#newBox').empty();
                    return;
                }

                if (!userRole) {
                    $('#page').empty();
                    $('#page').append("<option value=''>--Select Page--</option>");
                    $('#sectionpage').empty();
                    $('#sectionpage').append("<option value=''>--Select Section--</option>");
                    $('#currentBox').empty();
                    $('#newBox').empty();
                    alert("Empty user role ");
                    return;
                }

                $.ajax({
                    url: '${pageContext.request.contextPath}/LoadPageUserRolePrivilege.ipg',
                    data: {userRole: userRole, sectionpage: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();

                        var msg = data.message;

                        if (msg) {
                            $('#page').empty();
                            $('#currentBox').empty();
                            $('#newBox').empty();
                            $('#page').append("<option value=''>--Select Page--</option>");
                            alert("5 " + data.message);
                        } else {
                            $('#page').empty();
                            $('#currentBox').empty();
                            $('#newBox').empty();
                            $('#page').append("<option value=''>--Select Page--</option>")

                            $.each(data.pageMap, function (index, item) {
                                $('#page').append(
                                        $('<option></option>').val(item.key).html(item.value)
                                        );
                            });
                        }
                    },
                    error: function (data) {
                        //                        alert("Error occurd while loading.");
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function loadTask(keyval) {

                var userRole = $('#userRole').val();
                var section = $('#sectionpage').val();
                //                var page =  $("#page").val();   

                if ($("#page").val()) {
                    var e = document.getElementById("page");
                    var pag = e.options[e.selectedIndex].text;
                    document.getElementById('ta').innerHTML = pag + ". ";
                } else {
                    document.getElementById('ta').innerHTML = "No Page Selected";

                }

                if (!keyval && keyval !== "") {
                    //           $('#page').val("");
                    $('#currentBox').empty();
                    $('#newBox').empty();
                    alert("Empty page");
                    return;
                } else if (keyval === "") {
                    $('#currentBox').empty();
                    $('#newBox').empty();
                    logout(false);//logout method
                    return;
                }

                if (!section) {
                    //           $('#sectionpage').val("");
                    //           $('#page').val("");
                    $('#currentBox').empty();
                    $('#newBox').empty();
                    alert("Empty section");
                    return;
                } else if (!userRole) {
                    $('#sectionpage').empty();
                    $('#sectionpage').append("<option value=''>--Select Section--</option>");
                    $('#page').empty();
                    $('#page').append("<option value=''>--Select Page--</option>");
                    $('#currentBox').empty();
                    $('#newBox').empty();
                    alert("Empty user role");
                    return;
                }


                $.ajax({
                    url: '${pageContext.request.contextPath}/FindTaskUserRolePrivilege.ipg',
                    data: {userRole: userRole, sectionpage: section, page: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;

                        if (msg) {
                            $('#page').val("");
                            $('#currentBox').empty();
                            $('#newBox').empty();
                        } else {
                            $('#oldvalue').val(data.oldvalue);
                            $('#newBox').empty();
                            $('#currentBox').empty();

                            $.each(data.newList, function (index, item) {
                                $('#newBox').append(
                                        $('<option></option>').val(item.key).html(
                                        item.value));
                            });
                            $.each(data.currentList, function (index, item) {
                                $('#currentBox').append(
                                        $('<option></option>').val(item.key).html(
                                        item.value));
                            });

                        }
                    },
                    error: function (data) {
                        //                        alert("Error occurd while loading.");
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function toleft() {
                $("#newBox option:selected").each(function () {

                    $("#currentBox").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            ;
            function toright() {
                $("#currentBox option:selected").each(function () {

                    $("#newBox").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function toleftall() {
                $("#newBox option").each(function () {

                    $("#currentBox").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function torightall() {
                $("#currentBox option").each(function () {

                    $("#newBox").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function clickAssign() {

                $('#currentBox option').prop('selected', true);
                $('#newBox option').prop('selected', true);
                $("#assignbut").click();
            }


            function resetFieldData() {

                if ($("#Category").val() === "Pages") {
                    $("#section").val("");
                    $('#newBox').empty();
                    $('#currentBox').empty();
                    if (!$("#section").val()) {
                        document.getElementById('pa').innerHTML = "No Section Selected";
                    }


                } else if ($("#Category").val() === "Operations") {
                    $('#newBox').empty();
                    $('#currentBox').empty();
                    $("#sectionpage").val("");
                    $('#page').empty();
                    $('#page').append("<option value=''>--Select Page--</option>");
                    if (!$("#sectionpage").val()) {
                        document.getElementById('ta').innerHTML = "No Page Selected";
                    }
                }

            }
            function resetAllData() {
                $('#divmsg').text("");
                if ($("#Category").val() === "Sections") {
                    loadsection($("#userRole").val());

                } else if ($("#Category").val() === "Pages") {
                    loadPage($("#section").val());

                } else if ($("#Category").val() === "Operations") {
                    loadTask($("#page").val());
                }

            }

            function logout(param) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/LogOutUserUserRolePrivilege.ipg',
                    data: {},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });

            }
            ;


        </script>
    </head>
    <body >
        <div class="content-message">
            <s:div id="divmsg">
                <s:actionerror theme="jquery"/>
                <s:actionmessage theme="jquery"/>
            </s:div>
        </div>
        <s:set id="vassign" var="vassign">
            <s:property value="vassign" default="true" />
        </s:set>

        <!-- Form -->
        <div class="content-form">
            <s:form cssClass="form" action="UserRolePrivilege" theme="simple" method="post"
                    id="assignform" name="assignform" target="divmsg">

                <s:hidden id="userRole" name="userRole"/>
                <s:hidden id="userRoleDes" name="userRoleDes"/>
                <s:hidden id="Category" name="category"/>

                <div class="row row_popup">
                    <div class="col-sm-1" style="padding-right:0px;width: 100px;">
                        <div class="form-group">
                            <label>User Role : </label>                       
                        </div>
                    </div>
                    <div class="col-sm-4 text-left" style="padding-left: 0px;">
                        <div class="form-group">
                            <div id="uRole">
                            </div> 
                        </div>
                    </div>
                </div>
                <div class="row row_popup hideme1">
                    <div class="col-sm-1" style="padding-right:0px;width: 100px;">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Section &nbsp;&nbsp;&nbsp;: </label>                      
                        </div>
                    </div>
                    <div class="col-sm-4 text-left" style="padding-left: 0px;">
                        <div class="form-group">
                            <s:select  cssClass="form-control" id="section" list="sectionMap"  onchange="loadPage(this.value)" name="section" headerKey="" headerValue="--Select Section--" listKey="sectioncode" listValue="description"/>        
                        </div>
                    </div>
                </div>
                <div class="row row_popup hideme2">
                    <div class="col-sm-1" style="padding-right:0px;width: 100px;">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Section &nbsp;&nbsp;&nbsp;: </label>                      
                        </div>
                    </div>
                    <div class="col-sm-4 text-left" style="padding-left: 0px;">
                        <div class="form-group">
                            <s:select cssClass="form-control" id="sectionpage" list="sectionMap"  onchange="loaddropdownPages(this.value)" name="sectionpage" headerKey="" headerValue="--Select Section--" listKey="sectioncode" listValue="description"/>
                        </div>
                    </div>
                </div>
                <div class="row row_popup hideme2">
                    <div class="col-sm-1" style="padding-right:0px;width: 100px;">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Page &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: </label>                    
                        </div>
                    </div>
                    <div class="col-sm-4 text-left" style="padding-left: 0px;">
                        <div class="form-group">
                            <s:select cssClass="form-control" id="page" list="pageMap"  name="page" headerKey="" headerValue="--Select Page--" onchange="loadTask(this.value)" listKey="pagecode" listValue="description"/>
                        </div>
                    </div>
                </div>            
                <div class="row row_popup la1">
                    <div class="col-sm-1" style="padding-right:0px;width: 100px;">
                        <div class="form-group">
                            <span style="color: red">*</span><label>Sections &nbsp;: </label>                      
                        </div>
                    </div>
                    <div class="col-sm-4 text-left" style="padding-left: 0px;">
                        <div class="form-group">

                        </div>
                    </div>
                </div>
                <div class="row row_popup la2">
                    <div class="col-sm-1" style="padding-right:0px;width: 100px;">
                        <div class="form-group">
                            <label>Pages for &nbsp;: </label>                    
                        </div>
                    </div>
                    <div class="col-sm-4 text-left" style="padding-left: 0px;">
                        <div class="form-group">
                            <div id="pa">
                            </div> 
                        </div>
                    </div>
                </div>
                <div class="row row_popup la3">
                    <div class="col-sm-1" style="padding-right:0px;width: 100px;">
                        <div class="form-group">
                            <label>Tasks for &nbsp;&nbsp;: </label>                    
                        </div>
                    </div>
                    <div class="col-sm-4 text-left" style="padding-left: 0px;">
                        <div class="form-group">
                            <div id="ta">
                            </div> 
                        </div>
                    </div>
                </div>

                <div class="move-change-side">
                    <table>
                        <tr>
                            <td style="float: left;">
                                <div  style="overflow-x:scroll;width:300px;">  
                                    <s:select cssClass="move-selectClass" multiple="true" name="currentBox" id="currentBox" list="currentList" ondblclick="toright()" />

                                </div>
                            </td>

                            <td class="move-buttonClass">
                                <sj:a id="right" onClick="toright()" button="true" > <i class="fa fa-chevron-right" aria-hidden="true"></i> </sj:a> </br> 
                                <sj:a id="rightall" onClick="torightall()" button="true" > <i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i> </sj:a></br> 
                                <sj:a id="left" onClick="toleft()" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                <sj:a id="leftall" onClick="toleftall()" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                                </td>
                                <td style="float: right;">
                                    <div  style="overflow-x:scroll;width:300px;">  
                                    <s:select cssClass="move-selectClass" multiple="true"  name="newBox" id="newBox" list="newList" ondblclick="toleft()" />
                                </div>
                            </td> 
                        </tr>
                    </table>
                </div>

                <div style="display: none; visibility: hidden;">
                    <s:url var="assignurl" action="AssignUserRolePrivilege" />
                    <sj:submit button="true" href="%{assignurl}" id="assignbut"
                               targets="divmsg" />
                </div>


                <div class="row row_popup">
                    <div class="horizontal_line_popup"></div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div>  

                <div class="row">
                    <div class="col-md-12 text-right">
                        <div class="form-group">

                            <sj:submit
                                button="true"
                                value="Reset" 
                                name="reset"
                                onClick="resetAllData()" 
                                cssClass="btn btn-sm btn-reset"

                                />
                            <sj:submit
                                button="true"
                                id="assignbuta"
                                value="Assign"
                                onclick="clickAssign()"
                                cssClass="btn btn-sm btn-functions" 
                                disabled="#vassign"/>
                        </div>
                    </div>
                </div>  
            </s:form>            
            <!-- Start error dialog box -->
            <sj:dialog 
                id="errordialog" 
                buttons="{
                'OK':function() { $( this ).dialog( 'close' );}                                    
                }" 
                autoOpen="false" 
                modal="true" 
                title="Error Occured"
                />
        </div>
    </body>
</html>


