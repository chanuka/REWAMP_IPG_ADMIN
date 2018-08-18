/**
 *
 */
package com.epic.epay.ipg.dao.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.CommonFilePathBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantManagementDataBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.MerchantManagementInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgbatch;
import com.epic.epay.ipg.util.mapping.Ipgcommonconfig;
import com.epic.epay.ipg.util.mapping.Ipgcommonfilepath;
import com.epic.epay.ipg.util.mapping.Ipgcountry;
import com.epic.epay.ipg.util.mapping.Ipgcurrency;
import com.epic.epay.ipg.util.mapping.Ipgheadmerchant;
import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgmerchantcurrency;
import com.epic.epay.ipg.util.mapping.IpgmerchantcurrencyId;
import com.epic.epay.ipg.util.mapping.Ipgmerchantrule;
import com.epic.epay.ipg.util.mapping.IpgmerchantruleId;
import com.epic.epay.ipg.util.mapping.Ipgmerchantservicecharge;
import com.epic.epay.ipg.util.mapping.IpgmerchantservicechargeId;
import com.epic.epay.ipg.util.mapping.Ipgriskprofile;
import com.epic.epay.ipg.util.mapping.Ipgrule;
import com.epic.epay.ipg.util.mapping.Ipgservicecharge;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author Asitha Liyanawaduge
 *
 * 19/11/2013
 */
public class MerchantManagementDAO {

