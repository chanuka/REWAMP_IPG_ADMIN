/**
 * 
 */
package com.epic.epay.ipg.action.reportexplorer;

import com.epic.epay.ipg.bean.controlpanel.reportexplorer.MerchantDetailBean;
import com.epic.epay.ipg.bean.controlpanel.reportexplorer.TransactionDetailBean;
import com.epic.epay.ipg.bean.controlpanel.reportexplorer.TransactionExplorerBean;
import com.epic.epay.ipg.bean.controlpanel.reportexplorer.TransactionExplorerInputBean;
import com.epic.epay.ipg.bean.controlpanel.reportexplorer.TransactionIndividualDetailBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.dao.reportexplorer.TransactionExplorerDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.epic.epay.ipg.util.varlist.UserRoleTypeVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.struts2.ServletActionContext;

/**
 *
 * Created on  :Sep 16, 2014, 10:12:04 AM
 * @author 	   :thushanth
 *
 */
public class TransactionExplorerAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {
	
	TransactionExplorerInputBean inputBean = new TransactionExplorerInputBean();        
        HttpServletRequest request = ServletActionContext.getRequest();
        Ipgsystemuser sysUser =(Ipgsystemuser)request.getSession(false).getAttribute(SessionVarlist.SYSTEMUSER);
//	TransactionDetailBean detailBean = new TransactionDetailBean();

	   
    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called TransactionExplorer : execute");
        return SUCCESS;
    }

    public String view() {

        String result = "view";
        try {

            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_TRANSACTION));
                inputBean.setCardList(dao.getDefultCardAssociationList());
                
                TransactionExplorerDAO txndao = new TransactionExplorerDAO();
                // logged user is an Admin or not
