/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantManagementDataBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantManagementInputBean;
import com.epic.epay.ipg.bean.util.security.KeystoreCreatorBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.merchantmanagement.MerchantManagementDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.Validation;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.castor.util.Base64Decoder;

/**
 * @author Asitha Liyanawaduge
 *
 * 19/11/2013
 */
public class MerchantManagementSecAction extends ActionSupport implements
        ModelDriven<Object>, AccessControlService {

    /**
     *
     */
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
            Logger.getLogger(MerchantManagementSecAction.class.getName()).log(
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
            if (!dataList.isEmpty()) {
                inputBean.setRecords(dataList.get(0).getFullCount());
                inputBean.setGridModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);
            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }

        } catch (Exception e) {
            Logger.getLogger(MerchantManagementSecAction.class.getName()).log(
                    Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS
                    + " Page List Action");
        }
        return "list";
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
                        SectionVarList.MERCHANTMANAGEMENT, "Merchant Added",
                        null);
                Ipgmerchant merchant = dao.findMerchantById(inputBean.getMerchantId());
                if (merchant.getMerchantid() == null) {
                    message = dao.insertMerchant(inputBean, audit);
                    if (message.isEmpty()) {
                        /**
                         * ****************** initialize the keystore create
                         * process for merchant ******************
                         */
                        String ostype = this.getOS_Type();
                        String keygenstatus = commondao.getCommonConfigValueByCode(CommonVarList.COMMON_CONFIG_KEYSTOREGENSTATUS).getValue();
                        String keystorepassword = commondao.getCommonConfigValueByCode(CommonVarList.COMMON_CONFIG_KEYSTOREPASS).getValue();

                        if (keygenstatus.equals("YES")) {
                            Ipgcommonfilepath ipgcommonfilepath = dao.getKeystoreFilePath(ostype);
                            // String keystorepath = ipgcommonfilepath.
                            KeystoreCreatorBean keyBean = this.loadBeanToMakeKeystore(inputBean, keystorepassword, ipgcommonfilepath);
                            KeystoreCreator keystoreManager = new KeystoreCreator();
                            keystoreManager.CreateKeystoreWithCertificate(keyBean);
                        }
                        /**
                         * ******************* finish the keystore create
                         * process for merchant ******************
                         */
                    }
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
            addActionError("Merchant " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantManagementSecAction.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return result;
    }

    public String find() {
        System.out.println("called MerchantManagementAction: Find");
        Ipgmerchant merchant;
        try {
            if (inputBean.getMerchantId() != null
                    && !inputBean.getMerchantId().isEmpty()) {
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
                inputBean.setHeadmerchant(merchant.getIpgheadmerchant().getMerchantcustomerid());
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
                inputBean.setDateOfRegistry(merchant.getDateofregistry().toString().replace(" 00:00:00.0", ""));
                inputBean.setDateOfExpire(merchant.getDateofexpiry().toString().replace(" 00:00:00.0", ""));
                inputBean.setContactPerson(merchant.getContactperson());
                inputBean.setSecurityMechanism(merchant.getSecuritymechanism());
                inputBean.setRemarks(merchant.getRemarks());

            } else {
                inputBean.setMessage("Empty Merchant code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Merchant "
                    + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantManagementSecAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return "find";

    }

    public String update() {

        System.out.println("called MerchantManagementAction : update");
        String retType = "message";

        try {
            if (inputBean.getMerchantId() != null
                    && !inputBean.getMerchantId().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    MerchantManagementDAO dao = new MerchantManagementDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request,
                            TaskVarList.UPDATE_TASK,
                            PageVarList.MERCHANT_MANAGEMENT,
                            SectionVarList.MERCHANTMANAGEMENT, "Merchant Updated",
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
            Logger.getLogger(MerchantManagementSecAction.class.getName()).log(Level.SEVERE,
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
                    SectionVarList.MERCHANTMANAGEMENT, "Merchant Deleted", null);
            message = dao.deleteMerchant(inputBean, audit);
            if (message.isEmpty()) {
                message = "Merchant " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(MerchantManagementSecAction.class.getName()).log(Level.SEVERE, null, e);
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

    public KeystoreCreatorBean loadBeanToMakeKeystore(MerchantManagementInputBean inputBean, String keyStorePassword, Ipgcommonfilepath ipgcommonfilepath) throws Exception {
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

            String aliaspass = inputBean.getMerchantId() + "@123";
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
            byte[] passwordbyte = decoder.decode(keyStorePassword);
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
}
