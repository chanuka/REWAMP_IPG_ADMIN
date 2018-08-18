/**
 * 
 */
package com.epic.epay.ipg.action.reportexplorer;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.epic.epay.ipg.bean.controlpanel.reportexplorer.TransactionSummaryBean;
import com.epic.epay.ipg.bean.controlpanel.reportexplorer.TransactionSummaryInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.reportexplorer.TransactionSummaryDAO;
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
import javax.servlet.ServletContext;

/**
 *
 * Created on  :Sep 15, 2014, 2:45:30 PM
 * @author 	   :thushanth
 *
 */
public class TransactionSummaryAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {
	
	TransactionSummaryInputBean inputBean = new TransactionSummaryInputBean();

	   
	    public Object getModel() {
	        return inputBean;
	    }

	    @Override
	    public String execute() {
	        System.out.println("called TransactionSummary : execute");
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
	            System.out.println("called TransactionSummary :view");


	        } catch (Exception ex) {
	            addActionError("Transaction Summary " + MessageVarList.COMMON_ERROR_PROCESS);
	            Logger.getLogger(TransactionSummaryAction.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return result;
	    }

	    public String list() {
	        System.out.println("called TransactionSummary : List");
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
	                TransactionSummaryDAO dao = new TransactionSummaryDAO();
	                List<TransactionSummaryBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

	                if (!dataList.isEmpty()) {
	                    records = dataList.get(0).getFullCount();
	                    inputBean.setRecords(records);
	                    inputBean.setGridModel(dataList);
	                    int total = (int) Math.ceil((double) records / (double) rows);
	                    inputBean.setTotal(total);

	                    HttpSession session = ServletActionContext.getRequest().getSession(false);
	                    session.setAttribute(SessionVarlist.TXNSUMBEAN, inputBean);


	                } else {
	                    inputBean.setRecords(0L);
	                    inputBean.setTotal(0);
	                }
	            }
	        } catch (Exception e) {
	            Logger.getLogger(TransactionSummaryAction.class.getName()).log(Level.SEVERE, null, e);
	            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Page");
	        }
	        return "list";
	    }

	    public String generate() {

	        String result = "report";
	        try {

	            HttpSession session = ServletActionContext.getRequest().getSession(false);
	            TransactionSummaryInputBean searchBean = (TransactionSummaryInputBean) session.getAttribute(SessionVarlist.TXNSUMBEAN);

	            HashMap reportMap = new HashMap();
	            TransactionSummaryDAO dao = new TransactionSummaryDAO();
                    ServletContext context = ServletActionContext.getServletContext();
                    String imgPath = context.getRealPath("resources/img/ipg2.png");

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

	            List<TransactionSummaryBean> dataList = dao.getSearchList(searchBean, (int) searchBean.getFullCount(), 0, " order by p.ipgtransactionid");

	            inputBean.setParameterMap(reportMap);
	            inputBean.setLst(dataList);


	        } catch (Exception ex) {
	            addActionError("Transaction Summary " + MessageVarList.COMMON_ERROR_PROCESS);
	            Logger.getLogger(TransactionSummaryAction.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return result;
	    }

	    
	    public boolean checkAccess(String method, String userRole) {
	        boolean status = false;
	        String page = PageVarList.TXN_SUM_PAGE;
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
	        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.TXN_SUM_PAGE, request);

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

