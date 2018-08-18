<%-- 
    Author     : CHANUKA
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.epic.epay.ipg.util.common.IPGSectionComparator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.Set"%>
<%@page import="com.epic.epay.ipg.util.varlist.SessionVarlist"%>
<%@page import="java.util.List"%>
<%@ page  import="com.epic.epay.ipg.util.mapping.Ipgsectionpage" %>
<%@ page  import="com.epic.epay.ipg.util.mapping.Ipgpage" %>
<%@ page  import="com.epic.epay.ipg.util.mapping.Ipgsection" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src='<%=request.getContextPath()%>/resources/js/dtree.js'></script>
        <script type="text/javascript" src='<%=request.getContextPath()%>/resources/js/yahoo-dom-event.js'></script>
        <link rel="StyleSheet" href='<%=request.getContextPath()%>/resources/css/dtree.css' type="text/css"/>
        <title>IPG CONTROL PANEL</title>

        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.blockUI.js"></script>  
        <script type="text/javascript">
        
            $(document).ready(function(){        
                $(document).ajaxStart(function () {
                    $.blockUI({ css: {
                            border:'transparent',
                            backgroundColor: 'transparent'
                        },
                        message: '<img height="100" width="100" src="${pageContext.request.contextPath}/resources/img/loading.gif" />',
                        baseZ: 2000
                    });  
            
                });
                $(document).ajaxStop(function () {
                    $.unblockUI();
                });
         
            });
        
        
        </script>

    </head>
    <body>


        <%

            try {

                HashMap<Ipgsection, List<Ipgpage>> sectionPageList = (HashMap<Ipgsection, List<Ipgpage>>) request.getSession().getAttribute(SessionVarlist.SECTIONPAGELIST);

        %>


        <div class="dtree" style="height: 535px">

            <p><a href="javascript: d.openAll();">Open All</a> | <a href="javascript: d.closeAll();">Close All</a></p>

            <script type="text/javascript">
		

                var d = new dTree('d');
		

                d.add(0,-1,'IPG');
                //d.add(1,0,'User');
                <%
                IPGSectionComparator sec1 = new IPGSectionComparator();
                        
                Set<Ipgsection> section = new TreeSet<Ipgsection>(sec1);
                
                        Set<Ipgsection> section1 = sectionPageList.keySet();
                        for (Ipgsection sec2 : section1) {
                            section.add(sec2);
                        }


                        int i = 0;
                        int j = 0;
                        for (Ipgsection sec : section) {
                            i = j + 1;
                            j = i;
                            out.println("d.add(" + i + "," + 0 + ",\'" + sec.getDescription() + "\');");
                            List<Ipgpage> pageList = sectionPageList.get(sec);
                            for (Ipgpage pageBean : pageList) {
                                j++;
                                out.println("d.add(" + j + "," + i + ",\'" + pageBean.getDescription() + "\'" + ",\' " + request.getContextPath() + pageBean.getUrl() + ".ipg\');");
                            }

                        }


                    } catch (Exception ee) {

                        ee.printStackTrace();
                    }
                %>


                    document.write(d);

		
            </script>
          

        </div>


    </body>
</html>
