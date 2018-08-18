/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.TransactionDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.TransactionSearchBean;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtransaction;
import com.epic.epay.ipg.util.varlist.StatusVarList;
import com.epic.epay.ipg.util.varlist.UserRoleTypeVarList;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import oracle.sql.TIMESTAMP;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author badrika
 */
public class TransactionVoidDAO {

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
    public List<Ipgstatus> getDefultStatusList(String statusCode) throws Exception {

        List<Ipgstatus> statusList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgstatus as s where s.ipgstatuscategory.statuscategorycode =:statuscategorycode";
            Query query = session.createQuery(sql).setString("statuscategorycode", statusCode);
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
            String sql = "SELECT MERCHANTCUSTOMERID FROM IPGHEADMERCHANT WHERE TRANSACTIONINITAIATEDMERCHNTID in (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME =:username)";
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
            String sql = "SELECT MERCHANTCUSTOMERID FROM IPGHEADMERCHANT WHERE TRANSACTIONINITAIATEDMERCHNTID in (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME =:username) AND TRANSACTIONINITAIATEDSTATUS =:status";
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

    public TransactionDataBean getTransactionDataForswitch(String transactionId, String voidOrReversal) throws Exception {

        TransactionDataBean bean = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgtransaction as s where s.id.ipgtransactionid =:txnid and s.ipgstatus.statuscode =:status";
            Query query = session.createQuery(sql);

            query.setString("txnid", transactionId);
            if (voidOrReversal.equals("void")) {
                query.setString("status", StatusVarList.TXN_COMPLETE_CONFIRMED);
            } else if (voidOrReversal.equals("reversal")) {
                query.setString("status", StatusVarList.TXN_ENGINEREQUESTSENT);
            }

            Iterator it = query.iterate();

            while (it.hasNext()) {

                bean = new TransactionDataBean();
                Ipgtransaction txn = (Ipgtransaction) it.next();

                bean.setMerchantName(txn.getId().getMerchantid());
                Date date = txn.getTransactioncreateddatetime();
                Timestamp timestamp = new Timestamp(date.getTime());
                bean.setCreatedTime(timestamp);

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
        return bean;
    }

    /// ------------------------ Transaction Void Serch --------------------- /////////////
    public List<TransactionDataBean> getTxnList(String voidOrreversal, TransactionSearchBean searchBean, int max, int first, String orderBy) throws Exception {

        Session session = null;
        long fullcount = 0;

        //initialize, declare variables
        String sql = null; //to store entire sql statement
        List<TransactionDataBean> dataList = new ArrayList<TransactionDataBean>();
        String whereString = "";

        if (voidOrreversal.equals("void")) {
            whereString = this.makeWhereStatementToVoid(searchBean);
        } else if (voidOrreversal.equals("reversal")) {
            whereString = this.makeWhereStatementToReversal(searchBean);
        }

        try {
            session = HibernateInit.sessionFactory.openSession();

            sql = " SELECT t.IPGTRANSACTIONID, m.merchantname, c.DESCRIPTION AS CARDASSOCIATION, t.CARDHOLDERPAN,"
                    + " t.AMOUNT, s.description AS STATUS, t.CREATEDTIME  "
                    + " FROM IPGTRANSACTION t, IPGMERCHANT m, IPGCARDASSOCIATION c,IPGSTATUS s  "
                    + whereString + orderBy;
//                    + whereString + " ORDER BY t.IPGTRANSACTIONID DESC ";

            SQLQuery query = session.createSQLQuery(sql);

            if (searchBean.getTxnId() != null && !searchBean.getTxnId().isEmpty()) {
                query.setParameter("txnid", searchBean.getTxnId());
            }
            if (searchBean.getMerchantName() != null && !searchBean.getMerchantName().isEmpty()) {
                query.setParameter("mername", searchBean.getMerchantName());
            }
            if (searchBean.getCardHolderName() != null && !searchBean.getCardHolderName().isEmpty()) {
                query.setParameter("cardname", searchBean.getCardHolderName());
            }
            if (searchBean.getCardNumber() != null && !searchBean.getCardNumber().isEmpty()) {
                query.setParameter("cardnum", searchBean.getCardNumber().toString());
            }
            if (searchBean.getStatus() != null && !searchBean.getStatus().isEmpty()) {
                query.setParameter("status", searchBean.getStatus());
            }
            if (searchBean.getCardType() != null && !searchBean.getCardType().isEmpty()) {
                query.setParameter("cardtype", searchBean.getCardType());
            }
            if (searchBean.getFromDate() != null && !searchBean.getFromDate().isEmpty()) {
                String sqlDate = this.stringTodate(searchBean.getFromDate());
                query.setParameter("frmdate", sqlDate);
            }
            if (searchBean.getToDate() != null && !searchBean.getToDate().isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d = Common.formatStringtoDate(searchBean.getToDate());
                int da = d.getDate() + 1;
                d.setDate(da);
                String sqlDate = sdf.format(d);
                query.setParameter("todate", sqlDate);
            }

            fullcount = query.list().size();
            
            query.setMaxResults(max);
            query.setFirstResult(first);

            List list = query.list();

            for (Iterator it = list.iterator(); it.hasNext();) {
                Object[] obj = (Object[]) it.next();
                TransactionDataBean bean = new TransactionDataBean();

                bean.setTxnId(obj[0].toString());
                bean.setMerchantName(obj[1].toString());
                bean.setCardType(obj[2].toString());
                bean.setCardNumber(obj[3].toString());
                bean.setAmount(obj[4].toString());
                bean.setStatus(obj[5].toString());

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

    private String makeWhereStatementToVoid(TransactionSearchBean searchBean) throws Exception {
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
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.STATUSCODE ='" + StatusVarList.TXN_COMPLETE_CONFIRMED + "'";
                } else if (searchBean.getTag() == 2) {
                    // Serch Merchant user

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE "
                            + " AND t.MERCHANTID = (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' )"
                            + " AND t.STATUSCODE ='" + StatusVarList.TXN_COMPLETE_CONFIRMED + "'";
                } else if (searchBean.getTag() == 3) {
                    // Serch Head Merchant user No status

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.MERCHANTID IN "
                            + " ( SELECT MERCHANTID FROM IPGMERCHANT WHERE MERCHANTCUSTOMERID = "
                            + " ( SELECT MERCHANTCUSTOMERID FROM IPGHEADMERCHANT WHERE TRANSACTIONINITAIATEDMERCHNTID = "
                            + " (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' ) ) )"
                            + " AND t.STATUSCODE ='" + StatusVarList.TXN_COMPLETE_CONFIRMED + "'";
                }
            } else {
                //user having some search parameters
                //whereString = "  WHERE  ";

                if (searchBean.getTag() == 1) {
                    // Serch Not Merchant user

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.STATUSCODE ='" + StatusVarList.TXN_COMPLETE_CONFIRMED + "' AND";
                } else if (searchBean.getTag() == 2) {
                    // Serch Merchant user

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE "
                            + " AND t.MERCHANTID = (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' )"
                            + " AND t.STATUSCODE ='" + StatusVarList.TXN_COMPLETE_CONFIRMED + "' AND";

                } else if (searchBean.getTag() == 3) {
                    // Serch Head Merchant user No status

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.MERCHANTID IN "
                            + " ( SELECT MERCHANTID FROM IPGMERCHANT WHERE MERCHANTCUSTOMERID = "
                            + " ( SELECT MERCHANTCUSTOMERID FROM IPGHEADMERCHANT WHERE TRANSACTIONINITAIATEDMERCHNTID = "
                            + " (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' ) ) )"
                            + " AND t.STATUSCODE ='" + StatusVarList.TXN_COMPLETE_CONFIRMED + "' AND";
                }
            }

            //for transactionID
            if (searchBean.getTxnId() != null && !searchBean.getTxnId().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.IPGTRANSACTIONID LIKE :txnid ";
                andString = 1;
            }

            //for merchant
            if (searchBean.getMerchantName() != null && !searchBean.getMerchantName().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " m.MERCHANTNAME LIKE :mername ";
                andString = 1;
            }

            //for cardHolder
            if (searchBean.getCardHolderName() != null && !searchBean.getCardHolderName().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.CARDNAME LIKE :cardname ";
                andString = 1;
            }

            //for cardHolder
            if (searchBean.getCardNumber() != null && !searchBean.getCardNumber().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.CARDHOLDERPAN LIKE :cardnum ";
                andString = 1;
            }

            //for status
            if (searchBean.getStatus() != null && !searchBean.getStatus().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " s.STATUSCODE LIKE :status";
                andString = 1;
            }
            //for cardAssociation
            if (searchBean.getCardType() != null && !searchBean.getCardType().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " c.CARDASSOCIATIONCODE LIKE :cardtype ";
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

    private String makeWhereStatementToReversal(TransactionSearchBean searchBean) throws Exception {
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
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.STATUSCODE ='" + StatusVarList.TXN_ENGINEREQUESTSENT + "'";
                } else if (searchBean.getTag() == 2) {
                    // Serch Merchant user

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE "
                            + " AND t.MERCHANTID = (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' )"
                            + " AND t.STATUSCODE ='" + StatusVarList.TXN_ENGINEREQUESTSENT + "'";

                } else if (searchBean.getTag() == 3) {
                    // Serch Head Merchant user No status

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.MERCHANTID IN "
                            + " ( SELECT MERCHANTID FROM IPGMERCHANT WHERE MERCHANTCUSTOMERID = "
                            + " ( SELECT MERCHANTCUSTOMERID FROM IPGHEADMERCHANT WHERE TRANSACTIONINITAIATEDMERCHNTID = "
                            + " (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' ) ) ) "
                            + " AND t.STATUSCODE ='" + StatusVarList.TXN_ENGINEREQUESTSENT + "'";

                }
            } else {
                //user having some search parameters
                //whereString = "  WHERE  ";
                if (searchBean.getTag() == 1) {
                    // Serch Not Merchant user

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.STATUSCODE ='" + StatusVarList.TXN_ENGINEREQUESTSENT + "' AND";
                } else if (searchBean.getTag() == 2) {
                    // Serch Merchant user

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE "
                            + " AND t.MERCHANTID = (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' )"
                            + " AND t.STATUSCODE ='" + StatusVarList.TXN_ENGINEREQUESTSENT + "' AND";

                } else if (searchBean.getTag() == 3) {
                    // Serch Head Merchant user No status

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.MERCHANTID IN "
                            + " ( SELECT MERCHANTID FROM IPGMERCHANT WHERE MERCHANTCUSTOMERID = "
                            + " ( SELECT MERCHANTCUSTOMERID FROM IPGHEADMERCHANT WHERE TRANSACTIONINITAIATEDMERCHNTID = "
                            + " (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' ) ) )"
                            + " AND t.STATUSCODE ='" + StatusVarList.TXN_ENGINEREQUESTSENT + "' AND";

                }
            }

            //for transactionID
            if (searchBean.getTxnId() != null && !searchBean.getTxnId().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.IPGTRANSACTIONID LIKE :txnid ";
                andString = 1;
            }

            //for merchant
            if (searchBean.getMerchantName() != null && !searchBean.getMerchantName().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " m.MERCHANTNAME LIKE :mername ";
                andString = 1;
            }

            //for cardHolder
            if (searchBean.getCardHolderName() != null && !searchBean.getCardHolderName().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.CARDNAME LIKE :cardname ";
                andString = 1;
            }

            //for cardHolder
            if (searchBean.getCardNumber() != null && !searchBean.getCardNumber().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.CARDHOLDERPAN LIKE :cardnum ";
                andString = 1;
            }

            //for status
            if (searchBean.getStatus() != null && !searchBean.getStatus().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " s.STATUSCODE LIKE :status";
                andString = 1;
            }
            //for cardAssociation
            if (searchBean.getCardType() != null && !searchBean.getCardType().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " c.CARDASSOCIATIONCODE LIKE :cardtype ";
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
            DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = userDateFormat.parse(date);
            convertedDate = dateFormatNeeded.format(date1);

        } catch (Exception ex) {
            throw ex;
        }
        return convertedDate;
    }

    public String saveAudit(Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();

            session.save(audit);
            txn.commit();

        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return message;
    }
    ////////////
}
