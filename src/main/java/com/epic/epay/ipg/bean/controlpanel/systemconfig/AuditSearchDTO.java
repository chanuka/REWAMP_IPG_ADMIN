/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

import com.epic.epay.ipg.util.mapping.Ipgpage;
import com.epic.epay.ipg.util.mapping.Ipgsection;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.mapping.Ipguserrole;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chalitha
 */
public class AuditSearchDTO {

    /*-------for access control-----------*/
    private boolean vsearch;
    private boolean vviewlink;
    private boolean vgenerate;
    private List<Ipguserrole> userRoleList;
    private List<Ipgsection> sectionList;
    private List<Ipgpage> pageList;
    private List<Ipgtask> taskList;
    private HashMap parameterMap;
    private List<AuditDataBean> lst;
    
    private boolean search;
    private List<AuditDataBean> gridModel;
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

    private String auditId;
    private String userRole;
    private String description;
    private String section;
    private String ipgpage;
    private String task;
    private String remarks;
    private String ip;
    private String fdate;
    private String tdate;
    private String userRoleDes;
    private String sectionDes;
    private String ipgpageDes;
    private String taskDes;
    private String reporttype;
    private AuditDataBean auditDataBean;
    private ByteArrayInputStream excelStream;
    private ByteArrayInputStream zipStream;

    public AuditDataBean getAuditDataBean() {
        return auditDataBean;
    }

    public void setAuditDataBean(AuditDataBean auditDataBean) {
        this.auditDataBean = auditDataBean;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpgpage() {
        return ipgpage;
    }

    public void setIpgpage(String ipgpage) {
        this.ipgpage = ipgpage;
    }

    public boolean isVsearch() {
        return vsearch;
    }

    public void setVsearch(boolean vsearch) {
        this.vsearch = vsearch;
    }

    public boolean isVviewlink() {
        return vviewlink;
    }

    public void setVviewlink(boolean vviewlink) {
        this.vviewlink = vviewlink;
    }
    /*-------for access control-----------*/

    public void setUserRoleList(List<Ipguserrole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public void setSectionList(List<Ipgsection> sectionList) {
        this.sectionList = sectionList;
    }

    public void setPageList(List<Ipgpage> pageList) {
        this.pageList = pageList;
    }

    public void setTaskList(List<Ipgtask> taskList) {
        this.taskList = taskList;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public List<Ipguserrole> getUserRoleList() {
        return userRoleList;
    }

    public List<Ipgsection> getSectionList() {
        return sectionList;
    }

    public List<Ipgpage> getPageList() {
        return pageList;
    }

    public List<Ipgtask> getTaskList() {
        return taskList;
    }   

    public List<AuditDataBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<AuditDataBean> gridModel) {
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

    public long getFullCount() {
        
        return fullCount;
    }  
    
    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String _userRole) {
        this.userRole = _userRole;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String _section) {
        this.section = _section;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String _task) {
        this.task = _task;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String _remarks) {
        this.remarks = _remarks;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String _ip) {
        this.ip = _ip;
    }

    public String getFdate() {
        return fdate;
    }

    public void setFdate(String _fdate) {
        this.fdate = _fdate;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String _tdate) {
        this.tdate = _tdate;
    }

    public String getReporttype() {
        return reporttype;
    }
    
    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }

    public HashMap getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(HashMap parameterMap) {
        this.parameterMap = parameterMap;
    }

    public List<AuditDataBean> getLst() {
        return lst;
    }

    public void setLst(List<AuditDataBean> lst) {
        this.lst = lst;
    }

    public boolean isVgenerate() {
        return vgenerate;
    }

    public void setVgenerate(boolean vgenerate) {
        this.vgenerate = vgenerate;
    }

    public String getUserRoleDes() {
        return userRoleDes;
    }

    public void setUserRoleDes(String userRoleDes) {
        this.userRoleDes = userRoleDes;
    }

    public String getSectionDes() {
        return sectionDes;
    }

    public void setSectionDes(String sectionDes) {
        this.sectionDes = sectionDes;
    }

    public String getIpgpageDes() {
        return ipgpageDes;
    }

    public void setIpgpageDes(String ipgpageDes) {
        this.ipgpageDes = ipgpageDes;
    }

    public String getTaskDes() {
        return taskDes;
    }

    public void setTaskDes(String taskDes) {
        this.taskDes = taskDes;
    }

    public ByteArrayInputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(ByteArrayInputStream excelStream) {
        this.excelStream = excelStream;
    }

    public ByteArrayInputStream getZipStream() {
        return zipStream;
    }

    public void setZipStream(ByteArrayInputStream zipStream) {
        this.zipStream = zipStream;
    }
}
