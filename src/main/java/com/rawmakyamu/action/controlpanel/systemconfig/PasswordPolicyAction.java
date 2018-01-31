/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.controlpanel.systemconfig;

import com.rawmakyamu.util.varlist.PageVarList;
import com.rawmakyamu.util.varlist.SessionVarlist;
import com.rawmakyamu.util.varlist.TaskVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import com.rawmakyamu.util.varlist.SectionVarList;
import com.rawmakyamu.bean.controlpanel.systemconfig.PasswordPolicyInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.controlpanel.systemconfig.PasswordPolicyDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import com.rawmakyamu.util.mapping.Passwordpolicy;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author thushanth
 */
public class PasswordPolicyAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    PasswordPolicyInputBean inputBean = new PasswordPolicyInputBean();

    public PasswordPolicyAction() {
    }

    @Override
    public String execute() throws Exception {
        System.out.println("called PasswordPolicyAction : execute");
        return SUCCESS;
    }

    public Object getModel() {
        return inputBean;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.PASSWORD_POLICY;
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
        } else if ("reset".equals(method)) {
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

    private boolean applyUserPrivileges() throws Exception {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.PASSWORD_POLICY, request);

            inputBean.setVadd(true);
            inputBean.setVupdatelink(true);
            inputBean.setVupdatebutt(true);
            PasswordPolicyDAO dao = new PasswordPolicyDAO();
            if (dao.isExistPasswordPolicy()) {
                if (tasklist != null && tasklist.size() > 0) {
                    for (Task task : tasklist) {
                        if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                            inputBean.setVadd(true);
//                        } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                        } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                            inputBean.setVupdatelink(false);
                            inputBean.setVupdatebutt(false);
                        } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                        }
                    }
                    Passwordpolicy passwordpolicy = dao.getPasswordPolicyDetails();
                    inputBean.setPolicyid(true);

                    inputBean.setPasswordpolicyid(String.valueOf(passwordpolicy.getPasswordpolicyid()));
                    inputBean.setMinimumlength(String.valueOf(passwordpolicy.getMinimumlength()));
                    inputBean.setMaximumlength(String.valueOf(passwordpolicy.getMaximumlength()));
                    inputBean.setMinimumspecialcharacters(String.valueOf(passwordpolicy.getMinimumspecialcharacters()));
                    inputBean.setMinimumuppercasecharacters(String.valueOf(passwordpolicy.getMinimumuppercasecharacters()));
                    inputBean.setMinimumlowercasecharacters(String.valueOf(passwordpolicy.getMinimumlowercasecharacters()));
                    inputBean.setMinimumnumericalcharacters(String.valueOf(passwordpolicy.getMinimumnumericalcharacters()));
                    inputBean.setRepeatcharactersallow(String.valueOf(passwordpolicy.getRepeatcharactersallow()));
                    inputBean.setInitialpasswordexpirystatus(String.valueOf(passwordpolicy.getInitialpasswordexpirystatus()));
                    inputBean.setPasswordexpiryperiod(String.valueOf(passwordpolicy.getPasswordexpiryperiod()));
                    inputBean.setNoofhistorypassword(String.valueOf(passwordpolicy.getNoofhistorypassword()));
                    inputBean.setMinimumpasswordchangeperiod(String.valueOf(passwordpolicy.getMinimumpasswordchangeperiod()));
                    inputBean.setIdleaccountexpiryperiod(String.valueOf(passwordpolicy.getIdleaccountexpiryperiod()));
                    inputBean.setNoofinvalidloginattempt(String.valueOf(passwordpolicy.getNoofinvalidloginattempt()));

                    inputBean.setOldvalue(
                            inputBean.getPasswordpolicyid() + "|"
                            + inputBean.getMinimumlength() + "|"
                            + inputBean.getMaximumlength() + "|"
                            + inputBean.getMinimumspecialcharacters() + "|"
                            + inputBean.getMinimumuppercasecharacters() + "|"
                            + inputBean.getMinimumlowercasecharacters() + "|"
                            + inputBean.getMinimumnumericalcharacters() + "|"
                            + inputBean.getRepeatcharactersallow() + "|"
                            + inputBean.getPasswordexpiryperiod() + "|"
                            + inputBean.getNoofhistorypassword() + "|"
                            + inputBean.getMinimumpasswordchangeperiod() + "|"
                            + inputBean.getIdleaccountexpiryperiod() + "|"
                            + inputBean.getNoofinvalidloginattempt()
                    );

                }
            } else {
                if (tasklist != null && tasklist.size() > 0) {
                    for (Task task : tasklist) {
                        if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                            inputBean.setVadd(false);
//                        } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                        } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                            inputBean.setVupdatelink(true);
                            inputBean.setVupdatebutt(true);
                        } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                        }
                    }
                    inputBean.setPolicyid(false);

                }
            }
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public String view() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
//                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

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

            System.out.println("called PasswordPolicyAction : view");

        } catch (Exception ex) {
            addActionError("Password policy action " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String find() {
        System.out.println("called PasswordPolicyAction: Find");
        Passwordpolicy passwordpolicy = new Passwordpolicy();
        try {
            if (inputBean.getPasswordpolicyid() != null && !inputBean.getPasswordpolicyid().isEmpty()) {

                PasswordPolicyDAO dao = new PasswordPolicyDAO();

                passwordpolicy = dao.findPasswordPolicyById(inputBean.getPasswordpolicyid());

                inputBean.setPasswordpolicyid(String.valueOf(passwordpolicy.getPasswordpolicyid()));
                inputBean.setMinimumlength(String.valueOf(passwordpolicy.getMinimumlength()));
                inputBean.setMaximumlength(String.valueOf(passwordpolicy.getMaximumlength()));
                inputBean.setMinimumspecialcharacters(String.valueOf(passwordpolicy.getMinimumspecialcharacters()));
                inputBean.setMinimumuppercasecharacters(String.valueOf(passwordpolicy.getMinimumuppercasecharacters()));
                inputBean.setMinimumnumericalcharacters(String.valueOf(passwordpolicy.getMinimumnumericalcharacters()));
                inputBean.setMinimumlowercasecharacters(String.valueOf(passwordpolicy.getMinimumlowercasecharacters()));
                inputBean.setRepeatcharactersallow(String.valueOf(passwordpolicy.getRepeatcharactersallow()));
                inputBean.setInitialpasswordexpirystatus(String.valueOf(passwordpolicy.getInitialpasswordexpirystatus()));
                inputBean.setPasswordexpiryperiod(String.valueOf(passwordpolicy.getPasswordexpiryperiod()));
                inputBean.setNoofhistorypassword(String.valueOf(passwordpolicy.getNoofhistorypassword()));
                inputBean.setMinimumpasswordchangeperiod(String.valueOf(passwordpolicy.getMinimumpasswordchangeperiod()));
                inputBean.setIdleaccountexpiryperiod(String.valueOf(passwordpolicy.getIdleaccountexpiryperiod()));
                inputBean.setNoofinvalidloginattempt(String.valueOf(passwordpolicy.getNoofinvalidloginattempt()));

            } else {
                inputBean.setMessage("Empty password policy id.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Password policy " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String add() {
        System.out.println("called PasswordPolicyAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            PasswordPolicyDAO dao = new PasswordPolicyDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                String newV = inputBean.getPasswordpolicyid() + "|"
                        + inputBean.getMinimumlength() + "|"
                        + inputBean.getMaximumlength() + "|"
                        + inputBean.getMinimumspecialcharacters() + "|"
                        + inputBean.getMinimumuppercasecharacters() + "|"
                        + inputBean.getMinimumlowercasecharacters() + "|"
                        + inputBean.getMinimumnumericalcharacters() + "|"
                        + inputBean.getRepeatcharactersallow() + "|"
                        + inputBean.getPasswordexpiryperiod() + "|"
                        + inputBean.getNoofhistorypassword() + "|"
                        + inputBean.getMinimumpasswordchangeperiod() + "|"
                        + inputBean.getIdleaccountexpiryperiod() + "|"
                        + inputBean.getNoofinvalidloginattempt();
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.PASSWORD_POLICY, SectionVarList.SYSTEMCONFIGMANAGEMENT, "Password policy details added", null, null, newV);

                message = dao.insertPasswordPolicy(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Password policy  " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Password policy " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String update() {

        System.out.println("called PasswordPolicyAction : update");
        String retType = "message";

        try {
            if (inputBean.getPasswordpolicyid() != null && !inputBean.getPasswordpolicyid().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    PasswordPolicyDAO dao = new PasswordPolicyDAO();

                    String newV = inputBean.getPasswordpolicyid() + "|"
                            + inputBean.getMinimumlength() + "|"
                            + inputBean.getMaximumlength() + "|"
                            + inputBean.getMinimumspecialcharacters() + "|"
                            + inputBean.getMinimumuppercasecharacters() + "|"
                            + inputBean.getMinimumlowercasecharacters() + "|"
                            + inputBean.getMinimumnumericalcharacters() + "|"
                            + inputBean.getRepeatcharactersallow() + "|"
                            + inputBean.getPasswordexpiryperiod() + "|"
                            + inputBean.getNoofhistorypassword() + "|"
                            + inputBean.getMinimumpasswordchangeperiod() + "|"
                            + inputBean.getIdleaccountexpiryperiod() + "|"
                            + inputBean.getNoofinvalidloginattempt();

                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK,
                            PageVarList.PASSWORD_POLICY, SectionVarList.SYSTEMCONFIGMANAGEMENT,
                            "Password policy details updated", null, inputBean.getOldvalue(), newV);
                    message = dao.updatePasswordPolicy(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Password policy " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Password policy " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    private String validateInputs() {
        String message = "";

        if (inputBean.getPasswordpolicyid() == null || inputBean.getPasswordpolicyid().isEmpty()) {
            message = MessageVarList.PASSPOLICY_POLICYID_EMPTY;
        } else if (inputBean.getMinimumlength() == null || inputBean.getMinimumlength().isEmpty()) {
            message = MessageVarList.PASSPOLICY_MINLEN_EMPTY;
        } else if (inputBean.getMaximumlength() == null || inputBean.getMaximumlength().isEmpty()) {
            message = MessageVarList.PASSPOLICY_MAXLEN_EMPTY;
        } else if (Integer.parseInt(inputBean.getMinimumlength()) >= Integer.parseInt(inputBean.getMaximumlength())) {
            message = MessageVarList.PASSPOLICY_MIN_MAX_LENGHT_DIFF;
        } else if (inputBean.getMinimumspecialcharacters() == null || inputBean.getMinimumspecialcharacters().isEmpty()) {
            message = MessageVarList.PASSPOLICY_SPECCHARS_EMPTY;
        } else if (Integer.parseInt(inputBean.getMinimumspecialcharacters()) > 6) {
            message = MessageVarList.PASSPOLICY_SPECCHARS_SHOULD_BE_LESS + "7";
        } else if (inputBean.getMinimumuppercasecharacters() == null || inputBean.getMinimumuppercasecharacters().isEmpty()) {
            message = MessageVarList.PASSPOLICY_MINUPPER_EMPTY;
        } else if (inputBean.getMinimumlowercasecharacters() == null || inputBean.getMinimumlowercasecharacters().isEmpty()) {
            message = MessageVarList.PASSPOLICY_MINLOWER_EMPTY;
        } else if (inputBean.getMinimumnumericalcharacters() == null || inputBean.getMinimumnumericalcharacters().isEmpty()) {
            message = MessageVarList.PASSPOLICY_MINNUM_EMPTY;

        } else if (inputBean.getRepeatcharactersallow() == null || inputBean.getRepeatcharactersallow().isEmpty()) {
            message = MessageVarList.PASSPOLICY_REPEATE_CHARACTERS_EMPTY;
        } else if (Integer.parseInt(inputBean.getRepeatcharactersallow()) == 0) {
            message = MessageVarList.PASSPOLICY_REPEATE_CHARACTERS_ZERO;
        } //        else if (inputBean.getInitialpasswordexpirystatus() == null || inputBean.getInitialpasswordexpirystatus().isEmpty()) {
        //            message = MessageVarList.PASSPOLICY_INIT_PASSWORD_EXPIRY_STATUS_EMPTY;
        //        } 
        else if (inputBean.getPasswordexpiryperiod() == null || inputBean.getPasswordexpiryperiod().isEmpty()) {
            message = MessageVarList.PASSPOLICY_PASSWORD_EXPIRY_PERIOD_EMPTY;
        } else if (inputBean.getMinimumpasswordchangeperiod() == null || inputBean.getMinimumpasswordchangeperiod().isEmpty()) {
            message = MessageVarList.PASSPOLICY_MIN_PASSWORD_CHANGE_PERIOD_EMPTY;
        } else if (inputBean.getNoofhistorypassword() == null || inputBean.getNoofhistorypassword().isEmpty()) {
            message = MessageVarList.PASSPOLICY_NO_OF_HISTORY_PASSWORD_EMPTY;
        } else if (inputBean.getIdleaccountexpiryperiod() == null || inputBean.getIdleaccountexpiryperiod().isEmpty()) {
            message = MessageVarList.PASSPOLICY_IDLE_ACCOUNT_EXPIRY_PERIOD_EMPTY;
        } else if (inputBean.getNoofinvalidloginattempt() == null || inputBean.getNoofinvalidloginattempt().isEmpty()) {
            message = MessageVarList.PASSPOLICY_NO_OF_INVALID_LOGIN_ATTEMPTS_EMPTY;
        }

        if (inputBean.getMinimumlowercasecharacters() != null && !inputBean.getMinimumlowercasecharacters().isEmpty()
                && inputBean.getMinimumnumericalcharacters() != null && !inputBean.getMinimumnumericalcharacters().isEmpty()
                && inputBean.getMinimumspecialcharacters() != null && !inputBean.getMinimumspecialcharacters().isEmpty()
                && inputBean.getMinimumuppercasecharacters() != null && !inputBean.getMinimumuppercasecharacters().isEmpty()) {

            Integer minLower = Integer.parseInt(inputBean.getMinimumlowercasecharacters());
            Integer minNumerical = Integer.parseInt(inputBean.getMinimumnumericalcharacters());
            Integer minSpecial = Integer.parseInt(inputBean.getMinimumspecialcharacters());
            Integer minUpper = Integer.parseInt(inputBean.getMinimumuppercasecharacters());
//        Integer minVal=Integer.parseInt(inputBean.get());

            Integer minLength = minLower + minNumerical + minSpecial + minUpper;
//            Integer maxLength = minLength + 3;
            Integer maxLength = minLength;

            System.err.println("minLength " + minLength);
            System.err.println("maxLength " + maxLength);
            try {

                if (Integer.parseInt(inputBean.getMinimumlength()) < minLength) { //4
                    if (message.equals("")) {
                        message = MessageVarList.PASSPOLICY_MINLEN_INVALID + (minLength);
                    }
                }
            } catch (Exception e) {
                if (message.equals("")) {
                    message = MessageVarList.PASSPOLICY_MINLEN_INVALID + (minLength);
                }

            }
            try {
                if (Integer.parseInt(inputBean.getMaximumlength()) <= maxLength) { // 10 //12 now    11>12
                    if (message.equals("")) {
                        message = MessageVarList.PASSPOLICY_MAXLEN_INVALID + (maxLength);
                    }
                }
            } catch (Exception e) {
                if (message.equals("")) {
                    message = MessageVarList.PASSPOLICY_MAXLEN_INVALID + (maxLength);
                }
            }
            //check noofhistorypassword is 1(one) or above
            try {
                if (Integer.parseInt(inputBean.getNoofhistorypassword()) <= 0) {
                    if (message.equals("")) {
                        message = MessageVarList.PASSPOLICY_NO_OF_HISTORY_PASSWORD_INVALID;
                    }
                }
            } catch (Exception e) {
                if (message.equals("")) {
                    message = MessageVarList.PASSPOLICY_NO_OF_HISTORY_PASSWORD_INVALID;
                }

            }
            //check Password Expiry Period is 10 or above
            try {
                if (Integer.parseInt(inputBean.getPasswordexpiryperiod()) < 1) {
                    if (message.equals("")) {
                        message = MessageVarList.PASSPOLICY_PASSWORD_EXPIRY_PERIOD_INVALID;
                    }
                }
            } catch (Exception e) {
                if (message.equals("")) {
                    message = MessageVarList.PASSPOLICY_PASSWORD_EXPIRY_PERIOD_INVALID;
                }

            }
            //check Idle Account Expiry Period is 10 or above
            try {
                if (Integer.parseInt(inputBean.getIdleaccountexpiryperiod()) < 1) {
                    if (message.equals("")) {
                        message = MessageVarList.PASSPOLICY_IDLE_ACCOUNT_EXPIRY_PERIOD_INVALID;
                    }
                }
            } catch (Exception e) {
                if (message.equals("")) {
                    message = MessageVarList.PASSPOLICY_IDLE_ACCOUNT_EXPIRY_PERIOD_INVALID;
                }

            }

        }
        return message;
    }

//    public String list() {
//        System.out.println("called PasswordPolicyAction: List");
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
//            PasswordPolicyDAO dao = new PasswordPolicyDAO();
//            List<PasswordPolicyBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
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
//
//        } catch (Exception e) {
//            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, e);
//            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Password policy");
//        }
//        return "list";
//    }
    public String reset() {
        System.out.println("called PasswordPolicyAction: Reset");

        Passwordpolicy passwordpolicy;
        PasswordPolicyDAO dao = new PasswordPolicyDAO();
        try {
            if (dao.isExistPasswordPolicy()) {

                passwordpolicy = dao.getPasswordPolicyDetails();
                inputBean.setPpbean(passwordpolicy);
                System.err.println(passwordpolicy.getPasswordexpiryperiod() + "<<<<<<<<<<<<<<");
                System.err.println(inputBean.getPasswordexpiryperiod() + "<<<<<<<<<<<<<<");
                System.err.println(inputBean.getPpbean().getPasswordexpiryperiod() + "<<<<<<<<<<<<<<");

                inputBean.setOldvalue(
                        inputBean.getPpbean().getPasswordpolicyid() + "|"
                        + inputBean.getPpbean().getMinimumlength() + "|"
                        + inputBean.getPpbean().getMaximumlength() + "|"
                        + inputBean.getPpbean().getMinimumspecialcharacters() + "|"
                        + inputBean.getPpbean().getMinimumuppercasecharacters() + "|"
                        + inputBean.getPpbean().getMinimumlowercasecharacters() + "|"
                        + inputBean.getPpbean().getMinimumnumericalcharacters() + "|"
                        + inputBean.getPpbean().getRepeatcharactersallow() + "|"
                        + inputBean.getPpbean().getPasswordexpiryperiod() + "|"
                        + inputBean.getPpbean().getNoofhistorypassword() + "|"
                        + inputBean.getPpbean().getMinimumpasswordchangeperiod() + "|"
                        + inputBean.getPpbean().getIdleaccountexpiryperiod() + "|"
                        + inputBean.getPpbean().getNoofinvalidloginattempt()
                );

                inputBean.setPasswordpolicyid(String.valueOf(passwordpolicy.getPasswordpolicyid()));
                inputBean.setMinimumlength(String.valueOf(passwordpolicy.getMinimumlength()));
                inputBean.setMaximumlength(String.valueOf(passwordpolicy.getMaximumlength()));
                inputBean.setMinimumspecialcharacters(String.valueOf(passwordpolicy.getMinimumspecialcharacters()));
                inputBean.setMinimumuppercasecharacters(String.valueOf(passwordpolicy.getMinimumuppercasecharacters()));
                inputBean.setMinimumnumericalcharacters(String.valueOf(passwordpolicy.getMinimumnumericalcharacters()));
                inputBean.setMinimumlowercasecharacters(String.valueOf(passwordpolicy.getMinimumlowercasecharacters()));
                inputBean.setRepeatcharactersallow(String.valueOf(passwordpolicy.getRepeatcharactersallow()));
                inputBean.setInitialpasswordexpirystatus(String.valueOf(passwordpolicy.getInitialpasswordexpirystatus()));
                inputBean.setPasswordexpiryperiod(String.valueOf(passwordpolicy.getPasswordexpiryperiod()));
                inputBean.setNoofhistorypassword(String.valueOf(passwordpolicy.getNoofhistorypassword()));
                inputBean.setMinimumpasswordchangeperiod(String.valueOf(passwordpolicy.getMinimumpasswordchangeperiod()));
                inputBean.setIdleaccountexpiryperiod(String.valueOf(passwordpolicy.getIdleaccountexpiryperiod()));
                inputBean.setNoofinvalidloginattempt(String.valueOf(passwordpolicy.getNoofinvalidloginattempt()));

            } else {
                inputBean.setMessage("Empty password policy id.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Password policy " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "reset";

    }
}
