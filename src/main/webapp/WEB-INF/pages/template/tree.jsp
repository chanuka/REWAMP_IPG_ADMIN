<%-- 
    Document   : tree
    Created on : May 2, 2018, 2:13:19 PM
    Author     : prathibha_s
--%>

<%@page import="java.util.Collections"%>
<%@page import="com.epic.epay.ipg.util.mapping.Ipgpage"%>
<%@page import="com.epic.epay.ipg.util.mapping.Ipgsection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.epic.epay.ipg.util.common.IPGSectionComparator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.Set"%>
<%@page import="com.epic.epay.ipg.util.varlist.SessionVarlist"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<html>
    <head>

        <%--<%@include file="/WEB-INF/pages/template/header_top.jspf" %>--%>


        <script type="text/javascript">

            $(document).ready(function () {
                $(document).ajaxStart(function () {
                    $.blockUI({css: {
                            border: 'transparent',
                            backgroundColor: 'transparent'
                        },
                        message: '<img height="100" width="100" src="${pageContext.request.contextPath}/resources/nav/lodingSVG/bar.svg" />',
                        baseZ: 2000000
                    });

                });
                $(document).ajaxStop(function () {
                    $.unblockUI();
                });

            });

//          for resize table
            function refreshTable() {
                $("#gridtable").jqGrid('setGridWidth', $(".content-table").width());
            }
            setTimeout(refreshTable, 1000);



            function sectionClick(sec, pge) {
                var scroll = $(".sidebar-left-collapse").scrollTop();
                window.localStorage.setItem('scroll', scroll);

                window.localStorage.setItem("selectedsec", sec.id)
                window.localStorage.setItem("selectedpage", pge.id)

            }

            $(document).ready(function () {
                var idsec = window.localStorage.getItem("selectedsec");
                var idpge = window.localStorage.getItem("selectedpage");
                var idsecval = "#" + idsec;
                var idpgeval = "#" + idpge;
                $(idsecval).addClass('selected');
                $(idpgeval).addClass('active');
                
                var scroll = window.localStorage.getItem("scroll");
                $(".sidebar-left-collapse").scrollTop(scroll);
            });



        </script>


    </head>
    <body>

    <aside class="sidebar-left-collapse">
        <div class="company-logo">
            <!--<img src="${pageContext.request.contextPath}/resources/nav/images/ipg.svg" width="70px" height="auto" />-->
            <!--<h5 style="color:white">-->
            <!--<span style="color: #fdc60d;text-outline:2px 2px #ff0000; ">Bank of Ceylon</span></h5>-->
            <h6 style="color:white;margin:0px;padding: 2px;background:#607D8B;">Logged as : ${SYSTEMUSER.username}</h6>
            <h6 style="color:white;margin:0px;padding: 2px;background: #0050c3;">User Role : ${SYSTEMUSER.ipguserroleByUserrolecode.userrolecode}</h6>
        </div>
        <div class="sidebar-links">
            <%

                try {

                    HashMap<Ipgsection, List<Ipgpage>> sectionPageList = (HashMap<Ipgsection, List<Ipgpage>>) request.getSession().getAttribute(SessionVarlist.SECTIONPAGELIST);

                    IPGSectionComparator sec1 = new IPGSectionComparator();

                    TreeSet<Ipgsection> section3 = new TreeSet<Ipgsection>(sec1);
                    TreeSet<Ipgsection> section = new TreeSet<Ipgsection>(sec1);

                    Set<Ipgsection> section1 = sectionPageList.keySet();
                    for (Ipgsection sec2 : section1) {
                        section3.add(sec2);
                    }
                    for (Ipgsection elem : section3) {
                        section.add(elem);
                    }

                    int sect = 0;
                    int pge = 1000;

                    for (Ipgsection sec : section) {

                        String secID = "sec_" + sect;

                        out.println("<div id=" + secID + " class='link-default'>");
                        out.println("<a href='javascript:void(0)'>" + sec.getDescription() + "</a>");
                        out.println("<ul class='sub-links'>");
                        List<Ipgpage> pageList = sectionPageList.get(sec);
                        for (Ipgpage pageBean : pageList) {

                            String pageID = "pge_" + pge;
                            out.println("<li id=" + pageID + ">");
                            out.println("<a onclick='sectionClick(" + secID + "," + pageID + ")' href='" + request.getContextPath() + pageBean.getUrl() + ".ipg'" + ">" + pageBean.getDescription() + "</a>");
                            out.println("</li>");
                            pge++;
                        }
                        out.println("</ul>");
                        out.println("</div>");
                        sect++;

                    }

                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            %>

        </div>
    </aside>


    <!--<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>-->
    <script src="${pageContext.request.contextPath}/resources/nav/js/index.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.blockUI.js"></script>

</body>
</html>

