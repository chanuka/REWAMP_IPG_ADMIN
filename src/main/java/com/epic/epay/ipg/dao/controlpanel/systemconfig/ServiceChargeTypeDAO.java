/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.ServiceChargeTypeDataBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.ServiceChargeTypeInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgservicechargetype;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author Asitha Liyanawaduge
 *
 * 01/11/2013
 */
public class ServiceChargeTypeDAO {

    /**
     * @param inputBean
     * @param rows
     * @param from
     * @param sortIndex
     * @param sortOrder
     * @return
     * @throws Exception
     */
    public List<ServiceChargeTypeDataBean> getSearchList(
            ServiceChargeTypeInputBean inputBean, int rows, int from,
            String sortIndex, String sortOrder) throws Exception {
        List<Ipgservicechargetype> searchList = null;
        List<ServiceChargeTypeDataBean> dataBeanList = null;
        Session session = null;
        long fullCount = 0;
        if ("".equals(sortIndex.trim())) {
            sortIndex = null;
        }
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session
                    .createCriteria(Ipgservicechargetype.class);
            this.makeWhereClause(criteria, inputBean);

            if (sortIndex != null && sortOrder != null) {
                if (sortOrder.equals("asc")) {
                    criteria.addOrder(Order.asc(sortIndex));
                }
                if (sortOrder.equals("desc")) {
                    criteria.addOrder(Order.desc(sortIndex));
                }
            } else {
                criteria.addOrder(Order.desc("createdtime"));
            }

            fullCount = criteria.list().size();

            criteria.setFirstResult(from);
            criteria.setMaxResults(rows);

            searchList = criteria.list();
            dataBeanList = new ArrayList<ServiceChargeTypeDataBean>();

            for (Ipgservicechargetype sct : searchList) {

                ServiceChargeTypeDataBean serviceChargeType = new ServiceChargeTypeDataBean();

                try {
                    serviceChargeType.setServicechargetypecode(sct
                            .getServicechargetypecode());
                } catch (NullPointerException npe) {
                    serviceChargeType.setServicechargetypecode("--");
                }
                try {
                    serviceChargeType.setDescription(sct.getDescription());
                } catch (NullPointerException npe) {
                    serviceChargeType.setDescription("--");
                }
                try {
                    serviceChargeType.setSortkey(sct.getSortkey().toString());
                } catch (NullPointerException npe) {
                    serviceChargeType.setSortkey("--");
                }
                try {
                    String createdTime = sct.getCreatedtime().toString();
                    serviceChargeType.setCreatedtime(createdTime.substring(0, createdTime.length() - 2));
                } catch (NullPointerException npe) {
                    serviceChargeType.setCreatedtime("--");
                }
                serviceChargeType.setFullcount(fullCount);

                dataBeanList.add(serviceChargeType);
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

        return dataBeanList;
    }

    private void makeWhereClause(Criteria criteria, ServiceChargeTypeInputBean inputBean) {

        if (inputBean.getServiceChargeTypeCode() != null && !inputBean.getServiceChargeTypeCode().trim().isEmpty()) {
            criteria.add(Restrictions.like("servicechargetypecode", inputBean.getServiceChargeTypeCode().trim(), MatchMode.ANYWHERE));
        }
        if (inputBean.getDescription() != null && !inputBean.getDescription().trim().isEmpty()) {
            criteria.add(Restrictions.like("description", inputBean.getDescription().trim(), MatchMode.ANYWHERE));
        }
        if (inputBean.getSortKey() != null && !inputBean.getSortKey().trim().isEmpty()) {
            criteria.add(Restrictions.eq("sortkey", new BigDecimal(inputBean.getSortKey().trim())));
        }

    }

    /**
     * @param inputBean
     * @param audit
     * @return
     * @throws Exception
     */
    public String insertServiceChargeType(ServiceChargeTypeInputBean inputBean,
            Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgservicechargetype) session.get(Ipgservicechargetype.class,
                    inputBean.getServiceChargeTypeCode().trim()) == null) {
                txn = session.beginTransaction();

                Ipgservicechargetype sct = new Ipgservicechargetype();
                sct.setServicechargetypecode(inputBean
                        .getServiceChargeTypeCode().trim());
                sct.setDescription(inputBean.getDescription().trim());
                sct.setSortkey(new BigDecimal(inputBean.getSortKey()));
                sct.setCreatedtime(sysDate);
                sct.setLastupdatedtime(sysDate);
                sct.setLastupdateduser(audit.getLastupdateduser());
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(sct.getServicechargetypecode())
                        .append("|").append(sct.getDescription())
                        .append("|").append(sct.getSortkey());
                audit.setNewvalue(newValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(sct);

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

    /**
     * @param serviceChargeTypeCode
     * @return
     * @throws Exception
     */
    public Ipgservicechargetype findServiceChargeTypeById(
            String serviceChargeTypeCode) throws Exception {
        Ipgservicechargetype stc = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            stc = (Ipgservicechargetype) session.get(
                    Ipgservicechargetype.class, serviceChargeTypeCode);

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
        return stc;
    }

    /**
     * @param inputBean
     * @param audit
     * @return
     * @throws Exception
     */
    public String updateServiceChargeType(ServiceChargeTypeInputBean inputBean,
            Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgservicechargetype u = (Ipgservicechargetype) session.get(Ipgservicechargetype.class, inputBean
                    .getServiceChargeTypeCode().trim());

            if (u != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(u.getServicechargetypecode())
                        .append("|").append(u.getDescription())
                        .append("|").append(u.getSortkey());

                u.setDescription(inputBean.getDescription());
                u.setSortkey(new BigDecimal(inputBean.getSortKey()));
                u.setLastupdateduser(audit.getLastupdateduser());
                u.setLastupdatedtime(sysDate);

                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(u.getServicechargetypecode())
                        .append("|").append(u.getDescription())
                        .append("|").append(u.getSortkey());
                audit.setOldvalue(oldValue.toString());
                audit.setNewvalue(newValue.toString());
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

    /**
     * @param inputBean
     * @param audit
     * @return
     * @throws Exception
     */
    public String deleteServiceChargeType(ServiceChargeTypeInputBean inputBean,
            Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgservicechargetype u = (Ipgservicechargetype) session.get(
                    Ipgservicechargetype.class, inputBean
                            .getServiceChargeTypeCode().trim());
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

}
