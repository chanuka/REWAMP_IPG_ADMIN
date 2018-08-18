/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantTerminalBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantTerminalInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcurrency;
import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgmerchnatterminal;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author badrika
 */
public class MerchantTerminalDAO {

    public List<MerchantTerminalBean> getSearchList(MerchantTerminalInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<MerchantTerminalBean> dataList = new ArrayList<MerchantTerminalBean>();
        Session session = null;
        try {

            long count = 0;
            String where =this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();


            String sqlCount = "select count(u.terminalid) from Ipgmerchnatterminal as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }

                String sqlSearch = "from Ipgmerchnatterminal u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    MerchantTerminalBean bean = new MerchantTerminalBean();
                    Ipgmerchnatterminal ter = (Ipgmerchnatterminal) it.next();

                    try {
                        bean.setMerchantId(ter.getIpgmerchant().getMerchantid());
                    } catch (NullPointerException npe) {
                        bean.setMerchantId("--");
                    }
                    try {
                        bean.setCurrencyCode(ter.getIpgcurrency().getDescription());
                    } catch (NullPointerException npe) {
                        bean.setCurrencyCode("--");
                    }
                    try {
                        bean.setTid(ter.getTerminalid());
                    } catch (NullPointerException npe) {
                        bean.setTid("--");
                    }
                    try {
                        bean.setStatus(ter.getIpgstatus().getDescription());
                    } catch (NullPointerException npe) {
                        bean.setStatus("--");
                    }
                    try {
                        bean.setDelId(ter.getIpgmerchant().getMerchantid() + "|" + ter.getIpgcurrency().getCurrencyisocode() + "|" + ter.getTerminalid());
                    } catch (NullPointerException npe) {
                        bean.setDelId("--");
                    }
                    try {
                        bean.setCreatedtime(ter.getCreatedtime().toString().substring(0, ter.getCreatedtime().toString().length() - 2));
                    } catch (NullPointerException npe) {
                        bean.setCreatedtime("--");
                    }


                    bean.setFullCount(count);

                    dataList.add(bean);
                }
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

    private String makeWhereClause(MerchantTerminalInputBean inputBean){
        String where = "1=1";
        if (inputBean.getMerchantId()!= null && !inputBean.getMerchantId().trim().isEmpty()) {
            where += " and u.ipgmerchant.merchantid = '" + inputBean.getMerchantId().trim() + "'";
        }
        if (inputBean.getCurrencyCode()!= null && !inputBean.getCurrencyCode().trim().isEmpty()) {
            where += " and u.ipgcurrency.currencyisocode = '" + inputBean.getCurrencyCode().trim() + "'";
        }
        if (inputBean.getStatus()!= null && !inputBean.getStatus().trim().isEmpty()) {
            where += " and u.ipgstatus.statuscode = '" + inputBean.getStatus().trim() + "'";
        }
        if (inputBean.getTid()!= null && !inputBean.getTid().trim().isEmpty()) {
            where += " and lower(u.terminalid) like lower('%" + inputBean.getTid().trim() + "%')";
        }
        return where;
    }
    
    public String getBatchStatusByTerminalId(String tid) throws Exception {
        String status = "";
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select u.status from Ipgbatch u where u.batchnumber in (select t.batchnumber from Ipgtransaction t where t.terminalid=:tid)";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("tid", tid);

            List list = query.list();
            if (list.size() > 0) {
                status = (String) list.get(0);
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

    public String deleteTransaction(String merchantId, String tid) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgtransaction as u where u.id.merchantid=:mid and u.terminalid=:tid";
            Query query = session.createQuery(sql);
            query.setString("mid", merchantId);
            query.setString("tid", tid);

            List txnList = query.list();
            for (int i = 0; i < txnList.size(); i++) {
                txn = session.beginTransaction();
                try {
                    if (txnList.get(i) != null) {
                        session.delete(txnList.get(i));
                        txn.commit();
                    } else {
                        message = MessageVarList.COMMON_NOT_EXISTS;
                    }
                } catch (Exception e) {
                    if (txn != null) {
                        txn.rollback();
                    }
                    throw e;
                }
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

    // delete ipg merchant terminal 
    public String deleteMerchantTerminal(String tid, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgmerchnatterminal u = (Ipgmerchnatterminal) session.get(Ipgmerchnatterminal.class, tid);

            if (u != null) {

                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.delete(u);

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

    public String getHeadMerchantTransactionInitiatedStatusByMerchantId(String mid) throws Exception {
        String status = "";
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select TRANSACTIONINITAIATEDSTATUS from IPGHEADMERCHANT where MERCHANTCUSTOMERID in (select MERCHANTCUSTOMERID FROM IPGMERCHANT WHERE MERCHANTID =:mid) ";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("mid", mid);

            if (query.list().size() > 0) {
                status = (String) query.list().get(0);
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

    public HashMap<String, String> GetCurrencyByHeadMerchant(MerchantTerminalInputBean inputBean) throws Exception {

        Session session = null;
        HashMap<String, String> currencyMap = new HashMap<String, String>();
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select u.currencyisocode,u.description from Ipgcurrency u "
                    + "where "
                    + "u.currencyisocode not in ("
                    + "select mt.currencytype from Ipgmerchnatterminal mt "
                    + "where mt.merchantid in ("
                    + "select hm.transactioninitaiatedmerchntid from Ipgheadmerchant hm "
                    + "where hm.merchantcustomerid in (select m.merchantcustomerid from Ipgmerchant m where m.merchantid =:mid)))";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("mid", inputBean.getMerchantId());
            List list = query.list();

            for (Iterator it = list.iterator(); it.hasNext();) {
                Object[] obj = (Object[]) it.next();
                currencyMap.put(obj[0].toString(), obj[1].toString());
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
        return currencyMap;
    }

    public String GetTransactionInitiatedIdByMerchantId(String mid) throws Exception {
        String status = "";
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select hm.TRANSACTIONINITAIATEDMERCHNTID from Ipgheadmerchant hm where hm.merchantcustomerid in (select m.merchantcustomerid from ipgmerchant m where m.merchantid=:mid)";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("mid", mid);

            if (query.list().size() > 0) {
                status = (String) query.list().get(0);
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

    public List<MerchantTerminalBean> getIPGMerchantTerminalList(String mid) throws Exception {

        List<MerchantTerminalBean> merchList = new ArrayList<MerchantTerminalBean>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgmerchnatterminal as mt where mt.ipgmerchant.merchantid=:mid";
            Query query = session.createQuery(sql).setString("mid", mid);
            Iterator it = query.iterate();
            while (it.hasNext()) {
                MerchantTerminalBean bean = new MerchantTerminalBean();
                Ipgmerchnatterminal merTer = (Ipgmerchnatterminal) it.next();

                bean.setMerchantId(mid);
                bean.setCurrencyCode(merTer.getIpgcurrency().getCurrencyisocode());
                bean.setTid(merTer.getTerminalid());
                bean.setStatus(merTer.getIpgstatus().getStatuscode());

                merchList.add(bean);
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
        return merchList;
    }

    public HashMap<String, String> GetCurrencyListUsingHMerchant(MerchantTerminalInputBean inputBean, String transactionId) throws Exception {

        Session session = null;
        HashMap<String, String> currencyMap = new HashMap<String, String>();
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select u.currencyisocode,u.description from Ipgcurrency u "
                    + "where "
                    + "u.currencyisocode in (select mt.CURRENCYTYPE from Ipgmerchnatterminal mt where mt.merchantid=:txnid) "
                    + "and u.currencyisocode not in (select mt.CURRENCYTYPE from Ipgmerchnatterminal mt where mt.merchantid=:mid)";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("txnid", transactionId);
            query.setParameter("mid", inputBean.getMerchantId());
            List list = query.list();

            for (Iterator it = list.iterator(); it.hasNext();) {
                Object[] obj = (Object[]) it.next();
                currencyMap.put(obj[0].toString(), obj[1].toString());
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
        return currencyMap;
    }

    public HashMap<String, String> GetCurrencyByMerchant(MerchantTerminalInputBean inputBean) throws Exception {

        Session session = null;
        HashMap<String, String> currencyMap = new HashMap<String, String>();
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select u.currencyisocode,u.description from Ipgcurrency u where u.currencyisocode not in(select mt.CURRENCYTYPE from Ipgmerchnatterminal mt where "
                    + "mt.merchantid=:mid)";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("mid", inputBean.getMerchantId());
            List list = query.list();

            for (Iterator it = list.iterator(); it.hasNext();) {
                Object[] obj = (Object[]) it.next();
                currencyMap.put(obj[0].toString(), obj[1].toString());
            }
            System.out.println("size : " + currencyMap.size());
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
        return currencyMap;
    }

    public String insertMerchantTerminal(MerchantTerminalInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgmerchnatterminal) session.get(Ipgmerchnatterminal.class, inputBean.getTid().trim()) == null) {
                txn = session.beginTransaction();

                Ipgmerchnatterminal terminal = new Ipgmerchnatterminal();

                terminal.setTerminalid(inputBean.getTid().trim());

                Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                terminal.setIpgstatus(status);

                Ipgmerchant mer = new Ipgmerchant();
                mer.setMerchantid(inputBean.getMerchantId());
                terminal.setIpgmerchant(mer);

                Ipgcurrency currency = (Ipgcurrency) session.get(Ipgcurrency.class, inputBean.getCurrencyCode().trim());
                terminal.setIpgcurrency(currency);
                
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(terminal.getIpgmerchant().getMerchantid())
                        .append("|").append(terminal.getIpgcurrency().getDescription())
                        .append("|").append(terminal.getTerminalid())
                        .append("|").append(terminal.getIpgstatus().getDescription());
                audit.setNewvalue(newValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(terminal);

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
    public HashMap<String, String> GetAllCurrency() throws Exception {

        Session session = null;
        HashMap<String, String> currencyMap = new HashMap<String, String>();
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select u.currencyisocode,u.description from Ipgcurrency u ";
            SQLQuery query = session.createSQLQuery(sql);
            List list = query.list();

            for (Iterator it = list.iterator(); it.hasNext();) {
                Object[] obj = (Object[]) it.next();
                currencyMap.put(obj[0].toString(), obj[1].toString());
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
        return currencyMap;
    }
    ///////////////////////////////
}
