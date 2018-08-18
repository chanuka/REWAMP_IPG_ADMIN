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
 * Ipgpagetask generated by hbm2java
 */
@Entity
@Table(name="IPGPAGETASK"
    
)
public class Ipgpagetask  implements java.io.Serializable {


     private IpgpagetaskId id;
     private Ipgpage ipgpage;
     private Ipgtask ipgtask;
     private String lastupdateduser;
     private Date lastupdatedtime;
     private Date createdtime;

    public Ipgpagetask() {
    }

	
    public Ipgpagetask(IpgpagetaskId id, Ipgpage ipgpage, Ipgtask ipgtask) {
        this.id = id;
        this.ipgpage = ipgpage;
        this.ipgtask = ipgtask;
    }
    
    public Ipgpagetask(IpgpagetaskId id, Ipgtask ipgtask) {
        this.id = id;
        this.ipgtask = ipgtask;
    }
    
    
    public Ipgpagetask(IpgpagetaskId id, Ipgpage ipgpage, Ipgtask ipgtask, String lastupdateduser, Date lastupdatedtime, Date createdtime) {
       this.id = id;
       this.ipgpage = ipgpage;
       this.ipgtask = ipgtask;
       this.lastupdateduser = lastupdateduser;
       this.lastupdatedtime = lastupdatedtime;
       this.createdtime = createdtime;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="userrolecode", column=@Column(name="USERROLECODE", nullable=false, length=16) ), 
        @AttributeOverride(name="pagecode", column=@Column(name="PAGECODE", nullable=false, length=16) ), 
        @AttributeOverride(name="taskcode", column=@Column(name="TASKCODE", nullable=false, length=16) ) } )
    public IpgpagetaskId getId() {
        return this.id;
    }
    
    public void setId(IpgpagetaskId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PAGECODE", nullable=false, insertable=false, updatable=false)
    public Ipgpage getIpgpage() {
        return this.ipgpage;
    }
    
    public void setIpgpage(Ipgpage ipgpage) {
        this.ipgpage = ipgpage;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TASKCODE", nullable=false, insertable=false, updatable=false)
    public Ipgtask getIpgtask() {
        return this.ipgtask;
    }
    
    public void setIpgtask(Ipgtask ipgtask) {
        this.ipgtask = ipgtask;
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


