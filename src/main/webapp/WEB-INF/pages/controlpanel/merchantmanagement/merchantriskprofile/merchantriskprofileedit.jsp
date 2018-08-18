<%-- 
    Document   : merchantriskprofileedit
    Created on : Jul 16, 2018, 9:47:06 AM
    Author     : sivaganesan_t
--%>

<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML>

<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <script type="text/javascript">

            function torightedit(val) {
                $("#eallowed" + val + " option:selected").each(function() {

                    $("#eblocked" + val).append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function toleftedit(val) {
                $("#eblocked" + val + " option:selected").each(function() {

                    $("#eallowed" + val).append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function torightalledit(val) {
                $("#eallowed" + val + " option").each(function() {

                    $("#eblocked" + val).append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function toleftalledit(val) {
                $("#eblocked" + val + " option").each(function() {

                    $("#eallowed" + val).append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function assignRiskProfileUpdate() {
                $("#eblockedcountry option").each(function() {
                    $(this).attr('selected', 'selected');
                });
                $("#eallowedcountry option").each(function() {
                    $(this).attr('selected', 'selected');
                });
                $("#eallowedbin option").each(function() {
                    $(this).attr('selected', 'selected');
                });
                $("#eblockedbin option").each(function() {
                    $(this).attr('selected', 'selected');
                });
                $("#eallowedcurrencytypes option").each(function() {
                    $(this).attr('selected', 'selected');
                });
                $("#eblockedcurrencytypes option").each(function() {
                    $(this).attr('selected', 'selected');
                });
                $("#eallowedcategories option").each(function() {
                    $(this).attr('selected', 'selected');
                });
                $("#eblockedcategories option").each(function() {
                    $(this).attr('selected', 'selected');
                });

            }
            
            function cancelDataEdit(key){
                var riskprofilecode = $('#eriskprofilecode').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/findMerchantRiskProfile.ipg',
                    data: {
                        riskprofilecode: riskprofilecode
                    },
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {
                            if (key == 'div1') {
                                $('#eriskprofilecode').val(data.riskprofilecode);
                                $('#eriskprofilecode').attr('readOnly', true);
                                $('#edescription').val(data.description);
                                $('#estatus').val(data.status);
                                $('#emaxsingletxnlimit').val(data.maxsingletxnlimit);
                                $('#eminsingletxnlimit').val(data.minsingletxnlimit);
                                $('#emaxtxncount').val(data.maxtxncount);
                                $('#emaxtxnamount').val(data.maxtxnamount);
                                $('#emaxpasswordcount').val(data.maxpasswordcount);
                            } else if (key == 'div2') {
                                $('#eblockedcountry').empty();
                                $('#eallowedcountry').empty();
                                $('#eblockedcategories').empty();
                                $('#eallowedcategories').empty();
                                $.each(data.blockedcountryList, function (index, item) {
                                    $("#eblockedcountry").append(
                                        $('<option></option>').val(index).html( item)
                                   );
                                });
                                $.each(data.allowedcountryList, function (index, item) {
                                    $("#eallowedcountry").append(
                                        $('<option></option>').val(index).html( item)
                                   );
                                });
                                $.each(data.blockedmerchantcategoriesList, function (index, item) {
                                    $("#eblockedcategories").append(
                                        $('<option></option>').val(index).html( item)
                                   );
                                });
                                $.each(data.allowedmerchantcategoriesList, function (index, item) {
                                    $("#eallowedcategories").append(
                                        $('<option></option>').val(index).html( item)
                                   );
                                });
                                
                            } else if (key == 'div3') {
                                $('#eblockedcurrencytypes').empty();
                                $('#eallowedcurrencytypes').empty();
                                $('#eblockedbin').empty();
                                $('#eallowedbin').empty();
                                $.each(data.blockedcurrencytypesList, function (index, item) {
                                    $("#eblockedcurrencytypes").append(
                                        $('<option></option>').val(index).html( item)
                                   );
                                });
                                $.each(data.allowedcurrencytypesList, function (index, item) {
                                    $("#eallowedcurrencytypes").append(
                                        $('<option></option>').val(index).html( item)
                                   );
                                });
                                $.each(data.blockedbinList, function (index, item) {
                                    $("#eblockedbin").append(
                                        $('<option></option>').val(index).html( item)
                                   );
                                });
                                $.each(data.allowedbinList, function (index, item) {
                                    $("#eallowedbin").append(
                                        $('<option></option>').val(index).html( item)
                                   );
                                });
                            }
                        }
                    },
                    error: function (data) {
                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            
            function nextDivEdit(key){
                if (key == 'div1') {
                    $('#etabone').hide();
                    $('#etabtwo').show();
                    $('#etabthree').hide();
                } else if (key == 'div2') {
                    $('#etabone').hide();
                    $('#etabtwo').hide();
                    $('#etabthree').show();
                }
            }
            
            function backDivEdit(key){
                if (key == 'div2') {
                    $('#etabone').show();
                    $('#etabtwo').hide();
                    $('#etabthree').hide();
                } else if (key == 'div3') {
                    $('#etabone').hide();
                    $('#etabtwo').show();
                    $('#etabthree').hide();
                }
            }

        </script>

    </head>

    <body>

        <div class="content-message">
            <s:div id="divmsgupdate">
                <s:actionmessage theme="jquery"/>
                <s:actionerror theme="jquery"/>
            </s:div>
        </div>

        <!-- Form -->
        <div class="content-form">

            <s:form action="MerchantRiskProfile" theme="simple" method="post" id="editmerchantriskprofile" >
                <div id="etabone" >
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Risk Profile Code <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="riskprofilecode" id="eriskprofilecode" readonly="true"
                                             maxLength="8" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Description <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="description" id="edescription" 
                                             maxLength="64" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Status <span style="color: red">*</span></label>
                                <s:select id="estatus" list="statusList" cssClass="form-control"
                                          name="status" headerKey=""
                                          headerValue="--Select Status--" listKey="statuscode"
                                          listValue="description"  />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Maximum Single Transaction Limit <span style="color: red">*</span></label>
                                <s:textfield name="maxsingletxnlimit" id="emaxsingletxnlimit" cssClass="form-control"
                                             maxLength="8" 
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Minimum Single Transaction Limit <span style="color: red">*</span></label>
                                <s:textfield name="minsingletxnlimit" id="eminsingletxnlimit" cssClass="form-control"
                                             maxLength="8" 
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Maximum Transaction Count <span style="color: red">*</span></label>
                                <s:textfield name="maxtxncount" id="emaxtxncount" cssClass="form-control"
                                             maxLength="4" 
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Maximum Total Transaction Amount<span style="color: red">*</span></label>
                                <s:textfield name="maxtxnamount" id="emaxtxnamount" cssClass="form-control"
                                             maxLength="8" 
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Maximum Password Count <span style="color: red">*</span></label>
                                <s:textfield name="maxpasswordcount" id="emaxpasswordcount" cssClass="form-control" 
                                             maxLength="2" 
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
                    <div class="row">
                        <div class="col-sm-12 text-right">
                            <div class="form-group">
                                <sj:submit 
                                    button="true" 
                                    value="Reset" 
                                    onClick="cancelDataEdit('div1')"
                                    cssClass="btn btn-sm btn-reset"
                                    />                          
                                <sj:submit
                                    button="true"
                                    value="Next"
                                    id="ifnextbtn"
                                    onclick="nextDivEdit('div1')"
                                    cssClass="btn btn-sm btn-functions" 
                                    />                        
                            </div>
                        </div>
                    </div>
                </div>
                <div id="etabtwo" hidden>
                    <div class="move-change-side">
                        <table>
                            <tr>
                                <td style="float: left;">
                                    <h6><b>Allowed Countries</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="allowedcountry" id="eallowedcountry" list="allowedcountryList" 
                                              listKey="key" listValue="value"
                                              ondblclick="torightedit('country')" />
                                </td>
                                <td class="move-buttonClassWithText">
                                    <sj:a id="eright" onClick="torightedit('country')" button="true"><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a> </br> 
                                    <sj:a id="erightall" onClick="torightalledit('country')" button="true"><i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="eleft" onClick="toleftedit('country')" button="true"><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="eleftall" onClick="toleftalledit('country')" button="true"><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                                    </td>

                                    <td style="float: right;">
                                        <h6><b>Blocked Countries</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="blockedcountry" id="eblockedcountry" list="blockedcountryList"
                                              listKey="key" listValue="value"
                                              ondblclick="toleftedit('country')" />
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
                                              name="allowedcategories" id="eallowedcategories" list="allowedmerchantcategoriesList" 
                                              listKey="key" listValue="value"
                                              ondblclick="torightedit('categories')" />
                                </td>
                                <td class="move-buttonClassWithText">
                                    <sj:a id="eright2" onClick="torightedit('categories')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a> </br> 
                                    <sj:a id="erightall2" onClick="torightalledit('categories')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="eleft2" onClick="toleftedit('categories')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="eleftall2" onClick="toleftalledit('categories')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                                    </td>
                                    <td style="float: right;">
                                        <h6><b>Blocked Merchant Categories</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="blockedcategories" id="eblockedcategories" list="blockedmerchantcategoriesList"
                                              listKey="key" listValue="value"
                                              ondblclick="toleftedit('categories')"  />
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="row">
                        <div class="col-sm-12 text-right">
                            <div class="form-group">
                                <sj:submit 
                                    button="true" 
                                    value="Reset" 
                                    onClick="cancelDataEdit('div2')"
                                    cssClass="btn btn-sm btn-reset"
                                    /> 
                                <sj:submit 
                                    button="true" 
                                    value="Back" 
                                    onClick="backDivEdit('div2')"
                                    cssClass="btn btn-sm btn-functions"
                                    /> 
                                <sj:submit
                                    button="true"
                                    value="Next"
                                    id="ifnextbtn"
                                    onclick="nextDivEdit('div2')"
                                    cssClass="btn btn-sm btn-functions" 
                                    />                        
                            </div>
                        </div>
                    </div>            
                </div>
                <div id="etabthree" hidden>             
                    <div class="move-change-side">
                        <table>
                            <tr>
                                <td style="float: left;">
                                    <h6><b>Allowed Currency Types</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="allowedcurrencytypes" id="eallowedcurrencytypes" list="allowedcurrencytypesList" 
                                              listKey="key" listValue="value"
                                              ondblclick="torightedit('currencytypes')" />
                                </td>
                                <td class="move-buttonClassWithText">
                                    <sj:a id="eright3" onClick="torightedit('currencytypes')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a> </br> 
                                    <sj:a id="erightall3" onClick="torightalledit('currencytypes')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="eleft3" onClick="toleftedit('currencytypes')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="eleftall3" onClick="toleftalledit('currencytypes')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                                    </td>
                                    <td style="float: right;">
                                        <h6><b>Blocked Currency Types</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="blockedcurrencytypes" id="eblockedcurrencytypes" list="blockedcurrencytypesList"
                                              listKey="key" listValue="value"
                                              ondblclick="toleftedit('currencytypes')" />
                                </td>

                            </tr>
                        </table>
                    </div>
                    <div class="move-change-side">
                        <table>
                            <tr>
                                <td style="float: left;">
                                    <h6><b>Allowed BIN</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="allowedbin" id="eallowedbin" list="allowedbinList" 
                                              listKey="key" listValue="value"
                                              ondblclick="torightedit('bin')" />
                                </td>
                                <td class="move-buttonClassWithText">
                                    <sj:a id="eright4" onClick="torightedit('bin')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a> </br> 
                                    <sj:a id="erightall4" onClick="torightalledit('bin')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="eleft4" onClick="toleftedit('bin')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="eleftall4" onClick="toleftalledit('bin')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                                    </td>
                                    <td style="float: right;">
                                        <h6><b>Blocked BIN</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="blockedbin" id="eblockedbin" list="blockedbinList"
                                              listKey="key" listValue="value"
                                              ondblclick="toleftedit('bin')"  />
                                </td>
                            </tr>

                        </table>
                    </div>
                    <div class="row">
                        <div class="col-md-12 text-right">
                            <div class="form-group">
                                <sj:submit 
                                    button="true" 
                                    value="Reset" 
                                    onClick="cancelDataEdit('div3')"
                                    cssClass="btn btn-sm btn-reset"
                                    /> 
                                <sj:submit 
                                    button="true" 
                                    value="Back" 
                                    onClick="backDivEdit('div3')"
                                    cssClass="btn btn-sm btn-functions"
                                    /> 
                                <s:url var="updateurl" action="updateMerchantRiskProfile" />
                                <sj:submit
                                    button="true"
                                    value="Update"
                                    href="%{updateurl}"
                                    onclick="assignRiskProfileUpdate()"
                                    cssClass="btn btn-sm btn-functions"
                                    targets="divmsgupdate"
                                    />
                            </div>
                        </div>
                    </div>
                </div>            
            </s:form>
        </div> 
    </body>
</html>
