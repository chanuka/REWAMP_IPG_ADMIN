/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.CurrencyBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.CurrencyInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcurrency;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;

/**
 * @created on :Oct 31, 2013, 10:07:29 AM
 * @author :thushanth
 */
public class CurrencyDAO {

    public String insertCurrency(CurrencyInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgcurrency) session.get(Ipgcurrency.class, inputBean.getCurrencyISOCode().trim()) == null) {
                txn = session.beginTransaction();

                Ipgcurrency ipgcurrency = new Ipgcurrency();
                ipgcurrency.setCurrencyisocode(inputBean.getCurrencyISOCode().trim());
                ipgcurrency.setCurrencycode(inputBean.getCurrencyCode().trim());
                ipgcurrency.setDescription(inputBean.getDescription().trim());
                ipgcurrency.setSortkey(new BigDecimal(inputBean.getSortKey().trim()));

                ipgcurrency.setCreatedtime(sysDate);
                ipgcurrency.setLastupdatedtime(sysDate);
                ipgcurrency.setLastupdateduser(audit.getLastupdateduser());
                
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(ipgcurrency.getCurrencyisocode())
                        .append("|").append(ipgcurrency.getCurrencycode())
                        .append("|").append(ipgcurrency.getDescription())
                        .append("|").append(ipgcurrency.getSortkey());
                audit.setNewvalue(newValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(ipgcurrency);

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

    public String deleteCurrency(CurrencyInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgcurrency u = (Ipgcurrency) session.get(Ipgcurrency.class, inputBean.getCurrencyISOCode().trim());
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

    public String updateCurrency(CurrencyInputBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgcurrency u = (Ipgcurrency) session.get(Ipgcurrency.class, inputBean.getCurrencyISOCode().trim());

            if (u != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(u.getCurrencyisocode())
                        .append("|").append(u.getCurrencycode())
                        .append("|").append(u.getDescription())
                        .append("|").append(u.getSortkey());

                u.setCurrencyisocode(inputBean.getCurrencyISOCode().trim());
                u.setCurrencycode(inputBean.getCurrencyCode().trim());
                u.setDescription(inputBean.getDescription().trim());
                u.setSortkey(new BigDecimal(inputBean.getSortKey().trim()));

                u.setLastupdatedtime(sysDate);
                u.setLastupdateduser(audit.getLastupdateduser()); 
                
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(u.getCurrencyisocode())
                        .append("|").append(u.getCurrencycode())
                        .append("|").append(u.getDescription())
                        .append("|").append(u.getSortkey());
                audit.setNewvalue(newValue.toString());                
                audit.setOldvalue(oldValue.toString());
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

    //get search list
    public List<CurrencyBean> getSearchList(CurrencyInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<CurrencyBean> dataList = new ArrayList<CurrencyBean>();
        Session session = null;
        try {

            long count = 0;
            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(u.currencyisocode) from Ipgcurrency as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }

                String sqlSearch = "from Ipgcurrency u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    CurrencyBean currencybean = new CurrencyBean();
                    Ipgcurrency ipgcurrency = (Ipgcurrency) it.next();

                    try {
                        currencybean.setCurrencyisocode(ipgcurrency.getCurrencyisocode());
                    } catch (NullPointerException npe) {
                        currencybean.setCurrencyisocode("--");
                    }
                    try {
                        currencybean.setCurrencycode(ipgcurrency.getCurrencycode());
                    } catch (NullPointerException npe) {
                        currencybean.setCurrencycode("--");
                    }
                    try {
                        currencybean.setDescription(ipgcurrency.getDescription());
                    } catch (NullPointerException npe) {
                        currencybean.setDescription("--");
                    }
                    try {
                        currencybean.setSortkey(ipgcurrency.getSortkey().toString());
                    } catch (NullPointerException npe) {
                        currencybean.setSortkey("--");
                    }
                    try {
                        String createdTime = ipgcurrency.getCreatedtime().toString();
                        currencybean.setCreatedtime(createdTime.substring(0, createdTime.length() - 2));
                    } catch (NullPointerException npe) {
                        currencybean.setCreatedtime("--");
                    }
                    
                    currencybean.setFullCount(count);

                    dataList.add(currencybean);
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

    private String makeWhereClause(CurrencyInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getCurrencyISOCode() != null && !inputBean.getCurrencyISOCode().trim().isEmpty()) {
            where += " and lower(u.currencyisocode) like lower('%" + inputBean.getCurrencyISOCode().trim() + "%')";
        }
        if (inputBean.getCurrencyCode() != null && !inputBean.getCurrencyCode().trim().isEmpty()) {
            where += " and lower(u.currencycode) like lower('%" + inputBean.getCurrencyCode().trim() + "%')";
        }
        if (inputBean.getDescription() != null && !inputBean.getDescription().trim().isEmpty()) {
            where += " and lower(u.description) like lower('%" + inputBean.getDescription().trim() + "%')";
        }
        if (inputBean.getSortKey() != null && !inputBean.getSortKey().trim().isEmpty()) {
            where += " and lower(u.sortkey) like lower('%" + inputBean.getSortKey().trim() + "%')";
        }

        return where;
    }

    public Ipgcurrency findCurrencyByCode(String currencyisocode) throws Exception {
        Ipgcurrency ipgcurrency = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgcurrency as u where u.currencyisocode=:currencyisocode";
            Query query = session.createQuery(sql).setString("currencyisocode", currencyisocode);
            ipgcurrency = (Ipgcurrency) query.list().get(0);

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
        return ipgcurrency;

    }

}
