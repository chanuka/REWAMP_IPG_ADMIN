/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.usermanagement;

import com.epic.epay.ipg.bean.controlpanel.usermanagement.SystemUserBean;
import com.epic.epay.ipg.bean.controlpanel.usermanagement.SystemUserInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.PasswordPolicyDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.dao.controlpanel.usermanagement.SystemUserDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgpasswordpolicy;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.OracleMessage;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.epic.epay.ipg.util.varlist.UserRoleTypeVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 * @created :Oct 25, 2013, 2:23:11 PM
 * @author :thushanth
 */
public class SystemUserAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    SystemUserInputBean inputBean = new SystemUserInputBean();

    public SystemUserAction() {
    }

    public String execute() throws Exception {
        System.out.println("called SystemUserMgtAction : execute");
        return SUCCESS;
    }

    public Object getModel() {
        return inputBean;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.SYS_USER_PAGE;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("list".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("viewpopup".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("add".equals(method)) {
            task = TaskVarList.ADD_TASK;
        } else if ("Search".equals(method)) {
            task = TaskVarList.SEARCH_TASK;
        } else if ("delete".equals(method)) {
            task = TaskVarList.DELETE_TASK;
        } else if ("find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("update".equals(method)) {
            task = TaskVarList.UPDATE_TASK;
        } else if ("check".equals(method)) {
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
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.SYS_USER_PAGE, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(false);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
                    inputBean.setVdelete(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                    inputBean.setVupdatelink(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                }
            }
        }
        inputBean.setVupdatebutt(true);

    }

    public String view() {

        String result = "view";

        try {

            this.applyUserPrivileges();

            CommonDAO dao = new CommonDAO();
            inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_SYSUSER));
            inputBean.setUserroleList(dao.getAllUserRoleList());
            inputBean.setMerchantList(dao.getMerchantList(CommonVarList.MERCHANT_STATUS_ACTIVE));

            System.out.println("called SystemUserMgtAction :view");

        } catch (Exception ex) {
            addActionError("System User Management " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String check() {
        System.out.println("called SystemUserMgtAction :check");
        String result = "check";
        try {
            if (!inputBean.getMainuserrole().isEmpty() && inputBean.getMainuserrole() != null) {
                HttpSession session = ServletActionContext.getRequest().getSession(false);
                SystemUserDAO dao = new SystemUserDAO();
                if ((dao.checkUserRole(inputBean.getMainuserrole())).equals(UserRoleTypeVarList.MERCHANT)) {
                    inputBean.setMessage("true");
                    session.setAttribute(SessionVarlist.ISMERCH, "true");
                } else {
                    inputBean.setMessage("false");
                    session.setAttribute(SessionVarlist.ISMERCH, "false");
                }

            }

        } catch (Exception ex) {
            addActionError("System User Management " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String list() {

        System.out.println("called SystemUserAction: list");

        try {
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            Ipgsystemuser sysUser = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;

            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }

            SystemUserDAO dao = new SystemUserDAO();

            List<SystemUserBean> dataList = dao.getAllSystemUsersList(inputBean, rows, from, orderBy, sysUser.getUsername());

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();
                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("[")
                        .append(checkEmptyorNullString("Username", inputBean.getUsername()))
                        .append(checkEmptyorNullString("User Role", inputBean.getUserrole()))
                        .append(checkEmptyorNullString("Status", inputBean.getStatus()))
                        .append("]");

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.SYS_USER_PAGE, SectionVarList.USERMANAGEMENT, "User management search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " System User Action");
        }
        return "list";
    }

    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called SystemUser : ViewPopup");

        try {

            this.applyUserPrivileges();

            CommonDAO dao = new CommonDAO();
            PasswordPolicyDAO passPolicydao = new PasswordPolicyDAO();
            Ipgpasswordpolicy passPolicy = passPolicydao.getPasswordPolicyDetails();
            inputBean.setPwtooltip(passPolicydao.generateToolTipMessage(passPolicy));
            inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_SYSUSER));
            inputBean.setUserroleList(dao.getAllUserRoleList());
            inputBean.setMerchantList(dao.getMerchantList(CommonVarList.MERCHANT_STATUS_ACTIVE));

        } catch (Exception e) {
            addActionError("System user " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String add() {
        System.out.println("called UserManagementAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            SystemUserDAO dao = new SystemUserDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.SYS_USER_PAGE, SectionVarList.USERMANAGEMENT, "System user " + inputBean.getUsername() + " added", null);
                message = dao.insertPage(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("System User " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("System User Management " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String update() {

        System.out.println("called UserManagementAction : update");
        String retType = "message";

        try {
            if (inputBean.getUsername() != null && !inputBean.getUsername().isEmpty()) {

                //set username get in hidden fileds
                inputBean.setUsername(inputBean.getUsername());

                String message = this.validateUpdateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    SystemUserDAO dao = new SystemUserDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.SYS_USER_PAGE, SectionVarList.USERMANAGEMENT, "System user " + inputBean.getUsername() + " updated", null);
                    message = dao.updatePage(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("System User " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("System User Management " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String detail() {

        System.out.println("called SystemUser: detail");
        Ipgsystemuser ipgsystemuser = null;

        try {
            if (inputBean.getUsername() != null && !inputBean.getUsername().isEmpty()) {

                SystemUserDAO dao = new SystemUserDAO();
                CommonDAO comdao = new CommonDAO();
                inputBean.setStatusList(comdao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_SYSUSER));
                inputBean.setUserroleList(comdao.getAllUserRoleList());
                inputBean.setMerchantList(comdao.getMerchantList(CommonVarList.MERCHANT_STATUS_ACTIVE));

                ipgsystemuser = dao.getSystemUserByUserName(inputBean.getUsername());

//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    inputBean.setUsername(ipgsystemuser.getUsername());
                } catch (NullPointerException e) {
                    inputBean.setUsername("");
                }
                try {
                    inputBean.setUserrole(ipgsystemuser.getIpguserroleByUserrolecode().getUserrolecode());
                } catch (Exception e) {
                    inputBean.setUserrole("");
                }
                try {
                    inputBean.setDualAuth(ipgsystemuser.getIpguserroleByDualauthuserrole().getUserrolecode());
                } catch (Exception e) {
                    inputBean.setDualAuth("");
                }
                try {
                    inputBean.setStatus(ipgsystemuser.getIpgstatus().getStatuscode());
                } catch (Exception e) {
                    inputBean.setStatus("");
                }
                try {
                    inputBean.setEmpid(ipgsystemuser.getEmpid());
                } catch (NullPointerException e) {
                    inputBean.setEmpid("");
                }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    inputBean.setExpirydate(sdf.format(ipgsystemuser.getExpirydate()));
                } catch (Exception e) {
                    inputBean.setExpirydate("");
                }
                try {
                    inputBean.setFullname(ipgsystemuser.getFullname());
                } catch (NullPointerException e) {
                    inputBean.setFullname("");
                }
                try {
                    inputBean.setAddress1(ipgsystemuser.getAddress1());
                } catch (NullPointerException e) {
                    inputBean.setAddress1("");
                }
                try {
                    inputBean.setAddress2(ipgsystemuser.getAddress2());
                } catch (NullPointerException e) {
                    inputBean.setAddress2("");
                }
                try {
                    inputBean.setCity(ipgsystemuser.getCity());
                } catch (NullPointerException e) {
                    inputBean.setCity("");
                }
                try {
                    inputBean.setMobile(ipgsystemuser.getMobile());
                } catch (NullPointerException e) {
                    inputBean.setMobile("");
                }
                try {
                    inputBean.setFax(ipgsystemuser.getFax());
                } catch (NullPointerException e) {
                    inputBean.setFax("");
                }
                try {
                    inputBean.setTelno(ipgsystemuser.getTelno());
                } catch (NullPointerException e) {
                    inputBean.setTelno("");
                }
                try {
                    inputBean.setMail(ipgsystemuser.getEmail());
                } catch (NullPointerException e) {
                    inputBean.setMail("");
                }

                HttpSession session = ServletActionContext.getRequest().getSession(false);
                if (ipgsystemuser.getMerchantid() != null && !ipgsystemuser.getMerchantid().isEmpty()) {
                    inputBean.setMername(ipgsystemuser.getMerchantid());
                    session.setAttribute(SessionVarlist.ISMERCH, "true");
                } else {
                    inputBean.setMername("");
                    session.setAttribute(SessionVarlist.ISMERCH, "false");
                }
            } else {
                inputBean.setMessage("Empty system user name.");
            }

        } catch (Exception ex) {
            inputBean.setMessage("System User Management " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";

    }

    public String find() {
        System.out.println("called UserManagementAction: Find");
        Ipgsystemuser ipgsystemuser = new Ipgsystemuser();
        try {
            if (inputBean.getUsername() != null && !inputBean.getUsername().isEmpty()) {

                SystemUserDAO dao = new SystemUserDAO();

                ipgsystemuser = dao.getSystemUserByUserName(inputBean.getUsername());

                try {
                    inputBean.setUsername(ipgsystemuser.getUsername());
                } catch (NullPointerException e) {
                    inputBean.setUsername("");
                }
                try {
                    inputBean.setUserrole(ipgsystemuser.getIpguserroleByUserrolecode().getUserrolecode());
                } catch (Exception e) {
                    inputBean.setUserrole("");
                }
                try {
                    inputBean.setDualAuth(ipgsystemuser.getIpguserroleByDualauthuserrole().getUserrolecode());
                } catch (Exception e) {
                    inputBean.setDualAuth("");
                }
                try {
                    inputBean.setStatus(ipgsystemuser.getIpgstatus().getStatuscode());
                } catch (Exception e) {
                    inputBean.setStatus("");
                }
                try {
                    inputBean.setEmpid(ipgsystemuser.getEmpid());
                } catch (NullPointerException e) {
                    inputBean.setEmpid("");
                }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    inputBean.setExpirydate(sdf.format(ipgsystemuser.getExpirydate()));
                } catch (Exception e) {
                    inputBean.setExpirydate("");
                }
                try {
                    inputBean.setFullname(ipgsystemuser.getFullname());
                } catch (NullPointerException e) {
                    inputBean.setFullname("");
                }
                try {
                    inputBean.setAddress1(ipgsystemuser.getAddress1());
                } catch (NullPointerException e) {
                    inputBean.setAddress1("");
                }
                try {
                    inputBean.setAddress2(ipgsystemuser.getAddress2());
                } catch (NullPointerException e) {
                    inputBean.setAddress2("");
                }
                try {
                    inputBean.setCity(ipgsystemuser.getCity());
                } catch (NullPointerException e) {
                    inputBean.setCity("");
                }
                try {
                    inputBean.setMobile(ipgsystemuser.getMobile());
                } catch (NullPointerException e) {
                    inputBean.setMobile("");
                }
                try {
                    inputBean.setFax(ipgsystemuser.getFax());
                } catch (NullPointerException e) {
                    inputBean.setFax("");
                }
                try {
                    inputBean.setTelno(ipgsystemuser.getTelno());
                } catch (NullPointerException e) {
                    inputBean.setTelno("");
                }
                try {
                    inputBean.setMail(ipgsystemuser.getEmail());
                } catch (NullPointerException e) {
                    inputBean.setMail("");
                }

                HttpSession session = ServletActionContext.getRequest().getSession(false);
                if (ipgsystemuser.getMerchantid() != null && !ipgsystemuser.getMerchantid().isEmpty()) {
                    inputBean.setMername(ipgsystemuser.getMerchantid());
                    session.setAttribute(SessionVarlist.ISMERCH, "true");
                } else {
                    inputBean.setMername("");
                    session.setAttribute(SessionVarlist.ISMERCH, "false");
                }

            } else {
                inputBean.setMessage("Empty system user id.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("System User Management " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String delete() {
        System.out.println("called UserManagementAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            SystemUserDAO dao = new SystemUserDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.SYS_USER_PAGE, SectionVarList.USERMANAGEMENT, "System user " + inputBean.getUsername() + " deleted", null);
            message = dao.deleteUser(inputBean, audit);
            if (message.isEmpty()) {
                message = "System User " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, e);
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    private String checkPolicy(String password) throws Exception {
        String errorMsg = "";
        try {
            PasswordPolicyDAO dao = new PasswordPolicyDAO();

            Ipgpasswordpolicy ipgpasswordpolicy = dao.getPasswordPolicyDetails();
            if (ipgpasswordpolicy != null) {

                String msg = this.CheckPasswordValidity(password, ipgpasswordpolicy);

                if (!msg.equals("")) {
                    errorMsg = msg;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return errorMsg;
    }

    public String CheckPasswordValidity(String password, Ipgpasswordpolicy bean) throws Exception {
        String msg = "";
        boolean flag = true;
        try {

            if (password.length() < bean.getMinimumlength().intValue()) {
                flag = false;
                msg = MessageVarList.SYSUSER_PASSWORD_TOO_SHORT;
            } else if (password.length() > bean.getMaximumlength().intValue()) {
                flag = false;
                msg = MessageVarList.SYSUSER_PASSWORD_TOO_LARGE;
            }

            if (flag) {
                int digits = 0;
                int upper = 0;
                int lower = 0;
                int special = 0;
                String specialCharacters = "";

                for (int i = 0; i < password.length(); i++) {
                    char c = password.charAt(i);

                    if (Character.isDigit(c)) {
                        digits++;
                    } else if (Character.isUpperCase(c)) {
                        upper++;
                    } else if (Character.isLowerCase(c)) {
                        lower++;
                    } else {
                        special++;
                        int check = specialCharacters.indexOf(Character.toString(c));
                        if (check < 0) {
                            specialCharacters = specialCharacters + Character.toString(c);
                        }

                    }
                }

                if (lower < bean.getMinimumlowercasecharacters().intValue()) {
                    msg = MessageVarList.SYSUSER_PASSWORD_LESS_LOWER_CASE_CHARACTERS;
                    flag = false;
                } else if (upper < bean.getMinimumuppercasecharacters().intValue()) {
                    msg = MessageVarList.SYSUSER_PASSWORD_LESS_UPPER_CASE_CHARACTERS;
                    flag = false;
                } else if (digits < bean.getMinimumnumericalcharacters().intValue()) {
                    msg = MessageVarList.SYSUSER_PASSWORD_LESS_NUMERICAL_CHARACTERS;
                    flag = false;
                } else if (special < bean.getMinimumspecialcharacters().intValue()) {
                    msg = MessageVarList.SYSUSER_PASSWORD_LESS_SPACIAL_CHARACTERS;;
                    flag = false;
                } else if (specialCharacters.length() > Integer.valueOf(bean.getAllowedspecialcharacters().toString())) {
                    msg = "Invalid Password. There are more special characters than required";
                    flag = false;
                }

            }

        } catch (Exception e) {
            throw e;
        }
        return msg;
    }

    private String validateInputs() throws Exception {

        String message = "";
        try {
            if (inputBean.getUsername() == null || inputBean.getUsername().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_USERNAME;
            } else if (inputBean.getPassword() == null || inputBean.getPassword().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_PASSWORD;
            } else if (!inputBean.getPassword().equals(inputBean.getConfirmpassword())) {
                message = MessageVarList.SYSUSER_MGT_PASSWORD_MISSMATCH;
            } else if (inputBean.getUserrole() == null || inputBean.getUserrole().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_USERROLE;
            } //            else if (inputBean.getDualAuth() == null || inputBean.getDualAuth().trim().isEmpty()) {
            //                message = MessageVarList.SYSUSER_MGT_EMPTY_DUAL_USERROLE;      
            //            }
            else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_STATUS;
            } else if (inputBean.getEmpid() == null || inputBean.getEmpid().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_EMPID;
            } else if (inputBean.getExpirydate() == null || inputBean.getExpirydate().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_EXPIRYDATE;
            } else if (inputBean.getFullname() == null || inputBean.getFullname().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_FULLNAME;
            } else if (inputBean.getAddress1() == null || inputBean.getAddress1().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_ADDRESS1;
            } else if (inputBean.getAddress2() == null || inputBean.getAddress2().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_ADDRESS2;
            } else if (inputBean.getCity() == null || inputBean.getCity().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_CITY;
            } else if (inputBean.getMobile() == null || inputBean.getMobile().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_MOBILE;
            } else if (inputBean.getTelno() == null || inputBean.getTelno().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_TELNO;
            } else if (inputBean.getFax() == null || inputBean.getFax().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_FAX;
            } else if (inputBean.getMail() == null || inputBean.getMail().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_EMAIL;
            } else if (!inputBean.getMail().isEmpty() && !Validation.isValidEmail(inputBean.getMail())) {
                message = MessageVarList.SYSUSER_MGT_INVALID_EMAIL;
            }

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if ((((String) session.getAttribute(SessionVarlist.ISMERCH))).equals("true") && (inputBean.getMername() == null || inputBean.getMername().trim().isEmpty())) {
                message = MessageVarList.SYSUSER_EMPTY_MERCHANT_NAME;
            }

            if (inputBean.getPassword().equals(inputBean.getConfirmpassword())) {
                String msg = "";
                msg = this.checkPolicy(inputBean.getPassword());
                if (message.equals("")) {
                    message = msg;
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    private String validateUpdateInputs() throws Exception {
        String message = "";
        try {
            if (inputBean.getUsername() == null || inputBean.getUsername().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_USERNAME;
            } else if (inputBean.getUserrole() == null || inputBean.getUserrole().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_USERROLE;
            } //            else if (inputBean.getDualAuth() == null || inputBean.getDualAuth().trim().isEmpty()) {
            //                message = MessageVarList.SYSUSER_MGT_EMPTY_DUAL_USERROLE;    
            //            } 
            else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_STATUS;
            } else if (inputBean.getEmpid() == null || inputBean.getEmpid().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_EMPID;
            } else if (inputBean.getExpirydate() == null || inputBean.getExpirydate().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_EXPIRYDATE;
            } else if (inputBean.getFullname() == null || inputBean.getFullname().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_FULLNAME;
            } else if (inputBean.getAddress1() == null || inputBean.getAddress1().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_ADDRESS1;
            } else if (inputBean.getAddress2() == null || inputBean.getAddress2().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_ADDRESS2;
            } else if (inputBean.getCity() == null || inputBean.getCity().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_CITY;
            } else if (inputBean.getMobile() == null || inputBean.getMobile().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_MOBILE;
            } else if (inputBean.getTelno() == null || inputBean.getTelno().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_TELNO;
            } else if (inputBean.getFax() == null || inputBean.getFax().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_FAX;
            } else if (inputBean.getMail() == null || inputBean.getMail().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_EMAIL;
            } else if (!inputBean.getMail().isEmpty() && !Validation.isValidEmail(inputBean.getMail())) {
                message = MessageVarList.SYSUSER_MGT_INVALID_EMAIL;
            }

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if ((((String) session.getAttribute(SessionVarlist.ISMERCH))).equals("true") && (inputBean.getMername() == null || inputBean.getMername().trim().isEmpty())) {
                message = MessageVarList.SYSUSER_EMPTY_MERCHANT_NAME;
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

}
