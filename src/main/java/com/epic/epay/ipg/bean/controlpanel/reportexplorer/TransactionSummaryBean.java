/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.reportexplorer;

/**
 *
 * Created on  :Sep 15, 2014, 2:50:27 PM
 * @author 	   :thushanth
 *
 */
public class TransactionSummaryBean {
	
    private String txnid;
    private String merchantname;
    private String associationcode;
    private String cardnumber;
    private String amount;
    private String status;
    private String createdtime;
    private long fullCount;
    
    
	public String getTxnid() {
		return txnid;
	}
	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}
	public String getMerchantname() {
		return merchantname;
	}
	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}
	public String getAssociationcode() {
		return associationcode;
	}
	public void setAssociationcode(String associationcode) {
		this.associationcode = associationcode;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
    
    
    
}
