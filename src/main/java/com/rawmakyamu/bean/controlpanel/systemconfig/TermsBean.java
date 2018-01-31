/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.bean.controlpanel.systemconfig;

/**
 *
 * @author jayana_i
 */
public class TermsBean {
    private String status;
    private String terms;
    private String versionno;
    private String currentSeries;
    private String logoutTime;
    private String maxAttempt;
    private String category;
    private long fullCount;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
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

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    public String getVersionno() {
        return versionno;
    }

    public void setVersionno(String versionno) {
        this.versionno = versionno;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
}
