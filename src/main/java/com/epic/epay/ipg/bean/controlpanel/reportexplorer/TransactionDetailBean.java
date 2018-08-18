/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.reportexplorer;

/**
 *
 * Created on  :Sep 16, 2014, 12:18:38 PM
 * @author 	   :thushanth
 *
 */
public class TransactionDetailBean {
	
    private String txnhisid;    
    private String lastuptime;
    private String lastupuser;
    private String remarks;
    private String status;
    private String createdtime;
    private long fullCount;
    
    
	public String getTxnhisid() {
		return txnhisid;
	}
	public void setTxnhisid(String txnhisid) {
		this.txnhisid = txnhisid;
	}
	public String getLastuptime() {
		return lastuptime;
	}
	public void setLastuptime(String lastuptime) {
		this.lastuptime = lastuptime;
	}
	public String getLastupuser() {
		return lastupuser;
	}
	public void setLastupuser(String lastupuser) {
		this.lastupuser = lastupuser;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
