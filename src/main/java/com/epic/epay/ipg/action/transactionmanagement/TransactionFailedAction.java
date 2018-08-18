/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.epay.ipg.action.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.TransactionFailedDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.TransactionFailedInputBean;
import com.epic.epay.ipg.dao.transactionmanagement.TransactionFailedDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author sivaganesan_t
 */
public class TransactionFailedAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {
    
    TransactionFailedInputBean inputBean =new TransactionFailedInputBean();
    TransactionFailedDAO dao =null;
    
    public Object getModel() {
        return inputBean;
    }
    
    @Override
    public String execute() {
        System.out.println("called TransactionFailedAction : execute");
        return SUCCESS;
    }
    
    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.TRANSACTION_FAILED;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Search".equals(method)) {
            task = TaskVarList.SEARCH_TASK;
        } 
        if ("execute".equals(method)) {
            status = true;
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            status = new Common().checkMethodAccess(task, page, userRole, request);
        }
        return status;
    }
    
    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.TRANSACTION_FAILED, request);

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
    
    public String view() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                dao = new TransactionFailedDAO();
                inputBean.setCardTypeList(dao.getCardTypeList());
                inputBean.setStatusList(dao.getFailedStatusList());

            } else {
                result = "loginpage";
            }

            System.out.println("called TransactionFailedAction :View");


        } catch (Exception ex) {
            addActionError("Transaction Failed " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionFailedAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public String Search() {
        System.out.println("called TransactionFailedAction : Search");
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

                dao = new TransactionFailedDAO();
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

                    List<TransactionFailedDataBean> dataList = dao.getTxnList(inputBean, rows, from, orderBy);

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
            addActionError("Transaction failed " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionFailedAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "list";
    }
    
    private String validateInputs() {
        String message = "";
        if (inputBean.getTxnId() != null && !Validation.isSpecailCharacter(inputBean.getTxnId())) {
            message = MessageVarList.TXN_FAILED_INVALID_TXN_ID;
        } else if (inputBean.getMerchantName() != null && !Validation.isSpecailCharacter(inputBean.getMerchantName())) {
            message = MessageVarList.TXN_FAILED_INVALID_MERCHNT_NAME;
        } else if (inputBean.getCardHolderName() != null && !Validation.isSpecailCharacter(inputBean.getCardHolderName())) {
            message = MessageVarList.TXN_FAILED_INVALID_CARD_HOLDR_NAME;
        } else if (inputBean.getCardNumber() != null && !Validation.isSpecailCharacter(inputBean.getCardNumber())) {
            message = MessageVarList.TXN_FAILED_INVALID_CARD_NUMBER;
        }
        return message;
    }
}
