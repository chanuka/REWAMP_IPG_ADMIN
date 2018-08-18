/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.MerchantRiskProfileBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.MerchantRiskProfileInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgbin;
import com.epic.epay.ipg.util.mapping.Ipgcountry;
import com.epic.epay.ipg.util.mapping.Ipgcurrency;
import com.epic.epay.ipg.util.mapping.Ipgmerchantcategory;
import com.epic.epay.ipg.util.mapping.Ipgriskprofile;
import com.epic.epay.ipg.util.mapping.Ipgriskprofileblockedbin;
import com.epic.epay.ipg.util.mapping.IpgriskprofileblockedbinId;
import com.epic.epay.ipg.util.mapping.Ipgriskprofileblockedcountry;
import com.epic.epay.ipg.util.mapping.IpgriskprofileblockedcountryId;
import com.epic.epay.ipg.util.mapping.Ipgriskprofileblockedcurrency;
import com.epic.epay.ipg.util.mapping.IpgriskprofileblockedcurrencyId;
import com.epic.epay.ipg.util.mapping.Ipgriskprofileblockedmcc;
import com.epic.epay.ipg.util.mapping.IpgriskprofileblockedmccId;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author chalitha
 */
public class MerchantRiskProfileDAO {

    public List<MerchantRiskProfileBean> getSearchList(MerchantRiskProfileInputBean inputBean, int rows, int from, String orderBy) throws Exception {

        List<MerchantRiskProfileBean> dataList = new ArrayList<MerchantRiskProfileBean>();
        Session session = null;
        try {
            long count = 0;
            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            Query queryCount = session
                    .createQuery("select count(riskprofilecode) from Ipgriskprofile as u where " + where);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }

                Query querySearch = session.createQuery("from Ipgriskprofile u where " + where
                        + orderBy);

                querySearch.setMaxResults(rows);
                querySearch.setFirstResult(from);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    MerchantRiskProfileBean riskProfileBean = new MerchantRiskProfileBean();
                    Ipgriskprofile riskProfile = (Ipgriskprofile) it.next();

                    try {
                        riskProfileBean.setRiskprofilecode(riskProfile.getRiskprofilecode().toString());
                    } catch (NullPointerException npe) {
                        riskProfileBean.setRiskprofilecode("--");
                    }
                    try {
                        riskProfileBean.setDescription(riskProfile.getDescription());
                    } catch (NullPointerException npe) {
                        riskProfileBean.setDescription("--");
                    }
                    try {
                        riskProfileBean.setStatus(riskProfile.getIpgstatus().getDescription());
                    } catch (NullPointerException npe) {
                        riskProfileBean.setStatus("--");
                    }

                    try {
                        riskProfileBean.setMaxsingletxnlimit(riskProfile.getMaximumsingletxnlimit().toString());
                    } catch (NullPointerException npe) {
                        riskProfileBean.setMaxsingletxnlimit("--");
                    }

                    try {
                        riskProfileBean.setMinsingletxnlimit(riskProfile.getMinimumsingletxnlimit().toString());
                    } catch (NullPointerException npe) {
                        riskProfileBean.setMinsingletxnlimit("--");
                    }

                    try {
                        riskProfileBean.setMaxtxncount(riskProfile.getMaximumtxncount().toString());
                    } catch (NullPointerException npe) {
                        riskProfileBean.setMaxtxncount("--");
                    }
                    try {
                        riskProfileBean.setMaxtxnamount(riskProfile.getMaximumtotaltxnamount().toString());
                    } catch (NullPointerException npe) {
                        riskProfileBean.setMaxtxnamount("--");
                    }
                    try {
                        riskProfileBean.setMaxpasswordcount(riskProfile.getMaximumpasswordcount().toString());
                    } catch (NullPointerException npe) {
                        riskProfileBean.setMaxpasswordcount("--");
                    }
                    try {
                        riskProfileBean.setCreatedtime(riskProfile.getCreatedtime().toString().substring(0, riskProfile.getCreatedtime().toString().length() - 2));
                    } catch (NullPointerException npe) {
                        riskProfileBean.setCreatedtime("--");
                    }

                    riskProfileBean.setFullCount(count);
                    dataList.add(riskProfileBean);
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

    private String makeWhereClause(MerchantRiskProfileInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getRiskprofilecode() != null && !inputBean.getRiskprofilecode().trim().isEmpty()) {
            where += " and lower(u.riskprofilecode) like lower('%" + inputBean.getRiskprofilecode().trim() + "%')";
        }
        if (inputBean.getDescription() != null && !inputBean.getDescription().trim().isEmpty()) {
            where += " and lower(u.description) like lower('%" + inputBean.getDescription().trim() + "%')";
        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().trim().isEmpty()) {
            where += " and u.ipgstatus.statuscode = '" + inputBean.getStatus().trim() + "'";
        }
        if (inputBean.getMaxsingletxnlimit() != null && !inputBean.getMaxsingletxnlimit().trim().isEmpty()) {
            where += " and lower(u.maximumsingletxnlimit) like lower('%" + inputBean.getMaxsingletxnlimit().trim() + "%')";
        }
        if (inputBean.getMinsingletxnlimit() != null && !inputBean.getMinsingletxnlimit().trim().isEmpty()) {
            where += " and lower(u.minimumsingletxnlimit) like lower('%" + inputBean.getMinsingletxnlimit().trim() + "%')";
        }
        if (inputBean.getMaxtxncount() != null && !inputBean.getMaxtxncount().trim().isEmpty()) {
            where += " and lower(u.maximumtxncount) like lower('%" + inputBean.getMaxtxncount().trim() + "%')";
        }
        if (inputBean.getMaxtxnamount() != null && !inputBean.getMaxtxnamount().trim().isEmpty()) {
            where += " and lower(u.maximumtotaltxnamount) like lower('%" + inputBean.getMaxtxnamount().trim() + "%')";
        }
        if (inputBean.getMaxpasswordcount() != null && !inputBean.getMaxpasswordcount().trim().isEmpty()) {
            where += " and lower(u.maximumpasswordcount) like lower('%" + inputBean.getMaxpasswordcount().trim() + "%')";
        }
        return where;
    }

