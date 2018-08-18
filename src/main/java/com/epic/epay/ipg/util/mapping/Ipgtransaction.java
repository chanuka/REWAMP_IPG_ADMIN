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
 * Ipgtransaction generated by hbm2java
 */
@Entity
@Table(name = "IPGTRANSACTION" )
public class Ipgtransaction implements java.io.Serializable {

    private IpgtransactionId id;
    private Ipgcountry ipgcountry;
    private Ipgcurrency ipgcurrency;
    private Ipgstatus ipgstatus;
    private Ipgcardassociation ipgcardassociation;
    private String merchantremarks;
    private String cardholderpan;
    private int amount;
    private Date purchaseddatetime;
    private String cardname;
    private String expirydate;
    private String cvcnumber;
    private Date transactioncreateddatetime;
    private String transactioncode;
    private String rrn;
    private String approvalcode;
    private String batchnumber;
    private String merchantreferanceno;
    private String lastupdateduser;
    private Date lastupdatedtime;
    private Date createdtime;
    private String merchanttype;
    private String terminalid;
    private String cavv;
    private String cvv;
    private String eci;
    private String clientip;
    private String cvvnumber;
    private String cidnumber;
    private String txncategory;
    private String orderid;
    private Ipgmerchant ipgmerchant;
    private Set<Epictxnrequests> epictxnrequestses = new HashSet<Epictxnrequests>(0);

    public Ipgtransaction() {
    }

    public Ipgtransaction(IpgtransactionId id, Ipgcurrency ipgcurrency, Ipgmerchant ipgmerchant, Ipgstatus ipgstatus, Ipgcardassociation ipgcardassociation, String merchantremarks, String cardholderpan, int amount, Date purchaseddatetime, String cardname, String expirydate, Date transactioncreateddatetime) {
        this.id = id;
        this.ipgcurrency = ipgcurrency;
        this.ipgstatus = ipgstatus;
        this.ipgcardassociation = ipgcardassociation;
        this.merchantremarks = merchantremarks;
        this.cardholderpan = cardholderpan;
        this.amount = amount;
        this.purchaseddatetime = purchaseddatetime;
        this.cardname = cardname;
        this.expirydate = expirydate;
        this.ipgmerchant = ipgmerchant;
        this.transactioncreateddatetime = transactioncreateddatetime;
    }

