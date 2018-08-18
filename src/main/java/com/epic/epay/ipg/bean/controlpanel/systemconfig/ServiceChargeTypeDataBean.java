/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

/**
 * @author Asitha Liyanawaduge
 * 
 * 01/11/2013
 */
public class ServiceChargeTypeDataBean {
	
	private String servicechargetypecode;
	private String description;
	private String sortkey;
        private String createdtime;
	private long fullcount;
	
	public String getServicechargetypecode() {
		return servicechargetypecode;
	}
	public void setServicechargetypecode(String servicechargetypecode) {
		this.servicechargetypecode = servicechargetypecode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSortkey() {
		return sortkey;
	}
	public void setSortkey(String sortkey) {
		this.sortkey = sortkey;
	}
	public long getFullcount() {
		return fullcount;
	}
	public void setFullcount(long fullcount) {
		this.fullcount = fullcount;
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
