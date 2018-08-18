/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.controlpanel.merchantmanagement;

/**
 *
 * @author badrika
 */
public class CommonFilePathBean {
    private String errorLogPath;
    private String inforLogPath;
    private String txnLogPath;
    private String certificatePath;
    private String ccbtxnFiles;

    public String getErrorLogPath() {
        return errorLogPath;
    }

    public void setErrorLogPath(String errorLogPath) {
        this.errorLogPath = errorLogPath;
    }

    public String getInforLogPath() {
        return inforLogPath;
    }

    public void setInforLogPath(String inforLogPath) {
        this.inforLogPath = inforLogPath;
    }

    public String getTxnLogPath() {
        return txnLogPath;
    }

    public void setTxnLogPath(String txnLogPath) {
        this.txnLogPath = txnLogPath;
    }

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    public String getCcbtxnFiles() {
        return ccbtxnFiles;
    }

    public void setCcbtxnFiles(String ccbtxnFiles) {
        this.ccbtxnFiles = ccbtxnFiles;
    }
    
    
    
}
