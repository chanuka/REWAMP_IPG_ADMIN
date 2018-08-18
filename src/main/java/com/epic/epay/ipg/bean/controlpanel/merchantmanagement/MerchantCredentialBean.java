/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.merchantmanagement;

/**
 * 
 * @created on : Nov 14, 2013, 10:26:37 AM
 * @author     : thushanth
 *
 */
public class MerchantCredentialBean {
	
	private String merchantId;
	private String merchantname;
	private String cardassociation;
	private String cardassociationCode;
	private String username;
        private String createdtime;
	private long fullCount;

    public String getCardassociationCode() {
        return cardassociationCode;
    }

    public void setCardassociationCode(String cardassociationCode) {
        this.cardassociationCode = cardassociationCode;
    }
	
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantname() {
		return merchantname;
	}
	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}
	public String getCardassociation() {
		return cardassociation;
	}
	public void setCardassociation(String cardassociation) {
		this.cardassociation = cardassociation;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getFullCount() {
		return fullCount;
	}
	public void setFullCount(long fullCount) {
		this.fullCount = fullCount;
	}

    /**
     * @return the createdtime
     */
    public String getCreatedtime() {
        return createdtime;
    }

    /**
     * @param createdtime the createdtime to set
     */
    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }
	

}
