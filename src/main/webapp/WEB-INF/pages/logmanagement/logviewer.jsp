<%-- 
    Document   : logviewer
    Created on : 22/10/2014, 12:31:31 PM
    Author     : asela
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE HTML>

<html>
    <head>

        <%@include file="/WEB-INF/pages/template/header.jspf" %>        
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ColumnFilterWidgets.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ColumnFilterWidgets.css" type="text/css" />

        <script type="text/javascript">
            $(document).ready(function () {
                $("#logview").dataTable({
                    "sPaginationType": "full_numbers",
                    "bJQueryUI": true,
                    "sDom": 'W<"clear">lfrtip',
                    "oColumnFilterWidgets": {
                        "aiExclude": [0, 1, 5, 6, 7]
                    },
                    "aoColumnDefs": [{'bSortable': false, 'aTargets': [0, 6, 7]}]
                })
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

                <div class="content-table">
                    <!--this div for triangles: don't delete-->
                    <div></div>
                    <table width="700px;" cellpadding="0" cellspacing="0" border="1" class="display " id="logview">
                        <thead>
                            <tr class="gradeB">
                                <th></th>
                                <th>Name</th>
                                <th>Date / Time</th>
                                <th>Type</th>
                                <th>Category</th>
                                <th>Size(Bytes)</th>
                                <th>Download</th>
                                <th>View</th>
                            </tr>
                        </thead>
                        <tbody>
                            <s:iterator value="logFiles">                           
                                <tr class="gradeD">
                                    <td><input type="checkbox" id="${file.logFile.name}" name="selected"  value="${file.logFile.path}"></td>
                                    <td style="font-weight: bold"><s:property value="name"/></td>
                                    <td><s:property value="date"/></td>
                                    <td style="color:${color}; font-weight: bold"><s:property value="type"/></td>
                                    <td><s:property value="logFileCategory"/></td>
                                    <td><s:property value="length"/></td>
                                    <td><a href="<%=request.getContextPath()%>/DownloadLogs.ipg?path=<s:property value="logFile.path"/>"><img src="<%=request.getContextPath()%>/resources/img/logmanagement/download.png" width="20" height="20" alt="Download Log File" title="Download Log File"/> </a></td>
                                    <td><a href="<%=request.getContextPath()%>/ViewDetailLogs.ipg?path=<s:property value="logFile.path"/>"><img src="<%=request.getContextPath()%>/resources/img/logmanagement/view.png" width="20" height="20" alt="View Log File" title="View Log File"/> </a> </td>
                                </tr>
                            </s:iterator>

                        </tbody>
                    </table>   
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