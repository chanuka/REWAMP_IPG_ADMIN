/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.HeadMerchantBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.HeadMerchantInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcommonconfig;
import com.epic.epay.ipg.util.mapping.Ipgcommonfilepath;
import com.epic.epay.ipg.util.mapping.Ipgcountry;
import com.epic.epay.ipg.util.mapping.Ipgheadmerchant;
import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgriskprofile;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Chalitha
 */
public class HeadMerchantDAO {

    public List<HeadMerchantBean> getSearchList(HeadMerchantInputBean inputBean, int rows, int from, String orderBy) throws Exception {

        List<HeadMerchantBean> dataList = new ArrayList<HeadMerchantBean>();
        Session session = null;
        try {
            long count = 0;
            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();
            Query queryCount = session.createQuery("select count(merchantcustomerid) from Ipgheadmerchant as u where " + where);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }

                Query querySearch = session.createQuery("from Ipgheadmerchant u where " + where + orderBy);

                querySearch.setMaxResults(rows);
                querySearch.setFirstResult(from);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    HeadMerchantBean headMerchantBean = new HeadMerchantBean();
                    Ipgheadmerchant ipgheadmerchant = (Ipgheadmerchant) it.next();

                    try {
                        headMerchantBean.setMerchantcustomerid(ipgheadmerchant.getMerchantcustomerid());
                    } catch (NullPointerException npe) {
                        headMerchantBean.setMerchantcustomerid("--");
                    }
                    try {
                        headMerchantBean.setMerchantid(ipgheadmerchant.getTransactioninitaiatedmerchntid());
                    } catch (NullPointerException npe) {
                        headMerchantBean.setMerchantcustomerid("--");
                    }
                    try {
                        headMerchantBean.setMerchantname(ipgheadmerchant.getMerchantname());
                    } catch (NullPointerException npe) {
                        headMerchantBean.setMerchantname("--");
                    }
                    try {
                        headMerchantBean.setAddress1(ipgheadmerchant.getIpgaddress().getAddress1());
                    } catch (NullPointerException npe) {
                        headMerchantBean.setAddress1("--");
                    }

                    try {
                        headMerchantBean.setCity(ipgheadmerchant.getIpgaddress().getCity());
                    } catch (NullPointerException npe) {
                        headMerchantBean.setCity("--");
                    }

                    try {
                        headMerchantBean.setEmail(ipgheadmerchant.getIpgaddress().getEmail());
                    } catch (NullPointerException npe) {
                        headMerchantBean.setEmail("--");
                    }
                    try {
                        headMerchantBean.setAuthreqstatus(ipgheadmerchant.getIpgstatusByAuthenticationrequied().getDescription());
                    } catch (NullPointerException npe) {
                        headMerchantBean.setAuthreqstatus("--");
                    }
                    try {
                        headMerchantBean.setTxninitstatus(ipgheadmerchant.getIpgstatusByTransactioninitaiatedstatus().getDescription());
                    } catch (NullPointerException npe) {
                        headMerchantBean.setTxninitstatus("--");
                    }
                    try {
                        headMerchantBean.setCreatedtime(ipgheadmerchant.getCreatedtime().toString().substring(0, ipgheadmerchant.getCreatedtime().toString().length() - 2));
                    } catch (NullPointerException npe) {
                        headMerchantBean.setCreatedtime("--");
                    }

                    headMerchantBean.setFullCount(count);
                    dataList.add(headMerchantBean);
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

    private String makeWhereClause(HeadMerchantInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getMerchantcustomerid() != null && !inputBean.getMerchantcustomerid().trim().isEmpty()) {
            where += " and lower(u.merchantcustomerid) like lower('%" + inputBean.getMerchantcustomerid().trim() + "%')";
        }
        if (inputBean.getMerchantid() != null && !inputBean.getMerchantid().trim().isEmpty()) {
            where += " and lower(u.transactioninitaiatedmerchntid) like lower('%" + inputBean.getMerchantid().trim() + "%')";
        }
        if (inputBean.getMerchantname() != null && !inputBean.getMerchantname().trim().isEmpty()) {
            where += " and lower(u.merchantname) like lower('%" + inputBean.getMerchantname().trim() + "%')";
        }
        if (inputBean.getAddress1() != null && !inputBean.getAddress1().trim().isEmpty()) {
            where += " and lower(u.ipgaddress.address1) like lower('%" + inputBean.getAddress1().trim() + "%')";
        }
        if (inputBean.getCity() != null && !inputBean.getCity().trim().isEmpty()) {
            where += " and lower(u.ipgaddress.city) like lower('%" + inputBean.getCity().trim() + "%')";
        }
        if (inputBean.getEmail() != null && !inputBean.getEmail().trim().isEmpty()) {
            where += " and lower(u.ipgaddress.email) like lower('%" + inputBean.getEmail().trim() + "%')";
        }
        if (inputBean.getAuthreqstatus()!= null && !inputBean.getAuthreqstatus().trim().isEmpty()) {
            where += " and u.ipgstatusByAuthenticationrequied.statuscode = '" + inputBean.getAuthreqstatus().trim() + "'";
        }
        if (inputBean.getTxninitstatus()!= null && !inputBean.getTxninitstatus().trim().isEmpty()) {
            where += " and u.ipgstatusByTransactioninitaiatedstatus.statuscode = '" + inputBean.getTxninitstatus().trim() + "'";
        }
        return where;
    }

