<%--  
        @created on :Oct 30, 2013, 9:53:02 AM
        @author     :thushanth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML>

<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf" %>


        <script type="text/javascript">

            function editformatter(cellvalue, options, rowObject) {
                u = $("#updateButton").is(':disabled');
                return "<a href='#' title='Edit' onClick='javascript:editCommonconfig(" + cellvalue + ")'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function editCommonconfig(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findCommonConfig.ipg',
                    data: {recordId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {

                            $('#mpiServerIP').val(data.mpiServerIP);
                            $('#mpiServerPort').val(data.mpiServerPort);
                            $('#switchIP').val(data.switchIP);
                            $('#switchPort').val(data.switchPort);
                            $('#ipgengineurl').val(data.ipgengineurl);
                            $('#mpiserverurl').val(data.mpiserverurl);
                            $('#keystorePassword').val(data.keystorePassword);
                            $('#acquirerBin').val(data.acquirerBin);

                            $('#updateButton').button("enable");

                        }
                    },
                    error: function (data) {
                        $("#editerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function resetAllData() {

                u = $("#updateButton").is(':disabled');

                if (u == true) {
                    $('#mpiServerIP').val("");
                    $('#mpiServerPort').val("");
                    $('#switchIP').val("");
                    $('#switchPort').val("");
                    $('#ipgengineurl').val("");
                    $('#mpiserverurl').val("");
                    $('#keystorePassword').val("");
                    $('#acquirerBin').val("");

                    $('#divmsg').text("");
                    $('#updateButton').button("disable");
                } else {
                    var recordid = 1;
                    editCommonconfig(recordid);
                }
            }

            function resetFieldData() {
                $('#mpiServerIP').val("");
                $('#mpiServerPort').val("");
                $('#switchIP').val("");
                $('#switchPort').val("");
                $('#ipgengineurl').val("");
                $('#mpiserverurl').val("");
                $('#keystorePassword').val("");
                $('#acquirerBin').val("");
                $('#updateButton').button("disable");
                jQuery("#gridtable").trigger("reloadGrid");
            }


        </script>
    </head>

    <body>

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

                <s:set var="vupdatebutt"><s:property value="vupdatebutt" default="true"/></s:set>
                <s:set var="vupdatelink"><s:property value="vupdatelink" default="true"/></s:set>

                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="commonconfigadd" method="post" action="CommonConfig" theme="simple" >

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >MPI Server IP <span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="mpiServerIP" id="mpiServerIP" maxLength="15" onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9.]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >MPI Server Port <span style="color: red">*</span></label>
                                    <s:textfield  cssClass="form-control" id="mpiServerPort"   name="mpiServerPort" maxLength="5" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >MPI Switch IP <span style="color: red">*</span></label>
                                    <s:textfield  cssClass="form-control" id="switchIP"   name="switchIP" maxLength="15" onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9.]/g,''))" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >MPI Switch Port <span style="color: red">*</span></label>
                                    <s:textfield  cssClass="form-control" name="switchPort" id="switchPort" maxLength="5" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >IPG Engine URL <span style="color: red">*</span></label>
                                    <s:textfield  cssClass="form-control" name="ipgengineurl" id="ipgengineurl" maxLength="64" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >MPI Server URL  <span style="color: red">*</span></label>
                                    <s:textfield  cssClass="form-control" name="mpiserverurl" id="mpiserverurl" maxLength="64" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Keystore Password  <span style="color: red">*</span></label>
                                    <s:password  cssClass="form-control" name="keystorePassword" id="keystorePassword" maxLength="32" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Acquirer Bin  <span style="color: red">*</span></label>
                                    <s:textfield  cssClass="form-control" name="acquirerBin" id="acquirerBin" maxLength="6" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                            </div>
                        </div>
                                
                        <s:url var="updateurl" action="updateCommonConfig"/>
                        
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{updateurl}" value="Update" targets="divmsg"   disabled="#vupdatebutt" id="updateButton"/>

                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()"  />
                                </div>
                            </div>
                        </div>
                    </s:form>
                </div>

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



                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <s:url var="listurl" action="listCommonConfig"/>
                    <sjg:grid
                        id="gridtable"
                        caption="Common Configuration"
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
                        <sjg:gridColumn name="mpiserverip" index="u.mpiserverip" title="MPI Server IP"  sortable="true"/>
                        <sjg:gridColumn name="mpiserverport" index="u.mpiserverport" title="MPI Server Port"  sortable="true"/>
                        <sjg:gridColumn name="switchip" index="u.switchip" title="Switch IP"  sortable="true"/>
                        <sjg:gridColumn name="switchport" index="u.switchport" title="Switch Port"  sortable="true"/>
                        <sjg:gridColumn name="ipgengineurl" index="u.ipgengineurl" title="IPG Engine URL"  sortable="true"/>
                        <sjg:gridColumn name="mpiserverurl" index="u.mpiserverurl" title="MPI Server URL"  sortable="true"/>
                        <sjg:gridColumn name="acquirerBin" index="u.acquirerbin" title="Acquirer Bin"  sortable="true"/>
                        <%--<sjg:gridColumn name="keystorePassword" index="u.keystorepassword" title="Keystore Password"  sortable="true"/>--%>
                        <sjg:gridColumn name="recordid" index="u.recordid" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>

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



