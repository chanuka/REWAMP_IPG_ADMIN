/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.merchantmanagement;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantCredentialBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantCredentialInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.merchantmanagement.MerchantCredentialDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.PasswordPolicyDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgmerchantcredential;
import com.epic.epay.ipg.util.mapping.Ipgpasswordpolicy;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.OracleMessage;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.bouncycastle.util.encoders.Base64;

/**
 *
 * @created on : Nov 14, 2013, 10:26:02 AM
 * @author : thushanth
 *
 */
public class MerchantCredentialAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    MerchantCredentialInputBean inputBean = new MerchantCredentialInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() throws Exception {
        System.out.println("called MerchantCredentialAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.MERCHANT_CREDENTIAL;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("list".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("add".equals(method)) {
            task = TaskVarList.ADD_TASK;
        } else if ("viewpopup".equals(method)) {
            task = TaskVarList.VIEW_TASK;
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
                inputBean.setMerchantList(dao.getMerchantList());
                inputBean.setCardAssociationList(dao.getDefultCardAssociationList());

            } else {
                result = "loginpage";
            }

            System.out.println("called MerchantCredentialAction :view");

        } catch (Exception ex) {
            addActionError("MerchantCredentialAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantCredentialAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
     public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called MerchantCredentialAction : ViewPopup");

        try {
            
             if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
                inputBean.setMerchantList(dao.getMerchantList());
                inputBean.setCardAssociationList(dao.getDefultCardAssociationList());

            } else {
                result = "loginpage";
            }

        } catch (Exception e) {
            addActionError("MerchantCredentialAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantCredentialAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
     
    public String detail() {

        System.out.println("called MerchantCredentialAction: detail");
        Ipgmerchantcredential ipgmerchantservice = new Ipgmerchantcredential();

        try {
            if (inputBean.getMerchantID() != null && !inputBean.getMerchantID().isEmpty()) {

                CommonDAO comdao = new CommonDAO();
                inputBean.setMerchantList(comdao.getMerchantList());
                inputBean.setCardAssociationList(comdao.getDefultCardAssociationList());
                
                
                MerchantCredentialDAO dao = new MerchantCredentialDAO();

                ipgmerchantservice = dao.findMerchantChargeByID(inputBean.getMerchantID(), inputBean.getCardAssociation());

                inputBean.setMerchantID(ipgmerchantservice.getId().getMerchantid());
                inputBean.setMerchantID2(ipgmerchantservice.getId().getMerchantid());
                inputBean.setCardAssociation(ipgmerchantservice.getIpgcardassociation().getCardassociationcode());
                inputBean.setCardAssociation2(ipgmerchantservice.getIpgcardassociation().getCardassociationcode());
                inputBean.setUserName(ipgmerchantservice.getUsername());
                inputBean.setPassword("");
                inputBean.setRePassword("");

            } else {
                inputBean.setMessage("Empty Merchant ID");
            }

        } catch (Exception e) {
            addActionError("MerchantCredentialAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantCredentialAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    } 
    
    public String find() {
        System.out.println("called MerchantCredentialAction: Find");

        Ipgmerchantcredential ipgmerchantservice = new Ipgmerchantcredential();

        try {
            if (inputBean.getMerchantID() != null && !inputBean.getMerchantID().isEmpty()) {

                MerchantCredentialDAO dao = new MerchantCredentialDAO();

                ipgmerchantservice = dao.findMerchantChargeByID(inputBean.getMerchantID(), inputBean.getCardAssociation());

                inputBean.setMerchantID(ipgmerchantservice.getId().getMerchantid());
                inputBean.setMerchantID2(ipgmerchantservice.getId().getMerchantid());
                inputBean.setCardAssociation(ipgmerchantservice.getIpgcardassociation().getCardassociationcode());
                inputBean.setCardAssociation2(ipgmerchantservice.getIpgcardassociation().getCardassociationcode());
                inputBean.setUserName(ipgmerchantservice.getUsername());
                inputBean.setPassword("");
                inputBean.setRePassword("");

            } else {
                inputBean.setMessage("Empty Merchant ID");
            }
        } catch (Exception ex) {
            inputBean.setMessage("MerchantCredentialAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantCredentialAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.MERCHANT_CREDENTIAL, request);

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
                }else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
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
        System.out.println("called MerchantCredentialAction: List");
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
            MerchantCredentialDAO dao = new MerchantCredentialDAO();
            List<MerchantCredentialBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("Merchant credential search using [")
                        .append(checkEmptyorNullString("Merchant ID", inputBean.getMerchantID()))
                        .append(checkEmptyorNullString("Card Association ", inputBean.getCardAssociation()))
                        .append(checkEmptyorNullString("User Name", inputBean.getUserName()))
                        .append("] parameters ");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.MERCHANT_CREDENTIAL, SectionVarList.MERCHANTMANAGEMENT, searchParameters.toString(), null);
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
            Logger.getLogger(MerchantCredentialAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " MerchantCredentialAction");
        }
        return "list";
    }

    private String validateInputs() throws Exception {
        MerchantCredentialDAO dao = new MerchantCredentialDAO();
        boolean isexist = dao.isCardAssociationDesExists(inputBean.getCardAssociation());
        String message = "";
        try {
            if (inputBean.getMerchantID() == null || inputBean.getMerchantID().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_CREDENTIAL_EMPTY_NAME;
            } else if (inputBean.getCardAssociation() == null || inputBean.getCardAssociation().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_CREDENTIAL_EMPTY_CARD_ASSOCIATION;
            } //            else if (isexist) {
            //                message = MessageVarList.MERCHANT_CREDENTIAL_ALREADY_EXISTS_CARD_ASSOCIATION;
            //            }
            else if (inputBean.getUserName() == null || inputBean.getUserName().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_CREDENTIAL_EMPTY_USERNAME;
            } else if (inputBean.getPassword() == null || inputBean.getPassword().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_CREDENTIAL_EMPTY_PASS;
            } else if (inputBean.getRePassword() == null || inputBean.getRePassword().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_CREDENTIAL_EMPTY_RE_TYPE_PASS;
            } else if (!inputBean.getPassword().trim().contentEquals(inputBean.getRePassword().trim())) {
                message = MessageVarList.MERCHANT_CREDENTIAL_UNMATCH_PASSWORDS;
            } else if (!Validation.isSpecailCharacter(inputBean.getUserName().trim())) {
                message = MessageVarList.MERCHANT_CREDENTIAL_INVALID_USERNAME;
            }
            if (inputBean.getPassword().equals(inputBean.getRePassword())) {
                if (!Validation.isAlphaNumeric(inputBean.getPassword())) {
                    message = "Password is invalid";
//                    String msg = "";
//                    msg = this.checkPolicy(inputBean.getPassword());
//                    if (message.equals("")) {
//                        message = msg;
//                    }
                } else if (inputBean.getPassword().length() != 8) {
                    message = "Password length should be 8";
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    private String checkPolicy(String password) throws Exception {
        String errorMsg = "";
        try {
            PasswordPolicyDAO dao = new PasswordPolicyDAO();

            Ipgpasswordpolicy mpipasswordpolicy = dao.getPasswordPolicyDetails();
            if (mpipasswordpolicy != null) {

                String msg = this.CheckPasswordValidity(password, mpipasswordpolicy);

                if (!msg.equals("")) {
                    errorMsg = msg;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return errorMsg;
    }

    public String CheckPasswordValidity(String password, Ipgpasswordpolicy bean) throws Exception {
        String msg = "";
        boolean flag = true;
        try {

            if (password.length() < bean.getMinimumlength().intValue()) {
                flag = false;
                msg = MessageVarList.SYSUSER_PASSWORD_TOO_SHORT;
            } else if (password.length() > 8) {
                flag = false;
                msg = MessageVarList.SYSUSER_PASSWORD_TOO_LARGE;
            }

            if (flag) {
                int digits = 0;
                int upper = 0;
                int lower = 0;
                int special = 0;

                for (int i = 0; i < password.length(); i++) {
                    char c = password.charAt(i);

                    if (Character.isDigit(c)) {
                        digits++;
                    } else if (Character.isUpperCase(c)) {
                        upper++;
                    } else if (Character.isLowerCase(c)) {
                        lower++;
                    } else {
                        special++;
                    }
                }

                if (lower < bean.getMinimumlowercasecharacters().intValue()) {
                    msg = MessageVarList.SYSUSER_PASSWORD_LESS_LOWER_CASE_CHARACTERS;
                    flag = false;
                } else if (upper < bean.getMinimumuppercasecharacters().intValue()) {
                    msg = MessageVarList.SYSUSER_PASSWORD_LESS_UPPER_CASE_CHARACTERS;
                    flag = false;
                } else if (digits < bean.getMinimumnumericalcharacters().intValue()) {
                    msg = MessageVarList.SYSUSER_PASSWORD_LESS_NUMERICAL_CHARACTERS;
                    flag = false;
                } else if (special < bean.getMinimumspecialcharacters().intValue()) {
                    msg = MessageVarList.SYSUSER_PASSWORD_LESS_SPACIAL_CHARACTERS;;
                    flag = false;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return msg;
    }

    private String updatevalidateInputs() throws Exception {
        MerchantCredentialDAO dao = new MerchantCredentialDAO();
        boolean isexist = dao.isCardAssociationDesExists(inputBean.getCardAssociation());
        String message = "";
        try {
            if (inputBean.getMerchantID() == null || inputBean.getMerchantID().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_CREDENTIAL_EMPTY_NAME;
            } else if (inputBean.getCardAssociation() == null || inputBean.getCardAssociation().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_CREDENTIAL_EMPTY_CARD_ASSOCIATION;
            } else if (isexist) {
                message = MessageVarList.MERCHANT_CREDENTIAL_ALREADY_EXISTS_CARD_ASSOCIATION;
            } else if (inputBean.getUserName() == null || inputBean.getUserName().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_CREDENTIAL_EMPTY_USERNAME;
            } else {
                if (!Validation.isSpecailCharacter(inputBean.getUserName().trim())) {
                    message = MessageVarList.MERCHANT_CREDENTIAL_INVALID_USERNAME;
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    public String add() {
        System.out.println("called MerchantCredentialAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            MerchantCredentialDAO dao = new MerchantCredentialDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.MERCHANT_CREDENTIAL, SectionVarList.MERCHANTMANAGEMENT, "Merchant credential ["+inputBean.getMerchantID()+", "+inputBean.getCardAssociation()+"] added", null);
//                inputBean.setPassword(Common.makeHash(inputBean.getPassword().trim()));
                System.out.println("password******" + inputBean.getPassword());
                String password = new String(Base64.encode(inputBean.getPassword().getBytes()));
                inputBean.setPassword(password);
                System.out.println("encoded password******" + inputBean.getPassword());
                message = dao.insertMerchantCredential(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Merchant Credential " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("MerchantCredentialAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantCredentialAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String delete() {
        System.out.println("called MerchantCredentialAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            MerchantCredentialDAO dao = new MerchantCredentialDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.MERCHANT_CREDENTIAL, SectionVarList.MERCHANTMANAGEMENT, "Merchant credential ["+inputBean.getMerchantID()+", "+inputBean.getCardAssociation()+"] deleted", null);
            message = dao.deleteMerchantCredential(inputBean, audit);
            if (message.isEmpty()) {
                message = "Merchant Credential " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(MerchantCredentialAction.class.getName()).log(Level.SEVERE, null, e);
//					            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String update() {

        System.out.println("called MerchantCredentialAction : update");
        String retType = "message";

        try {
            if (inputBean.getMerchantID2() != null && !inputBean.getMerchantID2().isEmpty()) {

                inputBean.setMerchantID(inputBean.getMerchantID2());
                inputBean.setCardAssociation(inputBean.getCardAssociation2());
                String message = this.validateInputs();

                if (message.isEmpty()) {
                    HttpServletRequest request = ServletActionContext.getRequest();
                    MerchantCredentialDAO dao = new MerchantCredentialDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.MERCHANT_CREDENTIAL, SectionVarList.MERCHANTMANAGEMENT, "Merchant credential ["+inputBean.getMerchantID()+", "+inputBean.getCardAssociation()+"] updated", null);

                    System.out.println("password******" + inputBean.getPassword());
                    String password = new String(Base64.encode(inputBean.getPassword().getBytes()));
                    inputBean.setPassword(password);
                    System.out.println("encoded password******" + inputBean.getPassword());

//                    System.out.println("password string******" + String.valueOf(Base64.encode(inputBean.getPassword().getBytes())));
                    message = dao.updateMerchantCredential(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Merchant Credential " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            } else {
                addActionError("Merchant ID cannot be empty");
            }
        } catch (Exception ex) {
            Logger.getLogger(MerchantCredentialAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Merchant Credential " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }
}
