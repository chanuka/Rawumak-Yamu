/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.bean.controlpanel.systemconfig;

import com.rawmakyamu.util.mapping.Status;
import java.util.List;

/**
 *
 * @author chathuri_t
 */
public class TransactionTypeInputBean {

    private String transactiontypecode;
    private String description;
    private String status;
    private String OTPReqStatus;
    private String RiskReqStatus;
    private String merchantTxnType;
    private String description_receiver;
    private String history_visibility;
    private String sortOrder;
    private List<Status> statusList;
    private List<Status> OTPReqstatusList;
    private List<Status> history_visibilityList;
    private List<Status> RiskReqstatusList;
    private String defaultStatus;
    private String defaultOTPReqStatus;
    private String defaultRiskReqStatus;
    private String message;
    private String newvalue;
    private String oldvalue;
    private String oldsortKey;

    /*------ for search ----------*/
    private String s_transactiontypecode;
    private String s_description;
    private String s_status;
    private String s_OTPReqStatus;
    private String s_RiskReqStatus;
    private String s_merchantTxnType;
    private String s_description_receiver;
    private String s_history_visibility;
    private String s_sortOrder;

    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdatebutt;
    private boolean vupdatelink;
    private boolean vdelete;
    private boolean vsearch;

    /*------------------------list data table  ------------------------------*/
    private List<TransactionTypeBean> gridModel;
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

    private String SearchAudit;
    private boolean search;

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    /*------------------------list data table  ------------------------------*/
    public List<Status> getRiskReqstatusList() {
        return RiskReqstatusList;
    }

    public void setRiskReqstatusList(List<Status> RiskReqstatusList) {
        this.RiskReqstatusList = RiskReqstatusList;
    }

    public String getRiskReqStatus() {
        return RiskReqStatus;
    }

    public void setRiskReqStatus(String RiskReqStatus) {
        this.RiskReqStatus = RiskReqStatus;
    }

    public String getDefaultRiskReqStatus() {
        return defaultRiskReqStatus;
    }

    public void setDefaultRiskReqStatus(String defaultRiskReqStatus) {
        this.defaultRiskReqStatus = defaultRiskReqStatus;
    }

    public String getTransactiontypecode() {
        return transactiontypecode;
    }

    public void setTransactiontypecode(String transactiontypecode) {
        this.transactiontypecode = transactiontypecode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOTPReqStatus() {
        return OTPReqStatus;
    }

    public void setOTPReqStatus(String OTPReqStatus) {
        this.OTPReqStatus = OTPReqStatus;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public List<Status> getOTPReqstatusList() {
        return OTPReqstatusList;
    }

    public void setOTPReqstatusList(List<Status> OTPReqstatusList) {
        this.OTPReqstatusList = OTPReqstatusList;
    }

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public String getDefaultOTPReqStatus() {
        return defaultOTPReqStatus;
    }

    public void setDefaultOTPReqStatus(String defaultOTPReqStatus) {
        this.defaultOTPReqStatus = defaultOTPReqStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNewvalue() {
        return newvalue;
    }

    public void setNewvalue(String newvalue) {
        this.newvalue = newvalue;
    }

    public String getOldvalue() {
        return oldvalue;
    }

    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
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

    public boolean isVupdatelink() {
        return vupdatelink;
    }

    public void setVupdatelink(boolean vupdatelink) {
        this.vupdatelink = vupdatelink;
    }

    public boolean isVdelete() {
        return vdelete;
    }

    public void setVdelete(boolean vdelete) {
        this.vdelete = vdelete;
    }

    public List<TransactionTypeBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<TransactionTypeBean> gridModel) {
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

    public String getS_transactiontypecode() {
        return s_transactiontypecode;
    }

    public void setS_transactiontypecode(String s_transactiontypecode) {
        this.s_transactiontypecode = s_transactiontypecode;
    }

    public String getS_description() {
        return s_description;
    }

    public void setS_description(String s_description) {
        this.s_description = s_description;
    }

    public String getS_status() {
        return s_status;
    }

    public void setS_status(String s_status) {
        this.s_status = s_status;
    }

    public String getS_OTPReqStatus() {
        return s_OTPReqStatus;
    }

    public void setS_OTPReqStatus(String s_OTPReqStatus) {
        this.s_OTPReqStatus = s_OTPReqStatus;
    }

    public String getS_RiskReqStatus() {
        return s_RiskReqStatus;
    }

    public void setS_RiskReqStatus(String s_RiskReqStatus) {
        this.s_RiskReqStatus = s_RiskReqStatus;
    }

    public boolean isVsearch() {
        return vsearch;
    }

    public void setVsearch(boolean vsearch) {
        this.vsearch = vsearch;
    }

    public String getSearchAudit() {
        return SearchAudit;
    }

    public void setSearchAudit(String SearchAudit) {
        this.SearchAudit = SearchAudit;
    }

    public String getMerchantTxnType() {
        return merchantTxnType;
    }

    public void setMerchantTxnType(String merchantTxnType) {
        this.merchantTxnType = merchantTxnType;
    }

    public String getS_merchantTxnType() {
        return s_merchantTxnType;
    }

    public void setS_merchantTxnType(String s_merchantTxnType) {
        this.s_merchantTxnType = s_merchantTxnType;
    }

    public List<Status> getHistory_visibilityList() {
        return history_visibilityList;
    }

    public void setHistory_visibilityList(List<Status> history_visibilityList) {
        this.history_visibilityList = history_visibilityList;
    }

    public String getDescription_receiver() {
        return description_receiver;
    }

    public void setDescription_receiver(String description_receiver) {
        this.description_receiver = description_receiver;
    }

    public String getHistory_visibility() {
        return history_visibility;
    }

    public void setHistory_visibility(String history_visibility) {
        this.history_visibility = history_visibility;
    }

    public String getS_description_receiver() {
        return s_description_receiver;
    }

    public void setS_description_receiver(String s_description_receiver) {
        this.s_description_receiver = s_description_receiver;
    }

    public String getS_history_visibility() {
        return s_history_visibility;
    }

    public void setS_history_visibility(String s_history_visibility) {
        this.s_history_visibility = s_history_visibility;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getOldsortKey() {
        return oldsortKey;
    }

    public void setOldsortKey(String oldsortKey) {
        this.oldsortKey = oldsortKey;
    }

    public String getS_sortOrder() {
        return s_sortOrder;
    }

    public void setS_sortOrder(String s_sortOrder) {
        this.s_sortOrder = s_sortOrder;
    }

}
