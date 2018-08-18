/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.RuleTypeBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.RuleTypeInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgrulescope;
import com.epic.epay.ipg.util.mapping.Ipgruletype;
import com.epic.epay.ipg.util.mapping.IpgruletypeId;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;

/**
 * @created on :Nov 4, 2013, 4:39:06 PM
 * @author :thushanth
 */
public class RuleTypeDAO {

    public String insertRuleType(RuleTypeInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgruletype) session.get(Ipgruletype.class, inputBean.getRuletypeCode().trim()) == null) {
                txn = session.beginTransaction();

                IpgruletypeId id = new IpgruletypeId();
                id.setRuletypecode(inputBean.getRuletypeCode());
                id.setRulescopecode(inputBean.getRulescope());                
                
                Ipgruletype ipgruletype = new Ipgruletype();                
                ipgruletype.setId(id);
                ipgruletype.setDescription(inputBean.getDescription().trim());
                Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus());
                ipgruletype.setIpgstatus(status);
                
                ipgruletype.setCreatedtime(sysDate);
                ipgruletype.setLastupdatedtime(sysDate);
                ipgruletype.setLastupdateduser(audit.getLastupdateduser());
                /**
                 * for audit new value
                 */
                Ipgrulescope ipgrulescope = (Ipgrulescope) session.get(Ipgrulescope.class, id.getRulescopecode());
                
                StringBuilder newValue = new StringBuilder();
                newValue.append(ipgruletype.getId().getRuletypecode())
                        .append("|").append(ipgrulescope.getDescription())
                        .append("|").append(ipgruletype.getDescription())
                        .append("|").append(ipgruletype.getIpgstatus().getDescription());

                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(ipgruletype);

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

    public String deleteRuleType(RuleTypeInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            IpgruletypeId id = new IpgruletypeId();
            id.setRuletypecode(inputBean.getRuletypeCode().trim());
            id.setRulescopecode(inputBean.getRulescope());
            Ipgruletype u = (Ipgruletype) session.get(Ipgruletype.class, id);
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

    public String updateRuleType(RuleTypeInputBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            IpgruletypeId id = new IpgruletypeId();
            id.setRuletypecode(inputBean.getRuletypeCode());
            id.setRulescopecode(inputBean.getRulescope());
            Ipgruletype u = (Ipgruletype) session.get(Ipgruletype.class, id);

            if (u != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(u.getId().getRuletypecode())                        
                        .append("|").append(u.getIpgrulescope().getDescription())
                        .append("|").append(u.getDescription())
                        .append("|").append(u.getIpgstatus().getDescription());

                u.setDescription(inputBean.getDescription().trim());
                Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus());
                u.setIpgstatus(status);               
                
                u.setLastupdatedtime(sysDate);
                u.setLastupdateduser(audit.getLastupdateduser());
                /**
                 * for audit new value
                 */
                Ipgrulescope ipgrulescope = (Ipgrulescope) session.get(Ipgrulescope.class, id.getRulescopecode());
                
                StringBuilder newValue = new StringBuilder();
                newValue.append(u.getId().getRuletypecode())                        
                        .append("|").append(ipgrulescope.getDescription())
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

    //get search list
    public List<RuleTypeBean> getSearchList(RuleTypeInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<RuleTypeBean> dataList = new ArrayList<RuleTypeBean>();
        Session session = null;
        try {

            long count = 0;
            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(u.id.ruletypecode) from Ipgruletype as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }

                String sqlSearch = "from Ipgruletype u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    RuleTypeBean ruletypebean = new RuleTypeBean();
                    Ipgruletype ipgruletype = (Ipgruletype) it.next();

                    try {
                        ruletypebean.setRuletypecode(ipgruletype.getId().getRuletypecode());
                    } catch (NullPointerException npe) {
                        ruletypebean.setRuletypecode("--");
                    }
                    try {
                        Ipgrulescope rulescope = (Ipgrulescope) session.get(Ipgrulescope.class, ipgruletype.getId().getRulescopecode());
                        ruletypebean.setRulescope(rulescope.getRulescopecode());
                        ruletypebean.setRulescopeDes(rulescope.getDescription());
                    } catch (Exception e) {
                        ruletypebean.setRulescopeDes("--");
                        ruletypebean.setRulescope("");
                    }

                    try {
                        ruletypebean.setDescription(ipgruletype.getDescription());
                    } catch (NullPointerException npe) {
                        ruletypebean.setDescription("--");
                    }
                    try {
                        ruletypebean.setStatus(ipgruletype.getIpgstatus().getDescription());
                    } catch (NullPointerException npe) {
                        ruletypebean.setStatus("--");
                    }
                    try {
                        ruletypebean.setCreatedtime(ipgruletype.getCreatedtime().toString());
                    } catch (NullPointerException npe) {
                        ruletypebean.setCreatedtime("--");
                    }

                    ruletypebean.setFullCount(count);

                    dataList.add(ruletypebean);
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

    private String makeWhereClause(RuleTypeInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getRuletypeCode() != null && !inputBean.getRuletypeCode().trim().isEmpty()) {
            where += " and lower(u.id.ruletypecode) like lower('%" + inputBean.getRuletypeCode().trim() + "%')";
        }
        if (inputBean.getRulescope() != null && !inputBean.getRulescope().trim().isEmpty()) {
            where += " and lower(u.id.rulescopecode) like lower('%" + inputBean.getRulescope().trim() + "%')";
        }
        if (inputBean.getDescription() != null && !inputBean.getDescription().trim().isEmpty()) {
            where += " and lower(u.description) like lower('%" + inputBean.getDescription().trim() + "%')";
        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().trim().isEmpty()) {
            where += " and u.ipgstatus.statuscode = '" + inputBean.getStatus().trim() + "'";
        }

        return where;
    }

    public Ipgruletype findRuleTypeByCode(RuleTypeInputBean inputBean) throws Exception {
        Ipgruletype ipgcurrency = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgruletype as u where u.id.ruletypecode=:ruletypecode and u.id.rulescopecode=:rulescopecode ";
            Query query = session.createQuery(sql).setString("ruletypecode", inputBean.getRuletypeCode()).setString("rulescopecode", inputBean.getRulescope());
            ipgcurrency = (Ipgruletype) query.list().get(0);

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
