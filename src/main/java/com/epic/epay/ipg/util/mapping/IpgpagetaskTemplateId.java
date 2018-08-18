
package com.epic.epay.ipg.util.mapping;
// Generated Jul 5, 2018 5:45:52 PM by Hibernate Tools 3.6.0


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * IpgpagetaskTemplateId generated by hbm2java
 */
@Embeddable
public class IpgpagetaskTemplateId  implements java.io.Serializable {


     private String pagecode;
     private String taskcode;

    public IpgpagetaskTemplateId() {
    }

    public IpgpagetaskTemplateId(String pagecode, String taskcode) {
       this.pagecode = pagecode;
       this.taskcode = taskcode;
    }
   


    @Column(name="PAGECODE", nullable=false, length=16)
    public String getPagecode() {
        return this.pagecode;
    }
    
    public void setPagecode(String pagecode) {
        this.pagecode = pagecode;
    }


    @Column(name="TASKCODE", nullable=false, length=16)
    public String getTaskcode() {
        return this.taskcode;
    }
    
    public void setTaskcode(String taskcode) {
        this.taskcode = taskcode;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof IpgpagetaskTemplateId) ) return false;
		 IpgpagetaskTemplateId castOther = ( IpgpagetaskTemplateId ) other; 
         
		 return ( (this.getPagecode()==castOther.getPagecode()) || ( this.getPagecode()!=null && castOther.getPagecode()!=null && this.getPagecode().equals(castOther.getPagecode()) ) )
 && ( (this.getTaskcode()==castOther.getTaskcode()) || ( this.getTaskcode()!=null && castOther.getTaskcode()!=null && this.getTaskcode().equals(castOther.getTaskcode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPagecode() == null ? 0 : this.getPagecode().hashCode() );
         result = 37 * result + ( getTaskcode() == null ? 0 : this.getTaskcode().hashCode() );
         return result;
   }   


}