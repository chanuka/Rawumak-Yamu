/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.controlpanel.usermanagement;

import com.rawmakyamu.bean.controlpanel.usermanagement.PageBean;
import com.rawmakyamu.bean.controlpanel.usermanagement.PageInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.systemaudit.SystemAuditDAO;
import com.rawmakyamu.dao.controlpanel.usermanagement.PageDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import static com.rawmakyamu.util.common.Common.checkEmptyorNullString;
import com.rawmakyamu.util.common.Validation;
import com.rawmakyamu.util.mapping.Page;
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
 * @author chalitha
 */
public class PageAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    PageInputBean inputBean = new PageInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called PageAction : execute");
        return SUCCESS;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.PAGE_MGT_PAGE, request);

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
        String page = PageVarList.PAGE_MGT_PAGE;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("list".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("add".equals(method)) {
            task = TaskVarList.ADD_TASK;
        } else if ("delete".equals(method)) {
            task = TaskVarList.DELETE_TASK;
        } else if ("find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("update".equals(method)) {
            task = TaskVarList.UPDATE_TASK;
        } else if ("Detail".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("ViewPopup".equals(method)) {
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

        System.out.println("called PageAction :view");
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

        } catch (Exception ex) {
            addActionError("Page " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String ViewPopup() {

        System.out.println("called PageAction :viewpopup");
        String result = "viewpopup";

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
            addActionError("Page " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String list() {
        System.out.println("called PageAction: List");
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
            PageDAO dao = new PageDAO();
            List<PageBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Page Code", inputBean.getPageCodeSearch())
                        + checkEmptyorNullString("Description", inputBean.getDescriptionSearch())
                        + checkEmptyorNullString("Sort Key", inputBean.getSortKeySearch())
                        + checkEmptyorNullString("URL", inputBean.getUrlSearch())
                        + checkEmptyorNullString("Status", inputBean.getStatussearch())
                        + "]";

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.PAGE_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Page management search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Page");
        }
        return "list";
    }

    public String add() {
        System.out.println("called PageAction : add");
        String result = "message";

        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            PageDAO dao = new PageDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.PAGE_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Page code " + inputBean.getPageCode() + " added", null, null, null);
                message = dao.insertPage(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Page " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Page " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String update() {

        System.out.println("called PageAction : update");
        String retType = "message";

        try {
            if (inputBean.getPageCode() != null && !inputBean.getPageCode().isEmpty()) {

                //set username get in hidden fileds
                inputBean.setPageCode(inputBean.getPageCode());

                String message = this.validateUpdates();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    PageDAO dao = new PageDAO();

                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.PAGE_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Page code " + inputBean.getPageCode() + " updated", null, null, null);
                    message = dao.updatePage(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Page " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Page " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String delete() {

        System.out.println("called PageAction : Delete");
        String message = null;
        String retType = "delete";

        try {

            HttpServletRequest request = ServletActionContext.getRequest();

            PageDAO dao = new PageDAO();

            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.PAGE_MGT_PAGE, SectionVarList.USERMANAGEMENT, "Page code " + inputBean.getPageCode() + " deleted", null);

            message = dao.deletePage(inputBean, audit);
            if (message.isEmpty()) {
                message = "Page " + MessageVarList.COMMON_SUCC_DELETE;
            }

            inputBean.setMessage(message);

        } catch (Exception e) {
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String find() {
        System.out.println("called PageAction: find");
        Page Page = null;
        try {
            if (inputBean.getPageCode() != null && !inputBean.getPageCode().isEmpty()) {

                PageDAO dao = new PageDAO();

                Page = dao.findPageById(inputBean.getPageCode());

                inputBean.setPageCode(Page.getPagecode());
                inputBean.setDescription(Page.getDescription());
                inputBean.setSortKey(Page.getSortkey().toString());
                inputBean.setStatus(Page.getStatus().getStatuscode());
                inputBean.setUrl(Page.getUrl());

            } else {
                inputBean.setMessage("Empty Page code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Page " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String Detail() {
        System.out.println("called PageAction: detail");
        Page Page = null;
        try {
            if (inputBean.getPageCode() != null && !inputBean.getPageCode().isEmpty()) {

                PageDAO dao = new PageDAO();
                CommonDAO commonDAO = new CommonDAO();
                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setDefaultStatus(CommonVarList.STATUS_ACTIVE);

                Page = dao.findPageById(inputBean.getPageCode());

                inputBean.setPageCode(Page.getPagecode());
                inputBean.setDescription(Page.getDescription());
                inputBean.setSortKey(Page.getSortkey().toString());
                inputBean.setStatus(Page.getStatus().getStatuscode());
                inputBean.setUrl(Page.getUrl());

            } else {
                inputBean.setMessage("Empty Page code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Page " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PageAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";

    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getPageCode() == null || inputBean.getPageCode().trim().isEmpty()) {
            message = MessageVarList.PAGE_MGT_EMPTY_PAGE_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.PAGE_MGT_EMPTY_DESCRIPTION;
        } else if (inputBean.getUrl() == null || inputBean.getUrl().trim().isEmpty()) {
            message = MessageVarList.PAGE_MGT_EMPTY_URL;
        } else if (inputBean.getSortKey() == null || inputBean.getSortKey().trim().isEmpty()) {
            message = MessageVarList.PAGE_MGT_EMPTY_SORTKEY;
        } else if (inputBean.getStatus() != null && inputBean.getStatus().isEmpty()) {
            message = MessageVarList.PAGE_MGT_EMPTY_STATUS;
        } else {
            if (!Validation.isSpecailCharacter(inputBean.getPageCode())) {
                message = MessageVarList.PAGE_MGT_ERROR_PAGE_CODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
                message = MessageVarList.PAGE_MGT_ERROR_DESC_INVALID;
            } else if (!inputBean.getUrl().startsWith("/")) {
                message = MessageVarList.PAGE_MGT_ERROR_URL_INVALID;
            } else {
                try {
                    new Integer(inputBean.getSortKey());
                } catch (Exception e) {
                    message = MessageVarList.PAGE_MGT_ERROR_SORTKEY_INVALID;
                }
                try {
                    CommonDAO dao = new CommonDAO();
                    message = dao.getPageSortKeyCount(inputBean.getSortKey());

                } catch (Exception e) {
                    message = MessageVarList.PAGE_MGT_ERROR_SORT_KEY_ALREADY_EXSITS;
                }
            }
        }
        return message;
    }

    private String validateUpdates() {
        String message = "";
        if (inputBean.getPageCode() == null || inputBean.getPageCode().trim().isEmpty()) {
            message = MessageVarList.PAGE_MGT_EMPTY_PAGE_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.PAGE_MGT_EMPTY_DESCRIPTION;
        } else if (inputBean.getUrl() == null || inputBean.getUrl().trim().isEmpty()) {
            message = MessageVarList.PAGE_MGT_EMPTY_URL;
        } else if (inputBean.getSortKey() == null || inputBean.getSortKey().trim().isEmpty()) {
            message = MessageVarList.PAGE_MGT_EMPTY_SORTKEY;
        } else if (inputBean.getStatus() != null && inputBean.getStatus().isEmpty()) {
            message = MessageVarList.PAGE_MGT_EMPTY_STATUS;
        } else {
            if (!Validation.isSpecailCharacter(inputBean.getPageCode())) {
                message = MessageVarList.PAGE_MGT_ERROR_PAGE_CODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
                message = MessageVarList.PAGE_MGT_ERROR_DESC_INVALID;
            } else if (!inputBean.getUrl().startsWith("/")) {
                message = MessageVarList.PAGE_MGT_ERROR_URL_INVALID;
            } else {
                try {
                    new Integer(inputBean.getSortKey());
                } catch (Exception e) {
                    message = MessageVarList.PAGE_MGT_ERROR_SORTKEY_INVALID;
                }
            }
        }
        return message;
    }

}
