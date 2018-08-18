<%-- 
    Document   : countryedit
    Created on : Jul 12, 2018, 11:22:52 AM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            
             function editCountry(keyval) {
                 $.ajax({
                    url: '${pageContext.request.contextPath}/findCountry.ipg',
                    data: {
                        countryCode: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {
                            $('#ecountryCode').val( data.countryCode);
                            $('#ecountryCode').attr('readOnly', true);
                            $('#edescription').val(data.description);
                            $('#esortKey').val(data.sortKey);
                            $('#ecountryisocode').val(data.countryisocode);
                        }
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            function cancelUpateData() {
                var countryCode = $('#ecountryCode').val();
                editCountry(countryCode);
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
            <s:form id="countryedit" method="post" action="Country"  theme="simple" cssClass="form" enctype="multipart/form-data"> 
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Country Code <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="countryCode"
                                         id="ecountryCode" maxLength="8"
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
                            <label class="control-label" >Sort Key <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="sortKey" id="esortKey" maxLength="3"
                                         onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"
                                         onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Country ISO Code <span style="color: red">*</span></label>
                            <s:textfield cssClass="form-control" name="countryisocode" id="ecountryisocode" maxLength="3"
                                         onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                         onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                        </div>
                    </div>
                </div> 
                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 
                <s:url var="updateurl" action="updateCountry"/>
                <div class="row">
                    <div class="col-md-12 text-right">
                        <div class="form-group">
                            <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="cancelUpateData()" />
                            <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{updateurl}" value="Update" targets="divmsgupdate" id="updateButton" />
                        </div>
                    </div>
                </div>
            </s:form>        
        </div>
    </body>
</html>

