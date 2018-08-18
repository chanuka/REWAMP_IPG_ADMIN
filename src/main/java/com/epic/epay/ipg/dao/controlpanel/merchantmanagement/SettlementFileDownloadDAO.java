/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.SettlementFileDownloadDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.TransactionDataBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgbatch;
import com.epic.epay.ipg.util.mapping.IpgbatchId;
import com.epic.epay.ipg.util.mapping.Ipgcommonfilepath;
import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgsettlementfile;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.StatusVarList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.crypto.Data;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author nalin
 */
public class SettlementFileDownloadDAO {

    //get merchant list
    public List<SettlementFileDownloadDataBean> getAllMerchantIdList() throws Exception {

        List<SettlementFileDownloadDataBean> midList = new ArrayList<SettlementFileDownloadDataBean>();
        List<Ipgmerchant> list = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgmerchant as m";
            Query query = session.createQuery(sql);
            list = query.list();

            for (Ipgmerchant mchnt : list) {
                SettlementFileDownloadDataBean bean = new SettlementFileDownloadDataBean();
                bean.setMidkey(mchnt.getMerchantid());
                bean.setMidvalue(mchnt.getMerchantid());
                midList.add(bean);
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
        return midList;
    }

    public List<SettlementFileDownloadDataBean> getBatchList() throws Exception {

        List<SettlementFileDownloadDataBean> batchList = new ArrayList<SettlementFileDownloadDataBean>();
        List<Object[]> list = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT b.id.merchantid as mid ,m.merchantname as mname ,b.id.batchnumber as batchnum,"
                    + "s.description as des, b.terminalid as tid "
                    + "FROM Ipgmerchant m,Ipgbatch b, Ipgstatus s "
                    + "where b.id.merchantid= m.merchantid AND b.ipgstatus.statuscode=s.statuscode "
                    + "and b.ipgstatus.statuscode=:statuscode and b.ipgsettlementfile.fileid IS NULL ";

            Query query = session.createQuery(sql);
            query.setParameter("statuscode", StatusVarList.BATCH_SETTLED_STATUS);
            list = query.list();
            long count = list.size();
            for (Object[] ob : list) {
                SettlementFileDownloadDataBean bean = new SettlementFileDownloadDataBean();
                try {
                    bean.setMid(ob[0].toString());
                } catch (NullPointerException npe) {
                    bean.setMid("--");
                }
                
                try {
                    bean.setMerchantname(ob[1].toString());
                } catch (NullPointerException npe) {
                    bean.setMerchantname("--");
                }
                
                try {
                    bean.setBatchId(ob[2].toString());
                } catch (NullPointerException npe) {
                    bean.setBatchId("--");
                }
                
                try {
                    bean.setStatus(ob[3].toString());
                } catch (NullPointerException npe) {
                    bean.setStatus("--");
                }
                
                try {
                    bean.setTid(ob[4].toString());
                } catch (NullPointerException npe) {
                    bean.setTid("--");
                }
                
                bean.setFullCount(count);
                batchList.add(bean);
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
        return batchList;
    }

    public List<SettlementFileDownloadDataBean> getBatchListByMerchant(String merchantId) throws Exception {

        List<SettlementFileDownloadDataBean> batchList = new ArrayList<SettlementFileDownloadDataBean>();
        List<Object[]> list = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT b.id.merchantid as mid ,m.merchantname as mname ,b.id.batchnumber as batchnum,"
                    + "s.description as des, b.terminalid "
                    + "FROM Ipgmerchant m,Ipgbatch b, Ipgstatus s "
                    + "where b.id.merchantid= m.merchantid AND b.ipgstatus.statuscode=s.statuscode "
                    + "and b.ipgstatus.statuscode=:statuscode and  b.id.merchantid = :mid and b.ipgsettlementfile.fileid IS NULL ";

            Query query = session.createQuery(sql);
            query.setParameter("statuscode", StatusVarList.BATCH_SETTLED_STATUS);
            query.setParameter("mid", merchantId);
            list = query.list();
            long count = list.size();
            for (Object[] ob : list) {
                SettlementFileDownloadDataBean bean = new SettlementFileDownloadDataBean();
                
                try {
                    bean.setMid(ob[0].toString());
                } catch (NullPointerException npe) {
                    bean.setMid("--");
                }
                
                try {
                    bean.setMerchantname(ob[1].toString());
                } catch (NullPointerException npe) {
                    bean.setMerchantname("--");
                }
                
                try {
                    bean.setBatchId(ob[2].toString());
                } catch (NullPointerException npe) {
                    bean.setBatchId("--");
                }
                
                try {
                    bean.setStatus(ob[3].toString());
                } catch (NullPointerException npe) {
                    bean.setStatus("--");
                }
                
                try {
                    bean.setTid(ob[4].toString());
                } catch (NullPointerException npe) {
                    bean.setTid("--");
                }

                bean.setFullCount(count);
                batchList.add(bean);
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
        return batchList;
    }

    public List<TransactionDataBean> getAllTxnList() throws Exception {
        List<TransactionDataBean> txnList = new ArrayList<TransactionDataBean>();
        List<Object[]> list = null;
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT T.IPGTRANSACTIONID,T.MERCHANTID,T.CARDHOLDERPAN,T.AMOUNT,"
                    + "T.TRANSACTIONCREATEDDATETIME,T.BATCHNUMBER,T.TERMINALID,T.APPROVALCODE,T.RRN "
                    + "FROM IPGTRANSACTION T,IPGBATCH B "
                    + "WHERE B.BATCHNUMBER = T.BATCHNUMBER AND T.STATUSCODE=:statuscode AND B.STATUS=:status "
                    + "AND B.MERCHANTID = T.MERCHANTID AND B.SETTLEMENTFILE IS NULL";

            Query query = session.createSQLQuery(sql);
            query.setParameter("statuscode", StatusVarList.TXN_SETTLED);
            query.setParameter("status", StatusVarList.BATCH_SETTLED_STATUS);
            list = query.list();

            for (Object[] ob : list) {

                TransactionDataBean bean = new TransactionDataBean();
                
                try {
                    bean.setTxnId(ob[0].toString());
                } catch (NullPointerException npe) {
                    bean.setTxnId("--");
                }
                try {
                    bean.setMid(ob[1].toString());
                } catch (NullPointerException npe) {
                    bean.setMid("--");
                }
                try {
                    bean.setCardNumber(ob[2].toString());
                } catch (NullPointerException npe) {
                    bean.setCardNumber("--");
                }
                try {
                    bean.setAmount(ob[3].toString());
                } catch (NullPointerException npe) {
                    bean.setAmount("--");
                }
                try {
                    bean.setTxnDate(ob[4].toString());
                } catch (NullPointerException npe) {
                    bean.setTxnDate("--");
                }
                try {
                    bean.setBatchId(ob[5].toString());
                } catch (NullPointerException npe) {
                    bean.setBatchId("--");
                }
                try {
                    bean.setTid(ob[6].toString());
                } catch (NullPointerException npe) {
                    bean.setTid("--");
                }
                try {
                    bean.setApprovalCode(ob[7].toString());
                } catch (NullPointerException npe) {
                    bean.setApprovalCode("--");
                }
                try {
                    bean.setRrn(ob[8].toString());
                } catch (NullPointerException npe) {
                    bean.setRrn("--");
                }

                txnList.add(bean);
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
        return txnList;
    }

    public List<TransactionDataBean> getTxnListByMerchant(String merchantId) throws Exception {
        List<TransactionDataBean> txnList = new ArrayList<TransactionDataBean>();
        List<Object[]> list = null;
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT T.IPGTRANSACTIONID, T.MERCHANTID, T.CARDHOLDERPAN, T.AMOUNT,"
                    + " T.TRANSACTIONCREATEDDATETIME, T.BATCHNUMBER, T.TERMINALID, T.APPROVALCODE, T.RRN "
                    + "FROM IPGTRANSACTION T,IPGBATCH B "
                    + "WHERE B.BATCHNUMBER = T.BATCHNUMBER AND T.STATUSCODE=:statuscode "
                    + "AND B.STATUS=:status AND B.MERCHANTID=:merchantId "
                    + "AND B.MERCHANTID = T.MERCHANTID AND B.SETTLEMENTFILE IS NULL";

            Query query = session.createSQLQuery(sql);
            query.setParameter("statuscode", StatusVarList.TXN_SETTLED);
            query.setParameter("status", StatusVarList.BATCH_SETTLED_STATUS);
            query.setParameter("merchantId", merchantId);
            list = query.list();

            for (Object[] ob : list) {

                TransactionDataBean bean = new TransactionDataBean();
                
                try {
                    bean.setTxnId(ob[0].toString());
                } catch (NullPointerException npe) {
                    bean.setTxnId("--");
                }
                try {
                    bean.setMid(ob[1].toString());
                } catch (NullPointerException npe) {
                    bean.setMid("--");
                }
                try {
                    bean.setCardNumber(ob[2].toString());
                } catch (NullPointerException npe) {
                    bean.setCardNumber("--");
                }
                try {
                    bean.setAmount(ob[3].toString());
                } catch (NullPointerException npe) {
                    bean.setAmount("--");
                }
                try {
                    bean.setTxnDate(ob[4].toString());
                } catch (NullPointerException npe) {
                    bean.setTxnDate("--");
                }
                try {
                    bean.setBatchId(ob[5].toString());
                } catch (NullPointerException npe) {
                    bean.setBatchId("--");
                }
                try {
                    bean.setTid(ob[6].toString());
                } catch (NullPointerException npe) {
                    bean.setTid("--");
                }
                try {
                    bean.setApprovalCode(ob[7].toString());
                } catch (NullPointerException npe) {
                    bean.setApprovalCode("--");
                }
                try {
                    
                    if(ob[8]!=null){
                        bean.setRrn(ob[8].toString());
                    }else{
                        bean.setRrn("062217454672");
                    }
                } catch (NullPointerException npe) {
                    bean.setRrn("062217454672");
                }

                txnList.add(bean);
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
        return txnList;
    }

    public String getFilePathBySystemOSType(String osType) throws Exception {

        String filepath = "";
        Ipgcommonfilepath ipgcommonfilepath = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgcommonfilepath as c where c.ostype=:ostype";
            Query query = session.createQuery(sql);
            query.setString("ostype", osType);

            if (query.list().size() > 0) {
                ipgcommonfilepath = (Ipgcommonfilepath) query.list().get(0);

                filepath = ipgcommonfilepath.getSettlementfilepath();
            } else {
                filepath = "notassign";
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
        return filepath;
    }

    public String getMaxSequenceNo() throws Exception {

        String maxSequenceNo = "000";
        List<Object[]> list = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT MAX(SEQUENCENO) FROM IPGSETTLEMENTFILE ";
            Query query = session.createSQLQuery(sql);

            if (query.uniqueResult() != null) {
                maxSequenceNo = String.valueOf((query.uniqueResult()));
            }

//            list = query.list();
//            if (list.size() > 0) {
//                for (Object[] ob : list) {
//
//                    if (ob.length > 0) {
//                        maxSequenceNo = ob[0].toString();
//                    } else {
//
//                        maxSequenceNo = "000";
//                    }
//
//                }
//            } else {
//                maxSequenceNo = "000";
//            }
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
        return maxSequenceNo;
    }

    public String insertSettlementFile(String fileId, String sequenceNo, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";
        try {

            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgsettlementfile) session.get(Ipgsettlementfile.class, fileId.trim()) == null) {
                txn = session.beginTransaction();

                Ipgsettlementfile settleFile = new Ipgsettlementfile();

                settleFile.setFileid(fileId);
                settleFile.setSequenceno(sequenceNo);

                Ipgstatus status = new Ipgstatus(StatusVarList.NO_STATUS);
                settleFile.setIpgstatus(status);

                settleFile.setLastupdatedtime(sysDate);
                settleFile.setLastupdateduser(audit.getLastupdateduser());
                settleFile.setCreatedtime(sysDate);

                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(settleFile);

                txn.commit();
            } else {
                message = MessageVarList.COMMON_ALREADY_EXISTS;
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

    public String updateBatchStatus(String batchId, String mid, String fileId, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {

            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();

            Ipgbatch b = (Ipgbatch) session.get(Ipgbatch.class, new IpgbatchId(mid, batchId));

//            String sql = "from Ipgbatch as b where b.IpgbatchId.batchnumber=:batchId and b.IpgbatchId.merchantid=:mid";
//            Query query = session.createQuery(sql);
//            query.setString("batchId", batchId);
//            query.setString("mid", mid);
            Date sysDate = CommonDAO.getSystemDate(session);

//            Ipgbatch b =(Ipgbatch) query.list().get(0);
            if (b != null) {

                Ipgsettlementfile settleFile = new Ipgsettlementfile(StatusVarList.NO_STATUS);
                b.setIpgsettlementfile(settleFile);

                b.getIpgsettlementfile().setFileid(fileId);
                b.setLastupdateduser(audit.getLastupdateduser());
                b.setLastupdatedtime(sysDate);

                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.update(b);

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

    public String updateSettelmentFileStatus(String fileId, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {

            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgsettlementfile s = (Ipgsettlementfile) session.get(Ipgsettlementfile.class, fileId);

            if (s != null) {

                Ipgstatus status = new Ipgstatus(StatusVarList.YES_STATUS);
                s.setIpgstatus(status);

                s.setLastupdateduser(audit.getLastupdateduser());
                s.setLastupdatedtime(sysDate);

                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.update(s);

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
}
