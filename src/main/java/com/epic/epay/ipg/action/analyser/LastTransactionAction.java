/**
 * 
 */
package com.epic.epay.ipg.action.analyser;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.epic.epay.ipg.bean.analyser.LastTransactionInputBean;
import com.epic.epay.ipg.bean.analyser.TransactionHistoryBean;
import com.epic.epay.ipg.dao.analyser.LastTransactionDAO;
import com.epic.epay.ipg.util.common.AccessControlService;
import com.epic.epay.ipg.util.common.Common;
import com.epic.epay.ipg.util.mapping.Ipgtask;
import com.epic.epay.ipg.util.mapping.Ipgtransactionhistory;
import com.epic.epay.ipg.util.varlist.CommonVarList;
import com.epic.epay.ipg.util.varlist.MessageVarList;
import com.epic.epay.ipg.util.varlist.PageVarList;
import com.epic.epay.ipg.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @created on 	Dec 5, 2013, 1:23:19 PM
 * @author 		thushanth
 *
 */
public class LastTransactionAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {
	
	private LastTransactionInputBean txnInputBean = new LastTransactionInputBean();
	private List<TransactionHistoryBean> hisBeanList= new ArrayList<TransactionHistoryBean>();
	
    public Object getModel() {
        return txnInputBean;
    }

    public String execute() throws Exception {
        System.out.println("called LastTransactionAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.ANALYSER_LAST_TRANSACTION;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
       } else if ("find".equals(method)) {
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
    
    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Ipgtask> tasklist = new Common().getUserTaskListByPage(PageVarList.MERCHANT_CREDENTIAL, request);
        
        if (tasklist != null && tasklist.size() > 0) {
            for (Ipgtask task : tasklist) {
            	if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
            		
                }
            }
        }
        return true;
    }
         	
     

    public String view() {
    	
        String result = "view";
        System.out.println("called LastTransactionAction :view");
  
        try {
            if (this.applyUserPrivileges()) {
            	
            	
            }else {
                result = "loginpage";
            }
        }        
        catch (Exception ex) {
            addActionError("LastTransactionAction: " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(LastTransactionAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    
    
    public String find(){
    	 System.out.println("called LastTransactionAction :find");
    	 
    	long timediff =0;
    	int stage= 0;
    	List<Long> timediffList =new ArrayList<Long>();
    	List<Integer> stageList =new ArrayList<Integer>();
    	String [] statusOrder ={CommonVarList.FIRST_TXN_STATUS, CommonVarList.SECOND_TXN_STATUS, CommonVarList.THIRD_TXN_STATUS, CommonVarList.FOURTH_TXN_STATUS, CommonVarList.FIFTH_TXN_STATUS, CommonVarList.SIXTH_TXN_STATUS, CommonVarList.SEVENTH_TXN_STATUS, CommonVarList.EIGHTH_TXN_STATUS, CommonVarList.NINETH_TXN_STATUS, CommonVarList.TENTH_TXN_STATUS, CommonVarList.ELEVENTH_TXN_STATUS, CommonVarList.TWELVETH_COMPLETE_TXN_STATUS};
    	
    	try {
        	
        	LastTransactionDAO dao = new LastTransactionDAO();
        	txnInputBean.setTxnid(dao.getLatestTxn());
                
                if(txnInputBean.getTxnid().isEmpty()){
                    txnInputBean.setMsg("NO DATA.");
        		return "find";
                }
        	List<Ipgtransactionhistory> hisList = dao.getLastTxnHis(txnInputBean.getTxnid());
        	
        	if(hisList.isEmpty()){
        		txnInputBean.setMsg("NO DATA.");
        		return "find";
        	}
        	
        	for (Ipgtransactionhistory mr : hisList) {
        		TransactionHistoryBean historyBean = new TransactionHistoryBean();
        		historyBean.setTransactionHisId(mr.getIpgtransactionhistoryid().toString()); 
        		historyBean.setStatus(mr.getIpgstatus().getStatuscode());
        		historyBean.setAnalytime(Timestamp.valueOf(mr.getAnalytime()));
        		hisBeanList.add(historyBean);
        	} 
                
               List<String> dan = Arrays.asList(statusOrder);

            int statusCnt = dan.indexOf(hisBeanList.get(0).getStatus());
    	
    	for(int i=0; i< hisBeanList.size()-1; i++){    		
    		
    		if((statusOrder[statusCnt].equals(hisBeanList.get(i).getStatus()) &&  statusOrder[statusCnt+1].equals(hisBeanList.get(i+1).getStatus()))){    			
    			if(hisBeanList.get(i+1).getAnalytime().compareTo(hisBeanList.get(i).getAnalytime()) > 0){    			
    				timediff = (hisBeanList.get(i+1).getAnalytime().getTime()) - (hisBeanList.get(i).getAnalytime().getTime());
    				stage = statusCnt+1;
    			}  		
    		
    		else if(hisBeanList.get(i+1).getAnalytime().compareTo(hisBeanList.get(i).getAnalytime()) == 0){    			
    			timediff=0;
    			stage = statusCnt+1;    			
    		}else{   			
    			timediff=0;
    			stage = statusCnt+1;
    			txnInputBean.setMsg(txnInputBean.getMsg()+"<br>"+MessageVarList.ANALYZER_LAST_TRANSACTION_ERROR+" stage "+stage+".");    			
    		}    			
    		}else{
    			timediff=0;
    			stage = statusCnt+1;
    			if(!statusOrder[statusCnt].equals(hisBeanList.get(i).getStatus())){
    			txnInputBean.setMsg(txnInputBean.getMsg()+"<br> Can not calculate stage "+ stage+"; &nbsp;'"+statusOrder[statusCnt] +"' not found in stage "+ stage+".");
                        --i;
    			}if(!statusOrder[statusCnt+1].equals(hisBeanList.get(i+1).getStatus())){
    				txnInputBean.setMsg(txnInputBean.getMsg()+"<br> Can not calculate stage "+ stage+"; &nbsp;'"+statusOrder[statusCnt+1] +"' not found in stage "+ stage+".");
    			}
    		}    		
    		timediffList.add(timediff);
    		stageList.add(stage);
                statusCnt++;
    	}
    	
    	txnInputBean.setTimeDiffList(timediffList);
    	txnInputBean.setStageList(stageList);
    	
    	}catch (IllegalArgumentException e) {
             txnInputBean.setMsg("LastTransactionAction: " + MessageVarList.ANALYZER_LAST_TRANSACTION_ERROR_ILLEGAL);
             Logger.getLogger(LastTransactionAction.class.getName()).log(Level.SEVERE, null, e);
         }
         
         catch (NullPointerException ex) {
             txnInputBean.setMsg("LastTransactionAction: " + MessageVarList.ANALYZER_LAST_TRANSACTION_NULL_ERROR);
             Logger.getLogger(LastTransactionAction.class.getName()).log(Level.SEVERE, null, ex);
         }
          catch (Exception ex) {
             txnInputBean.setMsg("LastTransactionAction: " + MessageVarList.COMMON_ERROR_PROCESS);
             Logger.getLogger(LastTransactionAction.class.getName()).log(Level.SEVERE, null, ex);
         }
    	return "find";    	
    }
}
