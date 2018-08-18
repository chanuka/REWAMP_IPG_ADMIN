/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.AuthConfigurationBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.AuthConfigurationInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.AuthConfigurationDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.mapping.Ipgauthconfiguration;
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
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author badrika
 */
public class AuthConfigurationAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    AuthConfigurationInputBean inputBean = new AuthConfigurationInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() throws Exception {
        System.out.println("called AuthConfigurationAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.AUTH_CONFIG_PAGE;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("list".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("viewpopup".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("add".equals(method)) {
            task = TaskVarList.ADD_TASK;
        } else if ("delete".equals(method)) {
            task = TaskVarList.DELETE_TASK;
        } else if ("find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("update".equals(method)) {
            task = TaskVarList.UPDATE_TASK;
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

    public String view() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
                AuthConfigurationDAO acdao = new AuthConfigurationDAO();
                inputBean.setCardassociationList(dao.getDefultCardAssociationList());
                inputBean.setTxstatusMap(this.gettxstatusList());
                inputBean.setEcivalueList(acdao.getECIValueList());
                inputBean.setAuthstatusList(dao.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));

            } else {
                result = "loginpage";
            }

            System.out.println("called AuthConfigurationAction :view");

        } catch (Exception ex) {
            addActionError("Auth Configuration " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(AuthConfigurationAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private HashMap<String, String> gettxstatusList() {

        HashMap<String, String> statusMap = new HashMap<String, String>();
        statusMap.put("Y", "Authentication Successful(Y)");
        statusMap.put("A", "Proof of Authentication(A)");
        statusMap.put("U", "Authentication Could not Complete(U)");
        statusMap.put("N", "Authentication Failed(N)");
        return statusMap;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.AUTH_CONFIG_PAGE, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
                    inputBean.setVdelete(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                    inputBean.setVupdatelink(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                }
            }
        }
        inputBean.setVupdatebutt(true);

        return true;
    }

    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called CardAssociationAction : ViewPopup");

        try {

            if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
                AuthConfigurationDAO acdao = new AuthConfigurationDAO();
                inputBean.setCardassociationList(dao.getDefultCardAssociationList());
                inputBean.setTxstatusMap(this.gettxstatusList());
                inputBean.setEcivalueList(acdao.getECIValueList());
                inputBean.setAuthstatusList(dao.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));
            } else {
                result = "loginpage";
            }

        } catch (Exception e) {
            addActionError("AuthConfigurationAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(AuthConfigurationAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String add() {
        System.out.println("called AuthConfigurationAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            AuthConfigurationDAO dao = new AuthConfigurationDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {
                /**
                 * for audit description
                 */
                StringBuilder description = new StringBuilder();
                description.append("Authentication configuration [")
                        .append("Card Association-").append(inputBean.getCardassociation()).append(", ")
                        .append("Txn Status-").append(inputBean.getTxstatus()).append(", ")
                        .append("ECI Value-").append(inputBean.getEcivalue())
                        .append("] added");
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.AUTH_CONFIG_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, description.toString(), null);
                message = dao.insertAuthConfig(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Auth Configuration " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Auth Configuration " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(AuthConfigurationAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private String validateInputs() throws Exception {

        String message = "";
        try {
            if (inputBean.getCardassociation() != null && inputBean.getCardassociation().isEmpty()) {
                message = MessageVarList.AUTH_CONFIG_EMPTY_CA;
            } else if (inputBean.getTxstatus() != null && inputBean.getTxstatus().isEmpty()) {
                message = MessageVarList.AUTH_CONFIG_EMPTY_TXSTATUS;
            } else if (inputBean.getEcivalue() != null && inputBean.getEcivalue().isEmpty()) {
                message = MessageVarList.AUTH_CONFIG_EMPTY_ECI_VALUE;
            } else if (inputBean.getStatus() != null && inputBean.getStatus().isEmpty()) {
                message = MessageVarList.AUTH_CONFIG_EMPTY_STATUS;
            }

        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    private String validateForUpdate() throws Exception {

        String message = "";
        try {
            if (inputBean.getStatus() != null && inputBean.getStatus().isEmpty()) {
                message = MessageVarList.AUTH_CONFIG_EMPTY_STATUS;
            }

        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    public String detail() {

        System.out.println("called AuthConfigurationAction: detail");
        Ipgauthconfiguration i = new Ipgauthconfiguration();

        try {
            if (inputBean.getPkey() != null && !inputBean.getPkey().isEmpty()) {

                CommonDAO comdao = new CommonDAO();
                AuthConfigurationDAO acdao = new AuthConfigurationDAO();
                inputBean.setCardassociationList(comdao.getDefultCardAssociationList());
                inputBean.setTxstatusMap(this.gettxstatusList());
                inputBean.setEcivalueList(acdao.getECIValueList());
                inputBean.setAuthstatusList(comdao.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));

                AuthConfigurationDAO dao = new AuthConfigurationDAO();

                i = dao.findAuthConfigByPkey(inputBean.getPkey());

                inputBean.setCardassociation(i.getId().getCardassociationcode());
                inputBean.setEcivalue(i.getId().getEcivalue());
                inputBean.setTxstatus(i.getId().getTxstatus());
                inputBean.setStatus(i.getStatus());

            } else {
                inputBean.setMessage("Empty Auth Config Code");
            }

        } catch (Exception e) {
            addActionError("Card AuthConfigurationAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(AuthConfigurationAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    }

    public String update() {

        System.out.println("called AuthConfigurationAction : update");
        String retType = "message";

        try {
            if (inputBean.getPkey() != null && !inputBean.getPkey().isEmpty()) {

                String message = this.validateForUpdate();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    AuthConfigurationDAO dao = new AuthConfigurationDAO();
                    /**
                     * for audit description
                     */
                    StringBuilder description = new StringBuilder();
                    description.append("Authentication configuration [")
                            .append("Card Association-").append(inputBean.getPkey().split("\\|")[0]).append(", ")
                            .append("Txn Status-").append(inputBean.getPkey().split("\\|")[1]).append(", ")
                            .append("ECI Value-").append(inputBean.getPkey().split("\\|")[2])
                            .append("] updated");
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.AUTH_CONFIG_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, description.toString(), null);
                    message = dao.updateAuthConfig(inputBean, audit);
                    if (message.isEmpty()) {
                        addActionMessage("Auth Configuration  " + MessageVarList.COMMON_SUCC_UPDATE);
                        System.out.println("Auth Configuration  " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AuthConfigurationAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Auth Configuration  " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String delete() {
        System.out.println("called AuthConfigurationAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            AuthConfigurationDAO dao = new AuthConfigurationDAO();
            /**
             * for audit description
             */
            StringBuilder description = new StringBuilder();
            description.append("Authentication configuration [")
                    .append("Card Association-").append(inputBean.getPkey().split("\\|")[0]).append(", ")
                    .append("Txn Status-").append(inputBean.getPkey().split("\\|")[1]).append(", ")
                    .append("ECI Value-").append(inputBean.getPkey().split("\\|")[2])
                    .append("] deleted");
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.AUTH_CONFIG_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Authentication configuration [" + inputBean.getCardassociation() + ", " + inputBean.getTxstatus() + ", " + inputBean.getEcivalue() + "] deleted", null);
            message = dao.deleteAuthConfig(inputBean, audit);
            if (message.isEmpty()) {
                message = "Auth Configuration " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(AuthConfigurationAction.class.getName()).log(Level.SEVERE, null, e);
//	            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String list() {
        System.out.println("called AuthConfigurationAction: List");
        try {
            //if (inputBean.isSearch()) {

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }
            AuthConfigurationDAO dao = new AuthConfigurationDAO();
            List<AuthConfigurationBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            /**
             * for audit search
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("[")
                        .append(checkEmptyorNullString("Card Association", inputBean.getCardassociation()))
                        .append(checkEmptyorNullString("ECI Value", inputBean.getEcivalue()))
                        .append(checkEmptyorNullString("Transaction Status", inputBean.getTxstatus()))
                        .append(checkEmptyorNullString("Authentication Status", inputBean.getStatus()))
                        .append("]");

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.AUTH_CONFIG_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Authenticate configuration search using " + searchParameters + " parameters ", null);
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
            // }
        } catch (Exception e) {
            Logger.getLogger(AuthConfigurationAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Auth Configuration ");
        }
        return "list";
    }

    public String find() {
        System.out.println("called AuthConfigurationAction: find");

        Ipgauthconfiguration i = new Ipgauthconfiguration();

        try {
            if (inputBean.getPkey() != null && !inputBean.getPkey().isEmpty()) {

                AuthConfigurationDAO dao = new AuthConfigurationDAO();

                i = dao.findAuthConfigByPkey(inputBean.getPkey());

                inputBean.setCardassociation(i.getId().getCardassociationcode());
                inputBean.setEcivalue(i.getId().getEcivalue());
                inputBean.setTxstatus(i.getId().getTxstatus());
                inputBean.setStatus(i.getStatus());

            } else {
                inputBean.setMessage("Empty Auth Config Code");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Auth Configuration " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(AuthConfigurationAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

}
