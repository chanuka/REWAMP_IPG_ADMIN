/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.usermanagement;

import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipguserrole;
import java.util.List;

/**
 *  @created :Oct 25, 2013, 2:48:09 PM
 *  @author  :thushanth
 */
public class SystemUserInputBean {
	
	/* ---------user inputs data-----  */
    private String userID;
    private String username;
    private String password;
    private String confirmpassword;
    private String userrole;
    private String dualAuth;
    private String mainuserrole;
    private String mername;
    private String status;
    private String empid;
    private String expirydate;
    private String fullname;
    private String address1;
    private String address2;
    private String city;
    private String mobile;
    private String telno;
    private String fax;
    private String mail;
    private String pwtooltip;
    private List<Ipguserrole> userroleList;
    private List<Ipgstatus> statusList;
    private List<Ipgmerchant> merchantList;
    private String message;
    /* ---------user inputs data-----  */
    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdatebutt;
    private boolean vupdatelink;
    private boolean vsearch;
    private boolean vdelete;
    /*-------for access control-----------*/
    /*------------------------list data table  ------------------------------*/
    private List<SystemUserBean> gridModel;
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
    private boolean search;
    /*------------------------list data table  ------------------------------*/
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmpassword() {
		return confirmpassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	public String getUserrole() {
		return userrole;
	}
	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getExpirydate() {
		return expirydate;
	}
	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public List<Ipguserrole> getUserroleList() {
		return userroleList;
	}
	public void setUserroleList(List<Ipguserrole> userroleList) {
		this.userroleList = userroleList;
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
	public List<SystemUserBean> getGridModel() {
		return gridModel;
	}
	public void setGridModel(List<SystemUserBean> gridModel) {
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
        public List<Ipgmerchant> getMerchantList() {
            return merchantList;
        }

        public void setMerchantList(List<Ipgmerchant> merchantList) {
            this.merchantList = merchantList;
        } 
        public String getMainuserrole() {
            return mainuserrole;
        }

        public void setMainuserrole(String mainuserrole) {
            this.mainuserrole = mainuserrole;
        }       
        public String getMername() {
            return mername;
        }
        public void setMername(String mername) {
            this.mername = mername;
        }
        
        public String getUserID() {
            return userID;
        }        
        public void setUserID(String userID) {
            this.userID = userID;
        } 
        
        public String getDualAuth() {
            return dualAuth;
        }        
        public void setDualAuth(String dualAuth) {
            this.dualAuth = dualAuth;
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

        public String getPwtooltip() {
            return pwtooltip;
        }

        public void setPwtooltip(String pwtooltip) {
            this.pwtooltip = pwtooltip;
        }
        
}
