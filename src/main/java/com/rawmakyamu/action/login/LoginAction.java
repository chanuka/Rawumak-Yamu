/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.login;

import com.rawmakyamu.bean.login.LoginBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.login.LoginDAO;
import com.rawmakyamu.util.common.Common;
import com.rawmakyamu.util.mapping.Page;
import com.rawmakyamu.util.mapping.Passwordpolicy;
import com.rawmakyamu.util.mapping.Section;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Systemuser;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.LogoutMsgVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import com.rawmakyamu.util.varlist.PageVarList;
import com.rawmakyamu.util.varlist.SectionVarList;
import com.rawmakyamu.util.varlist.SessionVarlist;
import com.rawmakyamu.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author chanuka
 */
public class LoginAction extends ActionSupport implements ModelDriven<Object> {

    LoginBean loginBean = new LoginBean();
    Systemuser sysUser = new Systemuser();
    Passwordpolicy passwordpolicy = null;
    Calendar cal2 = Calendar.getInstance();
    Calendar cal4 = Calendar.getInstance();
    String lastLoggedDate = "";
    String currentDate = "";

    @Override
    public Object getModel() {
        return loginBean;
    }

    public String execute() {
        System.out.println("called LoginAction : execute");
        return SUCCESS;
    }

    public String Check() {
        String result = "message";
        String warnmsg = "";
        try {
            String message = this.validateUser();
            if (message.isEmpty()) {

                HttpSession sessionPrevious = ServletActionContext.getRequest().getSession(false);
                if (sessionPrevious != null) {
                    sessionPrevious.invalidate();
                }
                //set user to the session
                HttpSession session = ServletActionContext.getRequest().getSession(true);
                session.setAttribute(SessionVarlist.SYSTEMUSER, sysUser);
                session.setAttribute(SessionVarlist.LAST_LOGGED_DATE, lastLoggedDate);
                session.setAttribute(SessionVarlist.CURRENT_DATE_TIME, currentDate);

//                cal4.setTime(sysUser.getPasswordexpirydate());
                // get current date from DB                            
//                cal2.setTime(CommonDAO.getSystemDateLogin());
                // check for minimnum password change period
                if (daysBetween(cal2, cal4) <= passwordpolicy.getMinimumpasswordchangeperiod()) {
                    if (daysBetween(cal2, cal4) == 0) {
                        warnmsg = MessageVarList.COMMON_WARN_CHANGE_PASSWORD + "today.";
                    } else {
                        warnmsg = MessageVarList.COMMON_WARN_CHANGE_PASSWORD + "in " + daysBetween(cal2, cal4) + " days.";

                    }
                    session.setAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD, warnmsg);
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 0);

                }

                //set user and sessionid to hashmap
                HashMap<String, String> userMap = null;

                ServletContext sc = ServletActionContext.getServletContext();
                userMap = (HashMap<String, String>) sc.getAttribute(SessionVarlist.USERMAP);
                if (userMap == null) {
                    userMap = new HashMap<String, String>();
                }
                userMap.put(sysUser.getUsername(), session.getId());
                sc.setAttribute(SessionVarlist.USERMAP, userMap);

                LoginDAO loginDao = new LoginDAO();

                HashMap<String, List<Task>> pageMap = loginDao.getPageTask(sysUser.getUserrole().getUserrolecode());
                session.setAttribute(SessionVarlist.TASKMAP, pageMap); // to be used in the user privilages test

                HashMap<Section, List<Page>> sectionPagesMap = loginDao.getSectionPages(sysUser.getUserrole().getUserrolecode());

                session.setAttribute(SessionVarlist.SECTIONPAGELIST, sectionPagesMap);

                if (Integer.parseInt(sysUser.getInitialloginstatus()) == 0) {

                    result = "firstlogin";

                } else if (daysBetween(cal2, cal4) == -1) {
                    result = "firstlogin";

                } else {

                    result = SUCCESS;
                }
                addActionMessage("Last logged date : " + lastLoggedDate);
            } else {
                loginBean.setErrormessage(message);
                addActionError(message);
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private String validateUser() {
        String message = "";
        try {
            LoginDAO loginDao = new LoginDAO();
            HttpServletRequest request = ServletActionContext.getRequest();

            if ((loginBean.getUsername() == null || loginBean.getUsername().isEmpty()) && (loginBean.getPassword() == null || loginBean.getPassword().isEmpty())) {
                message = MessageVarList.LOGIN_EMPTY_USERNAME_PASSWORD;
            } else if (loginBean.getUsername() == null || loginBean.getUsername().isEmpty()) {
                message = MessageVarList.LOGIN_EMPTY_USERNAME;
            } else if (loginBean.getPassword() == null || loginBean.getPassword().isEmpty()) {
                message = MessageVarList.LOGIN_EMPTY_PASSWORD;
            } else {
                //check user validity from db
                try {

                    sysUser = loginDao.findUserbyUsername(loginBean.getUsername());

                    String hPass = Common.makeHash(loginBean.getPassword());

                    passwordpolicy = loginDao.findPasswordPolicy();
                    try {
                        //set last logged date and current date
                        lastLoggedDate = Common.getFormattedDateForLogin(sysUser.getLoggeddate());
                        Date date = new Date();
                        currentDate = Common.getFormattedDateForLogin(date);

                        Calendar cal3 = Calendar.getInstance();
                        cal3.setTime(sysUser.getLoggeddate());
                        cal4.setTime(sysUser.getExpirydate());

                        // get current date from DB                            
                        cal2.setTime(CommonDAO.getSystemDateLogin());
//                cal2.setTime(new Date());

                        if (sysUser == null) {
                            message = MessageVarList.LOGIN_INVALID;
                        } else if (sysUser.getUserrole().getUserrolecode().equals(CommonVarList.USER_ROLE_MERCHANT) || sysUser.getUserrole().getUserrolecode().equals(CommonVarList.USER_ROLE_MERCHANT_CUSTOMER)) {
                            message = MessageVarList.LOGIN_INVALID_ROLE;
                        } else if (!sysUser.getStatus().getStatuscode().equals(CommonVarList.STATUS_ACTIVE)) {
                            message = MessageVarList.LOGIN_DEACTIVE;
                        } else if (!sysUser.getUserrole().getStatus().getStatuscode().equals(CommonVarList.STATUS_ACTIVE)) {
                            message = MessageVarList.LOGIN_DEACTIVE_ROLE;
                        } else if (Integer.parseInt(sysUser.getNoofinvlidattempt()) >= passwordpolicy.getNoofinvalidloginattempt()) {
                            loginBean.setAttempts(Integer.parseInt(sysUser.getNoofinvlidattempt()));
                            loginBean.setStatus(CommonVarList.STATUS_DEACTIVE);

                            Systemaudit audit = Common.makeAudittrace(request, sysUser, TaskVarList.LOGIN_TASK, PageVarList.LOGIN_PAGE, SectionVarList.DEFAULT_SECTION, "Login de-activated", null);
                            loginDao.updateUser(loginBean, audit, false);
                            message = MessageVarList.LOGIN_DEACTIVE;

                        } else if (daysBetween(cal3, cal2) >= passwordpolicy.getIdleaccountexpiryperiod()) {

                            loginBean.setAttempts(Integer.parseInt(sysUser.getNoofinvlidattempt()));
                            loginBean.setStatus(CommonVarList.STATUS_DEACTIVE);

                            Systemaudit audit = Common.makeAudittrace(request, sysUser, TaskVarList.LOGIN_TASK, PageVarList.LOGIN_PAGE, SectionVarList.DEFAULT_SECTION, "Login de-activated", null);
                            loginDao.updateUser(loginBean, audit, false);
                            message = MessageVarList.LOGIN_DEACTIVE;
//                        } else if (!hPass.equals(sysUser.getPassword())) {
//                            message = MessageVarList.LOGIN_ERROR_INVALID;
                        } else if (!hPass.equals(sysUser.getPassword())) {

                            if (sysUser.getNoofinvlidattempt() == null || Integer.parseInt(sysUser.getNoofinvlidattempt()) == 0) {
                                sysUser.setNoofinvlidattempt("0");
                            }
                            int attempts = Integer.parseInt(sysUser.getNoofinvlidattempt());
                            attempts++;
                            loginBean.setAttempts(attempts);
                            loginBean.setStatus(sysUser.getStatus().getStatuscode());

                            Systemaudit audit = Common.makeAudittrace(request, sysUser, TaskVarList.LOGIN_TASK, PageVarList.LOGIN_PAGE, SectionVarList.DEFAULT_SECTION, "Login attempted ", null);
                            loginDao.updateUser(loginBean, audit, false);
//                            message = MessageVarList.LOGIN_INVALID;
                            message = MessageVarList.LOGIN_ERROR_INVALID;

                        } else if (message.isEmpty()) {

                            loginBean.setAttempts(new Integer("0"));
                            loginBean.setStatus(sysUser.getStatus().getStatuscode());

                            Systemaudit audit = Common.makeAudittrace(request, sysUser, TaskVarList.LOGIN_TASK, PageVarList.LOGIN_PAGE, SectionVarList.DEFAULT_SECTION, "Login successfully", null);
                            loginDao.updateUser(loginBean, audit, true);
                        }

                    } catch (Exception ex) {
                        message = MessageVarList.LOGIN_ERROR_INVALID;
                    }
                } catch (Exception ex) {
                    message = MessageVarList.LOGIN_ERROR_INVALID;
                }
            }
        } catch (Exception ex) {
            message = MessageVarList.LOGIN_ERROR_LOAD;
            Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

    public String Logout() {
        String result = "message";
        System.out.println("called LoginAction : Logout");

        try {
            //invalidate session
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session != null) {
                //error messages
                if (loginBean.getMessage() != null && !loginBean.getMessage().isEmpty()) {
                    String message = loginBean.getMessage();
                    if (message.equals("error1")) {
                        loginBean.setErrormessage("Access denied. Please login again.");
                        addActionError("Access denied. Please login again.");
                    } else if (message.equals("error2")) {
                        addActionError("You have been logged in from another machine. Access denied.");
                        loginBean.setErrormessage("You have been logged in from another machine. Access denied.");
                    } else if (message.equals("error3")) {
                    } else if (message.equals("success1")) {
                        addActionMessage("Your password changed successfully. Please login with the new password.");
                        loginBean.setErrormessage("Your password changed successfully. Please login with the new password.");
                    }
                } else//if loginbean not set check for the session message
                {
                    if (session != null) {
                        String msg = String.valueOf(session.getAttribute("intercept"));
                        if (msg.equalsIgnoreCase("ERROR_ACCESS")) {
                            addActionError(LogoutMsgVarList.ERROR_ACCESS);
                            loginBean.setErrormessage(LogoutMsgVarList.ERROR_ACCESS);
                        } else if (msg.equals("ERROR_ACCESSPOINT")) {
                            addActionError(LogoutMsgVarList.ERROR_ACCESSPOINT);
                            loginBean.setErrormessage(LogoutMsgVarList.ERROR_ACCESSPOINT);
                        } else if (msg.equals("ERROR_USER_INFO")) {
                            addActionError(LogoutMsgVarList.ERROR_USER_INFO);
                            loginBean.setErrormessage(LogoutMsgVarList.ERROR_USER_INFO);
                        } else if (msg.equals("PASSWORD_CHANGE_SUCCESS")) {
                            loginBean.setErrormessage(LogoutMsgVarList.PASSWORD_CHANGE_SUCCESS);
                            addActionMessage(LogoutMsgVarList.PASSWORD_CHANGE_SUCCESS);
                        } else {
                            addActionError(LogoutMsgVarList.ERROR_SESSION);
                            loginBean.setErrormessage(LogoutMsgVarList.ERROR_SESSION);
                        }
                    }
                }

                sysUser = ((Systemuser) session.getAttribute(SessionVarlist.SYSTEMUSER));

                if (sysUser != null) {

                    LoginDAO loginDao = new LoginDAO();

                    Systemaudit audit = Common.makeAudittrace(ServletActionContext.getRequest(), sysUser, TaskVarList.LOGOUT_TASK, PageVarList.LOGIN_PAGE, SectionVarList.DEFAULT_SECTION, "Log out successfully", null);
                    loginBean.setStatus(sysUser.getStatus().getStatuscode());
                    loginBean.setUsername(sysUser.getUsername());

                    loginDao.updateUser(loginBean, audit, false);
                }

                session.invalidate();

            }

        } catch (Exception ex) {
            Logger.getLogger(LoginAction.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /**
     * Using Calendar - THE CORRECT WAY*
     */
    public static long daysBetween(Calendar startDate, Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        long daysBetween = -1;
        while (date.before(endDate)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

}
