/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

/**
 *
 * @author badrika
 */
public class AuthConfigurationBean {

    private String pkey;
    private String cardassociation;
    private String txstatus;
    private String ecivalue;
    private String status;
    private long fullCount;

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    public String getCardassociation() {
        return cardassociation;
    }

    public void setCardassociation(String cardassociation) {
        this.cardassociation = cardassociation;
    }

    public String getTxstatus() {
        return txstatus;
    }

    public void setTxstatus(String txstatus) {
        this.txstatus = txstatus;
    }

    public String getEcivalue() {
        return ecivalue;
    }

    public void setEcivalue(String ecivalue) {
        this.ecivalue = ecivalue;
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
