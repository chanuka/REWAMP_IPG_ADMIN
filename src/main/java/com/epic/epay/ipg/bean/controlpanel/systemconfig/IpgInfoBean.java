package com.epic.epay.ipg.bean.controlpanel.systemconfig;

import java.util.Date;

/**
 *
 * @author jeevan
 */
public class IpgInfoBean {
    
    private long infoId;
    private String primaryUrl;
    private String secondaryUrl;
    private String lastUpdatedUser;
    private Date lastUpdatedTime;
    private String createdtime;
    private long fullCount;


    public String getPrimaryUrl() {
        return primaryUrl;
    }

    public void setPrimaryUrl(String primaryUrl) {
        this.primaryUrl = primaryUrl;
    }

    public String getSecondaryUrl() {
        return secondaryUrl;
    }

    public void setSecondaryUrl(String secondaryUrl) {
        this.secondaryUrl = secondaryUrl;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public long getFullCount() {
        return fullCount;
    }
    
    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    public long getInfoId() {
        return infoId;
    }

    public void setInfoId(long infoId) {
        this.infoId = infoId;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
    
    
}
