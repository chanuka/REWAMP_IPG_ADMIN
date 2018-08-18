<%-- 
    Document   : ipginfo
    Created on : 31/10/2013, 2:08:00 PM
    Author     : jeevan
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

            function editformatter(cellvalue) {
                a = $("#addButton").is(':disabled');
                u = $("#updateButton").is(':disabled');
                return "<a href='#' title='Edit' onClick='javascript:editBin(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function deleteformatter(cellvalue) {
                return "<a href='#' title='Delete' onClick='javascript:deleteBinInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }


            function editBin(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findIPGMPIInfoServelet.ipg',
                    data: {ipgMpiInfoId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {

                            $('#ipgMpiInfoId').val(data.ipgMpiInfoId);
                            $('#primaryUrl').val(data.primaryUrl);
                            $('#secondaryUrl').val(data.secondaryUrl);
                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");
                        }
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function deleteBinInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete Ipg info ' + keyval + ' ?');
                return false;
            }

            function deleteBin(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteIPGMPIInfoServelet.ipg',
                    data: {ipgMpiInfoId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
                        //                        jQuery("#gridtable").trigger("reloadGrid");                      
                    },
                    error: function (data) {
                        $("#deleteerrordialog").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            //            var a =  $("#addButton").is(':disabled');
            //            var u =  $("#updateButton").is(':disabled');  
            function resetAllData() {
                a = $("#addButton").is(':disabled');
                u = $("#updateButton").is(':disabled');
                //                alert("add status " + a  +" and updated stauts " + u);

                if (a == true && u == true) {
                    $('#primaryUrl').val("");
                    //                    $('#bincode').attr('readOnly',false);
                    $('#secondaryUrl').val("");
                    //                    $('#onusstatus').val("");
                    //                    $('#cardassociation').val("");
                    //                    $('#status').val("");
                    $('#divmsg').text("");
                    $('#addButton').button("disable");
                    $('#updateButton').button("disable");
                } else if (a == true && u == false) {
                    var ipgMpiInfoId = $('#ipgMpiInfoId').val();
                    //   $('#primaryUrl').attr('primaryUrl',true);
                    editBin(ipgMpiInfoId);
                } else if (a == false && u == true) {
                    $('#primaryUrl').val("");
                    $('#binprimaryUrlcode').attr('primaryUrl', false);
                    $('#secondaryUrl').val("");
                    //                    $('#onusstatus').val("");
                    //                    $('#cardassociation').val("");
                    //                    $('#status').val("");
                    $('#divmsg').text("");
                    $('#addButton').button("enable");
                    $('#updateButton').button("disable");
                }
            }

            function resetFieldData() {

                if (updateNotifier == true) {

                    $('#primaryUrl').val("");
                    //                    $('#bincode').attr('readOnly', false);
                    $('#secondaryUrl').val("");
                    //                    $('#status').val("");
                    //                    $('#onusstatus').val("");
                    //                    $('#cardassociation').val("");
                    $('#updateButton').button("disable");
                    if ((<s:property value="vadd" />) == false) {
                        $('#addButton').button("enable");

                    }
                    updateNotifier = false;
                } else {


                    $('#primaryUrl').val("");
                    //                    $('#bincode').attr('readOnly',false);
                    $('#secondaryUrl').val("");
                    //                    $('#onusstatus').val("");
                    //                    $('#cardassociation').val("");
                    //                    $('#status').val("");
                    //               alert(" 2nd alert add status " + a  +" and updated stauts " + u);
                    if (a == true && u == true) {
                        //                   alert("call 1st");
                        $('#addButton').button("disable");
                        $('#updateButton').button("disable");
                    } else if (a == true && u == false) {
                        $('#addButton').button("disable");
                        $('#updateButton').button("disable");
                    } else if (a == false && u == true) {
                        $('#addButton').button("enable");
                        $('#updateButton').button("disable");
                    }

                }
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


                <s:set id="vadd" var="vadd"><s:property value="vadd" default="true"/></s:set>
                <s:set var="vupdatebutt"><s:property value="vupdatebutt" default="true"/></s:set>
                <s:set var="vupdatelink"><s:property value="vupdatelink" default="true"/></s:set>
                <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>

                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="binadd" method="post" action="Bin" theme="simple" >

                        <%--  <s:textfield type="hidden"  name="ipgMpiInfoId" id="ipgMpiInfoId" /> --%>

                        <s:hidden name="ipgMpiInfoId" id="ipgMpiInfoId" />
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Primary URL <span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="primaryUrl" id="primaryUrl" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Secondary URL <span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="secondaryUrl" id="secondaryUrl" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                            </div>
                        </div>

                        <s:url var="addurl" action="addIPGMPIInfoServelet"/>
                        <s:url var="updateurl" action="updateIPGMPIInfoServelet"/>

                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{addurl}" value="Add" targets="divmsg" id="addButton"  disabled="#vadd"/>
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{updateurl}" value="Update" targets="divmsg"   disabled="#vupdatebutt" id="updateButton" onclick="javascript:updateNotifier=true;"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()"  />
                                </div>
                            </div>
                        </div>
                    </s:form>
                </div>

                <!-- Start delete confirm dialog box -->
                <sj:dialog
                    id="deletedialog"
                    buttons="{
                    'OK':function() { deleteBin($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );}
                    }"
                    autoOpen="false"
                    modal="true"
                    title="Delete Ipg info"
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

                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <s:url var="listurl" action="listIPGMPIInfoServelet"/>
                    <sjg:grid
                        id="gridtable"
                        caption="IPG MPI Info"
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
                        <sjg:gridColumn name="infoId" index="u.ipgmpiinfoid" title="Info ID"  sortable="true"/>
                        <sjg:gridColumn name="primaryUrl" index="u.primaryurl" title="Primary URL"  sortable="true"/>
                        <sjg:gridColumn name="secondaryUrl" index="u.secounderyurl" title="Secondary URL"  sortable="true"/>
                        <%--<sjg:gridColumn name="onusstatus" index="u.ipgstatusByOnusstatus.description" title="Secondary URL"  sortable="true"/>--%>
                        <sjg:gridColumn name="infoId" index="u.ipgmpiinfoid" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                        <sjg:gridColumn name="infoId" index="u.ipgmpiinfoid" title="Delete" width="40" align="center" formatter="deleteformatter" hidden="#vdelete"/>  
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