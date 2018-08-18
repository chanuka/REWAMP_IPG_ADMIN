/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

import java.util.List;

/**
 * @author Asitha Liyanawaduge
 * 
 * 01/11/2013
 */
public class ServiceChargeTypeInputBean {
			
	private String serviceChargeTypeCode;
	private String description;
	private String sortKey;
	private String message;
	
	/*-------for access control-----------*/
	private boolean vadd;
	private boolean vupdatebutt;
	private boolean vupdatelink;
	private boolean vdelete;
        private boolean vsearch;
	/*-------for access control-----------*/

	/*------------------------list data table  ------------------------------*/
	private List<ServiceChargeTypeDataBean> gridModel;
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
	
	public boolean isVadd() {
		return vadd;
	}
	public String getServiceChargeTypeCode() {
		return serviceChargeTypeCode;
	}
	public void setServiceChargeTypeCode(String serviceChargeTypeCode) {
		this.serviceChargeTypeCode = serviceChargeTypeCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSortKey() {
		return sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public List<ServiceChargeTypeDataBean> getGridModel() {
		return gridModel;
	}
	public void setGridModel(List<ServiceChargeTypeDataBean> gridModel) {
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
