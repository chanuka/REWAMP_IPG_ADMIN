/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.varlist;

/**
 *
 * @author badrika
 */
public class TransactionCodeVarList {
    
    public static final String IPG_REFIND = "06";
    public static final String IPG_MOTO = "05";
    public static final String IPG_VOID = "02";
    public static final String IPG_REVERSAL = "03";
    public static final String IPG_SETTLEMENT = "04";
    public static final String IPG_SALE = "01";
    
    //switch txn message
    public static final String SWITCH_SUCCESS = "00";
    public static final String SWITCH_HOST_DOWN = "E1";
    public static final String SWITCH_BAD_TID = "E2";
    public static final String SWITCH_RE_CANCEL = "E3";
    public static final String SWITCH_INVALID_TXNID = "E4";
}
