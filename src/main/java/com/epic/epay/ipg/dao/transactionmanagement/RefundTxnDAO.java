/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.transactionmanagement;

import com.epic.epay.ipg.bean.transactionmanagement.RefundTxnDataBean;
import com.epic.epay.ipg.bean.transactionmanagement.RefundTxnInputBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
import com.epic.epay.ipg.util.mapping.Ipgcurrency;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgtransaction;
import com.epic.epay.ipg.util.mapping.IpgtransactionId;
import com.epic.epay.ipg.util.mapping.Ipgtransactionhistory;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.StatusVarList;
import com.epic.epay.ipg.util.varlist.UserRoleTypeVarList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author asela
 */
public class RefundTxnDAO {

    //get card Type List list
    public boolean isMerchantUser(String userName) throws Exception {
        boolean isMerchant = false;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT SU.USERNAME FROM IPGSYSTEMUSER SU WHERE SU.USERNAME =:username AND SU.USERROLECODE in (SELECT USERROLECODE FROM IPGUSERROLE WHERE USERROLETYPECODE =:userrolecode)";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("username", userName);
            query.setParameter("userrolecode", UserRoleTypeVarList.MERCHANT);

            if (query.list().size() > 0) {
                isMerchant = true;
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
        return isMerchant;
    }

    public boolean isHeadMerchant(String userName) throws Exception {
        boolean isHeadMerchant = false;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT MERCHANTCUSTOMERID FROM IPGHEADMERCHANT WHERE MERCHANTCUSTOMERID in (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME =:username)";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("username", userName);

            if (query.list().size() > 0) {
                isHeadMerchant = true;
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
        return isHeadMerchant;
    }

    public boolean txnInitiatedStatus(String userName) throws Exception {
        boolean yes = false;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT MERCHANTCUSTOMERID FROM IPGHEADMERCHANT WHERE MERCHANTCUSTOMERID in (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME =:username) AND TRANSACTIONINITAIATEDSTATUS =:status";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("username", userName);
            query.setParameter("status", StatusVarList.YES_STATUS);

            if (query.list().size() > 0) {
                yes = true;
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
        return yes;
    }

    //-------------------------------Refund Transaction-------------------------------------------------------------------
    public String refundTransactionInitiated(RefundTxnInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Ipgtransaction) session.get(Ipgtransaction.class, new IpgtransactionId(inputBean.getTxnid().trim(), inputBean.getMerchantid().trim())) == null) {
                txn = session.beginTransaction();

                Ipgtransaction tran = new Ipgtransaction();

                IpgtransactionId id = new IpgtransactionId();
                id.setIpgtransactionid(inputBean.getTxnid().trim());
                id.setMerchantid(inputBean.getMerchantid().trim());
                tran.setId(id);

                Ipgstatus st = new Ipgstatus();
                st.setStatuscode(inputBean.getStatus().trim());
                tran.setIpgstatus(st);

                Ipgcardassociation cd = new Ipgcardassociation();
                cd.setCardassociationcode(inputBean.getCardType().trim());
                tran.setIpgcardassociation(cd);

                Ipgcurrency cr = new Ipgcurrency();
                cr.setCurrencyisocode(inputBean.getCurrency().trim());
                tran.setIpgcurrency(cr);

                tran.setCardholderpan(inputBean.getCardno().trim());
                tran.setAmount(Integer.parseInt(inputBean.getRefundAmount().trim()));
                tran.setExpirydate(inputBean.getExpiryDate().trim());
                tran.setTxncategory(StatusVarList.REFUND_TXN_CATEGORY);
                tran.setOrderid(inputBean.getOrderid().trim());
                tran.setMerchantremarks("Refund");
                tran.setCardname(inputBean.getCardHolderName().trim());

                tran.setTransactioncreateddatetime(sysDate);
                tran.setLastupdatedtime(sysDate);
                tran.setCreatedtime(sysDate);
                tran.setPurchaseddatetime(sysDate);
                tran.setLastupdateduser(inputBean.getLoginUser());

                session.save(tran);

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

    /// ------------------------ Transaction Void Serch --------------------- /////////////
    public List getTxnList(RefundTxnInputBean searchBean, int max, int first, String orderBy) throws Exception {

        Session session = null;

        //initialize, declare variables
        String sql = null; //to store entire sql statement
        List<RefundTxnDataBean> dataList = new ArrayList<RefundTxnDataBean>();
        String whereString = "";

        whereString = this.makeWhereStatement(searchBean);

        try {
            session = HibernateInit.sessionFactory.openSession();

            sql = "SELECT T.IPGTRANSACTIONID as tid ,M.MERCHANTNAME as mname ,C.DESCRIPTION AS CARDASSOCIATION,"
                    + " T.AMOUNT as amount,S.DESCRIPTION AS STATUS,T.CREATEDTIME as ctime ,T.ORDERID as torderid,T.CARDHOLDERPAN as tcardholderpan ,T.ORDERID as ordid "
                    + " FROM IPGTRANSACTION T, IPGMERCHANT M, IPGCARDASSOCIATION C,IPGSTATUS S  "
                    + whereString + orderBy;
//                    + whereString + " ORDER BY t.IPGTRANSACTIONID DESC ";

            SQLQuery query = session.createSQLQuery(sql);

            query.setMaxResults(max);
            query.setFirstResult(first);
            System.out.println("sql : " + query.getQueryString() );
            List list = query.list();

            for (Iterator it = list.iterator(); it.hasNext();) {
                Object[] obj = (Object[]) it.next();
                RefundTxnDataBean bean = new RefundTxnDataBean();

                bean.setTxnid(obj[0].toString());
                bean.setMerchantname(obj[1].toString());
                bean.setCardassociationcode(obj[2].toString());
                bean.setAmount(obj[3].toString());
                bean.setStatus(obj[4].toString());
                bean.setOrderid(obj[6].toString());
                bean.setCardNumber(obj[7].toString());

                dataList.add(bean);
//                inputBean.getCurrencyMap().put(obj[0].toString(), obj[1].toString());
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

    private String makeWhereStatement(RefundTxnInputBean searchBean) throws Exception {
        //initialize, declare variables
        String whereString = null;
        int andString = 0;

        try {

            if ((searchBean.getFromDate() == null || searchBean.getFromDate().toString().isEmpty())
                    && (searchBean.getToDate() == null || searchBean.getToDate().toString().isEmpty())
                    && (searchBean.getCardno() == null || searchBean.getCardno().isEmpty())
                    && (searchBean.getOrderid() == null || searchBean.getOrderid().isEmpty())
                    && (searchBean.getMerchantName() == null || searchBean.getMerchantName().isEmpty())
                    && (searchBean.getLoginUser() == null || searchBean.getLoginUser().isEmpty())) {

                //user doesnt have search parameters
                //whereString = "    ";
                if (searchBean.getTag() == 1) {
                    // Serch Not Merchant user

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.STATUSCODE ='" + StatusVarList.TXN_COMPLETE_CONFIRMED + "'";
                } else if (searchBean.getTag() == 2) {
                    // Serch Merchant user

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE "
                            + " AND t.MERCHANTID = (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' )"
                            + " AND t.STATUSCODE ='" + StatusVarList.TXN_COMPLETE_CONFIRMED + "'";

                } else if (searchBean.getTag() == 3) {
                    // Serch Head Merchant user No status

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.MERCHANTID IN "
                            + " ( SELECT MERCHANTID FROM IPGMERCHANT WHERE MERCHANTCUSTOMERID = "
                            + " (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' ) )"
                            + " AND t.STATUSCODE ='" + StatusVarList.TXN_COMPLETE_CONFIRMED + "'";

                }
            } else {
                //user having some search parameters
                //whereString = "  WHERE  ";
                if (searchBean.getTag() == 1) {
                    // Serch Not Merchant user

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.STATUSCODE ='" + StatusVarList.TXN_COMPLETE_CONFIRMED + "'";
                } else if (searchBean.getTag() == 2) {
                    // Serch Merchant user

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE "
                            + " AND t.MERCHANTID = (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' )"
                            + " AND t.STATUSCODE ='" + StatusVarList.TXN_COMPLETE_CONFIRMED + "'";

                } else if (searchBean.getTag() == 3) {
                    // Serch Head Merchant user No status

                    whereString = "  WHERE  t.MERCHANTID=m.MERCHANTID AND t.CARDASSOCIATIONCODE=c.CARDASSOCIATIONCODE "
                            + " AND t.STATUSCODE=s.STATUSCODE AND t.MERCHANTID IN "
                            + " ( SELECT MERCHANTID FROM IPGMERCHANT WHERE MERCHANTCUSTOMERID = "
                            + " (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME = '" + searchBean.getLoginUser() + "' ) )"
                            + " AND t.STATUSCODE ='" + StatusVarList.TXN_COMPLETE_CONFIRMED + "'";

                }
            }

            //for cardHolder
            if (searchBean.getCardno() != null && !searchBean.getCardno().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.CARDHOLDERPAN LIKE :cardnum ";
                andString = 1;
            }

            //for cardHolder
            if (searchBean.getOrderid() != null && !searchBean.getOrderid().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.orderid LIKE :orderid ";
                andString = 1;
            }

            //determine what exist as date...first one, second one or both
            if ((!searchBean.getFromDate().toString().isEmpty()) && (!searchBean.getToDate().toString().isEmpty())) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.CREATEDTIME BETWEEN to_date(:frmdate, 'yy-mm-dd') AND to_date(:todate, 'yy-mm-dd') ";
                andString = 1;
            } else if (!searchBean.getFromDate().toString().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.CREATEDTIME >= to_date(:frmdate, 'yy-mm-dd')";
            } else if (!searchBean.getToDate().toString().isEmpty()) {
                if (andString == 1) {
                    whereString += " AND ";
                }
                whereString += " t.CREATEDTIME <= to_date(:todate, 'yy-mm-dd') ";
            }

        } catch (Exception exception) {

            throw exception;
        }

        return whereString;
    }

    //convert string to Date type
    private String stringTodate(String date) throws ParseException, Exception {
        SimpleDateFormat dateFormat;
        Date convertedDate = null;
        try {
            String dateString = date;

            dateFormat = new SimpleDateFormat("yyyy-mm-dd");

            convertedDate = dateFormat.parse(dateString);
            dateFormat.format(convertedDate);

        } catch (Exception ex) {
            throw ex;
        }
        return (dateFormat.format(convertedDate));

    }

    public String saveAudit(Ipgsystemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();

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

    public Ipgtransaction getTransactionInfo(String txnId) throws Exception {
        String sql = "";
        Ipgtransaction ipgtransaction = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            sql = "from Ipgtransaction as u where u.id.ipgtransactionid=:txnId";

            Query query = session.createQuery(sql);
            query.setString("txnId", txnId);

            ipgtransaction = (Ipgtransaction) query.list().get(0);

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.flush();
                    session.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return ipgtransaction;
    }

    public String insertIPGTxnHistory(RefundTxnInputBean inputBean) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            txn = session.beginTransaction();

            Ipgtransactionhistory tranHis = new Ipgtransactionhistory();

            tranHis.setIpgtransactionid(inputBean.getTxnid().trim());

            Ipgstatus st = new Ipgstatus();
            st.setStatuscode(inputBean.getStatus().trim());
            tranHis.setIpgstatus(st);

            tranHis.setLastupdatedtime(sysDate);
            tranHis.setCreatedtime(sysDate);
            tranHis.setLastupdateduser(inputBean.getLoginUser());

            session.save(tranHis);

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

    public String updateTransactionStage(RefundTxnInputBean inputBean) throws Exception {

        Ipgtransaction tran = null;
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ipgtransaction as u where u.id.ipgtransactionid=:txnid";
            Query query = session.createQuery(sql);
            query.setString("txnid", inputBean.getTxnid().trim());
            tran = (Ipgtransaction) query.list().get(0);

            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            if (tran != null) {

                Ipgstatus st = new Ipgstatus();
                st.setStatuscode(inputBean.getStatus());
                tran.setIpgstatus(st);

                tran.setLastupdatedtime(sysDate);

                session.update(tran);

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
