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

import com.epic.epay.ipg.bean.controlpanel.systemconfig.PasswordPolicyBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.PasswordPolicyInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgpasswordpolicy;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;

/**
 *  @created :Oct 25, 2013, 5:19:44 PM
 *  @author  :thushanth
 */
public class PasswordPolicyDAO {
	
    public Ipgpasswordpolicy getPasswordPolicyDetails() throws Exception {
        Ipgpasswordpolicy ipgpasswordpolicy = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgpasswordpolicy as u  join fetch u.ipgstatus ";

            Query query = session.createQuery(sql);

            ipgpasswordpolicy = (Ipgpasswordpolicy) query.list().get(0);

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
        return ipgpasswordpolicy;
    }

    public boolean isExistPasswordPolicy() throws Exception {
        long count = 0;
        boolean status = false;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "select count(ipgpasswordpolicyid) from Ipgpasswordpolicy";

            Query query = session.createQuery(sql);

            Iterator it = query.iterate();

            while (it.hasNext()) {
                count = (Long) it.next();
                if (count > 0) {
                    status = true;
                    break;
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
        return status;
    }

    public Ipgpasswordpolicy findPasswordPolicyById(String ipgpasswordpolicyid) throws Exception {
        Ipgpasswordpolicy ipgpasswordpolicy = new Ipgpasswordpolicy();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgpasswordpolicy as m join fetch m.ipgstatus where m.ipgpasswordpolicyid=:ipgpasswordpolicyid";
            Query query = session.createQuery(sql).setString("ipgpasswordpolicyid", ipgpasswordpolicyid);
            ipgpasswordpolicy = (Ipgpasswordpolicy) query.list().get(0);

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
        return ipgpasswordpolicy;
    }

    public String insertPasswordPolicy(PasswordPolicyInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgpasswordpolicy) session.get(Ipgpasswordpolicy.class, new BigDecimal(inputBean.getPasswordpolicyid().trim())) == null) {
                txn = session.beginTransaction();

                Ipgpasswordpolicy i = new Ipgpasswordpolicy();

                i.setIpgpasswordpolicyid(new BigDecimal(inputBean.getPasswordpolicyid()));
                i.setMinimumlength(new BigDecimal(inputBean.getMinimumlength()));
                i.setMaximumlength(new BigDecimal(inputBean.getMaximumlength()));
                i.setAllowedspecialcharacters(new BigDecimal(inputBean.getAllowedspacialchars()));
                i.setMinimumspecialcharacters(new BigDecimal(inputBean.getMinimumspacialchars()));
                i.setMinimumuppercasecharacters(new BigDecimal(inputBean.getMinimumuppercasechars()));
                i.setMinimumnumericalcharacters(new BigDecimal(inputBean.getMinimumnumericalchars()));
                i.setMinimumlowercasecharacters(new BigDecimal(inputBean.getMinimumlowercasechars()));
                i.setNoofpincount(new Integer(inputBean.getNoofPINcount()));
                
                Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                i.setIpgstatus(status);

                i.setCreatedtime(sysDate);
                i.setLastupdatedtime(sysDate);
                i.setLastupdateduser(audit.getLastupdateduser());
                
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(i.getIpgpasswordpolicyid())
                        .append("|").append(i.getMinimumlength())
                        .append("|").append(i.getMaximumlength())
                        .append("|").append(i.getAllowedspecialcharacters())
                        .append("|").append(i.getMinimumspecialcharacters())
                        .append("|").append(i.getMinimumuppercasecharacters())
                        .append("|").append(i.getMinimumnumericalcharacters())
                        .append("|").append(i.getMinimumlowercasecharacters())
                        .append("|").append(i.getNoofpincount())
                        .append("|").append(i.getIpgstatus().getDescription());
                audit.setNewvalue(newValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(i);

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

    public String updatePasswordPolicy(PasswordPolicyInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgpasswordpolicy i = (Ipgpasswordpolicy) session.get(Ipgpasswordpolicy.class, new BigDecimal(inputBean.getPasswordpolicyid().trim()));

            if (i != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(i.getIpgpasswordpolicyid())
                        .append("|").append(i.getMinimumlength())
                        .append("|").append(i.getMaximumlength())
                        .append("|").append(i.getAllowedspecialcharacters())
                        .append("|").append(i.getMinimumspecialcharacters())
                        .append("|").append(i.getMinimumuppercasecharacters())
                        .append("|").append(i.getMinimumnumericalcharacters())
                        .append("|").append(i.getMinimumlowercasecharacters())
                        .append("|").append(i.getNoofpincount())
                        .append("|").append(i.getIpgstatus().getDescription());

                i.setMinimumlength(new BigDecimal(inputBean.getMinimumlength()));
                i.setMaximumlength(new BigDecimal(inputBean.getMaximumlength()));
                i.setAllowedspecialcharacters(new BigDecimal(inputBean.getAllowedspacialchars()));
                i.setMinimumspecialcharacters(new BigDecimal(inputBean.getMinimumspacialchars()));
                i.setMinimumuppercasecharacters(new BigDecimal(inputBean.getMinimumuppercasechars()));
                i.setMinimumnumericalcharacters(new BigDecimal(inputBean.getMinimumnumericalchars()));
                i.setMinimumlowercasecharacters(new BigDecimal(inputBean.getMinimumlowercasechars()));
                i.setNoofpincount(new Integer(inputBean.getNoofPINcount()));
                
                Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                i.setIpgstatus(status);

                i.setCreatedtime(sysDate);
                i.setLastupdatedtime(sysDate);
                i.setLastupdateduser(audit.getLastupdateduser());
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(i.getIpgpasswordpolicyid())
                        .append("|").append(i.getMinimumlength())
                        .append("|").append(i.getMaximumlength())
                        .append("|").append(i.getAllowedspecialcharacters())
                        .append("|").append(i.getMinimumspecialcharacters())
                        .append("|").append(i.getMinimumuppercasecharacters())
                        .append("|").append(i.getMinimumnumericalcharacters())
                        .append("|").append(i.getMinimumlowercasecharacters())
                        .append("|").append(i.getNoofpincount())
                        .append("|").append(i.getIpgstatus().getDescription());
                audit.setNewvalue(newValue.toString());
                audit.setOldvalue(oldValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.update(i);

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

    public List<PasswordPolicyBean> getSearchList(PasswordPolicyInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<PasswordPolicyBean> dataList = new ArrayList<PasswordPolicyBean>();
        Session session = null;


        try {
            long count = 0;

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(ipgpasswordpolicyid) from Ipgpasswordpolicy as m";
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by createdtime desc ";
                }
                String sqlSearch = "from Ipgpasswordpolicy m " + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    PasswordPolicyBean passwordPolicyBean = new PasswordPolicyBean();
                    Ipgpasswordpolicy ipgpasswordpolicy = (Ipgpasswordpolicy) it.next();

                    try {
                        passwordPolicyBean.setPasswordpolicyid(ipgpasswordpolicy.getIpgpasswordpolicyid().toString());
                    } catch (NullPointerException npe) {
                        passwordPolicyBean.setPasswordpolicyid("--");
                    }
                    try {
                        passwordPolicyBean.setMaximumlength(ipgpasswordpolicy.getMaximumlength().toString());
                    } catch (NullPointerException npe) {
                        passwordPolicyBean.setMaximumlength("--");
                    }
                    try {
                        passwordPolicyBean.setMinimumlength(ipgpasswordpolicy.getMinimumlength().toString());
                    } catch (NullPointerException npe) {
                        passwordPolicyBean.setMinimumlength("--");
                    }
                    try {
                        passwordPolicyBean.setMinimumspacialchars(ipgpasswordpolicy.getMinimumspecialcharacters().toString());
                    } catch (NullPointerException npe) {
                        passwordPolicyBean.setMinimumspacialchars("--");
                    }
                    try {
                        passwordPolicyBean.setAllowedspacialchars(ipgpasswordpolicy.getAllowedspecialcharacters().toString());
                    } catch (NullPointerException npe) {
                        passwordPolicyBean.setAllowedspacialchars("--");
                    }
                    try {
                        passwordPolicyBean.setMinimumuppercasechars(ipgpasswordpolicy.getMinimumuppercasecharacters().toString());
                    } catch (NullPointerException npe) {
                        passwordPolicyBean.setMinimumuppercasechars("--");
                    }
                    try {
                        passwordPolicyBean.setMinimumnumericalchars(ipgpasswordpolicy.getMinimumnumericalcharacters().toString());
                    } catch (NullPointerException npe) {
                        passwordPolicyBean.setMinimumnumericalchars("--");
                    }
                    try {
                        passwordPolicyBean.setMinimumlowercasechars(ipgpasswordpolicy.getMinimumlowercasecharacters().toString());
                    } catch (NullPointerException npe) {
                        passwordPolicyBean.setMinimumlowercasechars("--");
                    }
                    try {
                        passwordPolicyBean.setNoofpincount(ipgpasswordpolicy.getNoofpincount().toString());
                    } catch (NullPointerException npe) {
                        passwordPolicyBean.setNoofpincount("--");
                    }
                    try {
                        passwordPolicyBean.setStatus(this.getStatusDes(ipgpasswordpolicy.getIpgstatus()).getDescription());
                    } catch (NullPointerException npe) {
                        passwordPolicyBean.setStatus("--");
                    }
                    try {
                        String createdTime = ipgpasswordpolicy.getCreatedtime().toString();
                        passwordPolicyBean.setCreatedtime(createdTime.substring(0, createdTime.length() - 2));
                    } catch (NullPointerException npe) {
                        passwordPolicyBean.setCreatedtime("--");
                    }
                    passwordPolicyBean.setFullCount(count);

                    dataList.add(passwordPolicyBean);
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
    
    public Ipgstatus getStatusDes(Ipgstatus statusCode) throws Exception {

        Ipgstatus status = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
           String sql = "from Ipgstatus as s where s.statuscode=:status";
            Query query = session.createQuery(sql).setString("status", statusCode.getStatuscode());
            
         //   String sql = "from Ipgstatus as s where s.statuscode=:statusCode";
          //  Query query = session.createQuery(sql);
            
            status = (Ipgstatus) query.list().get(0);

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
    
    public String generateToolTipMessage(Ipgpasswordpolicy ppolicy) {

//        String tooltip = null;
//
//        tooltip = "Maximum " + ppolicy.getMaximumlength() + " character(s) <br/>";
//        tooltip = tooltip + "At least " + ppolicy.getMinimumspecialcharacters() + " special character(s)<br/>";
//        tooltip = tooltip + "At least " + ppolicy.getMinimumuppercasecharacters() + " upper case character(s)<br/>";
//        tooltip = tooltip + "At least " + ppolicy.getMinimumlowercasecharacters() + " lower case character(s)<br/>";
//        tooltip = tooltip + "At least " + ppolicy.getMinimumnumericalcharacters() + " numeric character(s)<br/>";
//        if (ppolicy.getRepeatcharactersallow() < 2) {
//            tooltip = tooltip + "Password must not contain repeat characters <br/>";
//        } else {
//            tooltip = tooltip + "Maximum  " + ppolicy.getRepeatcharactersallow() + " repeat character(s)<br/>";
//        }
        StringBuilder tooltip = new StringBuilder();
        tooltip.append("Maximum ").append(ppolicy.getMaximumlength()).append(" character(s) <br/>")
                .append("Minimum ").append(ppolicy.getMinimumlength()).append(" character(s)<br/>")
                .append("At least ").append(ppolicy.getMinimumspecialcharacters()).append(" special character(s)<br/>")
                .append("At least ").append(ppolicy.getMinimumuppercasecharacters()).append(" upper case character(s)<br/>")
                .append("At least ").append(ppolicy.getMinimumlowercasecharacters()).append(" lower case character(s)<br/>")
                .append("At least ").append(ppolicy.getMinimumnumericalcharacters()).append(" numeric character(s)<br/>")
                .append("Allowed ").append(ppolicy.getAllowedspecialcharacters()).append(" type(s) of special character <br/>");

        return tooltip.toString();
    }


}
