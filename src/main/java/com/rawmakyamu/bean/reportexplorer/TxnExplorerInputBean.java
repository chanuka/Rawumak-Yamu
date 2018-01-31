/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.bean.reportexplorer;

import com.rawmakyamu.util.mapping.Currency;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Transactiontype;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 *
 * @author thushanth
 */
public class TxnExplorerInputBean {

    //~~~~~~~~~~~~~~~user inputs ~~~~~~~~~~~~~~~~//
    private String cardNo;
    private String currency;
    private String tid;
    private String mid;
    private String txnTypeCode;
    private String amount;
    private String deviceID;
    private String transactionID;
    private String approveCode;
    private String rrn;
    private String nic;
    private String traceNo;
    private String resCode;
    private String walletid;
    private String processingcode;
    private String status;
    private String mobileNo;
    private String invoiceno;
    private String load;
    private String message;
    private String tocardno;
    private String fromcardno;
    private String listeneruid;
    private String toMobileNo;
    private String tokenid;
    private String toEmail;
    private String approvalstatus;

    private List<Status> statusList;
    private List<Status> approvalStatusList;
    private List<String> walletist;
    private List<Currency> currencyList;
    private List<Transactiontype> txnTypeList;
    private String fromdate;
    private String todate;
    private TransDataBean transdataBean;
    /*-------for access control-----------*/
    private boolean vgenerate;
    private boolean vgenerateview;
    private boolean vsearch;
    private boolean vviewlink;
    private boolean search;
    /*-------for access control-----------*/
    /*------------------------list data table  ------------------------------*/
    private List<TxnDataBean> gridModel;
    private List<TxnHisDataBean> gridHisModel;
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
    private long fullCount;
//////////////////
    private String reporttype;
    private ByteArrayInputStream excelStream;
    private ByteArrayInputStream zipStream;

    /*------------------------list data table  ------------------------------*/
 
    public List<String> getWalletist() {
        return walletist;
    }

    public void setWalletist(List<String> walletist) {
        this.walletist = walletist;
    }

    public boolean isVgenerateview() {
        return vgenerateview;
    }

    public void setVgenerateview(boolean vgenerateview) {
        this.vgenerateview = vgenerateview;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getTxnTypeCode() {
        return txnTypeCode;
    }

    public void setTxnTypeCode(String txnTypeCode) {
        this.txnTypeCode = txnTypeCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getApproveCode() {
        return approveCode;
    }

    public void setApproveCode(String approveCode) {
        this.approveCode = approveCode;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public List<Transactiontype> getTxnTypeList() {
        return txnTypeList;
    }

    public void setTxnTypeList(List<Transactiontype> txnTypeList) {
        this.txnTypeList = txnTypeList;
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

    public TransDataBean getTransdataBean() {
        return transdataBean;
    }

    public void setTransdataBean(TransDataBean transdataBean) {
        this.transdataBean = transdataBean;
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

    public boolean isVviewlink() {
        return vviewlink;
    }

    public void setVviewlink(boolean vviewlink) {
        this.vviewlink = vviewlink;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public List<TxnDataBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<TxnDataBean> gridModel) {
        this.gridModel = gridModel;
    }

    public List<TxnHisDataBean> getGridHisModel() {
        return gridHisModel;
    }

    public void setGridHisModel(List<TxnHisDataBean> gridHisModel) {
        this.gridHisModel = gridHisModel;
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

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    public String getWalletid() {
        return walletid;
    }

    public void setWalletid(String walletid) {
        this.walletid = walletid;
    }

    public String getProcessingcode() {
        return processingcode;
    }

    public void setProcessingcode(String processingcode) {
        this.processingcode = processingcode;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getTocardno() {
        return tocardno;
    }

    public void setTocardno(String tocardno) {
        this.tocardno = tocardno;
    }

    public String getFromcardno() {
        return fromcardno;
    }

    public void setFromcardno(String fromcardno) {
        this.fromcardno = fromcardno;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getListeneruid() {
        return listeneruid;
    }

    public void setListeneruid(String listeneruid) {
        this.listeneruid = listeneruid;
    }

    public String getToMobileNo() {
        return toMobileNo;
    }

    public void setToMobileNo(String toMobileNo) {
        this.toMobileNo = toMobileNo;
    }

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getApprovalstatus() {
        return approvalstatus;
    }

    public void setApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

    public List<Status> getApprovalStatusList() {
        return approvalStatusList;
    }

    public void setApprovalStatusList(List<Status> approvalStatusList) {
        this.approvalStatusList = approvalStatusList;
    }

}
