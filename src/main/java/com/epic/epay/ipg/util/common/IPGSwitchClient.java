/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.common;

import com.epic.epay.ipg.dao.common.CommonDAO;
import com.epic.epay.ipg.util.mapping.Ipgcommonconfig;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

/**
 *
 * @author badrika
 */
public class IPGSwitchClient {

    private Socket client;
    private BufferedReader br;
    private PrintWriter pw;
    private List lst = null;
    private CommonDAO commondao = null;
    private Ipgcommonconfig comondata = null;

    public IPGSwitchClient() throws Exception {

        //read xml file & get txn switch configuration
//        lst = XMLReader.getTxnSwitchConfigurations(realPath);
        commondao = new CommonDAO();
        String switchport = commondao.getCommonConfigValueByCode(CommonVarList.COMMON_CONFIG_SWITCHPORT).getValue();
        String switchIP = commondao.getCommonConfigValueByCode(CommonVarList.COMMON_CONFIG_SWITCHIP).getValue();
        InetAddress inetAdd = InetAddress.getByName(switchIP); //ip
        SocketAddress serverAdd = new InetSocketAddress(inetAdd, Integer.parseInt(switchport)); //port

        client = new Socket();
        client.connect(serverAdd);

        br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        pw = new PrintWriter(client.getOutputStream());
    }

    public void sendPacket(String request) throws Exception {
        pw.println(request);
        pw.flush();
    }

    public String receivePacket() throws Exception {
        String response;

        response = br.readLine(); // read the response from the socket

        return response;
    }

    public void closeAll() throws Exception {
        if (client != null) {
            client.close();
        }
        if (br != null) {
            br.close();
        }
        if (pw != null) {
            pw.close();
        }
        client = null;
        br = null;
        pw = null;
    }
}
