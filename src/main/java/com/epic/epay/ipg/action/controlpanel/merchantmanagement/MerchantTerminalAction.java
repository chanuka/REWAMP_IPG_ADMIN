/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantTerminalBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantTerminalInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.merchantmanagement.MerchantTerminalDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgcurrency;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.OracleMessage;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author badrika
 */
public class MerchantTerminalAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    MerchantTerminalInputBean inputBean = new MerchantTerminalInputBean();
    MerchantTerminalDAO dao = null;

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called MerchantTerminalAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.MERCHANT_TERMINAL;
        String task = null;
        if ("View".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("List".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("viewpopup".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Add".equals(method)) {
            task = TaskVarList.ADD_TASK;
        } else if ("Delete".equals(method)) {
            task = TaskVarList.DELETE_TASK;
        } else if ("GetCurrency".equals(method)) {
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

    public String View() {

        List<Ipgcurrency> firstcurrencyList = new ArrayList<Ipgcurrency>();
        HashMap<String, String> currencyMap = new HashMap<String, String>();
        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setMerchantList(dao.getMerchantList());
                inputBean.setCurrencyList(firstcurrencyList);
                MerchantTerminalDAO merchTerdao = new MerchantTerminalDAO();
                currencyMap = merchTerdao.GetAllCurrency();
                inputBean.setCurrencyMap(currencyMap);

            } else {
                result = "loginpage";
            }

            System.out.println("called MerchantTerminalAction :view");

        } catch (Exception ex) {
            addActionError("Merchant Currency " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantTerminalAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called MerchantTerminalAction : ViewPopup");
        List<Ipgcurrency> firstcurrencyList = new ArrayList<Ipgcurrency>();
        try {
            
             if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setMerchantList(dao.getMerchantList());
                inputBean.setCurrencyList(firstcurrencyList);

            } else {
                result = "loginpage";
            }

        } catch (Exception e) {
            addActionError("MerchantTerminalAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantTerminalAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    
    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.MERCHANT_TERMINAL, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                }else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
                    inputBean.setVdelete(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                }
            }
        }

        return true;
    }

    public String List() {
        System.out.println("called MerchantTerminalAction: List");
        try {
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }
            dao = new MerchantTerminalDAO();
            List<MerchantTerminalBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            /**
             * for audit search
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("Merchant terminal search using [")
                        .append(checkEmptyorNullString("Merchant ID", inputBean.getMerchantId()))
                        .append(checkEmptyorNullString("Currency ", inputBean.getCurrencyCode()))
                        .append(checkEmptyorNullString("Terminal ID", inputBean.getTid()))
                        .append(checkEmptyorNullString("Status", inputBean.getStatus()))
                        .append("] parameters ");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.MERCHANT_TERMINAL, SectionVarList.MERCHANTMANAGEMENT, searchParameters.toString() , null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);
            }
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
            Logger.getLogger(MerchantTerminalAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Merchant Terminal");
        }
        return "list";
    }

    public String Delete() {
        System.out.println("called MerchantTerminalAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            dao = new MerchantTerminalDAO();

            String[] delidarray = inputBean.getDelId().split("\\|");
            String mid = delidarray[0];
            String cur = delidarray[1];
            String tid = delidarray[2];

            String batchStatus = "";
            batchStatus = dao.getBatchStatusByTerminalId(tid);

            if (batchStatus.toUpperCase().equals("BATSE")) {
                message = dao.deleteTransaction(mid, tid);
                if (message.isEmpty()) {
                    message = "Merchant Terminal " + MessageVarList.COMMON_SUCC_DELETE;
                }
                inputBean.setMessage(message);
            } else if (batchStatus.toUpperCase().equals("BAOPE")) {
                message = MessageVarList.MERCHANT_TERMINAL_DELETE_ERROR;
                inputBean.setMessage(message);
            } else if (batchStatus.toUpperCase().equals("")) {
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.MERCHANT_TERMINAL, SectionVarList.MERCHANTMANAGEMENT, "Merchant terminal ID "+tid+" deleted", null);
                message = dao.deleteMerchantTerminal(tid, audit);
                if (message.isEmpty()) {
                    message = "Merchant Terminal " + MessageVarList.COMMON_SUCC_DELETE;
                }
                inputBean.setMessage(message);
            }
            ///////////////////

        } catch (Exception e) {
            Logger.getLogger(MerchantTerminalAction.class.getName()).log(Level.SEVERE, null, e);
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }
    //GetCurrency 

    public String GetCurrency() {
        System.out.println("called MerchantTerminalAction: GetCurrency");
        HashMap<String, String> currencyMap = new HashMap<String, String>();
        List<MerchantTerminalBean> merchList = new ArrayList<MerchantTerminalBean>();
        try {
            if (inputBean.getMerchantId() != null && !inputBean.getMerchantId().isEmpty()) {

                String mid = inputBean.getMerchantId();
                dao = new MerchantTerminalDAO();

                String transStatus = dao.getHeadMerchantTransactionInitiatedStatusByMerchantId(mid);

                if (transStatus.toUpperCase().equals("YES")) {
                    currencyMap.clear();
                    currencyMap = dao.GetCurrencyByHeadMerchant(inputBean);//1
                    String transactionId = dao.GetTransactionInitiatedIdByMerchantId(mid);
                    merchList = dao.getIPGMerchantTerminalList(mid);

                    if (transactionId != null && !mid.equals(transactionId)) {
                        currencyMap.clear();
                        currencyMap = dao.GetCurrencyListUsingHMerchant(inputBean, transactionId);//2
                    }
                    if (currencyMap.isEmpty() && merchList.isEmpty()) {
                        addActionError("Please select currency for merchant ID " + transactionId);
                        inputBean.setMessage("Please select currency for merchant ID " + transactionId);
                    }

                } else if (transStatus.toUpperCase().equals("NO")) {
                    currencyMap.clear();
                    currencyMap = dao.GetCurrencyByMerchant(inputBean);//3
                }

                inputBean.setCurrencyMap(currencyMap);

            } else {
                inputBean.setMessage("");
            }

        } catch (Exception ex) {
            inputBean.setMessage("MerchantTerminal " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantTerminalAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "currency";

    }

    public String Add() {
        System.out.println("called MerchantTerminalAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            dao = new MerchantTerminalDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.MERCHANT_TERMINAL, SectionVarList.MERCHANTMANAGEMENT, "Merchant terminal ID "+inputBean.getTid()+" added", null);
                message = dao.insertMerchantTerminal(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Merchant Terminal " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Merchant Terminal " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantTerminalAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private String validateInputs() {
        String message = "";

        if (inputBean.getMerchantId() != null && inputBean.getMerchantId().isEmpty()) {
            message = MessageVarList.EMPTY_MERCHANT_ID;
        } else if (inputBean.getCurrencyCode() != null && inputBean.getCurrencyCode().isEmpty()) {
            message = MessageVarList.EMPTY_CURRENCY;
        } else if (inputBean.getTid() == null || inputBean.getTid().trim().isEmpty()) {
            message = MessageVarList.EMPTY_TERMINAL_ID;
        } else if (inputBean.getTid().length() > 0 && inputBean.getTid().length() != 8) {
            message = MessageVarList.LENGTH_TERMINAL_ID;
        } else if (inputBean.getTid().length() > 0 && !Validation.isSpecailCharacter(inputBean.getTid())) {
            message = MessageVarList.INVALID_TERMINAL_ID;
        } else if (inputBean.getStatus() != null && inputBean.getStatus().isEmpty()) {
            message = MessageVarList.EMPTY_STATUS_TER;
        }

        return message;
    }
}
