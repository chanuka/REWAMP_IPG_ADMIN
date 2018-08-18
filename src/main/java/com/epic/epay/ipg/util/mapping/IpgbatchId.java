package com.epic.epay.ipg.util.mapping;
// Generated Oct 24, 2013 11:34:32 AM by Hibernate Tools 3.2.1.GA

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * IpgbatchId generated by hbm2java
 */
@Embeddable
public class IpgbatchId implements java.io.Serializable {

    private String merchantid;
    private String batchnumber;

    public IpgbatchId() {
    }

    public IpgbatchId(String merchantid, String batchnumber) {
        this.merchantid = merchantid;
        this.batchnumber = batchnumber;
    }

    @Column(name = "MERCHANTID", nullable = false, precision = 16, scale = 0)
    public String getMerchantid() {
        return this.merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    @Column(name = "BATCHNUMBER", nullable = false, length = 6)
    public String getBatchnumber() {
        return this.batchnumber;
    }

    public void setBatchnumber(String batchnumber) {
        this.batchnumber = batchnumber;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof IpgbatchId)) {
            return false;
        }
        IpgbatchId castOther = (IpgbatchId) other;

        return (this.getMerchantid() == castOther.getMerchantid())
                && ((this.getBatchnumber() == castOther.getBatchnumber()) || (this.getBatchnumber() != null && castOther.getBatchnumber() != null && this.getBatchnumber().equals(castOther.getBatchnumber())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getMerchantid().hashCode();
        result = 37 * result + (getBatchnumber() == null ? 0 : this.getBatchnumber().hashCode());
        return result;
    }
}
