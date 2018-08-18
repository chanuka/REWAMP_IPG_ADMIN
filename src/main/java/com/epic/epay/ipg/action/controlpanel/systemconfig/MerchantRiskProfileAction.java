/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.MerchantRiskProfileBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.MerchantRiskProfileInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.MerchantRiskProfileDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgriskprofile;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author chalitha
 */
public class MerchantRiskProfileAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    MerchantRiskProfileInputBean inputBean = new MerchantRiskProfileInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called MerchantRiskProfileAction : execute");
        return SUCCESS;
    }

    public String view() {
        String result = "view";
        try {
            if (this.applyUserPrivileges()) {
                  CommonDAO commonDAO = new CommonDAO();
                  inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
            }else{
                result = "loginpage";
            }

            System.out.println("called MerchantRiskProfileAction :view");

        } catch (Exception ex) {
            addActionError("MerchantRiskProfile " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantRiskProfileAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String list() {

        System.out.println("called MerchantRiskProfileAction : List");
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

            MerchantRiskProfileDAO dao = new MerchantRiskProfileDAO();
            List<MerchantRiskProfileBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            /**
             * for audit search
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("Bin search using [")
                        .append(checkEmptyorNullString("Risk Profile Code", inputBean.getRiskprofilecode()))
                        .append(checkEmptyorNullString("Description ", inputBean.getDescription()))
                        .append(checkEmptyorNullString("Status ", inputBean.getStatus()))
                        .append(checkEmptyorNullString("Maximum Single Txn Limit", inputBean.getMaxsingletxnlimit()))
                        .append(checkEmptyorNullString("Minimum Single Txn Limit", inputBean.getMinsingletxnlimit()))
                        .append(checkEmptyorNullString("Maximum Txn Count", inputBean.getMaxtxncount()))
                        .append(checkEmptyorNullString("Maximum Total Txn Amoun", inputBean.getMaxtxnamount()))
                        .append(checkEmptyorNullString("Maximum Password Count", inputBean.getMaxpasswordcount()))
                        .append("] parameters ");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.MERCHANT_RISK_PROFILE_MGT_PAGE, SectionVarList.MERCHANTMANAGEMENT, searchParameters.toString(), null);
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
            Logger.getLogger(MerchantRiskProfileAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " MerchantRiskProfile");
        }
        return "list";
    }

    public String update() {

        System.out.println("called MerchantRiskProfileAction : update");
        String retType = "message";

        try {
            if (inputBean.getRiskprofilecode() != null
                    && !inputBean.getRiskprofilecode().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    MerchantRiskProfileDAO dao = new MerchantRiskProfileDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request,
                            TaskVarList.UPDATE_TASK,
                            PageVarList.MERCHANT_RISK_PROFILE_MGT_PAGE,
                            SectionVarList.SYSTEMCONFIGMANAGEMENT, "Merchant risk profile code "+inputBean.getRiskprofilecode()+" updated",
                            null);
                    message = dao.updateMerchantRiskProfile(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Merchant risk profile " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MerchantRiskProfileAction.class.getName()).log(Level.SEVERE,
                    null, ex);
            addActionError("MerchantRiskProfile " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String add() {
        System.out.println("called MerchantRiskProfileAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            MerchantRiskProfileDAO dao = new MerchantRiskProfileDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request,
                        TaskVarList.ADD_TASK, PageVarList.MERCHANT_RISK_PROFILE_MGT_PAGE,
                        SectionVarList.SYSTEMCONFIGMANAGEMENT, "Merchant risk profile code "+inputBean.getRiskprofilecode()+" added", null);
                message = dao.insertMerchantRiskProfile(inputBean, audit);
                if (message.isEmpty()) {
                    addActionMessage("Merchant risk profile " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("MerchantRiskProfile " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantRiskProfileAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return result;
    }

    public String delete() {
        System.out.println("called MerchantRiskProfileAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            MerchantRiskProfileDAO dao = new MerchantRiskProfileDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request,
                    TaskVarList.DELETE_TASK, PageVarList.MERCHANT_RISK_PROFILE_MGT_PAGE,
                    SectionVarList.SYSTEMCONFIGMANAGEMENT, "Merchant risk profile code "+inputBean.getRiskprofilecode()+" deleted", null);
            message = dao.deleteMerchantRiskProfile(inputBean, audit);
            if (message.isEmpty()) {
                message = "Merchant risk profile " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(MerchantRiskProfileAction.class.getName()).log(Level.SEVERE, null, e);
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String find() {
        System.out.println("called MerchantRiskProfileAction: Find");
        Ipgriskprofile riskprofile;
        String riskprofilecode = null;
        try {
            if ((riskprofilecode = inputBean.getRiskprofilecode()) != null
                    && !inputBean.getRiskprofilecode().isEmpty() || (ServletActionContext.getRequest().getSession(false).getAttribute("Riskprofilecode")) != null) {

                //String riskcodefromsession = (String) ServletActionContext.getRequest().getSession(false).getAttribute("Riskprofilecode");
                if (riskprofilecode == null) {
                    riskprofilecode = (String) ServletActionContext.getRequest().getSession(false).getAttribute("Riskprofilecode");
                    ServletActionContext.getRequest().getSession(false).removeAttribute("Riskprofilecode");
                }
                ServletActionContext.getRequest().getSession(false).setAttribute("Riskprofilecode", riskprofilecode);
                MerchantRiskProfileDAO dao = new MerchantRiskProfileDAO();
                riskprofile = dao.findMerchantRiskProfileById(riskprofilecode);
                CommonDAO commonDAO = new CommonDAO();
                inputBean.setRiskprofilecode(riskprofilecode);
//                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setRiskprofilecode(riskprofile.getRiskprofilecode());
                inputBean.setStatus(riskprofile.getIpgstatus().getStatuscode());
                inputBean.setMinsingletxnlimit(riskprofile.getMinimumsingletxnlimit().toString());
                inputBean.setMaxtxncount(riskprofile.getMaximumtxncount().toString());
                inputBean.setMaxpasswordcount(riskprofile.getMaximumpasswordcount().toString());
                inputBean.setMaxsingletxnlimit(riskprofile.getMaximumsingletxnlimit().toString());
                inputBean.setMinsingletxnlimit(riskprofile.getMinimumsingletxnlimit().toString());
                inputBean.setMaxtxnamount(riskprofile.getMaximumtotaltxnamount().toString());
                inputBean.setDescription(riskprofile.getDescription());
                Map<String, String> blockedbin = dao.findblockedbin(riskprofilecode);
                Map<String, String> blockedcountry = dao.findblockedcountry(riskprofilecode);
                Map<String, String> blockedcurrency = dao.findblockedcurrency(riskprofilecode);
                Map<String, String> blockedmcc = dao.findblockedmcc(riskprofilecode);
                inputBean.setBlockedbinList(blockedbin);
                inputBean.setBlockedcountryList(blockedcountry);
                inputBean.setBlockedcurrencytypesList(blockedcurrency);
                inputBean.setBlockedmerchantcategoriesList(blockedmcc);

                Map<String, String> AllowedBinList = dao.getDefaultBinList();
                for (String key : blockedbin.keySet()) {
                    if (AllowedBinList.containsKey(key)) {
                        AllowedBinList.remove(key);
                    }
                }
                Map<String, String> AllowedCountryList = dao.getDefultCountryList();
                for (String key : blockedcountry.keySet()) {
                    if (AllowedCountryList.containsKey(key)) {
                        AllowedCountryList.remove(key);
                    }
                }

                Map<String, String> AllowedCurrencyList = dao.getDefaultCurrencyList();
                for (String key : blockedcurrency.keySet()) {
                    if (AllowedCurrencyList.containsKey(key)) {
                        AllowedCurrencyList.remove(key);
                    }
                }

                Map<String, String> AllowedMccList = dao.getDefultMerchantCategoriesList();
                for (String key : blockedmcc.keySet()) {
                    if (AllowedMccList.containsKey(key)) {
                        AllowedMccList.remove(key);
                    }
                }

                inputBean.setAllowedbinList(AllowedBinList);
                inputBean.setAllowedcountryList(AllowedCountryList);
                inputBean.setAllowedcurrencytypesList(AllowedCurrencyList);
                inputBean.setAllowedmerchantcategoriesList(AllowedMccList);



            } else {
                inputBean.setMessage("Empty Risk Profile code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("MerchantRiskProfile "
                    + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantRiskProfileAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return "find";

    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(
                PageVarList.MERCHANT_RISK_PROFILE_MGT_PAGE, request);

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
                } else if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                 }else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                } else if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                    inputBean.setVupdatelink(false);
                }
            }
        }
        inputBean.setVupdatebutt(true);

        return true;
    }

    public String viewpopup() {
        System.out.println("called MerchantRiskProfileAction :viewpopup");
        String result = "viewpopup";
        try {
            if (this.applyUserPrivileges()) {
                CommonDAO commonDAO = new CommonDAO();
                MerchantRiskProfileDAO dao = new MerchantRiskProfileDAO();
                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setAllowedcountryList(dao.getDefultCountryList());
                inputBean.setAllowedmerchantcategoriesList(dao.getDefultMerchantCategoriesList());
                inputBean.setAllowedcurrencytypesList(dao.getDefaultCurrencyList());
                inputBean.setAllowedbinList(dao.getDefaultBinList());
            }else{
                result = "loginpage";
            }
            

        } catch (Exception ex) {
            addActionError("MerchantRiskProfileAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantRiskProfileAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public String detail() {

        System.out.println("called MerchantRiskProfileAction: detail");
        Ipgriskprofile riskprofile;
        String riskprofilecode = null;
        try {
            if ((riskprofilecode = inputBean.getRiskprofilecode()) != null
                    && !inputBean.getRiskprofilecode().isEmpty() || (ServletActionContext.getRequest().getSession(false).getAttribute("Riskprofilecode")) != null) {

                //String riskcodefromsession = (String) ServletActionContext.getRequest().getSession(false).getAttribute("Riskprofilecode");
                if (riskprofilecode == null) {
                    riskprofilecode = (String) ServletActionContext.getRequest().getSession(false).getAttribute("Riskprofilecode");
                    ServletActionContext.getRequest().getSession(false).removeAttribute("Riskprofilecode");
                }
                ServletActionContext.getRequest().getSession(false).setAttribute("Riskprofilecode", riskprofilecode);
                MerchantRiskProfileDAO dao = new MerchantRiskProfileDAO();
                riskprofile = dao.findMerchantRiskProfileById(riskprofilecode);
                CommonDAO commonDAO = new CommonDAO();
                inputBean.setRiskprofilecode(riskprofilecode);
                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setRiskprofilecode(riskprofile.getRiskprofilecode());
                inputBean.setStatus(riskprofile.getIpgstatus().getStatuscode());
                inputBean.setMinsingletxnlimit(riskprofile.getMinimumsingletxnlimit().toString());
                inputBean.setMaxtxncount(riskprofile.getMaximumtxncount().toString());
                inputBean.setMaxpasswordcount(riskprofile.getMaximumpasswordcount().toString());
                inputBean.setMaxsingletxnlimit(riskprofile.getMaximumsingletxnlimit().toString());
                inputBean.setMinsingletxnlimit(riskprofile.getMinimumsingletxnlimit().toString());
                inputBean.setMaxtxnamount(riskprofile.getMaximumtotaltxnamount().toString());
                inputBean.setDescription(riskprofile.getDescription());
                Map<String, String> blockedbin = dao.findblockedbin(riskprofilecode);
                Map<String, String> blockedcountry = dao.findblockedcountry(riskprofilecode);
                Map<String, String> blockedcurrency = dao.findblockedcurrency(riskprofilecode);
                Map<String, String> blockedmcc = dao.findblockedmcc(riskprofilecode);
                inputBean.setBlockedbinList(blockedbin);
                inputBean.setBlockedcountryList(blockedcountry);
                inputBean.setBlockedcurrencytypesList(blockedcurrency);
                inputBean.setBlockedmerchantcategoriesList(blockedmcc);

                Map<String, String> AllowedBinList = dao.getDefaultBinList();
                for (String key : blockedbin.keySet()) {
                    if (AllowedBinList.containsKey(key)) {
                        AllowedBinList.remove(key);
                    }
                }
                Map<String, String> AllowedCountryList = dao.getDefultCountryList();
                for (String key : blockedcountry.keySet()) {
                    if (AllowedCountryList.containsKey(key)) {
                        AllowedCountryList.remove(key);
                    }
                }

                Map<String, String> AllowedCurrencyList = dao.getDefaultCurrencyList();
                for (String key : blockedcurrency.keySet()) {
                    if (AllowedCurrencyList.containsKey(key)) {
                        AllowedCurrencyList.remove(key);
                    }
                }

                Map<String, String> AllowedMccList = dao.getDefultMerchantCategoriesList();
                for (String key : blockedmcc.keySet()) {
                    if (AllowedMccList.containsKey(key)) {
                        AllowedMccList.remove(key);
                    }
                }

                inputBean.setAllowedbinList(AllowedBinList);
                inputBean.setAllowedcountryList(AllowedCountryList);
                inputBean.setAllowedcurrencytypesList(AllowedCurrencyList);
                inputBean.setAllowedmerchantcategoriesList(AllowedMccList);



            } else {
                inputBean.setMessage("Empty Risk Profile code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("MerchantRiskProfile "
                    + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantRiskProfileAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return "detail";

    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.MERCHANT_RISK_PROFILE_MGT_PAGE;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("list".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("add".equals(method)) {
            task = TaskVarList.ADD_TASK;
        } else if ("delete".equals(method)) {
            task = TaskVarList.DELETE_TASK;
        } else if ("find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("update".equals(method)) {
            task = TaskVarList.UPDATE_TASK;
        } else if ("viewpopup".equals(method)) {
            task = TaskVarList.VIEW_TASK;
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

    private String validateInputs() {
        String message = "";
        if (inputBean.getRiskprofilecode() == null || inputBean.getRiskprofilecode().trim().isEmpty()) {
            message = MessageVarList.RISK_PROFILE_MGT_EMPTY_RISK_PROFILE_CODE;
        } else if (inputBean.getDescription() == null
                || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.RISK_PROFILE_MGT_EMPTY_DESCRIPTION;
        } else if (inputBean.getStatus() == null
                || inputBean.getStatus().trim().isEmpty()) {
            message = MessageVarList.RISK_PROFILE_MGT_EMPTY_STATUS;
        } else if (inputBean.getMaxsingletxnlimit() == null
                || inputBean.getMaxsingletxnlimit().trim().isEmpty()) {
            message = MessageVarList.RISK_PROFILE_MGT_EMPTY_MAX_TXN_LIMIT;
        } else if (inputBean.getMinsingletxnlimit() == null
                || inputBean.getMinsingletxnlimit().trim().isEmpty()) {
            message = MessageVarList.RISK_PROFILE_MGT_EMPTY_MIN_TXN_LIMIT;
        } else if (inputBean.getMaxtxncount() == null
                || inputBean.getMaxtxncount().trim().isEmpty()) {
            message = MessageVarList.RISK_PROFILE_MGT_EMPTY_MAX_TXN_COUNT;
        } else if (inputBean.getMaxtxnamount() == null
                || inputBean.getMaxtxnamount().trim().isEmpty()) {
            message = MessageVarList.RISK_PROFILE_MGT_EMPTY_MAX_TXN_AMOUNT;
        } else if (inputBean.getMaxpasswordcount() == null
                || inputBean.getMaxpasswordcount().trim().isEmpty()) {
            message = MessageVarList.RISK_PROFILE_MGT_EMPTY_MAX_PASS_COUNT;
        } else {
            if (!Validation.isSpecailCharacter(inputBean.getRiskprofilecode())) {
                message = MessageVarList.RISK_PROFILE_MGT_ERROR_RISK_PROFILE_CODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
                message = MessageVarList.RISK_PROFILE_MGT_ERROR_DESC_INVALID;
            } else if (!Validation.isNumeric(inputBean.getMaxsingletxnlimit())) {
                message = MessageVarList.RISK_PROFILE_MGT_ERROR_MAX_TXN_LIMIT_INVALID;
            } else if (!Validation.isNumeric(inputBean.getMinsingletxnlimit())) {
                message = MessageVarList.RISK_PROFILE_MGT_ERROR_MIN_TXN_LIMIT_INVALID;
            } else if (!Validation.isNumeric(inputBean.getMaxtxncount())) {
                message = MessageVarList.RISK_PROFILE_MGT_ERROR_MAX_TXN_COUNT_INVALID;
            } else if (!Validation.isNumeric(inputBean.getMaxtxnamount())) {
                message = MessageVarList.RISK_PROFILE_MGT_ERROR_MAX_TXN_AMOUNT_INVALID;
            } else if (!Validation.isNumeric(inputBean.getMaxpasswordcount())) {
                message = MessageVarList.RULE_SCOPE_MGT_ERROR_MAX_PASS_COUNT_INVALID;
            } else if (Integer.parseInt(inputBean.getMinsingletxnlimit()) > Integer.parseInt(inputBean.getMaxsingletxnlimit())) {
                message = MessageVarList.RULE_SCOPE_MGT_ERROR_MIN_GREATER_THAN_MAX;
            }
        }
        return message;
    }
}
