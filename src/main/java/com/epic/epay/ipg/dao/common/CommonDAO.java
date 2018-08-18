/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.common;

import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
import com.epic.epay.ipg.util.mapping.Ipgcommonconfig;
import com.epic.epay.ipg.util.mapping.Ipgcommonfilepath;
import com.epic.epay.ipg.util.mapping.Ipgcountry;
import com.epic.epay.ipg.util.mapping.Ipgcurrency;
import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgpage;
import com.epic.epay.ipg.util.mapping.Ipgresponsecodes;
import com.epic.epay.ipg.util.mapping.Ipgriskprofile;
import com.epic.epay.ipg.util.mapping.Ipgrulescope;
import com.epic.epay.ipg.util.mapping.Ipgruletype;
import com.epic.epay.ipg.util.mapping.Ipgsection;
import com.epic.epay.ipg.util.mapping.Ipgsecuritylevel;
import com.epic.epay.ipg.util.mapping.Ipgservicecharge;
import com.epic.epay.ipg.util.mapping.Ipgservicechargetype;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.mapping.Ipgtriggersequence;
import com.epic.epay.ipg.util.mapping.Ipguserrole;
import com.epic.epay.ipg.util.mapping.Ipguserroletype;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author chanuka
 */
public class CommonDAO {

    // get system date from database
    public static Date getSystemDate(Session session) throws Exception {
        Date sysDateTime = null;
        try {

            String sql = "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') as a from dual";
            Query query = session.createSQLQuery(sql);
            List l = query.list();
            String stime = (String) l.get(0);
            sysDateTime = Timestamp.valueOf(stime);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
        }
        return sysDateTime;
    }

    // get status list
    public List<Ipgstatus> getDefultStatusList(String statusCode)
            throws Exception {

        List<Ipgstatus> statusList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgstatus as s where s.ipgstatuscategory.statuscategorycode =:statuscategorycode"
                    + " order by s.description asc";
            Query query = session.createQuery(sql).setString(
                    "statuscategorycode", statusCode);
            statusList = query.list();

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
        return statusList;
    }

    // get merchant list
    public List<Ipgmerchant> getMerchantList(String statusCode)
            throws Exception {

        List<Ipgmerchant> merchantList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgmerchant as s where s.ipgstatus.statuscode =:activemerchant";
            Query query = session.createQuery(sql).setString(
                    "activemerchant", statusCode);
            merchantList = query.list();

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
        return merchantList;
    }

    // get status list
    public Ipgpage getPageDescription(String pageCode) throws Exception {

        Ipgpage pageBean = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgpage as s where s.pagecode =:pagecode";
            Query query = session.createQuery(sql).setString("pagecode",
                    pageCode);
            pageBean = (Ipgpage) query.list().get(0);

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
        return pageBean;
    }

    public Ipgsection getSectionDescription(String sectioncode) throws Exception {

        Ipgsection sectionBean = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgsection as s where s.sectioncode =:sectioncode";
            Query query = session.createQuery(sql).setString("sectioncode",
                    sectioncode);
            sectionBean = (Ipgsection) query.list().get(0);

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
        return sectionBean;
    }

    public Ipgtask getTaskDescription(String taskcode) throws Exception {

        Ipgtask taskBean = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgtask as s where s.taskcode =:taskcode";
            Query query = session.createQuery(sql).setString("taskcode",
                    taskcode);
            taskBean = (Ipgtask) query.list().get(0);

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
        return taskBean;
    }

    public Ipguserrole getUserRoleDescription(String userrolecode) throws Exception {

        Ipguserrole userroleBean = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipguserrole as s where s.userrolecode =:userrolecode";
            Query query = session.createQuery(sql).setString("userrolecode",
                    userrolecode);
            userroleBean = (Ipguserrole) query.list().get(0);

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
        return userroleBean;
    }

    public Ipgsection getSectionOfPage(String pageCode, String userRole)
            throws Exception {

        Ipgsection sectionBean = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select s.ipgsection from Ipgsectionpage as s where s.id.pagecode =:pagecode and s.id.userrolecode=:userrolecode ";
            Query query = session.createQuery(sql)
                    .setString("pagecode", pageCode)
                    .setString("userrolecode", userRole);
            sectionBean = (Ipgsection) query.list().get(0);

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
        return sectionBean;
    }

