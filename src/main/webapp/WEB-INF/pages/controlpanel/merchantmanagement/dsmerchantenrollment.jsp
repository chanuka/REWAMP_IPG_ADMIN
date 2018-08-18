<%-- 
    Document   : dsmerchantenrollment
    Created on : 16/09/2014, 2:05:14 PM
    Author     : badrika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML>

<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf" %>

        <script type="text/javascript">

            function loadMerchant(param) {

//                var cardassociation = $('#cardassociation').val();
                var cardassociation = param;

                if (!cardassociation) {
                    window.location = "${pageContext.request.contextPath}/viewDSMerchantEnrollment.ipg?";
                    var restext = event.originalEvent.request.responseText;
                    $('#divmsg').html(restext);
                }

                $('#divmsg').text("");
                var search = true;
                var again = false;

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        cardassociation: cardassociation,
                        search: search,
                        again: again
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});

                jQuery("#gridtable").trigger("reloadGrid");

//                $('#updateButton').button("enable");
//                $('#addButton').button("enable");
            }

            function addMerchant() {

                $('#divmsg').text("");
                var cardassociation = $('#cardassociation').val();
                var search = true;
                var again = true;

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        cardassociation: cardassociation,
                        search: search,
                        again: again
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});

                jQuery("#gridtable").trigger("reloadGrid");
            }

            function createExcel() {

                var recors = $("#gridtable").jqGrid('getGridParam', 'selarrrow');

                var qwe = new Array();
                for (i = 0; i < recors.length; i++) {
                    qwe[i] = $("#gridtable").jqGrid('getCell', recors[i], "merchantid");
                }
//                if(!qwe[0]){
//                    alert("no records")
//                }
//                return; 

                $('#selectedList').val(qwe);
            }

            $.subscribe('anyerrors', function (event, data) {
//                loadMerchant(false);

                var restext = event.originalEvent.request.responseText;

                $('#divmsg').html(restext);
                jQuery("#gridtable").trigger("reloadGrid");

//empty the list




            });

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
                <%--<s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>--%>
                <!-- Form -->
                <div class="content-form">
                    <s:form id="dsmerchantenrolment" method="post" action="createExcelDSMerchantEnrollment" theme="simple" >
                        <s:hidden id="selectedList" name="selectedList" />  
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label" >Card Association  <span style="color: red">*</span></label>
                                    <s:select cssClass="form-control" id="cardassociation" list="%{cardAssociationList}"  name="cardassociation" headerKey="" headerValue="--Select--" 
                                              listKey="cardassociationcode" listValue="description" onchange="loadMerchant(this.value)" />
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <label class="control-label"> <span class="mandatory-field">Mandatory fields are marked with <span>*</span></span></label>
                            </div>
                        </div> 

                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" value="Create Excel" id="addButton"  onclick="createExcel()"  />
                                    <sj:a cssClass="btn btn-sm btn-functions" button="true" id="updateButton" onclick="addMerchant()" >All Merchants</sj:a>
                                </div>
                            </div>
                        </div>

                    </s:form>
                </div>

                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <s:url var="listurl" action="ListDSMerchantEnrollment"/>
                    <sjg:grid
                        id="gridtable"
                        caption="DS Merchant Enrollment"
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
                        multiselect="true"

                        onErrorTopics="anyerrors" 
                        >                            
                        <%--<sjg:gridColumn name="merchantid" id="check" title="Select"  editable="true" edittype="checkbox" formatter="checkbox" align="center" editoptions="{ value:'True:False'}" formatoptions="{disabled : false}"/>--%>
                        <sjg:gridColumn name="merchantid" index="u.ipgmerchant.merchantid" title="Merchant ID"  sortable="true"/>
                        <sjg:gridColumn name="merchantname" index="u.ipgmerchant.merchantname" title="Merchant Name"  sortable="true"/>
                        <sjg:gridColumn name="cardassociation" index="u.ipgcardassociation.description" title="Card Association" sortable="true"/>
                        <sjg:gridColumn name="dsstatus"  index="u.directoryserver" title="Excel Generated Status"  sortable="true"/>

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



