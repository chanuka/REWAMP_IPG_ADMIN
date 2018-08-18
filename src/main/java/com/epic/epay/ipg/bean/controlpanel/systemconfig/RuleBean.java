/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

/**
 *  @created on :Nov 5, 2013, 2:47:29 PM
 *  @author     :thushanth
 */
public class RuleBean {
	
     private String ruleid;
     private String securitylevel;
     private String ruletype;
     private String rulescope;
     private String operator;
     private String startvalue;
     private String endvalue;
     private String triggersequenceid;
     private String priority;
     private String description;
     private String status;
     private String createdtime;
     
     private long fullCount;
     
     
	public String getRuleid() {
		return ruleid;
	}
	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}
	public String getSecuritylevel() {
		return securitylevel;
	}
	public void setSecuritylevel(String securitylevel) {
		this.securitylevel = securitylevel;
	}
	public String getRuletype() {
		return ruletype;
	}
	public void setRuletype(String ruletype) {
		this.ruletype = ruletype;
	}
	public String getRulescope() {
		return rulescope;
	}
	public void setRulescope(String rulescope) {
		this.rulescope = rulescope;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getStartvalue() {
		return startvalue;
	}
	public void setStartvalue(String startvalue) {
		this.startvalue = startvalue;
	}
	public String getEndvalue() {
		return endvalue;
	}
	public void setEndvalue(String endvalue) {
		this.endvalue = endvalue;
	}
	public String getTriggersequenceid() {
		return triggersequenceid;
	}
	public void setTriggersequenceid(String triggersequenceid) {
		this.triggersequenceid = triggersequenceid;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public long getFullCount() {
		return fullCount;
	}
	public void setFullCount(long fullCount) {
		this.fullCount = fullCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
