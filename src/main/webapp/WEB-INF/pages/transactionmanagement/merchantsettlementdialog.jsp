<%-- 
    Document   : merchantsettlementdialog
    Created on : 15 Oct, 2014, 8:53:14 AM
    Author     : asela
--%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="/struts-jquery-tags" prefix="sj" %>
<!-- ----------------------------dual authorization form start --------------------------------------------- -->             
<script type="text/javascript">

    function resetAllData() {
        $('#uname').val("");
        $('#passwordval').val("");
    }

    function closeDialog() {
        $("#remotedialog").dialog('close');
        return false;
    }
</script>
<div id="content1">   
    <s:div id="divmsg">
        <s:actionerror theme="jquery"/>
        <s:actionmessage theme="jquery"/>
    </s:div>

    <s:form id="dialogauth" method="post" action="MerchantTransactionSettlement" theme="simple" >
        <s:hidden type="hidden" name="merchantId" value="%{bean.merchantid}" />
        <s:hidden type="hidden" name="batchId" value="%{bean.batchid}" />
        <s:hidden type="hidden" name="terminalId" value="%{bean.terminalid}" />

        <table align="center" cellpadding="5" cellspacing="5" >                          
            <tr>
                <td colspan="2" class="Title" align="center">Dual Authorization</td>
            </tr>
            <tr>
                <td class="label">              
                    Username:</td>
                <td align="center">
                    <s:textfield id="uname" name="uname"/>   
                </td>
            </tr>
            <tr>
                <td class="label">              
                    Password:</td>
                <td align="center">
                    <s:password id="passwordval" name="passwordval"/>   
                </td>
            </tr>
            <tr>
                <td><s:url var="authurl" action="DualAuthMerchantTransactionSettlement"/></td>
                <td>
                    <sj:submit button="true" href="%{authurl}" value="Authorize" targets="divmsg" />  
                    <sj:submit button="true" href="%{authurl}" value="Cancel"  onclick="closeDialog()"/>  
                </td>
            </tr>                      
        </table>
    </s:form>
</div>
<div class="clearer"><span></span></div>
<!-- ----------------------------dual authorization form end --------------------------------------------- -->  