/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.MotoTransactionInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
import com.epic.epay.ipg.util.mapping.Ipgcurrency;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgtransaction;
import com.epic.epay.ipg.util.mapping.IpgtransactionId;
import com.epic.epay.ipg.util.mapping.Ipgtransactionhistory;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.StatusVarList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author badrika
 */
public class MotoTransactionDAO {

    //get currency list
    public List<Ipgcurrency> getCurrencyList() throws Exception {

        List<Ipgcurrency> currencyList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgcurrency";
            Query query = session.createQuery(sql);
            currencyList = query.list();

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
        return currencyList;
    }

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

    //-------------------------------Moto Transaction-------------------------------------------------------------------
    public String motoTransactionInitiated(MotoTransactionInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgtransaction) session.get(Ipgtransaction.class, new IpgtransactionId(inputBean.getTxnId(), inputBean.getMerchantId())) == null) {
                txn = session.beginTransaction();

                Ipgtransaction tran = new Ipgtransaction();

                IpgtransactionId id = new IpgtransactionId();
                id.setIpgtransactionid(inputBean.getTxnId().trim());
                id.setMerchantid(inputBean.getMerchantId().trim());
                tran.setId(id);

                Ipgstatus st = new Ipgstatus();
                st.setStatuscode(inputBean.getStatus().trim());
                tran.setIpgstatus(st);

                Ipgcardassociation cd = new Ipgcardassociation();
                cd.setCardassociationcode(inputBean.getCardType().trim());
                tran.setIpgcardassociation(cd);

                Ipgcurrency cr = new Ipgcurrency();
                cr.setCurrencyisocode(inputBean.getCurrency().trim());
                tran.setIpgcurrency(cr);

                tran.setCardholderpan(inputBean.getCardNo().trim());
                tran.setAmount(Integer.parseInt(inputBean.getAmount().trim()));
                tran.setExpirydate(inputBean.getExpiryDate().trim());
                tran.setTxncategory(StatusVarList.MOTO_TXN_CATEGORY);
                tran.setOrderid(inputBean.getOrderCode().trim());
                tran.setMerchantremarks("Moto");
                tran.setCardname(inputBean.getCardHolderName().trim());

                tran.setTransactioncreateddatetime(sysDate);
                tran.setLastupdatedtime(sysDate);
                tran.setCreatedtime(sysDate);
                tran.setPurchaseddatetime(sysDate);
                tran.setLastupdateduser(inputBean.getUser());

                session.save(tran);

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

    //transaction history insert
    public String insertIPGTxnHistory(MotoTransactionInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);


            txn = session.beginTransaction();

            Ipgtransactionhistory tranHis = new Ipgtransactionhistory();

            tranHis.setIpgtransactionid(inputBean.getTxnId().trim());

            Ipgstatus st = new Ipgstatus();
            st.setStatuscode(inputBean.getStatus().trim());
            tranHis.setIpgstatus(st);

            tranHis.setLastupdatedtime(sysDate);
            tranHis.setCreatedtime(sysDate);
            tranHis.setLastupdateduser(inputBean.getUser());


            session.save(tranHis);

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

    public String updateTransactionStage(MotoTransactionInputBean inputBean) throws Exception {

        Ipgtransaction tran = null;
        Session session = null;
        Transaction txn = null;
        String message = "";


        try {
            session = HibernateInit.sessionFactory.openSession();
            
            String sql = "from Ipgtransaction as u where u.id.ipgtransactionid=:txnid";
            Query query = session.createQuery(sql);
            query.setString("txnid", inputBean.getTxnId().trim());
            tran = (Ipgtransaction) query.list().get(0);

            
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            if (tran != null) {

                Ipgstatus st = new Ipgstatus();
                st.setStatuscode(inputBean.getStatus());
                tran.setIpgstatus(st);

                tran.setLastupdatedtime(sysDate);

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
    ////////////////////////////
}
