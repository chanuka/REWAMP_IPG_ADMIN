package com.epic.epay.ipg.util.mapping;
// Generated Oct 24, 2013 11:34:32 AM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * IpgtransactionId generated by hbm2java
 */
@Embeddable
public class IpgtransactionId  implements java.io.Serializable {


     private String ipgtransactionid;
     private String merchantid;

    public IpgtransactionId() {
    }

    public IpgtransactionId(String ipgtransactionid, String merchantid) {
       this.ipgtransactionid = ipgtransactionid;
       this.merchantid = merchantid;
    }
   

    @Column(name="IPGTRANSACTIONID", nullable=false, length=20)
    public String getIpgtransactionid() {
        return this.ipgtransactionid;
    }
    
    public void setIpgtransactionid(String ipgtransactionid) {
        this.ipgtransactionid = ipgtransactionid;
    }

    @Column(name="MERCHANTID", nullable=false, length=15)
    public String getMerchantid() {
        return this.merchantid;
    }
    
    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof IpgtransactionId) ) return false;
		 IpgtransactionId castOther = ( IpgtransactionId ) other; 
         
		 return ( (this.getIpgtransactionid()==castOther.getIpgtransactionid()) || ( this.getIpgtransactionid()!=null && castOther.getIpgtransactionid()!=null && this.getIpgtransactionid().equals(castOther.getIpgtransactionid()) ) )
 && ( (this.getMerchantid()==castOther.getMerchantid()) || ( this.getMerchantid()!=null && castOther.getMerchantid()!=null && this.getMerchantid().equals(castOther.getMerchantid()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIpgtransactionid() == null ? 0 : this.getIpgtransactionid().hashCode() );
         result = 37 * result + ( getMerchantid() == null ? 0 : this.getMerchantid().hashCode() );
         return result;
   }   


}


