/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.controlpanel.systemconfig;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.rawmakyamu.action.controlpanel.usermanagement.PageAction;
import com.rawmakyamu.bean.controlpanel.systemconfig.DriverTrackingMgtInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import com.rawmakyamu.util.varlist.PageVarList;
import com.rawmakyamu.util.varlist.SessionVarlist;
import com.rawmakyamu.util.varlist.TaskVarList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author ASUS
 */
public class DriverTrackingMgtAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    DriverTrackingMgtInputBean inputBean = new DriverTrackingMgtInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called DriverTrackingMgtAction : execute");
        return SUCCESS;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.DRIVER_TRACKING_MGT_PAGE, request);

//        if (tasklist != null && tasklist.size() > 0) {
//            for (Task task : tasklist) {
//                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
//                    inputBean.setVadd(false);
//                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
//                    inputBean.setVdelete(false);
//                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
//                    inputBean.setVupdatelink(false);
//                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
//                    inputBean.setVsearch(false);
//                }
//            }
//        }
//        inputBean.setVupdatebutt(true);
        return true;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.DRIVER_TRACKING_MGT_PAGE;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        }

        if ("execute".equals(method)) {
            status = true;
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            status = new Common().checkMethodAccess(task, page, userRole, request);
        }
        return status;
    }

    public String view() {

        System.out.println("called DriverTrackingMgtAction :view");
        String result = "view";

        try {
            if (this.applyUserPrivileges()) {

//                CommonDAO dao = new CommonDAO();
//                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
//                inputBean.setDefaultStatus(CommonVarList.STATUS_ACTIVE);

            } else {
                result = "loginpage";
            }

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

        } catch (Exception ex) {
            addActionError("Driver tracking " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
