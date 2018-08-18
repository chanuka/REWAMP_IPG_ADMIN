/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

/**
 *  @created :Oct 28, 2013, 9:18:00 AM
 *  @author  :thushanth
 */
public class PasswordPolicyBean {
	
    private String passwordpolicyid;
    private String minimumlength;
    private String maximumlength;
    private String allowedspacialchars;
    private String minimumspacialchars;
    private String minimumuppercasechars;
    private String minimumnumericalchars;
    private String minimumlowercasechars;
    private String createdtime;
    private String noofpincount;
    private String status;
    private long fullCount;
	public String getPasswordpolicyid() {
		return passwordpolicyid;
	}
	public void setPasswordpolicyid(String passwordpolicyid) {
		this.passwordpolicyid = passwordpolicyid;
	}
	public String getMinimumlength() {
		return minimumlength;
	}
	public void setMinimumlength(String minimumlength) {
		this.minimumlength = minimumlength;
	}
	public String getMaximumlength() {
		return maximumlength;
	}
	public void setMaximumlength(String maximumlength) {
		this.maximumlength = maximumlength;
	}
	public String getAllowedspacialchars() {
		return allowedspacialchars;
	}
	public void setAllowedspacialchars(String allowedspacialchars) {
		this.allowedspacialchars = allowedspacialchars;
	}
	public String getMinimumspacialchars() {
		return minimumspacialchars;
	}
	public void setMinimumspacialchars(String minimumspacialchars) {
		this.minimumspacialchars = minimumspacialchars;
	}
	public String getMinimumuppercasechars() {
		return minimumuppercasechars;
	}
	public void setMinimumuppercasechars(String minimumuppercasechars) {
		this.minimumuppercasechars = minimumuppercasechars;
	}
	public String getMinimumnumericalchars() {
		return minimumnumericalchars;
	}
	public void setMinimumnumericalchars(String minimumnumericalchars) {
		this.minimumnumericalchars = minimumnumericalchars;
	}
	public String getMinimumlowercasechars() {
		return minimumlowercasechars;
	}
	public void setMinimumlowercasechars(String minimumlowercasechars) {
		this.minimumlowercasechars = minimumlowercasechars;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getFullCount() {
		return fullCount;
	}
	public void setFullCount(long fullCount) {
		this.fullCount = fullCount;
	}
        public String getNoofpincount() {
            return noofpincount;
        }

        public void setNoofpincount(String noofpincount) {
            this.noofpincount = noofpincount;
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
