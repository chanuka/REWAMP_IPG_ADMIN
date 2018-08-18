/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.common;

import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.date.SystemDateTime;
import com.epic.epay.ipg.util.mapping.Ipgcommonfilepath;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author badrika
 */
public class LogFileCreator {

    private static CommonDAO commondao = null;
    private static Ipgcommonfilepath comonpath = null;
    private static String os_name;
    private static String realPathSwitchRequest;
    private static String realPathSwitchResponse;

    //write info log. for write switch request sent to switch by ipg
    public static void writInforTologsForSwitchRequest(String msg) {
        BufferedWriter bw = null;
        FileWriter fw = null;
//        List lst = null;
//        String url = null;

        try {
            
            os_name = Common.getOS_Type();
            commondao = new CommonDAO();
            comonpath = commondao.getCommonFilePathBySystemOSType(os_name);
            realPathSwitchRequest = comonpath.getSwitchrequest();

            //read xml file & get SwitchRequestLog configuration
//            lst = XMLReader.getSwitchRequestLogConfigurations(realPathSwitchRequest);
            //set the element fron list to url
//            url = lst.get(0).toString();
            String line = "\nINFOR.\n";
            String filename = getFileName() + "_EPAY_IPG_SWITCH_REQUEST";

            filename = realPathSwitchRequest + filename;
            File file = new File(realPathSwitchRequest);
            if (!file.exists()) {
                file.mkdirs();
            }

            msg = line + getTime() + "    " + msg;

            fw = new FileWriter(filename, true);
            bw = new BufferedWriter(fw);
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            Logger.getLogger(LogFileCreator.class.getName()).log(Level.SEVERE, null, ioe);
        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                }
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
                bw = null;
                fw = null;

            } catch (Exception ioe2) {
            }
        }
    }

    //write info log. for write switch response sent to ipg by switch
    public static void writInforTologsForSwitchResponse(String msg) {
        BufferedWriter bw = null;
        FileWriter fw = null;
//        List lst = null;
//        String resUrl = null;

        try {
            os_name = Common.getOS_Type();
            commondao = new CommonDAO();
            comonpath = commondao.getCommonFilePathBySystemOSType(os_name);
            realPathSwitchResponse = comonpath.getSwitchresponse();
            //read xml file & get SwitchResponseLog configuration
//            lst = XMLReader.getSwitchResponseLogConfigurations(realPathSwitchResponse);

            //set the element fron list to url
//            resUrl = lst.get(0).toString();
            String line = "\nINFOR.\n";
            String filename = getFileName() + "_EPAY_IPG_SWITCH_RESPONSE";

            filename = realPathSwitchResponse + filename;
            File file = new File(realPathSwitchResponse);
            if (!file.exists()) {
                file.mkdirs();
            }

            msg = line + getTime() + "    " + msg;

            fw = new FileWriter(filename, true);
            bw = new BufferedWriter(fw);
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            Logger.getLogger(LogFileCreator.class.getName()).log(Level.SEVERE, null, ioe);
        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                }
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
                bw = null;
                fw = null;

            } catch (Exception ioe2) {
            }
        }
    }

    private static String getFileName() throws Exception {

        Timestamp sysDate = null;
        sysDate = SystemDateTime.getSystemDataAndTime();

        int currentYear = sysDate.getYear() + 1900;
        int currentMonth = sysDate.getMonth() + 1;
        int currentDay = sysDate.getDate();

        return "" + currentYear + currentMonth + currentDay + "";

    }

    private static String getTime() throws Exception {
        return "" + SystemDateTime.getSystemDataAndTime() + "";
    }

}
