/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.AuthConfigurationBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.AuthConfigurationInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgauthconfiguration;
import com.epic.epay.ipg.util.mapping.IpgauthconfigurationId;
import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
import com.epic.epay.ipg.util.mapping.Ipgecivalue;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author badrika
 */
public class AuthConfigurationDAO {

    private HashMap<String, String> gettxstatusList() {

        HashMap<String, String> statusMap = new HashMap<String, String>();
        statusMap.put("Y", "Authentication Successful(Y)");
        statusMap.put("A", "Proof of Authentication(A)");
        statusMap.put("U", "Authentication Could not Complete(U)");
        statusMap.put("N", "Authentication Failed(N)");
        return statusMap;
    }
    // get status list
    public List<Ipgecivalue> getECIValueList() throws Exception {

        List<Ipgecivalue> ecivaluelist = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgecivalue ";
            Query query = session.createQuery(sql);
            ecivaluelist = query.list();

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
        return ecivaluelist;
    }

    public String insertAuthConfig(AuthConfigurationInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);            
            
            if ((Ipgauthconfiguration) session.get(Ipgauthconfiguration.class,
                    new IpgauthconfigurationId(inputBean.getCardassociation(), inputBean.getEcivalue(), inputBean.getTxstatus())) == null) {
                txn = session.beginTransaction();

                Ipgauthconfiguration authconfig = new Ipgauthconfiguration();

                IpgauthconfigurationId id = new IpgauthconfigurationId();
                id.setCardassociationcode(inputBean.getCardassociation());
                id.setEcivalue(inputBean.getEcivalue());
                id.setTxstatus(inputBean.getTxstatus());
                
                authconfig.setId(id);                
                authconfig.setStatus(inputBean.getStatus());
                
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                Ipgstatus status =(Ipgstatus)session.get(Ipgstatus.class, inputBean.getStatus());
                Ipgcardassociation  cardassociation =(Ipgcardassociation)session.get(Ipgcardassociation.class, authconfig.getId().getCardassociationcode());
                newValue.append(cardassociation.getDescription())
                        .append("|").append(this.gettxstatusList().get(authconfig.getId().getTxstatus()))
                        .append("|").append(authconfig.getId().getEcivalue())
                        .append("|").append(status.getDescription());
                audit.setNewvalue(newValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(authconfig);

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

    public String deleteAuthConfig(AuthConfigurationInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            
            String[] idarray = inputBean.getPkey().split("\\|");
            String ca = idarray[0];
            String eci = idarray[1];
            String txst = idarray[2];

            Ipgauthconfiguration u = (Ipgauthconfiguration) session.get(Ipgauthconfiguration.class, new IpgauthconfigurationId(ca, eci, txst));

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
	

    public String updateAuthConfig(AuthConfigurationInputBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            
            String[] idarray = inputBean.getPkey().split("\\|");
            String ca = idarray[0];
            String eci = idarray[1];
            String txst = idarray[2];

            Ipgauthconfiguration u = (Ipgauthconfiguration) session.get(Ipgauthconfiguration.class, new IpgauthconfigurationId(ca, eci, txst));

            if (u != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                Ipgstatus status =(Ipgstatus)session.get(Ipgstatus.class, u.getStatus());
                Ipgcardassociation  cardassociation =(Ipgcardassociation)session.get(Ipgcardassociation.class, u.getId().getCardassociationcode());
                oldValue.append(cardassociation.getDescription())
                        .append("|").append(this.gettxstatusList().get(u.getId().getTxstatus()))
                        .append("|").append(u.getId().getEcivalue())
                        .append("|").append(status.getDescription());
                
                u.setStatus(inputBean.getStatus());                
                
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                Ipgstatus newStatus =(Ipgstatus)session.get(Ipgstatus.class, inputBean.getStatus());
                newValue.append(cardassociation.getDescription())
                        .append("|").append(this.gettxstatusList().get(u.getId().getTxstatus()))
                        .append("|").append(u.getId().getEcivalue())
                        .append("|").append(newStatus.getDescription());
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

//				    //get search list			
    public List<AuthConfigurationBean> getSearchList(AuthConfigurationInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<AuthConfigurationBean> dataList = new ArrayList<AuthConfigurationBean>();
        Session session = null;
        try {

            long count = 0;
            String where =this.makeWhereClause(inputBean);
            HashMap<String, String> txstatusMap =this.gettxstatusList();
            session = HibernateInit.sessionFactory.openSession();
            
            String sqlCount = "select count(u.id.cardassociationcode) from Ipgauthconfiguration as u where "+where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                String sqlSearch = "from Ipgauthconfiguration u where "+where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    AuthConfigurationBean bean = new AuthConfigurationBean();
                    Ipgauthconfiguration i = (Ipgauthconfiguration) it.next();

                    try {
                        bean.setPkey(i.getId().getCardassociationcode() + "|" + i.getId().getEcivalue() + "|" + i.getId().getTxstatus());
                    } catch (NullPointerException npe) {
                        bean.setPkey("--");
                    }
                    try {
                        Ipgcardassociation  cardassociation =(Ipgcardassociation)session.get(Ipgcardassociation.class, i.getId().getCardassociationcode());
                        bean.setCardassociation(cardassociation.getDescription());
                    } catch (NullPointerException npe) {
                        bean.setCardassociation("--");
                    }
                    try {
                        bean.setEcivalue(i.getId().getEcivalue());
                    } catch (NullPointerException npe) {
                        bean.setEcivalue("--");
                    }
                    try {
                        String txstatus=txstatusMap.get(i.getId().getTxstatus());
                        if(txstatus!=null && !txstatus.isEmpty()){
                            bean.setTxstatus(txstatus);
                        }else{
                             bean.setTxstatus("--");
                        }
                    } catch (NullPointerException npe) {
                        bean.setTxstatus("--");
                    }
                    try {
                        Ipgstatus status =(Ipgstatus)session.get(Ipgstatus.class, i.getStatus());
                        bean.setStatus(status.getDescription());
                    } catch (NullPointerException npe) {
                        bean.setStatus("--");
                    }

                    bean.setFullCount(count);

                    dataList.add(bean);
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
    private String makeWhereClause(AuthConfigurationInputBean inputBean){
        String where = "1=1";
        if (inputBean.getCardassociation() != null && !inputBean.getCardassociation().trim().isEmpty()) {
            where += " and u.id.cardassociationcode = '" + inputBean.getCardassociation().trim() + "'";
        }
        if (inputBean.getTxstatus()!= null && !inputBean.getTxstatus().trim().isEmpty()) {
            where += " and u.id.txstatus = '" + inputBean.getTxstatus().trim() + "'";
        }
        if (inputBean.getEcivalue()!= null && !inputBean.getEcivalue().trim().isEmpty()) {
            where += " and u.id.ecivalue = '" + inputBean.getEcivalue().trim() + "'";
        }
        if (inputBean.getStatus()!= null && !inputBean.getStatus().trim().isEmpty()) {
            where += " and u.status = '" + inputBean.getStatus().trim() + "'";
        }
        return where;
    }

    public Ipgauthconfiguration findAuthConfigByPkey(String pkey) throws Exception {
        Ipgauthconfiguration i = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String[] idarray = pkey.split("\\|");
            String ca = idarray[0];
            String eci = idarray[1];
            String txst = idarray[2];

            String sql = "from Ipgauthconfiguration as u where u.id.cardassociationcode=:ca "
                    + "and u.id.txstatus=:txst and u.id.ecivalue=:eci";
            Query query = session.createQuery(sql).setString("ca", ca).setString("txst", txst).setString("eci", eci);
            i = (Ipgauthconfiguration) query.list().get(0);

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
        return i;

    }

}
