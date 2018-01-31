/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.controlpanel.usermanagement;

import com.rawmakyamu.bean.controlpanel.usermanagement.UserRolePageBean;
import com.rawmakyamu.bean.controlpanel.usermanagement.UserRolePrivilegeInputBean;
import com.rawmakyamu.bean.controlpanel.usermanagement.UserRoleSectionBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.controlpanel.usermanagement.UserRolePrivilegeDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import com.rawmakyamu.util.mapping.Page;
import com.rawmakyamu.util.mapping.Section;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import com.rawmakyamu.util.varlist.PageVarList;
import com.rawmakyamu.util.varlist.SectionVarList;
import com.rawmakyamu.util.varlist.SessionVarlist;
import com.rawmakyamu.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @Document : UserRolePrivilegeAction
 * @Created on : Jan 7, 2014, 10:38:41 AM
 * @author : thushanth
 */
public class UserRolePrivilegeAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    /**
     *
     */
    private static final long serialVersionUID = -3848060911824408165L;
    /**
     *
     */
    UserRolePrivilegeInputBean inputBean = new UserRolePrivilegeInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called UserRolePrivilegeAction : execute");
        return SUCCESS;
    }

    public String View() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setUserRoleList(dao.getUserRoleList(CommonVarList.STATUS_ACTIVE));

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
            System.out.println("called UserRolePrivilegeAction :View");

        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String Manage() {
        System.out.println("called UserRolePrivilegeAction : manage");

        try {
            if (this.applyUserPrivileges()) {

                String message = this.validateInputs();
                //inputBean.setUserRoleDes("Administrator");
                if (message.isEmpty()) {
                    return "assignrole";

                } else {
                    CommonDAO dao = new CommonDAO();
                    inputBean.setUserRoleList(dao.getUserRoleList(CommonVarList.STATUS_ACTIVE));
                    addActionError(message);
                }
            } else {
                return "loginpage";
            }
        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "cerror";

    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getUserRole() == null || inputBean.getUserRole().trim().isEmpty()) {
            message = MessageVarList.USER_ROLE_PRI_EMPTY_USER_ROLE;
        } else if (inputBean.getCategory() == null || inputBean.getCategory().trim().isEmpty()) {
            message = MessageVarList.USER_ROLE_PRI_EMPTY_CATAGARY;
        } else if (!((inputBean.getCategory().equals("Sections")) || (inputBean.getCategory().equals("Pages")) || (inputBean.getCategory().equals("Operations")))) {
            message = MessageVarList.USER_ROLE_PRI_INVALID_CATAGARY;
        }
        return message;
    }

    public String LoadSection() {

        System.out.println("called UserRolePrivilegeAction : LoadSection");
        List<Section> sectionList = new ArrayList<Section>();
        try {
            if (inputBean.getUserRole() != null && !inputBean.getUserRole().isEmpty()) {

                UserRolePrivilegeDAO dao = new UserRolePrivilegeDAO();

                sectionList = dao.getSectionListByUserRole(inputBean.getUserRole());

                for (Iterator<Section> it = sectionList.iterator(); it.hasNext();) {

                    Section sec = it.next();
                    UserRoleSectionBean userRoleSectionBean = new UserRoleSectionBean();
                    userRoleSectionBean.setKey(sec.getSectioncode());
                    userRoleSectionBean.setValue(sec.getDescription());
                    inputBean.getSectionMap().add(userRoleSectionBean);
                }

            } else {
                inputBean.setMessage("Empty userrole code.");
            }
        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(Level.SEVERE, null, ex);

        }
        return "loadsections";
    }

    public String LoadPage() {
        System.out.println("called UserRolePrivilegeAction: LoadPage");
        List<Page> pageList = new ArrayList<Page>();
        try {

            if (inputBean.getUserRole() != null
                    && !inputBean.getUserRole().isEmpty()
                    && inputBean.getSectionpage() != null
                    && !inputBean.getSectionpage().isEmpty()) {

                UserRolePrivilegeDAO dao = new UserRolePrivilegeDAO();

                pageList = dao.findpageByUserRoleSection(inputBean);

                for (Iterator<Page> it = pageList.iterator(); it.hasNext();) {
                    Page mpisection = it.next();
                    UserRolePageBean userRolePageBean = new UserRolePageBean();
                    userRolePageBean.setKey(mpisection.getPagecode());
                    userRolePageBean.setValue(mpisection.getDescription());
                    inputBean.getPageMap().add(userRolePageBean);
                }

            } else {
                inputBean.setMessage("Empty user role or empty section");
            }

        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return "loadpages";

    }

    public String Find() {
        System.out.println("called UserRolePrivilegeAction: Find");
        try {
            if (inputBean.getUserRole() != null
                    && !inputBean.getUserRole().isEmpty()) {
                UserRolePrivilegeDAO dao = new UserRolePrivilegeDAO();
                dao.findSecByUserRole(inputBean);

                inputBean.setOldvalue(inputBean.getUserRole() + "|" + inputBean.getNewList());

            } else {
                inputBean.setMessage("Empty user role");

            }
        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return "find";
    }

    public String FindPage() {

        System.out.println("called UserRolePrivilegeAction : FindPage");
        try {
            if (inputBean.getSection() != null && !inputBean.getSection().isEmpty() && inputBean.getUserRole() != null && !inputBean.getUserRole().isEmpty()) {

                UserRolePrivilegeDAO dao = new UserRolePrivilegeDAO();
                dao.getPageListBySection(inputBean);

                inputBean.setOldvalue(inputBean.getUserRole() + "|" + inputBean.getSection() + "|" + inputBean.getNewList());
            } else {
                inputBean.setMessage("Empty section code or empty user role");
            }
        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(Level.SEVERE, null, ex);

        }

        return "findpage";
    }

    public String FindTask() {
        System.out.println("called UserRolePrivilegeAction: FindTask");

        try {

            if (inputBean.getUserRole() != null
                    && !inputBean.getUserRole().isEmpty()
                    && inputBean.getSectionpage() != null
                    && !inputBean.getSectionpage().isEmpty()
                    && inputBean.getPage() != null
                    && !inputBean.getPage().isEmpty()) {

                UserRolePrivilegeDAO dao = new UserRolePrivilegeDAO();

                dao.findTask(inputBean);

                inputBean.setOldvalue(inputBean.getUserRole() + "|" + inputBean.getSectionpage() + "|" + inputBean.getPage() + "|" + inputBean.getNewList());

            } else {
                inputBean.setMessage("Empty user role or empty section or empty page");
            }

        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return "findtask";

    }

    public String Assign() {
        System.out.println("called UserRolePrivilegeAction : Assign");
        String result = "message";
        String message = "";
        try {
            if (inputBean.getUserRole() != null && !inputBean.getUserRole().isEmpty()) {

                HttpServletRequest request = ServletActionContext.getRequest();

                UserRolePrivilegeDAO userRuleDao = new UserRolePrivilegeDAO();

                if (inputBean.getCategory().equals("Sections")) {
                    String newV = inputBean.getUserRole() + "|" + inputBean.getNewBox();
                    Systemaudit audit = Common.makeAudittrace(request,
                            TaskVarList.ASSIGN_TASK, PageVarList.USER_ROLE_PRIVILEGE_MGT,
                            SectionVarList.USERMANAGEMENT, inputBean.getNewBox() + " sections assigned to user role code " + inputBean.getUserRole(), null, inputBean.getOldvalue(), newV);
                    message = userRuleDao.assignSection(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("User role privilege " + MessageVarList.COMMON_SUCC_ASSIGN);
                    } else {
                        addActionError(message);
                    }

                } else if (inputBean.getCategory().equals("Pages")) {
                    if (!(inputBean.getSection() == null) && !(inputBean.getSection().trim().isEmpty())) {
                        String newV = inputBean.getUserRole() + "|"
                                + inputBean.getSection() + "|"
                                + inputBean.getNewBox();

                        Systemaudit audit = Common.makeAudittrace(request,
                                TaskVarList.ASSIGN_TASK, PageVarList.USER_ROLE_PRIVILEGE_MGT,
                                SectionVarList.USERMANAGEMENT, inputBean.getNewBox() + " pages assigned to section code " + inputBean.getSection(), null, inputBean.getOldvalue(), newV);

                        message = this.validateAssignPages(inputBean);

                        if (message.isEmpty()) {

                            message = userRuleDao.assignSectionPages(inputBean, audit);

                            if (message.isEmpty()) {
                                addActionMessage("User role privilege " + MessageVarList.COMMON_SUCC_ASSIGN);
                            } else {
                                addActionError(message);
                            }

                        } else {
                            addActionError(message);
                        }

                    } else {
                        addActionError(MessageVarList.USER_ROLE_PRI_EMPTY_SECTION);
                        return result;
                    }
                } else if (inputBean.getCategory().equals("Operations")) {
                    if (!(inputBean.getSectionpage() == null) && !(inputBean.getSectionpage().trim().isEmpty())) {
                        if (!(inputBean.getPage() == null) && !(inputBean.getPage().trim().isEmpty())) {

                            CommonDAO cdao = new CommonDAO();

                            String newV = cdao.getUserRoleDesBycode(inputBean.getUserRole()) + "|"
                                    + cdao.getSectionByprefix(inputBean.getSectionpage()) + "|"
                                    + cdao.getPageByprefix(inputBean.getPage()) + "|"
                                    + inputBean.getNewBox();

                            Systemaudit audit = Common.makeAudittrace(request,
                                    TaskVarList.ASSIGN_TASK, PageVarList.USER_ROLE_PRIVILEGE_MGT,
                                    SectionVarList.USERMANAGEMENT, inputBean.getNewBox() + " tasks assigned to page code " + inputBean.getPage(), null, inputBean.getOldvalue(), newV);

                            message = userRuleDao.assignTask(inputBean, audit);
                            /**
                             * Insert DualAuth records
                             */
//                            message = userRuleDao.insertDualAuthRecord(inputBean, audit);

                            if (message.isEmpty()) {
                                addActionMessage("User role privilege " + MessageVarList.COMMON_SUCC_ASSIGN);
                            } else {
                                addActionError(message);
                            }

                            /**
                             * end
                             */
                        } else {
                            addActionError(MessageVarList.USER_ROLE_PRI_EMPTY_PAGE);
                            return result;
                        }
                    } else {
                        addActionError(MessageVarList.USER_ROLE_PRI_EMPTY_SECTION);
                        return result;
                    }
                } else {
                    addActionMessage("No catagory has been selected");
                }

            } else {
                addActionError("User role cannot be empty");
            }
        } catch (Exception ex) {
            addActionError("User role privilege " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(UserRolePrivilegeAction.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return result;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.USER_ROLE_PRIVILEGE_MGT, request);

        inputBean.setVassign(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Task task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
//                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ASSIGN_TASK)) {
                    inputBean.setVassign(false);
                }
            }
        }
        inputBean.setVmanage(true);
        return true;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.USER_ROLE_PRIVILEGE_MGT;
        String task = null;
        if ("View".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("LoadSection".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("LoadPage".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("FindPage".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("FindTask".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Manage".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Assign".equals(method)) {
            task = TaskVarList.ASSIGN_TASK;
        } else if ("Find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("LogOutUser".equals(method)) {
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

    public String LogOutUser() {
        System.out.println("called UserRolePrivilegeAction : LogOutUser");
        return "logoutuser";
    }

    private String validateAssignPages(UserRolePrivilegeInputBean inputBean) {

        String message = "";

        try {

            UserRolePrivilegeDAO dao = new UserRolePrivilegeDAO();

            /**
             * get customer,user,privilege type pages
             */
            List<String> custPageList = dao.getPageListByPageType(CommonVarList.PAGE_TYPE_CUSTOMER);
            List<String> userPageList = dao.getPageListByPageType(CommonVarList.PAGE_TYPE_USER);
            List<String> privilegePageList = dao.getPageListByPageType(CommonVarList.PAGE_TYPE_PRIVILEGE);

            List<String> allPageList = dao.getPageListByUserRoleAndSection(inputBean.getUserRole(), inputBean.getSection());

            allPageList.addAll(inputBean.getNewBox());

            if (allPageList.containsAll(custPageList)) {

                message = MessageVarList.USER_ROLE_PRI_PAGE_TYPE_DEPEND;
                return message;

            } else if (allPageList.containsAll(userPageList)) {

                message = MessageVarList.USER_ROLE_PRI_PAGE_TYPE_DEPEND;
                return message;

            } else if (allPageList.containsAll(privilegePageList)) {

                message = MessageVarList.USER_ROLE_PRI_PAGE_TYPE_DEPEND;
                return message;

            }
        } catch (Exception ex) {
            message = "User role privilege " + MessageVarList.COMMON_ERROR_PROCESS;
        }
        
        return message;
    }
}
