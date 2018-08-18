/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.controlpanel.systemconfig;

import com.epic.epay.ipg.bean.controlpanel.systemconfig.AuditDataBean;
import com.epic.epay.ipg.bean.controlpanel.systemconfig.AuditSearchDTO;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.common.ExcelCommon;
import com.epic.epay.ipg.util.common.HibernateInit;
import com.epic.epay.ipg.util.common.PartialList;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author chalitha
 */
public class SystemAuditDAO {
    private final int columnCount = 35;
    private final int headerRowCount = 14;

    public PartialList<AuditDataBean> getSearchList(AuditSearchDTO auditSearchDTO, int rows, int from, String sortIndex, String sortOrder) throws Exception {
        List<Ipgsystemaudit> searchList = null;
        List<AuditDataBean> dataBeanList = null;
        Session session = null;
        long fullCount = 0;
        if ("".equals(sortIndex.trim())) {
            sortIndex = null;
        }
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Ipgsystemaudit.class);
            criteria.createAlias("ipguserrole", "ur")
                    .createAlias("ipgsection", "sec")
                    .createAlias("ipgpage", "pg")
                    .createAlias("ipgtask", "tk");

            if (sortIndex != null && sortOrder != null) {
                if (sortOrder.equals("asc")) {
                    criteria.addOrder(Order.asc(sortIndex));
                }
                if (sortOrder.equals("desc")) {
                    criteria.addOrder(Order.desc(sortIndex));
                }

            }

            if (auditSearchDTO.getUserRole() != null && !auditSearchDTO.getUserRole().isEmpty()) {
                criteria.add(Restrictions.eq("ur.userrolecode", auditSearchDTO.getUserRole()));
            }

            if (auditSearchDTO.getDescription() != null && !auditSearchDTO.getDescription().isEmpty()) {
                criteria.add(Restrictions.ilike("description", auditSearchDTO.getDescription().trim(), MatchMode.ANYWHERE));
            }

            if (auditSearchDTO.getSection() != null && !auditSearchDTO.getSection().isEmpty()) {
                criteria.add(Restrictions.eq("sec.sectioncode", auditSearchDTO.getSection()));
            }

            if (auditSearchDTO.getIpgpage() != null && !auditSearchDTO.getIpgpage().isEmpty()) {
                criteria.add(Restrictions.eq("pg.pagecode", auditSearchDTO.getIpgpage()));
            }

            if (auditSearchDTO.getTask() != null && !auditSearchDTO.getTask().isEmpty()) {
                criteria.add(Restrictions.eq("tk.taskcode", auditSearchDTO.getTask()));
            }

            if (auditSearchDTO.getIp() != null && !auditSearchDTO.getIp().isEmpty()) {
                criteria.add(Restrictions.ilike("ip", auditSearchDTO.getIp().trim(), MatchMode.ANYWHERE));
            }

            if (auditSearchDTO.getFdate() != null && !auditSearchDTO.getFdate().isEmpty()) {
                criteria.add(Restrictions.ge("createdtime", Common.formatStringtoDate(auditSearchDTO.getFdate())));
            }

            if (auditSearchDTO.getTdate() != null && !auditSearchDTO.getTdate().isEmpty()) {
                Date todate  = Common.formatStringtoDate(auditSearchDTO.getTdate());
                int da = todate.getDate() + 1;
                todate.setDate(da);
                criteria.add(Restrictions.le("createdtime", todate));
            }

            fullCount = criteria.list().size();

            criteria.setFirstResult(from);
            criteria.setMaxResults(rows);

            searchList = criteria.list();
            dataBeanList = new ArrayList<AuditDataBean>();

            for (Ipgsystemaudit m : searchList) {
                dataBeanList.add(new AuditDataBean(m));
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

        PartialList<AuditDataBean> list = new PartialList<AuditDataBean>();

        list.setList(dataBeanList);
        list.setFullCount(fullCount);

        return list;
    }

