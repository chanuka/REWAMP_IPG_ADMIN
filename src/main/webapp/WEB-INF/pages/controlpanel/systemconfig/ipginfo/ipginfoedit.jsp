<%-- 
    Document   : ipginfoedit
    Created on : Jul 10, 2018, 2:12:11 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            
             function editIpgInfo(keyval) {
                 $.ajax({
                    url: '${pageContext.request.contextPath}/findIPGMPIInfoServelet.ipg',
                    data: {ipgMpiInfoId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {

                            $('#eipgMpiInfoId').val(data.ipgMpiInfoId);
                            $('#eprimaryUrl').val(data.primaryUrl);
                            $('#esecondaryUrl').val(data.secondaryUrl);
                        }
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            function cancelUpateData() {
                var ipgMpiInfoId = $('#eipgMpiInfoId').val();
                editIpgInfo(ipgMpiInfoId);
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
            <s:form id="ipgmpiinfoserveletedit" method="post" action="IPGMPIInfoServelete"  theme="simple" cssClass="form" enctype="multipart/form-data"> 
                <s:hidden name="ipgMpiInfoId" id="eipgMpiInfoId" />
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Primary URL <span style="color: red">*</span> </label>
                            <s:textfield cssClass="form-control" name="primaryUrl" id="eprimaryUrl" maxLength="250" />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" >Secondary URL <span style="color: red">*</span> </label>
                            <s:textfield cssClass="form-control" name="secondaryUrl" id="esecondaryUrl" maxLength="250" />
                        </div>
                    </div>
                </div>  
                <div class="row">
                    <div class="col-md-12">
                        <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                    </div>
                </div> 
                <s:url var="updateurl" action="updateIPGMPIInfoServelet"/>
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

