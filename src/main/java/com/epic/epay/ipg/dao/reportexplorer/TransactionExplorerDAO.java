/**
 * 
 */
package com.epic.epay.ipg.dao.reportexplorer;

import com.epic.epay.ipg.bean.controlpanel.reportexplorer.MerchantDetailBean;
import com.epic.epay.ipg.bean.controlpanel.reportexplorer.TransactionDetailBean;
import com.epic.epay.ipg.bean.controlpanel.reportexplorer.TransactionExplorerBean;
import com.epic.epay.ipg.bean.controlpanel.reportexplorer.TransactionExplorerInputBean;
import com.epic.epay.ipg.bean.controlpanel.reportexplorer.TransactionIndividualDetailBean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.ExcelCommon;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
import com.epic.epay.ipg.util.mapping.Ipgheadmerchant;
import com.epic.epay.ipg.util.mapping.Ipgmerchant;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtransaction;
import com.epic.epay.ipg.util.mapping.IpgtransactionId;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.StatusVarList;
import com.epic.epay.ipg.util.varlist.UserRoleTypeVarList;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * Created on  :Sep 16, 2014, 10:12:53 AM
 * @author 	   :thushanth
 *
 */
public class TransactionExplorerDAO {
    
        private final int columnCount = 35;
        private final int headerRowCount = 14;
	
