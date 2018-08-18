/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;


/**
 *  @created on :Nov 1, 2013, 10:57:03 AM
 *  @author     :thushanth
 */
public class OnUsBinRangeBean {
	
    private String ipgonusbinrangeid;
    private String statuscode;
    private String value1;
    private String value2;
    private String remarks;
    private String createdtime;
    private long fullCount;
    
    
	public String getIpgonusbinrangeid() {
		return ipgonusbinrangeid;
	}
	public void setIpgonusbinrangeid(String ipgonusbinrangeid) {
		this.ipgonusbinrangeid = ipgonusbinrangeid;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public long getFullCount() {
		return fullCount;
	}
	public void setFullCount(long fullCount) {
		this.fullCount = fullCount;
	}
	public String getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}

    /**
     * @return the createdtime
     */
    public String getCreatedtime() {
        return createdtime;
    }

    /**
     * @param createdtime the createdtime to set
     */
    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }
	

}
