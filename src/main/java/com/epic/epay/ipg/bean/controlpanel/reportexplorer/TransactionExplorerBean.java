/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.reportexplorer;

/**
 *
 * Created on  :Sep 16, 2014, 10:12:24 AM
 * @author 	   :thushanth
 *
 */
public class TransactionExplorerBean {
	
    private String txnid;
    private String merchantname;
    private String merchantid;
    private String associationcode;
    private String cardnumber;
    private String amount;
    private String status;
    private String createdtime;
    private String ECIval;
    private String purDate;
    private String cardholder;
    private long fullCount;
    
    
	public String getTxnid() {
		return txnid;
	}
	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}
	public String getMerchantname() {
		return merchantname;
	}
	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}
	public String getAssociationcode() {
		return associationcode;
	}
	public void setAssociationcode(String associationcode) {
		this.associationcode = associationcode;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedtime() {
		return createdtime;
	}
	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}
	public long getFullCount() {
		return fullCount;
	}
	public void setFullCount(long fullCount) {
		this.fullCount = fullCount;
	}
	public String getMerchantid() {
		return merchantid;
	}
	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}
        public String getECIval() {
            return ECIval;
        }
        public void setECIval(String ECIval) {
            this.ECIval = ECIval;
        }
        public String getPurDate() {
            return purDate;
        }
        public void setPurDate(String purDate) {
            this.purDate = purDate;
        }
        public String getCardholder() {
            return cardholder;
        }
        public void setCardholder(String cardholder) {
            this.cardholder = cardholder;
        }

}
