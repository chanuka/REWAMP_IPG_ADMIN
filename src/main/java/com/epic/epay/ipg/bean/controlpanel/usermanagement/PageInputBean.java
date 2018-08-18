/**
 *
 */
package com.epic.epay.ipg.bean.controlpanel.usermanagement;

import java.util.List;

import com.epic.epay.ipg.util.mapping.Ipgstatus;

/**
 * @author Asitha Liyanawaduge
 *
 * 25/10/2013
 */
public class PageInputBean {

    private String pageCode;
    private String description;
    private String url;
    private String pageRoot;
    private String sortKey;
    private String status;
    private String message;    
    private String createdTime;
    private List<Ipgstatus> statusList;
    
    /*-------for search -----------*/
    private String searchPageCode;
    private String searchDescription;
    private String searchSortKey;
    private String searchStatus;
    /*-------for search -----------*/

    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdatebutt;
    private boolean vupdatelink;
    private boolean vdelete;
    private boolean vsearch;
    /*-------for access control-----------*/

 /*------------------------list data table  ------------------------------*/
    private List<PageInputBean> gridModel;
    private long fullCount;
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private boolean loadonce = false;
    private boolean search = false;
    /*------------------------list data table  ------------------------------*/

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPageRoot() {
        return pageRoot;
    }

    public void setPageRoot(String pageRoot) {
        this.pageRoot = pageRoot;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
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

    public boolean isVdelete() {
        return vdelete;
    }

    public void setVdelete(boolean vdelete) {
        this.vdelete = vdelete;
    }

    public List<PageInputBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<PageInputBean> gridModel) {
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
    
    public boolean isLoadonce() {
        return loadonce;
    }

    public void setLoadonce(boolean loadonce) {
        this.loadonce = loadonce;
    }

    /**
     * @return the createdTime
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime the createdTime to set
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * @return the searchPageCode
     */
    public String getSearchPageCode() {
        return searchPageCode;
    }

    /**
     * @param searchPageCode the searchPageCode to set
     */
    public void setSearchPageCode(String searchPageCode) {
        this.searchPageCode = searchPageCode;
    }

    /**
     * @return the searchDescription
     */
    public String getSearchDescription() {
        return searchDescription;
    }

    /**
     * @param searchDescription the searchDescription to set
     */
    public void setSearchDescription(String searchDescription) {
        this.searchDescription = searchDescription;
    }

    /**
     * @return the searchSortKey
     */
    public String getSearchSortKey() {
        return searchSortKey;
    }

    /**
     * @param searchSortKey the searchSortKey to set
     */
    public void setSearchSortKey(String searchSortKey) {
        this.searchSortKey = searchSortKey;
    }

    /**
     * @return the searchStatus
     */
    public String getSearchStatus() {
        return searchStatus;
    }

    /**
     * @param searchStatus the searchStatus to set
     */
    public void setSearchStatus(String searchStatus) {
        this.searchStatus = searchStatus;
    }

    /**
     * @return the vsearch
     */
    public boolean isVsearch() {
        return vsearch;
    }

    /**
     * @param vsearch the vsearch to set
     */
    public void setVsearch(boolean vsearch) {
        this.vsearch = vsearch;
    }

    /**
     * @return the fullCount
     */
    public long getFullCount() {
        return fullCount;
    }

    /**
     * @param fullCount the fullCount to set
     */
    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    /**
     * @return the search
     */
    public boolean isSearch() {
        return search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(boolean search) {
        this.search = search;
    }

}