    public Ipgtransaction(IpgtransactionId id, Ipgcountry ipgcountry, Ipgcurrency ipgcurrency, Ipgstatus ipgstatus, Ipgcardassociation ipgcardassociation, String merchantremarks, String cardholderpan, int amount, Date purchaseddatetime, String cardname, String expirydate, String cvcnumber, Date transactioncreateddatetime, String transactioncode, String rrn, String approvalcode, String batchnumber, String merchantreferanceno, String lastupdateduser, Date lastupdatedtime, Date createdtime, String merchanttype, String terminalid, String cavv, String cvv, String eci, String clientip, String cvvnumber, String cidnumber, String txncategory, String orderid, Set<Epictxnrequests> epictxnrequestses) {
        this.id = id;
        this.ipgcountry = ipgcountry;
        this.ipgcurrency = ipgcurrency;
        this.ipgstatus = ipgstatus;
        this.ipgcardassociation = ipgcardassociation;
        this.merchantremarks = merchantremarks;
        this.cardholderpan = cardholderpan;
        this.amount = amount;
        this.purchaseddatetime = purchaseddatetime;
        this.cardname = cardname;
        this.expirydate = expirydate;
        this.cvcnumber = cvcnumber;
        this.transactioncreateddatetime = transactioncreateddatetime;
        this.transactioncode = transactioncode;
        this.rrn = rrn;
        this.approvalcode = approvalcode;
        this.batchnumber = batchnumber;
        this.merchantreferanceno = merchantreferanceno;
        this.lastupdateduser = lastupdateduser;
        this.lastupdatedtime = lastupdatedtime;
        this.createdtime = createdtime;
        this.merchanttype = merchanttype;
        this.terminalid = terminalid;
        this.cavv = cavv;
        this.cvv = cvv;
        this.eci = eci;
        this.clientip = clientip;
        this.cvvnumber = cvvnumber;
        this.cidnumber = cidnumber;
        this.txncategory = txncategory;
        this.orderid = orderid;
        this.epictxnrequestses = epictxnrequestses;
    }

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "ipgtransactionid", column =
        @Column(name = "IPGTRANSACTIONID", nullable = false, length = 20)),
        @AttributeOverride(name = "merchantid", column =
        @Column(name = "MERCHANTID", nullable = false, length = 15))})
    public IpgtransactionId getId() {
        return this.id;
    }

    public void setId(IpgtransactionId id) {
        this.id = id;
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
    @JoinColumn(name = "CURRENCYCODE", nullable = false)
    public Ipgcurrency getIpgcurrency() {
        return this.ipgcurrency;
    }

    public void setIpgcurrency(Ipgcurrency ipgcurrency) {
        this.ipgcurrency = ipgcurrency;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUSCODE", nullable = false)
    public Ipgstatus getIpgstatus() {
        return this.ipgstatus;
    }

    public void setIpgstatus(Ipgstatus ipgstatus) {
        this.ipgstatus = ipgstatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CARDASSOCIATIONCODE", nullable = false)
    public Ipgcardassociation getIpgcardassociation() {
        return this.ipgcardassociation;
    }

    public void setIpgcardassociation(Ipgcardassociation ipgcardassociation) {
        this.ipgcardassociation = ipgcardassociation;
    }

    @Column(name = "MERCHANTREMARKS", nullable = false)
    public String getMerchantremarks() {
        return this.merchantremarks;
    }

    public void setMerchantremarks(String merchantremarks) {
        this.merchantremarks = merchantremarks;
    }

    @Column(name = "CARDHOLDERPAN", nullable = false)
    public String getCardholderpan() {
        return this.cardholderpan;
    }

    public void setCardholderpan(String cardholderpan) {
        this.cardholderpan = cardholderpan;
    }

    @Column(name = "AMOUNT", nullable = false, precision = 9, scale = 0)
    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PURCHASEDDATETIME", nullable = false, length = 7)
    public Date getPurchaseddatetime() {
        return this.purchaseddatetime;
    }

    public void setPurchaseddatetime(Date purchaseddatetime) {
        this.purchaseddatetime = purchaseddatetime;
    }

    @Column(name = "CARDNAME", nullable = false, length = 25)
    public String getCardname() {
        return this.cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    @Column(name = "EXPIRYDATE", nullable = false, length = 6)
    public String getExpirydate() {
        return this.expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    @Column(name = "CVCNUMBER", length = 4)
    public String getCvcnumber() {
        return this.cvcnumber;
    }

    public void setCvcnumber(String cvcnumber) {
        this.cvcnumber = cvcnumber;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSACTIONCREATEDDATETIME", nullable = false, length = 7)
    public Date getTransactioncreateddatetime() {
        return this.transactioncreateddatetime;
    }

    public void setTransactioncreateddatetime(Date transactioncreateddatetime) {
        this.transactioncreateddatetime = transactioncreateddatetime;
    }

    @Column(name = "TRANSACTIONCODE", length = 2)
    public String getTransactioncode() {
        return this.transactioncode;
    }

    public void setTransactioncode(String transactioncode) {
        this.transactioncode = transactioncode;
    }

    @Column(name = "RRN", length = 12)
    public String getRrn() {
        return this.rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    @Column(name = "APPROVALCODE", length = 6)
    public String getApprovalcode() {
        return this.approvalcode;
    }

    public void setApprovalcode(String approvalcode) {
        this.approvalcode = approvalcode;
    }

    @Column(name = "BATCHNUMBER", length = 6)
    public String getBatchnumber() {
        return this.batchnumber;
    }

    public void setBatchnumber(String batchnumber) {
        this.batchnumber = batchnumber;
    }

    @Column(name = "MERCHANTREFERANCENO", length = 15)
    public String getMerchantreferanceno() {
        return this.merchantreferanceno;
    }

    public void setMerchantreferanceno(String merchantreferanceno) {
        this.merchantreferanceno = merchantreferanceno;
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

    @Column(name = "MERCHANTTYPE", length = 4)
    public String getMerchanttype() {
        return this.merchanttype;
    }

    public void setMerchanttype(String merchanttype) {
        this.merchanttype = merchanttype;
    }

    @Column(name = "TERMINALID", length = 8)
    public String getTerminalid() {
        return this.terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    @Column(name = "CAVV", length = 32)
    public String getCavv() {
        return this.cavv;
    }

    public void setCavv(String cavv) {
        this.cavv = cavv;
    }

    @Column(name = "CVV", length = 4)
    public String getCvv() {
        return this.cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Column(name = "ECI", length = 4)
    public String getEci() {
        return this.eci;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }

    @Column(name = "CLIENTIP", length = 16)
    public String getClientip() {
        return this.clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    @Column(name = "CVVNUMBER", length = 3)
    public String getCvvnumber() {
        return this.cvvnumber;
    }

    public void setCvvnumber(String cvvnumber) {
        this.cvvnumber = cvvnumber;
    }

    @Column(name = "CIDNUMBER", length = 4)
    public String getCidnumber() {
        return this.cidnumber;
    }

    public void setCidnumber(String cidnumber) {
        this.cidnumber = cidnumber;
    }

    @Column(name = "TXNCATEGORY", length = 6)
    public String getTxncategory() {
        return this.txncategory;
    }

    public void setTxncategory(String txncategory) {
        this.txncategory = txncategory;
    }

    @Column(name = "ORDERID", length = 12)
    public String getOrderid() {
        return this.orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @OneToMany(  fetch = FetchType.LAZY, mappedBy = "ipgtransaction")
    public Set<Epictxnrequests> getEpictxnrequestses() {
        return this.epictxnrequestses;
    }

    public void setEpictxnrequestses(Set<Epictxnrequests> epictxnrequestses) {
        this.epictxnrequestses = epictxnrequestses;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANTID", nullable = false, insertable = false, updatable = false)
    public Ipgmerchant getIpgmerchant() {
        return this.ipgmerchant;
    }

    public void setIpgmerchant(Ipgmerchant ipgmerchant) {
        this.ipgmerchant = ipgmerchant;
    }
}
