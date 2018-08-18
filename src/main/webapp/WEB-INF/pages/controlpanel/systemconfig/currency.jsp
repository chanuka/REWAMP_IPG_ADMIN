<%--  
        @created on :Oct 31, 2013, 10:05:42 AM
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
                return "<a href='#' title='Edit' onClick='javascript:editCurrency(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }


            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Delete' onClick='javascript:deleteCurrencyInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function editCurrency(keyval) {
                if (keyval === "null") {
                    keyval = "";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/findCurrency.ipg',
                    data: {currencyISOCode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {

                            $('#currencyISOCode').val(data.currencyISOCode);
                            $('#currencyISOCode').attr('readOnly', true);
                            $('#currencyCode').val(data.currencyCode);
                            $('#description').val(data.description);
                            $('#sortKey').val(data.sortKey);
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

            function deleteCurrencyInit(keyval) {
                $('#divmsg').empty();
                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete Currency ' + keyval + ' ?');
                return false;
            }

            function deleteCurrency(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteCurrency.ipg',
                    data: {currencyISOCode: keyval},
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
                    $('#currencyISOCode').val("");
                    $('#currencyISOCode').attr('readOnly', false);
                    $('#currencyCode').val("");
                    $('#description').val("");
                    $('#sortKey').val("");
                    $('#divmsg').text("");
                    $('#addButton').button("enable");
                    $('#updateButton').button("disable");
                } else {
                    var currencyisocode = $('#currencyISOCode').val();
                    editCurrency(currencyisocode);
                }
            }

            function resetFieldData() {

                $('#currencyISOCode').val("");
                $('#currencyISOCode').attr('readOnly', false);
                $('#currencyCode').val("");
                $('#description').val("");
                $('#sortKey').val("");
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
                    <s:form id="currencyadd" method="post" action="addCurrency" theme="simple" >

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Currency ISO Code <span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="currencyISOCode" id="currencyISOCode" maxLength="8" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Currency Code <span style="color: red">*</span></label>
                                    <s:textfield  cssClass="form-control" id="currencyCode"   name="currencyCode" maxLength="8" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))"  />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">Description <span style="color: red">*</span></label>
                                    <s:textfield  cssClass="form-control" id="description"   name="description" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label">sort Key <span style="color: red">*</span></label>
                                    <s:textfield  cssClass="form-control" name="sortKey" id="sortKey" maxLength="3" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                            </div>
                        </div>   
                                
                        <s:url var="addurl" action="addCurrency"/>
                        <s:url var="updateurl" action="updateCurrency"/>
                        
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
                    'OK':function() { deleteCurrency($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Delete Currency"                            
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
                    <s:url var="listurl" action="listCurrency"/>
                    <sjg:grid
                        id="gridtable"
                        caption="IPG Currency"
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
                        <sjg:gridColumn name="currencyisocode" index="u.currencyisocode" title="Currency ISO Code"  sortable="true"/>
                        <sjg:gridColumn name="currencycode" index="u.currencycode" title="Currency Code"  sortable="true"/>
                        <sjg:gridColumn name="description" index="u.description" title="Description"  sortable="true"/>
                        <sjg:gridColumn name="sortkey" index="u.sortkey" title="Sort Key"  sortable="true"/>
                        <sjg:gridColumn name="currencyisocode" index="u.currencyisocode" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                        <sjg:gridColumn name="currencyisocode" index="u.currencyisocode" title="Delete" width="25" align="center" formatter="deleteformatter" hidden="#vdelete"/>
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
