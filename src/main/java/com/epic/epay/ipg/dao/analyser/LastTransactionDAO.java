/**
 * 
 */
package com.epic.epay.ipg.dao.analyser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgtransactionhistory;
import com.epic.epay.ipg.util.varlist.CommonVarList;
/**
 * @created on 	Dec 5, 2013, 1:24:25 PM
 * @author 		thushanth
 *
 */
public class LastTransactionDAO {
	
	public String getLatestTxn() throws Exception{		

	    String successTxnCode= CommonVarList.TWELVETH_COMPLETE_TXN_STATUS;
	    String Txnid=null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql =" select id.ipgtransactionid from Ipgtransaction as u where u.ipgstatus.statuscode=:statuscode order by createdtime desc and rownum = 1  ";
            Query query = session.createQuery(sql).setString("statuscode", successTxnCode);
            Iterator itCount = query.iterate();
            if(itCount.hasNext()){
            Txnid=(String)itCount.next();
            }else{
                Txnid="";
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
		return Txnid; 
	}
	
	
	public List<Ipgtransactionhistory> getLastTxnHis(String txnid) throws Exception{
		
        Session session = null;
        List<Ipgtransactionhistory> list =null;       
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql ="from Ipgtransactionhistory as u where u.ipgtransactionid=:txnid order by analytime asc";
            Query query = session.createQuery(sql).setString("txnid", txnid);
            list =query.list();

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
        return list;
		
	}
	

}
