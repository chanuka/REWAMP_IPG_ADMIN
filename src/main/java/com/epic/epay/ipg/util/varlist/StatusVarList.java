/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.varlist;

/**
 *
 * @author badrika
 */
public class StatusVarList {

    //transaction status
    public static final String TXN_INITIATED = "TRINI"; //Transaction Initiated
    public static final String TXN_VALIDATED = "TRVAL"; //Transaction Validated
    public static final String TXN_VALIDATIONFAILED = "TRVEF"; //Transaction Validation Failed
    public static final String TXN_VERIFIREQUESTCREATED = "TRVRC"; //Transaction Verification Request Created
    public static final String TXN_VERIFIREQCREATEDFAIL = "TRVRF"; //Transaction Verification Request Created Failed
    public static final String TXN_VERIFIREQUESTSENT = "TRVRS"; //Transaction Verification Request Sent
    public static final String TXN_FAILED = "TRFAI"; //Transaction Failed
    public static final String TXN_VERIFICARIONRESRECEIVED = "TRVRR"; //Transaction Verification Response Recieved
    public static final String TXN_ENGINEREQCREATED = "TRERC"; //Transaction Engine Request Created
    public static final String TXN_ENGINEREQCREATEDFAIL = "TRERF"; //Transaction Engine Request Created Failed
    public static final String TXN_ENGINEREQUESTSENT = "TRERS"; //Transaction Engine Request Sent
    public static final String TXN_ENGINERESRECEIVED = "TRERR"; //Transaction Engine Response Received
    public static final String TXN_RESSENTTOMERCHANT = "TRRSM"; //IPG Response Sent
    public static final String TXN_COMPLETE_CONFIRMED = "TRCMC"; //IPG Transaction complete Confirmed
    public static final String TXN_COMPLETED = "TRCMP"; //Transaction Completed
    public static final String TXN_SETTLED = "TRSET"; //Transaction Settled
    public static final String TXN_CANCELED = "TXNCAL"; //Transaction Canceled
    public static final String TXN_VERFICATION_FAILED = "TRVIF"; //Transaction Verification Failed
    public static final String TXN_REQUEST_RECEIVED = "TRRRI"; //Transaction Request Recieved
    public static final String GENERAL_STATUS_ACTIVE = "Active"; //general sattus description
    public static final String GENERAL_STATUS_DEACTIVE = "In-Active"; //general sattus description
    public static final String TXN_FAILED_LIST_IN_CLAUSE = "'TRVEF','TRFAI','TRVRF','TRVIF','TRERF'"; //Transaction Failed List

    public static final String MOTO_TXN_CATEGORY = "MOTO";
    public static final String REFUND_TXN_CATEGORY = "REFUND";

    public static final String BATCH_OPEN_STATUS = "BAOPE";
    public static final String BATCH_SETTLED_STATUS = "BATSE";

    public static final String YES_STATUS = "YES";
    public static final String NO_STATUS = "NO";

    // Transaction batch upload status 
    public static final String BATCH_FILE_UPLOAD_INITIATE = "FINIT";
    public static final String BATCH_FILE_UPLOAD_COMPLETE = "FCOM";
    public static final String BATCH_FILE_UPLOAD_ERROR = "FERR";
    public static final String BATCH_TRANSACTION_TYPE_INSTANT = "INST";
    public static final String BATCH_TRANSACTION_TYPE_MANUAL = "MANU";
    public static final String BATCH_TRANSACTION_TYPE_AUTO = "AUTO";
    public static final String BATCH_TRANSACTION_TYPE_INSTANT_PREFIX = "INS_";
    public static final String BATCH_TRANSACTION_TYPE_MANUAL_PREFIX = "MAN_";
    public static final String BATCH_TRANSACTION_TYPE_AUTO_PREFIX = "AUT_";
    public static final int BATCH_TRANSACTION_TYPE_INSTANT_LENGTH = 8;
    public static final int BATCH_TRANSACTION_TYPE_MANUAL_LENGTH = 9;
    public static final int BATCH_TRANSACTION_TYPE_AUTO_LENGTH = 12;
    
    
}
