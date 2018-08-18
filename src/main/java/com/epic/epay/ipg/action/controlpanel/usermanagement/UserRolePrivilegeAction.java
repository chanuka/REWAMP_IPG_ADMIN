/*
 *
 */
package com.epic.epay.ipg.action.controlpanel.usermanagement;

import com.epic.epay.ipg.bean.controlpanel.usermanagement.UserRolePrivilegeInputBean;
import com.epic.epay.ipg.bean.controlpanel.usermanagement.UserRoleSectionBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.usermanagement.UserRolePrivilegeDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.mapping.Ipgsection;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author sivaganesan_t
 */
public class UserRolePrivilegeAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    UserRolePrivilegeInputBean inputBean = new UserRolePrivilegeInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called UserRolePrivilageAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.USER_ROLE_PRIVILAGE;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("LoadSection".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("LoadPage".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("FindPage".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("FindTask".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Manage".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Assign".equals(method)) {
            task = TaskVarList.ASSIGN_TASK;
        } else if ("Find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("LogOutUser".equals(method)) {
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
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.USER_ROLE_PRIVILAGE, request);

        inputBean.setVassign(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ASSIGN_TASK)) {
                    inputBean.setVassign(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                }
            }
        }
        inputBean.setVmanage(true);

    }

    public String view() {

        System.out.println("called UserRolePrivilegeAction :View");
        String result = "view";

        try {

            this.applyUserPrivileges();
            CommonDAO dao = new CommonDAO();
            inputBean.setUserRoleList(dao.getUserRoleList());

        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String Manage() {
        System.out.println("called UserRolePrivilegeAction : manage");

        try {
            this.applyUserPrivileges();

            String message = this.validateInputs();

            if (message.isEmpty()) {
                return "assignrole";

            } else {
                CommonDAO dao = new CommonDAO();
                inputBean.setUserRoleList(dao.getUserRoleList());
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "cerror";

    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getUserRole() == null || inputBean.getUserRole().trim().isEmpty()) {
            message = MessageVarList.USER_ROLE_PRI_EMPTY_USER_ROLE;
        } else if (inputBean.getCategory() == null || inputBean.getCategory().trim().isEmpty()) {
            message = MessageVarList.USER_ROLE_PRI_EMPTY_CATAGARY;
        } else if (!((inputBean.getCategory().equals("Sections")) || (inputBean.getCategory().equals("Pages")) || (inputBean.getCategory().equals("Operations")))) {
            message = MessageVarList.USER_ROLE_PRI_INVALID_CATAGARY;
        }
        return message;
    }

    public String Find() {
        System.out.println("called UserRolePrivilegeAction: Find");
        try {
            if (inputBean.getUserRole() != null
                    && !inputBean.getUserRole().isEmpty()) {
                UserRolePrivilegeDAO dao = new UserRolePrivilegeDAO();
                dao.findSectionsByUser(inputBean);

                inputBean.setOldvalue(inputBean.getUserRole() + "|" + inputBean.getNewList());

            } else {
                inputBean.setMessage("Empty user role");

            }
        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return "find";
    }

    public String LoadSection() {

        System.out.println("called UserRolePrivilegeAction : LoadSection");
        List<Ipgsection> sectionList = new ArrayList<Ipgsection>();
        try {
            if (inputBean.getUserRole() != null && !inputBean.getUserRole().isEmpty()) {

                UserRolePrivilegeDAO dao = new UserRolePrivilegeDAO();

                sectionList = dao.getSectionListByUserRole(inputBean.getUserRole());

                for (Iterator<Ipgsection> it = sectionList.iterator(); it.hasNext();) {

                    Ipgsection sec = it.next();
                    UserRoleSectionBean userRoleSectionBean = new UserRoleSectionBean();
                    userRoleSectionBean.setKey(sec.getSectioncode());
                    userRoleSectionBean.setValue(sec.getDescription());
                    inputBean.getSectionMap().add(userRoleSectionBean);
                }

            } else {
                inputBean.setMessage("Empty userrole code.");
            }
        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(Level.SEVERE, null, ex);

        }
        return "loadsections";
    }

    public String LoadPage() {
        System.out.println("called UserRolePrivilegeAction: LoadPage");
        try {

            if (inputBean.getUserRole() != null
                    && !inputBean.getUserRole().isEmpty()
                    && inputBean.getSectionpage() != null
                    && !inputBean.getSectionpage().isEmpty()) {

                UserRolePrivilegeDAO dao = new UserRolePrivilegeDAO();

                dao.findpageByUserRoleSection(inputBean);

            } else {
                inputBean.setMessage("Empty user role or empty section");
            }

        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return "loadpages";

    }

    public String FindPage() {

        System.out.println("called UserRolePrivilegeAction : FindPage");
        try {

            if (inputBean.getSection() != null && !inputBean.getSection().isEmpty() && inputBean.getUserRole() != null && !inputBean.getUserRole().isEmpty()) {

                UserRolePrivilegeDAO dao = new UserRolePrivilegeDAO();
                dao.getPageListBySection(inputBean);

                /**
                 * for audit
                 */
                StringBuilder stringBuilderOld = new StringBuilder();
                stringBuilderOld.append(inputBean.getUserRole())
                        .append("|").append(inputBean.getSection())
                        .append("|").append(inputBean.getNewList());

                inputBean.setOldvalue(stringBuilderOld.toString());

            } else {
                inputBean.setMessage("Empty section code or empty user role");
            }
        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(Level.SEVERE, null, ex);

        }

        return "findpage";
    }

    public String FindTask() {
        System.out.println("called UserRolePrivilegeAction: FindTask");

        try {

            if (inputBean.getUserRole() != null
                    && !inputBean.getUserRole().isEmpty()
                    && inputBean.getSectionpage() != null
                    && !inputBean.getSectionpage().isEmpty()
                    && inputBean.getPage() != null
                    && !inputBean.getPage().isEmpty()) {

                UserRolePrivilegeDAO dao = new UserRolePrivilegeDAO();

                dao.findTask(inputBean);

                /**
                 * for audit
                 */
                StringBuilder stringBuilderOld = new StringBuilder();

                stringBuilderOld.append(inputBean.getUserRole())
                        .append("|").append(inputBean.getSection())
                        .append("|").append(inputBean.getNewList());

                inputBean.setOldvalue(stringBuilderOld.toString());

            } else {
                inputBean.setMessage("Empty user role or empty section or empty page");
            }

        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return "findtask";

    }

    public String Assign() {
        System.out.println("called UserRolePrivilegeAction : Assign");
        String result = "message";
        String message = "";
        try {
            if (inputBean.getUserRole() != null && !inputBean.getUserRole().isEmpty()) {

                HttpServletRequest request = ServletActionContext.getRequest();

                UserRolePrivilegeDAO userRuleDao = new UserRolePrivilegeDAO();

                StringBuilder stringBuilderNew = new StringBuilder();

                if (inputBean.getCategory().equals("Sections")) {

                    stringBuilderNew.append(inputBean.getUserRole())
                            .append("|").append(inputBean.getNewBox());

                    Ipgsystemaudit audit = Common.makeAudittrace(request,
                            TaskVarList.ASSIGN_TASK, PageVarList.USER_ROLE_PRIVILAGE,
                            SectionVarList.USERMANAGEMENT, inputBean.getNewBox() + " sections assigned to user role code " + inputBean.getUserRole(), null);

                    if (userRuleDao.assignSection(inputBean, audit)) {
                        addActionMessage("User role privilege " + MessageVarList.COMMON_SUCC_ASSIGN);
                    } else {
                        addActionError(MessageVarList.COMMON_ERROR_PROCESS + " User role privilege");
                    }

                } else if (inputBean.getCategory().equals("Pages")) {
                    if (!(inputBean.getSection() == null) && !(inputBean.getSection().trim().isEmpty())) {

                        stringBuilderNew.append(inputBean.getUserRole())
                                .append("|").append(inputBean.getSection())
                                .append("|").append(inputBean.getNewBox());

                        Ipgsystemaudit audit = Common.makeAudittrace(request,
                                TaskVarList.ASSIGN_TASK, PageVarList.USER_ROLE_PRIVILAGE,
                                SectionVarList.USERMANAGEMENT, inputBean.getNewBox() + " pages assigned to user role code " + inputBean.getUserRole() + " in section " + inputBean.getSection(), null);

                        if (userRuleDao.assignSectionPages(inputBean, audit)) {
                            addActionMessage("User role privilege " + MessageVarList.COMMON_SUCC_ASSIGN);
                        } else {
                            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " User role privilege");
                        }

                    } else {
                        addActionError(MessageVarList.USER_ROLE_PRI_EMPTY_SECTION);
                        return result;
                    }
                } else if (inputBean.getCategory().equals("Operations")) {
                    if (!(inputBean.getSectionpage() == null) && !(inputBean.getSectionpage().trim().isEmpty())) {
                        if (!(inputBean.getPage() == null) && !(inputBean.getPage().trim().isEmpty())) {

                            stringBuilderNew.append(inputBean.getUserRole())
                                    .append("|").append(inputBean.getSectionpage())
                                    .append("|").append(inputBean.getPage())
                                    .append("|").append(inputBean.getNewBox());

                            Ipgsystemaudit audit = Common.makeAudittrace(request,
                                    TaskVarList.ASSIGN_TASK, PageVarList.USER_ROLE_PRIVILAGE,
                                    SectionVarList.USERMANAGEMENT, inputBean.getNewBox() + " tasks assigned to page code " + inputBean.getPage(), null);

                            if (userRuleDao.assignTask(inputBean, audit)) {
                                addActionMessage("User role privilege " + MessageVarList.COMMON_SUCC_ASSIGN);
                            } else {
                                addActionError(MessageVarList.COMMON_ERROR_PROCESS + " User role privilege");
                            }

                        } else {
                            addActionError(MessageVarList.USER_ROLE_PRI_EMPTY_PAGE);
                            return result;
                        }
                    } else {
                        addActionError(MessageVarList.USER_ROLE_PRI_EMPTY_SECTION);
                        return result;
                    }
                } else {
                    addActionMessage("No catagory has been selected");
                }

            } else {
                addActionError("User role cannot be empty");
            }
        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return result;
    }

    public String LogOutUser() {
        System.out.println("called UserRolePrivilegeAction : LogOutUser");
        return "logoutuser";
    }
}
