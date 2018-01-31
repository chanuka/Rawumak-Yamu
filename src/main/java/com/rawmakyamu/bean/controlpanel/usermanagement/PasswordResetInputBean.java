/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rawmakyamu.bean.controlpanel.usermanagement;

/**
 *
 * @Document   : PasswordResetInputBean
 * @Created on : Mar 7, 2014, 1:17:33 PM
 * @author     : thushanth
 */
public class PasswordResetInputBean {
    
    private String username;
    private String pwtooltip;
    private String userrole;
    private String currpwd;
    private String newpwd;
    private String renewpwd;
    private String husername;
    /*-------for access control-----------*/
    private boolean vupdatepwd;

    public String getPwtooltip() {
        return pwtooltip;
    }

    public void setPwtooltip(String pwtooltip) {
        this.pwtooltip = pwtooltip;
    }

    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getCurrpwd() {
        return currpwd;
    }

    public void setCurrpwd(String currpwd) {
        this.currpwd = currpwd;
    }

    public String getNewpwd() {
        return newpwd;
    }

    public void setNewpwd(String newpwd) {
        this.newpwd = newpwd;
    }

    public String getRenewpwd() {
        return renewpwd;
    }

    public void setRenewpwd(String renewpwd) {
        this.renewpwd = renewpwd;
    }

    public String getHusername() {
        return husername;
    }

    public void setHusername(String husername) {
        this.husername = husername;
    }

    public boolean isVupdatepwd() {
        return vupdatepwd;
    }

    public void setVupdatepwd(boolean vupdatepwd) {
        this.vupdatepwd = vupdatepwd;
    }

    
    

}
