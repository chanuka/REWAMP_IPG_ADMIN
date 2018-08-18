/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

import com.epic.epay.ipg.util.mapping.Ipgstatus;
import java.util.List;

/**
 *  @created :Oct 28, 2013, 9:18:27 AM
 *  @author  :thushanth
 */
public class PasswordPolicyInputBean {
	
	 /*********** user input data ***********************/
    private String passwordpolicyid;
    private String minimumlength;
    private String maximumlength;
    private String allowedspacialchars;
    private String minimumspacialchars;
    private String minimumuppercasechars;
    private String minimumnumericalchars;
    private String minimumlowercasechars;
    private String noofPINcount;
    private String status;
    private String message;
    private List<Ipgstatus> statusList;
    /*********** user input data ***********************/
    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdatebutt;
    private boolean vupdatelink;
    private boolean policyid;
    /*-------for access control-----------*/
    /*------------------------list data table  ------------------------------*/
    private List<PasswordPolicyBean> gridModel;
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
    /*------------------------list data table  ------------------------------*/
	public String getPasswordpolicyid() {
		return passwordpolicyid;
	}
	public void setPasswordpolicyid(String passwordpolicyid) {
		this.passwordpolicyid = passwordpolicyid;
	}
	public String getMinimumlength() {
		return minimumlength;
	}
	public void setMinimumlength(String minimumlength) {
		this.minimumlength = minimumlength;
	}
	public String getMaximumlength() {
		return maximumlength;
	}
	public void setMaximumlength(String maximumlength) {
		this.maximumlength = maximumlength;
	}
	public String getAllowedspacialchars() {
		return allowedspacialchars;
	}
	public void setAllowedspacialchars(String allowedspacialchars) {
		this.allowedspacialchars = allowedspacialchars;
	}
	public String getMinimumspacialchars() {
		return minimumspacialchars;
	}
	public void setMinimumspacialchars(String minimumspacialchars) {
		this.minimumspacialchars = minimumspacialchars;
	}
	public String getMinimumuppercasechars() {
		return minimumuppercasechars;
	}
	public void setMinimumuppercasechars(String minimumuppercasechars) {
		this.minimumuppercasechars = minimumuppercasechars;
	}
	public String getMinimumnumericalchars() {
		return minimumnumericalchars;
	}
	public void setMinimumnumericalchars(String minimumnumericalchars) {
		this.minimumnumericalchars = minimumnumericalchars;
	}
	public String getMinimumlowercasechars() {
		return minimumlowercasechars;
	}
	public void setMinimumlowercasechars(String minimumlowercasechars) {
		this.minimumlowercasechars = minimumlowercasechars;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Ipgstatus> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<Ipgstatus> statusList) {
		this.statusList = statusList;
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
	public boolean isPolicyid() {
		return policyid;
	}
	public void setPolicyid(boolean policyid) {
		this.policyid = policyid;
	}
	public List<PasswordPolicyBean> getGridModel() {
		return gridModel;
	}
	public void setGridModel(List<PasswordPolicyBean> gridModel) {
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
        public String getNoofPINcount() {
            return noofPINcount;
        }

        public void setNoofPINcount(String noofPINcount) {
            this.noofPINcount = noofPINcount;
        }

}
