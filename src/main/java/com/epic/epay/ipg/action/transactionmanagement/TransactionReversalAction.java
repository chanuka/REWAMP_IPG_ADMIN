/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.TransactionDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.TransactionSearchBean;
import com.epic.epay.ipg.dao.transactionmanagement.TransactionVoidDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.IPGSwitchClient;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.epic.epay.ipg.util.varlist.TransactionCodeVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author badrika
 */
public class TransactionReversalAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    TransactionSearchBean inputBean = new TransactionSearchBean();
    TransactionVoidDAO dao = null;
    private String messageToEngine = null;
    private List transactionSwitchResponseLst = null;
    private boolean transactionSwitchResponseFlag = false;
    private IPGSwitchClient iPGSwitchClient = null;

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called TransactionReversalAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.TRANSACTION_REVERSAL;
        String task = null;
        if ("View".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Search".equals(method)) {
            task = TaskVarList.SEARCH_TASK;
        } else if ("Reversal".equals(method)) {
            task = TaskVarList.REVERSAL_TASK;
        }
        if ("execute".equals(method)) {
            status = true;
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            status = new Common().checkMethodAccess(task, page, userRole, request);
        }
        return status;
    }

    public String View() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                dao = new TransactionVoidDAO();
                inputBean.setCardTypeList(dao.getCardTypeList());
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_TRANSACTION));

            } else {
                result = "loginpage";
            }

            System.out.println("called TransactionReversalAction :View");


        } catch (Exception ex) {
            addActionError("Transaction Reversal " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionReversalAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String Search() {
        System.out.println("called TransactionReversalAction : Search");
        try {

            if (inputBean.isSearch()) {

                int rows = inputBean.getRows();
                int page = inputBean.getPage();
                int to = (rows * page);
                int from = to - rows;
                long records = 0;
                String orderBy = "";
                if (!inputBean.getSidx().isEmpty()) {
                    orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
                }

                dao = new TransactionVoidDAO();
                String message = this.validateInputs();

                if (message.isEmpty()) {
                    HttpServletRequest request = ServletActionContext.getRequest();
                    HttpSession session = request.getSession(false);
                    Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
                    inputBean.setLoginUser(user.getUsername());

                    //get the sql query for retrieve  transactions
                    boolean isMerchant = dao.isMerchantUser(inputBean.getLoginUser());
                    if (isMerchant) {
                        boolean isHeadMerchant = dao.isHeadMerchant(inputBean.getLoginUser());
                        if (isHeadMerchant) {
                            boolean txnInitiatedStatusYes = dao.txnInitiatedStatus(inputBean.getLoginUser());
                            if (txnInitiatedStatusYes) {
                                inputBean.setTag(3);
                            } else {
                                inputBean.setTag(2);
                            }
                        } else {
                            inputBean.setTag(2);
                        }
                    } else {
                        inputBean.setTag(1);
                    }

                    List<TransactionDataBean> dataList = dao.getTxnList("reversal", inputBean, rows, from, orderBy);

                    if (!dataList.isEmpty()) {
                        records = dataList.get(0).getFullCount();
                        inputBean.setRecords(records);
                        inputBean.setGridModel(dataList);
                        int total = (int) Math.ceil((double) records / (double) rows);
                        inputBean.setTotal(total);
                    } else {
                        inputBean.setRecords(0L);
                        inputBean.setTotal(0);
                    }

                } else {
                    addActionError(message);
                }
            }

        } catch (Exception ex) {
            addActionError("Transaction reversal " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionVoidAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "list";
    }
    
    public String Reversal() {
// public String Void() {
        System.out.println("called TransactionVoidAction : Void");
        String message = null;
        String retType = "reversal";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            dao = new TransactionVoidDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.REVERSAL_TASK, PageVarList.TRANSACTION_REVERSAL, SectionVarList.TRANSACTIONMANAGEMENT, "Transaction Reversal", null);

            /////
            String transactionId = inputBean.getTxnId();
            String transactionCode = TransactionCodeVarList.IPG_REVERSAL;
            //get merchantId using transactionid by calling getIpGTransactionBeanForSwitch method
            String merchantId = dao.getTransactionDataForswitch(transactionId,"reversal").getTxnId();
            Date dateTime = dao.getTransactionDataForswitch(transactionId,"reversal").getCreatedTime();
            java.sql.Timestamp timeStampDate = new Timestamp(dateTime.getTime());

            //call to createCurrentDateTime method to get the time
            String createdDate = this.createCurrentDateTime(timeStampDate);

            //create the message for the switch
            messageToEngine = transactionCode + "|" + transactionId + "|" + merchantId + "|" + createdDate;

            if (messageToEngine.length() > 0) {

                //communicate with transaction switch & get the response
                transactionSwitchResponseLst = this.communicateWithTxnSwitch(messageToEngine, request, response);
                transactionSwitchResponseFlag = Boolean.parseBoolean(transactionSwitchResponseLst.get(0).toString());


                if (transactionSwitchResponseFlag) {
                    message = dao.saveAudit(audit);
                    inputBean.setMessage(MessageVarList.COMMON_SUCC_REVERSAL);                    
                } else {
                    inputBean.setMessage(transactionSwitchResponseLst.get(1).toString());
                }
            } else {
                //error occured when creating mpi request message
                addActionError(MessageVarList.FAILD_SWITCH_REQUEST_CREATE);
            }



            /////            

        } catch (Exception e) {
            Logger.getLogger(TransactionVoidAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(MessageVarList.COMMON_FAILED_REVERSAL);
        }
        return retType;
    }   
//    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.TRANSACTION_REVERSAL, request);

        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                }
            }
        }
        return true;
    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getTxnId() != null && !Validation.isSpecailCharacter(inputBean.getTxnId())) {
            message = MessageVarList.TXN_VOID_INVALID_TXN_ID;
        } else if (inputBean.getMerchantName() != null && !Validation.isSpecailCharacter(inputBean.getMerchantName())) {
            message = MessageVarList.TXN_VOID_INVALID_MERCHNT_NAME;
        } else if (inputBean.getCardHolderName() != null && !Validation.isSpecailCharacter(inputBean.getCardHolderName())) {
            message = MessageVarList.TXN_VOID_INVALID_CARD_HOLDR_NAME;
        } else if (inputBean.getCardNumber() != null && !Validation.isSpecailCharacter(inputBean.getCardNumber())) {
            message = MessageVarList.TXN_VOID_INVALID_CARD_NUMBER;
        }
        return message;
    }
    
    public String createCurrentDateTime(Timestamp dateInput) throws Exception {
        String currentdatetime = "";
        try {
            String DATE_FORMAT = "MMddHHmmss";

            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

            Date date = new Date(dateInput.getTime());
            currentdatetime = sdf.format(date);
        } catch (Exception ex) {
            throw ex;
        }
        return currentdatetime;
    }
    
     private List communicateWithTxnSwitch(String switchRequestMessage, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List lst = null;
        try {
//            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession(false);

            lst = new ArrayList();
            boolean socketFlag = false;
            String message = null;

            //initiate communicate with txn switch
            //start handle socket communication--------------------------------------------------------------------

            // server reuest format
            // "TxnID"

            String serverRequest = switchRequestMessage;
            String serverResponse = "";

            try {
                //get txn switch congiguration file path
//                String realPath = session.getServletContext().getRealPath("resources/settings/TxnSwitch.xml");
//                String realPath = this.getServletContext().getRealPath("resources/settings/TxnSwitch.xml");

                // Create connection with Txn Switch
                iPGSwitchClient = new IPGSwitchClient();

                // Send data packet to Txn Switch
                iPGSwitchClient.sendPacket(serverRequest);
                socketFlag = true;

            } catch (Exception e) {

                // Scenario 1 - fail to send a request
                lst.add(false);
                message = MessageVarList.FAILED_TO_SEND_MESSAGE_TO_TXN_SWITCH;
                lst.add(message);
            }

            if (socketFlag) {
                try {
                    // server response format
                    // "TxnType"

                    // Data receiving method
                    serverResponse = iPGSwitchClient.receivePacket();

                    //get txn switch response & seperate it.
                    //" Txn code|Txn id|Response code "
                    StringTokenizer tk = new StringTokenizer(serverResponse, "|");
                    if (tk.countTokens() != 3) {
                        lst.add(false);
                        message = MessageVarList.INVALID_TXN_SWITCH_RESPONSE;
                        lst.add(message);
                    } else {
                        lst.add(true); //txn switch communication successfully completed.
                        lst.add(tk.nextToken());
                        lst.add(tk.nextToken());
                        lst.add(tk.nextToken());
                    }


                } catch (Exception e) {
                    // Scenario 2 - fail to receive the response
                    lst.add(false);
                    message = MessageVarList.FAILED_TO_RECEIVE_MESSAGE_FROM_TXN_SWITCH;
                    lst.add(message);

                }
            }
            //end handle socket communication--------------------------------------------------------------------


        } catch (Exception ex) {
            throw ex;
        } finally {
            iPGSwitchClient.closeAll();
        }
        return lst;
    }
    
}
