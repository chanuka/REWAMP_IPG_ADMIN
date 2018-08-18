/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.HeadMerchantBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.HeadMerchantInputBean;
import com.epic.epay.ipg.bean.util.security.KeystoreCreatorBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.merchantmanagement.HeadMerchantDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgcommonfilepath;
import com.epic.epay.ipg.util.mapping.Ipgcountry;
import com.epic.epay.ipg.util.mapping.Ipgheadmerchant;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.castor.util.Base64Decoder;

/**
 *
 * @author Chalitha
 */
public class HeadMerchantSecAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    HeadMerchantInputBean inputBean = new HeadMerchantInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String view() {
        String result = "view";
        try {
            if (!this.applyUserPrivileges()) {
                result = "loginpage";
            }

            System.out.println("called HeadMerchantAction :view");

        } catch (Exception ex) {
            addActionError("HeadMerchant " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(HeadMerchantSecAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String list() {

        System.out.println("called HeadMerchantAction : List");
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

            HeadMerchantDAO dao = new HeadMerchantDAO();
            List<HeadMerchantBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
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
            Logger.getLogger(HeadMerchantSecAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " HeadMerchant");
        }
        return "list";
    }

    public String add() {
        System.out.println("called HeadMerchantAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HeadMerchantDAO dao = new HeadMerchantDAO();
            CommonDAO commondao = new CommonDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Ipgsystemaudit audit = Common.makeAudittrace(request,
                        TaskVarList.ADD_TASK, PageVarList.HEAD_MERCHANT_MGT_PAGE,
                        SectionVarList.MERCHANTMANAGEMENT, "HeadMerchant Added", null);
                Ipgheadmerchant merchant = dao.findHeadMerchantById(inputBean.getMerchantcustomerid());

                if (merchant.getMerchantcustomerid() == null) {
                    message = dao.insertHeadMerchant(inputBean, audit);
                    if (message.isEmpty()) {
                        /**
                         * ****************** initialize the keystore create
                         * process for merchant ******************
                         */
                        String ostype = this.getOS_Type();
                        
                        String keygenstatus = commondao.getCommonConfigValueByCode(CommonVarList.COMMON_CONFIG_KEYSTOREGENSTATUS).getValue();
                        String keystorepassword = commondao.getCommonConfigValueByCode(CommonVarList.COMMON_CONFIG_KEYSTOREPASS).getValue();

                        if (keygenstatus.equals("YES")) {
                             Ipgcommonfilepath  ipgcommonfilepath = dao.getKeystoreFilePath(ostype);
         
                            KeystoreCreatorBean keyBean = this.loadBeanToMakeKeystore(inputBean, keystorepassword,ipgcommonfilepath);
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
                    addActionMessage("HeadMerchant "
                            + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("HeadMerchant " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(HeadMerchantSecAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return result;
    }

    public String find() {
        System.out.println("called HeadMerchantAction: Find");
        Ipgheadmerchant headmerchant;
        Ipgmerchant merchant;
        String merchantcustomerid = null;
        try {
            if (inputBean.getMerchantcustomerid() != null
                    && !inputBean.getMerchantcustomerid().isEmpty() || (ServletActionContext.getRequest().getSession(false).getAttribute("Merchantcustomerid")) != null) {

                if ((merchantcustomerid = inputBean.getMerchantcustomerid()) == null) {
                    merchantcustomerid = (String) ServletActionContext.getRequest().getSession(false).getAttribute("Merchantcustomerid");
                    ServletActionContext.getRequest().getSession(false).removeAttribute("Merchantcustomerid");

                }
                ServletActionContext.getRequest().getSession(false).setAttribute("Merchantcustomerid", merchantcustomerid);
                HeadMerchantDAO dao = new HeadMerchantDAO();
                headmerchant = dao.findHeadMerchantById(merchantcustomerid);
                merchant = dao.findMerchantById(headmerchant.getTransactioninitaiatedmerchntid());
                CommonDAO commonDAO = new CommonDAO();
                inputBean.setMerchantcustomerid(merchantcustomerid);
                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setCountryList(dao.getDefultCountryList());
                inputBean.setAuthreqstatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));
                inputBean.setTxninitstatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));
                inputBean.setRiskprofileList(commonDAO.getRiskProfileList());
                inputBean.setMerchantid(headmerchant.getTransactioninitaiatedmerchntid());
                inputBean.setMerchantname(headmerchant.getMerchantname());
                inputBean.setAddress1(headmerchant.getIpgaddress().getAddress1());
                inputBean.setAddress2(headmerchant.getIpgaddress().getAddress2());
                inputBean.setCity(headmerchant.getIpgaddress().getCity());
                inputBean.setStatecode(headmerchant.getIpgaddress().getStatecode());
                inputBean.setPostalcode(headmerchant.getIpgaddress().getPostalcode());
                inputBean.setProvince(headmerchant.getIpgaddress().getProvince());
                inputBean.setCountry(headmerchant.getIpgaddress().getCountrycode());
                inputBean.setMobile(headmerchant.getIpgaddress().getMobile());
                inputBean.setTel(headmerchant.getIpgaddress().getTelno());
                inputBean.setFax(headmerchant.getIpgaddress().getFax());
                inputBean.setEmail(headmerchant.getIpgaddress().getEmail());
                inputBean.setPrimaryurl(headmerchant.getPrimaryurl());
                inputBean.setSecondaryurl(headmerchant.getSecondaryurl());
                inputBean.setDynamicreturnerrorurl(merchant.getDinamicreturnerrorurl());
                inputBean.setDynamicreturnsuccessurl(merchant.getDinamicreturnsuccessurl());
                inputBean.setDateofregistry(headmerchant.getDateofregistry().toString().replace(" 00:00:00.0", ""));
                inputBean.setDateofexpiry(headmerchant.getDateofexpiry().toString().replace(" 00:00:00.0", ""));
                inputBean.setStatus(headmerchant.getIpgstatusByStatuscode().getStatuscode().toString());
                inputBean.setContactperson(headmerchant.getContactperson());
                inputBean.setSecuritymechanism(headmerchant.getSecuritymechanism());
                inputBean.setRemarks(headmerchant.getRemarks());
                inputBean.setSecuritymechanism(headmerchant.getSecuritymechanism());
                inputBean.setRiskprofile(headmerchant.getIpgriskprofile().getRiskprofilecode().toString());
                inputBean.setAuthreqstatus(headmerchant.getIpgstatusByAuthenticationrequied().getStatuscode().toString());
                inputBean.setTxninitstatus(headmerchant.getIpgstatusByTransactioninitaiatedstatus().getStatuscode().toString());

            } else {
                inputBean.setMessage("Empty Head Merchant code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Head Merchant "
                    + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(HeadMerchantSecAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return "find";

    }

    public String update() {

        System.out.println("called HeadMerchantAction : update");
        String retType = "message";

        try {
            if (inputBean.getMerchantcustomerid() != null
                    && !inputBean.getMerchantcustomerid().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    HeadMerchantDAO dao = new HeadMerchantDAO();
                    Ipgsystemaudit audit = Common.makeAudittrace(request,
                            TaskVarList.UPDATE_TASK,
                            PageVarList.HEAD_MERCHANT_MGT_PAGE,
                            SectionVarList.MERCHANTMANAGEMENT, "HeadMerchant Updated",
                            null);
                    message = dao.updateHeadMerchant(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("HeadMerchant " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(HeadMerchantSecAction.class.getName()).log(Level.SEVERE,
                    null, ex);
            addActionError("HeadMerchant " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String delete() {
        System.out.println("called HeadMerchantAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HeadMerchantDAO dao = new HeadMerchantDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request,
                    TaskVarList.DELETE_TASK, PageVarList.HEAD_MERCHANT_MGT_PAGE,
                    SectionVarList.MERCHANTMANAGEMENT, "HeadMerchant Deleted", null);
            message = dao.deleteHeadMerchant(inputBean, audit);
            if (message.isEmpty()) {
                message = "HeadMerchant " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(HeadMerchantSecAction.class.getName()).log(Level.SEVERE, null, e);
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String viewaddprofile() {
        System.out.println("called HeadMerchantAction :addprofile");
        try {

            CommonDAO commonDAO = new CommonDAO();
            HeadMerchantDAO dao = new HeadMerchantDAO();
            inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
            inputBean.setCountryList(dao.getDefultCountryList());
            inputBean.setAuthreqstatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));
            inputBean.setTxninitstatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_AUTH_REQUIRED));
            inputBean.setRiskprofileList(commonDAO.getRiskProfileList());
            inputBean.setSecuritymechanism("Digitally Sign");

        } catch (Exception ex) {
            addActionError("HeadMerchantAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(HeadMerchantSecAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "viewaddprofile";
    }

    private String validateInputs() {
        String message = "";

        if (inputBean.getMerchantcustomerid() == null
                || inputBean.getMerchantcustomerid().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_MERCHANT_CUSTOMER_CODE;
        } else if (inputBean.getMerchantid().trim() == null
                || inputBean.getMerchantid().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_MERCHANT_ID;            
        }else if (!isValidMerchantID(inputBean.getMerchantid())) {
            message = MessageVarList.MERCHANT_ID_INVALID;                       
        } else if (inputBean.getMerchantname() == null
                || inputBean.getMerchantname().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_MERCHANT_NAME;
        } else if (inputBean.getAddress1() == null
                || inputBean.getAddress1().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_ADDRESS1;
        } else if (inputBean.getCity().trim() == null
                || inputBean.getCity().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_CITY;
        } else if (inputBean.getMobile().trim() == null
                || inputBean.getMobile().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_MOBILE;
        } else if (inputBean.getTel().trim() == null
                || inputBean.getTel().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_TEL_NO;
        } else if (inputBean.getFax().trim() == null
                || inputBean.getFax().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_FAX;
        } else if (inputBean.getEmail().trim() == null
                || inputBean.getEmail().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_EMAIL;
        } else if (inputBean.getPrimaryurl().trim() == null
                || inputBean.getPrimaryurl().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_PRIMARY_URL;
        } else if (inputBean.getDynamicreturnsuccessurl().trim() == null
                || inputBean.getDynamicreturnsuccessurl().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_SUCCESS_URL;
        } else if (inputBean.getDynamicreturnerrorurl().trim() == null
                || inputBean.getDynamicreturnerrorurl().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_ERROR_URL;
        } else if (inputBean.getDateofregistry().trim() == null
                || inputBean.getDateofregistry().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_DATE_OF_REGISTRY;
        } else if (inputBean.getDateofexpiry().trim() == null
                || inputBean.getDateofexpiry().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_DATE_OF_EXPIARY;
        } else if (inputBean.getStatus().trim() != null
                && inputBean.getStatus().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_STATUS;
        } else if (inputBean.getContactperson().trim() == null
                || inputBean.getContactperson().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_CONTACT_PERSON;
        } else if (inputBean.getSecuritymechanism().trim().toString() == null
                || inputBean.getSecuritymechanism().trim().toString().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_SECURITY_MECHANISM;
        } else if (inputBean.getRiskprofile().trim() != null
                && inputBean.getRiskprofile().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_RISK_PROFILE;
        } else if (inputBean.getAuthreqstatus().trim() != null
                && inputBean.getAuthreqstatus().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_AUTH_REQUIRED_STATUS;
        } else if (inputBean.getTxninitstatus().trim() != null
                && inputBean.getTxninitstatus().trim().isEmpty()) {
            message = MessageVarList.HM_MGT_EMPTY_TXN_INIT_STATUS;

        } else {
            if (!Validation.isSpecailCharacter(inputBean.getMerchantcustomerid())) {
                message = MessageVarList.HM_MGT_ERROR_MERCHANT_CUSTOMER_CODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean
                    .getMerchantid())) {
                message = MessageVarList.HM_MGT_ERROR_MERCHAND_ID_INVALID;
//            }else if (!Validation.isSpecailCharacter(inputBean
//                    .getAddress1())) {
//                message = MessageVarList.HM_MGT_ERROR_ADDRESS1_INVALID;
//            }
//            else if (!Validation.isSpecailCharacter(inputBean
//                    .getAddress2())) {
//                message = MessageVarList.HM_MGT_ERROR_ADDRESS2_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean
                    .getCity())) {
                message = MessageVarList.HM_MGT_ERROR_CITY_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean
                    .getStatecode())) {
                message = MessageVarList.HM_MGT_ERROR_STATE_CODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean
                    .getPostalcode())) {
                message = MessageVarList.HM_MGT_ERROR_POSTAL_CODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean
                    .getProvince())) {
                message = MessageVarList.HM_MGT_ERROR_PROVINCE_INVALID;
            } //             else if (!Validation.isAlphaNumeric(inputBean
            //                    .getMobile())) {
            //                message = MessageVarList.HM_MGT_ERROR_MOBILE_INVALID;
            //            }
            //             else if (!Validation.isAlphaNumeric(inputBean
            //                    .getTel())) {
            //                message = MessageVarList.HM_MGT_ERROR_TEL_NO_INVALID;
            //            }
            //             else if (!Validation.isAlphaNumeric(inputBean
            //                    .getFax())) {
            //                message = MessageVarList.HM_MGT_ERROR_FAX_INVALID;
            //            }
            else if (!Validation.isValidEmail(inputBean
                    .getEmail())) {
                message = MessageVarList.HM_MGT_ERROR_EMAIL_INVALID;
            } else if (!Validation.isValidUrl(inputBean
                    .getPrimaryurl())) {
                message = MessageVarList.HM_MGT_ERROR_PRIMARY_URL_INVALID;
            } else if (!Validation.isValidUrl(inputBean
                    .getSecondaryurl())) {
                message = MessageVarList.HM_MGT_ERROR_SECONDARY_URL_INVALID;
            } else if (!Validation.isValidUrl(inputBean
                    .getDynamicreturnsuccessurl())) {
                message = MessageVarList.HM_MGT_ERROR_SUCCESS_URL_INVALID;
            } else if (!Validation.isValidUrl(inputBean
                    .getDynamicreturnerrorurl())) {
                message = MessageVarList.HM_MGT_ERROR_ERROR_URL_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean
                    .getContactperson())) {
                message = MessageVarList.HM_MGT_ERROR_CONTACT_PERSON_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean
                    .getRemarks())) {
                message = MessageVarList.HM_MGT_ERROR_REMARKS_INVALID;
            }

        }
        return message;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.HEAD_MERCHANT_MGT_PAGE;
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
        } else if ("viewaddprofile".equals(method)) {
            task = TaskVarList.ADD_TASK;
        } else if ("viewupdateprofile".equals(method)) {
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

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(
                PageVarList.HEAD_MERCHANT_MGT_PAGE, request);

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

    public KeystoreCreatorBean loadBeanToMakeKeystore(HeadMerchantInputBean inputBean, String keyStorePassword,Ipgcommonfilepath ipgcommonfilepath) throws Exception {
        KeystoreCreatorBean bean = new KeystoreCreatorBean();
        String fileSeparator = System.getProperty("file.separator");
        try {
            /**
             * ******** start set bean values ********************
             */
            bean.setAlias(inputBean.getMerchantid());
            bean.setName(inputBean.getMerchantid());
            bean.setOrganization(inputBean.getMerchantname());
            bean.setCity(inputBean.getCity());
            bean.setOrganizationalUnitName(inputBean.getMerchantname());

            if (inputBean.getProvince() == null) {
                bean.setProvince("");
            } else {
                bean.setProvince(inputBean.getProvince());
            }

            HeadMerchantDAO dao = new HeadMerchantDAO();
            Ipgcountry country = dao.getCountryByCode(inputBean.getCountry());
            String countrycode = country.getCountryisocode();
            String c = countrycode.substring(0, 2);
            bean.setTwo_letter_countrycode(c);

            String aliaspass = inputBean.getMerchantid() + "@123";
            String keystorename = inputBean.getMerchantid() + ".jks";
            String certificatename = inputBean.getMerchantid() + ".cer";

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
    public boolean isValidMerchantID(String merchantId){
        boolean val = false;
        if(merchantId.length() == 15){
        val = true;
        }
        return val;
    }
}
