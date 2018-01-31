/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rawmakyamu.bean.reportexplorer;

/**
 *
 * @author thushanth
 */
public class TxnHisDataBean {
    
    private String transactionhistoryid;
    private String transactionid;
    private String statuscode;    
    private String lastupdateduser;    
    private String createdtime;
//    private String transactioncode;   
    private long fullCount;

    public String getTransactionhistoryid() {
        return transactionhistoryid;
    }

    public void setTransactionhistoryid(String transactionhistoryid) {
        this.transactionhistoryid = transactionhistoryid;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getLastupdateduser() {
        return lastupdateduser;
    }

    public void setLastupdateduser(String lastupdateduser) {
        this.lastupdateduser = lastupdateduser;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }
    
    

    
}
