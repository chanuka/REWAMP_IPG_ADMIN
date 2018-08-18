/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.usermanagement;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.epic.epay.ipg.bean.controlpanel.usermanagement.UserRoleInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipguserrole;
import com.epic.epay.ipg.util.mapping.Ipguserroletype;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.util.ArrayList;
import java.util.Iterator;
import org.hibernate.Query;

/**
 * @author Asitha Liyanawaduge
 *
 * 28/10/2013
 */
public class UserRoleDAO {

    public String insertUserRole(UserRoleInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipguserrole) session.get(Ipguserrole.class, inputBean.getUserRoleCode().trim()) == null) {

                boolean sortKeyExists = (Long) session.createQuery("select count(*) from Ipguserrole where sortkey = '" + inputBean.getSortKey().trim() + "' ").uniqueResult() > 0;

                if (!sortKeyExists) {
                    txn = session.beginTransaction();

                    Ipguserrole userRole = new Ipguserrole();

                    userRole.setUserrolecode(inputBean.getUserRoleCode().trim());

                    Ipguserroletype userroletype = (Ipguserroletype) session.get(Ipguserroletype.class, inputBean.getUserRoleType().trim());
                    userRole.setIpguserroletype(userroletype);

                    Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                    userRole.setIpgstatus(status);

                    userRole.setDescription(inputBean.getDescription().trim());
                    userRole.setSortkey(new BigDecimal(inputBean.getSortKey()));

                    userRole.setLastupdateduser(audit.getLastupdateduser());
                    userRole.setLastupdatedtime(sysDate);
                    userRole.setCreatedtime(sysDate);
                    
                    /**
                     * for audit new value
                     */
                    StringBuilder newValue = new StringBuilder();
                    newValue.append(userRole.getUserrolecode())
                            .append("|").append(userRole.getDescription())
                            .append("|").append(userRole.getIpguserroletype().getDescription())
                            .append("|").append(userRole.getIpgstatus().getDescription())
                            .append("|").append(userRole.getSortkey());
                    audit.setNewvalue(newValue.toString());
                    audit.setCreatedtime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.save(userRole);

                    txn.commit();
                } else {
                    message = MessageVarList.USER_ROLE_SORT_KEY_ALREADY_EXISTS;
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

    /**
     * @param inputBean
     * @param audit
     * @return
     * @throws Exception
     */
    public String updateUserRole(UserRoleInputBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipguserrole u = (Ipguserrole) session.get(Ipguserrole.class, inputBean.getUserRoleCode().trim());

            boolean sortKeyExists = (Long) session.createQuery("select count(*) from Ipguserrole where sortkey = '" + inputBean.getSortKey().trim() + "' and userrolecode != '" + inputBean.getUserRoleCode().trim() + "' ").uniqueResult() > 0;

            if (u != null) {

                if (!sortKeyExists) {
                    
                    /**
                     * for audit old value
                     */
                    StringBuilder oldValue = new StringBuilder();
                    oldValue.append(u.getUserrolecode())
                            .append("|").append(u.getDescription())
                            .append("|").append(u.getIpguserroletype().getDescription())
                            .append("|").append(u.getIpgstatus().getDescription())
                            .append("|").append(u.getSortkey());
                    
                    Ipguserroletype userroletype = (Ipguserroletype) session.get(Ipguserroletype.class, inputBean.getUserRoleType().trim());
                    u.setIpguserroletype(userroletype);

                    Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                    u.setIpgstatus(status);

                    u.setDescription(inputBean.getDescription().trim());
                    u.setSortkey(new BigDecimal(inputBean.getSortKey()));

                    u.setLastupdateduser(audit.getLastupdateduser());
                    u.setLastupdatedtime(sysDate);
                    u.setCreatedtime(sysDate);
                    
                    /**
                     * for audit new value
                     */
                    StringBuilder newValue = new StringBuilder();
                    newValue.append(u.getUserrolecode())
                            .append("|").append(u.getDescription())
                            .append("|").append(u.getIpguserroletype().getDescription())
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
                    message = MessageVarList.USER_ROLE_SORT_KEY_ALREADY_EXISTS;
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
    public String deleteUserRole(UserRoleInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipguserrole u = (Ipguserrole) session.get(Ipguserrole.class, inputBean.getUserRoleCode().trim());

            String sql = "select count(*) from Ipgsystemuser as u where u.ipguserroleByUserrolecode.userrolecode=:userrolecode and u.systemuserid is not null";
            Query query = session.createQuery(sql).setString("userrolecode", inputBean.getUserRoleCode().trim());
            boolean hasAssingedUsers = (Long) query.uniqueResult() > 0;

            if (u != null) {

                if (!hasAssingedUsers) {
                    audit.setCreatedtime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.delete(u);

                    txn.commit();
                } else {
                    message = MessageVarList.USERROLE_USERS_DEPEND;
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

    public List<UserRoleInputBean> getSearchList(UserRoleInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<UserRoleInputBean> dataBeanList = new ArrayList<UserRoleInputBean>();
        Session session = null;
        long fullCount = 0;
        try {
            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(userrolecode) from Ipguserrole where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            fullCount = (Long) itCount.next();

            if (fullCount > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by createdtime desc ";
                }
                String sqlSearch = "from Ipguserrole where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    UserRoleInputBean userRoleBean = new UserRoleInputBean();
                    Ipguserrole userrole = (Ipguserrole) it.next();

                    try {
                        userRoleBean.setUserRoleCode(userrole.getUserrolecode());
                    } catch (NullPointerException npe) {
                        userRoleBean.setUserRoleCode("--");
                    }
                    try {
                        userRoleBean.setDescription(userrole.getDescription());
                    } catch (NullPointerException npe) {
                        userRoleBean.setDescription("--");
                    }
                    try {
                        userRoleBean.setSortKey(userrole.getSortkey().toString());
                    } catch (NullPointerException npe) {
                        userRoleBean.setSortKey("--");
                    }
                    try {
                        userRoleBean.setStatusDes(userrole.getIpgstatus().getDescription());
                    } catch (NullPointerException npe) {
                        userRoleBean.setStatusDes("--");
                    }
                    try {
                        userRoleBean.setUserRoleTypeDes(userrole.getIpguserroletype().getDescription());
                    } catch (NullPointerException npe) {
                        userRoleBean.setUserRoleTypeDes("--");
                    }
                    try {
                        String createdTime = userrole.getCreatedtime().toString();
                        userRoleBean.setCreatedTime(createdTime.substring(0, createdTime.length() - 2));
                    } catch (NullPointerException npe) {
                        userRoleBean.setCreatedTime("--");
                    }

                    userRoleBean.setFullCount(fullCount);
                    
                    dataBeanList.add(userRoleBean);
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

    private String makeWhereClause(UserRoleInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getUserRoleCode() != null && !inputBean.getUserRoleCode().trim().isEmpty()) {
            where += " and lower(userrolecode) like lower('%" + inputBean.getUserRoleCode().trim() + "%')";
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
        if (inputBean.getUserRoleType() != null && !inputBean.getUserRoleType().trim().isEmpty()) {
            where += " and ipguserroletype.userroletypecode = '" + inputBean.getUserRoleType().trim() + "'";
        }
        return where;
    }

    //find user role by user role code for update
    public Ipguserrole findUserRoleById(String userRoleCode) throws Exception {
        Ipguserrole userrole = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            userrole = (Ipguserrole) session.get(Ipguserrole.class, userRoleCode);

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
        return userrole;
    }

}
