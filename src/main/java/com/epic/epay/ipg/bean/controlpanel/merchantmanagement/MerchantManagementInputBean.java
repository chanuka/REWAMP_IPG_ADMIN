/**
 *
 */
package com.epic.epay.ipg.bean.controlpanel.merchantmanagement;

import java.util.List;

import com.epic.epay.ipg.util.mapping.Ipgcountry;
import com.epic.epay.ipg.util.mapping.Ipgheadmerchant;
import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgriskprofile;
import com.epic.epay.ipg.util.mapping.Ipgservicecharge;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Asitha Liyanawaduge
 *
 * 19/11/2013
 */
public class MerchantManagementInputBean {

    private String merchantId;
    private String headmerchant;
    private String headmerchant2;
    private List<Ipgheadmerchant> headmerchantList = new ArrayList<Ipgheadmerchant>();
    private String email;
    private String merchantName;
    private String primaryURL;
    private String address1;
    private String secondaryURL;
    private String address2;
    private String drsURL;
    private String city;
    private String dreURL;
    private String stateCode;
    private String dateOfRegistry;
    private String postalCode;
    private String dateOfExpire;
    private String province;
    private String status;
    private String country;
    private String contactPerson;
    private String mobile;
    private String securityMechanism;
    private String telNo;
    private String remarks;
    private String fax;
    private String authreqstatus;
    private String riskprofile;
    private String message;
    private String isdefaultmerchany;
    private String defaultmerchant;
    private List<String> securitymechamismList;
    private List<Ipgstatus> statusList;
    private List<Ipgstatus> authreqstatusList;
    private List<Ipgcountry> countryList;
    private List<Ipgriskprofile> riskprofileList;
    private List<Ipgstatus> defaultmerchantList;

    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdatebutt;
    private boolean vupdatelink;
    private boolean vdelete;
    private boolean vdownload;
    private boolean vrule;
    private boolean vassignc;
    private boolean vassignr;
    private boolean vupload;
    private boolean vsearch;
    /*-------for access control-----------*/

 /*------------------------list data table  ------------------------------*/
    private List<MerchantManagementDataBean> gridModel;
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

 /*------------------------Assign Currency  ------------------------------*/
    private String merchantId_ac;

    /*------------------------Rule and service  ------------------------------*/
    private String merchantId_rs;

    private String merchantID;
    private List<Ipgmerchant> merchantList = new ArrayList<Ipgmerchant>();
    private Map<String, String> currentList = new HashMap<String, String>();
    private Map<String, String> newList = new HashMap<String, String>();
    private List<String> newBox;
    private List<String> currentBox;

    private String servicechargeID;
    private String reMarks;
    private List<Ipgservicecharge> servicechargeList;

    /*------------------------Delete ------------------------------*/
    private String merchantId_de;

//    ----------------------upload---------------------------
    private String merchantId_uc;
    private File file;
    private String fileContentType;
    private String fileFileName;

