/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.dao.logmanagement;


import com.epic.epay.ipg.bean.logmanagement.LogFile;
import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.mapping.Ipgcommonfilepath;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ruwan_e
 */
public class LogFileDAO {

    private static String LOG_FILE_DIR;
    private Ipgcommonfilepath filepaths;
    private CommonDAO commonDAO;

    public LogFileDAO() throws Exception {
//        String windowsPath = "C:\\epaympi\\Logs";
//        String linuxPath = "/root/epaympi/Logs";
//        String sunPath = "/epic/epaympi/Logs";//        

        String osType = getOS_Type();
        commonDAO = new CommonDAO();
        try {
            filepaths = commonDAO.getCommonFilePathBySystemOSType(osType);
        } catch (Exception ex) {
            Logger.getLogger(LogFileDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
        File dir = null;

        LOG_FILE_DIR = filepaths.getAdminerrorlog();
        dir = new File(LOG_FILE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

    }
//    private FileTreeModel fileTree;

    public List<String> getAllCategories() {
        File file = new File(LOG_FILE_DIR + File.separator + "info");

        File dir = new File(LOG_FILE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return dir.isDirectory();
            }
        });
        return Arrays.asList(directories);
    }

    public void visitAllDirsAndFiles(File dir) {
        process(dir);

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                visitAllDirsAndFiles(new File(dir, children[i]));
            }
        }
    }

    private static void process(File dir) {
        dir.getAbsolutePath();
    }

    List<String> getAllTypes() {
        File file = new File(LOG_FILE_DIR);

        if (!file.exists()) {
            file.mkdirs();
        }

        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return dir.isDirectory();
            }
        });
        return Arrays.asList(directories);

    }

    public static List<LogFile> getLogFiles(String type, String category) throws IOException {
        return getLogFiles(new File(LOG_FILE_DIR + File.separator + type + File.separator + category));

    }

    public static List<LogFile> getAllLogFiles() throws IOException {
        return getLogFiles(new File(LOG_FILE_DIR));
    }

    private static List<LogFile> getLogFiles(File directory) throws IOException {
        final List<LogFile> logFiles = new ArrayList<LogFile>();
        new FileTraversal() {
            @Override
            public void onFile(final File f) {
                logFiles.add(new LogFile(f));
            }
        }.traverse(directory);

        return logFiles;
    }

    List<String> getCategoriesByType(String type) {
        File file = new File(LOG_FILE_DIR + File.separator + type);

        File dir = new File(LOG_FILE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return dir.isDirectory();
            }
        });
        return Arrays.asList(directories);
    }

    private static String getOS_Type() {

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
}
