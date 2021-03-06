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
 * Ipguserrolesection generated by hbm2java
 */
@Entity
@Table(name="IPGUSERROLESECTION"
    
)
public class Ipguserrolesection  implements java.io.Serializable {


     private IpguserrolesectionId id;
     private Ipgsection ipgsection;
     private String lastupdateduser;
     private Date lastupdatedtime;
     private Date createdtime;

    public Ipguserrolesection() {
    }

	
    public Ipguserrolesection(IpguserrolesectionId id, Ipgsection ipgsection) {
        this.id = id;
        this.ipgsection = ipgsection;
    }
    public Ipguserrolesection(IpguserrolesectionId id, Ipgsection ipgsection, String lastupdateduser, Date lastupdatedtime, Date createdtime) {
       this.id = id;
       this.ipgsection = ipgsection;
       this.lastupdateduser = lastupdateduser;
       this.lastupdatedtime = lastupdatedtime;
       this.createdtime = createdtime;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="userrolecode", column=@Column(name="USERROLECODE", nullable=false, length=16) ), 
        @AttributeOverride(name="sectioncode", column=@Column(name="SECTIONCODE", nullable=false, length=16) ) } )
    public IpguserrolesectionId getId() {
        return this.id;
    }
    
    public void setId(IpguserrolesectionId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SECTIONCODE", nullable=false, insertable=false, updatable=false)
    public Ipgsection getIpgsection() {
        return this.ipgsection;
    }
    
    public void setIpgsection(Ipgsection ipgsection) {
        this.ipgsection = ipgsection;
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




}


