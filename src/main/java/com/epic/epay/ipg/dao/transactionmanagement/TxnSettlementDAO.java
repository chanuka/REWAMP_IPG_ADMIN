/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.TxnSettlementDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.TxnSettlementedBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgbatch;
import com.epic.epay.ipg.util.mapping.IpgbatchId;
import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtransaction;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.StatusVarList;
import com.epic.epay.ipg.util.varlist.UserRoleTypeVarList;
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
public class TxnSettlementDAO {

    //get merchant list
    public List<TxnSettlementDataBean> getAllMerchantIdList() throws Exception {

        List<TxnSettlementDataBean> midList = new ArrayList<TxnSettlementDataBean>();
        List<Ipgmerchant> list = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgmerchant as m";
            Query query = session.createQuery(sql);
            list = query.list();

            for (Ipgmerchant mchnt : list) {
                TxnSettlementDataBean bean = new TxnSettlementDataBean();
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

    //get merchant list
//   public List<TxnSettlementDataBean> getMerchantIdList() throws Exception {
//
//        List<TxnSettlementDataBean> midList = new ArrayList<TxnSettlementDataBean>();
//        List<Ipgsystemuser> list = null;
//        Session session = null;
//        try {
//            session = HibernateInit.sessionFactory.openSession();
//            String sql = "from Ipgsystemuser as m where m.ipguserroleByUserrolecode.userrolecode=:userrolecode";
//            Query query = session.createQuery(sql);
//            query.setParameter("userrolecode", UserRoleTypeVarList.MERCHANT);
//            list = query.list();
//
//            for (Ipgsystemuser mchnt : list) {
//                TxnSettlementDataBean bean = new TxnSettlementDataBean();
//                boolean status = false;
//                if (midList.size() > 0) {
//                    for (TxnSettlementDataBean bean1 : midList) {
//                        if (bean1.getMidkey().equals(mchnt.getMerchantid())) {
//                            status = true;
//                        }
//                    }
//                }
//                if (!status) {
//                    bean.setMidkey(mchnt.getMerchantid());
//                    bean.setMidvalue(mchnt.getMerchantid());
//                    midList.add(bean);
//                }
//
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
//        return midList;
//    }

    //get card Type List list
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

    // get settlement list 
    public List<TxnSettlementDataBean> getSettlementList(String loginUser,String flag,int max, int first) throws Exception {

        List<TxnSettlementDataBean> settlementList = new ArrayList<TxnSettlementDataBean>();
        List<Object[]> list = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT b.id.merchantid as mid ,m.merchantname as mname ,b.id.batchnumber as batchnum,s.description as des FROM Ipgmerchant m,Ipgbatch b, Ipgstatus s where b.id.merchantid= m.merchantid AND b.ipgstatus.statuscode=s.statuscode ";
            if(flag.equals("YES")){
                sql+=" and m.ipgheadmerchant.merchantcustomerid = ( select h.merchantcustomerid from Ipgheadmerchant as h where h.transactioninitaiatedmerchntid = (select su.merchantid from Ipgsystemuser as su where su.username=:username))";
            } else if(flag.equals("NO")){
                sql+=" and m.merchantid = ( select su.merchantid from Ipgsystemuser as su where su.username=:username)";
            }
            Query query = session.createQuery(sql);
            if(flag.equals("YES") || flag.equals("NO")){
                query.setString("username", loginUser);
            }
            long count = query.list().size();
            query.setMaxResults(max);
            query.setFirstResult(first);
            list = query.list();
            for (Object[] ob : list) {
                TxnSettlementDataBean bean = new TxnSettlementDataBean();
                bean.setMid(ob[0].toString());
                bean.setMerchantname(ob[1].toString());
                bean.setBatchnumber(ob[2].toString());
                bean.setStatus(ob[3].toString());
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

    public String getBatchId(String mid) throws Exception {

        String batchid = "";
        Ipgbatch ipgbatch = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgbatch as b where b.id.merchantid=:mid and b.ipgstatus.statuscode=:status";
            Query query = session.createQuery(sql);
            query.setString("mid", mid);
            query.setString("status", StatusVarList.BATCH_OPEN_STATUS);

            if (query.list().size() > 0) {
                ipgbatch = (Ipgbatch) query.list().get(0);

                batchid = ipgbatch.getId().getBatchnumber();
            } else {
                batchid = "notassign";
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
        return batchid;
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

                Ipgbatch newIpgbatch = new Ipgbatch();

                IpgbatchId ipgbatchId = new IpgbatchId();
                ipgbatchId.setBatchnumber(String.format("%06d", Integer.parseInt(batchId)+1));
                ipgbatchId.setMerchantid(mid);
                newIpgbatch.setId(ipgbatchId);

                Ipgstatus stnew = new Ipgstatus();
                stnew.setStatuscode(StatusVarList.BATCH_OPEN_STATUS);
                newIpgbatch.setIpgstatus(stnew);

                newIpgbatch.setLastupdateduser(sysaudit.getLastupdateduser());
                newIpgbatch.setLastupdatedtime(sysDate);
                newIpgbatch.setCreatedtime(sysDate);

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
    
    public List<TxnSettlementedBean> getSetteledTxnDetail(String merchantID,String batchno) throws Exception {
        List<TxnSettlementedBean> txnList = new ArrayList<TxnSettlementedBean>();
        List<Ipgtransaction> list = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgtransaction as t where t.id.ipgtransactionid in(select r.id.ipgtransactionid from Ipgenginerequest as r where r.id.merchantid =:merchantid and r.id.batchno =:batchno and r.id.statuscode=:statuscode )";
            Query query = session.createQuery(sql);
            query.setString("merchantid", merchantID);
            query.setString("batchno", batchno);
            query.setString("statuscode", StatusVarList.TXN_SETTLED);
            list = query.list();

            for (Ipgtransaction txn : list) {
                TxnSettlementedBean bean = new TxnSettlementedBean();
                try{
                    bean.setTxnid(txn.getId().getIpgtransactionid().toString());
                }catch(NullPointerException e){
                    bean.setTxnid("--");
                }
                try{
                    bean.setMerchant(txn.getId().getMerchantid().toString());
                }catch(NullPointerException e){
                    bean.setMerchant("--");
                }try{
                    bean.setTerminalid(txn.getTerminalid());
                }catch(NullPointerException e){
                    bean.setTerminalid("--");
                }
                try{
                    bean.setStatus(StatusVarList.TXN_SETTLED);
                }catch(NullPointerException e){
                    bean.setStatus("--");
                }
                try{
                    bean.setCardassociation(txn.getIpgcardassociation().getCardassociationcode());
                }catch(Exception e){
                    bean.setCardassociation("--");
                }
                try{
                    bean.setAmount(String.valueOf(txn.getAmount()));
                }catch(Exception e){
                    bean.setAmount("--");
                }
                try{
                    bean.setBatchnumber(txn.getBatchnumber().toString());
                }catch(Exception e){
                    bean.setBatchnumber("--");
                }
                try{
                    bean.setRrn(txn.getRrn().toString());
                }catch(Exception e){
                    bean.setRrn("--");
                }
                try{
                    bean.setLastupdatedtime(txn.getLastupdatedtime().toString());
                }catch(Exception e){
                    bean.setLastupdatedtime("--");
                }
                try{
                    bean.setLastupdateduser(txn.getLastupdateduser().toString());
                }catch(Exception e){
                    bean.setLastupdateduser("--");
                }
                try{
                    bean.setCreatedtime(txn.getCreatedtime().toString());
                }catch(Exception e){
                    bean.setCreatedtime("--");
                }
                try{
                    bean.setPurchaseddatetime(txn.getPurchaseddatetime().toString());
                }catch(Exception e){
                    bean.setPurchaseddatetime("--");
                }
                try{
                    String pan =txn.getCardholderpan().toString();
                    String star ="***************************";
                    String value = "";
                    if(pan.length()>4){
                        value= star.substring(0, pan.length()-4);
                        value+=pan.substring(pan.length()-4, pan.length());
                    }else{
                        value = pan;
                    }
                    bean.setCardholderpan(value);
                }catch(Exception e){
                    bean.setCardholderpan("--");
                }
                try{
                    bean.setCardname(txn.getCardname().toString());
                }catch(Exception e){
                    bean.setCardname("--");
                }
                try{
                    bean.setMerchantreferanceno(txn.getMerchantreferanceno().toString());
                }catch(Exception e){
                    bean.setMerchantreferanceno("--");
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
    
    public List<TxnSettlementDataBean> getMerchantIdList(String username) throws Exception {

        List<TxnSettlementDataBean> midList = new ArrayList<TxnSettlementDataBean>();
        List<Ipgsystemuser> list = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgsystemuser as m where m.username=:username";
            Query query = session.createQuery(sql);
            query.setParameter("username", username);
            list = query.list();

            for (Ipgsystemuser mchnt : list) {
                TxnSettlementDataBean bean = new TxnSettlementDataBean();
                
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
    
    public List<TxnSettlementDataBean> getHeadMerchantIdList(String username) throws Exception {

        List<TxnSettlementDataBean> midList = new ArrayList<TxnSettlementDataBean>();
        List<Ipgmerchant> list = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = " from Ipgmerchant as mr where mr.ipgheadmerchant.merchantcustomerid = ( select h.merchantcustomerid from Ipgheadmerchant as h where h.transactioninitaiatedmerchntid = (select m.merchantid from Ipgsystemuser as m where m.username=:username))";
            Query query = session.createQuery(sql);
            query.setParameter("username", username);
            list = query.list();

            for (Ipgmerchant mchnt : list) {
                TxnSettlementDataBean bean = new TxnSettlementDataBean();
                
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
}
