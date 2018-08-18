/**
 * 
 */
package com.epic.epay.ipg.dao.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.CommonFilePathBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcommonfilepath;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * Created on  :Dec 13, 2013, 10:19:40 AM
 * @author 	   :thushanth
 *
 */
public class TransactionBatchUploadDAO {	


	    //get merchant list
	    public List<Ipgstatus> getStatusList(String creditBatchTxn) throws Exception {

	        List<Ipgstatus> statusList = null;
	        Session session = null;
	        try {
	            session = HibernateInit.sessionFactory.openSession();
	            String sql = "from Ipgstatus as s where s.ipgstatuscategory.statuscategorycode =:creditBatchTxn";
	            Query query = session.createQuery(sql).setString("creditBatchTxn", creditBatchTxn);
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

	    public CommonFilePathBean getBatchTxnPath(String osType) throws Exception {

	        CommonFilePathBean bean = null;
	        Session session = null;

	        try {
	            session = HibernateInit.sessionFactory.openSession();
	            String sql = "from Ipgcommonfilepath as u where u.ostype =:os";
	            Query query = session.createQuery(sql).setString("os", osType);

	            Iterator it = query.iterate();
	            if (it.hasNext()) {
	                Ipgcommonfilepath path = (Ipgcommonfilepath) it.next();
	                bean = new CommonFilePathBean();

	                bean.setTxnLogPath(path.getTransactionlog());
	                bean.setCcbtxnFiles(path.getCcbtxnfiles());
	                bean.setCertificatePath(path.getCertificates());
	                bean.setErrorLogPath(path.getErrorlog());
	                bean.setInforLogPath(path.getInfolog());
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
	        return bean;

	    }

	    public String insertAudit(Ipgsystemaudit audit) throws Exception {

	        Session session = null;
	        Transaction txn = null;
	        String message = "";

	        try {
	            session = HibernateInit.sessionFactory.openSession();
	            txn = session.beginTransaction();
	            Date sysDate = CommonDAO.getSystemDate(session);

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
	        return message;
	    }
	}

