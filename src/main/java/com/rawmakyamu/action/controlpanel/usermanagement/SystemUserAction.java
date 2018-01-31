/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.controlpanel.usermanagement;

import com.rawmakyamu.bean.controlpanel.usermanagement.SystemUserDataBean;
import com.rawmakyamu.bean.controlpanel.usermanagement.SystemUserInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.controlpanel.systemconfig.PasswordPolicyDAO;
import com.rawmakyamu.dao.systemaudit.SystemAuditDAO;
import com.rawmakyamu.dao.controlpanel.usermanagement.SystemUserDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import static com.rawmakyamu.util.common.Common.checkEmptyorNullString;
import com.rawmakyamu.util.common.Validation;
import com.rawmakyamu.util.mapping.MsUserParam;
import com.rawmakyamu.util.mapping.Page;
import com.rawmakyamu.util.mapping.Passwordpolicy;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.mapping.Systemuser;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import com.rawmakyamu.util.varlist.OracleMessage;
import com.rawmakyamu.util.varlist.PageVarList;
import com.rawmakyamu.util.varlist.SectionVarList;
import com.rawmakyamu.util.varlist.SessionVarlist;
import com.rawmakyamu.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author asitha_l
 */
public class SystemUserAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    SystemUserInputBean inputBean = new SystemUserInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called SystemUserAction : execute");
        return SUCCESS;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.SYSTEM_USER, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);
        inputBean.setVpasswordreset(true);

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
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.PASSWORD_RESET_TASK)) {
                    inputBean.setVpasswordreset(false);
                }
            }
        }
        inputBean.setVupdatebutt(true);

        return true;
    }

    public boolean checkAccess(String method, String userRole) {

        boolean status = false;
        String page = PageVarList.SYSTEM_USER;
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
        } else if ("findDualAuthUsers".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("update".equals(method)) {
            task = TaskVarList.UPDATE_TASK;
        } else if ("delete".equals(method)) {
            task = TaskVarList.DELETE_TASK;
        } else if ("changepassword".equals(method)) {
            task = TaskVarList.PASSWORD_RESET_TASK;
        } else if ("updatechangepassword".equals(method)) {
            task = TaskVarList.PASSWORD_RESET_TASK;
        } else if ("viewpopup".equals(method)) {
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

        System.out.println("called SystemUserAction :view");
        String result = "view";

        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setUserroleList(dao.getALLUserList(CommonVarList.SYS_MERCHANT));

                /**
                 * set password expiry period (inserted)
                 */
                PasswordPolicyDAO passPolicydao = new PasswordPolicyDAO();
                Calendar cal = Calendar.getInstance();
                Passwordpolicy passPolicy = passPolicydao.getPasswordPolicyDetails();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cal.setTime(CommonDAO.getSystemDateLogin());
                cal.add(Calendar.DAY_OF_MONTH, passPolicy.getPasswordexpiryperiod());

                /**
                 * set expiry date to session
                 */
                HttpServletRequest request = ServletActionContext.getRequest();
                request.getSession(false).setAttribute(SessionVarlist.PASSWORD_EXPIRY_PERIOD, formatter.format(cal.getTime()));

                inputBean.setExpirydate(formatter.format(cal.getTime()));
                inputBean.setPwtooltip(passPolicydao.generateToolTipMessage(passPolicy));

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
            addActionError("System user " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called SystemUser : ViewPopup");

        try {

            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                SystemUserDAO sysdao = new SystemUserDAO();

                PasswordPolicyDAO passPolicydao = new PasswordPolicyDAO();
                Passwordpolicy passPolicy = passPolicydao.getPasswordPolicyDetails();

                MsUserParam userparam = null;
                userparam = sysdao.setTooltip("PWTOOLTIP");

                inputBean.setPwtooltip(passPolicydao.generateToolTipMessage(passPolicy));
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setUserroleList(dao.getUserRoleListByStatus(CommonVarList.STATUS_ACTIVE, CommonVarList.SYS_MERCHANT));

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

        } catch (Exception e) {
            addActionError("System user " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String list() {

        System.out.println("called SystemUserAction: list");

        try {

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            Systemuser sysUser = ((Systemuser) session.getAttribute(SessionVarlist.SYSTEMUSER));

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;

            String sortIndex = "";
            String sortOrder = "";
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                sortIndex = inputBean.getSidx();
                sortOrder = inputBean.getSord();
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }

            SystemUserDAO dao = new SystemUserDAO();

            List<SystemUserDataBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy, sysUser.getUsername());

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Username", inputBean.getUsername())
                        + checkEmptyorNullString("Full Name", inputBean.getFullname())
                        + checkEmptyorNullString("Employee ID", inputBean.getServiceid())
                        + checkEmptyorNullString("NIC", inputBean.getNic())
                        + checkEmptyorNullString("Email", inputBean.getEmail())
                        + checkEmptyorNullString("Contact Number", inputBean.getContactno())
                        + checkEmptyorNullString("User Role", inputBean.getUserrole())
                        + checkEmptyorNullString("Status", inputBean.getStatus())
                        + "]";

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.SYSTEM_USER, SectionVarList.USERMANAGEMENT, "User management search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " System User Action");
        }
        return "list";
    }

    public String add() {

        System.out.println("called SystemUserAction : add");
        String result = "message";

        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            SystemUserDAO dao = new SystemUserDAO();
            CommonDAO cdao = new CommonDAO();

            String message = this.validateInputs();

            if (message.isEmpty()) {

                String serid_audit = "";
                String add1_audit = "";
                String city_audit = "";
                String dob_audit = "";

                if (inputBean.getServiceid() == null || inputBean.getServiceid().isEmpty()) {
                    serid_audit = "";
                } else {
                    serid_audit = inputBean.getServiceid();
                }
                if (inputBean.getAddress1() == null || inputBean.getAddress1().isEmpty()) {
                    add1_audit = "";
                } else {
                    add1_audit = inputBean.getAddress1();
                }

                if (inputBean.getCity() == null || inputBean.getCity().isEmpty()) {
                    city_audit = "";
                } else {
                    city_audit = inputBean.getCity();
                }

                if (inputBean.getDateofbirth() == null || inputBean.getDateofbirth().isEmpty()) {
                    dob_audit = "";
                } else {
                    dob_audit = inputBean.getDateofbirth();
                }

                String newV = inputBean.getUsername()
                        + "|" + inputBean.getFullname()
                        + "|" + inputBean.getNic()
                        + "|" + cdao.getUserRoleDesBycode(inputBean.getUserrole())
                        + "|" + cdao.getStatusByprefix(inputBean.getStatus())
                        + "|" + inputBean.getContactno()
                        + "|" + inputBean.getEmail()
                        + "|" + add1_audit
                        + "|" + city_audit
                        + "|" + serid_audit
                        + "|" + inputBean.getExpirydate()
                        + "|" + dob_audit;

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.SYSTEM_USER, SectionVarList.USERMANAGEMENT, "System user added", null, null, newV);

                /**
                 * use without dual auth
                 */
                message = dao.insertSystemUser(inputBean, audit);
//                if (!dao.isSystemUserExist(inputBean.getUsername())) {
//
////                    message = this.insertTmpAuthRecord(inputBean, audit, TaskVarList.ADD_TASK);

                    if (message.isEmpty()) {
                        addActionMessage("System user " + MessageVarList.COMMON_SUCC_INSERT );
                    } else {
                        addActionError(message);
                    }
//                } else {
//                    addActionError(MessageVarList.COMMON_ALREADY_EXISTS);
//                }

            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("System user " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String update() {

        System.out.println("called SystemUserAction : update");
        String retType = "message";

        try {
            if (inputBean.getUsername() != null && !inputBean.getUsername().isEmpty()) {

                //set username get in hidden fileds
                inputBean.setUsername(inputBean.getUsername());

                String message = this.validateUpdateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    CommonDAO cdao = new CommonDAO();

                    String serid_audit = "";
                    String add1_audit = "";
                    String city_audit = "";
                    String dob_audit = "";

                    if (inputBean.getServiceid() == null || inputBean.getServiceid().isEmpty()) {
                        serid_audit = "";
                    } else {
                        serid_audit = inputBean.getServiceid();
                    }
                    if (inputBean.getAddress1() == null || inputBean.getAddress1().isEmpty()) {
                        add1_audit = "";
                    } else {
                        add1_audit = inputBean.getAddress1();
                    }

                    if (inputBean.getCity() == null || inputBean.getCity().isEmpty()) {
                        city_audit = "";
                    } else {
                        city_audit = inputBean.getCity();
                    }
                    if (inputBean.getDateofbirth() == null || inputBean.getDateofbirth().isEmpty()) {
                        dob_audit = "";
                    } else {
                        dob_audit = inputBean.getDateofbirth();
                    }

                    String newV = inputBean.getUsername()
                            + "|" + inputBean.getFullname()
                            + "|" + inputBean.getNic()
                            + "|" + cdao.getUserRoleDesBycode(inputBean.getUserrole())
                            + "|" + cdao.getStatusByprefix(inputBean.getStatus())
                            + "|" + inputBean.getContactno()
                            + "|" + inputBean.getEmail()
                            + "|" + add1_audit
                            + "|" + city_audit
                            + "|" + serid_audit
                            + "|" + inputBean.getExpirydate()
                            + "|" + dob_audit;

                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.SYSTEM_USER, SectionVarList.USERMANAGEMENT, "System user updated", null, inputBean.getOldvalue(), newV);

                    /**
                     * use without dual auth
                     */
                    SystemUserDAO dao = new SystemUserDAO();
                    message = dao.updateSystemUser(inputBean, audit);
//                    message = this.insertTmpAuthRecord(inputBean, audit, TaskVarList.UPDATE_TASK);

                    if (message.isEmpty()) {
                        addActionMessage("System user " + MessageVarList.COMMON_SUCC_UPDATE );
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("System user " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String delete() {

        System.out.println("called SystemUserAction : delete");
        String message = null;
        String retType = "delete";

        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Systemuser cuUser = ((Systemuser) request.getSession(false).getAttribute(SessionVarlist.SYSTEMUSER));
            SystemUserDAO dao = new SystemUserDAO();
            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.SYSTEM_USER, SectionVarList.USERMANAGEMENT, "System user " + inputBean.getUsername() + " deleted", null);

            /**
             * use without dual auth
             */
            message = dao.deleteSystemUser(inputBean, cuUser, audit);
//            message = dao.checkSystemUser(inputBean, cuUser);

            if (message.isEmpty()) {
                /**
                 * set system user values to input bean
                 */
                this.setValuesToInputBean(inputBean);

//                message = this.insertTmpAuthRecord(inputBean, audit, TaskVarList.DELETE_TASK);

                if (message.isEmpty()) {
                    message = "System user " + MessageVarList.COMMON_SUCC_DELETE ;
                }
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String detail() {

        System.out.println("called SystemUser: detail");
        Systemuser user = null;

        try {
            if (inputBean.getUsername() != null && !inputBean.getUsername().isEmpty()) {

                SystemUserDAO dao = new SystemUserDAO();
                CommonDAO commonDAO = new CommonDAO();

                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setUserroleList(commonDAO.getALLUserList(CommonVarList.SYS_MERCHANT));

                user = dao.getSystemUserByUserName(inputBean.getUsername());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    inputBean.setUsername(user.getUsername());
                } catch (NullPointerException e) {
                    inputBean.setUsername("");
                }

                try {
                    inputBean.setExpirydate(sdf.format(user.getExpirydate()).substring(0, 19));
                } catch (NullPointerException e) {
                    inputBean.setExpirydate("");
                }

                try {
                    inputBean.setUserrole(user.getUserrole().getUserrolecode());
                } catch (NullPointerException e) {
                    inputBean.setUserrole("");
                }

                try {
                    inputBean.setStatus(user.getStatus().getStatuscode());
                } catch (NullPointerException e) {
                    inputBean.setStatus("");
                }

                try {
                    inputBean.setFullname(user.getFullname());
                } catch (NullPointerException e) {
                    inputBean.setFullname("");
                }

                try {
                    inputBean.setServiceid(user.getEmpid().toString());
                } catch (NullPointerException e) {
                    inputBean.setServiceid("");
                }

                try {
                    inputBean.setAddress1(user.getAddress().toString());
                } catch (NullPointerException e) {
                    inputBean.setAddress1("");
                }

                try {
                    inputBean.setCity(user.getCity().toString());
                } catch (NullPointerException e) {
                    inputBean.setCity("");
                }

                try {
                    inputBean.setContactno(user.getMobile());
                } catch (NullPointerException e) {
                    inputBean.setContactno("");
                }

                try {
                    inputBean.setEmail(user.getEmail());
                } catch (NullPointerException e) {
                    inputBean.setEmail("");
                }

                try {
                    inputBean.setNic(user.getNic());
                } catch (NullPointerException e) {
                    inputBean.setNic("");
                }

                try {
                    inputBean.setDateofbirth(sdf1.format(user.getDateofbirth()).toString());
                } catch (NullPointerException e) {
                    inputBean.setDateofbirth("");
                }

                inputBean.setOldvalue(
                        inputBean.getUsername()
                        + "|" + inputBean.getFullname()
                        + "|" + inputBean.getNic()
                        + "|" + commonDAO.getUserRoleDesBycode(inputBean.getUserrole())
                        + "|" + commonDAO.getStatusByprefix(inputBean.getStatus())
                        + "|" + inputBean.getContactno()
                        + "|" + inputBean.getEmail()
                        + "|" + inputBean.getAddress1()
                        + "|" + inputBean.getCity()
                        + "|" + inputBean.getServiceid()
                        + "|" + inputBean.getExpirydate()
                        + "|" + inputBean.getDateofbirth()
                );

            } else {
                inputBean.setMessage("Empty ID.");
            }

        } catch (Exception ex) {
            inputBean.setMessage("System user " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";

    }

    public String findDualAuthUsers() {
        System.out.println("called SystemUserAction : findDualAuthUsers");

        try {

            if (inputBean.getUserrole() != null && !inputBean.getUserrole().isEmpty()) {

                SystemUserDAO dao = new SystemUserDAO();

                int currUserLevel = dao.getCurrUsersUserLevel(inputBean.getUserrole());

                dao.findUsersByUserRole(inputBean, currUserLevel);

            } else {
                inputBean.setMessage("");
            }

        } catch (Exception ex) {
            inputBean.setMessage("System user " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "findDualAuthUsers";

    }

    public String find() {

        System.out.println("called UserManagementAction: find");
        Systemuser user = new Systemuser();

        try {

            if (inputBean.getUsername() != null && !inputBean.getUsername().isEmpty()) {

                SystemUserDAO dao = new SystemUserDAO();
                CommonDAO cdao = new CommonDAO();

                user = dao.getSystemUserByUserName(inputBean.getUsername());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    inputBean.setUsername(user.getUsername());
                } catch (NullPointerException e) {
                    inputBean.setUsername("");
                }

                try {
                    inputBean.setExpirydate(sdf.format(user.getExpirydate()));
                } catch (NullPointerException e) {
                    inputBean.setExpirydate("");
                }

                try {
                    inputBean.setUserrole(user.getUserrole().getUserrolecode());
                } catch (NullPointerException e) {
                    inputBean.setUserrole("");
                }

                try {
                    inputBean.setStatus(user.getStatus().getStatuscode());
                } catch (NullPointerException e) {
                    inputBean.setStatus("");
                }

                try {
                    inputBean.setFullname(user.getFullname());
                } catch (NullPointerException e) {
                    inputBean.setFullname("");
                }

                try {
                    inputBean.setServiceid(user.getEmpid().toString());
                } catch (NullPointerException e) {
                    inputBean.setServiceid("");
                }

                try {
                    inputBean.setAddress1(user.getAddress().toString());
                } catch (NullPointerException e) {
                    inputBean.setAddress1("");
                }

                try {
                    inputBean.setCity(user.getCity().toString());
                } catch (NullPointerException e) {
                    inputBean.setCity("");
                }

                try {
                    inputBean.setContactno(user.getMobile());
                } catch (NullPointerException e) {
                    inputBean.setContactno("");
                }

                try {
                    inputBean.setEmail(user.getEmail());
                } catch (NullPointerException e) {
                    inputBean.setEmail("");
                }

                try {
                    inputBean.setNic(user.getNic());
                } catch (NullPointerException e) {
                    inputBean.setNic("");
                }

                try {
                    inputBean.setDateofbirth(sdf1.format(user.getDateofbirth()).toString());
                } catch (NullPointerException e) {
                    inputBean.setDateofbirth("");
                }

                inputBean.setOldvalue(
                        inputBean.getUsername()
                        + "|" + inputBean.getFullname()
                        + "|" + inputBean.getNic()
                        + "|" + cdao.getUserRoleDesBycode(inputBean.getUserrole())
                        + "|" + cdao.getStatusByprefix(inputBean.getStatus())
                        + "|" + inputBean.getContactno()
                        + "|" + inputBean.getEmail()
                        + "|" + inputBean.getAddress1()
                        + "|" + inputBean.getCity()
                        + "|" + inputBean.getServiceid()
                        + "|" + inputBean.getExpirydate()
                        + "|" + inputBean.getDateofbirth()
                );

            } else {
                inputBean.setMessage("Empty system user id.");

                /**
                 * set password expiry period (inserted)
                 */
                PasswordPolicyDAO passPolicydao = new PasswordPolicyDAO();
                Calendar cal = Calendar.getInstance();
                Passwordpolicy passPolicy = passPolicydao.getPasswordPolicyDetails();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                cal.setTime(CommonDAO.getSystemDateLogin());
                cal.add(Calendar.DAY_OF_MONTH, passPolicy.getPasswordexpiryperiod());
                inputBean.setExpirydate(formatter.format(cal.getTime()));

            }
        } catch (Exception ex) {
            inputBean.setMessage("System user " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "find";
    }

    public String changepassword() {

        System.out.println("called SystemUserAction :changePassword");
        String retType = "changepassword";

        try {

            SystemUserDAO dao = new SystemUserDAO();

            String userrole = dao.getUserroleDescriptionByUsername(inputBean.getUsername());

            inputBean.setUsername(inputBean.getUsername());
            inputBean.setHusername(inputBean.getUsername());
            inputBean.setUserrole(userrole);

            PasswordPolicyDAO passPolicydao = new PasswordPolicyDAO();
            Passwordpolicy passPolicy = passPolicydao.getPasswordPolicyDetails();
            inputBean.setPwtooltip(passPolicydao.generateToolTipMessage(passPolicy));

        } catch (Exception ex) {
            addActionError(" Passsword Reset " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retType;
    }

    public String updatechangepassword() {

        System.out.println("called SystemUserAction :updatechangepassword");
        String retType = "message";

        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String message = this.validateChangePasswordInputs();

            if (message.isEmpty()) {

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.PASSWORD_RESET_TASK, PageVarList.SYSTEM_USER, SectionVarList.USERMANAGEMENT, "Password of " + inputBean.getHusername() + " reset ", null);
                inputBean.setCrenewpwd(Common.makeHash(inputBean.getCrenewpwd().trim()));

                /**
                 * use without dual auth
                 */
                SystemUserDAO dao = new SystemUserDAO();
                message = dao.updatePasswordReset(inputBean, audit);
                inputBean.setUsername(inputBean.getHusername().trim());

                /**
                 * set system user values to input bean
                 */
                this.setValuesToInputBean(inputBean);

//                message = this.insertTmpAuthRecord(inputBean, audit, TaskVarList.PASSWORD_RESET_TASK);

                if (message.isEmpty()) {
                    addActionMessage(MessageVarList.RESET_PASSWORD_SUCCESS );
                } else {
                    addActionError(message);
                }

            } else {
                addActionError(message);
            }
        } catch (Exception ex) {
            addActionError(MessageVarList.COMMON_ERROR_UPDATE + " Password Reset");
            Logger.getLogger(SystemUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retType;
    }

    private String validateInputs() throws Exception {

        String message = "";
        try {
            if (inputBean.getUsername() == null || inputBean.getUsername().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_USERNAME;
            } else if (inputBean.getFullname() == null || inputBean.getFullname().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_FULLNAME;
            } else if (inputBean.getNic() == null || inputBean.getNic().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_NIC;
            } else if (!inputBean.getNic().isEmpty() && !Validation.isAlphaNumeric(inputBean.getNic())) {
                message = MessageVarList.SYSUSER_MGT_INVALID_NIC;
            } else if (!inputBean.getNic().isEmpty() && !Validation.isValidateNIC(inputBean.getNic())) {
                message = MessageVarList.SYSUSER_MGT_INVALID_NIC;
            } else if (inputBean.getPassword() == null || inputBean.getPassword().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_PASSWORD;
            } else if (!inputBean.getPassword().equals(inputBean.getConfirmpassword())) {
                message = MessageVarList.SYSUSER_MGT_PASSWORD_MISSMATCH;
            } else if (inputBean.getUserrole() == null || inputBean.getUserrole().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_USERROLE;
            } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_STATUS;
            } else if (inputBean.getContactno() == null || inputBean.getContactno().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_COANTACTNO;
            } else if (!inputBean.getContactno().isEmpty() && !Validation.validLocalPhoneno(inputBean.getContactno())) {
                message = MessageVarList.SYSUSER_MGT_INVALID_CONTACT_NO;
            } else if (!inputBean.getContactno().isEmpty() && !Validation.isNumeric(inputBean.getContactno())) {
                message = MessageVarList.SYSUSER_MGT_INVALID_CONTACT_NO;
            } else if (inputBean.getEmail() == null || inputBean.getEmail().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_EMAIL;
            } else if (!inputBean.getEmail().isEmpty() && !Validation.isValidEmail(inputBean.getEmail())) {
                message = MessageVarList.SYSUSER_MGT_INVALID_EMAIL;

            } else if (!inputBean.getDateofbirth().isEmpty() && inputBean.getDateofbirth() != null) {
                if (!Validation.isValidateDoBwithNIC(inputBean.getDateofbirth(), inputBean.getNic())) {
                    message = MessageVarList.SYSUSER_MGT_INVALID_NIC_DOB;
                }
            }

            if (inputBean.getPassword().equals(inputBean.getConfirmpassword())) {
                String msg = "";
                msg = this.checkPolicy(inputBean.getPassword());
                if (message.equals("")) {
                    message = msg;
                }
            }

            SystemUserDAO dao = new SystemUserDAO();
            if (message.isEmpty() && ((inputBean.getUserrole() != null || !inputBean.getUserrole().trim().isEmpty()))) {
                if (!dao.isTasksExistByUserRole(inputBean.getUserrole())) {
                    message = MessageVarList.SYSUSER_MGT_INVALID_USER_ROLE;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    private String checkPolicy(String password) throws Exception {
        String errorMsg = "";
        try {
            SystemUserDAO dao = new SystemUserDAO();

            Passwordpolicy passwordpolicy = dao.getPasswordPolicyDetails();
            if (passwordpolicy != null) {

                String msg = this.CheckPasswordValidity(password, passwordpolicy);

                if (!msg.equals("")) {
                    errorMsg = msg;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return errorMsg;
    }

    public String CheckPasswordValidity(String password, Passwordpolicy bean) throws Exception {
        String msg = "";
        boolean flag = true;
        Set<Character> set = new HashSet<Character>();;
        List<Character> list = new ArrayList<Character>();
        int x = 0;
        try {

            if (password.length() < bean.getMinimumlength()) {
                flag = false;
                msg = MessageVarList.SYSUSER_PASSWORD_TOO_SHORT + bean.getMinimumlength();
            } else if (password.length() > bean.getMaximumlength()) {
                flag = false;
                msg = MessageVarList.SYSUSER_PASSWORD_TOO_LARGE + bean.getMaximumlength();
            }

            if (flag) {
                int digits = 0;
                int upper = 0;
                int lower = 0;
                int special = 0;

                for (int i = 0; i < password.length(); i++) {
                    char c = password.charAt(i);
                    list.add(c);
                    if (Character.isDigit(c)) {
                        digits++;
                    } else if (Character.isUpperCase(c)) {
                        upper++;
                    } else if (Character.isLowerCase(c)) {
                        lower++;
                    } else {
                        special++;
                    }
                }

                if (lower < bean.getMinimumlowercasecharacters().intValue()) {
                    msg = MessageVarList.SYSUSER_PASSWORD_LESS_LOWER_CASE_CHARACTERS + bean.getMinimumlowercasecharacters();
                    flag = false;
                } else if (upper < bean.getMinimumuppercasecharacters().intValue()) {
                    msg = MessageVarList.SYSUSER_PASSWORD_LESS_UPPER_CASE_CHARACTERS + bean.getMinimumuppercasecharacters();
                    flag = false;
                } else if (digits < bean.getMinimumnumericalcharacters().intValue()) {
                    msg = MessageVarList.SYSUSER_PASSWORD_LESS_NUMERICAL_CHARACTERS + bean.getMinimumnumericalcharacters();
                    flag = false;
                } else if (special < bean.getMinimumspecialcharacters().intValue()) {
                    msg = MessageVarList.SYSUSER_PASSWORD_LESS_SPACIAL_CHARACTERS + bean.getMinimumspecialcharacters();
                    flag = false;
                }
            }

            if (flag) {
                do {
                    Character[] rechar = list.toArray(new Character[0]);
                    list.clear();
                    set.clear();
                    for (Character c : rechar) {
                        if (!set.add(c)) {
                            list.add(c);
                        }
                    }
                    x++;
                    if (bean.getRepeatcharactersallow() < x) {
                        msg = MessageVarList.SYSUSER_PASSWORD_MORE_CHAR_REPEAT + bean.getRepeatcharactersallow();
                        break;
                    }

                } while (!list.isEmpty());

            }

        } catch (Exception e) {
            throw e;
        }
        return msg;
    }

    private String validateUpdateInputs() throws Exception {
        String message = "";
        try {
            if (inputBean.getFullname() == null || inputBean.getFullname().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_FULLNAME;
            } else if (inputBean.getNic() == null || inputBean.getNic().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_NIC;
            } else if (!inputBean.getNic().isEmpty() && !Validation.isAlphaNumeric(inputBean.getNic())) {
                message = MessageVarList.SYSUSER_MGT_INVALID_NIC;
            } else if (!inputBean.getNic().isEmpty() && !Validation.isValidateNIC(inputBean.getNic())) {
                message = MessageVarList.SYSUSER_MGT_INVALID_NIC;
            } else if (inputBean.getUserrole() == null || inputBean.getUserrole().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_USERROLE;
            } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_STATUS;
            } else if (inputBean.getContactno() == null || inputBean.getContactno().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_COANTACTNO;
            } else if (!inputBean.getContactno().isEmpty() && !Validation.isNumeric(inputBean.getContactno())) {
                message = MessageVarList.SYSUSER_MGT_INVALID_CONTACT_NO;
            } else if (!inputBean.getContactno().isEmpty() && !Validation.validLocalPhoneno(inputBean.getContactno())) {
                message = MessageVarList.SYSUSER_MGT_INVALID_CONTACT_NO;
            } else if (inputBean.getEmail() == null || inputBean.getEmail().trim().isEmpty()) {
                message = MessageVarList.SYSUSER_MGT_EMPTY_EMAIL;
            } else if (!inputBean.getEmail().isEmpty() && !Validation.isValidEmail(inputBean.getEmail().trim())) {
                message = MessageVarList.SYSUSER_MGT_INVALID_EMAIL;
            } else if (!inputBean.getDateofbirth().isEmpty() && inputBean.getDateofbirth() != null) {
                if (!Validation.isValidateDoBwithNIC(inputBean.getDateofbirth(), inputBean.getNic())) {
                    message = MessageVarList.SYSUSER_MGT_INVALID_NIC_DOB;
                }
            }

            SystemUserDAO dao = new SystemUserDAO();
            if (message.isEmpty() && ((inputBean.getUserrole() != null || !inputBean.getUserrole().trim().isEmpty()))) {
                if (!dao.isTasksExistByUserRole(inputBean.getUserrole())) {
                    message = MessageVarList.SYSUSER_MGT_INVALID_USER_ROLE;
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    private String validateChangePasswordInputs() throws Exception {
        String message = "";
        try {

            if (inputBean.getCnewpwd() == null || inputBean.getCnewpwd().trim().isEmpty()) {
                message = MessageVarList.PASSWORDRESET_EMPTY_NEW_PASSWORD;
            } else if (inputBean.getCrenewpwd() == null || inputBean.getCrenewpwd().trim().isEmpty()) {
                message = MessageVarList.PASSWORDRESET_EMPTY_COM_PASSWORD;
            } else if (!inputBean.getCnewpwd().trim().contentEquals(inputBean.getCrenewpwd().trim())) {
                message = MessageVarList.PASSWORDRESET_MATCH_PASSWORD;
            }
            if (inputBean.getCnewpwd().trim().equals(inputBean.getCrenewpwd().trim())) {
                String msg = "";
                msg = this.checkPolicy(inputBean.getCnewpwd().trim());
                if (message.equals("")) {
                    message = msg;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

//    /**
//     * insert data to the tempauthrec table
//     *
//     * @param inputBean
//     * @param audit
//     * @param taskCode
//     * @return
//     * @throws Exception
//     */
//    private String insertTmpAuthRecord(SystemUserInputBean inputBean, Systemaudit audit, String taskCode) throws Exception {
//
//        Pendingtask dualAuth = new Pendingtask();
//        String message = "";
//        String fields = "";
//
//        try {
//
//            Page page = new Page();
//            page.setPagecode(PageVarList.SYSTEM_USER);
//            dualAuth.setPage(page);
//
//            Status status = new Status();
//            status.setStatuscode(CommonVarList.STATUS_DUALAUTH_PEND);
//            dualAuth.setStatus(status);
//
//            Task t = new Task();
//            t.setTaskcode(taskCode);
//            dualAuth.setTask(t);
//
//            if (taskCode.equals(TaskVarList.PASSWORD_RESET_TASK)) {
//                fields = inputBean.getUsername().trim()
//                        + "|" + inputBean.getFullname()
//                        + "|" + inputBean.getNic().trim()
//                        + "|" + inputBean.getUserrole()
//                        + "|" + inputBean.getStatus()
//                        + "|" + inputBean.getContactno().trim()
//                        + "|" + inputBean.getEmail().trim()
//                        + "|" + Common.checkEmptyorNullStringReturn(inputBean.getAddress1())
//                        + "|" + Common.checkEmptyorNullStringReturn(inputBean.getCity())
//                        + "|" + Common.checkEmptyorNullStringReturn(inputBean.getServiceid())
//                        + "|" + inputBean.getExpirydate()
//                        + "|" + Common.checkEmptyorNullStringReturn(inputBean.getDateofbirth())
//                        + "|" + inputBean.getCrenewpwd();
//
//            } else {
//                fields = inputBean.getUsername().trim()
//                        + "|" + inputBean.getFullname()
//                        + "|" + inputBean.getNic().trim()
//                        + "|" + inputBean.getUserrole()
//                        + "|" + inputBean.getStatus()
//                        + "|" + inputBean.getContactno().trim()
//                        + "|" + inputBean.getEmail().trim()
//                        + "|" + Common.checkEmptyorNullStringReturn(inputBean.getAddress1())
//                        + "|" + Common.checkEmptyorNullStringReturn(inputBean.getCity())
//                        + "|" + Common.checkEmptyorNullStringReturn(inputBean.getServiceid())
//                        + "|" + inputBean.getExpirydate()
//                        + "|" + Common.checkEmptyorNullStringReturn(inputBean.getDateofbirth());
//
//                if (inputBean.getPassword() == null || inputBean.getPassword().trim().isEmpty()) {
//                    fields = fields + "|";
//                } else {
//                    fields = fields + "|" + Common.mpiMd5(inputBean.getPassword().trim());
//                }
//            }
//
//            dualAuth.setFields(fields);
//            dualAuth.setPKey(inputBean.getUsername().trim());
//
//            message = CommonDAO.insertDualAuthRecord(dualAuth, audit);
//
//        } catch (Exception ex) {
//            message = MessageVarList.COMMON_ERROR_INSERT_PENDTASK;
//            Logger.getLogger(TaskAction.class.getName()).log(Level.SEVERE, null, ex);
//
//        }
//
//        return message;
//    }

    private void setValuesToInputBean(SystemUserInputBean inputBean) {

        SystemUserDAO dao = new SystemUserDAO();

        try {
            Systemuser user = dao.getSystemUserByUserName(inputBean.getUsername());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

            try {
                inputBean.setUsername(user.getUsername());
            } catch (NullPointerException e) {
                inputBean.setUsername("");
            }

            try {
                inputBean.setExpirydate(sdf.format(user.getExpirydate()).substring(0, 19));
            } catch (NullPointerException e) {
                inputBean.setExpirydate("");
            }

            try {
                inputBean.setUserrole(user.getUserrole().getUserrolecode());
            } catch (NullPointerException e) {
                inputBean.setUserrole("");
            }

            try {
                inputBean.setStatus(user.getStatus().getStatuscode());
            } catch (NullPointerException e) {
                inputBean.setStatus("");
            }

            try {
                inputBean.setFullname(user.getFullname());
            } catch (NullPointerException e) {
                inputBean.setFullname("");
            }

            try {
                inputBean.setServiceid(user.getEmpid());
            } catch (NullPointerException e) {
                inputBean.setServiceid("");
            }

            try {
                inputBean.setAddress1(user.getAddress());
            } catch (NullPointerException e) {
                inputBean.setAddress1("");
            }

            try {
                inputBean.setCity(user.getCity());
            } catch (NullPointerException e) {
                inputBean.setCity("");
            }

            try {
                inputBean.setContactno(user.getMobile());
            } catch (NullPointerException e) {
                inputBean.setContactno("");
            }

            try {
                inputBean.setEmail(user.getEmail());
            } catch (NullPointerException e) {
                inputBean.setEmail("");
            }

            try {
                inputBean.setNic(user.getNic());
            } catch (NullPointerException e) {
                inputBean.setNic("");
            }

            try {
                inputBean.setDateofbirth(sdf1.format(user.getDateofbirth()));
            } catch (NullPointerException e) {
                inputBean.setDateofbirth("");
            }
        } catch (Exception ex) {
        }
    }

}
