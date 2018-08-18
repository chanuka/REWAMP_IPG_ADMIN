<%-- 
    Document   : addriskprofile
    Created on : Nov 08, 2013, 1:10:41 PM
    Author     : chalitha
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML>

<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf"%>

        <script type="text/javascript">

            function toright(val) {
                $("#allowed" + val + " option:selected").each(function () {

                    $("#blocked" + val).append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function toleft(val) {
                $("#blocked" + val + " option:selected").each(function () {

                    $("#allowed" + val).append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function torightall(val) {
                $("#allowed" + val + " option").each(function () {

                    $("#blocked" + val).append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function toleftall(val) {
                $("#blocked" + val + " option").each(function () {

                    $("#allowed" + val).append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function clickAdd() {
                $("#blockedcountry option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#allowedcountry option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#allowedbin option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#blockedbin option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#allowedcurrencytypes option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#blockedcurrencytypes option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#allowedcategories option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#blockedcategories option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#addbut").click();
            }


            function resetFieldData() {

                $('#riskprofilecode').val("");
                $('#description').val("");
                $('#status').val("");
                $('#maxsingletxnlimit').val("");
                $('#minsingletxnlimit').val("");
                $('#maxtxncount').val("");
                $('#maxtxnamount').val("");
                $('#maxpasswordcount').val("");
                toleftall('country');
                toleftall('categories');
                toleftall('bin');
                toleftall('currencytypes');

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
                toleftall('country');
                toleftall('categories');
                toleftall('bin');
                toleftall('currencytypes');
                $('#divmsg').text("");
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

                <s:set id="vadd" var="vadd">
                    <s:property value="vadd" default="true" />
                </s:set>

                <!-- Form -->
                <div class="content-form">
                    <s:form action="MerchantRiskProfile" theme="simple" method="post" id="addform" name="addform" target="divmsg" >

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Risk Profile Code <span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="riskprofilecode" id="riskprofilecode" 
                                                 maxLength="16" 
                                                 onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                                 onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Description <span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="description" id="description" 
                                                 maxLength="64" 
                                                 onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                                 onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Status <span style="color: red">*</span></label>
                                    <s:select id="status" list="statusList" cssClass="form-control"
                                              name="status" headerKey=""
                                              headerValue="--Select Status--" listKey="statuscode"
                                              listValue="description"  />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Maximum Single Transaction Limit <span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="maxsingletxnlimit" id="maxsingletxnlimit" 
                                                 maxLength="16" 
                                                 onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                                 onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Minimum Single Transaction Limit <span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="minsingletxnlimit" id="minsingletxnlimit" 
                                                 maxLength="16" 
                                                 onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                                 onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Maximum Transaction Count <span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="maxtxncount" id="maxtxncount" 
                                                 maxLength="16" 
                                                 onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                                 onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Maximum Total Transaction Amount<span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="maxtxnamount" id="maxtxnamount" 
                                                 maxLength="64" 
                                                 onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                                 onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Maximum Password Count <span style="color: red">*</span></label>
                                    <s:textfield cssClass="form-control" name="maxpasswordcount" id="maxpasswordcount" 
                                                 maxLength="16" 
                                                 onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                                 onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                            </div>
                        </div>


                        <div class="move-change-side">
                            <table>
                                <tr>
                                    <td style="float: left;">
                                        <h6><b>Allowed Countries</b></h6>
                                        <s:select cssClass="move-selectClass" multiple="true"
                                                  name="allowedcountry" id="allowedcountry" list="allowedcountryList" 
                                                  listKey="key" listValue="value"
                                                  ondblclick="toright('country')" />
                                    </td>
                                    <td class="move-buttonClassWithText">
                                        <sj:a id="right" onClick="toright('country')" button="true"><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a> </br> 
                                        <sj:a id="rightall" onClick="torightall('country')" button="true"><i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a></br> 
                                        <sj:a id="left" onClick="toleft('country')" button="true"><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                        <sj:a id="leftall" onClick="toleftall('country')" button="true"><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                                        </td>

                                        <td style="float: right;">
                                            <h6><b>Blocked Countries</b></h6>
                                        <s:select cssClass="move-selectClass" multiple="true"
                                                  name="blockedcountry" id="blockedcountry" list="blockedcountryList"
                                                  listKey="key" listValue="value"
                                                  ondblclick="toleft('country')" />
                                    </td>

                                </tr>
                            </table>
                        </div>


                        <div class="move-change-side">
                            <table>
                                <tr>
                                    <td style="float: left;">
                                        <h6><b>Allowed Merchant Categories</b></h6>
                                        <s:select cssClass="move-selectClass" multiple="true"
                                                  name="allowedcategories" id="allowedcategories" list="allowedmerchantcategoriesList" 
                                                  listKey="key" listValue="value"
                                                  ondblclick="toright('categories')" />
                                    </td>
                                    <td class="move-buttonClassWithText">
                                        <sj:a id="right2" onClick="toright('categories')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a> </br> 
                                        <sj:a id="rightall2" onClick="torightall('categories')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a></br> 
                                        <sj:a id="left2" onClick="toleft('categories')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                        <sj:a id="leftall2" onClick="toleftall('categories')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                                        </td>
                                        <td style="float: right;">
                                            <h6><b>Blocked Merchant Categories</b></h6>
                                        <s:select cssClass="move-selectClass" multiple="true"
                                                  name="blockedcategories" id="blockedcategories" list="blockedmerchantcategoriesList"
                                                  listKey="key" listValue="value"
                                                  ondblclick="toleft('categories')"  />
                                    </td>
                                </tr>
                            </table>
                        </div>



                        <div class="move-change-side">
                            <table>
                                <tr>
                                    <td style="float: left;">
                                        <h6><b>Allowed Currency Types</b></h6>
                                        <s:select cssClass="move-selectClass" multiple="true"
                                                  name="allowedcurrencytypes" id="allowedcurrencytypes" list="allowedcurrencytypesList" 
                                                  listKey="key" listValue="value"
                                                  ondblclick="toright('currencytypes')" />
                                    </td>
                                    <td class="move-buttonClassWithText">
                                        <sj:a id="right3" onClick="toright('currencytypes')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a> </br> 
                                        <sj:a id="rightall3" onClick="torightall('currencytypes')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a></br> 
                                        <sj:a id="left3" onClick="toleft('currencytypes')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                        <sj:a id="leftall3" onClick="toleftall('currencytypes')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                                        </td>
                                        <td style="float: right;">
                                            <h6><b>Blocked Currency Types</b></h6>
                                        <s:select cssClass="move-selectClass" multiple="true"
                                                  name="blockedcurrencytypes" id="blockedcurrencytypes" list="blockedcurrencytypesList"
                                                  listKey="key" listValue="value"
                                                  ondblclick="toleft('currencytypes')" />
                                    </td>

                                </tr>
                            </table>
                        </div>
                        <div class="move-change-side">
                            <table>
                                <tr>
                                    <td style="float: left;">
                                        <h6><b>Allowed Bin</b></h6>
                                        <s:select cssClass="move-selectClass" multiple="true"
                                                  name="allowedbin" id="allowedbin" list="allowedbinList" 
                                                  listKey="key" listValue="value"
                                                  ondblclick="toright('bin')" />
                                    </td>
                                    <td class="move-buttonClassWithText">
                                        <sj:a id="right4" onClick="toright('bin')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a> </br> 
                                        <sj:a id="rightall4" onClick="torightall('bin')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a></br> 
                                        <sj:a id="left4" onClick="toleft('bin')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                        <sj:a id="leftall4" onClick="toleftall('bin')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                                        </td>
                                        <td style="float: right;">
                                            <h6><b>Blocked Bin</b></h6>
                                        <s:select cssClass="move-selectClass" multiple="true"
                                                  name="blockedbin" id="blockedbin" list="blockedbinList"
                                                  listKey="key" listValue="value"
                                                  ondblclick="toleft('bin')"  />
                                    </td>
                                </tr>

                                <tr style="display: none; visibility: hidden;">
                                    <td><s:url var="addurl" action="addMerchantRiskProfile" />
                                        <sj:submit button="true" href="%{addurl}" id="addbut"
                                                   targets="divmsg" /></td>
                                </tr>
                            </table>
                        </div>





                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <sj:a cssClass="btn btn-sm btn-functions" button="true" id="addbuta" onclick="clickAdd()" disabled="#vadd">Add</sj:a> 
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                        </div>
                    </s:form>
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


