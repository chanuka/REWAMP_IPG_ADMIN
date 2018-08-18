<%-- 
    Document   : merchnatterminal
    Created on : Jul 20, 2018, 1:36:25 PM
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

            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Delete' onClick='javascript:deleteTerminalInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function deleteTerminalInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete merchant terminal ' + keyval + ' ?');
                return false;
            }

            function deleteTerminal(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/DeleteMerchantTerminal.ipg',
                    data: {delId: keyval},
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



            function resetAllData() {

                $('#tid').val("");
                $('#merchantId').val("");
                $('#currencyCode').val("");
                $('#status').val("");
                $('#divmsg').text("");
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        tid: '',
                        merchantId: '',
                        currencyCode: '',
                        status: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            }

            function resetFieldData() {

                $('#itid').val("");
                $('#imerchantId').val("");
                $('#icurrencyCode').val("");
                $('#istatus').val("");

                jQuery("#gridtable").trigger("reloadGrid");
            }

            function searchMerchantTerminal() {
                $('#divmsg').text("");
                var tid = $('#tid').val();
                var merchantId = $('#merchantId').val();
                var currencyCode = $('#currencyCode').val();
                var status = $('#status').val();

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        tid: tid,
                        merchantId: merchantId,
                        currencyCode: currencyCode,
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
                    <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>
                    <s:set var="vsearch"><s:property value="vsearch" default="true"/></s:set>
                        <!-- Form -->
                        <div class="content-form">
                        <s:form id="merchantterminal" method="post" action="MerchantTerminal" theme="simple" >


                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Merchant ID </label>
                                        <s:select cssClass="form-control" id="merchantId" list="%{merchantList}"  name="merchantId" headerKey="" headerValue="--Select--" listKey="merchantid" listValue="merchantid" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Currency </label>
                                        <s:select cssClass="form-control" id="currencyCode" list="%{currencyMap}"  name="currencyCode" headerKey="" headerValue="--Select--" listKey="key" listValue="value" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Terminal ID </label>
                                        <s:textfield cssClass="form-control" name="tid" id="tid" maxLength="8" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
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
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search"  id="search"  disabled="#vsearch" onclick="searchMerchantTerminal()"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                            <div class="col-sm-3"></div>
                            <div class="col-sm-3 text-right">
                                <s:url var="addurlLink" action="viewpopupMerchantTerminal"/>
                                <div class="form-group">
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurlLink}"
                                        buttonIcon="ui-icon-newwin"
                                        disabled="#vadd"
                                        value="Add Merchant Terminal"
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
                        'OK':function() { deleteTerminal($(this).data('keyval'));$( this ).dialog( 'close' ); },
                        'Cancel':function() { $( this ).dialog( 'close' );} 
                        }" 
                        autoOpen="false" 
                        modal="true" 
                        title="Delete Merchant Terminal"                            
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
                        id="remotedialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        title="Add Merchant Terminal"                            
                        loadingText="Loading .."                            
                        position="center"                            
                        width="1000"
                        height="500"
                        dialogClass= "dialogclass"
                        cssStyle="overflow: visible;"
                        />
                    <div class="content-table">
                        <!--this div for triangles: don't delete-->
                        <div></div>
                        <s:url var="listurl" action="ListMerchantTerminal"/>
                        <sjg:grid
                            id="gridtable"
                            caption="Merchant Terminal"
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
                            <sjg:gridColumn name="delId" index="" title="Delete" width="40" align="center" formatter="deleteformatter" hidden="#vdelete"/>  
                            <sjg:gridColumn name="merchantId" index="u.Ipgmerchant.merchantid" title="Merchant ID"  sortable="true"/>
                            <sjg:gridColumn name="currencyCode" index="u.Ipgcurrency.description" title="Currency"  sortable="true"/>
                            <sjg:gridColumn name="tid" index="u.terminalid" title="Terminal ID"  sortable="true"/>
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