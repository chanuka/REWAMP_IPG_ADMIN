/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.systemconfig;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.RuleBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.RuleInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.RuleDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgrule;
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

/**
 * @created on :Nov 5, 2013, 2:46:48 PM
 * @author :thushanth
 */
public class RuleAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    RuleInputBean inputBean = new RuleInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() throws Exception {
        System.out.println("called RuleAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.RULE_PAGE;
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
        } else if ("findList".equals(method)) {
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
//                inputBean.setRuletypeList(dao.getRuleTypeList());
                inputBean.setRuletypeSerchList(dao.getRuleTypeSearchList());
                inputBean.setRulescopeList(dao.getRuleScopeList());
                inputBean.setSecuritylevelList(dao.getSecurityLevelList(CommonVarList.STATUS_ACTIVE));
                inputBean.setTriggersequenceList(dao.getTriggerSequenceList());
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
//                List<String> opplist = new ArrayList<String>();
//                opplist.add("=");
//                opplist.add("<");
//                opplist.add(">");
//                inputBean.setOperationList(opplist);

            } else {
                result = "loginpage";
            }

            System.out.println("called RuleAction :view");


        } catch (Exception ex) {
            addActionError("RuleAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String find() {
        System.out.println("called RuleAction: Find");

        Ipgrule ipgrule = new Ipgrule();

        try {
            if (inputBean.getRuleId() != null && !inputBean.getRuleId().isEmpty()) {

                RuleDAO dao = new RuleDAO();

                ipgrule = dao.findRuleByID(inputBean.getRuleId());
                try{
                    inputBean.setRuletypeList(dao.findRuleTypeByRuleScope(ipgrule.getIpgrulescope().getRulescopecode()));
                    inputBean.setOperationList(dao.findOperatorByRuleScope(ipgrule.getIpgrulescope().getRulescopecode()));
                }catch(Exception e){
                    
                }
                inputBean.setRuleId(new Long(ipgrule.getRuleid()).toString());
                inputBean.setRuleScope(ipgrule.getIpgrulescope().getRulescopecode());
                inputBean.setRuleScope2(ipgrule.getIpgrulescope().getRulescopecode());
                inputBean.setDescription(ipgrule.getDescription());
                inputBean.setEndValue(ipgrule.getEndvalue());
                inputBean.setStartValue(ipgrule.getStartvalue());
//                inputBean.setPriority(ipgrule.getPriority());
                inputBean.setRuleType(ipgrule.getRuletypecode());
                inputBean.setSecurityLevel(ipgrule.getIpgsecuritylevel().getSecuritylevel());
                inputBean.setOperator(ipgrule.getOperator());
                inputBean.setTriggersequence(ipgrule.getTriggersequenceid().toString());
                inputBean.setStatus(ipgrule.getIpgstatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty Rule ID");
            }
        } catch (Exception ex) {
            inputBean.setMessage("RuleAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }
    
    public String findList() {
        System.out.println("called RuleAction: FindListByRuleScope");

        try {
            if (inputBean.getRuleScope() != null && !inputBean.getRuleScope().isEmpty()) {
                RuleDAO dao = new RuleDAO();
                inputBean.setRuletypeList(dao.findRuleTypeByRuleScope(inputBean.getRuleScope()));
                inputBean.setOperationList(dao.findOperatorByRuleScope(inputBean.getRuleScope()));
                
            } else {
                inputBean.setMessage("Empty Rule Scope");
            }
        } catch (Exception ex) {
            inputBean.setMessage("RuleAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.RULE_PAGE, request);


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
        System.out.println("called RuleAction: List");
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


            RuleDAO dao = new RuleDAO();
            List<RuleBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            /**
             * for audit search
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("[")
                        .append(checkEmptyorNullString("Rule Scope Code", inputBean.getRuleScope()))
                        .append(checkEmptyorNullString("Operator", inputBean.getOperator()))
                        .append(checkEmptyorNullString("Rule Type", inputBean.getRuleType()))
                        .append(checkEmptyorNullString("Start Value", inputBean.getStartValue()))
                        .append(checkEmptyorNullString("End Value", inputBean.getEndValue()))
//                        .append(checkEmptyorNullString("Priority", inputBean.getPriority()))
                        .append(checkEmptyorNullString("Security Level", inputBean.getSecurityLevel()))
                        .append(checkEmptyorNullString("Description", inputBean.getDescription()))
                        .append(checkEmptyorNullString("Trigger Sequence", inputBean.getTriggersequence()))
                        .append(checkEmptyorNullString("Status", inputBean.getStatus()))
                        .append("]");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.RULE_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Rule search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(RuleAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " OnUsBinRangeAction");
        }
        return "list";
    }

    private String validateInputs() throws Exception {

        String message = "";
        try {
            if (inputBean.getRuleScope() == null || inputBean.getRuleScope().trim().isEmpty()) {
                message = MessageVarList.RULE_EMPTY_RULE_SCOPE_CODE;
            } else if (inputBean.getOperator() == null || inputBean.getOperator().trim().isEmpty()) {
                message = MessageVarList.RULE_EMPTY_OPERATOR;
            } else if (inputBean.getRuleType() == null || inputBean.getRuleType().trim().isEmpty()) {
                message = MessageVarList.RULE_EMPTY_RULE_TYPE;
            } else if (inputBean.getStartValue() == null || inputBean.getStartValue().trim().isEmpty()) {
                message = MessageVarList.RULE_EMPTY_START_VALUE;
            } else if (inputBean.getEndValue() == null || inputBean.getEndValue().trim().isEmpty()) {
                message = MessageVarList.RULE_EMPTY_END_VALUE;
//            } else if (inputBean.getPriority() == null || inputBean.getPriority().trim().isEmpty()) {
//                message = MessageVarList.RULE_EMPTY_PRIORITY;
            } else if (inputBean.getSecurityLevel() == null || inputBean.getSecurityLevel().trim().isEmpty()) {
                message = MessageVarList.RULE_EMPTY_SECURITY_LEVEL;
            } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
                message = MessageVarList.RULE_EMPTY_DES;
            } else if (inputBean.getTriggersequence()== null || inputBean.getTriggersequence().trim().isEmpty()) {
                message = MessageVarList.RULE_EMPTY_TRIGGERSEQUENCE;
            } else if (inputBean.getStatus()== null || inputBean.getStatus().trim().isEmpty()) {
                message = MessageVarList.RULE_EMPTY_STATUS;
            } else {
                if (!Validation.isSpecailCharacter(inputBean.getStartValue().trim())) {
                    if (!Validation.isValidUrl(inputBean.getStartValue().trim())) {
                        message = MessageVarList.RULE_INVALID_START_VALUE;
                    }
                } else if (!Validation.isSpecailCharacter(inputBean.getEndValue().trim())) {
                    if (!Validation.isValidUrl(inputBean.getStartValue().trim())) {
                        message = MessageVarList.RULE_INVALID_END_VALUE;
                    }
//                } else if (!Validation.isSpecailCharacter(inputBean.getPriority().trim())) {
//                    message = MessageVarList.RULE_INVALID_PRIORITY;
                } else if (!Validation.isSpecailCharacter(inputBean.getDescription().trim())) {
                    message = MessageVarList.RULE_INVALID_DES;
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return message;
    }
    
    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called RuleAction : ViewPopup");

        try {
             if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
//                inputBean.setRuletypeList(dao.getRuleTypeList());
                inputBean.setRulescopeList(dao.getRuleScopeList());
                inputBean.setSecuritylevelList(dao.getSecurityLevelList(CommonVarList.STATUS_ACTIVE));
                inputBean.setTriggersequenceList(dao.getTriggerSequenceList());
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
//                List<String> opplist = new ArrayList<String>();
//                opplist.add("=");
//                opplist.add("<");
//                opplist.add(">");
//                inputBean.setOperationList(opplist);
            } else {
                result = "loginpage";
            }
        } catch (Exception e) {
            addActionError("RuleAction" + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    
    public String add() {
        System.out.println("called RuleAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            RuleDAO dao = new RuleDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.RULE_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Rule added", null);
                message = dao.insertRule(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Rule " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("RuleAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String delete() {
        System.out.println("called RuleAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            RuleDAO dao = new RuleDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.RULE_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Rule ID "+inputBean.getRuleId()+" deleted", null);
            message = dao.deleteRule(inputBean, audit);
            if (message.isEmpty()) {
                message = "Rule " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(RuleAction.class.getName()).log(Level.SEVERE, null, e);
//				            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }
    
    public String detail() {

        System.out.println("called RuleAction: detail");
        Ipgrule ipgrule = new Ipgrule();

        try {
            if (inputBean.getRuleId() != null && !inputBean.getRuleId().isEmpty()) {
                
                CommonDAO comdao = new CommonDAO();
//                inputBean.setRuletypeList(comdao.getRuleTypeList());
                inputBean.setRulescopeList(comdao.getRuleScopeList());
                inputBean.setSecuritylevelList(comdao.getSecurityLevelList(CommonVarList.STATUS_ACTIVE));
                inputBean.setTriggersequenceList(comdao.getTriggerSequenceList());
                inputBean.setStatusList(comdao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                
                RuleDAO dao = new RuleDAO();
                ipgrule = dao.findRuleByID(inputBean.getRuleId());
                try{
                    inputBean.setRuletypeList(dao.findRuleTypeByRuleScope(ipgrule.getIpgrulescope().getRulescopecode()));
                    inputBean.setOperationList(dao.findOperatorByRuleScope(ipgrule.getIpgrulescope().getRulescopecode()));
                }catch(Exception e){
                    
                }
                

                inputBean.setRuleId(new Long(ipgrule.getRuleid()).toString());
                inputBean.setRuleScope(ipgrule.getIpgrulescope().getRulescopecode());
                inputBean.setRuleScope2(ipgrule.getIpgrulescope().getRulescopecode());
                inputBean.setDescription(ipgrule.getDescription());
                inputBean.setEndValue(ipgrule.getEndvalue());
                inputBean.setStartValue(ipgrule.getStartvalue());
//                inputBean.setPriority(ipgrule.getPriority());
                inputBean.setRuleType(ipgrule.getRuletypecode());
                inputBean.setSecurityLevel(ipgrule.getIpgsecuritylevel().getSecuritylevel());
                inputBean.setOperator(ipgrule.getOperator());
                inputBean.setTriggersequence(ipgrule.getTriggersequenceid().toString());
                inputBean.setStatus(ipgrule.getIpgstatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty Rule ID");
            }

        } catch (Exception e) {
            addActionError("RuleAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(RuleAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    }

    public String update() {

        System.out.println("called RuleAction : update");
        String retType = "message";


        try {
            if (inputBean.getRuleId() != null && !inputBean.getRuleId().isEmpty()) {
                inputBean.setRuleScope(inputBean.getRuleScope2());
                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    RuleDAO dao = new RuleDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.RULE_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Rule ID "+inputBean.getRuleId()+" updated", null);
                    message = dao.updateRule(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Rule " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RuleAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Rule " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }
}