    public List<Ipgsystemaudit> getAll(int rows, int from, String orderBy) throws Exception {
        List<Ipgsystemaudit> searchList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Ipgsystemaudit.class);
            criteria.setFirstResult(from);
            criteria.setMaxResults(rows);
            criteria.addOrder(Order.desc(orderBy));
            searchList = criteria.list();
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
        return searchList;
    }
    public List searchAudits(String userRole,
            String description,
            String section,
            String page,
            String task,
            String remarks,
            String ip,
            Date fromDate,
            Date toDate) {

        Session session = null;
        session = HibernateInit.sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Ipgsystemaudit.class, "sysaudit");

        criteria.createAlias("sysaudit.ipguserrol.userrolecode", "ipguserrole");
        criteria.createAlias("sysaudit.ipgsection.sectioncode", "section");
        criteria.createAlias("sysaudit.ipgpage.pagecode", "page");
        criteria.createAlias("sysaudit.ipgtask.taskcode", "task");

        List results1 = criteria.list();

        if (userRole != null) {
//            criteria.add(Restrictions.eq("ipguserroleid", userRole));
        }
        if (section != null) {
            criteria.add(Restrictions.eq("section", section));
        }
        if (page != null) {
            criteria.add(Restrictions.le("createdtime", toDate));
        }
        if (task != null) {
            criteria.add(Restrictions.eq("task", task));
        }
        if (fromDate != null) {
            criteria.add(Restrictions.ge("createdtime", fromDate));
        }
        if (toDate != null) {
            criteria.add(Restrictions.le("createdtime", toDate));
        }
        if (ip != null) {
            criteria.add(Restrictions.le("createdtime", toDate));
        }
        List results = criteria.list();
        //
        // Execute the query
        //
        return results;
    }

    public AuditDataBean findAuditById(String auditId) throws Exception {
        AuditDataBean auditDatabean;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            Ipgsystemaudit auditBean = (Ipgsystemaudit) session.get(Ipgsystemaudit.class, new BigDecimal(auditId));
            auditDatabean = new AuditDataBean(auditBean);

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
        return auditDatabean;
    }
    public String saveAudit(Ipgsystemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            txn = session.beginTransaction();
            audit.setCreatedtime(sysDate);
            audit.setLastupdatedtime(sysDate);
            audit.setLastupdateduser(audit.getLastupdateduser());

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
    
    public Object generateExcelReport(AuditSearchDTO auditSearchDTO) throws Exception{
        Session hbsession = null;
        Object returnObject = null;
        List<Ipgsystemaudit> searchList = null;
        
        try {
            String directory = ServletActionContext.getServletContext().getInitParameter("tmpreportpath");
            File file = new File(directory);
            if (file.exists()) {
                FileUtils.deleteDirectory(file);
            }
            long count = 0;

            hbsession = HibernateInit.sessionFactory.openSession();
            hbsession.beginTransaction();
            Criteria criteria = hbsession.createCriteria(Ipgsystemaudit.class);
            criteria.createAlias("ipguserrole", "ur")
                    .createAlias("ipgsection", "sec")
                    .createAlias("ipgpage", "pg")
                    .createAlias("ipgtask", "tk");

            

            if (auditSearchDTO.getUserRole() != null && !auditSearchDTO.getUserRole().isEmpty()) {
                criteria.add(Restrictions.eq("ur.userrolecode", auditSearchDTO.getUserRole()));
            }

            if (auditSearchDTO.getDescription() != null && !auditSearchDTO.getDescription().isEmpty()) {
                criteria.add(Restrictions.ilike("description", auditSearchDTO.getDescription().trim(), MatchMode.ANYWHERE));
            }

            if (auditSearchDTO.getSection() != null && !auditSearchDTO.getSection().isEmpty()) {
                criteria.add(Restrictions.eq("sec.sectioncode", auditSearchDTO.getSection()));
            }

            if (auditSearchDTO.getIpgpage() != null && !auditSearchDTO.getIpgpage().isEmpty()) {
                criteria.add(Restrictions.eq("pg.pagecode", auditSearchDTO.getIpgpage()));
            }

            if (auditSearchDTO.getTask() != null && !auditSearchDTO.getTask().isEmpty()) {
                criteria.add(Restrictions.eq("tk.taskcode", auditSearchDTO.getTask()));
            }

            if (auditSearchDTO.getIp() != null && !auditSearchDTO.getIp().isEmpty()) {
                criteria.add(Restrictions.ilike("ip", auditSearchDTO.getIp().trim(), MatchMode.ANYWHERE));
            }

            if (auditSearchDTO.getFdate() != null && !auditSearchDTO.getFdate().isEmpty()) {
                criteria.add(Restrictions.ge("createdtime", Common.formatStringtoDate(auditSearchDTO.getFdate())));
            }

            if (auditSearchDTO.getTdate() != null && !auditSearchDTO.getTdate().isEmpty()) {
                Date todate  = Common.formatStringtoDate(auditSearchDTO.getTdate());
                int da = todate.getDate() + 1;
                todate.setDate(da);
                criteria.add(Restrictions.le("createdtime", todate));
            }

            int countnum = criteria.list().size();
            count =criteria.list().size();
            if (countnum > 0) {
                long maxRow = Long.parseLong(ServletActionContext.getServletContext().getInitParameter("numberofrowsperexcel"));
                SXSSFWorkbook workbook = this.createExcelTopSection(auditSearchDTO);
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
                
                for (int i = 0; i < numberOfTimes; i++) {
                    criteria.setFirstResult(from);
                    criteria.setMaxResults(selectRow);
                    searchList = criteria.list();
                    for (Ipgsystemaudit m : searchList) {
                        AuditDataBean auditDataBean =new AuditDataBean(m);
                        if (currRow + 1 > maxRow) {
                            fileCount++;
                            this.writeTemporaryFile(workbook, fileCount, directory);
                            workbook = this.createExcelTopSection(auditSearchDTO);
                            sheet = workbook.getSheetAt(0);
                            currRow = headerRowCount;
                            this.createExcelTableHeaderSection(workbook, currRow);
                        }
                        currRow = this.createExcelTableBodySection(workbook, auditDataBean, currRow, listrownumber);
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
    
    private SXSSFWorkbook createExcelTopSection(AuditSearchDTO auditSearchDTO) throws Exception {

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
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(auditSearchDTO.getFdate()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("To Date");
        cell = row.createCell(4);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(auditSearchDTO.getTdate()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(6);
        cell.setCellValue("User Role");
        cell = row.createCell(7);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(auditSearchDTO.getUserRoleDes()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));
//-------------------------------------------------------------------------
        row = sheet.createRow(5);
        cell = row.createCell(0);
        cell.setCellValue("Description");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(auditSearchDTO.getDescription()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("Section");
        cell = row.createCell(4);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(auditSearchDTO.getSectionDes()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(6);
        cell.setCellValue("Page");
        cell = row.createCell(7);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(auditSearchDTO.getIpgpageDes()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

//-------------------------------------------------------------------------------
        row = sheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("Task");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(auditSearchDTO.getTaskDes()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("IP Address");
        cell = row.createCell(4);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(auditSearchDTO.getIp()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

      
//----------------------------------------------------------------------------------------  
        return workbook;
    }
    
    private int createExcelTableHeaderSection(SXSSFWorkbook workbook, int currrow) throws Exception {
        CellStyle columnHeaderCell = ExcelCommon.getColumnHeadeCell(workbook);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.createRow(currrow++);

        Cell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(1);
        cell.setCellValue("User Role");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(2);
        cell.setCellValue("Description");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(3);
        cell.setCellValue("Section");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(4);
        cell.setCellValue("Page");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(5);
        cell.setCellValue("Task");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(6);
        cell.setCellValue("IP Address");
        cell.setCellStyle(columnHeaderCell);
        
        cell = row.createCell(7);
        cell.setCellValue("Created User");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(8);
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
                file = new File(directory + File.separator + "System Audit Report_" + fileCount + ".xlsx");
            } else {
                file = new File(directory + File.separator + "System Audit Report.xlsx");
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
    
    private int createExcelTableBodySection(SXSSFWorkbook workbook, AuditDataBean dataBean, int currrow, int rownumber) throws Exception {
        Sheet sheet = workbook.getSheetAt(0);
        CellStyle rowColumnCell = ExcelCommon.getRowColumnCell(workbook);
        Row row = sheet.createRow(currrow++);

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);

        Cell cell = row.createCell(0);
        cell.setCellValue(dataBean.getId());
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(dataBean.getUserRole());
        cell.setCellStyle(rowColumnCell);
        
        cell = row.createCell(2);
        cell.setCellValue(dataBean.getDescription());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(3);
        cell.setCellValue(dataBean.getSection());
        cell.setCellStyle(rowColumnCell);
        
        cell = row.createCell(4);
        cell.setCellValue(dataBean.getPage());
        cell.setCellStyle(rowColumnCell);
        
        cell = row.createCell(5);
        cell.setCellValue(dataBean.getTask());
        cell.setCellStyle(rowColumnCell);
        
        cell = row.createCell(6);
        cell.setCellValue(dataBean.getIp());
        cell.setCellStyle(rowColumnCell);
        
        cell = row.createCell(7);
        cell.setCellValue(dataBean.getLastUpdatedUser());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(8);
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
}
