<%-- 
    Document   : merchantcredential
    Created on : Jul 19, 2018, 12:31:13 PM
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

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Edit' onClick='javascript:editMerchantCredentialInit(&#34;" + rowObject.merchantId + "&#34;&#44;&#34;" + rowObject.cardassociationCode + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }


            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Delete' onClick='javascript:deleteMerchantCredentialInit(&#34;" + rowObject.merchantId + "&#34;&#44;&#34;" + rowObject.merchantname + "&#34;&#44;&#34;" + rowObject.cardassociationCode + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function editMerchantCredentialInit(merID, cardAss) {
                $("#updatedialog").data('merchantID', merID).data('cardAssociation', cardAss).dialog('open');
            }

            $.subscribe('openviewmerchantcredentialtopage', function(event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailMerchantCredential.ipg?merchantID=" + $led.data('merchantID') + "&cardAssociation=" + $led.data('cardAssociation'));
            });

            function editMerchantCredential(merID, cardAss) {
                if (merID === "null") {
                    merID = "";
                }
                if (cardAss === "null") {
                    cardAss = "";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/findMerchantCredential.ipg',
                    data: {merchantID: merID, cardAssociation: cardAss},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {

                            $('#merchantID').val(data.merchantID);
                            $('#merchantID2').val(data.merchantID);
                            $('#merchantID').attr('disabled', true);
//                            $("#merchantID option").filter("option:not(:selected)").attr('disabled', true);
                            $(".hideme").hide();
                            $('#cardAssociation').val(data.cardAssociation);
                            $('#cardAssociation2').val(data.cardAssociation);
                            $('#cardAssociation').attr('disabled', true);
                            $('#userName').val(data.userName);
                            $('#password').val("");
                            $('#rePassword').val("");
                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");

                        }
                    },
                    error: function(data) {
                        $("#editerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function deleteMerchantCredentialInit(merID, mername, cardAssociation) {
                $('#divmsg').empty();
                $("#deletedialog").data('Merchantid', merID).data('cardAss', cardAssociation).dialog('open');

                $("#deletedialog").html('Are you sure you want to delete Merchant Name :' + mername + ' ?');
                return false;
            }

            function deleteMerchantCredential(merID, cardAss) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteMerchantCredential.ipg',
                    data: {merchantID: merID, cardAssociation: cardAss},
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
                        //                        jQuery("#gridtable").trigger("reloadGrid");                      
                    },
                    error: function(data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');

                    }
                });
            }

            function resetAllData() {
                $('#merchantID').val("");
                $('#cardAssociation').val("");
                $('#userName').val("");
                $('#divmsg').text("");

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        merchantID: '',
                        cardAssociation: '',
                        userName: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            }

            function resetFieldData() {
                $('#imerchantID').val("");
                $('#icardAssociation').val("");
                $('#iuserName').val("");
                $('#ipassword').val("");
                $('#irePassword').val("");

                jQuery("#gridtable").trigger("reloadGrid");
            }

            function searchMerchantCredential() {
                $('#divmsg').text("");
                var merchantID = $('#merchantID').val();
                var cardAssociation = $('#cardAssociation').val();
                var userName = $('#userName').val();

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        merchantID: merchantID,
                        cardAssociation: cardAssociation,
                        userName: userName,
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
                        <s:form id="merchantcredential" method="post" action="MerchantCredential" theme="simple" >

                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Merchant ID </label>
                                        <s:select cssClass="form-control" id="merchantID" list="%{merchantList}"  name="merchantID" headerKey="" headerValue="--Select Merchant--"  listKey="merchantid" listValue="merchantid" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Card Association </label>
                                        <s:select cssClass="form-control" id="cardAssociation" list="%{cardAssociationList}"  name="cardAssociation" headerKey="" headerValue="--Select Card Association--" listKey="cardassociationcode" listValue="description"  />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >User Name  </label>
                                        <s:textfield cssClass="form-control" id="userName" name="userName" maxLength="255" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>
                            </div>
                        </s:form>            
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" id="search"  disabled="#vsearch" onclick="searchMerchantCredential()"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                            <div class="col-sm-3"></div>
                            <div class="col-sm-3 text-right">
                                <s:url var="addurlLink" action="viewpopupMerchantCredential"/>
                                <div class="form-group">
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurlLink}"
                                        buttonIcon="ui-icon-newwin"
                                        disabled="#vadd"
                                        value="Add Merchant Credential"
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
                        'OK':function() { deleteMerchantCredential($(this).data('Merchantid'),$(this).data('cardAss'));$( this ).dialog( 'close' ); },
                        'Cancel':function() { $( this ).dialog( 'close' );} 
                        }" 
                        autoOpen="false" 
                        modal="true" 
                        title="Delete Merchant Credential"                            
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
                        title="Add Merchant Credential"                            
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
                        title="Update Merchant Credential"
                        onOpenTopics="openviewmerchantcredentialtopage" 
                        loadingText="Loading .."
                        width="1000"
                        height="500"
                        cssStyle="overflow: visible;"
                        dialogClass= "dialogclass"
                        /> 

                    <div class="content-table">
                        <!--this div for triangles: don't delete-->
                        <div></div>
                        <s:url var="listurl" action="listMerchantCredential"/>
                        <sjg:grid
                            id="gridtable"
                            caption="IPG Merchant Credential"
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
                            <sjg:gridColumn name="cardassociationCode" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                            <sjg:gridColumn name="cardassociationCode" title="Delete" width="35" align="center" formatter="deleteformatter" hidden="#vdelete"/>
                            <sjg:gridColumn name="merchantId" index="u.ipgmerchant.merchantid" title="Merchant ID"  sortable="true"/>
                            <sjg:gridColumn name="merchantname" index="u.ipgmerchant.merchantname" title="Merchant Name"  sortable="true"/>
                            <sjg:gridColumn name="cardassociation" index="u.ipgcardassociation.description" title="Card Association"  sortable="true"/>
                            <sjg:gridColumn name="username" index="u.username" title="Username"  sortable="true"/>   
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
