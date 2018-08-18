package com.epic.epay.ipg.util.mapping;
// Generated Oct 24, 2013 11:34:32 AM by Hibernate Tools 3.2.1.GA

import java.sql.Blob;
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
 * Ipgheadmerchant generated by hbm2java
 */
@Entity
@Table(name = "IPGHEADMERCHANT" )
public class Ipgheadmerchant implements java.io.Serializable {

    private String merchantcustomerid;
    private Ipgcountry ipgcountry;
    private Ipgstatus ipgstatusByTransactioninitaiatedstatus;
    private Ipgstatus ipgstatusByStatuscode;
    private Ipgstatus ipgstatusByAuthenticationrequied;
    private Ipgaddress ipgaddress;
    private Ipgriskprofile ipgriskprofile;
    private String merchantname;
    private String primaryurl;
    private String secondaryurl;
    private Date dateofregistry;
    private Date dateofexpiry;
    private String contactperson;
    private String remarks;
    private Blob privatekey;
    private Blob publickey;
//    private String riskprofilecode;
    private String transactioninitaiatedmerchntid;
    private String lastupdateduser;
    private Date lastupdatedtime;
    private Date createdtime;
    private Blob symetrickey;
    private String securitymechanism;
    private String directoryserver;
    private String merchantcustomername;
    private String email;
    private Set<Ipgmerchant> ipgmerchants = new HashSet<Ipgmerchant>(0);

    public Ipgheadmerchant() {
    }

    public Ipgheadmerchant(String merchantcustomerid, Ipgaddress ipgaddress, String merchantname, String primaryurl) {
        this.merchantcustomerid = merchantcustomerid;
        this.ipgaddress = ipgaddress;
        this.merchantname = merchantname;
        this.primaryurl = primaryurl;
    }

    public Ipgheadmerchant(String merchantcustomerid, Ipgriskprofile ipgriskprofile, Ipgcountry ipgcountry, Ipgstatus ipgstatusByTransactioninitaiatedstatus, Ipgstatus ipgstatusByStatuscode, Ipgstatus ipgstatusByAuthenticationrequied, Ipgaddress ipgaddress, String merchantname, String primaryurl, String secondaryurl, Date dateofregistry, Date dateofexpiry, String contactperson, String remarks, Blob privatekey, Blob publickey, String transactioninitaiatedmerchntid, String lastupdateduser, Date lastupdatedtime, Date createdtime, Blob symetrickey, String securitymechanism, String directoryserver,String merchantcustomername,String email, Set<Ipgmerchant> ipgmerchants) {
        this.merchantcustomerid = merchantcustomerid;
        this.ipgcountry = ipgcountry;
        this.ipgstatusByTransactioninitaiatedstatus = ipgstatusByTransactioninitaiatedstatus;
        this.ipgstatusByStatuscode = ipgstatusByStatuscode;
        this.ipgstatusByAuthenticationrequied = ipgstatusByAuthenticationrequied;
        this.ipgaddress = ipgaddress;
        this.merchantname = merchantname;
        this.primaryurl = primaryurl;
        this.secondaryurl = secondaryurl;
        this.dateofregistry = dateofregistry;
        this.dateofexpiry = dateofexpiry;
        this.contactperson = contactperson;
        this.remarks = remarks;
        this.privatekey = privatekey;
        this.publickey = publickey;
//        this.riskprofilecode = riskprofilecode;
        this.transactioninitaiatedmerchntid = transactioninitaiatedmerchntid;
        this.lastupdateduser = lastupdateduser;
        this.lastupdatedtime = lastupdatedtime;
        this.createdtime = createdtime;
        this.symetrickey = symetrickey;
        this.securitymechanism = securitymechanism;
        this.directoryserver = directoryserver;
        this.ipgmerchants = ipgmerchants;
        this.ipgriskprofile = ipgriskprofile;
        this.merchantcustomername=merchantcustomername;
        this.email=email;
    }

    @Id
    @Column(name = "MERCHANTCUSTOMERID", unique = true, nullable = false, length = 15)
    public String getMerchantcustomerid() {
        return this.merchantcustomerid;
    }

