/**
 * 
 */
package com.epic.epay.ipg.bean.controlpanel.reportexplorer;

import com.epic.epay.ipg.util.mapping.Ipgcardassociation;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Created on  :Sep 16, 2014, 10:12:37 AM
 * @author 	   :thushanth
 *
 */
public class TransactionExplorerInputBean {
	
	private String fromdate;
    private String todate;
    private String txnid;
    private String merID;
    private String merName;
    private String ECIval;
    private String purDate;
    private String cardholder;
    private String cardno;
    private String status;
    private String cardtype;
    private String reporttype;
    private ByteArrayInputStream excelStream;
    private ByteArrayInputStream zipStream;
    private List<Ipgcardassociation> cardList;
    private List<Ipgstatus> statusList;
    private boolean vgenerate;
    private boolean vsearch;
    private boolean vviewlink;
    private boolean search;
    private HashMap parameterMap;
    private List<TransactionExplorerBean> lst;
    private MerchantDetailBean merDataBean;
    private TransactionIndividualDetailBean txnDataBean;
    private long fullCount;
    /*------------------------list data table  ------------------------------*/
    private List<TransactionExplorerBean> gridModel;
//    private List<MerchantDetailBean> merGridModel;
    private List<TransactionDetailBean> txnGridModel;
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;
    private boolean loadonce = false;
    /*------------------------list data table  ------------------------------*/
    
    
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	public String getTodate() {
		return todate;
	}
	public void setTodate(String todate) {
		this.todate = todate;
	}
	public String getTxnid() {
		return txnid;
	}
	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}
	public String getCardholder() {
		return cardholder;
	}
	public void setCardholder(String cardholder) {
		this.cardholder = cardholder;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public List<Ipgcardassociation> getCardList() {
		return cardList;
	}
	public void setCardList(List<Ipgcardassociation> cardList) {
		this.cardList = cardList;
	}
	public List<Ipgstatus> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<Ipgstatus> statusList) {
		this.statusList = statusList;
	}
	public boolean isVgenerate() {
		return vgenerate;
	}
	public void setVgenerate(boolean vgenerate) {
		this.vgenerate = vgenerate;
	}
	public boolean isVsearch() {
		return vsearch;
	}
	public void setVsearch(boolean vsearch) {
		this.vsearch = vsearch;
	}
	public boolean isVviewlink() {
		return vviewlink;
	}
	public void setVviewlink(boolean vviewlink) {
		this.vviewlink = vviewlink;
	}
	public boolean isSearch() {
		return search;
	}
	public void setSearch(boolean search) {
		this.search = search;
	}
	public HashMap getParameterMap() {
		return parameterMap;
	}
	public void setParameterMap(HashMap parameterMap) {
		this.parameterMap = parameterMap;
	}
	public List<TransactionExplorerBean> getLst() {
		return lst;
	}
	public void setLst(List<TransactionExplorerBean> lst) {
		this.lst = lst;
	}
	public MerchantDetailBean getMerDataBean() {
		return merDataBean;
	}
	public void setMerDataBean(MerchantDetailBean merDataBean) {
		this.merDataBean = merDataBean;
	}
	public long getFullCount() {
		return fullCount;
	}
	public void setFullCount(long fullCount) {
		this.fullCount = fullCount;
	}
	public List<TransactionExplorerBean> getGridModel() {
		return gridModel;
	}
	public void setGridModel(List<TransactionExplorerBean> gridModel) {
		this.gridModel = gridModel;
	}
//	public List<MerchantDetailBean> getMerGridModel() {
//		return merGridModel;
//	}
//	public void setMerGridModel(List<MerchantDetailBean> merGridModel) {
//		this.merGridModel = merGridModel;
//	}
	public List<TransactionDetailBean> getTxnGridModel() {
		return txnGridModel;
	}
	public void setTxnGridModel(List<TransactionDetailBean> txnGridModel) {
		this.txnGridModel = txnGridModel;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Long getRecords() {
		return records;
	}
	public void setRecords(Long records) {
		this.records = records;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getSearchOper() {
		return searchOper;
	}
	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}
	public boolean isLoadonce() {
		return loadonce;
	}
	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

        public String getMerID() {
            return merID;
        }

        public void setMerID(String merID) {
            this.merID = merID;
        }

        public String getMerName() {
            return merName;
        }

        public void setMerName(String merName) {
            this.merName = merName;
        }

        public String getECIval() {
            return ECIval;
        }

        public void setECIval(String ECIval) {
            this.ECIval = ECIval;
        }

        public String getPurDate() {
            return purDate;
        }

        public void setPurDate(String purDate) {
            this.purDate = purDate;
        }

        public TransactionIndividualDetailBean getTxnDataBean() {
            return txnDataBean;
        }

        public void setTxnDataBean(TransactionIndividualDetailBean txnDataBean) {
            this.txnDataBean = txnDataBean;
        }

        public String getReporttype() {
            return reporttype;
        }

        public void setReporttype(String reporttype) {
            this.reporttype = reporttype;
        }

        public ByteArrayInputStream getExcelStream() {
            return excelStream;
        }

        public void setExcelStream(ByteArrayInputStream excelStream) {
            this.excelStream = excelStream;
        }

        public ByteArrayInputStream getZipStream() {
            return zipStream;
        }

        public void setZipStream(ByteArrayInputStream zipStream) {
            this.zipStream = zipStream;
        }
	   
}