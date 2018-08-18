/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.controlpanel.usermanagement;

import com.epic.epay.ipg.bean.controlpanel.usermanagement.TaskInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author chanuka
 */
public class TaskDAO {

    public String insertPage(TaskInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {

            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgtask) session.get(Ipgtask.class, inputBean.getTaskCode().trim()) == null) {

                // check if sort key already exists
                boolean sortKeyExists = (Long) session.createQuery("select count(*) from Ipgtask where sortkey = '" + inputBean.getSortKey().trim() + "' ").uniqueResult() > 0;

                if (!sortKeyExists) {

                    txn = session.beginTransaction();

                    Ipgtask task = new Ipgtask();
                    task.setTaskcode(inputBean.getTaskCode().trim());
                    task.setDescription(inputBean.getDescription().trim());
                    task.setSortkey(new BigDecimal(inputBean.getSortKey()));
                        
                    Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                    task.setIpgstatus(status);
                    
                    task.setCreatedtime(sysDate);
                    task.setLastupdatedtime(sysDate);
                    task.setLastupdateduser(audit.getLastupdateduser());
                    
                    /**
                     * for audit new value
                     */
                    StringBuilder newValue = new StringBuilder();
                    newValue.append(task.getTaskcode())
                            .append("|").append(task.getDescription())
                            .append("|").append(task.getSortkey())
                            .append("|").append(task.getIpgstatus().getDescription()); 

                    audit.setNewvalue(newValue.toString());
                    audit.setCreatedtime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.save(task);

                    txn.commit();
                } else {
                    message = MessageVarList.TASK_SORT_KEY_ALREADY_EXISTS;
                }
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

    //update task
    public String updateTask(TaskInputBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgtask u = (Ipgtask) session.get(Ipgtask.class, inputBean.getTaskCode().trim());

            boolean sortKeyExists = (Long) session.createQuery("select count(*) from Ipgtask where sortkey = '" + inputBean.getSortKey().trim() + "' and taskcode != '" + inputBean.getTaskCode().trim() + "' ").uniqueResult() > 0;

            if (u != null) {

                if (!sortKeyExists) {
                    
                    /**
                     * for audit old value
                     */
                    StringBuilder oldValue = new StringBuilder();
                    oldValue.append(u.getTaskcode())
                            .append("|").append(u.getDescription())
                            .append("|").append(u.getSortkey())
                            .append("|").append(u.getIpgstatus().getDescription()); 
                    
                    u.setDescription(inputBean.getDescription());
                    u.setSortkey(new BigDecimal(inputBean.getSortKey()));

                    Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                    u.setIpgstatus(status);

                    u.setLastupdateduser(audit.getLastupdateduser());
                    u.setLastupdatedtime(sysDate);
                    
                    /**
                     * for audit new value
                     */
                    StringBuilder newValue = new StringBuilder();
                    newValue.append(u.getTaskcode())
                            .append("|").append(u.getDescription())
                            .append("|").append(u.getSortkey())
                            .append("|").append(u.getIpgstatus().getDescription()); 
                    
                    audit.setOldvalue(oldValue.toString());
                    audit.setNewvalue(newValue.toString());
                    audit.setCreatedtime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.update(u);

                    txn.commit();
                } else {
                    message = MessageVarList.TASK_SORT_KEY_ALREADY_EXISTS;
                }
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

    //delete section
    public String deleteTask(TaskInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgtask u = (Ipgtask) session.get(Ipgtask.class, inputBean.getTaskCode().trim());

            String sql = "select count(*) from Ipgpagetask as p where p.ipgtask.taskcode=:taskcode and p.id.pagecode is not null";
            Query query = session.createQuery(sql).setString("taskcode", inputBean.getTaskCode().trim());
            boolean hasAssingedPages = (Long) query.uniqueResult() > 0;

            if (u != null) {

                if (!hasAssingedPages) {
                    audit.setCreatedtime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.delete(u);

                    txn.commit();
                } else {
                    message = MessageVarList.TASK_PAGES_DEPEND;
                }
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

    public List<TaskInputBean> getSearchList(TaskInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<TaskInputBean> dataList = new ArrayList<TaskInputBean>();
        Session session = null;
        try {

            long count = 0;
            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(taskcode) from Ipgtask as t where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                
                if (orderBy.equals("")) {
                    orderBy = " order by t.createdtime desc ";
                }
                
                String sqlSearch = "from Ipgtask t where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    TaskInputBean mpiTask = new TaskInputBean();
                    Ipgtask task = (Ipgtask) it.next();

                    try {
                        mpiTask.setTaskCode(task.getTaskcode());
                    } catch (NullPointerException npe) {
                        mpiTask.setTaskCode("--");
                    }
                    try {
                        mpiTask.setDescription(task.getDescription());
                    } catch (NullPointerException npe) {
                        mpiTask.setDescription("--");
                    }
                    try {
                        mpiTask.setStatus(task.getIpgstatus().getDescription());
                    } catch (NullPointerException npe) {
                        mpiTask.setStatus("--");
                    }
                    try {
                        mpiTask.setSortKey(task.getSortkey().toString());
                    } catch (NullPointerException npe) {
                        mpiTask.setSortKey("--");
                    }
                    try {
                        String createdTime = task.getCreatedtime().toString();
                        mpiTask.setCreatedTime(createdTime.substring(0, createdTime.length() - 2));
                    } catch (NullPointerException npe) {
                        mpiTask.setCreatedTime("--");
                    }

                    mpiTask.setFullCount(count);

                    dataList.add(mpiTask);
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

    private String makeWhereClause(TaskInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getTaskCode() != null && !inputBean.getTaskCode().trim().isEmpty()) {
            where += " and lower(t.taskcode) like lower('%" + inputBean.getTaskCode().trim() + "%')";
        }
        if (inputBean.getDescription() != null && !inputBean.getDescription().trim().isEmpty()) {
            where += " and lower(t.description) like lower('%" + inputBean.getDescription().trim() + "%')";
        }
        if (inputBean.getSortKey() != null && !inputBean.getSortKey().trim().isEmpty()) {
            where += " and t.sortkey = '" + inputBean.getSortKey().trim() + "'";
        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().trim().isEmpty()) {
            where += " and t.ipgstatus.statuscode = '" + inputBean.getStatus().trim() + "'";
        }
        return where;
    }

    //find task by task code for update
    public Ipgtask findTaskById(String taskCode) throws Exception {
        Ipgtask task = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgtask as t where t.taskcode=:taskcode";
            Query query = session.createQuery(sql).setString("taskcode", taskCode);
            task = (Ipgtask) query.list().get(0);

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
        return task;

    }

}
