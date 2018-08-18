/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.reportexplorer;

import com.epic.epay.ipg.bean.controlpanel.reportexplorer.MerchantTransactionBean;
import com.epic.epay.ipg.bean.controlpanel.reportexplorer.MerchantTransactionInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.reportexplorer.MerchantTransactionDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author chalitha
 */
public class MerchantTransactionAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    MerchantTransactionInputBean inputBean = new MerchantTransactionInputBean();

   
    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called merchant transaction : execute");
        return SUCCESS;
    }

    public String view() {

        String result = "view";
        try {

            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_TRANSACTION));
                inputBean.setCardList(dao.getDefultCardAssociationList());

            } else {

                result = "loginpage";
            }
            System.out.println("called MerchantTransaction :view");


        } catch (Exception ex) {
            addActionError("Merchant Transaction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantTransactionAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String list() {
        System.out.println("called merchant transaction : List");
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
                MerchantTransactionDAO dao = new MerchantTransactionDAO();
                List<MerchantTransactionBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

                if (!dataList.isEmpty()) {
                    records = dataList.get(0).getFullCount();
                    inputBean.setRecords(records);
                    inputBean.setGridModel(dataList);
                    int total = (int) Math.ceil((double) records / (double) rows);
                    inputBean.setTotal(total);

                    HttpSession session = ServletActionContext.getRequest().getSession(false);
                    session.setAttribute(SessionVarlist.TXNSEARCHBEAN, inputBean);


                } else {
                    inputBean.setRecords(0L);
                    inputBean.setTotal(0);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MerchantTransactionAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Page");
        }
        return "list";
    }

    public String generate() {

        String result = "report";
        try {

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            MerchantTransactionInputBean searchBean = (MerchantTransactionInputBean) session.getAttribute(SessionVarlist.TXNSEARCHBEAN);

            HashMap reportMap = new HashMap();
            MerchantTransactionDAO dao = new MerchantTransactionDAO();
            ServletContext context = ServletActionContext.getServletContext();
            String imgPath = context.getRealPath("resources/img/sampath_logo.jpg");

            if (searchBean.getFromdate() == null || searchBean.getFromdate().isEmpty()) {
                reportMap.put("Fromdate", "--");
            } else {
                reportMap.put("Fromdate", searchBean.getFromdate());
            }

            if (searchBean.getTodate() == null || searchBean.getTodate().isEmpty()) {
                reportMap.put("Todate", "--");
            } else {
                reportMap.put("Todate", searchBean.getTodate());
            }
            reportMap.put("imageurl", imgPath);

            List<MerchantTransactionBean> dataList = dao.getSearchList(searchBean, (int) searchBean.getFullCount(), 0, " order by p.ipgtransactionid");

            inputBean.setParameterMap(reportMap);
            inputBean.setLst(dataList);


        } catch (Exception ex) {
            addActionError("Merchant Transaction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantTransactionAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    
    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.MERCHANT_TXN_PAGE;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("list".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("generate".equals(method)) {
            task = TaskVarList.GENERATE_TASK;
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
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.MERCHANT_TXN_PAGE, request);

        inputBean.setVsearch(true);
        inputBean.setVviewlink(true);
        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                    inputBean.setVviewlink(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.GENERATE_TASK)) {
                    inputBean.setVgenerate(false);
                }
            }
        }
        return true;
    }
}
