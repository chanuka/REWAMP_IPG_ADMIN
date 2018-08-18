/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.systemconfig;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.OnUsBinRangeBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.OnUsBinRangeInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.OnUsBinRangeDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgonusbinrange;
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
 * @created on :Nov 1, 2013, 10:56:16 AM
 * @author :thushanth
 */
public class OnUsBinRangeAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    OnUsBinRangeInputBean inputBean = new OnUsBinRangeInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() throws Exception {
        System.out.println("called OnUsBinRangeAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.ONUSBINRANGE_PAGE;
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
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

            } else {
                result = "loginpage";
            }

            System.out.println("called OnUsBinRangeAction :view");


        } catch (Exception ex) {
            addActionError("OnUsBinRangeAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(OnUsBinRangeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String find() {
        System.out.println("called OnUsBinRangeAction: Find");

        Ipgonusbinrange ipgonusbinrange = new Ipgonusbinrange();

        try {
            if (inputBean.getIpgonusbinrangeId() != null && !inputBean.getIpgonusbinrangeId().isEmpty()) {

                OnUsBinRangeDAO dao = new OnUsBinRangeDAO();

                ipgonusbinrange = dao.findCurrencyByID(inputBean.getIpgonusbinrangeId());

                inputBean.setIpgonusbinrangeId(ipgonusbinrange.getIpgonusbinrangeid().toString());
                inputBean.setValue_1(ipgonusbinrange.getValue1());
                inputBean.setValue_2(ipgonusbinrange.getValue2());
                inputBean.setReMarks(ipgonusbinrange.getRemarks());
                inputBean.setStatus(ipgonusbinrange.getIpgstatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty On-Us-Bin-Range");
            }
        } catch (Exception ex) {
            inputBean.setMessage("OnUsBinRangeAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(OnUsBinRangeAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.ONUSBINRANGE_PAGE, request);


        inputBean.setVadd(true);
        inputBean.setVdelete(true);
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
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                }else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);    
                }
            }
        }
        inputBean.setVupdatebutt(true);

        return true;
    }

    public String list() {
        System.out.println("called OnUsBinRangeAction: List");
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
            OnUsBinRangeDAO dao = new OnUsBinRangeDAO();
            List<OnUsBinRangeBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            /**
             * for audit search
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("[")
                        .append(checkEmptyorNullString("Value 1", inputBean.getValue_1()))
                        .append(checkEmptyorNullString("Value 2", inputBean.getValue_2()))
                        .append(checkEmptyorNullString("Remarks", inputBean.getReMarks()))
                        .append(checkEmptyorNullString("Status Code", inputBean.getStatus()))
                        .append("]");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.ONUSBINRANGE_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "On us BIN range search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(OnUsBinRangeAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " OnUsBinRangeAction");
        }
        return "list";
    }

    private String validateInputs() throws Exception {

        String message = "";
        try {
            if (inputBean.getValue_1() == null || inputBean.getValue_1().trim().isEmpty()) {
                message = MessageVarList.ONUSBINRANGE_EMPTY_VALUE_1;
            } else if (inputBean.getValue_2() == null || inputBean.getValue_2().trim().isEmpty()) {
                message = MessageVarList.ONUSBINRANGE_EMPTY_VALUE_2;
            } else if (inputBean.getReMarks() == null || inputBean.getReMarks().trim().isEmpty()) {
                message = MessageVarList.ONUSBINRANGE_EMPTY_REMARKS;
            } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
                message = MessageVarList.ONUSBINRANGE_EMPTY_STATUS;
            } else {
                if (!Validation.isSpecailCharacter(inputBean.getValue_1().trim())) {
                    message = MessageVarList.ONUSBINRANGE_INVALID_VALUE_1;
                } else if (!Validation.isSpecailCharacter(inputBean.getValue_2().trim())) {
                    message = MessageVarList.ONUSBINRANGE_INVALID_VALUE_2;
                } else if (!Validation.isSpecailCharacter(inputBean.getReMarks().trim())) {
                    message = MessageVarList.ONUSBINRANGE_INVALID_REMARKS;
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return message;
    }
    
    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called OnUsBinRangeAction : ViewPopup");

        try {
            
             if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

            } else {
                result = "loginpage";
            }

        } catch (Exception e) {
            addActionError("OnUsBinRangeAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(OnUsBinRangeAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    
    public String add() {
        System.out.println("called OnUsBinRangeAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            OnUsBinRangeDAO dao = new OnUsBinRangeDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.ONUSBINRANGE_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "On us BIN range added", null);
                message = dao.insertOnUsBinRange(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("On Us BIN Range " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("OnUsBinRangeAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(OnUsBinRangeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String delete() {
        System.out.println("called OnUsBinRangeAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            OnUsBinRangeDAO dao = new OnUsBinRangeDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.ONUSBINRANGE_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "On us BIN range ID "+inputBean.getIpgonusbinrangeId()+" deleted", null);
            message = dao.deleteOnUsBinRange(inputBean, audit);
            if (message.isEmpty()) {
                message = "On Us BIN Range " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(OnUsBinRangeAction.class.getName()).log(Level.SEVERE, null, e);
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }
    
    public String detail() {

        System.out.println("called OnUsBinRangeAction: detail");
        Ipgonusbinrange ipgonusbinrange = new Ipgonusbinrange();

        try {
            if (inputBean.getIpgonusbinrangeId() != null && !inputBean.getIpgonusbinrangeId().isEmpty()) {

                CommonDAO comdao = new CommonDAO();
                inputBean.setStatusList(comdao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                
                OnUsBinRangeDAO dao = new OnUsBinRangeDAO();

                ipgonusbinrange = dao.findCurrencyByID(inputBean.getIpgonusbinrangeId());

                inputBean.setIpgonusbinrangeId(ipgonusbinrange.getIpgonusbinrangeid().toString());
                inputBean.setValue_1(ipgonusbinrange.getValue1());
                inputBean.setValue_2(ipgonusbinrange.getValue2());
                inputBean.setReMarks(ipgonusbinrange.getRemarks());
                inputBean.setStatus(ipgonusbinrange.getIpgstatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty On-Us-BIN-Range");
            }
        } catch (Exception e) {
            addActionError("OnUsBinRangeAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(OnUsBinRangeAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    }

    public String update() {

        System.out.println("called OnUsBinRangeAction : update");
        String retType = "message";


        try {
            if (inputBean.getIpgonusbinrangeId() != null && !inputBean.getIpgonusbinrangeId().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    OnUsBinRangeDAO dao = new OnUsBinRangeDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.ONUSBINRANGE_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "On us BIN range ID "+inputBean.getIpgonusbinrangeId()+" updated", null);
                    message = dao.updateOnUsBinRange(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("On Us BIN Range " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(OnUsBinRangeAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("On Us BIN Range " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }
}