//                if(!(sysUser.getIpguserroleByUserrolecode().getIpguserroletype().getUserroletypecode()).equals(UserRoleTypeVarList.ADMINISTRATOR)){
//                    inputBean.setMerID(sysUser.getMerchantid());
//                    inputBean.setMerName(CommonDAO.findMerNameById());
//                }
                
                if(txndao.isMerchantUser(sysUser.getUsername()) ){
                    if(txndao.isHeadMerchant(sysUser.getUsername()) && txndao.txnInitiatedStatus(sysUser.getUsername())){
                        
                    }else{
                        inputBean.setMerID(sysUser.getMerchantid());
                        inputBean.setMerName(CommonDAO.findMerNameById());
                    }
                    
                }

            } else {

                result = "loginpage";
            }
            System.out.println("called TransactionExplorer :view");


        } catch (Exception ex) {
            addActionError("Transaction Explorer " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public String detailView() {
    	
        System.out.println("called TransactionExplorer : DetailView");
        try {
        	if (inputBean.getMerID() != null && !inputBean.getMerID().isEmpty()) {
        	
        	TransactionExplorerDAO dao = new TransactionExplorerDAO();
//        	MerchantDetailBean dataBean = dao.findMerById(inputBean.getMerID());
//        	inputBean.setMerDataBean(dataBean);
                TransactionIndividualDetailBean txndatabean =dao.findTxnById(inputBean);
                inputBean.setTxnDataBean(txndatabean);
        	
        	HttpSession session = ServletActionContext.getRequest().getSession(false);
                session.setAttribute(SessionVarlist.TRANS_EXPOR_INDIVIDUAL_BEAN, txndatabean);
            session.setAttribute(SessionVarlist.TXNIDEXP, inputBean.getTxnid());
        	
        	} else {
        		addActionError("Merchant id not found");
//        		CommonDAO dao = new CommonDAO();
//                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_TRANSACTION));
//                inputBean.setCardList(dao.getDefultCardAssociationList());
//        		return "view";
            }
    
        } catch (Exception e) {
            Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Page");
        }
        return "detailview";
    } 
    
    public String detailList() {
        System.out.println("called TransactionExplorer : DetailList");
        try {
//        	inputBean.setTxnid("1374043466896606");
        	HttpSession session = ServletActionContext.getRequest().getSession(false);
        	inputBean.setTxnid((String)session.getAttribute(SessionVarlist.TXNIDEXP));
        	
            if (inputBean.getTxnid() != null && inputBean.getTxnid() != "") {

                int rows = inputBean.getRows();
                int page = inputBean.getPage();
                int to = (rows * page);
                int from = to - rows;
                long records = 0;
                String orderBy = "";
                if (!inputBean.getSidx().isEmpty()) {
                    orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
                }
                TransactionExplorerDAO dao = new TransactionExplorerDAO();
                List<TransactionDetailBean> detailList = dao.getDetailList(inputBean, rows, from, orderBy);

                
                
//                if (detailList.getMerList()) {
//                    records = detailList.get(0).getFullCount();
                    records = 1;
                    inputBean.setRecords(records);
//                    inputBean.setMerGridModel(detailList.getMerList());
                    inputBean.setTxnGridModel(detailList);
                    int total = (int) Math.ceil((double) records / (double) rows);
                    inputBean.setTotal(total);

//                } else {
//                    inputBean.setRecords(0L);
//                    inputBean.setTotal(0);
//                }
            }else{
            	addActionError("No Transaction ID selected");
            }

        } catch (Exception e) {
            Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Page");
        }
        return "detaillist";
    } 
    
    public String list() {
        System.out.println("called TransactionExplorer : List");
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
                TransactionExplorerDAO dao = new TransactionExplorerDAO();
                String flag = "";
                boolean isMerchant = dao.isMerchantUser(sysUser.getUsername());
                if (isMerchant){
                    boolean isHeadMerchant = dao.isHeadMerchant(sysUser.getUsername());
                    if (isHeadMerchant) {
                        boolean txnInitiatedStatusYes = dao.txnInitiatedStatus(sysUser.getUsername());
                        if (txnInitiatedStatusYes) {
                            flag ="YES";
                        } else {
                            flag ="NO";
                            inputBean.setMerID(sysUser.getMerchantid());
                            inputBean.setMerName(CommonDAO.findMerNameById());
                        }
                    } else {
                        flag ="NO";
                        inputBean.setMerID(sysUser.getMerchantid());
                        inputBean.setMerName(CommonDAO.findMerNameById());
                    }
                }
                List<TransactionExplorerBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy,flag);
                
                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("Transaction Explorer search using [")
                        .append(checkEmptyorNullString("From Date", inputBean.getFromdate()))
                        .append(checkEmptyorNullString("To Date ", inputBean.getTodate()))
                        .append(checkEmptyorNullString("Transaction ID", inputBean.getTxnid()))
                        .append(checkEmptyorNullString("Merchant ID ", inputBean.getMerID()))
                        .append(checkEmptyorNullString("Card Holder Name", inputBean.getCardholder()))
                        .append(checkEmptyorNullString("Merchant Name ", inputBean.getMerName()))
                        .append(checkEmptyorNullString("Card Number ", inputBean.getCardno()))
                        .append(checkEmptyorNullString("Purchase Date", inputBean.getPurDate()))
                        .append(checkEmptyorNullString("Status ", inputBean.getStatus()))
                        .append(checkEmptyorNullString("ECI Value", inputBean.getECIval()))
                        .append(checkEmptyorNullString("Card Association ", inputBean.getCardtype()))
                        .append("] parameters ");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.TXN_EXPLORER_PAGE, SectionVarList.REPORTEXPLORER, searchParameters.toString(), null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);
                if (!dataList.isEmpty()) {
                    records = dataList.get(0).getFullCount();
                    inputBean.setRecords(records);
                    inputBean.setGridModel(dataList);
                    int total = (int) Math.ceil((double) records / (double) rows);
                    inputBean.setTotal(total);

                    HttpSession session = ServletActionContext.getRequest().getSession(false);
                    session.setAttribute(SessionVarlist.TXNEXPBEAN, inputBean);


                } else {
                    inputBean.setRecords(0L);
                    inputBean.setTotal(0);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Page");
        }
        return "list";
    }

    public String generate() {

        String result ="view";
//        String result = "report";
        try {

            if (inputBean.getReporttype().equalsIgnoreCase("pdf")) {
                HttpSession session = ServletActionContext.getRequest().getSession(false);
                TransactionExplorerInputBean searchBean = (TransactionExplorerInputBean) session.getAttribute(SessionVarlist.TXNEXPBEAN);

                // logged user is an Admin or not
//                if (!(sysUser.getIpguserroleByUserrolecode().getIpguserroletype().getUserroletypecode()).equals(UserRoleTypeVarList.ADMINISTRATOR)) {
//                    searchBean.setMerID(sysUser.getMerchantid());
//                    searchBean.setMerName(CommonDAO.findMerNameById());
//                }
                

                HashMap reportMap = new HashMap();
                TransactionExplorerDAO dao = new TransactionExplorerDAO();
                ServletContext context = ServletActionContext.getServletContext();
                String imgPath = context.getRealPath("resources/img/ipg2.png");
                
                String flag = "";
                boolean isMerchant = dao.isMerchantUser(sysUser.getUsername());
                if (isMerchant){
                    boolean isHeadMerchant = dao.isHeadMerchant(sysUser.getUsername());
                    if (isHeadMerchant) {
                        boolean txnInitiatedStatusYes = dao.txnInitiatedStatus(sysUser.getUsername());
                        if (txnInitiatedStatusYes) {
                            flag ="YES";
                        } else {
                            searchBean.setMerID(sysUser.getMerchantid());
                            searchBean.setMerName(CommonDAO.findMerNameById());
                            flag ="NO";
                        }
                    } else {
                        searchBean.setMerID(sysUser.getMerchantid());
                        searchBean.setMerName(CommonDAO.findMerNameById());
                        flag ="NO";
                    }
                }

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
                if (searchBean.getECIval() == null || searchBean.getECIval().isEmpty()) {
                    reportMap.put("eci", "--");
                } else {
                    reportMap.put("eci", searchBean.getECIval().trim());
                }
                if (searchBean.getPurDate() == null || searchBean.getPurDate().isEmpty()) {
                    reportMap.put("purdate", "--");
                } else {
                    reportMap.put("purdate", searchBean.getPurDate());
                }
                if (searchBean.getCardholder() == null || searchBean.getCardholder().isEmpty()) {
                    reportMap.put("cardholder", "--");
                } else {
                    reportMap.put("cardholder", searchBean.getCardholder().trim());
                }
                if (searchBean.getCardno() == null || searchBean.getCardno().isEmpty()) {
                    reportMap.put("cardno", "--");
                } else {
                    reportMap.put("cardno", searchBean.getCardno().trim());
                }
                if (searchBean.getMerName() == null || searchBean.getMerName().isEmpty()) {
                    reportMap.put("mername", "--");
                } else {
                    reportMap.put("mername", searchBean.getMerName().trim());
                }
                if (searchBean.getMerID() == null || searchBean.getMerID().isEmpty()) {
                    reportMap.put("merid", "--");
                } else {
                    reportMap.put("merid", searchBean.getMerID().trim());
                }
                if (searchBean.getTxnid() == null || searchBean.getTxnid().isEmpty()) {
                    reportMap.put("txnid", "--");
                } else {
                    reportMap.put("txnid", searchBean.getTxnid());
                }

                reportMap.put("imageurl", imgPath);

                List<TransactionExplorerBean> dataList = dao.getSearchList(searchBean, (int) searchBean.getFullCount(), 0, " order by p.ipgtransactionid",flag);

                if (searchBean.getStatus() == null || searchBean.getStatus().isEmpty()) {
                    reportMap.put("status", "--");
                } else {
                    //                reportMap.put("status", searchBean.getStatus());
                    reportMap.put("status", dataList.get(0).getStatus());
                }
                if (searchBean.getCardtype() == null || searchBean.getCardtype().isEmpty()) {
                    reportMap.put("cardtype", "--");
                } else {
                    reportMap.put("cardtype", searchBean.getCardtype());
                    reportMap.put("cardtype", dataList.get(0).getAssociationcode());
                }

                inputBean.setParameterMap(reportMap);
                inputBean.setLst(dataList);
                HttpServletRequest request = ServletActionContext.getRequest();
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.TXN_EXPLORER_PAGE, SectionVarList.REPORTEXPLORER, "Transaction explorer PDF report generated ", null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);
                result = "report";
            } else if (inputBean.getReporttype().trim().equalsIgnoreCase("exel")) {
                TransactionExplorerDAO dao = new TransactionExplorerDAO();
                result = "excelreport";
                ByteArrayOutputStream outputStream = null;
                try {

                    HttpSession session = ServletActionContext.getRequest().getSession(false);
                    
                    TransactionExplorerInputBean searchBean = (TransactionExplorerInputBean) session.getAttribute(SessionVarlist.TXNEXPBEAN);
                    String flag = "";
                    boolean isMerchant = dao.isMerchantUser(sysUser.getUsername());
                    if (isMerchant){
                        boolean isHeadMerchant = dao.isHeadMerchant(sysUser.getUsername());
                        if (isHeadMerchant) {
                            boolean txnInitiatedStatusYes = dao.txnInitiatedStatus(sysUser.getUsername());
                            if (txnInitiatedStatusYes) {
                                flag ="YES";
                            } else {
                                searchBean.setMerID(sysUser.getMerchantid());
                                searchBean.setMerName(CommonDAO.findMerNameById());
                                flag ="NO";
                            }
                        } else {
                            searchBean.setMerID(sysUser.getMerchantid());
                            searchBean.setMerName(CommonDAO.findMerNameById());
                            flag ="NO";
                        }
                    }
                    Object object = dao.generateExcelReport(searchBean,flag);
                    if (object instanceof SXSSFWorkbook) {
                        SXSSFWorkbook workbook = (SXSSFWorkbook) object;
                        outputStream = new ByteArrayOutputStream();
                        workbook.write(outputStream);
                        inputBean.setExcelStream(new ByteArrayInputStream(outputStream.toByteArray()));
//
                    } else if (object instanceof ByteArrayOutputStream) {
                        outputStream = (ByteArrayOutputStream) object;
                        inputBean.setZipStream(new ByteArrayInputStream(outputStream.toByteArray()));
                        result = "zip";
                    }

                    HttpServletRequest request = ServletActionContext.getRequest();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.TXN_EXPLORER_PAGE, SectionVarList.REPORTEXPLORER, "Transaction explorer excel report generated ", null);
                    SystemAuditDAO sysdao = new SystemAuditDAO();
                    sysdao.saveAudit(audit);

                } catch (Exception e) {
                    addActionError(MessageVarList.COMMON_ERROR_PROCESS + " exception detail excel report");
                    Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, e);
//                    this.loadPageData();
                    result = "view";
                    throw e;
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.flush();
                            outputStream.close();
                        }

                    } catch (IOException ex) {
                        //do nothing
                    }
                }
            }
            


        } catch (Exception ex) {
            addActionError("Transaction Explorer " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.TXN_EXPLORER_PAGE;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("list".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("detailView".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("detailList".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("generate".equals(method)) {
            task = TaskVarList.GENERATE_TASK;
        } else if ("individualReport".equals(method)) {
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
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.TXN_EXPLORER_PAGE, request);

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
    
    public String individualReport() {
        System.out.println("called TransactionExplorerAction: individualReport");
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.TXN_EXPLORER_PAGE, SectionVarList.REPORTEXPLORER, "Transaction explorer individual report generated", null);
            SystemAuditDAO dao = new SystemAuditDAO();
            dao.saveAudit(audit);
//        
            //get image path
            ServletContext context = ServletActionContext.getServletContext();
            String imgPath = context.getRealPath("resources/img/ipg2.png");

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            TransactionIndividualDetailBean detailBean = (TransactionIndividualDetailBean) session.getAttribute(SessionVarlist.TRANS_EXPOR_INDIVIDUAL_BEAN);
            inputBean.setTxnDataBean(detailBean);
//            parameterMap.put("bankaddressheader", CommonVarList.REPORT_SBANK_ADD_HEADER);
//            parameterMap.put("printeddate", sdf.format(cal.getTime()));
//            parameterMap.put("bankaddress", CommonVarList.REPORT_SBANK_ADD);
//            parameterMap.put("banktel", CommonVarList.REPORT_SBANK_TEL);
//            parameterMap.put("bankmail", CommonVarList.REPORT_SBANK_MAIL);
            HashMap reportMap = new HashMap();
            reportMap.put("imageurl", imgPath);
            inputBean.setParameterMap(reportMap);

        } catch (Exception e) {
            Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Transaction explorer " + MessageVarList.COMMON_ERROR_PROCESS);
            return "message";
        }
        return "individualreport";
    }
}




