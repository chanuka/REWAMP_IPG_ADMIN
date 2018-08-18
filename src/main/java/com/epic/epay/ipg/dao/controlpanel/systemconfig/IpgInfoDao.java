package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.IpgInfoBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.IpgInfoInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgmpiinfo;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author jeevan
 */
public class IpgInfoDao {

//    public List<IpgInfoBean> getSearchList(BinInputBean inputBean, int rows, int from, String orderBy) throws Exception {
    public List<IpgInfoBean> getSearchList(IpgInfoInputBean inputBean, int rows, int from, String orderBy) throws Exception {

        List<IpgInfoBean> dataList = new ArrayList<IpgInfoBean>();
        Session session = null;
        try {
            long count = 0;
            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            Query queryCount = session
                    .createQuery("select count(ipgmpiinfoid) from Ipgmpiinfo as u where " + where);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }
                Query querySearch = session.createQuery("from Ipgmpiinfo u where " + where
                        + orderBy);

                querySearch.setMaxResults(rows);
                querySearch.setFirstResult(from);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    IpgInfoBean infoBean = new IpgInfoBean();
                    Ipgmpiinfo info = (Ipgmpiinfo) it.next();

                    try {
                        infoBean.setInfoId(info.getIpgmpiinfoid());
                    } catch (Exception e) {
//                        infoBean.setInfoId("--");
                    }

                    try {
                        infoBean.setPrimaryUrl(info.getPrimaryurl());
                    } catch (Exception e) {
                        infoBean.setPrimaryUrl("--");
                    }

                    try {
                        infoBean.setSecondaryUrl(info.getSecounderyurl());
                    } catch (Exception e) {
                        infoBean.setSecondaryUrl("--");
                    }

                    try {
                        infoBean.setLastUpdatedUser(info.getLastupdateduser());
                    } catch (Exception e) {
                        infoBean.setLastUpdatedUser("--");
                    }

                    try {
                        infoBean.setLastUpdatedTime(info.getLastupdatedtime());
                    } catch (Exception e) {
                        infoBean.setLastUpdatedTime(null);

                    }
                    try {
                        String createdTime = info.getCreatedtime().toString();
                        infoBean.setCreatedtime(createdTime.substring(0, createdTime.length() - 2));
                    } catch (NullPointerException npe) {
                        infoBean.setCreatedtime("--");
                    }
                    
                    infoBean.setFullCount(count);
                    dataList.add(infoBean);
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

    private String makeWhereClause(IpgInfoInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getPrimaryUrl() != null && !inputBean.getPrimaryUrl().trim().isEmpty()) {
            where += " and lower(u.primaryurl) like lower('%" + inputBean.getPrimaryUrl().trim() + "%')";
        }
        if (inputBean.getSecondaryUrl() != null && !inputBean.getSecondaryUrl().trim().isEmpty()) {
            where += " and lower(u.secounderyurl) like lower('%" + inputBean.getSecondaryUrl().trim() + "%')";
        }
        return where;
    }

    public String insertIpgInfo(IpgInfoInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgmpiinfo info = new Ipgmpiinfo();

//            if ((Ipgmpiinfo) session.get(Ipgmpiinfo.class, inputBean.getIpgMpiInfoId().trim()) == null) {
//            if ((Ipgmpiinfo) session.get(Ipgmpiinfo.class, info.getIpgmpiinfoid()) == null) {
            txn = session.beginTransaction();

//                Ipgbin bin = new Ipgbin();
//                Ipgmpiinfo info = new Ipgmpiinfo();
//                info.setIpgmpiinfoid(Long.parseLong(inputBean.getIpgMpiInfoId()));
            info.setPrimaryurl(inputBean.getPrimaryUrl());
            info.setSecounderyurl(inputBean.getSecondaryUrl());

            info.setLastupdatedtime(sysDate);
            info.setCreatedtime(sysDate);
            /**
             * for audit new value
             */
            StringBuilder newValue = new StringBuilder();
            newValue.append(info.getPrimaryurl())
                    .append("|").append(info.getSecounderyurl());
            audit.setNewvalue(newValue.toString());
            audit.setCreatedtime(sysDate);
            audit.setLastupdatedtime(sysDate);

            session.save(audit);
            session.save(info);

            txn.commit();
//            } else {
//                message = MessageVarList.COMMON_ALREADY_EXISTS;
//            }
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

    public String deleteIpgInfo(IpgInfoInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgmpiinfo u = (Ipgmpiinfo) session.get(Ipgmpiinfo.class, Long.parseLong(inputBean.getIpgMpiInfoId().trim()));
            
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

    public Ipgmpiinfo findInfoById(String ipgMpiInfoId) throws Exception {
        Ipgmpiinfo info = new Ipgmpiinfo();
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgmpiinfo as u where u.ipgmpiinfoid=:ipgmpiinfoid";
            Query query = session.createQuery(sql).setString("ipgmpiinfoid", ipgMpiInfoId);
            info = (Ipgmpiinfo) query.list().get(0);

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
        return info;
    }

    public String updateIpgInfo(IpgInfoInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {

            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgmpiinfo m = (Ipgmpiinfo) session.get(Ipgmpiinfo.class, Long.parseLong(inputBean.getIpgMpiInfoId().trim()));

            if (m != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(m.getPrimaryurl())
                        .append("|").append(m.getSecounderyurl());
                
                m.setPrimaryurl(inputBean.getPrimaryUrl().trim());
                m.setSecounderyurl(inputBean.getSecondaryUrl().trim());

                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(m.getPrimaryurl())
                        .append("|").append(m.getSecounderyurl());
                audit.setNewvalue(newValue.toString());
                audit.setOldvalue(oldValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(m);

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
            } catch (HibernateException e) {
                throw e;
            }
        }
        return message;
    }
    /*
     public String updateDsInfo(DsInfoInputBean inputBean, Acssystemaudit audit) throws Exception {
     Session session = null;
     Transaction txn = null;
     String message = "";

     try {
     session = HibernateInit.sessionFactory.openSession();
     txn = session.beginTransaction();
     Date sysDate = CommonDAO.getSystemDate(session);

     Acsdsinfo m = (Acsdsinfo) session.get(Acsdsinfo.class, inputBean.getDscode().trim());

     if (m != null) {


     m.setPrimaryurl(inputBean.getPrimaryurl().trim());
     m.setSecondaryurl(inputBean.getSecondaryurl().trim());

     Acsstatus st = new Acsstatus();
     st.setStatuscode(inputBean.getStatus());
     m.setAcsstatus(st);
                
     m.setCreatedtime(sysDate);
     m.setLastupdatedtime(sysDate);
     m.setLastupdateduser(audit.getLastupdateduser());

     audit.setCreatedtime(sysDate);
     audit.setLastupdatedtime(sysDate);

     session.save(audit);
     session.update(m);

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
     
     */
    public String getNextInfoId() throws Exception {
        String infoId = "";
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "select max(ipgmpiinfoid)+1 from Ipgmpiinfo";
            Query query = session.createQuery(sql);
            Iterator itId = query.iterate();
            infoId = itId.next().toString();

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
        return infoId;
    }
}