    public String deleteMerchantRiskProfile(MerchantRiskProfileInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgriskprofile u = (Ipgriskprofile) session.get(Ipgriskprofile.class, inputBean.getRiskprofilecode().trim());
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

    public String insertMerchantRiskProfile(MerchantRiskProfileInputBean InputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgriskprofile) session.get(Ipgriskprofile.class, InputBean.getRiskprofilecode().trim()) == null) {
                txn = session.beginTransaction();

                Ipgriskprofile riskprofile = new Ipgriskprofile();

                riskprofile.setRiskprofilecode(InputBean.getRiskprofilecode().trim());

                riskprofile.setDescription(InputBean.getDescription().trim());

                riskprofile.setMaximumsingletxnlimit(new BigDecimal(InputBean.getMaxsingletxnlimit()));

                riskprofile.setMinimumsingletxnlimit(new BigDecimal(InputBean.getMinsingletxnlimit()));

                riskprofile.setMaximumtxncount(new Short(InputBean.getMaxtxncount()));

                riskprofile.setMaximumtotaltxnamount(new BigDecimal(InputBean.getMaxtxnamount()));

                riskprofile.setMaximumpasswordcount(new Byte(InputBean.getMaxpasswordcount()));

                Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, InputBean.getStatus().trim());
                riskprofile.setIpgstatus(status);

                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(riskprofile.getRiskprofilecode())
                        .append("|").append(riskprofile.getDescription())
                        .append("|").append(riskprofile.getIpgstatus().getDescription())
                        .append("|").append(riskprofile.getMaximumsingletxnlimit())
                        .append("|").append(riskprofile.getMinimumsingletxnlimit())
                        .append("|").append(riskprofile.getMaximumtxncount())
                        .append("|").append(riskprofile.getMaximumtotaltxnamount())
                        .append("|").append(riskprofile.getMaximumpasswordcount());

                //blocked countries
                HashSet<Ipgriskprofileblockedcountry> blockedcountryList = new HashSet<Ipgriskprofileblockedcountry>();
                List<String> newblockedcountryList = InputBean.getBlockedcountry();
                // for new audit value
                newValue.append("|[");

