/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.controlpanel.systemconfig;

/**
 *
 * @author jayana_i
 */
import com.rawmakyamu.bean.controlpanel.systemconfig.IBMobUserParamBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.IBMobUserParamInputBean;
import com.rawmakyamu.dao.controlpanel.systemconfig.IBMobUserParamDAO;
import com.rawmakyamu.dao.systemaudit.SystemAuditDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import static com.rawmakyamu.util.common.Common.checkEmptyorNullString;
import com.rawmakyamu.util.common.Validation;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.varlist.MessageVarList;
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

public class IBMobUserParamAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    IBMobUserParamInputBean inputBean = new IBMobUserParamInputBean();

    @Override
    public String execute() throws Exception {
        System.out.println("called IBMobUserParamAction : execute");
        return SUCCESS;
    }

    public Object getModel() {
        return inputBean;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.IB_MOB_USER_PARAM_PAGE;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("update".equals(method)) {
            task = TaskVarList.UPDATE_TASK;
        } else if ("find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("list".equals(method)) {
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

    public String view() {
        String result = "view";

        try {
            if (this.applyUserPrivileges()) {

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

            System.out.println("called IBMobUserParamAction: view");

        } catch (Exception e) {
            addActionError("Common configuration " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(IBMobUserParamAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String find() {
        System.out.println("called IBMobUserParamAction: Find");

        IBMobUserParamBean bean = new IBMobUserParamBean();
        try {
            if (inputBean.getParamCode() != null && !inputBean.getParamCode().isEmpty()) {

                IBMobUserParamDAO dao = new IBMobUserParamDAO();

                bean = dao.findListnerConfigurationById(inputBean.getParamCode());

                inputBean.setParamCode(bean.getParamcode());
                inputBean.setDescription(bean.getDescription());
                inputBean.setValue(bean.getValue());
                inputBean.setLastupdateduser(bean.getLastupdateduser());
                inputBean.setLastupdatedtime(bean.getLastupdatedtime());
                inputBean.setCreatedtime(bean.getCreatedtime());

            } else {
                inputBean.setMessage("No MS user parameter id is found");
            }

        } catch (Exception ex) {
            inputBean.setMessage("Common configuration " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(IBMobUserParamAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

//    public String list() {
//        System.out.println("called IBMobUserParamAction: List");
//        try {
//
//            int rows = inputBean.getRows();
//            int page = inputBean.getPage();
//            int to = (rows * page);
//            int from = to - rows;
//            long records = 0;
//            String orderBy = "";
//            if (!inputBean.getSidx().isEmpty()) {
//                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
//            }
//
//            IBMobUserParamDAO dao = new IBMobUserParamDAO();
//            List<IBMobUserParamBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
//            if (!dataList.isEmpty()) {
//                records = dataList.get(0).getFullCount();
//                inputBean.setRecords(records);
//                inputBean.setGridModel(dataList);
//                int total = (int) Math.ceil((double) records / (double) rows);
//                inputBean.setTotal(total);
//            } else {
//                inputBean.setRecords(0L);
//                inputBean.setTotal(0);
//            }
//        } catch (Exception e) {
//            Logger.getLogger(IBMobUserParamAction.class.getName()).log(Level.SEVERE, null, e);
//            addActionError(" IBM user parameter" + MessageVarList.COMMON_ERROR_PROCESS);
//        }
//        return "list";
//    }
    public String list() {
        System.out.println("called IBMobUserParamAction: List");
        try {

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }
            IBMobUserParamDAO dao = new IBMobUserParamDAO();
            List<IBMobUserParamBean> dataList = dao.getSearchList(inputBean, to, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {
                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Parameter Code", inputBean.getS_paramcode())
                        + checkEmptyorNullString("Description", inputBean.getS_description())
                        + checkEmptyorNullString("Parameter Value", inputBean.getS_value())
                        + "]";

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.IB_MOB_USER_PARAM_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Common configuration search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(IBMobUserParamAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Common configuration  " + MessageVarList.COMMON_ERROR_PROCESS);
        }
        return "list";
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.IB_MOB_USER_PARAM_PAGE, request);

        inputBean.setVadd(true);
        inputBean.setVsearch(true);
        inputBean.setVupdatelink(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Task task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
//                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
//                    inputBean.setVdelete(false);
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

    public String update() {

        System.out.println("called IBMobUserParamAction : update");
        String result = "message";

        try {
            if (inputBean.getParamCode() != null && !inputBean.getParamCode().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    IBMobUserParamDAO dao = new IBMobUserParamDAO();
                    String newv = inputBean.getParamCode() + "|"
                            + inputBean.getDescription() + "|"
                            + inputBean.getValue();

                    System.out.println("old value: " + inputBean.getOldvalue());
                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.IB_MOB_USER_PARAM_PAGE, SectionVarList.SYSTEM_CONFIG, "Common configuration updated", null, inputBean.getOldvalue(), newv);
                    message = dao.updateListnerConfiguration(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Common configuration " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(IBMobUserParamAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Common configuration  " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return result;
    }

    public String detail() {
        System.out.println("called IBMobUserParamAction : detail");
        IBMobUserParamBean tt = null;
        try {
            if (inputBean.getParamCode() != null && !inputBean.getParamCode().isEmpty()) {

                IBMobUserParamDAO dao = new IBMobUserParamDAO();

                tt = dao.findListnerConfigurationById(inputBean.getParamCode());

                inputBean.setParamCode(tt.getParamcode());
                inputBean.setDescription(tt.getDescription());
                inputBean.setValue(tt.getValue());

            } else {
                inputBean.setMessage("Empty Param code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Common configuration " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(IBMobUserParamAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";

    }

    private String validateInputs() throws Exception {
        String message = "";

        if (inputBean.getParamCode() == null || inputBean.getParamCode().trim().isEmpty()) {
            message = MessageVarList.IB_MOB_USER_PARAM_EMPTY_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().isEmpty()) {
            message = MessageVarList.IB_MOB_USER_PARAM_EMPTY_DESCRIPTION;
        } //        else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
        //            message = MessageVarList.IB_MOB_USER_PARAM_INVALID_DESCRIPTION;
        //        } 
        else if (inputBean.getValue() == null || inputBean.getValue().isEmpty()) {
            message = MessageVarList.IB_MOB_USER_PARAM_EMPTY_VALUE;
        }
//            else if (!Validation.isAlphaNumeric(inputBean.getValue())) {
//            message = MessageVarList.IB_MOB_USER_PARAM_INVALID_VALUE;
//        }
        return message;
    }
}
