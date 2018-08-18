<%-- 
    Document   : newjsp
    Created on : May 4, 2018, 10:52:03 AM
    Author     : prathibha_s
--%>

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
                
            </div>

            
            
            

            <div class="content-table">
            <!--this div for triangles: don't delete-->
            <div></div>
            
            
            
             </div>
            </div>
        </section>
        <footer>
            <!--for footer-->
            <jsp:include page="/WEB-INF/pages/template/footer.jsp"/>
        </footer>
            
            
            