                if (newblockedcountryList.size() > 0) {

                    for (String ct : newblockedcountryList) {

                        Ipgriskprofileblockedcountry blockedcountry = new Ipgriskprofileblockedcountry();
                        IpgriskprofileblockedcountryId id = new IpgriskprofileblockedcountryId();
                        id.setCountrycode(ct.trim());
                        id.setRiskprofilecode(riskprofile.getRiskprofilecode());
                        blockedcountry.setId(id);
                        blockedcountry.setCreatedtime(sysDate);

                        //for audit new value
                        newValue.append(ct).append(",");

                        blockedcountryList.add(blockedcountry);
                    }
                    //for audit new value
                    newValue.deleteCharAt(newValue.length() - 1).append("]");

                    riskprofile.setIpgriskprofileblockedcountries(blockedcountryList);
                } else {
                    // for new audit value
                    newValue.append("]");
                }

                //blocked MCCs
                HashSet<Ipgriskprofileblockedmcc> blockedmccList = new HashSet<Ipgriskprofileblockedmcc>();
                List<String> newblockedmccList = InputBean.getBlockedcategories();
                // for new audit value
                newValue.append("|[");

                if (newblockedmccList.size() > 0) {

                    for (String mc : newblockedmccList) {

                        Ipgriskprofileblockedmcc blockedmcc = new Ipgriskprofileblockedmcc();
                        IpgriskprofileblockedmccId id = new IpgriskprofileblockedmccId();
                        id.setMcccode(mc.trim());
                        id.setRiskprofilecode(riskprofile.getRiskprofilecode());

                        blockedmcc.setId(id);
                        blockedmcc.setCreatedtime(sysDate);

                        //for audit new value
                        newValue.append(mc).append(",");

                        blockedmccList.add(blockedmcc);

                    }
                    //for audit new value
                    newValue.deleteCharAt(newValue.length() - 1).append("]");

                    riskprofile.setIpgriskprofileblockedmccs(blockedmccList);
                } else {
                    // for new audit value
                    newValue.append("]");

                }

                //blocked currencies
                HashSet<Ipgriskprofileblockedcurrency> blockedcurrencyList = new HashSet<Ipgriskprofileblockedcurrency>();
                List<String> newblockedcurrencyList = InputBean.getBlockedcurrencytypes();
                // for new audit value
                newValue.append("|[");

                if (newblockedcurrencyList.size() > 0) {

                    for (String c : newblockedcurrencyList) {

                        Ipgriskprofileblockedcurrency blockedct = new Ipgriskprofileblockedcurrency();
                        IpgriskprofileblockedcurrencyId id = new IpgriskprofileblockedcurrencyId();
                        id.setCurrencycode(c.trim());
                        id.setRiskprofilecode(riskprofile.getRiskprofilecode());
                        blockedct.setId(id);

                        blockedct.setCreatedtime(sysDate);

                        //for audit new value
                        newValue.append(c).append(",");

                        blockedcurrencyList.add(blockedct);

                    }
                    //for audit new value
                    newValue.deleteCharAt(newValue.length() - 1).append("]");

                    riskprofile.setIpgriskprofileblockedcurrencies(blockedcurrencyList);

                } else {
                    // for new audit value
                    newValue.append("]");

                }

                //blocked BINs
                HashSet<Ipgriskprofileblockedbin> blockedbinList = new HashSet<Ipgriskprofileblockedbin>();
                List<String> newblockedbinList = InputBean.getBlockedbin();
                // for new audit value
                newValue.append("|[");

                if (newblockedbinList.size() > 0) {

                    for (String c : newblockedbinList) {

                        Ipgriskprofileblockedbin blockedbin = new Ipgriskprofileblockedbin();
                        IpgriskprofileblockedbinId id = new IpgriskprofileblockedbinId();
                        id.setBin(c.trim());
                        id.setRiskprofilecode(riskprofile.getRiskprofilecode());
                        blockedbin.setId(id);

                        blockedbin.setCreatedtime(sysDate);

                        //for audit new value
                        newValue.append(c).append(",");

                        blockedbinList.add(blockedbin);

                    }
                    //for audit new value
                    newValue.deleteCharAt(newValue.length() - 1).append("]");

                    riskprofile.setIpgriskprofileblockedbins(blockedbinList);

                } else {
                    // for new audit value
                    newValue.append("]");

                }

                riskprofile.setLastupdatedtime(sysDate);
                riskprofile.setLastupdateduser(audit.getLastupdateduser());
                riskprofile.setCreatedtime(sysDate);

