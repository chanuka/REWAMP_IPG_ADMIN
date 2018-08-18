/**
 * 
 */
package com.epic.epay.ipg.dao.reportexplorer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.epic.epay.ipg.bean.controlpanel.reportexplorer.TransactionSummaryBean;
import com.epic.epay.ipg.bean.controlpanel.reportexplorer.TransactionSummaryInputBean;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.HibernateInit;

/**
 *
 * Created on  :Sep 15, 2014, 2:50:06 PM
 * @author 	   :thushanth
 *
 */
public class TransactionSummaryDAO {
	
	 public List<TransactionSummaryBean> getSearchList(TransactionSummaryInputBean inputBean, int max, int first, String orderBy) throws Exception {
	        List<TransactionSummaryBean> dataList = new ArrayList<TransactionSummaryBean>();
	        Session session = null;
	        try {
	            String where = this.makeWhereClause(inputBean);
	            long count = 0;

	            session = HibernateInit.sessionFactory.openSession();
	            String sqlCount = "select count(*) from Ipgtransaction p where " + where;
	            Query queryCount = session.createSQLQuery(sqlCount);
	            queryCount = setDatesToQuery(queryCount, inputBean, session);
	            List<BigDecimal> list = queryCount.list();
	            //Iterator itCount = queryCount.iterate();select count(distinct ipgtransactionid) as cnt from Ipgtransaction p where p.ipgtransactionid = '1386913552451702'
	            //count = (Long) itCount.next();
	            count = list.get(0).longValue();
	            if (count > 0) {


	                String sqlSearch = "Select p.ipgtransactionid,m.merchantname,c.description,p.cardholderpan,p.amount,s.description as statusdes,p.createdtime "
	                        + "From Ipgtransaction p "
	                        + "inner join Ipgmerchant m on p.merchantid=m.merchantid "
	                        + "inner join Ipgcardassociation c on p.cardassociationcode=c.cardassociationcode "
	                        + "inner join Ipgstatus s on p.statuscode=s.statuscode"
	                        + " Where " + where + orderBy;




	                Query querySearch = session.createSQLQuery(sqlSearch);
	                querySearch = setDatesToQuery(querySearch, inputBean, session);
	                querySearch.setMaxResults(max);
	                querySearch.setFirstResult(first);

	                List<Object[]> objectArrList = (List<Object[]>) querySearch.list();
//	                Iterator itSearch = querySearch.iterate();
	                for (Object[] objArr : objectArrList) {
	                	TransactionSummaryBean dataBean = new TransactionSummaryBean();
//	                    Mpitransaction txn = (Mpitransaction) itSearch.next();
	                    try {
	                        dataBean.setTxnid(objArr[0].toString());
	                    } catch (NullPointerException npe) {
	                        dataBean.setTxnid("--");
	                    }
	                    try {
	                        dataBean.setMerchantname(objArr[1].toString());
	                    } catch (NullPointerException npe) {
	                        dataBean.setMerchantname("--");
	                    }
	                    try {
	                        dataBean.setAssociationcode(objArr[2].toString());
	                    } catch (NullPointerException npe) {
	                        dataBean.setAssociationcode("--");
	                    }
	                    try {
	                        dataBean.setCardnumber(objArr[3].toString());
	                    } catch (NullPointerException npe) {
	                        dataBean.setCardnumber("--");
	                    }
	                    try {
	                        dataBean.setAmount(objArr[4].toString());
	                    } catch (NullPointerException npe) {
	                        dataBean.setAmount("--");
	                    }
	                    //null has to be checked for every foreign keys
	                    try {
	                        dataBean.setStatus(objArr[5].toString());
	                    } catch (NullPointerException npe) {
	                        dataBean.setStatus("--");
	                    }
	                    try {
	                        dataBean.setCreatedtime(objArr[6].toString());
	                    } catch (NullPointerException npe) {
	                        dataBean.setCreatedtime("--");
	                    }


	                    dataBean.setFullCount(count);

	                    dataList.add(dataBean);
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

	    private String makeWhereClause(TransactionSummaryInputBean inputBean) {
	        String where = "1=1";
	        if ((inputBean.getFromdate() == null || inputBean.getFromdate().isEmpty())
	                && (inputBean.getTodate() == null || inputBean.getTodate().isEmpty())
	                && (inputBean.getTxnid() == null || inputBean.getTxnid().isEmpty())
	                && (inputBean.getCardholder() == null || inputBean.getCardholder().isEmpty())
	                && (inputBean.getCardno() == null || inputBean.getCardno().isEmpty())
	                && (inputBean.getStatus() == null || inputBean.getStatus().isEmpty())
	                && (inputBean.getCardtype() == null || inputBean.getCardtype().isEmpty())) {
	        } else {
	            if (inputBean.getFromdate() != null && !inputBean.getFromdate().isEmpty()) {
	                where += " and p.transactioncreateddatetime >= :fromdate";
	            }
	            if (inputBean.getTodate() != null && !inputBean.getTodate().isEmpty()) {
	                where += " and p.transactioncreateddatetime <= :todate";
	            }
	            if (inputBean.getTxnid() != null && !inputBean.getTxnid().isEmpty()) {
	                where += " and p.ipgtransactionid = '" + inputBean.getTxnid() + "'";
	            }
	            //check
	            if (inputBean.getCardholder() != null && !inputBean.getCardholder().isEmpty()) {
	                where += " and p.cardname = '" + inputBean.getCardholder() + "'";
	            }

	            if (inputBean.getCardno() != null && !inputBean.getCardno().isEmpty()) {
	                where += " and p.cardholderpan = '" + inputBean.getCardno() + "'";
	            }
	            if (inputBean.getStatus() != null && !inputBean.getStatus().isEmpty()) {
	                where += " and p.statuscode = '" + inputBean.getStatus() + "'";

	            }
	            if (inputBean.getCardtype() != null && !inputBean.getCardtype().isEmpty()) {
	                where += " and p.cardassociationcode = '" + inputBean.getCardtype() + "'";

	            }


	        }

	        return where;
	    }

	    public Query setDatesToQuery(Query sql, TransactionSummaryInputBean inputBean, Session session) throws Exception {

	        if ((inputBean.getFromdate() == null || inputBean.getFromdate().isEmpty())
	                && (inputBean.getTodate() == null || inputBean.getTodate().isEmpty())) {
	        } else {
	            if (inputBean.getFromdate() != null && !inputBean.getFromdate().isEmpty()) {
	                if ((Common.formatStringtoDate(inputBean.getFromdate()) != null)) {
	                    sql.setDate("fromdate", Common.formatStringtoDate(inputBean.getFromdate()));
	                }
	            }
	            if (inputBean.getTodate() != null && !inputBean.getTodate().isEmpty()) {
	                if ((Common.formatStringtoDate(inputBean.getTodate()) != null)) {
	                    sql.setDate("todate", Common.formatStringtoDate(inputBean.getTodate()));
	                }
	            }
	        }
	        return sql;
	    }

}
