/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.CountryDataBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.CountryInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcountry;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author Asitha Liyanawaduge
 *
 * 31/10/2013
 */
public class CountryDAO {

    /**
     * @param inputBean
     * @param rows
     * @param from
     * @param sortIndex
     * @param sortOrder
     * @return
     * @throws Exception
     */
    public List<CountryDataBean> getSearchList(CountryInputBean inputBean,
            int rows, int from, String sortIndex, String sortOrder)
            throws Exception {
        List<Ipgcountry> searchList = null;
        List<CountryDataBean> dataBeanList = null;
        Session session = null;
        long fullCount = 0;
        if ("".equals(sortIndex.trim())) {
            sortIndex = null;
        }
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Ipgcountry.class);
            this.makeWhereClause(criteria, inputBean);
            if (sortIndex != null && sortOrder != null) {
                if (sortOrder.equals("asc")) {
                    criteria.addOrder(Order.asc(sortIndex));
                }
                if (sortOrder.equals("desc")) {
                    criteria.addOrder(Order.desc(sortIndex));
                }
            } else {
                criteria.addOrder(Order.desc("createdtime"));
            }

            fullCount = criteria.list().size();

            criteria.setFirstResult(from);
            criteria.setMaxResults(rows);

            searchList = criteria.list();
            dataBeanList = new ArrayList<CountryDataBean>();

            for (Ipgcountry country : searchList) {

                CountryDataBean countryBean = new CountryDataBean();

                try {
                    countryBean.setCountrycode(country.getCountrycode());
                } catch (NullPointerException npe) {
                    countryBean.setCountrycode("--");
                }
                try {
                    countryBean.setDescription(country.getDescription());
                } catch (NullPointerException npe) {
                    countryBean.setDescription("--");
                }
                try {
                    countryBean.setSortkey(country.getSortkey().toString());
                } catch (NullPointerException npe) {
                    countryBean.setSortkey("--");
                }
                try {
                    countryBean.setCountryisocode(country.getCountryisocode().toString());
                } catch (NullPointerException npe) {
                    countryBean.setCountryisocode("--");
                }
                try {
                    String createdTime = country.getCreatedtime().toString();
                    countryBean.setCreatedtime(createdTime.substring(0, createdTime.length() - 2));
                } catch (NullPointerException npe) {
                    countryBean.setCreatedtime("--");
                }

                countryBean.setFullcount(fullCount);

                dataBeanList.add(countryBean);
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

    private void makeWhereClause(Criteria criteria, CountryInputBean inputBean) {

        if (inputBean.getCountryCode() != null && !inputBean.getCountryCode().trim().isEmpty()) {
            criteria.add(Restrictions.like("countrycode", inputBean.getCountryCode().trim(), MatchMode.ANYWHERE));
        }
        if (inputBean.getDescription() != null && !inputBean.getDescription().trim().isEmpty()) {
            criteria.add(Restrictions.like("description", inputBean.getDescription().trim(), MatchMode.ANYWHERE));
        }
        if (inputBean.getSortKey() != null && !inputBean.getSortKey().trim().isEmpty()) {
            criteria.add(Restrictions.eq("sortkey", new BigDecimal(inputBean.getSortKey().trim())));
        }
        if (inputBean.getCountryisocode() != null && !inputBean.getCountryisocode().trim().isEmpty()) {
            criteria.add(Restrictions.like("countryisocode", inputBean.getCountryisocode().trim(), MatchMode.ANYWHERE));
        }

    }

    /**
     * @param inputBean
     * @param audit
     * @return
     * @throws Exception
     */
    public String insertCountry(CountryInputBean inputBean, Ipgsystemaudit audit)
            throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgcountry) session.get(Ipgcountry.class, inputBean
                    .getCountryCode().trim()) == null) {
                txn = session.beginTransaction();

                Ipgcountry country = new Ipgcountry();
                country.setCountrycode(inputBean.getCountryCode().trim());
                country.setDescription(inputBean.getDescription().trim());
                country.setSortkey(new BigDecimal(inputBean.getSortKey()));
                country.setCountryisocode(inputBean.getCountryisocode().trim());
                country.setCreatedtime(sysDate);
                country.setLastupdatedtime(sysDate);
                country.setLastupdateduser(audit.getLastupdateduser());

                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(country.getCountrycode())
                        .append("|").append(country.getDescription())
                        .append("|").append(country.getSortkey())
                        .append("|").append(country.getCountryisocode());
                audit.setNewvalue(newValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(country);

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

    /**
     * @param countryCode
     * @return
     * @throws Exception
     */
    public Ipgcountry findCountryById(String countryCode) throws Exception {
        Ipgcountry country = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            country = (Ipgcountry) session.get(Ipgcountry.class, countryCode);

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
        return country;
    }

    /**
     * @param inputBean
     * @param audit
     * @return
     * @throws Exception
     */
    public String updateCountry(CountryInputBean inputBean, Ipgsystemaudit audit)
            throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgcountry u = (Ipgcountry) session.get(Ipgcountry.class, inputBean
                    .getCountryCode().trim());

            if (u != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(u.getCountrycode())
                        .append("|").append(u.getDescription())
                        .append("|").append(u.getSortkey())
                        .append("|").append(u.getCountryisocode());

                u.setDescription(inputBean.getDescription());
                u.setSortkey(new BigDecimal(inputBean.getSortKey()));
                u.setCountryisocode(inputBean.getCountryisocode().trim());
                u.setLastupdateduser(audit.getLastupdateduser());
                u.setLastupdatedtime(sysDate);

                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(u.getCountrycode())
                        .append("|").append(u.getDescription())
                        .append("|").append(u.getSortkey())
                        .append("|").append(u.getCountryisocode());
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

    /**
     * @param inputBean
     * @param audit
     * @return
     * @throws Exception
     */
    public String deleteCountry(CountryInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgcountry u = (Ipgcountry) session.get(
                    Ipgcountry.class, inputBean
                            .getCountryCode().trim());
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

}
