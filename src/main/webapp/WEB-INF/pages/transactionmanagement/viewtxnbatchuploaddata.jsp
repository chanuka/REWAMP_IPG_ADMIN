<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML
>

<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf" %>
        <script type="text/javascript">


            function backToMain() {
                window.location = "${pageContext.request.contextPath}/viewIPGTxnBatchUploadServlet.ipg?";
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
                <div class="content-form">
                    <s:form id="view" method="post" action="IPGTxnBatchUploadServlet" theme="simple" >
                        <table border="0" cellspacing="5">
                            <tbody>

                                <tr>
                                    <td></td>
                                    <td>
                                    </td>
                                </tr>

                                <tr>
                                    <td></td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td></b></td>
                                    <td><s:textarea name="content" readonly="true" value="%{content}" cols="85%" rows="10%"  />
                                    </td>
                                </tr>

                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td>
                                        <sj:a href="#" name="back" button="true" onclick="backToMain()">Back</sj:a>

                                        </td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
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
