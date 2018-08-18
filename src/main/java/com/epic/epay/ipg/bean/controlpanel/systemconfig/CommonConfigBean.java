/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

/**
 *  @created :Oct 29, 2013, 9:25:13 AM
 *  @author  :thushanth
 */
public class CommonConfigBean {
	
    private String code;
    private String description;
    private String value;
    private String lastupdateduser;
    private String lastupdatedtime;
    private String createdtime;
    private long fullCount;

    
    public long getFullCount() {
            return fullCount;
    }
    public void setFullCount(long fullCount) {
            this.fullCount = fullCount;
    }
    
    public String getCreatedtime() {
        return createdtime;
    }
    
    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the lastupdateduser
     */
    public String getLastupdateduser() {
        return lastupdateduser;
    }

    /**
     * @param lastupdateduser the lastupdateduser to set
     */
    public void setLastupdateduser(String lastupdateduser) {
        this.lastupdateduser = lastupdateduser;
    }

    /**
     * @return the lastupdatedtime
     */
    public String getLastupdatedtime() {
        return lastupdatedtime;
    }

    /**
     * @param lastupdatedtime the lastupdatedtime to set
     */
    public void setLastupdatedtime(String lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }
    
    
    

}