    /**
     * @param inputBean
     * @param rows
     * @param from
     * @param orderBy
     * @return
     * @throws Exception
     */
    public List<MerchantManagementDataBean> getSearchList(MerchantManagementInputBean inputBean, int rows, int from, String orderBy) throws Exception {
        List<MerchantManagementDataBean> dataBeanList = new ArrayList<MerchantManagementDataBean>();
        Session session = null;

        InputStream fileInputStream;
        String certPath = this.getCertificatePath(this.getOS_Type());

        try {
            long count = 0;
            String where = this.makeWhereClause(inputBean);
            session = HibernateInit.sessionFactory.openSession();

            Query queryCount = session.createQuery("select count(merchantid) from Ipgmerchant as u where " + where);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {
                if (orderBy.equals("")) {
                    orderBy = " order by u.createdtime desc ";
                }

                Query querySearch = session.createQuery("from Ipgmerchant u where " + where + orderBy);

                querySearch.setMaxResults(rows);
                querySearch.setFirstResult(from);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    Ipgmerchant merchant = (Ipgmerchant) it.next();
                    MerchantManagementDataBean bean = new MerchantManagementDataBean();

                    try {
                        bean.setMerchantid(merchant.getMerchantid());
                    } catch (NullPointerException npe) {
                        bean.setMerchantid("--");
                    }
                    try {
                        fileInputStream = new FileInputStream(new File(certPath + merchant.getMerchantid() + ".cer"));
                        bean.setFilepath("Y");
                    } catch (FileNotFoundException npe) {
                        bean.setFilepath("N");
                    }
                    try {
                        bean.setMerchantname(merchant.getMerchantname());
                    } catch (NullPointerException npe) {
                        bean.setMerchantname("--");
                    }
                    try {
                        bean.setAddress(merchant.getIpgaddress().getAddress1());
                    } catch (NullPointerException npe) {
                        bean.setAddress("--");
                    }
                    try {
                        bean.setCity(merchant.getIpgaddress().getCity());
                    } catch (NullPointerException npe) {
                        bean.setCity("--");
                    }
                    try {
                        bean.setEmail(merchant.getIpgaddress().getEmail());
                    } catch (NullPointerException npe) {
                        bean.setEmail("--");
                    }
                    try {
                        bean.setDigitalSign(merchant.getSecuritymechanism());
                    } catch (NullPointerException npe) {
                        bean.setDigitalSign("--");
                    }
                    try {
                        bean.setHeadmerchant(merchant.getIpgheadmerchant().getMerchantcustomername());
                    } catch (NullPointerException npe) {
                        bean.setHeadmerchant("--");
                    }
                    try {
                        Ipgstatus status = (Ipgstatus) session.get(Ipgstatus.class, merchant.getIsdefaultmerchany());
                        bean.setDefaultmerchant(status.getDescription());
                    } catch (Exception npe) {
                        bean.setDefaultmerchant("--");
                    }
                    try {
                        bean.setCreatedtime(merchant.getCreatedtime().toString().substring(0, merchant.getCreatedtime().toString().length() - 2));
                    } catch (NullPointerException npe) {
                        bean.setCreatedtime("--");
                    }

                    bean.setFullCount(count);

                    dataBeanList.add(bean);
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

    private String makeWhereClause(MerchantManagementInputBean inputBean) {
        String where = "1=1";
        if (inputBean.getMerchantId() != null && !inputBean.getMerchantId().trim().isEmpty()) {
            where += " and lower(u.merchantid) like lower('%" + inputBean.getMerchantId().trim() + "%')";
        }
        if (inputBean.getMerchantName() != null && !inputBean.getMerchantName().trim().isEmpty()) {
            where += " and lower(u.merchantname) like lower('%" + inputBean.getMerchantName().trim() + "%')";
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
        if (inputBean.getHeadmerchant() != null && !inputBean.getHeadmerchant().trim().isEmpty()) {
            where += " and u.ipgheadmerchant.merchantcustomerid = '" + inputBean.getHeadmerchant().trim() + "'";
        }
        if (inputBean.getDefaultmerchant()!= null && !inputBean.getDefaultmerchant().trim().isEmpty()) {
            where += " and u.isdefaultmerchany = '" + inputBean.getDefaultmerchant().trim() + "'";
        }
        return where;
    }

    /**
     * @param inputBean
     * @param audit
     * @return
     * @throws Exception
     */
    public String insertMerchant(MerchantManagementInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            byte[] privateKey = new byte[8];
            byte[] publicKey = new byte[8];
            byte[] symetricKey = new byte[8];
            System.out.println("checked : " + inputBean.getHeadmerchant());

            Query query = session.createSQLQuery(
                    "CALL INSERTMERCHANTNEW(:merchant_id,:merchant_name,:merchant_customer_id,:address_1,:address_2,:city_name,:state_code,:postal_code,"
                    + ":province_name,:country_code,:mobile_no,:tel_no,:fax_no,:email_ad,:primary_url,:secondary_url,:date_of_registry,"
                    + ":date_of_expiry,:status_code,:contact_person,:remarks_dat,:last_updated_user,:private_key,:public_key,"
                    + ":symmetric_key,:security_mech,:exec_stat,:success_url,:error_url,:auth_requied,:risk_profile,:is_Default_Merchany)")
                    .setParameter("merchant_id", inputBean.getMerchantId())
                    .setParameter("merchant_name", inputBean.getMerchantName())
                    .setParameter("merchant_customer_id", inputBean.getHeadmerchant())
                    .setParameter("address_1", inputBean.getAddress1())
                    .setParameter("address_2", inputBean.getAddress2())
                    .setParameter("city_name", inputBean.getCity())
                    .setParameter("state_code", inputBean.getStateCode())
                    .setParameter("postal_code", inputBean.getPostalCode())
                    .setParameter("province_name", inputBean.getProvince())
                    .setParameter("country_code", inputBean.getCountry())
                    .setParameter("mobile_no", inputBean.getMobile())
                    .setParameter("tel_no", inputBean.getTelNo())
                    .setParameter("fax_no", inputBean.getFax())
                    .setParameter("email_ad", inputBean.getEmail())
                    .setParameter("primary_url", inputBean.getPrimaryURL())
                    .setParameter("secondary_url", inputBean.getSecondaryURL())
                    .setParameter("date_of_registry", Common.formatStringtoDate(inputBean.getDateOfRegistry()))
                    .setParameter("date_of_expiry", Common.formatStringtoDate(inputBean.getDateOfExpire()))
                    .setParameter("status_code", inputBean.getStatus())
                    .setParameter("contact_person", inputBean.getContactPerson())
                    .setParameter("remarks_dat", inputBean.getRemarks())
                    .setParameter("last_updated_user", audit.getLastupdateduser())
                    .setParameter("private_key", privateKey)
                    .setParameter("public_key", publicKey)
                    .setParameter("symmetric_key", symetricKey)
                    .setParameter("security_mech", inputBean.getSecurityMechanism())
                    .setParameter("exec_stat", -1)
                    .setParameter("success_url", inputBean.getDrsURL())
                    .setParameter("error_url", inputBean.getDreURL())
                    .setParameter("auth_requied", inputBean.getAuthreqstatus())
                    .setParameter("risk_profile", inputBean.getRiskprofile().trim())
                    .setParameter("is_Default_Merchany", inputBean.getIsdefaultmerchany());

            int result = query.executeUpdate();

            /**
             * for audit new value
             */
            Ipgcountry ipgcountry = (Ipgcountry) session.get(Ipgcountry.class, inputBean.getCountry().trim());
            Ipgheadmerchant ipgheadmerchant = (Ipgheadmerchant) session.get(Ipgheadmerchant.class, inputBean.getHeadmerchant().trim());
            Ipgstatus ipgstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
            Ipgstatus ipgauthstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getAuthreqstatus().trim());
            Ipgriskprofile ipgriskprofile = (Ipgriskprofile) session.get(Ipgriskprofile.class, inputBean.getRiskprofile().trim());

            StringBuilder newValue = new StringBuilder();
            newValue.append(inputBean.getMerchantId())
                    .append("|").append(ipgheadmerchant.getMerchantname())
                    .append("|").append(inputBean.getMerchantName())
                    .append("|").append(inputBean.getEmail())
                    .append("|").append(inputBean.getAddress1())
                    .append("|").append(inputBean.getAddress2())
                    .append("|").append(inputBean.getPrimaryURL())
                    .append("|").append(inputBean.getSecondaryURL())
                    .append("|").append(inputBean.getCity())
                    .append("|").append(inputBean.getStateCode())
                    .append("|").append(inputBean.getDrsURL())
                    .append("|").append(inputBean.getDreURL())
                    .append("|").append(inputBean.getPostalCode())
                    .append("|").append(inputBean.getProvince())
                    .append("|").append(inputBean.getDateOfRegistry())
                    .append("|").append(inputBean.getDateOfExpire())
                    .append("|").append(ipgcountry.getDescription())
                    .append("|").append(inputBean.getMobile())
                    .append("|").append(ipgstatus.getDescription())
                    .append("|").append(inputBean.getContactPerson())
                    .append("|").append(inputBean.getTelNo())
                    .append("|").append(inputBean.getFax())
                    .append("|").append(inputBean.getRemarks())
                    .append("|").append(ipgauthstatus.getDescription())
                    .append("|").append(ipgriskprofile.getDescription())
                    .append("|").append(inputBean.getSecurityMechanism());
            audit.setNewvalue(newValue.toString());
            audit.setCreatedtime(sysDate);
            audit.setLastupdatedtime(sysDate);
            session.save(audit);
            txn.commit();

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

    public String updateMerchant(MerchantManagementInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            String merchantcode = inputBean.getMerchantId().trim();
            Ipgmerchant merchant = (Ipgmerchant) session.get(Ipgmerchant.class, merchantcode);

            if (merchant != null) {

                /**
                 * for audit old value
                 */
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                StringBuilder oldValue = new StringBuilder();
                oldValue.append(merchant.getMerchantid())
                        .append("|").append(merchant.getIpgheadmerchant().getMerchantname())
                        .append("|").append(merchant.getMerchantname())
                        .append("|").append(merchant.getIpgaddress().getEmail())
                        .append("|").append(merchant.getIpgaddress().getAddress1())
                        .append("|").append(merchant.getIpgaddress().getAddress2())
                        .append("|").append(merchant.getPrimaryurl())
                        .append("|").append(merchant.getSecondaryurl())
                        .append("|").append(merchant.getIpgaddress().getCity())
                        .append("|").append(merchant.getIpgaddress().getStatecode())
                        .append("|").append(merchant.getDinamicreturnsuccessurl())
                        .append("|").append(merchant.getDinamicreturnerrorurl())
                        .append("|").append(merchant.getIpgaddress().getPostalcode())
                        .append("|").append(merchant.getIpgaddress().getProvince())
                        .append("|").append(formatter.format(merchant.getDateofregistry()))
                        .append("|").append(formatter.format(merchant.getDateofexpiry()))
                        .append("|").append(merchant.getIpgcountry().getDescription())
                        .append("|").append(merchant.getIpgaddress().getMobile())
                        .append("|").append(merchant.getIpgstatus().getDescription())
                        .append("|").append(merchant.getContactperson())
                        .append("|").append(merchant.getIpgaddress().getTelno())
                        .append("|").append(merchant.getIpgaddress().getFax())
                        .append("|").append(merchant.getRemarks())
                        .append("|").append(merchant.getIpgstatusByAuthenticationrequied().getDescription())
                        .append("|").append(merchant.getIpgriskprofile().getDescription())
                        .append("|").append(merchant.getSecuritymechanism());

                byte[] privateKey = new byte[8];
                byte[] publicKey = new byte[8];
                byte[] symetricKey = new byte[8];

                Query query = session.createSQLQuery(
                        "CALL UPDATEMERCHANTNEW(:merchant_id,:merchant_name,:merchant_customer_id,:address_1,:address_2,:city_name,:state_code,:postal_code,"
                        + ":province_name,:country_code,:mobile_no,:tel_no,:fax_no,:email_ad,:primary_url,:secondary_url,:date_of_registry,"
                        + ":date_of_expiry,:status_code,:contact_person,:remarks_dat,:last_updated_user,:last_updated_time,"
                        + ":security_mechanism,:symetric_key,:exec_stat,:success_url,:error_url,:auth_requied,:risk_profile,:is_Default_Merchany)")
                        .setParameter("merchant_id", inputBean.getMerchantId().trim())
                        //                        .setParameter("txninit_status", inputBean.getTxninitstatus().trim())
                        .setParameter("merchant_name", inputBean.getMerchantName().trim())
                        .setParameter("merchant_customer_id", inputBean.getHeadmerchant())
                        .setParameter("address_1", inputBean.getAddress1().trim())
                        .setParameter("address_2", inputBean.getAddress2().trim())
                        .setParameter("city_name", inputBean.getCity().trim())
                        .setParameter("state_code", inputBean.getStateCode().trim())
                        .setParameter("postal_code", inputBean.getPostalCode().trim())
                        .setParameter("province_name", inputBean.getProvince().trim())
                        .setParameter("country_code", inputBean.getCountry().trim())
                        .setParameter("mobile_no", inputBean.getMobile().trim())
                        .setParameter("tel_no", inputBean.getTelNo().trim())
                        .setParameter("fax_no", inputBean.getFax().trim())
                        .setParameter("email_ad", inputBean.getEmail().trim())
                        .setParameter("primary_url", inputBean.getPrimaryURL().trim())
                        .setParameter("secondary_url", inputBean.getSecondaryURL().trim())
                        .setParameter("date_of_registry", new java.sql.Timestamp(Common.formatStringtoLongDate(inputBean.getDateOfRegistry())))
                        .setParameter("date_of_expiry", new java.sql.Timestamp(Common.formatStringtoLongDate(inputBean.getDateOfExpire())))
                        .setParameter("status_code", inputBean.getStatus().trim())
                        .setParameter("contact_person", inputBean.getContactPerson().trim())
                        .setParameter("remarks_dat", inputBean.getRemarks().trim())
                        .setParameter("last_updated_user", audit.getLastupdateduser().trim())
                        .setParameter("last_updated_time", new java.sql.Timestamp(audit.getLastupdatedtime().getTime()))
                        .setParameter("symetric_key", symetricKey)
                        .setParameter("security_mechanism", inputBean.getSecurityMechanism().trim())
                        .setParameter("exec_stat", -1)
                        .setParameter("success_url", inputBean.getDrsURL().trim())//
                        .setParameter("error_url", inputBean.getDreURL().trim())
                        .setParameter("auth_requied", inputBean.getAuthreqstatus().trim())
                        //                        .setParameter("auth_required", inputBean.get.trim())
                        .setParameter("risk_profile", inputBean.getRiskprofile().trim())
                        .setParameter("is_Default_Merchany", inputBean.getIsdefaultmerchany());
                int result = query.executeUpdate();
                
                /**
                 * for audit new value
                 */
                Ipgcountry ipgcountry = (Ipgcountry) session.get(Ipgcountry.class, inputBean.getCountry().trim());
                Ipgheadmerchant ipgheadmerchant = (Ipgheadmerchant) session.get(Ipgheadmerchant.class, inputBean.getHeadmerchant().trim());
                Ipgstatus ipgstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getStatus().trim());
                Ipgstatus ipgauthstatus = (Ipgstatus) session.get(Ipgstatus.class, inputBean.getAuthreqstatus().trim());
                Ipgriskprofile ipgriskprofile = (Ipgriskprofile) session.get(Ipgriskprofile.class, inputBean.getRiskprofile().trim());

                StringBuilder newValue = new StringBuilder();
                newValue.append(inputBean.getMerchantId())
                        .append("|").append(ipgheadmerchant.getMerchantname())
                        .append("|").append(inputBean.getMerchantName())
                        .append("|").append(inputBean.getEmail())
                        .append("|").append(inputBean.getAddress1())
                        .append("|").append(inputBean.getAddress2())
                        .append("|").append(inputBean.getPrimaryURL())
                        .append("|").append(inputBean.getSecondaryURL())
                        .append("|").append(inputBean.getCity())
                        .append("|").append(inputBean.getStateCode())
                        .append("|").append(inputBean.getDrsURL())
                        .append("|").append(inputBean.getDreURL())
                        .append("|").append(inputBean.getPostalCode())
                        .append("|").append(inputBean.getProvince())
                        .append("|").append(inputBean.getDateOfRegistry())
                        .append("|").append(inputBean.getDateOfExpire())
                        .append("|").append(ipgcountry.getDescription())
                        .append("|").append(inputBean.getMobile())
                        .append("|").append(ipgstatus.getDescription())
                        .append("|").append(inputBean.getContactPerson())
                        .append("|").append(inputBean.getTelNo())
                        .append("|").append(inputBean.getFax())
                        .append("|").append(inputBean.getRemarks())
                        .append("|").append(ipgauthstatus.getDescription())
                        .append("|").append(ipgriskprofile.getDescription())
                        .append("|").append(inputBean.getSecurityMechanism());
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

    public String deleteMerchant(MerchantManagementInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            String sql = "select count(*) from Ipgmerchantcredential as u where u.id.merchantid =:merid";
            Query query = session.createQuery(sql).setString("merid",
                    inputBean.getMerchantId().trim());

            Long count = (Long) query.iterate().next();

            if (count != 0) {
                message = MessageVarList.MERCH_INVALID_DEL + ". Merchant Credentials are already assigned to this record.";
                return message;
            } else {
                String sql2 = "select count(*) from Ipgmerchantaddon as u where u.merchantid =:merid";
                Query query2 = session.createQuery(sql2).setString("merid",
                        inputBean.getMerchantId().trim());
                Long count2 = (Long) query2.iterate().next();
                if (count2 != 0) {
                    message = MessageVarList.MERCH_INVALID_DEL + ". Merchant-Add-On are already assigned to this record.";
                    return message;
                } else {
                    String sql3 = "select count(*) from Ipgheadmerchant as u where u.transactioninitaiatedmerchntid =:merid";
                    Query query3 = session.createQuery(sql3).setString("merid",
                            inputBean.getMerchantId().trim());
                    Long count3 = (Long) query3.iterate().next();
                    if (count3 != 0) {
                        message = MessageVarList.MERCH_INVALID_DEL + ". This record assigned to head merchant.";
                        return message;
                    } else {
                        String sql4 = "select count(*) from Ipgmerchnatterminal as u where u.ipgmerchant.merchantid =:merid";
                        Query query4 = session.createQuery(sql4).setString("merid",
                                inputBean.getMerchantId().trim());
                        Long count4 = (Long) query4.iterate().next();
                        if (count4 != 0) {
                            message = MessageVarList.MERCH_INVALID_DEL + ". Terminals are already assigned to this record.";
                            return message;
                        } else {
                            String sql5 = "select count(*) from Ipgtransaction as u where u.id.merchantid =:merid";
                            Query query5 = session.createQuery(sql5).setString("merid",
                                    inputBean.getMerchantId().trim());
                            Long count5 = (Long) query5.iterate().next();
                            if (count5 != 0) {
                                message = MessageVarList.MERCH_INVALID_DEL + ". Transactions are already happened to this record.";
                                return message;
                            } else {
                                Ipgmerchant u = (Ipgmerchant) session.get(Ipgmerchant.class, inputBean.getMerchantId().trim());
                                if (u != null) {

                                    String sql6 = "from Ipgbatch as u where u.id.merchantid =:merid";
                                    Query query6 = session.createQuery(sql6).setString("merid",
                                            inputBean.getMerchantId().trim());
                                    List<Ipgbatch> batchList = query6.list();
                                    for (Ipgbatch batch : batchList) {
                                        session.delete(batch);
                                    }
                                    audit.setCreatedtime(sysDate);
                                    audit.setLastupdatedtime(sysDate);

                                    session.save(audit);
                                    session.delete(u);

                                    txn.commit();
                                } else {
                                    message = MessageVarList.COMMON_NOT_EXISTS;
                                }
                            }
                        }
                    }
                }
            }
//            List count =session.createCriteria("Ipgsystemuser").setProjection(Projections.rowCount()).list();

//            ( (Integer) session.createQuery("select count(*) from Ipgsystemuser ").iterate().next() ).intValue();
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

    public Ipgmerchant findMerchantById(String merchantcode) throws Exception {

        Ipgmerchant merchant = new Ipgmerchant();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql1 = "from Ipgmerchant as u join fetch u.ipgaddress where u.merchantid=:merchantid";
            Query query1 = session.createQuery(sql1).setString("merchantid", merchantcode);
            if (query1.list().size() > 0) {
                merchant = (Ipgmerchant) query1.list().get(0);
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
        return merchant;

    }

    public List<Ipgheadmerchant> getHeadMerchantList()
            throws Exception {

        List<Ipgheadmerchant> list = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgheadmerchant as u order by u.merchantname";
            Query query = session.createQuery(sql);
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
        return list;
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

    public String getCertificatePath(String osType) throws Exception {

        String certPath = "";
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgcommonfilepath as u where u.ostype =:os";
            Query query = session.createQuery(sql).setString("os", osType);

            Iterator it = query.iterate();
            if (it.hasNext()) {
                Ipgcommonfilepath ipgcomfilepath = (Ipgcommonfilepath) it.next();

                certPath = ipgcomfilepath.getCertificates();

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
        return certPath;

    }

    public CommonFilePathBean getCertificatePathBean(String osType) throws Exception {

        CommonFilePathBean bean = null;
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ipgcommonfilepath as u where u.ostype =:os";
            Query query = session.createQuery(sql).setString("os", osType);

            Iterator it = query.iterate();
            if (it.hasNext()) {
                Ipgcommonfilepath path = (Ipgcommonfilepath) it.next();
                bean = new CommonFilePathBean();

                bean.setTxnLogPath(path.getTransactionlog());
                bean.setCcbtxnFiles(path.getCcbtxnfiles());
                bean.setCertificatePath(path.getCertificates());
                bean.setErrorLogPath(path.getErrorlog());
                bean.setInforLogPath(path.getInfolog());
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
        return bean;

    }
    //to get os name (windows or linux)

    public static String getOS_Type() {

        String osType = "";
        String osName = "";
        osName = System.getProperty("os.name", "").toLowerCase();

        // For WINDOWS
        if (osName.contains("windows")) {
            osType = "WINDOWS";
        } else if (osName.contains("linux")) {
            osType = "LINUX";
        } else if (osName.contains("sunos")) {
            osType = "SUNOS";
        }

        return osType;
    }

    public void insertMerchantCertificateMgt(Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;

        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);
            txn = session.beginTransaction();

            audit.setCreatedtime(sysDate);
            audit.setLastupdatedtime(sysDate);

            session.save(audit);

            txn.commit();

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

    }

    public void findRuleBySecLevel(MerchantManagementInputBean bean) throws Exception {

        String merchantId = bean.getMerchantId_rs();
        List<Ipgrule> newList = new ArrayList<Ipgrule>();
        List<Ipgrule> currentList = new ArrayList<Ipgrule>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql1 = "from Ipgrule as t where t.ipgsecuritylevel.securitylevel=:securitylevel and ruleid not in (select mp.id.ruleid from Ipgmerchantrule mp where mp.id.merchantid=:merchantid)";
            String sql2 = "from Ipgrule as t where t.ipgsecuritylevel.securitylevel=:securitylevel and ruleid in (select mp.id.ruleid from Ipgmerchantrule mp where mp.id.merchantid=:merchantid)";

            Query query1 = session.createQuery(sql1)
                    .setString("securitylevel", CommonVarList.SECURITY_LEVEL_MER)
                    .setString("merchantid", merchantId);
            Query query2 = session.createQuery(sql2)
                    .setString("securitylevel", CommonVarList.SECURITY_LEVEL_MER)
                    .setString("merchantid", merchantId);

            newList = (List<Ipgrule>) query1.list();
            currentList = (List<Ipgrule>) query2.list();

            for (Iterator<Ipgrule> it = newList.iterator(); it.hasNext();) {

                Ipgrule ipgrule = it.next();
                bean.getCurrentList().put(new Long(ipgrule.getRuleid()).toString(),
                        ipgrule.getDescription());
            }

            for (Iterator<Ipgrule> it = currentList.iterator(); it.hasNext();) {

                Ipgrule ipgrule = it.next();
                bean.getNewList().put(new Long(ipgrule.getRuleid()).toString(),
                        ipgrule.getDescription());
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
    }

    public String assignMerchantRule(MerchantManagementInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        IpgmerchantruleId ipgmerchantruleid = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            Ipgmerchantrule ipgmerchant = null;
            Ipgmerchantrule u = (Ipgmerchantrule) session.get(Ipgmerchantrule.class, new IpgmerchantruleId(inputBean.getMerchantId_rs().trim(), new BigDecimal(0)));

            String sql = "from Ipgmerchantrule as u where u.id.merchantid=:merchantid";
            Query query = session.createQuery(sql).setString("merchantid", inputBean.getMerchantId_rs());
            List<Ipgmerchantrule> merchantRuleList = query.list();
            List<String> newBoxHas = inputBean.getNewBox();
            int count = merchantRuleList.size();

            /**
             * for audit new/old values
             */
            StringBuilder newValue = new StringBuilder();
            StringBuilder oldValue = new StringBuilder();
            newValue.append(inputBean.getMerchantId_rs()).append("|");
            oldValue.append(inputBean.getMerchantId_rs()).append("|");

            Ipgmerchantservicecharge merchantservicecharge = (Ipgmerchantservicecharge) session.get(Ipgmerchantservicecharge.class, new IpgmerchantservicechargeId(inputBean.getMerchantId_rs().trim(), new BigDecimal(inputBean.getServicechargeID().trim())));

            if (merchantservicecharge == null) {

                Ipgmerchantservicecharge ipgmerchantservicecharge = new Ipgmerchantservicecharge();

                ipgmerchantservicecharge.setId(new IpgmerchantservicechargeId(inputBean.getMerchantId_rs().trim(), new BigDecimal(inputBean.getServicechargeID().trim())));
//                ipgmerchantservicecharge.setRemarks(inputBean.getReMarks().trim());

                ipgmerchantservicecharge.setCreatedtime(sysDate);
                ipgmerchantservicecharge.setLastupdatedtime(sysDate);
                ipgmerchantservicecharge.setLastupdateduser(audit.getLastupdateduser());

                session.saveOrUpdate(ipgmerchantservicecharge);

                //for audit newvalue
                Ipgservicecharge newservicecharge = (Ipgservicecharge) session.get(Ipgservicecharge.class, (new BigDecimal(inputBean.getServicechargeID().trim())));                
                newValue.append(newservicecharge.getDescription());
            } else {
                //for audit old value
                oldValue.append(merchantservicecharge.getIpgservicecharge().getDescription());
                //for audit new value
                newValue.append(merchantservicecharge.getIpgservicecharge().getDescription());
                
            }

            // for audit new/old values
            newValue.append("|[");
            oldValue.append("|[");

            for (Ipgmerchantrule mr : merchantRuleList) {

                if (newBoxHas.contains(mr.getId().getRuleid().toString())) {

                    mr.setLastupdatedtime(sysDate);
                    mr.setLastupdateduser(audit.getLastupdateduser());
                    session.update(mr);

                    //for audit new/old values
                    newValue.append(mr.getId().getRuleid()).append(",");
                    oldValue.append(mr.getId().getRuleid()).append(",");

                    newBoxHas.remove(mr.getId().getRuleid().toString());
                } else {

                    //for audit old value
                    oldValue.append(mr.getId().getRuleid()).append(",");

                    session.delete(mr);
                    session.flush();
                }
            }

            for (String ruleid : newBoxHas) {

                ipgmerchant = new Ipgmerchantrule();
                ipgmerchantruleid = new IpgmerchantruleId();
                ipgmerchantruleid.setMerchantid(inputBean.getMerchantId_rs());
                ipgmerchantruleid.setRuleid(new BigDecimal(ruleid));
                ipgmerchant.setId(ipgmerchantruleid);
                ipgmerchant.setCreatedtime(sysDate);
                ipgmerchant.setLastupdatedtime(sysDate);
                ipgmerchant.setLastupdateduser(audit.getLastupdateduser());

                //for new audit value
                newValue.append(ruleid).append(",");

                session.save(ipgmerchant);
            }
            /**
             * for audit new value
             */
            if (newValue.charAt(newValue.length() - 1) != '[') {
                newValue.deleteCharAt(newValue.length() - 1).append("]");
            } else {
                newValue.append("]");
            }
            audit.setNewvalue(newValue.toString());

            /**
             * for audit old value
             */
            if (oldValue.charAt(oldValue.length() - 1) != '[') {
                oldValue.deleteCharAt(oldValue.length() - 1).append("]");
            } else {
                oldValue.append("]");
            }
            audit.setOldvalue(oldValue.toString());
            audit.setCreatedtime(sysDate);
            audit.setLastupdatedtime(sysDate);
            session.save(audit);
            txn.commit();

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

    public Ipgmerchantservicecharge findMerchantChargeByID(String merchantID) throws Exception {
        Ipgmerchantservicecharge ipgmerchantservicecharge = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgmerchantservicecharge as u where u.id.merchantid=:merchantid ";
            Query query = session.createQuery(sql).setString("merchantid", merchantID);
//            ipgmerchantservicecharge = (Ipgmerchantservicecharge) query.list();

            if (query.list().size() > 0) {
                ipgmerchantservicecharge = (Ipgmerchantservicecharge) query.list().get(0);
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
        return ipgmerchantservicecharge;

    }

    //currency
    public void findCurrency(MerchantManagementInputBean bean) throws Exception {

        String merchantId = bean.getMerchantId_ac();
        List<Ipgcurrency> newList = new ArrayList<Ipgcurrency>();
        List<Ipgcurrency> currentList = new ArrayList<Ipgcurrency>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql1 = "from Ipgcurrency as t where t.currencyisocode not in (select mp.id.currencycode from Ipgmerchantcurrency mp where mp.id.merchantid=:merchantid)";
            String sql2 = "from Ipgcurrency as t where t.currencyisocode in (select mp.id.currencycode from Ipgmerchantcurrency mp where mp.id.merchantid=:merchantid)";

            Query query1 = session.createQuery(sql1).setString("merchantid", merchantId);
            Query query2 = session.createQuery(sql2).setString("merchantid", merchantId);

            newList = (List<Ipgcurrency>) query1.list();
            currentList = (List<Ipgcurrency>) query2.list();

            for (Iterator<Ipgcurrency> it = newList.iterator(); it.hasNext();) {

                Ipgcurrency ipgcuncy = it.next();
                bean.getCurrentList().put(new Long(ipgcuncy.getCurrencyisocode()).toString(), ipgcuncy.getDescription());
            }

            for (Iterator<Ipgcurrency> it = currentList.iterator(); it.hasNext();) {

                Ipgcurrency ipgcuncy = it.next();
                bean.getNewList().put(new Long(ipgcuncy.getCurrencyisocode()).toString(), ipgcuncy.getDescription());
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
    }

    public String assignMerchantCurrency(MerchantManagementInputBean inputBean, Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        IpgmerchantcurrencyId ipgmerchantcurrencyId = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            Ipgmerchantcurrency ipgmerchant = null;
//            Ipgmerchantcurrency u = (Ipgmerchantcurrency) session.get(Ipgmerchantcurrency.class, new IpgmerchantcurrencyId(inputBean.getMerchantID().trim(), inputBean.getMerchantID()));

            String sql = "from Ipgmerchantcurrency as u where u.id.merchantid=:merchantid";
            Query query = session.createQuery(sql).setString("merchantid", inputBean.getMerchantId_ac());
            List<Ipgmerchantcurrency> merchantCurList = query.list();
            List<String> newBoxHas = inputBean.getNewBox();
            int count = merchantCurList.size();

            //for audit new/old values
            StringBuilder newValue = new StringBuilder();
            StringBuilder oldValue = new StringBuilder();
            newValue.append(inputBean.getMerchantId_ac()).append("|[");
            oldValue.append(inputBean.getMerchantId_ac()).append("|[");

            for (Ipgmerchantcurrency mr : merchantCurList) {

                if (newBoxHas.contains(mr.getId().getCurrencycode())) {

                    mr.setLastupdatedtime(sysDate);
                    mr.setLastupdateduser(audit.getLastupdateduser());
                    session.update(mr);

                    //for audit new/old values
                    newValue.append(mr.getId().getCurrencycode()).append(",");
                    oldValue.append(mr.getId().getCurrencycode()).append(",");

                    newBoxHas.remove(mr.getId().getCurrencycode());
                } else {
                    //for audit old value
                    oldValue.append(mr.getId().getCurrencycode()).append(",");

                    session.delete(mr);
                    session.flush();
                }
            }

            for (String curcode : newBoxHas) {

                ipgmerchant = new Ipgmerchantcurrency();
                ipgmerchantcurrencyId = new IpgmerchantcurrencyId();
                ipgmerchantcurrencyId.setMerchantid(inputBean.getMerchantId_ac());
                ipgmerchantcurrencyId.setCurrencycode(curcode);
                ipgmerchant.setId(ipgmerchantcurrencyId);
                ipgmerchant.setCreatedtime(sysDate);
                ipgmerchant.setLastupdatedtime(sysDate);
                ipgmerchant.setLastupdateduser(audit.getLastupdateduser());

                //for audit new/old values
                newValue.append(curcode).append(",");

                session.save(ipgmerchant);
            }
            /**
             * for audit new value
             */
            if (newValue.charAt(newValue.length() - 1) != '[') {
                newValue.deleteCharAt(newValue.length() - 1).append("]");
            } else {
                newValue.append("]");
            }
            audit.setNewvalue(newValue.toString());

            /**
             * for audit old value
             */
            if (oldValue.charAt(oldValue.length() - 1) != '[') {
                oldValue.deleteCharAt(oldValue.length() - 1).append("]");
            } else {
                oldValue.append("]");
            }
            audit.setOldvalue(oldValue.toString());
            audit.setCreatedtime(sysDate);
            audit.setLastupdatedtime(sysDate);
            session.save(audit);
            txn.commit();

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

    public String insertAudit(Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            audit.setCreatedtime(sysDate);
            audit.setLastupdatedtime(sysDate);

            session.save(audit);

            txn.commit();

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
    
    public String isDefaultMerchant(String headmerchantcode) throws Exception {
        Session session = null;
        String message = CommonVarList.STATUS_AUTH_NO;
        Ipgheadmerchant headmerchant = new Ipgheadmerchant();

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql ="from Ipgheadmerchant as u where u.merchantcustomerid=:merchantcustomerid";
            Query query = session.createQuery(sql).setString("merchantcustomerid", headmerchantcode);
            if (query.list().size() > 0) {
                headmerchant = (Ipgheadmerchant) query.list().get(0);
                if(headmerchant.getTransactioninitaiatedmerchntid()==null || headmerchant.getTransactioninitaiatedmerchntid().isEmpty()){
                    message=CommonVarList.STATUS_AUTH_YES;
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
        return message;
    }

}
