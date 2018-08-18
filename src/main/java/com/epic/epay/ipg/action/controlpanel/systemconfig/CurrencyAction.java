/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.systemconfig;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.CurrencyBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.CurrencyInputBean;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.CurrencyDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgcurrency;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.OracleMessage;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @created on :Oct 31, 2013, 10:04:43 AM
 * @author :thushanth
 */
public class CurrencyAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    CurrencyInputBean inputBean = new CurrencyInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() throws Exception {
        System.out.println("called CurrrencyAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.CURRENCY_PAGE;
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
//		                CommonDAO dao = new CommonDAO();
            } else {
                result = "loginpage";
            }

            System.out.println("called CurrrencyAction :view");

        } catch (Exception ex) {
            addActionError("Currency Management " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CurrencyAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String find() {
        System.out.println("called CurrrencyAction: Find");

        Ipgcurrency ipgcommonconfig = new Ipgcurrency();

        try {
            if (inputBean.getCurrencyISOCode() != null && !inputBean.getCurrencyISOCode().isEmpty()) {

                CurrencyDAO dao = new CurrencyDAO();

                ipgcommonconfig = dao.findCurrencyByCode(inputBean.getCurrencyISOCode());

                inputBean.setCurrencyISOCode(ipgcommonconfig.getCurrencyisocode());
                inputBean.setCurrencyCode(ipgcommonconfig.getCurrencycode());
                inputBean.setDescription(ipgcommonconfig.getDescription());
                inputBean.setSortKey(ipgcommonconfig.getSortkey().toString());

            } else {
                inputBean.setMessage("Empty Currency ISO Code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Currency " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CurrencyAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.CURRENCY_PAGE, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
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
        System.out.println("called CurrrencyAction: List");
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
            CurrencyDAO dao = new CurrencyDAO();
            List<CurrencyBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("[")
                        .append(checkEmptyorNullString("Currency ISO Code", inputBean.getCurrencyISOCode()))
                        .append(checkEmptyorNullString("Currency Code", inputBean.getCurrencyCode()))
                        .append(checkEmptyorNullString("Sort Key", inputBean.getSortKey()))
                        .append(checkEmptyorNullString("Description", inputBean.getDescription()))
                        .append("]");

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.CURRENCY_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Currency search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(CurrencyAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Currency");
        }
        return "list";
    }

    private String validateInputs() throws Exception {

        String message = "";
        try {
            if (inputBean.getCurrencyISOCode() == null || inputBean.getCurrencyISOCode().trim().isEmpty()) {
                message = MessageVarList.CURRENCY_EMPTY_ISO_CODE;
            } else if (inputBean.getCurrencyCode() == null || inputBean.getCurrencyCode().trim().isEmpty()) {
                message = MessageVarList.CURRENCY_EMPTY_CODE;
            } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
                message = MessageVarList.CURRENCY_EMPTY_DES;
            } else if (inputBean.getSortKey() == null || inputBean.getSortKey().trim().isEmpty()) {
                message = MessageVarList.CURRENCY_EMPTY_SORTKEY;
            } else {
                if (!Validation.isNumeric(inputBean.getCurrencyISOCode().trim())) {
                    message = MessageVarList.CURRENCY_INVALID_ISO_CODE;
                } else if (inputBean.getCurrencyISOCode().trim().length() > 3) {
                    message = MessageVarList.CURRENCY_INVALID_ISO_CODE;
                } else if (!Validation.isSpecailCharacter(inputBean.getCurrencyCode().trim()) || Validation.isithaveNumber(inputBean.getCurrencyCode().trim())) {
                    message = MessageVarList.CURRENCY_INVALID_CODE;
                } else if (inputBean.getCurrencyCode().trim().length() != 3) {
                    message = MessageVarList.CURRENCY_INVALID_CODE;
                } else if (!Validation.isSpecailCharacter(inputBean.getDescription().trim())) {
                    message = MessageVarList.CURRENCY_INVLID_DES;
                } else if (!Validation.isNumeric(inputBean.getSortKey().trim())) {
                    message = MessageVarList.CURRENCY_INVLID_SORTKEY;
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called CurrencyAction : ViewPopup");

        try {
            if (this.applyUserPrivileges()) {
//		                CommonDAO dao = new CommonDAO();
            } else {
                result = "loginpage";
            }
        } catch (Exception e) {
            addActionError("CurrencyAction" + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CurrencyAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String add() {
        System.out.println("called CurrrencyAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            CurrencyDAO dao = new CurrencyDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.CURRENCY_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Currency ISO code " + inputBean.getCurrencyISOCode()+ " added", null);
                message = dao.insertCurrency(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Currency " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Currency Management " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CurrencyAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String delete() {
        System.out.println("called CurrencyAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            CurrencyDAO dao = new CurrencyDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.CURRENCY_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Currency ISO code " + inputBean.getCurrencyISOCode()+ " deleted", null);
            message = dao.deleteCurrency(inputBean, audit);
            if (message.isEmpty()) {
                message = "Currency " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(CurrencyAction.class.getName()).log(Level.SEVERE, null, e);
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String detail() {

        System.out.println("called CurrencyAction: detail");
        Ipgcurrency ipgcommonconfig = new Ipgcurrency();

        try {
            if (inputBean.getCurrencyISOCode() != null && !inputBean.getCurrencyISOCode().isEmpty()) {

                CurrencyDAO dao = new CurrencyDAO();

                ipgcommonconfig = dao.findCurrencyByCode(inputBean.getCurrencyISOCode());

                inputBean.setCurrencyISOCode(ipgcommonconfig.getCurrencyisocode());
                inputBean.setCurrencyCode(ipgcommonconfig.getCurrencycode());
                inputBean.setDescription(ipgcommonconfig.getDescription());
                inputBean.setSortKey(ipgcommonconfig.getSortkey().toString());

            } else {
                inputBean.setMessage("Empty Currency ISO Code.");
            }

        } catch (Exception e) {
            addActionError("CurrencyAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CurrencyAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    }

    public String update() {

        System.out.println("called CurrencyAction : update");
        String retType = "message";

        try {
            if (inputBean.getCurrencyISOCode() != null && !inputBean.getCurrencyISOCode().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    CurrencyDAO dao = new CurrencyDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.CURRENCY_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Currency ISO code " + inputBean.getCurrencyISOCode()+ " updated", null);
                    message = dao.updateCurrency(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Currency " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CurrencyAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Common Configuration " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }
}
