/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.usermanagement;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.epic.epay.ipg.bean.controlpanel.usermanagement.PageInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.dao.controlpanel.usermanagement.PageDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgpage;
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

/**
 * @author Asitha Liyanawaduge
 *
 * 25/10/2013
 */
public class PageAction extends ActionSupport implements ModelDriven<Object>,
        AccessControlService {

    PageInputBean inputBean = new PageInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called PageAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.PAGE_MGT_PAGE;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("list".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("add".equals(method)) {
            task = TaskVarList.ADD_TASK;
        } else if ("delete".equals(method)) {
            task = TaskVarList.DELETE_TASK;
        } else if ("find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("update".equals(method)) {
            task = TaskVarList.UPDATE_TASK;
        } else if ("viewpopup".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("detail".equals(method)) {
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

    private void applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.PAGE_MGT_PAGE, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                } else if (task.getTaskcode().equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
                    inputBean.setVdelete(false);
                } else if (task.getTaskcode().equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                    inputBean.setVupdatelink(false);
                } else if (task.getTaskcode().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                }
            }
        }
        inputBean.setVupdatebutt(true);

    }

    public String view() {
        System.out.println("called PageAction :view");
        String result = "view";

        try {

            this.applyUserPrivileges();
            CommonDAO dao = new CommonDAO();
            inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the page");
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //load pop up for page insertion
    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called PageAction : viewpopup");

        try {

            this.applyUserPrivileges();
            CommonDAO dao = new CommonDAO();
            inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

        } catch (Exception e) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the page");
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String add() {
        System.out.println("called PageAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            PageDAO pageDao = new PageDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.PAGE_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Page code " + inputBean.getPageCode() + " added", null);
                message = pageDao.insertPage(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Page " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the page");
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //load pop up for task update
    public String detail() {
        System.out.println("called PageAction: detail");
        String result = "detail";
        Ipgpage page;

        try {
            if (inputBean.getPageCode() != null && !inputBean.getPageCode().isEmpty()) {
                CommonDAO comdao = new CommonDAO();
                inputBean.setStatusList(comdao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                PageDAO dao = new PageDAO();

                page = dao.findPageById(inputBean.getPageCode());

                inputBean.setPageCode(page.getPagecode());
                inputBean.setDescription(page.getDescription());
                inputBean.setSortKey(page.getSortkey().toString());
                inputBean.setStatus(page.getIpgstatus().getStatuscode());
                inputBean.setUrl(page.getUrl());
                inputBean.setPageRoot(page.getRoot());

            } else {
                inputBean.setMessage("Empty page code.");
            }

        } catch (Exception e) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the page");
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return result;

    }

    public String update() {

        System.out.println("called PageAction : update");
        String result = "message";
        try {
            if (inputBean.getPageCode() != null && !inputBean.getPageCode().isEmpty()) {
                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    PageDAO pageDao = new PageDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.PAGE_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Page code " + inputBean.getPageCode() + " updated", null);
                    message = pageDao.updatePage(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Page " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError(MessageVarList.COMMON_ERROR_UPDATE + " the page");
        }
        return result;
    }

    public String delete() {
        System.out.println("called PageAction : delete");
        String message = null;
        String result = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            PageDAO pageDao = new PageDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.PAGE_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Page code " + inputBean.getPageCode() + " deleted", null);
            message = pageDao.deletePage(inputBean, audit);
            if (message.isEmpty()) {
                message = "Page " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
            addActionError(MessageVarList.COMMON_ERROR_DELETE + " the page");
        }
        return result;
    }

    public String list() {
        System.out.println("called PageAction: list");
        String result = "list";
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

            PageDAO pageDao = new PageDAO();
            List<PageInputBean> dataList = pageDao.getSearchList(inputBean, rows, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("Page search using [ ")
                        .append(checkEmptyorNullString("Page Code", inputBean.getPageCode()))
                        .append(checkEmptyorNullString("Description ", inputBean.getDescription()))
                        .append(checkEmptyorNullString("URL", inputBean.getUrl()))
                        .append(checkEmptyorNullString("Page Root", inputBean.getPageRoot()))
                        .append(checkEmptyorNullString("Sort Key", inputBean.getSortKey()))
                        .append(checkEmptyorNullString("Status", inputBean.getStatus()))
                        .append(" ] parameters");

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.PAGE_MGT_PAGE, SectionVarList.USERMANAGEMENT, searchParameters.toString(), null);
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

        } catch (Exception e) {
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the page");
        }
        return result;
    }

    //find page for update reset
    public String find() {
        System.out.println("called PageAction: find");
        String result = "find";
        Ipgpage ipgPage = null;
        try {
            if (inputBean.getPageCode() != null && !inputBean.getPageCode().isEmpty()) {

                PageDAO dao = new PageDAO();

                ipgPage = dao.findPageById(inputBean.getPageCode());

                inputBean.setDescription(ipgPage.getDescription());
                inputBean.setUrl(ipgPage.getUrl());
                inputBean.setPageRoot(ipgPage.getRoot());
                inputBean.setSortKey(ipgPage.getSortkey().toString());
                inputBean.setStatus(ipgPage.getIpgstatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty page code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage(MessageVarList.COMMON_ERROR_PROCESS + " the page");
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getPageCode() == null || inputBean.getPageCode().trim().isEmpty()) {
            message = MessageVarList.EMPTY_PAGE_CODE;
        } else if (!Validation.isSpecailCharacter(inputBean.getPageCode())) {
            message = MessageVarList.INVALID_PAGE_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.EMPTY_PAGE_DESCRIPTION;
        } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
            message = MessageVarList.INVALID_PAGE_DESCRIPTION;
        } else if (inputBean.getUrl() == null || inputBean.getUrl().trim().isEmpty()) {
            message = MessageVarList.EMPTY_PAGE_URL;
        } else if (inputBean.getPageRoot() == null || inputBean.getPageRoot().trim().isEmpty()) {
            message = MessageVarList.EMPTY_PAGE_ROOT;
        } else if (!Validation.isSpecailCharacter(inputBean.getPageRoot())) {
            message = MessageVarList.INVALID_PAGE_ROOT;
        } else if (inputBean.getSortKey() == null || inputBean.getSortKey().trim().isEmpty()) {
            message = MessageVarList.EMPTY_PAGE_SORTKEY;
        } else if (!Validation.isNumeric(inputBean.getSortKey())) {
            message = MessageVarList.INVALID_SORTKEY;
        } else if (inputBean.getStatus() != null && inputBean.getStatus().isEmpty()) {
            message = MessageVarList.EMPTY_PAGE_STATUS;
        }
        return message;
    }

}
