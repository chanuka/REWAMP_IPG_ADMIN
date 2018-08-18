/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.RefundTxnDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.RefundTxnInputBean;
import com.epic.epay.ipg.dao.transactionmanagement.RefundTxnDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.IPGSwitchClient;
import com.epic.epay.ipg.util.common.LogFileCreator;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.mapping.Ipgtransaction;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.StatusVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.epic.epay.ipg.util.varlist.TransactionCodeVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author asela
 */
public class RefundTxnAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    RefundTxnInputBean inputBean = new RefundTxnInputBean();
    RefundTxnDAO dao = null;
    private IPGSwitchClient iPGSwitchClient;
    private List transactionSwitchResponseLst = null;
    private boolean transactionSwitchResponseFlag = false;
    private String value = null;

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called RefundTxnAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.REFUND_TRANSACTION;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Search".equals(method)) {
            task = TaskVarList.SEARCH_TASK;
        } else if ("Refund".equals(method)) {
            task = TaskVarList.REFUND_TASK;
        }
        if ("execute".equals(method)) {
            status = true;
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            status = new Common().checkMethodAccess(task, page, userRole, request);
        }
        return status;

    }

    public String view() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

//                HttpServletRequest request = ServletActionContext.getRequest();
//                HttpSession session = request.getSession(false);
//                Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
//                inputBean.setUser(user.getUsername());
//                inputBean.setMerchantId(user.getMerchantid());
            } else {
                result = "loginpage";
            }

            System.out.println("called RefundTxnAction :view");

        } catch (Exception ex) {
            addActionError("Refund Transaction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TxnSettlementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.REFUND_TRANSACTION, request);

        inputBean.setVrefund(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.REFUND_TASK)) {
                    inputBean.setVrefund(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                }
            }
        }
        return true;
    }

    public String Refund() {
        System.out.println("called RefundTxnAction : add");
        String retType = "message";

        try {

            String message = this.validateInput(inputBean, null);

            if (message.isEmpty()) {

                HttpServletRequest request = ServletActionContext.getRequest();
                HttpSession session = request.getSession(false);
                Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
                inputBean.setLoginUser(user.getUsername());
                inputBean.setMerchantid(user.getMerchantid());
                dao = new RefundTxnDAO();
//                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.MERCHANT_CURRENCY, SectionVarList.MERCHANTMANAGEMENT, "Moto transaction added", null);
                this.setValueforProccessInit(inputBean.getTxnid());
                inputBean.setTxnid(this.createTransactionId());
                inputBean.setStatus(StatusVarList.TXN_INITIATED);

                String message1 = dao.refundTransactionInitiated(inputBean);
                String message2 = "history";
                String switchRequestMessage = null;
                if (message1.isEmpty()) {
                    message2 = dao.insertIPGTxnHistory(inputBean);

                    if (message2.isEmpty()) {
                        switchRequestMessage = this.createRequestMessageForTxnSwitch();

                        if (switchRequestMessage.length() > 0) {
                            inputBean.setStatus(StatusVarList.TXN_ENGINEREQCREATED);

                            if (this.updateTransactionStatusAndHistory()) {
                                //communicate with transaction switch & get the response
                                transactionSwitchResponseLst = this.communicateWithTxnSwitch(switchRequestMessage);

                                transactionSwitchResponseFlag = Boolean.parseBoolean(transactionSwitchResponseLst.get(0).toString());
                                if (!transactionSwitchResponseFlag) {

                                    inputBean.setStatus(StatusVarList.TXN_FAILED);
                                    this.updateTransactionStatusAndHistory();
                                    value = "FAILED" + "|" + transactionSwitchResponseLst.get(1).toString();
                                    addActionError(value);

                                } else {

                                    value = transactionSwitchResponseLst.get(1).toString() + "|" + transactionSwitchResponseLst.get(2).toString() + "|" + transactionSwitchResponseLst.get(3).toString();

                                    if (transactionSwitchResponseLst.get(3).toString().equals("00")) {
                                        addActionMessage(value);
                                    } else if (transactionSwitchResponseLst.get(3).toString().equals("E1")) {
                                    }
                                }
                            } else {
                                addActionError(MessageVarList.TXN_FAILED);
                            }

                        } else {
                            inputBean.setStatus(StatusVarList.TXN_ENGINEREQCREATEDFAIL);
                            this.updateTransactionStatusAndHistory();
                            addActionError(MessageVarList.FAILD_SWITCH_REQUEST_CREATE);
                        }

                    } else {
                        addActionError(MessageVarList.FAILED_TO_INITIATE_IPG_TRANSACTION);
                    }
                } else {
                    addActionError(message1);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            Logger.getLogger(RefundTxnAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Refund Transaction " + MessageVarList.COMMON_ERROR_PROCESS);
        }
        return retType;
    }

    private String validateInput(RefundTxnInputBean refundBean, Ipgtransaction ipgtxn) throws Exception {
        String message = "";
        try {

            if (refundBean.getRefundAmount().contentEquals("") || refundBean.getRefundAmount().isEmpty()) {
                message = MessageVarList.AMOUNT_NULL;
            } else if (!Validation.isDecimalOrNumeric(refundBean.getRefundAmount(), "8", "2")) {
                message = MessageVarList.AMOUNT_NIVALID;
            } else if (Double.parseDouble(refundBean.getRefundAmount()) >= Double.parseDouble(Integer.toString(ipgtxn.getAmount()))) {
                message = MessageVarList.AMOUNT_NIVALID;
            }

        } catch (Exception ex) {
            throw ex;
        }
        return message;
    }

    private String createTransactionId() throws Exception {
        String tId;
        try {
            Random randomgenerator = new Random();
            Date nowDate = new Date();

            long date = nowDate.getTime();
            tId = (date + "" + randomgenerator.nextInt(1000));
        } catch (Exception ex) {
            throw ex;
        }
        return tId;
    }

    //create txn request message for transaction switch
    private String createRequestMessageForTxnSwitch() throws Exception {
        String message = null;
        try {

            //get SwitchRequestLog congiguration file path
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession(false);
//            String realPathSwitchRequest = session.getServletContext().getRealPath("resources/settings/SWITCHREQUESTLog.xml");

            ////////////////////////////////////**************///////////////////////////////////////////////////////////////////////////////////////////////
            //create moto transaction switch message
            message = TransactionCodeVarList.IPG_REFIND + "|" + inputBean.getTxnid() + "|" + inputBean.getMerchantid() + "|" + this.createCurrentDateTime();

            ////////////////////////////////////*************///////////////////////////////////////////////////////////////////////////////////////////////// 
            //write info log. for write switch request sent to switch by ipg
            LogFileCreator.writInforTologsForSwitchRequest(message);//see this

        } catch (Exception ex) {
            throw ex;
        }
        return message;
    }

    public String createCurrentDateTime() throws Exception {
        String currentdatetime = "";
        try {
            String DATE_FORMAT = "MMddHHmmss";

            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

            Date date = new Date();
            currentdatetime = sdf.format(date);
        } catch (Exception ex) {
            throw ex;
        }
        return currentdatetime;
    }

    private boolean updateTransactionStatusAndHistory() throws Exception {
        boolean success = false;
        String message2 = null;
        try {

            dao = new RefundTxnDAO();
            String message = dao.updateTransactionStage(inputBean);
            if (message.isEmpty()) {
                message2 = dao.insertIPGTxnHistory(inputBean);

                if (message2.isEmpty()) {
                    success = true;
                }
            }

        } catch (Exception ex) {
            throw ex;
        }
        return success;
    }

    //communicate with transaction switch
    private List communicateWithTxnSwitch(String switchRequestMessage) throws Exception {
        List lst = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
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
                // Create connection with Txn Switch
                iPGSwitchClient = new IPGSwitchClient();

                //switch request sent. then update db
                inputBean.setStatus(StatusVarList.TXN_ENGINEREQUESTSENT);
                this.updateTransactionStatusAndHistory();

                // Send data packet to Txn Switch
                iPGSwitchClient.sendPacket(serverRequest);

                socketFlag = true;
            } catch (Exception e) {
                // Scenario 1 - fail to send a request
                message = MessageVarList.FAILED_TO_SEND_MESSAGE_TO_TXN_SWITCH;
                lst.add(false);
                lst.add(message);
            }
            if (socketFlag) {
                try {
                    // server response format
                    // "TxnType"
                    // Data receiving method
                    serverResponse = iPGSwitchClient.receivePacket();

                    //get SwitchResponseLog congiguration file path
//                    String realPathSwitchResponse = session.getServletContext().getRealPath("resources/settings/SWITCHRESPONSELog.xml");
                    //write info log. for write switch response sent to ipg by switch
                    LogFileCreator.writInforTologsForSwitchResponse(serverResponse);

                    //switch response received. then update db
                    inputBean.setStatus(StatusVarList.TXN_ENGINERESRECEIVED);
                    this.updateTransactionStatusAndHistory();

                    //get txn switch response & seperate it.
                    //" Txn code|Txn id|Response code "
                    StringTokenizer tk = new StringTokenizer(serverResponse, "|");
                    if (tk.countTokens() != 3) {
                        message = MessageVarList.INVALID_TXN_SWITCH_RESPONSE;
                        lst.add(false);
                        lst.add(message);
                    } else {
                        lst.add(true); //txn switch communication successfully completed.
                        lst.add(tk.nextToken());
                        lst.add(tk.nextToken());
                        lst.add(tk.nextToken());
                    }
                } catch (Exception e) {
                    // Scenario 2 - fail to receive the response
                    message = MessageVarList.FAILED_TO_RECEIVE_MESSAGE_FROM_TXN_SWITCH;
                    lst.add(false);
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

    public void setValueforProccessInit(String txnid) throws Exception {
        try {
            dao = new RefundTxnDAO();
            Ipgtransaction ipgtxn = dao.getTransactionInfo(txnid);
            inputBean.setStatus(ipgtxn.getIpgstatus().getStatuscode());
            inputBean.setCurrency(ipgtxn.getIpgcurrency().getCurrencycode());
            inputBean.setCardno(ipgtxn.getCardholderpan());
            inputBean.setExpiryDate(ipgtxn.getExpirydate());
            inputBean.setCardHolderName(inputBean.getLoginUser());
            inputBean.setOrderid(ipgtxn.getOrderid());
        } catch (Exception e) {
            throw e;
        }
    }
    ///////////////////

    public String Search() {
        System.out.println("called RefundTxnAction : Search");
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

                dao = new RefundTxnDAO();
              //  String message = this.validateInputs();

                //if (message.isEmpty()) {
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

                List<RefundTxnDataBean> dataList = dao.getTxnList(inputBean, rows, from, orderBy);

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

                ///} else {
                //     addActionError(message);
                // }
            }

        } catch (Exception ex) {
            addActionError("Transaction Refund " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionVoidAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "list";
    }
}
