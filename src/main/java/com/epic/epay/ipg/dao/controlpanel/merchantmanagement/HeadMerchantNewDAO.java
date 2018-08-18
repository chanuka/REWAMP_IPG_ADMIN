/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.HeadMerchantBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.HeadMerchantInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcommonconfig;
import com.epic.epay.ipg.util.mapping.Ipgcommonfilepath;
import com.epic.epay.ipg.util.mapping.Ipgcountry;
import com.epic.epay.ipg.util.mapping.Ipgheadmerchant;
import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgriskprofile;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Chalitha
 */
public class HeadMerchantNewDAO {

    public List<HeadMerchantBean> getSearchList(HeadMerchantInputBean inputBean, int rows, int from, String orderBy) throws Exception {

        List<HeadMerchantBean> dataList = new ArrayList<HeadMerchantBean>();
        Session session = null;
        try {
            long count = 0;
            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();
            Query queryCount = session.createQuery("select count(merchantcustomerid) from Ipgheadmerchant as u where " + where);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }

                Query querySearch = session.createQuery("from Ipgheadmerchant u where " + where + orderBy);

                querySearch.setMaxResults(rows);
                querySearch.setFirstResult(from);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    HeadMerchantBean headMerchantBean = new HeadMerchantBean();
                    Ipgheadmerchant ipgheadmerchant = (Ipgheadmerchant) it.next();

                    try {
                        headMerchantBean.setMerchantcustomerid(ipgheadmerchant.getMerchantcustomerid().toString());
                    } catch (Exception npe) {
                        headMerchantBean.setMerchantcustomerid("--");
                    }
                    try {
                        headMerchantBean.setMerchantname(ipgheadmerchant.getMerchantcustomername().toString());
                    } catch (Exception npe) {
                        headMerchantBean.setMerchantname("--");
                    }

                    try {
                        headMerchantBean.setEmail(ipgheadmerchant.getEmail().toString());
                    } catch (Exception npe) {
                        headMerchantBean.setEmail("--");
                    }
                    try {
                        headMerchantBean.setStatus(ipgheadmerchant.getIpgstatusByStatuscode().getDescription());
                    } catch (NullPointerException npe) {
                        headMerchantBean.setAuthreqstatus("--");
                    }
                    try {
                        headMerchantBean.setTxninitstatus(ipgheadmerchant.getIpgstatusByTransactioninitaiatedstatus().getDescription());
                    } catch (NullPointerException npe) {
                        headMerchantBean.setTxninitstatus("--");
                    }
                    try {
                        headMerchantBean.setCreatedtime(ipgheadmerchant.getCreatedtime().toString().substring(0, ipgheadmerchant.getCreatedtime().toString().length() - 2));
                    } catch (NullPointerException npe) {
                        headMerchantBean.setCreatedtime("--");
                    }

                    headMerchantBean.setFullCount(count);
                    dataList.add(headMerchantBean);
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

    private String makeWhereClause(HeadMerchantInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getMerchantcustomerid() != null && !inputBean.getMerchantcustomerid().trim().isEmpty()) {
            where += " and lower(u.merchantcustomerid) like lower('%" + inputBean.getMerchantcustomerid().trim() + "%')";
        }
        if (inputBean.getMerchantname() != null && !inputBean.getMerchantname().trim().isEmpty()) {
            where += " and lower(u.merchantcustomername) like lower('%" + inputBean.getMerchantname().trim() + "%')";
        }
        if (inputBean.getEmail() != null && !inputBean.getEmail().trim().isEmpty()) {
            where += " and lower(u.email) like lower('%" + inputBean.getEmail().trim() + "%')";
        }
        if (inputBean.getStatus()!= null && !inputBean.getStatus().trim().isEmpty()) {
            where += " and u.ipgstatusByStatuscode.statuscode = '" + inputBean.getStatus().trim() + "'";
        }
        if (inputBean.getTxninitstatus()!= null && !inputBean.getTxninitstatus().trim().isEmpty()) {
            where += " and u.ipgstatusByTransactioninitaiatedstatus.statuscode = '" + inputBean.getTxninitstatus().trim() + "'";
        }
        return where;
    }

    public String insertHeadMerchant(HeadMerchantInputBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);
            if ((Ipgheadmerchant) session.get(Ipgheadmerchant.class, inputBean.getMerchantcustomerid().trim()) == null) {
                txn = session.beginTransaction();

                byte[] privateKey = new byte[8];
                byte[] publicKey = new byte[8];
                byte[] symetricKey = new byte[8];

                Query query = session.createSQLQuery(
                        "CALL INSERTHEADMERCHANTNEW(:merchantcus_id,:txninit_status,:merchant_name,:email_ad,:status_code,:remarks_dat,:last_updated_user,"
                        + ":exec_stat)")
                        .setParameter("merchantcus_id", inputBean.getMerchantcustomerid().trim())
                        .setParameter("txninit_status", inputBean.getTxninitstatus().trim())
                        .setParameter("merchant_name", inputBean.getMerchantname().trim())
                        .setParameter("email_ad", inputBean.getEmail().trim())
                        .setParameter("status_code", inputBean.getStatus().trim())
                        .setParameter("remarks_dat", inputBean.getRemarks().trim())
                        .setParameter("last_updated_user", audit.getLastupdateduser().trim())
                        .setParameter("exec_stat", -1);
                int result = query.executeUpdate();

                /**
                 * for audit new value
                 */
                Ipgstatus ipgstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                Ipgstatus ipgtranstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getTxninitstatus().trim());

