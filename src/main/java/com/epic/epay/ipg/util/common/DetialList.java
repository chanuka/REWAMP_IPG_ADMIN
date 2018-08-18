/**
 * 
 */
package com.epic.epay.ipg.util.common;

/**
 *
 * Created on  :Sep 16, 2014, 2:45:22 PM
 * @author 	   :thushanth
 *
 */
public class DetialList<E, T> {
	
	private E merList;
    private T txnList;

    // Generic constructor
    public DetialList() {

    }

	public E getMerList() {
		return merList;
	}

	public void setMerList(E merList) {
		this.merList = merList;
	}

	public T getTxnList() {
		return txnList;
	}

	public void setTxnList(T txnList) {
		this.txnList = txnList;
	}

}
