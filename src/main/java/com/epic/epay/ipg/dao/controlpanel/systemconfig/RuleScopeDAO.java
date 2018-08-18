/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.RuleScopeBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.RuleScopeInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgrulescope;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author chalitha
 */
public class RuleScopeDAO {
    
    public java.lang.String insertRuleScope(RuleScopeInputBean InputBean,Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgrulescope) session.get(Ipgrulescope.class, InputBean.getRulescopecode().trim()) == null) {
                txn = session.beginTransaction();

                Ipgrulescope rulescope = new Ipgrulescope();

                rulescope.setRulescopecode(InputBean.getRulescopecode().trim());
                
                rulescope.setDescription(InputBean.getDescription().trim());
               
                Ipgstatus status = (Ipgstatus)session.get(Ipgstatus.class, InputBean.getStatus());
                rulescope.setIpgstatus(status);
                
                rulescope.setLastupdatedtime(sysDate);
                rulescope.setLastupdateduser(audit.getLastupdateduser());
                rulescope.setCreatedtime(sysDate);
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(rulescope.getRulescopecode())
                        .append("|").append(rulescope.getDescription())
                        .append("|").append(rulescope.getIpgstatus().getDescription());
                audit.setNewvalue(newValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(rulescope);

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
    
    public List<RuleScopeBean> getSearchList(RuleScopeInputBean inputBean,int rows, int from, String orderBy) throws Exception {
        
        List<RuleScopeBean> dataList = new ArrayList<RuleScopeBean>();
        Session session = null;
        try {
            long count = 0;
            String where =this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            Query queryCount = session
                    .createQuery("select count(rulescopecode) from Ipgrulescope as u where "+where);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }

                Query querySearch = session.createQuery("from Ipgrulescope u where "+where
                        + orderBy);

                querySearch.setMaxResults(rows);
                querySearch.setFirstResult(from);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    RuleScopeBean rulescopeBean = new RuleScopeBean();
                    Ipgrulescope rulescope = (Ipgrulescope) it.next();

                    try {
                        rulescopeBean.setRulescopecode(rulescope.getRulescopecode().toString());
                    } catch (NullPointerException npe) {
                        rulescopeBean.setRulescopecode("--");
                    }
                    try {
                        rulescopeBean.setDescription(rulescope.getDescription());
                    } catch (NullPointerException npe) {
                        rulescopeBean.setDescription("--");
                    }
                    try {
                        rulescopeBean.setStatus(rulescope.getIpgstatus().getDescription().toString());
                    } catch (NullPointerException npe) {
                        rulescopeBean.setSortkey("--");
                    }
                    try {
                        rulescopeBean.setCreatedtime(rulescope.getCreatedtime().toString());
                    } catch (NullPointerException npe) {
                        rulescopeBean.setCreatedtime("--");
                    }

                    rulescopeBean.setFullCount(count);
                    dataList.add(rulescopeBean);
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
    
    private String makeWhereClause(RuleScopeInputBean inputBean){
        String where = "1=1";
        if (inputBean.getRulescopecode()!= null && !inputBean.getRulescopecode().trim().isEmpty()) {
            where += " and lower(u.rulescopecode) like lower('%" + inputBean.getRulescopecode().trim() + "%')";
        }
        if (inputBean.getDescription()!= null && !inputBean.getDescription().trim().isEmpty()) {
            where += " and lower(u.description) like lower('%" + inputBean.getDescription().trim() + "%')";
        }
        if (inputBean.getStatus()!= null && !inputBean.getStatus().trim().isEmpty()) {
            where += " and u.ipgstatus.statuscode = '" + inputBean.getStatus().trim() + "'";
        }
        return where;
    }
    
    public java.lang.String updateRuleScope(RuleScopeInputBean InputBean,Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgrulescope u = ( Ipgrulescope) session.get( Ipgrulescope.class, InputBean.getRulescopecode().trim());

            if (u != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(u.getRulescopecode())
                        .append("|").append(u.getDescription())
                        .append("|").append(u.getIpgstatus().getDescription());
                
                u.setDescription(InputBean.getDescription());
                
                Ipgstatus status = (Ipgstatus)session.get(Ipgstatus.class, InputBean.getStatus());
                u.setIpgstatus(status);
                u.setLastupdateduser(audit.getLastupdateduser());
                u.setLastupdatedtime(sysDate);
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(u.getRulescopecode())
                        .append("|").append(u.getDescription())
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
    
    public String deleteRuleScope(RuleScopeInputBean inputBean, Ipgsystemaudit audit)throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgrulescope u = (Ipgrulescope) session.get(Ipgrulescope.class, inputBean.getRulescopecode().trim());
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
    
     public Ipgrulescope findRuleScopeById(String rulescopeCode) throws Exception {
        Ipgrulescope rulescope = new Ipgrulescope();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgrulescope as u where u.rulescopecode=:rulescopecode";
            Query query = session.createQuery(sql).setString("rulescopecode",rulescopeCode);
            rulescope = (Ipgrulescope) query.list().get(0);

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
        return rulescope;
    }
    
    
}
