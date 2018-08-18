/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.BinBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.BinInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.BinDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgbin;
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
public class BinAction extends ActionSupport implements
        ModelDriven<Object>, AccessControlService {

    BinInputBean inputBean = new BinInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called BinAction : execute");
        return SUCCESS;
    }

    public String view() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setCardassociationList(dao.getDefultCardAssociationList());
                inputBean.setOnusstatusList(dao.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));

            } else {
                result = "loginpage";
            }

            System.out.println("called BinAction :view");

        } catch (Exception ex) {
            addActionError("BIN " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(BinAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called BinAction : ViewPopup");

        try {
            
             if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setCardassociationList(dao.getDefultCardAssociationList());
                inputBean.setOnusstatusList(dao.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));
            } else {
                result = "loginpage";
            }

        } catch (Exception e) {
            addActionError("BinAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(BinAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    
    public String add() {
        System.out.println("called BinAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            BinDAO dao = new BinDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request,
                        TaskVarList.ADD_TASK, PageVarList.BIN_MGT_PAGE,
                        SectionVarList.SYSTEMCONFIGMANAGEMENT, "BIN number "+inputBean.getBincode()+" added", null);
                message = dao.insertBin(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("BIN "
                            + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Bin " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(BinAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return result;
    }
    
    public String detail() {

        System.out.println("called BinAction: detail");
        Ipgbin bin;
        
        try {
            if (inputBean.getBincode() != null
                    && !inputBean.getBincode().isEmpty()) {
                CommonDAO comdao = new CommonDAO();
                inputBean.setStatusList(comdao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setCardassociationList(comdao.getDefultCardAssociationList());
                inputBean.setOnusstatusList(comdao.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));
                BinDAO dao = new BinDAO();

                bin = dao.findBinById(inputBean.getBincode());

                inputBean.setBincode(bin.getBin());
                inputBean.setDescription(bin.getDescription());
                inputBean.setStatus(bin.getIpgstatusByStatus().getStatuscode());
                inputBean.setCardassociation((bin.getIpgcardassociation().getCardassociationcode()));
                inputBean.setOnusstatus(bin.getIpgstatusByOnusstatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty BIN code.");
            }

        } catch (Exception e) {
            addActionError("Card BinAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(BinAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    }

    public String update() {

        System.out.println("called BinAction : update");
        String retType = "message";

        try {
            if (inputBean.getBincode() != null
                    && !inputBean.getBincode().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    BinDAO dao = new BinDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request,
                            TaskVarList.UPDATE_TASK,
                            PageVarList.BIN_MGT_PAGE,
                            SectionVarList.SYSTEMCONFIGMANAGEMENT, "BIN number "+inputBean.getBincode()+" updated",
                            null);
                    message = dao.updateBin(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("BIN " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BinAction.class.getName()).log(Level.SEVERE,
                    null, ex);
            addActionError("BIN " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String delete() {
        System.out.println("called BinAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            BinDAO dao = new BinDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request,
                    TaskVarList.DELETE_TASK, PageVarList.BIN_MGT_PAGE,
                    SectionVarList.SYSTEMCONFIGMANAGEMENT, "BIN number "+inputBean.getBincode()+" deleted", null);
            message = dao.deleteBin(inputBean, audit);
            if (message.isEmpty()) {
                message = "BIN " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(BinAction.class.getName()).log(Level.SEVERE, null, e);
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String list() {

        System.out.println("called BinAction: List");
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

            BinDAO dao = new BinDAO();
            List<BinBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("[")
                        .append(checkEmptyorNullString("BIN Number", inputBean.getBincode()))
                        .append(checkEmptyorNullString("Description ", inputBean.getDescription()))
                        .append(checkEmptyorNullString("On Us Status", inputBean.getOnusstatus()))
                        .append(checkEmptyorNullString("Card Association", inputBean.getStatus()))
                        .append("]");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.BIN_MGT_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "BIN search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(BinAction.class.getName()).log(Level.SEVERE,
                    null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " BIN");
        }
        return "list";
    }

    public String find() {
        System.out.println("called BinAction: Find");
        Ipgbin bin;
        try {
            if (inputBean.getBincode() != null
                    && !inputBean.getBincode().isEmpty()) {

                BinDAO dao = new BinDAO();

                bin = dao.findBinById(inputBean.getBincode());

                inputBean.setBincode(bin.getBin());
                inputBean.setDescription(bin.getDescription());
                inputBean.setStatus(bin.getIpgstatusByStatus().getStatuscode());
                inputBean.setCardassociation((bin.getIpgcardassociation().getCardassociationcode()));
                inputBean.setOnusstatus(bin.getIpgstatusByOnusstatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty BIN code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("BIN "
                    + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(BinAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return "find";

    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(
                PageVarList.BIN_MGT_PAGE, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                } else if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
                    inputBean.setVdelete(false);
                }else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
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
        if (inputBean.getBincode() == null
                || inputBean.getBincode().trim().isEmpty()) {
            message = MessageVarList.BIN_MGT_EMPTY_BIN_CODE;
        } else if (inputBean.getDescription() == null
                || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.BIN_MGT_EMPTY_DESCRIPTION;
        } else if (inputBean.getOnusstatus() == null
                || inputBean.getOnusstatus().trim().isEmpty()) {
            message = MessageVarList.BIN_MGT_EMPTY_ONUS_STATUS;
        } else if (inputBean.getCardassociation() == null
                || inputBean.getCardassociation().trim().isEmpty()) {
            message = MessageVarList.BIN_MGT_EMPTY_CARD_ASSOCIATION;
        } else if (inputBean.getStatus() != null
                && inputBean.getStatus().isEmpty()) {
            message = MessageVarList.BIN_MGT_EMPTY_STATUS;
        } else {
            if (!Validation.isSpecailCharacter(inputBean.getBincode())) {
                message = MessageVarList.BIN_MGT_ERROR_BIN_CODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean
                    .getDescription())) {
                message = MessageVarList.BIN_MGT_ERROR_DESC_INVALID;
            }
        }
        return message;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.BIN_MGT_PAGE;
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
