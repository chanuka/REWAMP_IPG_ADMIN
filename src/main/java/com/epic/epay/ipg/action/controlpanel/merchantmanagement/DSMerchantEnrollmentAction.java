/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.action.controlpanel.merchantmanagement;

import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.DSMerchantEnrollmentBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.DSMerchantEnrollmentInputBean;
import com.epic.epay.ipg.bean.controlpanel.merchantmanagement.IPGDSVISABean;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.dao.controlpanel.merchantmanagement.DSMerchantEnrollmentDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.mapping.Ipgaddress;
import com.epic.epay.ipg.util.mapping.Ipgbin;
import com.epic.epay.ipg.util.mapping.Ipgmerchantcredential;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author badrika
 */
public class DSMerchantEnrollmentAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    DSMerchantEnrollmentInputBean inputBean = new DSMerchantEnrollmentInputBean();
    String[] selectedlist = null;
    private List<IPGDSVISABean> tmpVisaList;
    private String inputFile;
    private WritableCellFormat times;
    private WritableCellFormat timesBoldUnderline;
    private InputStream fileInputStream = null;
    private String fileName;
    WritableWorkbook workbook;
    int rowNum;

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called DSMerchantEnrollmentAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.DS_MERCHANT_ENROL;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("List".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("createExcel".equals(method)) {
            task = TaskVarList.VIEW_TASK;
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
                CommonDAO dao = new CommonDAO();
                inputBean.setCardAssociationList(dao.getDefultCardAssociationList());

            } else {
                result = "loginpage";
            }

            System.out.println("called DSMerchantEnrollmentAction :view");

        } catch (Exception ex) {
            addActionError("DSMerchantEnrollment " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(DSMerchantEnrollmentAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String createExcel() {
        String result = "message";
        try {

            if (inputBean.getSelectedList().get(0).equals("")) {
                addActionError("No records selected");
                CommonDAO dao = new CommonDAO();
                inputBean.setCardAssociationList(dao.getDefultCardAssociationList());
                inputBean.setCardassociation("");
                return "message";

            } else if (inputBean.getSelectedList().get(0).length() > 0) {

                selectedlist = inputBean.getSelectedList().get(0).split("\\,");
                this.getMerchantInfo();

                String osType = "";
                String certPath = null;

                DSMerchantEnrollmentDAO dao = new DSMerchantEnrollmentDAO();
                osType = DSMerchantEnrollmentDAO.getOS_Type();
                certPath = dao.getCertificatePath(osType);

                File directory = new File(certPath);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                fileName = "report.xls";
                this.createExcelwithData(certPath + fileName);
                
                try {
                    fileInputStream = new FileInputStream(new File(certPath + fileName));
                    System.out.println(fileInputStream);
                    return "createExcel";

                } catch (FileNotFoundException ex) {
                    addActionError("File doesn't exists !");
                } catch (Exception e) {
                    addActionError("Excel report download failed !");
                }
            }

            System.out.println("called DSMerchantEnrollmentAction :createExcel");

        } catch (Exception ex) {
            addActionError("DSMerchantEnrollment " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(DSMerchantEnrollmentAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public void createExcelwithData(String filepath) {
        try {
            this.setOutputFile(filepath);
            this.write();
            System.out.println("Excel created in " + filepath);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    private void write() throws IOException, WriteException {
        File file = new File(inputFile);
        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));

        workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Report", 0);
        WritableSheet excelSheet = workbook.getSheet(0);
        createLabel(excelSheet);
        createContent(excelSheet);

        workbook.write();
        workbook.close();
    }

    private void createContent(WritableSheet sheet) throws WriteException, RowsExceededException {

        // Now a bit of text
        int count = 1;
        for (IPGDSVISABean visa : tmpVisaList) {
            addLabel(sheet, 0, count, visa.getAcquirerId());//accuireid
            addLabel(sheet, 1, count, visa.getMerchantId());
            addLabel(sheet, 2, count, visa.getPassword());
            addLabel(sheet, 3, count, visa.getDescription());
            addLabel(sheet, 4, count, visa.getCountry());
//            addLabel(sheet, 5, count, visa.getAction());
//            addLabel(sheet, 6, count, visa.getId());
            count++;

        }
    }

    private void addLabel(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }

    private void createLabel(WritableSheet sheet) throws WriteException {
        // create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TAHOMA, 10);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);

        // Create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(
                WritableFont.TAHOMA, 10, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // automatically wrap the cells
        timesBoldUnderline.setWrap(true);

        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setFormat(timesBoldUnderline);
        //cv.setAutosize(true);

        // Write a few headers
        addCaption(sheet, 0, 0, "Acquirer ID");
        addCaption(sheet, 1, 0, "Merchant ID");
        addCaption(sheet, 2, 0, "Password");
        addCaption(sheet, 3, 0, "Description");
        addCaption(sheet, 4, 0, "Country Code");
//        addCaption(sheet, 5, 0, "Action");
//        addCaption(sheet, 6, 0, "ID");

    }

    private void addCaption(WritableSheet sheet, int column, int row, String s)
            throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }

    private void getMerchantInfo() throws Exception {
        tmpVisaList = new ArrayList<IPGDSVISABean>();
        DSMerchantEnrollmentDAO dao = new DSMerchantEnrollmentDAO();
        List<Ipgbin> binList = dao.getAcquirerBin(inputBean.getCardassociation());
        
        for (Ipgbin binList1 : binList) {
            for (String mid : selectedlist) {
                IPGDSVISABean visaBean = new IPGDSVISABean();
                Ipgaddress address = dao.getAddress(mid);//get addressbean according to the mid
                Ipgmerchantcredential credential = dao.getCredentialPassword(mid,inputBean.getCardassociation()); //set ds status and get password
                //set data
                visaBean.setAcquirerId(binList1.getBin());
                visaBean.setMerchantId(mid);
                visaBean.setPassword(credential.getPassword());
                visaBean.setDescription(address.getAddress1()+","+address.getAddress2()+","+address.getCity());
                visaBean.setCountry(address.getCountrycode());                
                tmpVisaList.add(visaBean);
            }
        }
        
        
    }

    public String List() {
        System.out.println("called DSMerchantEnrollmentAction: List");
        try {
            if (inputBean.isSearch()) {
                System.out.println("dsfsdfsdf");
                if (inputBean.getCardassociation().isEmpty() || inputBean.getCardassociation() == null) {
                    addActionError("Card association empty");
//                    CommonDAO dao = new CommonDAO();
//                    inputBean.setCardAssociationList(dao.getDefultCardAssociationList());
                    return "msg";

                } else {

                    int rows = inputBean.getRows();
                    int page = inputBean.getPage();
                    int to = (rows * page);
                    int from = to - rows;
                    long records = 0;
                    String orderBy = "";
                    if (!inputBean.getSidx().isEmpty()) {
                        orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
                    }
                    DSMerchantEnrollmentDAO dao = new DSMerchantEnrollmentDAO();
                    List<DSMerchantEnrollmentBean> dataList = dao.getMerchantList(inputBean, rows, from, orderBy);
                    System.out.println("***********" + !dataList.isEmpty());
                    if (!dataList.isEmpty()) {
//                    records = dataList.get(0).getFullCount();
                        records = dataList.size();
                        inputBean.setRecords(records);
                        inputBean.setGridModel(dataList);
                        int total = (int) Math.ceil((double) records / (double) rows);
                        inputBean.setTotal(total);
                    } else {
                        inputBean.setRecords(0L);
                        inputBean.setTotal(0);
                    }
                }
            }

        } catch (Exception e) {
            Logger.getLogger(DSMerchantEnrollmentAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " DSMerchantEnrollmentAction");
        }
        return "list";
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(
                PageVarList.DS_MERCHANT_ENROL, request);

        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
                if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                    inputBean.setVadd(false);
                } else if (task.getTaskcode().toString()
                        .equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                }
            }
        }
        inputBean.setVadd(true);
        inputBean.setVupdatebutt(true);

        return true;
    }

}
