<%-- 
    Document   : mpipasswordpolicymgt
    Created on : 23 Sep, 2013, 8:30:22 AM
    Author     : asela
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf" %>

        <script type="text/javascript">

            function editformatter(cellvalue) {
                a = $("#addButton").is(':disabled');
                u = $("#updateButton").is(':disabled');
                return "<a href='#' title='Edit' onClick='javascript:editPasswordPolicicy(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function editPasswordPolicicy(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindPasswordPolicy.ipg',
                    data: {passwordpolicyid: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {

                            $('#passwordpolicyid').attr('readOnly', true);
                            $('#passwordpolicyid').val(data.passwordpolicyid);
                            $('#minimumlength').val(data.minimumlength);
                            $('#maximumlength').val(data.maximumlength);
                            $('#allowedspacialchars').val(data.allowedspacialchars);
                            $('#minimumspacialchars').val(data.minimumspacialchars);
                            $('#minimumuppercasechars').val(data.minimumuppercasechars);
                            $('#minimumnumericalchars').val(data.minimumnumericalchars);
                            $('#minimumlowercasechars').val(data.minimumlowercasechars);
                            $('#noofPINcount').val(data.noofPINcount);
                            $('#status').val(data.status);
                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");

                            jQuery("#gridtable").trigger("reloadGrid");
                        }
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function resetAllData() {
                a = $("#addButton").is(':disabled');
                u = $("#updateButton").is(':disabled');
                if (a == true && u == true) {
                    $('#passwordpolicyid').attr('readOnly', false);
                    $('#passwordpolicyid').val("");
                    $('#minimumlength').val("");
                    $('#maximumlength').val("");
                    $('#allowedspacialchars').val("");
                    $('#minimumspacialchars').val("");
                    $('#minimumuppercasechars').val("");
                    $('#minimumnumericalchars').val("");
                    $('#minimumlowercasechars').val("");
                    $('#noofPINcount').val("");
                    $('#status').val("");
                    $('#addButton').button("disable");
                    $('#updateButton').button("disable");
                    $('#divmsg').text("");
                } else if (a == true && u == false) {
                    var passwordpolicyid = $('#passwordpolicyid').val();
                    $('#passwordpolicyid').attr('readOnly', true);
                    editPasswordPolicicy(passwordpolicyid);
                } else if (a == false && u == true) {
                    $('#passwordpolicyid').attr('readOnly', false);
                    $('#passwordpolicyid').val("");
                    $('#minimumlength').val("");
                    $('#maximumlength').val("");
                    $('#allowedspacialchars').val("");
                    $('#minimumspacialchars').val("");
                    $('#minimumuppercasechars').val("");
                    $('#minimumnumericalchars').val("");
                    $('#minimumlowercasechars').val("");
                    $('#noofPINcount').val("");
                    $('#status').val("");
                    $('#addButton').button("enable");
                    $('#updateButton').button("disable");
                    $('#divmsg').text("");

                }

            }

            function resetFieldData() {
                $.ajax({
                    url: '${pageContext.request.contextPath}/ResetPasswordPolicy.ipg',
                    data: {},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        var msg = data.message;
                        if (msg) {
                            $('#passwordpolicyid').attr('readOnly', false);
                            $('#passwordpolicyid').val("");
                            $('#minimumlength').val("");
                            $('#maximumlength').val("");
                            $('#allowedspacialchars').val("");
                            $('#minimumspacialchars').val("");
                            $('#minimumuppercasechars').val("");
                            $('#minimumnumericalchars').val("");
                            $('#minimumlowercasechars').val("");
                            $('#noofPINcount').val("");
                            $('#status').val("");
                            $('#addButton').button("enable");
                            $('#updateButton').button("disable");
                            jQuery("#gridtable").trigger("reloadGrid");
                        } else {

                            $('#passwordpolicyid').attr('readOnly', true);
                            $('#passwordpolicyid').val(data.passwordpolicyid);
                            $('#minimumlength').val(data.minimumlength);
                            $('#maximumlength').val(data.maximumlength);
                            $('#allowedspacialchars').val(data.allowedspacialchars);
                            $('#minimumspacialchars').val(data.minimumspacialchars);
                            $('#minimumuppercasechars').val(data.minimumuppercasechars);
                            $('#minimumnumericalchars').val(data.minimumnumericalchars);
                            $('#minimumlowercasechars').val(data.minimumlowercasechars);
                            $('#noofPINcount').val(data.noofPINcount);
                            $('#status').val(data.status);
                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");

                            jQuery("#gridtable").trigger("reloadGrid");
                        }
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
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
                    <s:set var="policyid"><s:property value="policyid" default="true"/></s:set>


                        <!-- Form -->
                        <div class="content-form">
                        <%--<s:form id="pageadd" method="post" action="MPITask" theme="simple" >--%>
                        <s:form id="pageadd" method="post" action="" theme="simple" >


                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Password Policy ID <span style="color: red">*</span></label>
                                        <s:textfield cssClass="form-control" name="passwordpolicyid" id="passwordpolicyid" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" readonly="#policyid"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Minimum Length<span style="color: red">*</span></label>
                                        <s:textfield cssClass="form-control" name="minimumlength" id="minimumlength" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Maximum Length<span style="color: red">*</span></label>
                                        <s:textfield cssClass="form-control" name="maximumlength" id="maximumlength" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/> 
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Allowed Special Chars<span style="color: red">*</span></label>
                                        <s:textfield cssClass="form-control" name="allowedspacialchars" id="allowedspacialchars" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Minimum Special Chars <span style="color: red">*</span></label>
                                        <s:textfield cssClass="form-control" name="minimumspacialchars" id="minimumspacialchars" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Minimum Uppercase Chars<span style="color: red">*</span></label>
                                        <s:textfield cssClass="form-control" name="minimumuppercasechars" id="minimumuppercasechars" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Minimum Numerical Chars<span style="color: red">*</span></label>
                                        <s:textfield cssClass="form-control" name="minimumnumericalchars" id="minimumnumericalchars" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Minimum Lowercase Chars<span style="color: red">*</span></label>
                                        <s:textfield cssClass="form-control" name="minimumlowercasechars" id="minimumlowercasechars" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >No of PIN Count<span style="color: red">*</span></label>
                                        <s:textfield cssClass="form-control" name="noofPINcount" id="noofPINcount" maxLength="2" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Status<span style="color: red">*</span></label>
                                        <s:select cssClass="form-control" id="status" list="%{statusList}"  name="status" headerKey="" headerValue="--Select Status--" listKey="statuscode" listValue="description" />
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                                </div>
                            </div>

                            <s:url var="addurl" action="AddPasswordPolicy"/>
                            <s:url var="updateurl" action="UpdatePasswordPolicy"/>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{addurl}" value="Add" targets="divmsg" id="addButton"  disabled="#vadd"/>
                                        <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="%{updateurl}" value="Update" targets="divmsg"   disabled="#vupdatebutt" id="updateButton"/>
                                        <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()"  />
                                    </div>
                                </div>
                            </div>
                        </s:form>
                    </div>

                    <div class="content-table">
                        <!--this div for triangles: don't delete-->
                        <div></div>
                        <%--<s:url var="listurl" action="ListMPIPasswordPolicyServlet"/>--%>
                        <s:url var="listurl" action="ListPasswordPolicy"/>
                        <sjg:grid
                            id="gridtable"
                            caption="Password Policy Management"
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
                            <sjg:gridColumn name="passwordpolicyid" index="m.mpipasswordpolicyid" title="Edit" width="30" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                            <sjg:gridColumn name="minimumlength" index="m.minimumlength" title="Minimum Length"  sortable="true"/>
                            <sjg:gridColumn name="maximumlength" index="m.maximumlength" title="Maximum Length"  sortable="true"/>
                            <sjg:gridColumn name="allowedspacialchars" index="m.allowedspecialcharacters" title="Allowed Special Chars"  sortable="true"/>
                            <sjg:gridColumn name="minimumspacialchars" index="m.minimumspecialcharacters" title="Minimum Special Chars"  sortable="true"/>
                            <sjg:gridColumn name="minimumuppercasechars" index="m.minimumuppercasecharacters" title="Minimum Uppercase Chars"  sortable="true"/>
                            <sjg:gridColumn name="minimumnumericalchars" index="m.minimumnumericalcharacters" title="Minimum Numerical Chars"  sortable="true"/>
                            <sjg:gridColumn name="status" index="m.ipgstatus.description" title="Status"  sortable="true"/>
                            <sjg:gridColumn name="createdtime" index="m.createdtime" title="Created Time"  sortable="true"/>

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

