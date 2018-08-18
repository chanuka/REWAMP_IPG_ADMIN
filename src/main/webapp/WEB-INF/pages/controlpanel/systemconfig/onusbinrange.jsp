<%--  
        @created on :Nov 1, 2013, 10:55:36 AM
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
                return "<a href='#' title='Edit' onClick='javascript:editOnUsBinRange(" + cellvalue + ")'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }


            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Delete' onClick='javascript:deleteOnUsBinRangeInit(" + cellvalue + ")'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function editOnUsBinRange(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findOnUsBinRange.ipg',
                    data: {ipgonusbinrangeId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {
                            $('#ipgonusbinrangeId').val(data.ipgonusbinrangeId);
                            $('#value_1').val(data.value_1);
                            $('#value_2').val(data.value_2);
                            $('#reMarks').val(data.reMarks);
                            $('#status').val(data.status);
                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");

                        }
                    },
                    error: function (data) {
                        $("#editerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function deleteOnUsBinRangeInit(keyval) {
                $('#divmsg').empty();
                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete On-Us-Bin-Range-ID :' + keyval + ' ?');
                return false;
            }

            function deleteOnUsBinRange(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteOnUsBinRange.ipg',
                    data: {ipgonusbinrangeId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
//                        jQuery("#gridtable").trigger("reloadGrid");                      
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');

                    }
                });
            }

            function resetAllData() {
                var s = $("#addButton").is(':disabled');
                if (s == false) {
                    $('#value_1').val("");
                    $('#value_2').val("");
                    $('#reMarks').val("");
                    $('#status').val("");
                    $('#divmsg').text("");
                    $('#addButton').button("enable");
                    $('#updateButton').button("disable");
                } else {
                    var ipgonusbinrangeId = $('#ipgonusbinrangeId').val();
                    editOnUsBinRange(ipgonusbinrangeId);
                }
            }

            function resetFieldData() {

                $('#value_1').val("");
                $('#value_2').val("");
                $('#reMarks').val("");
                $('#status').val("");
                $('#addButton').button("enable");
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

                <s:set id="vadd" var="vadd"><s:property value="vadd" default="true"/></s:set>
                <s:set var="vupdatebutt"><s:property value="vupdatebutt" default="true"/></s:set>
                <s:set var="vupdatelink"><s:property value="vupdatelink" default="true"/></s:set>
                <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>

                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="OnUsBinRangeadd" method="post" action="addOnUsBinRange" theme="simple" >

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Value 1 <span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="value_1" id="value_1" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Value 2 <span style="color: red">*</span></label>
                                    <s:textfield  cssClass="form-control" id="value_2"   name="value_2" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"  />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Remarks <span style="color: red">*</span></label>
                                    <s:textfield  cssClass="form-control" id="reMarks" name="reMarks" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Status Code <span style="color: red">*</span></label>
                                    <s:select cssClass="form-control" id="status" list="%{statusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                            </div>
                        </div>        
                                
                        <s:url var="addurl" action="addOnUsBinRange"/>
                        <s:url var="updateurl" action="updateOnUsBinRange"/>
                        
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{addurl}" value="Add" targets="divmsg" id="addButton"  disabled="#vadd"/>

                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{updateurl}" value="Update" targets="divmsg"   disabled="#vupdatebutt" id="updateButton"/>

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
                    'OK':function() { deleteOnUsBinRange($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Delete On-Us-Bin-Range"                            
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


                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <s:url var="listurl" action="listOnUsBinRange"/>
                    <sjg:grid
                        id="gridtable"
                        caption="IPG On Us Bin Range"
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
                        <sjg:gridColumn name="value1" index="u.value1" title="Value 1"  sortable="true"/>
                        <sjg:gridColumn name="value2" index="u.value2" title="Value 2"  sortable="true"/>
                        <sjg:gridColumn name="remarks" index="u.remarks" title="Remarks"  sortable="true"/>
                        <sjg:gridColumn name="statuscode" index="u.ipgstatus.description" title="Status"  sortable="true"/>
                        <sjg:gridColumn name="ipgonusbinrangeid" index="u.ipgonusbinrangeid" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                        <sjg:gridColumn name="ipgonusbinrangeid" index="u.ipgonusbinrangeid" title="Delete" width="25" align="center" formatter="deleteformatter" hidden="#vdelete"/>
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


