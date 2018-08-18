/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.systemconfig;

import com.epic.epay.ipg.util.mapping.Ipgcardassociation;

/**
 * @author ruwan_e
 *
 */
public class CardAssociationBean {
	
	private String cardAssociationCode;
	private String description;
	private String verifieldImageURL;
	private String cardImageURL;
	private String sortKey; 
        private String createdtime;
	
	/**
	 * @param m
	 */
	public CardAssociationBean(Ipgcardassociation m) {
            if(m.getCardassociationcode()!=null && !m.getCardassociationcode().isEmpty()){
                this.cardAssociationCode = m.getCardassociationcode();
            }else{
                this.cardAssociationCode = "--";
            }
            if(m.getDescription()!=null && !m.getDescription().isEmpty()){
                this.description = m.getDescription();
            }else{
                this.description = "--";
            }
            if(m.getVerifiedimageurl()!=null && !m.getVerifiedimageurl().isEmpty()){
                this.verifieldImageURL = m.getVerifiedimageurl();
            }else{
                this.verifieldImageURL = "--";
            }
            if(m.getCardimageurl()!=null && !m.getCardimageurl().isEmpty()){
                this.cardImageURL = m.getCardimageurl();
            }else{
                this.cardImageURL = "--";
            }
            if(m.getSortkey()!=null ){
                this.sortKey = m.getSortkey().toString();
            }else{
                this.sortKey = "--";
            }
            if(m.getCreatedtime()!=null ){
                String createdTime = m.getCreatedtime().toString();
                this.createdtime = createdTime.substring(0, createdTime.length() - 2);
            }else{
                this.createdtime = "--";
            }
	
	}

	/**
	 * @return the cardAssociationCode
	 */
	public String getCardAssociationCode() {
		return cardAssociationCode;
	}

	/**
	 * @param cardAssociationCode the cardAssociationCode to set
	 */
	public void setCardAssociationCode(String cardAssociationCode) {
		this.cardAssociationCode = cardAssociationCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the verifieldImageURL
	 */
	public String getVerifieldImageURL() {
		return verifieldImageURL;
	}

	/**
	 * @param verifieldImageURL the verifieldImageURL to set
	 */
	public void setVerifieldImageURL(String verifieldImageURL) {
		this.verifieldImageURL = verifieldImageURL;
	}

	/**
	 * @return the cardImageURL
	 */
	public String getCardImageURL() {
		return cardImageURL;
	}

	/**
	 * @param cardImageURL the cardImageURL to set
	 */
	public void setCardImageURL(String cardImageURL) {
		this.cardImageURL = cardImageURL;
	}

	/**
	 * @return the sortKey
	 */
	public String getSortKey() {
		return sortKey;
	}

	/**
	 * @param sortKey the sortKey to set
	 */
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
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
