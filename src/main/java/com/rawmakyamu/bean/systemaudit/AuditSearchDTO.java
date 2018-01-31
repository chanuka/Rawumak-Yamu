/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rawmakyamu.bean.systemaudit;

import com.rawmakyamu.util.mapping.Page;
import com.rawmakyamu.util.mapping.Section;
import com.rawmakyamu.util.mapping.Systemuser;
import com.rawmakyamu.util.mapping.Task;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 *
 * @author thushanth
 */
public class AuditSearchDTO {
    
     /*-------for access control-----------*/
    private boolean vsearch;
    private boolean vgenerate;
    private boolean vgenerateview;
    private boolean vviewlink;
    private List<Systemuser> userList;
    private List<Section> sectionList;
    private List<Page> pageList;
    private List<Task> taskList;
    
    private boolean search;
    private List<AuditDataBean> gridModel;
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;
    private long fullCount;

    private String auditId;
    private String user;
    private String section;
    private String description;
    private String sdblpage;
    private String task;
    private String fdate;
    private String tdate;
    private AuditDataBean auditDataBean;
    
    private String reporttype;
    private ByteArrayInputStream excelStream;
    private ByteArrayInputStream zipStream;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVgenerate() {
        return vgenerate;
    }

    public void setVgenerate(boolean vgenerate) {
        this.vgenerate = vgenerate;
    }

    public boolean isVgenerateview() {
        return vgenerateview;
    }

    public void setVgenerateview(boolean vgenerateview) {
        this.vgenerateview = vgenerateview;
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

    public List<Systemuser> getUserList() {
        return userList;
    }

    public void setUserList(List<Systemuser> userList) {
        this.userList = userList;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public List<Page> getPageList() {
        return pageList;
    }

    public void setPageList(List<Page> pageList) {
        this.pageList = pageList;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public List<AuditDataBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<AuditDataBean> gridModel) {
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

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSdblpage() {
        return sdblpage;
    }

    public void setSdblpage(String sdblpage) {
        this.sdblpage = sdblpage;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFdate() {
        return fdate;
    }

    public void setFdate(String fdate) {
        this.fdate = fdate;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public AuditDataBean getAuditDataBean() {
        return auditDataBean;
    }

    public void setAuditDataBean(AuditDataBean auditDataBean) {
        this.auditDataBean = auditDataBean;
    }

    
    
}
