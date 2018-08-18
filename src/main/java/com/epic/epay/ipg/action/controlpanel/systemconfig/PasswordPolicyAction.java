/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.PasswordPolicyBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.PasswordPolicyInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.PasswordPolicyDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.mapping.Ipgpasswordpolicy;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 * @created :Oct 25, 2013, 5:26:26 PM
 * @author :thushanth
 */
public class PasswordPolicyAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    PasswordPolicyInputBean inputBean = new PasswordPolicyInputBean();

    public PasswordPolicyAction() {
    }

    @Override
    public String execute() throws Exception {
        System.out.println("called PasswordPolicyAction : execute");
        return SUCCESS;
    }

    public Object getModel() {
        return inputBean;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.PASSWORD_POLICY;
        String task = null;
        if ("View".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("List".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Add".equals(method)) {
            task = TaskVarList.ADD_TASK;
        } else if ("Delete".equals(method)) {
            task = TaskVarList.DELETE_TASK;
        } else if ("Find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Update".equals(method)) {
            task = TaskVarList.UPDATE_TASK;
        } else if ("Reset".equals(method)) {
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

    private boolean applyUserPrivileges() throws Exception {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.PASSWORD_POLICY, request);

            inputBean.setVadd(true);
            inputBean.setVupdatelink(true);
            inputBean.setVupdatebutt(true);
            PasswordPolicyDAO dao = new PasswordPolicyDAO();
            if (dao.isExistPasswordPolicy()) {
                if (tasklist != null && tasklist.size() > 0) {
                    for (Ipgtask task : tasklist) {
                        if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                            inputBean.setVadd(true);
                        } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                        } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                            inputBean.setVupdatelink(false);
                            inputBean.setVupdatebutt(false);
                        } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                        }
                    }
                    Ipgpasswordpolicy ipgpasswordpolicy = null;
                    ipgpasswordpolicy = dao.getPasswordPolicyDetails();
                    inputBean.setPolicyid(true);
                    inputBean.setPasswordpolicyid(ipgpasswordpolicy.getIpgpasswordpolicyid().toString());
                    inputBean.setMinimumlength(ipgpasswordpolicy.getMinimumlength().toString());
                    inputBean.setMaximumlength(ipgpasswordpolicy.getMaximumlength().toString());
                    inputBean.setAllowedspacialchars(ipgpasswordpolicy.getAllowedspecialcharacters().toString());
                    inputBean.setMinimumspacialchars(ipgpasswordpolicy.getMinimumspecialcharacters().toString());
                    inputBean.setMinimumuppercasechars(ipgpasswordpolicy.getMinimumuppercasecharacters().toString());
                    inputBean.setMinimumnumericalchars(ipgpasswordpolicy.getMinimumnumericalcharacters().toString());
                    inputBean.setMinimumlowercasechars(ipgpasswordpolicy.getMinimumlowercasecharacters().toString());
                    inputBean.setNoofPINcount(ipgpasswordpolicy.getNoofpincount().toString());
                    inputBean.setStatus(ipgpasswordpolicy.getIpgstatus().getStatuscode().toString());

                }
            } else {
                if (tasklist != null && tasklist.size() > 0) {
                    for (Ipgtask task : tasklist) {
                        if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                            inputBean.setVadd(false);
                        } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                        } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                            inputBean.setVupdatelink(true);
                            inputBean.setVupdatebutt(true);
                        } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                        }
                    }
                    inputBean.setPolicyid(false);

                }
            }
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public String View() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));


            } else {
                result = "loginpage";
            }

            System.out.println("called PasswordPolicyAction : view");


        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Password Policy View Action");
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String Find() {
        System.out.println("called PasswordPolicyAction: Find");
        Ipgpasswordpolicy ipgpasswordpolicy = new Ipgpasswordpolicy();
        try {
            if (inputBean.getPasswordpolicyid() != null && !inputBean.getPasswordpolicyid().isEmpty()) {

                PasswordPolicyDAO dao = new PasswordPolicyDAO();

                ipgpasswordpolicy = dao.findPasswordPolicyById(inputBean.getPasswordpolicyid());

                inputBean.setPasswordpolicyid(ipgpasswordpolicy.getIpgpasswordpolicyid().toString());
                inputBean.setMinimumlength(ipgpasswordpolicy.getMinimumlength().toString());
                inputBean.setMaximumlength(ipgpasswordpolicy.getMaximumlength().toString());
                inputBean.setAllowedspacialchars(ipgpasswordpolicy.getAllowedspecialcharacters().toString());
                inputBean.setMinimumspacialchars(ipgpasswordpolicy.getMinimumspecialcharacters().toString());
                inputBean.setMinimumuppercasechars(ipgpasswordpolicy.getMinimumuppercasecharacters().toString());
                inputBean.setMinimumnumericalchars(ipgpasswordpolicy.getMinimumnumericalcharacters().toString());
                inputBean.setMinimumlowercasechars(ipgpasswordpolicy.getMinimumlowercasecharacters().toString());
                inputBean.setNoofPINcount(ipgpasswordpolicy.getNoofpincount().toString());
                inputBean.setStatus(ipgpasswordpolicy.getIpgstatus().getStatuscode().toString());

            } else {
                inputBean.setMessage("Empty password policy id.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Password policy  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String Add() {
        System.out.println("called PasswordPolicyAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            PasswordPolicyDAO dao = new PasswordPolicyDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.PASSWORD_POLICY, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Password policy details added", null);
                message = dao.insertPasswordPolicy(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Password policy  " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Password policy  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String Update() {

        System.out.println("called PasswordPolicyAction : update");
        String retType = "message";

        try {
            if (inputBean.getPasswordpolicyid() != null && !inputBean.getPasswordpolicyid().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    PasswordPolicyDAO dao = new PasswordPolicyDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.PASSWORD_POLICY, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Password policy details updated", null);
                    message = dao.updatePasswordPolicy(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Password policy " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Password policy " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    private String validateInputs() {
        String message = "";

        if (inputBean.getPasswordpolicyid() == null || inputBean.getPasswordpolicyid().isEmpty()) {
            message = MessageVarList.PASSPOLICY_POLICYID_EMPTY;
        } else if (inputBean.getMinimumlength() == null | inputBean.getMinimumlength().isEmpty()) {
            message = MessageVarList.PASSPOLICY_MINLEN_EMPTY;
        } else if (inputBean.getMaximumlength() == null | inputBean.getMaximumlength().isEmpty()) {
            message = MessageVarList.PASSPOLICY_MAXLEN_EMPTY;
        } else if (Integer.parseInt(inputBean.getMinimumlength()) >= Integer.parseInt(inputBean.getMaximumlength())) {
            message = MessageVarList.PASSPOLICY_MIN_MAX_LENGHT_DIFF;
        } else if (inputBean.getAllowedspacialchars() == null | inputBean.getAllowedspacialchars().isEmpty()) {
            message = MessageVarList.PASSPOLICY_SPECCHARS_EMPTY;
        } else if (inputBean.getMinimumspacialchars() == null | inputBean.getMinimumspacialchars().isEmpty()) {
            message = MessageVarList.PASSPOLICY_MINSPECCHARS_EMPTY;
        } else if (Integer.parseInt(inputBean.getMinimumspacialchars()) > 6) {
            message = MessageVarList.PASSPOLICY_SPECCHARS_SHOULD_BE_LESS + "7";
        } else if (inputBean.getMinimumuppercasechars() == null | inputBean.getMinimumuppercasechars().isEmpty()) {
            message = MessageVarList.PASSPOLICY_MINUPPER_EMPTY;
        } else if (inputBean.getMinimumnumericalchars() == null | inputBean.getMinimumnumericalchars().isEmpty()) {
            message = MessageVarList.PASSPOLICY_MINNUM_EMPTY;
        } else if (inputBean.getMinimumlowercasechars() == null | inputBean.getMinimumlowercasechars().isEmpty()) {
            message = MessageVarList.PASSPOLICY_MINLOWER_EMPTY;
        } else if (inputBean.getNoofPINcount() == null | inputBean.getNoofPINcount().isEmpty()) {
            message = MessageVarList.PASSPOLICY_PIN_EMPTY;
        }
        else if (inputBean.getStatus() == null | inputBean.getStatus().isEmpty()) {
            message = MessageVarList.PASSPOLICY_STATUS_EMPTY;
        }
        if (message.equals("")) {
            Integer minLower = Integer.parseInt(inputBean.getMinimumlowercasechars());
            Integer minNumerical = Integer.parseInt(inputBean.getMinimumnumericalchars());
            Integer minSpecial = Integer.parseInt(inputBean.getMinimumspacialchars());
            Integer minUpper = Integer.parseInt(inputBean.getMinimumuppercasechars());
            
            Integer minLength = minLower + minNumerical + minSpecial + minUpper;
            Integer maxLength = minLength;
            try {
                if ( minSpecial < Integer.parseInt(inputBean.getAllowedspacialchars())) {
                    if (message.equals("")) {
                        message = MessageVarList.PASSPOLICY_SPECCHARS_INVALID;
                    }
                }
            } catch (Exception e) {
               if (message.equals("")) {
                    message = MessageVarList.PASSPOLICY_SPECCHARS_INVALID;
                }
            }
            try {
                if ( minSpecial >0 && Integer.parseInt(inputBean.getAllowedspacialchars())==0) {
                    if (message.equals("")) {
                        message = MessageVarList.PASSPOLICY_SPECCHARS_INVALID_DETAIL;
                    }
                }
            } catch (Exception e) {
               if (message.equals("")) {
                    message = MessageVarList.PASSPOLICY_SPECCHARS_INVALID_DETAIL;
                }
            }
            try {
                if (Integer.parseInt(inputBean.getMinimumlength()) < minLength) {
                    if (message.equals("")) {
                        message = MessageVarList.PASSPOLICY_MINLEN_INVALID_DETAIL + (minLength);
                    }
                }
            } catch (Exception e) {
               if (message.equals("")) {
                    message = MessageVarList.PASSPOLICY_MINLEN_INVALID_DETAIL + (minLength);
                }
            }
            try {
                if (Integer.parseInt(inputBean.getMaximumlength()) <=  maxLength) {
                    if (message.equals("")) {
                        message = MessageVarList.PASSPOLICY_MAXLEN_INVALID_DETAIT + (maxLength);
                    }
                }
            } catch (Exception e) {
                if (message.equals("")) {
                    message = MessageVarList.PASSPOLICY_MAXLEN_INVALID_DETAIT + (maxLength);
                }
            }
        }
        return message;
    }

    public String List() {
        System.out.println("called PasswordPolicyAction: List");
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
            PasswordPolicyDAO dao = new PasswordPolicyDAO();
            List<PasswordPolicyBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
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
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Password policy");
        }
        return "list";
    }

    public String Reset() {
        System.out.println("called PasswordPolicyAction: Reset");

        Ipgpasswordpolicy ipgpasswordpolicy = new Ipgpasswordpolicy();
        PasswordPolicyDAO dao = new PasswordPolicyDAO();
        try {
            if (dao.isExistPasswordPolicy()) {



                ipgpasswordpolicy = dao.getPasswordPolicyDetails();

                inputBean.setPasswordpolicyid(ipgpasswordpolicy.getIpgpasswordpolicyid().toString());
                inputBean.setMinimumlength(ipgpasswordpolicy.getMinimumlength().toString());
                inputBean.setMaximumlength(ipgpasswordpolicy.getMaximumlength().toString());
                inputBean.setAllowedspacialchars(ipgpasswordpolicy.getAllowedspecialcharacters().toString());
                inputBean.setMinimumspacialchars(ipgpasswordpolicy.getMinimumspecialcharacters().toString());
                inputBean.setMinimumuppercasechars(ipgpasswordpolicy.getMinimumuppercasecharacters().toString());
                inputBean.setMinimumnumericalchars(ipgpasswordpolicy.getMinimumnumericalcharacters().toString());
                inputBean.setMinimumlowercasechars(ipgpasswordpolicy.getMinimumlowercasecharacters().toString());
                inputBean.setNoofPINcount(ipgpasswordpolicy.getNoofpincount().toString());
                inputBean.setStatus(ipgpasswordpolicy.getIpgstatus().getStatuscode().toString());

            } else {
                inputBean.setMessage("Empty password policy id.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Password policy  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "reset";

    }
}
