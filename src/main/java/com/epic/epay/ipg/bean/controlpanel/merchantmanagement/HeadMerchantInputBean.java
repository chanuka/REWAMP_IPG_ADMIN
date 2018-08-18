/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.controlpanel.merchantmanagement;

import com.epic.epay.ipg.util.mapping.Ipgcountry;
import com.epic.epay.ipg.util.mapping.Ipgriskprofile;
import com.epic.epay.ipg.util.mapping.Ipgsecuritylevel;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import java.util.List;

/**
 *
 * @author Chalitha
 */
public class HeadMerchantInputBean {

    private String merchantcustomerid;
    private String merchantid;
    private String merchantname;
    private String address1;
    private String address2;
    private String city;
    private String statecode;
    private String postalcode;
    private String province;
    private String country;
    private String mobile;
    private String tel;
    private String fax;
    private String email;
    private String primaryurl;
    private String secondaryurl;
    private String dateofregistry;
    private String dateofexpiry;
    private String status;
    private String contactperson;
    private String securitymechanism;
    private String remarks;
    private String riskprofile;
    private String authreqstatus;
    private String txninitstatus;
    private String dynamicreturnsuccessurl;
    private String dynamicreturnerrorurl;
    private List<Ipgcountry> countryList;
    private List<Ipgstatus> statusList;
    private List<Ipgriskprofile> riskprofileList;
    private List<Ipgstatus> authreqstatusList;
    private List<Ipgstatus> txninitstatusList;
    private String message;
    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdatebutt;
    private boolean vupdatelink;
    private boolean vdelete;
    private boolean vsearch;
    /*-------for access control-----------*/
    /*------------------------list data table  ------------------------------*/
    private List<HeadMerchantBean> gridModel;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMerchantcustomerid() {
        return merchantcustomerid;
    }

    public void setMerchantcustomerid(String merchantcustomerid) {
        this.merchantcustomerid = merchantcustomerid;
    }

    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
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

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimaryurl() {
        return primaryurl;
    }

    public void setPrimaryurl(String primaryurl) {
        this.primaryurl = primaryurl;
    }

    public String getSecondaryurl() {
        return secondaryurl;
    }

    public void setSecondaryurl(String secondaryurl) {
        this.secondaryurl = secondaryurl;
    }

    public String getDateofregistry() {
        return dateofregistry;
    }

    public void setDateofregistry(String dateofregistry) {
        this.dateofregistry = dateofregistry;
    }

    public String getDateofexpiry() {
        return dateofexpiry;
    }

    public void setDateofexpiry(String dateofexpiry) {
        this.dateofexpiry = dateofexpiry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getSecuritymechanism() {
        return securitymechanism;
    }

    public void setSecuritymechanism(String securitymechanism) {
        this.securitymechanism = securitymechanism;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRiskprofile() {
        return riskprofile;
    }

    public void setRiskprofile(String riskprofile) {
        this.riskprofile = riskprofile;
    }

    public String getAuthreqstatus() {
        return authreqstatus;
    }

    public void setAuthreqstatus(String authreqstatus) {
        this.authreqstatus = authreqstatus;
    }

    public String getTxninitstatus() {
        return txninitstatus;
    }

    public void setTxninitstatus(String txninitstatus) {
        this.txninitstatus = txninitstatus;
    }

    public List<Ipgcountry> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Ipgcountry> countryList) {
        this.countryList = countryList;
    }

    public List<Ipgstatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Ipgstatus> statusList) {
        this.statusList = statusList;
    }

    public List<Ipgriskprofile> getRiskprofileList() {
        return riskprofileList;
    }

    public void setRiskprofileList(List<Ipgriskprofile> riskprofileList) {
        this.riskprofileList = riskprofileList;
    }

    public List<Ipgstatus> getAuthreqstatusList() {
        return authreqstatusList;
    }

    public void setAuthreqstatusList(List<Ipgstatus> authreqstatusList) {
        this.authreqstatusList = authreqstatusList;
    }

    public List<Ipgstatus> getTxninitstatusList() {
        return txninitstatusList;
    }

    public void setTxninitstatusList(List<Ipgstatus> txninitstatusList) {
        this.txninitstatusList = txninitstatusList;
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

    public List<HeadMerchantBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<HeadMerchantBean> gridModel) {
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

    public String getDynamicreturnsuccessurl() {
        return dynamicreturnsuccessurl;
    }

    public void setDynamicreturnsuccessurl(String dynamicreturnsuccessurl) {
        this.dynamicreturnsuccessurl = dynamicreturnsuccessurl;
    }

    public String getDynamicreturnerrorurl() {
        return dynamicreturnerrorurl;
    }

    public void setDynamicreturnerrorurl(String dynamicreturnerrorurl) {
        this.dynamicreturnerrorurl = dynamicreturnerrorurl;
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
