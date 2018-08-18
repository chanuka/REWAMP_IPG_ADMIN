/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

/**
 *
 * @author chalitha
 */
public class BinBean {

    private String bincode;
    private String description;
    private String onusstatus;
    private String status;
    private String cardassociation;
    private String createdtime;
    private long fullCount;

    public String getBincode() {
        return bincode;
    }

    public void setBincode(String bincode) {
        this.bincode = bincode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOnusstatus() {
        return onusstatus;
    }

    public void setOnusstatus(String onusstatus) {
        this.onusstatus = onusstatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCardassociation() {
        return cardassociation;
    }

    public void setCardassociation(String cardassociation) {
        this.cardassociation = cardassociation;
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
