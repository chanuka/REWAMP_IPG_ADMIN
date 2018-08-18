/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.merchantmanagement;

/**
 * 
 * @created on : Nov 22, 2013, 11:50:56 AM
 * @author     : Thushanth
 *
 */
public class MerchantCertificateManagerInputBean {
	
/* ---------user inputs data-----  */	
	
      
    private String message;
    
    /* ---------user inputs data-----  */
    
    /*-------for access control-----------*/
    
    private boolean vdownload;
    
    /*-------for access control-----------*/
   
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isVdownload() {
		return vdownload;
	}

	public void setVdownload(boolean vdownload) {
		this.vdownload = vdownload;
	}
    
    

    
}
