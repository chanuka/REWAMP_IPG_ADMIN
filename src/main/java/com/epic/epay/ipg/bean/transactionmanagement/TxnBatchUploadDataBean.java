/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.transactionmanagement;

/**
 *
 * @author asela
 */
public class TxnBatchUploadDataBean {

    private String fileid;
    private String filename;
    private String filetype;
    private String status;
    private String statuscode;
    private String txntypecode;
    private String txntypedes;
    private long fullCount;

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getTxntypecode() {
        return txntypecode;
    }

    public void setTxntypecode(String txntypecode) {
        this.txntypecode = txntypecode;
    }

    public String getTxntypedes() {
        return txntypedes;
    }

    public void setTxntypedes(String txntypedes) {
        this.txntypedes = txntypedes;
    }

   
}
