/**
 *
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

import java.io.File;
import java.util.List;

/**
 * @author ruwan_e
 *
 */
public class CardAssociationInputBean {

    private String cardAssociationCode;
    private String description;
    private File verifieldImageURL;
    private String verifieldImageURLContentType;
    private String verifieldImageURLFileName;
    private File cardImageURL;
    private String cardImageURLContentType;
    private String cardImageURLFileName;
    private String sortKey;
    private String message;

    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdatebutt;
    private boolean vupdatelink;
    private boolean vdelete;
    private boolean vsearch;
    /*-------for access control-----------*/

    /*------------------------list data table  ------------------------------*/
    private List<CardAssociationBean> gridModel;
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

    public String getCardAssociationCode() {
        return cardAssociationCode;
    }

    public boolean isVdelete() {
        return vdelete;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setVdelete(boolean vdelete) {
        this.vdelete = vdelete;
    }

    public List<CardAssociationBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<CardAssociationBean> gridModel) {
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

    public void setCardAssociationCode(String cardAssociationCode) {
        this.cardAssociationCode = cardAssociationCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
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

    public File getVerifieldImageURL() {
        return verifieldImageURL;
    }

    public void setVerifieldImageURL(File verifieldImageURL) {
        this.verifieldImageURL = verifieldImageURL;
    }

    public String getVerifieldImageURLContentType() {
        return verifieldImageURLContentType;
    }

    public void setVerifieldImageURLContentType(String verifieldImageURLContentType) {
        this.verifieldImageURLContentType = verifieldImageURLContentType;
    }

    public String getVerifieldImageURLFileName() {
        return verifieldImageURLFileName;
    }

    public void setVerifieldImageURLFileName(String verifieldImageURLFileName) {
        this.verifieldImageURLFileName = verifieldImageURLFileName;
    }

    public File getCardImageURL() {
        return cardImageURL;
    }

    public void setCardImageURL(File cardImageURL) {
        this.cardImageURL = cardImageURL;
    }

    public String getCardImageURLContentType() {
        return cardImageURLContentType;
    }

    public void setCardImageURLContentType(String cardImageURLContentType) {
        this.cardImageURLContentType = cardImageURLContentType;
    }

    public String getCardImageURLFileName() {
        return cardImageURLFileName;
    }

    public void setCardImageURLFileName(String cardImageURLFileName) {
        this.cardImageURLFileName = cardImageURLFileName;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

  
    public boolean isVsearch() {
        return vsearch;
    }

    public void setVsearch(boolean vsearch) {
        this.vsearch = vsearch;
    }

}
