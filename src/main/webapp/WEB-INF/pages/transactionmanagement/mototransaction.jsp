<%-- 
    Document   : mototransaction
    Created on : 04/12/2013, 4:58:13 PM
    Author     : badrika
--%>

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

            function resetAllData() {

                $('#orderCode').val("");
                $('#amount').val("");
                $('#currency').val("");
                $('#cardType').val("");
                $('#cardNo').val("");
                $('#expiryDate').val("");
                $('#cardHolderName').val("");
                $('#divmsg').text("");

            }

            function resetFieldData() {

                $('#orderCode').val("");
                $('#amount').val("");
                $('#currency').val("");
                $('#cardType').val("");
                $('#cardNo').val("");
                $('#expiryDate').val("");
                $('#cardHolderName').val("");
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

                <s:set id="vadd" var="vadd"><s:property value="vadd" default="true"/></s:set>
                    <!-- Form -->
                    <div class="content-form">
                    <s:form id="mototransaction" method="post" action="MotoTransaction" theme="simple" >


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
                                    <td>Order Code <span style="color: red">*</span></td>
                                    <td><s:textfield name="orderCode" id="orderCode" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Amount <span style="color: red">*</span></td>
                                    <td><s:textfield  name="amount" id="amount" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                    </td>                                      
                                </tr>

                                <tr>
                                    <td>Currency <span style="color: red">*</span></td>
                                    <td>
                                        <s:select  id="currency" list="%{currencyList}"  name="currency" headerKey="" headerValue="--Select--" listKey="currencyisocode" listValue="description" />
                                    </td>

                                </tr>
                                <tr>
                                    <td>Card Type <span style="color: red">*</span></td>
                                    <td>
                                        <s:select  id="cardType" list="%{cardTypeList}"  name="cardType" headerKey="" headerValue="--Select--" listKey="cardassociationcode" listValue="description" />
                                    </td>

                                </tr>
                                <tr>
                                    <td>Card No <span style="color: red">*</span></td>
                                    <td><s:textfield  name="cardNo" id="cardNo" maxLength="16" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                    </td>                                         
                                </tr>
                                <tr>
                                    <td>Expiry Date (yymm) <span style="color: red">*</span></td>
                                    <td><s:textfield  name="expiryDate" id="expiryDate" maxLength="4" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9 ]/g,''))"/>
                                    </td>                                         
                                </tr>
                                <tr>
                                    <td>Card Holder Name <span style="color: red">*</span></td>
                                    <td><s:textfield  name="cardHolderName" id="cardHolderName" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                    </td>                                         
                                </tr>

                                <tr>
                                    <td><span class="mandatoryfield">Mandatory fields are marked with *</span></td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td> <s:url var="addurl" action="AddMotoTransaction"/>
                                    <td>

                                        <sj:submit button="true" href="%{addurl}" value="Submit" targets="divmsg" id="addButton"  disabled="#vadd"/>

                                        <sj:submit button="true" value="Reset" name="reset" onClick="resetAllData()"  />
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


