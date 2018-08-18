<%-- 
    Document   : commonconfig
    Created on : Jul 12, 2018, 4:52:27 PM
    Author     : sivaganesan_t
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

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Edit' onClick='javascript:editCommonconfigInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display:inline-table;border:none;'/></a>";
            }

            function editCommonconfigInit(keyval) {
                $("#updatedialog").data('code', keyval).dialog('open');
            }

            $.subscribe('openviewcommonconfigtopage', function (event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailCommonConfig.ipg?code=" + $led.data('code'));
            });

            function editCommonconfig(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findCommonConfig.ipg',
                    data: {code: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {
                            $('#code').val(data.code);
                            $('#description').val(data.description);
                            $('#value').val(data.value);

                            $('#updateButton').button("enable");

                        }
                    },
                    error: function (data) {
                        $("#editerrordialog").html("Error occurred while processing.").dialog('open');
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            }

            function resetAllData() {
                $('#code').val("");
                $('#description').val("");
                $('#value').val("");
                $('#divmsg').text("");

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        code: '',
                        description: '',
                        value: '',
                        search: false
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            }

            function resetFieldData() {
                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function searchCommonConfig() {
                var code = $('#code').val();
                var description = $('#description').val();
                var value = $('#value').val();

                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        code: code,
                        description: description,
                        value: value,
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

                    <s:set var="vupdatebutt"><s:property value="vupdatebutt" default="true"/></s:set>
                    <s:set var="vupdatelink"><s:property value="vupdatelink" default="true"/></s:set>
                    <s:set var="vsearch"><s:property value="vsearch" default="true" /></s:set>

                        <!-- Form -->
                        <div class="content-form">
                        <s:form id="commonconfig" method="post" action="CommonConfig" theme="simple" >

                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Code </label>
                                        <s:textfield cssClass="form-control" id="code" name="code" maxLength="15" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Description</label>
                                        <s:textfield  cssClass="form-control" id="description"   name="description" maxLength="20" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" >Value</label>
                                        <s:textfield  cssClass="form-control" id="value"   name="value" maxLength="128" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                    </div>
                                </div>                            
                            </div>                        
                        </s:form>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <sj:submit cssClass="btn btn-sm btn-functions" button="true" href="#" value="Search" id="search"  disabled="#vsearch" onclick="searchCommonConfig()"/>
                                    <sj:submit cssClass="btn btn-sm btn-reset" button="true" value="Reset" name="reset" onClick="resetAllData()" />
                                </div>
                            </div>
                            <div class="col-sm-6"></div>
                        </div>
                    </div>

                    <!-- Start edit process dialog box -->
                    <sj:dialog 
                        id="editerrordialog" 
                        buttons="{
                        'OK':function() { $( this ).dialog( 'close' );}                                    
                        }" 
                        autoOpen="false" 
                        modal="true" 
                        title="Edit error."
                        />   

                    <sj:dialog                                     
                        id="updatedialog"                                 
                        autoOpen="false" 
                        modal="true" 
                        position="center"
                        title="Update Common Configuration"
                        onOpenTopics="openviewcommonconfigtopage" 
                        loadingText="Loading .."
                        width="1000"
                        height="500"
                        cssStyle="overflow: visible;"
                        dialogClass= "dialogclass"
                        /> 

                    <div class="content-table">
                        <!--this div for triangles: don't delete-->
                        <div></div>
                        <s:url var="listurl" action="listCommonConfig"/>
                        <sjg:grid
                            id="gridtable"
                            caption="Common Configuration"
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
                            <sjg:gridColumn name="code" index="commonconfig.code" title="Edit" width="25" align="center" formatter="editformatter" hidden="#vupdatelink"/>
                            <sjg:gridColumn name="code" index="commonconfig.code" title="Code"  sortable="true"/>
                            <sjg:gridColumn name="description" index="commonconfig.description" title="Description"  sortable="true"/>
                            <sjg:gridColumn name="value" index="commonconfig.value" title="Value"  sortable="true"/>
                            <sjg:gridColumn name="createdtime" index="commonconfig.createdtime" title="Created Time"  sortable="true"/>
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
