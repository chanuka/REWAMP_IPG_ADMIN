/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.controlpanel.usermanagement;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.epic.epay.ipg.bean.controlpanel.usermanagement.PasswordResetInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.varlist.MessageVarList;

/**
 *
 * @author chanuka
 */
public class PasswordResetDAO {

    //password reset user 
    public String UpdatePasswordReset(PasswordResetInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

//            Mpisystemuser u = (Mpisystemuser) session.get(Mpisystemuser.class, inputBean.getHusername().trim());

            String sql = "from Ipgsystemuser as u where u.username=:username";
            Query query = session.createQuery(sql).setString("username", inputBean.getHusername().trim());
            Ipgsystemuser u = (Ipgsystemuser) query.list().get(0);


            if (u != null) {


                u.setPassword(inputBean.getRenewpwd());
                u.setLastupdateduser(audit.getLastupdateduser());
                u.setLastupdatedtime(sysDate);

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

    //find user by username
    public Ipgsystemuser findUserById(String username) throws Exception {

    	Ipgsystemuser user = new Ipgsystemuser();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgsystemuser as u join fetch u.ipgstatus join fetch u.ipguserroleByUserrolecode where u.username=:username";
            Query query = session.createQuery(sql).setString("username", username);
            user = (Ipgsystemuser) query.list().get(0);

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
        return user;

    }
}
