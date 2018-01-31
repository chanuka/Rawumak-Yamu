/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.bean.controlpanel.systemconfig;

import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Termadult;
import com.rawmakyamu.util.mapping.Termteen;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jayana_i
 */
public class TermsInputBean {

    private String status;
    private String statusadd;
    private String statusAct;
    private String termsid;
    private String termsidadd;
    private String currentSeries;
    private String logoutTime;
    private String maxAttempt;

    private String description;
    private String descriptionadd;
    private String versionno;
    private String versionnoadd;
    private String category;
    private String categoryadd;
    

    private String defaultStatus;
    private List<Status> StatusList;
    private List<TermtypeBean> TermtypeList;
    public List<TermsVersionBean> versionList;
    private List<TermsVersionBean> versionMap = new ArrayList<TermsVersionBean>();
    private List<Termadult> versionadultList;
    private List<Termteen> versionteenList;
    private List<CategoryBean> categoryList;

    private String oldvalue;
    private String newvalue;
    private String message;

    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupload;
    private boolean vdownload;
    private boolean vupdatebutt;
    private boolean vupdatelink;
    private boolean vsearch;
    private boolean listnerconfigid;

    private boolean vdelete;
    /*------------------------list data table  ------------------------------*/
    private List<TermsBean> gridModel;
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
    
    public String getTermsidadd() {
        return termsidadd;
    }

    public void setTermsidadd(String termsidadd) {
        this.termsidadd = termsidadd;
    }

    public String getDescriptionadd() {
        return descriptionadd;
    }

    public void setDescriptionadd(String descriptionadd) {
        this.descriptionadd = descriptionadd;
    }

    public String getVersionnoadd() {
        return versionnoadd;
    }

    public void setVersionnoadd(String versionnoadd) {
        this.versionnoadd = versionnoadd;
    }

    
    
    public List<TermtypeBean> getTermtypeList() {
        return TermtypeList;
    }

    public void setTermtypeList(List<TermtypeBean> TermtypeList) {
        this.TermtypeList = TermtypeList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryadd() {
        return categoryadd;
    }

    public void setCategoryadd(String categoryadd) {
        this.categoryadd = categoryadd;
    }

    public List<CategoryBean> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryBean> categoryList) {
        this.categoryList = categoryList;
    }

    

    public String getTermsid() {
        return termsid;
    }

    public void setTermsid(String termsid) {
        this.termsid = termsid;
    }

    public String getCurrentSeries() {
        return currentSeries;
    }

    public void setCurrentSeries(String currentSeries) {
        this.currentSeries = currentSeries;
    }

    public String getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getMaxAttempt() {
        return maxAttempt;
    }

    public void setMaxAttempt(String maxAttempt) {
        this.maxAttempt = maxAttempt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public List<Status> getStatusList() {
        return StatusList;
    }

    public void setStatusList(List<Status> StatusList) {
        this.StatusList = StatusList;
    }

    public String getOldvalue() {
        return oldvalue;
    }

    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
    }

    public String getNewvalue() {
        return newvalue;
    }

    public void setNewvalue(String newvalue) {
        this.newvalue = newvalue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isVadd() {
        return vadd;
    }

    public void setVadd(boolean vadd) {
        this.vadd = vadd;
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

    public boolean isVsearch() {
        return vsearch;
    }

    public void setVsearch(boolean vsearch) {
        this.vsearch = vsearch;
    }

    public boolean isListnerconfigid() {
        return listnerconfigid;
    }

    public void setListnerconfigid(boolean listnerconfigid) {
        this.listnerconfigid = listnerconfigid;
    }

    public boolean isVdelete() {
        return vdelete;
    }

    public void setVdelete(boolean vdelete) {
        this.vdelete = vdelete;
    }

    public List<TermsBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<TermsBean> gridModel) {
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

    public List<TermsVersionBean> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<TermsVersionBean> versionList) {
        this.versionList = versionList;
    }

    public List<Termadult> getVersionadultList() {
        return versionadultList;
    }

    public void setVersionadultList(List<Termadult> versionadultList) {
        this.versionadultList = versionadultList;
    }

    public List<Termteen> getVersionteenList() {
        return versionteenList;
    }

    public void setVersionteenList(List<Termteen> versionteenList) {
        this.versionteenList = versionteenList;
    }

    public String getVersionno() {
        return versionno;
    }

    public void setVersionno(String versionno) {
        this.versionno = versionno;
    }

    public List<TermsVersionBean> getVersionMap() {
        return versionMap;
    }

    public void setVersionMap(List<TermsVersionBean> versionMap) {
        this.versionMap = versionMap;
    }

    public String getStatusAct() {
        return statusAct;
    }

    public void setStatusAct(String statusAct) {
        this.statusAct = statusAct;
    }

    public String getStatusadd() {
        return statusadd;
    }

    public void setStatusadd(String statusadd) {
        this.statusadd = statusadd;
    }

}
