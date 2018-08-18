/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.usermanagement;

/**
 * @author Asitha Liyanawaduge
 * 
 * 25/10/2013
 */
public class PageBean {
	
	private String pagecode;
    private String description;
    private String url;
    private String pageroot;
    private String sortkey;
    private String statusdes;
    private long fullCount;
    
	public String getPagecode() {
		return pagecode;
	}
	public void setPagecode(String pagecode) {
		this.pagecode = pagecode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPageroot() {
		return pageroot;
	}
	public void setPageroot(String pageroot) {
		this.pageroot = pageroot;
	}
	public String getSortkey() {
		return sortkey;
	}
	public void setSortkey(String sortkey) {
		this.sortkey = sortkey;
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

}
