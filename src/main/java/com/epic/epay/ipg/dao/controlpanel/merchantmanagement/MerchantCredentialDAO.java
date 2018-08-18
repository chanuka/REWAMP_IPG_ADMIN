/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantCredentialBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantCredentialInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgmerchantcredential;
import com.epic.epay.ipg.util.mapping.IpgmerchantcredentialId;
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
 * @created on : Nov 14, 2013, 10:26:53 AM
 * @author : thushanth
 *
 */
public class MerchantCredentialDAO {

    public String insertMerchantCredential(MerchantCredentialInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgmerchantcredential) session.get(Ipgmerchantcredential.class,
                    new IpgmerchantcredentialId(inputBean.getMerchantID().trim(), inputBean.getCardAssociation())) == null) {
                txn = session.beginTransaction();

                Ipgmerchantcredential ipgmerchantcredential = new Ipgmerchantcredential();

//                                        Ipgcardassociation ipgcardassociation = new Ipgcardassociation();
//			                ipgcardassociation.setCardassociationcode(inputBean.getCardAssociation());
                IpgmerchantcredentialId ipgmercreid = new IpgmerchantcredentialId();
                ipgmercreid.setMerchantid(inputBean.getMerchantID().trim());
                ipgmercreid.setCardassociationcode(inputBean.getCardAssociation());

                ipgmerchantcredential.setId(ipgmercreid);
//			                ipgmerchantcredential.setMerchantid(inputBean.getMerchantID().trim());
//			                ipgmerchantcredential.setIpgcardassociation(ipgcardassociation);

                ipgmerchantcredential.setUsername(inputBean.getUserName().trim());
                ipgmerchantcredential.setPassword(inputBean.getPassword().trim());

                ipgmerchantcredential.setCreatedtime(sysDate);
                ipgmerchantcredential.setLastupdatedtime(sysDate);
                ipgmerchantcredential.setLastupdateduser(audit.getLastupdateduser());
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                Ipgmerchant ipgmerchant =  (Ipgmerchant) session.get(Ipgmerchant.class, inputBean.getMerchantID().trim());
                Ipgcardassociation ipgcardassociation = (Ipgcardassociation) session.get(Ipgcardassociation.class, inputBean.getCardAssociation().trim());
                newValue.append(ipgmerchant.getMerchantname())
                        .append("|").append(ipgcardassociation.getDescription())
                        .append("|").append(ipgmerchantcredential.getUsername());
                audit.setNewvalue(newValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);
                
                session.save(audit);
                session.save(ipgmerchantcredential);
                txn.commit();
            } else {
                message = MessageVarList.COMMON_ALREADY_EXISTS;
            }
        } //        catch (org.hibernate.exception.ConstraintViolationException conex) {
        //            if (txn != null) {
        //                txn.rollback();
        //            }
        ////                                       throw conex;
        //            message = MessageVarList.MERCHANT_CREDENTIAL_USERNAME_EXISTS;
        //        }
        catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            try {
//			                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return message;
    }

    public String deleteMerchantCredential(MerchantCredentialInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            System.out.println("inputBean.getMerchantID()**********" + inputBean.getMerchantID());
            System.out.println("inputBean.getCardAssociation()*********" + inputBean.getCardAssociation());

            Ipgmerchantcredential u = (Ipgmerchantcredential) session.get(Ipgmerchantcredential.class, new IpgmerchantcredentialId(inputBean.getMerchantID(), inputBean.getCardAssociation()));

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

    public String updateMerchantCredential(MerchantCredentialInputBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            System.out.println("inputBean.getMerchantID()**********" + inputBean.getMerchantID());
            System.out.println("inputBean.getCardAssociation()*********" + inputBean.getCardAssociation());
            
            Ipgmerchantcredential u = (Ipgmerchantcredential) session.get(Ipgmerchantcredential.class, new IpgmerchantcredentialId(inputBean.getMerchantID(), inputBean.getCardAssociation()));

            if (u != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(u.getIpgmerchant().getMerchantname())
                        .append("|").append(u.getIpgcardassociation().getDescription())
                        .append("|").append(u.getUsername());
                

                IpgmerchantcredentialId ipgmercreid = new IpgmerchantcredentialId();
                ipgmercreid.setMerchantid(inputBean.getMerchantID().trim());
                ipgmercreid.setCardassociationcode(inputBean.getCardAssociation());

                u.setId(ipgmercreid);

//					                Ipgcardassociation ipgcardassociation = new Ipgcardassociation();
//					                ipgcardassociation.setCardassociationcode(inputBean.getCardAssociation());
//					                u.setIpgcardassociation(ipgcardassociation);	
                u.setUsername(inputBean.getUserName().trim());
                u.setPassword(inputBean.getPassword());

                u.setLastupdatedtime(sysDate);
                u.setLastupdateduser(audit.getLastupdateduser());
                
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                Ipgmerchant ipgmerchant =  (Ipgmerchant) session.get(Ipgmerchant.class, u.getIpgmerchant().getMerchantid().trim());
                Ipgcardassociation ipgcardassociation = (Ipgcardassociation) session.get(Ipgcardassociation.class, u.getIpgcardassociation().getCardassociationcode().trim());
                newValue.append(ipgmerchant.getMerchantname())
                        .append("|").append(ipgcardassociation.getDescription())
                        .append("|").append(u.getUsername());
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
    public List<MerchantCredentialBean> getSearchList(MerchantCredentialInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<MerchantCredentialBean> dataList = new ArrayList<MerchantCredentialBean>();
        Session session = null;
        try {

            long count = 0;
            String where =this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(u.id.merchantid) from Ipgmerchantcredential as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }

                String sqlSearch = "from Ipgmerchantcredential u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    MerchantCredentialBean merchantcredentialbean = new MerchantCredentialBean();
                    Ipgmerchantcredential ipgmerchantcredential = (Ipgmerchantcredential) it.next();

                    try {
                        merchantcredentialbean.setMerchantId(ipgmerchantcredential.getIpgmerchant().getMerchantid());
                    } catch (NullPointerException npe) {
                        merchantcredentialbean.setMerchantId("--");
                    }
                    try {
                        merchantcredentialbean.setMerchantname(ipgmerchantcredential.getIpgmerchant().getMerchantname());
                    } catch (NullPointerException npe) {
                        merchantcredentialbean.setMerchantname("--");
                    }
                    try {
                        merchantcredentialbean.setCardassociation(ipgmerchantcredential.getIpgcardassociation().getDescription());
                    } catch (NullPointerException npe) {
                        merchantcredentialbean.setCardassociation("--");
                    }
                    try {
                        merchantcredentialbean.setCardassociationCode(ipgmerchantcredential.getIpgcardassociation().getCardassociationcode());
                    } catch (NullPointerException npe) {
                        merchantcredentialbean.setCardassociationCode("--");
                    }
                    try {
                        merchantcredentialbean.setUsername(ipgmerchantcredential.getUsername());
                    } catch (NullPointerException npe) {
                        merchantcredentialbean.setUsername("--");
                    }
                    try {
                        merchantcredentialbean.setCreatedtime(ipgmerchantcredential.getCreatedtime().toString());
                    } catch (NullPointerException npe) {
                        merchantcredentialbean.setCreatedtime("--");
                    }


                    merchantcredentialbean.setFullCount(count);

                    dataList.add(merchantcredentialbean);
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
    
    private String makeWhereClause(MerchantCredentialInputBean inputBean){
        String where = "1=1";
        if (inputBean.getUserName()!= null && !inputBean.getUserName().trim().isEmpty()) {
            where += " and lower(u.username) like lower('%" + inputBean.getUserName().trim() + "%')";
        }
        if (inputBean.getMerchantID()!= null && !inputBean.getMerchantID().trim().isEmpty()) {
            where += " and u.id.merchantid = '" + inputBean.getMerchantID().trim() + "'";
        }
        if (inputBean.getCardAssociation()!= null && !inputBean.getCardAssociation().trim().isEmpty()) {
            where += " and u.id.cardassociationcode = '" + inputBean.getCardAssociation().trim() + "'";
        }
        return where;
    }

    public Ipgmerchantcredential findMerchantChargeByID(String merchantID, String carsass) throws Exception {
        Ipgmerchantcredential ipgmerchantcredential = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            System.out.println("merchantID**********" + merchantID);
            System.out.println("carsass*********" + carsass);
            
            String sql = "from Ipgmerchantcredential as u where u.id.merchantid=:merchantid and u.id.cardassociationcode=:ca";
            Query query = session.createQuery(sql).setString("merchantid", merchantID).setString("ca", carsass);
            ipgmerchantcredential = (Ipgmerchantcredential) query.list().get(0);

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
        return ipgmerchantcredential;

    }

    public boolean isCardAssociationDesExists(String cardassociation)
            throws Exception {

        List<Ipgcardassociation> list = null;
        Ipgcardassociation ipgcardassociation;
        Session session = null;
        boolean existsstatus = false;
        try {

            session = HibernateInit.sessionFactory.openSession();
            //     String sql = "select u.Ipgcardassociation from Ipgmerchantcredential as u where u.Ipgcardassociation.description in (select c.description from Ipgcardassociation as c where c.cardassociationcode=:cardassociation)";

            String sql = "select u.cardassociationcode from Ipgmerchantcredential u,ipgcardassociation ca "
                    + " where ca.cardassociationcode=u.cardassociationcode and ca.description in "
                    + " (select c.description from Ipgcardassociation c where c.cardassociationcode=:cardassociation) ";

            Query query = session.createSQLQuery(sql)
                    .setString("cardassociation", cardassociation);
            list = query.list();
            if (list.size() > 0) {
                existsstatus = true;
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
        return existsstatus;
    }
}
