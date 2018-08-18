/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.BinBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.BinInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgbin;
import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
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
 * @author chalitha
 */
public class BinDAO {
    
    public java.lang.String insertBin(BinInputBean InputBean,Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgbin) session.get(Ipgbin.class, InputBean.getBincode().trim()) == null) {
                txn = session.beginTransaction();

                Ipgbin bin = new Ipgbin();

                bin.setBin(InputBean.getBincode().trim());
                
                bin.setDescription(InputBean.getDescription().trim());
                
                Ipgstatus ipgstatus =(Ipgstatus)session.get(Ipgstatus.class, InputBean.getStatus());
                bin.setIpgstatusByStatus(ipgstatus);
                
                Ipgcardassociation ipgcardassociation =(Ipgcardassociation)session.get(Ipgcardassociation.class, InputBean.getCardassociation());
                bin.setIpgcardassociation(ipgcardassociation);
                
                Ipgstatus ipgonusstatus =(Ipgstatus)session.get(Ipgstatus.class, InputBean.getOnusstatus());
                bin.setIpgstatusByOnusstatus(ipgonusstatus);
                
                bin.setLastupdatedtime(sysDate);
                bin.setLastupdateduser(audit.getLastupdateduser());
                bin.setCreatedtime(sysDate);
                
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(bin.getBin())
                        .append("|").append(bin.getDescription())
                        .append("|").append(bin.getIpgstatusByOnusstatus().getDescription())
                        .append("|").append(bin.getIpgcardassociation().getDescription())
                        .append("|").append(bin.getIpgstatusByStatus().getDescription());
                audit.setNewvalue(newValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(bin);

                txn.commit();
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
    
    public List<BinBean> getSearchList(BinInputBean inputBean,int rows, int from, String orderBy) throws Exception {
        
        List<BinBean> dataList = new ArrayList<BinBean>();
        Session session = null;
        try {
            long count = 0;

            String where =this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();
            
            Query queryCount = session
                    .createQuery("select count(bin) from Ipgbin as u where "+where);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }

                Query querySearch = session.createQuery("from Ipgbin u where " +where
                        + orderBy);

                querySearch.setMaxResults(rows);
                querySearch.setFirstResult(from);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    BinBean binBean = new BinBean();
                    Ipgbin bin = (Ipgbin) it.next();

                    try {
                        binBean.setBincode(bin.getBin());
                    } catch (NullPointerException npe) {
                        binBean.setBincode("--");
                    }
                    try {
                        binBean.setDescription(bin.getDescription());
                    } catch (NullPointerException npe) {
                        binBean.setDescription("--");
                    }
                    try {
                        binBean.setStatus(bin.getIpgstatusByStatus().getDescription());
                    } catch (NullPointerException npe) {
                        binBean.setStatus("--");
                    }
                    try {
                        binBean.setCardassociation(bin.getIpgcardassociation().getDescription());
                    } catch (NullPointerException npe) {
                        binBean.setCardassociation("--");
                    }
                    try {
                        binBean.setOnusstatus(bin.getIpgstatusByOnusstatus().getDescription());
                    } catch (NullPointerException npe) {
                        binBean.setOnusstatus("--");
                    }
                    try {
                        String createdTime = bin.getCreatedtime().toString();
                        binBean.setCreatedtime(createdTime.substring(0, createdTime.length() - 2));
                    } catch (NullPointerException npe) {
                        binBean.setCreatedtime("--");
                    }
                    
                    binBean.setFullCount(count);
                    dataList.add(binBean);
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
    
    private String makeWhereClause(BinInputBean inputBean){
        String where = "1=1";
        if (inputBean.getBincode() != null && !inputBean.getBincode().trim().isEmpty()) {
            where += " and lower(u.bin) like lower('%" + inputBean.getBincode().trim() + "%')";
        }
        if (inputBean.getDescription()!= null && !inputBean.getDescription().trim().isEmpty()) {
            where += " and lower(u.description) like lower('%" + inputBean.getDescription().trim() + "%')";
        }
        if (inputBean.getOnusstatus()!= null && !inputBean.getOnusstatus().trim().isEmpty()) {
            where += " and u.ipgstatusByOnusstatus.statuscode = '" + inputBean.getOnusstatus().trim() + "'";
        }
        if (inputBean.getCardassociation()!= null && !inputBean.getCardassociation().trim().isEmpty()) {
            where += " and u.ipgcardassociation.cardassociationcode = '" + inputBean.getCardassociation().trim() + "'";
        }
        if (inputBean.getStatus()!= null && !inputBean.getStatus().trim().isEmpty()) {
            where += " and u.ipgstatusByStatus.statuscode = '" + inputBean.getStatus().trim() + "'";
        }
        return where;
    }
    
    public java.lang.String updateBin(BinInputBean InputBean,Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgbin u = ( Ipgbin) session.get( Ipgbin.class, InputBean.getBincode().trim());

            if (u != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(u.getBin())
                        .append("|").append(u.getDescription())                        
                        .append("|").append(u.getIpgstatusByOnusstatus().getDescription())
                        .append("|").append(u.getIpgcardassociation().getDescription())
                        .append("|").append(u.getIpgstatusByStatus().getDescription());
                
                u.setDescription(InputBean.getDescription());
                
                Ipgstatus ipgstatus =(Ipgstatus)session.get(Ipgstatus.class, InputBean.getStatus());
                u.setIpgstatusByStatus(ipgstatus);
                
                Ipgcardassociation ipgcardassociation =(Ipgcardassociation)session.get(Ipgcardassociation.class, InputBean.getCardassociation());
                u.setIpgcardassociation(ipgcardassociation);
                
                Ipgstatus ipgonusstatus =(Ipgstatus)session.get(Ipgstatus.class, InputBean.getOnusstatus());
                u.setIpgstatusByOnusstatus(ipgonusstatus);
                
                
                u.setLastupdateduser(audit.getLastupdateduser());
                u.setLastupdatedtime(sysDate);
                /**
                 * for audit old value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(u.getBin())
                        .append("|").append(u.getDescription())                        
                        .append("|").append(u.getIpgstatusByOnusstatus().getDescription())
                        .append("|").append(u.getIpgcardassociation().getDescription())
                        .append("|").append(u.getIpgstatusByStatus().getDescription());
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
    
    public String deleteBin(BinInputBean inputBean, Ipgsystemaudit audit)throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgbin u = (Ipgbin) session.get(Ipgbin.class, inputBean.getBincode().trim());
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
    
     public Ipgbin findBinById(String binCode) throws Exception {
        Ipgbin bin = new Ipgbin();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgbin as u where u.bin=:bin";
            Query query = session.createQuery(sql).setString("bin",binCode);
            bin = (Ipgbin) query.list().get(0);

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
        return bin;
    }
    
}
