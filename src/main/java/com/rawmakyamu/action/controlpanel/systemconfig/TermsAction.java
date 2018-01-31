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
import com.rawmakyamu.bean.controlpanel.systemconfig.TermsBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.TermsInputBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.TermsVersionBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.TermtypeBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.controlpanel.systemconfig.TermsDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import com.rawmakyamu.util.varlist.PageVarList;
import com.rawmakyamu.util.varlist.SectionVarList;
import com.rawmakyamu.util.varlist.SessionVarlist;
import com.rawmakyamu.util.varlist.TaskVarList;
import static com.opensymphony.xwork2.Action.SUCCESS;
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

public class TermsAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    TermsInputBean inputBean = new TermsInputBean();

    public String execute() throws Exception {
        System.out.println("called TermsAction : execute");
        return SUCCESS;
    }

    public TermsInputBean getModel() {
        return inputBean;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.TERMS_PAGE;
        String task = null;
        if ("View".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("List".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Add".equals(method)) {
            task = TaskVarList.ADD_TASK;
        } else if ("Addnew".equals(method)) {
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
        } else if ("LoadVersion".equals(method)) {
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

    private List<TermtypeBean> getTermsTypeList() {
        List<TermtypeBean> userLevel = new ArrayList<TermtypeBean>();

        TermtypeBean terminalORICatBean = new TermtypeBean();
        terminalORICatBean.setKey("ADULT");
        terminalORICatBean.setValue("Adult");
        userLevel.add(terminalORICatBean);
        terminalORICatBean = new TermtypeBean();
        terminalORICatBean.setKey("TEEN");
        terminalORICatBean.setValue("Teen");
        userLevel.add(terminalORICatBean);

        return userLevel;
    }

    public String View() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                TermsDAO tdao = new TermsDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setDefaultStatus(CommonVarList.STATUS_ACTIVE);
                inputBean.setTermtypeList(this.getTermsTypeList());
//                inputBean.setVersionList(dao.getVersionAdultList());
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

            System.out.println("called TermsAction :view");

        } catch (Exception ex) {
            addActionError("Terms " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TermsAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String ViewPopup() {
        String result = "viewpopup";
        System.out.println("called TermsAction : ViewPopup");
        try {
            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setDefaultStatus(CommonVarList.STATUS_ACTIVE);
                inputBean.setTermtypeList(this.getTermsTypeList());
//                inputBean.setVersionList(dao.getVersionAdultList());

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
            addActionError("Terms " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TermsAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String Add() {
        System.out.println("called TermsAction : add");
        String result = "messageadd";

        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            TermsDAO dao = new TermsDAO();
            String message = this.validateInputs();
            String des;
            if (message.isEmpty()) {
                if (inputBean.getTermsidadd().equals("ADULT")) {
                    des = "Adult type terms and conditions version number " + inputBean.getVersionnoadd() + " added ";
                } else {
                    des = "Teen type terms and conditions version number " + inputBean.getVersionnoadd() + " added ";
                }
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.TERMS_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, des, null, null, null);
                if (inputBean.getTermsidadd().equals("ADULT")) {          // adult description
                    message = dao.insertAdultTerms(inputBean, audit);
                } else if (inputBean.getTermsidadd().equals("TEEN")) {   // teen description  
                    message = dao.insertTeenTerms(inputBean, audit);
                }

                if (message.isEmpty()) {
                    addActionMessage("Terms and conditions " + MessageVarList.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }

            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Terms " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TermsAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // for normal ajax submit form
    public String Addnew() {
        System.out.println("called TermsAction : add");
        String result = "addnew";
        
        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            TermsDAO dao = new TermsDAO();
            String message = this.validateInputs();
            String des;
            if (message.isEmpty()) {
                if (inputBean.getTermsidadd().equals("ADULT")) {
                    des = "Adult type terms and conditions version number " + inputBean.getVersionnoadd() + " added ";
                } else {
                    des = "Teen type terms and conditions version number " + inputBean.getVersionnoadd() + " added ";
                }
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.TERMS_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, des, null, null, null);
                if (inputBean.getTermsidadd().equals("ADULT")) {          // adult description
                    message = dao.insertAdultTerms(inputBean, audit);
                } else if (inputBean.getTermsidadd().equals("TEEN")) {   // teen description  
                    message = dao.insertTeenTerms(inputBean, audit);
                }

                if (message.isEmpty()) {
                    inputBean.setMessage(null);
                } else {
                    inputBean.setMessage(message);
                }

            } else {
                inputBean.setMessage(message);
            }

        } catch (Exception ex) {
            addActionError("Terms " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TermsAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String Delete() {
        System.out.println("called TermsAction : delete");
        String retType = "delete";
        String message = "";
        TermsBean terms = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            TermsDAO dao = new TermsDAO();
            String des = "";
            if (inputBean.getTermsid().equals("ADULT")) {          // adult description
                terms = dao.findTermsAdultById(inputBean.getVersionno());
                des = "Adult type terms and conditions version number " + inputBean.getVersionno() + " deleted";

            } else if (inputBean.getTermsid().equals("TEEN")) {   // teen description  
                terms = dao.findTermsTeenById(inputBean.getVersionno());
                des = "Teen type terms and conditions version number " + inputBean.getVersionno() + " deleted";
            }
            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.TERMS_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, des, null, null, null);
            message = dao.deleteTerms(inputBean, audit);

            if (message.isEmpty()) {
                inputBean.setMessage(null);
            } else {
                inputBean.setMessage(message);
            }

        } catch (Exception ex) {
            Logger.getLogger(TermsAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Terms " + MessageVarList.COMMON_ERROR_UPDATE);
        }

        return retType;
    }

    public String Update() {

        System.out.println("called TermsAction : update");
        String retType = "message";
        TermsBean terms = null;

        try {
            String message = this.validateUpdateInputs();

            if (message.isEmpty()) {
                HttpServletRequest request = ServletActionContext.getRequest();
                TermsDAO dao = new TermsDAO();
                String des = "";
                if (inputBean.getTermsid().equals("ADULT")) {          // adult description
                    terms = dao.findTermsAdultById(inputBean.getVersionno());
                    if (terms.getTerms().equals(inputBean.getDescription())) {
                        des = "Adult type terms and conditions version number " + inputBean.getVersionno() + " updated without modifications";
                    } else {
                        des = "Adult type terms and conditions " + inputBean.getVersionno() + " updated with modifications";
                    }
                } else if (inputBean.getTermsid().equals("TEEN")) {   // teen description  
                    terms = dao.findTermsTeenById(inputBean.getVersionno());
                    if (terms.getTerms().equals(inputBean.getDescription())) {
                        des = "Teen type terms and conditions " + inputBean.getVersionno() + " updated without modifications";
                    } else {
                        des = "Teen type terms and conditions " + inputBean.getVersionno() + " updated with modifications";
                    }
                }
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.TERMS_PAGE, SectionVarList.SYSTEMCONFIGMANAGEMENT, des, null, null, null);
                message = dao.updateTerms(inputBean, audit);
                if (message.isEmpty()) {
                    addActionMessage("Terms and conditions " + MessageVarList.COMMON_SUCC_UPDATE);
                    inputBean.setMessage("Terms and conditions " + MessageVarList.COMMON_SUCC_UPDATE);
                } else {
                    addActionError(message);
                    inputBean.setMessage(message);
                }
            } else {
                addActionError(message);
            }
        } catch (Exception ex) {
            Logger.getLogger(TermsAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Terms " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String Find() {
        System.out.println("called TermsAction: Find");
        try {
            CommonDAO cdao = new CommonDAO();
            if (inputBean.getTermsid().equals("ADULT")) {          // adult description
                inputBean.getVersionMap().clear();
                for (Iterator<TermsVersionBean> it = cdao.getVersionAdultList().iterator(); it.hasNext();) {
                    TermsVersionBean sec = it.next();
                    TermsVersionBean userRoleSectionBean = new TermsVersionBean();
                    userRoleSectionBean.setKey(sec.getKey());
                    userRoleSectionBean.setValue(sec.getValue());
                    inputBean.getVersionMap().add(userRoleSectionBean);
//                    System.err.println(sec.getValue());
                }
            } else if (inputBean.getTermsid().equals("TEEN")) {   // teen description  
                inputBean.getVersionMap().clear();
                for (Iterator<TermsVersionBean> it = cdao.getVersionTeenList().iterator(); it.hasNext();) {
                    TermsVersionBean sec = it.next();
                    TermsVersionBean userRoleSectionBean = new TermsVersionBean();
                    userRoleSectionBean.setKey(sec.getKey());
                    userRoleSectionBean.setValue(sec.getValue());
                    inputBean.getVersionMap().add(userRoleSectionBean);
//                    System.err.println(sec.getValue());
                }
            }

        } catch (Exception ex) {
            inputBean.setMessage("Terms " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TermsAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String LoadVersion() {
        System.out.println("called TermsAction: LoadVersion");
        TermsBean terms = null;
        try {
            TermsDAO dao = new TermsDAO();

            if (inputBean.getTermsid().equals("ADULT")) {
                terms = dao.findTermsAdultById(inputBean.getVersionno());
                inputBean.setDescription(terms.getTerms());
                inputBean.setVersionno(terms.getVersionno());
                inputBean.setStatus(terms.getStatus());
                inputBean.setStatusAct(terms.getStatus());
            } else if (inputBean.getTermsid().equals("TEEN")) {
                terms = dao.findTermsTeenById(inputBean.getVersionno());
                inputBean.setDescription(terms.getTerms());
                inputBean.setVersionno(terms.getVersionno());
                inputBean.setStatus(terms.getStatus());
                inputBean.setStatusAct(terms.getStatus());
            }
        } catch (Exception ex) {
            inputBean.setMessage("Terms " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TermsAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "find";

    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.TERMS_PAGE, request);

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

    private String validateUpdateInputs() throws Exception {
        String message = "";
        CommonDAO dao = new CommonDAO();
        if (inputBean.getStatus() == null && !inputBean.getTermsid().isEmpty()) {
            inputBean.setStatus("ACT");
        }

        if (inputBean.getTermsid() == null || inputBean.getTermsid().trim().isEmpty()) {
            message = MessageVarList.TERMS_EMPTY_TERM_TYPE;
        } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
            message = MessageVarList.TERMS_EMPTY_STATUS;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.TERMS_EMPTY_DESCRIPTION;
        } else if (inputBean.getDescription().equals("<p><br></p>")) {
            message = MessageVarList.TERMS_EMPTY_DESCRIPTION;
        }
        return message;
    }

    private String validateInputs() throws Exception {
        String message = "";
        TermsDAO dao = new TermsDAO();
        if (inputBean.getTermsidadd() == null || inputBean.getTermsidadd().trim().isEmpty()) {
            message = MessageVarList.TERMS_EMPTY_TERM_TYPE;
        } else if (inputBean.getVersionnoadd() == null || inputBean.getVersionnoadd().trim().isEmpty()) {
            message = MessageVarList.TERMS_EMPTY_VERSION;
        } else if (!inputBean.getVersionnoadd().isEmpty() && dao.checkVersionExist(inputBean)) {
            message = MessageVarList.TERMS_VERSION_EXISTS;
        } else if (inputBean.getStatusadd() == null || inputBean.getStatusadd().trim().isEmpty()) {
            message = MessageVarList.TERMS_EMPTY_STATUS;
        } else if (inputBean.getDescriptionadd() == null || inputBean.getDescriptionadd().trim().isEmpty()) {
            message = MessageVarList.TERMS_EMPTY_DESCRIPTION;
        } else if (inputBean.getDescriptionadd().equals("<p><br></p>")) {
            message = MessageVarList.TERMS_EMPTY_DESCRIPTION;
        }
        return message;
    }

}
