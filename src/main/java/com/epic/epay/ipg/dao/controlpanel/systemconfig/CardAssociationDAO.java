/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.CardAssociationBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.CardAssociationInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.common.PartialList;
import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
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
 * @author ruwan_e
 *
 */
public class CardAssociationDAO {

    /**
     * @param inputBean
     * @param rows
     * @param from
     * @param sortIndex
     * @param sortOrder
     * @return
     * @throws Exception
     */
    public PartialList<CardAssociationBean> getSearchList(CardAssociationInputBean inputBean,int rows, int from,String sortIndex, String sortOrder) throws Exception {
        List<Ipgcardassociation> searchList = null;
        List<CardAssociationBean> dataBeanList = null;
        Session session = null;
        long fullCount = 0;
        if ("".equals(sortIndex.trim())) {
            sortIndex = null;
        }
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session
                    .createCriteria(Ipgcardassociation.class);
            this.makeWhereClause(criteria, inputBean);
            if (sortIndex != null && sortOrder != null) {
                if (sortOrder.equals("asc")) {
                    criteria.addOrder(Order.asc(sortIndex));
                }
                if (sortOrder.equals("desc")) {
                    criteria.addOrder(Order.desc(sortIndex));
                }

            }else{
                criteria.addOrder(Order.desc("createdtime"));
            }

            fullCount = criteria.list().size();

            criteria.setFirstResult(from);
            criteria.setMaxResults(rows);

            searchList = criteria.list();
            dataBeanList = new ArrayList<CardAssociationBean>();

            for (Ipgcardassociation m : searchList) {
                dataBeanList.add(new CardAssociationBean(m));
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

        PartialList<CardAssociationBean> list = new PartialList<CardAssociationBean>();

        list.setList(dataBeanList);
        list.setFullCount(fullCount);

        return list;
    }
    
    private void makeWhereClause(Criteria criteria,CardAssociationInputBean inputBean) {
           
            if (inputBean.getCardAssociationCode()!= null && !inputBean.getCardAssociationCode().trim().isEmpty()) {
                criteria.add(Restrictions.like("cardassociationcode", inputBean.getCardAssociationCode().trim(),MatchMode.ANYWHERE));
            }
            if (inputBean.getDescription()!= null && !inputBean.getDescription().trim().isEmpty()) {
                criteria.add(Restrictions.like("description", inputBean.getDescription().trim(), MatchMode.ANYWHERE));
            }
            if (inputBean.getSortKey()!= null && !inputBean.getSortKey().trim().isEmpty()) {
                criteria.add(Restrictions.eq("sortkey", Integer.parseInt(inputBean.getSortKey().trim())));
            }

        }
    /**
     * @param inputBean
     * @param audit
     * @return
     * @throws Exception
     */
    public String insertCardAssociation(CardAssociationInputBean inputBean,
            Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgcardassociation) session.get(Ipgcardassociation.class, inputBean.getCardAssociationCode()
                    .trim()) == null) {
                txn = session.beginTransaction();

                Ipgcardassociation ipgcardassociation = new Ipgcardassociation();
                ipgcardassociation.setCardassociationcode(inputBean.getCardAssociationCode().trim());
                ipgcardassociation.setDescription(inputBean.getDescription().trim());
                ipgcardassociation.setCardimageurl(inputBean.getCardImageURLFileName());
                ipgcardassociation.setVerifiedimageurl(inputBean.getVerifieldImageURLFileName());
                ipgcardassociation.setSortkey(new Integer(inputBean.getSortKey()));

                ipgcardassociation.setCreatedtime(sysDate);
                ipgcardassociation.setLastupdatedtime(sysDate);
                ipgcardassociation.setLastupdateduser(audit.getLastupdateduser());
                
                /**
                 * for new audit value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(ipgcardassociation.getCardassociationcode())
                        .append("|").append(ipgcardassociation.getDescription())
                        .append("|").append(ipgcardassociation.getCardimageurl())
                        .append("|").append(ipgcardassociation.getVerifiedimageurl())
                        .append("|").append(ipgcardassociation.getSortkey());
                audit.setNewvalue(newValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(ipgcardassociation);

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

    public Ipgcardassociation findCardAssociationById(String cardassociationcode) throws Exception {
        Ipgcardassociation ipgcardassociation = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            ipgcardassociation = (Ipgcardassociation) session.get(Ipgcardassociation.class, cardassociationcode);

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
        return ipgcardassociation;
    }

    public String updateCardAssociation(CardAssociationInputBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgcardassociation u = (Ipgcardassociation) session.get(Ipgcardassociation.class, inputBean.getCardAssociationCode());

            if (u != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(u.getCardassociationcode())
                        .append("|").append(u.getDescription())
                        .append("|").append(u.getCardimageurl() != null ? u.getCardimageurl() : "")
                        .append("|").append(u.getVerifiedimageurl() != null ? u.getVerifiedimageurl() : "")
                        .append("|").append(u.getSortkey());
                
                if (inputBean.getVerifieldImageURLFileName() != null) {
                    u.setVerifiedimageurl(inputBean.getVerifieldImageURLFileName());
                }
                if (inputBean.getCardImageURLFileName() != null) {
                    u.setCardimageurl(inputBean.getCardImageURLFileName());
                }
                u.setSortkey(Integer.parseInt(inputBean.getSortKey()));

                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(u.getCardassociationcode())
                        .append("|").append(u.getDescription())
                        .append("|").append(u.getCardimageurl() != null ? u.getCardimageurl() : "")
                        .append("|").append(u.getVerifiedimageurl() != null ? u.getVerifiedimageurl() : "")
                        .append("|").append(u.getSortkey());
                audit.setNewvalue(newValue.toString());
                audit.setOldvalue(oldValue.toString());
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

    public String deleteCardAssociation(CardAssociationInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgcardassociation u = (Ipgcardassociation) session.get(
                    Ipgcardassociation.class, inputBean.getCardAssociationCode().trim());
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
