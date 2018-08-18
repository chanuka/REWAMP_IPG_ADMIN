/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.epay.ipg.bean.controlpanel.merchantmanagement;

import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
import java.util.List;

/**
 *
 * @author badrika
 */
public class DSMerchantEnrollmentInputBean {
    
    private String cardassociation;
    private List<String> selectedList;
    private List<Ipgcardassociation> cardAssociationList;
    private String message;
    
    /*-------for access control-----------*/
    private boolean vadd;
    private boolean search;
    private boolean again;
    private boolean vupdatebutt;
    
    /*------------------------list data table  ------------------------------*/
    private List<DSMerchantEnrollmentBean> gridModel;
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord="";
    private String sidx="";
    private String searchField;
    private String searchString;
    private String searchOper;
    private boolean loadonce = false;

    public List<String> getSelectedList() {
        return selectedList;
    }
    public void setSelectedList(List<String> selectedList) {
        this.selectedList = selectedList;
    }
    public boolean isVupdatebutt() {
        return vupdatebutt;
    }

    public void setVupdatebutt(boolean vupdatebutt) {
        this.vupdatebutt = vupdatebutt;
    }

    public List<Ipgcardassociation> getCardAssociationList() {
        return cardAssociationList;
    }

    public void setCardAssociationList(List<Ipgcardassociation> cardAssociationList) {
        this.cardAssociationList = cardAssociationList;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public boolean isAgain() {
        return again;
    }

    public void setAgain(boolean again) {
        this.again = again;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCardassociation() {
        return cardassociation;
    }

    public void setCardassociation(String cardassociation) {
        this.cardassociation = cardassociation;
    }

    public boolean isVadd() {
        return vadd;
    }

    public void setVadd(boolean vadd) {
        this.vadd = vadd;
    }

    public List<DSMerchantEnrollmentBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<DSMerchantEnrollmentBean> gridModel) {
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
    
    
    
    
}
