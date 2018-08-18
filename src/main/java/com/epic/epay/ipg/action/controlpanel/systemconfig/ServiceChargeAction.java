/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.ServiceChargeBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.ServiceChargeInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.ServiceChargeDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgservicecharge;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
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
public class ServiceChargeAction extends ActionSupport implements
        ModelDriven<Object>, AccessControlService {

    ServiceChargeInputBean inputBean = new ServiceChargeInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called ServiceChargeAction : execute");
        return SUCCESS;
    }

    public String view() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setChargeList(dao.getServiceChargeTypeList());
                inputBean.setCurrencyList(dao.getDefaultCurrencyList());

            } else {
                result = "loginpage";
            }

            System.out.println("called ServiceChargeAction :view");

        } catch (Exception ex) {
            addActionError("ServiceCharge " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(ServiceChargeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called ServiceChargeAction : ViewPopup");

        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setChargeList(dao.getServiceChargeTypeList());
                inputBean.setCurrencyList(dao.getDefaultCurrencyList());

            } else {
                result = "loginpage";
            }

        } catch (Exception e) {
            addActionError("ServiceChargeAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(ServiceChargeAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    
    public String add() {
        System.out.println("called ServiceChargeAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            ServiceChargeDAO dao = new ServiceChargeDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {
                String serviceChargeId = dao.getNextServiceChargeId();
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.SERVICE_CHARGE_MGT_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Service charge added", null);
                message = dao.insertServiceCharge(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("ServiceCharge " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("ServiceCharge " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(ServiceChargeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public String update() {

        System.out.println("called ServiceChargeAction : update");
        String retType = "message";

        try {
            if (inputBean.getServicechargecode() != null && !inputBean.getServicechargecode().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    ServiceChargeDAO dao = new ServiceChargeDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request,
                            TaskVarList.UPDATE_TASK,
                            PageVarList.SERVICE_CHARGE_MGT_PAGE,
                            SectionVarList.SYSTEMCONFIGMANAGEMENT, "Service charge ID "+inputBean.getServicechargeid()+" updated",
                            null);
                    message = dao.updateServiceCharge(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("ServiceCharge " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ServiceChargeAction.class.getName()).log(Level.SEVERE,
                    null, ex);
            addActionError("ServiceCharge " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String delete() {
        System.out.println("called ServiceChargeAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            ServiceChargeDAO dao = new ServiceChargeDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.SERVICE_CHARGE_MGT_PAGE,
                    SectionVarList.SYSTEMCONFIGMANAGEMENT, "Service charge ID"+inputBean.getServicechargeid()+" deleted", null);
            message = dao.deleteServiceCharge(inputBean, audit);
            if (message.isEmpty()) {
                message = "ServiceCharge " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(ServiceChargeAction.class.getName()).log(Level.SEVERE, null, e);
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String list() {

        System.out.println("called ServiceChargeAction: List");
        try {



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

            ServiceChargeDAO dao = new ServiceChargeDAO();
            List<ServiceChargeBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            /**
             * for audit search
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("[")
                        .append(checkEmptyorNullString("Service Charge Type", inputBean.getChargetype()))
                        .append(checkEmptyorNullString("Description", inputBean.getDescription()))
                        .append(checkEmptyorNullString("Value", inputBean.getValue()))
                        .append(checkEmptyorNullString("Rate ", inputBean.getRate()))
                        .append(checkEmptyorNullString("Currency", inputBean.getCurrency()))
                        .append("]");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.SERVICE_CHARGE_MGT_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Service charge search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(ServiceChargeAction.class.getName()).log(Level.SEVERE,
                    null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " ServiceCharge");
        }
        return "list";
    }
    
    public String detail() {

        System.out.println("called ServiceChargeAction: detail");
        Ipgservicecharge servicecharge;
        
        try {
            if (inputBean.getServicechargecode() != null
                    && !inputBean.getServicechargecode().isEmpty()) {
                CommonDAO comdao = new CommonDAO();
                inputBean.setChargeList(comdao.getServiceChargeTypeList());
                inputBean.setCurrencyList(comdao.getDefaultCurrencyList());
                ServiceChargeDAO dao = new ServiceChargeDAO();
                servicecharge = dao.findServiceChargeById(inputBean.getServicechargecode());

                inputBean.setChargetype(servicecharge.getIpgservicechargetype().getServicechargetypecode());
                inputBean.setDescription(servicecharge.getDescription());
                inputBean.setValue(servicecharge.getValue());
                inputBean.setRate((servicecharge.getRate().toString()));
                inputBean.setCurrency(servicecharge.getIpgcurrency().getCurrencyisocode());

            } else {
                inputBean.setMessage("Empty servicecharge code");
            }

        } catch (Exception e) {
            addActionError("ServiceChargeAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(ServiceChargeAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    }

    public String find() {
        System.out.println("called ServiceChargeAction: find");
        Ipgservicecharge servicecharge;
        try {
            if (inputBean.getServicechargecode() != null
                    && !inputBean.getServicechargecode().isEmpty()) {

                ServiceChargeDAO dao = new ServiceChargeDAO();

                servicecharge = dao.findServiceChargeById(inputBean.getServicechargecode());

                inputBean.setChargetype(servicecharge.getIpgservicechargetype().getServicechargetypecode());
                inputBean.setDescription(servicecharge.getDescription());
                inputBean.setValue(servicecharge.getValue());
                inputBean.setRate((servicecharge.getRate().toString()));
                inputBean.setCurrency(servicecharge.getIpgcurrency().getCurrencyisocode());

            } else {
                inputBean.setMessage("Empty servicecharge code");
            }
        } catch (Exception ex) {
            inputBean.setMessage("ServiceCharge " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(ServiceChargeAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return "find";

    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(
                PageVarList.SERVICE_CHARGE_MGT_PAGE, request);

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

        if (inputBean.getChargetype() == null
                || inputBean.getChargetype().trim().isEmpty()) {
            message = MessageVarList.SERVICE_CHARGE_MGT_EMPTY_SERVICE_CHARGE_TYPE;
        } else if (inputBean.getDescription() == null
                || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.SERVICE_CHARGE_MGT_EMPTY_DESCRIPTION;
        } else if (inputBean.getValue() == null
                || inputBean.getValue().trim().isEmpty()) {
            message = MessageVarList.SERVICE_CHARGE_MGT_EMPTY_SERVICE_CHARGE_VALUE;
        } else if (inputBean.getRate() == null
                || inputBean.getRate().trim().isEmpty()) {
            message = MessageVarList.SERVICE_CHARGE_MGT_EMPTY_SERVICE_CHARGE_RATE;
        } else if (inputBean.getCurrency() != null
                && inputBean.getCurrency().isEmpty()) {
            message = MessageVarList.SERVICE_CHARGE_MGT_EMPTY_CURRENCY;
        } else {
            if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
                message = MessageVarList.SERVICE_CHARGE_MGT_ERROR_DESC_INVALID;
            } else if (!Validation.isNumeric(inputBean.getValue())) {
                message = MessageVarList.SERVICE_CHARGE_MGT_ERROR_VALUE_INVALID;
            } else if (!Validation.isNumeric(inputBean.getRate())) {
                message = MessageVarList.SERVICE_CHARGE_MGT_ERROR_RATE_INVALID;
            }

        }

        return message;

    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.SERVICE_CHARGE_MGT_PAGE;
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
            status = new Common().checkMethodAccess(task, page, userRole,
                    request);
        }
        return status;
    }
}
