/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.RuleScopeBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.RuleScopeInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.RuleScopeDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgrulescope;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author chalitha
 */
public class RuleScopeAction extends ActionSupport implements
        ModelDriven<Object>, AccessControlService {

    RuleScopeInputBean inputBean = new RuleScopeInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called RuleScopeAction : execute");
        return SUCCESS;
    }

    public String view() {
        String result = "view";
        try {
            if (!this.applyUserPrivileges()) {
                result = "loginpage";
            }else{
                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
            }

            System.out.println("called RuleScopeAction :view");

        } catch (Exception ex) {
            addActionError("RuleScope " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleScopeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called RuleScopeAction : ViewPopup");

        try {
            if (!this.applyUserPrivileges()) {
                result = "loginpage";
            }else{
                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
            }
        } catch (Exception e) {
            addActionError("RuleScopeAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleScopeAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    
    public String add() {
        System.out.println("called RuleScopeAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            RuleScopeDAO dao = new RuleScopeDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request,
                        TaskVarList.ADD_TASK, PageVarList.RULE_SCOPE_MGT_PAGE,
                        SectionVarList.SYSTEMCONFIGMANAGEMENT, "Rule scope code "+inputBean.getRulescopecode()+" added", null);
                message = dao.insertRuleScope(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("RuleScope "
                            + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("RuleScope " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleScopeAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return result;
    }

    public String detail() {

        System.out.println("called RuleScopeAction: detail");
         Ipgrulescope rulescope;
        
        try {
            if (inputBean.getRulescopecode() != null
                    && !inputBean.getRulescopecode().isEmpty()) {
                CommonDAO comdao = new CommonDAO();
                inputBean.setStatusList(comdao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

                RuleScopeDAO dao = new RuleScopeDAO();

                rulescope = dao.findRuleScopeById(inputBean.getRulescopecode());

                inputBean.setRulescopecode(rulescope.getRulescopecode());
                inputBean.setDescription(rulescope.getDescription());
                inputBean.setStatus(rulescope.getIpgstatus().getStatuscode());
//                inputBean.setSortkey(rulescope.getSortkey().toString());

            } else {
                inputBean.setMessage("Empty Rule scope code.");
            }

        } catch (Exception e) {
            addActionError("RuleScopeAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleScopeAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    }
    
    public String update() {

        System.out.println("called RuleScopeAction : update");
        String retType = "message";

        try {
            if (inputBean.getRulescopecode() != null
                    && !inputBean.getRulescopecode().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    RuleScopeDAO dao = new RuleScopeDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request,
                            TaskVarList.UPDATE_TASK,
                            PageVarList.RULE_SCOPE_MGT_PAGE,
                            SectionVarList.SYSTEMCONFIGMANAGEMENT, "Rule scope code "+inputBean.getRulescopecode()+" updated",
                            null);
                    message = dao.updateRuleScope(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("RuleScope " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RuleScopeAction.class.getName()).log(Level.SEVERE,
                    null, ex);
            addActionError("RuleScope " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String delete() {
        System.out.println("called RuleScopeAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            RuleScopeDAO dao = new RuleScopeDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request,
                    TaskVarList.DELETE_TASK, PageVarList.RULE_SCOPE_MGT_PAGE,
                    SectionVarList.SYSTEMCONFIGMANAGEMENT, "Rule scope code "+inputBean.getRulescopecode()+" deleted", null);
            message = dao.deleteRuleScope(inputBean, audit);
            if (message.isEmpty()) {
                message = "RuleScope " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(RuleScopeAction.class.getName()).log(Level.SEVERE, null, e);
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String list() {

        System.out.println("called RuleScopeAction: List");
        try {

            // if (inputBean.isSearch()) {

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " "
                        + inputBean.getSord();
            }

            RuleScopeDAO dao = new RuleScopeDAO();
            List<RuleScopeBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("[")
                        .append(checkEmptyorNullString("Rule Scope Code ", inputBean.getRulescopecode()))
                        .append(checkEmptyorNullString("Description", inputBean.getDescription()))
                        .append(checkEmptyorNullString("Status ", inputBean.getStatus()))
                        .append("]");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.RULE_SCOPE_MGT_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Rule scope search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(RuleScopeAction.class.getName()).log(Level.SEVERE,
                    null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " RuleScope");
        }
        return "list";
    }

    public String find() {
        System.out.println("called RuleScopeAction: Find");
        Ipgrulescope rulescope;
        try {
            if (inputBean.getRulescopecode() != null
                    && !inputBean.getRulescopecode().isEmpty()) {

                RuleScopeDAO dao = new RuleScopeDAO();

                rulescope = dao.findRuleScopeById(inputBean.getRulescopecode());

                inputBean.setRulescopecode(rulescope.getRulescopecode());
                inputBean.setDescription(rulescope.getDescription());
                inputBean.setStatus(rulescope.getIpgstatus().getStatuscode());
//                inputBean.setSortkey(rulescope.getSortkey().toString());

            } else {
                inputBean.setMessage("Empty Rule scope code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("RuleScope "
                    + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleScopeAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return "find";

    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.RULE_SCOPE_MGT_PAGE;
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

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(
                PageVarList.RULE_SCOPE_MGT_PAGE, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                }else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);    
                } else if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
                    inputBean.setVdelete(false);
                } else if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                } else if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                    inputBean.setVupdatelink(false);
                }
            }
        }
        inputBean.setVupdatebutt(true);

        return true;
    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getRulescopecode() == null
                || inputBean.getRulescopecode().trim().isEmpty()) {
            message = MessageVarList.RULE_SCOPE_MGT_EMPTY_RULE_SCOPE_CODE;
        } else if (inputBean.getDescription() == null
                || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.RULE_SCOPE_MGT_EMPTY_DESCRIPTION;
        } else if (inputBean.getStatus()== null
                || inputBean.getStatus().trim().isEmpty()) {
            message = MessageVarList.RULE_SCOPE_MGT_EMPTY_STATUS_KEY ;
        } else {
            if (!Validation.isSpecailCharacter(inputBean.getRulescopecode())) {
                message = MessageVarList.RULE_SCOPE_MGT_ERROR_RULE_SCOPE_CODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
                message = MessageVarList.RULE_SCOPE_MGT_ERROR_DESC_INVALID;
            }
        }
        return message;
    }
}