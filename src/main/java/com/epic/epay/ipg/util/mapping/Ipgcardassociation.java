package com.epic.epay.ipg.util.mapping;
// Generated Oct 24, 2013 11:34:32 AM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Ipgcardassociation generated by hbm2java
 */
@Entity
@Table(name="IPGCARDASSOCIATION"
    
)
public class Ipgcardassociation  implements java.io.Serializable {


     private String cardassociationcode;
     private String description;
     private String verifiedimageurl;
     private String cardimageurl;
     private Integer sortkey;
     private String lastupdateduser;
     private Date lastupdatedtime;
     private Date createdtime;
     private Set<Ipgtransaction> ipgtransactions = new HashSet<Ipgtransaction>(0);
     private Set<Ipgbin> ipgbins = new HashSet<Ipgbin>(0);
     private Set<Ipgmerchantcredential> ipgmerchantcredentials = new HashSet<Ipgmerchantcredential>(0);

    public Ipgcardassociation() {
    }

	
    public Ipgcardassociation(String cardassociationcode) {
        this.cardassociationcode = cardassociationcode;
    }
    public Ipgcardassociation(String cardassociationcode, String description, String verifiedimageurl, String cardimageurl, Integer sortkey, String lastupdateduser, Date lastupdatedtime, Date createdtime, Set<Ipgtransaction> ipgtransactions, Set<Ipgbin> ipgbins, Set<Ipgmerchantcredential> ipgmerchantcredentials) {
       this.cardassociationcode = cardassociationcode;
       this.description = description;
       this.verifiedimageurl = verifiedimageurl;
       this.cardimageurl = cardimageurl;
       this.sortkey = sortkey;
       this.lastupdateduser = lastupdateduser;
       this.lastupdatedtime = lastupdatedtime;
       this.createdtime = createdtime;
       this.ipgtransactions = ipgtransactions;
       this.ipgbins = ipgbins;
       this.ipgmerchantcredentials = ipgmerchantcredentials;
    }
   
     @Id 
    
    @Column(name="CARDASSOCIATIONCODE", unique=true, nullable=false, length=16)
    public String getCardassociationcode() {
        return this.cardassociationcode;
    }
    
    public void setCardassociationcode(String cardassociationcode) {
        this.cardassociationcode = cardassociationcode;
    }
    
    @Column(name="DESCRIPTION", length=64)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="VERIFIEDIMAGEURL")
    public String getVerifiedimageurl() {
        return this.verifiedimageurl;
    }
    
    public void setVerifiedimageurl(String verifiedimageurl) {
        this.verifiedimageurl = verifiedimageurl;
    }
    
    @Column(name="CARDIMAGEURL")
    public String getCardimageurl() {
        return this.cardimageurl;
    }
    
    public void setCardimageurl(String cardimageurl) {
        this.cardimageurl = cardimageurl;
    }
    
    @Column(name="SORTKEY", precision=5, scale=0)
    public Integer getSortkey() {
        return this.sortkey;
    }
    
    public void setSortkey(Integer sortkey) {
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
@OneToMany(  fetch=FetchType.LAZY, mappedBy="ipgcardassociation")
    public Set<Ipgtransaction> getIpgtransactions() {
        return this.ipgtransactions;
    }
    
    public void setIpgtransactions(Set<Ipgtransaction> ipgtransactions) {
        this.ipgtransactions = ipgtransactions;
    }
@OneToMany(  fetch=FetchType.LAZY, mappedBy="ipgcardassociation")
    public Set<Ipgbin> getIpgbins() {
        return this.ipgbins;
    }
    
    public void setIpgbins(Set<Ipgbin> ipgbins) {
        this.ipgbins = ipgbins;
    }
@OneToMany(  fetch=FetchType.LAZY, mappedBy="ipgcardassociation")
    public Set<Ipgmerchantcredential> getIpgmerchantcredentials() {
        return this.ipgmerchantcredentials;
    }
    
    public void setIpgmerchantcredentials(Set<Ipgmerchantcredential> ipgmerchantcredentials) {
        this.ipgmerchantcredentials = ipgmerchantcredentials;
    }




}


