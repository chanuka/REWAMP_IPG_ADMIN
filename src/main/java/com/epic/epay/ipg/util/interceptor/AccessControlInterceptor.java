/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.interceptor;

import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;
import java.util.HashMap;
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
public class AccessControlInterceptor implements Interceptor{

    public void destroy() {
        System.out.println("Access Control Interceptor : Destroyed");
    }

    public void init() {
       System.out.println("Access Control Interceptor : Initialized");
    }

    public String intercept(ActionInvocation ai) throws Exception {
        String result = "";
        String INTERCEPT_LOGOUT = "acccontroler";
        try {
            System.out.println("-------Access Control Interceptor : Started-------");
            String className = ai.getAction().getClass().getName();
            System.out.println("Access Control Interceptor : Intercepted " + className);

            ActionProxy ap = ai.getProxy();
            String method = ap.getMethod();

            System.out.println("Access Control Interceptor : Method " + method);

            if (method.trim().equalsIgnoreCase("Check") || method.trim().equalsIgnoreCase("Logout")) {
                result = ai.invoke();
            } else {
                try {
                    HttpServletRequest request = ServletActionContext.getRequest();
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
                        if (user != null) {
                            user.getUsername();
                            //check user logged in another mechine
                            ServletContext sc = ServletActionContext.getServletContext();
                            HashMap<String, String> userMap = (HashMap<String, String>) sc.getAttribute(SessionVarlist.USERMAP);
                            String sessionId = userMap.get(user.getUsername());
                            //
                            if (sessionId.equals(session.getId())) {
                                //check user access
                                boolean status = false;
                                Object action = ai.getAction();
                                if (action instanceof AccessControlService) {/*------------- have to be deletede after implemented------------*/
                                    if (((AccessControlService) action).checkAccess(method, user.getIpguserroleByUserrolecode().getUserrolecode())) {
                                        result = ai.invoke();
                                    } else {
                                        result = INTERCEPT_LOGOUT;
                                        session.setAttribute("intercept", "ERROR_ACCESS");
                                    }
                                } else {/*------------- have to be deletede after implemented------------*/
                                    System.out.println("Please implement Access Control Service. This method is allowed for everyone.");
                                    result = ai.invoke();
                                }
                            } else {
                                result = "multiaccess";
                            }
                        } else {
                            result = INTERCEPT_LOGOUT;
                            session.setAttribute("intercept", "ERROR_USER_INFO");
                        }
                    } else {
                        result = INTERCEPT_LOGOUT;
                    }

                } catch (Exception ex) {
                    Logger.getLogger(AccessControlInterceptor.class.getName()).log(Level.SEVERE, null, ex);
                    result = INTERCEPT_LOGOUT;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(AccessControlInterceptor.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("Access Control Interceptor : result " + result);
        System.out.println("-------Access Control Interceptor : Stopped-------");
        return result;
    }
    
}
