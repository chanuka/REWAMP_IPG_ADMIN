<%-- 
    Document   : merchantriskprofileinsert
    Created on : Jul 13, 2018, 4:05:27 PM
    Author     : sivaganesan_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML>

<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <script type="text/javascript">

            function toright(val) {
                $("#iallowed" + val + " option:selected").each(function () {

                    $("#iblocked" + val).append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function toleft(val) {
                $("#iblocked" + val + " option:selected").each(function () {

                    $("#iallowed" + val).append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function torightall(val) {
                $("#iallowed" + val + " option").each(function () {

                    $("#iblocked" + val).append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function toleftall(val) {
                $("#iblocked" + val + " option").each(function () {

                    $("#iallowed" + val).append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function assignRiskProfileAdd() {
                $("#iblockedcountry option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#iallowedcountry option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#iallowedbin option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#iblockedbin option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#iallowedcurrencytypes option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#iblockedcurrencytypes option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#iallowedcategories option").each(function () {
                    $(this).attr('selected', 'selected');
                });
                $("#iblockedcategories option").each(function () {
                    $(this).attr('selected', 'selected');
                });
            }


//            function resetFieldData() {
//
//                $('#iriskprofilecode').val("");
//                $('#idescription').val("");
//                $('#istatus').val("");
//                $('#imaxsingletxnlimit').val("");
//                $('#iminsingletxnlimit').val("");
//                $('#imaxtxncount').val("");
//                $('#imaxtxnamount').val("");
//                $('#imaxpasswordcount').val("");
//                toleftall('country');
//                toleftall('categories');
//                toleftall('bin');
//                toleftall('currencytypes');
//
//            }
//            function resetAllData() {
//
//                $('#iriskprofilecode').val("");
//                $('#idescription').val("");
//                $('#istatus').val("");
//                $('#imaxsingletxnlimit').val("");
//                $('#iminsingletxnlimit').val("");
//                $('#imaxtxncount').val("");
//                $('#imaxtxnamount').val("");
//                $('#imaxpasswordcount').val("");
//                toleftall('country');
//                toleftall('categories');
//                toleftall('bin');
//                toleftall('currencytypes');
//                $('#divmsginsert').text("");
//            }
            
            function nextDivInsert(key){
                if (key == 'div1') {
                    $('#itabone').hide();
                    $('#itabtwo').show();
                    $('#itabthree').hide();
                } else if (key == 'div2') {
                    $('#itabone').hide();
                    $('#itabtwo').hide();
                    $('#itabthree').show();
                }
            }
            
            function backDivInsert(key){
                if (key == 'div2') {
                    $('#itabone').show();
                    $('#itabtwo').hide();
                    $('#itabthree').hide();
                } else if (key == 'div3') {
                    $('#itabone').hide();
                    $('#itabtwo').show();
                    $('#itabthree').hide();
                }
            }
            
            function cancelDataInsert(key){
                if (key == 'div1') {
                    $('#iriskprofilecode').val("");
                    $('#idescription').val("");
                    $('#istatus').val("");
                    $('#imaxsingletxnlimit').val("");
                    $('#iminsingletxnlimit').val("");
                    $('#imaxtxncount').val("");
                    $('#imaxtxnamount').val("");
                    $('#imaxpasswordcount').val("");
                    $('#divmsginsert').text("");
                } else if (key == 'div2') {
                    toleftall('country');
                    toleftall('categories');
                    $('#divmsginsert').text("");
                } else if (key == 'div3') {
                    toleftall('bin');
                    toleftall('currencytypes');
                    $('#divmsginsert').text("");
                }
            }
        </script>
    </head>

    <body>
        <div class="content-message">
            <s:div id="divmsginsert">
                <s:actionmessage theme="jquery"/>
                <s:actionerror theme="jquery"/>
            </s:div>
        </div>


        <!-- Form -->
        <div class="content-form">
            <s:form action="MerchantRiskProfile" theme="simple" method="post" id="addmerchantriskprofile" >
                <div id="itabone" >
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Risk Profile Code <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="riskprofilecode" id="iriskprofilecode" 
                                             maxLength="8" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Description <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="description" id="idescription" 
                                             maxLength="64" 
                                             onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Status <span style="color: red">*</span></label>
                                <s:select id="istatus" list="statusList" cssClass="form-control"
                                          name="status" headerKey=""
                                          headerValue="--Select Status--" listKey="statuscode"
                                          listValue="description"  />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Maximum Single Transaction Limit <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="maxsingletxnlimit" id="imaxsingletxnlimit" 
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
                                <s:textfield cssClass="form-control" name="minsingletxnlimit" id="iminsingletxnlimit" 
                                             maxLength="8" 
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Maximum Transaction Count <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="maxtxncount" id="imaxtxncount" 
                                             maxLength="4" 
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Maximum Total Transaction Amount<span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="maxtxnamount" id="imaxtxnamount" 
                                             maxLength="8" 
                                             onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" 
                                             onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="control-label" >Maximum Password Count <span style="color: red">*</span></label>
                                <s:textfield cssClass="form-control" name="maxpasswordcount" id="imaxpasswordcount" 
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
                                    onClick="cancelDataInsert('div1')"
                                    cssClass="btn btn-sm btn-reset"
                                    />                          
                                <sj:submit
                                    button="true"
                                    value="Next"
                                    id="ifnextbtn"
                                    onclick="nextDivInsert('div1')"
                                    cssClass="btn btn-sm btn-functions" 
                                    />                        
                            </div>
                        </div>
                    </div>
                </div>
                <div id="itabtwo" hidden>            
                    <div class="move-change-side">
                        <table>
                            <tr>
                                <td style="float: left;">
                                    <h6><b>Allowed Countries</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="allowedcountry" id="iallowedcountry" list="allowedcountryList" 
                                              listKey="key" listValue="value"
                                              ondblclick="toright('country')" />
                                </td>
                                <td class="move-buttonClassWithText">
                                    <sj:a id="iright" onClick="toright('country')" button="true"><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a> </br> 
                                    <sj:a id="irightall" onClick="torightall('country')" button="true"><i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="ileft" onClick="toleft('country')" button="true"><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="ileftall" onClick="toleftall('country')" button="true"><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                                    </td>

                                    <td style="float: right;">
                                        <h6><b>Blocked Countries</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="blockedcountry" id="iblockedcountry" list="blockedcountryList"
                                              listKey="key" listValue="value"
                                              ondblclick="toleft('country')" />
                                </td>

                            </tr>
                        </table>
                    </div>


                    <div class="move-change-side" >
                        <table>
                            <tr>
                                <td style="float: left;">
                                    <h6><b>Allowed Merchant Categories</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="allowedcategories" id="iallowedcategories" list="allowedmerchantcategoriesList" 
                                              listKey="key" listValue="value"
                                              ondblclick="toright('categories')" />
                                </td>
                                <td class="move-buttonClassWithText">
                                    <sj:a id="iright2" onClick="toright('categories')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a> </br> 
                                    <sj:a id="irightall2" onClick="torightall('categories')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="ileft2" onClick="toleft('categories')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="ileftall2" onClick="toleftall('categories')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                                    </td>
                                    <td style="float: right;">
                                        <h6><b>Blocked Merchant Categories</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="blockedcategories" id="iblockedcategories" list="blockedmerchantcategoriesList"
                                              listKey="key" listValue="value"
                                              ondblclick="toleft('categories')"  />
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
                                    onClick="cancelDataInsert('div2')"
                                    cssClass="btn btn-sm btn-reset"
                                    /> 
                                <sj:submit 
                                    button="true" 
                                    value="Back" 
                                    onClick="backDivInsert('div2')"
                                    cssClass="btn btn-sm btn-functions"
                                    /> 
                                <sj:submit
                                    button="true"
                                    value="Next"
                                    id="ifnextbtn"
                                    onclick="nextDivInsert('div2')"
                                    cssClass="btn btn-sm btn-functions" 
                                    />                        
                            </div>
                        </div>
                    </div>            
                </div>
                <div id="itabthree" hidden>             
                    <div class="move-change-side">
                        <table>
                            <tr>
                                <td style="float: left;">
                                    <h6><b>Allowed Currency Types</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="allowedcurrencytypes" id="iallowedcurrencytypes" list="allowedcurrencytypesList" 
                                              listKey="key" listValue="value"
                                              ondblclick="toright('currencytypes')" />
                                </td>
                                <td class="move-buttonClassWithText">
                                    <sj:a id="iright3" onClick="toright('currencytypes')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a> </br> 
                                    <sj:a id="irightall3" onClick="torightall('currencytypes')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="ileft3" onClick="toleft('currencytypes')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="ileftall3" onClick="toleftall('currencytypes')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                                    </td>
                                    <td style="float: right;">
                                        <h6><b>Blocked Currency Types</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="blockedcurrencytypes" id="iblockedcurrencytypes" list="blockedcurrencytypesList"
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
                                    <h6><b>Allowed BIN</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="allowedbin" id="iallowedbin" list="allowedbinList" 
                                              listKey="key" listValue="value"
                                              ondblclick="toright('bin')" />
                                </td>
                                <td class="move-buttonClassWithText">
                                    <sj:a id="iright4" onClick="toright('bin')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a> </br> 
                                    <sj:a id="irightall4" onClick="torightall('bin')" button="true" ><i class="fa fa-chevron-right" aria-hidden="true"></i><i class="fa fa-chevron-right" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="ileft4" onClick="toleft('bin')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a></br> 
                                    <sj:a id="ileftall4" onClick="toleftall('bin')" button="true" ><i class="fa fa-chevron-left" aria-hidden="true"></i><i class="fa fa-chevron-left" aria-hidden="true"></i></sj:a>
                                    </td>
                                    <td style="float: right;">
                                        <h6><b>Blocked BIN</b></h6>
                                    <s:select cssClass="move-selectClass" multiple="true"
                                              name="blockedbin" id="iblockedbin" list="blockedbinList"
                                              listKey="key" listValue="value"
                                              ondblclick="toleft('bin')"  />
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
                                    onClick="cancelDataInsert('div3')"
                                    cssClass="btn btn-sm btn-reset"
                                    /> 
                                <sj:submit 
                                    button="true" 
                                    value="Back" 
                                    onClick="backDivInsert('div3')"
                                    cssClass="btn btn-sm btn-functions"
                                    /> 
                                <s:url var="addurl" action="addMerchantRiskProfile" />
                                <sj:submit
                                    button="true"
                                    value="Add"
                                    href="%{addurl}"
                                    onclick="assignRiskProfileAdd()"
                                    cssClass="btn btn-sm btn-functions"
                                    targets="divmsginsert"
                                    />
                            </div>
                        </div>
                    </div>
                </div>
            </s:form>
        </div>
           
</body>
</html>
