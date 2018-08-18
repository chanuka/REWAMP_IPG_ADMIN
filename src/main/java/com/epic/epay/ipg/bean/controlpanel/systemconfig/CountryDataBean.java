/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

/**
 * @author Asitha Liyanawaduge
 * 
 * 31/10/2013
 */
public class CountryDataBean {
	
	private String countrycode;
	private String description;
	private String sortkey;
        private String countryisocode;
        private String createdtime;
	private long fullcount;
	
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
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
        public String getCountryisocode() {
            return countryisocode;
        }
        public void setCountryisocode(String countryisocode) {
            this.countryisocode = countryisocode;
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
