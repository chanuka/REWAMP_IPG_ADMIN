/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.transactionmanagement;

import java.sql.Timestamp;

/**
 *
 * @author asela
 */
public class AutoFileBean {

    private String cardNo;
    private String cardHolderName;
    private String expiryDate;
    private String nic;
    private String transactionAmount;
    private String currency;
    private String numOfInstallment;
    private String recuringPeriod;
    private String count;
    private String merchantId;
    private String cardAssociation;
    private String fileId;
    private String transactionId;
    private String remarks;
    private String nextTransactionDate;
    private String lastUpdatedUser;
    private Timestamp transactionCreateDate;
    
    public String getCardAssociation() {
        return cardAssociation;
    }

    public void setCardAssociation(String cardAssociation) {
        this.cardAssociation = cardAssociation;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getNumOfInstallment() {
        return numOfInstallment;
    }

    public void setNumOfInstallment(String numOfInstallment) {
        this.numOfInstallment = numOfInstallment;
    }

    public String getRecuringPeriod() {
        return recuringPeriod;
    }

    public void setRecuringPeriod(String recuringPeriod) {
        this.recuringPeriod = recuringPeriod;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getNextTransactionDate() {
        return nextTransactionDate;
    }

    public void setNextTransactionDate(String nextTransactionDate) {
        this.nextTransactionDate = nextTransactionDate;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public Timestamp getTransactionCreateDate() {
        return transactionCreateDate;
    }

    public void setTransactionCreateDate(Timestamp transactionCreateDate) {
        this.transactionCreateDate = transactionCreateDate;
    }
    
}
