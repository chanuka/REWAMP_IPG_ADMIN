/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.CommonConfigBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.CommonConfigInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcommonconfig;
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
 * @created :Oct 29, 2013, 9:25:38 AM
 * @author :thushanth
 */
public class CommonConfigDAO {

    public String updateCommonConfig(CommonConfigInputBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgcommonconfig commonconfig = (Ipgcommonconfig) session.get(Ipgcommonconfig.class, inputBean.getCode().trim());

            if (commonconfig != null) {
                
               /**
                * for audit old value
                */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(commonconfig.getCode())
                        .append("|").append(commonconfig.getDescription())
                        .append("|").append(commonconfig.getValue());
                
                commonconfig.setDescription(inputBean.getDescription().trim());
                commonconfig.setValue(inputBean.getValue().trim());                
                /**
                * for audit new value
                */
                StringBuilder newValue = new StringBuilder();
                newValue.append(commonconfig.getCode())
                        .append("|").append(commonconfig.getDescription())
                        .append("|").append(commonconfig.getValue());
                
                audit.setNewvalue(newValue.toString());
                audit.setOldvalue(oldValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.update(commonconfig);

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
    public List<CommonConfigBean> getSearchList(CommonConfigInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<CommonConfigBean> dataList = new ArrayList<CommonConfigBean>();
        Session session = null;
        try {

            long count = 0;
            String where =this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(commonconfig.code) from Ipgcommonconfig as commonconfig where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by commonconfig.createdtime desc ";
                }
                String sqlSearch = "from Ipgcommonconfig commonconfig where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    CommonConfigBean commonbean = new CommonConfigBean();
                    Ipgcommonconfig ipgcommon = (Ipgcommonconfig) it.next();

                    try {
                        commonbean.setCode(ipgcommon.getCode());
                    } catch (NullPointerException npe) {
                        commonbean.setCode("--");
                    }
                    try {
                        commonbean.setDescription(ipgcommon.getDescription());
                    } catch (NullPointerException npe) {
                        commonbean.setDescription("--");
                    }
                    try {
                        commonbean.setValue(ipgcommon.getValue());
                    } catch (NullPointerException npe) {
                        commonbean.setValue("--");
                    }
                    try {
                        commonbean.setCreatedtime(ipgcommon.getCreatedtime().toString().substring(0, ipgcommon.getCreatedtime().toString().length() - 2));
                    } catch (NullPointerException npe) {
                        commonbean.setCreatedtime("--");
                    }
                    commonbean.setFullCount(count);

                    dataList.add(commonbean);
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
    
    private String makeWhereClause(CommonConfigInputBean inputBean){
        String where = "1=1";
        if (inputBean.getCode()!= null && !inputBean.getCode().trim().isEmpty()) {
            where += " and lower(commonconfig.code) like lower('%" + inputBean.getCode().trim() + "%')";
        }
        if (inputBean.getDescription()!= null && !inputBean.getDescription().trim().isEmpty()) {
            where += " and lower(commonconfig.description) like lower('%" + inputBean.getDescription().trim() + "%')";
        }
        if (inputBean.getValue()!= null && !inputBean.getValue().trim().isEmpty()) {
            where += " and lower(commonconfig.value) like lower('%" + inputBean.getValue().trim() + "%')";
        }       
        
        return where;
    }

    public Ipgcommonconfig findCommonConfigById(String code) throws Exception {
        Ipgcommonconfig ipgcommoncofig = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgcommonconfig as commonconfig where commonconfig.code=:code";
            Query query = session.createQuery(sql).setString("code", code);
            ipgcommoncofig = (Ipgcommonconfig) query.list().get(0);

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
        return ipgcommoncofig;

    }
}
