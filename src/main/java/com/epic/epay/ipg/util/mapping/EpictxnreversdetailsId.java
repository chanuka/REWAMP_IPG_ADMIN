package com.epic.epay.ipg.util.mapping;
// Generated Oct 24, 2013 11:34:32 AM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * EpictxnreversdetailsId generated by hbm2java
 */
@Embeddable
public class EpictxnreversdetailsId  implements java.io.Serializable {


     private String mti;
     private String processingcode;
     private String txndate;
     private String txntime;
     private String traceno;
     private String rrn;
     private String authocode;
     private String tid;
     private String mid;
     private String amount;

    public EpictxnreversdetailsId() {
    }

    public EpictxnreversdetailsId(String mti, String processingcode, String txndate, String txntime, String traceno, String rrn, String authocode, String tid, String mid, String amount) {
       this.mti = mti;
       this.processingcode = processingcode;
       this.txndate = txndate;
       this.txntime = txntime;
       this.traceno = traceno;
       this.rrn = rrn;
       this.authocode = authocode;
       this.tid = tid;
       this.mid = mid;
       this.amount = amount;
    }
   

    @Column(name="MTI", length=4)
    public String getMti() {
        return this.mti;
    }
    
    public void setMti(String mti) {
        this.mti = mti;
    }

    @Column(name="PROCESSINGCODE", length=6)
    public String getProcessingcode() {
        return this.processingcode;
    }
    
    public void setProcessingcode(String processingcode) {
        this.processingcode = processingcode;
    }

    @Column(name="TXNDATE", length=4)
    public String getTxndate() {
        return this.txndate;
    }
    
    public void setTxndate(String txndate) {
        this.txndate = txndate;
    }

    @Column(name="TXNTIME", length=6)
    public String getTxntime() {
        return this.txntime;
    }
    
    public void setTxntime(String txntime) {
        this.txntime = txntime;
    }

    @Column(name="TRACENO", length=6)
    public String getTraceno() {
        return this.traceno;
    }
    
    public void setTraceno(String traceno) {
        this.traceno = traceno;
    }

    @Column(name="RRN", length=12)
    public String getRrn() {
        return this.rrn;
    }
    
    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    @Column(name="AUTHOCODE", length=6)
    public String getAuthocode() {
        return this.authocode;
    }
    
    public void setAuthocode(String authocode) {
        this.authocode = authocode;
    }

    @Column(name="TID", length=8)
    public String getTid() {
        return this.tid;
    }
    
    public void setTid(String tid) {
        this.tid = tid;
    }

    @Column(name="MID", length=15)
    public String getMid() {
        return this.mid;
    }
    
    public void setMid(String mid) {
        this.mid = mid;
    }

    @Column(name="AMOUNT", length=12)
    public String getAmount() {
        return this.amount;
    }
    
    public void setAmount(String amount) {
        this.amount = amount;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof EpictxnreversdetailsId) ) return false;
		 EpictxnreversdetailsId castOther = ( EpictxnreversdetailsId ) other; 
         
		 return ( (this.getMti()==castOther.getMti()) || ( this.getMti()!=null && castOther.getMti()!=null && this.getMti().equals(castOther.getMti()) ) )
 && ( (this.getProcessingcode()==castOther.getProcessingcode()) || ( this.getProcessingcode()!=null && castOther.getProcessingcode()!=null && this.getProcessingcode().equals(castOther.getProcessingcode()) ) )
 && ( (this.getTxndate()==castOther.getTxndate()) || ( this.getTxndate()!=null && castOther.getTxndate()!=null && this.getTxndate().equals(castOther.getTxndate()) ) )
 && ( (this.getTxntime()==castOther.getTxntime()) || ( this.getTxntime()!=null && castOther.getTxntime()!=null && this.getTxntime().equals(castOther.getTxntime()) ) )
 && ( (this.getTraceno()==castOther.getTraceno()) || ( this.getTraceno()!=null && castOther.getTraceno()!=null && this.getTraceno().equals(castOther.getTraceno()) ) )
 && ( (this.getRrn()==castOther.getRrn()) || ( this.getRrn()!=null && castOther.getRrn()!=null && this.getRrn().equals(castOther.getRrn()) ) )
 && ( (this.getAuthocode()==castOther.getAuthocode()) || ( this.getAuthocode()!=null && castOther.getAuthocode()!=null && this.getAuthocode().equals(castOther.getAuthocode()) ) )
 && ( (this.getTid()==castOther.getTid()) || ( this.getTid()!=null && castOther.getTid()!=null && this.getTid().equals(castOther.getTid()) ) )
 && ( (this.getMid()==castOther.getMid()) || ( this.getMid()!=null && castOther.getMid()!=null && this.getMid().equals(castOther.getMid()) ) )
 && ( (this.getAmount()==castOther.getAmount()) || ( this.getAmount()!=null && castOther.getAmount()!=null && this.getAmount().equals(castOther.getAmount()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMti() == null ? 0 : this.getMti().hashCode() );
         result = 37 * result + ( getProcessingcode() == null ? 0 : this.getProcessingcode().hashCode() );
         result = 37 * result + ( getTxndate() == null ? 0 : this.getTxndate().hashCode() );
         result = 37 * result + ( getTxntime() == null ? 0 : this.getTxntime().hashCode() );
         result = 37 * result + ( getTraceno() == null ? 0 : this.getTraceno().hashCode() );
         result = 37 * result + ( getRrn() == null ? 0 : this.getRrn().hashCode() );
         result = 37 * result + ( getAuthocode() == null ? 0 : this.getAuthocode().hashCode() );
         result = 37 * result + ( getTid() == null ? 0 : this.getTid().hashCode() );
         result = 37 * result + ( getMid() == null ? 0 : this.getMid().hashCode() );
         result = 37 * result + ( getAmount() == null ? 0 : this.getAmount().hashCode() );
         return result;
   }   


}


