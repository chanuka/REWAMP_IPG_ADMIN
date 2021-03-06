package com.epic.epay.ipg.util.mapping;
// Generated Oct 24, 2013 11:34:32 AM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Ipgmerchnatterminal generated by hbm2java
 */
@Entity
@Table(name = "IPGMERCHNATTERMINAL"
)
public class Ipgmerchnatterminal implements java.io.Serializable {

    private String terminalid;
    private Ipgstatus ipgstatus;
    private Ipgcurrency ipgcurrency;
    private Ipgmerchant ipgmerchant;
    private Date createdtime;

    public Ipgmerchnatterminal() {
    }

    public Ipgmerchnatterminal(String terminalid, Ipgcurrency ipgcurrency, Ipgmerchant ipgmerchant) {
        this.terminalid = terminalid;
        this.ipgcurrency = ipgcurrency;
        this.ipgmerchant = ipgmerchant;
    }

    public Ipgmerchnatterminal(String terminalid, Ipgstatus ipgstatus, Ipgcurrency ipgcurrency, Ipgmerchant ipgmerchant, Date createdtime) {
        this.terminalid = terminalid;
        this.ipgstatus = ipgstatus;
        this.ipgcurrency = ipgcurrency;
        this.ipgmerchant = ipgmerchant;
        this.createdtime = createdtime;
    }

    @Id

    @Column(name = "TERMINALID", unique = true, nullable = false, length = 8)
    public String getTerminalid() {
        return this.terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS")
    public Ipgstatus getIpgstatus() {
        return this.ipgstatus;
    }

    public void setIpgstatus(Ipgstatus ipgstatus) {
        this.ipgstatus = ipgstatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCYTYPE", nullable = false)
    public Ipgcurrency getIpgcurrency() {
        return this.ipgcurrency;
    }

    public void setIpgcurrency(Ipgcurrency ipgcurrency) {
        this.ipgcurrency = ipgcurrency;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANTID", nullable = false)
    public Ipgmerchant getIpgmerchant() {
        return this.ipgmerchant;
    }

    public void setIpgmerchant(Ipgmerchant ipgmerchant) {
        this.ipgmerchant = ipgmerchant;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATEDTIME", length = 7)
    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

}
