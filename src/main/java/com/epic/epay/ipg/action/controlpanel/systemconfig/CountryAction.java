/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.systemconfig;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.CountryDataBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.CountryInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.CountryDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.RuleDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgcountry;
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
import java.util.ArrayList;

/**
 * @author Asitha Liyanawaduge
 *
 * 31/10/2013
 */
public class CountryAction extends ActionSupport implements
        ModelDriven<Object>, AccessControlService {

    /**
     *
     */
    CountryInputBean inputBean = new CountryInputBean();
    private static final long serialVersionUID = -3211133396359251628L;

    public String view() {

        String result = "view";
        try {
            if (!this.applyUserPrivileges()) {
                result = "loginpage";
            }

            System.out.println("called CountryAction :view");

        } catch (Exception ex) {
            addActionError("Country Action "
                    + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CountryAction.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return result;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(
                PageVarList.COUNTRY_PAGE, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                }else if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
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
        System.out.println("called CountryAction: list");
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

            CountryDAO dao = new CountryDAO();
            List<CountryDataBean> dataList = dao.getSearchList(
                    inputBean, rows, from, sortIndex, sortOrder);
            
            /**
             * for audit search
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("[")
                        .append(checkEmptyorNullString("Country Code ", inputBean.getCountryCode()))
                        .append(checkEmptyorNullString("Sort Key ", inputBean.getSortKey()))
                        .append(checkEmptyorNullString("Description", inputBean.getDescription()))
                        .append(checkEmptyorNullString("Country ISO Code", inputBean.getCountryisocode()))
                        .append("]");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.COUNTRY_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Country search using " + searchParameters + " parameters ", null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);
            }
            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullcount();
                inputBean.setRecords(dataList.get(0).getFullcount());
                inputBean.setGridModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);
            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }

        } catch (Exception e) {
            Logger.getLogger(CountryAction.class.getName()).log(
                    Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS
                    + " Country Action");
        }
        return "list";
    }

    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called CountryAction : ViewPopup");

        try {
             if (!this.applyUserPrivileges()) {
                result = "loginpage";
            }
        } catch (Exception e) {
            addActionError("CountryAction" + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CountryAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    
    public String add() {
        System.out.println("called CountryAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            CountryDAO dao = new CountryDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request,
                        TaskVarList.ADD_TASK, PageVarList.COUNTRY_PAGE,
                        SectionVarList.SYSTEMCONFIGMANAGEMENT, "Country code "+inputBean.getCountryCode()+" added", null);
                message = dao.insertCountry(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Country "
                            + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Country " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CountryAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return result;
    }

    public String find() {
        System.out.println("called CountryAction: Find");
        Ipgcountry country = null;
        try {
            if (inputBean.getCountryCode() != null
                    && !inputBean.getCountryCode().isEmpty()) {

                CountryDAO dao = new CountryDAO();

                country = dao.findCountryById(inputBean.getCountryCode());

                inputBean.setDescription(country.getDescription());
                inputBean.setSortKey(country.getSortkey().toString());
                inputBean.setCountryisocode(country.getCountryisocode());

            } else {
                inputBean.setMessage("Empty Country code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Country "
                    + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CountryAction.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String detail() {

        System.out.println("called CountryAction: detail");
        Ipgcountry country = null;
        try {
            if (inputBean.getCountryCode() != null
                    && !inputBean.getCountryCode().isEmpty()) {

                CountryDAO dao = new CountryDAO();

                country = dao.findCountryById(inputBean.getCountryCode());

                inputBean.setDescription(country.getDescription());
                inputBean.setSortKey(country.getSortkey().toString());
                inputBean.setCountryisocode(country.getCountryisocode());

            } else {
                inputBean.setMessage("Empty Country code.");
            }

        } catch (Exception e) {
            addActionError("CountryAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CountryAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    }
    
    public String update() {

        System.out.println("called CountryAction : update");
        String retType = "message";

        try {
            if (inputBean.getCountryCode() != null && !inputBean.getCountryCode().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    CountryDAO dao = new CountryDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.COUNTRY_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Country code "+inputBean.getCountryCode()+" updated", null);
                    message = dao.updateCountry(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Country " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CountryAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Country " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String delete() {
        System.out.println("called CountryAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            CountryDAO dao = new CountryDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.COUNTRY_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Country code "+inputBean.getCountryCode()+" deleted", null);
            message = dao.deleteCountry(inputBean, audit);
            if (message.isEmpty()) {
                message = "Country " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(CountryAction.class.getName()).log(Level.SEVERE, null, e);
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
        if (inputBean.getCountryCode() == null
                || inputBean.getCountryCode().trim().isEmpty()) {
            message = MessageVarList.COUNTRY_CODE_EMPTY;
        } else if (!Validation.isSpecailCharacter(inputBean.getCountryCode())) {
            message = MessageVarList.INVALID_COUNTRY_CODE;
        } else if (inputBean.getDescription() == null
                || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.COUNTRY_DESCRIPTION_EMPTY;
        } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
            message = MessageVarList.INVALID_COUNTRY_DESCRIPTION;
        } else if (inputBean.getSortKey() == null
                || inputBean.getSortKey().trim().isEmpty()) {
            message = MessageVarList.COUNTRY_SORTKEY_EMPTY;
        } else if (!Validation.isNumeric(inputBean.getSortKey())) {
            message = MessageVarList.INVALID_COUNTRY_SORTKEY;
        } else if (inputBean.getCountryisocode()== null
                || inputBean.getCountryisocode().trim().isEmpty()) {
            message = MessageVarList.COUNTRY_ISOCODE_EMPTY;
        }
        return message;
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
        String page = PageVarList.COUNTRY_PAGE;
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
