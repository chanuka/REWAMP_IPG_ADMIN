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
 * Ipgmerchantaddon generated by hbm2java
 */
@Entity
@Table(name = "IPGMERCHANTADDON" )
public class Ipgmerchantaddon implements java.io.Serializable {

    private String merchantid;
    private Ipgmerchant ipgmerchant;
    private String logopath;
    private String xcordinate;
    private String ycordinate;
    private String displaytext;
    private String themecolor;
    private String fontfamily;
    private String fontstyle;
    private Integer fontsize;
    private String remarks;
    private String lastupdateduser;
    private Date lastupdatedtime;
    private Date createdtime;

    public Ipgmerchantaddon() {
    }

    public Ipgmerchantaddon(String merchantid, Ipgmerchant ipgmerchant) {
        this.merchantid = merchantid;
        this.ipgmerchant = ipgmerchant;
    }

    public Ipgmerchantaddon(String merchantid, Ipgmerchant ipgmerchant, String logopath, String xcordinate, String ycordinate, String displaytext, String themecolor, String fontfamily, String fontstyle, Integer fontsize, String remarks, String lastupdateduser, Date lastupdatedtime, Date createdtime) {
        this.merchantid = merchantid;
        this.ipgmerchant = ipgmerchant;
        this.logopath = logopath;
        this.xcordinate = xcordinate;
        this.ycordinate = ycordinate;
        this.displaytext = displaytext;
        this.themecolor = themecolor;
        this.fontfamily = fontfamily;
        this.fontstyle = fontstyle;
        this.fontsize = fontsize;
        this.remarks = remarks;
        this.lastupdateduser = lastupdateduser;
        this.lastupdatedtime = lastupdatedtime;
        this.createdtime = createdtime;
    }

    @Id
    @Column(name = "MERCHANTID", unique = true, nullable = false, length = 15)
    public String getMerchantid() {
        return this.merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    @Column(name = "LOGOPATH")
    public String getLogopath() {
        return this.logopath;
    }

    public void setLogopath(String logopath) {
        this.logopath = logopath;
    }

    @Column(name = "XCORDINATE", length = 64)
    public String getXcordinate() {
        return this.xcordinate;
    }

    public void setXcordinate(String xcordinate) {
        this.xcordinate = xcordinate;
    }

    @Column(name = "YCORDINATE", length = 64)
    public String getYcordinate() {
        return this.ycordinate;
    }

    public void setYcordinate(String ycordinate) {
        this.ycordinate = ycordinate;
    }

    @Column(name = "DISPLAYTEXT")
    public String getDisplaytext() {
        return this.displaytext;
    }

    public void setDisplaytext(String displaytext) {
        this.displaytext = displaytext;
    }

    @Column(name = "THEMECOLOR", length = 64)
    public String getThemecolor() {
        return this.themecolor;
    }

    public void setThemecolor(String themecolor) {
        this.themecolor = themecolor;
    }

    @Column(name = "FONTFAMILY", length = 64)
    public String getFontfamily() {
        return this.fontfamily;
    }

    public void setFontfamily(String fontfamily) {
        this.fontfamily = fontfamily;
    }

    @Column(name = "FONTSTYLE", length = 64)
    public String getFontstyle() {
        return this.fontstyle;
    }

    public void setFontstyle(String fontstyle) {
        this.fontstyle = fontstyle;
    }

    @Column(name = "FONTSIZE", precision = 5, scale = 0)
    public Integer getFontsize() {
        return this.fontsize;
    }

    public void setFontsize(Integer fontsize) {
        this.fontsize = fontsize;
    }

    @Column(name = "REMARKS")
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "LASTUPDATEDUSER", length = 20)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANTID", unique = true, nullable = false, insertable = false, updatable = false)
    public Ipgmerchant getIpgmerchant() {
        return this.ipgmerchant;
    }

    public void setIpgmerchant(Ipgmerchant ipgmerchant) {
        this.ipgmerchant = ipgmerchant;
    }
}
