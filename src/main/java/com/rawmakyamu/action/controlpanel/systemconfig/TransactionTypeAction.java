/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.controlpanel.systemconfig;

import com.rawmakyamu.bean.controlpanel.systemconfig.TransactionTypeBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.TransactionTypeInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.systemaudit.SystemAuditDAO;
import com.rawmakyamu.dao.controlpanel.systemconfig.TransactionTypeDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import static com.rawmakyamu.util.common.Common.checkEmptyorNullString;
import com.rawmakyamu.util.common.Validation;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.mapping.Transactiontype;
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
 * @author chathuri_t
 */
public class TransactionTypeAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    TransactionTypeInputBean inputBean = new TransactionTypeInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called TransactionTypeAction : execute");
        return SUCCESS;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.TXN_TYPE_MGT_PAGE;
        String task = null;
        if ("view".equals(method)) {
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

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.TXN_TYPE_MGT_PAGE, request);

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

    public String view() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setDefaultStatus(CommonVarList.STATUS_ACTIVE);
                inputBean.setHistory_visibilityList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_AUTH));

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

            System.out.println("called TransactionTypeAction :View");

        } catch (Exception ex) {
            addActionError("Transaction type " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String detail() {
        System.out.println("called TransactionTypeAction : detail");
        Transactiontype tt = null;
        try {
            if (inputBean.getTransactiontypecode() != null && !inputBean.getTransactiontypecode().isEmpty()) {

                TransactionTypeDAO dao = new TransactionTypeDAO();
                CommonDAO commonDAO = new CommonDAO();
                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setOTPReqstatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_AUTH));
                inputBean.setHistory_visibilityList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_AUTH));
                inputBean.setRiskReqstatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_AUTH));
//                inputBean.setMerchantTxnTypeList(commonDAO.getmerchantTxnTypeList());

                tt = dao.findTransactionTypeById(inputBean.getTransactiontypecode());

                inputBean.setTransactiontypecode(tt.getTypecode());
                inputBean.setDescription(tt.getDescription());
                inputBean.setStatus(tt.getStatus().getStatuscode());
                inputBean.setOTPReqStatus(tt.getOtpRequired());

                if (tt.getSortOrder() != null) {
                    inputBean.setSortOrder(tt.getSortOrder().toString());
                    inputBean.setOldsortKey(tt.getSortOrder().toString());
                }

                inputBean.setRiskReqStatus(tt.getRiskRequired());

                System.out.println("<<<" + tt.getMerchantTxnType() + ">>>>>>>");
                try {
                    inputBean.setMerchantTxnType(tt.getMerchantTxnType());
                } catch (Exception e) {
                    inputBean.setMerchantTxnType("");
                }
                try {
                    inputBean.setHistory_visibility(tt.getHistory_visibility());
                } catch (Exception e) {
                    inputBean.setHistory_visibility("");
                }
                try {
                    inputBean.setDescription_receiver(tt.getDescription_receiver());
                } catch (Exception e) {
                    inputBean.setDescription_receiver("");
                }
                inputBean.setOldvalue(inputBean.getTransactiontypecode() + "|" + inputBean.getDescription() + "|" + inputBean.getStatus());

            } else {
                inputBean.setMessage("Empty TransactionType id.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("TransactionType id  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";

    }

    public String ViewPopup() {
        String result = "viewpopup";
        System.out.println("called TransactionType : ViewPopup");
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setOTPReqstatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_AUTH));
                inputBean.setHistory_visibilityList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_AUTH));
                inputBean.setRiskReqstatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_AUTH));
