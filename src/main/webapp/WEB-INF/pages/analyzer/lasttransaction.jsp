<!--
        Created on : Dec 5, 2013, 1:28:15 PM
        author     : thushanth
-->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>


<!DOCTYPE HTML>

<html>
    <head>

        <%--<%@include file="/WEB-INF/pages/template/header.jspf"%>--%>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/js_chart/jquery.jqplot.min.css" />  
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/scripts/examples.min.css" />
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/scripts/shCoreDefault.min.css" />
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/scripts/shThemejqPlot.min.css" />

        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/js_chart/jquery.min.js"></script> 
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/js_chart/jquery.jqplot.min.js"></script>
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/js_chart/jqplot.barRenderer.min.js"></script>   
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/js_chart/jqplot.categoryAxisRenderer.min.js"></script>
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/js_chart/jqplot.canvasAxisLabelRenderer.min.js"></script>
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/js_chart/jqplot.canvasTextRenderer.min.js"></script>
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/js_chart/jqplot.pointLabels.min.js"></script>
        <script  type="text/javascript" src="${pageContext.request.contextPath}/resources/css/scripts/jqplot.highlighter.min.js"></script>

        <script type="text/javascript">
            window.onload = function () {

                $.ajax({
                    url: '${pageContext.request.contextPath}/findLastTransaction.ipg',
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        var msg = data.msg;
                        if (msg) {
                            document.getElementById("error").innerHTML = "<b><font color='red'; size='2'; >" + data.msg + "</font></b>";

                            if (data.timeDiffList) {
                                barchart(data);
                            }
                        } else {
                            barchart(data);
                        }
                    },
                    error: function (data) {
                        alert("Error occurred while processing.");
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.ipg?";
                    }
                });
            };

            $(document).ready(function () {
                var links_1 = $('.sidebar-links > div > a');

                links_1.click('click', function () {

                    if ($(this).parent().hasClass('selected')) {
                        if ($(this).next().children().hasClass('active')) {
                            $(this).css({'background-color': '#9E9E9E', 'color': '#ffffff'});

                            links_1.parents().removeClass('selected');
                        } else {
                            $(this).parent().removeClass('selected');
                        }

                    } else {
                        for (var i = 0; i < links_1.parents().length; i++) {
                            if (links_1.eq(i).parents().hasClass('selected')) {
                                var foo = false;
                                if (links_1.eq(i).next().children().hasClass('active')) {
                                    foo = true;
                                }
                                if (!foo) {
                                    links_1.eq(i).parents().removeClass('selected');
                                }

                            }
                        }
                        $(this).parent().addClass('selected');
                    }
                });
            });



            function barchart(data) {

                plot1 = $.jqplot('chart1', [data.timeDiffList], {
                    // Only animate if we're not using excanvas (not in IE 7 or IE 8)..
                    //        animate: !$.jqplot.use_excanvas,
                    animate: true,

                    animateReplot: true,
                    title: 'Transaction Response Time Analyser For the Last Completed Transaction',

                    seriesDefaults: {
                        renderer: $.jqplot.BarRenderer,
                        pointLabels: {show: true},
                        rendererOptions: {
                            varyBarColor: true,
                            barWidth: 20

                        }
                    },
                    axes: {
                        xaxis: {
                            renderer: $.jqplot.CategoryAxisRenderer,
                            label: 'Stages',
                            ticks: data.stageList
                        },
                        yaxis: {
                            label: 'Milliseconds (millsec)',
                            labelRenderer: $.jqplot.CanvasAxisLabelRenderer
                        }
                    },
                    highlighter: {show: false}
                });


                $('#chart1').bind('jqplotDataHighlight',
                        function (ev, seriesIndex, pointIndex, dataa) {
                            $('#info1a').html('Stage <b>' + data.stageList[(dataa[0]) - 1] + '</b> took <b>' + dataa[1] + '</b> Milliseconds.');

                        }
                );

                $('#chart1').bind('jqplotDataUnhighlight',
                        function (ev) {
                            $('#info1a').html('Use the Pointer for details.');
                        }
                );
            }
            ;

        </script>

    </head>
    <body>
        <!--for background wallpaper-->
        <div class="background-wallpaper"></div>
        <!--for header-->
        <jsp:include page="/WEB-INF/pages/template/subheader.jsp"/>
        <!--for side navigation bar-->
        <jsp:include page="/WEB-INF/pages/template/tree2.jsp"/>

        <!--section > main content-->
    <section> 
        <div class="container-main">
            <div class="content-main">

                <!--for bread Crumb-->
                <jsp:include page="/WEB-INF/pages/template/breadcrumb.jsp"/>

                <div class="content-message">

                </div>
                <div class="content-form">
                    <table width="900" border="0">
                        <tr>
                            <td valign="top" width="500">				 
                                <!-----------------------Start of bar chart---------------------- -->

                                <div id='chart1' style='margin-top:10px; margin-left:20px; width:500px; height:600px;'></div><br>

                                <div><b>INFO:  &nbsp;&nbsp; </b><span id="info1a"></span></div>

                                <!-----------------------End of bar chart---------------------- -->
                            </td>

                            <td  valign="top">		
                                <!-----------------------Start of description---------------------- -->
                                <br><br><br><br>
                                <table width="400" border="0" cellspacing="15px" >
                                    <tr>
                                        <td width="25" >1</td>
                                        <td>Transaction Initiated - Transaction Request Received</td>
                                    </tr>
                                    <tr>
                                        <td>2</td>
                                        <td>Transaction Request Received - Transaction Validated</td>
                                    </tr>
                                    <tr>
                                        <td>3</td>
                                        <td>Transaction Validated - Transaction Verification Request Created</td>
                                    </tr>
                                    <tr>
                                        <td>4</td>
                                        <td>Transaction Verification Request Created - Transaction Verification Request Sent</td>
                                    </tr>
                                    <tr>
                                        <td>5</td>
                                        <td>Transaction Verification Request Sent - Transaction Verification Response Received</td>
                                    </tr>
                                    <tr>
                                        <td>6</td>
                                        <td>Transaction Verification Response Received - Transaction Engine Request Created</td>
                                    </tr>
                                    <tr>
                                        <td>7</td>
                                        <td>Transaction Engine Request Created - Transaction Engine Request Sent</td>
                                    </tr>
                                    <tr>
                                        <td>8</td>
                                        <td>Transaction Engine Request Sent - Transaction Completed</td>
                                    </tr>
                                    <tr>
                                        <td>9</td>
                                        <td>Transaction Completed - Transaction Engine Response Received</td>
                                    </tr>
                                    <tr>
                                        <td>10</td>
                                        <td>Transaction Engine Response Received - IPG Response Sent</td>
                                    </tr>
                                    <tr>
                                        <td>11</td>
                                        <td>IPG Response Sent - Transaction Complete Confirmed</td>
                                    </tr>
                                </table>
                                <!-----------------------End of description---------------------- -->
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
    </section>
    <footer>
        <!--for footer-->
        <jsp:include page="/WEB-INF/pages/template/footer.jsp"/>
    </footer>

</body>
</html>




