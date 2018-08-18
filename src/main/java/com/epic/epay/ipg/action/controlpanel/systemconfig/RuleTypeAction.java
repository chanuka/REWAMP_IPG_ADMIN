/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.RuleTypeBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.RuleTypeInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.RuleTypeDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgruletype;
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
 * @created on :Nov 4, 2013, 4:37:30 PM
 * @author :thushanth
 */
public class RuleTypeAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    RuleTypeInputBean inputBean = new RuleTypeInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() throws Exception {
        System.out.println("called RuleTypeAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.RULE_TYPE_PAGE;
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
                inputBean.setRulescopeList(dao.getRuleScopeList());
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
            } else {
                result = "loginpage";
            }

            System.out.println("called RuleTypeAction :view");


        } catch (Exception ex) {
            addActionError("RuleTypeAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleTypeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String find() {
        System.out.println("called RuleTypeAction: Find");

        Ipgruletype ipgruletype = new Ipgruletype();

        try {
            if (inputBean.getRuletypeCode() != null && !inputBean.getRuletypeCode().isEmpty()) {
                 if (inputBean.getRulescope()!= null && !inputBean.getRulescope().isEmpty()) {
                      RuleTypeDAO dao = new RuleTypeDAO();

                    ipgruletype = dao.findRuleTypeByCode(inputBean);

                    inputBean.setRuletypeCode(ipgruletype.getId().getRuletypecode());
                    inputBean.setRulescope(ipgruletype.getId().getRulescopecode());
                    inputBean.setDescription(ipgruletype.getDescription());
                    inputBean.setStatus(ipgruletype.getIpgstatus().getStatuscode());
                 }else{
                    inputBean.setMessage("Empty Rule Cope");
                 }

            } else {
                inputBean.setMessage("Empty Rule Type Code");
            }
        } catch (Exception ex) {
            inputBean.setMessage("RuleTypeAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleTypeAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.RULE_TYPE_PAGE, request);


        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                }else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
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

        return true;
    }

    public String list() {
        System.out.println("called RuleTypeAction: List");
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
            RuleTypeDAO dao = new RuleTypeDAO();
            List<RuleTypeBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            /**
             * for audit search
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("[")
                        .append(checkEmptyorNullString("Rule Type Code ", inputBean.getRuletypeCode()))
                        .append(checkEmptyorNullString("Description", inputBean.getDescription()))
                        .append(checkEmptyorNullString("Rule Scope", inputBean.getRulescope()))
                        .append(checkEmptyorNullString("Status", inputBean.getStatus()))
                        .append("]");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.RULE_TYPE_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Rule type search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(RuleTypeAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " RuleTypeAction");
        }
        return "list";
    }

    private String validateInputs() throws Exception {

        String message = "";
        try {
            if (inputBean.getRuletypeCode() == null || inputBean.getRuletypeCode().trim().isEmpty()) {
                message = MessageVarList.RULE_TYPE_EMPTY_CODE;
            } else if (inputBean.getRulescope()== null || inputBean.getRulescope().trim().isEmpty()) {
                message = MessageVarList.RULE_TYPE_EMPTY_SCOPE;    
            } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
                message = MessageVarList.RULE_TYPE_EMPTY_DES;
            } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
                message = MessageVarList.RULE_TYPE_EMPTY_STATUS;
            } else {
                if (!Validation.isSpecailCharacter(inputBean.getRuletypeCode().trim())) {
                    message = MessageVarList.RULE_TYPE_INVALID_CODE;
                } else if (!Validation.isSpecailCharacter(inputBean.getDescription().trim())) {
                    message = MessageVarList.RULE_TYPE_INVALID_DES;
                    
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called RuleTypeAction : ViewPopup");

        try {
             if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
                inputBean.setRulescopeList(dao.getRuleScopeList());
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                 
            } else {
                result = "loginpage";
            }
        } catch (Exception e) {
            addActionError("RuleTypeAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleTypeAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    
    public String add() {
        System.out.println("called RuleTypeAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            RuleTypeDAO dao = new RuleTypeDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.RULE_TYPE_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Rule type code "+inputBean.getRuletypeCode()+" added", null);
                message = dao.insertRuleType(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Rule Type " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("RuleTypeAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleTypeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String delete() {
        System.out.println("called RuleTypeAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            RuleTypeDAO dao = new RuleTypeDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.RULE_TYPE_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Rule type code "+inputBean.getRuletypeCode()+" deleted", null);
            message = dao.deleteRuleType(inputBean, audit);
            if (message.isEmpty()) {
                message = "Rule Type " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(RuleTypeAction.class.getName()).log(Level.SEVERE, null, e);
//	            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }
    
    public String detail() {

        System.out.println("called RuleTypeAction: detail");
        Ipgruletype ipgruletype = new Ipgruletype();

        try {
            if (inputBean.getRuletypeCode() != null && !inputBean.getRuletypeCode().isEmpty()) {
                if (inputBean.getRulescope()!= null && !inputBean.getRulescope().isEmpty()) {
                    CommonDAO comdao = new CommonDAO();
                   inputBean.setRulescopeList(comdao.getRuleScopeList());
                   inputBean.setStatusList(comdao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

                   RuleTypeDAO dao = new RuleTypeDAO();

                   ipgruletype = dao.findRuleTypeByCode(inputBean);

                   inputBean.setRuletypeCode(ipgruletype.getId().getRuletypecode());
                   inputBean.setRulescope(ipgruletype.getId().getRulescopecode());
                   inputBean.setRulescope2(ipgruletype.getId().getRulescopecode());
                   inputBean.setDescription(ipgruletype.getDescription());
                   inputBean.setStatus(ipgruletype.getIpgstatus().getStatuscode());
                }else{
                   inputBean.setMessage("Empty Rule Cope");
                }    
            } else {
                inputBean.setMessage("Empty Rule Type Code");
            }

        } catch (Exception e) {
            addActionError("RuleTypeAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleTypeAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    }
    
    public String update() {

        System.out.println("called RuleTypeAction : update");
        String retType = "message";


        try {
            if (inputBean.getRuletypeCode() != null && !inputBean.getRuletypeCode().isEmpty()) {
                inputBean.setRulescope(inputBean.getRulescope2());
                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    RuleTypeDAO dao = new RuleTypeDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.RULE_TYPE_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Rule type code "+inputBean.getRuletypeCode()+" updated", null);
                    message = dao.updateRuleType(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Rule Type " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RuleTypeAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Rule Type " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }
}
