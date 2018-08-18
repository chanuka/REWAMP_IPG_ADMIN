/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.controlpanel.merchantmanagement;

/**
 *
 * @author badrika
 */
public class DSMerchantEnrollmentBean {

    private String check;
    private String merchantid;
    private String merchantname;
    private String cardassociation;
    private String dsstatus;
    private long fullCount;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public String getCardassociation() {
        return cardassociation;
    }

    public void setCardassociation(String cardassociation) {
        this.cardassociation = cardassociation;
    }

    public String getDsstatus() {
        return dsstatus;
    }

    public void setDsstatus(String dsstatus) {
        this.dsstatus = dsstatus;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }
    
    

}
