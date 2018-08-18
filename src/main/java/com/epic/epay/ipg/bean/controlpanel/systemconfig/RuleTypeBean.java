/**
 *
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

/**
 * @created on :Nov 4, 2013, 4:41:34 PM
 * @author :thushanth
 */
public class RuleTypeBean {

    private String ruletypecode;
    private String rulescope;
    private String rulescopeDes;
    private String status;
    private String description;
    private String sortkey;
    private String createdtime;
    private long fullCount;

    public String getRuletypecode() {
        return ruletypecode;
    }

    public void setRuletypecode(String ruletypecode) {
        this.ruletypecode = ruletypecode;
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

    public String getRulescope() {
        return rulescope;
    }

    public void setRulescope(String rulescope) {
        this.rulescope = rulescope;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRulescopeDes() {
        return rulescopeDes;
    }

    public void setRulescopeDes(String rulescopeDes) {
        this.rulescopeDes = rulescopeDes;
    }

}
