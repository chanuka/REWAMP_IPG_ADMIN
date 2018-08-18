/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.logmanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ruwan_e
 */
public class LogFile {

    private String logFileCategory;
    private LogFileType type; //ERROR or INFO
    private File logFile;
    private String path;
    private String typeDes;

    public String getTypeDes() {
        return typeDes;
    }

    public void setTypeDes(String typeDes) {
        this.typeDes = typeDes;
    }

    public String getLogFileCategory() {
        return logFileCategory;
    }

    public void setLogFileCategory(String logFileCategory) {
        this.logFileCategory = logFileCategory;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
    
    //for JSON
    private long length;
    private String name;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LogFile() { }

    public LogFile(File file) {
        this.logFile = file;
        this.logFileCategory = file.getParentFile().getName();
        this.type = LogFileType.valueOf(file.getParentFile().getParentFile().getName().toUpperCase());
        this.length = file.length();
        this.name= file.getName();
        this.date= strDate(new Date(file.lastModified()));
        initFileContent();
    }
    
    public String strDate(Date date){
    
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = df.format(date);
        return strDate;
    }
    
    public LogFile(String pathname) {
        this(new File(pathname));
    }

    public File getLogFile() {
        return logFile;
    }

    public void setLogFile(File logFile) {
        this.logFile = logFile;
    }


//    public String getPath() {
//        return logFile.getAbsolutePath();
//    }

    public LogFileType getType() {
        return type;
    }

    public void setType(LogFileType type) {
        this.type = type;
    }
    
    
    public long getLength() {
        return length;
    }   
    
    private String content;

    public void setContent(String content) {
        this.content = content;
    }  
    
    public String getContent() {              
        return content;
    }
    
    private void initFileContent(){
        BufferedReader br = null;
        String c = "";
        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(logFile.getAbsolutePath()));

            while ((sCurrentLine = br.readLine()) != null) {
                c = c + sCurrentLine + '\n';
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        content = c;
    }
}
