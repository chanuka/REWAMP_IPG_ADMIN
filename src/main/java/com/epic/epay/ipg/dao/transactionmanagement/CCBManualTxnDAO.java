/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.ManualTransactionDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.ManualTransactionInputBean;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgccbatransaction;
import com.epic.epay.ipg.util.mapping.Ipgcommonconfig;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.StatusVarList;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
import org.hibernate.Transaction;

/**
 *
 * @author nalin
 */
public class CCBManualTxnDAO {

    public List<ManualTransactionDataBean> getSearchList(ManualTransactionInputBean searchBean, int max, int first, String orderBy) throws Exception {

        Session session = null;

        //initialize, declare variables
        String sql = null; //to store entire sql statement
        List<ManualTransactionDataBean> dataList = new ArrayList<ManualTransactionDataBean>();
        String whereString = "";
        long count = 0;

        whereString = this.makeWhereStatement(searchBean);

        try {
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = " SELECT count(*) "
                    + " FROM IPGCCBATRANSACTION BT, IPGTRANSACTION T, IPGCURRENCY CC,IPGCCBAFILE F  "
                    + whereString + orderBy;

            Query queryCount = session.createSQLQuery(sqlCount);
            queryCount = setDatesToQuery(queryCount, searchBean, session);
            List<BigDecimal> listCount = queryCount.list();
            count = listCount.get(0).longValue();

            sql = " SELECT BT.ID ,BT.CARDNUMBER,BT.NICNO, T.MERCHANTID,T.CARDNAME,CC.DESCRIPTION AS CURRENCYDES"
                    + " FROM IPGCCBATRANSACTION BT, IPGTRANSACTION T, IPGCURRENCY CC,IPGCCBAFILE F  "
                    + whereString + orderBy;

            Query query = session.createSQLQuery(sql);
            query = setDatesToQuery(query, searchBean, session);

            query.setMaxResults(max);
            query.setFirstResult(first);

            List list = query.list();

            for (Iterator it = list.iterator(); it.hasNext();) {
                Object[] obj = (Object[]) it.next();
                ManualTransactionDataBean bean = new ManualTransactionDataBean();

                bean.setCcbaTransactionId(obj[0].toString());
                bean.setCardnumber(obj[1].toString());
                bean.setNicNo(obj[2].toString());
                bean.setMerchantid(obj[3].toString());
                bean.setMerchantname(obj[4].toString());
                bean.setCurrency(obj[5].toString());

                dataList.add(bean);

            }
            if (!dataList.isEmpty()) {
                dataList.get(0).setFullCount(count);
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

    private String makeWhereStatement(ManualTransactionInputBean searchBean) throws Exception {
        //initialize, declare variables
        String whereString = null;
        int andString = 0;
        try {

            if ((searchBean.getFromDate() == null || searchBean.getFromDate().toString().isEmpty())
                    && (searchBean.getToDate() == null || searchBean.getToDate().toString().isEmpty())
                    && (searchBean.getCardNumber() == null || searchBean.getCardNumber().isEmpty())
                    && (searchBean.getNic() == null || searchBean.getNic().isEmpty())
                    && (searchBean.getMerchantId() == null || searchBean.getMerchantId().isEmpty())
                    && (searchBean.getCurrency() == null || searchBean.getCurrency().isEmpty())) {
                //user doesnt have search parameters
                //whereString = "    ";

                // Serch Head Merchant user No status
                whereString = " WHERE BT.TRANSACTIONID = T.IPGTRANSACTIONID AND BT.CURRENCY = CC.CURRENCYISOCODE "
                        + " AND BT.FILEID = F.FILEID AND T.STATUSCODE = '" + StatusVarList.TXN_INITIATED + "' "
                        + " AND F.TRANSACTIONCATEGORY  = '" + StatusVarList.BATCH_TRANSACTION_TYPE_MANUAL + "' ";

            } else {
                //user having some search parameters
                //whereString = "  WHERE  ";

                whereString = " WHERE BT.TRANSACTIONID = T.IPGTRANSACTIONID AND BT.CURRENCY = CC.CURRENCYISOCODE "
                        + " AND BT.FILEID = F.FILEID AND T.STATUSCODE = '" + StatusVarList.TXN_INITIATED + "' "
                        + " AND F.TRANSACTIONCATEGORY  = '" + StatusVarList.BATCH_TRANSACTION_TYPE_MANUAL + "' AND";

            }

            //for transactionID
            if (searchBean.getCardNumber() != null && !searchBean.getCardNumber().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " BT.CARDNUMBER LIKE '" + searchBean.getCardNumber() + "%'";
                andString = 1;
            }

            //for merchant
            if (searchBean.getNic() != null && !searchBean.getNic().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " BT.NICNO LIKE '" + searchBean.getNic() + "%'";
                andString = 1;
            }

            //for cardHolder
            if (searchBean.getMerchantId() != null && !searchBean.getMerchantId().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " T.MERCHANTID LIKE '" + searchBean.getMerchantId() + "%'";
                andString = 1;
            }

            //for cardHolder
            if (searchBean.getCurrency() != null && !searchBean.getCurrency().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " BT.CURRENCY = '" + searchBean.getCurrency() + "'";
                andString = 1;
            }

            if (null != searchBean.getFromDate() && !searchBean.getFromDate().equals("")) {
                if (andString == 1) {
                    whereString += " AND ";
                }

                whereString += " TO_DATE(:frmdate,'yyyy-MM-dd') <= BT.NEXTDATE ";
                andString = 1;
            }
            if (null != searchBean.getToDate() && !searchBean.getToDate().equals("")) {

                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " BT.NEXTDATE <= TO_DATE(:todate,'yyyy-MM-dd') ";
                andString = 1;
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

    public ManualTransactionDataBean getProcessBean(String ccbaTransactionId) throws Exception {
        Session session = null;
        ManualTransactionDataBean dataBean = new ManualTransactionDataBean();
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT BT.TRANSACTIONID , T.MERCHANTID , T.TRANSACTIONCREATEDDATETIME "
                    + " FROM IPGCCBATRANSACTION BT,IPGTRANSACTION T "
                    + " WHERE BT.TRANSACTIONID = T.IPGTRANSACTIONID AND BT.ID =:ccbaTransactionId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("ccbaTransactionId", ccbaTransactionId);

            List list = query.list();
            for (Iterator it = list.iterator(); it.hasNext();) {
                Object[] obj = (Object[]) it.next();
                //ManualTransactionDataBean bean = new ManualTransactionDataBean();

                dataBean.setTransactionId(obj[0].toString());
                dataBean.setMerchantid(obj[1].toString());

                // Timestamp timestamp = new Timestamp(((java.sql.Date)obj[2]).getTime());
                final String OLD_FORMAT = "dd-MM-yy";
                final String NEW_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
                DateFormat formatter = new SimpleDateFormat(OLD_FORMAT);
                Date d = formatter.parse(obj[2].toString());
                ((SimpleDateFormat) formatter).applyPattern(NEW_FORMAT);
                String newDateString = formatter.format(d);
                Timestamp ts = Timestamp.valueOf(newDateString);
                dataBean.setTxnDateTime(ts);
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
        return dataBean;
    }

    public String updateIPGCCBATransaction(ManualTransactionDataBean bean) throws Exception {

        Ipgccbatransaction tran = null;
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgccbatransaction as t where t.transactionid=:txnid";
            Query query = session.createQuery(sql);
            query.setString("txnid", bean.getCcbaTransactionId().trim());
            tran = (Ipgccbatransaction) query.list().get(0);

            txn = session.beginTransaction();
            //Date sysDate = CommonDAO.getSystemDate(session);

            if (tran != null) {

                tran.setResponsecode(bean.getResponseCode());

                session.update(tran);

                txn.commit();
            } else {
                message = MessageVarList.COMMON_NOT_EXISTS;
            }
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
    
    public Query setDatesToQuery(Query sql, ManualTransactionInputBean inputBean, Session session) throws Exception {

        if (inputBean.getFromDate() != null && !inputBean.getFromDate().isEmpty()) {
            String sqlDate = this.stringTodate(inputBean.getFromDate());
            sql.setParameter("frmdate", sqlDate);
        }
        if (inputBean.getToDate() != null && !inputBean.getToDate().isEmpty()) {
            String sqlDate = this.stringTodate(inputBean.getToDate());
            sql.setParameter("todate", sqlDate);
        }

        return sql;
    }
}
