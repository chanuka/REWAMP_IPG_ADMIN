/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.RuleBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.RuleInputBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.RuleOperatorListBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.RuleTypeListBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgrule;
import com.epic.epay.ipg.util.mapping.Ipgruleoperator;
import com.epic.epay.ipg.util.mapping.Ipgrulescope;
import com.epic.epay.ipg.util.mapping.Ipgruletype;
import com.epic.epay.ipg.util.mapping.IpgruletypeId;
import com.epic.epay.ipg.util.mapping.Ipgsecuritylevel;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtriggersequence;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @created on :Nov 5, 2013, 2:47:59 PM
 * @author :thushanth
 */
public class RuleDAO {

    public String insertRule(RuleInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session); 
            
            HttpServletRequest request = ServletActionContext.getRequest();
            Ipgsystemuser sysUser = (Ipgsystemuser) request.getSession(false).getAttribute(SessionVarlist.SYSTEMUSER);
            /*if ((Ipgrule) session.get(Ipgrule.class, new Long(inputBean.getRuleId().trim())) == null) {*/
            txn = session.beginTransaction();

            Ipgrule ipgrule = new Ipgrule();
            ipgrule.setDescription(inputBean.getDescription().trim());
            ipgrule.setEndvalue(inputBean.getEndValue().trim());
//            ipgrule.setPriority(inputBean.getPriority().trim());
            ipgrule.setStartvalue(inputBean.getStartValue().trim());
            ipgrule.setOperator(inputBean.getOperator().trim());
            Long triggersequenceid = Long.parseLong(inputBean.getTriggersequence());
            ipgrule.setTriggersequenceid(triggersequenceid);

//            Ipgruletype ruletype = new Ipgruletype();
//            ruletype.setRuletypecode(inputBean.getRuleType());
//            ipgrule.setIpgruletype(ruletype);            
            ipgrule.setRuletypecode(inputBean.getRuleType());

            Ipgrulescope ipgrulescope = (Ipgrulescope) session.get(Ipgrulescope.class, inputBean.getRuleScope());
            ipgrule.setIpgrulescope(ipgrulescope);

