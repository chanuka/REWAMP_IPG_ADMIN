/**
 *
 */
package com.epic.epay.ipg.bean.transactionmanagement;

import java.util.List;

import com.epic.epay.ipg.util.mapping.Ipgstatus;

/**
 * @author Asitha Liyanawaduge
 *
 * 04/12/2013
 */
public class MerchantTracsactionSettlementInputBean {

    private String headMerchantId;
    private String headMerchantName;
    private String merchantId;
    private String merchantName;
    private String terminalId;
    private String loginUser;
    private String status;
    private String key;
    private String batchId;
    private String uname;
    private String passwordval;
    private MerchantTracsactionSettlementDataBean bean;

    private List<Ipgstatus> statusList;

    private boolean vsearch;
    private boolean vsettle;
    private boolean vprocess;

    private boolean search;
    private List<MerchantTracsactionSettlementDataBean> gridModel;
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;
    private long fullCount;

    public String getHeadMerchantId() {
        return headMerchantId;
    }

    public void setHeadMerchantId(String headMerchantId) {
        this.headMerchantId = headMerchantId;
    }

    public String getHeadMerchantName() {
        return headMerchantName;
    }

    public void setHeadMerchantName(String headMerchantName) {
        this.headMerchantName = headMerchantName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public MerchantTracsactionSettlementDataBean getBean() {
        return bean;
    }

    public void setBean(MerchantTracsactionSettlementDataBean bean) {
        this.bean = bean;
    }

    
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MerchantTracsactionSettlementDataBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<MerchantTracsactionSettlementDataBean> gridModel) {
        this.gridModel = gridModel;
    }

    public Integer getRows() {
        return rows;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
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

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public List<Ipgstatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Ipgstatus> statusList) {
        this.statusList = statusList;
    }

    public boolean isVsearch() {
        return vsearch;
    }

    public void setVsearch(boolean vsearch) {
        this.vsearch = vsearch;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public boolean isVsettle() {
        return vsettle;
    }

    public void setVsettle(boolean vsettle) {
        this.vsettle = vsettle;
    }

    public boolean isVprocess() {
        return vprocess;
    }

    public void setVprocess(boolean vprocess) {
        this.vprocess = vprocess;
    }

    public String getPasswordval() {
        return passwordval;
    }

    public void setPasswordval(String passwordval) {
        this.passwordval = passwordval;
    }

    
}
