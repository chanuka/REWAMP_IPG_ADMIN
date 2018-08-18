/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.usermanagement;

import com.epic.epay.ipg.bean.controlpanel.usermanagement.SystemUserBean;
import com.epic.epay.ipg.bean.controlpanel.usermanagement.SystemUserInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.common.IPGMd5;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipguserrole;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @created :Oct 25, 2013, 2:45:26 PM
 * @author :thushanth
 */
public class SystemUserDAO {

    private String makeWhereClause(SystemUserInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getUsername() != null && !inputBean.getUsername().trim().isEmpty()) {
            where += " and lower(u.username) like lower('%" + inputBean.getUsername().trim() + "%')";
        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().trim().isEmpty()) {
            where += " and u.ipgstatus.statuscode = '" + inputBean.getStatus().trim() + "'";
        }
        if (inputBean.getUserrole() != null && !inputBean.getUserrole().trim().isEmpty()) {
            where += " and u.ipguserroleByUserrolecode.userrolecode = '" + inputBean.getUserrole().trim() + "'";
        }

        return where;
    }

    public List<SystemUserBean> getAllSystemUsersList(SystemUserInputBean inputBean, int max, int first, String orderBy, String username) throws Exception {

        List<SystemUserBean> usersList = new ArrayList<SystemUserBean>();
        Session session = null;
        try {

            long count = 0;

            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();
            String sql = "select count(systemuserid) from Ipgsystemuser u where u.username!=:username and " + where;
            Query query = session.createQuery(sql).setString("username", username);

            Iterator itCount = query.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }

                String sqlSearch = "from Ipgsystemuser u where u.username!=:username and " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch).setString("username", username);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    SystemUserBean systemUserBean = new SystemUserBean();

                    Ipgsystemuser ipgsystemuser = (Ipgsystemuser) it.next();

                    try {
                        systemUserBean.setUserrole(ipgsystemuser.getIpguserroleByUserrolecode().getDescription());
                    } catch (Exception e) {
                        systemUserBean.setUserrole("--");
                    }
                    try {
                        systemUserBean.setUsername(ipgsystemuser.getUsername());
                    } catch (Exception e) {
                        systemUserBean.setUsername("--");
                    }
                    try {
                        systemUserBean.setStatus(ipgsystemuser.getIpgstatus().getDescription());
                    } catch (Exception e) {
                        systemUserBean.setStatus("--");
                    }
                    try {
                        String createdTime = ipgsystemuser.getCreatedtime().toString();
                        systemUserBean.setCreatedtime(createdTime.substring(0, createdTime.length() - 2));
                    } catch (NullPointerException npe) {
                        systemUserBean.setCreatedtime("--");
                    }

                    systemUserBean.setFullCount(count);

                    usersList.add(systemUserBean);
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
        return usersList;
    }

    public String checkUserRole(String userrole) throws Exception {
        Session session = null;
        List<String> list = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select su.ipguserroletype.userroletypecode from Ipguserrole as su where su.userrolecode=:userrolecode";
            Query query = session.createQuery(sql).setString("userrolecode", userrole);
            list = query.list();
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
        return list.get(0);
    }

    public String insertPage(SystemUserInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if (!isSystemUserExist(inputBean.getUsername())) {
                txn = session.beginTransaction();

                Ipgsystemuser ipgsystemuser = new Ipgsystemuser();
                ipgsystemuser.setUsername(inputBean.getUsername());

                ipgsystemuser.setPassword(IPGMd5.ipgMd5(inputBean.getPassword()));

                Ipguserrole userrole = (Ipguserrole) session.get(Ipguserrole.class, inputBean.getUserrole().trim());
                ipgsystemuser.setIpguserroleByUserrolecode(userrole);

                //Dual Authentication user role
//                Ipguserrole dualur = new Ipguserrole();
//                dualur.setUserrolecode(inputBean.getDualAuth()); 
//                ipgsystemuser.setIpguserroleByDualauthuserrole(dualur);
                Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                ipgsystemuser.setIpgstatus(status);

                ipgsystemuser.setEmpid(inputBean.getEmpid());

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                ipgsystemuser.setExpirydate(formatter.parse(inputBean.getExpirydate()));

                ipgsystemuser.setFullname(inputBean.getFullname());
                ipgsystemuser.setAddress1(inputBean.getAddress1());
                ipgsystemuser.setAddress2(inputBean.getAddress2());
                ipgsystemuser.setCity(inputBean.getCity());
                ipgsystemuser.setMobile(inputBean.getMobile());
                ipgsystemuser.setTelno(inputBean.getTelno());
                ipgsystemuser.setFax(inputBean.getFax());
                ipgsystemuser.setEmail(inputBean.getMail());

                //set PINCOUNT to 0(zero)
                ipgsystemuser.setPincount(0);

                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                ipgsystemuser.setLastupdateduser(audit.getLastupdateduser());
                ipgsystemuser.setLastupdatedtime(sysDate);
                ipgsystemuser.setCreatedtime(sysDate);

                //check new user has "MERCH" userroletype
                HttpSession httpsession = ServletActionContext.getRequest().getSession(false);
                if ((((String) httpsession.getAttribute(SessionVarlist.ISMERCH))).equals("true")) {
                    ipgsystemuser.setMerchantid(inputBean.getMername());
                }

                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(ipgsystemuser.getUsername())
                        .append("|").append(ipgsystemuser.getIpguserroleByUserrolecode().getDescription())
                        .append("|").append(ipgsystemuser.getIpgstatus().getDescription())
                        .append("|").append(ipgsystemuser.getMerchantid() != null ? ipgsystemuser.getMerchantid() : "")
                        .append("|").append(ipgsystemuser.getEmpid())
                        .append("|").append(formatter.format(ipgsystemuser.getExpirydate()))
                        .append("|").append(ipgsystemuser.getFullname())
                        .append("|").append(ipgsystemuser.getAddress1())
                        .append("|").append(ipgsystemuser.getAddress2())
                        .append("|").append(ipgsystemuser.getCity())
                        .append("|").append(ipgsystemuser.getMobile())
                        .append("|").append(ipgsystemuser.getTelno())
                        .append("|").append(ipgsystemuser.getFax())
                        .append("|").append(ipgsystemuser.getEmail());
                audit.setNewvalue(newValue.toString());
                session.save(audit);
                session.save(ipgsystemuser);

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

    public boolean isSystemUserExist(String systemUserName) throws Exception {
        boolean status = false;
        Session session = null;
        try {

            long count = 0;

            session = HibernateInit.sessionFactory.openSession();
            String sql = "select count(su.systemuserid) from Ipgsystemuser as su where su.username=:username";

            Query query = session.createQuery(sql);
            query.setParameter("username", systemUserName);

            Iterator it = query.iterate();

            while (it.hasNext()) {
                count = (Long) it.next();
                if (count > 0) {
                    status = true;
                    break;
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
        return status;
    }

    public Ipgsystemuser getSystemUserByUserName(String systemUserName) throws Exception {
        Ipgsystemuser user = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgsystemuser as su where su.username=:username";

            Query query = session.createQuery(sql);
            query.setString("username", systemUserName);

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

    public String updatePage(SystemUserInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            if (isSystemUserExist(inputBean.getUsername())) {
                txn = session.beginTransaction();

                /**
                 * for audit old value
                 */
                Query query = session.createQuery("from Ipgsystemuser as su where su.username=:username");
                query.setString("username", inputBean.getUsername());
                Ipgsystemuser ipgsystemuser = (Ipgsystemuser) query.list().get(0);
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(ipgsystemuser.getUsername())
                        .append("|").append(ipgsystemuser.getIpguserroleByUserrolecode().getDescription())
                        .append("|").append(ipgsystemuser.getIpgstatus().getDescription())
                        .append("|").append(ipgsystemuser.getMerchantid() != null ? ipgsystemuser.getMerchantid() : "")
                        .append("|").append(ipgsystemuser.getEmpid())
                        .append("|").append(formatter.format(ipgsystemuser.getExpirydate()))
                        .append("|").append(ipgsystemuser.getFullname())
                        .append("|").append(ipgsystemuser.getAddress1())
                        .append("|").append(ipgsystemuser.getAddress2())
                        .append("|").append(ipgsystemuser.getCity())
                        .append("|").append(ipgsystemuser.getMobile())
                        .append("|").append(ipgsystemuser.getTelno())
                        .append("|").append(ipgsystemuser.getFax())
                        .append("|").append(ipgsystemuser.getEmail());

                ipgsystemuser.setUsername(inputBean.getUsername());
                //  ipgsystemuser.setPassword(inputBean.getPassword());

                Ipguserrole userrole = (Ipguserrole) session.get(Ipguserrole.class, inputBean.getUserrole().trim());
                ipgsystemuser.setIpguserroleByUserrolecode(userrole);

                //Dual Authentication user role
//                Ipguserrole dualur = new Ipguserrole();
//                dualur.setUserrolecode(inputBean.getDualAuth()); 
//                ipgsystemuser.setIpguserroleByDualauthuserrole(dualur);
                Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                ipgsystemuser.setIpgstatus(status);

                //if 'Active', change noofinvalidattempt to 0.
                if ((inputBean.getStatus()).equals(CommonVarList.USER_STATUS_ACTIVE)) {
                    ipgsystemuser.setPincount(0);
                }

                ipgsystemuser.setEmpid(inputBean.getEmpid());

                ipgsystemuser.setExpirydate(formatter.parse(inputBean.getExpirydate()));

                ipgsystemuser.setFullname(inputBean.getFullname());
                ipgsystemuser.setAddress1(inputBean.getAddress1());
                ipgsystemuser.setAddress2(inputBean.getAddress2());
                ipgsystemuser.setCity(inputBean.getCity());
                ipgsystemuser.setMobile(inputBean.getMobile());
                ipgsystemuser.setTelno(inputBean.getTelno());
                ipgsystemuser.setFax(inputBean.getFax());
                ipgsystemuser.setEmail(inputBean.getMail());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                ipgsystemuser.setLastupdateduser(audit.getLastupdateduser());
                ipgsystemuser.setLastupdatedtime(sysDate);
                ipgsystemuser.setCreatedtime(sysDate);

                //check user has "MERCH" userroletype
                HttpSession httpsession = ServletActionContext.getRequest().getSession(false);
                if ((((String) httpsession.getAttribute(SessionVarlist.ISMERCH))).equals("true")) {
                    ipgsystemuser.setMerchantid(inputBean.getMername());
                } else {
                    ipgsystemuser.setMerchantid(null);
                }

                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(ipgsystemuser.getUsername())
                        .append("|").append(ipgsystemuser.getIpguserroleByUserrolecode().getDescription())
                        .append("|").append(ipgsystemuser.getIpgstatus().getDescription())
                        .append("|").append(ipgsystemuser.getMerchantid() != null ? ipgsystemuser.getMerchantid() : "")
                        .append("|").append(ipgsystemuser.getEmpid())
                        .append("|").append(inputBean.getExpirydate())
                        .append("|").append(ipgsystemuser.getFullname())
                        .append("|").append(ipgsystemuser.getAddress1())
                        .append("|").append(ipgsystemuser.getAddress2())
                        .append("|").append(ipgsystemuser.getCity())
                        .append("|").append(ipgsystemuser.getMobile())
                        .append("|").append(ipgsystemuser.getTelno())
                        .append("|").append(ipgsystemuser.getFax())
                        .append("|").append(ipgsystemuser.getEmail());

                audit.setOldvalue(oldValue.toString());
                audit.setNewvalue(newValue.toString());
                session.save(audit);
                session.update(ipgsystemuser);

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

    //delete user
    public String deleteUser(SystemUserInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();

            Date sysDate = CommonDAO.getSystemDate(session);

            SystemUserDAO dao = new SystemUserDAO();

            Ipgsystemuser su = (Ipgsystemuser) dao.getSystemUserByUserName(inputBean.getUsername());
            if (su != null) {

                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.delete(su);

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

}
