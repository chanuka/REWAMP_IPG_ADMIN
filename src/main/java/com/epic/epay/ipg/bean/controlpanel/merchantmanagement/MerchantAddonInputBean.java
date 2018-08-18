/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.merchantmanagement;

import java.util.List;

import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgservicecharge;

/**
 * 
 * @created on : Nov 19, 2013, 11:34:20 AM
 * @author     : thushanth
 *
 */
public class MerchantAddonInputBean {
	
/* ---------user inputs data-----  */	
	
    private String merchantID;
    private String merchantNameID;
    private String logoPath;
    private String cordinateX;
    private String cordinateY;
    private String displayText;
    private String themeColor;
    private String fontFamily;
    private String fontStyle;
    private String fontSize;
    private String remarks;
    private List<Ipgmerchant> merchantList;
    private List<String> fontfamilyList;
    private List<String> fontstyleList;
    private List<String> fontsizeList;
    private String message;
    
    /* ---------user inputs data-----  */
    
    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdatebutt;
    private boolean vupdatelink;
    private boolean vdelete;
    private boolean vsearch;
    /*-------for access control-----------*/
    
    /*------------------------list data table  ------------------------------*/
    private List<MerchantAddonBean> gridModel;
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;
    private boolean loadonce = false;
    private boolean search = false;
    /*------------------------list data table  ------------------------------*/
    
    
    
	public String getMerchantID() {
		return merchantID;
	}
	public String getMerchantNameID() {
		return merchantNameID;
	}
	public void setMerchantNameID(String merchantNameID) {
		this.merchantNameID = merchantNameID;
	}
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public String getCordinateX() {
		return cordinateX;
	}
	public void setCordinateX(String cordinateX) {
		this.cordinateX = cordinateX;
	}
	public String getCordinateY() {
		return cordinateY;
	}
	public void setCordinateY(String cordinateY) {
		this.cordinateY = cordinateY;
	}
	public String getDisplayText() {
		return displayText;
	}
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	public String getThemeColor() {
		return themeColor;
	}
	public void setThemeColor(String themeColor) {
		this.themeColor = themeColor;
	}
	public String getFontFamily() {
		return fontFamily;
	}
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}
	public String getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}
	public String getFontSize() {
		return fontSize;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<Ipgmerchant> getMerchantList() {
		return merchantList;
	}
	public void setMerchantList(List<Ipgmerchant> merchantList) {
		this.merchantList = merchantList;
	}
	public List<String> getFontfamilyList() {
		return fontfamilyList;
	}
	public void setFontfamilyList(List<String> fontfamilyList) {
		this.fontfamilyList = fontfamilyList;
	}
	public List<String> getFontstyleList() {
		return fontstyleList;
	}
	public void setFontstyleList(List<String> fontstyleList) {
		this.fontstyleList = fontstyleList;
	}
	public List<String> getFontsizeList() {
		return fontsizeList;
	}
	public void setFontsizeList(List<String> fontsizeList) {
		this.fontsizeList = fontsizeList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isVadd() {
		return vadd;
	}
	public void setVadd(boolean vadd) {
		this.vadd = vadd;
	}
	public boolean isVupdatebutt() {
		return vupdatebutt;
	}
	public void setVupdatebutt(boolean vupdatebutt) {
		this.vupdatebutt = vupdatebutt;
	}
	public boolean isVupdatelink() {
		return vupdatelink;
	}
	public void setVupdatelink(boolean vupdatelink) {
		this.vupdatelink = vupdatelink;
	}
	public boolean isVdelete() {
		return vdelete;
	}
	public void setVdelete(boolean vdelete) {
		this.vdelete = vdelete;
	}
	public List<MerchantAddonBean> getGridModel() {
		return gridModel;
	}
	public void setGridModel(List<MerchantAddonBean> gridModel) {
		this.gridModel = gridModel;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Long getRecords() {
		return records;
	}
	public void setRecords(Long records) {
		this.records = records;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getSearchOper() {
		return searchOper;
	}
	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}
	public boolean isLoadonce() {
		return loadonce;
	}
	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}
        public boolean isVsearch() {
            return vsearch;
        }
        public void setVsearch(boolean vsearch) {
            this.vsearch = vsearch;
        }
        public boolean isSearch() {
            return search;
        }
        public void setSearch(boolean search) {
            this.search = search;
        }
    
    
	
}
