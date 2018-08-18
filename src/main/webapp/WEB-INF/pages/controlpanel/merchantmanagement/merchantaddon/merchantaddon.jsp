<%-- 
    Document   : merchantaddon
    Created on : Jul 19, 2018, 4:01:42 PM
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

        <%@include file="/WEB-INF/pages/controlpanel/merchantmanagement/ValidateUserInputScript.jsp" %>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jscolor/jscolor.js"></script>  

        <script type="text/javascript">

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Edit' onClick='javascript:editMerchantAddonInit(&#34;" + rowObject.merchantId + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }


            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Delete' onClick='javascript:deleteMerchantAddonInit(&#34;" + rowObject.merchantId + "&#34;&#44;&#34;" + rowObject.merchantname + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function editMerchantAddonInit(keyval) {
                $("#updatedialog").data('merchantID', keyval).dialog('open');
            }

            $.subscribe('openviewmerchantaddontopage', function(event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailMerchantAddon.ipg?merchantID=" + $led.data('merchantID'));
            });



            function deleteMerchantAddonInit(merID, mername) {
                $("#deletedialog").data('Merchantid', merID).dialog('open');

                $("#deletedialog").html('Are you sure you want to delete Merchant Name :' + mername + ' ?');
                return false;
            }

            function deleteMerchantAddon(merID) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteMerchantAddon.ipg',
                    data: {merchantID: merID},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);

                        jQuery("#gridtable").trigger("reloadGrid");
                    },
                    error: function(data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');

                    }
                });
            }

            function resetAllData() {
                $('#merchantNameID').val("");
                $('#cordinateX').val("");
                $('#cordinateY').val("");
                $('#displayText').val("");
                $('#remarks').val("");
                $('#themeColor').val("");
                $('#divmsg').text("");
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        merchantNameID: '',
                        cordinateX: '',
                        cordinateY: '',
                        displayText: '',
                        remarks: '',
                        themeColor: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {

                $("#iimageid").attr("checked", false);
                $('#imerchantID').val("");
                $('#imerchantNameID').val("");
                $('#iimage').val("");
                $('#icordinateX').val("");
                $('#icordinateY').val("");
                $('#idisplayText').val("");
                var color = document.getElementById("ithemeColor");
                if(color){
                    document.getElementById('ithemeColor').color.fromString('FFFFFF');
                }
                $('#ifontFamily').val("");
                $('#ifontStyle').val("");
                $('#ifontSize').val("");
                $('#iremarks').val("");

                jQuery("#gridtable").trigger("reloadGrid");

            }
            function searchMerchantAddon() {
                $('#divmsg').text("");
                var merchantNameID = $('#merchantNameID').val();
                var cordinateX = $('#cordinateX').val();
                var cordinateY = $('#cordinateY').val();
                var displayText = $('#displayText').val();
                var remarks = $('#remarks').val();
                var themeColor = $('#themeColor').val();

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        merchantNameID: merchantNameID,
                        cordinateX: cordinateX,
                        cordinateY: cordinateY,
                        displayText: displayText,
                        remarks: remarks,
                        themeColor: themeColor,
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

                    <s:set var="vupdatelink"><s:property value="vupdatelink" default="true"/></s:set>
                    <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>
                    <s:set var="vsearch"><s:property value="vsearch" default="true"/></s:set>
                        <!-- Form -->
                        <div class="content-form">
                        <s:form id="merchantaddon" method="post" action="MerchantAddon" theme="simple" >
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Merchant ID </label>
                                        <s:select cssClass="form-control" id="merchantNameID" list="%{merchantList}"  name="merchantNameID" headerKey="" headerValue="--Select Merchant Name--"  listKey="merchantid" listValue="merchantid" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >X Cordinate</label>
                                        <s:textfield cssClass="form-control" id="cordinateX" name="cordinateX" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Y Cordinate</label>
                                        <s:textfield cssClass="form-control" id="cordinateY" name="cordinateY" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Theme Color </label>
                                        <s:textfield cssClass="form-control" id="themeColor" name="themeColor" />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Display Text </label>
                                        <s:textfield cssClass="form-control" id="displayText" name="displayText" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Remarks </label>
                                        <s:textfield cssClass="form-control" id="remarks" name="remarks" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                    </div>
                                </div>
                            </div>
                            <s:hidden id="merchantID" name="merchantID" /> 
                        </s:form>


                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" id="search"  disabled="#vsearch" onclick="searchMerchantAddon()"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                            <div class="col-sm-3"></div>
                            <div class="col-sm-3 text-right">
                                <s:url var="addurlLink" action="viewpopupMerchantAddon"/>
                                <div class="form-group">
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurlLink}"
                                        buttonIcon="ui-icon-newwin"
                                        disabled="#vadd"
                                        value="Add Merchant Add On"
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
                        'OK':function() { deleteMerchantAddon($(this).data('Merchantid'));$( this ).dialog( 'close' ); },
                        'Cancel':function() { $( this ).dialog( 'close' );} 
                        }" 
                        autoOpen="false" 
                        modal="true" 
                        title="Delete Merchant Add On"                            
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

                    <sj:dialog                                     
                        id="remotedialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        title="Add Merchant Add On"                            
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
                        title="Update Merchant Add On"
                        onOpenTopics="openviewmerchantaddontopage" 
                        loadingText="Loading .."
                        width="1000"
                        height="500"
                        cssStyle="overflow: visible;"
                        dialogClass= "dialogclass"
                        /> 
                    <div class="content-table">
                        <!--this div for triangles: don't delete-->
                        <div></div>
                        <s:url var="listurl" action="listMerchantAddon"/>
                        <sjg:grid
                            id="gridtable"
                            caption="IPG Merchant Add-on"
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
                            <sjg:gridColumn name="merchantId" index="u.merchantid" title="Edit" width="40" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                            <sjg:gridColumn name="merchantId" index="u.merchantid" title="Delete" width="50" align="center" formatter="deleteformatter" hidden="#vdelete"/>
                            <sjg:gridColumn name="merchantId" index="u.ipgmerchant.merchantid" title="Merchant ID"  sortable="true"/>
                            <sjg:gridColumn name="merchantname" index="u.ipgmerchant.merchantname" title="Merchant Name"  sortable="true"/>
                            <sjg:gridColumn name="logopath" index="u.logopath" title="Logo Path"  sortable="true"/>
                            <sjg:gridColumn name="xcordinate" index="u.xcordinate" title="X Cordinate"  sortable="true"/>                            
                            <sjg:gridColumn name="ycordinate" index="u.ycordinate" title="Y Cordinate"  sortable="true"/>   
                            <sjg:gridColumn name="displaytext" index="u.displaytext" title="Tag Line"  sortable="true"/>   
                            <sjg:gridColumn name="themecolor" index="u.themecolor" title="Theme Color"  sortable="true"/>   
                            <sjg:gridColumn name="remarks" index="u.remarks" title="Remarks"  sortable="true"/>
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