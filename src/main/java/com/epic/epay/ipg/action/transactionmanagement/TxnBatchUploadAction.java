/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.AutoFileBean;
import com.epic.epay.ipg.bean.transactionmanagement.InstantFileBean;
import com.epic.epay.ipg.bean.transactionmanagement.ManualFileBean;
import com.epic.epay.ipg.bean.transactionmanagement.TxnBatchUploadDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.TxnBatchUploadInputBean;
import com.epic.epay.ipg.dao.transactionmanagement.TxnBatchUploadDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.CreditCardValidator;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.StatusVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author asela
 */
public class TxnBatchUploadAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    TxnBatchUploadInputBean inputBean = new TxnBatchUploadInputBean();
    TxnBatchUploadDAO dao;
    private int fileLines;
    private String batchFileId = "";
    private String errorMessage = "";
    private String errorStatus;
    private String content = "";
    private List<String> contentList = new ArrayList<String>();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called TxnBatchUploadAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.TRANSACTION_BATCHUPLOAD;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Upload".equals(method)) {
            task = TaskVarList.UPLOAD_TASK;
        } else if ("Search".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Show".equals(method)) {
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
        System.out.println("called TxnBatchUploadAction : view");
        try {
            if (this.applyUserPrivileges()) {

                List<TxnBatchUploadDataBean> txntypelist = new ArrayList<TxnBatchUploadDataBean>();
                TxnBatchUploadDataBean bean1 = new TxnBatchUploadDataBean();
                TxnBatchUploadDataBean bean2 = new TxnBatchUploadDataBean();
                TxnBatchUploadDataBean bean3 = new TxnBatchUploadDataBean();

                bean1.setTxntypecode("INST");
                bean1.setTxntypedes("Instant");

                bean2.setTxntypecode("MANU");
                bean2.setTxntypedes("Manual");

                bean3.setTxntypecode("AUTO");
                bean3.setTxntypedes("Auto");

                txntypelist.add(bean1);
                txntypelist.add(bean2);
                txntypelist.add(bean3);

                inputBean.setTxnTypeList(txntypelist);

            } else {
                result = "loginpage";
            }

        } catch (Exception ex) {
            addActionError("Transaction batch upload  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TxnBatchUploadAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String Upload() {
        String result = "message";
        System.out.println("called TxnBatchUploadAction : upload");
        dao = new TxnBatchUploadDAO();
        try {
            String msg = "";
            msg = validateBean(inputBean);
            if (msg.isEmpty()) {
                //set values to bean
                this.setValuesToBean();

                if (isValidFile(inputBean.getFileFileName(), inputBean.getPrefixCode())) {

                    if (!dao.isFileExist(inputBean.getFilename(), StatusVarList.BATCH_FILE_UPLOAD_COMPLETE)) {
                        String filePath = "";
                        if (getOS_Type().equals("WINDOWS")) {
                            filePath = dao.getFilePathBySystemOSType(getOS_Type()) + inputBean.getPrefix();
                        } else if (getOS_Type().equals("LINUX")) {
                            filePath = dao.getFilePathBySystemOSType(getOS_Type()) + inputBean.getPrefix();
                        } else if (getOS_Type().equals("SUNOS")) {
                            filePath = dao.getFilePathBySystemOSType(getOS_Type()) + inputBean.getPrefix();
                        }

                        if (!filePath.equals("notassign")) {

                            this.createDirectory(filePath);

                            List<TxnBatchUploadDataBean> list = dao.getStatusListWithoutComp();
                            String uploadStatus = "";
                            int i = 0;

                            for (TxnBatchUploadDataBean s : list) {
                                i++;
                                uploadStatus += s.getStatuscode();
                                if (i != list.size()) {
                                    uploadStatus += "','";
                                }
                            }

                            if (!dao.isFileExist(inputBean.getFilename(), uploadStatus)) {
                                // Batch file upload initiated.
                                HttpServletRequest request = ServletActionContext.getRequest();
                                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPLOAD_TASK, PageVarList.TRANSACTION_BATCHUPLOAD, SectionVarList.MERCHANTMANAGEMENT, "Transaction Batch Upload Process Start", null);
                                dao.insertTxnBatchUploadData(inputBean, audit);
                                String fileid = dao.getFileIdByFileName(inputBean.getFileFileName());
                                inputBean.setFileid(fileid);

                                // batch file upload process initiated 
                                File destFile = new File(filePath, inputBean.getFileFileName());
                                FileUtils.copyFile(inputBean.getFile(), destFile);

                                if (readFile(destFile.getAbsolutePath(), inputBean.getTxncategory())) {
                                    // Batch file upload completed.

                                    audit = Common.makeAudittrace(request, TaskVarList.UPLOAD_TASK, PageVarList.TRANSACTION_BATCHUPLOAD, SectionVarList.MERCHANTMANAGEMENT, "Transaction Batch Upload Process Completed", null);
                                    if (!inputBean.getFileid().isEmpty()) {
                                        dao.updateTxnBatchUploadData(inputBean, audit);
                                    }
                                    addActionMessage(MessageVarList.FILE_UPLOAD_SUCCESS);
                                } else {

                                    audit = Common.makeAudittrace(request, TaskVarList.UPLOAD_TASK, PageVarList.TRANSACTION_BATCHUPLOAD, SectionVarList.MERCHANTMANAGEMENT, "Transaction Batch Upload Process Error Occured", null);
                                    if (!inputBean.getFileid().isEmpty()) {
                                        dao.updateTxnBatchUploadErrorData(inputBean, audit);
                                    }
                                    addActionError(errorMessage);
                                }

                            } else {
                                addActionError(MessageVarList.FILE_ALREADY_UPLOADED);
                            }
                        } else {
                            addActionError(MessageVarList.FILE_PATH_EMPTY);
                        }

                    } else {
                        addActionError(MessageVarList.FILE_ALREADY_UPLOADED);
                    }

                } else {
                    addActionError(MessageVarList.INVALID_FILE);
                }

            } else {
                if (msg.equals(MessageVarList.FILE_TYPE_INVALID) && !getActionErrors().isEmpty()) {
                    //   addActionError(msg);
                } else {
                    addActionError(msg);
                }
            }

        } catch (Exception e) {
            addActionError("Transaction Batch Upload  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TxnBatchUploadAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public void setValuesToBean() throws Exception {
        try {
            inputBean.setPrefix(inputBean.getTransactiontype());
            inputBean.setFileid("");
            inputBean.setFilename(inputBean.getFileFileName());
            inputBean.setTxncategory(inputBean.getPrefix());
            if (inputBean.getPrefix().equals(StatusVarList.BATCH_TRANSACTION_TYPE_INSTANT)) {
                inputBean.setPrefixCode(StatusVarList.BATCH_TRANSACTION_TYPE_INSTANT_PREFIX);
            } else if (inputBean.getPrefix().equals(StatusVarList.BATCH_TRANSACTION_TYPE_AUTO)) {
                inputBean.setPrefixCode(StatusVarList.BATCH_TRANSACTION_TYPE_AUTO_PREFIX);
            } else if (inputBean.getPrefix().equals(StatusVarList.BATCH_TRANSACTION_TYPE_MANUAL)) {
                inputBean.setPrefixCode(StatusVarList.BATCH_TRANSACTION_TYPE_MANUAL_PREFIX);
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public String validateBean(TxnBatchUploadInputBean bean) throws Exception {
        String msg = "";
        try {
            if (null == bean.getFileFileName()) {
                msg = MessageVarList.FILE_TYPE_INVALID;
            } else if (null == bean.getTransactiontype() || bean.getTransactiontype().equals("") || bean.getTransactiontype().length() <= 0) {
                msg = MessageVarList.FILE_TYPE_EMPTY;
            } else if (null == bean.getFileFileName() || bean.getFileFileName().equals("") || bean.getFileFileName().length() <= 0) {
                msg = MessageVarList.FILE_NAME_EMPTY;
            }
        } catch (Exception e) {
            throw e;
        }
        return msg;
    }

    private void createDirectory(String path) throws Exception {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public String getOS_Type() throws Exception {
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

    public boolean isValidFile(String fileName, String prefix) throws Exception {
        boolean status = false;
        try {
            if (fileName.startsWith(prefix)) {
                status = true;
            }
        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.TRANSACTION_BATCHUPLOAD, request);

        inputBean.setVupload(true);
        inputBean.setVview(false);
        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPLOAD_TASK)) {
                    inputBean.setVupload(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                }
            }
        }
        return true;
    }

    public String Search() {
        System.out.println("called TxnBatchUploadAction : Search");
        try {
            //           if (inputBean.isSearch()) {

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }
            dao = new TxnBatchUploadDAO();
            List<TxnBatchUploadDataBean> dataList = dao.getUploadedFileList(rows, from, orderBy);

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
            Logger.getLogger(TxnBatchUploadAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Txn Batch Upload");
        }
        return "list";
    }

    // get by servlet class 
    // read transaction batch file
    public boolean readFile(String filePath, String transactionCategory) throws Exception {
        String value = "";
        boolean status = true;
        boolean emptyLineStatus = false;
        BufferedReader reader = null;
        int emptylinecount = 0;
        int fileLine = 0;
        List<InstantFileBean> listInstant = null;
        List<AutoFileBean> listAuto = null;
        List<ManualFileBean> listManual = null;
        dao = new TxnBatchUploadDAO();
        if (transactionCategory.equals(StatusVarList.BATCH_TRANSACTION_TYPE_INSTANT)) {
            listInstant = new ArrayList<InstantFileBean>();
        } else if (transactionCategory.equals(StatusVarList.BATCH_TRANSACTION_TYPE_AUTO)) {
            listAuto = new ArrayList<AutoFileBean>();
        } else if (transactionCategory.equals(StatusVarList.BATCH_TRANSACTION_TYPE_MANUAL)) {
            listManual = new ArrayList<ManualFileBean>();
        }
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            // read line by line 
            fileLines = 0;

            batchFileId = dao.getFileIdByFileName(inputBean.getFilename());
            while ((value = reader.readLine()) != null) {
                if (value.trim().length() == 0) {
                    // catch blank lines
                    emptyLineStatus = true;
                    emptylinecount += 1;
                    continue;  // Skip blank lines  
                }
                fileLines += 1;

                System.out.println("*** " + value);
                // validate line by line
                if (transactionCategory.equals(StatusVarList.BATCH_TRANSACTION_TYPE_INSTANT)) {
                    status = this.validateInstaintFile(value);

                    // reject file
                    if (emptyLineStatus && status) {
                        errorMessage = MessageVarList.BATCH_FILE_EMPTY_LINE_DETECTED;
                        errorStatus = MessageVarList.EMPTY_LINE_DETECTED;
                        status = false;
                        break;
                    }
                    // remove empty lines
                    if (emptyLineStatus || value.equals("")) {
                        fileLines -= 1;
                    }
                    if (status) {
                        insertInstantValueDataIntoList(value, listInstant);
                    } else {
                        if (!emptyLineStatus && !status) {
                            break;
                        }
                    }
                } else if (transactionCategory.equals(StatusVarList.BATCH_TRANSACTION_TYPE_AUTO)) {
                    status = this.validateAutoFile(value);
                    // reject file
                    if (emptyLineStatus && status) {
                        errorMessage = MessageVarList.BATCH_FILE_EMPTY_LINE_DETECTED;
                        errorStatus = MessageVarList.EMPTY_LINE_DETECTED;
                        status = false;
                        break;
                    }
                    // remove empty lines
                    if (emptyLineStatus || value.equals("")) {
                        fileLines -= 1;
                    }
                    if (status) {
                        insertAutoValueDataIntoList(value, listAuto);
                    } else {
                        if (!emptyLineStatus && !status) {
                            break;
                        }

                    }
                } else if (transactionCategory.equals(StatusVarList.BATCH_TRANSACTION_TYPE_MANUAL)) {
                    status = this.validateManualFile(value);
                    // reject file
                    if (emptyLineStatus && status) {
                        errorMessage = MessageVarList.BATCH_FILE_EMPTY_LINE_DETECTED;
                        errorStatus = MessageVarList.EMPTY_LINE_DETECTED;
                        status = false;
                        break;
                    }
                    // remove empty lines
                    if (emptyLineStatus || value.equals("")) {
                        fileLines -= 1;
                    }
                    if (status) {
                        insertManualValueDataIntoList(value, listManual);
                    } else {
                        if (!emptyLineStatus && !status) {
                            break;
                        }
                    }
                }
            }

            BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            while ((value = reader1.readLine()) != null) {
                fileLine += 1;
            }

            boolean notemptyfile = true;
            if (emptylinecount == fileLine) {
                if (errorMessage.equals("") || null == errorMessage) {
                    errorMessage = MessageVarList.BATCH_FILE_LENTH_ERROR;
                    errorStatus = MessageVarList.LENTH_ERROR;
                    notemptyfile = false;
                    status = false;
                }
            }
            if (status && notemptyfile) {
                if (transactionCategory.equals(StatusVarList.BATCH_TRANSACTION_TYPE_INSTANT)) {
                    if (fileLines == listInstant.size()) {
                        // set transaction id for all bean 
                        for (InstantFileBean bean : listInstant) {
                            bean.setTransactionId(createTransactionId());
                        }

                        int insertTransactionData = dao.insertInstantTransactionData(listInstant);
                        int insertBatchTransactionData = 0;

                        if (insertTransactionData > 0) {
                            insertBatchTransactionData = dao.insertInstantBatchTransactionData(listInstant);
                        }
                        if (insertBatchTransactionData > 0) {
                            status = true;
                        }
                    } else {
                        if (errorMessage.equals("") || null == errorMessage) {
                            errorMessage = MessageVarList.BATCH_FILE_LENTH_ERROR;
                            errorStatus = MessageVarList.LENTH_ERROR;
                        }
                    }
                } else if (transactionCategory.equals(StatusVarList.BATCH_TRANSACTION_TYPE_AUTO)) {
                    if (fileLines == listAuto.size()) {
                        // set transaction id for all bean 
                        for (AutoFileBean bean : listAuto) {
                            bean.setTransactionId(createTransactionId());
                        }

                        int insertTransactionData = dao.insertAutoTransactionData(listAuto);
                        int insertBatchTransactionData = 0;

                        if (insertTransactionData > 0) {
                            insertBatchTransactionData = dao.insertAutoBatchTransactionData(listAuto);
                        }
                        if (insertBatchTransactionData > 0) {
                            status = true;
                        }
                    } else {
                        if (errorMessage.equals("") || null == errorMessage) {
                            errorMessage = MessageVarList.BATCH_FILE_LENTH_ERROR;
                            errorStatus = MessageVarList.LENTH_ERROR;
                        }
                    }

                } else if (transactionCategory.equals(StatusVarList.BATCH_TRANSACTION_TYPE_MANUAL)) {

                    if (fileLines == listManual.size()) {
                        // set transaction id for all bean 
                        for (ManualFileBean bean : listManual) {
                            bean.setTransactionId(createTransactionId());
                        }

                        int insertTransactionData = dao.insertManualTransactionData(listManual);
                        int insertBatchTransactionData = 0;

                        if (insertTransactionData > 0) {
                            insertBatchTransactionData = dao.insertManualBatchTransactionData(listManual);
                        }
                        if (insertBatchTransactionData > 0) {
                            status = true;
                        }
                    } else {
                        if (errorMessage.equals("") || null == errorMessage) {
                            errorMessage = MessageVarList.BATCH_FILE_LENTH_ERROR;
                            errorStatus = MessageVarList.LENTH_ERROR;
                        }
                    }

                }
            }
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            status = false;
            throw e;
        } finally {
            if (null != reader) {
                reader.close();
            }
        }
        return status;
    }

    // validate instant type file
    public boolean validateInstaintFile(String value) throws Exception {
        boolean status = true;
        dao = new TxnBatchUploadDAO();
        try {

            StringTokenizer tokenizer = new StringTokenizer(value, "|");
            if (tokenizer.countTokens() == StatusVarList.BATCH_TRANSACTION_TYPE_INSTANT_LENGTH) {

                String[] array = new String[tokenizer.countTokens()];
                // assign tokenizer values to array
                while (tokenizer.hasMoreTokens()) {
                    for (int i = 0; i < array.length; i++) {
                        array[i] = tokenizer.nextToken();
                    }
                }

                CreditCardValidator creaditCardValidator = new CreditCardValidator();
                if (!creaditCardValidator.luhnValidate(array[0])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_LUHNA_VALIDATE_FAILED;
                    errorStatus = MessageVarList.LUHNA_VALIDATE_FAILED;
                } else if (!Validation.isNonNumericNonSpecialString(array[1])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_CARD_HOLDER_NAME_ERROR;
                    errorStatus = MessageVarList.CARD_HOLDER_NAME_ERROR;
                } else if (!Validation.isNumeric(array[2]) || array[2].length() != 4) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_EXPIRY_DATE_INVALID;
                    errorStatus = MessageVarList.EXPIRY_DATE_INVALID;
                } else if (!Validation.checkNIC(array[3])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_NIC_INVALID;
                    errorStatus = MessageVarList.NIC_INVALID;
                } else if (!Validation.isDecimalNumeric(array[4])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_TRANSACTION_AMOUNT_ERROR;
                    errorStatus = MessageVarList.TRANSACTION_AMOUNT_ERROR;
                } else if (!Validation.isNumeric(array[5])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_CURRENCY_CODE_ERROR;
                    errorStatus = MessageVarList.CURRENCY_CODE_ERROR;
                } else if (!dao.isValidCurrency(array[5])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_CURRENCY_CODE_ERROR;
                    errorStatus = MessageVarList.CURRENCY_CODE_ERROR;
                } else if (!Validation.isNumeric(array[6]) || array[6].length() > 15) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_MERCHANT_ID_ERROR;
                    errorStatus = MessageVarList.MERCHANT_ID_ERROR;
                } else if (!dao.isValidMerchant(array[6])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_MERCHANT_ID_ERROR;
                    errorStatus = MessageVarList.MERCHANT_ID_ERROR;
                } else if (!Validation.isNonNumericNonSpecialString(array[7])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_CARD_ASSOCIATION_INVALID;
                    errorStatus = MessageVarList.CARD_ASSOCIATION_INVALID;
                }
            } else {
                status = false;
                errorMessage = MessageVarList.BATCH_FILE_LENTH_ERROR;
                errorStatus = MessageVarList.LENTH_ERROR;
            }

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    // validate manual type file.
    public boolean validateManualFile(String value) throws Exception {
        boolean status = true;
        dao = new TxnBatchUploadDAO();
        try {
            StringTokenizer tokenizer = new StringTokenizer(value, "|");
            if (tokenizer.countTokens() == StatusVarList.BATCH_TRANSACTION_TYPE_MANUAL_LENGTH) {

                String[] array = new String[tokenizer.countTokens()];
                // assign tokenizer values to array
                while (tokenizer.hasMoreTokens()) {
                    for (int i = 0; i < array.length; i++) {
                        array[i] = tokenizer.nextToken();
                    }
                }

                CreditCardValidator creaditCardValidator = new CreditCardValidator();
                if (!creaditCardValidator.luhnValidate(array[0])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_LUHNA_VALIDATE_FAILED;
                    errorStatus = MessageVarList.LUHNA_VALIDATE_FAILED;
                } else if (!Validation.isNonNumericNonSpecialString(array[1])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_CARD_HOLDER_NAME_ERROR;
                    errorStatus = MessageVarList.CARD_HOLDER_NAME_ERROR;
                } else if (!Validation.isNumeric(array[2]) || array[2].length() != 4) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_EXPIRY_DATE_INVALID;
                    errorStatus = MessageVarList.EXPIRY_DATE_INVALID;
                } else if (!Validation.checkNIC(array[3])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_NIC_INVALID;
                    errorStatus = MessageVarList.NIC_INVALID;
                } else if (!Validation.isDecimalNumeric(array[4])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_TRANSACTION_AMOUNT_ERROR;
                    errorStatus = MessageVarList.TRANSACTION_AMOUNT_ERROR;
                } else if (!Validation.isNumeric(array[5])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_CURRENCY_CODE_ERROR;
                    errorStatus = MessageVarList.CURRENCY_CODE_ERROR;
                } else if (!dao.isValidCurrency(array[5])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_CURRENCY_CODE_ERROR;
                    errorStatus = MessageVarList.CURRENCY_CODE_ERROR;
                } else if (!Validation.isNumeric(array[6]) || array[6].length() > 15) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_MERCHANT_ID_ERROR;
                    errorStatus = MessageVarList.MERCHANT_ID_ERROR;
                } else if (!dao.isValidMerchant(array[6])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_MERCHANT_ID_ERROR;
                    errorStatus = MessageVarList.MERCHANT_ID_ERROR;
                } else if (!Validation.isNonNumericNonSpecialString(array[7])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_CARD_ASSOCIATION_INVALID;
                    errorStatus = MessageVarList.CARD_ASSOCIATION_INVALID;
                } else if (!Validation.isAllowNumericNonSpecialString(array[8])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_NEXT_TRANSACTION_DATE_ERROR;
                    errorStatus = MessageVarList.NEXT_TRANSACTION_DATE_ERROR;
                }

            } else {
                status = false;
                errorMessage = MessageVarList.BATCH_FILE_LENTH_ERROR;
                errorStatus = MessageVarList.LENTH_ERROR;
            }

        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    // validate auto type file.
    public boolean validateAutoFile(String value) throws Exception {
        boolean status = true;
        try {
            StringTokenizer tokenizer = new StringTokenizer(value, "|");
            if (tokenizer.countTokens() == StatusVarList.BATCH_TRANSACTION_TYPE_AUTO_LENGTH) {

                String[] array = new String[tokenizer.countTokens()];
                // assign tokenizer values to array
                while (tokenizer.hasMoreTokens()) {
                    for (int i = 0; i < array.length; i++) {
                        array[i] = tokenizer.nextToken();
                    }
                }

                CreditCardValidator creaditCardValidator = new CreditCardValidator();
                if (!creaditCardValidator.luhnValidate(array[0])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_LUHNA_VALIDATE_FAILED;
                    errorStatus = MessageVarList.LUHNA_VALIDATE_FAILED;
                } else if (!Validation.isNonNumericNonSpecialString(array[1])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_CARD_HOLDER_NAME_ERROR;
                    errorStatus = MessageVarList.CARD_HOLDER_NAME_ERROR;
                } else if (!Validation.isNumeric(array[2]) || array[2].length() != 4) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_EXPIRY_DATE_INVALID;
                    errorStatus = MessageVarList.EXPIRY_DATE_INVALID;
                } else if (!Validation.checkNIC(array[3])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_NIC_INVALID;
                    errorStatus = MessageVarList.NIC_INVALID;
                } else if (!Validation.isDecimalNumeric(array[4])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_TRANSACTION_AMOUNT_ERROR;
                    errorStatus = MessageVarList.TRANSACTION_AMOUNT_ERROR;
                } else if (!Validation.isNumeric(array[5])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_CURRENCY_CODE_ERROR;
                    errorStatus = MessageVarList.CURRENCY_CODE_ERROR;
                } else if (!dao.isValidCurrency(array[5])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_CURRENCY_CODE_ERROR;
                    errorStatus = MessageVarList.CURRENCY_CODE_ERROR;
                } else if (!Validation.isNumeric(array[6])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_NUM_OF_INSTALLMENT_ERROR;
                    errorStatus = MessageVarList.NUM_OF_INSTALLMENT_ERROR;
                } else if (!Validation.isNumeric(array[7])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_INSTALLMENT_PERIOD_ERROR;
                    errorStatus = MessageVarList.INSTALLMENT_PERIOD_ERROR;
                } else if (!Validation.isNumeric(array[8])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_COUNT_VALUE_ERROR;
                    errorStatus = MessageVarList.COUNT_VALUE_ERROR;
                } else if (!Validation.isNumeric(array[9]) || array[9].length() > 15) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_MERCHANT_ID_ERROR;
                    errorStatus = MessageVarList.MERCHANT_ID_ERROR;
                } else if (!dao.isValidMerchant(array[9])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_MERCHANT_ID_ERROR;
                    errorStatus = MessageVarList.MERCHANT_ID_ERROR;

                } else if (!Validation.isNonNumericNonSpecialString(array[10])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_CARD_ASSOCIATION_INVALID;
                    errorStatus = MessageVarList.CARD_ASSOCIATION_INVALID;
                } else if (!Validation.isAllowNumericNonSpecialString(array[11])) {
                    status = false;
                    errorMessage = MessageVarList.BATCH_FILE_NEXT_TRANSACTION_DATE_ERROR;
                    errorStatus = MessageVarList.NEXT_TRANSACTION_DATE_ERROR;
                }

            } else {
                status = false;
                errorMessage = MessageVarList.BATCH_FILE_LENTH_ERROR;
                errorStatus = MessageVarList.LENTH_ERROR;
            }
        } catch (Exception e) {
            status = false;
            throw e;
        }
        return status;
    }

    // insert instant type data into list
    public void insertInstantValueDataIntoList(String value, List<InstantFileBean> list) throws Exception {
        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession(false);
            Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);

            StringTokenizer tokenizer = new StringTokenizer(value, "|");

            InstantFileBean bean = new InstantFileBean();
            bean.setFileId(batchFileId);
            bean.setCardNo(tokenizer.nextToken());
            bean.setCardHolderName(tokenizer.nextToken());
            bean.setExpiryDate(tokenizer.nextToken());
            bean.setNic(tokenizer.nextToken());
            bean.setTransactionAmount(tokenizer.nextToken());
            bean.setCurrency(tokenizer.nextToken());
            bean.setMerchantId(tokenizer.nextToken());
            bean.setCardAssociation(tokenizer.nextToken());
            bean.setRemarks(MessageVarList.BATCH_FILE_TRANSACTION_INIT);
            bean.setLastUpdatedUser(user.getUsername());
            list.add(bean);
        } catch (Exception e) {
            throw e;
        }
    }

    // insert auto type data into list
    public void insertAutoValueDataIntoList(String value, List<AutoFileBean> list) throws Exception {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession(false);
            Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);

            StringTokenizer tokenizer = new StringTokenizer(value, "|");

            AutoFileBean bean = new AutoFileBean();
            bean.setFileId(batchFileId);
            bean.setCardNo(tokenizer.nextToken());
            bean.setCardHolderName(tokenizer.nextToken());
            bean.setExpiryDate(tokenizer.nextToken());
            bean.setNic(tokenizer.nextToken());
            bean.setTransactionAmount(tokenizer.nextToken());
            bean.setCurrency(tokenizer.nextToken());
            bean.setNumOfInstallment(tokenizer.nextToken());
            bean.setRecuringPeriod(tokenizer.nextToken());
            bean.setCount(tokenizer.nextToken());
            bean.setMerchantId(tokenizer.nextToken());
            bean.setCardAssociation(tokenizer.nextToken());
            bean.setNextTransactionDate(tokenizer.nextToken());
            bean.setRemarks(MessageVarList.BATCH_FILE_TRANSACTION_INIT);
            bean.setLastUpdatedUser(user.getUsername());
            list.add(bean);
        } catch (Exception e) {
            throw e;
        }
    }

    // insert manual type data into list
    public void insertManualValueDataIntoList(String value, List<ManualFileBean> list) throws Exception {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession(false);
            Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);

            StringTokenizer tokenizer = new StringTokenizer(value, "|");

            ManualFileBean bean = new ManualFileBean();

            bean.setFileId(batchFileId);
            bean.setCardNo(tokenizer.nextToken());
            bean.setCardHolderName(tokenizer.nextToken());
            bean.setExpiryDate(tokenizer.nextToken());
            bean.setNic(tokenizer.nextToken());
            bean.setTransactionAmount(tokenizer.nextToken());
            bean.setCurrency(tokenizer.nextToken());
            // bean.setNumOfInstallment(tokenizer.nextToken());
            // bean.setRecuringPeriod(tokenizer.nextToken());
            // bean.setCount(tokenizer.nextToken());
            bean.setMerchantId(tokenizer.nextToken());
            bean.setCardAssociation(tokenizer.nextToken());
            bean.setNextTransactionDate(tokenizer.nextToken());
            bean.setRemarks(MessageVarList.BATCH_FILE_TRANSACTION_INIT);
            bean.setLastUpdatedUser(user.getUsername());
            list.add(bean);
        } catch (Exception e) {
            throw e;
        }
    }
    // set date in yyyy-mm-dd format

    public String getExpiryDate(String date) {

        String month = date.substring(0, 2);
        String year1 = date.substring(2, 4);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date currentdate = new Date();
        String currentYear = sdf.format(currentdate);
        String currentYear24 = currentYear.substring(2, 4);
        int year = Integer.parseInt(currentYear) - Integer.parseInt(currentYear24);
        year = year + Integer.parseInt(year1);
        return Integer.toString(year) + "/" + month + "/" + "01";
    }

    // // generate transaction id
    private String createTransactionId() throws Exception {
        String tId;
        try {
            
            String date = createCurrentDateTime();
            String randm = TxnBatchUploadAction.generatePin();           
            
            tId = (date + randm);
            
        } catch (Exception ex) {
            throw ex;
        }
        return tId;
    }
    
        //to generate randaom number
    private static final int[] pins = new int[900000];  
   
    static {  
        for (int i = 0; i < pins.length; i++)  
            pins[i] = 100000 + i;  
    }  
   
    private static int pinCount;  
   
    private static final Random random = new Random();  
   
    public static String generatePin() {  
        if (pinCount >= pins.length)  
            throw new IllegalStateException();  
        int index = random.nextInt(pins.length - pinCount) + pinCount;  
        int pin = pins[index];  
        pins[index] = pins[pinCount++];  
        
        return String.valueOf(pin).substring(0, 4);  
    }
    //
    
    public String createCurrentDateTime() throws Exception {
        String currentdatetime = "";
        try {
            String DATE_FORMAT = "yyMMddHHmmss";

            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

            Date date = new Date();
            currentdatetime = sdf.format(date);
        } catch (Exception ex) {
            throw ex;
        }
        return currentdatetime;
    }

    public String Show() {
        String result = "show";
        System.out.println("called TxnBatchUploadAction : show");
        try {
            dao = new TxnBatchUploadDAO();
            List<TxnBatchUploadDataBean> list = dao.getUploadedFileList();

            TxnBatchUploadDataBean newBean = new TxnBatchUploadDataBean();
            for (TxnBatchUploadDataBean bean : list) {
                if (bean.getFileid().equals(inputBean.getFileid())) {
                    newBean = bean;
                }
            }

            String uploadedLocation = "";
            if (getOS_Type().equals("WINDOWS")) {
                uploadedLocation = dao.getFilePathBySystemOSType(getOS_Type()) + newBean.getTxntypecode() + "\\" + newBean.getFilename();
                contentList.clear();
                contentList = readFile(uploadedLocation);
            } else if (getOS_Type().equals("LINUX")) {
                uploadedLocation = dao.getFilePathBySystemOSType(getOS_Type()) + newBean.getTxntypecode() + "/" + newBean.getFilename();
                contentList.clear();
                contentList = readFile(uploadedLocation);
            } else if (getOS_Type().equals("SUNOS")) {
                uploadedLocation = dao.getFilePathBySystemOSType(getOS_Type()) + newBean.getTxntypecode() + "/" + newBean.getFilename();
                contentList.clear();
                contentList = readFile(uploadedLocation);
            }

            if (!contentList.isEmpty()) {
                String[] string = new String[contentList.size()];

                for (int i = 0; i < string.length; i++) {
                    string[i] = contentList.get(i);
                }

                int count = string.length;

                for (int i = string.length - 1; i >= 0; i--) {
                    if (string[i].trim().length() == 0) {
                        count--;
                    }
                }
                int num = count + 1;
                content = "";
                for (int i = string.length - 1; i >= 0; i--) {
                    if (string[i].trim().length() == 0) {
                        content = content + " \n";
                    } else {
                        num--;
                        content = "\n " + num + ") " + string[i] + content + " \n";
                    }
                }

            }

            inputBean.setContent(content);

        } catch (Exception e) {
            Logger.getLogger(TxnBatchUploadAction.class.getName()).log(Level.SEVERE, null, e);
            if (errorMessage.equals(MessageVarList.BATCH_FILE_NOT_FOUND_ERROR)) {
                addActionError(errorMessage);
            } else {
                addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Txn Batch Upload");
            }
        }
        return result;
    }

    public List<String> readFile(String filePath) throws IOException, Exception {
        List<String> list = new ArrayList<String>();
        BufferedReader br = null;

        try {

            String sCurrentLine;

            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            list.clear();
            while ((sCurrentLine = br.readLine()) != null) {
                list.add(sCurrentLine);
            }

        } catch (FileNotFoundException e) {
            errorMessage = MessageVarList.BATCH_FILE_NOT_FOUND_ERROR;
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                throw ex;
            }
        }

        return list;
    }
}
