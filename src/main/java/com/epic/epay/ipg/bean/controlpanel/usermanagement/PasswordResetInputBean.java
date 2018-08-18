/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.controlpanel.usermanagement;

/**
 *
 * @author chanuka
 */
public class PasswordResetInputBean {

    private String username;
    private String userrole;
    private String currpwd;
    private String newpwd;
    private String renewpwd;
    private String husername;
    private String pwtooltip;
    
    /*-------for access control-----------*/
    private boolean vupdatepwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getCurrpwd() {
        return currpwd;
    }

    public void setCurrpwd(String currpwd) {
        this.currpwd = currpwd;
    }

    public String getNewpwd() {
        return newpwd;
    }

    public void setNewpwd(String newpwd) {
        this.newpwd = newpwd;
    }

    public String getRenewpwd() {
        return renewpwd;
    }

    public void setRenewpwd(String renewpwd) {
        this.renewpwd = renewpwd;
    }

    public String getHusername() {
        return husername;
    }

    public void setHusername(String husername) {
        this.husername = husername;
    }

    public boolean isVupdatepwd() {
        return vupdatepwd;
    }

    public void setVupdatepwd(boolean vupdatepwd) {
        this.vupdatepwd = vupdatepwd;
    }

    public String getPwtooltip() {
        return pwtooltip;
    }

    public void setPwtooltip(String pwtooltip) {
        this.pwtooltip = pwtooltip;
    }
    
    
}
