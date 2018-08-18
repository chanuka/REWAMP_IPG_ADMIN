/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.varlist;

/**
 *
 * @author upul
 */
public class OracleMessage {

    public static final String ALLREADY_ADD = "Record already exists";
    public static final String INTE_CONST = "Parent key not found";
    public static final String INTE_CHILD = "Cannot delete. Record already in use.";
    public static final String INV_DATETIME = "Invalid date";
    public static final String INV_COLOMN_SIZE = "Invalid value size";
    public static final String TABLE_NOT_EXIST = "Table or view not exist.Please contact your databse administrator.";
    public static final String ORACLE_NOT_LICENCE = "ORACLE is not licenced.Contact database administrator.";
    public static final String VALUE_TOO_LARGE = "Inserted value is too large.Try with short one.";
    public static final String UNKNOWN_DB_ERROR = "Unknown error occured when communicating with database..";
    public static final String VALUE_TOO_LARGE_COLUMN = "Value too large for column.Try with short one.";
    public static final String CANT_INSERST_NULL = "Cannot insert null value";
    
    
    //custom messages onlu for cms holiday
     public static final String HOLIDAY_NOT_FOUND = "No holiday found on this day.Please check the date again.";

    public static String getMessege(String tmpErrorMsg) {

        String message = null;

        if (tmpErrorMsg.indexOf("ORA-00001") != -1) {

            message = ALLREADY_ADD;

        } else if (tmpErrorMsg.indexOf("ORA-02292") != -1) {
            message = INTE_CHILD;

        } else if (tmpErrorMsg.indexOf("ORA-02291") != -1) {
            message = INTE_CONST;

        } else if (tmpErrorMsg.indexOf("ORA-01830") != -1) {
            message = INV_DATETIME;

        } else if (tmpErrorMsg.indexOf("ORA-01438") != -1) {
            message = INV_COLOMN_SIZE;

        } else if (tmpErrorMsg.indexOf("ORA-00942") != -1) {
            message = TABLE_NOT_EXIST;

        } else if (tmpErrorMsg.indexOf("ORA-00436") != -1) {
            message = ORACLE_NOT_LICENCE;

        } else if (tmpErrorMsg.indexOf("ORA-12899") != -1) {
            message = VALUE_TOO_LARGE_COLUMN;

        } else if (tmpErrorMsg.indexOf("ORA-01400") != -1) {
            message = CANT_INSERST_NULL;

        } else if (tmpErrorMsg.indexOf("ORA-01438") != -1) {
            message = VALUE_TOO_LARGE;

        } 
        
        //custom messages only for cms
        else if (tmpErrorMsg.indexOf("ORA-HOLIDAY_VALUE_NOT_FOUND") != -1) {
            message = HOLIDAY_NOT_FOUND;

        } 
        
        
        else {
            message = UNKNOWN_DB_ERROR;
        }


        return message;
    }
}
