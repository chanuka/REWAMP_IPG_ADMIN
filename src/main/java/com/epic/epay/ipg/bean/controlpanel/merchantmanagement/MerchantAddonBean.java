/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.merchantmanagement;

/**
 * 
 * @created on : Nov 19, 2013, 11:33:58 AM
 * @author     : thushanth
 *
 */
public class MerchantAddonBean {
	
    private String merchantId;
    private String merchantname;
    private String logopath;
    private String xcordinate;
    private String ycordinate;
    private String displaytext;
    private String themecolor;
    private String fontfamily;
    private String fontstyle;
    private String fontsize;
    private String remarks;
    private String createdtime;
    private long fullCount;
    
    
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getLogopath() {
		return logopath;
	}
	public void setLogopath(String logopath) {
		this.logopath = logopath;
	}
	public String getXcordinate() {
		return xcordinate;
	}
	public void setXcordinate(String xcordinate) {
		this.xcordinate = xcordinate;
	}
	public String getYcordinate() {
		return ycordinate;
	}
	public void setYcordinate(String ycordinate) {
		this.ycordinate = ycordinate;
	}
	public String getDisplaytext() {
		return displaytext;
	}
	public void setDisplaytext(String displaytext) {
		this.displaytext = displaytext;
	}
	public String getThemecolor() {
		return themecolor;
	}
	public void setThemecolor(String themecolor) {
		this.themecolor = themecolor;
	}
	public String getFontfamily() {
		return fontfamily;
	}
	public void setFontfamily(String fontfamily) {
		this.fontfamily = fontfamily;
	}
	public String getFontstyle() {
		return fontstyle;
	}
	public void setFontstyle(String fontstyle) {
		this.fontstyle = fontstyle;
	}
	public String getFontsize() {
		return fontsize;
	}
	public void setFontsize(String fontsize) {
		this.fontsize = fontsize;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public long getFullCount() {
		return fullCount;
	}
	public void setFullCount(long fullCount) {
		this.fullCount = fullCount;
	}
	public String getMerchantname() {
		return merchantname;
	}
	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
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
