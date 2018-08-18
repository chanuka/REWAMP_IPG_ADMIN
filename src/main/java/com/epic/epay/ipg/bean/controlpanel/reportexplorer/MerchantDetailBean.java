/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.reportexplorer;

/**
 *
 * Created on  :Sep 16, 2014, 2:33:10 PM
 * @author 	   :thushanth
 *
 */
public class MerchantDetailBean {
	
	private String merchantname;
    private String addressid;
    private String priURL;
    private String country;
    private String merstatus;
    private String contperson;
    private String merremarks;
    private long fullCount;
    
    
	public String getMerchantname() {
		return merchantname;
	}
	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}
	public String getAddressid() {
		return addressid;
	}
	public void setAddressid(String addressid) {
		this.addressid = addressid;
	}
	public String getPriURL() {
		return priURL;
	}
	public void setPriURL(String priURL) {
		this.priURL = priURL;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMerstatus() {
		return merstatus;
	}
	public void setMerstatus(String merstatus) {
		this.merstatus = merstatus;
	}
	public String getContperson() {
		return contperson;
	}
	public void setContperson(String contperson) {
		this.contperson = contperson;
	}
	public String getMerremarks() {
		return merremarks;
	}
	public void setMerremarks(String merremarks) {
		this.merremarks = merremarks;
	}
	public long getFullCount() {
		return fullCount;
	}
	public void setFullCount(long fullCount) {
		this.fullCount = fullCount;
	}
}
