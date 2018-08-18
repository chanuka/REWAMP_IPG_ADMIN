/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.logmanagement;


import com.epic.epay.ipg.bean.logmanagement.LogFile;
import com.epic.epay.ipg.dao.logmanagement.LogFileDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author asitha_l
 */
public class LogManagementAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {
    
    LogFile inputBean = new LogFile();
    List<LogFile> logFiles = new ArrayList<LogFile>();
    private InputStream fileInputStream;
    
    public InputStream getFileInputStream() {
        return fileInputStream;
    }
    
    public List<LogFile> getLogFiles() {
        return logFiles;
    }
    
    public void setLogFiles(List<LogFile> logFiles) {
        this.logFiles = logFiles;
    }
    
    @Override
    public Object getModel() {
        return inputBean;
    }
    
    public String View() {
        
        String result = "view";
        try {
            
            if (this.applyUserPrivileges()) {
                LogFileDAO dao = new LogFileDAO();
                logFiles = dao.getAllLogFiles();
                
            } else {
                
                result = "loginpage";
            }
            System.out.println("called LogManagementAction :view");
            
            
        } catch (Exception ex) {
            addActionError("Logmanagement " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(LogManagementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public String Download() {
        String result = "download";
        try {
            LogFile file = new LogFile(inputBean.getPath());
            inputBean.setName(file.getName());
            fileInputStream = new FileInputStream(new File(inputBean.getPath()));
            System.out.println("called LogManagementAction :download");
            
        } catch (Exception ex) {
            addActionError("Logmanagement " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(LogManagementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
        
    }
    
    public String ViewDetail() {
        String result = "viewdetail";
        try {
            LogFile file = new LogFile(inputBean.getPath());
            
            inputBean.setTypeDes(file.getType().getDescription());
            inputBean.setLogFileCategory(file.getLogFileCategory());
            inputBean.setDate(file.getDate());
            inputBean.setContent(file.getContent());
            
            System.out.println("called LogManagementAction :ViewDetail");
            
        } catch (Exception ex) {
            addActionError("Logmanagement " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(LogManagementAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
        
    }
    
    private boolean applyUserPrivileges() {
        
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.LOG_MGT_PAGE, request);
        
        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                }
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                }
            }
        }
        return true;
    }
    
    @Override
    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.LOG_MGT_PAGE;
        String task = null;
        if ("View".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("ViewDetail".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Download".equals(method)) {
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
}
