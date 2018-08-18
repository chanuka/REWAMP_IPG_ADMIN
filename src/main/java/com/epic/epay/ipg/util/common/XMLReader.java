/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author badrika
 */
public class XMLReader {
    
     //read xml file & get txn switch configuration
    public static List getTxnSwitchConfigurations(String fullPath) {
        //to store ip, port
        String ip = null;
        String port = null;
        List lst = new ArrayList();

        try {
            File file = new File(fullPath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("configurations");

            for (int s = 0; s < nodeLst.getLength(); s++) {

                Node fstNode = nodeLst.item(s);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element fstElmnt = (Element) fstNode;

                    NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("ip");
                    Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                    NodeList fstNm = fstNmElmnt.getChildNodes();

                    NodeList secNmElmntLst = fstElmnt.getElementsByTagName("port");
                    Element secNmElmnt = (Element) secNmElmntLst.item(0);
                    NodeList secNm = secNmElmnt.getChildNodes();

                    //set found data to the url
                    ip = ((Node) fstNm.item(0)).getNodeValue().toString();
                    port = ((Node) secNm.item(0)).getNodeValue().toString();

                    lst.add(ip);
                    lst.add(port);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lst;
    }
    
    //read xml file & get SwitchRequestLog configuration
    public static List getSwitchRequestLogConfigurations(String realPathSwitchRequest) {
        //to store url
        String url = null;
        List lst = new ArrayList();

        try {
            File file = new File(realPathSwitchRequest);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("configurations");

            for (int s = 0; s < nodeLst.getLength(); s++) {

                Node fstNode = nodeLst.item(s);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element fstElmnt = (Element) fstNode;

                    NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("primaryurl");
                    Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                    NodeList fstNm = fstNmElmnt.getChildNodes();

                    //set found data to the url
                    url = ((Node) fstNm.item(0)).getNodeValue().toString();


                    lst.add(url);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lst;
    }
    
    //read xml file & get SwitchResponseLog configuration
    public static List getSwitchResponseLogConfigurations(String realPathSwitchResponse) {
        //to store url
        String url = null;
        List lst = new ArrayList();

        try {
            File file = new File(realPathSwitchResponse);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("configurations");

            for (int s = 0; s < nodeLst.getLength(); s++) {

                Node fstNode = nodeLst.item(s);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element fstElmnt = (Element) fstNode;

                    NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("primaryurl");
                    Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                    NodeList fstNm = fstNmElmnt.getChildNodes();

                    //set found data to the url
                    url = ((Node) fstNm.item(0)).getNodeValue().toString();


                    lst.add(url);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lst;
    }
    
}
