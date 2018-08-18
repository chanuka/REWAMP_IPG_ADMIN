/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.logmanagement;

/**
 *
 * @author ruwan_e
 */
public enum LogFileType {

    INFO("info"), ERROR("error");
    
    private String desc;

    private LogFileType(String desc) {
        this.desc = desc;
    }

    public String getDescription() {
        return desc;
    }
}
