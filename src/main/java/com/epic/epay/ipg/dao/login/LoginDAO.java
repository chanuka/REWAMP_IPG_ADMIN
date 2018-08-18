/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.login;

import com.epic.epay.ipg.bean.login.LoginBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgpage;
import com.epic.epay.ipg.util.mapping.Ipgpagetask;
import com.epic.epay.ipg.util.mapping.Ipgpasswordpolicy;
import com.epic.epay.ipg.util.mapping.Ipgsection;
import com.epic.epay.ipg.util.mapping.Ipgsectionpage;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author chanuka
 */
public class LoginDAO {

    public Ipgsystemuser findUserbyUsername(String username) throws Exception {

        Ipgsystemuser user = null;
        Session session = null;

        try {

            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgsystemuser as u join fetch u.ipgstatus join fetch u.ipguserroleByUserrolecode where u.username =:username";
            Query query = session.createQuery(sql).setString("username", username);

            user = (Ipgsystemuser) query.list().get(0);

        } catch (IndexOutOfBoundsException ibe) {
            user = null;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.flush();
                    session.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return user;
    }

    public HashMap<String, List<Ipgtask>> getPageTask(String userrole) throws Exception {

        List<Ipgpagetask> pageList = null;

        HashMap<String, List<Ipgtask>> secMap = new HashMap<String, List<Ipgtask>>();// key : page code value : task list
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgpagetask as u join fetch u.ipgpage join fetch u.ipgtask where u.id.userrolecode =:userrole";
            Query query = session.createQuery(sql).setString("userrole", userrole);

            pageList = query.list();
            //set data to map
            for (Ipgpagetask bean : pageList) {

                List<Ipgtask> taskList = secMap.get(bean.getIpgpage().getPagecode());
                if (taskList == null || taskList.isEmpty()) {
                    taskList = new ArrayList<Ipgtask>();
                    taskList.add(bean.getIpgtask());
                    secMap.put(bean.getIpgpage().getPagecode(), taskList);
                } else {
                    taskList.add(bean.getIpgtask());
                    secMap.put(bean.getIpgpage().getPagecode(), taskList);
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
        return secMap;
    }

    public HashMap<Ipgsection, List<Ipgpage>> getSectionPages(String userrole) throws Exception {

        List<Ipgsectionpage> sectionPList = null;
        HashMap<Ipgsection, List<Ipgpage>> secMap = new HashMap<Ipgsection, List<Ipgpage>>();// key : page code value : task list
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgsectionpage as u join fetch u.ipgsection join fetch u.ipgpage join fetch u.ipguserrole where u.id.userrolecode =:userrole and u.ipgpage.ipgstatus.statuscode=:statuscode order by u.ipgpage.sortkey";
            Query query = session.createQuery(sql).setString("userrole", userrole).setString("statuscode", CommonVarList.STATUS_ACTIVE);

            sectionPList = query.list();
            //set data to map
            for (Ipgsectionpage bean : sectionPList) {
                List<Ipgpage> pageList = secMap.get(bean.getIpgsection());

                if (pageList == null || pageList.isEmpty()) {
                    pageList = new ArrayList<Ipgpage>();
                    pageList.add(bean.getIpgpage());
                    secMap.put(bean.getIpgsection(), pageList);
                } else {
                    pageList.add(bean.getIpgpage());
                    secMap.put(bean.getIpgsection(), pageList);
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
        return secMap;
    }

    public boolean updateUser(LoginBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        boolean status = true;
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            String sql = "from Ipgsystemuser as u where u.username =:username";
            Query query = session.createQuery(sql).setString("username", inputBean.getUsername().trim());

            Ipgsystemuser u = (Ipgsystemuser) query.list().get(0);

//            Mpisystemuser u = (Mpisystemuser) session.get(Mpisystemuser.class, inputBean.getUsername().trim());
            if (u != null) {

                // When successful transaction logged date must be set  
//                 if (login) {
//                    u.setLoggeddate(sysDate);//set last logged date only in successfull login
//                }
                Ipgstatus s = new Ipgstatus();
                s.setStatuscode(inputBean.getStatus());

                u.setIpgstatus(s);
                u.setLastupdatedtime(sysDate);
                u.setPincount(inputBean.getAttempts());

                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.update(u);

                txn.commit();
            } else {
                status = false;
            }
        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.flush();
                    session.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return status;
    }

    public Ipgpasswordpolicy findPasswordPolicy() throws Exception {
        Ipgpasswordpolicy passwordpolicy = new Ipgpasswordpolicy();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String hql = "from Ipgpasswordpolicy as m";
            Query query = session.createQuery(hql);
            passwordpolicy = (Ipgpasswordpolicy) query.list().get(0);

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
        return passwordpolicy;
    }
}
