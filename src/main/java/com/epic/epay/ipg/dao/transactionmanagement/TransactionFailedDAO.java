/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.epay.ipg.dao.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.TransactionFailedDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.TransactionFailedInputBean;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.varlist.StatusVarList;
import com.epic.epay.ipg.util.varlist.UserRoleTypeVarList;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author sivaganesan_t
 */
public class TransactionFailedDAO {
    
    //get card Type List list
    public List<Ipgcardassociation> getCardTypeList() throws Exception {

        List<Ipgcardassociation> cardTypeList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgcardassociation";
            Query query = session.createQuery(sql);
            cardTypeList = query.list();

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return cardTypeList;
    }
    
    // get transaction status list
    public List<Ipgstatus> getFailedStatusList() throws Exception {

        List<Ipgstatus> statusList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgstatus as s where s.statuscode in (:txnfailed1,:txnfailed2,:txnfailed3,:txnfailed4,:txnfailed5)";
            Query query = session.createQuery(sql);
            query.setString("txnfailed1", "TRVEF");
            query.setString("txnfailed2", "TRFAI");
            query.setString("txnfailed3", "TRVRF");
            query.setString("txnfailed4", "TRVIF");
            query.setString("txnfailed5", "TRERF");
            statusList = query.list();

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return statusList;
    }
    
