<%-- 
    Document   : merchantriskprofile
    Created on : Jul 13, 2018, 11:32:46 AM
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
                return "<a href='#' title='Edit' onClick='javascript:editMerchantRiskProfileInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function deleteformatter(cellvalue) {
                return "<a href='#' title='Delete' onClick='javascript:deleteMerchantRiskProfileInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function editMerchantRiskProfileInit(keyval) {
                $("#updatedialog").data('riskprofilecode', keyval).dialog('open');
            }

            $.subscribe('openviewmerchantriskprofiletopage', function(event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailMerchantRiskProfile.ipg?riskprofilecode=" + $led.data('riskprofilecode'));
            });

            function editMerchantRiskProfile(keyval) {


                $('#riskprofilecode').val(keyval);
                $('#upbut').click();
            }

            function deleteMerchantRiskProfileInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete Merchant Risk Profile ' + keyval + ' ?');
                return false;
            }

            function deleteMerchantRiskProfile(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteMerchantRiskProfile.ipg',
                    data: {riskprofilecode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        jQuery("#gridtable").trigger("reloadGrid");
                    },
                    error: function(data) {
                        $("#deleteerrordialog").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            function resetAllData() {
                $('#riskprofilecode').val("");
                $('#description').val("");
                $('#status').val("");
                $('#maxsingletxnlimit').val("");
                $('#minsingletxnlimit').val("");
                $('#maxtxncount').val("");
                $('#maxtxnamount').val("");
                $('#maxpasswordcount').val("");
                $('#divmsg').text("");
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        riskprofilecode: '',
                        description: '',
                        status: '',
                        maxsingletxnlimit: '',
                        minsingletxnlimit: '',
                        maxtxncount: '',
                        maxtxnamount: '',
                        maxpasswordcount: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {
                $('#iriskprofilecode').val("");
                $('#idescription').val("");
                $('#istatus').val("");
                $('#imaxsingletxnlimit').val("");
                $('#iminsingletxnlimit').val("");
                $('#imaxtxncount').val("");
                $('#imaxtxnamount').val("");
                $('#imaxpasswordcount').val("");
                toleftallinsert('country');
                toleftallinsert('categories');
                toleftallinsert('bin');
                toleftallinsert('currencytypes');

                jQuery("#gridtable").trigger("reloadGrid");

            }

            function toleftallinsert(val) {
                $("#iblocked" + val + " option").each(function() {

                    $("#iallowed" + val).append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }

            function searchMerchantRiskProfile() {
                $('#divmsg').text("");
                var riskprofilecode = $('#riskprofilecode').val();
                var description = $('#description').val();
                var status = $('#status').val();
                var maxsingletxnlimit = $('#maxsingletxnlimit').val();
                var minsingletxnlimit = $('#minsingletxnlimit').val();
                var maxtxncount = $('#maxtxncount').val();
                var maxtxnamount = $('#maxtxnamount').val();
                var maxpasswordcount = $('#maxpasswordcount').val();



                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        riskprofilecode: riskprofilecode,
                        description: description,
                        status: status,
                        maxsingletxnlimit: maxsingletxnlimit,
                        minsingletxnlimit: minsingletxnlimit,
                        maxtxncount: maxtxncount,
                        maxtxnamount: maxtxnamount,
                        maxpasswordcount: maxpasswordcount,
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
                        <s:form id="merchantriskprofile" method="post" action="MerchantRiskProfile" theme="simple" >
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Risk Profile Code </label>
                                        <s:textfield cssClass="form-control" name="riskprofilecode" id="riskprofilecode" 
                                                     maxLength="8" 
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Description </label>
                                        <s:textfield cssClass="form-control" name="description" id="description" 
                                                     maxLength="64" 
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Status </label>
                                        <s:select id="status" list="statusList" cssClass="form-control"
                                                  name="status" headerKey=""
                                                  headerValue="--Select Status--" listKey="statuscode"
                                                  listValue="description"  />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Maximum Single Transaction Limit </label>
                                        <s:textfield cssClass="form-control" name="maxsingletxnlimit" id="maxsingletxnlimit" 
                                                     maxLength="8" 
                                                     onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                                     onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Minimum Single Transaction Limit </label>
                                        <s:textfield cssClass="form-control" name="minsingletxnlimit" id="minsingletxnlimit" 
                                                     maxLength="8" 
                                                     onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                                     onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Maximum Transaction Count </label>
                                        <s:textfield cssClass="form-control" name="maxtxncount" id="maxtxncount" 
                                                     maxLength="4" 
                                                     onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                                     onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Maximum Total Transaction Amount</label>
                                        <s:textfield cssClass="form-control" name="maxtxnamount" id="maxtxnamount" 
                                                     maxLength="8" 
                                                     onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                                     onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Maximum Password Count</label>
                                        <s:textfield cssClass="form-control" name="maxpasswordcount" id="maxpasswordcount" 
                                                     maxLength="2" 
                                                     onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                                     onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                    </div>
                                </div>
                            </div>

                        </s:form>

                        <!-- Form -->

                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" id="search"  disabled="#vsearch" onclick="searchMerchantRiskProfile()"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                            <div class="col-sm-3"></div>
                            <div class="col-sm-3 text-right">
                                <s:url var="addurlLink" action="viewpopupMerchantRiskProfile"/>
                                <div class="form-group">
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurlLink}"
                                        buttonIcon="ui-icon-newwin"
                                        disabled="#vadd"
                                        value="Add Merchant Risk Profile"
                                        id="addButton_new"
                                        cssClass="btn btn-sm btn-functions" 
                                        />
                                </div>
                            </div>
                        </div>


                        <!-- Start delete confirm dialog box -->
                        <sj:dialog 
                            id="deletedialog" 
                            buttons="{ 
                            'OK':function() { deleteMerchantRiskProfile($(this).data('keyval'));$( this ).dialog( 'close' ); },
                            'Cancel':function() { $( this ).dialog( 'close' );} 
                            }" 
                            autoOpen="false" 
                            modal="true" 
                            title="Delete Merchant Risk Profile"                            
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
                            title="Add Merchant Risk Profile"                            
                            loadingText="Loading .."                            
                            position="center"                            
                            width="1000"
                            height="500"
                            dialogClass= "dialogclass"

                            />
                        <sj:dialog                                     
                            id="updatedialog"                                 
                            autoOpen="false" 
                            modal="true" 
                            position="center"
                            title="Update Merchant Risk Profile"
                            onOpenTopics="openviewmerchantriskprofiletopage" 
                            loadingText="Loading .."
                            width="1000"
                            height="500"

                            dialogClass= "dialogclass"
                            /> 

                        <div class="content-table">
                            <!--this div for triangles: don't delete-->
                            <div></div>
                            <s:url var="listurl" action="listMerchantRiskProfile"/>
                            <sjg:grid
                                id="gridtable"
                                caption="Risk Profile Management"
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
                                <sjg:gridColumn name="riskprofilecode" index="u.riskprofilecode" title="Edit" width="40" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                                <sjg:gridColumn name="riskprofilecode" index="u.riskprofilecode" title="Delete" width="55" align="center" formatter="deleteformatter" hidden="#vdelete"/>  
                                <sjg:gridColumn name="riskprofilecode" index="u.riskprofilecode" title="Risk Profile Code"  sortable="true"/>
                                <sjg:gridColumn name="description" index="u.description" title="Description"  sortable="true"/>
                                <sjg:gridColumn name="status" index="u.ipgstatus.description" title="Status"  sortable="true"/> 
                                <sjg:gridColumn name="maxsingletxnlimit" index="u.maximumsingletxnlimit" title="Max Single Txn Limit"  sortable="true"/>
                                <sjg:gridColumn name="minsingletxnlimit" index="u.minimumsingletxnlimit" title="Min Single Txn Limit"  sortable="true"/>
                                <sjg:gridColumn name="maxtxncount" index="u.maximumtxncount" title="Max Daily Txn Count"  sortable="true"/>
                                <sjg:gridColumn name="maxtxnamount" index="u.maximumtotaltxnamount" title="Max Daily Txn Amount"  sortable="true"/>
                                <sjg:gridColumn name="maxpasswordcount" index="u.maximumpasswordcount" title="Max Password Count"  sortable="true"/>
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

