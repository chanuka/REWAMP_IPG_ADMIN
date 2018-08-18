package com.epic.epay.ipg.util.mapping;
// Generated Oct 24, 2013 11:34:32 AM by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * IpgmerchantservicechargeId generated by hbm2java
 */
@Embeddable
public class IpgmerchantservicechargeId  implements java.io.Serializable {


     private String merchantid;
     private BigDecimal servicechargeid;

    public IpgmerchantservicechargeId() {
    }

    public IpgmerchantservicechargeId(String merchantid, BigDecimal servicechargeid) {
       this.merchantid = merchantid;
       this.servicechargeid = servicechargeid;
    }
   

    @Column(name="MERCHANTID", nullable=false, length=15)
    public String getMerchantid() {
        return this.merchantid;
    }
    
    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    @Column(name="SERVICECHARGEID", nullable=false, precision=22, scale=0)
    public BigDecimal getServicechargeid() {
        return this.servicechargeid;
    }
    
    public void setServicechargeid(BigDecimal servicechargeid) {
        this.servicechargeid = servicechargeid;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof IpgmerchantservicechargeId) ) return false;
		 IpgmerchantservicechargeId castOther = ( IpgmerchantservicechargeId ) other; 
         
		 return ( (this.getMerchantid()==castOther.getMerchantid()) || ( this.getMerchantid()!=null && castOther.getMerchantid()!=null && this.getMerchantid().equals(castOther.getMerchantid()) ) )
 && ( (this.getServicechargeid()==castOther.getServicechargeid()) || ( this.getServicechargeid()!=null && castOther.getServicechargeid()!=null && this.getServicechargeid().equals(castOther.getServicechargeid()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMerchantid() == null ? 0 : this.getMerchantid().hashCode() );
         result = 37 * result + ( getServicechargeid() == null ? 0 : this.getServicechargeid().hashCode() );
         return result;
   }   


}


