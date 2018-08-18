/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.login;

import com.epic.epay.ipg.bean.login.LoginBean;
import com.epic.epay.ipg.dao.login.LoginDAO;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.mapping.Ipgpage;
import com.epic.epay.ipg.util.mapping.Ipgpasswordpolicy;
import com.epic.epay.ipg.util.mapping.Ipgsection;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.LogoutMsgVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author chanuka
 */
public class LoginAction extends ActionSupport implements ModelDriven<Object> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    LoginBean loginBean = new LoginBean();
    Ipgsystemuser sysUser = new Ipgsystemuser();
    Ipgpasswordpolicy passwordpolicy = null;

    public String execute() {
        System.out.println("called LoginAction : execute");
        return SUCCESS;
    }

    public Object getModel() {
        return loginBean;
    }

    public String Check() {
        String result = "message";
        try {

            String message = this.validateUser();

            if (message.isEmpty()) {
                //set user to the session
                HttpSession session = ServletActionContext.getRequest().getSession(true);
                session.setAttribute(SessionVarlist.SYSTEMUSER, sysUser);



                //set user and sessionid to hashmap
                HashMap<String, String> userMap = null;

                ServletContext sc = ServletActionContext.getServletContext();
                userMap = (HashMap<String, String>) sc.getAttribute(SessionVarlist.USERMAP);
                if (userMap == null) {
                    userMap = new HashMap<String, String>();
                }
                userMap.put(sysUser.getUsername(), session.getId());
                sc.setAttribute(SessionVarlist.USERMAP, userMap);




                LoginDAO loginDao = new LoginDAO();

                HashMap<String, List<Ipgtask>> pageMap = loginDao.getPageTask(sysUser.getIpguserroleByUserrolecode().getUserrolecode());
                session.setAttribute(SessionVarlist.TASKMAP, pageMap); // to be used in the user privilages test


                HashMap<Ipgsection, List<Ipgpage>> sectionPagesMap = loginDao.getSectionPages(sysUser.getIpguserroleByUserrolecode().getUserrolecode());

                session.setAttribute(SessionVarlist.SECTIONPAGELIST, sectionPagesMap);

                result = SUCCESS;

            } else {
                loginBean.setErrormessage(message);
                addActionError(message);
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private String validateUser() {
        String message = "";
        try {
            LoginDAO loginDao = new LoginDAO();
            HttpServletRequest request = ServletActionContext.getRequest();

            if (loginBean.getUsername() == null || loginBean.getUsername().isEmpty()) {
                message = MessageVarList.LOGIN_EMPTY_USERNAME;
            } else if (loginBean.getPassword() == null || loginBean.getPassword().isEmpty()) {
                message = MessageVarList.LOGIN_EMPTY_PWD;
            } else {
                //check user validity from db
                sysUser = loginDao.findUserbyUsername(loginBean.getUsername());

                String hPass = Common.makeHash(loginBean.getPassword());
                passwordpolicy = loginDao.findPasswordPolicy();
                if (sysUser == null) {
                    message = MessageVarList.LOGIN_INVALID;
                } else if (sysUser.getIpgstatus().getStatuscode().equals(CommonVarList.USER_STATUS_DEACTIVE)) {
                    message = MessageVarList.LOGIN_DEACTIVE;
                } 
                else if (sysUser.getPincount() >= passwordpolicy.getNoofpincount()) {
                    loginBean.setAttempts(sysUser.getPincount());
                    loginBean.setStatus(CommonVarList.USER_STATUS_DEACTIVE);
                    Ipgsystemaudit audit = Common.makeAudittrace(request, sysUser, TaskVarList.LOGIN_TASK, PageVarList.LOGIN_PAGE, SectionVarList.DEFAULT_SECTION, "Login de-activated", null);
                    loginDao.updateUser(loginBean, audit);
                    message = MessageVarList.LOGIN_DEACTIVE;
                } 
                
                else if (!hPass.equals(sysUser.getPassword())) {                   
                    
                    if (sysUser.getPincount() == null || sysUser.getPincount() == 0) {
                        sysUser.setPincount(new Integer("0"));
                    }
                    int attempts = sysUser.getPincount();
                    attempts++;
                    loginBean.setAttempts(attempts);
                    loginBean.setStatus(sysUser.getIpgstatus().getStatuscode());
                    Ipgsystemaudit audit = Common.makeAudittrace(request, sysUser, TaskVarList.LOGIN_TASK, PageVarList.LOGIN_PAGE, SectionVarList.DEFAULT_SECTION, "Login attempted ", null);
                    loginDao.updateUser(loginBean, audit);
                    message = MessageVarList.LOGIN_INVALID;
                }
                else if (message.isEmpty()) {
                    loginBean.setAttempts(new Integer("0"));
                    loginBean.setStatus(sysUser.getIpgstatus().getStatuscode());
                    Ipgsystemaudit audit = Common.makeAudittrace(request, sysUser, TaskVarList.LOGIN_TASK, PageVarList.LOGIN_PAGE, SectionVarList.DEFAULT_SECTION, "Login successfully", null);
                    loginDao.updateUser(loginBean, audit);
                }
            }

        } catch (Exception ex) {
            message = MessageVarList.LOGIN_ERROR_LOAD;
            Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

    public String Logout() {
        String result = "message";
        System.out.println("called LoginAction : Logout");

        try {
            //invalidate session
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session != null) {
                //error messages
                if (loginBean.getMessage() != null && !loginBean.getMessage().isEmpty()) {
                    String message = loginBean.getMessage();
                    if (message.equals("error1")) {
                        loginBean.setErrormessage("Access denied. Please login again.");
                        addActionError("Access denied. Please login again.");
                    } else if (message.equals("error2")) {
                        addActionError("You have been logged in from another mechine. Access denied.");
                        loginBean.setErrormessage("You have been logged in from another mechine. Access denied.");
                    } else if (message.equals("success1")) {
                        addActionMessage("Your password changed successfully. Please login with the new password.");
                        loginBean.setErrormessage("Your password changed successfully. Please login with the new password.");
                    }
                } else {//if loginbean not set check for the session message
//                    if (session != null) {
                        String msg = String.valueOf(session.getAttribute("intercept"));
                        if (msg.equalsIgnoreCase("ERROR_ACCESS")) {
                            addActionError(LogoutMsgVarList.ERROR_ACCESS);
                            loginBean.setErrormessage(LogoutMsgVarList.ERROR_ACCESS);
                        } else if (msg.equals("ERROR_ACCESSPOINT")) {
                            addActionError(LogoutMsgVarList.ERROR_ACCESSPOINT);
                            loginBean.setErrormessage(LogoutMsgVarList.ERROR_ACCESSPOINT);
                        } else if (msg.equals("ERROR_USER_INFO")) {
                            addActionError(LogoutMsgVarList.ERROR_USER_INFO);
                            loginBean.setErrormessage(LogoutMsgVarList.ERROR_USER_INFO);
                        } else if (msg.equals("PASSWORD_CHANGE_SUCCESS")) {
                            loginBean.setErrormessage(LogoutMsgVarList.PASSWORD_CHANGE_SUCCESS);
                            addActionMessage(LogoutMsgVarList.PASSWORD_CHANGE_SUCCESS);
                        } else if (msg.equals("ERROR_ACCESS")) {
                            addActionMessage(LogoutMsgVarList.ERROR_ACCESS);
                            loginBean.setErrormessage(LogoutMsgVarList.ERROR_ACCESS);
                        } 
//                        else if (msg.equals("ERROR_SESSION")) {
//                            addActionMessage(LogoutMsgVarList.ERROR_SESSION);
//                            loginBean.setErrormessage(LogoutMsgVarList.ERROR_SESSION);
//                        }
//                    }
                }

                sysUser = ((Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER));

                if (sysUser != null) {

                    LoginDAO loginDao = new LoginDAO();

                    Ipgsystemaudit audit = Common.makeAudittrace(ServletActionContext.getRequest(), sysUser, TaskVarList.LOGOUT_TASK, PageVarList.LOGIN_PAGE, SectionVarList.DEFAULT_SECTION, "Log out successfully", null);
                    loginBean.setStatus(sysUser.getIpgstatus().getStatuscode());
                    loginBean.setUsername(sysUser.getUsername());

                    loginDao.updateUser(loginBean, audit);
                }


                session.invalidate();

            }

        } catch (Exception ex) {
            Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
