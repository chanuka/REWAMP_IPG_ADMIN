<%-- 
    Document   : cardassociationedit
    Created on : Jul 6, 2018, 4:50:43 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            
             function editCardAssociation(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findCardAssociation.ipg',
                    data: {
                        cardAssociationCode: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {
                            $("#vimagecid").attr("checked", false);
                            $("#imagecid").attr("checked", false);
                            $('#ecardAssociationCode').val(data.cardAssociationCode);
                            $('#ecardAssociationCode').attr('readOnly', true);
                            $('#edescription').val(data.description);
                            $("#everifieldImageURL").val(data.everifieldImageURL);
                            $('#ecardImageURL').val(data.ecardImageURL);
                            $("#everifieldImageURL").attr('disabled', true);
                            $('#ecardImageURL').attr('disabled', true);
//                            $(".hideme").show();
                            // document.getElementById("verifieldImageURL").value(data.verifieldImageURLFileName);
                            // document.getElementById("cardImageURL").value(data.cardImageURLFileName);

                            //$('#verifieldImageURL').val(data.verifieldImageURLFileName);
                            // $('#cardImageURL').val(data.cardImageURLFileName);
                            $('#esortKey').val(data.sortKey);
                        }
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            function cancelUpateData() {
                var id = $('#ecardAssociationCode').val();
                editCardAssociation(id);
            }

            function isNumber(evt) {
                evt = (evt) ? evt : window.event;
                var charCode = (evt.which) ? evt.which : evt.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                return true;
            }

            function alpha(e) {
                var k;
                document.all ? k = e.keyCode : k = e.which;
                return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57) || (k == 13));
            }

        </script>

    </head>
    <body >
        <div class="content-message">
            <s:div id="divmsgupdate">
                <s:actionerror theme="jquery"/>
                <s:actionmessage theme="jquery"/>
            </s:div>
        </div>
        
        <div class="content-form">
            <s:form id="cardassociationedit" method="post" action="CardAssociation"  theme="simple" cssClass="form" enctype="multipart/form-data"> 
                <%--<s:hidden name="oldvalue" id="oldvalue"/>--%>
            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Card Association Code <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" name="cardAssociationCode" id="ecardAssociationCode"
                                     maxLength="16"
                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                     readonly="true"/>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Description <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" name="description" id="edescription"
                                     maxLength="64"
                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Verified Image URL <span style="color: red">*</span></label>
                        <s:file name="verifieldImageURL" id="everifieldImageURL" cssClass="choosefileClass" disabled="true" />
                        <s:checkbox name="vimagecid" id="vimagecid" onclick="enablefile1()" />
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Card Image URL <span style="color: red">*</span></label>
                        <s:file name="cardImageURL" id="ecardImageURL" cssClass="choosefileClass" disabled="true"/>
                        <s:checkbox name="imagecid" id="imagecid" onclick="enablefile2()" />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label" >Sort Key <span style="color: red">*</span></label>
                        <s:textfield cssClass="form-control" name="sortKey" id="esortKey" maxLength="3"
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
                <s:url var="updateurl" action="updateCardAssociation"/>
                <sj:submit button="true" href="%{updateurl}" value="Update" targets="divmsgupdate" disabled="#vupdatebutt" id="updateButton" />
            </div>

            <div class="row">
                <div class="col-md-12 text-right">
                    <div class="form-group">
                        <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="cancelUpateData()" />
                        <sj:submit cssClass="btn btn-sm btn-functions" button="true" id="triupdateButton" onclick="validateUpdateIpgCardAssociation()"  value="Update" />
                    </div>
                </div>
            </div>
            </s:form>        
        </div>
    </body>
</html>
