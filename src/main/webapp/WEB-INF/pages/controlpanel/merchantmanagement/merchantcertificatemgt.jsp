<!--
        created on: Nov 22, 2013, 11:57:49 AM
        auther:     Thushanth
-->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>

<!DOCTYPE HTML>

<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf"%>

        <script type="text/javascript">

            function clearMesssage() {
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


                    <!-- Form -->
                    <div class="content-form" style="min-height:150px">
                        <s:form id="MerchantCertificateManagerdownload" method="post"
                                action="downloadMerchantCertificateManager" theme="simple" cssStyle="float:left;width:50%">

                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" style="width: 319px;max-width: 300%;">Download Your Certificate &nbsp;&nbsp;&nbsp;</label>
                                        <sj:submit cssClass="btn btn-sm btn-functions" button="true" value="Download"
                                                   id="downButton" onclick="clearMesssage()"/>
                                        <sj:submit cssClass="btn btn-sm btn-functions" cssStyle="visibility:hidden;" />
                                    </div>
                                </div>
                            </div>
                        </s:form>

                        <s:form id="keystoreDCertificateManagerdownload" method="post"
                                action="keystoreDMerchantCertificateManager" theme="simple" cssStyle="float:right;width:50%">

                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label" style="width: 319px;max-width: 300%;">Download Your key Store &nbsp;&nbsp;&nbsp;</label>
                                        <sj:submit cssClass="btn btn-sm btn-functions" button="true" value="Download"
                                                   id="keystoreDButton" onclick="clearMesssage()"/>
                                        <sj:submit cssClass="btn btn-sm btn-functions" cssStyle="visibility:hidden;" />
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