    public void setMerchantcustomerid(String merchantcustomerid) {
        this.merchantcustomerid = merchantcustomerid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRYCODE")
    public Ipgcountry getIpgcountry() {
        return this.ipgcountry;
    }

    public void setIpgcountry(Ipgcountry ipgcountry) {
        this.ipgcountry = ipgcountry;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSACTIONINITAIATEDSTATUS")
    public Ipgstatus getIpgstatusByTransactioninitaiatedstatus() {
        return this.ipgstatusByTransactioninitaiatedstatus;
    }

    public void setIpgstatusByTransactioninitaiatedstatus(Ipgstatus ipgstatusByTransactioninitaiatedstatus) {
        this.ipgstatusByTransactioninitaiatedstatus = ipgstatusByTransactioninitaiatedstatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUSCODE")
    public Ipgstatus getIpgstatusByStatuscode() {
        return this.ipgstatusByStatuscode;
    }

    public void setIpgstatusByStatuscode(Ipgstatus ipgstatusByStatuscode) {
        this.ipgstatusByStatuscode = ipgstatusByStatuscode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHENTICATIONREQUIED")
    public Ipgstatus getIpgstatusByAuthenticationrequied() {
        return this.ipgstatusByAuthenticationrequied;
    }

    public void setIpgstatusByAuthenticationrequied(Ipgstatus ipgstatusByAuthenticationrequied) {
        this.ipgstatusByAuthenticationrequied = ipgstatusByAuthenticationrequied;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESSID")
    public Ipgaddress getIpgaddress() {
        return this.ipgaddress;
    }

    public void setIpgaddress(Ipgaddress ipgaddress) {
        this.ipgaddress = ipgaddress;
    }

    @Column(name = "MERCHANTNAME")
    public String getMerchantname() {
        return this.merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    @Column(name = "PRIMARYURL")
    public String getPrimaryurl() {
        return this.primaryurl;
    }

    public void setPrimaryurl(String primaryurl) {
        this.primaryurl = primaryurl;
    }

    @Column(name = "SECONDARYURL")
    public String getSecondaryurl() {
        return this.secondaryurl;
    }

    public void setSecondaryurl(String secondaryurl) {
        this.secondaryurl = secondaryurl;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEOFREGISTRY", length = 7)
    public Date getDateofregistry() {
        return this.dateofregistry;
    }

    public void setDateofregistry(Date dateofregistry) {
        this.dateofregistry = dateofregistry;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEOFEXPIRY", length = 7)
    public Date getDateofexpiry() {
        return this.dateofexpiry;
    }

    public void setDateofexpiry(Date dateofexpiry) {
        this.dateofexpiry = dateofexpiry;
    }

    @Column(name = "CONTACTPERSON")
    public String getContactperson() {
        return this.contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    @Column(name = "REMARKS")
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "PRIVATEKEY")
    public Blob getPrivatekey() {
        return this.privatekey;
    }

    public void setPrivatekey(Blob privatekey) {
        this.privatekey = privatekey;
    }

    @Column(name = "PUBLICKEY")
    public Blob getPublickey() {
        return this.publickey;
    }

    public void setPublickey(Blob publickey) {
        this.publickey = publickey;
    }

//    @Column(name = "RISKPROFILECODE", length = 8)
//    public String getRiskprofilecode() {
//        return this.riskprofilecode;
//    }
//
//    public void setRiskprofilecode(String riskprofilecode) {
//        this.riskprofilecode = riskprofilecode;
//    }

    @Column(name = "TRANSACTIONINITAIATEDMERCHNTID", length = 15)
    public String getTransactioninitaiatedmerchntid() {
        return this.transactioninitaiatedmerchntid;
    }

    public void setTransactioninitaiatedmerchntid(String transactioninitaiatedmerchntid) {
        this.transactioninitaiatedmerchntid = transactioninitaiatedmerchntid;
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

    @Column(name = "SYMETRICKEY")
    public Blob getSymetrickey() {
        return this.symetrickey;
    }

    public void setSymetrickey(Blob symetrickey) {
        this.symetrickey = symetrickey;
    }

    @Column(name = "SECURITYMECHANISM", length = 64)
    public String getSecuritymechanism() {
        return this.securitymechanism;
    }

    public void setSecuritymechanism(String securitymechanism) {
        this.securitymechanism = securitymechanism;
    }

    @Column(name = "DIRECTORYSERVER", length = 16)
    public String getDirectoryserver() {
        return this.directoryserver;
    }

    public void setDirectoryserver(String directoryserver) {
        this.directoryserver = directoryserver;
    }

    @OneToMany(  fetch = FetchType.LAZY, mappedBy = "ipgheadmerchant")
    public Set<Ipgmerchant> getIpgmerchants() {
        return this.ipgmerchants;
    }

    public void setIpgmerchants(Set<Ipgmerchant> ipgmerchants) {
        this.ipgmerchants = ipgmerchants;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RISKPROFILECODE")
    public Ipgriskprofile getIpgriskprofile() {
        return this.ipgriskprofile;
    }

    public void setIpgriskprofile(Ipgriskprofile ipgriskprofile) {
        this.ipgriskprofile = ipgriskprofile;
    }

    @Column(name = "MERCHANTCUSTOMERNAME")
    public String getMerchantcustomername() {
        return merchantcustomername;
    }

    public void setMerchantcustomername(String merchantcustomername) {
        this.merchantcustomername = merchantcustomername;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}