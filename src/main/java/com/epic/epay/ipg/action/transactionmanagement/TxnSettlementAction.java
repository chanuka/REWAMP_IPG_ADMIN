/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.TxnSettlementDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.TxnSettlementInputBean;
import com.epic.epay.ipg.bean.transactionmanagement.TxnSettlementedBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.transactionmanagement.TxnSettlementDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.IPGSwitchClient;
import com.epic.epay.ipg.util.common.LogFileCreator;
import com.epic.epay.ipg.util.mapping.Ipgresponsecodes;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.epic.epay.ipg.util.varlist.TransactionCodeVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
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
public class TxnSettlementAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    TxnSettlementInputBean inputBean = new TxnSettlementInputBean();
    TxnSettlementDAO dao;
    IPGSwitchClient iPGSwitchClient;
    private List transactionSwitchResponseLst = null;
    private boolean transactionSwitchResponseFlag = false;
    private String value;

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called TxnSettlementAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.SETTLEMENT_TRANSACTION;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Settle".equals(method)) {
            task = TaskVarList.SETTLE_TASK;
        } else if ("Search".equals(method)) {
            task = TaskVarList.VIEW_TASK;
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

                HttpServletRequest request = ServletActionContext.getRequest();
                HttpSession session = request.getSession(false);
                Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
                inputBean.setLoginUser(user.getUsername());

                dao = new TxnSettlementDAO();

                boolean isMerchant = dao.isMerchantUser(inputBean.getLoginUser());
                if (!isMerchant) {
                    List<TxnSettlementDataBean> list = dao.getAllMerchantIdList();
                    inputBean.setMerchantList(list);
                } else {
                   
                        boolean isHeadMerchant = dao.isHeadMerchant(inputBean.getLoginUser());
                        if (isHeadMerchant) {
                            boolean txnInitiatedStatusYes = dao.txnInitiatedStatus(inputBean.getLoginUser());
                            if (txnInitiatedStatusYes) {
                                List<TxnSettlementDataBean> list = dao.getHeadMerchantIdList(inputBean.getLoginUser());
                                inputBean.setMerchantList(list);
                            } else {
                                List<TxnSettlementDataBean> list = dao.getMerchantIdList(inputBean.getLoginUser());
                                inputBean.setMerchantList(list);
                            }
                        } else {
                                List<TxnSettlementDataBean> list = dao.getMerchantIdList(inputBean.getLoginUser());
                                inputBean.setMerchantList(list);
                            
                        }
                }
            } else {
                result = "loginpage";
            }

            System.out.println("called TxnSettlementAction :view");

        } catch (Exception ex) {
            addActionError("Transaction Settlement  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TxnSettlementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.SETTLEMENT_TRANSACTION, request);

        inputBean.setVsettlement(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SETTLE_TASK)) {
                    inputBean.setVsettlement(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                }
            }
        }
        return true;
    }

    public String Search() {
        System.out.println("called TxnSettlementAction : Search");
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession(false);
            Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
            inputBean.setLoginUser(user.getUsername());
            //           if (inputBean.isSearch()) {

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }
            String flag = "";
            dao = new TxnSettlementDAO();
            boolean isMerchant = dao.isMerchantUser(inputBean.getLoginUser());
            if (isMerchant){
                boolean isHeadMerchant = dao.isHeadMerchant(inputBean.getLoginUser());
                if (isHeadMerchant) {
                    boolean txnInitiatedStatusYes = dao.txnInitiatedStatus(inputBean.getLoginUser());
                    if (txnInitiatedStatusYes) {
                        flag ="YES";
                    } else {
                        flag ="NO";
                    }
                } else {
                    flag ="NO";
                }
            }
            List<TxnSettlementDataBean> dataList = dao.getSettlementList(inputBean.getLoginUser(),flag,rows, from);

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
            // }
        } catch (Exception e) {
            Logger.getLogger(TxnSettlementAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Txn Settlement");
        }
        return "list";
    }

    public String Settle() {

        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SETTLE_TASK, PageVarList.SETTLEMENT_TRANSACTION, SectionVarList.MERCHANTMANAGEMENT, "Transaction Settlement Process Start", null);
            String msg = "";
            msg = validateInput(inputBean);
            if (msg.isEmpty()) {
                dao = new TxnSettlementDAO();

                String TransactionCode = TransactionCodeVarList.IPG_SETTLEMENT;

                String batchId = dao.getBatchId(inputBean.getMid());
                //String tId = 
                if (!batchId.equals("notassign")) {
                    String messageToEngine = TransactionCode + "|" + inputBean.getMid() + "|" + batchId;

                    //communicate with transaction switch & get the response
                    if (messageToEngine.length() > 0) {
                        transactionSwitchResponseLst = this.communicateWithTxnSwitch(messageToEngine);
                        transactionSwitchResponseFlag = Boolean.parseBoolean(transactionSwitchResponseLst.get(0).toString());

                        if (transactionSwitchResponseFlag) {
                            
                            // set success message
                            value = transactionSwitchResponseLst.get(1).toString() + "|" + transactionSwitchResponseLst.get(2).toString() + "|" + transactionSwitchResponseLst.get(3).toString();
                            
                            if (transactionSwitchResponseLst.get(3).toString().equals(TransactionCodeVarList.SWITCH_SUCCESS)) {
                                addActionMessage("Merchant "+transactionSwitchResponseLst.get(2).toString()+" transaction settled successfully");
                                dao.settleBatchNumber(inputBean.getMid(), batchId, audit);
                                this.generateFile(inputBean.getMid(),batchId);
                            } else if (transactionSwitchResponseLst.get(3).toString().equals(TransactionCodeVarList.SWITCH_HOST_DOWN)) {
                                addActionError("Transaction settlement host is down");
                            } else if (transactionSwitchResponseLst.get(3).toString().equals(TransactionCodeVarList.SWITCH_BAD_TID)) {
                                addActionError("Merchant "+transactionSwitchResponseLst.get(2).toString()+" transaction contains bad terminal ID");
                            } else if (transactionSwitchResponseLst.get(3).toString().equals(TransactionCodeVarList.SWITCH_RE_CANCEL)) {
                                addActionError("Merchant "+transactionSwitchResponseLst.get(2).toString()+" transaction settlement request is canceled");
                            }else{
                                CommonDAO comDAO =new CommonDAO();
                                Ipgresponsecodes  response= comDAO.getPesponseByCode(transactionSwitchResponseLst.get(3).toString());
                                String responseDetails="Transaction settlement "+response.getDescription();
                                addActionError(responseDetails);
                            }
                        } else {
                            value = transactionSwitchResponseLst.get(1).toString();
                            addActionError(value);
                        }

                    } else {
                        addActionError(MessageVarList.FAILD_SWITCH_REQUEST_CREATE);
                    }
                } else {
                    value = "No Batch id found";
                    addActionError(value);
                }
            } else {
                System.out.println("msg : " + msg);
                addActionError(msg);
            }

        } catch (Exception ex) {
            addActionError("Transaction Settlement  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TxnSettlementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
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

    private String validateInput(TxnSettlementInputBean inputBean) throws Exception {
        String message = "";
        try {

            if (inputBean.getMid() == null || inputBean.getMid().trim().isEmpty()) {
                message = MessageVarList.MERCHANTID_NULL;
            }

        } catch (Exception ex) {
            throw ex;
        }
        return message;
    }

//    private boolean createSettlementFile() throws Exception {
//        try {
//            BufferedWriter bw = null;
//            String settlementFilePath = null;
//            String fileName = null;
//            String record = null;
//
//
//            File file = new File(settlementFilePath);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//
//
//            bw = new BufferedWriter(new FileWriter(fileName, true));
//            bw.write(record);
//            bw.newLine();
//            bw.flush();
//
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//        return true;
//    }
    private String generateFile(String mid,String batchid){
        String message ="";
        List<TxnSettlementedBean> txnList = null;
        StringBuffer content = null;
        BufferedWriter bwr = null;
        FileWriter filewr = null;
        try {
            File settlementpathfile = new File("C:/LOGS/IPG/LOGS/TXN_SETTLEMENT");
            if (!settlementpathfile.exists()) {
                settlementpathfile.mkdirs();
            }
            File settlementcsv = new File(settlementpathfile,"settlement.csv");
            int num = 0;
            String save;
            while(settlementcsv.exists()) {
                save =  "settlement"+ (num++) +".csv";
                settlementcsv = new File(settlementpathfile, save); 
            }
            TxnSettlementDAO txnDao = new TxnSettlementDAO();
            txnList = txnDao.getSetteledTxnDetail(mid, batchid);
            content = new StringBuffer();
               content.append("Transaction ID");
               content.append(',');
               content.append("Merchant ID");
               content.append(',');
               content.append("Terminal ID");
               content.append(',');
               content.append("Status");
               content.append(',');
               content.append("Card Association");
               content.append(',');
               content.append("Amount");
               content.append(',');
               content.append("Batch Number");
               content.append(',');
               content.append("RRN");
               content.append(',');
               content.append("Last Updated Time");
               content.append(',');
               content.append("Created Time");
               content.append(',');
               content.append("Purchased Date Time");
               content.append(',');
               content.append("Card Holder PAN");
               content.append(',');
               content.append("Merchant Referance No");
               content.append('\n');
            for(TxnSettlementedBean txn :txnList){
               content.append(txn.getTxnid());
               content.append(',');
               content.append(txn.getMerchant());
               content.append(',');
               content.append(txn.getTerminalid());
               content.append(',');
               content.append(txn.getStatus());
               content.append(',');
               content.append(txn.getCardassociation());
               content.append(',');
               content.append(txn.getAmount());
               content.append(',');
               content.append(txn.getBatchnumber());
               content.append(',');
               content.append(txn.getRrn());
               content.append(',');
               content.append(txn.getLastupdatedtime());
               content.append(',');
               content.append(txn.getCreatedtime());
               content.append(',');
               content.append(txn.getPurchaseddatetime());
               content.append(',');
               content.append(txn.getCardholderpan());
               content.append(',');
               content.append(txn.getMerchantreferanceno());
               content.append('\n');
            }
            filewr = new FileWriter(settlementcsv);
            bwr =new BufferedWriter(filewr);
            bwr.write(content.toString());
            
        }catch(Exception e){
            message=MessageVarList.COMMON_ERROR_PROCESS;
        }finally{
            try{
                if(bwr != null){
                    bwr.flush();
                    bwr.close();
                }
            }catch(Exception e){}
            try{
                if(filewr != null){
                    filewr.flush();
                    filewr.close();
                }
            }catch(Exception e){}
        }
        return message;
    }
}
