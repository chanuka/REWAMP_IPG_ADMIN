/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.merchantmanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantCertificateManagerInputBean;
import com.epic.epay.ipg.dao.controlpanel.merchantmanagement.MerchantCertificateManagerDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 *
 * @created on : Nov 22, 2013, 3:33:41 PM
 * @author : Thushanth
 *
 */
public class MerchantCertificateManagerAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    private InputStream fileInputStream;
    private String fileName;
    MerchantCertificateManagerInputBean inputBean = new MerchantCertificateManagerInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() throws Exception {
        System.out.println("called MerchantAddonAction : execute");
        return SUCCESS;
    }

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

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.MERCHANT_CERTIFICATE_MGT;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("download".equals(method)) {
            task = TaskVarList.DOWNLOAD_TASK;
        } else if ("keystoreD".equals(method)) {
            task = TaskVarList.DOWNLOAD_TASK;
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

            } else {
                result = "loginpage";
            }
            System.out.println("called MerchantCertificateMgtAction :view");

        } catch (Exception ex) {
            addActionError("MerchantCertificateMgtAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantCertificateManagerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.MERCHANT_CERTIFICATE_MGT, request);

        inputBean.setVdownload(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.DOWNLOAD_TASK)) {
                    inputBean.setVdownload(false);

                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {

                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                }
            }
        }
        return true;
    }

    public String download() {

        System.out.println("called MerchantCertificateMgtAction : certificate download");
        String retType = "message";
        String osType = "";
        String certPath = null;
        MerchantCertificateManagerDAO dao = new MerchantCertificateManagerDAO();
        osType = MerchantCertificateManagerDAO.getOS_Type();

        try {
            Ipgsystemuser sysUser = new Ipgsystemuser();

            HttpServletRequest request = ServletActionContext.getRequest();
            sysUser = (Ipgsystemuser) request.getSession(false).getAttribute(SessionVarlist.SYSTEMUSER);
            certPath = dao.getCertificatePath(osType);
            fileName = sysUser.getMerchantid() + ".cer";
            System.out.println("file name : " + fileName);
            try {
                fileInputStream = new FileInputStream(new File(certPath + fileName));

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DOWNLOAD_TASK, PageVarList.MERCHANT_CERTIFICATE_MGT, SectionVarList.MERCHANTMANAGEMENT, "Merchant certificate downloaded", null);
                dao.insertMerchantCertificateMgt(audit);

                return "download";

            } catch (FileNotFoundException ex) {
                addActionError("Merchant Certificate doesn't exists !");
            } catch (Exception e) {
                addActionError("Merchant Certificate download failed !");
            }

        } catch (Exception ex) {
            Logger.getLogger(MerchantCertificateManagerAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Merchant Certificate Manager " + MessageVarList.MERCHANT_CER_MGT_ERROR_DOWNLOAD);
        }
        return retType;
    }
    
    public String keystoreD() {

        System.out.println("called MerchantCertificateMgtAction : keystore download");
        String retType = "message";
        String osType = "";
        String keyStorePath = null;
        MerchantCertificateManagerDAO dao = new MerchantCertificateManagerDAO();
        osType = MerchantCertificateManagerDAO.getOS_Type();

        try {
            Ipgsystemuser sysUser = new Ipgsystemuser();

            HttpServletRequest request = ServletActionContext.getRequest();
            sysUser = (Ipgsystemuser) request.getSession(false).getAttribute(SessionVarlist.SYSTEMUSER);
            keyStorePath = dao.getKeyStorePath(osType);
            fileName = sysUser.getMerchantid() + ".jks";
            System.out.println("file name : " + fileName);
            try {
                fileInputStream = new FileInputStream(new File(keyStorePath + fileName));

                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DOWNLOAD_TASK, PageVarList.MERCHANT_CERTIFICATE_MGT, SectionVarList.MERCHANTMANAGEMENT, "Merchant Key Store Downloaded", null);
                dao.insertMerchantCertificateMgt(audit);

                return "download";

            } catch (FileNotFoundException ex) {
                addActionError("Merchant Key Store doesn't exists !");
            } catch (Exception e) {
                addActionError("Merchant Key Store download failed !");
            }

        } catch (Exception ex) {
            Logger.getLogger(MerchantCertificateManagerAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Merchant Certificate Manager " + MessageVarList.MERCHANT_CER_MGT_ERROR_DOWNLOAD);
        }
        return retType;
    }

}
