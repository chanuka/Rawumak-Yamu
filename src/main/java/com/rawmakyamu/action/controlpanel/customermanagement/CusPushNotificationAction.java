/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.controlpanel.customermanagement;

import com.rawmakyamu.bean.controlpanel.custmanagement.CusPushNotificationDataBean;
import com.rawmakyamu.bean.controlpanel.custmanagement.CusPushNotificationFileDataBean;
import com.rawmakyamu.bean.controlpanel.custmanagement.CusPushNotificationInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.controlpanel.customermanagement.CusPushNotificationDAO;
import com.rawmakyamu.dao.systemaudit.SystemAuditDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import static com.rawmakyamu.util.common.Common.checkEmptyorNullString;
import com.rawmakyamu.util.common.Validation;
import com.rawmakyamu.util.mapping.MsCustomerWalletPushFile;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import com.rawmakyamu.util.varlist.PageVarList;
import com.rawmakyamu.util.varlist.SectionVarList;
import com.rawmakyamu.util.varlist.TaskVarList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha_s
 */
public class CusPushNotificationAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    CusPushNotificationInputBean inputBean = new CusPushNotificationInputBean();

    private String conXLFileName;
    private File conXL;
    private String serverPath;

    private InputStream inputStream;
    private String fileName;
    private long contentLength;

    public String getConXLFileName() {
        return conXLFileName;
    }

    public void setConXLFileName(String conXLFileName) {
        this.conXLFileName = conXLFileName;
    }

    public File getConXL() {
        return conXL;
    }

    public void setConXL(File conXL) {
        this.conXL = conXL;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public Object getModel() {
        return inputBean;
    }

    public String execute() throws Exception {
        System.out.println("Called CustomerPushNotification: execute");
        return SUCCESS;
    }

    public String view() {
        String result = "view";
        System.out.println("called CustomerPushNotification: view");
        try {
            if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
                inputBean.setStatusFileList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_PUSH_NOTIFICATION));

            } else {
                result = "loginpage";
            }

        } catch (Exception e) {
            addActionError("Customer Push Notification " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CusPushNotificationAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String Detail() {
        System.out.println("called CustomerPushNotification: detail");
        MsCustomerWalletPushFile block = null;
        try {
            if (inputBean.getId() != null && !inputBean.getId().isEmpty()) {
                CommonDAO commonDAO = new CommonDAO();

                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_PUSH_NOTIFICATION));

                HttpSession session = ServletActionContext.getRequest().getSession(true);
                session.setAttribute("FILENAMEID", inputBean.getId());
                session.setAttribute("STATUSFROMID", commonDAO.getFileNameFromID(inputBean.getId()).getStatus());

            } else {
                inputBean.setMessage("Empty Customer Push Notification ID.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Customer push notification " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(BlockNotificationAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";

    }

    public String ListFile() {
        System.out.println("called CustomerPushNotification : ListFile");
        try {
            CommonDAO daos = new CommonDAO();

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }
            CusPushNotificationDAO dao = new CusPushNotificationDAO();

            List<CusPushNotificationFileDataBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {
                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("File ID", inputBean.getFileId().toString())
                        + checkEmptyorNullString("File Name", inputBean.getFileNamef())
                        + checkEmptyorNullString("Status", inputBean.getStatus())
                        + "]";

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.CUSTOMER_PUSH_NOTIFICATION, SectionVarList.CUSTOMER_MANAGEMENT, "Push Notifications searched using " + searchParameters + " parameters ", null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);
            }

            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                inputBean.setRecords(records);
                inputBean.setGridFileModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);
            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }

        } catch (Exception e) {
            Logger.getLogger(CusPushNotificationAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Customer Push Notification");
        }
        return "list";
    }

    public String List() {
        System.out.println("called CustomerPushNotification : List");
        try {

            HttpSession session = ServletActionContext.getRequest().getSession(true);

            CommonDAO daos = new CommonDAO();

            session.setAttribute("FILENAME", daos.getFileNameFromID(inputBean.getId()).getFileName());

            inputBean.setFileName(daos.getFileNameFromID(inputBean.getId()).getFileName());

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }
            CusPushNotificationDAO dao = new CusPushNotificationDAO();

            List<CusPushNotificationDataBean> dataList = dao.getSearchList2(inputBean, rows, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {
                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Wallet Id", inputBean.getWalletId().toString())
                        + checkEmptyorNullString("Message", inputBean.getMessage())
                        + checkEmptyorNullString("Status", inputBean.getStatus())
                        + "]";

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.CUSTOMER_PUSH_NOTIFICATION, SectionVarList.CUSTOMER_MANAGEMENT, "Push Notifications searched using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(CusPushNotificationAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Customer Push Notification");
        }
        return "list";
    }

    public String ChangeStatus() {
        String message = "";
        try {
            HttpSession session = ServletActionContext.getRequest().getSession(true);
            String fileNameid = (String) session.getAttribute("FILENAMEID");

            System.out.println("file name satr " + fileNameid);

            CommonDAO daos = new CommonDAO();
            CusPushNotificationDAO dao = new CusPushNotificationDAO();

            HttpServletRequest request = ServletActionContext.getRequest();

            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEND_TASK, PageVarList.CUSTOMER_PUSH_NOTIFICATION, SectionVarList.NOTIFICATION_MGT, "File name " + daos.getFileNameFromID(fileNameid).getFileName() + " sent ", null, null, null);
            message = dao.setStatus(fileNameid, audit);

            if (message.isEmpty()) {
                inputBean.setMessage(null);

            } else {
                inputBean.setMessage(message);
            }

        } catch (Exception e) {
            Logger.getLogger(CusPushNotificationAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Customer Push Notification");
        }
        return "list";
    }

    public String ViewPopupcsv() {
        String result = "viewpopupcsv";
        System.out.println("called CustomerPushNotification : ViewPopupcsv");
        try {
            if (this.applyUserPrivileges()) {

            } else {
                result = "loginpage";
            }

        } catch (Exception e) {
            addActionError("Customer Push Notification " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CusPushNotificationAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    /**
     * normal method
     *
     */
//    public String Upload() {
//        System.out.println("called CustomerPushNotification : upload");
//        String result = "messagecsv";
//        ServletContext context = ServletActionContext.getServletContext();
//        InputStream is = null;
//        InputStreamReader isr = null;
//        BufferedReader br = null;
//
////        if (Common.getOS_Type().equals("WINDOWS")) {
//        this.serverPath = context.getRealPath("/resouces/csv_temp/push_notification");
////        } else if (Common.getOS_Type().equals("LINUX")) {
////            this.serverPath = "/opt/Epic/csv_temp/push_notification";
////        }
//        try {
//            if (inputBean.getHiddenId() != null) {
//
//                HttpServletRequest request = ServletActionContext.getRequest();
//                CusPushNotificationDAO dao = new CusPushNotificationDAO();
//
//                String message = "";
//                String message1 = "";
//                String message2 = "";
//
//                String token = "";
//
//                String thisLine = null;
//
//                DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//                Date date = new Date();
//
//                message = this.getFile(this.conXLFileName); // get file
//                inputBean.setFileName(this.conXLFileName);
//
//                this.conXLFileName = dateFormat.format(date) + this.conXLFileName;
//
//                File directory = new File(serverPath);
//
//                if (!directory.exists()) {
//                    directory.mkdirs();
//                }
//
//                if (-1 != this.conXLFileName.lastIndexOf("\\")) {
//                    this.conXLFileName = this.conXLFileName.substring(this.conXLFileName.lastIndexOf("\\") + 1);
//                }
//                File filetoCreate = new File(serverPath, this.conXLFileName);
////                    if (isfileexists==true) {            //set to database
//                if (filetoCreate.exists()) {                //set to local 
//                    addActionError("File already exists.");
//                    System.err.println("File already exists.");
//                } else {
//
//                }
//                int duplicates = 0;
//                int success = 0;
//
//                if (message.isEmpty()) {
//
//                    if (this.conXL == null) {
//                    } else {
//                        FileUtils.copyFile(this.conXL, filetoCreate);
//                        System.out.println(filetoCreate.getPath());
//                    }
//
//                    Scanner content = new Scanner(this.conXL).useDelimiter("\\Z");
//                    ////////////////////
//                    is = new FileInputStream(this.conXL);
//                    isr = new InputStreamReader(is);
//                    br = new BufferedReader(isr);
//
//                    ///////////////////
//                    String[] parts;
//                    int countrecord = 1;
//                    int succesrec = 0;
//                    boolean getline = false;
//                    inputBean.setFileName(this.createFileId());
//
//                    int check = 0;
//
//                    while ((thisLine = br.readLine()) != null) {
//                        if (getline) {
//                            token = thisLine;
//
//                            parts = token.split("\\,");
//
//                            try {
//                                inputBean.setWalletId(parts[0].trim());
//                                inputBean.setMessage(parts[1].trim());
//
//                            } catch (Exception ee) {
//                                message1 = "File has incorrectly ordered records 0";
//                                System.err.println(token);
//                                System.err.println(ee);
//                            }
//                            countrecord++;
//
//                            if (parts.length == 2 && message1.isEmpty()) {
//
//                                message1 = "";
//                                message = this.validateInputsForCSV();
//
//                                if (message.isEmpty()) {
//                                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.ATM_LOCATIONS, SectionVarList.SYSTEM_CONFIG, "Push Notification file name" + inputBean.getFileName() + " added", null, null, null);
//                                    message = dao.uploadCusPushNoti(inputBean, audit);
//                                    if (message.isEmpty()) {
//                                        succesrec++;
//                                        success++;
//                                    } else {
//                                        message = message + " at line number 1 " + countrecord + ",success count :" + succesrec;
//                                        duplicates++;
//                                        continue;
//                                    }
//                                } else {
//                                    message = message + " at line number 2 " + countrecord + ",success count :" + succesrec;
//                                    break;
//                                }
//
//                            } else {
//                                check++;
//                                if (check == 1) {
//                                    message2 = "File has incorrectly ordered records at line number 3 " + countrecord;
//                                }
//                                message1 = "";
//                                continue;
//                            }
//                        } else {
//                            getline = true;
//                        }
//                    }
//
//                    if (!message2.isEmpty() && message.isEmpty()) {
//                        if (check > 0) {
//                            message2 += ",success count :" + success;
//                        }
//                    }
//
//                }
//
//                if (message.isEmpty() && message2.isEmpty()) {
//                    if (duplicates == 0) {
//                        addActionMessage("File uploaded successfully. All records successfully added");
//                    } else {
//                        addActionMessage("File uploaded successfully. Duplicate records - " + duplicates + ". Uploaded records - " + success + ".");
//                    }
//                } else {
//                    if (message.isEmpty()) {
//                        addActionError(message2);
//                    } else if (message2.isEmpty()) {
//                        addActionError(message);
//                    } else if (!message.isEmpty() && !message2.isEmpty()) {
//                        addActionError(message2 + ". " + message + ".");
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            addActionError("Customer Push Notification  " + MessageVarList.COMMON_ERROR_PROCESS);
//            Logger.getLogger(CusPushNotificationAction.class.getName()).log(Level.SEVERE, null, ex);
//
//        } finally {
//            try {
//                if (is != null) {
//                    is.close();
//                }
//                if (isr != null) {
//                    isr.close();
//                }
//                if (br != null) {
//                    br.close();
//                }
//            } catch (IOException ee) {
//                Logger.getLogger(CusPushNotificationAction.class.getName()).log(Level.SEVERE, null, ee);
//            }
//        }
//        return result;
//    }
    public String Upload() {
        System.out.println("called CustomerPushNotification : upload");
        String result = "messagecsv";
        ServletContext context = ServletActionContext.getServletContext();
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

//        if (Common.getOS_Type().equals("WINDOWS")) {
        this.serverPath = context.getRealPath("/resouces/csv_temp/push_notification");
//        } else if (Common.getOS_Type().equals("LINUX")) {
//            this.serverPath = "/opt/Epic/csv_temp/push_notification";
//        }
        try {
            if (inputBean.getHiddenId() != null) {

                HttpServletRequest request = ServletActionContext.getRequest();
                CusPushNotificationDAO dao = new CusPushNotificationDAO();

                String message = "";

                String token = "";

                String thisLine = null;

                DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date();

                message = this.getFile(this.conXLFileName); // get file
                inputBean.setFileName(this.conXLFileName);

                this.conXLFileName = dateFormat.format(date) + this.conXLFileName;

                File directory = new File(serverPath);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                if (-1 != this.conXLFileName.lastIndexOf("\\")) {
                    this.conXLFileName = this.conXLFileName.substring(this.conXLFileName.lastIndexOf("\\") + 1);
                }
                File filetoCreate = new File(serverPath, this.conXLFileName);
//                    if (isfileexists==true) {            //set to database
                if (filetoCreate.exists()) {                //set to local 
                    addActionError("File already exists.");
                    System.err.println("File already exists.");
                } else {

                }
                int duplicates = 0;
                int success = 0;

                List<Integer> e1 = new ArrayList<Integer>();//parts.length == 2 && message.isEmpty()
                List<Integer> e2 = new ArrayList<Integer>();//this.validateInputsForCSV();
                List<String> valmsg = new ArrayList<String>();//this.validateInputsForCSV() message;
                List<Integer> e3 = new ArrayList<Integer>();//dao.uploadCusPushNoti(inputBean, audit); duplicate

                if (message.isEmpty()) {

                    if (this.conXL == null) {
                    } else {
                        FileUtils.copyFile(this.conXL, filetoCreate);
                        System.out.println(filetoCreate.getPath());
                    }

                    ////////////////////
                    is = new FileInputStream(this.conXL);
                    isr = new InputStreamReader(is);
                    br = new BufferedReader(isr);

                    ///////////////////
                    String[] parts;
                    int countrecord = 1;
                    int succesrec = 0;
                    boolean getline = false;

                    String File_name = this.createFileId();
                    inputBean.setFileName(File_name);

                    while ((thisLine = br.readLine()) != null) {
                        if (getline) {
                            token = thisLine;

                            parts = token.split("\\,");
                            message = "";
                            try {
                                inputBean.setWalletId(parts[0].trim());
                                inputBean.setMessage(parts[1].trim());

                            } catch (Exception ee) {
                                message = "File has incorrectly ordered records";
                                System.err.println(token);
                                System.err.println(ee);
                            }
                            countrecord++;

                            if (parts.length == 2 && message.isEmpty()) {

                                message = "";
                                message = this.validateInputsForCSV();

                                if (message.isEmpty()) {
                                    message = "";
                                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.CUSTOMER_PUSH_NOTIFICATION, SectionVarList.SYSTEM_CONFIG, " Push Notification file name " + inputBean.getFileName() + " added ", null, null, null);
                                    message = dao.uploadCusPushNoti(inputBean, audit);
                                    if (message.isEmpty()) {
                                        succesrec++;
                                        success++;
                                    } else {
                                        e3.add(countrecord);
                                        message = message + " at line number " + countrecord + ",success count :" + succesrec;
                                        duplicates++;
                                        continue;
                                    }
                                } else {
                                    valmsg.add(message);
                                    e1.add(countrecord);
                                    e2.add(countrecord);
                                    message = message + " at line number " + countrecord + ",success count :" + succesrec;
                                    continue;
                                }

                            } else {
                                e1.add(countrecord);
                                message = "File has incorrectly ordered records at line number " + countrecord;

                                continue;
                            }
                        } else {
                            getline = true;
                        }
                    }

                    if (e1.size() > 0 || e3.size() > 0 || success > 0) {
                        String file_errors = "";
                        String duplicate_errors = "";
                        if (e1.size() > 0) {
                            file_errors = ", Error line numbers -" + e1;
                        }
                        if (e3.size() > 0) {
                            duplicate_errors = ", Duplicate error line numbers - " + e3;
                        }

                        if (success > 0) {
                            addActionMessage("File uploaded successfully. Success records - " + success
                                    + file_errors + duplicate_errors
                            );
                            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.CUSTOMER_PUSH_NOTIFICATION, SectionVarList.SYSTEM_CONFIG, " Push Notification file bulk name " + inputBean.getFileName() + " added ", null, null, null);
                            dao.insetFileName(File_name, audit);

                        } else {
                            addActionError("No records added" + file_errors + duplicate_errors);
                        }
                    } else {
                        addActionError("No records added");
                    }
                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            addActionError("Customer Push Notification  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(CusPushNotificationAction.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException ee) {
                Logger.getLogger(CusPushNotificationAction.class.getName()).log(Level.SEVERE, null, ee);
            }
        }
        return result;
    }

    public String getFile(String file) {
        String msgEx = "";
        System.out.println("file " + file);
        if (file == null) {
            msgEx = "Please choose a file to upload.";
        } else {
            msgEx = Validation.isCSV(file);
        }
        return msgEx;
    }

    private String validateInputsForCSV() {
        String message = "";

        if (inputBean.getWalletId() == null || inputBean.getWalletId().trim().isEmpty()) {
            message = MessageVarList.CUS_PUSH_NOTIFICATION_WALLETID;
        } else if (inputBean.getMessage() == null || inputBean.getMessage().trim().isEmpty()) {
            message = MessageVarList.CUS_PUSH_NOTIFICATION_MESSAGE;
        } else if (inputBean.getWalletId().length() < 10 || inputBean.getWalletId().length() > 10) {
            message = MessageVarList.CUS_PUSH_NOTIFICATION_WALLETID_LENGTH;
        } else if (!Validation.isNumeric(inputBean.getWalletId())) {
            message = MessageVarList.CUS_PUSH_NOTIFICATION_WALLETID_NUMERIC;
        }

        return message;
    }

    private String validateUpload() throws Exception {
        String message = "";
        return message;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.CUSTOMER_PUSH_NOTIFICATION;
        String task = null;

        if ("view".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("List".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("ChangeStatus".equals(method)) {
            task = TaskVarList.SEND_TASK;
        } else if ("ListFile".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Upload".equals(method)) {
            task = TaskVarList.UPLOAD_TASK;
        } else if ("ViewPopupcsv".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Detail".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("template".equals(method)) {
            task = TaskVarList.UPLOAD_TASK;
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
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.CUSTOMER_PUSH_NOTIFICATION, request);

        HttpSession session = ServletActionContext.getRequest().getSession(true);
        session.setAttribute("VSEND", true);
        session.setAttribute("VSEARCH", true);
        inputBean.setVsearch(true);
        inputBean.setVupload(true);
        inputBean.setVview(true);
        inputBean.setVsend(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Task task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPLOAD_TASK)) {
                    inputBean.setVupload(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                    session.setAttribute("VSEARCH", false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                    inputBean.setVview(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEND_TASK)) {
                    inputBean.setVsend(false);
                    session.setAttribute("VSEND", false);
                }

            }
        }

        return true;
    }

    public String template() {
        System.out.println("called CustomerPushNotification: template");
        try {
            ServletContext context = ServletActionContext.getServletContext();
            String destPath = context.getRealPath("/resouces/csv_temp/push_notification");
            File fileToDownload = new File(destPath, "PUSHNOTY.csv");
            inputStream = new FileInputStream(fileToDownload);
            fileName = fileToDownload.getName();
            contentLength = fileToDownload.length();
        } catch (Exception e) {
            Logger.getLogger(CusPushNotificationAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Customer Push Notification " + MessageVarList.COMMON_ERROR_PROCESS);
            return "message";
        }
        return "excelreport";
    }

    public String createFileId() throws Exception {

        String tId;
        try {
            Random randomgenerator = new Random();
            Date nowDate = new Date();

            long date = nowDate.getTime();
            tId = ("CPN_" + date + "" + randomgenerator.nextInt(1000));
        } catch (Exception ex) {
            throw ex;
        }

        return tId;
    }

}
