/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.bean.controlpanel.systemconfig;

import com.rawmakyamu.util.mapping.Status;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 *
 * @author jayana_i
 */
public class ATMLocationsInputBean {

    private String id;
    private String description;
    private String udescription;
    private String sdescription;

    private String latitude;
    private String longitude;
    private String ulatitude;
    private String ulongitude;
    private String address;
    private String uaddress;
    private String createdtime;
    private String lastupdatedtime;
    private String hiddenId;

    private String atmcode;
    private String satmcode;
    private String uatmcode;

    private String status;
    private String sstatus;
    private String ustatus;
    private String sstatusATM;
    private String sstatusCDM;
    private String sstatusAgent;
    private String sstatusBranch;
    private String sstatusShop;

    private boolean isAtm;
    private boolean isCdm;
    private boolean isBranch;
    private boolean isAgent;
    private boolean isShop;

    //for csv upload
    private String isAtmUp;
    private String isCdmUp;
    private String isBranchUp;
    private String isAgentUp;
    private String isShopUp;

    private String filaname;

    private long fullCount;

    private String defaultStatus;
    private List<Status> StatusList;
    private List<Status> StatusListATM;
    private List<Status> StatusListCDM;
    private List<Status> StatusListBranch;
    private List<Status> StatusListAgent;
    private List<Status> StatusListShop;

//    private String conXLContentType;
//    private String conXLFileName;
//    private File conXL;
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
    private List<ATMLocationsBean> gridModel;
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

    private ByteArrayInputStream excelStream;

    public String getFilaname() {
        return filaname;
    }

    public void setFilaname(String filaname) {
        this.filaname = filaname;
    }

    public ByteArrayInputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(ByteArrayInputStream excelStream) {
        this.excelStream = excelStream;
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

    public void setVsearch(boolean vsearch) {
        this.vsearch = vsearch;
    }

    public boolean isVupload() {
        return vupload;
    }

    public void setVupload(boolean vupload) {
        this.vupload = vupload;
    }

    public String getUaddress() {
        return uaddress;
    }

    public void setUaddress(String uaddress) {
        this.uaddress = uaddress;
    }

    public String getUdescription() {
        return udescription;
    }

    public void setUdescription(String udescription) {
        this.udescription = udescription;
    }

    public String getUatmcode() {
        return uatmcode;
    }

    public void setUatmcode(String uatmcode) {
        this.uatmcode = uatmcode;
    }

    public String getUlatitude() {
        return ulatitude;
    }

    public void setUlatitude(String ulatitude) {
        this.ulatitude = ulatitude;
    }

    public String getUlongitude() {
        return ulongitude;
    }

    public void setUlongitude(String ulongitude) {
        this.ulongitude = ulongitude;
    }

    public String getUstatus() {
        return ustatus;
    }

    public void setUstatus(String ustatus) {
        this.ustatus = ustatus;
    }

    public String getSdescription() {
        return sdescription;
    }

    public void setSdescription(String sdescription) {
        this.sdescription = sdescription;
    }

    public String getSatmcode() {
        return satmcode;
    }

    public void setSatmcode(String satmcode) {
        this.satmcode = satmcode;
    }

    public String getSstatus() {
        return sstatus;
    }

    public void setSstatus(String sstatus) {
        this.sstatus = sstatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(String lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    public String getAtmcode() {
        return atmcode;
    }

    public void setAtmcode(String atmcode) {
        this.atmcode = atmcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
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

    public List<ATMLocationsBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<ATMLocationsBean> gridModel) {
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

    public String getHiddenId() {
        return hiddenId;
    }

    public void setHiddenId(String hiddenId) {
        this.hiddenId = hiddenId;
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

    public boolean isIsAtm() {
        return isAtm;
    }

    public void setIsAtm(boolean isAtm) {
        this.isAtm = isAtm;
    }

    public boolean isIsCdm() {
        return isCdm;
    }

    public void setIsCdm(boolean isCdm) {
        this.isCdm = isCdm;
    }

    public boolean isIsBranch() {
        return isBranch;
    }

    public void setIsBranch(boolean isBranch) {
        this.isBranch = isBranch;
    }

    public boolean isIsAgent() {
        return isAgent;
    }

    public void setIsAgent(boolean isAgent) {
        this.isAgent = isAgent;
    }

    public String getIsAtmUp() {
        return isAtmUp;
    }

    public void setIsAtmUp(String isAtmUp) {
        this.isAtmUp = isAtmUp;
    }

    public String getIsCdmUp() {
        return isCdmUp;
    }

    public void setIsCdmUp(String isCdmUp) {
        this.isCdmUp = isCdmUp;
    }

    public String getIsBranchUp() {
        return isBranchUp;
    }

    public void setIsBranchUp(String isBranchUp) {
        this.isBranchUp = isBranchUp;
    }

    public String getIsAgentUp() {
        return isAgentUp;
    }

    public void setIsAgentUp(String isAgentUp) {
        this.isAgentUp = isAgentUp;
    }

    public boolean isIsShop() {
        return isShop;
    }

    public void setIsShop(boolean isShop) {
        this.isShop = isShop;
    }

    public String getIsShopUp() {
        return isShopUp;
    }

    public void setIsShopUp(String isShopUp) {
        this.isShopUp = isShopUp;
    }

    public List<Status> getStatusListATM() {
        return StatusListATM;
    }

    public void setStatusListATM(List<Status> StatusListATM) {
        this.StatusListATM = StatusListATM;
    }

    public List<Status> getStatusListCDM() {
        return StatusListCDM;
    }

    public void setStatusListCDM(List<Status> StatusListCDM) {
        this.StatusListCDM = StatusListCDM;
    }

    public List<Status> getStatusListBranch() {
        return StatusListBranch;
    }

    public void setStatusListBranch(List<Status> StatusListBranch) {
        this.StatusListBranch = StatusListBranch;
    }

    public List<Status> getStatusListAgent() {
        return StatusListAgent;
    }

    public void setStatusListAgent(List<Status> StatusListAgent) {
        this.StatusListAgent = StatusListAgent;
    }

    public List<Status> getStatusListShop() {
        return StatusListShop;
    }

    public void setStatusListShop(List<Status> StatusListShop) {
        this.StatusListShop = StatusListShop;
    }

    public String getSstatusATM() {
        return sstatusATM;
    }

    public void setSstatusATM(String sstatusATM) {
        this.sstatusATM = sstatusATM;
    }

    public String getSstatusCDM() {
        return sstatusCDM;
    }

    public void setSstatusCDM(String sstatusCDM) {
        this.sstatusCDM = sstatusCDM;
    }

    public String getSstatusAgent() {
        return sstatusAgent;
    }

    public void setSstatusAgent(String sstatusAgent) {
        this.sstatusAgent = sstatusAgent;
    }

    public String getSstatusBranch() {
        return sstatusBranch;
    }

    public void setSstatusBranch(String sstatusBranch) {
        this.sstatusBranch = sstatusBranch;
    }

    public String getSstatusShop() {
        return sstatusShop;
    }

    public void setSstatusShop(String sstatusShop) {
        this.sstatusShop = sstatusShop;
    }

}
