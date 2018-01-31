/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.bean.login;

/**
 *
 * @author chanuka
 */
public class LoginBean {

    private String message;
    private String errormessage;
    private String username;
    private String password;
    private int attempts;
    private String status;
    private String choosesection;
    private String hchoosesection;

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChoosesection() {
        return choosesection;
    }

    public void setChoosesection(String choosesection) {
        this.choosesection = choosesection;
    }

    public String getHchoosesection() {
        return hchoosesection;
    }

    public void setHchoosesection(String hchoosesection) {
        this.hchoosesection = hchoosesection;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
