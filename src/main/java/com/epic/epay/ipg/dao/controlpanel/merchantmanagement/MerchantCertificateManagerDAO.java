/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.merchantmanagement;

import java.util.Date;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcommonfilepath;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;

/**
 *
 * @created on : Nov 22, 2013, 11:51:35 AM
 * @author : Thushanth
 *
 */
public class MerchantCertificateManagerDAO {

    public void insertMerchantCertificateMgt(Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;

        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);
            txn = session.beginTransaction();

            audit.setCreatedtime(sysDate);
            audit.setLastupdatedtime(sysDate);

            session.save(audit);

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

                certPath = ipgcomfilepath.getCertificates();

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
    
    public String getKeyStorePath(String osType) throws Exception {

        String keyStorePath = "";
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgcommonfilepath as u where u.ostype =:os";
            Query query = session.createQuery(sql).setString("os", osType);

            Iterator it = query.iterate();
            if (it.hasNext()) {
                Ipgcommonfilepath ipgcomfilepath = (Ipgcommonfilepath) it.next();

                keyStorePath = ipgcomfilepath.getKeystorepath();

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
        return keyStorePath;

    }

    //to get os name (windows or linux)
    public static String getOS_Type() {

        String osType = "";
        String osName = "";
        osName = System.getProperty("os.name", "").toLowerCase();

        // For WINDOWS
        if (osName.contains("windows")) {
            osType = "WINDOWS";
        } else if (osName.contains("linux")) {
            osType = "LINUX";
        } else if (osName.contains("sunos")) {
            osType = "SUNOS";
        }


        return osType;
    }
}
