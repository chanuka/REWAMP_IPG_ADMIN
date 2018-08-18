<%--     
    Author     : thushanth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">



<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf" %>


        <script type="text/javascript">

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Edit' onClick='javascript:editSystemUserInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Delete' onClick='javascript:deleteSystemUserInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }
            function editSystemUserInit(keyval) {
                $("#updatedialog").data('username', keyval).dialog('open');
            }
            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailSystemUser.ipg?username=" + $led.data('username'));
            });
            function deleteSystemUserInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete system user ' + keyval + ' ?');
                return false;
            }

            function deleteSystemUser(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteSystemUser.ipg',
                    data: {username: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);

                        resetFieldData();
                    },
                    error: function(data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }





            function editSystemUser(keyval) {
                if (keyval === "null") {
                    keyval = "";
                }
                $('#mername').val("");
                $.ajax({
                    url: '${pageContext.request.contextPath}/findSystemUser.ipg',
                    data: {username: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {

                            $('#username').val(data.username);
                            $('#username').attr('readOnly', true);
                            $(".hideme").hide();
                            $('#userrole').val(data.userrole);
//                            $('#dualAuth').val(data.dualAuth);
                            $('#status').val(data.status);
                            $('#empid').val(data.empid);
                            $('#expirydate').val(data.expirydate);
                            $('#fullname').val(data.fullname);
                            $('#address1').val(data.address1);
                            $('#address2').val(data.address2);
                            $('#city').val(data.city);
                            $('#mobile').val(data.mobile);
                            $('#telno').val(data.telno);
                            $('#fax').val(data.fax);
                            $('#mail').val(data.mail);
                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");

                            //check selected user is a merchant
                            if (data.mername) {
                                $("#mername").prop('disabled', false);
                                $('#mername').val(data.mername);
                            } else {
                                $("#mername").prop('disabled', true);
                            }

                        }
                    },
                    error: function (data) {
                        $("#editerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function deleteSystemUserInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete system user ' + keyval + ' ?');
                return false;
            }

            function deleteSystemUser(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteSystemUser.ipg',
                    data: {username: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);

                        resetFieldData();
                    },
                    error: function(data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function resetAllData() {
                $('#username').val("");
                $('#userrole').val("");
                $('#status').val("");

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        username: "",
                        userrole: "",
                        status: "",
                        search: false
                    }
                });
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function searchSysUser() {
                var username = $('#username').val();
                var userrole = $('#userrole').val();
                var status = $('#status').val();
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        username: username,
                        userrole: userrole,
                        status: status,
                        search: true
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {
//                $('#username').val("");
//                $('#userrole').val("");
//                $('#status').val("");

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

                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }



        </script>
    </head>

    <body >


        <!--for background wallpaper-->
        <div class="background-wallpaper"></div>
        <!--for header-->
        <jsp:include page="/WEB-INF/pages/template/subheader.jsp"/>
        <!--for side navigation bar-->
        <jsp:include page="/WEB-INF/pages/template/tree.jsp"/>

        <!--section > main content-->
    <section> 
        <div class="container-main">
            <div class="content-main">

                <!--for bread Crumb-->
                <jsp:include page="/WEB-INF/pages/template/breadcrumb.jsp"/>

                <div class="content-message">
                    <s:div id="divmsg">
                        <s:actionmessage theme="jquery"/>
                        <s:actionerror theme="jquery"/>
                    </s:div>
                </div>

                <s:set id="vadd" var="vadd"><s:property value="vadd" default="true"/></s:set>
                <s:set id="vsearch" var="vsearch"><s:property value="vsearch" default="true"/></s:set>
                <s:set var="vupdatebutt"><s:property value="vupdatebutt" default="true"/></s:set>
                <s:set var="vupdatelink"><s:property value="vupdatelink" default="true"/></s:set>
                <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>
                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="sysuser" name="sysuser" autocomplete="off" method="post" action="SystemUser" theme="simple"   >


                        <s:url var="addurl" action="viewpopupSystemUser"/>
                        <s:url var="updateurl" action="UpdateSystemUser"/>

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >User Name</label>
                                    <s:textfield cssClass="form-control" name="username" id="username" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" autocomplete="off"  />
                                </div>
                            </div>
                            <div class="col-md-3" >
                                <div class="form-group">
                                    <label class="control-label" >User Role</label>
                                    <s:select cssClass="form-control" id="userrole" list="%{userroleList}"  name="userrole" headerKey="" headerValue="--Select User Role--" listKey="userrolecode" listValue="description" onchange="checkuser(this);" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Status </label>
                                    <s:select cssClass="form-control" id="status" list="%{statusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                                </div>
                            </div>
                        </div>
                    </s:form>


                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" id="addButton"  disabled="#vsearch" onclick="searchSysUser()"/>

                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onclick="resetAllData()"  />
                                </div>
                            </div>
                            <div class="col-sm-3"></div>
                            <div class="col-sm-3 text-right">
                                <div class="form-group">
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurl}"
                                        buttonIcon="ui-icon-newwin"
                                        disabled="#vadd"
                                        value="Add System User"
                                        id="addButton_new"
                                        cssClass="btn btn-sm btn-functions" 
                                        />
                                </div>
                            </div>
                        </div>
                </div>

                <!-- Start delete confirm dialog box -->
                <sj:dialog 
                    id="deletedialog" 
                    buttons="{ 
                    'OK':function() { deleteSystemUser($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Delete System User"                            
                    />
                <!-- Start delete process dialog box -->
                <sj:dialog 
                    id="deletesuccdialog" 
                    buttons="{
                    'OK':function() { $( this ).dialog( 'close' );}
                    }"  
                    autoOpen="false" 
                    modal="true" 
                    title="Deleting Process." 
                    />
                <!-- Start delete error dialog box -->
                <sj:dialog 
                    id="deleteerrordialog" 
                    buttons="{
                    'OK':function() { $( this ).dialog( 'close' );}                                    
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Delete error."
                    />

                <!-- Start edit process dialog box -->
                <sj:dialog 
                    id="editerrordialog" 
                    buttons="{
                    'OK':function() { $( this ).dialog( 'close' );}                                    
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Edit error."
                    /> 

                <!-- Start common error dialog box -->
                <sj:dialog 
                    id="errordialog" 
                    buttons="{
                    'OK':function() { $( this ).dialog( 'close' );}                                    
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Common Error."
                    /> 

                <sj:dialog                                     
                    id="remotedialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    title="Add System User"                            
                    loadingText="Loading .."                            
                    position="center"                            
                    width="1000"
                    height="500"
                    dialogClass= "dialogclass"
                    cssStyle="overflow: visible;"
                    />
                <sj:dialog                                     
                    id="updatedialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    position="center"
                    title="Update System User"
                    onOpenTopics="openviewtasktopage" 
                    loadingText="Loading .."
                    width="1000"
                    height="500"
                    cssStyle="overflow: visible;"
                    dialogClass= "dialogclass"
                    /> 
                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <s:url var="listurl" action="listSystemUser"/>
                    <sjg:grid
                        id="gridtable"
                        caption="System Users"
                        dataType="json"
                        href="%{listurl}"
                        pager="true"
                        gridModel="gridModel"
                        rowList="10,15,20"
                        rowNum="10"
                        autowidth="true"
                        rownumbers="true"
                        onCompleteTopics="completetopics"
                        rowTotal="false"
                        viewrecords="true"
                        >
                        <sjg:gridColumn name="username" index="u.username" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                        <sjg:gridColumn name="username" index="u.username" title="Delete" width="40" align="center" formatter="deleteformatter" hidden="#vdelete"/>  
                        <sjg:gridColumn name="username" index="u.username" title="User Name"  sortable="true"/>
                        <sjg:gridColumn name="userrole" index="u.ipguserroleByUserrolecode.description" title="User Role"  sortable="true"/>
                        <sjg:gridColumn name="status" index="u.ipgstatus.description" title="Status"  sortable="true"/>                        
                        <sjg:gridColumn name="createdtime" index="u.createdtime" title="Created Time"  sortable="true"/>
                    </sjg:grid> 
                </div>
            </div>
        </div>
    </section>
    <footer>
        <!--for footer-->
        <jsp:include page="/WEB-INF/pages/template/footer.jsp"/>
    </footer>
</body>
</html>



