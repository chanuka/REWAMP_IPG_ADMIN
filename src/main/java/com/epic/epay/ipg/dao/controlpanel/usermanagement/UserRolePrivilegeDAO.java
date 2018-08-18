/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.controlpanel.usermanagement;

import com.epic.epay.ipg.bean.controlpanel.usermanagement.UserRolePageBean;
import com.epic.epay.ipg.bean.controlpanel.usermanagement.UserRolePrivilegeInputBean;
import com.epic.epay.ipg.bean.controlpanel.usermanagement.UserRoleTaskBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgpage;
import com.epic.epay.ipg.util.mapping.Ipgpagetask;
import com.epic.epay.ipg.util.mapping.IpgpagetaskId;
import com.epic.epay.ipg.util.mapping.Ipgsection;
import com.epic.epay.ipg.util.mapping.Ipgsectionpage;
import com.epic.epay.ipg.util.mapping.IpgsectionpageId;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.mapping.Ipguserrole;
import com.epic.epay.ipg.util.mapping.Ipguserrolesection;
import com.epic.epay.ipg.util.mapping.IpguserrolesectionId;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sivaganesan_t
 */
public class UserRolePrivilegeDAO {

    public void findSectionsByUser(UserRolePrivilegeInputBean bean) throws Exception {

        String code = bean.getUserRole();
        List<Ipgsection> newList = new ArrayList<Ipgsection>();
        List<Ipgsection> currentList = new ArrayList<Ipgsection>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql1 = "from Ipgsection as t where t.ipgstatus.statuscode=:status and sectioncode not in (select mp.id.sectioncode from Ipguserrolesection mp where mp.id.userrolecode=:userrolecode)";
            String sql2 = "from Ipgsection as t where t.ipgstatus.statuscode=:status and sectioncode in (select mp.id.sectioncode from Ipguserrolesection mp where mp.id.userrolecode=:userrolecode)";

            Query query1 = session.createQuery(sql1)
                    .setString("status", CommonVarList.STATUS_ACTIVE)
                    .setString("userrolecode", code);
            Query query2 = session.createQuery(sql2)
                    .setString("status", CommonVarList.STATUS_ACTIVE)
                    .setString("userrolecode", code);

            Ipguserrole p = (Ipguserrole) session.get(Ipguserrole.class, code);

            newList = (List<Ipgsection>) query1.list();
            currentList = (List<Ipgsection>) query2.list();

            for (Iterator<Ipgsection> it = newList.iterator(); it.hasNext();) {

                Ipgsection ipgsection = it.next();
                UserRoleTaskBean newsec = new UserRoleTaskBean();
                newsec.setKey(ipgsection.getSectioncode());
                newsec.setValue(ipgsection.getDescription());
                bean.getCurrentList().add(newsec);
            }

            for (Iterator<Ipgsection> it = currentList.iterator(); it.hasNext();) {

                Ipgsection ipgsection = it.next();
                UserRoleTaskBean oldsec = new UserRoleTaskBean();
                oldsec.setKey(ipgsection.getSectioncode());
                oldsec.setValue(ipgsection.getDescription());
                bean.getNewList().add(oldsec);
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
    }

    public List<Ipgsection> getSectionListByUserRole(String userRole) throws Exception {
        List<Ipgsection> sectionList = new ArrayList<Ipgsection>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            // String sql = "from Ipgsection as u where u.id.userrolecode=:userRole";
            String sql = "from Ipgsection as t where t.ipgstatus.statuscode=:status and sectioncode in (select mp.id.sectioncode from Ipguserrolesection mp where mp.id.userrolecode=:userrolecode)";
            Query query = session.createQuery(sql).setString("status", CommonVarList.STATUS_ACTIVE).setString("userrolecode", userRole);
            sectionList = (List<Ipgsection>) query.list();

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
        return sectionList;
    }

    public void findpageByUserRoleSection(UserRolePrivilegeInputBean bean) throws Exception {

        List<Ipgpage> pageList = new ArrayList<Ipgpage>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql1 = "from Ipgpage as p where p.ipgstatus.statuscode=:status and p.pagecode in (select sp.id.pagecode from Ipgsectionpage as sp where sp.id.userrolecode=:userrolecode and sp.id.sectioncode=:sectioncode)";

            Query query1 = session.createQuery(sql1).setString("status", CommonVarList.STATUS_ACTIVE).setString("userrolecode", bean.getUserRole()).setString("sectioncode", bean.getSectionpage());

            pageList = (List<Ipgpage>) query1.list();

            for (Iterator<Ipgpage> it = pageList.iterator(); it.hasNext();) {

                Ipgpage mpisection = it.next();
                UserRolePageBean userRolePageBean = new UserRolePageBean();
                userRolePageBean.setKey(mpisection.getPagecode());
                userRolePageBean.setValue(mpisection.getDescription());
                bean.getPageMap().add(userRolePageBean);
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
    }

    public void getPageListBySection(UserRolePrivilegeInputBean inputBean) throws Exception {

        String code = inputBean.getSection();
        String userrole = inputBean.getUserRole();
        List<Ipgpage> notAssignPageList = new ArrayList<Ipgpage>();
        List<Ipgpage> assignPageList = new ArrayList<Ipgpage>();
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql1 = "from Ipgpage as t where t.ipgstatus.statuscode=:status and t.pagecode not in (select mp.id.pagecode from Ipgsectionpage mp where mp.id.userrolecode=:userrolecode)";
            String sql2 = "from Ipgpage as t where t.ipgstatus.statuscode=:status and t.pagecode in (select mp.id.pagecode from Ipgsectionpage mp where mp.id.sectioncode=:sectioncode and mp.id.userrolecode=:userrolecode)";

            Query query1 = session.createQuery(sql1).setString("status", CommonVarList.STATUS_ACTIVE).setString("userrolecode", userrole);
            Query query2 = session.createQuery(sql2).setString("status", CommonVarList.STATUS_ACTIVE).setString("sectioncode", code).setString("userrolecode", userrole);

            notAssignPageList = (List<Ipgpage>) query1.list();
            assignPageList = (List<Ipgpage>) query2.list();

            for (Iterator<Ipgpage> it = notAssignPageList.iterator(); it.hasNext();) {

                Ipgpage page = it.next();
                UserRoleTaskBean newpage = new UserRoleTaskBean();
                newpage.setKey(page.getPagecode());
                newpage.setValue(page.getDescription());
                inputBean.getCurrentList().add(newpage);
            }

            for (Iterator<Ipgpage> it = assignPageList.iterator(); it.hasNext();) {

                Ipgpage page = it.next();
                UserRoleTaskBean currpage = new UserRoleTaskBean();
                currpage.setKey(page.getPagecode());
                currpage.setValue(page.getDescription());
                inputBean.getNewList().add(currpage);
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
    }

    public void findTask(UserRolePrivilegeInputBean bean) throws Exception {

        List<Ipgtask> taskList = new ArrayList<Ipgtask>();
        List<Ipgtask> notAssignTaskList = new ArrayList<Ipgtask>();

        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql1 = "from Ipgtask as t where t.ipgstatus.statuscode=:status and t.taskcode in ( select pt.id.taskcode from Ipgpagetask as pt where pt.id.userrolecode=:userrolecode and pt.id.pagecode=:pagecode) ";
            String sql2 = "from Ipgtask as t where t.ipgstatus.statuscode=:status and t.taskcode in (select tt.id.taskcode from IpgpagetaskTemplate as tt where tt.id.pagecode=:pagecodetemplate) and t.taskcode not in ( select pt.id.taskcode from Ipgpagetask as pt where pt.id.userrolecode=:userrolecode and pt.id.pagecode=:pagecode) ";

            Query query1 = session.createQuery(sql1).setString("status", CommonVarList.STATUS_ACTIVE).setString("userrolecode", bean.getUserRole()).setString("pagecode", bean.getPage());
            Query query2 = session.createQuery(sql2).setString("status", CommonVarList.STATUS_ACTIVE).setString("userrolecode", bean.getUserRole()).setString("pagecodetemplate", bean.getPage()).setString("pagecode", bean.getPage());

            taskList = (List<Ipgtask>) query1.list();
            notAssignTaskList = (List<Ipgtask>) query2.list();

            for (Iterator<Ipgtask> it = taskList.iterator(); it.hasNext();) {

                Ipgtask mpitask = it.next();
                UserRoleTaskBean currtask = new UserRoleTaskBean();
                currtask.setKey(mpitask.getTaskcode());
                currtask.setValue(mpitask.getDescription());
                bean.getNewList().add(currtask);
            }
            for (Iterator<Ipgtask> it = notAssignTaskList.iterator(); it.hasNext();) {

                Ipgtask mpitask = it.next();
                UserRoleTaskBean newtask = new UserRoleTaskBean();
                newtask.setKey(mpitask.getTaskcode());
                newtask.setValue(mpitask.getDescription());
                bean.getCurrentList().add(newtask);
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
    }

    public boolean assignSection(UserRolePrivilegeInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        boolean status = false;
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            String sql = "from Ipguserrolesection as pt where pt.id.userrolecode =:userrolecode";
            Query query = session.createQuery(sql).setString("userrolecode", inputBean.getUserRole());

            List<Ipguserrolesection> userSectionList = query.list();
            List<String> currSectionCodeList = inputBean.getNewBox();

            /**
             * for audit old/new values
             */
            Ipguserrole userrole = (Ipguserrole) session.get(Ipguserrole.class, inputBean.getUserRole().trim());
            StringBuilder newValue = new StringBuilder();
            StringBuilder oldValue = new StringBuilder();
            newValue.append(userrole.getDescription()).append("|[");
            oldValue.append(userrole.getDescription()).append("|[");
            
            for (Ipguserrolesection pt : userSectionList) {

                if (currSectionCodeList.contains(pt.getId().getSectioncode()
                        .toString())) {

                    pt.setLastupdatedtime(sysDate);
                    pt.setLastupdateduser(audit.getLastupdateduser());
                    session.update(pt);

                    //for audit old/new values
                    newValue.append(pt.getId().getSectioncode().toString()).append(",");
                    oldValue.append(pt.getId().getSectioncode().toString()).append(",");

                    currSectionCodeList.remove(pt.getId().getSectioncode()
                            .toString());

                } else {
                    //for audit old value
                    oldValue.append(pt.getId().getSectioncode().toString()).append(",");
                    
                    session.delete(pt);
                    session.flush();
                }
            }

            for (String sectionCode : currSectionCodeList) {

                IpguserrolesectionId ptId = new IpguserrolesectionId(
                        inputBean.getUserRole(), sectionCode);
                Ipguserrolesection pt = new Ipguserrolesection(ptId,
                        new Ipgsection(sectionCode));
                
                //for audit new value
                newValue.append(sectionCode).append(",");

                pt.setCreatedtime(sysDate);
                pt.setLastupdatedtime(sysDate);
                pt.setLastupdateduser(audit.getLastupdateduser());
                session.save(pt);

            }

            /**
             * for audit new value
             */
            if(newValue.charAt(newValue.length()-1)!='['){
                newValue.deleteCharAt(newValue.length() - 1).append("]");
            }else{                
                newValue.append("]");
            }
            audit.setNewvalue(newValue.toString());
            /**
             * for audit old value
             */
            if(oldValue.charAt(oldValue.length()-1)!='['){
                oldValue.deleteCharAt(oldValue.length() - 1).append("]");
            }else{                
                oldValue.append("]");
            }            
            audit.setOldvalue(oldValue.toString());
            audit.setCreatedtime(sysDate);
            audit.setLastupdatedtime(sysDate);

            session.save(audit);
            txn.commit();
            status = true;
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
        return status;
    }

    public boolean assignSectionPages(UserRolePrivilegeInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        boolean status = false;
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            String sql = "from Ipgsectionpage as pt where pt.id.userrolecode =:userrolecode and pt.id.sectioncode =:sectioncode";
            Query query = session.createQuery(sql).setString("userrolecode", inputBean.getUserRole()).setString("sectioncode", inputBean.getSection());

            List<Ipgsectionpage> sectionPageList = query.list();
            List<String> assignPageCodeList = inputBean.getNewBox();

            /**
             * for audit new/old values
             */
            Ipguserrole userrole = (Ipguserrole) session.get(Ipguserrole.class, inputBean.getUserRole().trim());
            Ipgsection section = (Ipgsection) session.get(Ipgsection.class, inputBean.getSection().trim());
            StringBuilder newValue = new StringBuilder();
            StringBuilder oldValue = new StringBuilder();
            newValue.append(userrole.getDescription()).append("|").append(section.getDescription()).append("|[");
            oldValue.append(userrole.getDescription()).append("|").append(section.getDescription()).append("|[");

            for (Ipgsectionpage pt : sectionPageList) {

                if (assignPageCodeList.contains(pt.getId().getPagecode().toString())) {

                    pt.setLastupdatedtime(sysDate);
                    pt.setLastupdateduser(audit.getLastupdateduser());
                    session.update(pt);

                    //for audit new/old values
                    newValue.append(pt.getId().getPagecode().toString()).append(",");
                    oldValue.append(pt.getId().getPagecode().toString()).append(",");

                    assignPageCodeList.remove(pt.getId().getPagecode().toString());

                } else {
                    //for audit old value
                    oldValue.append(pt.getId().getPagecode().toString()).append(",");
                    
                    session.delete(pt);
                    session.flush();
                }
            }

            for (String pageCode : assignPageCodeList) {

                IpgsectionpageId ptId = new IpgsectionpageId(inputBean.getUserRole(), inputBean.getSection(), pageCode);
                Ipgsectionpage pt = new Ipgsectionpage(ptId, new Ipgsection(inputBean.getSection()), new Ipgpage(pageCode));

                //for audit new value
                newValue.append(pageCode).append(",");

                pt.setCreatedtime(sysDate);
                pt.setLastupdatedtime(sysDate);
                pt.setLastupdateduser(audit.getLastupdateduser());
                session.save(pt);

            }
            /**
             * for audit new value
             */
            if(newValue.charAt(newValue.length()-1)!='['){
                newValue.deleteCharAt(newValue.length() - 1).append("]");
            }else{                
                newValue.append("]");
            }            
            audit.setNewvalue(newValue.toString());
            
            /**
             * for audit old value
             */
            if(oldValue.charAt(oldValue.length()-1)!='['){
                oldValue.deleteCharAt(oldValue.length() - 1).append("]");
            }else{                
                oldValue.append("]");
            }            
            audit.setOldvalue(oldValue.toString());
            audit.setCreatedtime(sysDate);
            audit.setLastupdatedtime(sysDate);

            session.save(audit);
            txn.commit();
            status = true;
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
        return status;
    }

    public boolean assignTask(UserRolePrivilegeInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        boolean status = false;
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            String sql = "from Ipgpagetask as pt where pt.id.userrolecode =:userrolecode and pt.id.pagecode =:pagecode ";
            Query query = session.createQuery(sql).setString("userrolecode", inputBean.getUserRole()).setString("pagecode", inputBean.getPage());

            List<Ipgpagetask> userSectionList = query.list();
            List<String> currSectionCodeList = inputBean.getNewBox();

            /**
             * for audit new/old values
             */
            Ipgpage page = (Ipgpage) session.get(Ipgpage.class, inputBean.getPage().trim());
            String sql1 = "from Ipgsectionpage as sp where sp.id.userrolecode =:userrolecode and sp.id.sectioncode =:sectioncode and sp.id.pagecode =:pagecode ";
            Query query1 = session.createQuery(sql1).setString("userrolecode", inputBean.getUserRole()).setString("sectioncode", inputBean.getSectionpage()).setString("pagecode", inputBean.getPage());
            List<Ipgsectionpage> sectionpage = query1.list();
            StringBuilder newValue = new StringBuilder();
            StringBuilder oldValue = new StringBuilder();
            newValue.append(sectionpage.get(0).getIpguserrole().getDescription()).append("|").append(sectionpage.get(0).getIpgsection().getDescription()).append("|").append(page.getDescription()).append("|[");
            oldValue.append(sectionpage.get(0).getIpguserrole().getDescription()).append("|").append(sectionpage.get(0).getIpgsection().getDescription()).append("|").append(page.getDescription()).append("|[");

            for (Ipgpagetask pt : userSectionList) {

                if (currSectionCodeList.contains(pt.getId().getTaskcode().toString())) {

                    pt.setLastupdatedtime(sysDate);
                    pt.setLastupdateduser(audit.getLastupdateduser());
                    session.update(pt);

                    //for audit new/old values
                    newValue.append(pt.getId().getTaskcode().toString()).append(",");
                    oldValue.append(pt.getId().getTaskcode().toString()).append(",");

                    currSectionCodeList.remove(pt.getId().getTaskcode().toString());

                } else {
                    //for audit old value
                    oldValue.append(pt.getId().getTaskcode().toString()).append(",");
                    
                    session.delete(pt);
                    session.flush();
                }
            }

            for (String taskcode : currSectionCodeList) {

                IpgpagetaskId ptId = new IpgpagetaskId(inputBean.getUserRole(), inputBean.getPage(), taskcode);

                Ipgpagetask pt = new Ipgpagetask(ptId, new Ipgtask(taskcode));

                //for audit values
                newValue.append(taskcode).append(",");

                pt.setCreatedtime(sysDate);
                pt.setLastupdatedtime(sysDate);
                pt.setLastupdateduser(audit.getLastupdateduser());
                session.save(pt);

            }
            /**
             * for audit new value
             */
            if(newValue.charAt(newValue.length()-1)!='['){
                newValue.deleteCharAt(newValue.length() - 1).append("]");
            }else{                
                newValue.append("]");
            }
            audit.setNewvalue(newValue.toString());
            
            /**
             * for audit old value
             */
            if(oldValue.charAt(oldValue.length()-1)!='['){
                oldValue.deleteCharAt(oldValue.length() - 1).append("]");
            }else{                
                oldValue.append("]");
            }
            audit.setOldvalue(oldValue.toString());
            audit.setCreatedtime(sysDate);
            audit.setLastupdatedtime(sysDate);

            session.save(audit);
            txn.commit();
            status = true;
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
        return status;
    }
}
