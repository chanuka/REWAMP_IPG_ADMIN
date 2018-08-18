/**
 * 
 */
package com.epic.epay.ipg.bean.analyser;

import java.sql.Timestamp;

/**
 * @created on 	Dec 5, 2013, 2:42:45 PM
 * @author 		thushanth
 *
 */
public class TransactionHistoryBean {
	
	private String transactionHisId;
	private String status;
	private Timestamp analytime;
	
	
	public String getTransactionHisId() {
		return transactionHisId;
	}
	public void setTransactionHisId(String transactionHisId) {
		this.transactionHisId = transactionHisId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getAnalytime() {
		return analytime;
	}
	public void setAnalytime(Timestamp analytime) {
		this.analytime = analytime;
	}

	
	

}
