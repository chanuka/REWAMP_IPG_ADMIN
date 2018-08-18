/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import java.text.SimpleDateFormat;

/**
 *
 * @author chalitha
 */
public class AuditDataBean {

    private String id;
    private String userRole;
    private String description;
    private String section;
    private String page;
    private String task;
    private String ip;
    private String oldvalue;
    private String newvalue;
    private String lastUpdatedUser;
    private String lastUpdatedDate;
    private String createdtime;

    public AuditDataBean() {}
    
    public AuditDataBean(Ipgsystemaudit audit) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        id = audit.getSystemauditid().toString();
        userRole = audit.getIpguserrole().getDescription();
        description = audit.getDescription();
        section = audit.getIpgsection().getDescription();
        page = audit.getIpgpage().getDescription();
        task = audit.getIpgtask().getDescription();
        ip = audit.getIp();
        lastUpdatedUser = audit.getLastupdateduser();
        try {
            lastUpdatedDate = sdf.format(audit.getLastupdatedtime());
        } catch (Exception npe) {
            lastUpdatedDate = "--";
        }
        try {
            createdtime = sdf.format(audit.getCreatedtime());
        } catch (Exception npe) {
            createdtime = "--";
        }
        if(audit.getOldvalue()!=null && !audit.getOldvalue().isEmpty()){
            oldvalue = audit.getOldvalue();
        } else {
            oldvalue = "--";
        }
        if(audit.getNewvalue()!=null && !audit.getNewvalue().isEmpty()){
            newvalue = audit.getNewvalue();
        } else {
            newvalue = "--";
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    } 

    public String getOldvalue() {
        return oldvalue;
    }

    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
    }

    public String getNewvalue() {
        return newvalue;
    }

    public void setNewvalue(String newvalue) {
        this.newvalue = newvalue;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }
}
