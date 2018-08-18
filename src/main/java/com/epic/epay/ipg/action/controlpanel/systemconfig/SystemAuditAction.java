/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.AuditDataBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.AuditSearchDTO;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.PartialList;
import com.epic.epay.ipg.util.mapping.Ipgpage;
import com.epic.epay.ipg.util.mapping.Ipgsection;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.mapping.Ipguserrole;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
 * @author chalitha
 */
public class SystemAuditAction extends ActionSupport implements ModelDriven<AuditSearchDTO>, AccessControlService {

    private AuditSearchDTO auditSearchDTO = new AuditSearchDTO();

    public String view() {

        String result = "view";
        try {

            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();

                auditSearchDTO.setUserRoleList(dao.getUserRoleList());
                auditSearchDTO.setSectionList(dao.getSectionList());
                auditSearchDTO.setPageList(dao.getPageList());
                auditSearchDTO.setTaskList(dao.getTaskList());

            } else {

                result = "loginpage";
            }
            System.out.println("called SystemAuditAction :view");


        } catch (Exception ex) {
            addActionError("System Audit " + MessageVarList.COMMON_ERROR_PROCESS);
        }
        return result;
    }
    
    public String viewDetail() {
        System.out.println("called SystemAuditAction :ViewDetail");
        try {
            SystemAuditDAO dao = new SystemAuditDAO();
            AuditDataBean dataBean = dao.findAuditById(auditSearchDTO.getAuditId());
            auditSearchDTO.setAuditDataBean(dataBean);
            
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            session.setAttribute(SessionVarlist.SYSTEM_AUDIT_INDIVIDUAL_BEAN, dataBean);

        } catch (Exception ex) {
            addActionError("SystemAuditAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemAuditAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "viewdetail";
    }
    
    public String list() {
        System.out.println("called SystemAuditAction : List");
        try {
            if (auditSearchDTO.isSearch()) {

                int rows = auditSearchDTO.getRows();
                int page = auditSearchDTO.getPage();
                int to = (rows * page);
                int from = to - rows;
                long records = 0;
                String sortIndex = "";
                String sortOrder = "";
                
                List<AuditDataBean> dataList = null;

                if (!auditSearchDTO.getSidx().isEmpty()) {
                    sortIndex = auditSearchDTO.getSidx();
                    sortOrder = auditSearchDTO.getSord();
                }

                SystemAuditDAO dao = new SystemAuditDAO();
                PartialList<AuditDataBean> searchList = dao.getSearchList(auditSearchDTO, rows, from, sortIndex, sortOrder);
                
                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("System audit search using [")
                        .append(checkEmptyorNullString("From Date", auditSearchDTO.getFdate()))
                        .append(checkEmptyorNullString("To Date", auditSearchDTO.getTdate()))
                        .append(checkEmptyorNullString("User Role", auditSearchDTO.getUserRole()))
                        .append(checkEmptyorNullString("Section", auditSearchDTO.getSection()))
                        .append(checkEmptyorNullString("Page", auditSearchDTO.getIpgpage()))
                        .append(checkEmptyorNullString("Task", auditSearchDTO.getTask()))
                        .append(checkEmptyorNullString("Description", auditSearchDTO.getDescription()))
                        .append(checkEmptyorNullString("IP Address", auditSearchDTO.getIp()))
                        .append("] parameters ");
                HttpServletRequest request = ServletActionContext.getRequest();
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.SYSTEM_AUDIT_PAGE, SectionVarList.SYSTEMAUDIT, searchParameters.toString(), null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);
                
                dataList = searchList.getList();
                records = searchList.getFullCount();

                if (!dataList.isEmpty()) {
                    auditSearchDTO.setRecords(records);
                    auditSearchDTO.setGridModel(dataList);
                    int total = (int) Math.ceil((double) records / (double) rows);
                    auditSearchDTO.setTotal(total);
                    
                    HttpSession session = ServletActionContext.getRequest().getSession(false);
                    session.setAttribute(SessionVarlist.SYSAUDITBEAN, auditSearchDTO);
                } else {
                    auditSearchDTO.setRecords(0L);
                    auditSearchDTO.setTotal(0);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(SystemAuditAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " SystemAudit");
        }
        return "list";
    }
    
    public String generate() {

        String result ="view";
//        String result = "report";
        try {

            if (auditSearchDTO.getReporttype().equalsIgnoreCase("pdf")) {
                HttpSession session = ServletActionContext.getRequest().getSession(false);
                AuditSearchDTO searchBean = (AuditSearchDTO) session.getAttribute(SessionVarlist.SYSAUDITBEAN);

                CommonDAO comDao =new CommonDAO();
                HashMap reportMap = new HashMap();
                SystemAuditDAO dao = new SystemAuditDAO();
                ServletContext context = ServletActionContext.getServletContext();
                String imgPath = context.getRealPath("resources/img/ipg2.png");

                if (searchBean.getFdate() == null || searchBean.getFdate().isEmpty()) {
                    reportMap.put("Fromdate", "--");
                } else {
                    reportMap.put("Fromdate", searchBean.getFdate());
                }

                if (searchBean.getTdate() == null || searchBean.getTdate().isEmpty()) {
                    reportMap.put("Todate", "--");
                } else {
                    reportMap.put("Todate", searchBean.getTdate());
                }
                if (searchBean.getUserRole()== null || searchBean.getUserRole().isEmpty()) {
                    reportMap.put("userrole", "--");
                } else {
                    Ipguserrole userRole = comDao.getUserRoleDescription(searchBean.getUserRole().trim());
                    reportMap.put("userrole", userRole.getDescription());
                }
                if (searchBean.getDescription()== null || searchBean.getDescription().isEmpty()) {
                    reportMap.put("description", "--");
                } else {
                    reportMap.put("description", searchBean.getDescription());
                }
                if (searchBean.getSection()== null || searchBean.getSection().isEmpty()) {
                    reportMap.put("section", "--");
                } else {
                    Ipgsection section =comDao.getSectionDescription(searchBean.getSection().trim());
                    reportMap.put("section", section.getDescription());
                }
                if (searchBean.getIpgpage()== null || searchBean.getIpgpage().isEmpty()) {
                    reportMap.put("page", "--");
                } else {
                    Ipgpage page = comDao.getPageDescription(searchBean.getIpgpage().trim());
                    reportMap.put("page", page.getDescription());
                }
                if (searchBean.getTask()== null || searchBean.getTask().isEmpty()) {
                    reportMap.put("task", "--");
                } else {
                    Ipgtask task =comDao.getTaskDescription(searchBean.getTask().trim());
                    reportMap.put("task", task.getDescription());
                }
                if (searchBean.getIp()== null || searchBean.getIp().isEmpty()) {
                    reportMap.put("ip", "--");
                } else {
                    reportMap.put("ip", searchBean.getIp().trim());
                }
               

                reportMap.put("imageurl", imgPath);

                PartialList<AuditDataBean> searchList = dao.getSearchList(searchBean, (int) searchBean.getRecords().intValue(), 0, "","");

                HttpServletRequest request = ServletActionContext.getRequest();
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.SYSTEM_AUDIT_PAGE, SectionVarList.SYSTEMAUDIT, "System audit PDF report generated ", null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);

                auditSearchDTO.setParameterMap(reportMap);
                auditSearchDTO.setLst(searchList.getList());
                result = "report";
            } else if (auditSearchDTO.getReporttype().trim().equalsIgnoreCase("exel")) {
                SystemAuditDAO dao = new SystemAuditDAO();
                CommonDAO comDao =new CommonDAO();
                result = "excelreport";
                ByteArrayOutputStream outputStream = null;
                try {

                    HttpSession session = ServletActionContext.getRequest().getSession(false);
                    AuditSearchDTO searchBean = (AuditSearchDTO) session.getAttribute(SessionVarlist.SYSAUDITBEAN);
                    if (searchBean.getUserRole()!= null && !searchBean.getUserRole().isEmpty()) {
                        Ipguserrole userRole = comDao.getUserRoleDescription(searchBean.getUserRole().trim());
                        searchBean.setUserRoleDes(userRole.getDescription());
                    }
                    if (searchBean.getSection()!= null && !searchBean.getSection().isEmpty()) {
                        Ipgsection section =comDao.getSectionDescription(searchBean.getSection().trim());
                        searchBean.setSectionDes(section.getDescription());
                    }
                    if (searchBean.getIpgpage()!= null && !searchBean.getIpgpage().isEmpty()) {
                        Ipgpage page = comDao.getPageDescription(searchBean.getIpgpage().trim());
                        searchBean.setIpgpageDes(page.getDescription());
                    }
                    if (searchBean.getTask()!= null && !searchBean.getTask().isEmpty()) {
                        Ipgtask task =comDao.getTaskDescription(searchBean.getTask().trim());
                        searchBean.setTaskDes(task.getDescription());
                    }
                    Object object = dao.generateExcelReport(searchBean);
                    if (object instanceof SXSSFWorkbook) {
                        SXSSFWorkbook workbook = (SXSSFWorkbook) object;
                        outputStream = new ByteArrayOutputStream();
                        workbook.write(outputStream);
                        auditSearchDTO.setExcelStream(new ByteArrayInputStream(outputStream.toByteArray()));
//
                    } else if (object instanceof ByteArrayOutputStream) {
                        outputStream = (ByteArrayOutputStream) object;
                        auditSearchDTO.setZipStream(new ByteArrayInputStream(outputStream.toByteArray()));
                        result = "zip";
                    }

                    HttpServletRequest request = ServletActionContext.getRequest();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.SYSTEM_AUDIT_PAGE, SectionVarList.SYSTEMAUDIT, "System audit excel report generated ", null);
                    SystemAuditDAO sysdao = new SystemAuditDAO();
                    sysdao.saveAudit(audit);

                } catch (Exception e) {
                    addActionError(MessageVarList.COMMON_ERROR_PROCESS + " exception detail excel report");
                    Logger.getLogger(SystemAuditAction.class.getName()).log(Level.SEVERE, null, e);
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
            addActionError("System Audit " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemAuditAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String execute() {
        System.out.println("called SystemAuditAction : execute");
        return SUCCESS;
    }

    public AuditSearchDTO getModel() {
        return auditSearchDTO;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.SYSTEM_AUDIT_PAGE;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("list".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("generate".equals(method)) {
            task = TaskVarList.GENERATE_TASK;
        } else if ("viewDetail".equals(method)) {
            task = TaskVarList.VIEW_TASK;
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
        List<Ipgtask> taskList = new Common().getUserTaskListByPage(PageVarList.SYSTEM_AUDIT_PAGE, request);

        auditSearchDTO.setVsearch(true);
        auditSearchDTO.setVviewlink(true);
        auditSearchDTO.setVgenerate(true);
        if (taskList != null && taskList.size() > 0) {
            for (Ipgtask task : taskList) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    auditSearchDTO.setVsearch(false);
                }else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                    auditSearchDTO.setVviewlink(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.GENERATE_TASK)) {
                    auditSearchDTO.setVgenerate(false);
                }
            }
        }
        return true;
    }
    
    public String individualReport() {
        System.out.println("called SystemAuditAction: individualReport");
        try {
//            cal.setTime(CommonDAO.getSystemDateLogin()); 
            HttpServletRequest request = ServletActionContext.getRequest();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.SYSTEM_AUDIT_PAGE, SectionVarList.SYSTEMAUDIT, "IPG System Audit individual report generated", null);
            SystemAuditDAO dao = new SystemAuditDAO();
            dao.saveAudit(audit);
//        
            //get image path
            ServletContext context = ServletActionContext.getServletContext();
            String imgPath = context.getRealPath("resources/img/ipg2.png");

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            AuditDataBean detailBean = (AuditDataBean) session.getAttribute(SessionVarlist.SYSTEM_AUDIT_INDIVIDUAL_BEAN);
            auditSearchDTO.setAuditDataBean(detailBean);
//            parameterMap.put("bankaddressheader", CommonVarList.REPORT_SBANK_ADD_HEADER);
//            parameterMap.put("printeddate", sdf.format(cal.getTime()));
//            parameterMap.put("bankaddress", CommonVarList.REPORT_SBANK_ADD);
//            parameterMap.put("banktel", CommonVarList.REPORT_SBANK_TEL);
//            parameterMap.put("bankmail", CommonVarList.REPORT_SBANK_MAIL);
            HashMap reportMap = new HashMap();
            reportMap.put("imageurl", imgPath);
            auditSearchDTO.setParameterMap(reportMap);

        } catch (Exception e) {
            Logger.getLogger(SystemAuditAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("System Audit " + MessageVarList.COMMON_ERROR_PROCESS);
            return "message";
        }
        return "individualreport";
    }
}
