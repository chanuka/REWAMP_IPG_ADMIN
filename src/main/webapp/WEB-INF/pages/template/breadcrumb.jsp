<%-- 
    Document   : breadcrumb
    Created on : Sep 11, 2017, 2:39:46 PM
    Author     : prathibha_s
--%>


<% if (session.getAttribute("CURRENTSECTION") != null && !session.getAttribute("CURRENTSECTION").equals("")) {%>
<div class="content-breadcrumb">
    <div>
        <span>
            <h>${CURRENTSECTION}</h>
        </span>
        <span>
            <h>
                <% if (session.getAttribute("CURRENTSECTION") != null && !session.getAttribute("CURRENTSECTION").equals("")) {%> 
                &nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-caret-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;
                <% }%>${CURRENTPAGE}
            </h>
        </span>
    </div>
</div>
<%  } else { %>
<div class="content-breadcrumb">
    <div>
        <span>
            <h>IPG Home Page</h>
        </span>
    </div>
</div>
<%  }%>
