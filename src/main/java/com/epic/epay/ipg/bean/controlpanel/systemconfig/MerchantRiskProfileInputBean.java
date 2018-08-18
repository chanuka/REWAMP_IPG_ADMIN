/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

import com.epic.epay.ipg.util.mapping.Ipgcountry;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author chalitha
 */
public class MerchantRiskProfileInputBean {

    private String riskprofilecode;
    private String description;
    private String status;
    private String maxsingletxnlimit;
    private String minsingletxnlimit;
    private String maxtxncount;
    private String maxpasswordcount;
    private String maxtxnamount;
    private String message;
    public List<String> allowedbin;
    public List<String> blockedbin;
    public List<String> allowedcurrencytypes;
    public List<String> blockedcurrencytypes;
    public List<String> allowedcategories;
    public List<String> blockedcategories;
    private List<String> allowedcountry;
    private List<String> blockedcountry;
    private List<Ipgstatus> statusList;
    private Map<String, String> allowedcountryList = new HashMap<String, String>();
    private Map<String, String> blockedcountryList = new HashMap<String, String>();
    private Map<String, String> allowedmerchantcategoriesList = new HashMap<String, String>();
    private Map<String, String> blockedmerchantcategoriesList = new HashMap<String, String>();
    private Map<String, String> allowedcurrencytypesList = new HashMap<String, String>();
    private Map<String, String> blockedcurrencytypesList = new HashMap<String, String>();
    private Map<String, String> allowedbinList = new HashMap<String, String>();
    private Map<String, String> blockedbinList = new HashMap<String, String>();
    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdatebutt;
    private boolean vupdatelink;
    private boolean vdelete;
    private boolean vsearch;
    /*-------for access control-----------*/
    /*------------------------list data table  ------------------------------*/
    private List<MerchantRiskProfileBean> gridModel;
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

    public String getRiskprofilecode() {
        return riskprofilecode;
    }

    public void setRiskprofilecode(String riskprofilecode) {
        this.riskprofilecode = riskprofilecode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMaxsingletxnlimit() {
        return maxsingletxnlimit;
    }

    public void setMaxsingletxnlimit(String maxsingletxnlimit) {
        this.maxsingletxnlimit = maxsingletxnlimit;
    }

    public String getMinsingletxnlimit() {
        return minsingletxnlimit;
    }

    public void setMinsingletxnlimit(String minsingletxnlimit) {
        this.minsingletxnlimit = minsingletxnlimit;
    }

    public String getMaxtxncount() {
        return maxtxncount;
    }

    public void setMaxtxncount(String maxtxncount) {
        this.maxtxncount = maxtxncount;
    }

    public String getMaxpasswordcount() {
        return maxpasswordcount;
    }

    public void setMaxpasswordcount(String maxpasswordcount) {
        this.maxpasswordcount = maxpasswordcount;
    }

    public String getMaxtxnamount() {
        return maxtxnamount;
    }

    public void setMaxtxnamount(String maxtxnamount) {
        this.maxtxnamount = maxtxnamount;
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

    public List<MerchantRiskProfileBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<MerchantRiskProfileBean> gridModel) {
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

    public List<Ipgstatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Ipgstatus> statusList) {
        this.statusList = statusList;
    }
    
    public List<String> getBlockedcountry() {
        return blockedcountry;
    }

    public void setBlockedcountry(List<String> blockedcountry) {
        this.blockedcountry = blockedcountry;
    }

    public Map<String, String> getBlockedcountryList() {
        return blockedcountryList;
    }

    public void setBlockedcountryList(Map<String, String> blockedcountryList) {
        this.blockedcountryList = blockedcountryList;
    }

    public List<String> getAllowedcountry() {
        return allowedcountry;
    }

    public void setAllowedcountry(List<String> allowedcountry) {
        this.allowedcountry = allowedcountry;
    }

    public Map<String, String> getAllowedcountryList() {
        return allowedcountryList;
    }

    public void setAllowedcountryList(Map<String, String> allowedcountryList) {
        this.allowedcountryList = allowedcountryList;
    }

    public List<String> getAllowedcategories() {
        return allowedcategories;
    }

    public void setAllowedcategories(List<String> allowedcategories) {
        this.allowedcategories = allowedcategories;
    }

    public List<String> getBlockedcategories() {
        return blockedcategories;
    }

    public void setBlockedcategories(List<String> blockedcategories) {
        this.blockedcategories = blockedcategories;
    }

    public Map<String, String> getAllowedmerchantcategoriesList() {
        return allowedmerchantcategoriesList;
    }

    public void setAllowedmerchantcategoriesList(Map<String, String> allowedmerchantcategoriesList) {
        this.allowedmerchantcategoriesList = allowedmerchantcategoriesList;
    }

    public Map<String, String> getBlockedmerchantcategoriesList() {
        return blockedmerchantcategoriesList;
    }

    public void setBlockedmerchantcategoriesList(Map<String, String> blockedmerchantcategoriesList) {
        this.blockedmerchantcategoriesList = blockedmerchantcategoriesList;
    }

    public List<String> getAllowedbin() {
        return allowedbin;
    }

    public void setAllowedbin(List<String> allowedbin) {
        this.allowedbin = allowedbin;
    }

    public List<String> getBlockedbin() {
        return blockedbin;
    }

    public void setBlockedbin(List<String> blockedbin) {
        this.blockedbin = blockedbin;
    }

    public List<String> getAllowedcurrencytypes() {
        return allowedcurrencytypes;
    }

    public void setAllowedcurrencytypes(List<String> allowedcurrencytypes) {
        this.allowedcurrencytypes = allowedcurrencytypes;
    }

    public List<String> getBlockedcurrencytypes() {
        return blockedcurrencytypes;
    }

    public void setBlockedcurrencytypes(List<String> blockedcurrencytypes) {
        this.blockedcurrencytypes = blockedcurrencytypes;
    }

    public Map<String, String> getAllowedcurrencytypesList() {
        return allowedcurrencytypesList;
    }

    public void setAllowedcurrencytypesList(Map<String, String> allowedcurrencytypesList) {
        this.allowedcurrencytypesList = allowedcurrencytypesList;
    }

    public Map<String, String> getBlockedcurrencytypesList() {
        return blockedcurrencytypesList;
    }

    public void setBlockedcurrencytypesList(Map<String, String> blockedcurrencytypesList) {
        this.blockedcurrencytypesList = blockedcurrencytypesList;
    }

    public Map<String, String> getAllowedbinList() {
        return allowedbinList;
    }

    public void setAllowedbinList(Map<String, String> allowedbinList) {
        this.allowedbinList = allowedbinList;
    }

    public Map<String, String> getBlockedbinList() {
        return blockedbinList;
    }

    public void setBlockedbinList(Map<String, String> blockedbinList) {
        this.blockedbinList = blockedbinList;
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
