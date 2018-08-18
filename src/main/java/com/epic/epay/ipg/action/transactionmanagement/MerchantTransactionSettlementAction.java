/**
 *
 */
package com.epic.epay.ipg.action.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.MerchantTracsactionSettlementDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.MerchantTracsactionSettlementInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.transactionmanagement.MerchantTransactionSettlementDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.IPGMd5;
import com.epic.epay.ipg.util.common.IPGSwitchClient;
import com.epic.epay.ipg.util.common.LogFileCreator;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 * @author Asitha Liyanawaduge
 *
 * 04/12/2013
 */
public class MerchantTransactionSettlementAction extends ActionSupport implements ModelDriven<MerchantTracsactionSettlementInputBean>,
        AccessControlService {

    /**
     *
     */
    private static final long serialVersionUID = 2532811413089485409L;
    IPGSwitchClient iPGSwitchClient;
    private List transactionSwitchResponseLst = null;
    private boolean transactionSwitchResponseFlag = false;
    private String value;
    MerchantTracsactionSettlementInputBean inputBean = new MerchantTracsactionSettlementInputBean();

    public String view() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getBatchStatusList(CommonVarList.STATUS_CATEGORY_BATCH_TRANSACTION_SETTLE));

            } else {
                result = "loginpage";
            }

            System.out.println("called MerchantTransactionSettlementAction :view");

        } catch (Exception ex) {
            addActionError("Merchant Transaction Settlement " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantTransactionSettlementAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return result;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(
                PageVarList.MERCHANT_TRANSACTION_SETTLEMENT, request);

        inputBean.setVsearch(true);
        inputBean.setVsettle(true);
        inputBean.setVprocess(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode()
                        .equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                } else if (task.getTaskcode()
                        .equalsIgnoreCase(TaskVarList.SETTLE_TASK)) {
                    inputBean.setVsettle(false);
                } else if (task.getTaskcode()
                        .equalsIgnoreCase(TaskVarList.SETTLE_TASK)) {
                    inputBean.setVprocess(false);
                }
            }
        }

        return true;
    }

    public String search() {
        System.out.println("called MerchantTransactionSettlementAction : Search");
        try {
            if (inputBean.isSearch()) {

                int rows = inputBean.getRows();
                int page = inputBean.getPage();
                int to = (rows * page);
                int from = to - rows;
                long records = 0;
                String sortIndex = "";
                String sortOrder = "";

                List<MerchantTracsactionSettlementDataBean> dataList = null;

                if (!inputBean.getSidx().isEmpty()) {
                    sortIndex = inputBean.getSidx();
                    sortOrder = inputBean.getSord();
                }

                MerchantTransactionSettlementDAO dao = new MerchantTransactionSettlementDAO();
                //dataList = dao.getSearchList(inputBean, rows, from, sortIndex, sortOrder);

                HttpServletRequest request = ServletActionContext.getRequest();
                HttpSession session = request.getSession(false);
                Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
                inputBean.setLoginUser(user.getUsername());

                String merchantId = dao.getCurrentUserMerchantId(inputBean.getLoginUser());
                String flag = "";

                if (dao.getHeadMerchantTxnInitStatus(merchantId).equals("YES")) {
                    flag = "YES";
                } else if (dao.getHeadMerchantTxnInitStatus(merchantId).equals("NO") || dao.isMerchantExist(merchantId)) {
                    flag = "NO";
                }

                dataList = dao.searchMerchantTransactions(inputBean, merchantId, flag, rows, from);

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
            }
        } catch (Exception e) {
            Logger.getLogger(MerchantTransactionSettlementAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Merchant Transaction Settlement");
        }
        return "search";
    }
    /* (non-Javadoc)
     * @see com.epic.epay.ipg.util.common.AccessControlService#checkAccess(java.lang.String, java.lang.String)
     */

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.MERCHANT_TRANSACTION_SETTLEMENT;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("search".equals(method)) {
            task = TaskVarList.SEARCH_TASK;
        } else if ("show".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("List".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("DualAuth".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("UserPwdAuth".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        }
        if ("execute".equals(method)) {
            status = true;
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            status = new Common().checkMethodAccess(task, page, userRole,
                    request);
        }
        return status;
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ModelDriven#getModel()
     */
    public MerchantTracsactionSettlementInputBean getModel() {
        // TODO Auto-generated method stub
        return inputBean;
    }

    public String show() throws Exception {
        String result = "show";
        try {

            String[] key = inputBean.getKey().split("\\|");
            String merchantid = key[0];
            String terminalid = key[1];
            String batchid = key[2];

            MerchantTransactionSettlementDAO dao = new MerchantTransactionSettlementDAO();
            MerchantTracsactionSettlementDataBean inputDataBean = dao.getPrimaryTransactionDetails(merchantid);
            inputDataBean.setMerchantid(merchantid);
            inputDataBean.setTerminalid(terminalid);
            inputDataBean.setBatchid(batchid);
            inputBean.setBean(inputDataBean);

        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    public String List() {
        System.out.println("called MerchantTransactionSettlementAction : List");
        try {
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
            MerchantTransactionSettlementDAO dao = new MerchantTransactionSettlementDAO();
            String[] key = inputBean.getKey().split("\\|");

            String merchantid = key[0];
            String terminalid = key[1];
            String batchid = key[2];



            List<MerchantTracsactionSettlementDataBean> dataList = dao.getAllTransactionDetails(merchantid, terminalid, batchid, rows, from, orderBy);

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
            Logger.getLogger(MerchantTransactionSettlementAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Merchant Txn Settlement");
        }
        return "list";
    }

    public String DualAuth() throws Exception {
        String result = "message";
        try {

            //String[] key = inputBean.getKey().split("|");
            String merchantid = inputBean.getMerchantId();
            String terminalid = inputBean.getTerminalId();
            String batchid = inputBean.getBatchId();

            MerchantTransactionSettlementDAO dao = new MerchantTransactionSettlementDAO();


            String uname = inputBean.getUname();
            
            String password = IPGMd5.ipgMd5(inputBean.getPasswordval());

            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession(false);
            Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
            inputBean.setLoginUser(user.getUsername());

            String dualAuthUserrole = dao.getDualAuthUserRole(user.getUsername());

            if (dao.checkDualAuthorization(dualAuthUserrole, uname, password)) {
                // rd = request.getRequestDispatcher("/TransactionSettlementServlet?merchantId=" + merchantId + "&batchId=" + batchId + "&terminalId=" + terminalId);
                this.Settle(batchid, merchantid, terminalid);
            } else {
                addActionError("Authorization Fail");
            }

        } catch (Exception e) {
            throw e;
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

    public String Settle(String batchId, String mid, String tid) {

        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SETTLE_TASK, PageVarList.SETTLEMENT_TRANSACTION, SectionVarList.MERCHANTMANAGEMENT, "Merchant Transaction Settlement Process Start", null);
            //String msg = "";
            MerchantTransactionSettlementDAO dao = new MerchantTransactionSettlementDAO();

            String TransactionCode = "04";

            String messageToEngine = TransactionCode + "|" + mid + "|" + batchId + "|" + tid;

            //communicate with transaction switch & get the response
            if (messageToEngine.length() > 0) {
                transactionSwitchResponseLst = this.communicateWithTxnSwitch(messageToEngine);
                transactionSwitchResponseFlag = Boolean.parseBoolean(transactionSwitchResponseLst.get(0).toString());

                if (transactionSwitchResponseFlag) {
                    dao.settleBatchNumber(mid, batchId, audit);
                    // set success message
                    value = transactionSwitchResponseLst.get(1).toString() + "|" + transactionSwitchResponseLst.get(2).toString() + "|" + transactionSwitchResponseLst.get(3).toString();

                    if (transactionSwitchResponseLst.get(3).toString().equals("00")) {
                        addActionMessage(value);
                    } else if (transactionSwitchResponseLst.get(3).toString().equals("E7")) {
                    }
                } else {
                    value = transactionSwitchResponseLst.get(1).toString();
                    addActionError(value);
                }

            } else {
                addActionError(MessageVarList.FAILD_SWITCH_REQUEST_CREATE);
            }

        } catch (Exception ex) {
            addActionError("Merchant Transaction Settlement  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantTransactionSettlementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String UserPwdAuth() {
        String result = "userpwd";
        try {
            String[] key = inputBean.getKey().split("\\|");

            String merchantid = key[0];
            String terminalid = key[1];
            String batchid = key[2];
    
            MerchantTracsactionSettlementDataBean inputDataBean = new MerchantTracsactionSettlementDataBean();
            inputDataBean.setMerchantid(merchantid);
            inputDataBean.setTerminalid(terminalid);
            inputDataBean.setBatchid(batchid);
            inputBean.setBean(inputDataBean);

        } catch (Exception ex) {
            addActionError("Merchant Transaction Settlement  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantTransactionSettlementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