    public boolean isMerchantUser(String userName) throws Exception {
        boolean isMerchant = false;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT SU.USERNAME FROM IPGSYSTEMUSER SU WHERE SU.USERNAME =:username AND SU.USERROLECODE in (SELECT USERROLECODE FROM IPGUSERROLE WHERE USERROLETYPECODE =:userrolecode)";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("username", userName);
            query.setParameter("userrolecode", UserRoleTypeVarList.MERCHANT);

            if (query.list().size() > 0) {
                isMerchant = true;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return isMerchant;
    }

    public boolean isHeadMerchant(String userName) throws Exception {
        boolean isHeadMerchant = false;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT MERCHANTCUSTOMERID FROM IPGHEADMERCHANT WHERE MERCHANTCUSTOMERID in (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME =:username)";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("username", userName);

            if (query.list().size() > 0) {
                isHeadMerchant = true;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return isHeadMerchant;
    }

    public boolean txnInitiatedStatus(String userName) throws Exception {
        boolean yes = false;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT MERCHANTCUSTOMERID FROM IPGHEADMERCHANT WHERE MERCHANTCUSTOMERID in (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME =:username) AND TRANSACTIONINITAIATEDSTATUS =:status";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("username", userName);
            query.setParameter("status", StatusVarList.YES_STATUS);

            if (query.list().size() > 0) {
                yes = true;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return yes;
    }
    
    public List<TransactionFailedDataBean> getTxnList( TransactionFailedInputBean inputbean, int max, int first, String orderBy) throws Exception {

        Session session = null;
        long fullcount = 0;

        //initialize, declare variables
        String sql = null; //to store entire sql statement
        List<TransactionFailedDataBean> dataList = new ArrayList<TransactionFailedDataBean>();
        String whereString = "";

        whereString = this.makeWhereStatementToTxnFailed(inputbean);
        try {
            session = HibernateInit.sessionFactory.openSession();

            sql = " SELECT t.IPGTRANSACTIONID, m.merchantname, c.DESCRIPTION AS CARDASSOCIATION, t.CARDHOLDERPAN,"
                    + " t.AMOUNT, s.description AS STATUS, t.CARDNAME, t.CREATEDTIME  "
                    + " FROM IPGTRANSACTION t, IPGMERCHANT m, IPGCARDASSOCIATION c,IPGSTATUS s  "
                    + whereString + orderBy;
//                    + whereString + " ORDER BY t.IPGTRANSACTIONID DESC ";
            
            SQLQuery query = session.createSQLQuery(sql);

//            if (inputbean.getTxnId() != null && !inputbean.getTxnId().isEmpty()) {
//                query.setParameter("txnid", inputbean.getTxnId());
//            }
//            if (inputbean.getMerchantName() != null && !inputbean.getMerchantName().isEmpty()) {
//                query.setParameter("mername", inputbean.getMerchantName());
//            }
//            if (inputbean.getCardHolderName() != null && !inputbean.getCardHolderName().isEmpty()) {
//                query.setParameter("cardname", inputbean.getCardHolderName());
//            }
//            if (inputbean.getCardNumber() != null && !inputbean.getCardNumber().isEmpty()) {
//                query.setParameter("cardnum", inputbean.getCardNumber().toString());
//            }
//            if (inputbean.getStatus() != null && !inputbean.getStatus().isEmpty()) {
//                query.setParameter("status", inputbean.getStatus());
//            }
//            if (inputbean.getCardType() != null && !inputbean.getCardType().isEmpty()) {
//                query.setParameter("cardtype", inputbean.getCardType());
//            }
            if (inputbean.getFromDate() != null && !inputbean.getFromDate().isEmpty()) {
                String sqlDate = this.stringTodate(inputbean.getFromDate());
                query.setParameter("frmdate", sqlDate);
            }
            if (inputbean.getToDate() != null && !inputbean.getToDate().isEmpty()) {
                String sqlDate = this.stringTodate(inputbean.getToDate());
                query.setParameter("todate", sqlDate);
            }

            fullcount = query.list().size();
            
            query.setMaxResults(max);
            query.setFirstResult(first);

            List list = query.list();

            for (Iterator it = list.iterator(); it.hasNext();) {
                Object[] obj = (Object[]) it.next();
                TransactionFailedDataBean bean = new TransactionFailedDataBean();

                bean.setTxnId(obj[0].toString());
                bean.setMerchantName(obj[1].toString());
                bean.setCardType(obj[2].toString());
                bean.setCardNumber(obj[3].toString());
                bean.setAmount(obj[4].toString());
                bean.setStatus(obj[5].toString());
                bean.setCardHolderName(obj[6].toString());
                bean.setCreatedTime(obj[7].toString());
                
                bean.setFullCount(fullcount);
                dataList.add(bean);
//                inputBean.getCurrencyMap().put(obj[0].toString(), obj[1].toString());
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return dataList;
    }
    
    private String makeWhereStatementToTxnFailed(TransactionFailedInputBean searchBean) throws Exception {
        //initialize, declare variables
        String whereString = null;
        int andString = 0;

        try {

            if ((searchBean.getFromDate() == null || searchBean.getFromDate().toString().isEmpty())
                    && (searchBean.getToDate() == null || searchBean.getToDate().toString().isEmpty())
                    && (searchBean.getCardType() == null || searchBean.getCardType().isEmpty())
                    && (searchBean.getCardNumber() == null || searchBean.getCardNumber().isEmpty())
                    && (searchBean.getMerchantName() == null || searchBean.getMerchantName().isEmpty())
                    && (searchBean.getTxnId() == null || searchBean.getTxnId().isEmpty())
                    && (searchBean.getCardHolderName() == null || searchBean.getCardHolderName().isEmpty())
                    && (searchBean.getStatus() == null || searchBean.getStatus().isEmpty())) {

                //user doesnt have search parameters
                //whereString = "    ";
                if (searchBean.getTag() == 1) {
                    // Serch Not Merchant user

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.STATUSCODE in ( " + StatusVarList.TXN_FAILED_LIST_IN_CLAUSE + " )";
                } else if (searchBean.getTag() == 2) {
                    // Serch Merchant user

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE "
                            + " AND t.MERCHANTID = (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' )"
                            + " AND t.STATUSCODE in ( " + StatusVarList.TXN_FAILED_LIST_IN_CLAUSE + " )";

                } else if (searchBean.getTag() == 3) {
                    // Serch Head Merchant user No status

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.MERCHANTID IN "
                            + " ( SELECT MERCHANTID FROM IPGMERCHANT WHERE MERCHANTCUSTOMERID = "
                            + " (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' ) )"
                            + " AND t.STATUSCODE in ( " + StatusVarList.TXN_FAILED_LIST_IN_CLAUSE + " )";

                }
            } else {
                //user having some search parameters
                //whereString = "  WHERE  ";
                if (searchBean.getTag() == 1) {
                    // Serch Not Merchant user

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.STATUSCODE in ( " + StatusVarList.TXN_FAILED_LIST_IN_CLAUSE + " ) AND";
                } else if (searchBean.getTag() == 2) {
                    // Serch Merchant user

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE "
                            + " AND t.MERCHANTID = (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' )"
                            + " AND t.STATUSCODE in ( " + StatusVarList.TXN_FAILED_LIST_IN_CLAUSE + " ) AND";

                } else if (searchBean.getTag() == 3) {
                    // Serch Head Merchant user No status

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.MERCHANTID IN "
                            + " ( SELECT MERCHANTID FROM IPGMERCHANT WHERE MERCHANTCUSTOMERID = "
                            + " (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' ) )"
                            + " AND t.STATUSCODE in ( " + StatusVarList.TXN_FAILED_LIST_IN_CLAUSE + " ) AND";

                }
            }

            //for transactionID
            if (searchBean.getTxnId() != null && !searchBean.getTxnId().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.IPGTRANSACTIONID LIKE '%"+searchBean.getTxnId().trim()+"%' ";
                andString = 1;
            }

            //for merchant
            if (searchBean.getMerchantName() != null && !searchBean.getMerchantName().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " m.MERCHANTNAME LIKE '%"+searchBean.getMerchantName().trim()+"%' ";
                andString = 1;
            }

            //for cardHolder
            if (searchBean.getCardHolderName() != null && !searchBean.getCardHolderName().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.CARDNAME LIKE '%"+searchBean.getCardHolderName().trim()+"%' ";
                andString = 1;
            }

            //for cardHolder
            if (searchBean.getCardNumber() != null && !searchBean.getCardNumber().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.CARDHOLDERPAN LIKE '%"+searchBean.getCardNumber().trim()+"%' ";
                andString = 1;
            }

            //for status
            if (searchBean.getStatus() != null && !searchBean.getStatus().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " s.STATUSCODE LIKE '%"+searchBean.getStatus().trim()+"%'";
                andString = 1;
            }
            //for cardAssociation
            if (searchBean.getCardType() != null && !searchBean.getCardType().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " c.CARDASSOCIATIONCODE LIKE '%"+searchBean.getCardType().trim()+"%' ";
                andString = 1;
            }

            //determine what exist as date...first one, second one or both
            if ((!searchBean.getFromDate().toString().isEmpty()) && (!searchBean.getToDate().toString().isEmpty())) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.CREATEDTIME BETWEEN to_date(:frmdate, 'yy-mm-dd') AND to_date(:todate, 'yy-mm-dd HH24:MI:SS') ";
                andString = 1;
            } else if (!searchBean.getFromDate().toString().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.CREATEDTIME >= to_date(:frmdate, 'yy-mm-dd')";
            } else if (!searchBean.getToDate().toString().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.CREATEDTIME <= to_date(:todate, 'yy-mm-dd HH24:MI:SS') ";
            }

        } catch (Exception exception) {

            throw exception;
        }

        return whereString;
    }
    
    //convert string to Date type
    private String stringTodate(String date) throws ParseException, Exception {

        String convertedDate;
        try {
            DateFormat userDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = userDateFormat.parse(date);
            convertedDate = dateFormatNeeded.format(date1);

        } catch (Exception ex) {
            throw ex;
        }
        return convertedDate;
    }
}
