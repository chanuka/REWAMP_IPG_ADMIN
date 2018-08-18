package com.epic.epay.ipg.util.mapping;
// Generated Oct 24, 2013 11:34:32 AM by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Ipgtransactionhistory generated by hbm2java
 */
@Entity
@Table(name="IPGTRANSACTIONHISTORY"
    
)
public class Ipgtransactionhistory  implements java.io.Serializable {


     private BigDecimal ipgtransactionhistoryid;
     private Ipgstatus ipgstatus;
     private String ipgtransactionid;
     private String remarks;
     private String lastupdateduser;
     private Date lastupdatedtime;
     private Date createdtime;
     private String analytime;

    public Ipgtransactionhistory() {
    }

	
    public Ipgtransactionhistory(BigDecimal ipgtransactionhistoryid, Ipgstatus ipgstatus, String ipgtransactionid) {
        this.ipgtransactionhistoryid = ipgtransactionhistoryid;
        this.ipgstatus = ipgstatus;
        this.ipgtransactionid = ipgtransactionid;
    }
    public Ipgtransactionhistory(BigDecimal ipgtransactionhistoryid, Ipgstatus ipgstatus, String ipgtransactionid, String remarks, String lastupdateduser, Date lastupdatedtime, Date createdtime, String analytime) {
       this.ipgtransactionhistoryid = ipgtransactionhistoryid;
       this.ipgstatus = ipgstatus;
       this.ipgtransactionid = ipgtransactionid;
       this.remarks = remarks;
       this.lastupdateduser = lastupdateduser;
       this.lastupdatedtime = lastupdatedtime;
       this.createdtime = createdtime;
       this.analytime = analytime;
    }
   
     @Id
     @SequenceGenerator(name="SequenceIdGenerator",sequenceName="IPGTRANSACTIONHISTORYID_SEQ")
     @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="SequenceIdGenerator")
    
    @Column(name="IPGTRANSACTIONHISTORYID", unique=true, nullable=false, precision=22, scale=0)
    public BigDecimal getIpgtransactionhistoryid() {
        return this.ipgtransactionhistoryid;
    }
    
    public void setIpgtransactionhistoryid(BigDecimal ipgtransactionhistoryid) {
        this.ipgtransactionhistoryid = ipgtransactionhistoryid;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STATUSCODE", nullable=false)
    public Ipgstatus getIpgstatus() {
        return this.ipgstatus;
    }
    
    public void setIpgstatus(Ipgstatus ipgstatus) {
        this.ipgstatus = ipgstatus;
    }
    
    @Column(name="IPGTRANSACTIONID", nullable=false, length=20)
    public String getIpgtransactionid() {
        return this.ipgtransactionid;
    }
    
    public void setIpgtransactionid(String ipgtransactionid) {
        this.ipgtransactionid = ipgtransactionid;
    }
    
    @Column(name="REMARKS")
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    @Column(name="LASTUPDATEDUSER", length=64)
    public String getLastupdateduser() {
        return this.lastupdateduser;
    }
    
    public void setLastupdateduser(String lastupdateduser) {
        this.lastupdateduser = lastupdateduser;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LASTUPDATEDTIME", length=7)
    public Date getLastupdatedtime() {
        return this.lastupdatedtime;
    }
    
    public void setLastupdatedtime(Date lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATEDTIME", length=7)
    public Date getCreatedtime() {
        return this.createdtime;
    }
    
    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }
    
    @Column(name="ANALYTIME", length=64)
    public String getAnalytime() {
        return this.analytime;
    }
    
    public void setAnalytime(String analytime) {
        this.analytime = analytime;
    }




}

