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
 * Ipgtask generated by hbm2java
 */
@Entity
@Table(name="IPGTASK")
public class Ipgtask  implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7769469138603030647L;
	/**
	 * 
	 */

	private String taskcode;
	private Ipgstatus ipgstatus;
	private String description;
	private BigDecimal sortkey;
	private String lastupdateduser;
	private Date lastupdatedtime;
	private Date createdtime;
	private Set<Ipgpagetask> ipgpagetasks = new HashSet<Ipgpagetask>(0);
	private Set<Ipgsystemaudit> ipgsystemaudits = new HashSet<Ipgsystemaudit>(0);

	public Ipgtask() {
	}


	public Ipgtask(String taskcode) {
		this.taskcode = taskcode;
	}
	public Ipgtask(String taskcode, Ipgstatus ipgstatus, String description, BigDecimal sortkey, String lastupdateduser, Date lastupdatedtime, Date createdtime, Set<Ipgpagetask> ipgpagetasks, Set<Ipgsystemaudit> ipgsystemaudits) {
		this.taskcode = taskcode;
		this.ipgstatus = ipgstatus;
		this.description = description;
		this.sortkey = sortkey;
		this.lastupdateduser = lastupdateduser;
		this.lastupdatedtime = lastupdatedtime;
		this.createdtime = createdtime;
		this.ipgpagetasks = ipgpagetasks;
		this.ipgsystemaudits = ipgsystemaudits;
	}

	@Id 

	@Column(name="TASKCODE", unique=true, nullable=false, length=16)
	public String getTaskcode() {
		return this.taskcode;
	}

	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
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

	@Column(name="SORTKEY",precision=22, scale=0)
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
	@OneToMany(  fetch=FetchType.LAZY, mappedBy="ipgtask")
	public Set<Ipgpagetask> getIpgpagetasks() {
		return this.ipgpagetasks;
	}

	public void setIpgpagetasks(Set<Ipgpagetask> ipgpagetasks) {
		this.ipgpagetasks = ipgpagetasks;
	}
	@OneToMany(  fetch=FetchType.LAZY, mappedBy="ipgtask")
	public Set<Ipgsystemaudit> getIpgsystemaudits() {
		return this.ipgsystemaudits;
	}

	public void setIpgsystemaudits(Set<Ipgsystemaudit> ipgsystemaudits) {
		this.ipgsystemaudits = ipgsystemaudits;
	}




}


