/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.transactionmanagement;

import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
import com.epic.epay.ipg.util.mapping.Ipgcurrency;
import java.util.List;

/**
 *
 * @author badrika
 */
public class MotoTransactionInputBean {
    
    private String orderCode;
    private String amount;
    private String currency;
    private String cardType;
    private String cardNo;
    private String expiryDate;
    private String cardHolderName;
    
    private String user;
    private String merchantId;
    private String TxnId;    
    private String status;
    
    private String message;
    private List<Ipgcardassociation> cardTypeList;
    private List<Ipgcurrency> currencyList;
    
    /*-------for access control-----------*/
    private boolean vadd;
    /*-------for access control-----------*/

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTxnId() {
        return TxnId;
    }

    public void setTxnId(String TxnId) {
        this.TxnId = TxnId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Ipgcardassociation> getCardTypeList() {
        return cardTypeList;
    }

    public void setCardTypeList(List<Ipgcardassociation> cardTypeList) {
        this.cardTypeList = cardTypeList;
    }

    public List<Ipgcurrency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<Ipgcurrency> currencyList) {
        this.currencyList = currencyList;
    }

    public boolean isVadd() {
        return vadd;
    }

    public void setVadd(boolean vadd) {
        this.vadd = vadd;
    }
    
    

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
    
    
    
}
