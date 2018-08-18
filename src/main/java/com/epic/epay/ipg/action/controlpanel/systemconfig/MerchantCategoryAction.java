/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.systemconfig;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.epic.epay.ipg.action.controlpanel.usermanagement.PageAction;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.MerchantCategoryBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.MerchantCategoryInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.MerchantCategoryDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgmerchantcategory;
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
 * @author Asitha Liyanawaduge
 *
 * 30/10/2013
 */
public class MerchantCategoryAction extends ActionSupport implements
        ModelDriven<Object>, AccessControlService {

    /**
     *
     */
    private static final long serialVersionUID = -1484467380485621897L;
    MerchantCategoryInputBean inputBean = new MerchantCategoryInputBean();

    public String view() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean
                        .setStatusList(dao
                        .getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

            } else {
                result = "loginpage";
            }

            System.out.println("called MerchantCategoryAction :view");

        } catch (Exception ex) {
            addActionError("Merchant Category "
                    + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantCategoryAction.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return result;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(
                PageVarList.MERCHANT_CATEGORY_PAGE, request);

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

    public String list() {
        System.out.println("called MerchantCategoryAction: list");
        try {

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;

            String sortIndex = "";
            String sortOrder = "";

            if (!inputBean.getSidx().isEmpty()) {
                sortIndex = inputBean.getSidx();
                sortOrder = inputBean.getSord();
            }

            MerchantCategoryDAO mccDao = new MerchantCategoryDAO();
            List<MerchantCategoryBean> dataList = mccDao.getSearchList(
                    inputBean, rows, from, sortIndex, sortOrder);
            /**
             * for audit search
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("[")
                        .append(checkEmptyorNullString("Merchant Category Code", inputBean.getMerchantCategoryCode()))
                        .append(checkEmptyorNullString("Status", inputBean.getStatus()))
                        .append(checkEmptyorNullString("Description", inputBean.getDescription()))
                        .append("]");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.MERCHANT_CATEGORY_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Merchant Category search using " + searchParameters + " parameters ", null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);
            }
            
            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                inputBean.setRecords(dataList.get(0).getFullCount());
                inputBean.setGridModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);
            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }

        } catch (Exception e) {
            Logger.getLogger(MerchantCategoryAction.class.getName()).log(
                    Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS
                    + " Merchant Category");
        }
        return "list";
    }

    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called MerchantCategoryAction : ViewPopup");

        try {
             if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

            } else {
                result = "loginpage";
            }
        } catch (Exception e) {
            addActionError("MerchantCategoryAction" + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantCategoryAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    
    public String add() {
        System.out.println("called MerchantCategoryAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            MerchantCategoryDAO mccDao = new MerchantCategoryDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request,
                        TaskVarList.ADD_TASK, PageVarList.MERCHANT_CATEGORY_PAGE,
                        SectionVarList.SYSTEMCONFIGMANAGEMENT, "Merchant category code "+inputBean.getMerchantCategoryCode()+" added", null);
                message = mccDao.insertMerchantCategory(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Merchant Category "
                            + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Merchant Category " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return result;
    }
    
    public String detail() {

        System.out.println("called MerchantCategoryAction: detail");
        Ipgmerchantcategory imc = null;
        try {
            if (inputBean.getMerchantCategoryCode() != null
                    && !inputBean.getMerchantCategoryCode().isEmpty()) {
                
                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

                MerchantCategoryDAO mccDao = new MerchantCategoryDAO();
                imc = mccDao.findMCById(inputBean.getMerchantCategoryCode());

                inputBean.setDescription(imc.getDescription());
                inputBean.setStatus(imc.getIpgstatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty merchant category code.");
            }

        } catch (Exception e) {
            addActionError("MerchantCategoryAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantCategoryAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    }

    public String update() {

        System.out.println("called MerchantCategoryAction : update");
        String retType = "message";

        try {
            if (inputBean.getMerchantCategoryCode() != null && !inputBean.getMerchantCategoryCode().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    MerchantCategoryDAO mccDao = new MerchantCategoryDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.MERCHANT_CATEGORY_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Merchant category code "+inputBean.getMerchantCategoryCode()+" updated", null);
                    message = mccDao.updateMerchantCategory(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Merchant Category " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MerchantCategoryAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Merchant Category " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String delete() {
        System.out.println("called MerchantCategoryAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            MerchantCategoryDAO mccDao = new MerchantCategoryDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.MERCHANT_CATEGORY_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Merchant category code "+inputBean.getMerchantCategoryCode()+" deleted", null);
            message = mccDao.deleteMerchantCategory(inputBean, audit);
            if (message.isEmpty()) {
                message = "Merchant Category " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(MerchantCategoryAction.class.getName()).log(Level.SEVERE, null, e);
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    /**
     * @return
     */
    private String validateInputs() {
        String message = "";
        if (inputBean.getMerchantCategoryCode() == null
                || inputBean.getMerchantCategoryCode().trim().isEmpty()) {
            message = MessageVarList.MERCHANT_CATEGORY_CODE_EMPTY;
        } else if (!Validation.isSpecailCharacter(inputBean.getMerchantCategoryCode())) {
            message = MessageVarList.INVALID_MERCHANT_CATEGORY_CODE;
        } else if (inputBean.getDescription() == null
                || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.MERCHANT_CATEGORY_EMPTY_DESCRIPTION;
        } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
            message = MessageVarList.INVALID_MERCHANT_CATEGORY_DESCRIPTION;
        } else if (inputBean.getStatus() != null
                && inputBean.getStatus().isEmpty()) {
            message = MessageVarList.EMPTY_MERCHANT_CATEGORY_STATUS;
        }
        return message;
    }

    public String find() {
        System.out.println("called MerchantCategoryAction: Find");
        Ipgmerchantcategory imc = null;
        try {
            if (inputBean.getMerchantCategoryCode() != null
                    && !inputBean.getMerchantCategoryCode().isEmpty()) {

                MerchantCategoryDAO mccDao = new MerchantCategoryDAO();

                imc = mccDao.findMCById(inputBean.getMerchantCategoryCode());

                inputBean.setDescription(imc.getDescription());
                inputBean.setStatus(imc.getIpgstatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty merchant category code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Merchant Category "
                    + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantCategoryAction.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        return "find";

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.epic.epay.ipg.util.common.AccessControlService#checkAccess(java.lang
     * .String, java.lang.String)
     */
    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.MERCHANT_CATEGORY_PAGE;
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

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.ModelDriven#getModel()
     */
    public Object getModel() {
        // TODO Auto-generated method stub
        return inputBean;
    }
}
