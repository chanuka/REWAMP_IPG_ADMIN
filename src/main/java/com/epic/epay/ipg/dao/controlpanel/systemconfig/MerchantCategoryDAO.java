/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.MerchantCategoryBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.MerchantCategoryInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgmerchantcategory;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
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
 * 30/10/2013
 */
public class MerchantCategoryDAO {

    /**
     * @param inputBean
     * @param rows
     * @param from
     * @param sortIndex
     * @param sortOrder
     * @return
     * @throws Exception
     */
    public List<MerchantCategoryBean> getSearchList(
            MerchantCategoryInputBean inputBean, int rows, int from,
            String sortIndex, String sortOrder) throws Exception {
        List<Ipgmerchantcategory> searchList = null;
        List<MerchantCategoryBean> dataBeanList = null;
        Session session = null;
        long fullCount = 0;
        if ("".equals(sortIndex.trim())) {
            sortIndex = null;
        }
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session
                    .createCriteria(Ipgmerchantcategory.class);
            this.makeWhereClause(criteria, inputBean);

            if (sortIndex != null && sortOrder != null) {
                if (sortOrder.equals("asc")) {
                    criteria.addOrder(Order.asc(sortIndex));
                }
                if (sortOrder.equals("desc")) {
                    criteria.addOrder(Order.desc(sortIndex));
                }
            } else {
                criteria.addOrder(Order.desc("createtime"));
            }

            fullCount = criteria.list().size();

            criteria.setFirstResult(from);
            criteria.setMaxResults(rows);

            searchList = criteria.list();
            dataBeanList = new ArrayList<MerchantCategoryBean>();

            for (Ipgmerchantcategory imc : searchList) {

                MerchantCategoryBean mcBean = new MerchantCategoryBean();

                try {
                    mcBean.setMcc(imc.getMcc());
                } catch (NullPointerException npe) {
                    mcBean.setMcc("--");
                }
                try {
                    mcBean.setDescription(imc.getDescription());
                } catch (NullPointerException npe) {
                    mcBean.setDescription("--");
                }
                try {
                    mcBean.setStatusdes(imc.getIpgstatus().getDescription());
                } catch (NullPointerException npe) {
                    mcBean.setStatusdes("--");
                }
                try {
                    String createdTime = imc.getCreatetime().toString();
                    mcBean.setCreatedtime(createdTime.substring(0, createdTime.length() - 2));
                } catch (NullPointerException npe) {
                    mcBean.setCreatedtime("--");
                }
                mcBean.setFullCount(fullCount);

                dataBeanList.add(mcBean);
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

    private void makeWhereClause(Criteria criteria, MerchantCategoryInputBean inputBean) {

        if (inputBean.getMerchantCategoryCode() != null && !inputBean.getMerchantCategoryCode().trim().isEmpty()) {
            criteria.add(Restrictions.like("mcc", inputBean.getMerchantCategoryCode().trim(), MatchMode.ANYWHERE));
        }
        if (inputBean.getDescription() != null && !inputBean.getDescription().trim().isEmpty()) {
            criteria.add(Restrictions.like("description", inputBean.getDescription().trim(), MatchMode.ANYWHERE));
        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().trim().isEmpty()) {
            criteria.add(Restrictions.eq("ipgstatus.statuscode", inputBean.getStatus().trim()));
        }

    }

    /**
     * @param merchantCategoryCode
     * @return
     * @throws Exception
     */
    public Ipgmerchantcategory findMCById(String merchantCategoryCode)
            throws Exception {
        Ipgmerchantcategory imc = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            imc = (Ipgmerchantcategory) session.get(Ipgmerchantcategory.class,
                    merchantCategoryCode);

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
        return imc;
    }

    /**
     * @param inputBean
     * @param audit
     * @return
     * @throws Exception
     */
    public String insertMerchantCategory(MerchantCategoryInputBean inputBean,
            Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgmerchantcategory) session.get(Ipgmerchantcategory.class,
                    inputBean.getMerchantCategoryCode().trim()) == null) {
                txn = session.beginTransaction();

                Ipgmerchantcategory imc = new Ipgmerchantcategory();
                imc.setMcc(inputBean.getMerchantCategoryCode().trim());
                imc.setDescription(inputBean.getDescription().trim());

                Ipgstatus ipgstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus());
                imc.setIpgstatus(ipgstatus);

                imc.setCreatetime(sysDate);
                imc.setLastupdatedtime(sysDate);
                imc.setLastupdateduser(audit.getLastupdateduser());
                
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(imc.getMcc())
                        .append("|").append(imc.getDescription())
                        .append("|").append(imc.getIpgstatus().getDescription());
                audit.setNewvalue(newValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(imc);

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
     * @param inputBean
     * @param audit
     * @return
     * @throws Exception
     */
    public String updateMerchantCategory(MerchantCategoryInputBean inputBean,
            Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgmerchantcategory u = (Ipgmerchantcategory) session.get(
                    Ipgmerchantcategory.class, inputBean
                            .getMerchantCategoryCode().trim());

            if (u != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(u.getMcc())
                        .append("|").append(u.getDescription())
                        .append("|").append(u.getIpgstatus().getDescription());

                u.setDescription(inputBean.getDescription());

                Ipgstatus ipgstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus());
                u.setIpgstatus(ipgstatus);

                u.setLastupdateduser(audit.getLastupdateduser());
                u.setLastupdatedtime(sysDate);

                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(u.getMcc())
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

    /**
     * @param inputBean
     * @param audit
     * @return
     * @throws Exception
     */
    public String deleteMerchantCategory(MerchantCategoryInputBean inputBean,
            Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgmerchantcategory u = (Ipgmerchantcategory) session.get(Ipgmerchantcategory.class, inputBean
                    .getMerchantCategoryCode().trim());
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
