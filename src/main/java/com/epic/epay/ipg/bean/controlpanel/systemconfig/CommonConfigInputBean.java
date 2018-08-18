/**
 *
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

import java.util.List;


/**
 * @created :Oct 29, 2013, 9:24:56 AM
 * @author :thushanth
 */
public class CommonConfigInputBean {

    /* ---------start of user inputs data-----  */
    private String code;
    private String description;
    private String value;
    private String message;
    /* ---------end of user inputs data-----  */

    /*-------for access control-----------*/
    private boolean vupdatebutt;
    private boolean vupdatelink;
    private boolean vdelete;
    private boolean vsearch;
    /*-------for access control-----------*/

    /*------------------------list data table  ------------------------------*/
    private List<CommonConfigBean> gridModel;
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
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }    
    public String getDescription() {
        return description;
    }    
    public void setDescription(String description) {
        this.description = description;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
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
    public List<CommonConfigBean> getGridModel() {
        return gridModel;
    }
    public void setGridModel(List<CommonConfigBean> gridModel) {
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
