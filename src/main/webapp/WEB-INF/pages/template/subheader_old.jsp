<%-- 
    Author     : chanuka
--%>
<%@taglib uri="/struts-tags" prefix="s" %>

<div>


    <table style="width: 100%">
        <tr>

            <% if (session.getAttribute("CURRENTSECTION") != null && !session.getAttribute("CURRENTSECTION").equals("")) {%>
            <td><div id="leftbreadcrumb"><h> ${CURRENTSECTION}</h><h><% if (session.getAttribute("CURRENTSECTION") != null && !session.getAttribute("CURRENTSECTION").equals("")) {%>---><% }%>${CURRENTPAGE}</h></div></td>
          <%  } else{ %>
            <td><div id="leftbreadcrumb"><h>IPG Home Page</h></div></td>
          <%  } %>

            <td align="right">        Logged as :&nbsp;<B>${SYSTEMUSER.username}</B>
                <a href='<%=request.getContextPath()%>/LogoutUserLogin.ipg' ><B>Log Out</B></a>&nbsp;&nbsp;&nbsp; 
                <a href='<%=request.getContextPath()%>/ViewChangePassword.ipg' ><B>Change Password</B></a>

            </td>

        </tr>
    </table>


</div>




