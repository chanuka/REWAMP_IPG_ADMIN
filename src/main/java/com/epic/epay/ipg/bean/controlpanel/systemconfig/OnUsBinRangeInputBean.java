/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

import java.math.BigDecimal;
import java.util.List;

import com.epic.epay.ipg.util.mapping.Ipgstatus;

/**
 *  @created on :Nov 1, 2013, 10:57:20 AM
 *  @author     :thushanth
 */
public class OnUsBinRangeInputBean {
	
	
	/* ---------user inputs data-----  */
	
	private String ipgonusbinrangeId;
    private String status;
    private String value_1;
    private String value_2;
    private String reMarks;
    private List<Ipgstatus> statusList;
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
    private List<OnUsBinRangeBean> gridModel;
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
    
    
	public String getIpgonusbinrangeId() {
		return ipgonusbinrangeId;
	}
	public void setIpgonusbinrangeId(String ipgonusbinrangeId) {
		this.ipgonusbinrangeId = ipgonusbinrangeId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getValue_1() {
		return value_1;
	}
	public void setValue_1(String value_1) {
		this.value_1 = value_1;
	}
	public String getValue_2() {
		return value_2;
	}
	public void setValue_2(String value_2) {
		this.value_2 = value_2;
	}
	public String getReMarks() {
		return reMarks;
	}
	public void setReMarks(String reMarks) {
		this.reMarks = reMarks;
	}
	public List<Ipgstatus> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<Ipgstatus> statusList) {
		this.statusList = statusList;
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
	public List<OnUsBinRangeBean> getGridModel() {
		return gridModel;
	}
	public void setGridModel(List<OnUsBinRangeBean> gridModel) {
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
