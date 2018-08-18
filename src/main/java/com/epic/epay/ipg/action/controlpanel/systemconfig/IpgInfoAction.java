package com.epic.epay.ipg.action.controlpanel.systemconfig;

//import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.IpgInfoBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.IpgInfoInputBean;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.IpgInfoDao;
import com.epic.epay.ipg.dao.controlpanel.systemconfig.SystemAuditDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import static com.epic.epay.ipg.util.common.Common.checkEmptyorNullString;
import com.epic.epay.ipg.util.mapping.Ipgmpiinfo;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.OracleMessage;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author jeevan
 */
public class IpgInfoAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    IpgInfoInputBean inputBean = new IpgInfoInputBean();

    public IpgInfoAction() {
    }

    public Object getModel() {
        return inputBean;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.IPG_INFO;
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

    public String view() {
        String result = "view";

        try {

            if (this.applyUserPrivileges()) {
            } else {
                result = "loginpage";
            }
            System.out.println("Called IPG Info Action : View");

        } catch (Exception e) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " IPG Info View  Action");
            Logger.getLogger(IpgInfoAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(
                PageVarList.IPG_INFO, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                }else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
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

        System.out.println("called BinAction: List");
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

            IpgInfoDao dao = new IpgInfoDao();
            List<IpgInfoBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
            
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                StringBuilder searchParameters = new StringBuilder();
                searchParameters.append("[")
                        .append(checkEmptyorNullString("Primary URL", inputBean.getPrimaryUrl()))
                        .append(checkEmptyorNullString("Secondary URL", inputBean.getSecondaryUrl()))
                        .append("]");
                
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.IPG_INFO, SectionVarList.SYSTEMCONFIGMANAGEMENT, "IPG MPI Information search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(BinAction.class.getName()).log(Level.SEVERE,
                    null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Ipg Info");
        }
        return "list";
    }
    
    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called IpgInfoAction : ViewPopup");

        try {
            
            if (this.applyUserPrivileges()) {
            } else {
                result = "loginpage";
            }

        } catch (Exception e) {
            addActionError("IpgInfoAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(IpgInfoAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    
    public String add() throws Exception {
        System.out.println("called IpgInfoAction : add");
        String result = "message";

        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            IpgInfoDao dao = new IpgInfoDao();
            String message = this.validateInput();

            if (message.isEmpty()) {
                Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.IPG_INFO, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Ipg MPI info added", null);
                message = dao.insertIpgInfo(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("IPG information " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }

            } else {
                addActionError(message);
            }

        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    public String delete() {
        System.out.println("called IpgInfoAction : Delete");
        String message = null;
        String retType = "delete";
        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            IpgInfoDao dao = new IpgInfoDao();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.IPG_INFO, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Ipg MPI info ID "+inputBean.getIpgMpiInfoId()+" deleted", null);

            message = dao.deleteIpgInfo(inputBean, audit);
            if (message.isEmpty()) {
                message = "IPG Information " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(BinAction.class.getName()).log(Level.SEVERE, null, e);
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    private String validateInput() throws Exception {
        String message = "";
        try {
            if (inputBean.getPrimaryUrl() == null || inputBean.getPrimaryUrl().trim().isEmpty()) {
                message = MessageVarList.PRIMARY_URL_EMPTY;

            } else if (inputBean.getSecondaryUrl() == null || inputBean.getSecondaryUrl().trim().isEmpty()) {
                message = MessageVarList.SECONDARY_URL_EMPTY;
            }

        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    public String find() {
        System.out.println("called Ipg info: find");
        Ipgmpiinfo info;

        try {
            if (inputBean.getIpgMpiInfoId() != null && !inputBean.getIpgMpiInfoId().isEmpty()) {
                IpgInfoDao dao = new IpgInfoDao();
                info = dao.findInfoById(inputBean.getIpgMpiInfoId());

                /*
                 CardAssociationDAO dao = new CardAssociationDAO();
                 card = dao.findCardAssociationBtId(inputBean.getCardAssociationCode());
                 */
                inputBean.setIpgMpiInfoId(Long.toString(info.getIpgmpiinfoid()));


                inputBean.setPrimaryUrl(info.getPrimaryurl());
                inputBean.setSecondaryUrl(info.getSecounderyurl());

            } else {
                inputBean.setMessage("Empty ipg info id");
            }

        } catch (Exception e) {
            inputBean.setMessage("Ipg info " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(IpgInfoAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return "find";

    }
    public String detail() {

        System.out.println("called ipg info: detail");
        Ipgmpiinfo info;
        
        try {
            if (inputBean.getIpgMpiInfoId() != null && !inputBean.getIpgMpiInfoId().isEmpty()) {
                IpgInfoDao dao = new IpgInfoDao();
                info = dao.findInfoById(inputBean.getIpgMpiInfoId());

                inputBean.setIpgMpiInfoId(Long.toString(info.getIpgmpiinfoid()));
                inputBean.setPrimaryUrl(info.getPrimaryurl());
                inputBean.setSecondaryUrl(info.getSecounderyurl());

            } else {
                inputBean.setMessage("Empty ipg info id");
            }

        } catch (Exception e) {
            addActionError("Card IpgInfoAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(IpgInfoAction.class.getName()).log(Level.SEVERE,
                    null, e);
        }

        return "detail";

    }
    public String update() {
        System.out.println("Called ipg info: update");
        String retType = "message";

        try {

            if (inputBean.getIpgMpiInfoId() != null && !inputBean.getIpgMpiInfoId().isEmpty()) {
                String message = this.validateInput();

                if (message.isEmpty()) {
                    HttpServletRequest request = ServletActionContext.getRequest();
                    IpgInfoDao dao = new IpgInfoDao();
                    Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.IPG_INFO, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Ipg MPI info ID "+inputBean.getIpgMpiInfoId()+" updated", null);
                    message = dao.updateIpgInfo(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("IPG information " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }
                } else {
                    addActionError(message);
                }
            }

        } catch (Exception e) {
            Logger.getLogger(IpgInfoAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Ipg info " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }
}