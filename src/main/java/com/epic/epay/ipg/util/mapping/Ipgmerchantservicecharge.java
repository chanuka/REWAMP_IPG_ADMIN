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
 * Ipgmerchantservicecharge generated by hbm2java
 */
@Entity
@Table(name = "IPGMERCHANTSERVICECHARGE" )
public class Ipgmerchantservicecharge implements java.io.Serializable {

    private IpgmerchantservicechargeId id;
    private Ipgservicecharge ipgservicecharge;
    private Ipgmerchant ipgmerchant;
    private String remarks;
    private String lastupdateduser;
    private Date lastupdatedtime;
    private Date createdtime;

    public Ipgmerchantservicecharge() {
    }

    public Ipgmerchantservicecharge(IpgmerchantservicechargeId id, Ipgservicecharge ipgservicecharge, Ipgmerchant ipgmerchant) {
        this.id = id;
        this.ipgservicecharge = ipgservicecharge;
        this.ipgmerchant = ipgmerchant;
    }

    public Ipgmerchantservicecharge(IpgmerchantservicechargeId id, Ipgmerchant ipgmerchant, String remarks, Ipgservicecharge ipgservicecharge, String lastupdateduser, Date lastupdatedtime, Date createdtime) {
        this.id = id;
        this.ipgservicecharge = ipgservicecharge;
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
        @AttributeOverride(name = "servicechargeid", column =
        @Column(name = "SERVICECHARGEID", nullable = false, precision = 22, scale = 0))})
    public IpgmerchantservicechargeId getId() {
        return this.id;
    }

    public void setId(IpgmerchantservicechargeId id) {
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
    @JoinColumn(name = "SERVICECHARGEID", nullable = false, insertable = false, updatable = false)
    public Ipgservicecharge getIpgservicecharge() {
        return this.ipgservicecharge;
    }

    public void setIpgservicecharge(Ipgservicecharge ipgservicecharge) {
        this.ipgservicecharge = ipgservicecharge;
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
