/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.date;

import com.epic.epay.ipg.util.common.HibernateInit;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author badrika
 */
public class SystemDateTime {   
    
    //get system date and time from the database
    public static Timestamp getSystemDataAndTime() throws Exception {
        Timestamp systemDateTime = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = " SELECT SYSDATE FROM DUAL ";
            SQLQuery query = session.createSQLQuery(sql);
            
            if (query.list().size() > 0) {
                systemDateTime = (Timestamp) query.list().get(0);
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
        return systemDateTime;
    }
}
