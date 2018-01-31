/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.systemaudit;

import com.rawmakyamu.bean.controlpanel.systemconfig.LoginHistoryBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.LoginHistoryInputBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.LoginHistoryTaskBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.controlpanel.systemconfig.LoginHistoryDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import static com.rawmakyamu.util.common.Common.checkEmptyorNullString;
import com.rawmakyamu.util.common.HibernateInit;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

/**
 *
 * @Document : LoginHistoryAction
 * @Created on : Feb 5, 2016, 8:42:31 AM
 * @author : jayana_i
 */
public class LoginHistoryAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    private LoginHistoryInputBean loginhistoryInputBean = new LoginHistoryInputBean();
    List<LoginHistoryBean> dataList = new ArrayList<LoginHistoryBean>();
    Map parameterMap = new HashMap();
//    String driver = "com.mysql.jdbc.Driver";
//    String connectionString = "jdbc:mysql://192.168.1.34:3306/rdbdev3";
//    Connection connection = null;
    InputStream fileInputStream = null;

    public String view() {

        String result = "view";
        try {

            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();

                loginhistoryInputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_LOGIN_HISTORY));
                loginhistoryInputBean.setTaskList(this.getTaskList());
                loginhistoryInputBean.setLogintypeList(dao.getDefultLogintypeList());
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

            System.out.println("called LoginHistoryAction :view");

        } catch (Exception ex) {
            addActionError("Login history " + MessageVarList.COMMON_ERROR_PROCESS);
        }
        return result;
    }

    public String reportGenerate() {

        System.out.println("called LoginHistoryAction : reportGeneration");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm a");
        JRSwapFileVirtualizer virtualizer = null;
        JasperPrint jasperPrint = null;
        byte[] outputFile;
        Session hSession = null;
        String retMsg = "view";
        try {
            if (loginhistoryInputBean.getReporttype().equals("pdf")) {

                LoginHistoryDAO dao = new LoginHistoryDAO();
                CommonDAO commonDAO = new CommonDAO();

                cal.setTime(CommonDAO.getSystemDateLogin());
//            connection = CommonDAO.getConnection();

                HttpSession session = ServletActionContext.getRequest().getSession(false);
                LoginHistoryInputBean searchBean = (LoginHistoryInputBean) session.getAttribute(SessionVarlist.AUDIT_SEARCHBEAN);

                //get path
                ServletContext context = ServletActionContext.getServletContext();
                String imgPath = context.getRealPath("/resouces/images/ntb.png");

                if (searchBean.getWalletid() != null && !searchBean.getWalletid().isEmpty()) {
//                SystemAuditDAO dao = new SystemAuditDAO();                
//                parameterMap.put("taskdes",dao.findAuditById("",searchBean.getWalletid(),"").getTask());
                    parameterMap.put("walletid", searchBean.getWalletid().trim());
                } else {
//                parameterMap.put("task", "--");
                    parameterMap.put("walletid", "--");
                }

                if (searchBean.getLoggedtime() != null && !searchBean.getLoggedtime().isEmpty()) {
                    parameterMap.put("loggedtime", searchBean.getLoggedtime().trim());
                } else {
                    parameterMap.put("loggedtime", "--");
                }

//            if (searchBean.getCustomerid()!= null && !searchBean.getCustomerid().isEmpty()) {
//                parameterMap.put("customerid", searchBean.getCustomerid().trim());
//            } else {
//                parameterMap.put("customerid", "--");
//            }
                if (searchBean.getNic() != null && !searchBean.getNic().isEmpty()) {
                    parameterMap.put("nic", searchBean.getNic().trim());
                } else {
                    parameterMap.put("nic", "--");
                }

                if (searchBean.getMobilenumber() != null && !searchBean.getMobilenumber().isEmpty()) {
                    parameterMap.put("mobilenumber", searchBean.getMobilenumber().trim());
                } else {
                    parameterMap.put("mobilenumber", "--");
                }

//            if (searchBean.getXcoordinate()!= null && !searchBean.getXcoordinate().isEmpty()) {
//                parameterMap.put("xcoordinate", searchBean.getXcoordinate().trim());
//            } else {
//                parameterMap.put("xcoordinate", "--");
//            }
//            if (searchBean.getYcoordinate()!= null && !searchBean.getYcoordinate().isEmpty()) {
//                parameterMap.put("ycoordinate", searchBean.getYcoordinate().trim());
//            } else {
//                parameterMap.put("ycoordinate", "--");
//            }
                if (searchBean.getStatus() != null && !searchBean.getStatus().isEmpty()) {

                    parameterMap.put("status", searchBean.getStatus().trim());
                    parameterMap.put("statusDes", dao.getStatusDescriptionByCode(searchBean.getStatus().trim()));
                } else {
                    parameterMap.put("status", "--");
                    parameterMap.put("statusDes", "--");
                }
                if (searchBean.getFromdate() != null && !searchBean.getFromdate().isEmpty()) {
                    parameterMap.put("fromdate", searchBean.getFromdate().trim());

                } else {
                    parameterMap.put("fromdate", "--");
                }

                if (searchBean.getTodate() != null && !searchBean.getTodate().isEmpty()) {
                    parameterMap.put("todate", searchBean.getTodate().trim());
                    String startDateString = searchBean.getTodate();
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date toDate;
                    try {
                        toDate = df.parse(startDateString);
                        int day = toDate.getDate();
                        day = day + 1;  // set 1 day earlier
                        toDate.setDate(day);
                        String newDateString = df.format(toDate);
                        parameterMap.put("SQL_tdate", newDateString.trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    parameterMap.put("todate", "--");

                }

                if (searchBean.getTask() != null && !searchBean.getTask().isEmpty()) {

                    parameterMap.put("taskDes", dao.getTaskDescriptionByCode(searchBean.getTask().trim()));
                    parameterMap.put("task", searchBean.getTask());

                } else {
                    parameterMap.put("taskDes", "--");
                    parameterMap.put("task", "--");
                }

                if (searchBean.getLogintype() != null && !searchBean.getLogintype().isEmpty()) {

                    parameterMap.put("typeDes", dao.getTypeDescriptionByCode(searchBean.getLogintype().trim()));
                    parameterMap.put("type", searchBean.getLogintype());

                } else {
                    parameterMap.put("typeDes", "--");
                    parameterMap.put("type", "--");
                }

                parameterMap.put("bankaddressheader", CommonVarList.REPORT_SBANK_ADD_HEADER);
//            parameterMap.put("totalrecordcount", new Long(searchBean.getFullCount()).toString());
                parameterMap.put("printeddate", sdf.format(cal.getTime()));
                parameterMap.put("bankaddress", CommonVarList.REPORT_SBANK_ADD);
                parameterMap.put("banktel", CommonVarList.REPORT_SBANK_TEL);
                parameterMap.put("bankmail", CommonVarList.REPORT_SBANK_MAIL);
                parameterMap.put("imageurl", imgPath);

                // Virtualizer 
                String directory = ServletActionContext.getServletContext().getInitParameter("tmpreportpath");
                File file = new File(directory);
                if (!file.exists()) {
                    file.mkdirs();
                }
                JRSwapFile swapFile = new JRSwapFile(directory, 4096, 200);
                virtualizer = new JRSwapFileVirtualizer(300, swapFile, true);
                parameterMap.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

                String reportLocation = context.getRealPath("WEB-INF/pages/systemaudit/report/mobileloginhistory/login_history_report.jasper");

                hSession = HibernateInit.sessionFactory.openSession();
                SessionImplementor sim = (SessionImplementor) hSession;

                jasperPrint = JasperFillManager.fillReport(reportLocation, parameterMap, sim.connection());

                if (virtualizer != null) {
                    virtualizer.setReadOnly(true);
                }

                outputFile = JasperExportManager.exportReportToPdf(jasperPrint);
                fileInputStream = new ByteArrayInputStream(outputFile);

                HttpServletRequest request = ServletActionContext.getRequest();
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.LOGIN_HISTORY, SectionVarList.SYSTEM_AUDIT, "Login history PDF report generated", null);
                CommonDAO.saveAudit(audit);

                retMsg = "download";
            } else if (loginhistoryInputBean.getReporttype().trim().equalsIgnoreCase("exel")) {
//                 System.err.println("EXEL printing");
                LoginHistoryDAO dao = new LoginHistoryDAO();
                retMsg = "excelreport";
                ByteArrayOutputStream outputStream = null;
                try {

                    HttpSession session = ServletActionContext.getRequest().getSession(false);

                    LoginHistoryInputBean searchBean = (LoginHistoryInputBean) session.getAttribute(SessionVarlist.AUDIT_SEARCHBEAN);
//                    Audittrace audittrace = Common.makeAudittrace(request, TaskVarList.REPORT_TASK, PageVarList.EXCEPTIONS_RPT_PAGE, this.getSearchParam() + " excel report viewed", null);
//                    Object object = new Object();
                    Object object = dao.generateExcelReport(searchBean);
                    if (object instanceof SXSSFWorkbook) {
                        SXSSFWorkbook workbook = (SXSSFWorkbook) object;
                        outputStream = new ByteArrayOutputStream();
                        workbook.write(outputStream);
                        loginhistoryInputBean.setExcelStream(new ByteArrayInputStream(outputStream.toByteArray()));

                    } else if (object instanceof ByteArrayOutputStream) {
                        outputStream = (ByteArrayOutputStream) object;
                        loginhistoryInputBean.setZipStream(new ByteArrayInputStream(outputStream.toByteArray()));
                        retMsg = "zip";
                    }

                    HttpServletRequest request = ServletActionContext.getRequest();
                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.LOGIN_HISTORY, SectionVarList.SYSTEM_AUDIT, "Login history excel report generated ", null);
                    CommonDAO.saveAudit(audit);

                } catch (Exception e) {
                    addActionError(MessageVarList.COMMON_ERROR_PROCESS + " exception detail excel report");
                    Logger.getLogger(LoginHistoryAction.class.getName()).log(Level.SEVERE, null, e);
                    this.loadPageData();
                    retMsg = "view";
                    throw e;
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.flush();
                            outputStream.close();
                        }

                    } catch (IOException ex) {
                        //do nothing
                    }
                }
            }
        } catch (Exception e) {
            this.loadPageData();
            Logger.getLogger(LoginHistoryAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Login history ");

            return "message";
        } finally {
            if (virtualizer != null) {
                virtualizer.cleanup();
            }
//            try{
//                 connection.close();
////                 fileInputStream.close();
//            }catch(Exception ex){
//                
//        }
            if (hSession != null) {
                hSession.close();
            }
        }

//        return "excelreport";
        return retMsg;
    }

    // only to display error msg at exception
    private void loadPageData() {
        try {
            CommonDAO dao = new CommonDAO();
            loginhistoryInputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_OTP_REQUEST_INITIATE));
        } catch (Exception e) {
            addActionError("Login history " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(com.rawmakyamu.action.systemaudit.LoginHistoryAction.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Map getParameterMap() {
        return parameterMap;
    }

    public List<LoginHistoryBean> getDataList() {
        return dataList;
    }

//    public Connection getConnection() {
//        return connection;
//    }
    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public String list() {
        System.out.println("called LoginHistoryAction : List");
        try {
            if (loginhistoryInputBean.isSearch()) {

                int rows = loginhistoryInputBean.getRows();
                int page = loginhistoryInputBean.getPage();
                int to = (rows * page);
                int from = to - rows;
                long records = 0;
                String sortIndex = "";
                String sortOrder = "";

                List<LoginHistoryBean> dataList = null;

                if (!loginhistoryInputBean.getSidx().isEmpty()) {
                    sortIndex = loginhistoryInputBean.getSidx();
                    sortOrder = loginhistoryInputBean.getSord();
                }
                String orderBy = "";
                if (!loginhistoryInputBean.getSidx().isEmpty()) {
                    orderBy = " order by " + loginhistoryInputBean.getSidx() + " " + loginhistoryInputBean.getSord();
                }
                HttpServletRequest request = ServletActionContext.getRequest();
                LoginHistoryDAO dao = new LoginHistoryDAO();

                dataList = dao.getSearchList(loginhistoryInputBean, rows, from, orderBy);

                String searchParameters = "["
                        + checkEmptyorNullString("From Date", loginhistoryInputBean.getFromdate())
                        + checkEmptyorNullString("To Date", loginhistoryInputBean.getTodate())
                        + checkEmptyorNullString("Wallet ID", loginhistoryInputBean.getWalletid())
                        + checkEmptyorNullString("Mobile Number", loginhistoryInputBean.getMobilenumber())
                        + checkEmptyorNullString("NIC", loginhistoryInputBean.getNic())
                        + checkEmptyorNullString("Status", loginhistoryInputBean.getStatus())
                        + checkEmptyorNullString("Task", loginhistoryInputBean.getTask())
                        + checkEmptyorNullString("Type", loginhistoryInputBean.getLogintype())
                        + "]";

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.LOGIN_HISTORY, SectionVarList.SYSTEM_AUDIT, "Login history search using " + searchParameters + " parameters ", null, null, null);
                CommonDAO.saveAudit(audit);

                if (!dataList.isEmpty()) {
                    records = dataList.get(0).getFullCount();
                    loginhistoryInputBean.setRecords(records);
                    loginhistoryInputBean.setGridModel(dataList);
                    int total = (int) Math.ceil((double) records / (double) rows);
                    loginhistoryInputBean.setTotal(total);

                    HttpSession session = ServletActionContext.getRequest().getSession(false);
                    session.setAttribute(SessionVarlist.AUDIT_SEARCHBEAN, loginhistoryInputBean);

                } else {
                    loginhistoryInputBean.setRecords(0L);
                    loginhistoryInputBean.setTotal(0);
                }
            }

        } catch (Exception e) {
            this.loadPageData();
            Logger.getLogger(com.rawmakyamu.action.systemaudit.LoginHistoryAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Login history " + MessageVarList.COMMON_ERROR_PROCESS);
            return "message";

        }
        return "list";
    }

    public String execute() {
        System.out.println("called LoginHistoryAction: execute");
        return SUCCESS;
    }

    public Object getModel() {
        return loginhistoryInputBean;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.LOGIN_HISTORY;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("list".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("reportGenerate".equals(method)) {
            task = TaskVarList.GENERATE_TASK;
//        } else if ("LogOutUser".equals(method)) {
//            task = TaskVarList.VIEW_TASK;
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
        List<Task> taskList = new Common().getUserTaskListByPage(PageVarList.LOGIN_HISTORY, request);

        loginhistoryInputBean.setVsearch(true);
        loginhistoryInputBean.setVgenerate(true);
//        cusSumInputBean.setVviewlink(true);
        if (taskList != null && taskList.size() > 0) {
            for (Task task : taskList) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    loginhistoryInputBean.setVsearch(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
//                    cusSumInputBean.setVviewlink(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.GENERATE_TASK)) {
                    loginhistoryInputBean.setVgenerate(false);
                }
            }
        }
        return true;
    }

    /**
     * get user level list
     *
     * @return
     */
    private HashMap<String, String> getTaskListHashh() {
        HashMap<String, String> task = new HashMap<String, String>();

        task.put("LOGN", "Login");
        task.put("PININ", "New Pin Enter");
        task.put("PSRS", "Password Reset");

        return task;
    }

    private List<LoginHistoryTaskBean> getTaskList() {
        List<LoginHistoryTaskBean> userLevel = new ArrayList<LoginHistoryTaskBean>();

        LoginHistoryTaskBean terminalORICatBean = new LoginHistoryTaskBean();
        terminalORICatBean.setKey("LOGN");
        terminalORICatBean.setValue("Login");
        userLevel.add(terminalORICatBean);
        terminalORICatBean = new LoginHistoryTaskBean();
        terminalORICatBean.setKey("PININ");
        terminalORICatBean.setValue("New Pin Enter");
        userLevel.add(terminalORICatBean);
        terminalORICatBean = new LoginHistoryTaskBean();
        terminalORICatBean.setKey("PSRS");
        terminalORICatBean.setValue("Password Reset");
        userLevel.add(terminalORICatBean);

        return userLevel;
    }
}
