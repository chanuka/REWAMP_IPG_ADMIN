/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.SettlementFileDownloadDataBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.SettlementFileDownloadInputBean;
import com.epic.epay.ipg.bean.transactionmanagement.TransactionDataBean;
import com.epic.epay.ipg.dao.controlpanel.merchantmanagement.SettlementFileDownloadDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.SectionVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author nalin
 */
public class SettlementFileDownloadAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    SettlementFileDownloadInputBean inputBean = new SettlementFileDownloadInputBean();
    SettlementFileDownloadDAO dao;
    private InputStream fileInputStream;
    private String value;

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called SettlementFileDownloadAction : execute");
        return SUCCESS;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.SETTLEMENT_FILE_DOWNLOAD;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Download".equals(method)) {
            task = TaskVarList.DOWNLOAD_TASK;
        } else if ("Search".equals(method)) {
            task = TaskVarList.SEARCH_TASK;
        }
        if ("execute".equals(method)) {
            status = true;
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            status = new Common().checkMethodAccess(task, page, userRole, request);
        }
        return status;
    }

    public String view() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                HttpServletRequest request = ServletActionContext.getRequest();
                HttpSession session = request.getSession(false);
                Ipgsystemuser user = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
                inputBean.setLoginUser(user.getUsername());

                dao = new SettlementFileDownloadDAO();

                List<SettlementFileDownloadDataBean> list = dao.getAllMerchantIdList();
                inputBean.setMerchantList(list);

            } else {
                result = "loginpage";
            }

            System.out.println("called SettlementFileDownloadAction :view");

        } catch (Exception ex) {
            addActionError("Settlement File Download  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SettlementFileDownloadAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.SETTLEMENT_TRANSACTION, request);

        inputBean.setVsettlement(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SETTLE_TASK)) {
                    inputBean.setVsettlement(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                }
            }
        }
        return true;
    }

    public String Search() {
        System.out.println("called SettlementFileDownloadAction : Search");
        try {

            List<SettlementFileDownloadDataBean> dataList = null;
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (inputBean.getSidx() != null) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }
            dao = new SettlementFileDownloadDAO();

            if (inputBean.getMid() == null || inputBean.getMid().isEmpty()) {
                dataList = dao.getBatchList();
            } else {
                dataList = dao.getBatchListByMerchant(inputBean.getMid());
            }



            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                inputBean.setRecords(records);
                inputBean.setGridModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);

            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }
            // }
        } catch (Exception e) {
            Logger.getLogger(SettlementFileDownloadAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Settlement File Download ");
        }
        return "list";
    }

    public String Download() {

        String result = "view";
        String settlementFilePath = null;
        String maxSequenceNo = null;
        String fileName = null;
        int sequenceNo;
        String fileId = null;
        List<SettlementFileDownloadDataBean> batchList = null;
        List<TransactionDataBean> txnList = null;

        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Ipgsystemaudit audit = Common.makeAudittrace(request, TaskVarList.DOWNLOAD_TASK, PageVarList.SETTLEMENT_FILE_DOWNLOAD, SectionVarList.MERCHANTMANAGEMENT, "Settlment File Download Process Start", null);
            txnList = new ArrayList<TransactionDataBean>();
            dao = new SettlementFileDownloadDAO();

            settlementFilePath = dao.getFilePathBySystemOSType(getOS_Type());
            maxSequenceNo = dao.getMaxSequenceNo();
            sequenceNo = Integer.parseInt(maxSequenceNo) + 1;
            maxSequenceNo = String.format("%03d", sequenceNo);
            fileId = "CDCI" + maxSequenceNo + ".INC";
            fileName = settlementFilePath + fileId;

            if (inputBean.getMid() == null || inputBean.getMid().isEmpty()) {

                txnList = dao.getAllTxnList();
                batchList = dao.getBatchList();

                if (txnList.size() > 0) {
                    dao.insertSettlementFile(fileId, maxSequenceNo, audit);
                    this.downlaodSettlemtFile(txnList, settlementFilePath, fileName, maxSequenceNo);

                    for (SettlementFileDownloadDataBean bean : batchList) {
                        dao.updateBatchStatus(bean.getBatchId(), bean.getMid(), fileId, audit);
                    }

                    dao.updateSettelmentFileStatus(fileId, audit);
                    inputBean.setFileName(fileId);
                    addActionMessage(MessageVarList.MERCHANT_SETTLEMT_FILE_DOWNLOAD_SUCCESS);
                    return "download";

                } else {
                    addActionError(MessageVarList.MERCHANT_SETTLEMT_FILE_NO_TRAN);
                }



            } else {

                txnList = dao.getTxnListByMerchant(inputBean.getMid());
                batchList = dao.getBatchListByMerchant(inputBean.getMid());
                if (txnList.size() > 0) {
                    dao.insertSettlementFile(fileId, maxSequenceNo, audit);
                    this.downlaodSettlemtFile(txnList, settlementFilePath, fileName, maxSequenceNo);

                    for (SettlementFileDownloadDataBean bean : batchList) {
                        dao.updateBatchStatus(bean.getBatchId(), bean.getMid(), fileId, audit);
                    }

                    dao.updateSettelmentFileStatus(fileId, audit);
                    inputBean.setFileName(fileId);
                    addActionMessage(MessageVarList.MERCHANT_SETTLEMT_FILE_DOWNLOAD_SUCCESS);
                    return "download";
                } else {
                    addActionError(MessageVarList.MERCHANT_SETTLEMT_FILE_NO_TRAN);
                }

            }

            this.viewMerchantList();

        } catch (Exception ex) {
            addActionError("Settelement File Download  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SettlementFileDownloadAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public String downlaodSettlemtFile(List<TransactionDataBean> txnList, String settlementFilePath, String fileName, String maxSequenceNo) throws Exception {

        System.out.println("called Settlement File Download : Create File");
        String retType = "message";

        BufferedWriter bw = null;
        String record;

        try {
            try {
                File file = new File(settlementFilePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                bw = new BufferedWriter(new FileWriter(fileName, true));

                record = this.createSettlemtFile(txnList, "CDCI" + maxSequenceNo, CommonVarList.BANK_CODE);

                bw.write(record);
                bw.newLine();

                bw.flush();
            } catch (Exception ex) {
                throw ex;
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                    }
                }
            }
            try {
                fileInputStream = new FileInputStream(new File(fileName));

            } catch (FileNotFoundException ex) {
                throw ex;
            } catch (Exception e) {
                throw e;
            }

        } catch (Exception ex) {
            throw ex;
        }
        return retType;
    }

    private String createSettlemtFile(List<TransactionDataBean> txnList, String fileName, String bankCode) throws Exception {
        String record = null;
        try {
            Calendar c1 = Calendar.getInstance();
            String recordDate = this.dateFormater(c1.getTime(), "yyMMdd");
            String generatedDate = this.dateFormater(c1.getTime(), "MMdd");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

            record = "H" + fileName + "00000" + recordDate + String.format("%1$74s", "") + "\n";

            for (TransactionDataBean bean : txnList) {

                String uniqueRefNo = bean.getRrn().substring(3);
//                String uniqueRefNo = bean.getRrn();

                Date date = formatter.parse(bean.getTxnDate());

                record = record
                        + "D"
                        + bankCode
                        + generatedDate
                        + "0000"
                        + uniqueRefNo
                        + bean.getCardNumber()
                        + "5"
                        + this.dateFormater(date, "yyMMdd")
                        + String.format("%09d", Integer.parseInt(bean.getAmount()))
                        + "00"
                        + CommonVarList.TXN_STATUS_SALE
                        + bean.getMid()
                        + bean.getTid()
                        + bean.getApprovalCode()
                        + String.format("%1$11s", "")
                        + "\n";

            }

        } catch (Exception e) {
            throw e;
        }

        return record;

    }

    public String getOS_Type() throws Exception {
        String osType = "";
        String osName = "";
        osName = System.getProperty("os.name", "").toLowerCase();
        if (osName.contains("windows")) {
            osType = "WINDOWS";
        } else if (osName.contains("linux")) {
            osType = "LINUX";
        } else if (osName.contains("sunos")) {
            osType = "SUNOS";
        }
        return osType;
    }

    private String dateFormater(Date rowDate, String dateFormat) throws Exception {

        String DATE_FORMAT = dateFormat; // "yyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(rowDate);

    }

    private void viewMerchantList() throws Exception {

        dao = new SettlementFileDownloadDAO();

        List<SettlementFileDownloadDataBean> list = dao.getAllMerchantIdList();
        inputBean.setMerchantList(list);
        inputBean.setMid(null);

    }
}
