package com.epic.epay.ipg.util.mapping;
// Generated Nov 8, 2013 9:17:09 AM by Hibernate Tools 3.2.1.GA


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
 * Ipgriskprofileblockedcurrency generated by hbm2java
 */
@Entity
@Table(name="IPGRISKPROFILEBLOCKEDCURRENCY"
    
)
public class Ipgriskprofileblockedcurrency  implements java.io.Serializable {


     private IpgriskprofileblockedcurrencyId id;
     private Ipgcurrency ipgcurrency;
     private Ipgriskprofile ipgriskprofile;
     private Date createdtime;

    public Ipgriskprofileblockedcurrency() {
    }

	
    public Ipgriskprofileblockedcurrency(IpgriskprofileblockedcurrencyId id, Ipgcurrency ipgcurrency, Ipgriskprofile ipgriskprofile) {
        this.id = id;
        this.ipgcurrency = ipgcurrency;
        this.ipgriskprofile = ipgriskprofile;
    }
    public Ipgriskprofileblockedcurrency(IpgriskprofileblockedcurrencyId id, Ipgcurrency ipgcurrency, Ipgriskprofile ipgriskprofile, Date createdtime) {
       this.id = id;
       this.ipgcurrency = ipgcurrency;
       this.ipgriskprofile = ipgriskprofile;
       this.createdtime = createdtime;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="riskprofilecode", column=@Column(name="RISKPROFILECODE", nullable=false, length=8) ), 
        @AttributeOverride(name="currencycode", column=@Column(name="CURRENCYCODE", nullable=false, length=8) ) } )
    public IpgriskprofileblockedcurrencyId getId() {
        return this.id;
    }
    
    public void setId(IpgriskprofileblockedcurrencyId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CURRENCYCODE", nullable=false, insertable=false, updatable=false)
    public Ipgcurrency getIpgcurrency() {
        return this.ipgcurrency;
    }
    
    public void setIpgcurrency(Ipgcurrency ipgcurrency) {
        this.ipgcurrency = ipgcurrency;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RISKPROFILECODE", nullable=false, insertable=false, updatable=false)
    public Ipgriskprofile getIpgriskprofile() {
        return this.ipgriskprofile;
    }
    
    public void setIpgriskprofile(Ipgriskprofile ipgriskprofile) {
        this.ipgriskprofile = ipgriskprofile;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="CREATEDTIME", length=7)
    public Date getCreatedtime() {
        return this.createdtime;
    }
    
    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }




}


