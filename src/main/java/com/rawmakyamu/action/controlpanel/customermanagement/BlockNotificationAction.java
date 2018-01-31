/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.controlpanel.customermanagement;

import com.rawmakyamu.bean.controlpanel.custmanagement.BlockNotificationDataBean;
import com.rawmakyamu.bean.controlpanel.custmanagement.BlockNotificationInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.controlpanel.customermanagement.BlockNotificationDAO;
import com.rawmakyamu.dao.systemaudit.SystemAuditDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import static com.rawmakyamu.util.common.Common.checkEmptyorNullString;
import com.rawmakyamu.util.mapping.MsCustomerWalletPushBlock;
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
 * @author prathibha_s
 */
public class BlockNotificationAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    BlockNotificationInputBean inputBean = new BlockNotificationInputBean();

    @Override
    public String execute() throws Exception {
        System.out.println("called BlockNotificationAction : execute");
        return SUCCESS;
    }

    public Object getModel() {
        return inputBean;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.BLOCK_NOTIFICATION_PAGE;
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

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.BLOCK_NOTIFICATION_PAGE, request);

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

    public String view() throws Exception {
        String result = "view";
        System.out.println("called BlockNotificationAction :view");

        try {

            if (this.applyUserPrivileges()) {

                inputBean.setDefaultStatus(CommonVarList.STATUS_BLOCK);

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
            addActionError("Block notification " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(BlockNotificationAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String list() {
        System.out.println("called BlockNotificationAction: List");
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
            BlockNotificationDAO dao = new BlockNotificationDAO();
            List<BlockNotificationDataBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Wallet ID", inputBean.getWalletIdSearch())
                        + checkEmptyorNullString("Status", inputBean.getStatusSearch())
                        + checkEmptyorNullString("From Date", inputBean.getFromdateSearch())
                        + checkEmptyorNullString("To Date", inputBean.getTodateSearch())
                        + "]";

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.BLOCK_NOTIFICATION_PAGE, SectionVarList.NOTIFICATION_MGT, "Block notification search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(BlockNotificationAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Block notification");
        }
        return "list";
    }

    public String ViewPopup() {
        
        System.out.println("called BlockNotificationAction :viewpopup");
        String result = "viewpopup";
        
        try {
            if (this.applyUserPrivileges()) {

                inputBean.setDefaultStatus(CommonVarList.STATUS_BLOCK);

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
            addActionError("Block notification " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(BlockNotificationAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String find() {
        
        System.out.println("called BlockNotificationAction: find");
        MsCustomerWalletPushBlock block = null;
        try {
            if (inputBean.getWalletId() != null && !inputBean.getWalletId().isEmpty()) {

                BlockNotificationDAO dao = new BlockNotificationDAO();

                block = dao.findBNById(inputBean.getWalletId());

                inputBean.setWalletId(block.getWalletId());
                if (block.getFromdate() != null) {
                    inputBean.setFromdate(block.getFromdate().toString());
                }
                if (block.getTodate() != null) {
                    inputBean.setTodate(block.getTodate().toString());
                }
                inputBean.setStatus(block.getStatus());

            } else {
                inputBean.setMessage("Empty block notification wallet ID.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Block notification " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(BlockNotificationAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String Detail() {
        System.out.println("called BlockNotificationAction: detail");
        MsCustomerWalletPushBlock block = null;
        try {
            if (inputBean.getWalletId() != null && !inputBean.getWalletId().isEmpty()) {

                BlockNotificationDAO dao = new BlockNotificationDAO();

                inputBean.setDefaultStatus(CommonVarList.STATUS_BLOCK);

                block = dao.findBNById(inputBean.getWalletId());

                inputBean.setWalletId(block.getWalletId());

                if (block.getFromdate() != null) {
                    inputBean.setFromdate(block.getFromdate().toString());
                }
                if (block.getTodate() != null) {
                    inputBean.setTodate(block.getTodate().toString());
                }
                inputBean.setStatus(block.getStatus());

            } else {
                inputBean.setMessage("Empty block notification wallet ID.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Block notification " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(BlockNotificationAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";

    }

    public String add() {
        System.out.println("called BlockNotificationAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            BlockNotificationDAO dao = new BlockNotificationDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.BLOCK_NOTIFICATION_PAGE, SectionVarList.NOTIFICATION_MGT, "Block notification wallet id " + inputBean.getWalletId() + " added", null, null, null);
                message = dao.insertBN(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Block notification " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Block notification " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(BlockNotificationAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private String validateInputs() {
        CommonDAO dao = new CommonDAO();
        String message = "";
        if (inputBean.getWalletId() == null || inputBean.getWalletId().trim().isEmpty()) {
            message = MessageVarList.BLOCK_NOTIFICATION_EMPTY_WALLET_ID;
        } else {
            if (inputBean.getWalletId().length() < 10) {
                message = MessageVarList.BLOCK_NOTIFICATION_INVALID_WALLET_ID;
            }
        }

        if (inputBean.getFromdate() != null || !inputBean.getFromdate().trim().isEmpty()) {
            if (inputBean.getTodate() != null || !inputBean.getTodate().trim().isEmpty()) {
                message = dao.compareDates(inputBean.getFromdate(), inputBean.getTodate());
            }
        }
        return message;
    }

    public String update() {

        System.out.println("called BlockNotificationAction : update");
        String retType = "message";

        try {
            if (inputBean.getWalletId() != null && !inputBean.getWalletId().isEmpty()) {

                //set username get in hidden fileds
                inputBean.setWalletId(inputBean.getWalletId());

                String message = this.validateUpdates();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    BlockNotificationDAO dao = new BlockNotificationDAO();

                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.BLOCK_NOTIFICATION_PAGE, SectionVarList.NOTIFICATION_MGT, "Block notification wallet ID " + inputBean.getWalletId() + " updated", null, null, null);
                    message = dao.updateBN(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Block notification " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BlockNotificationAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Block notification " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    private String validateUpdates() {
        String message = "";
        CommonDAO dao = new CommonDAO();
//        if (inputBean.getFromdate() == null || inputBean.getFromdate().trim().isEmpty()) {
//            message = MessageVarList.BLOCK_NOTIFICATION_EMPTY_FROM_DATE;
//        } else if (inputBean.getTodate() == null || inputBean.getTodate().trim().isEmpty()) {
//            message = MessageVarList.BLOCK_NOTIFICATION_EMPTY_TO_DATE;
//        }

        if (inputBean.getFromdate() != null || !inputBean.getFromdate().trim().isEmpty()) {
            if (inputBean.getTodate() != null || !inputBean.getTodate().trim().isEmpty()) {
                message = dao.compareDates(inputBean.getFromdate(), inputBean.getTodate());
            }
        }
        return message;
    }

    public String delete() {
        System.out.println("called BlockNotificationAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            BlockNotificationDAO dao = new BlockNotificationDAO();
            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.BLOCK_NOTIFICATION_PAGE, SectionVarList.NOTIFICATION_MGT, "Block notification wallet ID " + inputBean.getWalletId() + " deleted", null);
            message = dao.deleteBN(inputBean, audit);
            if (message.isEmpty()) {
                message = "Block notification " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(BlockNotificationAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }
}
