/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.usermanagement;

/**
 * @author Asitha Liyanawaduge
 * 
 * 28/10/2013
 */
public class UserRoleBean {
	
	private String userRoleCode;
    private String userRoleTypeDes;
    private String description;
    private String statusDes;
    private String sortKey;
    private long fullCount;
    
	public String getUserRoleCode() {
		return userRoleCode;
	}
	public void setUserRoleCode(String userRoleCode) {
		this.userRoleCode = userRoleCode;
	}
	public String getUserRoleTypeDes() {
		return userRoleTypeDes;
	}
	public void setUserRoleTypeDes(String userRoleTypeDes) {
		this.userRoleTypeDes = userRoleTypeDes;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatusDes() {
		return statusDes;
	}
	public void setStatusDes(String statusDes) {
		this.statusDes = statusDes;
	}
	public String getSortKey() {
		return sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	public long getFullCount() {
		return fullCount;
	}
	public void setFullCount(long fullCount) {
		this.fullCount = fullCount;
	}

}
