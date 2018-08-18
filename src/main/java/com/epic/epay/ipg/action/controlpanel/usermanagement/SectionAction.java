package com.epic.epay.ipg.action.controlpanel.usermanagement;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.epic.epay.ipg.bean.controlpanel.usermanagement.SectionInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.dao.controlpanel.usermanagement.SectionDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgsection;
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
 *
 * @author : chalitha
 */
public class SectionAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    SectionInputBean inputBean = new SectionInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called SectionAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.SECTION_MGT_PAGE;
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
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.SECTION_MGT_PAGE, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
                    inputBean.setVdelete(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                    inputBean.setVupdatelink(false);
                } else if (task.getTaskcode().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                }
            }
        }
        inputBean.setVupdatebutt(true);

    }

    public String view() {

        System.out.println("called SectionAction :view");
        String result = "view";

        try {

            this.applyUserPrivileges();
            CommonDAO dao = new CommonDAO();
            inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the section");
            Logger.getLogger(SectionAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //load pop up for section insertion
    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called SectionAction : viewpopup");

        try {

            this.applyUserPrivileges();
            CommonDAO dao = new CommonDAO();
            inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

        } catch (Exception e) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the section");
            Logger.getLogger(SectionAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String add() {

        System.out.println("called SectionAction : add");
        String result = "message";

        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            SectionDAO dao = new SectionDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.SECTION_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Section code " + inputBean.getSectioncode() + " added", null);
                message = dao.insertSection(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Section " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the section");
            Logger.getLogger(SectionAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //load pop up for section update
    public String detail() {

        System.out.println("called SectionAction: detail");
        String result = "detail";
        Ipgsection page;

        try {

            if (inputBean.getSectioncode() != null && !inputBean.getSectioncode().isEmpty()) {

                CommonDAO comdao = new CommonDAO();
                inputBean.setStatusList(comdao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                SectionDAO dao = new SectionDAO();

                page = dao.findSectionById(inputBean.getSectioncode());

                inputBean.setSectioncode(page.getSectioncode());
                inputBean.setDescription(page.getDescription());
                inputBean.setSortKey(page.getSortkey().toString());
                inputBean.setStatus(page.getIpgstatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty section code.");
            }

        } catch (Exception e) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the section");
            Logger.getLogger(SectionAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return result;

    }

    public String update() {

        System.out.println("called SectionAction : update");
        String result = "message";

        try {

            if (inputBean.getSectioncode() != null && !inputBean.getSectioncode().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    SectionDAO dao = new SectionDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.SECTION_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Section code " + inputBean.getSectioncode() + " updated", null);
                    message = dao.updateSection(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Section " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {

            Logger.getLogger(SectionAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError(MessageVarList.COMMON_ERROR_UPDATE + " the section");

        }
        return result;
    }

    public String delete() {

        System.out.println("called SectionAction : delete");
        String message = null;
        String result = "delete";

        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            SectionDAO dao = new SectionDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.SECTION_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Section code " + inputBean.getSectioncode() + " deleted", null);
            message = dao.deleteSection(inputBean, audit);

            if (message.isEmpty()) {
                message = "Section " + MessageVarList.COMMON_SUCC_DELETE;
            }

            inputBean.setMessage(message);

        } catch (Exception e) {

            Logger.getLogger(SectionAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));

        }
        return result;
    }

    public String list() {
        System.out.println("called SectionAction: list");
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

            SectionDAO dao = new SectionDAO();
            List<SectionInputBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("Section search using [ ")
                        .append(checkEmptyorNullString("Section Code", inputBean.getSectioncode()))
                        .append(checkEmptyorNullString("Description ", inputBean.getDescription()))
                        .append(checkEmptyorNullString("Sort Key", inputBean.getSortKey()))
                        .append(checkEmptyorNullString("Status", inputBean.getStatus()))
                        .append(" ] parameters");

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.SECTION_MGT_PAGE, SectionVarList.USERMANAGEMENT, searchParameters.toString(), null);
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
            Logger.getLogger(SectionAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the section");
        }
        return result;
    }

    //find section for update reset
    public String find() {
        System.out.println("called SectionAction: find");
        String result = "find";
        Ipgsection section = new Ipgsection();
        try {
            if (inputBean.getSectioncode() != null && !inputBean.getSectioncode().isEmpty()) {

                SectionDAO dao = new SectionDAO();

                section = dao.findSectionById(inputBean.getSectioncode());

                inputBean.setSectioncode(section.getSectioncode());
                inputBean.setDescription(section.getDescription());
                inputBean.setStatus(section.getIpgstatus().getStatuscode());
                inputBean.setSortKey(section.getSortkey().toString());

            } else {
                inputBean.setMessage("Empty section code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage(MessageVarList.COMMON_ERROR_PROCESS + "the section");
            Logger.getLogger(SectionAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;

    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getSectioncode() == null || inputBean.getSectioncode().trim().isEmpty()) {
            message = MessageVarList.SECTION_MGT_EMPTY_SECTION_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.SECTION_MGT_EMPTY_DESCRIPTION;
        } else if (inputBean.getStatus() != null && inputBean.getStatus().isEmpty()) {
            message = MessageVarList.SECTION_MGT_EMPTY_STATUS;
        } else if (inputBean.getSortKey() != null && inputBean.getSortKey().isEmpty()) {
            message = MessageVarList.SECTION_MGT_EMPTY_SORTKEY;
        } else {
            if (!Validation.isSpecailCharacter(inputBean.getSectioncode())) {
                message = MessageVarList.SECTION_MGT_ERROR_SECTIONCODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
                message = MessageVarList.SECTION_MGT_ERROR_DESC_INVALID;
            }
        }

        return message;
    }

}
