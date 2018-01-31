/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.controlpanel.usermanagement;

import com.rawmakyamu.bean.controlpanel.usermanagement.UserRoleMgtBean;
import com.rawmakyamu.bean.controlpanel.usermanagement.UserRoleMgtInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.systemaudit.SystemAuditDAO;
import com.rawmakyamu.dao.controlpanel.usermanagement.UserRoleMgtDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import static com.rawmakyamu.util.common.Common.checkEmptyorNullString;
import com.rawmakyamu.util.common.Validation;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.mapping.Userrole;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import com.rawmakyamu.util.varlist.OracleMessage;
import com.rawmakyamu.util.varlist.PageVarList;
import com.rawmakyamu.util.varlist.SectionVarList;
import com.rawmakyamu.util.varlist.SessionVarlist;
import com.rawmakyamu.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author asela
 */
public class UserRoleMgtAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    UserRoleMgtInputBean inputBean = new UserRoleMgtInputBean();

    public String execute() throws Exception {
        System.out.println("called UserRoleMgtAction : execute");
        return SUCCESS;
    }

    public UserRoleMgtInputBean getModel() {
        return inputBean;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.USER_ROLE_MGT_PAGE;
        String task = null;
        if ("View".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("List".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Add".equals(method)) {
            task = TaskVarList.ADD_TASK;
        } else if ("Delete".equals(method)) {
            task = TaskVarList.DELETE_TASK;
        } else if ("Find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Update".equals(method)) {
            task = TaskVarList.UPDATE_TASK;
        }//newly changed
        else if ("activate".equals(method)) {
            task = TaskVarList.UPDATE_TASK;
        } else if ("ViewPopup".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("detail".equals(method)) {
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

    public String View() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
//                inputBean.setUserRoleLevelList(dao.getUserLevelList());
                inputBean.setDefaultUser(CommonVarList.LEVEL_01);
                inputBean.setDefaultStatus(CommonVarList.STATUS_ACTIVE);
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

            System.out.println("called UserRoleMgtAction :view");

        } catch (Exception ex) {
            addActionError("User role " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRoleMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String ViewPopup() {
        String result = "viewpopup";
        System.out.println("called UserRoleMgtAction : ViewPopup");
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
//                inputBean.setUserRoleLevelList(dao.getUserLevelList());
                inputBean.setDefaultUser(CommonVarList.LEVEL_01);
                inputBean.setDefaultStatus(CommonVarList.STATUS_ACTIVE);

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

            System.out.println("called UserRoleMgtAction :View");

        } catch (Exception ex) {
            addActionError("UserRole " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRoleMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //start newly chnged
    public String activate() {
        System.out.println("called UserRoleMgtAction : activate");
        String message = null;
        String retType = "activate";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            UserRoleMgtDAO dao = new UserRoleMgtDAO();
            message = this.validateUpdateInputs();
            if (message.isEmpty()) {
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.USER_ROLE_MGT_PAGE, SectionVarList.USERMANAGEMENT, "User role code " + inputBean.getUserRoleCode() + " updated", null);
                message = dao.activateUrole(inputBean, audit);
                if (message.isEmpty()) {
                    message = "User Role " + MessageVarList.COMMON_SUCC_ACTIVATE;
                }
                inputBean.setMessage(message);
            } else {
                addActionError(message);
            }

        } catch (Exception e) {
            Logger.getLogger(UserRoleMgtAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(MessageVarList.COMMON_ERROR_ACTIVATE);
        }
        return retType;
    }

    //end newly changed
    public String Add() {
        System.out.println("called UserRoleMgtAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            UserRoleMgtDAO dao = new UserRoleMgtDAO();
            String message = this.validateInsertsInputs();

            if (message.isEmpty()) {

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.USER_ROLE_MGT_PAGE, SectionVarList.USERMANAGEMENT, "User role code " + inputBean.getUserRoleCode() + " added", null, null, null);
                message = dao.insertUserRole(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("User role " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("User role " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRoleMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String detail() {
        System.out.println("called UserRoleMgtAction : detail");
        Userrole tt = null;
        try {
            if (inputBean.getUserRoleCode() != null && !inputBean.getUserRoleCode().isEmpty()) {

                UserRoleMgtDAO dao = new UserRoleMgtDAO();
                CommonDAO commonDAO = new CommonDAO();
                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
//                inputBean.setUserRoleLevelList(commonDAO.getUserLevelList());
                inputBean.setDefaultUser(CommonVarList.LEVEL_01);
                inputBean.setDefaultStatus(CommonVarList.STATUS_ACTIVE);

                tt = dao.findUserRoleById(inputBean.getUserRoleCode());

                inputBean.setUserRoleCode(tt.getUserrolecode());
                inputBean.setDescription(tt.getDescription());
//                inputBean.setUserRoleLevel(Integer.toString(tt.getUserlevel().getLevelid()));
                inputBean.setStatus(tt.getStatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty User Role code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("User Role code  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRoleMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";

    }

    public String Update() {

        System.out.println("called UserRoleMgtAction : update");
        String retType = "message";

        try {
            if (inputBean.getUserRoleCode() != null && !inputBean.getUserRoleCode().isEmpty()) {

                //set username get in hidden fileds
                inputBean.setUserRoleCode(inputBean.getUserRoleCode());

                String message = this.validateUpdateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    UserRoleMgtDAO dao = new UserRoleMgtDAO();

                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.USER_ROLE_MGT_PAGE, SectionVarList.USERMANAGEMENT, "User role code " + inputBean.getUserRoleCode() + " updated", null, null, null);

                    message = dao.updateUserRoleMgt(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("User role " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(UserRoleMgtAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("User role " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String Delete() {
        System.out.println("called UserRoleMgtAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            UserRoleMgtDAO dao = new UserRoleMgtDAO();
            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.USER_ROLE_MGT_PAGE, SectionVarList.USERMANAGEMENT, "User role code " + inputBean.getUserRoleCode() + " deleted", null);
            message = dao.deleteUserRoleMgt(inputBean, audit);
            if (message.isEmpty()) {
                message = "User role " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(UserRoleMgtAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
        }
        return retType;
    }

    public String List() {
        System.out.println("called UserRoleMgtAction: List");
        try {
            //if (inputBean.isSearch()) {

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }
            UserRoleMgtDAO dao = new UserRoleMgtDAO();
            List<UserRoleMgtBean> dataList = dao.getSearchList(inputBean, to, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("User Role Code", inputBean.getS_userrolecode())
                        + checkEmptyorNullString("Description", inputBean.getS_description())
                        + checkEmptyorNullString("Status", inputBean.getS_status())
                        + "]";
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.USER_ROLE_MGT_PAGE, SectionVarList.USERMANAGEMENT, "User role management search using " + searchParameters + " parameters ", null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);
            }

            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                inputBean.setRecords(records);
                inputBean.setGridModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);
            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }
            // }
        } catch (Exception e) {
            Logger.getLogger(UserRoleMgtAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " User role");
        }
        return "list";
    }

    public String Find() {
        System.out.println("called UserRoleMgtAction: Find");
        Userrole userrole = null;
        try {
            if (inputBean.getUserRoleCode() != null && !inputBean.getUserRoleCode().isEmpty()) {

                UserRoleMgtDAO dao = new UserRoleMgtDAO();

                userrole = dao.findUserRoleById(inputBean.getUserRoleCode());

                inputBean.setUserRoleCode(userrole.getUserrolecode());
                inputBean.setDescription(userrole.getDescription());
//                inputBean.setUserRoleLevel(Integer.toString(userrole.getUserlevel().getLevelid()));
                inputBean.setStatus(userrole.getStatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty User role code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("User role " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRoleMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.USER_ROLE_MGT_PAGE, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Task task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
                    inputBean.setVdelete(false);
//                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                    inputBean.setVupdatelink(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                }
            }
        }
        inputBean.setVupdatebutt(true);

        return true;
    }

    private String validateInsertsInputs() {
        String message = "";
        if (inputBean.getUserRoleCode() == null || inputBean.getUserRoleCode().trim().isEmpty()) {
            message = MessageVarList.USER_ROLE_MGT_EMPTY_USER_ROLE_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.USER_ROLE_MGT_EMPTY_DESCRIPTION;
        } //        else if (inputBean.getUserRoleLevel() == null || inputBean.getUserRoleLevel().trim().isEmpty()) {
        //            message = MessageVarList.USER_ROLE_MGT_EMPTY_USER_ROLE_LEVEL;
        //        } 
        else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
            message = MessageVarList.USER_ROLE_MGT_EMPTY_STATUS;
        } else {
            if (!Validation.isSpecailCharacter(inputBean.getUserRoleCode())) {
                message = MessageVarList.USER_ROLE_MGT_ERROR_USER_ROLE_CODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
                message = MessageVarList.USER_ROLE_MGT_ERROR_DESC_INVALID;
            }
        }
        return message;
    }

    private String validateUpdateInputs() {
        String message = "";
        if (inputBean.getUserRoleCode() == null || inputBean.getUserRoleCode().trim().isEmpty()) {
            message = MessageVarList.USER_ROLE_MGT_EMPTY_USER_ROLE_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.USER_ROLE_MGT_EMPTY_DESCRIPTION;
        } //        else if (inputBean.getUserRoleLevel() == null || inputBean.getUserRoleLevel().trim().isEmpty()) {
        //            message = MessageVarList.USER_ROLE_MGT_EMPTY_USER_ROLE_LEVEL;
        //        } 
        else if (inputBean.getStatus() != null && inputBean.getStatus().isEmpty()) {
            message = MessageVarList.USER_ROLE_MGT_EMPTY_STATUS;
        } else {
            if (!Validation.isSpecailCharacter(inputBean.getUserRoleCode())) {
                message = MessageVarList.USER_ROLE_MGT_ERROR_USER_ROLE_CODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
                message = MessageVarList.USER_ROLE_MGT_ERROR_DESC_INVALID;
            } else {
//                try {
//                    Integer.parseInt(inputBean.getUserRoleLevel());
//                } catch (Exception e) {
//                    message = MessageVarList.USER_ROLE_MGT_ERROR_USER_ROLE_LEVEL_INVALID;
//                }
            }
        }
        return message;
    }
}
