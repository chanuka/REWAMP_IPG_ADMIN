/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.controlpanel.usermanagement;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.epic.epay.ipg.bean.controlpanel.usermanagement.PasswordResetInputBean;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.PasswordPolicyDAO;
import com.epic.epay.ipg.dao.controlpanel.usermanagement.PasswordResetDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.mapping.Ipgpasswordpolicy;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 *
 * @author chanuka
 */
public class PasswordResetAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    private PasswordResetInputBean inputBean = new PasswordResetInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called passwordresetAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {

        try {

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            session.setAttribute(SessionVarlist.CURRENTPAGE, "Change Password");

            session.setAttribute(SessionVarlist.CURRENTSECTION, "Home");

            return true;
        } catch (Exception ex) {
            Logger.getLogger(PasswordResetAction.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    //view server operating houts
    public String View() {
        System.out.println("called PasswordResetAction :View");
        String retType = "view";
        try {
            if (this.applyUserPrivileges()) {

                HttpServletRequest request = ServletActionContext.getRequest();
                HttpSession session = request.getSession(false);

                Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
                PasswordPolicyDAO passPolicydao = new PasswordPolicyDAO();
                Ipgpasswordpolicy passPolicy = passPolicydao.getPasswordPolicyDetails();
                inputBean.setPwtooltip(passPolicydao.generateToolTipMessage(passPolicy));
                inputBean.setUsername(user.getUsername());
                inputBean.setHusername(user.getUsername());
                inputBean.setUserrole(user.getIpguserroleByUserrolecode().getDescription());

            } else {
                retType = "loginpage";
            }
        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Passsword Reset");
            Logger.getLogger(PasswordResetAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retType;
    }

    //update password
    public String Update() {
        System.out.println("called PasswordResetAction :Update");
        String retType = "message";
        PasswordResetDAO dao = new PasswordResetDAO();
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession(false);
            String message = this.validateInputs();

            if (message.isEmpty()) {

            	Ipgsystemuser users = dao.findUserById(inputBean.getHusername());

                if (users.getPassword().trim().equals(Common.makeHash(inputBean.getCurrpwd().trim()))) {
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.LOGIN_PAGE, SectionVarList.DEFAULT_SECTION, "Password changed ", null);
                    inputBean.setRenewpwd(Common.makeHash(inputBean.getRenewpwd().trim()));
                    message = dao.UpdatePasswordReset(inputBean, audit);

                    if (message.isEmpty()) {
                        retType = "resetpassword";
                        session.setAttribute("intercept", "PASSWORD_CHANGE_SUCCESS");
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(MessageVarList.PASSWORDRESET_INVALID_CURR_PWD);
                }
            } else {
                addActionError(message);
            }
        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_UPDATE + " Password Reset");
            Logger.getLogger(PasswordResetAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retType;
    }

    //validation of the input fields
    private String validateInputs() throws Exception {
        String message = "";
        try {
            if (inputBean.getCurrpwd() == null || inputBean.getCurrpwd().trim().isEmpty()) {
                message = MessageVarList.PASSWORDRESET_EMPTY_PASSWORD;
            } else if (inputBean.getNewpwd() == null || inputBean.getNewpwd().trim().isEmpty()) {
                message = MessageVarList.PASSWORDRESET_EMPTY_COM_PASSWORD;
            } else if (inputBean.getRenewpwd() == null || inputBean.getRenewpwd().trim().isEmpty()) {
                message = MessageVarList.PASSWORDRESET_EMPTY_COM_PASSWORD;
            } else if (!inputBean.getNewpwd().trim().contentEquals(inputBean.getRenewpwd().trim())) {
                message = MessageVarList.PASSWORDRESET_MATCH_PASSWORD;
            }
            if (inputBean.getNewpwd().equals(inputBean.getRenewpwd())) {
                String msg = "";
                msg = this.checkPolicy(inputBean.getNewpwd());
                if (message.equals("")) {
                    message = msg;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    private boolean applyUserPrivileges() {
        inputBean.setVupdatepwd(false);
        return true;
    }

    private String checkPolicy(String password) throws Exception {
        String errorMsg = "";
        try {
            PasswordPolicyDAO dao = new PasswordPolicyDAO();

            Ipgpasswordpolicy mpipasswordpolicy = dao.getPasswordPolicyDetails();
            if (mpipasswordpolicy != null) {

                String msg = this.CheckPasswordValidity(password, mpipasswordpolicy);

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
                String specialCharacters ="";

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
                        int check =specialCharacters.indexOf(Character.toString(c));
                        if(check<0){
                           specialCharacters= specialCharacters + Character.toString(c) ;
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
                }else if (specialCharacters.length() > Integer.valueOf(bean.getAllowedspecialcharacters().toString()))
                {
                    msg   =   "Invalid Password. There are more special characters than required";
                    flag  =   false;
                }


            }


        } catch (Exception e) {
            throw e;
        }
        return msg;
    }
}
