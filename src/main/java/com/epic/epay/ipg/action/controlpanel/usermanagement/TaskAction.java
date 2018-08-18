/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.controlpanel.usermanagement;

import com.epic.epay.ipg.bean.controlpanel.usermanagement.TaskInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.dao.controlpanel.usermanagement.TaskDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author chanuka
 */
public class TaskAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    TaskInputBean inputBean = new TaskInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called TaskAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.TASK_MGT_PAGE;
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
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.TASK_MGT_PAGE, request);

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
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                }
            }
        }
        inputBean.setVupdatebutt(true);

    }

    public String view() {

        System.out.println("called TaskAction :view");
        String result = "view";

        try {

            this.applyUserPrivileges();
            CommonDAO dao = new CommonDAO();
            inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " task");
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //load pop up for task insertion
    public String viewpopup() {

        System.out.println("called TaskAction : viewpopup");
        String result = "viewpopup";

        try {

            this.applyUserPrivileges();
            CommonDAO dao = new CommonDAO();
            inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

        } catch (Exception e) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " task");
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String add() {
        System.out.println("called TaskAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            TaskDAO dao = new TaskDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.TASK_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Task code " + inputBean.getTaskCode() + " added", null);
                message = dao.insertPage(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Task " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " task");
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //load pop up for task update
    public String detail() {
        System.out.println("called TaskAction: detail");
        String result = "detail";
        Ipgtask task;

        try {
            if (inputBean.getTaskCode() != null && !inputBean.getTaskCode().isEmpty()) {
                CommonDAO comdao = new CommonDAO();
                inputBean.setStatusList(comdao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                TaskDAO dao = new TaskDAO();

                task = dao.findTaskById(inputBean.getTaskCode());

                inputBean.setTaskCode(task.getTaskcode());
                inputBean.setDescription(task.getDescription());
                inputBean.setSortKey(task.getSortkey().toString());
                inputBean.setStatus(task.getIpgstatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty task code.");
            }

        } catch (Exception e) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " task");
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return result;

    }

    public String update() {

        System.out.println("called TaskAction : update");
        String result = "message";
        try {
            if (inputBean.getTaskCode() != null && !inputBean.getTaskCode().isEmpty()) {
                //set username get in hidden fileds
                inputBean.setTaskCode(inputBean.getTaskCode());
                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    TaskDAO dao = new TaskDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.TASK_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Task code " + inputBean.getTaskCode() + " updated", null);
                    message = dao.updateTask(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Task " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError(MessageVarList.COMMON_ERROR_UPDATE + " task");
        }
        return result;
    }

    public String delete() {
        System.out.println("called TaskAction : delete");
        String message = null;
        String result = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            TaskDAO dao = new TaskDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.TASK_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Task code " + inputBean.getTaskCode() + " deleted", null);
            message = dao.deleteTask(inputBean, audit);
            if (message.isEmpty()) {
                message = "Task " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
            addActionError(MessageVarList.COMMON_ERROR_DELETE + " task");
        }
        return result;
    }

    public String list() {
        System.out.println("called TaskAction: list");
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
            TaskDAO dao = new TaskDAO();
            List<TaskInputBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("[ Task search using ")
                        .append(checkEmptyorNullString("Task Code", inputBean.getTaskCode()))
                        .append(checkEmptyorNullString("Description ", inputBean.getDescription()))
                        .append(checkEmptyorNullString("Sort Key", inputBean.getSortKey()))
                        .append(checkEmptyorNullString("Status", inputBean.getStatus()))
                        .append(" parameters ]");

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.TASK_MGT_PAGE, SectionVarList.USERMANAGEMENT, searchParameters.toString(), null);
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
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " task");
        }
        return result;
    }

    //find task for update reset
    public String find() {
        System.out.println("called TaskAction: find");
        String result = "find";
        Ipgtask mpiTask = null;
        try {
            if (inputBean.getTaskCode() != null && !inputBean.getTaskCode().isEmpty()) {

                TaskDAO dao = new TaskDAO();

                mpiTask = dao.findTaskById(inputBean.getTaskCode());

                inputBean.setTaskCode(mpiTask.getTaskcode());
                inputBean.setDescription(mpiTask.getDescription());
                inputBean.setSortKey(mpiTask.getSortkey().toString());
                inputBean.setStatus(mpiTask.getIpgstatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty task code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage(MessageVarList.COMMON_ERROR_PROCESS + " task");
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;

    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getTaskCode() == null || inputBean.getTaskCode().trim().isEmpty()) {
            message = MessageVarList.TASK_MGT_EMPTY_TASK_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.TASK_MGT_EMPTY_DESCRIPTION;
        } else if (inputBean.getSortKey() == null || inputBean.getSortKey().trim().isEmpty()) {
            message = MessageVarList.TASK_MGT_EMPTY_SORTKEY;
        } else if (inputBean.getStatus() != null && inputBean.getStatus().isEmpty()) {
            message = MessageVarList.TASK_MGT_EMPTY_STATUS;
        } else {
            if (!Validation.isSpecailCharacter(inputBean.getTaskCode())) {
                message = MessageVarList.TASK_MGT_ERROR_TASKCODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
                message = MessageVarList.TASK_MGT_ERROR_DESC_INVALID;
            } else {
                try {
                    new BigDecimal(inputBean.getSortKey());
                } catch (Exception e) {
                    message = MessageVarList.TASK_MGT_ERROR_SORTKEY_INVALID;
                }
            }
        }
        return message;
    }

}
