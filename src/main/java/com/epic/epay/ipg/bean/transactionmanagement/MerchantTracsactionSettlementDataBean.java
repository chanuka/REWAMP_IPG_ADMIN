/**
 *
 */
package com.epic.epay.ipg.bean.transactionmanagement;

/**
 * @author Asitha Liyanawaduge
 *
 * 04/12/2013
 */
public class MerchantTracsactionSettlementDataBean {

    private String headmerchant;
    private String headmerchantid;
    private String merchantid;
    private String merchantname;
    private String currency;
    private String terminalid;
    private String batchid;
    private String txncount;
    private String txnamount;
    private String status;
    private long fullCount;
    private String key;
    private String txnid;
    private String txnname;
    private String txndatetime;
    private String cardNumber;
    private String nic;

    public MerchantTracsactionSettlementDataBean() {
    }

    public String getHeadmerchant() {
        return headmerchant;
    }

    public void setHeadmerchant(String headmerchant) {
        this.headmerchant = headmerchant;
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

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getTxncount() {
        return txncount;
    }

    public void setTxncount(String txncount) {
        this.txncount = txncount;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getTxnamount() {
        return txnamount;
    }

    public void setTxnamount(String txnamount) {
        this.txnamount = txnamount;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getTxnname() {
        return txnname;
    }

    public void setTxnname(String txnname) {
        this.txnname = txnname;
    }

    public String getTxndatetime() {
        return txndatetime;
    }

    public void setTxndatetime(String txndatetime) {
        this.txndatetime = txndatetime;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHeadmerchantid() {
        return headmerchantid;
    }

    public void setHeadmerchantid(String headmerchantid) {
        this.headmerchantid = headmerchantid;
    }

}
