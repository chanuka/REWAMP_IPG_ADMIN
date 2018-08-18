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

import com.epic.epay.ipg.bean.controlpanel.systemconfig.OnUsBinRangeBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.OnUsBinRangeInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgonusbinrange;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;

/**
 * @created on :Nov 1, 2013, 10:56:43 AM
 * @author :thushanth
 */
public class OnUsBinRangeDAO {

    public String insertOnUsBinRange(OnUsBinRangeInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            /*if ((Ipgonusbinrange) session.get(Ipgonusbinrange.class, new BigDecimal(inputBean.getIpgonusbinrangeId().trim())) == null) {*/
            txn = session.beginTransaction();

            Ipgonusbinrange ipgonusbinrange = new Ipgonusbinrange();
            ipgonusbinrange.setValue1(inputBean.getValue_1().trim());
            ipgonusbinrange.setValue2(inputBean.getValue_2().trim());
            ipgonusbinrange.setRemarks(inputBean.getReMarks().trim());

            Ipgstatus ipgstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus());
            ipgonusbinrange.setIpgstatus(ipgstatus);

            ipgonusbinrange.setCreatedtime(sysDate);
            ipgonusbinrange.setLastupdatedtime(sysDate);
            ipgonusbinrange.setLastupdateduser(audit.getLastupdateduser());

            /**
             * for audit new value
             */
            StringBuilder newValue = new StringBuilder();
            newValue.append(ipgonusbinrange.getValue1())
                    .append("|").append(ipgonusbinrange.getValue2())
                    .append("|").append(ipgonusbinrange.getRemarks())
                    .append("|").append(ipgonusbinrange.getIpgstatus().getDescription());
            audit.setNewvalue(newValue.toString());
            audit.setCreatedtime(sysDate);
            audit.setLastupdatedtime(sysDate);
            session.save(ipgonusbinrange);
            
            session.save(audit);

            txn.commit();
            /* } else {
             message = MessageVarList.COMMON_ALREADY_EXISTS;
             }*/

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

    public String deleteOnUsBinRange(OnUsBinRangeInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgonusbinrange u = (Ipgonusbinrange) session.get(Ipgonusbinrange.class, new BigDecimal(inputBean.getIpgonusbinrangeId().trim()));
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

    public String updateOnUsBinRange(OnUsBinRangeInputBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgonusbinrange u = (Ipgonusbinrange) session.get(Ipgonusbinrange.class, new BigDecimal(inputBean.getIpgonusbinrangeId().trim()));

            if (u != null) {

                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(u.getValue1())
                        .append("|").append(u.getValue2())
                        .append("|").append(u.getRemarks())
                        .append("|").append(u.getIpgstatus().getDescription());

                u.setValue1(inputBean.getValue_1().trim());
                u.setValue2(inputBean.getValue_2().trim());
                u.setRemarks(inputBean.getReMarks().trim());

                Ipgstatus ipgstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus());
                u.setIpgstatus(ipgstatus);

                u.setLastupdatedtime(sysDate);
                u.setLastupdateduser(audit.getLastupdateduser());

                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(u.getValue1())
                        .append("|").append(u.getValue2())
                        .append("|").append(u.getRemarks())
                        .append("|").append(u.getIpgstatus().getDescription());
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
    public List<OnUsBinRangeBean> getSearchList(OnUsBinRangeInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<OnUsBinRangeBean> dataList = new ArrayList<OnUsBinRangeBean>();
        Session session = null;
        try {

            long count = 0;
            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(u.ipgonusbinrangeid) from Ipgonusbinrange as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }
                String sqlSearch = "from Ipgonusbinrange u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    OnUsBinRangeBean onusbinrangebean = new OnUsBinRangeBean();
                    Ipgonusbinrange ipgonusbinrange = (Ipgonusbinrange) it.next();

                    try {
                        onusbinrangebean.setIpgonusbinrangeid(ipgonusbinrange.getIpgonusbinrangeid().toString());
                    } catch (NullPointerException npe) {
                        onusbinrangebean.setIpgonusbinrangeid("--");
                    }
                    try {
                        onusbinrangebean.setValue1(ipgonusbinrange.getValue1());
                    } catch (NullPointerException npe) {
                        onusbinrangebean.setValue1("--");
                    }
                    try {
                        onusbinrangebean.setValue2(ipgonusbinrange.getValue2());
                    } catch (NullPointerException npe) {
                        onusbinrangebean.setValue2("--");
                    }
                    try {
                        onusbinrangebean.setRemarks(ipgonusbinrange.getRemarks());
                    } catch (NullPointerException npe) {
                        onusbinrangebean.setRemarks("--");
                    }
                    try {
                        onusbinrangebean.setStatuscode(ipgonusbinrange.getIpgstatus().getDescription());
                    } catch (NullPointerException npe) {
                        onusbinrangebean.setStatuscode("--");
                    }
                    try {
                        String createdTime = ipgonusbinrange.getCreatedtime().toString();
                        onusbinrangebean.setCreatedtime(createdTime.substring(0, createdTime.length() - 2));
                    } catch (NullPointerException npe) {
                        onusbinrangebean.setCreatedtime("--");
                    }
                    onusbinrangebean.setFullCount(count);

                    dataList.add(onusbinrangebean);
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

    private String makeWhereClause(OnUsBinRangeInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getValue_1() != null && !inputBean.getValue_1().trim().isEmpty()) {
            where += " and lower(u.value1) like lower('%" + inputBean.getValue_1().trim() + "%')";
        }
        if (inputBean.getValue_2() != null && !inputBean.getValue_2().trim().isEmpty()) {
            where += " and lower(u.value2) like lower('%" + inputBean.getValue_2().trim() + "%')";
        }
        if (inputBean.getReMarks() != null && !inputBean.getReMarks().trim().isEmpty()) {
            where += " and lower(u.remarks) like lower('%" + inputBean.getReMarks().trim() + "%')";
        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().trim().isEmpty()) {
            where += " and u.ipgstatus.statuscode = '" + inputBean.getStatus().trim() + "'";
        }
        return where;
    }

    public Ipgonusbinrange findCurrencyByID(String ipgonusbinrangeid) throws Exception {
        Ipgonusbinrange ipgcurrency = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgonusbinrange as u where u.ipgonusbinrangeid=:ipgonusbinrangeid";
            Query query = session.createQuery(sql).setString("ipgonusbinrangeid", ipgonusbinrangeid);
            ipgcurrency = (Ipgonusbinrange) query.list().get(0);

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
