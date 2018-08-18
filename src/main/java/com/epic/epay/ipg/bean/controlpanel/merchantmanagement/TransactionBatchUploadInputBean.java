/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.merchantmanagement;

import java.util.List;

import com.epic.epay.ipg.util.mapping.Ipgstatus;

/**
 *
 * Created on  :Dec 13, 2013, 10:19:01 AM
 * @author 	   :thushanth
 *
 */
public class TransactionBatchUploadInputBean {
	
    private String txnType;
    private List<Ipgstatus> statusList;     
    private boolean vupload;
    
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public List<Ipgstatus> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<Ipgstatus> statusList) {
		this.statusList = statusList;
	}
	public boolean isVupload() {
		return vupload;
	}
	public void setVupload(boolean vupload) {
		this.vupload = vupload;
	}
	
}