    public List<Ipguserrole> getAllUserRoleList() throws Exception {

        List<Ipguserrole> userroleList = new ArrayList<Ipguserrole>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipguserrole";
            Query query = session.createQuery(sql);
            userroleList = query.list();

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
        return userroleList;
    }

    public List<Ipguserrole> getUserRoleList() throws Exception {
        // TODO Auto-generated method stub

        List<Ipguserrole> userRoleList = new ArrayList<Ipguserrole>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipguserrole as u order by lower(u.description) asc";
            Query query = session.createQuery(sql);
            userRoleList = query.list();

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
        return userRoleList;
    }

    public List<Ipgcardassociation> getDefultCardAssociationList()
            throws Exception {

        List<Ipgcardassociation> cardAList = new ArrayList<Ipgcardassociation>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgcardassociation ";
            Query query = session.createQuery(sql);
            cardAList = query.list();

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
        return cardAList;
    }

    /**
     * @return
     */
    public List<Ipguserroletype> getUserRoleTypeList() throws Exception {
        List<Ipguserroletype> userRoleTypes = new ArrayList<Ipguserroletype>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Ipguserroletype.class);
            userRoleTypes = (List<Ipguserroletype>) criteria.list();

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
        return userRoleTypes;
    }

    public List<Ipgsection> getSectionList() throws Exception {
        List<Ipgsection> sectionList = new ArrayList<Ipgsection>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgsection as u order by lower(u.description) asc";
            Query query = session.createQuery(hql);
            sectionList = query.list();

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
        return sectionList;
    }

    public List<Ipgpage> getPageList() throws Exception {
        List<Ipgpage> pageList = new ArrayList<Ipgpage>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgpage as u order by lower(u.description) asc";
            Query query = session.createQuery(hql);
            pageList = query.list();

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
        return pageList;
    }

    public List<Ipgtask> getTaskList() throws Exception {
        List<Ipgtask> taskList = new ArrayList<Ipgtask>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgtask as u order by lower(u.description) asc";
            Query query = session.createQuery(hql);
            taskList = query.list();

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
        return taskList;
    }

    public List<Ipgservicechargetype> getServiceChargeTypeList()
            throws Exception {
        List<Ipgservicechargetype> serviceChargetypeList = new ArrayList<Ipgservicechargetype>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgservicechargetype ";
            Query query = session.createQuery(hql);
            serviceChargetypeList = query.list();

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
        return serviceChargetypeList;
    }

    public List<Ipgcurrency> getDefaultCurrencyList() throws Exception {
        List<Ipgcurrency> currencyList = new ArrayList<Ipgcurrency>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgcurrency ";
            Query query = session.createQuery(hql);
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

    public List<Ipgruletype> getRuleTypeList() throws Exception {
        List<Ipgruletype> ruleTypeList = new ArrayList<Ipgruletype>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgruletype";
            Query query = session.createQuery(hql);
            ruleTypeList = query.list();

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
        return ruleTypeList;
    }

    public List<Ipgrulescope> getRuleScopeList() throws Exception {
        List<Ipgrulescope> ruleScopeList = new ArrayList<Ipgrulescope>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgrulescope";
            Query query = session.createQuery(hql);
            ruleScopeList = query.list();

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
        return ruleScopeList;
    }

    public List<Ipgsecuritylevel> getSecurityLevelList(String statuscode)
            throws Exception {
        List<Ipgsecuritylevel> securityLevelList = new ArrayList<Ipgsecuritylevel>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgsecuritylevel as u where u.ipgstatus.statuscode =:status";
            Query query = session.createQuery(sql).setString("status",
                    statuscode);
            securityLevelList = query.list();

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
        return securityLevelList;
    }

    public List<Ipgmerchant> getMerchantList() throws Exception {
        List<Ipgmerchant> merchantList = new ArrayList<Ipgmerchant>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgmerchant";
            Query query = session.createQuery(hql);
            merchantList = query.list();

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
        return merchantList;
    }

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

    public List<Ipgservicecharge> getServiceChargeList() throws Exception {
        List<Ipgservicecharge> serviceChargeList = new ArrayList<Ipgservicecharge>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgservicecharge";
            Query query = session.createQuery(hql);
            serviceChargeList = query.list();

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
        return serviceChargeList;
    }

    /**
     * @return @throws Exception
     */
    public List<Ipgcountry> getCountryList() throws Exception {
        List<Ipgcountry> countryList = new ArrayList<Ipgcountry>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Ipgcountry.class);
            countryList = (List<Ipgcountry>) criteria.list();

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
        return countryList;
    }

    public List<Ipgriskprofile> getRiskProfileList() throws Exception {
        List<Ipgriskprofile> rpList = new ArrayList<Ipgriskprofile>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Ipgriskprofile.class);
            rpList = (List<Ipgriskprofile>) criteria.list();

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
        return rpList;
    }

    public List<Ipgtriggersequence> getTriggerSequenceList() throws Exception {
        List<Ipgtriggersequence> merchantList = new ArrayList<Ipgtriggersequence>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgtriggersequence";
            Query query = session.createQuery(hql);
            merchantList = query.list();

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
        return merchantList;
    }

    /**
     * @param statusCategoryBatchTransactionSettle
     * @return
     * @throws Exception
     */
    public List<Ipgstatus> getBatchStatusList(
            String statusCategoryBatchTransactionSettle) throws Exception {
        List<Ipgstatus> statusList = new ArrayList<Ipgstatus>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Ipgstatus.class);
            criteria.add(Restrictions.eq("ipgstatuscategory.statuscategorycode", statusCategoryBatchTransactionSettle));
            statusList = (List<Ipgstatus>) criteria.list();

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
        return statusList;
    }

    //get common file paths
    public Ipgcommonfilepath getCommonFilePathBySystemOSType(String osType) throws Exception {

//        String filepath = "";
        Ipgcommonfilepath ipgcommonfilepath = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgcommonfilepath as c where c.ostype=:ostype";
            Query query = session.createQuery(sql);
            query.setString("ostype", osType);

            if (query.list().size() > 0) {
                ipgcommonfilepath = (Ipgcommonfilepath) query.list().get(0);
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

    public static String findMerNameById() throws Exception {
        Session hbsession = null;
        String merName = "";
        HttpServletRequest request = ServletActionContext.getRequest();
        Ipgsystemuser sysUser = (Ipgsystemuser) request.getSession(false).getAttribute(SessionVarlist.SYSTEMUSER);
        try {

            hbsession = HibernateInit.sessionFactory.openSession();
            String sql = "";
            Query query = null;
            sql = "select merchantname from Ipgmerchant as m where m.merchantid =:merchantID";
            query = hbsession.createQuery(sql).setString("merchantID", sysUser.getMerchantid());
            List<String> list = query.list();
            merName = list.get(0);

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                hbsession.flush();
                hbsession.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return merName;
    }

    public static Date getSystemDateLogin() throws Exception {
        Date sysDateTime = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT NOW()";
            Query query = session.createSQLQuery(sql);
            List l = query.list();
            sysDateTime = (Date) l.get(0);
//            sysDateTime = Timestamp.valueOf(stime);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return sysDateTime;
    }

    public String getStatusByprefix(String srefix) throws Exception {
        Ipgstatus st = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            st = (Ipgstatus) session.get(Ipgstatus.class, srefix);
        } catch (Exception he) {
            throw he;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return st.getDescription();
    }

    public List<String> getRuleTypeSearchList() throws Exception {
        List<String> ruletype = new ArrayList<String>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "select distinct r.description from Ipgruletype as r";
            Query query = session.createQuery(hql);
            ruletype = query.list();

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
        return ruletype;
    }

    public Ipgcommonconfig getCommonConfigValueByCode(String code) throws Exception {

        Ipgcommonconfig ipgcommon = new Ipgcommonconfig();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql1 = "from Ipgcommonconfig as u where u.code=:code";
            Query query1 = session.createQuery(sql1).setString("code", code);
            if (query1.list().size() > 0) {
                ipgcommon = (Ipgcommonconfig) query1.list().get(0);
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
        return ipgcommon;

    }

    public Ipgresponsecodes getPesponseByCode(String responseCode) throws Exception {

        Ipgresponsecodes response = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgresponsecodes as s where s.code =:code";
            Query query = session.createQuery(sql).setString("code",
                    responseCode);
            response = (Ipgresponsecodes) query.list().get(0);

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
        return response;
    }

}