	public List<TransactionExplorerBean> getSearchList(TransactionExplorerInputBean inputBean, int max, int first, String orderBy,String falg) throws Exception {
        List<TransactionExplorerBean> dataList = new ArrayList<TransactionExplorerBean>();
        Session hbsession = null;
//        boolean isAdmin = false;
        Ipgsystemuser sysUser = new Ipgsystemuser();
        HttpServletRequest request = ServletActionContext.getRequest();
        sysUser=(Ipgsystemuser)request.getSession(false).getAttribute(SessionVarlist.SYSTEMUSER);
        // logged user is an Admin or not
//        if((sysUser.getIpguserroleByUserrolecode().getIpguserroletype().getUserroletypecode()).equals(UserRoleTypeVarList.ADMINISTRATOR)){
//            isAdmin = true;
//        }
        Ipgheadmerchant headmerchant =null;
//        String merchantCustomer="inner join( select h.merchantcustomerid as id from Ipgheadmerchant as h where h.transactioninitaiatedmerchntid = (select su.merchantid from Ipgsystemuser as su where su.username=:username)) headmerchant ";
        try {
            String where = this.makeWhereClause(inputBean);
            long count = 0;

            hbsession = HibernateInit.sessionFactory.openSession();
            if(falg.equals("YES")){
                String sql = " from Ipgheadmerchant as h where h.transactioninitaiatedmerchntid = (select m.merchantid from Ipgsystemuser as m where m.username=:username)";
                Query query = hbsession.createQuery(sql);
                query.setParameter("username", sysUser.getUsername());
                headmerchant = (Ipgheadmerchant)query.list().get(0);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//            if((sysUser.getIpguserroleByUserrolecode().getIpguserroletype().getUserroletypecode()).equals(UserRoleTypeVarList.ADMINISTRATOR)){
            if(falg.equals("YES")){
                String sqlCount = "select count(*) from Ipgtransaction p inner join Ipgmerchant m on p.merchantid=m.merchantid  where " + where +" and m.merchantcustomerid = :headmerchant ";
                System.out.println("------"+sqlCount);
                Query queryCount = hbsession.createSQLQuery(sqlCount).setString("headmerchant", headmerchant.getMerchantcustomerid());
                queryCount = setDatesToQuery(queryCount, inputBean, hbsession);
                List<BigDecimal> list = queryCount.list();
                //Iterator itCount = queryCount.iterate();select count(distinct ipgtransactionid) as cnt from Ipgtransaction p where p.ipgtransactionid = '1386913552451702'
                //count = (Long) itCount.next();
                count = list.get(0).longValue();
            }else{
                String sqlCount = "select count(*) from Ipgtransaction p inner join Ipgmerchant m on p.merchantid=m.merchantid where " + where ;
                Query queryCount = hbsession.createSQLQuery(sqlCount);
                System.out.println("------"+sqlCount);
                queryCount = setDatesToQuery(queryCount, inputBean, hbsession);
                List<BigDecimal> list = queryCount.list();
                //Iterator itCount = queryCount.iterate();select count(distinct ipgtransactionid) as cnt from Ipgtransaction p where p.ipgtransactionid = '1386913552451702'
                //count = (Long) itCount.next();
                count = list.get(0).longValue();
            }
            

            if (count > 0) {
            	String sqlSearch="";
            	Query querySearch = null;            	
            	
                // check current user is ADMINISTRATOR or not
//                if((sysUser.getIpguserroleByUserrolecode().getIpguserroletype().getUserroletypecode()).equals(UserRoleTypeVarList.ADMINISTRATOR)){
                if(falg.equals("YES")){
                     sqlSearch = "Select p.ipgtransactionid,m.merchantname,c.description,p.cardholderpan,p.amount,s.description as statusdes,p.createdtime,p.merchantid,p.cardname,p.eci,p.purchaseddatetime "
                            + "From Ipgtransaction p "
                            + "inner join Ipgmerchant m on p.merchantid=m.merchantid "
                            + "inner join Ipgcardassociation c on p.cardassociationcode=c.cardassociationcode "
                            + "inner join Ipgstatus s on p.statuscode=s.statuscode"
                            + " Where " + where + " and m.merchantcustomerid = :headmerchant " + orderBy;
                     querySearch = hbsession.createSQLQuery(sqlSearch).setString("headmerchant", headmerchant.getMerchantcustomerid());
                }else{

                     sqlSearch = "Select p.ipgtransactionid,m.merchantname,c.description,p.cardholderpan,p.amount,s.description as statusdes,p.createdtime,p.merchantid,p.cardname,p.eci,p.purchaseddatetime "
                            + "From Ipgtransaction p "
                            + "inner join Ipgmerchant m on p.merchantid=m.merchantid "
                            + "inner join Ipgcardassociation c on p.cardassociationcode=c.cardassociationcode "
                            + "inner join Ipgstatus s on p.statuscode=s.statuscode "                          
                            + "Where " + where +" "+ orderBy;
                     querySearch = hbsession.createSQLQuery(sqlSearch);
                }
               
                querySearch = setDatesToQuery(querySearch, inputBean, hbsession);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                List<Object[]> objectArrList = (List<Object[]>) querySearch.list();
//                Iterator itSearch = querySearch.iterate();
                for (Object[] objArr : objectArrList) {
                	TransactionExplorerBean dataBean = new TransactionExplorerBean();
//                    Mpitransaction txn = (Mpitransaction) itSearch.next();
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
                        String pan =objArr[3].toString().trim();
                        String star ="***************************";
                        String value = "";
                        if(pan.length()>4){
                            value= star.substring(0, pan.length()-4);
                            value+=pan.substring(pan.length()-4, pan.length());
                        }else{
                            value = pan;
                        }
                        dataBean.setCardnumber(value);
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
                        dataBean.setCreatedtime(sdf.format(objArr[6]));
                    } catch (Exception npe) {
                        dataBean.setCreatedtime("--");
                    }
                    try {
                        dataBean.setMerchantid(objArr[7].toString());
                    } catch (NullPointerException npe) {
                        dataBean.setMerchantid("--");
                    }
                    try {
                        dataBean.setCardholder(objArr[8].toString());
                    } catch (NullPointerException npe) {
                        dataBean.setCardholder("--");
                    }
                    try {
                        dataBean.setECIval(objArr[9].toString());
                    } catch (NullPointerException npe) {
                        dataBean.setECIval("--");
                    }
                    try {
                        dataBean.setPurDate(sdf.format(objArr[10]));
                    } catch (Exception npe) {
                        dataBean.setPurDate("--");
                    }


                    dataBean.setFullCount(count);

                    dataList.add(dataBean);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                hbsession.flush();
                hbsession.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return dataList;
    }
	
	// Detail Transaction list
	public List<TransactionDetailBean> getDetailList(TransactionExplorerInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<TransactionDetailBean> dataList = new ArrayList<TransactionDetailBean>(); 
        Session hbsession = null;
        Ipgsystemuser sysUser = new Ipgsystemuser();
        HttpServletRequest request = ServletActionContext.getRequest();
        sysUser=(Ipgsystemuser)request.getSession(false).getAttribute(SessionVarlist.SYSTEMUSER);
        try {
//            String where = this.makeWhereClause(inputBean);
            long count = 0;
            
            hbsession = HibernateInit.sessionFactory.openSession();
            
            
            if((sysUser.getIpguserroleByUserrolecode().getIpguserroletype().getUserroletypecode()).equals(UserRoleTypeVarList.ADMINISTRATOR)){
                String sqlCount = "select count(*) from Ipgtransactionhistory h where h.ipgtransactionid =:txnID";
                Query queryCount = hbsession.createSQLQuery(sqlCount).setString("txnID",inputBean.getTxnid()); ;
//                queryCount = setDatesToQuery(queryCount, inputBean, hbsession);
                List<BigDecimal> list = queryCount.list();
                //Iterator itCount = queryCount.iterate();select count(distinct ipgtransactionid) as cnt from Ipgtransaction p where p.ipgtransactionid = '1386913552451702'
                //count = (Long) itCount.next();
                count = list.get(0).longValue();
            }else{
                String sqlCount = "select count(*) from Ipgtransactionhistory h inner join Ipgtransaction t on h.ipgtransactionid=t.ipgtransactionid where h.ipgtransactionid =:txnID and t.merchantid  =:meruser";
                Query queryCount = hbsession.createSQLQuery(sqlCount).setString("txnID",inputBean.getTxnid()).setString("meruser", sysUser.getMerchantid()); 
                List<BigDecimal> list = queryCount.list();
                count = list.get(0).longValue();
            }

            if (count > 0) {
            	String sqlSearch = "";
            	Query querySearch = null;
            	
                // check current user is ADMINISTRATOR or not
                if((sysUser.getIpguserroleByUserrolecode().getIpguserroletype().getUserroletypecode()).equals(UserRoleTypeVarList.ADMINISTRATOR)){
                     sqlSearch = "Select h.ipgtransactionid,h.ipgtransactionhistoryid,h.remarks,s.description,h.createdtime,h.lastupdatedtime,h.lastupdateduser "
                            + "from Ipgtransactionhistory h "
                                     + "inner join Ipgstatus s on h.statuscode=s.statuscode "
                                     + "inner join Ipgtransaction t on h.ipgtransactionid=t.ipgtransactionid "                        
                                     + "Where h.ipgtransactionid =:txnID"; 
                     querySearch = hbsession.createSQLQuery(sqlSearch).setString("txnID",inputBean.getTxnid());
                     
                }else{
                     sqlSearch = "Select h.ipgtransactionid,h.ipgtransactionhistoryid,h.remarks,s.description,h.createdtime,h.lastupdatedtime,h.lastupdateduser "
                            + "from Ipgtransactionhistory h "
                                     + "inner join Ipgstatus s on h.statuscode=s.statuscode "
                                     + "inner join Ipgtransaction t on h.ipgtransactionid=t.ipgtransactionid "                        
                                     + "Where h.ipgtransactionid =:txnID and t.merchantid  =:meruser "+ orderBy; 
                     querySearch = hbsession.createSQLQuery(sqlSearch).setString("txnID",inputBean.getTxnid()).setString("meruser", sysUser.getMerchantid());
                             			
                }
  
//                querySearch = setDatesToQuery(querySearch, inputBean, hbsession);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);
                
                List<Object[]> objectArrList = (List<Object[]>) querySearch.list();

              for (Object[] objArr : objectArrList) {
//                Iterator it = querySearch.iterate();

//                while (it.hasNext()) {

                	TransactionDetailBean detailBean = new TransactionDetailBean();
//                	Ipgtransactionhistory transac = (Ipgtransactionhistory) it.next();

                    try {
                    	detailBean.setTxnhisid(objArr[1].toString());
                    } catch (NullPointerException npe) {
                    	detailBean.setTxnhisid("--");
                    }
                    try {
                    	detailBean.setStatus(objArr[3].toString());
                    } catch (NullPointerException npe) {
                    	detailBean.setStatus("--");
                    }
                    try {
                    	detailBean.setRemarks(objArr[2].toString());
                    } catch (NullPointerException npe) {
                    	detailBean.setRemarks("--");
                    }
                    try {
                    	detailBean.setLastuptime(objArr[5].toString());
                    } catch (NullPointerException npe) {
                    	detailBean.setLastuptime("--");
                    }
                    try {
                    	detailBean.setLastupuser(objArr[6].toString());
                    } catch (NullPointerException npe) {
                    	detailBean.setLastupuser("--");
                    }
                    try {
                    	detailBean.setCreatedtime(objArr[4].toString());
                    } catch (NullPointerException npe) {
                    	detailBean.setCreatedtime("--");
                    }

                    detailBean.setFullCount(count);

                    dataList.add(detailBean);
                }                
            }                
            
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                hbsession.flush();
                hbsession.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return dataList;
    }
	
	
	public MerchantDetailBean findMerById(String merID) throws Exception {		
        MerchantDetailBean merBean = new MerchantDetailBean();
        Session hbsession = null;
        Ipgsystemuser sysUser = new Ipgsystemuser();
        HttpServletRequest request = ServletActionContext.getRequest();
        sysUser=(Ipgsystemuser)request.getSession(false).getAttribute(SessionVarlist.SYSTEMUSER);
        try {

            hbsession = HibernateInit.sessionFactory.openSession();
            String sql ="";
            Query query = null;
//            String sqlmer = "Select * "
//                    + "From Ipgmerchant m ";
//                    + "inner join Ipgstatus s on m.statuscode=s.statuscode "
//                    + "inner join ipgcountry c on m.countrycode=c.countrycode "
//                    + "inner join Ipgtransaction t on m.merchantid=t.merchantid "
//                    + "Where m.merchantid = 23";
//            + " Where t.ipgtransactionid ='"+inputBean.getTxnid() +"' and m.merchantid = '" + sysUser.getMerchantid()  + "' "+ orderBy;


//            Query querymer = hbsession.createSQLQuery(sqlmer);
//            Iterator itt = (Ipgmerchant) querymer.iterate();

//            while (itt.hasNext()) {
            	
            
            // check current user is ADMINISTRATOR or not
            if((sysUser.getIpguserroleByUserrolecode().getIpguserroletype().getUserroletypecode()).equals(UserRoleTypeVarList.ADMINISTRATOR)){
                 sql = "from Ipgmerchant as m where m.merchantid =:merchantID";
                 query = hbsession.createQuery(sql).setString("merchantID",merID);
                 
            }else{
             sql = "from Ipgmerchant as m where m.merchantid =:merchantID and m.merchantid =:meruser";	
             query = hbsession.createQuery(sql).setString("merchantID",merID).setString("meruser", sysUser.getMerchantid());
            }
            
            
            	List<Ipgmerchant> merdel =  query.list();

                try {
                	merBean.setMerchantname(merdel.get(0).getMerchantname());
                } catch (Exception npe) {
                	merBean.setMerchantname("--");
                }
                try {
                	merBean.setAddressid(merdel.get(0).getIpgaddress().getAddress1());
                } catch (Exception npe) {
                	merBean.setAddressid("--");
                }
                try {
                	merBean.setCountry(merdel.get(0).getIpgcountry().getDescription());
                } catch (Exception npe) {
                	merBean.setCountry("--");
                }
                try {
                	merBean.setContperson(merdel.get(0).getContactperson());
                } catch (Exception npe) {
                	merBean.setContperson("--");
                }
                try {
                	merBean.setMerremarks(merdel.get(0).getRemarks());
                } catch (Exception npe) {
                	merBean.setMerremarks("--");
                }
                try {
                	merBean.setPriURL(merdel.get(0).getPrimaryurl());
                } catch (Exception npe) {
                	merBean.setPriURL("--");
                }                    
                try {
                	merBean.setMerstatus(merdel.get(0).getIpgstatus().getDescription());
                } catch (Exception npe) {
                	merBean.setMerstatus("--");
//                }
            }
            
    } catch (Exception e) {
        throw e;
    } finally {
        try {
            hbsession.flush();
            hbsession.close();
        } catch (Exception e) {
            throw e;
        }
    }
    return merBean;			
			
	}
		

    private String makeWhereClause(TransactionExplorerInputBean inputBean) {
        String where = "1=1";
        if ((inputBean.getFromdate() == null || inputBean.getFromdate().isEmpty())
                && (inputBean.getTodate() == null || inputBean.getTodate().isEmpty())
                && (inputBean.getTxnid() == null || inputBean.getTxnid().isEmpty())
                && (inputBean.getCardholder() == null || inputBean.getCardholder().isEmpty())
                && (inputBean.getCardno() == null || inputBean.getCardno().isEmpty())
                && (inputBean.getStatus() == null || inputBean.getStatus().isEmpty())
                && (inputBean.getMerID() == null || inputBean.getMerID().isEmpty())
                && (inputBean.getMerName() == null || inputBean.getMerName().isEmpty())
                && (inputBean.getECIval() == null || inputBean.getECIval().isEmpty())
                && (inputBean.getPurDate() == null || inputBean.getPurDate().isEmpty())
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
                where += " and lower(p.cardholderpan) like lower('%" + inputBean.getCardno() + "%')";
            }
            if (inputBean.getStatus() != null && !inputBean.getStatus().isEmpty()) {
                where += " and p.statuscode = '" + inputBean.getStatus() + "'";

            }
            if (inputBean.getCardtype() != null && !inputBean.getCardtype().isEmpty()) {
                where += " and p.cardassociationcode = '" + inputBean.getCardtype() + "'";

            }
            if (inputBean.getECIval() != null && !inputBean.getECIval().isEmpty()) {
                where += " and p.eci = '" + inputBean.getECIval() + "'";

            }
            if (inputBean.getPurDate() != null && !inputBean.getPurDate().isEmpty()) {
                where += " and p.purchaseddatetime >= :purdate";
            }
            if (inputBean.getPurDate() != null && !inputBean.getPurDate().isEmpty()) {
                where += " and p.purchaseddatetime <= :purenddate";
            }        
            // Logged user is Admin or not
//            if(isAd){
//            if (inputBean.getMerID() != null && !inputBean.getMerID().isEmpty()) {
//                where += " and p.merchantid = '" + inputBean.getMerID() + "'";
//
//            }
//            if (inputBean.getMerName() != null && !inputBean.getMerName().isEmpty()) {
//                where += " and lower(m.merchantname) like lower('%" + inputBean.getMerName() + "%')";
//
//            }
//        }
            if (inputBean.getMerID() != null && !inputBean.getMerID().isEmpty()) {
                where += " and p.merchantid = '" + inputBean.getMerID() + "'";

            }
            if (inputBean.getMerName() != null && !inputBean.getMerName().isEmpty()) {
                where += " and lower(m.merchantname) like lower('%" + inputBean.getMerName() + "%')";
            }

        }

        return where;
    }

    public Query setDatesToQuery(Query sql, TransactionExplorerInputBean inputBean, Session session) throws Exception {

        if ((inputBean.getFromdate() == null || inputBean.getFromdate().isEmpty())
                && (inputBean.getPurDate() == null || inputBean.getPurDate().isEmpty())
                && (inputBean.getTodate() == null || inputBean.getTodate().isEmpty())) {
        } else {
            if (inputBean.getFromdate() != null && !inputBean.getFromdate().isEmpty()) {
                if ((Common.formatStringtoDate(inputBean.getFromdate()) != null)) {
                    sql.setDate("fromdate", Common.formatStringtoDate(inputBean.getFromdate()));
                }
            }
            if (inputBean.getTodate() != null && !inputBean.getTodate().isEmpty()) {
                if ((Common.formatStringtoDate(inputBean.getTodate()) != null)) {
                    Date d = Common.formatStringtoDate(inputBean.getTodate());
                    int da = d.getDate() + 1;
                    d.setDate(da);
                    sql.setDate("todate", d);
                }
            }
            if (inputBean.getPurDate() != null && !inputBean.getPurDate().isEmpty()) {
                if ((Common.formatStringtoDate(inputBean.getPurDate()) != null)) {
                    sql.setDate("purdate", Common.formatStringtoDate(inputBean.getPurDate()));
                    sql.setDate("purenddate", new Date ((Common.formatStringtoDate(inputBean.getPurDate())).getTime()+TimeUnit.DAYS.toMillis(1)));
                }
            }
        }
        return sql;
    }
    
    public TransactionIndividualDetailBean findTxnById(TransactionExplorerInputBean inputBean) throws Exception {	
        Session hbsession = null;
        Ipgtransaction txn=null;
        TransactionIndividualDetailBean txnDetail= new TransactionIndividualDetailBean();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            hbsession = HibernateInit.sessionFactory.openSession();
            IpgtransactionId id = new IpgtransactionId();
            id.setIpgtransactionid(inputBean.getTxnid());
            id.setMerchantid(inputBean.getMerID());
            txn =(Ipgtransaction)hbsession.get(Ipgtransaction.class, id);
            

            try {
                   txnDetail.setTxnid(txn.getId().getIpgtransactionid().toString());
            } catch (Exception npe) {
                    txnDetail.setTxnid("--");
            }
            try {
                    txnDetail.setMerchantid(txn.getId().getMerchantid().toString());
                    Ipgmerchant merch =(Ipgmerchant)hbsession.get(Ipgmerchant.class,txn.getId().getMerchantid());
                    txnDetail.setMerchantname(merch.getMerchantname());
            } catch (Exception npe) {
                    txnDetail.setMerchantid("--");
                    txnDetail.setMerchantname("--");
            }
            try {
                    txnDetail.setAssociationcode(txn.getIpgcardassociation().getDescription().toString());
            } catch (Exception npe) {
                   txnDetail.setAssociationcode("--");
            }
            try {
                    txnDetail.setCurrency(txn.getIpgcurrency().getDescription().toString());
            } catch (Exception npe) {
                    txnDetail.setCurrency("--");
            }
            try {
                    txnDetail.setCountry(txn.getIpgcountry().getDescription().toString());
            } catch (Exception npe) {
                    txnDetail.setCountry("--");
            }
            try {
                    txnDetail.setMerchantremarks(txn.getMerchantremarks().toString());
            } catch (Exception npe) {
                    txnDetail.setMerchantremarks("--");
            }                    
            try {
                    String pan =txn.getCardholderpan().trim().toString();
                    String star ="***************************";
                    String value = "";
                    if(pan.length()>4){
                        value= star.substring(0, pan.length()-4);
                        value+=pan.substring(pan.length()-4, pan.length());
                    } else {
                        value = pan;
                    }
                    txnDetail.setCardholderpan(value);
            } catch (Exception npe) {
                    txnDetail.setCardholderpan("--");
            }
            try {
                    txnDetail.setCardname(txn.getCardname().toString());
            } catch (Exception npe) {
                   txnDetail.setCardname("--");
            }
            try {
                    txnDetail.setAmount(String.valueOf(txn.getAmount()).toString());
            } catch (Exception npe) {
                    txnDetail.setAmount("--");
            }
            try {
                    txnDetail.setStatus(txn.getIpgstatus().getDescription().toString());
            } catch (Exception npe) {
                    txnDetail.setStatus("--");
            }
            try {
                    txnDetail.setPurchaseddatetime(sdf.format(txn.getPurchaseddatetime()));
            } catch (Exception npe) {
                    txnDetail.setPurchaseddatetime("--");
            }                    
            try {
                    txnDetail.setExpirydate(txn.getExpirydate().toString());
            } catch (Exception npe) {
                    txnDetail.setExpirydate("--");
            }
            try {
                    txnDetail.setTerminalid(txn.getTerminalid().toString());
            } catch (Exception npe) {
                    txnDetail.setTerminalid("--");
            }                    
            try {
                    txnDetail.setTransactioncreateddatetime(sdf.format(txn.getTransactioncreateddatetime()));
            } catch (Exception npe) {
                    txnDetail.setTransactioncreateddatetime("--");
            }
            try {
                    txnDetail.setRrn(txn.getRrn().toString());
            } catch (Exception npe) {
                   txnDetail.setRrn("--");
            }
            try {
                    txnDetail.setApprovalcode(txn.getApprovalcode().toString());
            } catch (Exception npe) {
                    txnDetail.setApprovalcode("--");
            }
            try {
                    txnDetail.setBatchnumber(txn.getBatchnumber().toString());
            } catch (Exception npe) {
                    txnDetail.setBatchnumber("--");
            }
            try {
                    txnDetail.setMerchantreferanceno(txn.getMerchantreferanceno().toString().toString());
            } catch (Exception npe) {
                    txnDetail.setMerchantreferanceno("--");
            }                    
            try {
                    txnDetail.setClientip(txn.getClientip().toString());
            } catch (Exception npe) {
                    txnDetail.setClientip("--");
            }
            try {
                    txnDetail.setMerchanttype(txn.getMerchanttype().toString());
            } catch (Exception npe) {
                   txnDetail.setMerchanttype("--");
            }
            try {
                    txnDetail.setTxncategory(txn.getTxncategory().toString());
            } catch (Exception npe) {
                    txnDetail.setTxncategory("--");
            }
            try {
                    txnDetail.setLastupdateduser(txn.getLastupdateduser().toString());
            } catch (Exception npe) {
                    txnDetail.setLastupdateduser("--");
            }
            try {
                    txnDetail.setLastupdatedtime(sdf.format(txn.getLastupdatedtime()));
            } catch (Exception npe) {
                    txnDetail.setLastupdatedtime("--");
            }                    
            try {
                    txnDetail.setCreatedtime(sdf.format(txn.getCreatedtime()));
            } catch (Exception npe) {
                    txnDetail.setCreatedtime("--");
            }
            
    } catch (Exception e) {
        throw e;
    } finally {
        try {
            hbsession.flush();
            hbsession.close();
        } catch (Exception e) {
            throw e;
        }
    }
    return txnDetail;			
			
	}
    
    public Object generateExcelReport(TransactionExplorerInputBean inputBean,String flag) throws Exception{
        Session hbsession = null;
        Object returnObject = null;
//        boolean isAdmin = false;
        Ipgsystemuser sysUser = new Ipgsystemuser();
        HttpServletRequest request = ServletActionContext.getRequest();
        sysUser=(Ipgsystemuser)request.getSession(false).getAttribute(SessionVarlist.SYSTEMUSER);
        Ipgheadmerchant headmerchant =null;
        // logged user is an Admin or not
//        if((sysUser.getIpguserroleByUserrolecode().getIpguserroletype().getUserroletypecode()).equals(UserRoleTypeVarList.ADMINISTRATOR)){
//            isAdmin = true;
//        }
//        String merchantCustomer="inner join( select h.merchantcustomerid as id from Ipgheadmerchant as h where h.transactioninitaiatedmerchntid = (select su.merchantid from Ipgsystemuser as su where su.username=:username)) headmerchant ";
        try {
            String directory = ServletActionContext.getServletContext().getInitParameter("tmpreportpath");
            File file = new File(directory);
            if (file.exists()) {
                FileUtils.deleteDirectory(file);
            }
            String where = this.makeWhereClause(inputBean);
            long count = 0;

            hbsession = HibernateInit.sessionFactory.openSession();
            if(flag.equals("YES")){
                String sql = " from Ipgheadmerchant as h where h.transactioninitaiatedmerchntid = (select m.merchantid from Ipgsystemuser as m where m.username=:username)";
                Query query = hbsession.createQuery(sql);
                query.setParameter("username", sysUser.getUsername());
                headmerchant = (Ipgheadmerchant)query.list().get(0);
            }
//            if((sysUser.getIpguserroleByUserrolecode().getIpguserroletype().getUserroletypecode()).equals(UserRoleTypeVarList.ADMINISTRATOR)){
            if(flag.equals("YES")){
                
                
                String sqlCount = "select count(*) from Ipgtransaction p inner join Ipgmerchant m on p.merchantid=m.merchantid where " + where + " and m.merchantcustomerid =:headmerchant ";
                Query queryCount = hbsession.createSQLQuery(sqlCount).setString("headmerchant", headmerchant.getMerchantcustomerid());
                queryCount = setDatesToQuery(queryCount, inputBean, hbsession);
                List<BigDecimal> list = queryCount.list();
                //Iterator itCount = queryCount.iterate();select count(distinct ipgtransactionid) as cnt from Ipgtransaction p where p.ipgtransactionid = '1386913552451702'
                //count = (Long) itCount.next();
                count = list.get(0).longValue();
            }else{
                String sqlCount = "select count(*) from Ipgtransaction p inner join Ipgmerchant m on p.merchantid=m.merchantid where " + where ;
                Query queryCount = hbsession.createSQLQuery(sqlCount);
                queryCount = setDatesToQuery(queryCount, inputBean, hbsession);
                List<BigDecimal> list = queryCount.list();
                //Iterator itCount = queryCount.iterate();select count(distinct ipgtransactionid) as cnt from Ipgtransaction p where p.ipgtransactionid = '1386913552451702'
                //count = (Long) itCount.next();
                count = list.get(0).longValue();
            }
            
            int countnum = (int)count;
            if (countnum > 0) {
            	String sqlSearch="";
            	Query querySearch = null;            	
            	long maxRow = Long.parseLong(ServletActionContext.getServletContext().getInitParameter("numberofrowsperexcel"));
                SXSSFWorkbook workbook = this.createExcelTopSection(inputBean);
                Sheet sheet = workbook.getSheetAt(0);

                int currRow = headerRowCount;
                int fileCount = 0;

                currRow = this.createExcelTableHeaderSection(workbook, currRow);
                
                int selectRow = Integer.parseInt(ServletActionContext.getServletContext().getInitParameter("numberofselectrows"));
                int numberOfTimes = countnum / selectRow;
                if ((countnum % selectRow) > 0) {
                    numberOfTimes += 1;
                }
                int from = 0;
                int listrownumber = 1;

                // check current user is ADMINISTRATOR or not
//                if((sysUser.getIpguserroleByUserrolecode().getIpguserroletype().getUserroletypecode()).equals(UserRoleTypeVarList.ADMINISTRATOR)){
                if(flag.equals("YES")){
                     sqlSearch = "Select p.ipgtransactionid,m.merchantname,c.description,p.cardholderpan,p.amount,s.description as statusdes,p.createdtime,p.merchantid,p.cardname,p.eci,p.purchaseddatetime "
                            + "From Ipgtransaction p "
                            + "inner join Ipgmerchant m on p.merchantid=m.merchantid "
                            + "inner join Ipgcardassociation c on p.cardassociationcode=c.cardassociationcode "
                            + "inner join Ipgstatus s on p.statuscode=s.statuscode"
                            + " Where " + where +   " and m.merchantcustomerid =:headmerchant " ;
                     querySearch = hbsession.createSQLQuery(sqlSearch).setString("headmerchant", headmerchant.getMerchantcustomerid());
                }else{

                     sqlSearch = "Select p.ipgtransactionid,m.merchantname,c.description,p.cardholderpan,p.amount,s.description as statusdes,p.createdtime,p.merchantid,p.cardname,p.eci,p.purchaseddatetime "
                            + "From Ipgtransaction p "
                            + "inner join Ipgmerchant m on p.merchantid=m.merchantid "
                            + "inner join Ipgcardassociation c on p.cardassociationcode=c.cardassociationcode "
                            + "inner join Ipgstatus s on p.statuscode=s.statuscode "                          
                            + "Where " + where ;
                     querySearch = hbsession.createSQLQuery(sqlSearch);
                }
               
                querySearch = setDatesToQuery(querySearch, inputBean, hbsession);
                for (int i = 0; i < numberOfTimes; i++) {
                    querySearch.setMaxResults(selectRow);
                    querySearch.setFirstResult(from);
                    List<Object[]> objectArrList = (List<Object[]>) querySearch.list();
    //                Iterator itSearch = querySearch.iterate();
                    for (Object[] objArr : objectArrList) {
                            TransactionExplorerBean dataBean = new TransactionExplorerBean();
    //                    Mpitransaction txn = (Mpitransaction) itSearch.next();
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
                            String pan =objArr[3].toString().trim();
                            String star ="***************************";
                            String value = "";
                            if(pan.length()>4){
                                value= star.substring(0, pan.length()-4);
                                value+=pan.substring(pan.length()-4, pan.length());
                            }else{
                                value = pan;
                            }
                            dataBean.setCardnumber(value);
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
                        try {
                            dataBean.setMerchantid(objArr[7].toString());
                        } catch (NullPointerException npe) {
                            dataBean.setMerchantid("--");
                        }
                        try {
                            dataBean.setCardholder(objArr[8].toString());
                        } catch (NullPointerException npe) {
                            dataBean.setCardholder("--");
                        }
                        try {
                            dataBean.setECIval(objArr[9].toString());
                        } catch (NullPointerException npe) {
                            dataBean.setECIval("--");
                        }
                        try {
                            dataBean.setPurDate(objArr[10].toString());
                        } catch (NullPointerException npe) {
                            dataBean.setPurDate("--");
                    }
                        dataBean.setFullCount(count);
                         if (currRow + 1 > maxRow) {
                                fileCount++;
                                this.writeTemporaryFile(workbook, fileCount, directory);
                                workbook = this.createExcelTopSection(inputBean);
                                sheet = workbook.getSheetAt(0);
                                currRow = headerRowCount;
                                this.createExcelTableHeaderSection(workbook, currRow);
                            }
                            currRow = this.createExcelTableBodySection(workbook, dataBean, currRow, listrownumber);
                            listrownumber++;
                            if (currRow % 100 == 0) {
                                ((SXSSFSheet) sheet).flushRows(100); // retain 100 last rows and flush all others

                                // ((SXSSFSheet)sh).flushRows() is a shortcut for ((SXSSFSheet)sh).flushRows(0),
                                // this method flushes all rows
                            }
                    }
                    from = from + selectRow;
                }
                Date createdTime = CommonDAO.getSystemDate(hbsession);
                this.createExcelBotomSection(workbook, currRow, count, createdTime);

                if (fileCount > 0) {
                    fileCount++;
                    this.writeTemporaryFile(workbook, fileCount, directory);
                    ByteArrayOutputStream outputStream = Common.zipFiles(file.listFiles());
                    returnObject = outputStream;
                    workbook.dispose();
                } else {
                    for (int i = 0; i < columnCount; i++) {
                        //to auto size all column in the sheet
                        sheet.autoSizeColumn(i);
                    }

                    returnObject = workbook;
                }
            }
               

        } catch (Exception e) {
            throw e;
        } finally {
            if (hbsession != null) {
                hbsession.close();
            }
        }
        return returnObject;
    }
    
    private SXSSFWorkbook createExcelTopSection(TransactionExplorerInputBean inputBean) throws Exception {

        CommonDAO dao = new CommonDAO();

        SXSSFWorkbook workbook = new SXSSFWorkbook(-1);
        Sheet sheet = workbook.createSheet("TransactionExplorer_Report");

        CellStyle fontBoldedUnderlinedCell = ExcelCommon.getFontBoldedUnderlinedCell(workbook);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Internet Payment Gateway");
        cell.setCellStyle(fontBoldedUnderlinedCell);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("Transaction Explorer Report");
        cell.setCellStyle(fontBoldedUnderlinedCell);
//-----------------------------------------------------------------
        row = sheet.createRow(4);
        cell = row.createCell(0);
        cell.setCellValue("From Date");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getFromdate()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("To Date");
        cell = row.createCell(4);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getTodate()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(6);
        cell.setCellValue("Transaction ID");
        cell = row.createCell(7);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getTxnid()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));
//-------------------------------------------------------------------------
        row = sheet.createRow(5);
        cell = row.createCell(0);
        cell.setCellValue("Merchant ID");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getMerID()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("Card Holder");
        cell = row.createCell(4);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getCardholder()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(6);
        cell.setCellValue("Merchant Name");
        cell = row.createCell(7);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getMerName()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

//-------------------------------------------------------------------------------
        row = sheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("Card Number");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getCardno()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("Purchase Date");
        cell = row.createCell(4);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getPurDate()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(6);
        cell.setCellValue("Status");
        cell = row.createCell(7);
        String statusDes = "";
        try{
            statusDes = dao.getStatusByprefix(inputBean.getStatus());
        }catch(Exception e){
            statusDes = "-ALL-";
        }
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(statusDes));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

//-----------------------------------------------------------------------------------------
        row = sheet.createRow(7);

