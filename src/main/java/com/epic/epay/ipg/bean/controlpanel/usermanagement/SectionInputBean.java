package com.epic.epay.ipg.bean.controlpanel.usermanagement;

import java.io.Serializable;
import java.util.List;

import com.epic.epay.ipg.util.mapping.Ipgstatus;

/**
 *
 * @author chalitha
 */
public class SectionInputBean implements Serializable {
    
    private String sectioncode;
    private String description;
    private String status;
    private String sortKey;
    private String message;
    private String createdTime;
    private List<Ipgstatus> statusList;
    
    /*-------for search -----------*/
    private String searchSectionCode;
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
    private List<SectionInputBean> gridModel;
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
    
    public String getDescription() {
        return description;
    }

    public String getSectioncode() {
        return sectioncode;
    }

    public void setSectioncode(String sectioncode) {
        this.sectioncode = sectioncode;
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

    public List<SectionInputBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<SectionInputBean> gridModel) {
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

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
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
     * @return the searchSectionCode
     */
    public String getSearchSectionCode() {
        return searchSectionCode;
    }

    /**
     * @param searchSectionCode the searchSectionCode to set
     */
    public void setSearchSectionCode(String searchSectionCode) {
        this.searchSectionCode = searchSectionCode;
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
