/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.usermanagement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.epic.epay.ipg.bean.controlpanel.usermanagement.PageInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgpage;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.util.Iterator;
import org.hibernate.Query;

/**
 * @author Asitha Liyanawaduge
 *
 * 25/10/2013
 */
public class PageDAO {

    public String insertPage(PageInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgpage) session.get(Ipgpage.class, inputBean.getPageCode().trim()) == null) {

                boolean sortKeyExists = (Long) session.createQuery("select count(*) from Ipgpage where sortkey = '" + inputBean.getSortKey().trim() + "' ").uniqueResult() > 0;

                if (!sortKeyExists) {

                    txn = session.beginTransaction();

                    Ipgpage page = new Ipgpage();
                    page.setPagecode(inputBean.getPageCode().trim());
                    page.setDescription(inputBean.getDescription().trim());
                    page.setUrl(inputBean.getUrl().trim());
                    page.setRoot(inputBean.getPageRoot().trim());
                    page.setSortkey(new BigDecimal(inputBean.getSortKey()));

                    Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                    page.setIpgstatus(status);

                    page.setCreatedtime(sysDate);
                    page.setLastupdatedtime(sysDate);
                    page.setLastupdateduser(audit.getLastupdateduser());

                    /**
                     * for audit new value
                     */
                    StringBuilder newValue = new StringBuilder();
                    newValue.append(page.getPagecode())
                            .append("|").append(page.getDescription())
                            .append("|").append(page.getUrl())
                            .append("|").append(page.getRoot())
                            .append("|").append(page.getSortkey())
                            .append("|").append(page.getIpgstatus().getDescription());

                    audit.setNewvalue(newValue.toString());
                    audit.setCreatedtime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.save(page);

                    txn.commit();
                } else {
                    message = MessageVarList.PAGE_SORT_KEY_ALREADY_EXISTS;
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

    public String updatePage(PageInputBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgpage p = (Ipgpage) session.get(Ipgpage.class, inputBean.getPageCode().trim());

            boolean sortKeyExists = (Long) session.createQuery("select count(*) from Ipgpage where sortkey = '" + inputBean.getSortKey().trim() + "' and pagecode != '" + inputBean.getPageCode().trim() + "' ").uniqueResult() > 0;

            if (p != null) {

                if (!sortKeyExists) {
                    /**
                     * for audit old value
                     */
                    StringBuilder oldValue = new StringBuilder();
                    oldValue.append(p.getPagecode())
                            .append("|").append(p.getDescription())
                            .append("|").append(p.getUrl())
                            .append("|").append(p.getRoot())
                            .append("|").append(p.getSortkey())
                            .append("|").append(p.getIpgstatus().getDescription());

                    p.setDescription(inputBean.getDescription());
                    p.setUrl(inputBean.getUrl());
                    p.setRoot(inputBean.getPageRoot());
                    p.setSortkey(new BigDecimal(inputBean.getSortKey()));

                    Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                    p.setIpgstatus(status);

                    p.setLastupdateduser(audit.getLastupdateduser());
                    p.setLastupdatedtime(sysDate);
                    
                    /**
                     * for audit new value
                     */
                    StringBuilder newValue = new StringBuilder();
                    newValue.append(p.getPagecode())
                            .append("|").append(p.getDescription())
                            .append("|").append(p.getUrl())
                            .append("|").append(p.getRoot())
                            .append("|").append(p.getSortkey())
                            .append("|").append(p.getIpgstatus().getDescription());

                    audit.setOldvalue(oldValue.toString());
                    audit.setNewvalue(newValue.toString());
                    audit.setCreatedtime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.update(p);

                    txn.commit();
                } else {
                    message = MessageVarList.PAGE_SORT_KEY_ALREADY_EXISTS;
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

    /**
     * @param inputBean
     * @param audit
     * @return
     * @throws Exception
     */
    public String deletePage(PageInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgpage p = (Ipgpage) session.get(Ipgpage.class, inputBean.getPageCode().trim());

            String sql = "select count(*) from Ipgpagetask as p where p.ipgpage.pagecode=:pagecode and p.id.userrolecode is not null";
            Query query = session.createQuery(sql).setString("pagecode", inputBean.getPageCode().trim());
            boolean hasAssingedUserRoles = (Long) query.uniqueResult() > 0;

            if (p != null) {

                if (!hasAssingedUserRoles) {
                    audit.setCreatedtime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.delete(p);

                    txn.commit();
                } else {
                    message = MessageVarList.PAGE_USERROLES_DEPEND;
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

    public List<PageInputBean> getSearchList(PageInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<PageInputBean> dataBeanList = new ArrayList<PageInputBean>();
        Session session = null;
        try {
            long fullCount = 0;
            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(pagecode) from Ipgpage where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            fullCount = (Long) itCount.next();

            if (fullCount > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by createdtime desc ";
                }
                String sqlSearch = "from Ipgpage where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    PageInputBean pageBean = new PageInputBean();
                    Ipgpage page = (Ipgpage) it.next();

                    try {
                        pageBean.setPageCode(page.getPagecode());
                    } catch (NullPointerException npe) {
                        pageBean.setPageCode("--");
                    }
                    try {
                        pageBean.setDescription(page.getDescription());
                    } catch (NullPointerException npe) {
                        pageBean.setDescription("--");
                    }
                    try {
                        pageBean.setStatus(page.getIpgstatus().getDescription());
                    } catch (NullPointerException npe) {
                        pageBean.setStatus("--");
                    }
                    try {
                        pageBean.setSortKey(page.getSortkey().toString());
                    } catch (NullPointerException npe) {
                        pageBean.setSortKey("--");
                    }
                    try {
                        pageBean.setUrl(page.getUrl());
                    } catch (NullPointerException npe) {
                        pageBean.setUrl("--");
                    }
                    try {
                        pageBean.setPageRoot(page.getRoot());
                    } catch (NullPointerException npe) {
                        pageBean.setPageRoot("--");
                    }
                    try {
                        String createdTime = page.getCreatedtime().toString();
                        pageBean.setCreatedTime(createdTime.substring(0, createdTime.length() - 2));
                    } catch (NullPointerException npe) {
                        pageBean.setCreatedTime("--");
                    }

                    pageBean.setFullCount(fullCount);

                    dataBeanList.add(pageBean);
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

        return dataBeanList;
    }

    private String makeWhereClause(PageInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getPageCode() != null && !inputBean.getPageCode().trim().isEmpty()) {
            where += " and lower(pagecode) like lower('%" + inputBean.getPageCode().trim() + "%')";
        }
        if (inputBean.getDescription() != null && !inputBean.getDescription().trim().isEmpty()) {
            where += " and lower(description) like lower('%" + inputBean.getDescription().trim() + "%')";
        }
        if (inputBean.getSortKey() != null && !inputBean.getSortKey().trim().isEmpty()) {
            where += " and sortkey = '" + inputBean.getSortKey().trim() + "'";
        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().trim().isEmpty()) {
            where += " and ipgstatus.statuscode = '" + inputBean.getStatus().trim() + "'";
        }
        return where;
    }

    //find page by page code for update
    public Ipgpage findPageById(String pageCode) throws Exception {
        Ipgpage page = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            page = (Ipgpage) session.get(Ipgpage.class, pageCode);

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
        return page;
    }

}