    public String insertHeadMerchant(HeadMerchantInputBean inputBean, Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);
            if ((Ipgmerchant) session.get(Ipgmerchant.class, inputBean.getMerchantid()) == null) {
                if ((Ipgheadmerchant) session.get(Ipgheadmerchant.class, inputBean.getMerchantcustomerid().trim()) == null) {
                    txn = session.beginTransaction();

                    byte[] privateKey = new byte[8];
                    byte[] publicKey = new byte[8];
                    byte[] symetricKey = new byte[8];

                    Query query = session.createSQLQuery(
                            "CALL INSERTHEADMERCHANT(:merchantcus_id,:merchant_id,:txninit_status,:merchant_name,:address_1,:address_2,:city_name,:state_code,:postal_code,"
                            + ":province_name,:country_code,:mobile_no,:tel_no,:fax_no,:email_ad,:primary_url,:secondary_url,:date_of_registry,"
                            + ":date_of_expiry,:status_code,:contact_person,:remarks_dat,:last_updated_user,:private_key,:public_key,"
                            + ":symmetric_key,:security_mech,:exec_stat,:success_url,:error_url,:auth_required,:risk_profile)")
                            .setParameter("merchantcus_id", inputBean.getMerchantcustomerid().trim())
                            .setParameter("merchant_id", inputBean.getMerchantid().trim())
                            .setParameter("txninit_status", inputBean.getTxninitstatus().trim())
                            .setParameter("merchant_name", inputBean.getMerchantname().trim())
                            .setParameter("address_1", inputBean.getAddress1().trim())
                            .setParameter("address_2", inputBean.getAddress2().trim())
                            .setParameter("city_name", inputBean.getCity().trim())
                            .setParameter("state_code", inputBean.getStatecode().trim())
                            .setParameter("postal_code", inputBean.getPostalcode().trim())
                            .setParameter("province_name", inputBean.getProvince().trim())
                            .setParameter("country_code", inputBean.getCountry().trim())
                            .setParameter("mobile_no", inputBean.getMobile().trim())
                            .setParameter("tel_no", inputBean.getTel().trim())
                            .setParameter("fax_no", inputBean.getFax().trim())
                            .setParameter("email_ad", inputBean.getEmail().trim())
                            .setParameter("primary_url", inputBean.getPrimaryurl().trim())
                            .setParameter("secondary_url", inputBean.getSecondaryurl().trim())
                            .setParameter("date_of_registry", Common.formatStringtoDate(inputBean.getDateofregistry().trim()))
                            .setParameter("date_of_expiry", Common.formatStringtoDate(inputBean.getDateofexpiry().trim()))
                            .setParameter("status_code", inputBean.getStatus().trim())
                            .setParameter("contact_person", inputBean.getContactperson().trim())
                            .setParameter("remarks_dat", inputBean.getRemarks().trim())
                            .setParameter("last_updated_user", audit.getLastupdateduser().trim())
                            .setParameter("private_key", privateKey)
                            .setParameter("public_key", publicKey)
                            .setParameter("symmetric_key", symetricKey)
                            .setParameter("security_mech", inputBean.getSecuritymechanism().trim())
                            .setParameter("exec_stat", -1)
                            .setParameter("success_url", inputBean.getDynamicreturnsuccessurl().trim())
                            .setParameter("error_url", inputBean.getDynamicreturnerrorurl().trim())
                            .setParameter("auth_required", inputBean.getAuthreqstatus().trim())
                            .setParameter("risk_profile", inputBean.getRiskprofile().trim());
                    int result = query.executeUpdate();

                    /**
                     * for audit new value
                     */
                    Ipgcountry ipgcountry = (Ipgcountry) session.get(Ipgcountry.class, inputBean.getCountry().trim());
                    Ipgstatus ipgstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                    Ipgstatus ipgauthstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getAuthreqstatus().trim());
                    Ipgstatus ipgtranstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getTxninitstatus().trim());
                    Ipgriskprofile ipgriskprofile = (Ipgriskprofile) session.get(Ipgriskprofile.class, inputBean.getRiskprofile().trim());