//                inputBean.setMerchantTxnTypeList(dao.getmerchantTxnTypeList());

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

            System.out.println("called TransactionTypeAction :viewpopup");

        } catch (Exception ex) {
            addActionError("TransactionType " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String Add() {
        System.out.println("called TransactionTypeAction : Add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            TransactionTypeDAO dao = new TransactionTypeDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

//                inputBean.setStatus(CommonVarList.STATUS_ACTIVE);                
//                inputBean.setOTPReqStatus(CommonVarList.STATUS_YES);
                String newV = inputBean.getTransactiontypecode() + "|"
                        + inputBean.getDescription() + "|"
                        + inputBean.getStatus() + "|"
                        + inputBean.getSortOrder() + "|"
                        + inputBean.getOTPReqStatus() + "|"
                        + inputBean.getRiskReqStatus();
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.TXN_TYPE_MGT_PAGE, SectionVarList.SYSTEM_CONFIG, "Transaction type code " + inputBean.getTransactiontypecode() + " added", null, null, newV);
                message = dao.insertTransactionType(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Transaction type " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Transaction type " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private String validateInputs() throws Exception {
        String message = "";
        if (inputBean.getTransactiontypecode() == null || inputBean.getTransactiontypecode().trim().isEmpty()) {
            message = MessageVarList.TXN_TYPE_MGT_EMPTY_TT_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.TXN_TYPE_MGT_EMPTY_DESCRIPTION;
        } else if (inputBean.getStatus() != null && inputBean.getStatus().isEmpty()) {
            message = MessageVarList.TXN_TYPE_MGT_EMPTY_STATUS;
        } else if (inputBean.getOTPReqStatus() != null && inputBean.getOTPReqStatus().isEmpty()) {
            message = MessageVarList.TXN_TYPE_MGT_EMPTY_OTPREQSTATUS;
        } else if (inputBean.getRiskReqStatus() != null && inputBean.getRiskReqStatus().isEmpty()) {
            message = MessageVarList.TXN_TYPE_MGT_EMPTY_RISKREQSTATUS;
        } else if (inputBean.getMerchantTxnType() != null && inputBean.getMerchantTxnType().isEmpty()) {
            message = MessageVarList.TXN_TYPE_MGT_EMPTY_MCTTRANXTYPE;
        } else if (inputBean.getDescription_receiver() != null && inputBean.getDescription_receiver().isEmpty()) {
            message = MessageVarList.TXN_TYPE_MGT_EMPTY_DESCRIPTION_RECIEVE;
        } else if (inputBean.getHistory_visibility() != null && inputBean.getHistory_visibility().isEmpty()) {
            message = MessageVarList.TXN_TYPE_MGT_EMPTY_HISTORY_VISIBILITY;
        } else {
            if (!Validation.isSpecailCharacter(inputBean.getTransactiontypecode())) {
                message = MessageVarList.TXN_TYPE_MGT_ERROR_TT_CODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
                message = MessageVarList.TXN_TYPE_MGT_ERROR_DESC_INVALID;
            }
        }
        if ((inputBean.getSortOrder() != null || !inputBean.getSortOrder().isEmpty()) && !(inputBean.getSortOrder().equals(inputBean.getOldsortKey()))) {
            TransactionTypeDAO dao = new TransactionTypeDAO();
            if (dao.IsSortKeyExist(inputBean.getSortOrder())) {
                message = MessageVarList.TXN_TYPE_MGT_ERROR_SORT_KEY_ALREADY_EXIST;
            }
        }

        return message;
    }

    public String List() {
        System.out.println("called TransactionTypeAction: List");
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
            TransactionTypeDAO dao = new TransactionTypeDAO();
            List<TransactionTypeBean> dataList = dao.getSearchListHQL(inputBean, to, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {
                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Transaction Type Code", inputBean.getS_transactiontypecode())
                        + checkEmptyorNullString("Description", inputBean.getS_description())
                        + checkEmptyorNullString("Status", inputBean.getS_status())
                        + checkEmptyorNullString("Sort key", inputBean.getS_sortOrder())
                        + checkEmptyorNullString("OTP Required", inputBean.getS_OTPReqStatus())
                        + checkEmptyorNullString("Acquirer Risk Required", inputBean.getS_RiskReqStatus())
                        + checkEmptyorNullString("Merchant Txn Type", inputBean.getS_merchantTxnType())
                        + checkEmptyorNullString("Description receiver", inputBean.getS_description_receiver())
                        + checkEmptyorNullString("History visibility", inputBean.getS_history_visibility())
                        + "]";
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.TXN_TYPE_MGT_PAGE, SectionVarList.SYSTEM_CONFIG, "Transaction type search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(" Transaction type " + MessageVarList.COMMON_ERROR_PROCESS);
        }
        return "list";
    }

    public String Find() {
        System.out.println("called TransactionTypeAction : Find");
        Transactiontype tt = null;
        try {
            if (inputBean.getTransactiontypecode() != null && !inputBean.getTransactiontypecode().isEmpty()) {

                TransactionTypeDAO dao = new TransactionTypeDAO();

                tt = dao.findTransactionTypeById(inputBean.getTransactiontypecode());

                inputBean.setTransactiontypecode(tt.getTypecode());
                inputBean.setDescription(tt.getDescription());
                inputBean.setStatus(tt.getStatus().getStatuscode());
                inputBean.setOTPReqStatus(tt.getOtpRequired());
                inputBean.setRiskReqStatus(tt.getRiskRequired());

                if (tt.getSortOrder() != null) {
                    inputBean.setSortOrder(tt.getSortOrder().toString());
                    inputBean.setOldsortKey(tt.getSortOrder().toString());
                }

                System.out.println("<<<" + tt.getMerchantTxnType() + ">>>>>>>");
                try {
                    inputBean.setMerchantTxnType(tt.getMerchantTxnType());
                } catch (Exception ee) {
                    inputBean.setMerchantTxnType("");
                }
                try {
                    inputBean.setHistory_visibility(tt.getHistory_visibility());
                } catch (Exception e) {
                    inputBean.setHistory_visibility("");
                }
                try {
                    inputBean.setDescription_receiver(tt.getDescription_receiver());
                } catch (Exception e) {
                    inputBean.setDescription_receiver("");
                }
                inputBean.setOldvalue(inputBean.getTransactiontypecode() + "|" + inputBean.getDescription() + "|" + inputBean.getStatus() + "|" + inputBean.getOTPReqStatus() + "|" + inputBean.getRiskReqStatus());

            } else {
                inputBean.setMessage("Empty transaction type code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Transaction type  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String Update() {

        System.out.println("called TransactionTypeAction : Update");
        String retType = "message";

        try {
            if (inputBean.getTransactiontypecode() != null && !inputBean.getTransactiontypecode().isEmpty()) {

                inputBean.setTransactiontypecode(inputBean.getTransactiontypecode());

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    TransactionTypeDAO dao = new TransactionTypeDAO();

                    String newV = inputBean.getTransactiontypecode() + "|" + inputBean.getDescription() + "|" + inputBean.getStatus() + "|" + inputBean.getSortOrder() + "|" + inputBean.getOTPReqStatus() + "|" + inputBean.getRiskReqStatus();
                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.TXN_TYPE_MGT_PAGE, SectionVarList.SYSTEM_CONFIG, "Transaction type code " + inputBean.getTransactiontypecode() + " updated", null, inputBean.getOldvalue(), newV);
                    message = dao.updateTransactionType(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Transaction type " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Transaction type " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String Delete() {
        System.out.println("called TransactionTypeAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            TransactionTypeDAO dao = new TransactionTypeDAO();
            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.TXN_TYPE_MGT_PAGE, SectionVarList.SYSTEM_CONFIG, "Transaction type code " + inputBean.getTransactiontypecode() + " deleted", null);
            message = dao.deleteTransactionType(inputBean, audit);
            if (message.isEmpty()) {
                message = "Transaction type " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

}
