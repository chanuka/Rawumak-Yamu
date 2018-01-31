/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.bean.controlpanel.systemconfig;

import com.rawmakyamu.util.mapping.Loginhistory;
import com.rawmakyamu.util.mapping.Logintype;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemuser;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jayana_i
 */
public class LoginHistoryInputBean implements Serializable {

    private String historyid;
    private String walletid;
    private String pushid;
    private String sessionkey;
    private String xcoordinate;
    private String ycoordinate;
    private String loggedtime;
    private String status;
    private String logintype;

    private String mobilenumber;
//    private String customerid;
    private String nic;
    private String task;
    private String fromdate;
    private String todate;

    private LoginHistoryBean auditDataBean;
    /////
    private String reporttype;
    private ByteArrayInputStream excelStream;
    private ByteArrayInputStream zipStream;
    //////
    private boolean vadd;
    private boolean vupdatebutt;
    private boolean vsearch;
    private boolean vgenerate;

    private List<Systemuser> userList;
    private List<Loginhistory> loginhistoryList;
    private List<Status> StatusList;
    private List<Logintype> logintypeList;
//    private HashMap<String, String> taskList;

    private List<LoginHistoryTaskBean> taskList;
    private List<LoginHistoryBean> gridModel;
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;
    private boolean loadonce = false;

    public String getLogintype() {
        return logintype;
    }

    public void setLogintype(String logintype) {
        this.logintype = logintype;
    }

    public List<Logintype> getLogintypeList() {
        return logintypeList;
    }

    public void setLogintypeList(List<Logintype> logintypeList) {
        this.logintypeList = logintypeList;
    }

    public String getReporttype() {
        return reporttype;
    }

    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }

    public ByteArrayInputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(ByteArrayInputStream excelStream) {
        this.excelStream = excelStream;
    }

    public ByteArrayInputStream getZipStream() {
        return zipStream;
    }

    public void setZipStream(ByteArrayInputStream zipStream) {
        this.zipStream = zipStream;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

//    public String getCustomerid() {
//        return customerid;
//    }
//
//    public void setCustomerid(String customerid) {
//        this.customerid = customerid;
//    }
    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    private long fullCount;
    private Date createDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isVsearch() {
        return vsearch;
    }

    public void setVsearch(boolean vsearch) {
        this.vsearch = vsearch;
    }

    public boolean isVviewlink() {
        return vviewlink;
    }

    public void setVviewlink(boolean vviewlink) {
        this.vviewlink = vviewlink;
    }
    private boolean vviewlink;

    public List<Loginhistory> getLoginhistoryList() {
        return loginhistoryList;
    }

    public void setLoginhistoryList(List<Loginhistory> loginhistoryList) {
        this.loginhistoryList = loginhistoryList;
    }

    public List<Status> getStatusList() {
        return StatusList;
    }

    public void setStatusList(List<Status> StatusList) {
        this.StatusList = StatusList;
    }

    private boolean search;

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public List<Systemuser> getUserList() {
        return userList;
    }

    public void setUserList(List<Systemuser> userList) {
        this.userList = userList;
    }

    public String getHistoryid() {
        return historyid;
    }

    public void setHistoryid(String historyid) {
        this.historyid = historyid;
    }

    public String getWalletid() {
        return walletid;
    }

    public void setWalletid(String walletid) {
        this.walletid = walletid;
    }

    public String getPushid() {
        return pushid;
    }

    public void setPushid(String pushid) {
        this.pushid = pushid;
    }

    public String getSessionkey() {
        return sessionkey;
    }

    public void setSessionkey(String sessionkey) {
        this.sessionkey = sessionkey;
    }

    public String getXcoordinate() {
        return xcoordinate;
    }

    public void setXcoordinate(String xcoordinate) {
        this.xcoordinate = xcoordinate;
    }

    public String getYcoordinate() {
        return ycoordinate;
    }

    public void setYcoordinate(String ycoordinate) {
        this.ycoordinate = ycoordinate;
    }

    public String getLoggedtime() {
        return loggedtime;
    }

    public void setLoggedtime(String loggedtime) {
        this.loggedtime = loggedtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoginHistoryBean getAuditDataBean() {
        return auditDataBean;
    }

    public void setAuditDataBean(LoginHistoryBean auditDataBean) {
        this.auditDataBean = auditDataBean;
    }

    public boolean isVadd() {
        return vadd;
    }

    public void setVadd(boolean vadd) {
        this.vadd = vadd;
    }

    public boolean isVupdatebutt() {
        return vupdatebutt;
    }

    public void setVupdatebutt(boolean vupdatebutt) {
        this.vupdatebutt = vupdatebutt;
    }

    public List<LoginHistoryBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<LoginHistoryBean> gridModel) {
        this.gridModel = gridModel;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchOper() {
        return searchOper;
    }

    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }

    public boolean isLoadonce() {
        return loadonce;
    }

    public void setLoadonce(boolean loadonce) {
        this.loadonce = loadonce;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public List<LoginHistoryTaskBean> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<LoginHistoryTaskBean> taskList) {
        this.taskList = taskList;
    }

    

    public boolean isVgenerate() {
        return vgenerate;
    }

    public void setVgenerate(boolean vgenerate) {
        this.vgenerate = vgenerate;
    }

}
