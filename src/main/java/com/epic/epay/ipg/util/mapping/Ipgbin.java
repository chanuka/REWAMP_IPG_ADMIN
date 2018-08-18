package com.epic.epay.ipg.util.mapping;
// Generated Oct 24, 2013 11:34:32 AM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Ipgbin generated by hbm2java
 */
@Entity
@Table(name = "IPGBIN" )
public class Ipgbin implements java.io.Serializable {

    private String bin;
    private Ipgstatus ipgstatusByStatus;
    private Ipgstatus ipgstatusByOnusstatus;
    private Ipgcardassociation ipgcardassociation;
    private String description;
    private String lastupdateduser;
    private Date lastupdatedtime;
    private Date createdtime;
    private Set<Ipgriskprofileblockedbin> ipgriskprofileblockedbins = new HashSet<Ipgriskprofileblockedbin>(0);

    public Ipgbin() {
    }

    public Ipgbin(String bin) {
        this.bin = bin;
    }

    public Ipgbin(String bin, Ipgstatus ipgstatusByStatus, Ipgstatus ipgstatusByOnusstatus, Ipgcardassociation ipgcardassociation, String description, String lastupdateduser, Date lastupdatedtime, Date createdtime, Set<Ipgriskprofileblockedbin> ipgriskprofileblockedbins) {
        this.bin = bin;
        this.ipgstatusByStatus = ipgstatusByStatus;
        this.ipgstatusByOnusstatus = ipgstatusByOnusstatus;
        this.ipgcardassociation = ipgcardassociation;
        this.description = description;
        this.lastupdateduser = lastupdateduser;
        this.lastupdatedtime = lastupdatedtime;
        this.createdtime = createdtime;
        this.ipgriskprofileblockedbins = ipgriskprofileblockedbins;
    }

    @Id
    @Column(name = "BIN", unique = true, nullable = false, length = 6)
    public String getBin() {
        return this.bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS")
    public Ipgstatus getIpgstatusByStatus() {
        return this.ipgstatusByStatus;
    }

    public void setIpgstatusByStatus(Ipgstatus ipgstatusByStatus) {
        this.ipgstatusByStatus = ipgstatusByStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ONUSSTATUS")
    public Ipgstatus getIpgstatusByOnusstatus() {
        return this.ipgstatusByOnusstatus;
    }

    public void setIpgstatusByOnusstatus(Ipgstatus ipgstatusByOnusstatus) {
        this.ipgstatusByOnusstatus = ipgstatusByOnusstatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CARDASSOCIATIONCODE")
    public Ipgcardassociation getIpgcardassociation() {
        return this.ipgcardassociation;
    }

    public void setIpgcardassociation(Ipgcardassociation ipgcardassociation) {
        this.ipgcardassociation = ipgcardassociation;
    }

    @Column(name = "DESCRIPTION", length = 64)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "LASTUPDATEDUSER", length = 32)
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

    @OneToMany(  fetch = FetchType.LAZY, mappedBy = "ipgbin")
    public Set<Ipgriskprofileblockedbin> getIpgriskprofileblockedbins() {
        return this.ipgriskprofileblockedbins;
    }

    public void setIpgriskprofileblockedbins(Set<Ipgriskprofileblockedbin> ipgriskprofileblockedbins) {
        this.ipgriskprofileblockedbins = ipgriskprofileblockedbins;
    }
}
