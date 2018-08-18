/**
 * 
 */
package com.epic.epay.ipg.bean.analyser;

import java.util.List;

/**
 * @created on 	Dec 5, 2013, 1:24:08 PM
 * @author 		thushanth
 *
 */
public class LastTransactionInputBean {
	
	private String Txnid;	
	private List<Integer> stageList;
	private List<Long> timeDiffList;
	private String msg="";
	
	
	public String getTxnid() {
		return Txnid;
	}
	public void setTxnid(String txnid) {
		Txnid = txnid;
	}
	public List<Integer> getStageList() {
		return stageList;
	}
	public void setStageList(List<Integer> stageList) {
		this.stageList = stageList;
	}
	public List<Long> getTimeDiffList() {
		return timeDiffList;
	}
	public void setTimeDiffList(List<Long> timeDiffList) {
		this.timeDiffList = timeDiffList;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
