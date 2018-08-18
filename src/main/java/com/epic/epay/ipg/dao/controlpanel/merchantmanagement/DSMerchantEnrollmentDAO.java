/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.DSMerchantEnrollmentBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.DSMerchantEnrollmentInputBean;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgaddress;
import com.epic.epay.ipg.util.mapping.Ipgbin;
import com.epic.epay.ipg.util.mapping.Ipgcommonfilepath;
import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgmerchantcredential;
import com.epic.epay.ipg.util.mapping.IpgmerchantcredentialId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author badrika
 */
public class DSMerchantEnrollmentDAO {

    public List<DSMerchantEnrollmentBean> getMerchantList(DSMerchantEnrollmentInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<DSMerchantEnrollmentBean> dataList = new ArrayList<DSMerchantEnrollmentBean>();
        Session session = null;
        try {

            long count = 0;

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(u.id.merchantid) from Ipgmerchantcredential as u where u.ipgcardassociation.cardassociationcode=:cardaso";
            Query queryCount = session.createQuery(sqlCount).setString("cardaso", inputBean.getCardassociation());

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                String sqlSearch = "from Ipgmerchantcredential as u where u.ipgcardassociation.cardassociationcode=:cardaso"; // + orderBy
                Query querySearch = session.createQuery(sqlSearch).setString("cardaso", inputBean.getCardassociation());
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    DSMerchantEnrollmentBean dsmerenbean = new DSMerchantEnrollmentBean();
                    Ipgmerchantcredential ipgmerchantcredential = (Ipgmerchantcredential) it.next();

                    if (inputBean.isAgain() == true) {
                        try {
                            dsmerenbean.setMerchantid(ipgmerchantcredential.getId().getMerchantid());
                        } catch (NullPointerException npe) {
                            dsmerenbean.setMerchantid("--");
                        }
                        try {
                            dsmerenbean.setMerchantname(ipgmerchantcredential.getIpgmerchant().getMerchantname());
                        } catch (NullPointerException npe) {
                            dsmerenbean.setMerchantname("--");
                        }
                        try {
                            dsmerenbean.setCardassociation(ipgmerchantcredential.getIpgcardassociation().getDescription());
                        } catch (NullPointerException npe) {
                            dsmerenbean.setCardassociation("--");
                        }
                        try {
                            dsmerenbean.setDsstatus(ipgmerchantcredential.getDirectoryserver());
                        } catch (NullPointerException npe) {
                            dsmerenbean.setDsstatus("--");
                        }

                        dsmerenbean.setFullCount(count);

                        dataList.add(dsmerenbean);

                    } else {
                        if (ipgmerchantcredential.getDirectoryserver() == null) {
                            try {
                                dsmerenbean.setMerchantid(ipgmerchantcredential.getId().getMerchantid());
                            } catch (NullPointerException npe) {
                                dsmerenbean.setMerchantid("--");
                            }
                            try {
                                dsmerenbean.setMerchantname(ipgmerchantcredential.getIpgmerchant().getMerchantname());
                            } catch (NullPointerException npe) {
                                dsmerenbean.setMerchantname("--");
                            }
                            try {
                                dsmerenbean.setCardassociation(ipgmerchantcredential.getIpgcardassociation().getDescription());
                            } catch (NullPointerException npe) {
                                dsmerenbean.setCardassociation("--");
                            }
                            try {
                                dsmerenbean.setDsstatus(ipgmerchantcredential.getDirectoryserver());
                            } catch (NullPointerException npe) {
                                dsmerenbean.setDsstatus("--");
                            }

                            dataList.add(dsmerenbean);
                        }
                    }

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

    public Ipgaddress getAddress(String mid) throws Exception {

        Ipgaddress addresBean = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            //query 1
            String sql1 = "select s.ipgaddress.addressid from Ipgmerchant as s where s.merchantid =:mid ";//select s.ipgaddress.addressid 
            Query query1 = session.createQuery(sql1).setString("mid", mid);
            long id = (Long) query1.list().get(0);
            //query 2            
            String sql = "from Ipgaddress as s where s.addressid =:aid ";
            Query query = session.createQuery(sql).setString("aid", String.valueOf(id));
            addresBean = (Ipgaddress) query.list().get(0);

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
        return addresBean;
    }

    public Ipgmerchantcredential getCredentialPassword(String mid,String cardAssociation) throws Exception {

        Ipgmerchantcredential credentialBean = null;
        IpgmerchantcredentialId ipgmerchantcredentialId = new IpgmerchantcredentialId();
        Session session = null;
        Transaction txn = null;
        try {
            ipgmerchantcredentialId.setMerchantid(mid);
            ipgmerchantcredentialId.setCardassociationcode(cardAssociation);
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            credentialBean = (Ipgmerchantcredential) session.get(Ipgmerchantcredential.class, ipgmerchantcredentialId);

            //update directory server
            if (credentialBean != null) {
                credentialBean.setDirectoryserver("YES");
                session.update(credentialBean);
                txn.commit();
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
        return credentialBean;
    }

    //to get os name (windows or linux)
    public static String getOS_Type() {

        String osType = "";
        String osName = "";
        osName = System.getProperty("os.name", "").toLowerCase();

        // For WINDOWS
        if (osName.contains("windows")) {
            osType = "WINDOWS";
        } else {
            // For LINUX
            if (osName.contains("linux")) {
                osType = "LINUX";
            } else {
            }
        }

        return osType;
    }

    public String getCertificatePath(String osType) throws Exception {

        String certPath = "";
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgcommonfilepath as u where u.ostype =:os";
            Query query = session.createQuery(sql).setString("os", osType);

            Iterator it = query.iterate();
            if (it.hasNext()) {
                Ipgcommonfilepath ipgcomfilepath = (Ipgcommonfilepath) it.next();

                certPath = ipgcomfilepath.getDsenrollrpt();

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
        return certPath;

    }
    
    public List<Ipgbin> getAcquirerBin(String cardassociation) throws Exception {

        List<Ipgbin> binList = new ArrayList<Ipgbin>();        
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgbin as u where u.ipgcardassociation.cardassociationcode =:ca "
                    + "and u.ipgstatusByOnusstatus.statuscode =:onus ";
            Query query = session.createQuery(sql).setString("ca", cardassociation).setString("onus", "YES");

            Iterator it = query.iterate();
            while (it.hasNext()) {
                
                Ipgbin binBean = (Ipgbin) it.next();                
                binList.add(binBean);
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
        return binList;

    }

}
