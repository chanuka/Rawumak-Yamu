/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.systemaudit;

import com.rawmakyamu.bean.systemaudit.AuditDataBean;
import com.rawmakyamu.bean.systemaudit.AuditSearchDTO;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.systemaudit.SystemAuditDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import static com.rawmakyamu.util.common.Common.checkEmptyorNullString;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.common.PartialList;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
 * @author thushanth
 */
public class SystemAuditAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    private AuditSearchDTO auditSearchDTO = new AuditSearchDTO();
    List<AuditDataBean> dataList = new ArrayList<AuditDataBean>();
    AuditDataBean audata = new AuditDataBean();
    Map parameterMap = new HashMap();
//    Connection connection = null;
    InputStream fileInputStream = null;
    InputStream excelStream = null;

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public AuditDataBean getAudata() {
        return audata;
    }

    public Map getParameterMap() {
        return parameterMap;
    }

    public List<AuditDataBean> getDataList() {
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

    @Override
    public String execute() {
        System.out.println("called SystemAuditAction : execute");
        return SUCCESS;
    }

    public AuditSearchDTO getModel() {
        return auditSearchDTO;
    }

    public String view() {

        String result = "view";
        try {

            if (this.applyUserPrivileges()) {

                CommonDAO dao = new CommonDAO();

                auditSearchDTO.setUserList(dao.getUserList());
                auditSearchDTO.setSectionList(dao.getSectionList());
                auditSearchDTO.setPageList(dao.getPageList());
                auditSearchDTO.setTaskList(dao.getTaskList());

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
            System.out.println("called SystemAuditAction :view");

        } catch (Exception ex) {
            addActionError("System Audit " + MessageVarList.COMMON_ERROR_PROCESS);
        }

        return result;
    }

    public String viewDetail() {
        System.out.println("called SystemAuditAction :viewDetail");
        try {
            this.applyUserPrivileges();
            SystemAuditDAO dao = new SystemAuditDAO();
            AuditDataBean dataBean = dao.findAuditById(auditSearchDTO.getAuditId());
            auditSearchDTO.setAuditDataBean(dataBean);
            
            System.err.println(auditSearchDTO.isVgenerateview());
            System.err.println(auditSearchDTO.isVgenerate());
            
            
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            session.setAttribute(SessionVarlist.IND_AUDIT_SEARCHBEAN, dataBean);

//            HttpServletRequest request = ServletActionContext.getRequest();
//            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.VIEW_TASK, PageVarList.SYSTEM_AUDIT_PAGE, SectionVarList.SYSTEM_AUDIT, "System audit individual view", null, null, null);
//            CommonDAO.saveAudit(audit);
        } catch (Exception ex) {
            addActionError("SystemAuditAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemAuditAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "viewdetail";
    }

    public String individualReport() {

        System.out.println("called SystemAuditAction : individualReport");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm a");
        try {
            cal.setTime(CommonDAO.getSystemDateLogin());

            HttpServletRequest request = ServletActionContext.getRequest();
            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.SYSTEM_AUDIT_PAGE, SectionVarList.SYSTEM_AUDIT, "System audit individual report generated", null);
            SystemAuditDAO dao = new SystemAuditDAO();
            dao.saveAudit(audit);

            //get image path
            ServletContext context = ServletActionContext.getServletContext();
            String imgPath = context.getRealPath("/resouces/images/ntb.png");

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            audata = (AuditDataBean) session.getAttribute(SessionVarlist.IND_AUDIT_SEARCHBEAN);

//            if(audata.getNewvalue() != null && !audata.getNewvalue().isEmpty()){
//                
//            }else{
//                audata.setNewvalue("--");
//            }
//            if(audata.getOldvalue()!= null && !audata.getOldvalue().isEmpty()){
//                
//            }else{
//                audata.setOldvalue("--");
//            }
            parameterMap.put("bankaddressheader", CommonVarList.REPORT_SBANK_ADD_HEADER);
//            parameterMap.put("totalrecordcount", new Long(searchBean.getFullCount()).toString());
            parameterMap.put("printeddate", sdf.format(cal.getTime()));
            parameterMap.put("bankaddress", CommonVarList.REPORT_SBANK_ADD);
            parameterMap.put("banktel", CommonVarList.REPORT_SBANK_TEL);
            parameterMap.put("bankmail", CommonVarList.REPORT_SBANK_MAIL);
            parameterMap.put("imageurl", imgPath);

        } catch (Exception e) {
            Logger.getLogger(SystemAuditAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " SystemAudit");
            return "message";
        }
        return "report";
    }

    public String list() {
        System.out.println("called SystemAuditAction : list");
        try {
            if (auditSearchDTO.isSearch()) {

                int rows = auditSearchDTO.getRows();
                int page = auditSearchDTO.getPage();
                int to = (rows * page);
                int from = to - rows;
                long records = 0;
                String sortIndex = "";
                String sortOrder = "";

                List<AuditDataBean> dataList = null;

                if (!auditSearchDTO.getSidx().isEmpty()) {
                    sortIndex = auditSearchDTO.getSidx();
                    sortOrder = auditSearchDTO.getSord();
                }
                HttpServletRequest request = ServletActionContext.getRequest();
                SystemAuditDAO dao = new SystemAuditDAO();
                System.err.println(auditSearchDTO.getDescription());
                PartialList<AuditDataBean> searchList = dao.getSearchList(auditSearchDTO, rows, from, sortIndex, sortOrder);

                String searchParameters = "["
                        + checkEmptyorNullString("From Date", auditSearchDTO.getFdate())
                        + checkEmptyorNullString("To Date", auditSearchDTO.getTdate())
                        + checkEmptyorNullString("Username", auditSearchDTO.getUser())
                        + checkEmptyorNullString("Section", auditSearchDTO.getSection())
                        + checkEmptyorNullString("Page", auditSearchDTO.getSdblpage())
                        + checkEmptyorNullString("Task", auditSearchDTO.getTask())
                        + checkEmptyorNullString("Description", auditSearchDTO.getDescription())
                        + "]";

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.SYSTEM_AUDIT_PAGE, SectionVarList.SYSTEM_AUDIT, "System audit search using " + searchParameters + " parameters ", null, null, null);
                CommonDAO.saveAudit(audit);

                dataList = searchList.getList();
                records = searchList.getFullCount();

                if (!dataList.isEmpty()) {
                    auditSearchDTO.setRecords(records);
                    auditSearchDTO.setGridModel(dataList);
                    int total = (int) Math.ceil((double) records / (double) rows);
                    auditSearchDTO.setTotal(total);

                    HttpSession session = ServletActionContext.getRequest().getSession(false);
                    session.setAttribute(SessionVarlist.AUDIT_SEARCHBEAN, auditSearchDTO);

                } else {
                    auditSearchDTO.setRecords(0L);
                    auditSearchDTO.setTotal(0);
                }
            }
        } catch (Exception e) {
            this.loadPageData();
            Logger.getLogger(SystemAuditAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " SystemAudit");
            return "message";
        }
        return "list";
    }

    public String reportGenerate() {

        System.out.println("called SystemAuditAction : reportGeneration");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm a");
        JRSwapFileVirtualizer virtualizer = null;
        JasperPrint jasperPrint = null;
        byte[] outputFile;
        Session hSession = null;
        String retMsg = "view";

        try {
            if (auditSearchDTO.getReporttype().equals("pdf")) {
                cal.setTime(CommonDAO.getSystemDateLogin());
//            connection = CommonDAO.getConnection();

                HttpSession session = ServletActionContext.getRequest().getSession(false);
                AuditSearchDTO searchBean = (AuditSearchDTO) session.getAttribute(SessionVarlist.AUDIT_SEARCHBEAN);

                //get path
                ServletContext context = ServletActionContext.getServletContext();
                String imgPath = context.getRealPath("/resouces/images/ntb.png");

                if (searchBean.getUser() != null && !searchBean.getUser().isEmpty()) {
                    parameterMap.put("userrole", searchBean.getUser().trim());
                } else {
                    parameterMap.put("userrole", "--");
                }
                if (searchBean.getTask() != null && !searchBean.getTask().isEmpty()) {
                    SystemAuditDAO dao = new SystemAuditDAO();
                    parameterMap.put("taskdes", dao.findAuditById("", searchBean.getTask(), "").getTask());
                    parameterMap.put("task", searchBean.getTask().trim());
                } else {
                    parameterMap.put("task", "--");
                    parameterMap.put("taskdes", "--");
                }
                if (searchBean.getSdblpage() != null && !searchBean.getSdblpage().isEmpty()) {
                    SystemAuditDAO dao = new SystemAuditDAO();
                    parameterMap.put("pagedes", dao.findAuditById(searchBean.getSdblpage(), "", "").getSdblpage());
                    parameterMap.put("page", searchBean.getSdblpage().trim());
                } else {
                    parameterMap.put("page", "--");
                    parameterMap.put("pagedes", "--");
                }
                if (searchBean.getSection() != null && !searchBean.getSection().isEmpty()) {
                    SystemAuditDAO dao = new SystemAuditDAO();
                    parameterMap.put("secdes", dao.findAuditById("", "", searchBean.getSection()).getSection());
                    parameterMap.put("section", searchBean.getSection().trim());
                } else {
                    parameterMap.put("section", "--");
                    parameterMap.put("secdes", "--");
                }
                if (searchBean.getTdate() != null && !searchBean.getTdate().isEmpty()) {
                    parameterMap.put("tdate", searchBean.getTdate().trim());
                } else {
                    parameterMap.put("tdate", "--");
                }
                if (searchBean.getFdate() != null && !searchBean.getFdate().isEmpty()) {
                    parameterMap.put("fdate", searchBean.getFdate().trim());
                } else {
                    parameterMap.put("fdate", "--");
                }
                System.err.println(searchBean.getDescription());
                if (searchBean.getDescription() != null && !searchBean.getDescription().isEmpty()) {
                    parameterMap.put("descrip", searchBean.getDescription().trim());
                    parameterMap.put("desDes", searchBean.getDescription().trim());
                } else {
                    parameterMap.put("descrip", "--");
                    parameterMap.put("desDes", "--");
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

                String reportLocation = context.getRealPath("WEB-INF/pages/systemaudit/report/systemaudit/audit_report.jasper");

                hSession = HibernateInit.sessionFactory.openSession();
                SessionImplementor sim = (SessionImplementor) hSession;

                jasperPrint = JasperFillManager.fillReport(reportLocation, parameterMap, sim.connection());

                if (virtualizer != null) {
                    virtualizer.setReadOnly(true);
                }

                outputFile = JasperExportManager.exportReportToPdf(jasperPrint);
                fileInputStream = new ByteArrayInputStream(outputFile);

                HttpServletRequest request = ServletActionContext.getRequest();
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.SYSTEM_AUDIT_PAGE, SectionVarList.SYSTEM_AUDIT, "System audit PDF report generated", null);
                CommonDAO.saveAudit(audit);

                retMsg = "download";
            } else if (auditSearchDTO.getReporttype().trim().equalsIgnoreCase("exel")) {
                System.err.println("EXEL printing");
                SystemAuditDAO dao = new SystemAuditDAO();
                retMsg = "excelreport";
                ByteArrayOutputStream outputStream = null;
                try {

                    HttpSession session = ServletActionContext.getRequest().getSession(false);

                    AuditSearchDTO searchBean = (AuditSearchDTO) session.getAttribute(SessionVarlist.AUDIT_SEARCHBEAN);
//                    Audittrace audittrace = Common.makeAudittrace(request, TaskVarList.REPORT_TASK, PageVarList.EXCEPTIONS_RPT_PAGE, this.getSearchParam() + " excel report viewed", null);
//                    Object object = new Object();
                    Object object = dao.generateExcelReport(searchBean);
                    if (object instanceof SXSSFWorkbook) {
                        SXSSFWorkbook workbook = (SXSSFWorkbook) object;
                        outputStream = new ByteArrayOutputStream();
                        workbook.write(outputStream);
                        auditSearchDTO.setExcelStream(new ByteArrayInputStream(outputStream.toByteArray()));

                    } else if (object instanceof ByteArrayOutputStream) {
                        outputStream = (ByteArrayOutputStream) object;
                        auditSearchDTO.setZipStream(new ByteArrayInputStream(outputStream.toByteArray()));
                        retMsg = "zip";
                    }

                    HttpServletRequest request = ServletActionContext.getRequest();
                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.SYSTEM_AUDIT_PAGE, SectionVarList.SYSTEM_AUDIT, "System audit excel report generated ", null);
                    CommonDAO.saveAudit(audit);

                } catch (Exception e) {
//                    addActionError(MessageVarList.COMMON_ERROR_PROCESS + " exception detail excel report");
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
//            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " System Audit");

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

            auditSearchDTO.setUserList(dao.getUserList());
            auditSearchDTO.setSectionList(dao.getSectionList());
            auditSearchDTO.setPageList(dao.getPageList());
            auditSearchDTO.setTaskList(dao.getTaskList());
        } catch (Exception e) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " SystemAudit");
            Logger.getLogger(SystemAuditAction.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.SYSTEM_AUDIT_PAGE;
        String task = null;
        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("list".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("viewDetail".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("reportGenerate".equals(method)) {
            task = TaskVarList.GENERATE_TASK;
        } else if ("individualReport".equals(method)) {
            task = TaskVarList.GENERATE_TASK;
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
        List<Task> taskList = new Common().getUserTaskListByPage(PageVarList.SYSTEM_AUDIT_PAGE, request);

        auditSearchDTO.setVsearch(true);
        auditSearchDTO.setVgenerate(true);
        auditSearchDTO.setVviewlink(true);
        auditSearchDTO.setVgenerateview(true);
        if (taskList != null && taskList.size() > 0) {
            for (Task task : taskList) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    auditSearchDTO.setVsearch(false);
                }
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                    auditSearchDTO.setVviewlink(false);
                }
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.GENERATE_TASK)) {
                    auditSearchDTO.setVgenerate(false);
                    auditSearchDTO.setVgenerateview(false);
                }
            }
        }
        return true;
    }

}
