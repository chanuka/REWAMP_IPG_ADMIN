/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

/**
 *
 * @author chalitha
 */
public class MerchantRiskProfileBean {

    private String riskprofilecode;
    private String description;
    private String status;
    private String maxsingletxnlimit;
    private String minsingletxnlimit;
    private String maxtxncount;
    private String maxpasswordcount;
    private String maxtxnamount;
    private String createdtime;
    private long fullCount;

    public String getRiskprofilecode() {
        return riskprofilecode;
    }

    public void setRiskprofilecode(String riskprofilecode) {
        this.riskprofilecode = riskprofilecode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMaxsingletxnlimit() {
        return maxsingletxnlimit;
    }

    public void setMaxsingletxnlimit(String maxsingletxnlimit) {
        this.maxsingletxnlimit = maxsingletxnlimit;
    }

    public String getMinsingletxnlimit() {
        return minsingletxnlimit;
    }

    public void setMinsingletxnlimit(String minsingletxnlimit) {
        this.minsingletxnlimit = minsingletxnlimit;
    }

    public String getMaxtxncount() {
        return maxtxncount;
    }

    public void setMaxtxncount(String maxtxncount) {
        this.maxtxncount = maxtxncount;
    }

    public String getMaxpasswordcount() {
        return maxpasswordcount;
    }

    public void setMaxpasswordcount(String maxpasswordcount) {
        this.maxpasswordcount = maxpasswordcount;
    }

    public String getMaxtxnamount() {
        return maxtxnamount;
    }

    public void setMaxtxnamount(String maxtxnamount) {
        this.maxtxnamount = maxtxnamount;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    /**
     * @return the createdtime
     */
    public String getCreatedtime() {
        return createdtime;
    }

    /**
     * @param createdtime the createdtime to set
     */
    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }
    
    
}
