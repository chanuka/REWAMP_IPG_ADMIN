<%-- 
    Document   : cardassociationinsert
    Created on : Jul 6, 2018, 2:53:20 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<link rel="stylesheet" href="resouces/css/common/common_popup.css">-->

        <title>Insert System User</title>
        <script type="text/javascript">
            $(document).ready(function() {
//                $("#iexpirydate").val('${PASSWORDEXPIRYPERIOD}');
            });

            $.subscribe('resetAddButton', function(event, data) {
                $('#icardAssociationCode').val("");
                $('#idescription').val("");
                $('#iverifieldImageURL').val("");
                $('#icardImageURL').val("");
                $('#isortKey').val("");

                $("#divmsginsert").empty();
            });
            function isNumberinsert(evt) {
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

            $(document).ready(function() {
//                $.each($('.tooltip'), function (index, element) {
//                    $(this).remove();
//                });
//                $('[data-toggle="tooltip"]').tooltip({
//                    'placement': 'right'
//
//                });
            });

        </script>
        <style>
            .tooltip {

                background-color: black;
                color: #fff;
                border-radius: 6px;
                padding: 5px 0;
                white-space: pre-line;
                height: auto;
            }
            .tooltip-inner {
                min-width: 375%;
                max-width: 100%; 
                text-align: left;
            }

        </style>

    </head>
    <body  >
        <div class="content-message">
            <s:div id="divmsginsert">
                <s:actionerror theme="jquery"/>
                <s:actionmessage theme="jquery"/>
            </s:div>
        </div>
        <div class="content-form">
            <s:form id="cardassociationinsert" method="post" action="CardAssociation" theme="simple" cssClass="form" enctype="multipart/form-data">   
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Card Association Code <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="cardAssociationCode" id="icardAssociationCode"
                                         maxLength="16"
                                         onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                         onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Description <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="description" id="idescription"
                                         maxLength="64"
                                         onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                         onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Verified Image URL <span style="color: red">*</span></label>
                            <s:file name="verifieldImageURL" id="iverifieldImageURL" cssClass="choosefileClass" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Card Image URL <span style="color: red">*</span></label>
                            <s:file name="cardImageURL" id="icardImageURL" cssClass="choosefileClass" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Sort Key <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="sortKey" id="isortKey" maxLength="3"
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
                    <sj:submit button="true" href="%{addurl}" value="Add" targets="divmsginsert" id="addButton" disabled="#vadd" /> 
                </div>

                <div class="row">
                    <div class="col-md-12 text-right">
                        <div class="form-group">
                            <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClickTopics="resetAddButton" />
                            <sj:submit cssClass="btn btn-sm btn-functions" button="true" id="triaddButton" onclick="validateAddIpgCardAssociation()"  value="Add" />
                        </div>
                    </div>
                </div>
            </s:form>        
        </div>
    </body>

</html>