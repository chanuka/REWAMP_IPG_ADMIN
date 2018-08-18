<%-- 
    Author     : chanuka
--%>
<%@taglib uri="/struts-tags" prefix="s" %>
<script>
    function clearCookie() {
        window.localStorage.removeItem("selectedsec");
        window.localStorage.removeItem("selectedpage");
        window.localStorage.removeItem("scroll");
    }
</script>




<header>
    <div id="ipg-redline"></div>
    <div class="ipg-left">
        <span class="ipg-text1">IPG</span>
        <span class="ipg-text2">
            Internet Payment Gateway
        </span>
    </div>
    <div class="ipg-right">
        <div class="ipg-cpass" title="Change Password">
            <a style="text-decoration: none" href='<%=request.getContextPath()%>/ViewChangePassword.ipg?message=error3' ><i class="fa fa-ellipsis-h" aria-hidden="true"></i></a>
        </div>
        <div class="ipg-logout" title="Logout">
            <a href="<%=request.getContextPath()%>/LogoutUserLogin.ipg?message=error3" onclick="clearCookie();"><i class="fa fa-power-off" aria-hidden="true"></i></a>
        </div>
    </div>
    <div class="ipg-headerLine"></div>
</header>
