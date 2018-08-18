package com.epic.epay.ipg.action.controlpanel.systemconfig;

import com.epic.epay.ipg.action.controlpanel.usermanagement.PageAction;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.CardAssociationBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.CardAssociationInputBean;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.CardAssociationDAO;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.common.PartialList;
import com.epic.epay.ipg.util.common.Validation;
import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

/**
 * @author ruwan_e
 *
 */
public class CardAssociationAction extends ActionSupport implements
        ModelDriven<CardAssociationInputBean>, AccessControlService {

    /**
     *
     */
    private static final long serialVersionUID = 514250495271820035L;
    private CardAssociationInputBean inputBean = new CardAssociationInputBean();

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.epic.epay.ipg.util.common.AccessControlService#checkAccess(java.lang
     * .String, java.lang.String)
     */
    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.CARD_ASSOCIATION;
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
    public CardAssociationInputBean getModel() {
        return inputBean;
    }

    @Override
    public String execute() throws Exception {
        System.out.println("called CardAssociationAction : execute");
        return SUCCESS;
    }

    public String view() {

        String result = "view";
        try {
            if (!this.applyUserPrivileges()) {
                result = "loginpage";
            }
            System.out.println("called CardAssociationAction :view");

        } catch (Exception ex) {
            addActionError("CardAssociation "
                    + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CardAssociationAction.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return result;
    }

    public String list() {
        System.out.println("called CardAssociationAction: list");
        try {

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;

            String sortIndex = "";
            String sortOrder = "";

            List<CardAssociationBean> dataList = null;

            if (!inputBean.getSidx().isEmpty()) {
                sortIndex = inputBean.getSidx();
                sortOrder = inputBean.getSord();
            }

            CardAssociationDAO dao = new CardAssociationDAO();
            PartialList<CardAssociationBean> caPList = dao.getSearchList(inputBean,rows, from, sortIndex, sortOrder);
            /**
             * for audit search
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("[")
                        .append(checkEmptyorNullString("CardAssociationCode", inputBean.getCardAssociationCode()))
                        .append(checkEmptyorNullString("Description", inputBean.getDescription()))
                        .append(checkEmptyorNullString("SortKey", inputBean.getSortKey()))
                        .append("]");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.CARD_ASSOCIATION, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Card association search using " + searchParameters + " parameters ", null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);
            }
            dataList = caPList.getList();
            int tp = caPList.getTotalPages();
            if (!dataList.isEmpty()) {
/////////////////////				inputBean.setRecords();
                inputBean.setGridModel(dataList);
                inputBean.setTotal(tp);
            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }

        } catch (Exception e) {
            Logger.getLogger(CardAssociationAction.class.getName()).log(
                    Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS
                    + " CardAssociation List Action");
        }
        return "list";
    }

    public String add() {

        System.out.println("called CardAssociationAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            CardAssociationDAO dao = new CardAssociationDAO();
            String message = this.validateInputs();
            if (message.isEmpty()) {
                // String vcardimgpath = request.getRealPath("/resources/vcard/image");
                //  String cardimgpath = request.getRealPath("/resources/card/image");
                String vcardimgpath = ServletActionContext.getRequest().getRealPath("/resources/vcard/image");
                String cardimgpath = ServletActionContext.getRequest().getRealPath("/resources/card/image");
                //***************start save card imageurl files*********************//
                File directory = new File(vcardimgpath + "/");

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                File f = new File(vcardimgpath, inputBean.getVerifieldImageURLFileName());
                FileUtils.copyFile(inputBean.getVerifieldImageURL(), f);

                //***************end save files*********************//
                //***************start save card imageurl files*********************//
                directory = new File(cardimgpath + "/");

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                f = new File(cardimgpath, inputBean.getCardImageURLFileName());
                FileUtils.copyFile(inputBean.getCardImageURL(), f);
                //***************end save files*********************//   
                inputBean.setCardImageURLFileName(cardimgpath + "\\" + inputBean.getCardImageURLFileName());
                inputBean.setVerifieldImageURLFileName(vcardimgpath + "\\" + inputBean.getVerifieldImageURLFileName());

                Ipgsystemaudit audit = Common.makeAudittrace(request,
                        TaskVarList.ADD_TASK, PageVarList.CARD_ASSOCIATION,
                        SectionVarList.SYSTEMCONFIGMANAGEMENT, "Card association code "+inputBean.getCardAssociationCode()+" added", null);
                dao.insertCardAssociation(inputBean, audit);
                if (message.isEmpty()) {
                    addActionMessage("Card Association "
                            + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Task " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return result;
    }
    
    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called CardAssociationAction : ViewPopup");

        try {
            
             if (!this.applyUserPrivileges()) {
                result = "loginpage";
            }

        } catch (Exception e) {
            addActionError("CardAssociation " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CardAssociationAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(
                PageVarList.CARD_ASSOCIATION, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                }else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
                    inputBean.setVdelete(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                    inputBean.setVupdatelink(false);
                }
            }
        }
        inputBean.setVupdatebutt(true);

        return true;
    }

    private String validateInputs() {
            String message = "";
            if (inputBean.getCardAssociationCode() == null
                    || inputBean.getCardAssociationCode().trim().isEmpty()) {
                message = MessageVarList.EMPTY_CARD_ASSOCIATION_CODE;
            } else if (!Validation.isSpecailCharacter(inputBean
                    .getCardAssociationCode())) {
                message = MessageVarList.INVALID_CARD_ASSOCIATION_CODE;
            } else if (inputBean.getDescription() == null
                    || inputBean.getDescription().trim().isEmpty()) {
                message = MessageVarList.EMPTY_CARD_ASSOCIATION_DESCRIPTION;
            } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
                message = MessageVarList.INVALID_CARD_ASSOCIATION_DESCRIPTION;
            } else if (inputBean.getVerifieldImageURL() == null
                    || inputBean.getVerifieldImageURLFileName().trim().isEmpty()) {
                message = MessageVarList.EMPTY_VERIFIED_IMAGE_URL;
            } else if (inputBean.getCardImageURL() == null
                    || inputBean.getCardImageURLFileName().trim().isEmpty()) {
                message = MessageVarList.EMPTY_CARD_IMAGE_URL;
            } else if (inputBean.getSortKey() == null
                    || inputBean.getSortKey().trim().isEmpty()) {
                message = MessageVarList.EMPTY_PAGE_SORTKEY;
            } else if (!Validation.isNumeric(inputBean.getSortKey())) {
                message = MessageVarList.INVALID_SORTKEY;
            }
            return message;
    }

    private String validateUpdateInputs() {
            String message = "";
            if (inputBean.getCardAssociationCode() == null
                    || inputBean.getCardAssociationCode().trim().isEmpty()) {
                message = MessageVarList.EMPTY_CARD_ASSOCIATION_CODE;
            } else if (!Validation.isSpecailCharacter(inputBean
                    .getCardAssociationCode())) {
                message = MessageVarList.INVALID_CARD_ASSOCIATION_CODE;
            } else if (inputBean.getDescription() == null
                    || inputBean.getDescription().trim().isEmpty()) {
                message = MessageVarList.EMPTY_CARD_ASSOCIATION_DESCRIPTION;
            } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
                message = MessageVarList.INVALID_CARD_ASSOCIATION_DESCRIPTION;
            } else if (inputBean.getSortKey() == null
                    || inputBean.getSortKey().trim().isEmpty()) {
                message = MessageVarList.EMPTY_PAGE_SORTKEY;
            } else if (!Validation.isNumeric(inputBean.getSortKey())) {
                message = MessageVarList.INVALID_SORTKEY;
            }
            return message;
    }

    public String find() {
        String message = "find";
        try {
            CardAssociationDAO dao = new CardAssociationDAO();
            Ipgcardassociation ipgcardassociation = dao.findCardAssociationById(inputBean.getCardAssociationCode());
            try{
                inputBean.setDescription(ipgcardassociation.getDescription());
            }catch(Exception e){
                
            }
            try{
                inputBean.setVerifieldImageURLFileName(ipgcardassociation.getVerifiedimageurl());
            }catch(Exception e){
                
            }
            try{
               inputBean.setCardImageURLFileName(ipgcardassociation.getCardimageurl());
            }catch(Exception e){
                
            }
            try{
                inputBean.setVerifieldImageURL(new File(ipgcardassociation.getVerifiedimageurl()));
            }catch(Exception e){
                
            }
            try{
                inputBean.setCardImageURL(new File(ipgcardassociation.getCardimageurl()));
            }catch(Exception e){
                
            }
            try{
                inputBean.setSortKey(ipgcardassociation.getSortkey().toString());
            }catch(Exception e){
                
            }

        } catch (Exception e) {
            addActionError("Card Association " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CardAssociationAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }
        return message;
    }
    
    public String detail() {

        System.out.println("called CardAssociationAction: detail");

        try {
            CardAssociationDAO dao = new CardAssociationDAO();
            Ipgcardassociation ipgcardassociation = dao.findCardAssociationById(inputBean.getCardAssociationCode());
            try{
                inputBean.setDescription(ipgcardassociation.getDescription());
            }catch(Exception e){
                
            }
            try{
                inputBean.setVerifieldImageURLFileName(ipgcardassociation.getVerifiedimageurl());
            }catch(Exception e){
                
            }
            try{
               inputBean.setCardImageURLFileName(ipgcardassociation.getCardimageurl());
            }catch(Exception e){
                
            }
            try{
                inputBean.setVerifieldImageURL(new File(ipgcardassociation.getVerifiedimageurl()));
            }catch(Exception e){
                
            }
            try{
                inputBean.setCardImageURL(new File(ipgcardassociation.getCardimageurl()));
            }catch(Exception e){
                
            }
            try{
                inputBean.setSortKey(ipgcardassociation.getSortkey().toString());
            }catch(Exception e){
                
            }

        } catch (Exception e) {
            addActionError("Card Association " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CardAssociationAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    }

    public String update() {
        System.out.println("called CardAssociationAction : update");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            CardAssociationDAO dao = new CardAssociationDAO();
            String message = this.validateUpdateInputs();
            if (message.isEmpty()) {
                String vcardimgpath = request.getRealPath("/resources/vcard/image");
                String cardimgpath = request.getRealPath("/resources/card/image");
                //***************start save card imageurl files*********************//
                if (inputBean.getVerifieldImageURLFileName() != null) {
                    File directory = new File(vcardimgpath + "/");

                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    File f = new File(vcardimgpath, inputBean.getVerifieldImageURLFileName());
                    FileUtils.copyFile(inputBean.getVerifieldImageURL(), f);
                }
                //***************end save files*********************//
                //***************start save card imageurl files*********************//
                if (inputBean.getCardImageURLFileName() != null) {
                    File directory = new File(cardimgpath + "/");

                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    File f = new File(cardimgpath, inputBean.getCardImageURLFileName());
                    FileUtils.copyFile(inputBean.getCardImageURL(), f);
                }
                //***************end save files*********************//   
                if (inputBean.getCardImageURLFileName() != null) {
                    inputBean.setCardImageURLFileName(cardimgpath + "\\" + inputBean.getCardImageURLFileName());
                }
                if (inputBean.getVerifieldImageURLFileName() != null) {
                    inputBean.setVerifieldImageURLFileName(vcardimgpath + "\\" + inputBean.getVerifieldImageURLFileName());
                }
                Ipgsystemaudit audit = Common.makeAudittrace(request,
                        TaskVarList.UPDATE_TASK, PageVarList.CARD_ASSOCIATION,
                        SectionVarList.SYSTEMCONFIGMANAGEMENT, "Card association code "+inputBean.getCardAssociationCode()+" updated", null);
                dao.updateCardAssociation(inputBean, audit);
                if (message.isEmpty()) {
                    addActionMessage("Card Association "
                            + MessageVarList.COMMON_SUCC_UPDATE);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Card Association " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return result;
    }

    public String delete() {
        System.out.println("called CardAssociationAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            CardAssociationDAO dao = new CardAssociationDAO();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.CARD_ASSOCIATION, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Card association code "+inputBean.getCardAssociationCode()+" deleted", null);
            message = dao.deleteCardAssociation(inputBean, audit);
            if (message.isEmpty()) {
                message = "Card Association " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(CardAssociationAction.class.getName()).log(Level.SEVERE, null, e);
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }
}
