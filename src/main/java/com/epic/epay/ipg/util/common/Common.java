/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.common;

import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.mapping.Ipgpage;
import com.epic.epay.ipg.util.mapping.Ipgsection;
import com.epic.epay.ipg.util.mapping.Ipgstatus;
import com.epic.epay.ipg.util.mapping.Ipgsystemaudit;
import com.epic.epay.ipg.util.mapping.Ipgsystemuser;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.SessionVarlist;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chanuka
 */
public class Common {

    //Make Audittrace
    public static Ipgsystemaudit makeAudittrace(HttpServletRequest request, String task, String page, String section, String description, String remarks) {

        HttpSession session = request.getSession(false);

        Ipgsystemuser sysUser = (Ipgsystemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);

        Ipgsystemaudit audit = new Ipgsystemaudit();

        audit.setIpguserrole(sysUser.getIpguserroleByUserrolecode());
        audit.setDescription(description + " by " + sysUser.getUsername());
        audit.setIp(request.getRemoteAddr());

        Ipgsection sec = new Ipgsection();
        sec.setSectioncode(section);
        audit.setIpgsection(sec);

        Ipgstatus st = new Ipgstatus();
        st.setStatuscode(CommonVarList.STATUS_ACTIVE);
        audit.setIpgstatus(st);

        Ipgpage pg = new Ipgpage();
        pg.setPagecode(page);
        audit.setIpgpage(pg);

        Ipgtask tk = new Ipgtask();
        tk.setTaskcode(task);
        audit.setIpgtask(tk);

        audit.setRemarks(remarks);
        audit.setLastupdateduser(sysUser.getUsername());
        audit.setLastupdatedtime(new Date());
        return audit;

    }

    //Make Audittrace
    public static Ipgsystemaudit makeAudittrace(HttpServletRequest request, Ipgsystemuser user, String task, String page, String section, String description, String remarks) {

        Ipgsystemaudit audit = new Ipgsystemaudit();

        audit.setIpguserrole(user.getIpguserroleByUserrolecode());
        audit.setDescription(description + " by " + user.getUsername());
        audit.setIp(request.getRemoteAddr());


        Ipgsection sec = new Ipgsection();
        sec.setSectioncode(section);
        audit.setIpgsection(sec);

        Ipgstatus st = new Ipgstatus();
        st.setStatuscode(CommonVarList.STATUS_ACTIVE);
        audit.setIpgstatus(st);

        Ipgpage pg = new Ipgpage();
        pg.setPagecode(page);
        audit.setIpgpage(pg);

        Ipgtask tk = new Ipgtask();
        tk.setTaskcode(task);
        audit.setIpgtask(tk);

        audit.setRemarks(remarks);
        audit.setLastupdateduser(user.getUsername());
        audit.setLastupdatedtime(new Date());
        return audit;
    }

    public static String makeHash(String text) throws Exception {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    //returns allowed task list of current user
    public List<Ipgtask> getUserTaskListByPage(String page, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        HashMap<String, List<Ipgtask>> pageMap = (HashMap<String, List<Ipgtask>>) session.getAttribute(SessionVarlist.TASKMAP);
        List<Ipgtask> taskList = pageMap.get(page);
        return taskList;
    }

    //checks the accees to the method name passed
    public boolean checkMethodAccess(String taskcode, String page, String userRole, HttpServletRequest request) {
        boolean access = false;
        if (taskcode == null) {
            access = false;
        } else if (taskcode.isEmpty()) {
            access = false;
        } else {
            HttpSession session = request.getSession(false);
            HashMap<String, List<Ipgtask>> pageMap = (HashMap<String, List<Ipgtask>>) session.getAttribute(SessionVarlist.TASKMAP);
            List<Ipgtask> taskList = pageMap.get(page);
            if (taskList == null) {
                access = false;
            } else if (taskList.size() < 1) {
                access = false;
            } else {
                for (Ipgtask task : taskList) {
                    if (task.getTaskcode().toString().trim().equalsIgnoreCase(taskcode.trim())) {
                        access = true;
                        if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {

                            try {

                                session.setAttribute(SessionVarlist.CURRENTPAGE, new CommonDAO().getPageDescription(page).getDescription());

                                session.setAttribute(SessionVarlist.CURRENTSECTION, new CommonDAO().getSectionOfPage(page, userRole).getDescription());

                            } catch (Exception ex) {
                                Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                        break;
                    }
                }
            }
        }
        return access;
    }

    public static Date formatStringtoDate(String date) {
        Date fdate = null;
        try {
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            fdate = dateFormat.parse(date);
        } catch (Exception e) {
            System.out.println("Date Conversion Error");
        }
        return fdate;
    }

    public static long formatStringtoLongDate(String date) {
        Date fdate = null;
        long sqldate = 0;
        try {
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            fdate = dateFormat.parse(date);

            sqldate = fdate.getTime();

        } catch (Exception e) {
            System.out.println("Date Conversion Error");
        }
        return sqldate;
    }
    
    //to get os name (windows or linux or SUNOS)
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
    
    public static String checkEmptyorNullString(String feildName, String str) {

        StringBuilder stringBuilder = new StringBuilder();

        if (str == null || str.isEmpty()) {
            stringBuilder.append("");
        } else {
            stringBuilder.append(feildName).append(" - ").append(str).append(",");
        }
        return stringBuilder.toString();
    }
    
    public static String replaceEmptyorNullStringToALL(String string) {
        String value = "-ALL-";
        if (string != null && !string.trim().isEmpty()) {
            value = string;
        }
        return value;
    }
    
    public static ByteArrayOutputStream zipFiles(File[] listFiles) throws Exception {
        byte[] buffer;
        ByteArrayOutputStream outputStream = null;
        ZipOutputStream zipOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            zipOutputStream = new ZipOutputStream(outputStream);
            for (File file : listFiles) {
                buffer = new byte[(int) file.length()];
                fileInputStream = new FileInputStream(file);
                fileInputStream.read(buffer, 0, (int) file.length());
                ZipEntry ze = new ZipEntry(file.getName());

                zipOutputStream.putNextEntry(ze);
                zipOutputStream.write(buffer);
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (zipOutputStream != null) {
                zipOutputStream.finish();
                zipOutputStream.close();
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
        return outputStream;
    }
    
}
