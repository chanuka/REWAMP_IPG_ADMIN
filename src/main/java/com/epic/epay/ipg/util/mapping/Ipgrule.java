package com.epic.epay.ipg.util.mapping;
// Generated Jul 31, 2018 10:15:45 AM by Hibernate Tools 3.6.0

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
 * Ipgrule generated by hbm2java
 */
@Entity
@Table(name = "IPGRULE"
)
public class Ipgrule implements java.io.Serializable {

    private long ruleid;
    private Ipgsecuritylevel ipgsecuritylevel;
    private Ipgrulescope ipgrulescope;
    private String operator;
    private String ruletypecode;
    private String startvalue;
    private String endvalue;
    private Long triggersequenceid;
    private String lastupdateduser;
    private Date lastupdatedtime;
    private Date createdtime;
    private String description;
    private Ipgstatus ipgstatus;

    public Ipgrule() {
    }

    public Ipgrule(long ruleid) {
        this.ruleid = ruleid;
    }

    public Ipgrule(long ruleid, Ipgsecuritylevel ipgsecuritylevel, Ipgrulescope ipgrulescope, String operator, String ruletypecode, String startvalue, String endvalue, Long triggersequenceid, String lastupdateduser, Date lastupdatedtime, Date createdtime, String description, Ipgstatus ipgstatus) {
        this.ruleid = ruleid;
        this.ipgsecuritylevel = ipgsecuritylevel;
        this.ipgrulescope = ipgrulescope;
        this.operator = operator;
        this.ruletypecode = ruletypecode;
        this.startvalue = startvalue;
        this.endvalue = endvalue;
        this.triggersequenceid = triggersequenceid;
        this.lastupdateduser = lastupdateduser;
        this.lastupdatedtime = lastupdatedtime;
        this.createdtime = createdtime;
        this.description = description;
        this.ipgstatus = ipgstatus;
    }

    @Id    
    @Column(name = "RULEID", unique = true, nullable = false, precision = 16, scale = 0)
    public long getRuleid() {
        return this.ruleid;
    }

    public void setRuleid(long ruleid) {
        this.ruleid = ruleid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECURITYLEVEL")
    public Ipgsecuritylevel getIpgsecuritylevel() {
        return this.ipgsecuritylevel;
    }

    public void setIpgsecuritylevel(Ipgsecuritylevel ipgsecuritylevel) {
        this.ipgsecuritylevel = ipgsecuritylevel;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RULESCOPECODE")
    public Ipgrulescope getIpgrulescope() {
        return this.ipgrulescope;
    }

    public void setIpgrulescope(Ipgrulescope ipgrulescope) {
        this.ipgrulescope = ipgrulescope;
    }

    @Column(name = "OPERATOR", length = 16)
    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name = "RULETYPECODE", length = 16)
    public String getRuletypecode() {
        return this.ruletypecode;
    }

    public void setRuletypecode(String ruletypecode) {
        this.ruletypecode = ruletypecode;
    }

    @Column(name = "STARTVALUE", length = 64)
    public String getStartvalue() {
        return this.startvalue;
    }

    public void setStartvalue(String startvalue) {
        this.startvalue = startvalue;
    }

    @Column(name = "ENDVALUE", length = 64)
    public String getEndvalue() {
        return this.endvalue;
    }

    public void setEndvalue(String endvalue) {
        this.endvalue = endvalue;
    }

    @Column(name = "TRIGGERSEQUENCEID", precision = 16, scale = 0)
    public Long getTriggersequenceid() {
        return this.triggersequenceid;
    }

    public void setTriggersequenceid(Long triggersequenceid) {
        this.triggersequenceid = triggersequenceid;
    }

    @Column(name = "LASTUPDATEDUSER", length = 64)
    public String getLastupdateduser() {
        return this.lastupdateduser;
    }

    public void setLastupdateduser(String lastupdateduser) {
        this.lastupdateduser = lastupdateduser;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "LASTUPDATEDTIME", length = 7)
    public Date getLastupdatedtime() {
        return this.lastupdatedtime;
    }

    public void setLastupdatedtime(Date lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATEDTIME", length = 7)
    public Date getCreatedtime() {
        return this.createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    @Column(name = "DESCRIPTION", length = 128)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS")
    public Ipgstatus getIpgstatus() {
        return this.ipgstatus;
    }

    public void setIpgstatus(Ipgstatus ipgstatus) {
        this.ipgstatus = ipgstatus;
    }

}