                    StringBuilder newValue = new StringBuilder();
                    newValue.append(inputBean.getMerchantcustomerid())
                            .append("|").append(inputBean.getMerchantid())
                            .append("|").append(inputBean.getMerchantname())
                            .append("|").append(inputBean.getAddress1())
                            .append("|").append(inputBean.getAddress2())
                            .append("|").append(inputBean.getCity())
                            .append("|").append(ipgcountry.getDescription())
                            .append("|").append(inputBean.getStatecode())
                            .append("|").append(inputBean.getPostalcode())
                            .append("|").append(inputBean.getProvince())
                            .append("|").append(inputBean.getMobile())
                            .append("|").append(inputBean.getTel())
                            .append("|").append(inputBean.getFax())
                            .append("|").append(inputBean.getEmail())
                            .append("|").append(inputBean.getPrimaryurl())
                            .append("|").append(inputBean.getSecondaryurl())
                            .append("|").append(inputBean.getDynamicreturnsuccessurl())
                            .append("|").append(inputBean.getDynamicreturnerrorurl())
                            .append("|").append(inputBean.getDateofregistry())
                            .append("|").append(inputBean.getDateofexpiry())
                            .append("|").append(ipgstatus.getDescription())
                            .append("|").append(inputBean.getContactperson())
                            .append("|").append(inputBean.getRemarks())
                            .append("|").append(ipgriskprofile.getDescription())
                            .append("|").append(ipgauthstatus.getDescription())
                            .append("|").append(ipgtranstatus.getDescription())
                            .append("|").append(inputBean.getSecuritymechanism());
                    audit.setNewvalue(newValue.toString());
                    audit.setCreatedtime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);

                    txn.commit();

                } else {
                    message = MessageVarList.COMMON_ALREADY_EXISTS;
                }
            } else {
                message = MessageVarList.HM_MGT_EMPTY_MERCHANT_ID_EXISTS;
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

    public String updateHeadMerchant(HeadMerchantInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            String headmerchantcode = inputBean.getMerchantcustomerid().trim();
            Ipgheadmerchant headmerchant = (Ipgheadmerchant) session.get(Ipgheadmerchant.class, headmerchantcode);

            if (headmerchant != null) {
                /**
                 * for audit old value
                 */

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                StringBuilder oldValue = new StringBuilder();
                oldValue.append(headmerchant.getMerchantcustomerid())
                        .append("|").append(headmerchant.getTransactioninitaiatedmerchntid())
                        .append("|").append(headmerchant.getMerchantname())
                        .append("|").append(headmerchant.getIpgaddress().getAddress1())
                        .append("|").append(headmerchant.getIpgaddress().getAddress2())
                        .append("|").append(headmerchant.getIpgaddress().getCity())
                        .append("|").append(headmerchant.getIpgcountry().getDescription())
                        .append("|").append(headmerchant.getIpgaddress().getStatecode())
                        .append("|").append(headmerchant.getIpgaddress().getPostalcode())
                        .append("|").append(headmerchant.getIpgaddress().getProvince())
                        .append("|").append(headmerchant.getIpgaddress().getMobile())
                        .append("|").append(headmerchant.getIpgaddress().getTelno())
                        .append("|").append(headmerchant.getIpgaddress().getFax())
                        .append("|").append(headmerchant.getIpgaddress().getEmail())
                        .append("|").append(headmerchant.getPrimaryurl())
                        .append("|").append(headmerchant.getSecondaryurl())
                        .append("|").append(formatter.format(headmerchant.getDateofregistry()))
                        .append("|").append(formatter.format(headmerchant.getDateofexpiry()))
                        .append("|").append(headmerchant.getIpgstatusByStatuscode().getDescription())
                        .append("|").append(headmerchant.getContactperson())
                        .append("|").append(headmerchant.getRemarks())
                        .append("|").append(headmerchant.getIpgriskprofile().getDescription())
                        .append("|").append(headmerchant.getIpgstatusByAuthenticationrequied().getDescription())
                        .append("|").append(headmerchant.getIpgstatusByTransactioninitaiatedstatus().getDescription())
                        .append("|").append(inputBean.getSecuritymechanism());

                byte[] privateKey = new byte[8];
                byte[] publicKey = new byte[8];
                byte[] symetricKey = new byte[8];

                Query query = session.createSQLQuery(
                        "CALL UPDATEHEADMERCHANT(:merchantcus_id,:merchant_id,:merchant_name,:txninit_status,:address_1,:address_2,:city_name,:state_code,:postal_code,"
                        + ":province_name,:country_code,:mobile_no,:tel_no,:fax_no,:email_ad,:primary_url,:secondary_url,:date_of_registry,"
                        + ":date_of_expiry,:status_code,:contact_person,:remarks_dat,:last_updated_user,:last_updated_time,"
                        + ":security_mechanism,:symetric_key,:exec_stat,:risk_profile,:auth_required)")
                        .setParameter("merchantcus_id", inputBean.getMerchantcustomerid().trim())
                        .setParameter("merchant_id", inputBean.getMerchantid().trim())
                        .setParameter("txninit_status", inputBean.getTxninitstatus().trim())
                        .setParameter("merchant_name", inputBean.getMerchantname().trim())
                        .setParameter("address_1", inputBean.getAddress1().trim())
                        .setParameter("address_2", inputBean.getAddress2().trim())
                        .setParameter("city_name", inputBean.getCity().trim())
                        .setParameter("state_code", inputBean.getStatecode().trim())
                        .setParameter("postal_code", inputBean.getPostalcode().trim())
                        .setParameter("province_name", inputBean.getProvince().trim())
                        .setParameter("country_code", inputBean.getCountry().trim())
                        .setParameter("mobile_no", inputBean.getMobile().trim())
                        .setParameter("tel_no", inputBean.getTel().trim())
                        .setParameter("fax_no", inputBean.getFax().trim())
                        .setParameter("email_ad", inputBean.getEmail().trim())
                        .setParameter("primary_url", inputBean.getPrimaryurl().trim())
                        .setParameter("secondary_url", inputBean.getSecondaryurl().trim())
                        .setParameter("date_of_registry", new java.sql.Timestamp(Common.formatStringtoLongDate(inputBean.getDateofregistry())))
                        .setParameter("date_of_expiry", new java.sql.Timestamp(Common.formatStringtoLongDate(inputBean.getDateofexpiry())))
                        .setParameter("status_code", inputBean.getStatus().trim())
                        .setParameter("contact_person", inputBean.getContactperson().trim())
                        .setParameter("remarks_dat", inputBean.getRemarks().trim())
                        .setParameter("last_updated_user", audit.getLastupdateduser().trim())
                        .setParameter("last_updated_time", new java.sql.Timestamp(audit.getLastupdatedtime().getTime()))
                        .setParameter("security_mechanism", inputBean.getSecuritymechanism().trim())
                        .setParameter("symetric_key", symetricKey)
                        .setParameter("exec_stat", -1)
                        .setParameter("risk_profile", inputBean.getRiskprofile().trim())
                        .setParameter("auth_required", inputBean.getAuthreqstatus().trim());
                int result = query.executeUpdate();

                /**
                 * for audit new value
                 */
                Ipgcountry ipgcountry = (Ipgcountry) session.get(Ipgcountry.class, inputBean.getCountry().trim());
                Ipgstatus ipgstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                Ipgstatus ipgauthstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getAuthreqstatus().trim());
                Ipgstatus ipgtranstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getTxninitstatus().trim());
                Ipgriskprofile ipgriskprofile = (Ipgriskprofile) session.get(Ipgriskprofile.class, inputBean.getRiskprofile().trim());

                StringBuilder newValue = new StringBuilder();
                newValue.append(inputBean.getMerchantcustomerid())
                        .append("|").append(inputBean.getMerchantid())
                        .append("|").append(inputBean.getMerchantname())
                        .append("|").append(inputBean.getAddress1())
                        .append("|").append(inputBean.getAddress2())
                        .append("|").append(inputBean.getCity())
                        .append("|").append(ipgcountry.getDescription())
                        .append("|").append(inputBean.getStatecode())
                        .append("|").append(inputBean.getPostalcode())
                        .append("|").append(inputBean.getProvince())
                        .append("|").append(inputBean.getMobile())
                        .append("|").append(inputBean.getTel())
                        .append("|").append(inputBean.getFax())
                        .append("|").append(inputBean.getEmail())
                        .append("|").append(inputBean.getPrimaryurl())
                        .append("|").append(inputBean.getSecondaryurl())
                        .append("|").append(inputBean.getDateofregistry())
                        .append("|").append(inputBean.getDateofexpiry())
                        .append("|").append(ipgstatus.getDescription())
                        .append("|").append(inputBean.getContactperson())
                        .append("|").append(inputBean.getRemarks())
                        .append("|").append(ipgriskprofile.getDescription())
                        .append("|").append(ipgauthstatus.getDescription())
                        .append("|").append(ipgtranstatus.getDescription())
                        .append("|").append(inputBean.getSecuritymechanism());
                audit.setNewvalue(newValue.toString());
                audit.setOldvalue(oldValue.toString());
                audit.setCreatedtime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
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

    public Ipgheadmerchant findHeadMerchantById(String headmerchantcode) throws Exception {

        Ipgheadmerchant headmerchant = new Ipgheadmerchant();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql1 = "from Ipgheadmerchant as u join fetch u.ipgaddress where u.merchantcustomerid=:merchantcustomerid";
            Query query1 = session.createQuery(sql1).setString("merchantcustomerid", headmerchantcode);
            if (query1.list().size() > 0) {
                headmerchant = (Ipgheadmerchant) query1.list().get(0);
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
        return headmerchant;
    }

    public Ipgmerchant findMerchantById(String merchantcode) throws Exception {

        Ipgmerchant merchant = new Ipgmerchant();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql1 = "from Ipgmerchant as u where u.merchantid=:merchantid";
            Query query1 = session.createQuery(sql1).setString("merchantid", merchantcode);
            merchant = (Ipgmerchant) query1.list().get(0);

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
        return merchant;
    }

    public List<Ipgcountry> getDefultCountryList() throws Exception {
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

        return countrylist;
    }

    public String deleteHeadMerchant(HeadMerchantInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ipgheadmerchant u = (Ipgheadmerchant) session.get(Ipgheadmerchant.class, inputBean.getMerchantcustomerid().trim());
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

    public Ipgcommonfilepath getKeystoreFilePath(String ostype) throws Exception {

        Ipgcommonfilepath ipgcommonfilepath = new Ipgcommonfilepath();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql1 = "from Ipgcommonfilepath as u where u.ostype=:ostype";
            Query query1 = session.createQuery(sql1).setString("ostype", ostype);
            if (query1.list().size() > 0) {
                ipgcommonfilepath = (Ipgcommonfilepath) query1.list().get(0);
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
        return ipgcommonfilepath;

    }

    public Ipgcountry getCountryByCode(String code) throws Exception {

        Ipgcountry ipgcountry = new Ipgcountry();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql1 = "from Ipgcountry as u where u.countrycode=:code";
            Query query1 = session.createQuery(sql1).setString("code", code);
            if (query1.list().size() > 0) {
                ipgcountry = (Ipgcountry) query1.list().get(0);
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
        return ipgcountry;

    }  
    
}