    public boolean isVadd() {
        return vadd;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPrimaryURL() {
        return primaryURL;
    }

    public void setPrimaryURL(String primaryURL) {
        this.primaryURL = primaryURL;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getSecondaryURL() {
        return secondaryURL;
    }

    public void setSecondaryURL(String secondaryURL) {
        this.secondaryURL = secondaryURL;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getDrsURL() {
        return drsURL;
    }

    public void setDrsURL(String drsURL) {
        this.drsURL = drsURL;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDreURL() {
        return dreURL;
    }

    public void setDreURL(String dreURL) {
        this.dreURL = dreURL;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getDateOfRegistry() {
        return dateOfRegistry;
    }

    public void setDateOfRegistry(String dateOfRegistry) {
        this.dateOfRegistry = dateOfRegistry;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDateOfExpire() {
        return dateOfExpire;
    }

    public void setDateOfExpire(String dateOfExpire) {
        this.dateOfExpire = dateOfExpire;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSecurityMechanism() {
        return securityMechanism;
    }

    public void setSecurityMechanism(String securityMechanism) {
        this.securityMechanism = securityMechanism;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public boolean isVdownload() {
        return vdownload;
    }

    public void setVdownload(boolean vdownload) {
        this.vdownload = vdownload;
    }

    public boolean isVrule() {
        return vrule;
    }

    public void setVrule(boolean vrule) {
        this.vrule = vrule;
    }

    public boolean isVassignc() {
        return vassignc;
    }

    public void setVassignc(boolean vassignc) {
        this.vassignc = vassignc;
    }

    public boolean isVassignr() {
        return vassignr;
    }

    public void setVassignr(boolean vassignr) {
        this.vassignr = vassignr;
    }

    public List<MerchantManagementDataBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<MerchantManagementDataBean> gridModel) {
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

    public String getMessage() {
        return message;
    }

    public List<String> getSecuritymechamismList() {
        return securitymechamismList;
    }

    public void setSecuritymechamismList(List<String> securitymechamismList) {
        this.securitymechamismList = securitymechamismList;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHeadmerchant() {
        return headmerchant;
    }

    public void setHeadmerchant(String headmerchant) {
        this.headmerchant = headmerchant;
    }

    public List<Ipgheadmerchant> getHeadmerchantList() {
        return headmerchantList;
    }

    public void setHeadmerchantList(List<Ipgheadmerchant> headmerchantList) {
        this.headmerchantList = headmerchantList;
    }

    public String getMerchantId_ac() {
        return merchantId_ac;
    }

    public void setMerchantId_ac(String merchantId_ac) {
        this.merchantId_ac = merchantId_ac;
    }

    public String getMerchantId_rs() {
        return merchantId_rs;
    }

    public void setMerchantId_rs(String merchantId_rs) {
        this.merchantId_rs = merchantId_rs;
    }

    public String getMerchantId_de() {
        return merchantId_de;
    }

    public void setMerchantId_de(String merchantId_de) {
        this.merchantId_de = merchantId_de;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public List<Ipgmerchant> getMerchantList() {
        return merchantList;
    }

    public void setMerchantList(List<Ipgmerchant> merchantList) {
        this.merchantList = merchantList;
    }

    public Map<String, String> getCurrentList() {
        return currentList;
    }

    public void setCurrentList(Map<String, String> currentList) {
        this.currentList = currentList;
    }

    public Map<String, String> getNewList() {
        return newList;
    }

    public void setNewList(Map<String, String> newList) {
        this.newList = newList;
    }

    public List<String> getNewBox() {
        return newBox;
    }

    public void setNewBox(List<String> newBox) {
        this.newBox = newBox;
    }

    public List<String> getCurrentBox() {
        return currentBox;
    }

    public void setCurrentBox(List<String> currentBox) {
        this.currentBox = currentBox;
    }

    public String getServicechargeID() {
        return servicechargeID;
    }

    public void setServicechargeID(String servicechargeID) {
        this.servicechargeID = servicechargeID;
    }

    public String getReMarks() {
        return reMarks;
    }

    public void setReMarks(String reMarks) {
        this.reMarks = reMarks;
    }

    public List<Ipgservicecharge> getServicechargeList() {
        return servicechargeList;
    }

    public void setServicechargeList(List<Ipgservicecharge> servicechargeList) {
        this.servicechargeList = servicechargeList;
    }

    public boolean isVupload() {
        return vupload;
    }

    public void setVupload(boolean vupload) {
        this.vupload = vupload;
    }

    public String getMerchantId_uc() {
        return merchantId_uc;
    }

    public void setMerchantId_uc(String merchantId_uc) {
        this.merchantId_uc = merchantId_uc;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
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

    public String getAuthreqstatus() {
        return authreqstatus;
    }

    public void setAuthreqstatus(String authreqstatus) {
        this.authreqstatus = authreqstatus;
    }

    public List<Ipgstatus> getAuthreqstatusList() {
        return authreqstatusList;
    }

    public void setAuthreqstatusList(List<Ipgstatus> authreqstatusList) {
        this.authreqstatusList = authreqstatusList;
    }

    public String getRiskprofile() {
        return riskprofile;
    }

    public void setRiskprofile(String riskprofile) {
        this.riskprofile = riskprofile;
    }

    public List<Ipgriskprofile> getRiskprofileList() {
        return riskprofileList;
    }

    public void setRiskprofileList(List<Ipgriskprofile> riskprofileList) {
        this.riskprofileList = riskprofileList;
    }

    public String getIsdefaultmerchany() {
        return isdefaultmerchany;
    }

    public void setIsdefaultmerchany(String isdefaultmerchany) {
        this.isdefaultmerchany = isdefaultmerchany;
    }

    public String getHeadmerchant2() {
        return headmerchant2;
    }

    public void setHeadmerchant2(String headmerchant2) {
        this.headmerchant2 = headmerchant2;
    }

    public String getDefaultmerchant() {
        return defaultmerchant;
    }

    public void setDefaultmerchant(String defaultmerchant) {
        this.defaultmerchant = defaultmerchant;
    }

    public List<Ipgstatus> getDefaultmerchantList() {
        return defaultmerchantList;
    }

    public void setDefaultmerchantList(List<Ipgstatus> defaultmerchantList) {
        this.defaultmerchantList = defaultmerchantList;
    }

    
}
