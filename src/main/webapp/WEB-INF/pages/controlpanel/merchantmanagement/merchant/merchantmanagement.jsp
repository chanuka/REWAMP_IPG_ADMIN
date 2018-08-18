<%-- 
    Document   : merchantmanagement
    Created on : Nov 19, 2013, 11:23:49 AM
    Author     : asitha_l
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

            function editformatter(cellvalue) {

                return "<a href='#' title='Edit' onClick='javascript:editMerchantInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function editMerchantInit(keyval) {
                $("#updatedialog").data('merchantId', keyval).dialog('open');
            }

            $.subscribe('openviewmerchanttopage', function(event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailMerchant.ipg?merchantId=" + $led.data('merchantId'));
            });
            //uplaod cert
            function uplaodcertformatter(cellvalue, options, rowObject) {
                if (rowObject.digitalSign == "DigitallySign") {
                    return "<a href='javascript:void(0)' title='Upload Certificate' onClick='javascript:uplaodcertInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
                } else {
                    return "--";
                }
            }
            function uplaodcertInit(keyval) {
                $("#uploaddialog").data('merchantId', keyval).dialog('open');
            }
            $.subscribe('openviewuplaodcertpage', function(event, data) {
                var $leds = $("#uploaddialog");
                $leds.html("Loading..");
                $leds.load("viewUplaodCertMerchant.ipg?merchantId_uc=" + $leds.data('merchantId'));
            });


            //rule and service
            function ruleformatter(cellvalue, options, rowObject) {
                return "<a href='javascript:void(0)' title='Rule and Service' onClick='javascript:ruleInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }
            function ruleInit(keyval) {
                $("#ruledialog").data('merchantId', keyval).dialog('open');
            }
            $.subscribe('openviewrulepage', function(event, data) {
                var $leds = $("#ruledialog");
                $leds.html("Loading..");
                $leds.load("ruleServiceMerchant.ipg?merchantId_rs=" + $leds.data('merchantId'));
            });

            //assign currency
            function assignCurrencyformatter(cellvalue, options, rowObject) {
                return "<a href='javascript:void(0)' title='Assign Currency' onClick='javascript:assignCurrencyInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }
            function assignCurrencyInit(keyval) {
                $("#assignCurrencydialog").data('merchantId', keyval).dialog('open');
            }
            $.subscribe('openviewassignpage', function(event, data) {
                var $led = $("#assignCurrencydialog");
                $led.html("Loading..");
                $led.load("findCurrencyMerchant.ipg?merchantId_ac=" + $led.data('merchantId'));
            });


            // download 
            function downloadformatter(cellvalue, options, rowObject) {
                if (rowObject.filepath == "Y") {
                    return "<a href='downloadCertificateMerchant.ipg?merchantId_de=" + cellvalue + "' onclick='downloadmsgempty()' title='Download Certificate'><img class='ui-icon ui-icon-arrowthickstop-1-s' style='display:inline-table;border:none;'/></a>";
                } else {
                    return "<span title='No certificate found'>--</span>";
                }
            }
            function downloadmsgempty() {
                $('#divmsg').empty();
            }

            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Delete' onClick='javascript:deleteMerchantInit(&#34;"
                        + cellvalue
                        + "&#34;)'><img class='ui-icon ui-icon-trash' style='display:inline-table;border:none;'/></a>";
            }

            function editMerchant(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findMerchant.ipg',
                    data: {
                        merchantId: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message)
                        } else {

                            $('#merchantId').val(data.merchantId);
                            $('#merchantId').attr('readOnly', true);
                            $('#headmerchant').val(data.headmerchant)
                            $('#merchantName').val(data.merchantName);
                            $('#status').val(data.status);
                            $('#email').val(data.email);
                            $('#address1').val(data.address1);
                            $('#secondaryURL').val(data.secondaryURL);
                            $('#address2').val(data.address2);
                            $('#drsURL').val(data.drsURL);
                            $('#city').val(data.city);
                            $('#dreURL').val(data.dreURL);
                            $('#stateCode').val(data.stateCode);
                            $('#dateOfRegistry').val(data.dateOfRegistry);
                            $('#postalCode').val(data.postalCode);
                            $('#dateOfExpire').val(data.dateOfExpire);
                            $('#province').val(data.province);
                            $('#country').val(data.country);
                            $('#contactPerson').val(data.contactPerson);
                            $('#mobile').val(data.mobile);
                            $('#telNo').val(data.telNo);
                            $('#remarks').val(data.remarks);
                            $('#fax').val(data.fax);
                            $('#primaryURL').val(data.primaryURL);
                            $('[value="' + data.securityMechanism + '"]').prop('checked', true);
                            $('#addButton').button("disable");
                            $('#updateButton').button("enable");

                        }

                    },
                    error: function(data) {
                        $("#deleteerrordialog").html(
                                "Error occurred while processing.").dialog(
                                'open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function deleteMerchantInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html(
                        'Are you sure you want to delete Merchant ' + keyval + ' ?');
                return false;
            }

            function deleteMerchant(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteMerchant.ipg',
                    data: {
                        merchantId: keyval
                    },
                    dataType: "json",
                    type: "POST",
                    success: function(data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        resetFieldData();
                        //                        jQuery("#gridtable").trigger("reloadGrid");                      
                    },
                    error: function(data) {
                        $("#deleteerrordialog").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }
            function resetAllData() {
                $('#merchantId').val("");
                $('#merchantName').val("");
                $('#headmerchant').val("");
                $('#defaultmerchant').val("");
                $('#address1').val("");
                $('#city').val("");
                $('#email').val("");
                $('#divmsg').text("");
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        merchantId: '',
                        merchantName: '',
                        address1: '',
                        city: '',
                        email: '',
                        headmerchant: '',
                        defaultmerchant: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {
                $('#imerchantId').val("");
                $('#iheadmerchant').val("");
                $('#imerchantName').val("");
                $('#istatus').val("");
                $('#iemail').val("");
                $('#iaddress1').val("");
                $('#isecondaryURL').val("");
                $('#iaddress2').val("");
                $('#idrsURL').val("");
                $('#icity').val("");
                $('#idreURL').val("");
                $('#istateCode').val("");
                $('#idateOfRegistry').val("");
                $('#ipostalCode').val("");
                $('#idateOfExpire').val("");
                $('#iprovince').val("");
                $('#icountry').val("");
                $('#icontactPerson').val("");
                $('#imobile').val("");
                $('#itelNo').val("");
                $('#iremarks').val("");
                $('#ifax').val("");
                $('#iprimaryURL').val("");
                $('#iauthreqstatus').val("");
                $('#iriskprofile').val("");
                $('#iviewmerchantstatus').attr('hidden',true);
                $('[id="isecurityMechanismDigitallySign"][value="DigitallySign"]').prop('checked', false);
                $('[id="isecurityMechanismSymmetricKey"][value="SymmetricKey"]').prop('checked', false);

                jQuery("#gridtable").trigger("reloadGrid");
            }

            function searchMerchants() {
                $('#divmsg').text("");
                var merchantId = $('#merchantId').val();
                var merchantName = $('#merchantName').val();
                var address1 = $('#address1').val();
                var city = $('#city').val();
                var email = $('#email').val();
                var headmerchant = $('#headmerchant').val();
                var defaultmerchant = $('#defaultmerchant').val();


                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        merchantId: merchantId,
                        merchantName: merchantName,
                        address1: address1,
                        city: city,
                        email: email,
                        headmerchant: headmerchant,
                        defaultmerchant: defaultmerchant,
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
                    <s:set var="vdownload">
                        <s:property value="vdownload" default="true" />
                    </s:set>
                    <s:set var="vrule">
                        <s:property value="vrule" default="true" />
                    </s:set>
                    <s:set var="vassignc">
                        <s:property value="vassignc" default="true" />
                    </s:set>
                    <s:set var="vsearch">
                        <s:property value="vsearch" default="true"/>
                    </s:set>
                    <!-- Form -->
                    <div class="content-form">
                        <s:form id="merchant" method="post" action="Merchant" theme="simple">

                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Merchant ID </label>
                                        <s:textfield cssClass="form-control" name="merchantId" id="merchantId"
                                                     maxLength="15"
                                                     onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" />
                                    </div>
                                </div>

                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Merchant Name</label>
                                        <s:textfield cssClass="form-control" name="merchantName" id="merchantName"
                                                     maxLength="255"
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Merchant Customer Name</label>
                                        <s:select cssClass="form-control" id="headmerchant" list="%{headmerchantList}"
                                                  name="headmerchant" headerKey="" headerValue="--Select--"
                                                  listKey="merchantcustomerid" listValue="merchantcustomername" />
                                    </div>
                                </div>
                                <div class="col-md-3" >
                                    <div class="form-group">
                                        <label class="control-label">Default Merchant</label>
                                        <s:select cssClass="form-control" id="defaultmerchant" list="%{defaultmerchantList}"
                                                  name="defaultmerchant" headerKey="" headerValue="--Select--" 
                                                  listKey="statuscode" listValue="description" />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Address</label>
                                        <s:textfield cssClass="form-control" name="address1" id="address1" maxLength="255" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">City</label>
                                        <s:textfield cssClass="form-control"  name="city" id="city"
                                                     maxLength="64"
                                                     onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"
                                                     onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label">Email</label>
                                        <s:textfield cssClass="form-control" name="email" id="email" maxLength="255"/>
                                    </div>
                                </div>
                            </div>  
                        </s:form>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" id="search"  disabled="#vsearch" onclick="searchMerchants()"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                            <div class="col-sm-3"></div>
                            <div class="col-sm-3 text-right">
                                <s:url var="addurlLink" action="viewpopupMerchant"/>
                                <div class="form-group">
                                    <sj:submit 
                                        openDialog="remotedialog"
                                        button="true"
                                        href="%{addurlLink}"
                                        buttonIcon="ui-icon-newwin"
                                        disabled="#vadd"
                                        value="Add Merchant"
                                        id="addButton_new"
                                        cssClass="btn btn-sm btn-functions" 
                                        />
                                </div>
                            </div>
                        </div>        

                    </div>
                    <!--upload dialog-->
                    <sj:dialog                                     
                        id="uploaddialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        position="center"
                        title="Upload Merchant Certificate"
                        onOpenTopics="openviewuplaodcertpage" 
                        loadingText="Loading .."
                        width="800"
                        height="450"
                        dialogClass= ""
                        />
                    <!--rule dialog-->
                    <sj:dialog                                     
                        id="ruledialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        position="center"
                        title="Rule and Service charge"
                        onOpenTopics="openviewrulepage" 
                        loadingText="Loading .."
                        width="900"
                        height="550"
                        dialogClass= ""
                        />

                    <!--assign currency dialog-->
                    <sj:dialog                                     
                        id="assignCurrencydialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        position="center"
                        title="Assign Currency Page"
                        onOpenTopics="openviewassignpage" 
                        loadingText="Loading .."
                        width="900"
                        height="550"
                        dialogClass= "dialogclass"
                        />


                    <!-- download -->
                    <sj:dialog id="downloaddialog"
                               buttons="{ 
                               'OK':function() { downloadCertificate($(this).data('keyval'));$( this ).dialog( 'close' ); },
                               'Cancel':function() { $( this ).dialog( 'close' );} 
                               }"
                               autoOpen="false" modal="true" title="Download Certificate" />
                    <!-- Start download process dialog box -->
                    <sj:dialog id="downloadsuccdialog"
                               buttons="{
                               'OK':function() { $( this ).dialog( 'close' );}
                               }"
                               autoOpen="false" modal="true" title="Download Process." />
                    <!-- Start download error dialog box -->
                    <sj:dialog id="downloaderrordialog"
                               buttons="{
                               'OK':function() { $( this ).dialog( 'close' );}                                    
                               }"
                               autoOpen="false" modal="true" title="Download error." />


                    <!-- Start delete confirm dialog box -->
                    <sj:dialog id="deletedialog"
                               buttons="{ 
                               'OK':function() { deleteMerchant($(this).data('keyval'));$( this ).dialog( 'close' ); },
                               'Cancel':function() { $( this ).dialog( 'close' );} 
                               }"
                               autoOpen="false" modal="true" title="Delete Merchant" />
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

                    <sj:dialog                                     
                        id="remotedialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        title="Add Merchant"                            
                        loadingText="Loading .."                            
                        position="center"                            
                        width="1000"
                        height="500"
                        dialogClass= "dialogclass"

                        />
                    <sj:dialog                                     
                        id="updatedialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        position="center"
                        title="Update Merchant"
                        onOpenTopics="openviewmerchanttopage" 
                        loadingText="Loading .."
                        width="1000"
                        height="500"
                        dialogClass= "dialogclass"
                        />

                    <div class="content-table">
                        <!--this div for triangles: don't delete-->
                        <div></div>
                        <s:url var="listurl" action="listMerchant" />
                        <sjg:grid 
                            id="gridtable" 
                            caption="Merchant Management" 
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
                            shrinkToFit="true"
                            ><sjg:gridColumn name="merchantid" index="u.merchantid" title="Edit" width="60" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                            <sjg:gridColumn name="merchantid" index="u.merchantid" title="Upload Certificate" width="170" align="center" formatter="uplaodcertformatter" hidden="#vupload" />
                            <sjg:gridColumn name="merchantid" index="u.merchantid" title="Download Certificate" width="200" align="center" formatter="downloadformatter" hidden="#vdownload" />
                            <sjg:gridColumn name="merchantid" index="u.merchantid" title="Rule and Service" width="160" align="center" formatter="ruleformatter" hidden="#vrule" />
                            <sjg:gridColumn name="merchantid" index="u.merchantid" title="Assign Currency" width="160" align="center" formatter="assignCurrencyformatter" hidden="#vassignc" />
                            <sjg:gridColumn name="merchantid" index="u.merchantid" title="Delete" width="80" align="center" formatter="deleteformatter" hidden="#vdelete" />
                            <sjg:gridColumn name="merchantid" index="u.merchantid" title="Merchant ID" sortable="true" />
                            <sjg:gridColumn name="merchantname" index="u.merchantname" title="Merchant Name" sortable="true" />
                            <sjg:gridColumn name="headmerchant" index="u.ipgheadmerchant.merchantcustomername" title="Head Merchant" sortable="true" />
                            <sjg:gridColumn name="defaultmerchant" index="u.isdefaultmerchany" title="Default Merchant" sortable="true" />
                            <sjg:gridColumn name="address" index="u.ipgaddress.address1" title="Address" sortable="true" />
                            <sjg:gridColumn name="city" index="u.ipgaddress.city" title="City" sortable="true" />
                            <sjg:gridColumn name="email" index="u.ipgaddress.email" title="Email" sortable="true" />	
                            <sjg:gridColumn name="digitalSign" index="u.securitymechanism" title="Security Mechanism" sortable="true" />
                            <sjg:gridColumn name="createdtime" index="u.createdtime" title="Created Time" sortable="true" />
                            
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

