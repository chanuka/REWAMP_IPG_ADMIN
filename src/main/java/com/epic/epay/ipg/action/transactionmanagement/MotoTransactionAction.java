/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.MotoTransactionInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.transactionmanagement.MotoTransactionDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.IPGSwitchClient;
import com.epic.epay.ipg.util.common.LogFileCreator;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgcommonconfig;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.StatusVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.epic.epay.ipg.util.varlist.TransactionCodeVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.text.DateFormat;
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
 * @author badrika
 */
public class MotoTransactionAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    MotoTransactionInputBean inputBean = new MotoTransactionInputBean();
    MotoTransactionDAO dao = null;
    private IPGSwitchClient iPGSwitchClient;
    private List transactionSwitchResponseLst = null;
    private boolean transactionSwitchResponseFlag = false;
    private String value = null;
    private CommonDAO commondao = null;
    private Ipgcommonconfig comondata = null;

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called MotoTransactionAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.MOTO_TRANSACTION;
        String task = null;
        if ("View".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Add".equals(method)) {
            task = TaskVarList.ADD_TASK;
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

//                HttpServletRequest request = ServletActionContext.getRequest();
//                HttpSession session = request.getSession(false);
//                Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
//                inputBean.setUser(user.getUsername());
//                inputBean.setMerchantId(user.getMerchantid());
                dao = new MotoTransactionDAO();
                inputBean.setCardTypeList(dao.getCardTypeList());
                inputBean.setCurrencyList(dao.getCurrencyList());

            } else {
                result = "loginpage";
            }

            System.out.println("called MotoTransactionAction :view");

        } catch (Exception ex) {
            addActionError("Moto Transaction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MotoTransactionAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.MOTO_TRANSACTION, request);

        inputBean.setVadd(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                }
            }
        }
        return true;
    }

    public String Add() {
        System.out.println("called MotoTransactionAction : add");
        String retType = "message";

        try {
            String message = this.validateInputs();

            if (message.isEmpty()) {

                HttpServletRequest request = ServletActionContext.getRequest();
                HttpSession session = request.getSession(false);
                Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
                inputBean.setUser(user.getUsername());
                inputBean.setMerchantId(user.getMerchantid());

                dao = new MotoTransactionDAO();

//                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.MOTO_TRANSACTION, SectionVarList.MERCHANTMANAGEMENT, "Moto transaction added", null);
                inputBean.setTxnId(this.createTransactionId());
                inputBean.setStatus(StatusVarList.TXN_INITIATED);

                String message1 = dao.motoTransactionInitiated(inputBean);
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
//                                    value = "FAILED" + "|" + transactionSwitchResponseLst.get(1).toString();
                                    value = transactionSwitchResponseLst.get(1).toString();
                                    addActionError(value);

                                } else {

//                                    value = transactionSwitchResponseLst.get(1).toString() + "|" + transactionSwitchResponseLst.get(2).toString() + "|" + transactionSwitchResponseLst.get(3).toString();
                                    value = transactionSwitchResponseLst.get(2).toString() + "|" + transactionSwitchResponseLst.get(3).toString();

                                    if (transactionSwitchResponseLst.get(3).toString().equals("00")) {
                                        addActionMessage(value + " " + MessageVarList.TXN_SUCCESSFUL);
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
            Logger.getLogger(MotoTransactionAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Moto Transaction " + MessageVarList.COMMON_ERROR_PROCESS);
        }
        return retType;
    }

    private String validateInputs() throws Exception {
        String message = "";
        try {

            Date today = new Date();

            DateFormat df = new SimpleDateFormat("yy");
            int nowyy = Integer.parseInt(df.format(today));
            int mm = 0;
            int yy = 0;

            if (inputBean.getExpiryDate() != null && !inputBean.getExpiryDate().isEmpty()) {
                mm = Integer.parseInt(inputBean.getExpiryDate().substring(2));
                yy = Integer.parseInt(inputBean.getExpiryDate().substring(0, 2));
            }

            if (inputBean.getOrderCode() == null || inputBean.getOrderCode().trim().isEmpty()) {
                message = MessageVarList.MOTO_ORDER_NO_EMPTY;
            } else if (!Validation.isNumeric(inputBean.getOrderCode())) {
                message = MessageVarList.MOTO_ORDER_NO_INVALID;
            } else if (inputBean.getAmount() == null || inputBean.getAmount().trim().isEmpty()) {
                message = MessageVarList.MOTO_AMOUNT_EMPTY;
            } else if (!Validation.isDecimalOrNumeric(inputBean.getAmount(), "10", "2")) {
                message = MessageVarList.MOTO_AMOUNT_NIVALID;
            } else if (!Validation.isNumeric(inputBean.getAmount())) {
                message = MessageVarList.MOTO_AMOUNT_NIVALID;
            } else if (inputBean.getCurrency() == null || inputBean.getCurrency().trim().isEmpty()) {
                message = MessageVarList.MOTO_CURRENCY_EMPTY;
            } else if (inputBean.getCardType() == null || inputBean.getCardType().trim().isEmpty()) {
                message = MessageVarList.MOTO_CARD_TYPE_EMPTY;
            } else if (inputBean.getCardNo() == null || inputBean.getCardNo().trim().isEmpty()) {
                message = MessageVarList.MOTO_CARD_NUMBER_EMPTY;
            } else if (!Validation.isNumeric(inputBean.getCardNo())) {
                message = MessageVarList.MOTO_CARD_NUMBER_INVALID;
            } else if ((inputBean.getCardNo().length() != 15 && inputBean.getCardNo().length() != 16)) {
                message = MessageVarList.MOTO_CARD_NUMBER_INVALID;
            } else if (inputBean.getExpiryDate() == null || inputBean.getExpiryDate().trim().isEmpty()) {
                message = MessageVarList.MOTO_EXPIRY_DATE_EMPTY;
            } else if (!Validation.isNumeric(inputBean.getExpiryDate())) {
                message = MessageVarList.MOTO_EXPIRY_DATE_INVALID;
            } else if (mm > 12 || mm < 1 || yy < nowyy) {
                message = MessageVarList.MOTO_EXPIRY_DATE_INVALID;
            } else if (inputBean.getCardHolderName() == null || inputBean.getCardHolderName().trim().isEmpty()) {
                message = MessageVarList.MOTO_CARD_NAME_EMPTY;
            } else if (!Validation.isSpecailCharacter(inputBean.getCardHolderName())) {
                message = MessageVarList.MOTO_CARD_NAME_INVALID;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return message;
    }

    private String createTransactionId() throws Exception {
        String tId;
        try {
            String date = createCurrentDateTime();
            String randm = MotoTransactionAction.generatePin();
            
            System.out.println("date*****" +date);
            tId = (date + randm);
            System.out.println("tid*****" + tId);
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
            message = TransactionCodeVarList.IPG_MOTO + "|" + inputBean.getTxnId() + "|" + inputBean.getMerchantId() + "|" + this.createCurrentDateTime();

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
            String DATE_FORMAT = "yyMMddHHmmss";

            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

            Date date = new Date();
            currentdatetime = sdf.format(date);
        } catch (Exception ex) {
            throw ex;
        }
        return currentdatetime;
    }
    
    //to generate randaom number
    private static final int[] pins = new int[900000];  
   
    static {  
        for (int i = 0; i < pins.length; i++)  
            pins[i] = 100000 + i;  
    }  
   
    private static int pinCount;  
   
    private static final Random random = new Random();  
   
    public static String generatePin() {  
        if (pinCount >= pins.length)  
            throw new IllegalStateException();  
        int index = random.nextInt(pins.length - pinCount) + pinCount;  
        int pin = pins[index];  
        pins[index] = pins[pinCount++];  
        
        return String.valueOf(pin).substring(0, 4);  
    }
    //

    private boolean updateTransactionStatusAndHistory() throws Exception {
        boolean success = false;
        String message2 = null;
        try {

            dao = new MotoTransactionDAO();
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
    ///////////////////
}
