/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.bean.controlpanel.systemconfig;

import com.rawmakyamu.util.mapping.Promotionuserlevel;
import com.rawmakyamu.util.mapping.Status;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author : dilanka_w
 * @Created on : Jul 9, 2016, 1:11:32 PM
 */
public class PromotionInputBean {

    private static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    private String reporttype;
    private String promotionId;
    private String title;
    private String description;
    private String startDate;
    private String expDate;
    private String status;
    private String statusDes;
    private String sortOrder;
    private String updateSequence;
    private String clickCount;
    private String shareCount;
    private String likeCount;
    private String userLevel;
    private List<Status> statusList;
    private List<Promotionuserlevel> userLevelList;
//    private HashMap<String, String> userLevelList;
    private HashMap<String, String> sortOrderList;
    private String message;
    private String newvalue;
    private String oldvalue;

    private File tabImg;
    private File mobileImg;
    private String tabImgContentType;
    private String tabImgFileName;
    private String mobileImgContentType;
    private String mobileImgFileName;

    private byte[] edittabImg;
    private byte[] editmobileImg;
    private String tabImgedit;
    private String mobileImgedit;

    private ByteArrayInputStream excelStream;
    private ByteArrayInputStream zipStream;

    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdatebutt;
    private boolean vupdatelink;
    private boolean vdelete;
    private boolean vgenerate;
    private boolean vsearch;
    /*-------for access control-----------*/
    /*------------------------list data table  ------------------------------*/
    private List<PromotionBean> gridModel;
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
    /*------------------------list data table  ------------------------------*/
    /*------------------------for search  ------------------------------*/
    private String promotionIdSearch;
    private String descriptionSearch;
    private String titleSearch;
    private String statusSearch;
    private String sortOrderSearch;
    private String userLevelSearch;
    private String clickCountSearch;
    private String shareCountSearch;
    private String likeCountSearch;
    private String hiddenId;
    private String hiddenIdEdit;
    private String SearchAudit;
    private boolean search;

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    /*------------------------for search  ------------------------------*/
    public String getHiddenIdEdit() {
        return hiddenIdEdit;
    }

    public void setHiddenIdEdit(String hiddenIdEdit) {
        this.hiddenIdEdit = hiddenIdEdit;
    }

    public String getHiddenId() {
        return hiddenId;
    }

