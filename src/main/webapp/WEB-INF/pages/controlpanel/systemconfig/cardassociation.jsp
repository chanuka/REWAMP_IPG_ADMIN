<%-- 
    Document   : cardassociation
    Created on : 31/10/2013, 11:47:30 AM
    Author     : ruwan_e
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf"%>
        <%@include file="/WEB-INF/pages/controlpanel/merchantmanagement/ValidateUserInputScript.jsp" %>
        <script type="text/javascript">

            function hidecheckbox() {
                $(".hideme").hide();
            }


            function enablefile1() {
                var status = $("#vimagecid").is(":checked");
                if (status) {
                    $("#verifieldImageURL").attr('disabled', false);
                } else {
                    $("#verifieldImageURL").attr('disabled', true);
                }
            }

            function enablefile2() {
                var status = $("#imagecid").is(":checked");
                if (status) {
                    $('#cardImageURL').attr('disabled', false);
                } else {
                    $('#cardImageURL').attr('disabled', true);
                }
            }

            function editformatter(cellvalue) {
                a = $("#triaddButton").is(':disabled');
                u = $("#triupdateButton").is(':disabled');
                return "<a href='#' title='Edit' onClick='javascript:editCardAssociation(&#34;"
                        + cellvalue
                        + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function deleteformatter(cellvalue) {
                return "<a href='#' title='Delete' onClick='javascript:deleteCardAssociationInit(&#34;"
                        + cellvalue
                        + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function editCardAssociation(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findCardAssociation.ipg',
                    data: {
                        cardAssociationCode: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {
                            $('#cardAssociationCode').val(data.cardAssociationCode);
                            $('#cardAssociationCode').attr('readOnly', true);
                            $('#description').val(data.description);
                            $("#verifieldImageURL").attr('disabled', true);
                            $('#cardImageURL').attr('disabled', true);
                            $(".hideme").show();
                            // document.getElementById("verifieldImageURL").value(data.verifieldImageURLFileName);
                            // document.getElementById("cardImageURL").value(data.cardImageURLFileName);

                            //$('#verifieldImageURL').val(data.verifieldImageURLFileName);
                            // $('#cardImageURL').val(data.cardImageURLFileName);
                            $('#sortKey').val(data.sortKey);
                            $('#triaddButton').button("disable");
                            $('#updateButton').button("enable");
                            $('#triupdateButton').button("enable");
                        }
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function deleteCardAssociationInit(keyval) {
                $('#divmsg').empty();
                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete Card Association ' + keyval + ' ?');
                return false;
            }

            function deleteCardAssociation(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteCardAssociation.ipg',
                    data: {
                        cardAssociationCode: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
                    },
                    error: function (data) {
                        $("#deleteerrordialog").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function resetAllData() {
                a = $("#triaddButton").is(':disabled');
                u = $("#triupdateButton").is(':disabled');
                $("#vimagecid").attr("checked", false);
                $("#imagecid").attr("checked", false);
                if (a == true && u == true) {
                    $('#cardAssociationCode').val("");
                    $('#cardAssociationCode').attr('readOnly', false);
                    $('#description').val("");
                    $('#verifieldImageURL').val("");
                    $('#cardImageURL').val("");
                    $('#sortKey').val("");
                    $('#divmsg').text("");
                    $('#triaddButton').button("disable");
                    $('#triupdateButton').button("disable");
                } else if (a == true && u == false) {
                    var cardAssociationCode = $('#cardAssociationCode').val();
                    $('#cardAssociationCode').attr('readOnly', true);
                    editCardAssociation(cardAssociationCode);
                } else if (a == false && u == true) {
                    $('#cardAssociationCode').val("");
                    $('#cardAssociationCode').attr('readOnly', false);
                    $('#description').val("");
                    $('#verifieldImageURL').val("");
                    $('#cardImageURL').val("");
                    $('#sortKey').val("");
                    $('#divmsg').text("");
                    $('#triaddButton').button("enable");
                    $('#triupdateButton').button("disable");
                }
            }

            function resetFieldData() {
                var startStatus = '<s:property value="vadd" />'

                $("#vimagecid").attr("checked", false);
                $("#imagecid").attr("checked", false);
                $('#cardAssociationCode').val("");
                $('#cardAssociationCode').attr('readOnly', false);
                $('#description').val("");
                $('#verifieldImageURL').val("");
                $('#cardImageURL').val("");
                $('#sortKey').val("");
                if (a == true && u == true) {
                    $('#triaddButton').button("disable");
                    $('#triupdateButton').button("disable");
                } else if (a == true && u == false && startStatus == 'false') {
                    $('#triaddButton').button("enable");
                    $('#triupdateButton').button("disable");
                } else if (a == true && u == false && startStatus == 'true') {
                    $('#triaddButton').button("disable");
                    $('#triupdateButton').button("disable");
                } else if (a == false && u == true) {
                    $('#triaddButton').button("enable");
                    $('#triupdateButton').button("disable");
                }
                jQuery("#gridtable").trigger("reloadGrid");
            }
        </script>
    </head>

    <body onload="hidecheckbox()">

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

                    <s:set id="vadd" var="vadd">
                        <s:property value="vadd" default="true" />
                    </s:set>
                    <s:set var="vupdatebutt">
                        <s:property value="vupdatebutt" default="true" />
                    </s:set>
                    <s:set var="vupdatelink">
                        <s:property value="vupdatelink" default="true" />
                    </s:set>
                    <s:set var="vdelete">
                        <s:property value="vdelete" default="true" />
                    </s:set>

                    <!-- Form -->
                    <div class="content-form">
                        <s:form id="pageadd" method="post" action="MPITask" theme="simple" enctype="multipart/form-data" >

                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Card Association Code <span style="color: red">*</span></label>
                                        <s:textfield cssClass="form-control" name="cardAssociationCode" id="cardAssociationCode"
                                                     maxLength="16"
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Description <span style="color: red">*</span></label>
                                        <s:textfield cssClass="form-control" name="description" id="description"
                                                     maxLength="64"
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Verified Image URL <span style="color: red">*</span></label>
                                        <s:file name="verifieldImageURL" id="verifieldImageURL" cssClass="choosefileClass" />
                                        <s:checkbox name="vimagecid" id="vimagecid" onclick="enablefile1()" cssClass="hideme"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Card Image URL <span style="color: red">*</span></label>
                                        <s:file name="cardImageURL" id="cardImageURL" cssClass="choosefileClass" />
                                        <s:checkbox name="imagecid" id="imagecid" onclick="enablefile2()" cssClass="hideme"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Sort Key <span style="color: red">*</span></label>
                                        <s:textfield cssClass="form-control" name="sortKey" id="sortKey" maxLength="3"
                                                     onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                                </div>
                            </div> 




                            <div style="display: none; visibility: hidden;"> 
                                <s:url var="addurl" action="addCardAssociation"/>
                                <s:url var="updateurl" action="updateCardAssociation"/>
                                <sj:submit button="true" href="%{addurl}" value="Add" targets="divmsg" id="addButton" disabled="#vadd" /> 
                                <sj:submit button="true" href="%{updateurl}" value="Update" targets="divmsg" disabled="#vupdatebutt" id="updateButton" />
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <sj:submit cssClass="btn btn-sm btn-functions" button="true" id="triaddButton" onclick="validateAddIpgCardAssociation()" disabled="#vadd" value="Add" />
                                        <sj:submit cssClass="btn btn-sm btn-functions" button="true" id="triupdateButton" onclick="validateUpdateIpgCardAssociation()" disabled="#vupdatebutt" value="Update" />
                                        <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                    </div>
                                </div>
                            </div>

                        </s:form>
                    </div>

                    <!-- Start delete confirm dialog box -->
                    <sj:dialog id="deletedialog"
                               buttons="{ 
                               'OK':function() { deleteCardAssociation($(this).data('keyval'));$( this ).dialog( 'close' ); },
                               'Cancel':function() { $( this ).dialog( 'close' );} 
                               }"
                               autoOpen="false" modal="true" title="Delete Task" />
                    <!-- Start delete process dialog box -->
                    <sj:dialog id="deletesuccdialog"
                               buttons="{
                               'OK':function() { $( this ).dialog( 'close' );}
                               }"
                               autoOpen="false" modal="true" title="Deleting Process." />
                    <!-- Start delete error dialog box -->
                    <sj:dialog id="deleteerrordialog"
                               buttons="{
                               'OK':function() { $( this ).dialog( 'close' );}                                    
                               }"
                               autoOpen="false" modal="true" title="Delete error." />

                    <div class="content-table">
                        <!--this div for triangles: don't delete-->
                        <div></div>
                        <s:url var="listurl" action="listCardAssociation" />
                        <sjg:grid id="gridtable" caption="Card Association Management" dataType="json"
                                  href="%{listurl}" pager="true" gridModel="gridModel"
                                  rowList="10,15,20" rowNum="10" autowidth="true" rownumbers="true"
                                  onCompleteTopics="completetopics" rowTotal="false"
                                  viewrecords="true">
                            <sjg:gridColumn name="cardAssociationCode" index="cardassociationcode" title="Card Association Code"
                                            sortable="true" />
                            <sjg:gridColumn name="description" index="description"
                                            title="Description" sortable="true" />
                            <sjg:gridColumn name="verifieldImageURL" index="verifiedimageurl" title="Verifield Image URL" sortable="true" />
                            <sjg:gridColumn name="cardImageURL" index="cardimageurl"
                                            title="Card Image URL" sortable="true" />
                            <sjg:gridColumn name="sortKey" index="sortkey" title="Sort Key"
                                            sortable="true" />

                            <sjg:gridColumn name="cardAssociationCode" index="cardassociationcode" title="Edit"
                                            width="25" align="center" formatter="editformatter"
                                            hidden="#vupdatelink" />
                            <sjg:gridColumn name="cardAssociationCode" index="cardassociationcode" title="Delete"
                                            width="40" align="center" formatter="deleteformatter"
                                            hidden="#vdelete" />
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
