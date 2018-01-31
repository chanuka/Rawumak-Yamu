/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.bean.controlpanel.usermanagement;

/**
 *
 * @author asela
 */
public class UserRoleMgtBean {

    private String userRoleCode;
    private String description;
    private String userRoleLevel;
    private String status;
    private String createtime;
    private long fullCount;

    public String getUserRoleCode() {
        return userRoleCode;
    }

    public void setUserRoleCode(String userRoleCode) {
        this.userRoleCode = userRoleCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserRoleLevel() {
        return userRoleLevel;
    }

    public void setUserRoleLevel(String userRoleLevel) {
        this.userRoleLevel = userRoleLevel;
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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    
    
}
