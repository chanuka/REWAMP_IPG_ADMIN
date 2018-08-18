package com.epic.epay.ipg.util.mapping;
// Generated Nov 8, 2013 9:17:09 AM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * IpgriskprofileblockedcurrencyId generated by hbm2java
 */
@Embeddable
public class IpgriskprofileblockedcurrencyId  implements java.io.Serializable {


     private String riskprofilecode;
     private String currencycode;

    public IpgriskprofileblockedcurrencyId() {
    }

    public IpgriskprofileblockedcurrencyId(String riskprofilecode, String currencycode) {
       this.riskprofilecode = riskprofilecode;
       this.currencycode = currencycode;
    }
   

    @Column(name="RISKPROFILECODE", nullable=false, length=8)
    public String getRiskprofilecode() {
        return this.riskprofilecode;
    }
    
    public void setRiskprofilecode(String riskprofilecode) {
        this.riskprofilecode = riskprofilecode;
    }

    @Column(name="CURRENCYCODE", nullable=false, length=8)
    public String getCurrencycode() {
        return this.currencycode;
    }
    
    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof IpgriskprofileblockedcurrencyId) ) return false;
		 IpgriskprofileblockedcurrencyId castOther = ( IpgriskprofileblockedcurrencyId ) other; 
         
		 return ( (this.getRiskprofilecode()==castOther.getRiskprofilecode()) || ( this.getRiskprofilecode()!=null && castOther.getRiskprofilecode()!=null && this.getRiskprofilecode().equals(castOther.getRiskprofilecode()) ) )
 && ( (this.getCurrencycode()==castOther.getCurrencycode()) || ( this.getCurrencycode()!=null && castOther.getCurrencycode()!=null && this.getCurrencycode().equals(castOther.getCurrencycode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRiskprofilecode() == null ? 0 : this.getRiskprofilecode().hashCode() );
         result = 37 * result + ( getCurrencycode() == null ? 0 : this.getCurrencycode().hashCode() );
         return result;
   }   


}

