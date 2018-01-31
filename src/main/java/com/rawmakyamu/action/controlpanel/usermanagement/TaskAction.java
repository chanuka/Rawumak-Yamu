/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.controlpanel.usermanagement;

import com.rawmakyamu.bean.controlpanel.usermanagement.TaskBean;
import com.rawmakyamu.bean.controlpanel.usermanagement.TaskInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.systemaudit.SystemAuditDAO;
import com.rawmakyamu.dao.controlpanel.usermanagement.TaskDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import static com.rawmakyamu.util.common.Common.checkEmptyorNullString;
import com.rawmakyamu.util.common.Validation;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
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
 * @author chanuka
 */
public class TaskAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    /**
     *
     */
    private static final long serialVersionUID = -3848060911824408165L;
    /**
     *
     */
    TaskInputBean inputBean = new TaskInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called TaskAction : execute");
        return SUCCESS;
    }

    public String View() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
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
            System.out.println("called TaskAction :view");

        } catch (Exception ex) {
            addActionError("Task " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String ViewPopup() {
        String result = "viewpopup";
        System.out.println("called TaskAction : ViewPopup");
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
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

        } catch (Exception ex) {
            addActionError("Task " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //start newly chnged    
    public String activate() {
        System.out.println("called taskAction : activate");
        String message = null;
        String retType = "activate";

        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            TaskDAO dao = new TaskDAO();
            message = this.validateInputs();
            if (message.isEmpty()) {
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.TASK_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Task code " + inputBean.getTaskCode() + " updated", null);
                message = dao.activateTask(inputBean, audit);
                if (message.isEmpty()) {
                    message = "Task " + MessageVarList.COMMON_SUCC_ACTIVATE;
                }
                inputBean.setMessage(message);

            } else {
                addActionError(message);
            }

        } catch (Exception e) {
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(MessageVarList.COMMON_ERROR_ACTIVATE);
        }
        return retType;
    }//end newly changed

    public String Add() {
        System.out.println("called taskAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            TaskDAO dao = new TaskDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.TASK_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Task code " + inputBean.getTaskCode() + " added", null, null, null);
                message = dao.insertPage(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Task " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Task " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String Update() {

        System.out.println("called taskAction : update");
        String retType = "message";

        try {
            if (inputBean.getTaskCode() != null && !inputBean.getTaskCode().isEmpty()) {
                TaskDAO dao = new TaskDAO();
                //set username get in hidden fileds
                inputBean.setTaskCode(inputBean.getTaskCode());

                String message = this.validateUpdates();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();

                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.TASK_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Task code " + inputBean.getTaskCode() + " updated", null, null, null);
                    message = dao.updateTask(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Task " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Task " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String Delete() {
        System.out.println("called TaskAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            TaskDAO dao = new TaskDAO();
            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.TASK_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Task code " + inputBean.getTaskCode() + " deleted", null);
            message = dao.deleteTask(inputBean, audit);
            if (message.isEmpty()) {
                message = "Task " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
//            inputBean.setMessage(MessageVarList.COMMON_ERROR_DELETE);
        }
        return retType;
    }

    public String List() {
        System.out.println("called TaskAction: List");
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
            TaskDAO dao = new TaskDAO();
            List<TaskBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Task Code", inputBean.getTaskCodeSearch())
                        + checkEmptyorNullString("Description", inputBean.getDescriptionSearch())
                        + checkEmptyorNullString("Status", inputBean.getStatusSearch())
                        + "]";
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.TASK_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Task management search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(" Task " + MessageVarList.COMMON_ERROR_PROCESS);
        }
        return "list";
    }

    public String Find() {
        System.out.println("called TaskAction: Find");
        Task task = null;
        try {
            if (inputBean.getTaskCode() != null && !inputBean.getTaskCode().isEmpty()) {

                TaskDAO dao = new TaskDAO();

                task = dao.findTaskById(inputBean.getTaskCode());

                inputBean.setTaskCode(task.getTaskcode());
                inputBean.setDescription(task.getDescription());
//                inputBean.setSortKey(task.getSortkey().toString());
                inputBean.setStatus(task.getStatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty task code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Task " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String Detail() {
        System.out.println("called TaskAction: Detail");
        Task task = null;
        try {
            if (inputBean.getTaskCode() != null && !inputBean.getTaskCode().isEmpty()) {

                TaskDAO dao = new TaskDAO();
                CommonDAO commonDAO = new CommonDAO();

                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setDefaultStatus(CommonVarList.STATUS_ACTIVE);

                task = dao.findTaskById(inputBean.getTaskCode());

                inputBean.setTaskCode(task.getTaskcode());
                inputBean.setDescription(task.getDescription());
//                inputBean.setSortKey(task.getSortkey().toString());
                inputBean.setStatus(task.getStatus().getStatuscode());

            } else {
                inputBean.setMessage("Empty task code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Task " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.TASK_MGT_PAGE, request);

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

    private String validateInputs() {
        String message = "";
        if (inputBean.getTaskCode() == null || inputBean.getTaskCode().trim().isEmpty()) {
            message = MessageVarList.TASK_MGT_EMPTY_TASK_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.TASK_MGT_EMPTY_DESCRIPTION;
        } //        else if (inputBean.getSortKey() == null || inputBean.getSortKey().trim().isEmpty()) {
        //            message = MessageVarList.TASK_MGT_EMPTY_SORTKEY;
        //        } 
        else if (inputBean.getStatus() != null && inputBean.getStatus().isEmpty()) {
            message = MessageVarList.TASK_MGT_EMPTY_STATUS;
        } else if (!Validation.isSpecailCharacter(inputBean.getTaskCode())) {
            message = MessageVarList.TASK_MGT_ERROR_TASKCODE_INVALID;
        } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
            message = MessageVarList.TASK_MGT_ERROR_DESC_INVALID;
        }
//        else {
//            try {
//                new Integer(inputBean.getSortKey());
//            } catch (Exception e) {
//                message = MessageVarList.TASK_MGT_ERROR_SORTKEY_INVALID;
//            }
//            try {
//
//                CommonDAO dao = new CommonDAO();
//                message = dao.getTaskSortKeyCount(inputBean.getSortKey());
//
//            } catch (Exception e) {
//                message = MessageVarList.TASK_MGT_ERROR_SORTKEY_INVALID;
//            }
//
//        }
        return message;
    }

    private String validateUpdates() {
        String message = "";
        if (inputBean.getTaskCode() == null || inputBean.getTaskCode().trim().isEmpty()) {
            message = MessageVarList.TASK_MGT_EMPTY_TASK_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.TASK_MGT_EMPTY_DESCRIPTION;
        } //        else if (inputBean.getSortKey() == null || inputBean.getSortKey().trim().isEmpty()) {
        //            message = MessageVarList.TASK_MGT_EMPTY_SORTKEY;
        //        } 
        else if (inputBean.getStatus() != null && inputBean.getStatus().isEmpty()) {
            message = MessageVarList.TASK_MGT_EMPTY_STATUS;
        } else if (!Validation.isSpecailCharacter(inputBean.getTaskCode())) {
            message = MessageVarList.TASK_MGT_ERROR_TASKCODE_INVALID;
        } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
            message = MessageVarList.TASK_MGT_ERROR_DESC_INVALID;
        }

//        else {
//            try {
//                new Integer(inputBean.getSortKey());
//            } catch (Exception e) {
//                message = MessageVarList.TASK_MGT_ERROR_SORTKEY_INVALID;
//            }
//            try {
//                Task task = null;
//                TaskDAO tDao = new TaskDAO();
//                task = tDao.findTaskById(inputBean.getTaskCode());
//
//                inputBean.setOldsortkey(task.getSortkey().toString());
//
//                System.err.println("Old " + inputBean.getOldsortkey());
//                System.err.println("New " + inputBean.getSortKey());
//
//                CommonDAO dao = new CommonDAO();
//                if(inputBean.getSortKey().equals(inputBean.getOldsortkey())){
//                    message = dao.getTaskSortKeyCountUpdate(inputBean.getSortKey(), inputBean.getOldsortkey());
//                }else{
//                    
//                    message=MessageVarList.TASK_MGT_SORTKEY_ALREADY_EXSISTS;
//                }
//            } catch (Exception e) {
//                message = MessageVarList.TASK_MGT_ERROR_SORTKEY_INVALID;
//            }
//
//        }
        return message;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.TASK_MGT_PAGE;
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
        } else if ("Detail".equals(method)) {
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
}
