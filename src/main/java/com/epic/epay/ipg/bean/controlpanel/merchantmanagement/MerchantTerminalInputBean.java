/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.controlpanel.merchantmanagement;

import com.epic.epay.ipg.util.mapping.Ipgcurrency;
import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author badrika
 */
public class MerchantTerminalInputBean {

    private String delId;
    private String merchantId;
    private String currencyCode;
    private String tid;
    private String status;
    private HashMap<String, String> currencyMap = new HashMap<String, String>();
    private String message;
    private List<Ipgmerchant> merchantList;
    private List<Ipgcurrency> currencyList;
    private List<Ipgstatus> statusList;
    /*-------for access control-----------*/
    private boolean vadd;
//    private boolean vupdatebutt;
//    private boolean vupdatelink;
    private boolean vdelete;
    private boolean vsearch;
    /*-------for access control-----------*/
    /*------------------------list data table  ------------------------------*/
    private List<MerchantTerminalBean> gridModel;
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

    public HashMap<String, String> getCurrencyMap() {
        return currencyMap;
    }

    public void setCurrencyMap(HashMap<String, String> currencyMap) {
        this.currencyMap = currencyMap;
    }

    public String getDelId() {
        return delId;
    }

    public void setDelId(String delId) {
        this.delId = delId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
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

    public List<Ipgmerchant> getMerchantList() {
        return merchantList;
    }

    public void setMerchantList(List<Ipgmerchant> merchantList) {
        this.merchantList = merchantList;
    }

    public List<Ipgcurrency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<Ipgcurrency> currencyList) {
        this.currencyList = currencyList;
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

//    public boolean isVupdatebutt() {
//        return vupdatebutt;
//    }
//
//    public void setVupdatebutt(boolean vupdatebutt) {
//        this.vupdatebutt = vupdatebutt;
//    }
//
//    public boolean isVupdatelink() {
//        return vupdatelink;
//    }
//
//    public void setVupdatelink(boolean vupdatelink) {
//        this.vupdatelink = vupdatelink;
//    }
    public boolean isVdelete() {
        return vdelete;
    }

    public void setVdelete(boolean vdelete) {
        this.vdelete = vdelete;
    }

    public List<MerchantTerminalBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<MerchantTerminalBean> gridModel) {
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