                audit.setNewvalue(newValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(riskprofile);

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

    public String updateMerchantRiskProfile(MerchantRiskProfileInputBean InputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            String riskprofilecode = InputBean.getRiskprofilecode().trim();
            Ipgriskprofile riskprofile = (Ipgriskprofile) session.get(Ipgriskprofile.class, riskprofilecode);

            if (riskprofile != null) {
                /**
                 * for audit old value
                 */
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(riskprofile.getRiskprofilecode())
                        .append("|").append(riskprofile.getDescription())
                        .append("|").append(riskprofile.getIpgstatus().getDescription())
                        .append("|").append(riskprofile.getMaximumsingletxnlimit())
                        .append("|").append(riskprofile.getMinimumsingletxnlimit())
                        .append("|").append(riskprofile.getMaximumtxncount())
                        .append("|").append(riskprofile.getMaximumtotaltxnamount())
                        .append("|").append(riskprofile.getMaximumpasswordcount());

                riskprofile.setDescription(InputBean.getDescription().trim());

                riskprofile.setMaximumsingletxnlimit(new BigDecimal(InputBean.getMaxsingletxnlimit()));

                riskprofile.setMinimumsingletxnlimit(new BigDecimal(InputBean.getMinsingletxnlimit()));

                riskprofile.setMaximumtxncount(new Short(InputBean.getMaxtxncount()));

                riskprofile.setMaximumtotaltxnamount(new BigDecimal(InputBean.getMaxtxnamount()));

                riskprofile.setMaximumpasswordcount(new Byte(InputBean.getMaxpasswordcount()));

                Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, InputBean.getStatus().trim());
                riskprofile.setIpgstatus(status);

                /**
                 * for audit new value
                 */
                StringBuilder newValue = new StringBuilder();
                newValue.append(riskprofile.getRiskprofilecode())
                        .append("|").append(riskprofile.getDescription())
                        .append("|").append(riskprofile.getIpgstatus().getDescription())
                        .append("|").append(riskprofile.getMaximumsingletxnlimit())
                        .append("|").append(riskprofile.getMinimumsingletxnlimit())
                        .append("|").append(riskprofile.getMaximumtxncount())
                        .append("|").append(riskprofile.getMaximumtotaltxnamount())
                        .append("|").append(riskprofile.getMaximumpasswordcount());

                /////////blocked things
                String sql = "from Ipgriskprofileblockedbin as p where p.id.riskprofilecode =:riskprofilecode";
                Query query = session.createQuery(sql).setString("riskprofilecode", riskprofilecode);
                List<Ipgriskprofileblockedbin> blockedBinList = query.list();
                List<String> newblockedbin = InputBean.getBlockedbin();

                //for audit new/old values
                newValue.append("|[");
                oldValue.append("|[");

                for (Ipgriskprofileblockedbin pt : blockedBinList) {
                    if (newblockedbin.contains(pt.getId().getBin())) {
                        pt.setCreatedtime(sysDate);
                        session.update(pt);

                        //for audit new/old values
                        newValue.append(pt.getId().getBin()).append(",");
                        oldValue.append(pt.getId().getBin()).append(",");

                        newblockedbin.remove(pt.getId().getBin());
                    } else {
                        //for audit old value
                        oldValue.append(pt.getId().getBin()).append(",");

                        session.delete(pt);
                        session.flush();
                    }
                }

                for (String bin : newblockedbin) {

                    IpgriskprofileblockedbinId ptId = new IpgriskprofileblockedbinId();
                    ptId.setBin(bin);
                    ptId.setRiskprofilecode(riskprofilecode);

                    Ipgriskprofileblockedbin pt = new Ipgriskprofileblockedbin();
                    pt.setId(ptId);
                    pt.setCreatedtime(sysDate);

                    //for audit new value
                    newValue.append(bin).append(",");

                    session.save(pt);

                }
                /**
                 * for audit new value
                 */
                if (newValue.charAt(newValue.length() - 1) != '[') {
                    newValue.deleteCharAt(newValue.length() - 1).append("]");
                } else {
                    newValue.append("]");
                }
                if (oldValue.charAt(oldValue.length() - 1) != '[') {
                    oldValue.deleteCharAt(oldValue.length() - 1).append("]");
                } else {
                    oldValue.append("]");
                }

                String sql1 = "from Ipgriskprofileblockedcountry as p where p.id.riskprofilecode =:riskprofilecode";
                Query query1 = session.createQuery(sql1).setString("riskprofilecode", riskprofilecode);
                List<Ipgriskprofileblockedcountry> blockedCountryList = query1.list();
                List<String> newblockedcountry = InputBean.getBlockedcountry();
                //for audit new/old values
                newValue.append("|[");
                oldValue.append("|[");

                for (Ipgriskprofileblockedcountry pt : blockedCountryList) {
                    if (newblockedcountry.contains(pt.getId().getCountrycode())) {
                        pt.setCreatedtime(sysDate);

                        //for audit new/old values
                        newValue.append(pt.getId().getCountrycode()).append(",");
                        oldValue.append(pt.getId().getCountrycode()).append(",");

                        session.update(pt);
                        newblockedcountry.remove(pt.getId().getCountrycode());
                    } else {
                        //for audit old value
                        oldValue.append(pt.getId().getCountrycode()).append(",");

                        session.delete(pt);
                        session.flush();
                    }
                }

                for (String country : newblockedcountry) {

                    IpgriskprofileblockedcountryId ptId = new IpgriskprofileblockedcountryId();
                    ptId.setCountrycode(country);
                    ptId.setRiskprofilecode(riskprofilecode);

                    Ipgriskprofileblockedcountry pt = new Ipgriskprofileblockedcountry();
                    pt.setId(ptId);
                    pt.setCreatedtime(sysDate);

                    //for audit new value
                    newValue.append(country).append(",");

                    session.save(pt);

                }

                /**
                 * for audit new/old values
                 */
                if (newValue.charAt(newValue.length() - 1) != '[') {
                    newValue.deleteCharAt(newValue.length() - 1).append("]");
                } else {
                    newValue.append("]");
                }
                if (oldValue.charAt(oldValue.length() - 1) != '[') {
                    oldValue.deleteCharAt(oldValue.length() - 1).append("]");
                } else {
                    oldValue.append("]");
                }

                String sql2 = "from Ipgriskprofileblockedcurrency as p where p.id.riskprofilecode =:riskprofilecode";
                Query query2 = session.createQuery(sql2).setString("riskprofilecode", riskprofilecode);
                List<Ipgriskprofileblockedcurrency> blockedCurrencyList = query2.list();
                List<String> newblockedcurrency = InputBean.getBlockedcurrencytypes();
                //for audit new/old values
                newValue.append("|[");
                oldValue.append("|[");

                for (Ipgriskprofileblockedcurrency pt : blockedCurrencyList) {
                    if (newblockedcurrency.contains(pt.getId().getCurrencycode())) {
                        pt.setCreatedtime(sysDate);

                        //for audit new/old values
                        newValue.append(pt.getId().getCurrencycode()).append(",");
                        oldValue.append(pt.getId().getCurrencycode()).append(",");

                        session.update(pt);
                        newblockedcurrency.remove(pt.getId().getCurrencycode());
                    } else {
                        //for audit old value
                        oldValue.append(pt.getId().getCurrencycode()).append(",");

                        session.delete(pt);
                        session.flush();
                    }
                }

                for (String currency : newblockedcurrency) {

                    IpgriskprofileblockedcurrencyId ptId = new IpgriskprofileblockedcurrencyId();
                    ptId.setCurrencycode(currency);
                    ptId.setRiskprofilecode(riskprofilecode);

                    Ipgriskprofileblockedcurrency pt = new Ipgriskprofileblockedcurrency();
                    pt.setId(ptId);
                    pt.setCreatedtime(sysDate);

                    //for audit new value
                    newValue.append(currency).append(",");

                    session.save(pt);

                }
                /**
                 * for audit new/old values
                 */
                if (newValue.charAt(newValue.length() - 1) != '[') {
                    newValue.deleteCharAt(newValue.length() - 1).append("]");
                } else {
                    newValue.append("]");
                }
                if (oldValue.charAt(oldValue.length() - 1) != '[') {
                    oldValue.deleteCharAt(oldValue.length() - 1).append("]");
                } else {
                    oldValue.append("]");
                }

                String sql3 = "from Ipgriskprofileblockedmcc as p where p.id.riskprofilecode =:riskprofilecode";
                Query query3 = session.createQuery(sql3).setString("riskprofilecode", riskprofilecode);
                List<Ipgriskprofileblockedmcc> blockedMccList = query3.list();
                List<String> newblockedmcc = InputBean.getBlockedcategories();
                //for audit new/old values
                newValue.append("|[");
                oldValue.append("|[");

                for (Ipgriskprofileblockedmcc pt : blockedMccList) {
                    if (newblockedmcc.contains(pt.getId().getMcccode())) {
                        pt.setCreatedtime(sysDate);

                        //for audit new/old values
                        newValue.append(pt.getId().getMcccode()).append(",");
                        oldValue.append(pt.getId().getMcccode()).append(",");

                        session.update(pt);
                        newblockedmcc.remove(pt.getId().getMcccode());
                    } else {
                        //for audit old value
                        oldValue.append(pt.getId().getMcccode()).append(",");

                        session.delete(pt);
                        session.flush();
                    }
                }

                for (String mcc : newblockedmcc) {

                    IpgriskprofileblockedmccId ptId = new IpgriskprofileblockedmccId();
                    ptId.setMcccode(mcc);
                    ptId.setRiskprofilecode(riskprofilecode);

                    Ipgriskprofileblockedmcc pt = new Ipgriskprofileblockedmcc();
                    pt.setId(ptId);
                    pt.setCreatedtime(sysDate);

                    //for audit new value
                    newValue.append(mcc).append(",");

                    session.save(pt);

                }
                /**
                 * for audit new/old values
                 */
                if (newValue.charAt(newValue.length() - 1) != '[') {
                    newValue.deleteCharAt(newValue.length() - 1).append("]");
                } else {
                    newValue.append("]");
                }
                if (oldValue.charAt(oldValue.length() - 1) != '[') {
                    oldValue.deleteCharAt(oldValue.length() - 1).append("]");
                } else {
                    oldValue.append("]");
                }
                audit.setNewvalue(newValue.toString());
                audit.setOldvalue(oldValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.update(riskprofile);

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

    public Map<String, String> getDefultCountryList() throws Exception {
        List<Ipgcountry> countrylist = new ArrayList<Ipgcountry>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgcountry ";
            Query query = session.createQuery(hql);
            countrylist = query.list();

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
        Map<String, String> allowedcountries = new HashMap<String, String>();

        for (Iterator<Ipgcountry> it = countrylist.iterator(); it.hasNext();) {

            Ipgcountry ipgcountry = it.next();
            allowedcountries.put(ipgcountry.getCountrycode(),
                    ipgcountry.getDescription());

        }
        return allowedcountries;
    }

    public Map<String, String> getDefultMerchantCategoriesList() throws Exception {
        List<Ipgmerchantcategory> categories = new ArrayList<Ipgmerchantcategory>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgmerchantcategory ";
            Query query = session.createQuery(hql);
            categories = query.list();

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
        Map<String, String> categoriesmap = new HashMap<String, String>();

        for (Iterator<Ipgmerchantcategory> it = categories.iterator(); it.hasNext();) {

            Ipgmerchantcategory ipgmerchantcategory = it.next();
            categoriesmap.put(ipgmerchantcategory.getMcc(),
                    ipgmerchantcategory.getDescription());

        }
        return categoriesmap;
    }

    public Map<String, String> getDefaultCurrencyList() throws Exception {
        List<Ipgcurrency> currency = new ArrayList<Ipgcurrency>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgcurrency ";
            Query query = session.createQuery(hql);
            currency = query.list();

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
        Map<String, String> currencymap = new HashMap<String, String>();

        for (Iterator<Ipgcurrency> it = currency.iterator(); it.hasNext();) {

            Ipgcurrency ipgcurrency = it.next();
            currencymap.put(ipgcurrency.getCurrencyisocode(),
                    ipgcurrency.getDescription());

        }
        return currencymap;
    }

    public Map<String, String> getDefaultBinList() throws Exception {
        List<Ipgbin> bin = new ArrayList<Ipgbin>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Ipgbin ";
            Query query = session.createQuery(hql);
            bin = query.list();

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
        Map<String, String> binmap = new HashMap<String, String>();

        for (Iterator<Ipgbin> it = bin.iterator(); it.hasNext();) {

            Ipgbin ipgbin = it.next();
            binmap.put(ipgbin.getBin(),
                    ipgbin.getDescription());

        }
        return binmap;
    }

    public Ipgriskprofile findMerchantRiskProfileById(String riskprofilecode) throws Exception {

        Ipgriskprofile riskprofile = new Ipgriskprofile();
        Ipgriskprofileblockedcountry ipgriskprofileblockedcountry = new Ipgriskprofileblockedcountry();
        Ipgriskprofileblockedcurrency ipgriskprofileblockedcurrency = new Ipgriskprofileblockedcurrency();
        Ipgriskprofileblockedmcc ipgriskprofileblockedmcc = new Ipgriskprofileblockedmcc();
        Ipgriskprofileblockedbin ipgriskprofileblockedbin = new Ipgriskprofileblockedbin();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql1 = "from Ipgriskprofile as u join fetch u.ipgstatus where u.riskprofilecode=:riskprofilecode";
            Query query1 = session.createQuery(sql1).setString("riskprofilecode", riskprofilecode);
            riskprofile = (Ipgriskprofile) query1.list().get(0);

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
        return riskprofile;
    }

    public Map<String, String> findblockedbin(String riskprofilecode) throws Exception {
        List<Ipgbin> blockedBinList = new ArrayList<Ipgbin>();
        Map<String, String> blockedbinmap = new HashMap<String, String>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql1 = "Select u FROM Ipgbin u, Ipgriskprofileblockedbin v where v.id.riskprofilecode =:riskprofilecode and v.id.bin = u.bin";
            Query query1 = session.createQuery(sql1).setString("riskprofilecode", riskprofilecode);
            blockedBinList = (List<Ipgbin>) query1.list();

            for (Iterator<Ipgbin> it = blockedBinList.iterator(); it.hasNext();) {
                Ipgbin ipgbin = it.next();
                blockedbinmap.put(ipgbin.getBin(),
                        ipgbin.getDescription());

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

        return blockedbinmap;

    }

    public Map<String, String> findblockedcountry(String riskprofilecode) throws Exception {

        List<Ipgcountry> blockedCountryList = new ArrayList<Ipgcountry>();
        Map<String, String> blockedcountrymap = new HashMap<String, String>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql1 = "Select u FROM Ipgcountry u, Ipgriskprofileblockedcountry v where v.id.riskprofilecode =:riskprofilecode and v.id.countrycode = u.countrycode";
            Query query1 = session.createQuery(sql1).setString("riskprofilecode", riskprofilecode);
            blockedCountryList = (List<Ipgcountry>) query1.list();

            for (Iterator<Ipgcountry> it = blockedCountryList.iterator(); it.hasNext();) {
                Ipgcountry ipgcountry = it.next();
                blockedcountrymap.put(ipgcountry.getCountrycode(),
                        ipgcountry.getDescription());

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

        return blockedcountrymap;

    }

    public Map<String, String> findblockedcurrency(String riskprofilecode) throws Exception {

        List<Ipgcurrency> blockedCurrencyList = new ArrayList<Ipgcurrency>();
        Map<String, String> blockedcurrencymap = new HashMap<String, String>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql1 = "Select u FROM Ipgcurrency u, Ipgriskprofileblockedcurrency v where v.id.riskprofilecode =:riskprofilecode and v.id.currencycode = u.currencyisocode";
            Query query1 = session.createQuery(sql1).setString("riskprofilecode", riskprofilecode);
            blockedCurrencyList = (List<Ipgcurrency>) query1.list();

            for (Iterator<Ipgcurrency> it = blockedCurrencyList.iterator(); it.hasNext();) {
                Ipgcurrency ipgcurrency = it.next();
                blockedcurrencymap.put(ipgcurrency.getCurrencyisocode(),
                        ipgcurrency.getDescription());

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

        return blockedcurrencymap;

    }

    public Map<String, String> findblockedmcc(String riskprofilecode) throws Exception {

        List<Ipgmerchantcategory> blockedCountryList = new ArrayList<Ipgmerchantcategory>();
        Map<String, String> blockedmccmap = new HashMap<String, String>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql1 = "Select u FROM Ipgmerchantcategory u, Ipgriskprofileblockedmcc v where v.id.riskprofilecode =:riskprofilecode and v.id.mcccode = u.mcc";
            Query query1 = session.createQuery(sql1).setString("riskprofilecode", riskprofilecode);
            blockedCountryList = (List<Ipgmerchantcategory>) query1.list();

            for (Iterator<Ipgmerchantcategory> it = blockedCountryList.iterator(); it.hasNext();) {
                Ipgmerchantcategory ipgmcc = it.next();
                blockedmccmap.put(ipgmcc.getMcc(),
                        ipgmcc.getDescription());

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

        return blockedmccmap;

    }
}
