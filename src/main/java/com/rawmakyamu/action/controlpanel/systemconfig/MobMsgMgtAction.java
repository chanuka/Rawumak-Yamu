/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.controlpanel.systemconfig;

import com.rawmakyamu.bean.controlpanel.systemconfig.MobMsgBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.MobMsgInputBean;
import com.rawmakyamu.dao.controlpanel.systemconfig.MobMsgMgtDAO;
import com.rawmakyamu.dao.systemaudit.SystemAuditDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.varlist.MessageVarList;
import com.rawmakyamu.util.varlist.PageVarList;
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
import static com.rawmakyamu.util.common.Common.checkEmptyorNullString;
import com.rawmakyamu.util.mapping.MsUserMessage;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.varlist.SectionVarList;

/**
 *
 * @author dilanka_w
 */
public class MobMsgMgtAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    MobMsgInputBean inputBean = new MobMsgInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called MobMsgMgtAction: execute");
        return SUCCESS;
    }

    public String view() {
        System.out.println("called MobMsgMgtAction :view");
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

        } catch (Exception ex) {
            addActionError("Mobile message mgt " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MobMsgMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String list() {
        System.out.println("called MobMsgMgtAction: List");
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
            MobMsgMgtDAO dao = new MobMsgMgtDAO();
            List<MobMsgBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {
                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Parameter Code", inputBean.getParamCodeSearch())
                        + checkEmptyorNullString("Description", inputBean.getDescriptionSearch())
                        + checkEmptyorNullString("Parameter Value", inputBean.getValueSearch())
                        + "]";

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.MOB_MSG_MGT_PAGE, SectionVarList.SYSTEM_CONFIG, "Mobile message management search using " + searchParameters + " parameters ", null);
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

        } catch (Exception e) {
            Logger.getLogger(MobMsgMgtAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Mobile message mgt");
        }
        return "list";
    }

    public String find() {
        System.out.println("called MobMsgMgtAction: find");
        MsUserMessage userMsg = null;

        try {

            if (inputBean.getParamCode() != null && !inputBean.getParamCode().isEmpty()) {

                MobMsgMgtDAO dao = new MobMsgMgtDAO();

                userMsg = dao.findMobMsgByCode(inputBean.getParamCode());

                inputBean.setParamCode(userMsg.getParamcode());
                inputBean.setDescription(userMsg.getDescription());
                inputBean.setValue(userMsg.getParamValue());

            } else {
                inputBean.setMessage("Empty parameter code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Mobile message mgt " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MobMsgMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String Detail() {

        System.out.println("called MobMsgMgtAction: Detail");
        MsUserMessage userMsg = null;

        try {

            if (inputBean.getParamCode() != null && !inputBean.getParamCode().isEmpty()) {

                MobMsgMgtDAO dao = new MobMsgMgtDAO();

                userMsg = dao.findMobMsgByCode(inputBean.getParamCode());

                inputBean.setParamCode(userMsg.getParamcode());
                inputBean.setDescription(userMsg.getDescription());
                inputBean.setValue(userMsg.getParamValue());

            } else {
                inputBean.setMessage("Empty parameter code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Mobile message mgt " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MobMsgMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";

    }

    public String update() {

        System.out.println("called MobMsgMgtAction : update");
        String retType = "message";
        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            MobMsgMgtDAO dao = new MobMsgMgtDAO();

            if (inputBean.getParamCode() != null && !inputBean.getParamCode().isEmpty()) {

                String message = this.validateInputsForUpdate();

                if (message.isEmpty()) {

                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.MOB_MSG_MGT_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Parameter Code" + inputBean.getParamCode() + " updated", null, null, null);
                    message = dao.updateMobMsg(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Mobile message " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            inputBean.setMessage("mobile message mgt " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(MobMsgMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retType;
    }

    private String validateInputsForUpdate() {
        String message = "";

        if (inputBean.getParamCode() == null || inputBean.getParamCode().trim().isEmpty()) {
            message = MessageVarList.MOB_MSG_MGT_EMPTY_PARAMETER_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.MOB_MSG_MGT_EMPTY_DESCRIPTION;
        } else if (inputBean.getValue() == null || inputBean.getValue().trim().isEmpty()) {
            message = MessageVarList.MOB_MSG_MGT_EMPTY_VALUE;
        }

        return message;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.MOB_MSG_MGT_PAGE, request);

        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Task task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
                    inputBean.setVdelete(false);
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

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.MOB_MSG_MGT_PAGE;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("list".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("update".equals(method)) {
            task = TaskVarList.UPDATE_TASK;
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
