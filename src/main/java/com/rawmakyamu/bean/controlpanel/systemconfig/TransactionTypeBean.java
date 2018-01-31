/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rawmakyamu.bean.controlpanel.systemconfig;

/**
 *
 * @author chathuri_t
 */
public class TransactionTypeBean {
    
    private String transactiontypecode;
    private String description;
    private String status;
    private String OTPReqStatus;
    private String RiskReqStatus;
    private String merchantTxnType;
    private String description_receiver;
    private String history_visibility;
    private String sortOrder;
    private long fullCount;

    public String getRiskReqStatus() {
        return RiskReqStatus;
    }

    public void setRiskReqStatus(String RiskReqStatus) {
        this.RiskReqStatus = RiskReqStatus;
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

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    public String getMerchantTxnType() {
        return merchantTxnType;
    }

    public void setMerchantTxnType(String merchantTxnType) {
        this.merchantTxnType = merchantTxnType;
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

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
}
