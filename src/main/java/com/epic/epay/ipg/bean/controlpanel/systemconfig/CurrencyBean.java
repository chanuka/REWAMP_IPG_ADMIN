/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

/**
 *  @created on :Oct 31, 2013, 10:07:06 AM
 *  @author     :thushanth
 */
public class CurrencyBean {
	
    private String currencyisocode;
    private String currencycode;
    private String description;
    private String sortkey;
    private String createdtime;
    private long fullCount;
    
	public String getCurrencyisocode() {
		return currencyisocode;
	}
	public void setCurrencyisocode(String currencyisocode) {
		this.currencyisocode = currencyisocode;
	}
	public String getCurrencycode() {
		return currencycode;
	}
	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
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
    
    

}
