<%-- 
    Document   : bin
    Created on : Jul 9, 2018, 3:38:31 PM
    Author     : sivaganesan_t
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
                return "<a href='#' title='Edit' onClick='javascript:editBinInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function deleteformatter(cellvalue) {
                return "<a href='#' title='Delete' onClick='javascript:deleteBinInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function editBinInit(keyval) {
                $("#updatedialog").data('bincode', keyval).dialog('open');
            }

            $.subscribe('openviewbintopage', function(event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailBin.ipg?bincode=" + $led.data('bincode'));
            });

            function editBin(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findBin.ipg',
                    data: {bincode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {

                            $('#bincode').val(data.bincode);
                            $('#bincode').attr('readOnly', true);
                            $('#description').val(data.description);
                            $('#onusstatus').val(data.onusstatus);
                            $('#cardassociation').val(data.cardassociation);
                            $('#status').val(data.status);
                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");
                        }
                    },
                    error: function(data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function deleteBinInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete BIN ' + keyval + ' ?');
                return false;
            }

            function deleteBin(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteBin.ipg',
                    data: {bincode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
                        //                        jQuery("#gridtable").trigger("reloadGrid");                      
                    },
                    error: function(data) {
                        $("#deleteerrordialog").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            //            var a =  $("#addButton").is(':disabled');
            //            var u =  $("#updateButton").is(':disabled');  
            function resetAllData() {
                $('#bincode').val("");
                $('#description').val("");
                $('#onusstatus').val("");
                $('#cardassociation').val("");
                $('#status').val("");
                $('#divmsg').text("");
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        bincode: '',
                        description: '',
                        onusstatus: '',
                        cardassociation: '',
                        status: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {


                $('#ibincode').val("");
                $('#idescription').val("");
                $('#ionusstatus').val("");
                $('#icardassociation').val("");
                $('#istatus').val("");

                jQuery("#gridtable").trigger("reloadGrid");

            }
            function searchBin() {
                $('#divmsg').text("");
                var bincode = $('#bincode').val();
                var description = $('#description').val();
                var onusstatus = $('#onusstatus').val();
                var cardassociation = $('#cardassociation').val();
                var status = $('#status').val();

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        bincode: bincode,
                        description: description,
                        onusstatus: onusstatus,
                        cardassociation: cardassociation,
                        status: status,
                        search: true
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
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
                    <s:set var="vsearch"><s:property value="vsearch" default="true"/></s:set>
                        <!-- Form -->
                        <div class="content-form">
                        <s:form id="binadd" method="post" action="Bin" theme="simple" >

                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">BIN Number </label>
                                        <s:textfield cssClass="form-control" name="bincode" id="bincode" maxLength="6" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Description </label>
                                        <s:textfield  cssClass="form-control" name="description" id="description" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">On Us Status </label>
                                        <s:select  cssClass="form-control" id="onusstatus" list="%{onusstatusList}"  name="onusstatus" headerKey="" headerValue="--Select On Us Status--" listKey="statuscode" listValue="description" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Card Association </label>
                                        <s:select  cssClass="form-control" id="cardassociation" list="%{cardassociationList}"  name="cardassociation" headerKey="" headerValue="--Select Card Association--" listKey="cardassociationcode" listValue="description" />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Status </label>
                                        <s:select  cssClass="form-control" id="status" list="%{statusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                                    </div>
                                </div>
                            </div>
                        </s:form>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" id="search"  disabled="#vsearch" onclick="searchBin()"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                            <div class="col-sm-3"></div>
                            <div class="col-sm-3 text-right">
                                <s:url var="addurlLink" action="viewpopupBin"/>
                                <div class="form-group">
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurlLink}"
                                        buttonIcon="ui-icon-newwin"
                                        disabled="#vadd"
                                        value="Add BIN"
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
                        'OK':function() { deleteBin($(this).data('keyval'));$( this ).dialog( 'close' ); },
                        'Cancel':function() { $( this ).dialog( 'close' );} 
                        }" 
                        autoOpen="false" 
                        modal="true" 
                        title="Delete BIN"                            
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
                    <sj:dialog 
                        id="editerrordialog" 
                        buttons="{
                        'OK':function() { $( this ).dialog( 'close' );}                                    
                        }" 
                        autoOpen="false" 
                        modal="true" 
                        title="Edit error."
                        />   
                    <sj:dialog                                     
                        id="remotedialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        title="Add BIN"                            
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
                        title="Update BIN"
                        onOpenTopics="openviewbintopage" 
                        loadingText="Loading .."
                        width="1000"
                        height="500"
                        cssStyle="overflow: visible;"
                        dialogClass= "dialogclass"
                        /> 
                    <div class="content-table">
                        <!--this div for triangles: don't delete-->
                        <div></div>
                        <s:url var="listurl" action="listBin"/>
                        <sjg:grid
                            id="gridtable"
                            caption="BIN Management"
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
                            <sjg:gridColumn name="bincode" index="u.bin" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                            <sjg:gridColumn name="bincode" index="u.bin" title="Delete" width="40" align="center" formatter="deleteformatter" hidden="#vdelete"/> 
                            <sjg:gridColumn name="bincode" index="u.bin" title="BIN Number"  sortable="true"/>
                            <sjg:gridColumn name="description" index="u.description" title="Description"  sortable="true"/>
                            <sjg:gridColumn name="onusstatus" index="u.ipgstatusByOnusstatus.description" title="On Us Status"  sortable="true"/>
                            <sjg:gridColumn name="cardassociation" index="u.ipgcardassociation.description" title="Card Association"  sortable="true"/>
                            <sjg:gridColumn name="status" index="u.ipgstatusByStatus.description" title="Status"  sortable="true"/> 
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
