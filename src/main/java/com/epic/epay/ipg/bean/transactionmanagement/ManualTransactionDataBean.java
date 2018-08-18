/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.transactionmanagement;

import com.epic.epay.ipg.util.mapping.Epictxnrequests;
import java.sql.Timestamp;

/**
 *
 * @author nalin
 */
public class ManualTransactionDataBean {

//    public ManualTransactionDataBean(Epictxnrequests etr) {
//        
//        merchantid = etr.getIpgmerchant().getMerchantid();
//	merchantname = etr.getIpgmerchant().getMerchantname();
//        currency = etr.getIpgcurrency().getDescription();
//    }
    
    
    private boolean check;
    private String ccbaTransactionId;
    private String cardnumber;
    private String nicNo;
    private String merchantid;
    private String merchantname;
    private String currency;
    private Timestamp txnDateTime;
    private String transactionId;
    
    private long fullCount;
    
    private String switchIp;
    private int port;
    private String responseCode;

    public Timestamp getTxnDateTime() {
        return txnDateTime;
    }

    public void setTxnDateTime(Timestamp txnDateTime) {
        this.txnDateTime = txnDateTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getNicNo() {
        return nicNo;
    }

    public void setNicNo(String nicNo) {
        this.nicNo = nicNo;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    public String getCcbaTransactionId() {
        return ccbaTransactionId;
    }

    public void setCcbaTransactionId(String ccbaTransactionId) {
        this.ccbaTransactionId = ccbaTransactionId;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getSwitchIp() {
        return switchIp;
    }

    public void setSwitchIp(String switchIp) {
        this.switchIp = switchIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
    
    
    
}
