/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.CommonFilePathBean;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantManagementDataBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantManagementInputBean;
import com.epic.epay.ipg.bean.util.security.KeystoreCreatorBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.merchantmanagement.MerchantManagementDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgcommonconfig;
import com.epic.epay.ipg.util.mapping.Ipgcommonfilepath;
import com.epic.epay.ipg.util.mapping.Ipgcountry;
import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.security.KeystoreCreator;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.OracleMessage;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
import org.castor.util.Base64Decoder;

/**
 * @author Asitha Liyanawaduge
 *
 * 19/11/2013
 */
public class MerchantManagementAction extends ActionSupport implements
        ModelDriven<Object>, AccessControlService {

    /**
     *
     */
    private InputStream fileInputStream;
    private String fileName;

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private static final long serialVersionUID = -8789430023045166186L;
    MerchantManagementInputBean inputBean = new MerchantManagementInputBean();

    public String view() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setCountryList(dao.getCountryList());
                inputBean
                        .setStatusList(dao
                                .getDefultStatusList(CommonVarList.STATUS_CATEGORY_MERCHANT));
                inputBean.setDefaultmerchantList(dao.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));
                List<String> secmech = new ArrayList<String>();
                secmech.add("DigitallySign");
                secmech.add("SymmetricKey");
                inputBean.setSecuritymechamismList(secmech);

                MerchantManagementDAO mdao = new MerchantManagementDAO();

                inputBean.setHeadmerchantList(mdao.getHeadMerchantList());

            } else {
                result = "loginpage";
            }

            System.out.println("called MerchantManagementAction :view");

        } catch (Exception ex) {
            addActionError("Merchant " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantManagementAction.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return result;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(
                PageVarList.MERCHANT_MANAGEMENT, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVassignc(true);
        inputBean.setVdownload(true);
        inputBean.setVrule(true);
        inputBean.setVassignr(true);
        inputBean.setVupload(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                } else if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
                    inputBean.setVdelete(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                } else if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                } else if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.DOWNLOAD_TASK)) {
                    inputBean.setVdownload(false);
                } else if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.ASSIGN_TASK)) {
                    inputBean.setVassignc(false);
                    inputBean.setVassignr(false);
                } else if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.SERVICE_TASK)) {
                    inputBean.setVrule(false);
                } else if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.UPLOAD_TASK)) {
                    inputBean.setVupload(false);
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
        System.out.println("called MerchantManagementAction: List");
        try {

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String sortOrder = "";

            if (!inputBean.getSidx().isEmpty()) {
                sortOrder = " order by " + inputBean.getSidx() + " "
                        + inputBean.getSord();
            }

            MerchantManagementDAO dao = new MerchantManagementDAO();
            List<MerchantManagementDataBean> dataList = dao.getSearchList(
                    inputBean, rows, from, sortOrder);
            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("Merchant search using [")
                        .append(checkEmptyorNullString("Merchant ID", inputBean.getMerchantId()))
                        .append(checkEmptyorNullString("Head Merchant", inputBean.getHeadmerchant()))
                        .append(checkEmptyorNullString("Merchant Name ", inputBean.getMerchantName()))
                        .append(checkEmptyorNullString("Address", inputBean.getAddress1()))
                        .append(checkEmptyorNullString("City", inputBean.getCity()))
                        .append(checkEmptyorNullString("E-Mail ", inputBean.getEmail()))
                        .append(checkEmptyorNullString("Default Merchant ", inputBean.getDefaultmerchant()))
                        .append("] parameters ");

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.MERCHANT_MANAGEMENT, SectionVarList.MERCHANTMANAGEMENT, searchParameters.toString(), null);
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
            Logger.getLogger(MerchantManagementAction.class.getName()).log(
                    Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS
                    + " Page List Action");
        }
        return "list";
    }

    public String viewpopup() {
        System.out.println("called MerchantManagementAction :viewpopup");
        String result = "viewpopup";
        try {

            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setCountryList(dao.getCountryList());
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_MERCHANT));
                inputBean.setAuthreqstatusList(dao.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));
                inputBean.setDefaultmerchantList(dao.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));
                inputBean.setRiskprofileList(dao.getRiskProfileList());
                List<String> secmech = new ArrayList<String>();
                secmech.add("DigitallySign");
                secmech.add("SymmetricKey");
                inputBean.setSecuritymechamismList(secmech);

                MerchantManagementDAO mdao = new MerchantManagementDAO();

                inputBean.setHeadmerchantList(mdao.getHeadMerchantList());

            } else {
                result = "loginpage";
            }

        } catch (Exception ex) {
            addActionError("MerchantManagementAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantManagementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String add() {
        System.out.println("called MerchantManagementAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            MerchantManagementDAO dao = new MerchantManagementDAO();
            CommonDAO commondao = new CommonDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request,
                        TaskVarList.ADD_TASK, PageVarList.MERCHANT_MANAGEMENT,
                        SectionVarList.MERCHANTMANAGEMENT, "Merchant ID " + inputBean.getMerchantId() + " added",
                        null);
                Ipgmerchant merchant = dao.findMerchantById(inputBean.getMerchantId());
                if (merchant.getMerchantid() == null) {
                    message = dao.insertMerchant(inputBean, audit);
//                    if (message.isEmpty()) {
//                        /**
//                         * ****************** initialize the keystore create
//                         * process for merchant ******************
//                         */
                    String ostype = this.getOS_Type();
                    String keygenstatus = commondao.getCommonConfigValueByCode(CommonVarList.COMMON_CONFIG_KEYSTOREGENSTATUS).getValue();
                    String keystorepassword = commondao.getCommonConfigValueByCode(CommonVarList.COMMON_CONFIG_KEYSTOREPASS).getValue();

                    if (keygenstatus.equals("YES")) {
                        Ipgcommonfilepath ipgcommonfilepath = dao.getKeystoreFilePath(ostype);
                        //String keystorepath = ipgcommonfilepath.
                        KeystoreCreatorBean keyBean = this.loadBeanToMakeKeystore(inputBean, keystorepassword, ipgcommonfilepath);
                        KeystoreCreator keystoreManager = new KeystoreCreator();
                        keystoreManager.CreateKeystoreWithCertificate(keyBean);
                    }
//                        /**
//                         * ******************* finish the keystore create
//                         * process for merchant ******************
//                         */
//                    }
                } else {
                    message = MessageVarList.COMMON_ALREADY_EXISTS;
                }
                if (message.isEmpty()) {
                    addActionMessage("Merchant "
                            + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            addActionError("Merchant " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantManagementAction.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return result;
    }

    public String detail() {

        System.out.println("called MerchantManagementAction: detail");
        Ipgmerchant merchant;
        try {
            if (inputBean.getMerchantId() != null
                    && !inputBean.getMerchantId().isEmpty()) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                CommonDAO comdao = new CommonDAO();
                MerchantManagementDAO dao = new MerchantManagementDAO();

                inputBean.setCountryList(comdao.getCountryList());
                inputBean.setStatusList(comdao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_MERCHANT));
                inputBean.setAuthreqstatusList(comdao.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));
                inputBean.setDefaultmerchantList(comdao.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));
                inputBean.setRiskprofileList(comdao.getRiskProfileList());
                List<String> secmech = new ArrayList<String>();
                secmech.add("DigitallySign");
                secmech.add("SymmetricKey");
                inputBean.setSecuritymechamismList(secmech);
                inputBean.setHeadmerchantList(dao.getHeadMerchantList());

                merchant = dao.findMerchantById(inputBean.getMerchantId());
                inputBean.setStatus(merchant.getIpgstatus().getStatuscode());
                inputBean.setMerchantName(merchant.getMerchantname());
                try {
                    inputBean.setHeadmerchant(merchant.getIpgheadmerchant().getMerchantcustomerid());
                    inputBean.setHeadmerchant2(merchant.getIpgheadmerchant().getMerchantcustomerid());
                } catch (Exception e) {
                    inputBean.setHeadmerchant("");
                    inputBean.setHeadmerchant2("");
                }
                inputBean.setAddress1(merchant.getIpgaddress().getAddress1());
                inputBean.setAddress2(merchant.getIpgaddress().getAddress2());
                inputBean.setCity(merchant.getIpgaddress().getCity());
                inputBean.setStateCode(merchant.getIpgaddress().getStatecode());
                inputBean.setPostalCode(merchant.getIpgaddress().getPostalcode());
                inputBean.setProvince(merchant.getIpgaddress().getProvince());
                inputBean.setCountry(merchant.getIpgaddress().getCountrycode());
                inputBean.setMobile(merchant.getIpgaddress().getMobile());
                inputBean.setTelNo(merchant.getIpgaddress().getTelno());
                inputBean.setFax(merchant.getIpgaddress().getFax());
                inputBean.setEmail(merchant.getIpgaddress().getEmail());
                inputBean.setPrimaryURL(merchant.getPrimaryurl());
                inputBean.setSecondaryURL(merchant.getSecondaryurl());
                inputBean.setDreURL(merchant.getDinamicreturnerrorurl());
                inputBean.setDrsURL(merchant.getDinamicreturnsuccessurl());
                inputBean.setDateOfRegistry(sdf.format(merchant.getDateofregistry()));
                inputBean.setDateOfExpire(sdf.format(merchant.getDateofexpiry()));
                inputBean.setContactPerson(merchant.getContactperson());
                inputBean.setSecurityMechanism(merchant.getSecuritymechanism());
                inputBean.setRemarks(merchant.getRemarks());
                inputBean.setRiskprofile(merchant.getIpgriskprofile().getRiskprofilecode());
                inputBean.setIsdefaultmerchany(merchant.getIsdefaultmerchany());
                inputBean.setDefaultmerchant(merchant.getIsdefaultmerchany());
                try {
                    inputBean.setAuthreqstatus(merchant.getIpgstatusByAuthenticationrequied().getStatuscode());
                } catch (Exception e) {
                    inputBean.setAuthreqstatus("");
                }

            } else {
                inputBean.setMessage("Empty Merchant code.");
            }

        } catch (Exception e) {
            addActionError("MerchantManagementAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantManagementAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    }

    public String find() {
        System.out.println("called MerchantManagementAction: Find");
        Ipgmerchant merchant;
        try {
            if (inputBean.getMerchantId() != null
                    && !inputBean.getMerchantId().isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                MerchantManagementDAO dao = new MerchantManagementDAO();
                merchant = dao.findMerchantById(inputBean.getMerchantId());

                //CommonDAO commonDAO = new CommonDAO();
                //inputBean.setMerchantId(merchantcustomerid);
                // inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                // inputBean.setCountryList(dao.getDefultCountryList());
                // inputBean.setAuthreqstatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));
                // inputBean.setTxninitstatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));
                // inputBean.setRiskprofileList(commonDAO.getRiskProfileList());
                //inputBean.setMerchantId(merchant.getTransactioninitaiatedmerchntid());
                inputBean.setStatus(merchant.getIpgstatus().getStatuscode());
                inputBean.setMerchantName(merchant.getMerchantname());
                try {
                    inputBean.setHeadmerchant(merchant.getIpgheadmerchant().getMerchantcustomerid());
                } catch (Exception e) {
                    inputBean.setHeadmerchant("");
                }
                inputBean.setAddress1(merchant.getIpgaddress().getAddress1());
                inputBean.setAddress2(merchant.getIpgaddress().getAddress2());
                inputBean.setCity(merchant.getIpgaddress().getCity());
                inputBean.setStateCode(merchant.getIpgaddress().getStatecode());
                inputBean.setPostalCode(merchant.getIpgaddress().getPostalcode());
                inputBean.setProvince(merchant.getIpgaddress().getProvince());
                inputBean.setCountry(merchant.getIpgaddress().getCountrycode());
                inputBean.setMobile(merchant.getIpgaddress().getMobile());
                inputBean.setTelNo(merchant.getIpgaddress().getTelno());
                inputBean.setFax(merchant.getIpgaddress().getFax());
                inputBean.setEmail(merchant.getIpgaddress().getEmail());
                inputBean.setPrimaryURL(merchant.getPrimaryurl());
                inputBean.setSecondaryURL(merchant.getSecondaryurl());
                inputBean.setDreURL(merchant.getDinamicreturnerrorurl());
                inputBean.setDrsURL(merchant.getDinamicreturnsuccessurl());
                inputBean.setDateOfRegistry(sdf.format(merchant.getDateofregistry()));
                inputBean.setDateOfExpire(sdf.format(merchant.getDateofexpiry()));
                inputBean.setContactPerson(merchant.getContactperson());
                inputBean.setSecurityMechanism(merchant.getSecuritymechanism());
                inputBean.setRemarks(merchant.getRemarks());
                inputBean.setRiskprofile(merchant.getIpgriskprofile().getRiskprofilecode());
                inputBean.setIsdefaultmerchany(merchant.getIsdefaultmerchany());
                inputBean.setDefaultmerchant(merchant.getIsdefaultmerchany());
                try {
                    inputBean.setAuthreqstatus(merchant.getIpgstatusByAuthenticationrequied().getStatuscode());
                } catch (Exception e) {
                    inputBean.setAuthreqstatus("");
                }

            } else {
                inputBean.setMessage("Empty Merchant code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Merchant "
                    + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantManagementAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return "find";

    }

    public String update() {

        System.out.println("called MerchantManagementAction : update");
        String retType = "message";

        try {
            if (inputBean.getIsdefaultmerchany().equals("YES")) {
                if(inputBean.getHeadmerchant()==null || inputBean.getHeadmerchant().trim().isEmpty()){
                    inputBean.setHeadmerchant(inputBean.getHeadmerchant2());
                }
            }
            if (inputBean.getMerchantId() != null
                    && !inputBean.getMerchantId().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    MerchantManagementDAO dao = new MerchantManagementDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request,
                            TaskVarList.UPDATE_TASK,
                            PageVarList.MERCHANT_MANAGEMENT,
                            SectionVarList.MERCHANTMANAGEMENT, "Merchant ID " + inputBean.getMerchantId() + " updated",
                            null);
                    message = dao.updateMerchant(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Merchant " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MerchantManagementAction.class.getName()).log(Level.SEVERE,
                    null, ex);
            addActionError("Merchant " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String delete() {
        System.out.println("called MerchantManagementAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            MerchantManagementDAO dao = new MerchantManagementDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request,
                    TaskVarList.DELETE_TASK, PageVarList.MERCHANT_MANAGEMENT,
                    SectionVarList.MERCHANTMANAGEMENT, "Merchant ID " + inputBean.getMerchantId() + " deleted", null);
            message = dao.deleteMerchant(inputBean, audit);
            if (message.isEmpty()) {
                message = "Merchant " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(MerchantManagementAction.class.getName()).log(Level.SEVERE, null, e);
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }
    
    public String findDefault(){
        System.out.println("called MerchantManagementAction: findDefault");
        String defaultMerchantStatus;
        try {
            if (inputBean.getHeadmerchant()!= null
                    && !inputBean.getHeadmerchant().isEmpty()) {
                MerchantManagementDAO dao = new MerchantManagementDAO();
                defaultMerchantStatus = dao.isDefaultMerchant(inputBean.getHeadmerchant());
                inputBean.setDefaultmerchant(defaultMerchantStatus);
            } else {
                inputBean.setMessage("Empty Head Merchant code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Merchant "
                    + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantManagementAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return "find";
    }

    /**
     * @return
     */
    private String validateInputs() {
        String message = "";
        if (inputBean.getMerchantId() == null
                || inputBean.getMerchantId().trim().isEmpty()) {
            message = MessageVarList.MERCHANT_ID_EMPTY;
        } else if (!isValidMerchantID(inputBean.getMerchantId())) {
            message = MessageVarList.MERCHANT_ID_INVALID;
        } else if (inputBean.getHeadmerchant() == null
                || inputBean.getHeadmerchant().trim().isEmpty()) {
            message = MessageVarList.MERCHANT_MGT_EMPTY_MERCHANT_CUSTOMER_CODE;
        } else if (!Validation.isSpecailCharacter(inputBean.getMerchantId())) {
            message = MessageVarList.INVALID_MERCHANT_ID;
        } else if (inputBean.getMerchantName() == null
                || inputBean.getMerchantName().trim().isEmpty()) {
            message = MessageVarList.MERCHANT_NAME_EMPTY;
        } else if (!Validation.isSpecailCharacter(inputBean.getMerchantName())) {
            message = MessageVarList.INVALID_MERCHANT_NAME;
        } else if (inputBean.getAddress1() == null
                || inputBean.getAddress1().trim().isEmpty()) {
            message = MessageVarList.MERCHANT_ADDRESS1_EMPTY;
        } else if (inputBean.getCity() == null
                || inputBean.getCity().trim().isEmpty()) {
            message = MessageVarList.CITY_EMPTY;
        } else if (inputBean.getCountry() == null
                || inputBean.getCountry().trim().isEmpty()) {
            message = MessageVarList.COUNTRY_EMPTY;
        } else if (!Validation.isSpecailCharacter(inputBean.getCity())) {
            message = MessageVarList.INVALID_CITY;
        } else if (inputBean.getEmail() == null
                || inputBean.getEmail().trim().isEmpty()) {
            message = MessageVarList.ERROR_EMAIL_EMPTY;
        } else if (!Validation.isValidEmail(inputBean
                .getEmail())) {
            message = MessageVarList.ERROR_EMAIL_INVALID;
        } else if (inputBean.getPrimaryURL() == null
                || inputBean.getPrimaryURL().trim().isEmpty()) {
            message = MessageVarList.PRIMARY_URL_EMPTY;
        } else if (inputBean.getDrsURL() == null
                || inputBean.getDrsURL().trim().isEmpty()) {
            message = MessageVarList.DRS_URL_EMPTY;
        } else if (inputBean.getDreURL() == null
                || inputBean.getDreURL().trim().isEmpty()) {
            message = MessageVarList.DRE_URL_EMPTY;
        } else if (inputBean.getDateOfRegistry() == null
                || inputBean.getDateOfRegistry().trim().isEmpty()) {
            message = MessageVarList.DATE_OF_REGISTRY_EMPTY;
        } else if (inputBean.getDateOfExpire() == null
                || inputBean.getDateOfExpire().trim().isEmpty()) {
            message = MessageVarList.DATE_OF_EXPIRY_EMPTY;
        } else if (inputBean.getStatus() != null
                && inputBean.getStatus().isEmpty()) {
            message = MessageVarList.STATUS_EMPTY;
        } else if (inputBean.getContactPerson() == null
                || inputBean.getContactPerson().isEmpty()) {
            message = MessageVarList.CONTACT_PERSON_EMPTY;
        } else if (inputBean.getAuthreqstatus().trim() != null
                && inputBean.getAuthreqstatus().trim().isEmpty()) {
            message = MessageVarList.MERCH_MGT_EMPTY_AUTH_REQUIRED_STATUS;
        } else if (inputBean.getRiskprofile() == null
                || inputBean.getRiskprofile().isEmpty()) {
            message = MessageVarList.RISKPROFILE_EMPTY;
        } else if (inputBean.getSecurityMechanism() == null
                || inputBean.getSecurityMechanism().isEmpty()) {
            message = MessageVarList.SECURITY_MECHANISM_EMPTY;
        } else {
//            if (!Validation.isSpecailCharacter(inputBean
//                    .getAddress1())) {
//                message = MessageVarList.ERROR_ADDRESS1_INVALID;
//            } else if (!Validation.isSpecailCharacter(inputBean
//                    .getAddress2())) {
//                message = MessageVarList.ERROR_ADDRESS2_INVALID;
//            } else 
            if (!Validation.isSpecailCharacter(inputBean
                    .getStateCode())) {
                message = MessageVarList.ERROR_STATE_CODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean
                    .getPostalCode())) {
                message = MessageVarList.ERROR_POSTAL_CODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean
                    .getProvince())) {
                message = MessageVarList.ERROR_PROVINCE_INVALID;
            } else if (!Validation.isValidUrl(inputBean
                    .getPrimaryURL())) {
                message = MessageVarList.ERROR_PRIMARY_URL_INVALID;
            } else if (!Validation.isValidUrl(inputBean
                    .getSecondaryURL())) {
                message = MessageVarList.ERROR_SECONDARY_URL_INVALID;
            } else if (!Validation.isValidUrl(inputBean
                    .getDrsURL())) {
                message = MessageVarList.ERROR_SUCCESS_URL_INVALID;
            } else if (!Validation.isValidUrl(inputBean
                    .getDreURL())) {
                message = MessageVarList.ERROR_ERROR_URL_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean
                    .getContactPerson())) {
                message = MessageVarList.ERROR_CONTACT_PERSON_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean
                    .getRemarks())) {
                message = MessageVarList.ERROR_REMARKS_INVALID;

            }
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
        String page = PageVarList.MERCHANT_MANAGEMENT;
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
        } else if ("ruleService".equals(method)) {
            task = TaskVarList.SERVICE_TASK;
        } else if ("assignCurrency".equals(method)) {
            task = TaskVarList.ASSIGN_TASK;
        } else if ("assignRule".equals(method)) {
            task = TaskVarList.ASSIGN_TASK;
        } else if ("findCurrency".equals(method)) {
            task = TaskVarList.ASSIGN_TASK;
        } else if ("downloadCertificate".equals(method)) {
            task = TaskVarList.DOWNLOAD_TASK;
        } else if ("detailsC".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("detailsR".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("viewUplaodCert".equals(method)) {
            task = TaskVarList.UPLOAD_TASK;
        } else if ("uploadCert".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("viewpopup".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("detail".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("findDefault".equals(method)) {
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

    //to get os name (windows or linux)
    public String getOS_Type() {

        String osType = "";
        String osName = "";
        osName = System.getProperty("os.name", "").toLowerCase();

        // For WINDOWS
        if (osName.contains("windows")) {
            osType = "WINDOWS";
        } else if (osName.contains("linux")) {
            osType = "LINUX";
        } else if (osName.contains("sunos")) {
            osType = "SUNOS";
        }

        return osType;
    }

    public KeystoreCreatorBean loadBeanToMakeKeystore(MerchantManagementInputBean inputBean, String keyStorepassword, Ipgcommonfilepath ipgcommonfilepath) throws Exception {
        KeystoreCreatorBean bean = new KeystoreCreatorBean();
        String fileSeparator = System.getProperty("file.separator");
        try {
            /**
             * ******** start set bean values ********************
             */
            bean.setAlias(inputBean.getMerchantId());
            bean.setName(inputBean.getMerchantId());
            bean.setOrganization(inputBean.getMerchantName());
            bean.setCity(inputBean.getCity());
            bean.setOrganizationalUnitName(inputBean.getMerchantName());

            if (inputBean.getProvince() == null) {
                bean.setProvince("");
            } else {
                bean.setProvince(inputBean.getProvince());
            }

            MerchantManagementDAO dao = new MerchantManagementDAO();
            Ipgcountry country = dao.getCountryByCode(inputBean.getCountry());
            String countrycode = country.getCountryisocode();
            String c = countrycode.substring(0, 2);
            bean.setTwo_letter_countrycode(c);

            String aliaspass = "password";
            String keystorename = inputBean.getMerchantId() + ".jks";
            String certificatename = inputBean.getMerchantId() + ".cer";

            File keypathfile = new File(ipgcommonfilepath.getKeystorepath());
            if (!keypathfile.exists()) {
                keypathfile.mkdirs();
            }
            File certfile = new File(ipgcommonfilepath.getCertpath());
            if (!certfile.exists()) {
                certfile.mkdirs();
            }

            String keystorepath = ipgcommonfilepath.getKeystorepath() + fileSeparator + keystorename;
            String certificatepath = ipgcommonfilepath.getCertpath() + fileSeparator + certificatename;

            bean.setKeystorepath(keystorepath);

            Base64Decoder decoder = new Base64Decoder();
            byte[] passwordbyte = decoder.decode(keyStorepassword);
            String keypassword = new String(passwordbyte);

            bean.setKeystorepassword(keypassword);
            bean.setAliaspassword(aliaspass);
            bean.setCertificatepath(certificatepath);

            /**
             * ******** end set bean values ********************
             */
        } catch (Exception e) {
            throw e;
        }
        return bean;
    }

    public boolean isValidMerchantID(String merchantId) {
        boolean val = false;
        if (merchantId.length() == 15) {
            val = true;
        }
        return val;
    }

//    ============================================================================================================
    // new added methods
    public String ruleService() {
        System.out.println("called MerchantManagementAction : ruleService");
        String message = null;
        String retType = "rule";
        try {
            System.out.println("in ruleService");
            System.out.println("---- " + inputBean.getMerchantId_rs());

            MerchantManagementDAO dao = new MerchantManagementDAO();
            dao.findRuleBySecLevel(inputBean);
            CommonDAO daos = new CommonDAO();
            inputBean.setServicechargeList(daos.getServiceChargeList());

            if (dao.findMerchantChargeByID(inputBean.getMerchantId_rs()) != null) {
                inputBean.setServicechargeID(dao.findMerchantChargeByID(inputBean.getMerchantId_rs()).getIpgservicecharge().getServicechargeid().toString());
            } else {
                inputBean.setServicechargeID(null);
            }

        } catch (Exception e) {
            Logger.getLogger(MerchantManagementAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String detailsR() {
        System.out.println("called MerchantManagementAction : detailsR");
        try {
            System.out.println("in detailsR");
            System.out.println("---- " + inputBean.getMerchantId_rs());

            MerchantManagementDAO dao = new MerchantManagementDAO();
            dao.findRuleBySecLevel(inputBean);

            if (dao.findMerchantChargeByID(inputBean.getMerchantId_rs()) != null) {
                inputBean.setServicechargeID(dao.findMerchantChargeByID(inputBean.getMerchantId_rs()).getIpgservicecharge().getServicechargeid().toString());
            } else {
                inputBean.setServicechargeID(null);
            }

        } catch (Exception e) {
            Logger.getLogger(MerchantManagementAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return "find";
    }

    public String assignRule() {
        System.out.println("called MerchantManagementAction : assignRule");
        String result = "message";
        String message = "";
        try {

            message = this.validateInputsRuleService();

            if (message.isEmpty()) {

                if (inputBean.getMerchantId_rs() != null && !inputBean.getMerchantId_rs().isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();

                    MerchantManagementDAO dao = new MerchantManagementDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ASSIGN_TASK, PageVarList.MERCHANT_MANAGEMENT, SectionVarList.MERCHANTMANAGEMENT, "Rules assigned to merchant ID " + inputBean.getMerchantId_rs(), null);
                    message = dao.assignMerchantRule(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Merchant Rule " + MessageVarList.COMMON_SUCC_ASSIGN);
                    } else {
                        addActionError(message);
                    }
                } else {
                    addActionError("Merchant Name cannot be empty");
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Merchant Rule");
            Logger.getLogger(MerchantManagementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String downloadCertificate() {
        System.out.println("called MerchantManagementAction : downloadCertificate");
        String retType = "view";
        String osType = "";
        String certPath = null;
        MerchantManagementDAO dao = new MerchantManagementDAO();
        osType = MerchantManagementDAO.getOS_Type();
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            certPath = dao.getCertificatePath(osType);
            fileName = inputBean.getMerchantId_de() + ".cer";
            System.out.println("file name-- " + fileName);
            try {
                fileInputStream = new FileInputStream(new File(certPath + fileName));
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DOWNLOAD_TASK, PageVarList.MERCHANT_MANAGEMENT, SectionVarList.MERCHANTMANAGEMENT, "Merchant certificate of merchant ID " + inputBean.getMerchantId_de() + " downloaded from " + certPath + fileName, null);
                dao.insertMerchantCertificateMgt(audit);

                return "download";

            } catch (FileNotFoundException ex) {
                addActionError("Merchant Certificate doesn't exists !");
                inputBean.setMessage("Merchant Certificate doesn't exists !");

            } catch (Exception e) {
                addActionError("Merchant Certificate download failed !");
                inputBean.setMessage("Merchant Certificate download failed !");
            }

        } catch (Exception ex) {
            Logger.getLogger(MerchantCertificateManagerAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Merchant Certificate Manager " + MessageVarList.MERCHANT_CER_MGT_ERROR_DOWNLOAD);
        }
        this.view();
        return retType;

    }

    private String validateInputsAssignCurrency() {
        String message = "";
        if (inputBean.getMerchantId() == null || inputBean.getMerchantId().trim().isEmpty()) {
            message = MessageVarList.MERCHANT_AC_EMPTY_MERCHNAT_ID;
        } else if (inputBean.getHeadmerchant() == null || inputBean.getHeadmerchant().trim().isEmpty()) {
            message = MessageVarList.MERCHANT_AC_EMPTY_CURRENCY;
        }
        return message;
    }

    private String validateInputsRuleService() {
        String message = "";
        if (inputBean.getMerchantId_rs() == null || inputBean.getMerchantId_rs().trim().isEmpty()) {
            message = MessageVarList.MERCHANT_RS_EMPTY_MERCHNAT_ID;
        } else if (inputBean.getServicechargeID() == null || inputBean.getServicechargeID().trim().isEmpty()) {
            message = MessageVarList.MERCHANT_RS_EMPTY_SERVICE;
        }
        return message;
    }

    // currency
    public String findCurrency() {
        System.out.println("called MerchantManagementAction: findCurrency");
        try {

            System.out.println("in findCurrency");
            System.out.println("---- " + inputBean.getMerchantId_ac());
            if (inputBean.getMerchantId_ac() != null && !inputBean.getMerchantId_ac().isEmpty()) {
                MerchantManagementDAO dao = new MerchantManagementDAO();
                dao.findCurrency(inputBean);
            } else {
                inputBean.setMessage("Empty Merchant ID.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Merchant Currency " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantManagementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "assignc";
    }

    public String detailsC() {
        System.out.println("called MerchantManagementAction: detailsR");
        try {

            System.out.println("in detailsR");
            System.out.println("---- " + inputBean.getMerchantId_ac());
            if (inputBean.getMerchantId_ac() != null && !inputBean.getMerchantId_ac().isEmpty()) {
                MerchantManagementDAO dao = new MerchantManagementDAO();
                dao.findCurrency(inputBean);
            } else {
                inputBean.setMessage("Empty Merchant ID.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Merchant Currency " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantManagementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "find";
    }

    public String assignCurrency() {
        System.out.println("called MerchantManagementAction : Assign");
        String result = "message";
        String message = "";
        try {
            if (inputBean.getMerchantId_ac() != null && !inputBean.getMerchantId_ac().isEmpty()) {

                HttpServletRequest request = ServletActionContext.getRequest();

                MerchantManagementDAO dao = new MerchantManagementDAO();
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ASSIGN_TASK, PageVarList.MERCHANT_MANAGEMENT, SectionVarList.MERCHANTMANAGEMENT, "Currencies assigned to merchant ID " + inputBean.getMerchantId_ac(), null);
                message = dao.assignMerchantCurrency(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Merchant Currency " + MessageVarList.COMMON_SUCC_ASSIGN);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError("Merchant ID cannot be empty");
            }

        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Merchant Currency");
            Logger.getLogger(MerchantManagementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //upload cert
    public String viewUplaodCert() {
        System.out.println("called MerchantManagementAction : viewUplaodCert");
        String result = "uploadcert";
        String message = "";
        try {

            System.out.println("mert id " + inputBean.getMerchantId_uc());

        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Merchant view upload");
            Logger.getLogger(MerchantManagementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

    public String uploadCert() {
        System.out.println("called MerchantManagementAction : uploadCert");

        try {
            String osType = "";
            CommonFilePathBean url = null;
            MerchantManagementDAO dao = new MerchantManagementDAO();
            osType = dao.getOS_Type();
            url = dao.getCertificatePathBean(osType);
            String message = this.validateUpload();

            if (message.isEmpty()) {

                String mes2 = this.validateExtension();
                if (mes2.isEmpty()) {

                    File directory = new File(url.getCertificatePath());

                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    File f = new File(url.getCertificatePath(), inputBean.getMerchantId_uc() + ".cer");

                    if (f.exists()) {
                        addActionError("Certificate already exists.");
//                    return "error";

                    } else {

                        try {
                            FileUtils.copyFile(inputBean.getFile(), f);
                            addActionMessage("Certificate succesfully uploaded.");

                            HttpServletRequest request = ServletActionContext.getRequest();
                            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPLOAD_TASK, PageVarList.MERCHANT_MANAGEMENT, SectionVarList.MERCHANTMANAGEMENT, "Merchant certificate of merchant ID " + inputBean.getMerchantId_uc() + " uploaded ", null);
                            //for audit new value
                            StringBuilder newValue = new StringBuilder();
                            newValue.append(inputBean.getMerchantId_uc())
                                    .append("|").append(f.getAbsolutePath());
                            audit.setNewvalue(newValue.toString());
                            String succesaudit = dao.insertAudit(audit);

                        } catch (Exception e) {
                            addActionError("File upload failed!");
//                        return "error";
                        }
                    }

                } else {
                    addActionError(mes2);
                }

            } else {
                if (message.equals(MessageVarList.MERCHANT_SELECT_VALID_FILE) && !getActionErrors().isEmpty()) {
                    // addActionError(message);
                } else {
                    addActionError(message);
                }
//                return "error";
            }

        } catch (Exception e) {
            Logger.getLogger(MerchantManagementAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Certificate " + MessageVarList.COMMON_ERROR_UPLOAD);
        }

        return "message";
    }

    private String validateUpload() {
        String message = "";

        String filetype = "";
        if (inputBean.getFileFileName() == null) {
            message = MessageVarList.MERCHANT_SELECT_VALID_FILE;
        } else {
            filetype = inputBean.getFileFileName().substring(inputBean.getFileFileName().length() - 3);

            if (!filetype.equals("cer")) {
                message = MessageVarList.MERCHANT_SELECT_VALID_FILE;
            }
        }
        return message;
    }

    private String validateExtension() {
        String message = "";

        List<String> extensions = new ArrayList<String>();
        extensions.add("cer");

        int pos = inputBean.getFileFileName().lastIndexOf('.') + 1;
        String ext = inputBean.getFileFileName().substring(pos);
        String final_ext = ext.toLowerCase();

        for (int i = 0; i < extensions.size(); i++) {
            if (extensions.get(i).equals(final_ext)) {
                return message;
            }
        }

        message = "You must upload an image file with one of the following extensions:";
        for (int i = 0; i < extensions.size(); i++) {
            message = message + extensions.get(i) + ",";

        }
        return message;
    }

}
