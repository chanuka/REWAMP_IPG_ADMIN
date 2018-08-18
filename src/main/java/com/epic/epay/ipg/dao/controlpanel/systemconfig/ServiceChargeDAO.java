/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.ServiceChargeBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.ServiceChargeInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcurrency;
import com.epic.epay.ipg.util.mapping.Ipgservicecharge;
import com.epic.epay.ipg.util.mapping.Ipgservicechargetype;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.math.BigDecimal;
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
public class ServiceChargeDAO {

    public java.lang.String insertServiceCharge(ServiceChargeInputBean InputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            txn = session.beginTransaction();

            Ipgservicecharge servicecharge = new Ipgservicecharge();

            servicecharge.setDescription(InputBean.getDescription().trim());

            servicecharge.setValue(InputBean.getValue());
            servicecharge.setRate(new BigDecimal(InputBean.getRate().trim()));

            Ipgservicechargetype ipgservicechargetype = (Ipgservicechargetype) session.get(Ipgservicechargetype.class, InputBean.getChargetype());
            servicecharge.setIpgservicechargetype(ipgservicechargetype);

            Ipgcurrency ipgcurrency = (Ipgcurrency) session.get(Ipgcurrency.class, InputBean.getCurrency());
            servicecharge.setIpgcurrency(ipgcurrency);

            servicecharge.setLastupdatedtime(sysDate);
            servicecharge.setLastupdateduser(audit.getLastupdateduser());
            servicecharge.setCreatedtime(sysDate);

            /**
             * for audit new value
             */
            StringBuilder newValue = new StringBuilder();
            newValue.append(servicecharge.getIpgservicechargetype().getDescription())
                    .append("|").append(servicecharge.getDescription())
                    .append("|").append(servicecharge.getValue())
                    .append("|").append(servicecharge.getRate())
                    .append("|").append(servicecharge.getIpgcurrency().getDescription());
            audit.setNewvalue(newValue.toString());
            audit.setCreatedtime(sysDate);
            audit.setLastupdatedtime(sysDate);

            session.save(audit);
            session.save(servicecharge);

            txn.commit();

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

    public List<ServiceChargeBean> getSearchList(ServiceChargeInputBean inputBean, int rows, int from, String orderBy) throws Exception {

        List<ServiceChargeBean> dataList = new ArrayList<ServiceChargeBean>();
        Session session = null;
        try {
            long count = 0;

            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            Query queryCount = session.createQuery("select count(servicechargeid) from Ipgservicecharge as u where " + where);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }

                Query querySearch = session.createQuery("from Ipgservicecharge u where " + where
                        + orderBy);

                querySearch.setMaxResults(rows);
                querySearch.setFirstResult(from);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    ServiceChargeBean servicechargeBean = new ServiceChargeBean();
                    Ipgservicecharge servicecharge = (Ipgservicecharge) it.next();

                    try {
                        servicechargeBean.setServicechargecode(servicecharge.getServicechargeid().toString());
                    } catch (NullPointerException npe) {
                        servicechargeBean.setServicechargecode("--");
                    }
                    try {
                        servicechargeBean.setChargetype(servicecharge.getIpgservicechargetype().getDescription().toString());
                    } catch (NullPointerException npe) {
                        servicechargeBean.setChargetype("--");
                    }
                    try {
                        servicechargeBean.setDescription(servicecharge.getDescription());
                    } catch (NullPointerException npe) {
                        servicechargeBean.setDescription("--");
                    }
                    try {
                        servicechargeBean.setValue(servicecharge.getValue());
                    } catch (NullPointerException npe) {
                        servicechargeBean.setValue("--");
                    }
                    try {
                        servicechargeBean.setRate(servicecharge.getRate().toString());
                    } catch (NullPointerException npe) {
                        servicechargeBean.setRate("--");
                    }
                    try {
                        servicechargeBean.setCurrency(servicecharge.getIpgcurrency().getDescription());
                    } catch (NullPointerException npe) {
                        servicechargeBean.setCurrency("--");
                    }
                    try {
                        String createdTime = servicecharge.getCreatedtime().toString();
                        servicechargeBean.setCreatedtime(createdTime.substring(0, createdTime.length() - 2));
                    } catch (NullPointerException npe) {
                        servicechargeBean.setCreatedtime("--");
                    }

                    servicechargeBean.setFullCount(count);
                    dataList.add(servicechargeBean);
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

    private String makeWhereClause(ServiceChargeInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getChargetype() != null && !inputBean.getChargetype().trim().isEmpty()) {
            where += " and u.ipgservicechargetype.servicechargetypecode = '" + inputBean.getChargetype().trim() + "' ";
        }
        if (inputBean.getDescription() != null && !inputBean.getDescription().trim().isEmpty()) {
            where += " and lower(u.description) like lower('%" + inputBean.getDescription().trim() + "%')";
        }
        if (inputBean.getValue() != null && !inputBean.getValue().trim().isEmpty()) {
            where += " and lower(u.value) like lower('%" + inputBean.getValue().trim() + "%')";
        }
        if (inputBean.getRate() != null && !inputBean.getRate().trim().isEmpty()) {
            where += " and lower(u.rate) like lower('%" + inputBean.getRate().trim() + "%')";
        }
        if (inputBean.getCurrency() != null && !inputBean.getCurrency().trim().isEmpty()) {
            where += " and u.ipgcurrency.currencyisocode = '" + inputBean.getCurrency().trim() + "'";
        }
        return where;
    }

    public java.lang.String updateServiceCharge(ServiceChargeInputBean InputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgservicecharge servicecharge = (Ipgservicecharge) session.get(Ipgservicecharge.class, new BigDecimal(InputBean.getServicechargecode().trim()));

            if (servicecharge != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(servicecharge.getIpgservicechargetype().getDescription())
                        .append("|").append(servicecharge.getDescription())
                        .append("|").append(servicecharge.getValue())
                        .append("|").append(servicecharge.getRate())
                        .append("|").append(servicecharge.getIpgcurrency().getDescription());

                servicecharge.setDescription(InputBean.getDescription().trim());

                servicecharge.setValue(InputBean.getValue());
                servicecharge.setRate(new BigDecimal(InputBean.getRate().trim()));

                Ipgservicechargetype ipgservicechargetype = (Ipgservicechargetype) session.get(Ipgservicechargetype.class, InputBean.getChargetype());
                servicecharge.setIpgservicechargetype(ipgservicechargetype);

                Ipgcurrency ipgcurrency = (Ipgcurrency) session.get(Ipgcurrency.class, InputBean.getCurrency());
                servicecharge.setIpgcurrency(ipgcurrency);

                servicecharge.setLastupdateduser(audit.getLastupdateduser());
                servicecharge.setLastupdatedtime(sysDate);
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(servicecharge.getIpgservicechargetype().getDescription())
                        .append("|").append(servicecharge.getDescription())
                        .append("|").append(servicecharge.getValue())
                        .append("|").append(servicecharge.getRate())
                        .append("|").append(servicecharge.getIpgcurrency().getDescription());
                audit.setNewvalue(newValue.toString());
                audit.setOldvalue(oldValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.update(servicecharge);

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

    public String deleteServiceCharge(ServiceChargeInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgservicecharge u = (Ipgservicecharge) session.get(Ipgservicecharge.class, new BigDecimal(inputBean.getServicechargecode().trim()));
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

    public Ipgservicecharge findServiceChargeById(String servicechargecode) throws Exception {
        Ipgservicecharge servicecharge = new Ipgservicecharge();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgservicecharge as u join fetch u.ipgcurrency join fetch u.ipgservicechargetype  where u.servicechargeid=:servicechargeid";
            Query query = session.createQuery(sql).setString("servicechargeid", servicechargecode);
            servicecharge = (Ipgservicecharge) query.list().get(0);

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
        return servicecharge;
    }
    
    public String getNextServiceChargeId() throws Exception {
        String serviceChargeId = "";
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "select max(servicechargeid)+1 from Ipgservicecharge";
            Query query = session.createQuery(sql);
            Iterator itId = query.iterate();
            serviceChargeId = itId.next().toString();

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
        return serviceChargeId;
    }
}
