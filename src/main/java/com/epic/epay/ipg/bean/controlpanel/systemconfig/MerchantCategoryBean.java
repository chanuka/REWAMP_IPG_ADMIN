/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

/**
 * @author Asitha Liyanawaduge
 * 
 * 30/10/2013
 */
public class MerchantCategoryBean {
	
	private String mcc;
    private String description;
    private String statusdes;
    private String createdtime;
    private long fullCount;
    
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatusdes() {
		return statusdes;
	}
	public void setStatusdes(String statusdes) {
		this.statusdes = statusdes;
	}
	public long getFullCount() {
		return fullCount;
	}
	public void setFullCount(long fullCount) {
		this.fullCount = fullCount;
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
