/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.merchantmanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantAddonBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantAddonInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcommonfilepath;
import com.epic.epay.ipg.util.mapping.Ipgmerchantaddon;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import org.hibernate.HibernateException;

/**
 *
 * @created on : Nov 19, 2013, 11:35:00 AM
 * @author : thushanth
 *
 */
public class MerchantAddonDAO {

    public String insertMerchantAddon(MerchantAddonInputBean inputBean, Ipgsystemaudit audit, File image, File filetoCreate) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgmerchantaddon) session.get(Ipgmerchantaddon.class, inputBean.getMerchantNameID().trim()) == null) {
                txn = session.beginTransaction();

                FileUtils.copyFile(image, filetoCreate);
                Ipgmerchantaddon ipgmerchantaddon = new Ipgmerchantaddon();

                ipgmerchantaddon.setMerchantid(inputBean.getMerchantNameID().trim());
                ipgmerchantaddon.setLogopath(inputBean.getLogoPath().trim());
                ipgmerchantaddon.setXcordinate(inputBean.getCordinateX().trim());
                ipgmerchantaddon.setYcordinate(inputBean.getCordinateY().trim());
                ipgmerchantaddon.setDisplaytext(inputBean.getDisplayText().trim());
                ipgmerchantaddon.setThemecolor(inputBean.getThemeColor().trim());
                ipgmerchantaddon.setFontfamily(inputBean.getFontFamily().trim());
                ipgmerchantaddon.setFontstyle(inputBean.getFontStyle().trim());
                ipgmerchantaddon.setFontsize(new Integer(inputBean.getFontSize().trim()));
                ipgmerchantaddon.setRemarks(inputBean.getRemarks().trim());

                ipgmerchantaddon.setCreatedtime(sysDate);
                ipgmerchantaddon.setLastupdatedtime(sysDate);
                ipgmerchantaddon.setLastupdateduser(audit.getLastupdateduser());
                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(ipgmerchantaddon.getMerchantid())
                        .append("|").append(ipgmerchantaddon.getDisplaytext())
                        .append("|").append(ipgmerchantaddon.getXcordinate())
                        .append("|").append(ipgmerchantaddon.getYcordinate())
                        .append("|").append(ipgmerchantaddon.getThemecolor())
                        .append("|").append(ipgmerchantaddon.getFontfamily())
                        .append("|").append(ipgmerchantaddon.getFontsize())
                        .append("|").append(ipgmerchantaddon.getFontstyle())
                        .append("|").append(ipgmerchantaddon.getRemarks())
                        .append("|").append(ipgmerchantaddon.getLogopath());
                audit.setNewvalue(newValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(ipgmerchantaddon);

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

    public String deleteMerchantAddon(MerchantAddonInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgmerchantaddon u = (Ipgmerchantaddon) session.get(Ipgmerchantaddon.class, inputBean.getMerchantID().trim());

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

    public String updateMerchantAddon(MerchantAddonInputBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgmerchantaddon u = (Ipgmerchantaddon) session.get(Ipgmerchantaddon.class, inputBean.getMerchantNameID().trim());

            if (u != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(u.getMerchantid())
                        .append("|").append(u.getDisplaytext())
                        .append("|").append(u.getXcordinate())
                        .append("|").append(u.getYcordinate())
                        .append("|").append(u.getThemecolor())
                        .append("|").append(u.getFontfamily())
                        .append("|").append(u.getFontstyle())
                        .append("|").append(u.getFontsize())
                        .append("|").append(u.getRemarks())
                        .append("|").append(u.getLogopath());

                u.setMerchantid(inputBean.getMerchantNameID().trim());
                if (inputBean.getLogoPath() != null) {
                    u.setLogopath(inputBean.getLogoPath().trim());
                }
                u.setXcordinate(inputBean.getCordinateX().trim());
                u.setYcordinate(inputBean.getCordinateY().trim());
                u.setDisplaytext(inputBean.getDisplayText().trim());
                u.setThemecolor(inputBean.getThemeColor().trim());
                u.setFontfamily(inputBean.getFontFamily().trim());
                u.setFontstyle(inputBean.getFontStyle().trim());
                u.setFontsize(new Integer(inputBean.getFontSize().trim()));
                u.setRemarks(inputBean.getRemarks().trim());

                u.setLastupdatedtime(sysDate);
                u.setLastupdateduser(audit.getLastupdateduser());

                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(u.getMerchantid())
                        .append("|").append(u.getDisplaytext())
                        .append("|").append(u.getXcordinate())
                        .append("|").append(u.getYcordinate())
                        .append("|").append(u.getThemecolor())
                        .append("|").append(u.getFontfamily())
                        .append("|").append(u.getFontstyle())
                        .append("|").append(u.getFontsize())
                        .append("|").append(u.getRemarks())
                        .append("|").append(u.getLogopath());
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

    //get search list
    public List<MerchantAddonBean> getSearchList(MerchantAddonInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<MerchantAddonBean> dataList = new ArrayList<MerchantAddonBean>();
        Session session = null;
        try {

            long count = 0;
            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(u.merchantid) from Ipgmerchantaddon as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }

                String sqlSearch = "from Ipgmerchantaddon u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    MerchantAddonBean merchantaddonbean = new MerchantAddonBean();
                    Ipgmerchantaddon ipgmerchantaddon = (Ipgmerchantaddon) it.next();

                    try {
                        merchantaddonbean.setMerchantId(ipgmerchantaddon.getMerchantid());
                    } catch (NullPointerException npe) {
                        merchantaddonbean.setMerchantId("--");
                    }
                    try {
                        merchantaddonbean.setMerchantname(ipgmerchantaddon.getIpgmerchant().getMerchantname());
                    } catch (NullPointerException npe) {
                        merchantaddonbean.setMerchantname("--");
                    }
                    try {
                        merchantaddonbean.setLogopath(ipgmerchantaddon.getLogopath());
                    } catch (NullPointerException npe) {
                        merchantaddonbean.setLogopath("--");
                    }
                    try {
                        merchantaddonbean.setXcordinate(ipgmerchantaddon.getXcordinate());
                    } catch (NullPointerException npe) {
                        merchantaddonbean.setXcordinate("--");
                    }
                    try {
                        merchantaddonbean.setYcordinate(ipgmerchantaddon.getYcordinate());
                    } catch (NullPointerException npe) {
                        merchantaddonbean.setYcordinate("--");
                    }
                    try {
                        merchantaddonbean.setDisplaytext(ipgmerchantaddon.getDisplaytext());
                    } catch (NullPointerException npe) {
                        merchantaddonbean.setDisplaytext("--");
                    }
                    try {
                        merchantaddonbean.setThemecolor(ipgmerchantaddon.getThemecolor());
                    } catch (NullPointerException npe) {
                        merchantaddonbean.setThemecolor("--");
                    }
//		                    try {
//			                      merchantaddonbean.setFontfamily(ipgmerchantaddon.getFontfamily());
//			                } catch (NullPointerException npe) {
//			                        merchantaddonbean.setFontfamily("--");
//			                }
//		                    try {
//			                      merchantaddonbean.setFontstyle(ipgmerchantaddon.getFontstyle());
//			                } catch (NullPointerException npe) {
//			                        merchantaddonbean.setFontstyle("--");
//			                }
//		                    try {
//			                      merchantaddonbean.setFontsize(ipgmerchantaddon.getFontsize().toString());
//			                } catch (NullPointerException npe) {
//			                        merchantaddonbean.setFontsize("--");
//			                }
                    try {
                        merchantaddonbean.setRemarks(ipgmerchantaddon.getRemarks());
                    } catch (NullPointerException npe) {
                        merchantaddonbean.setRemarks("--");
                    }
                    try {
                        merchantaddonbean.setCreatedtime(ipgmerchantaddon.getCreatedtime().toString().substring(0, ipgmerchantaddon.getCreatedtime().toString().length() - 2));
                    } catch (NullPointerException npe) {
                        merchantaddonbean.setCreatedtime("--");
                    }

                    merchantaddonbean.setFullCount(count);

                    dataList.add(merchantaddonbean);
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

    private String makeWhereClause(MerchantAddonInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getMerchantNameID() != null && !inputBean.getMerchantNameID().trim().isEmpty()) {
            where += " and lower(u.merchantid) like lower('%" + inputBean.getMerchantNameID().trim() + "%')";
        }
        if (inputBean.getCordinateX() != null && !inputBean.getCordinateX().trim().isEmpty()) {
            where += " and lower(u.xcordinate) like lower('%" + inputBean.getCordinateX().trim() + "%')";
        }
        if (inputBean.getCordinateY() != null && !inputBean.getCordinateY().trim().isEmpty()) {
            where += " and lower(u.ycordinate) like lower('%" + inputBean.getCordinateY().trim() + "%')";
        }
        if (inputBean.getDisplayText() != null && !inputBean.getDisplayText().trim().isEmpty()) {
            where += " and lower(u.displaytext) like lower('%" + inputBean.getDisplayText().trim() + "%')";
        }
        if (inputBean.getRemarks() != null && !inputBean.getRemarks().trim().isEmpty()) {
            where += " and lower(u.remarks) like lower('%" + inputBean.getRemarks().trim() + "%')";
        }
        if (inputBean.getThemeColor() != null && !inputBean.getThemeColor().trim().isEmpty()) {
            where += " and lower(u.themecolor)like lower('%" + inputBean.getThemeColor().trim() + "%')";
        }
        return where;
    }

    public Ipgmerchantaddon findMerchantAddonByID(String merchantID) throws Exception {
        Ipgmerchantaddon ipgmerchantaddon = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgmerchantaddon as u where u.merchantid=:merchantid";
            Query query = session.createQuery(sql).setString("merchantid", merchantID);
            ipgmerchantaddon = (Ipgmerchantaddon) query.list().get(0);

        } catch (HibernateException e) {
            throw e;
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        return ipgmerchantaddon;

    }

    // get addon logo path
    public String getLogoPath(String osType) throws Exception {

        Session session = null;
        String logoPath = null;

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgcommonfilepath as u where u.ostype =:os";
            Query query = session.createQuery(sql).setString("os", osType);

            Iterator it = query.iterate();
            if (it.hasNext()) {
                Ipgcommonfilepath path = (Ipgcommonfilepath) it.next();
                logoPath = path.getAddonlogopath();
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
        return logoPath;

    }

}
