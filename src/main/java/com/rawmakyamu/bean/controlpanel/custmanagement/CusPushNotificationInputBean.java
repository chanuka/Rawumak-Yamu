/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.bean.controlpanel.custmanagement;

import com.rawmakyamu.util.mapping.Status;
import java.util.List;

/**
 *
 * @author prathibha_s
 */
public class CusPushNotificationInputBean {

    private String id;
    private String walletId;
    private String status;
    private String createTime;
    private String lastUpdatedTime;
    private String fileName;
    private String message;
    private String messages;
    private String hiddenId;

    //------file side------''
    private String fileId;
    private String fileNamef;
    private String statusFile;

    /*-------for access control-----------*/
    private boolean vupload;
    private boolean vdownload;
    private boolean vsearch;
    private boolean vview;
    private boolean vsend;
    /*------------------------list data table  ------------------------------*/
    private List<CusPushNotificationDataBean> gridModel;
    private List<CusPushNotificationFileDataBean> gridFileModel;
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

    private List<Status> statusList;
    private List<Status> statusFileList;

    private String SearchAudit;
    private boolean search;

    public String getId() {
        return id;
    }

    public String getFileId() {
        return fileId;
    }

    public boolean isVsend() {
        return vsend;
    }

    public void setVsend(boolean vsend) {
        this.vsend = vsend;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getFileNamef() {
        return fileNamef;
    }

    public void setFileNamef(String fileNamef) {
        this.fileNamef = fileNamef;
    }

    public String getStatusFile() {
        return statusFile;
    }

    public void setStatusFile(String statusFile) {
        this.statusFile = statusFile;
    }

    public List<Status> getStatusFileList() {
        return statusFileList;
    }

    public void setStatusFileList(List<Status> statusFileList) {
        this.statusFileList = statusFileList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHiddenId() {
        return hiddenId;
    }

    public void setHiddenId(String hiddenId) {
        this.hiddenId = hiddenId;
    }

    public boolean isVupload() {
        return vupload;
    }

    public void setVupload(boolean vupload) {
        this.vupload = vupload;
    }

    public boolean isVdownload() {
        return vdownload;
    }

    public void setVdownload(boolean vdownload) {
        this.vdownload = vdownload;
    }

    public boolean isVsearch() {
        return vsearch;
    }

    public boolean isVview() {
        return vview;
    }

    public void setVview(boolean vview) {
        this.vview = vview;
    }

    public void setVsearch(boolean vsearch) {
        this.vsearch = vsearch;
    }

    public List<CusPushNotificationDataBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<CusPushNotificationDataBean> gridModel) {
        this.gridModel = gridModel;
    }

    public List<CusPushNotificationFileDataBean> getGridFileModel() {
        return gridFileModel;
    }

    public void setGridFileModel(List<CusPushNotificationFileDataBean> gridFileModel) {
        this.gridFileModel = gridFileModel;
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

    public String getSearchAudit() {
        return SearchAudit;
    }

    public void setSearchAudit(String SearchAudit) {
        this.SearchAudit = SearchAudit;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

}
