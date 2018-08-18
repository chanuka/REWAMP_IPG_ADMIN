/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.ManualTransactionDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.ManualTransactionInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.transactionmanagement.CCBManualTxnDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.mapping.Ipgcommonconfig;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author nalin
 */
public class CCBManualTxnAction extends ActionSupport implements ModelDriven<Object>,
        AccessControlService {

    ManualTransactionInputBean inputBean = new ManualTransactionInputBean();
    CommonDAO dao = null;
    CCBManualTxnDAO manualDeo = null;

    public String view() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                dao = new CommonDAO();
                inputBean.setCurrencyList(dao.getCurrencyList());

            } else {
                result = "loginpage";
            }

            System.out.println("called CCBManualTxnAction :view");

        } catch (Exception ex) {
            addActionError("Manual Transaction  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantTransactionSettlementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String search() {
        System.out.println("called CCBManualTxnAction : Search");
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

                List<ManualTransactionDataBean> dataList = null;

                manualDeo = new CCBManualTxnDAO();
                dataList = manualDeo.getSearchList(inputBean, rows, from, orderBy);


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
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Manual Transaction");
        }
        return "search";
    }

    public String process() {
        System.out.println("called CCBManualTxnAction : Process");
        try {

            if (inputBean.getSelectedId() != null) {
                String errorMessage = null;

                List<ManualTransactionDataBean> list = new ArrayList<ManualTransactionDataBean>();
                manualDeo = new CCBManualTxnDAO();
                // get data set 

                for (String s : inputBean.getSelectedId()) {
                    ManualTransactionDataBean bean = manualDeo.getProcessBean(s);
                    bean.setCcbaTransactionId(s);
                    list.add(bean);
                }

                List<String> requestFormatList = new ArrayList<String>();

                if (!list.isEmpty()) {
                    String requestFormat = "";
                    for (ManualTransactionDataBean bean : list) {

                        Date dateTime = bean.getTxnDateTime();
                        java.sql.Timestamp timeStampDate = new Timestamp(dateTime.getTime());
                        String transactionDate = this.createCurrentDateTime(timeStampDate);


//                        String transactionDate = bean.getTxnDateTime();
//                        Timestamp ts = Timestamp.valueOf(transactionDate);
//                        //java.sql.Timestamp timeStampDate = new Timestamp(transactionDate.getTime());
//                        transactionDate = this.createCurrentDateTime(ts);

                        requestFormat = "01|" + bean.getTransactionId() + "|" + bean.getMerchantid() + "|" + transactionDate;
                        requestFormatList.add(requestFormat);
                    }
                }

                List<String> responseList = new ArrayList<String>();

                if (!requestFormatList.isEmpty()) {

                    for (String string : requestFormatList) {
                        String serverMsg = this.communicateWithSwitch(string);
                        responseList.add(serverMsg);
                    }
                }

                if (!responseList.isEmpty()) {

                    int num1 = responseList.size();
                    int num2 = 0;

                    for (String string : responseList) {
                        String lastIndex = checkResponse(string);
                        if (!lastIndex.equals("00")) {
                            num2++;
                        }
                    }
                    errorMessage = "";
                    if (num2 > 0) {
                        errorMessage = "Number of " + num2 + " transactions failded from " + num1;
                        addActionError(errorMessage);
                        inputBean.setMessage(errorMessage);
                        inputBean.setTag(false);
                    }
                }
                if (!errorMessage.equals("")) {
                    addActionError(errorMessage);
                    inputBean.setMessage(errorMessage);
                    inputBean.setTag(false);
                } else {
                    addActionMessage(MessageVarList.MANUAL_TXN_SUCCESS);
                    inputBean.setMessage(MessageVarList.MANUAL_TXN_SUCCESS);
                    inputBean.setTag(true);
                }

            } else {
                inputBean.setMessage(MessageVarList.MANUAL_TXN_NOT_SELECTED_ROW);
                inputBean.setTag(false);


            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(MerchantTransactionSettlementAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError(MessageVarList.MANUAL_TXN_PROCESS_SERVER_ERROR);
            inputBean.setMessage(MessageVarList.MANUAL_TXN_PROCESS_SERVER_ERROR);
            inputBean.setTag(false);
        } catch (ConnectException cne) {
            Logger.getLogger(MerchantTransactionSettlementAction.class.getName()).log(Level.SEVERE, null, cne);
            addActionError(MessageVarList.MANUAL_TXN_PROCESS_SERVER_ERROR);
            inputBean.setMessage(MessageVarList.MANUAL_TXN_PROCESS_SERVER_ERROR);
            inputBean.setTag(false);
        } catch (Exception e) {
            Logger.getLogger(MerchantTransactionSettlementAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Manual Transaction");
            inputBean.setMessage(MessageVarList.COMMON_ERROR_PROCESS + " Manual Transaction");
            inputBean.setTag(false);
        }
        return "process";
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(
                PageVarList.MANUAL_TRANSACTION, request);

        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                }
            }
        }

        return true;
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ModelDriven#getModel()
     */
    public Object getModel() {
        // TODO Auto-generated method stub
        return inputBean;
    }

    /* (non-Javadoc)
     * @see com.epic.epay.ipg.util.common.AccessControlService#checkAccess(java.lang.String, java.lang.String)
     */
    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.MANUAL_TRANSACTION;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("search".equals(method)) {
            task = TaskVarList.SEARCH_TASK;
        } else if ("process".equals(method)) {
            task = TaskVarList.MANUAL_TASK;
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

    private String communicateWithSwitch(String msg) throws Exception {
        PrintWriter PW = null;
        BufferedReader serverMsg = null;
        Socket clientScoket = null;
        String message = null;

        try {
            manualDeo = new CCBManualTxnDAO();
            CommonDAO commondao = new CommonDAO();
            Ipgcommonconfig bean = new Ipgcommonconfig();
            String switchport = commondao.getCommonConfigValueByCode(CommonVarList.COMMON_CONFIG_SWITCHPORT).getValue();
            String switchIP = commondao.getCommonConfigValueByCode(CommonVarList.COMMON_CONFIG_SWITCHIP).getValue();
            clientScoket = new Socket(switchIP,Integer.parseInt(switchport));
            PW = new PrintWriter(clientScoket.getOutputStream(), true);
            PW.println(msg);
            serverMsg = new BufferedReader(new InputStreamReader(clientScoket.getInputStream()));
            message = serverMsg.readLine();



        } catch (UnknownHostException uhe) {
            throw uhe;
        } catch (ConnectException cne) {
            throw cne;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (PW != null) {
                    PW.flush();
                    PW.close();
                }
                if (serverMsg != null) {
                    serverMsg.close();
                }
                if (clientScoket != null) {
                    clientScoket.close();
                }
            } catch (Exception ex) {
                throw ex;
            }
        }
        return message;
    }

    public String checkResponse(String response) throws Exception {
        String lastIndex = "";
        try {
            StringTokenizer tokenizer = new StringTokenizer(response, "|");

            String[] array = new String[tokenizer.countTokens()];
            while (tokenizer.hasMoreTokens()) {
                for (int i = 0; i < array.length; i++) {
                    array[i] = tokenizer.nextToken();
                }
            }
            lastIndex = array[array.length - 1];
            manualDeo = new CCBManualTxnDAO();
            ManualTransactionDataBean bean = new ManualTransactionDataBean();
            bean.setResponseCode(lastIndex);
            bean.setCcbaTransactionId(array[1]);
            manualDeo.updateIPGCCBATransaction(bean);
        } catch (Exception e) {
            throw e;
        }
        return lastIndex;
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
}
