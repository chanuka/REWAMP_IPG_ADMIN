/**
 *
 */
package com.epic.epay.ipg.action.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantAddonBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantAddonInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.merchantmanagement.MerchantAddonDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgmerchantaddon;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
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
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @created on : Nov 19, 2013, 11:33:17 AM
 * @author : thushanth
 *
 */
public class MerchantAddonAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    private File image;
    private String serverPath;
    private String imageFileName;
    MerchantAddonInputBean inputBean = new MerchantAddonInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() throws Exception {
        System.out.println("called MerchantAddonAction : execute");
        return SUCCESS;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public boolean checkAccess(String method, String userRole) {

        boolean status = false;
        String page = PageVarList.MERCHANT_ADDON;
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
//        } else if ("viewaddupdate".equals(method)) {
//            task = TaskVarList.VIEW_TASK;
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
                CommonDAO comdao = new CommonDAO();
                inputBean.setMerchantList(comdao.getMerchantList());
            } else {
                result = "loginpage";
            }

            System.out.println("called MerchantAddonAction :view");

        } catch (Exception ex) {
            addActionError("MerchantAddonAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantAddonAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String find() {
        System.out.println("called MerchantAddonAction: Find");

        Ipgmerchantaddon ipgmerchantaddon;

        try {
            if (inputBean.getMerchantID() != null && !inputBean.getMerchantID().isEmpty()) {

                MerchantAddonDAO dao = new MerchantAddonDAO();

                ipgmerchantaddon = dao.findMerchantAddonByID(inputBean.getMerchantID());

                inputBean.setMerchantID(ipgmerchantaddon.getMerchantid());
//                inputBean.setLogoPath(ipgmerchantaddon.getLogopath());
                inputBean.setCordinateX(ipgmerchantaddon.getXcordinate());
                inputBean.setCordinateY(ipgmerchantaddon.getYcordinate());
                inputBean.setDisplayText(ipgmerchantaddon.getDisplaytext());
                inputBean.setThemeColor(ipgmerchantaddon.getThemecolor());
                inputBean.setFontFamily(ipgmerchantaddon.getFontfamily());
                inputBean.setFontStyle(ipgmerchantaddon.getFontstyle());
                inputBean.setFontSize(ipgmerchantaddon.getFontsize().toString());
                inputBean.setRemarks(ipgmerchantaddon.getRemarks());

            } else {
                inputBean.setMessage("Empty Merchant ID");
            }
        } catch (Exception ex) {
            inputBean.setMessage("MerchantAddonAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantAddonAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.MERCHANT_ADDON, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                }else if (task.getTaskcode().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                } else if (task.getTaskcode().equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
                    inputBean.setVdelete(false);
                } else if (task.getTaskcode().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                } else if (task.getTaskcode().equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                    inputBean.setVupdatelink(false);
                } else if (task.getTaskcode().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                }
            }
        }
        inputBean.setVupdatebutt(true);

        return true;
    }

    public String list() {
        System.out.println("called MerchantAddonAction: List");
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
            MerchantAddonDAO dao = new MerchantAddonDAO();
            List<MerchantAddonBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            /**
             * for audit search
             */
          
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("Merchant add  on search using [")
                        .append(checkEmptyorNullString("Merchant ID", inputBean.getMerchantNameID()))
                        .append(checkEmptyorNullString("X Cordinate ", inputBean.getCordinateX()))
                        .append(checkEmptyorNullString("Y Cordinate", inputBean.getCordinateY()))
                        .append(checkEmptyorNullString("Theme Color ", inputBean.getThemeColor()))
                        .append(checkEmptyorNullString("Display Text", inputBean.getDisplayText()))
                        .append(checkEmptyorNullString("Remarks ", inputBean.getRemarks()))
                        .append("] parameters ");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.MERCHANT_ADDON, SectionVarList.MERCHANTMANAGEMENT, searchParameters.toString(), null);
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
            Logger.getLogger(MerchantAddonAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " MerchantAddonAction");
        }
        return "list";
    }

    private String validateInputs() throws Exception {

        String message = "";

        try {
            if (inputBean.getMerchantNameID() == null || inputBean.getMerchantNameID().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_NAME;
            } else if (this.image == null) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_PATH;
            } else if (inputBean.getCordinateX() == null || inputBean.getCordinateX().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_X_CO;
            } else if (inputBean.getCordinateY() == null || inputBean.getCordinateY().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_Y_CO;
            } else if (inputBean.getDisplayText() == null || inputBean.getDisplayText().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_DISPLAY_TEXT;
            } else if (inputBean.getFontFamily() == null || inputBean.getFontFamily().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_FONT_FAMILY;
            } else if (inputBean.getFontFamily() == null || inputBean.getFontFamily().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_FONT_STYLE;
            } else if (inputBean.getFontSize() == null || inputBean.getFontSize().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_FONT_SIZE;
            } else if (inputBean.getRemarks() == null || inputBean.getRemarks().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_REMARKS;
            } else {
                if (!Validation.isNumeric(inputBean.getCordinateX().trim())) {
                    message = MessageVarList.MERCHANT_ADDON_INVALID_X_CO;
                } else if (!Validation.isNumeric(inputBean.getCordinateY().trim())) {
                    message = MessageVarList.MERCHANT_ADDON_INVALID_Y_CO;
                } else if (!Validation.isSpecailCharacter(inputBean.getDisplayText().trim())) {
                    message = MessageVarList.MERCHANT_ADDON_INVALID_DISPLAY_TEXT;
                } else if (!Validation.isSpecailCharacter(inputBean.getRemarks().trim())) {
                    message = MessageVarList.MERCHANT_ADDON_INVALID_REMARKS;
                }

            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    private String validateUpdateInputs() throws Exception {

        String message = "";

        try {
            if (inputBean.getMerchantNameID() == null || inputBean.getMerchantNameID().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_NAME;
            } else if (inputBean.getCordinateX() == null || inputBean.getCordinateX().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_X_CO;
            } else if (inputBean.getCordinateY() == null || inputBean.getCordinateY().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_Y_CO;
            } else if (inputBean.getDisplayText() == null || inputBean.getDisplayText().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_DISPLAY_TEXT;
            } else if (inputBean.getFontFamily() == null || inputBean.getFontFamily().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_FONT_FAMILY;
            } else if (inputBean.getFontFamily() == null || inputBean.getFontFamily().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_FONT_STYLE;
            } else if (inputBean.getFontSize() == null || inputBean.getFontSize().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_FONT_SIZE;
            } else if (inputBean.getRemarks() == null || inputBean.getRemarks().trim().isEmpty()) {
                message = MessageVarList.MERCHANT_ADDON_EMPTY_REMARKS;
            } else {
                if (!Validation.isNumeric(inputBean.getCordinateX().trim())) {
                    message = MessageVarList.MERCHANT_ADDON_INVALID_X_CO;
                } else if (!Validation.isNumeric(inputBean.getCordinateY().trim())) {
                    message = MessageVarList.MERCHANT_ADDON_INVALID_Y_CO;
                } else if (!Validation.isSpecailCharacter(inputBean.getDisplayText().trim())) {
                    message = MessageVarList.MERCHANT_ADDON_INVALID_DISPLAY_TEXT;
                } else if (!Validation.isSpecailCharacter(inputBean.getRemarks().trim())) {
                    message = MessageVarList.MERCHANT_ADDON_INVALID_REMARKS;
                }

            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

//    public String viewaddupdate() {
//        System.out.println("called MerchantAddonAction : view add/update");
//        String result = "viewaddupdate";
//
//        try {
//            if (this.applyUserPrivileges()) {
//                CommonDAO dao = new CommonDAO();
//                inputBean.setMerchantList(dao.getMerchantList());
//
//                List<String> familylist = new ArrayList<String>();
//                familylist.add("Arial");
//                familylist.add("Times New Roman");
//                familylist.add("Tahoma");
//                familylist.add("Raleway");
//                inputBean.setFontfamilyList(familylist);
//
//                List<String> stylelist = new ArrayList<String>();
//                stylelist.add("Regular");
//                stylelist.add("Italic");
//                stylelist.add("Bold");
//                stylelist.add("Bold Italic");
//                inputBean.setFontstyleList(stylelist);
//
//                List<String> sizelist = new ArrayList<String>();
//                sizelist.add("10");
//                sizelist.add("11");
//                sizelist.add("12");
//                sizelist.add("13");
//                inputBean.setFontsizeList(sizelist);
//
//            } else {
//                result = "loginpage";
//            }
//        } catch (Exception ex) {
//            addActionError("MerchantAddonAction " + MessageVarList.COMMON_ERROR_PROCESS);
//            Logger.getLogger(MerchantAddonAction.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return result;
//
//    }
    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called MerchantAddonAction : ViewPopup");

        try {
            
             if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
                inputBean.setMerchantList(dao.getMerchantList());

                List<String> familylist = new ArrayList<String>();
                familylist.add("Arial");
                familylist.add("Times New Roman");
                familylist.add("Tahoma");
                familylist.add("Raleway");
                inputBean.setFontfamilyList(familylist);

                List<String> stylelist = new ArrayList<String>();
                stylelist.add("Regular");
                stylelist.add("Italic");
                stylelist.add("Bold");
                stylelist.add("Bold Italic");
                inputBean.setFontstyleList(stylelist);

                List<String> sizelist = new ArrayList<String>();
                sizelist.add("10");
                sizelist.add("11");
                sizelist.add("12");
                sizelist.add("13");
                inputBean.setFontsizeList(sizelist);

            } else {
                result = "loginpage";
            }

        } catch (Exception e) {
            addActionError("MerchantAddonAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantAddonAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    public String add() {
        System.out.println("called MerchantAddonAction : add");
        String result = "message";

        try {
            HttpServletRequest request = ServletActionContext.getRequest();

//            serverPath = request.getSession(false).getServletContext().getRealPath("/");
            MerchantAddonDAO dao = new MerchantAddonDAO();
            serverPath = dao.getLogoPath(Common.getOS_Type());
//            System.out.println(serverPath);
            String message = this.validateInputs();
            String imagename;
            
            if (message.isEmpty()) {

                String mesEx = Validation.validateExtension(this.imageFileName);
                if (mesEx.isEmpty()) {

                    File directory = new File(serverPath);

                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    int pos = this.imageFileName.lastIndexOf('.') + 1;
                    String ext = this.imageFileName.substring(pos);
                    imagename=inputBean.getMerchantNameID()+"."+ext;
                    File filetoCreate = new File(serverPath, imagename);

//                    if (filetoCreate.exists()) {
//                        addActionError("Image already exsists.");
//
//                    } else {
                        try {

                            inputBean.setLogoPath(serverPath + imagename);
                            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.MERCHANT_ADDON, SectionVarList.MERCHANTMANAGEMENT, "Merchant add on for merchant ID "+inputBean.getMerchantNameID()+" added", null);
                            message = dao.insertMerchantAddon(inputBean, audit, this.image, filetoCreate);

                            if (message.isEmpty()) {
                                addActionMessage("Merchant Add On " + MessageVarList.COMMON_SUCC_INSERT);
                            } else {
                                addActionError(message);
                            }
                        } catch (Exception e) {
                            addActionError("Image upload failed!");
                        }
//                    }

                } else {
                    addActionError(mesEx);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("MerchantAddonAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantAddonAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String delete() {
        System.out.println("called MerchantAddonAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            MerchantAddonDAO dao = new MerchantAddonDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.MERCHANT_ADDON, SectionVarList.MERCHANTMANAGEMENT, "Merchant add on for merchant ID "+inputBean.getMerchantID()+" deleted", null);
            message = dao.deleteMerchantAddon(inputBean, audit);
            if (message.isEmpty()) {
                message = "Merchant Add On " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(MerchantAddonAction.class.getName()).log(Level.SEVERE, null, e);
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String detail() {

        System.out.println("called MerchantAddonAction: detail");
        Ipgmerchantaddon ipgmerchantaddon = new Ipgmerchantaddon();

        try {
            if (inputBean.getMerchantID() != null && !inputBean.getMerchantID().isEmpty()) {
                
                CommonDAO comdao = new CommonDAO();
                inputBean.setMerchantList(comdao.getMerchantList());

                List<String> familylist = new ArrayList<String>();
                familylist.add("Arial");
                familylist.add("Times New Roman");
                familylist.add("Tahoma");
                familylist.add("Raleway");
                inputBean.setFontfamilyList(familylist);

                List<String> stylelist = new ArrayList<String>();
                stylelist.add("Regular");
                stylelist.add("Italic");
                stylelist.add("Bold");
                stylelist.add("Bold Italic");
                inputBean.setFontstyleList(stylelist);

                List<String> sizelist = new ArrayList<String>();
                sizelist.add("10");
                sizelist.add("11");
                sizelist.add("12");
                sizelist.add("13");
                inputBean.setFontsizeList(sizelist);
                
                MerchantAddonDAO dao = new MerchantAddonDAO();

                ipgmerchantaddon = dao.findMerchantAddonByID(inputBean.getMerchantID());

                inputBean.setMerchantID(ipgmerchantaddon.getMerchantid());
                inputBean.setMerchantNameID(ipgmerchantaddon.getMerchantid());
//                inputBean.setLogoPath(ipgmerchantaddon.getLogopath());
                inputBean.setCordinateX(ipgmerchantaddon.getXcordinate());
                inputBean.setCordinateY(ipgmerchantaddon.getYcordinate());
                inputBean.setDisplayText(ipgmerchantaddon.getDisplaytext());
                inputBean.setThemeColor(ipgmerchantaddon.getThemecolor());
                inputBean.setFontFamily(ipgmerchantaddon.getFontfamily());
                inputBean.setFontStyle(ipgmerchantaddon.getFontstyle());
                inputBean.setFontSize(ipgmerchantaddon.getFontsize().toString());
                inputBean.setRemarks(ipgmerchantaddon.getRemarks());

            } else {
                inputBean.setMessage("Empty Merchant ID");
            }

        } catch (Exception e) {
            addActionError("MerchantAddonAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MerchantAddonAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    }
    
    public String update() {

        System.out.println("called MerchantAddonAction : update");
        String retType = "message";

        try {
            if (inputBean.getMerchantID() != null && !inputBean.getMerchantID().isEmpty()) {
                
                inputBean.setMerchantNameID(inputBean.getMerchantID());
                String message = this.validateUpdateInputs();
                HttpServletRequest request = ServletActionContext.getRequest();
                MerchantAddonDAO dao = new MerchantAddonDAO();
                serverPath = dao.getLogoPath(Common.getOS_Type());

                String mesEx = "";
                File filetoCreate = null;
                String imagename ="";
                if (message.isEmpty()) {
                    if (this.image != null) {
                        mesEx = Validation.validateExtension(this.imageFileName);
                    }

                    if (mesEx.isEmpty()) {

                        if (image != null) {
                            File directory = new File(serverPath);

                            if (!directory.exists()) {
                                directory.mkdirs();
                            }
                            int pos = this.imageFileName.lastIndexOf('.') + 1;
                            String ext = this.imageFileName.substring(pos);
                            imagename=inputBean.getMerchantID()+"."+ext;
                            filetoCreate = new File(serverPath, imagename);
//                            filetoCreate = new File(serverPath, this.imageFileName);
                        }
//                        boolean fileexists = false;
//                        if (filetoCreate != null) {
//                            fileexists = filetoCreate.exists();
//                        }

//                        if (fileexists) {
//                            addActionError("Image already exists.");
//
//                        } else {
                            try {
                                if (this.image != null && !imagename.isEmpty()) {
                                    FileUtils.copyFile(this.image, filetoCreate);
//                                addActionMessage("Image succesfully uploaded.");

                                    inputBean.setLogoPath(serverPath + imagename);
                                }
         
                                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.MERCHANT_ADDON, SectionVarList.MERCHANTMANAGEMENT, "Merchant add on for merchant ID "+inputBean.getMerchantID()+" updated", null);
                                message = dao.updateMerchantAddon(inputBean, audit);

                                if (message.isEmpty()) {
                                    addActionMessage("Merchant Add On " + MessageVarList.COMMON_SUCC_UPDATE);
                                } else {
                                    addActionError(message);
                                }

                            } catch (Exception e) {
                                addActionError("Image upload failed!");
                            }
//                        }

                    } else {
                        addActionError(mesEx);
                    }

                } else {
                    addActionError(message);
                }
            } else {
                addActionError("Merchant Name cannot be empty");
            }
        } catch (Exception ex) {
            Logger.getLogger(MerchantAddonAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Merchant Add On " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

}
