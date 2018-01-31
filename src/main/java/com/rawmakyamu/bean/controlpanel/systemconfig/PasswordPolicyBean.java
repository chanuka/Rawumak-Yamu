/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rawmakyamu.bean.controlpanel.systemconfig;

/**
 *
 * @author thushanth
 */
public class PasswordPolicyBean {

    private String passwordpolicyid;
    private String minimumlength;
    private String maximumlength;
    private String minimumspecialcharacters;
    private String minimumuppercasecharacters;
    private String minimumnumericalcharacters;
    private String minimumlowercasecharacters;
    private String repeatcharactersallow;
    private String initialpasswordexpirystatus;
    private String passwordexpiryperiod;
    private String noofhistorypassword;
    private String minimumpasswordchangeperiod;
    private String idleaccountexpiryperiod;                    
    private String noofinvalidloginattempt;
    private long fullCount;

    public String getPasswordpolicyid() {
        return passwordpolicyid;
    }

    public void setPasswordpolicyid(String passwordpolicyid) {
        this.passwordpolicyid = passwordpolicyid;
    }

    public String getMinimumlength() {
        return minimumlength;
    }

    public void setMinimumlength(String minimumlength) {
        this.minimumlength = minimumlength;
    }

    public String getMaximumlength() {
        return maximumlength;
    }

    public void setMaximumlength(String maximumlength) {
        this.maximumlength = maximumlength;
    }

    
    public String getMinimumspecialcharacters() {
        return minimumspecialcharacters;
    }

    public void setMinimumspecialcharacters(String minimumspecialcharacters) {
        this.minimumspecialcharacters = minimumspecialcharacters;
    }

    public String getMinimumuppercasecharacters() {
        return minimumuppercasecharacters;
    }

    public void setMinimumuppercasecharacters(String minimumuppercasecharacters) {
        this.minimumuppercasecharacters = minimumuppercasecharacters;
    }

    public String getMinimumnumericalcharacters() {
        return minimumnumericalcharacters;
    }

    public void setMinimumnumericalcharacters(String minimumnumericalcharacters) {
        this.minimumnumericalcharacters = minimumnumericalcharacters;
    }

    public String getMinimumlowercasecharacters() {
        return minimumlowercasecharacters;
    }

    public void setMinimumlowercasecharacters(String minimumlowercasecharacters) {
        this.minimumlowercasecharacters = minimumlowercasecharacters;
    }

    public String getRepeatcharactersallow() {
        return repeatcharactersallow;
    }

    public void setRepeatcharactersallow(String repeatcharactersallow) {
        this.repeatcharactersallow = repeatcharactersallow;
    }

    public String getInitialpasswordexpirystatus() {
        return initialpasswordexpirystatus;
    }

    public void setInitialpasswordexpirystatus(String initialpasswordexpirystatus) {
        this.initialpasswordexpirystatus = initialpasswordexpirystatus;
    }

    public String getPasswordexpiryperiod() {
        return passwordexpiryperiod;
    }

    public void setPasswordexpiryperiod(String passwordexpiryperiod) {
        this.passwordexpiryperiod = passwordexpiryperiod;
    }

    public String getNoofhistorypassword() {
        return noofhistorypassword;
    }

    public void setNoofhistorypassword(String noofhistorypassword) {
        this.noofhistorypassword = noofhistorypassword;
    }

    public String getMinimumpasswordchangeperiod() {
        return minimumpasswordchangeperiod;
    }

    public void setMinimumpasswordchangeperiod(String minimumpasswordchangeperiod) {
        this.minimumpasswordchangeperiod = minimumpasswordchangeperiod;
    }

    public String getIdleaccountexpiryperiod() {
        return idleaccountexpiryperiod;
    }

    public void setIdleaccountexpiryperiod(String idleaccountexpiryperiod) {
        this.idleaccountexpiryperiod = idleaccountexpiryperiod;
    }

    public String getNoofinvalidloginattempt() {
        return noofinvalidloginattempt;
    }

    public void setNoofinvalidloginattempt(String noofinvalidloginattempt) {
        this.noofinvalidloginattempt = noofinvalidloginattempt;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }
}

