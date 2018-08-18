/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.controlpanel.merchantmanagement;

/**
 *
 * @author nalin
 */
public class SettlementFileDownloadDataBean {

    private String mid;
    private String tid;
    private String merchantname;
    private String batchId;
    private String numoftxn;
    private String description;
    private String midkey;
    private String midvalue;
    private String status;
    private long fullCount;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getNumoftxn() {
        return numoftxn;
    }

    public void setNumoftxn(String numoftxn) {
        this.numoftxn = numoftxn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMidkey() {
        return midkey;
    }

    public void setMidkey(String midkey) {
        this.midkey = midkey;
    }

    public String getMidvalue() {
        return midvalue;
    }

    public void setMidvalue(String midvalue) {
        this.midvalue = midvalue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }
    
    
    
}
