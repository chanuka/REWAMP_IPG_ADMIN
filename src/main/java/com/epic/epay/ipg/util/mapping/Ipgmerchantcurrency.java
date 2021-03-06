package com.epic.epay.ipg.util.mapping;
// Generated Oct 24, 2013 11:34:32 AM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Ipgmerchantcurrency generated by hbm2java
 */
@Entity
@Table(name = "IPGMERCHANTCURRENCY" )
public class Ipgmerchantcurrency implements java.io.Serializable {

    private IpgmerchantcurrencyId id;
    private Ipgcurrency ipgcurrency;
    private Ipgmerchant ipgmerchant;
    private String remarks;
    private String lastupdateduser;
    private Date lastupdatedtime;
    private Date createdtime;

    public Ipgmerchantcurrency() {
    }

    public Ipgmerchantcurrency(IpgmerchantcurrencyId id, Ipgcurrency ipgcurrency, Ipgmerchant ipgmerchant) {
        this.id = id;
        this.ipgcurrency = ipgcurrency;
        this.ipgmerchant = ipgmerchant;
    }

    public Ipgmerchantcurrency(IpgmerchantcurrencyId id, Ipgmerchant ipgmerchant, Ipgcurrency ipgcurrency, String remarks, String lastupdateduser, Date lastupdatedtime, Date createdtime) {
        this.id = id;
        this.ipgcurrency = ipgcurrency;
        this.ipgmerchant = ipgmerchant;
        this.remarks = remarks;
        this.lastupdateduser = lastupdateduser;
        this.lastupdatedtime = lastupdatedtime;
        this.createdtime = createdtime;
    }

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "merchantid", column =
        @Column(name = "MERCHANTID", nullable = false, length = 15)),
        @AttributeOverride(name = "currencycode", column =
        @Column(name = "CURRENCYCODE", nullable = false, length = 8))})
    public IpgmerchantcurrencyId getId() {
        return this.id;
    }

    public void setId(IpgmerchantcurrencyId id) {
        this.id = id;
    }

    @Column(name = "REMARKS")
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "LASTUPDATEDUSER", length = 64)
    public String getLastupdateduser() {
        return this.lastupdateduser;
    }

    public void setLastupdateduser(String lastupdateduser) {
        this.lastupdateduser = lastupdateduser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LASTUPDATEDTIME", length = 7)
    public Date getLastupdatedtime() {
        return this.lastupdatedtime;
    }

    public void setLastupdatedtime(Date lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATEDTIME", length = 7)
    public Date getCreatedtime() {
        return this.createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCYCODE", nullable = false, insertable = false, updatable = false)
    public Ipgcurrency getIpgcurrency() {
        return this.ipgcurrency;
    }

    public void setIpgcurrency(Ipgcurrency ipgcurrency) {
        this.ipgcurrency = ipgcurrency;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANTID", nullable = false, insertable = false, updatable = false)
    public Ipgmerchant getIpgmerchant() {
        return this.ipgmerchant;
    }

    public void setIpgmerchant(Ipgmerchant ipgmerchant) {
        this.ipgmerchant = ipgmerchant;
    }
}