                StringBuilder newValue = new StringBuilder();
                newValue.append(inputBean.getMerchantcustomerid())
                        .append("|").append(inputBean.getMerchantname())
                        .append("|").append(inputBean.getEmail())
                        .append("|").append(ipgstatus.getDescription())
                        .append("|").append(inputBean.getRemarks())
                        .append("|").append(ipgtranstatus.getDescription());
                audit.setNewvalue(newValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);

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

    public String updateHeadMerchant(HeadMerchantInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            String headmerchantcode = inputBean.getMerchantcustomerid().trim();
            Ipgheadmerchant headmerchant = (Ipgheadmerchant) session.get(Ipgheadmerchant.class, headmerchantcode);

            if (headmerchant != null) {
                /**
                 * for audit old value
                 */

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                StringBuilder oldValue = new StringBuilder();
                oldValue.append(headmerchant.getMerchantcustomerid())
                        .append("|").append(headmerchant.getMerchantcustomername())
                        .append("|").append(headmerchant.getEmail())
                        .append("|").append(headmerchant.getIpgstatusByStatuscode().getDescription())
                        .append("|").append(headmerchant.getRemarks())
                        .append("|").append(headmerchant.getIpgstatusByTransactioninitaiatedstatus().getDescription());

                byte[] privateKey = new byte[8];
                byte[] publicKey = new byte[8];
                byte[] symetricKey = new byte[8];

                Query query = session.createSQLQuery(
                        "CALL UPDATEHEADMERCHANTNEW(:merchantcus_id,:merchant_name,:txninit_status,:email_ad,:status_code,:remarks_dat,:last_updated_user,:last_updated_time,"
                        + ":exec_stat)")
                        .setParameter("merchantcus_id", inputBean.getMerchantcustomerid().trim())
                        .setParameter("txninit_status", inputBean.getTxninitstatus().trim())
                        .setParameter("merchant_name", inputBean.getMerchantname().trim())
                        .setParameter("email_ad", inputBean.getEmail().trim())
                        .setParameter("status_code", inputBean.getStatus().trim())
                        .setParameter("remarks_dat", inputBean.getRemarks().trim())
                        .setParameter("last_updated_user", audit.getLastupdateduser().trim())
                        .setParameter("last_updated_time", new java.sql.Timestamp(audit.getLastupdatedtime().getTime()))
                        .setParameter("exec_stat", -1);
                int result = query.executeUpdate();

                /**
                 * for audit new value
                 */
//                Ipgcountry ipgcountry = (Ipgcountry) session.get(Ipgcountry.class, inputBean.getCountry().trim());
                Ipgstatus ipgstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
//                Ipgstatus ipgauthstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getAuthreqstatus().trim());
                Ipgstatus ipgtranstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getTxninitstatus().trim());
//                Ipgriskprofile ipgriskprofile = (Ipgriskprofile) session.get(Ipgriskprofile.class, inputBean.getRiskprofile().trim());

                StringBuilder newValue = new StringBuilder();
                newValue.append(inputBean.getMerchantcustomerid())
                        .append("|").append(inputBean.getMerchantname())
                        .append("|").append(inputBean.getEmail())
                        .append("|").append(ipgstatus.getDescription())
                        .append("|").append(inputBean.getRemarks())
                        .append("|").append(ipgtranstatus.getDescription());
                audit.setNewvalue(newValue.toString());
                audit.setOldvalue(oldValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
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

    public Ipgheadmerchant findHeadMerchantById(String headmerchantcode) throws Exception {

        Ipgheadmerchant headmerchant = new Ipgheadmerchant();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql1 = "from Ipgheadmerchant as u where u.merchantcustomerid=:merchantcustomerid";
            Query query1 = session.createQuery(sql1).setString("merchantcustomerid", headmerchantcode);
            if (query1.list().size() > 0) {
                headmerchant = (Ipgheadmerchant) query1.list().get(0);
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
        return headmerchant;
    }

    public Ipgmerchant findMerchantById(String merchantcode) throws Exception {

        Ipgmerchant merchant = new Ipgmerchant();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql1 = "from Ipgmerchant as u where u.merchantid=:merchantid";
            Query query1 = session.createQuery(sql1).setString("merchantid", merchantcode);
            merchant = (Ipgmerchant) query1.list().get(0);

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
        return merchant;
    }

    public List<Ipgcountry> getDefultCountryList() throws Exception {
        List<Ipgcountry> countrylist = new ArrayList<Ipgcountry>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgcountry ";
            Query query = session.createQuery(hql);
            countrylist = query.list();

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

        return countrylist;
    }

    public String deleteHeadMerchant(HeadMerchantInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgheadmerchant u = (Ipgheadmerchant) session.get(Ipgheadmerchant.class, inputBean.getMerchantcustomerid().trim());
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

    public Ipgcommonfilepath getKeystoreFilePath(String ostype) throws Exception {

        Ipgcommonfilepath ipgcommonfilepath = new Ipgcommonfilepath();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql1 = "from Ipgcommonfilepath as u where u.ostype=:ostype";
            Query query1 = session.createQuery(sql1).setString("ostype", ostype);
            if (query1.list().size() > 0) {
                ipgcommonfilepath = (Ipgcommonfilepath) query1.list().get(0);
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
        return ipgcommonfilepath;

    }

    public Ipgcountry getCountryByCode(String code) throws Exception {

        Ipgcountry ipgcountry = new Ipgcountry();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql1 = "from Ipgcountry as u where u.countrycode=:code";
            Query query1 = session.createQuery(sql1).setString("code", code);
            if (query1.list().size() > 0) {
                ipgcountry = (Ipgcountry) query1.list().get(0);
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
        return ipgcountry;

    }  
    
}

