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
 * Ipgmerchantcredential generated by hbm2java
 */
@Entity
@Table(name = "IPGMERCHANTCREDENTIAL")
public class Ipgmerchantcredential implements java.io.Serializable {

    private IpgmerchantcredentialId id;
    private Ipgmerchant ipgmerchant;
    private Ipgcardassociation ipgcardassociation;
    private String username;
    private String password;
    private String lastupdateduser;
    private Date lastupdatedtime;
    private Date createdtime;
    private String directoryserver;

    public Ipgmerchantcredential() {
    }

    public Ipgmerchantcredential(IpgmerchantcredentialId id, Ipgmerchant ipgmerchant, Ipgcardassociation ipgcardassociation, String username, String password) {
        this.id = id;
        this.ipgmerchant = ipgmerchant;
        this.ipgcardassociation = ipgcardassociation;
        this.username = username;
        this.password = password;
    }

    public Ipgmerchantcredential(IpgmerchantcredentialId id, Ipgmerchant ipgmerchant, Ipgcardassociation ipgcardassociation, String username, String password, String lastupdateduser, Date lastupdatedtime, Date createdtime, String directoryserver) {
        this.id = id;
        this.ipgmerchant = ipgmerchant;
        this.ipgcardassociation = ipgcardassociation;
        this.username = username;
        this.password = password;
        this.lastupdateduser = lastupdateduser;
        this.lastupdatedtime = lastupdatedtime;
        this.createdtime = createdtime;
        this.directoryserver = directoryserver;
    }

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "merchantid", column =
        @Column(name = "MERCHANTID", nullable = false, length = 15)),
        @AttributeOverride(name = "cardassociationcode", column =
        @Column(name = "CARDASSOCIATIONCODE", nullable = false, length = 16))})
    public IpgmerchantcredentialId getId() {
        return this.id;
    }

    public void setId(IpgmerchantcredentialId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANTID", nullable = false, insertable = false, updatable = false)
    public Ipgmerchant getIpgmerchant() {
        return this.ipgmerchant;
    }

    public void setIpgmerchant(Ipgmerchant ipgmerchant) {
        this.ipgmerchant = ipgmerchant;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CARDASSOCIATIONCODE", nullable = false, insertable = false, updatable = false)
    public Ipgcardassociation getIpgcardassociation() {
        return this.ipgcardassociation;
    }

    public void setIpgcardassociation(Ipgcardassociation ipgcardassociation) {
        this.ipgcardassociation = ipgcardassociation;
    }

    @Column(name = "USERNAME", nullable = false)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD", nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "LASTUPDATEDUSER", length = 64)
    public String getLastupdateduser() {
        return this.lastupdateduser;
    }

    public void setLastupdateduser(String lastupdateduser) {
        this.lastupdateduser = lastupdateduser;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "LASTUPDATEDTIME", length = 7)
    public Date getLastupdatedtime() {
        return this.lastupdatedtime;
    }

    public void setLastupdatedtime(Date lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATEDTIME", length = 7)
    public Date getCreatedtime() {
        return this.createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    @Column(name = "DIRECTORYSERVER", length = 16)
    public String getDirectoryserver() {
        return this.directoryserver;
    }

    public void setDirectoryserver(String directoryserver) {
        this.directoryserver = directoryserver;
    }
}