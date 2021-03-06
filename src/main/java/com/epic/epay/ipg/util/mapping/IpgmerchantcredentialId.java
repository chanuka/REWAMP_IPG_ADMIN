package com.epic.epay.ipg.util.mapping;
// Generated Oct 3, 2014 1:30:49 PM by Hibernate Tools 3.2.1.GA

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * IpgmerchantcredentialId generated by hbm2java
 */
@Embeddable
public class IpgmerchantcredentialId implements java.io.Serializable {

    private String merchantid;
    private String cardassociationcode;

    public IpgmerchantcredentialId() {
    }

    public IpgmerchantcredentialId(String merchantid, String cardassociationcode) {
        this.merchantid = merchantid;
        this.cardassociationcode = cardassociationcode;
    }

    @Column(name = "MERCHANTID", nullable = false, length = 15)
    public String getMerchantid() {
        return this.merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    @Column(name = "CARDASSOCIATIONCODE", nullable = false, length = 16)
    public String getCardassociationcode() {
        return this.cardassociationcode;
    }

    public void setCardassociationcode(String cardassociationcode) {
        this.cardassociationcode = cardassociationcode;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof IpgmerchantcredentialId)) {
            return false;
        }
        IpgmerchantcredentialId castOther = (IpgmerchantcredentialId) other;

        return ((this.getMerchantid() == castOther.getMerchantid()) || (this.getMerchantid() != null && castOther.getMerchantid() != null && this.getMerchantid().equals(castOther.getMerchantid())))
                && ((this.getCardassociationcode() == castOther.getCardassociationcode()) || (this.getCardassociationcode() != null && castOther.getCardassociationcode() != null && this.getCardassociationcode().equals(castOther.getCardassociationcode())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getMerchantid() == null ? 0 : this.getMerchantid().hashCode());
        result = 37 * result + (getCardassociationcode() == null ? 0 : this.getCardassociationcode().hashCode());
        return result;
    }
}
