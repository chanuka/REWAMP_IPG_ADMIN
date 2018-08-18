package com.epic.epay.ipg.bean.controlpanel.usermanagement;

import java.io.Serializable;

/**
 * 
 * @author chalitha
 */

public class SectionBean implements Serializable {

	

	private static final long serialVersionUID = 4338675482067640870L;
	private String sectioncode;
	private String description;
	private String sortkey;
	private String statuscode;
	private long fullCount;

	public String getSectioncode() {
		return sectioncode;
	}

	public void setSectioncode(String sectioncode) {
		this.sectioncode = sectioncode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSortkey() {
		return sortkey;
	}

	public void setSortkey(String sortkey) {
		this.sortkey = sortkey;
	}

	public String getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}

	public long getFullCount() {
		return fullCount;
	}

	public void setFullCount(long fullCount) {
		this.fullCount = fullCount;
	}

}
