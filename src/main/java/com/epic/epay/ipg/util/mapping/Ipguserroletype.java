package com.epic.epay.ipg.util.mapping;
// Generated Oct 24, 2013 11:34:32 AM by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
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
 * Ipguserroletype generated by hbm2java
 */
@Entity
@Table(name="IPGUSERROLETYPE"
    
)
public class Ipguserroletype  implements java.io.Serializable {


     private String userroletypecode;
     private Ipgstatus ipgstatus;
     private String description;
     private BigDecimal sortkey;
     private String lastupdateduser;
     private Date lastupdatedtime;
     private Date createdtime;
     private Set<Ipguserrole> ipguserroles = new HashSet<Ipguserrole>(0);

    public Ipguserroletype() {
    }

	
    public Ipguserroletype(String userroletypecode) {
        this.userroletypecode = userroletypecode;
    }
    public Ipguserroletype(String userroletypecode, Ipgstatus ipgstatus, String description, BigDecimal sortkey, String lastupdateduser, Date lastupdatedtime, Date createdtime, Set<Ipguserrole> ipguserroles) {
       this.userroletypecode = userroletypecode;
       this.ipgstatus = ipgstatus;
       this.description = description;
       this.sortkey = sortkey;
       this.lastupdateduser = lastupdateduser;
       this.lastupdatedtime = lastupdatedtime;
       this.createdtime = createdtime;
       this.ipguserroles = ipguserroles;
    }
   
     @Id 
    
    @Column(name="USERROLETYPECODE", unique=true, nullable=false, length=16)
    public String getUserroletypecode() {
        return this.userroletypecode;
    }
    
    public void setUserroletypecode(String userroletypecode) {
        this.userroletypecode = userroletypecode;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STATUSCODE")
    public Ipgstatus getIpgstatus() {
        return this.ipgstatus;
    }
    
    public void setIpgstatus(Ipgstatus ipgstatus) {
        this.ipgstatus = ipgstatus;
    }
    
    @Column(name="DESCRIPTION", length=64)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="SORTKEY", precision=22, scale=0)
    public BigDecimal getSortkey() {
        return this.sortkey;
    }
    
    public void setSortkey(BigDecimal sortkey) {
        this.sortkey = sortkey;
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
@OneToMany(  fetch=FetchType.LAZY, mappedBy="ipguserroletype")
    public Set<Ipguserrole> getIpguserroles() {
        return this.ipguserroles;
    }
    
    public void setIpguserroles(Set<Ipguserrole> ipguserroles) {
        this.ipguserroles = ipguserroles;
    }




}