            Ipgsecuritylevel ipgsecurity = (Ipgsecuritylevel) session.get(Ipgsecuritylevel.class, inputBean.getSecurityLevel());
            ipgrule.setIpgsecuritylevel(ipgsecurity);

            Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus());
            ipgrule.setIpgstatus(status);

            ipgrule.setOperator(inputBean.getOperator());
            ipgrule.setCreatedtime(sysDate);
            ipgrule.setLastupdatedtime(sysDate);
            ipgrule.setLastupdateduser(audit.getLastupdateduser());
            /**
             * for audit new value
             */
            IpgruletypeId id = new IpgruletypeId();
            id.setRuletypecode(inputBean.getRuleType());
            id.setRulescopecode(inputBean.getRuleScope());
            Ipgruletype ipgruletype = (Ipgruletype) session.get(Ipgruletype.class, id);
            Ipgtriggersequence ipgtriggerseq = (Ipgtriggersequence) session.get(Ipgtriggersequence.class, (new BigDecimal(ipgrule.getTriggersequenceid())));
            StringBuilder newValue = new StringBuilder();
            newValue.append(ipgrule.getIpgrulescope().getDescription())
                    .append("|").append(ipgrule.getOperator())
                    .append("|").append(ipgruletype.getDescription())
                    .append("|").append(ipgrule.getStartvalue())
                    .append("|").append(ipgrule.getEndvalue())
                    .append("|").append(ipgrule.getIpgsecuritylevel().getDescription())
                    .append("|").append(ipgrule.getDescription())
                    .append("|").append(ipgtriggerseq.getDescription())
                    .append("|").append(ipgrule.getIpgstatus().getDescription());
            audit.setNewvalue(newValue.toString());
            audit.setCreatedtime(sysDate);
            audit.setLastupdatedtime(sysDate);
            
            session.save(ipgrule);
            
            audit.setDescription("Rule ID "+ipgrule.getRuleid()+" added by " + sysUser.getUsername());
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

    public String deleteRule(RuleInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgrule u = (Ipgrule) session.get(Ipgrule.class, new Long(inputBean.getRuleId().trim()));
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

    public String updateRule(RuleInputBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            Ipgrule ipgrule = (Ipgrule) session.get(Ipgrule.class, Long.parseLong(inputBean.getRuleId().trim()));

            if (ipgrule != null) {
                /**
                 * for audit old value
                 */
                IpgruletypeId id = new IpgruletypeId();
                id.setRuletypecode(inputBean.getRuleType());
                id.setRulescopecode(inputBean.getRuleScope());
                Ipgruletype ipgruletype = (Ipgruletype) session.get(Ipgruletype.class, id);
                Ipgtriggersequence ipgtriggerseq = (Ipgtriggersequence) session.get(Ipgtriggersequence.class, (new BigDecimal(ipgrule.getTriggersequenceid())));
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(ipgrule.getIpgrulescope().getDescription())
                        .append("|").append(ipgrule.getOperator())
                        .append("|").append(ipgruletype.getDescription())
                        .append("|").append(ipgrule.getStartvalue())
                        .append("|").append(ipgrule.getEndvalue())
                        .append("|").append(ipgrule.getIpgsecuritylevel().getDescription())
                        .append("|").append(ipgrule.getDescription())
                        .append("|").append(ipgtriggerseq.getDescription())
                        .append("|").append(ipgrule.getIpgstatus().getDescription());
                ipgrule.setDescription(inputBean.getDescription().trim());
                ipgrule.setEndvalue(inputBean.getEndValue().trim());
//                ipgrule.setPriority(inputBean.getPriority().trim());
                ipgrule.setStartvalue(inputBean.getStartValue().trim());
                Long triggersequenceid = Long.parseLong(inputBean.getTriggersequence());
                ipgrule.setTriggersequenceid(triggersequenceid);

                Ipgrulescope ipgrulescope = (Ipgrulescope) session.get(Ipgrulescope.class, inputBean.getRuleScope());
                ipgrule.setIpgrulescope(ipgrulescope);

//                Ipgruletype ruletype = new Ipgruletype();
//                ruletype.setRuletypecode(inputBean.getRuleType());
//                ipgrule.setIpgruletype(ruletype);
                ipgrule.setRuletypecode(inputBean.getRuleType());

                Ipgsecuritylevel ipgsecurity = (Ipgsecuritylevel) session.get(Ipgsecuritylevel.class, inputBean.getSecurityLevel());
                ipgrule.setIpgsecuritylevel(ipgsecurity);

                ipgrule.setOperator(inputBean.getOperator());

                ipgrule.setLastupdatedtime(sysDate);
                ipgrule.setLastupdateduser(audit.getLastupdateduser());
                /**
                 * for audit new value
                 */
                id.setRuletypecode(inputBean.getRuleType());
                id.setRulescopecode(inputBean.getRuleScope());
                Ipgruletype ruletype = (Ipgruletype) session.get(Ipgruletype.class, id);
                Ipgtriggersequence triggerseq = (Ipgtriggersequence) session.get(Ipgtriggersequence.class, (new BigDecimal(ipgrule.getTriggersequenceid())));
                StringBuilder newValue = new StringBuilder();
                newValue.append(ipgrule.getIpgrulescope().getDescription())
                        .append("|").append(ipgrule.getOperator())
                        .append("|").append(ruletype.getDescription())
                        .append("|").append(ipgrule.getStartvalue())
                        .append("|").append(ipgrule.getEndvalue())
                        .append("|").append(ipgrule.getIpgsecuritylevel().getDescription())
                        .append("|").append(ipgrule.getDescription())
                        .append("|").append(triggerseq.getDescription())
                        .append("|").append(ipgrule.getIpgstatus().getDescription());
                audit.setNewvalue(newValue.toString());
                audit.setOldvalue(oldValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.update(ipgrule);

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
    public List<RuleBean> getSearchList(RuleInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<RuleBean> dataList = new ArrayList<RuleBean>();
        Session session = null;
        try {

            long count = 0;
            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(u.ruleid) from Ipgrule as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }

                String sqlSearch = "from Ipgrule u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    RuleBean rulebean = new RuleBean();
                    Ipgrule ipgrule = (Ipgrule) it.next();

                    try {
                        rulebean.setRuleid(new Long(ipgrule.getRuleid()).toString());
                    } catch (NullPointerException npe) {
                        rulebean.setRuleid("--");
                    }
                    try {
                        rulebean.setDescription(ipgrule.getDescription());
                    } catch (NullPointerException npe) {
                        rulebean.setDescription("--");
                    }
                    try {
                        rulebean.setEndvalue(ipgrule.getEndvalue());
                    } catch (NullPointerException npe) {
                        rulebean.setEndvalue("--");
                    }
                    try {
                        rulebean.setStartvalue(ipgrule.getStartvalue());
                    } catch (NullPointerException npe) {
                        rulebean.setStartvalue("--");
                    }
//                    try {
//                        rulebean.setPriority(ipgrule.getPriority());
//                    } catch (NullPointerException npe) {
//                        rulebean.setPriority("--");
//                    }
                    try {
                        IpgruletypeId id = new IpgruletypeId();
                        id.setRulescopecode(ipgrule.getIpgrulescope().getRulescopecode());
                        id.setRuletypecode(ipgrule.getRuletypecode());
                        Ipgruletype ruleType = (Ipgruletype) session.get(Ipgruletype.class, id);
                        rulebean.setRuletype(ruleType.getDescription());
                    } catch (Exception npe) {
                        rulebean.setRuletype("--");
                    }
                    try {
                        rulebean.setRulescope(ipgrule.getIpgrulescope().getDescription());
                    } catch (NullPointerException npe) {
                        rulebean.setRulescope("--");
                    }
                    try {
                        rulebean.setSecuritylevel(ipgrule.getIpgsecuritylevel().getDescription());
                    } catch (NullPointerException npe) {
                        rulebean.setSecuritylevel("--");
                    }
                    try {
                        rulebean.setOperator(ipgrule.getOperator());
                    } catch (NullPointerException npe) {
                        rulebean.setOperator("--");
                    }

                    try {
                        BigDecimal triggersequenceid = new BigDecimal(ipgrule.getTriggersequenceid());
                        Ipgtriggersequence triggersequence = (Ipgtriggersequence) session.get(Ipgtriggersequence.class, triggersequenceid);
                        rulebean.setTriggersequenceid(triggersequence.getDescription());
                    } catch (Exception e) {
                        rulebean.setTriggersequenceid("--");
                    }

                    try {
                        rulebean.setStatus(ipgrule.getIpgstatus().getDescription());
                    } catch (NullPointerException npe) {
                        rulebean.setStatus("--");
                    }

                    try {
                        rulebean.setCreatedtime(ipgrule.getCreatedtime().toString());
                    } catch (NullPointerException npe) {
                        rulebean.setCreatedtime("--");
                    }

                    rulebean.setFullCount(count);

                    dataList.add(rulebean);
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

    private String makeWhereClause(RuleInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getRuleScope() != null && !inputBean.getRuleScope().trim().isEmpty()) {
            where += " and u.ipgrulescope.rulescopecode = '" + inputBean.getRuleScope().trim() + "'";
        }
        if (inputBean.getOperator() != null && !inputBean.getOperator().trim().isEmpty()) {
            where += " and u.operator = '" + inputBean.getOperator().trim() + "'";
        }
        if (inputBean.getRuleType() != null && !inputBean.getRuleType().trim().isEmpty()) {
            where += " and u.ruletypecode in (select id.ruletypecode from Ipgruletype where description = '" + inputBean.getRuleType().trim() + "')";
            where += " and u.ipgrulescope.rulescopecode in (select id.rulescopecode from Ipgruletype where description = '" + inputBean.getRuleType().trim() + "')";
        }
        if (inputBean.getStartValue() != null && !inputBean.getStartValue().trim().isEmpty()) {
            where += " and lower(u.startvalue) like lower('%" + inputBean.getStartValue().trim() + "%')";
        }
        if (inputBean.getEndValue() != null && !inputBean.getEndValue().trim().isEmpty()) {
            where += " and lower(u.endvalue) like lower('%" + inputBean.getEndValue().trim() + "%')";
        }
        if (inputBean.getPriority() != null && !inputBean.getPriority().trim().isEmpty()) {
            where += " and lower(u.priority) like lower('%" + inputBean.getPriority().trim() + "%')";
        }
        if (inputBean.getSecurityLevel() != null && !inputBean.getSecurityLevel().trim().isEmpty()) {
            where += " and u.ipgsecuritylevel.securitylevel = '" + inputBean.getSecurityLevel().trim() + "'";
        }
        if (inputBean.getDescription() != null && !inputBean.getDescription().trim().isEmpty()) {
            where += " and lower(u.description) like lower('%" + inputBean.getDescription().trim() + "%')";
        }
        if (inputBean.getTriggersequence() != null && !inputBean.getTriggersequence().trim().isEmpty()) {
            where += " and u.triggersequenceid = '" + inputBean.getTriggersequence().trim() + "'";
        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().trim().isEmpty()) {
            where += " and u.ipgstatus.statuscode = '" + inputBean.getStatus().trim() + "'";
        }
        return where;
    }

    public Ipgrule findRuleByID(String ruleid) throws Exception {
        Ipgrule ipgrule = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgrule as u where u.ruleid=:ruleid";
            Query query = session.createQuery(sql).setString("ruleid", ruleid);
            ipgrule = (Ipgrule) query.list().get(0);

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
        return ipgrule;

    }

    public List<RuleOperatorListBean> findOperatorByRuleScope(String scope) throws Exception {
        List<RuleOperatorListBean> operators = new ArrayList<RuleOperatorListBean>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgruleoperator u where u.id.rulescopecode=:rulescopecode";
            Query query = session.createQuery(sql).setString("rulescopecode", scope);
            Iterator it = query.iterate();

            while (it.hasNext()) {
                RuleOperatorListBean operator = new RuleOperatorListBean();
                Ipgruleoperator ipgoperator = (Ipgruleoperator) it.next();
                operator.setKey(ipgoperator.getId().getRuleoperatorcode());
                operator.setValue(ipgoperator.getId().getRuleoperatorcode());
                operators.add(operator);
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
        return operators;
    }

    public List<RuleTypeListBean> findRuleTypeByRuleScope(String scope) throws Exception {
        List<RuleTypeListBean> ruletypes = new ArrayList<RuleTypeListBean>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgruletype u where u.id.rulescopecode=:rulescopecode";
            Query query = session.createQuery(sql).setString("rulescopecode", scope);
            Iterator it = query.iterate();

            while (it.hasNext()) {
                RuleTypeListBean ruletype = new RuleTypeListBean();
                Ipgruletype ipgruletype = (Ipgruletype) it.next();
                ruletype.setKey(ipgruletype.getId().getRuletypecode());
                ruletype.setValue(ipgruletype.getDescription());
                ruletypes.add(ruletype);
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
        return ruletypes;
    }

}
