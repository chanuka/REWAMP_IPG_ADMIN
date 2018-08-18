/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.AutoFileBean;
import com.epic.epay.ipg.bean.transactionmanagement.InstantFileBean;
import com.epic.epay.ipg.bean.transactionmanagement.ManualFileBean;
import com.epic.epay.ipg.bean.transactionmanagement.TxnBatchUploadDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.TxnBatchUploadInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgccbafile;
import com.epic.epay.ipg.util.mapping.Ipgcommonfilepath;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.StatusCategoryVarList;
import com.epic.epay.ipg.util.varlist.StatusVarList;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author asela
 */
public class TxnBatchUploadDAO {

    //get card Type List list
    public boolean isFileExist(String filename, String uploadStatus) throws Exception {
        boolean status = false;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT COUNT(*) AS COUNTFILE FROM IPGCCBAFILE WHERE STATUS IN ('" + uploadStatus + "') "
                    + " AND FILENAME =:filename";
            SQLQuery query = session.createSQLQuery(sql);
            query.setString("filename", filename);

            Object ob = query.list().get(0);
            int count = Integer.parseInt(ob.toString());
            if (count > 0) {
                status = true;
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
        return status;
    }

    // get status list 
    public List<TxnBatchUploadDataBean> getStatusListWithoutComp() throws Exception {

        List<TxnBatchUploadDataBean> statusList = new ArrayList<TxnBatchUploadDataBean>();
        List<Ipgstatus> list = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgstatus as u where u.statuscode!=:statuscode and u.ipgstatuscategory.statuscategorycode=:statuscategorycode ";
            Query query = session.createQuery(sql);
            query.setString("statuscode", StatusVarList.BATCH_FILE_UPLOAD_COMPLETE);
            query.setString("statuscategorycode", StatusCategoryVarList.FILE_UPLOAD_STATUS);

            list = query.list();
            for (Ipgstatus ob : list) {
                TxnBatchUploadDataBean bean = new TxnBatchUploadDataBean();
                bean.setStatuscode(ob.getStatuscode());
                statusList.add(bean);
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
        return statusList;
    }

    public String insertTxnBatchUploadData(TxnBatchUploadInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

           // if ((Ipgccbafile) session.get(Ipgccbafile.class, inputBean.getFileid().trim()) == null) {
                txn = session.beginTransaction();

                Ipgccbafile ipgccbafile = new Ipgccbafile();

                ipgccbafile.setFilename(inputBean.getFilename());
                ipgccbafile.setTransactioncategory(inputBean.getTxncategory());
                ipgccbafile.setRemarks("Batch file upload initiated");

                Ipgstatus st = new Ipgstatus();
                st.setStatuscode(StatusVarList.BATCH_FILE_UPLOAD_INITIATE);
                ipgccbafile.setIpgstatus(st);

                ipgccbafile.setCreatetime(sysDate);
                ipgccbafile.setLastupdateddate(sysDate);
                ipgccbafile.setStarttime(sysDate);
                ipgccbafile.setEndtime(sysDate);
                ipgccbafile.setLastupdateduser(audit.getLastupdateduser());

                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(ipgccbafile);

                txn.commit();
           // } else {
          //      message = MessageVarList.COMMON_ALREADY_EXISTS;
          //  }

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

    public java.lang.String updateTxnBatchUploadData(TxnBatchUploadInputBean InputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgccbafile u = (Ipgccbafile) session.get(Ipgccbafile.class, new BigDecimal(InputBean.getFileid().trim()));

            if (u != null) {

                u.setRemarks("Batch file upload completed");

                Ipgstatus status = new Ipgstatus();
                status.setStatuscode(StatusVarList.BATCH_FILE_UPLOAD_COMPLETE);
                u.setIpgstatus(status);

                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.update(u);

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

    
    public java.lang.String updateTxnBatchUploadErrorData(TxnBatchUploadInputBean InputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgccbafile u = (Ipgccbafile) session.get(Ipgccbafile.class, new BigDecimal(InputBean.getFileid().trim()));

            if (u != null) {

                u.setRemarks("Batch file upload error");

                Ipgstatus status = new Ipgstatus();
                status.setStatuscode(StatusVarList.BATCH_FILE_UPLOAD_ERROR);
                u.setIpgstatus(status);

                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.update(u);

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
    
    
    public List<TxnBatchUploadDataBean> getUploadedFileList(int max, int first, String orderBy) throws Exception {

        List<TxnBatchUploadDataBean> settlementList = new ArrayList<TxnBatchUploadDataBean>();
        List<Object[]> list = null;
        List<Object[]> list1 = null;        
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            //String sql = "SELECT b.id.merchantid as mid ,m.merchantname as mname ,b.id.batchnumber as batchnum,s.description as des FROM Ipgmerchant m,Ipgbatch b, Ipgstatus s where b.id.merchantid= m.merchantid AND b.ipgstatus.statuscode=s.statuscode ";
            String sql = "SELECT F.FILEID as fid,F.FILENAME as fname ,F.TRANSACTIONCATEGORY as txncategory,S1.DESCRIPTION AS FILETYPE , S2.DESCRIPTION AS STATUSDES , F.STARTTIME as fstime, F.ENDTIME as endtime"
                    + " FROM IPGCCBAFILE F,IPGSTATUS S1,IPGSTATUS S2 "
                    + " WHERE F.TRANSACTIONCATEGORY = S1.STATUSCODE AND F.STATUS = S2.STATUSCODE " + orderBy;
            Query query = session.createSQLQuery(sql);
            Query query1 = session.createSQLQuery(sql);
            
            query.setMaxResults(max);
            query.setFirstResult(first);
            list = query.list();
            list1 = query1.list();
            long count = list1.size();
            for (Object[] ob : list) {
                TxnBatchUploadDataBean bean = new TxnBatchUploadDataBean();
                bean.setFileid(ob[0].toString());
                bean.setFilename(ob[1].toString());
                bean.setTxntypecode(ob[2].toString());
                bean.setFiletype(ob[3].toString());
                bean.setStatus(ob[4].toString());
                bean.setFullCount(count);
                settlementList.add(bean);
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
        return settlementList;
    }

     public List<TxnBatchUploadDataBean> getUploadedFileList() throws Exception {

        List<TxnBatchUploadDataBean> settlementList = new ArrayList<TxnBatchUploadDataBean>();
        List<Object[]> list = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            //String sql = "SELECT b.id.merchantid as mid ,m.merchantname as mname ,b.id.batchnumber as batchnum,s.description as des FROM Ipgmerchant m,Ipgbatch b, Ipgstatus s where b.id.merchantid= m.merchantid AND b.ipgstatus.statuscode=s.statuscode ";
            String sql = "SELECT F.FILEID as fid,F.FILENAME as fname ,F.TRANSACTIONCATEGORY as txncategory,S1.DESCRIPTION AS FILETYPE , S2.DESCRIPTION AS STATUSDES , F.STARTTIME as fstime, F.ENDTIME as endtime"
                    + " FROM IPGCCBAFILE F,IPGSTATUS S1,IPGSTATUS S2 "
                    + " WHERE F.TRANSACTIONCATEGORY = S1.STATUSCODE AND F.STATUS = S2.STATUSCODE " ;
            Query query = session.createSQLQuery(sql);
            list = query.list();
            long count = list.size();
            for (Object[] ob : list) {
                TxnBatchUploadDataBean bean = new TxnBatchUploadDataBean();
                bean.setFileid(ob[0].toString());
                bean.setFilename(ob[1].toString());
                bean.setTxntypecode(ob[2].toString());
                bean.setFiletype(ob[3].toString());
                bean.setStatus(ob[4].toString());
                bean.setFullCount(count);
                settlementList.add(bean);
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
        return settlementList;
    }   
    
    public String getFileIdByFileName(String fname) throws Exception {

        String fileid = "";
        Ipgccbafile ipgccbafile = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgccbafile as b where b.filename=:fname";
            Query query = session.createQuery(sql);
            query.setString("fname", fname);

            if (query.list().size() > 0) {
                ipgccbafile = (Ipgccbafile) query.list().get(0);

                fileid = ipgccbafile.getFileid().toString();
            } else {
                fileid = "notfound";
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
        return fileid;
    }

    public int insertInstantTransactionData(List<InstantFileBean> beanlist) throws Exception {

        Session session = null;
        Transaction txn = null;
        int val = 0;
        for (InstantFileBean bean : beanlist) {
            try {
                session = HibernateInit.sessionFactory.openSession();
                txn = session.beginTransaction();
                //String sql = "SELECT b.id.merchantid as mid ,m.merchantname as mname ,b.id.batchnumber as batchnum,s.description as des FROM Ipgmerchant m,Ipgbatch b, Ipgstatus s where b.id.merchantid= m.merchantid AND b.ipgstatus.statuscode=s.statuscode ";
                String sql = " INSERT INTO IPGTRANSACTION(IPGTRANSACTIONID,MERCHANTID,CARDHOLDERPAN,AMOUNT,EXPIRYDATE,MERCHANTREMARKS,CURRENCYCODE,PURCHASEDDATETIME,"
                        + "CARDASSOCIATIONCODE,STATUSCODE,CARDNAME,TRANSACTIONCREATEDDATETIME,LASTUPDATEDUSER) "
                        + " VALUES(?,?,?,?,?,?,?,SYSDATE,?,?,?,SYSDATE,?)";

                Query query = session.createSQLQuery(sql);
                query.setString(0, bean.getTransactionId());
                query.setString(1, bean.getMerchantId());
                query.setString(2, bean.getCardNo());
                query.setString(3, bean.getTransactionAmount());
                query.setString(4, bean.getExpiryDate());
                query.setString(5, bean.getRemarks());
                query.setString(6, bean.getCurrency());
                query.setString(7, bean.getCardAssociation());
                query.setString(8, StatusVarList.TXN_INITIATED);
                query.setString(9, bean.getCardHolderName());
                query.setString(10, bean.getLastUpdatedUser());

                val = query.executeUpdate();
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

        }
        return val;
    }

    public int insertManualTransactionData(List<ManualFileBean> beanlist) throws Exception {

        Session session = null;
        Transaction txn = null;
        int val = 0;
        for (ManualFileBean bean : beanlist) {
            try {
                session = HibernateInit.sessionFactory.openSession();
                txn = session.beginTransaction();
                //String sql = "SELECT b.id.merchantid as mid ,m.merchantname as mname ,b.id.batchnumber as batchnum,s.description as des FROM Ipgmerchant m,Ipgbatch b, Ipgstatus s where b.id.merchantid= m.merchantid AND b.ipgstatus.statuscode=s.statuscode ";
                String sql = " INSERT INTO IPGTRANSACTION(IPGTRANSACTIONID,MERCHANTID,CARDHOLDERPAN,AMOUNT,EXPIRYDATE,MERCHANTREMARKS,CURRENCYCODE,PURCHASEDDATETIME,"
                        + "CARDASSOCIATIONCODE,STATUSCODE,CARDNAME,TRANSACTIONCREATEDDATETIME,LASTUPDATEDUSER) "
                        + " VALUES(?,?,?,?,?,?,?,SYSDATE,?,?,?,SYSDATE,?)";

                Query query = session.createSQLQuery(sql);

                query.setString(0, bean.getTransactionId());
                query.setString(1, bean.getMerchantId());
                query.setString(2, bean.getCardNo());
                query.setString(3, bean.getTransactionAmount());
                query.setString(4, bean.getExpiryDate());
                query.setString(5, bean.getRemarks());
                query.setString(6, bean.getCurrency());
                query.setString(7, bean.getCardAssociation());
                query.setString(8, StatusVarList.TXN_INITIATED);
                query.setString(9, bean.getCardHolderName());
                query.setString(10, bean.getLastUpdatedUser());

                val = query.executeUpdate();
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

        }
        return val;
    }

    public int insertAutoTransactionData(List<AutoFileBean> beanlist) throws Exception {

        Session session = null;
        Transaction txn = null;
        int val = 0;
        for (AutoFileBean bean : beanlist) {
            try {
                session = HibernateInit.sessionFactory.openSession();
                txn = session.beginTransaction();
                //String sql = "SELECT b.id.merchantid as mid ,m.merchantname as mname ,b.id.batchnumber as batchnum,s.description as des FROM Ipgmerchant m,Ipgbatch b, Ipgstatus s where b.id.merchantid= m.merchantid AND b.ipgstatus.statuscode=s.statuscode ";
                String sql = " INSERT INTO IPGTRANSACTION(IPGTRANSACTIONID,MERCHANTID,CARDHOLDERPAN,AMOUNT,EXPIRYDATE,MERCHANTREMARKS,CURRENCYCODE,PURCHASEDDATETIME,"
                        + "CARDASSOCIATIONCODE,STATUSCODE,CARDNAME,TRANSACTIONCREATEDDATETIME,LASTUPDATEDUSER) "
                        + " VALUES(?,?,?,?,?,?,?,SYSDATE,?,?,?,SYSDATE,?)";

                Query query = session.createSQLQuery(sql);

                query.setString(0, bean.getTransactionId());
                query.setString(1, bean.getMerchantId());
                query.setString(2, bean.getCardNo());
                query.setString(3, bean.getTransactionAmount());
                query.setString(4, bean.getExpiryDate());
                query.setString(5, bean.getRemarks());
                query.setString(6, bean.getCurrency());
                query.setString(7, bean.getCardAssociation());
                query.setString(8, StatusVarList.TXN_INITIATED);
                query.setString(9, bean.getCardHolderName());
                query.setString(10, bean.getLastUpdatedUser());

                val = query.executeUpdate();
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

        }
        return val;
    }

    public int insertInstantBatchTransactionData(List<InstantFileBean> beanlist) throws Exception {

        Session session = null;
        Transaction txn = null;
        int val = 0;
        for (InstantFileBean bean : beanlist) {
            try {
                session = HibernateInit.sessionFactory.openSession();
                txn = session.beginTransaction();
                //String sql = "SELECT b.id.merchantid as mid ,m.merchantname as mname ,b.id.batchnumber as batchnum,s.description as des FROM Ipgmerchant m,Ipgbatch b, Ipgstatus s where b.id.merchantid= m.merchantid AND b.ipgstatus.statuscode=s.statuscode ";
                String sql = " INSERT INTO IPGCCBATRANSACTION(FILEID,CARDNUMBER,EXPIRYDATE,NICNO,AMOUNT,CURRENCY,TRANSACTIONID) "
                        + " VALUES(?,?,?,?,?,?,?) ";

                Query query = session.createSQLQuery(sql);

                query.setString(0, bean.getFileId());
                query.setString(1, bean.getCardNo());
                query.setString(2, bean.getExpiryDate());
                query.setString(3, bean.getNic());
                query.setString(4, bean.getTransactionAmount());
                query.setString(5, bean.getCurrency());
                query.setString(6, bean.getTransactionId());

                val = query.executeUpdate();
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

        }
        return val;
    }

    public int insertAutoBatchTransactionData(List<AutoFileBean> beanlist) throws Exception {

        Session session = null;
        Transaction txn = null;
        int val = 0;
        for (AutoFileBean bean : beanlist) {
            try {
                session = HibernateInit.sessionFactory.openSession();
                txn = session.beginTransaction();
                //String sql = "SELECT b.id.merchantid as mid ,m.merchantname as mname ,b.id.batchnumber as batchnum,s.description as des FROM Ipgmerchant m,Ipgbatch b, Ipgstatus s where b.id.merchantid= m.merchantid AND b.ipgstatus.statuscode=s.statuscode ";
                String sql = " INSERT INTO IPGCCBATRANSACTION(FILEID,CARDNUMBER,EXPIRYDATE,NICNO,AMOUNT,CURRENCY,NOOFINSTALLMENT,RECURRINGPERIOD,"
                        + "COUNT,NEXTDATE,TRANSACTIONID) "
                        + " VALUES(?,?,?,?,?,?,?,?,?,?,?) ";

                Query query = session.createSQLQuery(sql);

                query.setString(0, bean.getFileId());
                query.setString(1, bean.getCardNo());
                query.setString(2, bean.getExpiryDate());
                query.setString(3, bean.getNic());
                query.setString(4, bean.getTransactionAmount());
                query.setString(5, bean.getCurrency());
                query.setString(6, bean.getNumOfInstallment());
                query.setString(7, bean.getRecuringPeriod());
                query.setString(8, bean.getCount());
                query.setString(9, bean.getNextTransactionDate());
                query.setString(10, bean.getTransactionId());

                val = query.executeUpdate();
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

        }
        return val;
    }

    public int insertManualBatchTransactionData(List<ManualFileBean> beanlist) throws Exception {

        Session session = null;
        Transaction txn = null;
        int val = 0;
        for (ManualFileBean bean : beanlist) {
            try {
                session = HibernateInit.sessionFactory.openSession();
                txn = session.beginTransaction();
                //String sql = "SELECT b.id.merchantid as mid ,m.merchantname as mname ,b.id.batchnumber as batchnum,s.description as des FROM Ipgmerchant m,Ipgbatch b, Ipgstatus s where b.id.merchantid= m.merchantid AND b.ipgstatus.statuscode=s.statuscode ";
                String sql = " INSERT INTO IPGCCBATRANSACTION(FILEID,CARDNUMBER,EXPIRYDATE,NICNO,AMOUNT,CURRENCY"
                        + ",NEXTDATE,TRANSACTIONID) "
                        + " VALUES(?,?,?,?,?,?,?,?) ";

                Query query = session.createSQLQuery(sql);

                query.setString(0, bean.getFileId());
                query.setString(1, bean.getCardNo());
                query.setString(2, bean.getExpiryDate());
                query.setString(3, bean.getNic());
                query.setString(4, bean.getTransactionAmount());
                query.setString(5, bean.getCurrency());
                // ps.setString(7, bean.getNumOfInstallment());
                // ps.setString(8, bean.getRecuringPeriod());
                // ps.setString(9, bean.getCount());
                query.setString(6, bean.getNextTransactionDate());
                query.setString(7, bean.getTransactionId());

                val = query.executeUpdate();
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

        }
        return val;
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

                filepath = ipgcommonfilepath.getCcbtxnfiles();
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
    
     public boolean isValidMerchant(String mid) throws Exception {

        boolean status = false;
        Ipgccbafile ipgccbafile = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgmerchant as m where m.merchantid=:mid";
            Query query = session.createQuery(sql);
            query.setString("mid", mid);

            if (query.list().size() > 0) {
                status = true;
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
        return status;
    }

    public boolean isValidCurrency(String cid) throws Exception {

        boolean status = false;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgcurrency as c where c.currencyisocode=:cid";
            Query query = session.createQuery(sql);
            query.setString("cid", cid);

            if (query.list().size() > 0) {
                status = true;
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
        return status;
    }
  
     
}