        cell = row.createCell(0);
        cell.setCellValue("ECI Value");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getECIval()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("Card Association");
        cell = row.createCell(4);
        String cardAssoc = ""; 
        try{
            cardAssoc = this.getCardAssociationByprefix(inputBean.getCardtype());
        }catch(Exception e){
            cardAssoc = "-ALL-";
        }
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(cardAssoc));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

  
//----------------------------------------------------------------------------------------  
        return workbook;
    }
    
    private int createExcelTableHeaderSection(SXSSFWorkbook workbook, int currrow) throws Exception {
        CellStyle columnHeaderCell = ExcelCommon.getColumnHeadeCell(workbook);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.createRow(currrow++);

        Cell cell = row.createCell(0);
        cell.setCellValue("No");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(1);
        cell.setCellValue("Transaction ID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(2);
        cell.setCellValue("Merchant ID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(3);
        cell.setCellValue("Card Holder Name");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(4);
        cell.setCellValue("Merchant Name");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(5);
        cell.setCellValue("Card Number");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(6);
        cell.setCellValue("Purchase Date");
        cell.setCellStyle(columnHeaderCell);
        
        cell = row.createCell(7);
        cell.setCellValue("Status");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(8);
        cell.setCellValue("ECI Value");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(9);
        cell.setCellValue("Card Association");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(10);
        cell.setCellValue("Amount");
        cell.setCellStyle(columnHeaderCell);
        
        cell = row.createCell(11);
        cell.setCellValue("Created Time");
        cell.setCellStyle(columnHeaderCell);

        return currrow;
    }
    
    private void writeTemporaryFile(SXSSFWorkbook workbook, int fileCount, String directory) throws Exception {
        File file;
        FileOutputStream outputStream = null;
        try {
            Sheet sheet = workbook.getSheetAt(0);
//            for (int i = 0; i < columnCount; i++) {
//                //to auto size all column in the sheet
//                sheet.autoSizeColumn(i);
//            }

            file = new File(directory);
            if (!file.exists()) {
                System.out.println("Directory created or not : " + file.mkdirs());
            }

            if (fileCount > 0) {
                file = new File(directory + File.separator + "Transaction Explorer Report_" + fileCount + ".xlsx");
            } else {
                file = new File(directory + File.separator + "Transaction Explorer Report.xlsx");
            }
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
        } catch (IOException e) {
            throw e;
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }
    
    private int createExcelTableBodySection(SXSSFWorkbook workbook, TransactionExplorerBean dataBean, int currrow, int rownumber) throws Exception {
        Sheet sheet = workbook.getSheetAt(0);
        CellStyle rowColumnCell = ExcelCommon.getRowColumnCell(workbook);
        Row row = sheet.createRow(currrow++);

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);

        Cell cell = row.createCell(0);
        cell.setCellValue(rownumber);
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(dataBean.getTxnid());
        cell.setCellStyle(rowColumnCell);
        
        cell = row.createCell(2);
        cell.setCellValue(dataBean.getMerchantid());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(3);
        cell.setCellValue(dataBean.getCardholder());
        cell.setCellStyle(rowColumnCell);
        
        cell = row.createCell(4);
        cell.setCellValue(dataBean.getMerchantname());
        cell.setCellStyle(rowColumnCell);
        
        cell = row.createCell(5);
        cell.setCellValue(dataBean.getCardnumber());
        cell.setCellStyle(rowColumnCell);
        
        cell = row.createCell(6);
        cell.setCellValue(dataBean.getPurDate());
        cell.setCellStyle(rowColumnCell);
        
        cell = row.createCell(7);
        cell.setCellValue(dataBean.getStatus());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(8);
        cell.setCellValue(dataBean.getECIval());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(9);
        cell.setCellValue(dataBean.getAssociationcode());
        cell.setCellStyle(rowColumnCell);
        
        cell = row.createCell(10);
        cell.setCellValue(dataBean.getAmount());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(11);
        cell.setCellValue(dataBean.getCreatedtime());
        cell.setCellStyle(rowColumnCell);

        return currrow;
    }
    
    private void createExcelBotomSection(SXSSFWorkbook workbook, int currrow, long count, Date date) throws Exception {

        CellStyle fontBoldedCell = ExcelCommon.getFontBoldedCell(workbook);
        Sheet sheet = workbook.getSheetAt(0);

        currrow++;
        Row row = sheet.createRow(currrow++);
        Cell cell = row.createCell(0);
        cell.setCellValue("Summary");
        cell.setCellStyle(fontBoldedCell);

        row = sheet.createRow(currrow++);
        cell = row.createCell(0);
        cell.setCellValue("Total Record Count");
        cell = row.createCell(1);
        cell.setCellValue(count);
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(currrow++);
        cell = row.createCell(0);
        cell.setCellValue("Report Created Time");
        cell = row.createCell(1);
        if (date != null && !date.toString().isEmpty()) {
            cell.setCellValue(date.toString().substring(0, 19));
        } else {
            cell.setCellValue("--");
        }
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));
    }
    
    public String getCardAssociationByprefix(String srefix) throws Exception {
        Ipgcardassociation st = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            st = (Ipgcardassociation) session.get(Ipgcardassociation.class, srefix);
        } catch (Exception he) {
            throw he;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return st.getDescription();
    }
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
            String sql = "SELECT MERCHANTCUSTOMERID FROM IPGHEADMERCHANT WHERE TRANSACTIONINITAIATEDMERCHNTID in (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME =:username)";
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
            String sql = "SELECT MERCHANTCUSTOMERID FROM IPGHEADMERCHANT WHERE TRANSACTIONINITAIATEDMERCHNTID in (SELECT MERCHANTID FROM IPGSYSTEMUSER WHERE USERNAME =:username) AND TRANSACTIONINITAIATEDSTATUS =:status";
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
}

