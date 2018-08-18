/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.epay.ipg.bean.controlpanel.systemconfig;

import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
import com.epic.epay.ipg.util.mapping.Ipgecivalue;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author badrika
 */
public class AuthConfigurationInputBean {
    
    private String pkey;
    private String cardassociation;
    private String txstatus;
    private String ecivalue;
    private String status;
    private String message;
    private List<Ipgcardassociation> cardassociationList;
    private List<Ipgecivalue> ecivalueList;
    private List<Ipgstatus> authstatusList;
    private HashMap<String, String> txstatusMap = new HashMap<String, String>();
    
    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdatebutt;
    private boolean vupdatelink;
    private boolean vdelete;
    private boolean vsearch;
    
    /*--------list data table  -----------*/
    private List<AuthConfigurationBean> gridModel;
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

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, String> getTxstatusMap() {
        return txstatusMap;
    }

    public void setTxstatusMap(HashMap<String, String> txstatusMap) {
        this.txstatusMap = txstatusMap;
    }

    public List<Ipgcardassociation> getCardassociationList() {
        return cardassociationList;
    }

    public void setCardassociationList(List<Ipgcardassociation> cardassociationList) {
        this.cardassociationList = cardassociationList;
    }

    public List<Ipgecivalue> getEcivalueList() {
        return ecivalueList;
    }

    public void setEcivalueList(List<Ipgecivalue> ecivalueList) {
        this.ecivalueList = ecivalueList;
    }

    public String getCardassociation() {
        return cardassociation;
    }

    public List<Ipgstatus> getAuthstatusList() {
        return authstatusList;
    }

    public void setAuthstatusList(List<Ipgstatus> authstatusList) {
        this.authstatusList = authstatusList;
    }

    public void setCardassociation(String cardassociation) {
        this.cardassociation = cardassociation;
    }

    public String getTxstatus() {
        return txstatus;
    }

    public void setTxstatus(String txstatus) {
        this.txstatus = txstatus;
    }

    public String getEcivalue() {
        return ecivalue;
    }

    public void setEcivalue(String ecivalue) {
        this.ecivalue = ecivalue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<AuthConfigurationBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<AuthConfigurationBean> gridModel) {
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
