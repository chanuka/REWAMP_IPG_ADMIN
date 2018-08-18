<%-- 
    Document   : merchantriskprofile
    Created on : Nov 5, 2013, 11:46:45 AM
    Author     : chalitha
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
                return "<a href='#' title='Edit' onClick='javascript:editMerchantRiskProfile(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function deleteformatter(cellvalue) {
                return "<a href='#' title='Delete' onClick='javascript:deleteMerchantRiskProfileInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }


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
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        jQuery("#gridtable").trigger("reloadGrid");
                    },
                    error: function (data) {
                        $("#deleteerrordialog").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
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
                    <s:form id="MerchantRiskProfile" method="post" action="viewaddprofileMerchantRiskProfile" theme="simple" >

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" value="Add" id="addButton"  disabled="#vadd"/>
                                    <sj:submit cssClass="btn btn-sm btn-functions" cssStyle="visibility:hidden;" />
                                </div>
                            </div>
                        </div>
                        <div class="row" style="display: none; visibility: hidden;">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <s:url var="upurl" action="findMerchantRiskProfile" />
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{upurl}" id="upbut" />
                                </div>
                            </div>
                        </div>
                    </s:form>

                    <!-- Form -->
                    <s:form id="UpdateMerchantRiskProfile" method="post" action="findMerchantRiskProfile" theme="simple" >

                        <s:hidden id="riskprofilecode" name="riskprofilecode" />
                        <div class="row" style="display: none; visibility: hidden;">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <s:url var="upurl" action="findMerchantRiskProfile" />
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{upurl}" id="upbut" />
                                </div>
                            </div>
                        </div>
                    </s:form>
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
                        <sjg:gridColumn name="riskprofilecode" index="u.riskprofilecode" title="Risk Profile Code"  sortable="true"/>
                        <sjg:gridColumn name="description" index="u.description" title="Description"  sortable="true"/>
                        <sjg:gridColumn name="status" index="u.ipgstatus.description" title="Status"  sortable="true"/> 
                        <sjg:gridColumn name="maxsingletxnlimit" index="u.maximumsingletxnlimit" title="Max Single Txn Limit"  sortable="true"/>
                        <sjg:gridColumn name="minsingletxnlimit" index="u.minimumsingletxnlimit" title="Min Single Txn Limit"  sortable="true"/>
                        <sjg:gridColumn name="maxtxncount" index="u.maximumtxncount" title="Max Daily Txn Count"  sortable="true"/>
                        <sjg:gridColumn name="maxtxnamount" index="u.maximumtotaltxnamount" title="Max Daily Txn Amount"  sortable="true"/>
                        <sjg:gridColumn name="maxpasswordcount" index="u.maximumpasswordcount" title="Max Password Count"  sortable="true"/>
                        <sjg:gridColumn name="riskprofilecode" index="u.riskprofilecode" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                        <sjg:gridColumn name="riskprofilecode" index="u.riskprofilecode" title="Delete" width="40" align="center" formatter="deleteformatter" hidden="#vdelete"/>  
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
