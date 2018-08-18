/**
 *
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

import com.epic.epay.ipg.util.mapping.Ipgrulescope;
import com.epic.epay.ipg.util.mapping.Ipgruletype;
import com.epic.epay.ipg.util.mapping.Ipgsecuritylevel;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgtriggersequence;
import java.util.ArrayList;
import java.util.List;
/**
 * @created on :Nov 5, 2013, 2:47:13 PM
 * @author :thushanth
 */
public class RuleInputBean {

    /* ---------user inputs data-----  */
    private String ruleId;
    private String securityLevel;
    private String ruleType;
    private String ruleScope;
    private String ruleScope2;
    private String operator;
    private String startValue;
    private String endValue;
    private String priority;
    private String description;
    private String triggersequence;
    private String status;
    private List<Ipgsecuritylevel> securitylevelList;
    private List<RuleTypeListBean> ruletypeList =new ArrayList<RuleTypeListBean>();
    private List<String> ruletypeSerchList;
    private List<Ipgrulescope> rulescopeList;
    private List<Ipgtriggersequence> triggersequenceList;
    private List<RuleOperatorListBean> operationList = new ArrayList<RuleOperatorListBean>();
    private List<Ipgstatus> statusList;
    private String message;

    /* ---------user inputs data-----  */
 /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdatebutt;
    private boolean vupdatelink;
    private boolean vdelete;
    private boolean vsearch;
    /*-------for access control-----------*/

 /*------------------------list data table  ------------------------------*/
    private List<RuleBean> gridModel;
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

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleScope() {
        return ruleScope;
    }

    public void setRuleScope(String ruleScope) {
        this.ruleScope = ruleScope;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getStartValue() {
        return startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }

    public String getEndValue() {
        return endValue;
    }

    public void setEndValue(String endValue) {
        this.endValue = endValue;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<Ipgsecuritylevel> getSecuritylevelList() {
        return securitylevelList;
    }

    public void setSecuritylevelList(List<Ipgsecuritylevel> securitylevelList) {
        this.securitylevelList = securitylevelList;
    }

    public List<RuleTypeListBean> getRuletypeList() {
        return ruletypeList;
    }

    public void setRuletypeList(List<RuleTypeListBean> ruletypeList) {
        this.ruletypeList = ruletypeList;
    }

    public List<Ipgrulescope> getRulescopeList() {
        return rulescopeList;
    }

    public void setRulescopeList(List<Ipgrulescope> rulescopeList) {
        this.rulescopeList = rulescopeList;
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

    public List<RuleBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<RuleBean> gridModel) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RuleOperatorListBean> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<RuleOperatorListBean> operationList) {
        this.operationList = operationList;
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

    public List<Ipgtriggersequence> getTriggersequenceList() {
        return triggersequenceList;
    }

    public void setTriggersequenceList(List<Ipgtriggersequence> triggersequenceList) {
        this.triggersequenceList = triggersequenceList;
    }

    public String getTriggersequence() {
        return triggersequence;
    }

    public void setTriggersequence(String triggersequence) {
        this.triggersequence = triggersequence;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Ipgstatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Ipgstatus> statusList) {
        this.statusList = statusList;
    }

    public List<String> getRuletypeSerchList() {
        return ruletypeSerchList;
    }

    public void setRuletypeSerchList(List<String> ruletypeSerchList) {
        this.ruletypeSerchList = ruletypeSerchList;
    }

    public String getRuleScope2() {
        return ruleScope2;
    }
    
    public void setRuleScope2(String ruleScope2) {
        this.ruleScope2 = ruleScope2;
    }

}