    public void setHiddenId(String hiddenId) {
        this.hiddenId = hiddenId;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getUpdateSequence() {
        return updateSequence;
    }

    public void setUpdateSequence(String updateSequence) {
        this.updateSequence = updateSequence;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
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

    public File getTabImg() {
        return tabImg;
    }

    public void setTabImg(File tabImg) {
        this.tabImg = tabImg;
    }

    public File getMobileImg() {
        return mobileImg;
    }

    public void setMobileImg(File mobileImg) {
        this.mobileImg = mobileImg;
    }

    public String getTabImgContentType() {
        return tabImgContentType;
    }

    public void setTabImgContentType(String tabImgContentType) {
        this.tabImgContentType = tabImgContentType;
    }

    public String getTabImgFileName() {
        return tabImgFileName;
    }

    public void setTabImgFileName(String tabImgFileName) {
        this.tabImgFileName = tabImgFileName;
    }

    public String getMobileImgContentType() {
        return mobileImgContentType;
    }

    public void setMobileImgContentType(String mobileImgContentType) {
        this.mobileImgContentType = mobileImgContentType;
    }

    public String getMobileImgFileName() {
        return mobileImgFileName;
    }

    public void setMobileImgFileName(String mobileImgFileName) {
        this.mobileImgFileName = mobileImgFileName;
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

    public List<PromotionBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<PromotionBean> gridModel) {
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

    public String getClickCount() {
        return clickCount;
    }

    public void setClickCount(String clickCount) {
        this.clickCount = clickCount;
    }

    public String getShareCount() {
        return shareCount;
    }

    public void setShareCount(String shareCount) {
        this.shareCount = shareCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public List<Promotionuserlevel> getUserLevelList() {
        return userLevelList;
    }

    public void setUserLevelList(List<Promotionuserlevel> userLevelList) {
        this.userLevelList = userLevelList;
    }

    public HashMap<String, String> getSortOrderList() {
        return sortOrderList;
    }

    public void setSortOrderList(HashMap<String, String> sortOrderList) {
        this.sortOrderList = sortOrderList;
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

    public String getDescriptionSearch() {
        return descriptionSearch;
    }

    public void setDescriptionSearch(String descriptionSearch) {
        this.descriptionSearch = descriptionSearch;
    }

    public String getTitleSearch() {
        return titleSearch;
    }

    public void setTitleSearch(String titleSearch) {
        this.titleSearch = titleSearch;
    }

    public String getStatusSearch() {
        return statusSearch;
    }

    public void setStatusSearch(String statusSearch) {
        this.statusSearch = statusSearch;
    }

    public String getSortOrderSearch() {
        return sortOrderSearch;
    }

    public void setSortOrderSearch(String sortOrderSearch) {
        this.sortOrderSearch = sortOrderSearch;
    }

    public String getUserLevelSearch() {
        return userLevelSearch;
    }

    public void setUserLevelSearch(String userLevelSearch) {
        this.userLevelSearch = userLevelSearch;
    }

    public String getClickCountSearch() {
        return clickCountSearch;
    }

    public void setClickCountSearch(String clickCountSearch) {
        this.clickCountSearch = clickCountSearch;
    }

    public String getShareCountSearch() {
        return shareCountSearch;
    }

    public void setShareCountSearch(String shareCountSearch) {
        this.shareCountSearch = shareCountSearch;
    }

    public String getLikeCountSearch() {
        return likeCountSearch;
    }

    public void setLikeCountSearch(String likeCountSearch) {
        this.likeCountSearch = likeCountSearch;
    }

    public String getPromotionIdSearch() {
        return promotionIdSearch;
    }

    public void setPromotionIdSearch(String promotionIdSearch) {
        this.promotionIdSearch = promotionIdSearch;
    }

    public byte[] getEdittabImg() {
        return this.edittabImg;
    }

    public void setEdittabImg(byte[] edittabImg) {
        this.edittabImg = edittabImg;
    }

    public byte[] getEditmobileImg() {
        return this.editmobileImg;
    }

    public void setEditmobileImg(byte[] editmobileImg) {
        this.editmobileImg = editmobileImg;
    }

    public String getTabImgedit() {
        try {
            byte[] blobAsBytes = getEdittabImg();
            blobAsBytes = Base64.encodeBase64(blobAsBytes);
            this.tabImgedit = new String(blobAsBytes);
        } catch (Exception e) {
            this.tabImgedit = "";
        }
        return tabImgedit;
    }

    public void setTabImgedit(String tabImgedit) {
        this.tabImgedit = tabImgedit;
    }

    public String getMobileImgedit() {
        try {
            byte[] blobAsBytes = getEditmobileImg();
            blobAsBytes = Base64.encodeBase64(blobAsBytes);
            this.mobileImgedit = new String(blobAsBytes);
        } catch (Exception e) {
            this.mobileImgedit = "";
        }
        return mobileImgedit;
    }

    public void setMobileImgedit(String mobileImgedit) {
        this.mobileImgedit = mobileImgedit;
    }

    public String getSearchAudit() {
        return SearchAudit;
    }

    public void setSearchAudit(String SearchAudit) {
        this.SearchAudit = SearchAudit;
    }

    public String getReporttype() {
        return reporttype;
    }

    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }

    public boolean isVgenerate() {
        return vgenerate;
    }

    public void setVgenerate(boolean vgenerate) {
        this.vgenerate = vgenerate;
    }

    public boolean isVsearch() {
        return vsearch;
    }

    public void setVsearch(boolean vsearch) {
        this.vsearch = vsearch;
    }

}
