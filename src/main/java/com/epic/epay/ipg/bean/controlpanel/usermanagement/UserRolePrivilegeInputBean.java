/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.epay.ipg.bean.controlpanel.usermanagement;

import com.epic.epay.ipg.util.mapping.Ipguserrole;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sivaganesan_t
 */
public class UserRolePrivilegeInputBean {
    
    private String userRole;
    private String userRoleDes;
    private String message;
    private String Category;
    private String page;
    private List<Ipguserrole> userRoleList;
    private List<String> categoryList = new ArrayList<String>();
    private List<UserRoleTaskBean> currentList = new ArrayList < UserRoleTaskBean > ();
    private List<UserRoleTaskBean> newList = new ArrayList<UserRoleTaskBean>();
    private List<UserRoleSectionBean> sectionMap = new ArrayList<UserRoleSectionBean>();
    private List<UserRolePageBean> pageMap = new ArrayList<UserRolePageBean>();
    private List<String> newBox;
    private List<String> currentBox;
    private String section;
    private String sectionpage;
    private String oldvalue;
    
    
    /*-------for access control-----------*/
    private boolean vmanage;
    private boolean vassign;
    /*-------for access control-----------*/

    
    public boolean isVmanage() {
        return vmanage;
    }

    public void setVmanage(boolean vmanage) {
        this.vmanage = vmanage;
    }

    public boolean isVassign() {
        return vassign;
    }

    public void setVassign(boolean vassign) {
        this.vassign = vassign;
    }

    public List<Ipguserrole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<Ipguserrole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRoleDes() {
        return userRoleDes;
    }

    public void setUserRoleDes(String userRoleDes) {
        this.userRoleDes = userRoleDes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public List<UserRoleTaskBean> getCurrentList() {
        return currentList;
    }

    public void setCurrentList(List<UserRoleTaskBean> currentList) {
        this.currentList = currentList;
    }

    public List<UserRoleTaskBean> getNewList() {
        return newList;
    }

    public void setNewList(List<UserRoleTaskBean> newList) {
        this.newList = newList;
    }

    public List<UserRoleSectionBean> getSectionMap() {
        return sectionMap;
    }

    public void setSectionMap(List<UserRoleSectionBean> sectionMap) {
        this.sectionMap = sectionMap;
    }

    public List<UserRolePageBean> getPageMap() {
        return pageMap;
    }

    public void setPageMap(List<UserRolePageBean> pageMap) {
        this.pageMap = pageMap;
    }

    public List<String> getNewBox() {
        return newBox;
    }

    public void setNewBox(List<String> newBox) {
        this.newBox = newBox;
    }

    public List<String> getCurrentBox() {
        return currentBox;
    }

    public void setCurrentBox(List<String> currentBox) {
        this.currentBox = currentBox;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSectionpage() {
        return sectionpage;
    }

    public void setSectionpage(String sectionpage) {
        this.sectionpage = sectionpage;
    }

    public String getOldvalue() {
        return oldvalue;
    }

    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
    }
}
