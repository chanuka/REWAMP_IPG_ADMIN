
package com.epic.epay.ipg.util.mapping;
// Generated Jul 5, 2018 5:45:52 PM by Hibernate Tools 3.6.0

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
 * IpgpagetaskTemplate generated by hbm2java
 */
@Entity
@Table(name="IPGPAGETASK_TEMPLATE")
public class IpgpagetaskTemplate  implements java.io.Serializable {


     private IpgpagetaskTemplateId id;
     private Ipgpage ipgpage;
     private Ipgtask ipgtask;
     private String lastupdateduser;
     private Date lastupdatedtime;
     private Date createtime;

    public IpgpagetaskTemplate() {
    }

	
    public IpgpagetaskTemplate(IpgpagetaskTemplateId id, Ipgpage ipgpage, Ipgtask ipgtask) {
        this.id = id;
        this.ipgpage = ipgpage;
        this.ipgtask = ipgtask;
    }
    public IpgpagetaskTemplate(IpgpagetaskTemplateId id, Ipgpage ipgpage, Ipgtask ipgtask, String lastupdateduser, Date lastupdatedtime, Date createtime) {
       this.id = id;
       this.ipgpage = ipgpage;
       this.ipgtask = ipgtask;
       this.lastupdateduser = lastupdateduser;
       this.lastupdatedtime = lastupdatedtime;
       this.createtime = createtime;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="pagecode", column=@Column(name="PAGECODE", nullable=false, length=16) ), 
        @AttributeOverride(name="taskcode", column=@Column(name="TASKCODE", nullable=false, length=16) ) } )
    public IpgpagetaskTemplateId getId() {
        return this.id;
    }
    
    public void setId(IpgpagetaskTemplateId id) {
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

    @Temporal(TemporalType.DATE)
    @Column(name="LASTUPDATEDTIME", length=7)
    public Date getLastupdatedtime() {
        return this.lastupdatedtime;
    }
    
    public void setLastupdatedtime(Date lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="CREATETIME", length=7)
    public Date getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }




}
