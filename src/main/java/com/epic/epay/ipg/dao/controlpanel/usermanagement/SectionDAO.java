package com.epic.epay.ipg.dao.controlpanel.usermanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.epic.epay.ipg.bean.controlpanel.usermanagement.SectionInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgsection;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.math.BigDecimal;

/**
 * @author chalitha
 *
 */
public class SectionDAO {

    public java.lang.String insertSection(SectionInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgsection) session.get(Ipgsection.class, inputBean.getSectioncode().trim()) == null) {

                boolean sortKeyExists = (Long) session.createQuery("select count(*) from Ipgsection where sortkey = '" + inputBean.getSortKey().trim() + "' ").uniqueResult() > 0;

                if (!sortKeyExists) {
                    txn = session.beginTransaction();

                    Ipgsection section = new Ipgsection();

                    section.setSectioncode(inputBean.getSectioncode().trim());
                    section.setDescription(inputBean.getDescription().trim());

                    Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                    section.setIpgstatus(status);
                    
                    section.setSortkey(new BigDecimal(inputBean.getSortKey()));
                    section.setLastupdatedtime(sysDate);
                    section.setLastupdateduser(audit.getLastupdateduser());
                    section.setCreatedtime(sysDate);
                    
                    /**
                     * for audit new value
                     */
                    StringBuilder newValue = new StringBuilder();
                    newValue.append(section.getSectioncode())
                            .append("|").append(section.getDescription())
                            .append("|").append(section.getIpgstatus().getDescription())
                            .append("|").append(section.getSortkey());
                    audit.setNewvalue(newValue.toString());
                    audit.setCreatedtime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.save(section);

                    txn.commit();
                } else {
                    message = MessageVarList.SECTION_SORT_KEY_ALREADY_EXISTS;
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

    public java.lang.String updateSection(SectionInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgsection u = (Ipgsection) session.get(Ipgsection.class, inputBean.getSectioncode().trim());

            boolean sortKeyExists = (Long) session.createQuery("select count(*) from Ipgsection where sortkey = '" + inputBean.getSortKey().trim() + "' and sectioncode != '" + inputBean.getSectioncode().trim() + "' ").uniqueResult() > 0;

            if (u != null) {

                if (!sortKeyExists) {
                    /**
                     * for audit old value
                     */
                    StringBuilder oldValue = new StringBuilder();
                    oldValue.append(u.getSectioncode()).append("|").append(u.getDescription()).append("|")
                            .append(u.getIpgstatus().getDescription()).append("|").append(u.getSortkey());                   
                    
                    u.setDescription(inputBean.getDescription());
                    
                    Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                    u.setIpgstatus(status);

                    u.setSortkey(new BigDecimal(inputBean.getSortKey()));

                    u.setLastupdateduser(audit.getLastupdateduser());
                    u.setLastupdatedtime(sysDate);
                    
                    /**
                     * for audit new value
                     */
                    StringBuilder newValue = new StringBuilder();
                    newValue.append(u.getSectioncode())
                            .append("|").append(u.getDescription())
                            .append("|").append(u.getIpgstatus().getDescription())
                            .append("|").append(u.getSortkey());
                    audit.setOldvalue(oldValue.toString());
                    audit.setNewvalue(newValue.toString());
                    audit.setCreatedtime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.update(u);

                    txn.commit();
                } else {
                    message = MessageVarList.SECTION_SORT_KEY_ALREADY_EXISTS;
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

    public String deleteSection(SectionInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgsection u = (Ipgsection) session.get(Ipgsection.class, inputBean.getSectioncode().trim());

            String sql = "select count(*) from Ipguserrolesection as s where s.ipgsection.sectioncode=:sectioncode and s.id.userrolecode is not null";
            Query query = session.createQuery(sql).setString("sectioncode", inputBean.getSectioncode().trim());
            boolean hasAssingedUserRoles = (Long) query.uniqueResult() > 0;

            if (u != null) {

                if (!hasAssingedUserRoles) {
                    audit.setCreatedtime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.delete(u);

                    txn.commit();
                } else {
                    message = MessageVarList.SECTION_USERROLES_DEPEND;
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

    public List<SectionInputBean> getSearchList(SectionInputBean inputBean, int rows, int from, String orderBy) throws Exception {

        List<SectionInputBean> dataList = new ArrayList<SectionInputBean>();
        Session session = null;
        try {
            long fullCount = 0;
            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(u.sectioncode) from Ipgsection u where " + where;
            Query queryCount = session.createQuery(sqlCount);
            
            Iterator itCount = queryCount.iterate();
            fullCount = (Long) itCount.next();
            
            if (fullCount > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }
                String sqlSearch = "from Ipgsection u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(rows);
                querySearch.setFirstResult(from);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    SectionInputBean sectionBean = new SectionInputBean();
                    Ipgsection section = (Ipgsection) it.next();

                    try {
                        sectionBean.setSectioncode(section.getSectioncode());
                    } catch (NullPointerException npe) {
                        sectionBean.setSectioncode("--");
                    }
                    try {
                        sectionBean.setDescription(section.getDescription());
                    } catch (NullPointerException npe) {
                        sectionBean.setDescription("--");
                    }
                    try {
                        sectionBean.setStatus(section.getIpgstatus().getDescription());
                    } catch (NullPointerException npe) {
                        sectionBean.setStatus("--");
                    }
                    try {
                        sectionBean.setSortKey(section.getSortkey().toString());
                    } catch (NullPointerException npe) {
                        sectionBean.setSortKey("--");
                    }                    
                    try {
                        String createdTime = section.getCreatedtime().toString();
                        sectionBean.setCreatedTime(createdTime.substring(0, createdTime.length() - 2));
                    } catch (NullPointerException npe) {
                        sectionBean.setCreatedTime("--");
                    }

                    sectionBean.setFullCount(fullCount);
                    dataList.add(sectionBean);
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
    
    private String makeWhereClause(SectionInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getSectioncode()!= null && !inputBean.getSectioncode().trim().isEmpty()) {
            where += " and lower(u.sectioncode) like lower('%" + inputBean.getSectioncode().trim() + "%')";
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
    
    //find section by section code for update
    public Ipgsection findSectionById(String sectionCode) throws Exception {
        Ipgsection section = new Ipgsection();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgsection as u where u.sectioncode=:sectioncode";
            Query query = session.createQuery(sql).setString("sectioncode",sectionCode);
            section = (Ipgsection) query.list().get(0);

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
        return section;
    }
}
