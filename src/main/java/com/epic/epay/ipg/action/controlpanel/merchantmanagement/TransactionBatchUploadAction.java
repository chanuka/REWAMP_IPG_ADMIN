/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.merchantmanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import com.epic.epay.ipg.action.controlpanel.usermanagement.TaskAction;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.CommonFilePathBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.TransactionBatchUploadInputBean;
import com.epic.epay.ipg.dao.controlpanel.merchantmanagement.TransactionBatchUploadDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 *
 * Created on :Dec 13, 2013, 10:18:26 AM
 *
 * @author :thushanth
 *
 */
public class TransactionBatchUploadAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    private File file;
    private String fileFileName;
    private TransactionBatchUploadInputBean inputBean = new TransactionBatchUploadInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called TransactionBatchUploadAction : execute");
        return SUCCESS;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.MERCHANT_TRANSACTION_BATCH_UPLOAD;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("upload".equals(method)) {
            task = TaskVarList.UPLOAD_TASK;
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

                TransactionBatchUploadDAO dao = new TransactionBatchUploadDAO();
                inputBean.setStatusList(dao.getStatusList(CommonVarList.CREDIT_CARD_BATCH_TXN));

            } else {
                result = "loginpage";
            }

            System.out.println("called TransactionBatchUploadAction :view");


        } catch (Exception ex) {
            addActionError("Batch Upload " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String upload() {
        System.out.println("called TransactionBatchUploadAction : upload");

        try {
            String osType = "";
            CommonFilePathBean url = null;
            TransactionBatchUploadDAO dao = new TransactionBatchUploadDAO();
            osType = TransactionBatchUploadAction.getOS_Type();
            url = dao.getBatchTxnPath(osType);

            String message = this.validateUpload();

            if (message.isEmpty()) {
                String mes2 = this.validatePrefix();
                if (mes2.isEmpty()) {
                    File directory = new File(url.getCcbtxnFiles() + inputBean.getTxnType() + "\\");

                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    File f = new File(url.getCcbtxnFiles() + inputBean.getTxnType(), fileFileName);

                    if (f.exists()) {
                        addActionError("File already exsists.");

                    } else {
                        try {
                            FileUtils.copyFile(file, f);
                            addActionMessage("File succesfully uploaded.");

                            HttpServletRequest request = ServletActionContext.getRequest();
                            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPLOAD_TASK, PageVarList.MERCHANT_TRANSACTION_BATCH_UPLOAD, SectionVarList.MERCHANTMANAGEMENT, "File Uploaded", null);
                            String succesaudit = dao.insertAudit(audit);

                        } catch (Exception e) {
                            addActionError("File upload failed!");
                        }
                    }

                } else {
                    addActionError(mes2);
                }

            } else {
                addActionError(message);
            }

        } catch (Exception e) {
            Logger.getLogger(TransactionBatchUploadAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("File " + MessageVarList.TXN_BATCH_UPLOAD_ERROR);
        }

        return "message";
    }

    private String validateUpload() {
        String message = "";
        if (inputBean.getTxnType().contentEquals("") || inputBean.getTxnType().length() <= 0) {
            message = MessageVarList.TXN_BATCH_UPLOAD_EMPTY_TXN_TYPE;
        } else if (fileFileName == null || fileFileName.length() <= 0) {
            message = MessageVarList.TXN_BATCH_UPLOAD_EMPTY_FILE;
        }
        return message;
    }

    private String validatePrefix() {
        String message = "";

        List<String> prefixes = new ArrayList<String>();
        prefixes.add("auto");
        prefixes.add("manual");
        prefixes.add("instant");

        int pos = fileFileName.lastIndexOf('.');
        String realname = fileFileName.substring(0, pos).toLowerCase();

        if (prefixes.contains(realname)) {
            if ((inputBean.getTxnType().toLowerCase()).equalsIgnoreCase(realname)) {
                return message;
            } else {
                message = "file prefix and transaction type doesn't match";
                return message;
            }
        } else {

            message = "You must upload an file with one of the following Prefixes:";
            for (int i = 0; i < prefixes.size(); i++) {
                if (i == prefixes.size() - 1) {
                    message += " " + prefixes.get(i) + ".";
                    return message;
                }
                message += " " + prefixes.get(i) + ",";
            }

        }
        return message;

    }

    //to get os name (windows or linux)
    public static String getOS_Type() {

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

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.MERCHANT_TRANSACTION_BATCH_UPLOAD, request);

        inputBean.setVupload(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPLOAD_TASK)) {
                    inputBean.setVupload(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                }
            }
        }
        return true;
    }
}
