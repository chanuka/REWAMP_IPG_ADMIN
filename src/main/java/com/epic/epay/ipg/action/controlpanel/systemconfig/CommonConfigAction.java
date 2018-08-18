/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.CommonConfigBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.CommonConfigInputBean;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.CommonConfigDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.mapping.Ipgcommonconfig;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
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
 * @created :Oct 29, 2013, 9:24:28 AM
 * @author :thushanth
 */
public class CommonConfigAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    CommonConfigInputBean inputBean = new CommonConfigInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() throws Exception {
        System.out.println("called CommonConfigAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.COMMON_CONFIG_PAGE;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("list".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("update".equals(method)) {
            task = TaskVarList.UPDATE_TASK;
        } else if ("detail".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("find".equals(method)) {
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
            if (!this.applyUserPrivileges()) {
                result = "loginpage";
            }

            System.out.println("called CommonConfigAction :view");

        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " common configurations");
            Logger.getLogger(CommonConfigAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String find() {
        System.out.println("called CommonConfigAction: find");
        String result = "find";
        Ipgcommonconfig ipgcommonconfig = new Ipgcommonconfig();

        try {
            if (inputBean.getCode()!= null && !inputBean.getCode().isEmpty()) {

                CommonConfigDAO dao = new CommonConfigDAO();

                ipgcommonconfig = dao.findCommonConfigById(inputBean.getCode());

                inputBean.setDescription(ipgcommonconfig.getDescription());
                inputBean.setValue(ipgcommonconfig.getValue());

            } else {
                inputBean.setMessage("Empty common configuration code");
            }
        } catch (Exception ex) {
            inputBean.setMessage(MessageVarList.COMMON_ERROR_PROCESS + " common configurations " );
            Logger.getLogger(CommonConfigAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;

    }

    

    public String list() {
        String result = "list";
        System.out.println("called CommonConfigAction: list");
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
            CommonConfigDAO dao = new CommonConfigDAO();
            List<CommonConfigBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            
            /**
             * for audit search
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("Common configuration search using [")
                        .append(checkEmptyorNullString("Code", inputBean.getCode()))
                        .append(checkEmptyorNullString("Description", inputBean.getDescription()))
                        .append(checkEmptyorNullString("Value", inputBean.getValue()))
                        .append("] parameters");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.COMMON_CONFIG_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, searchParameters.toString(), null);
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
            Logger.getLogger(CommonConfigAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " common configurations");
        }
        return result;
    }

    private String validateInputs() throws Exception {

        String message = "";
        try {
            if (inputBean.getCode()== null || inputBean.getCode().trim().isEmpty()) {
                message = MessageVarList.COMMON_CONFIG_EMPTY_CODE;
            } else if (inputBean.getDescription()== null || inputBean.getDescription().trim().isEmpty()) {
                message = MessageVarList.COMMON_CONFIG_EMPTY_DES;
            } else if (inputBean.getValue()== null || inputBean.getValue().trim().isEmpty()) {
                message = MessageVarList.COMMON_CONFIG_EMPTY_VALUE;
            } 
            else {

//                try {
//                    if (!Validation.isvalidIP(inputBean.getMpiServerIP().trim())) {
//                        message = MessageVarList.COMMON_CONFIG_INVALID_SERVER_IP;
//                    } else if (!Validation.isvalidIP(inputBean.getSwitchIP().trim())) {
//                        message = MessageVarList.COMMON_CONFIG_INVLID_SWITCH_IP;
//                    } else if (!Validation.isNumeric(inputBean.getMpiServerPort().trim())) {
//                        message = MessageVarList.COMMON_CONFIG_INVALID_SERVER_PORT;
//                    } else if (!Validation.isNumeric(inputBean.getSwitchPort().trim())) {
//                        message = MessageVarList.COMMON_CONFIG_INVLID_SWITCH_PORT;
//                    } else if (Integer.parseInt(inputBean.getMpiServerPort().trim()) > 65535) {
//                        message = MessageVarList.COMMON_CONFIG_INVALID_SERVER_PORT;
//                    } else if (Integer.parseInt(inputBean.getSwitchPort().trim()) > 65535) {
//                        message = MessageVarList.COMMON_CONFIG_INVLID_SWITCH_PORT;
//                    } else if (!Validation.isValidUrl(inputBean.getIpgengineurl().trim())) {
//                        message = MessageVarList.COMMON_CONFIG_INVLID_IPG_ENGINE_URL;
//                    } else if (!Validation.isValidUrl(inputBean.getMpiserverurl().trim())) {
//                        message = MessageVarList.COMMON_CONFIG_INVLID_MPI_SERVER_URL;
//                    }else if (!Validation.isNumeric(inputBean.getAcquirerBin().trim()) || inputBean.getAcquirerBin().length()!=6) {
//                        message = MessageVarList.COMMON_CONFIG_INVLID_MPI_ACQUIREBIN;
//                    }
//                } catch (PatternSyntaxException e) {
//                    message = MessageVarList.COMMON_FILE_PATH_SYNTAX_ERROR;
//                } catch (Exception ex) {
//                    message = MessageVarList.COMMON_FILE_PATH_URL_ERROR;
//                }
            }

        } catch (Exception e) {
            throw e;
        }
        return message;
    }
    
    public String detail() {        
        System.out.println("called CommonConfigAction: detail");
        String result = "detail";
        Ipgcommonconfig ipgcommonconfig = new Ipgcommonconfig();

        try {
            if (inputBean.getCode()!= null && !inputBean.getCode().isEmpty()) {

                CommonConfigDAO dao = new CommonConfigDAO();

                ipgcommonconfig = dao.findCommonConfigById(inputBean.getCode());

                
                inputBean.setDescription(ipgcommonconfig.getDescription());
                inputBean.setValue(ipgcommonconfig.getValue());
                
            } else {
                inputBean.setMessage("Empty common configuration code.");
            }

        } catch (Exception e) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS+ " common configurations " );
            Logger.getLogger(CommonConfigAction.class.getName()).log(Level.SEVERE,null, e);
        }

        return result;

    }
    
    public String update() {

        System.out.println("called CommonConfignAction : update");
        String result = "message";

        try {
            if (inputBean.getCode()!= null && !inputBean.getCode().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    CommonConfigDAO dao = new CommonConfigDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.COMMON_CONFIG_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Common configuration code "+inputBean.getCode()+" updated", null);
                    message = dao.updateCommonConfig(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Common configuration " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CommonConfigAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError(MessageVarList.COMMON_ERROR_UPDATE + "common configurations");
        }
        return result;
    }
    
    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.COMMON_CONFIG_PAGE, request);

        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                    inputBean.setVupdatelink(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                }else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                }
            }
        }
        inputBean.setVupdatebutt(true);

        return true;
    }
}
