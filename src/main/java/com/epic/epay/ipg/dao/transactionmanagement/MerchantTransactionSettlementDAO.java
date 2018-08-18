/**
 *
 */
package com.epic.epay.ipg.dao.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.MerchantTracsactionSettlementDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.MerchantTracsactionSettlementInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgbatch;
import com.epic.epay.ipg.util.mapping.IpgbatchId;
import com.epic.epay.ipg.util.mapping.Ipgheadmerchant;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipguserrole;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.StatusVarList;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author Asitha Liyanawaduge
 *
 * 04/12/2013
 */
public class MerchantTransactionSettlementDAO {

    /**
     * @param inputBean
     * @param rows
     * @param from
     * @param sortIndex
     * @param sortOrder
     * @return
     * @throws Exception
     */
//    public List<MerchantTracsactionSettlementDataBean> getSearchList(
//            MerchantTracsactionSettlementInputBean inputBean, int rows,
//            int from, String sortIndex, String sortOrder) throws Exception {
//
//        List<Epictxnrequests> searchList = null;
//        List<MerchantTracsactionSettlementDataBean> dataBeanList = null;
//        Session session = null;
//        long fullCount = 0;
//        if ("".equals(sortIndex.trim())) {
//            sortIndex = null;
//        }
//        try {
//            session = HibernateInit.sessionFactory.openSession();
//            session.beginTransaction();
//            Criteria criteria = session.createCriteria(Ipgenginerequest.class);
//
//            if (sortIndex != null && sortOrder != null) {
//                if (sortOrder.equals("asc")) {
//                    criteria.addOrder(Order.asc(sortIndex));
//                }
//                if (sortOrder.equals("desc")) {
//                    criteria.addOrder(Order.desc(sortIndex));
//                }
//
//            }
//
//            if (inputBean.getHeadMerchantId() != null
//                    && !inputBean.getHeadMerchantId().isEmpty()) {
//                criteria.add(Restrictions.ilike(
//                        "ipgmerchant.ipgheadmerchant.merchantcustomerid",
//                        inputBean.getHeadMerchantId().trim(),
//                        MatchMode.ANYWHERE));
//            }
//
//            if (inputBean.getHeadMerchantName() != null
//                    && !inputBean.getHeadMerchantName().isEmpty()) {
//                criteria.add(Restrictions.ilike(
//                        "ipgmerchant.ipgheadmerchant.merchantname", inputBean
//                        .getHeadMerchantName().trim(),
//                        MatchMode.ANYWHERE));
//            }
//
//            if (inputBean.getMerchantId() != null
//                    && !inputBean.getMerchantId().isEmpty()) {
//                criteria.add(Restrictions.ilike("merchantid",
//                        inputBean.getMerchantId().trim(), MatchMode.ANYWHERE));
//            }
//
//            if (inputBean.getMerchantName() != null
//                    && !inputBean.getMerchantName().isEmpty()) {
//                criteria.add(Restrictions.ilike("ipgmerchant.merchantname",
//                        inputBean.getMerchantName().trim(), MatchMode.ANYWHERE));
//            }
//
//            if (inputBean.getTerminalId() != null
//                    && !inputBean.getTerminalId().isEmpty()) {
//                criteria.add(Restrictions.ilike("tid", inputBean
//                        .getTerminalId().trim(), MatchMode.ANYWHERE));
//            }
//
//            if (inputBean.getStatus() != null
//                    && !inputBean.getStatus().isEmpty()) {
//                criteria.add(Restrictions.eq("statuscode",
//                        inputBean.getStatus()));
//            }
//            //
//            // ProjectionList projList = Projections.projectionList();
//            // projList.add(Projections
//            // .count("ipgtransaction.id.ipgtransactionid"));
//            // projList.add(Projections.sum("amount"));
//            // // projList.add(Projections
//            // //
//            // .groupProperty("ipgmerchant.ipgheadmerchant.merchantcustomerid,ipgmerchant.merchantid,ipgmerchant.merchantname,ipgcurrency.description,tid,ipgbatch.id.batchnumber,ipgstatusByStatus.statuscode"));
//            // projList.add(Projections
//            // .groupProperty("txncode"));
//            // criteria.setProjection(projList);
//
//            fullCount = criteria.list().size();
//
//            criteria.setFirstResult(from);
//            criteria.setMaxResults(rows);
//
//            searchList = criteria.list();
//            dataBeanList = new ArrayList<MerchantTracsactionSettlementDataBean>();
//
//            for (Epictxnrequests m : searchList) {
//                dataBeanList.add(new MerchantTracsactionSettlementDataBean(m, fullCount));
//            }
//
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            try {
//                session.flush();
//                session.close();
//            } catch (Exception e) {
//                throw e;
//            }
//        }
//        return dataBeanList;
//    }
    public String getCurrentUserMerchantId(String userName) throws Exception {
        String merchantId = "";
        Ipgsystemuser ipgsystemuser = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgsystemuser as s WHERE s.username=:username ";
            Query query = session.createQuery(sql);
            query.setParameter("username", userName);
            if (query.list().size() > 0) {
                ipgsystemuser = (Ipgsystemuser) query.list().get(0);
                merchantId = ipgsystemuser.getMerchantid();
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
        return merchantId;
    }

    public String getHeadMerchantTxnInitStatus(String merchantid) throws Exception {
        Ipgheadmerchant ipgheadmerchant;
        String txnInitStatus = "";
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgheadmerchant as m WHERE m.transactioninitaiatedmerchntid=:merchantid";
            Query query = session.createQuery(sql);
            query.setParameter("merchantid", merchantid);

            if (query.list().size() > 0) {
                ipgheadmerchant = (Ipgheadmerchant) query.list().get(0);
                txnInitStatus = ipgheadmerchant.getIpgstatusByTransactioninitaiatedstatus().getStatuscode();
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
        return txnInitStatus;
    }

    public boolean isMerchantExist(String merchantid) throws Exception {
        boolean yes = false;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgmerchant as m where m.merchantid=:merchantid";
            Query query = session.createQuery(sql);
            query.setParameter("merchantid", merchantid);

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

    public List<MerchantTracsactionSettlementDataBean> searchMerchantTransactions(MerchantTracsactionSettlementInputBean bean, String mId, String flag, int rows,
            int from) throws SQLException, Exception {

        Session session = null;
        List<MerchantTracsactionSettlementDataBean> searchResultList = new ArrayList<MerchantTracsactionSettlementDataBean>();
        List<Object[]> serchList = new ArrayList<Object[]>();

        try {
            session = HibernateInit.sessionFactory.openSession();
            String query = "SELECT IHM.MERCHANTCUSTOMERID AS hmerchantid,IHM.MERCHANTNAME "
                    + "AS merchant_name ,ET.MERCHANTID AS merchant_id,IM.MERCHANTNAME as mname,IC.DESCRIPTION "
                    + "AS currencydes,ET.BATCHNO as batid,ET.TID AS tid,IB.STATUS as stdes,ST.DESCRIPTION AS STATUSDES,COUNT(ET.IPGTRANSACTIONID) "
                    + "AS TRANSACTIONCOUNT,SUM(ET.TRANSACTIONAMOUNT) AS TRANSACTIONAMOUNT FROM "
                    + "IPGENGINEREQUEST ET,IPGMERCHANT IM,IPGHEADMERCHANT IHM,IPGCURRENCY IC,"
                    + "IPGBATCH IB,IPGSTATUS ST WHERE IM.MERCHANTID = ET.MERCHANTID "
                    + "AND IM.MERCHANTCUSTOMERID = IHM.MERCHANTCUSTOMERID AND "
                    + "IC.CURRENCYISOCODE = ET.CURRENCYCODE AND IB.BATCHNUMBER = ET.BATCHNO "
                    + "AND IB.MERCHANTID = ET.MERCHANTID AND ST.STATUSCODE = IB.STATUS";

            String condition = "";

            if (!flag.contentEquals("") && flag.equals("YES")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "IHM.MERCHANTCUSTOMERID = ( SELECT MERCHANTCUSTOMERID FROM IPGHEADMERCHANT WHERE TRANSACTIONINITAIATEDMERCHNTID = '" + mId + "' ) ";
            }
            if (!flag.contentEquals("") && flag.equals("NO")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ET.MERCHANTID = '" + mId + "'";
            }

            final String headMerchant = bean.getHeadMerchantId();
            final String headMerchantName = bean.getHeadMerchantName();
            final String merchantId = bean.getMerchantId();
            final String merchantName = bean.getMerchantName();
            final String terminalId = bean.getTerminalId();
            final String batchStatus = bean.getStatus();

            if (!headMerchant.contentEquals("") || headMerchant.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "IHM.MERCHANTCUSTOMERID LIKE '%" + headMerchant + "%'";
            }
            if (!headMerchantName.contentEquals("") || headMerchantName.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(IHM.MERCHANTNAME) LIKE '%" + headMerchantName.toUpperCase() + "%'";
            }
            if (!merchantId.contentEquals("") || merchantId.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ET.MERCHANTID LIKE '%" + merchantId + "%'";
            }
            if (!merchantName.contentEquals("") || merchantName.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(IM.MERCHANTNAME) LIKE '%" + merchantName.toUpperCase() + "%'";
            }
            if (!terminalId.contentEquals("") || terminalId.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "ET.TID LIKE '%" + terminalId + "%'";
            }
            if (!batchStatus.contentEquals("") || batchStatus.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " IB.STATUS = '" + batchStatus + "' ";
            }
            if (!condition.equals("")) {
                query += " AND " + condition + " GROUP BY IHM.MERCHANTCUSTOMERID,"
                        + "IHM.MERCHANTNAME,ET.MERCHANTID,IM.MERCHANTNAME,IC.DESCRIPTION,ET.BATCHNO,"
                        + "ET.TID,IB.STATUS,ST.DESCRIPTION ORDER BY IHM.MERCHANTCUSTOMERID DESC ";
            } else {

                query += " GROUP BY IHM.MERCHANTCUSTOMERID,"
                        + "IHM.MERCHANTNAME,ET.MID,IM.MERCHANTNAME,IC.DESCRIPTION,ET.BATCHNO,"
                        + "ET.TID,IB.STATUS,ST.DESCRIPTION ORDER BY IHM.MERCHANTCUSTOMERID DESC ";
            }
            System.out.println("------"+query);
            SQLQuery sql = session.createSQLQuery(query);
            sql.setFirstResult(from);
            sql.setMaxResults(rows);
            serchList = sql.list();

            for (Object[] obj : serchList) {

                MerchantTracsactionSettlementDataBean rsBean = new MerchantTracsactionSettlementDataBean();

                rsBean.setHeadmerchant(obj[0].toString());
                rsBean.setMerchantname(obj[1].toString());
                rsBean.setMerchantid(obj[2].toString());
                rsBean.setMerchantname(obj[3].toString());
                rsBean.setCurrency(obj[4].toString());
                rsBean.setBatchid(obj[5].toString());
                rsBean.setTerminalid(obj[6].toString());
                rsBean.setTxncount(obj[9].toString());
                rsBean.setTxnamount(String.format("%1$,.2f", Double.parseDouble(obj[10].toString())));
                rsBean.setStatus(obj[7].toString());

                String key = rsBean.getMerchantid() + "|" + rsBean.getTerminalid() + "|" + rsBean.getBatchid();
                rsBean.setKey(key);

                searchResultList.add(rsBean);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return searchResultList;
    }

    public MerchantTracsactionSettlementDataBean getPrimaryTransactionDetails(String merchantid) throws Exception {
        Object[] obj;
        MerchantTracsactionSettlementDataBean bean = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            //   String sql = "from Ipgheadmerchant as m WHERE m.merchantid=:merchantid";

            String sql = "SELECT EH.MERCHANTNAME AS HEADMERCHANTNAME,EH.MERCHANTCUSTOMERID "
                    + "AS HEADMERCHANTID,EM.MERCHANTID,EM.MERCHANTNAME FROM IPGHEADMERCHANT EH,"
                    + "IPGMERCHANT EM WHERE EM.MERCHANTID=:merchantid AND EM.MERCHANTCUSTOMERID=EH.MERCHANTCUSTOMERID";

            SQLQuery query = session.createSQLQuery(sql);
            query.setString("merchantid", merchantid);

            if (query.list().size() > 0) {
                bean = new MerchantTracsactionSettlementDataBean();
                obj = (Object[]) query.list().get(0);
                bean.setHeadmerchant(obj[0].toString());
                bean.setHeadmerchantid(obj[1].toString());
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

    public List<MerchantTracsactionSettlementDataBean> getAllTransactionDetails(String merchantId, String terminalId, String batchId, int max, int first, String orderBy) throws Exception {
        Session session = null;
        List<MerchantTracsactionSettlementDataBean> merchantTransactionDetailList = null;
        List<Object[]> list = new ArrayList<Object[]>();

        try {
            session = HibernateInit.sessionFactory.openSession();
            String query = "SELECT ET.IPGTRANSACTIONID,IC.DESCRIPTION AS CURRENCY,"
                    + "ET.TRANSACTIONAMOUNT,ET.TRANSACTIONINITIATIONDATETIME,ET.CARDNUMBER,ST.DESCRIPTION AS STATUS "
                    + "FROM IPGENGINEREQUEST ET,IPGCURRENCY IC,IPGSTATUS ST WHERE "
                    + "IC.CURRENCYISOCODE = ET.CURRENCYCODE AND ST.STATUSCODE = ET.STATUSCODE "
                    + "AND ET.MERCHANTID=:mid  AND ET.TID=:tid AND ET.BATCHNO=:batchid " + orderBy;

            SQLQuery sql = session.createSQLQuery(query);
            sql.setString("mid", merchantId);
            sql.setString("tid", terminalId);
            sql.setString("batchid", batchId);
            sql.setMaxResults(max);
            sql.setFirstResult(first);
            list = sql.list();

            merchantTransactionDetailList = new ArrayList<MerchantTracsactionSettlementDataBean>();

            for (Object[] obj : list) {
                MerchantTracsactionSettlementDataBean bean = new MerchantTracsactionSettlementDataBean();

                bean.setTxnid(obj[0].toString());
               // bean.setTxnname(obj[1].toString());
                bean.setCurrency(obj[1].toString());
                bean.setTxnamount(String.format("%1$,.2f", Double.parseDouble(obj[2].toString())));

                String dateTime = obj[3].toString();
                String formatedDateTime = dateTime.substring(0, 2) + "-" + dateTime.substring(2, 4) + " " + dateTime.substring(4, 6) + ":" + dateTime.substring(6, 8) + ":" + dateTime.substring(8, 10);

                bean.setTxndatetime(formatedDateTime);
                bean.setCardNumber(obj[4].toString());
               // bean.setNic(obj[5].toString());
                bean.setStatus(obj[5].toString());

                merchantTransactionDetailList.add(bean);
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
        return merchantTransactionDetailList;
    }

    public String getDualAuthUserRole(String userName) throws Exception {
        Ipguserrole iDualauthuserrole;
        String userrolecode = "";
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select u.ipguserroleByDualauthuserrole from Ipgsystemuser as u where u.username=:username";
            Query query = session.createQuery(sql);
            query.setParameter("username", userName);

            if (query.list().size() > 0) {
                iDualauthuserrole = (Ipguserrole) query.list().get(0);
                userrolecode = iDualauthuserrole.getUserrolecode();
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
        return userrolecode;
    }

    public boolean checkDualAuthorization(String dualAuthUserrole, String userName, String password) throws Exception {
        Session session = null;
        boolean isAuthenticate = false;
        List<Ipgsystemuser> list = null;
        try {
            String query = "from Ipgsystemuser as u where u.username=:username and u.password=:password and u.ipguserroleByUserrolecode.userrolecode=:userrolecode";
            session = HibernateInit.sessionFactory.openSession();
            Query sql = session.createQuery(query);
            sql.setString("username", userName);
            sql.setString("password", password);
            sql.setString("userrolecode", dualAuthUserrole);
            list = sql.list();

            if (list.size() > 0) {
                isAuthenticate = true;
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
        return isAuthenticate;
    }

    public void settleBatchNumber(String mid, String batchId, Ipgsystemaudit sysaudit) throws Exception {

        Ipgbatch ipgbatch = null;
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgbatch as u where u.id.merchantid=:mid and u.id.batchnumber=:batchId";
            Query query = session.createQuery(sql);
            query.setString("mid", mid);
            query.setString("batchId", batchId);

            ipgbatch = (Ipgbatch) query.list().get(0);

            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            if (ipgbatch != null) {

                Ipgstatus st = new Ipgstatus();
                st.setStatuscode(StatusVarList.BATCH_SETTLED_STATUS);
                ipgbatch.setIpgstatus(st);

                ipgbatch.setLastupdatedtime(sysDate);
                // add new batch
                int nextbatchId = (Integer.parseInt(batchId) + 1);

                Ipgbatch newIpgbatch = new Ipgbatch();

                IpgbatchId ipgbatchId = new IpgbatchId();
                ipgbatchId.setBatchnumber(String.format("%06d", nextbatchId));
                ipgbatchId.setMerchantid(mid);
                newIpgbatch.setId(ipgbatchId);

                Ipgstatus stnew = new Ipgstatus();
                stnew.setStatuscode(StatusVarList.BATCH_OPEN_STATUS);
                newIpgbatch.setIpgstatus(stnew);

                newIpgbatch.setLastupdateduser(sysaudit.getLastupdateduser());

                session.update(ipgbatch);
                session.save(newIpgbatch);
                session.save(sysaudit);

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

    }

}
