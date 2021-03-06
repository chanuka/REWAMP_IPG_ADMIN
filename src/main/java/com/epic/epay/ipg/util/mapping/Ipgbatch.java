package com.epic.epay.ipg.util.mapping;
// Generated Oct 24, 2013 11:34:32 AM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Ipgbatch generated by hbm2java
 */
@Entity
@Table(name = "IPGBATCH")
public class Ipgbatch implements java.io.Serializable {

    private IpgbatchId id;
    private Ipgsettlementfile ipgsettlementfile;
    private Ipgstatus ipgstatus;
    private String lastupdateduser;
    private Date lastupdatedtime;
    private Date createdtime;
    private String terminalid;
    private Set<Epictxnrequests> epictxnrequestses = new HashSet<Epictxnrequests>(0);

    public Ipgbatch() {
    }

    public Ipgbatch(IpgbatchId id) {
        this.id = id;
    }

    public Ipgbatch(IpgbatchId id, Ipgsettlementfile ipgsettlementfile, Ipgstatus ipgstatus, String lastupdateduser, Date lastupdatedtime, Date createdtime, String terminalid, Set<Epictxnrequests> epictxnrequestses) {
        this.id = id;
        this.ipgsettlementfile = ipgsettlementfile;
        this.ipgstatus = ipgstatus;
        this.lastupdateduser = lastupdateduser;
        this.lastupdatedtime = lastupdatedtime;
        this.createdtime = createdtime;
        this.terminalid = terminalid;
        this.epictxnrequestses = epictxnrequestses;
    }

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "merchantid", column =
        @Column(name = "MERCHANTID", nullable = false, precision = 16, scale = 0)),
        @AttributeOverride(name = "batchnumber", column =
        @Column(name = "BATCHNUMBER", nullable = false, length = 6))})
    public IpgbatchId getId() {
        return this.id;
    }

    public void setId(IpgbatchId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS")
    public Ipgstatus getIpgstatus() {
        return this.ipgstatus;
    }

    public void setIpgstatus(Ipgstatus ipgstatus) {
        this.ipgstatus = ipgstatus;
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

    @Column(name = "TERMINALID", length = 8)
    public String getTerminalid() {
        return this.terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    @OneToMany(  fetch = FetchType.LAZY, mappedBy = "ipgbatch")
    public Set<Epictxnrequests> getEpictxnrequestses() {
        return this.epictxnrequestses;
    }

    public void setEpictxnrequestses(Set<Epictxnrequests> epictxnrequestses) {
        this.epictxnrequestses = epictxnrequestses;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SETTLEMENTFILE")
    public Ipgsettlementfile getIpgsettlementfile() {
        return this.ipgsettlementfile;
    }

    public void setIpgsettlementfile(Ipgsettlementfile ipgsettlementfile) {
        this.ipgsettlementfile = ipgsettlementfile;
    }
}
