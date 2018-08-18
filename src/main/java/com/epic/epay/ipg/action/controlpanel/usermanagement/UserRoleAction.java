/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.usermanagement;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.epic.epay.ipg.bean.controlpanel.usermanagement.UserRoleInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.dao.controlpanel.usermanagement.UserRoleDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.mapping.Ipguserrole;
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
 * 28/10/2013
 */
public class UserRoleAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    UserRoleInputBean inputBean = new UserRoleInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called UserRoleAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.USER_ROLE_PAGE;
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
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.USER_ROLE_PAGE, request);

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

        System.out.println("called UserRoleAction :view");
        String result = "view";

        try {

            this.applyUserPrivileges();
            CommonDAO dao = new CommonDAO();
            inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
            inputBean.setUserRoleTypeList(dao.getUserRoleTypeList());

        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the user role");
            Logger.getLogger(UserRoleAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    //load pop up for user role insertion
    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called UserRoleAction : viewpopup");

        try {

            this.applyUserPrivileges();
            CommonDAO dao = new CommonDAO();
            inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
            inputBean.setUserRoleTypeList(dao.getUserRoleTypeList());

        } catch (Exception e) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the user role");
            Logger.getLogger(UserRoleAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return result;
    }

    public String add() {

        System.out.println("called UserRoleAction : add");
        String result = "message";

        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            UserRoleDAO dao = new UserRoleDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.USER_ROLE_PAGE, SectionVarList.USERMANAGEMENT, "User role code " + inputBean.getUserRoleCode() + " added", null);
                message = dao.insertUserRole(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("User role " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the user role");
            Logger.getLogger(UserRoleAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //load pop up for user role update
    public String detail() {
        System.out.println("called UserRoleAction: detail");
        String result = "detail";
        Ipguserrole userrole;

        try {
            if (inputBean.getUserRoleCode() != null && !inputBean.getUserRoleCode().isEmpty()) {

                CommonDAO comdao = new CommonDAO();
                UserRoleDAO dao = new UserRoleDAO();

                inputBean.setStatusList(comdao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setUserRoleTypeList(comdao.getUserRoleTypeList());

                userrole = dao.findUserRoleById(inputBean.getUserRoleCode());

                inputBean.setUserRoleCode(userrole.getUserrolecode());
                inputBean.setDescription(userrole.getDescription());
                inputBean.setSortKey(userrole.getSortkey().toString());
                inputBean.setStatus(userrole.getIpgstatus().getStatuscode());
                inputBean.setUserRoleType(userrole.getIpguserroletype().getUserroletypecode());

            } else {
                inputBean.setMessage("Empty user role code.");
            }

        } catch (Exception e) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the user role");
            Logger.getLogger(UserRoleAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return result;

    }

    public String update() {

        System.out.println("called UserRoleAction : update");
        String result = "message";

        try {
            if (inputBean.getUserRoleCode() != null && !inputBean.getUserRoleCode().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    UserRoleDAO dao = new UserRoleDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.USER_ROLE_PAGE, SectionVarList.USERMANAGEMENT, "User role code " + inputBean.getUserRoleCode() + " updated", null);
                    message = dao.updateUserRole(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("User role " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }
                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(UserRoleAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("UserRole " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return result;
    }

    public String delete() {

        System.out.println("called UserRoleAction : delete");
        String message = null;
        String result = "delete";

        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            UserRoleDAO dao = new UserRoleDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.USER_ROLE_PAGE, SectionVarList.USERMANAGEMENT, "User role code " + inputBean.getUserRoleCode() + " deleted", null);
            message = dao.deleteUserRole(inputBean, audit);
            if (message.isEmpty()) {
                message = "User role " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);

        } catch (Exception e) {

            Logger.getLogger(UserRoleAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));

        }
        return result;
    }

    public String list() {

        System.out.println("called UserRoleAction: list");
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

            UserRoleDAO userRoleDao = new UserRoleDAO();
            List<UserRoleInputBean> dataList = userRoleDao.getSearchList(inputBean, rows, from, orderBy);
            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("User role search using [ ")
                        .append(checkEmptyorNullString("User Role Code", inputBean.getUserRoleCode()))
                        .append(checkEmptyorNullString("Description ", inputBean.getDescription()))
                        .append(checkEmptyorNullString("Sort Key", inputBean.getSortKey()))
                        .append(checkEmptyorNullString("User Role Type", inputBean.getUserRoleType()))
                        .append(checkEmptyorNullString("Status", inputBean.getStatus()))
                        .append(" ] parameters");

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.USER_ROLE_PAGE, SectionVarList.USERMANAGEMENT, searchParameters.toString(), null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);
            }

            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                inputBean.setRecords(dataList.get(0).getFullCount());
                inputBean.setGridModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);
            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }

        } catch (Exception e) {
            Logger.getLogger(UserRoleAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " the user role");
        }
        return result;
    }

    //find user role for update reset
    public String find() {

        System.out.println("called UserRoleAction: find");
        Ipguserrole userrole = null;
        String result = "find";

        try {

            if (inputBean.getUserRoleCode() != null && !inputBean.getUserRoleCode().isEmpty()) {

                UserRoleDAO dao = new UserRoleDAO();

                userrole = dao.findUserRoleById(inputBean.getUserRoleCode());

                inputBean.setUserRoleCode(userrole.getUserrolecode());
                inputBean.setDescription(userrole.getDescription());
                inputBean.setUserRoleType(userrole.getIpguserroletype().getUserroletypecode());
                inputBean.setSortKey(userrole.getSortkey().toString());
                inputBean.setStatus(userrole.getIpgstatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty user role code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage(MessageVarList.COMMON_ERROR_PROCESS + " the user role");
            Logger.getLogger(UserRoleAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;

    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getUserRoleCode() == null || inputBean.getUserRoleCode().trim().isEmpty()) {
            message = MessageVarList.USER_ROLE_MGT_EMPTY_CODE;
        } else if (!Validation.isSpecailCharacter(inputBean.getUserRoleCode())) {
            message = MessageVarList.INVALID_USERROLE_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.USER_ROLE_MGT_EMPTY_DESCRIPTION;
        } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
            message = MessageVarList.INVALID_USERROLE_DESCRIPTION;
        } else if (inputBean.getUserRoleType() == null || inputBean.getUserRoleType().trim().isEmpty()) {
            message = MessageVarList.USER_ROLE_TYPE_EMPTY;
        } else if (inputBean.getStatus() != null && inputBean.getStatus().isEmpty()) {
            message = MessageVarList.EMPTY_USER_ROLE_STATUS;
        } else if (inputBean.getSortKey() == null || inputBean.getSortKey().trim().isEmpty()) {
            message = MessageVarList.EMPTY_USER_ROLE_SORTKEY;
        } else if (!Validation.isNumeric(inputBean.getSortKey())) {
            message = MessageVarList.INVALID_USER_ROLE_SORTKEY;
        }
        return message;
    }

}
