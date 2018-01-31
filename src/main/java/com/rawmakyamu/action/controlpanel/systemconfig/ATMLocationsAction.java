/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.controlpanel.systemconfig;

import com.rawmakyamu.bean.controlpanel.systemconfig.ATMLocationsBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.ATMLocationsInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.controlpanel.systemconfig.ATMLocationsDAO;
import com.rawmakyamu.dao.systemaudit.SystemAuditDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import static com.rawmakyamu.util.common.Common.checkEmptyorNullString;
import com.rawmakyamu.util.common.Validation;
import com.rawmakyamu.util.mapping.AtmLocations;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import com.rawmakyamu.util.varlist.OracleMessage;
import com.rawmakyamu.util.varlist.PageVarList;
import com.rawmakyamu.util.varlist.SectionVarList;
import com.rawmakyamu.util.varlist.SessionVarlist;
import com.rawmakyamu.util.varlist.TaskVarList;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import javax.servlet.ServletContext;
import org.apache.struts2.ServletActionContext;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author : jayana_i
 */
public class ATMLocationsAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    ATMLocationsInputBean inputBean = new ATMLocationsInputBean();

    private String conXLFileName;
    private File conXL;
    private String serverPath;
    private InputStream inputStream;
    private String fileName;
    private long contentLength;

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

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called ATMLocationAction : execute");

        return SUCCESS;
    }

    public String view() {
        String result = "view";
        System.out.println("called ATMLocationsAction: view");
        try {
            if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setStatusListATM(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_AUTH));
                inputBean.setStatusListCDM(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_AUTH));
                inputBean.setStatusListAgent(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_AUTH));
                inputBean.setStatusListBranch(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_AUTH));
                inputBean.setStatusListShop(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_AUTH));

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
            addActionError("ATMLocationsAction " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(ATMLocationsAction.class.getName()).log(Level.SEVERE, null, e);
//            result = "loginpage";
        }
        return result;
    }

    public String ViewPopupcsv() {
        String result = "viewpopupcsv";
        System.out.println("called ATMLocations : ViewPopupcsv");
        try {
            if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

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
            addActionError("Locations " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(ATMLocationsAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.ATM_LOCATIONS, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVsearch(true);
        inputBean.setVupload(true);
        inputBean.setVdownload(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Task task : tasklist) {
                if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.ADD_TASK)) {
                    inputBean.setVadd(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.DELETE_TASK)) {
                    inputBean.setVdelete(false);
//                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.LOGIN_TASK)) {
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPDATE_TASK)) {
                    inputBean.setVupdatelink(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.UPLOAD_TASK)) {
                    inputBean.setVupload(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.DOWNLOAD_TASK)) {
                    inputBean.setVdownload(false);
                }
            }

        }
        inputBean.setVupdatebutt(true);

        return true;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.ATM_LOCATIONS;
        String section = null;

        if ("view".equals(method)) {
            section = TaskVarList.VIEW_TASK;
        } else if ("List".equals(method)) {
            section = TaskVarList.VIEW_TASK;
        } else if ("Add".equals(method)) {
            section = TaskVarList.ADD_TASK;
        } else if ("Delete".equals(method)) {
            section = TaskVarList.DELETE_TASK;
        } else if ("find".equals(method)) {
            section = TaskVarList.VIEW_TASK;
        } else if ("ViewPopup".equals(method)) {
            section = TaskVarList.VIEW_TASK;
        } else if ("ViewPopupcsv".equals(method)) {
            section = TaskVarList.VIEW_TASK;
        } else if ("update".equals(method)) {
            section = TaskVarList.UPDATE_TASK;
        } else if ("upload".equals(method)) {
            section = TaskVarList.UPDATE_TASK;
        } else if ("template".equals(method)) {
            section = TaskVarList.UPLOAD_TASK;
        } else if ("activate".equals(method)) {
            section = TaskVarList.UPDATE_TASK;
        } else if ("detail".equals(method)) {
            section = TaskVarList.VIEW_TASK;
        }

        if ("execute".equals(method)) {
            status = true;
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            status = new Common().checkMethodAccess(section, page, userRole, request);
        }
        return status;
    }

    public String Add() {
        System.out.println("called ATMLocationsAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            ATMLocationsDAO dao = new ATMLocationsDAO();

            String message = this.validateInputs();

            if (message.isEmpty()) {

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.ATM_LOCATIONS, SectionVarList.SYSTEM_CONFIG, "Location code " + inputBean.getAtmcode() + " added", null, null, null);

                message = dao.insertATMLocations(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Location " + MessageVarList.COMMON_SUCC_INSERT);

                } else {
                    addActionError(message);

                }
            } else {
                addActionError(message);

            }

        } catch (Exception ex) {
            addActionError("Locations  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(ATMLocationsAction.class.getName()).log(Level.SEVERE, null, ex);
//            System.err.println("ATM locations " + MessageVarList.COMMON_ERROR_PROCESS);
        }
        return result;
    }

    public long getFileSize(File file) {
        long file_size;
        if (file == null) {
            file_size = 0;
        } else {
            file_size = this.conXL.length();
//            System.err.println(file_size);
        }
        return file_size;
    }

    public String getFile(String file) {
        String msgEx = "";
        if (file == null) {
            msgEx = "Please choose a file to upload.";
        } else {
            msgEx = Validation.isCSV(file);
        }
        return msgEx;
    }

    public String upload() {
        System.out.println("called ATMLocationsAction : upload");
        String result = "messagecsv";
        ServletContext context = ServletActionContext.getServletContext();
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

//        this.serverPath = context.getRealPath("/resouces/csv_temp/atm_locations");
        if (Common.getOS_Type().equals("WINDOWS")) {
            this.serverPath = context.getRealPath("/resouces/csv_temp/atm_locations");
        } else if (Common.getOS_Type().equals("LINUX")) {
            this.serverPath = "/opt/Epic/csv_temp/atm_locations";
        }
        try {
            if (inputBean.getHiddenId() != null) {

                HttpServletRequest request = ServletActionContext.getRequest();
                ATMLocationsDAO dao = new ATMLocationsDAO();

                String message = "";
                String token = "";

                String thisLine = null;

                DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date();

                message = this.getFile(this.conXLFileName); // get file
                inputBean.setFilaname(this.conXLFileName);
                System.err.println("message :" + message);

                this.conXLFileName = dateFormat.format(date) + this.conXLFileName;
                System.err.println(this.conXLFileName);

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

                if (message.isEmpty()) {

                    if (this.conXL == null) {
                    } else {
                        FileUtils.copyFile(this.conXL, filetoCreate);
                        System.out.println(filetoCreate.getPath());
                    }

                    Scanner content = new Scanner(this.conXL).useDelimiter("\\Z");
                    ////////////////////
                    is = new FileInputStream(this.conXL);
                    isr = new InputStreamReader(is);
                    br = new BufferedReader(isr);

                    ///////////////////
                    String[] parts;
                    int countrecord = 1;
                    int succesrec = 0;
                    boolean getline = false;
//                    while (content.hasNextLine()) {               original
                    while ((thisLine = br.readLine()) != null) {
                        if (getline) {
//                            token = content.nextLine();
                            token = thisLine;

                            System.err.println(token);
                            parts = token.split("\\,");
                            try {
                                inputBean.setAtmcode(parts[0].trim());
                                inputBean.setDescription(parts[1].trim());
                                inputBean.setAddress(parts[2].trim());
                                inputBean.setLatitude(parts[3].trim());
                                inputBean.setLongitude(parts[4].trim());
                                inputBean.setStatus(parts[5].trim());
                                inputBean.setIsAtmUp(parts[6].trim());
                                inputBean.setIsCdmUp(parts[7].trim());
                                inputBean.setIsBranchUp(parts[8].trim());
                                inputBean.setIsAgentUp(parts[9].trim());
                                inputBean.setIsShopUp(parts[10].trim());

                            } catch (Exception ee) {
                                message = "File has incorrectly ordered records";
                                System.err.println(token);
                                System.err.println(ee);
                            }
                            countrecord++;

                            if (parts.length == 11 && message.isEmpty()) {
                                message = this.validateInputsForCSV();
                                if (this.validateUpload().isEmpty()) {
                                    if (message.isEmpty()) {
                                        Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.ATM_LOCATIONS, SectionVarList.SYSTEM_CONFIG, "Location code " + inputBean.getAtmcode() + " added", null, null, null);
                                        message = dao.uploadATMLocations(inputBean, audit);
                                        if (message.isEmpty()) {
                                            succesrec++;
                                        }
                                    } else {
                                        message = message + " at line number " + countrecord + ",success count :" + succesrec;
                                        break;
                                    }
                                } else {
                                    message = this.validateUpload();
                                    message = message + " at line number " + countrecord + ",success count :" + succesrec;
                                    break;
                                }
                            } else {
                                message = "File has incorrectly ordered records at line number " + countrecord + ",success count :" + succesrec;
                            }
                        } else {
                            getline = true;
//                            content.nextLine();
                        }
                    }

                }

                if (message.isEmpty()) {
//                    addActionMessage("ATM locations " + MessageVarList.COMMON_SUCC_INSERT);
                    addActionMessage("File uploaded successfully");
                    System.err.println("File uploaded successfully");

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            addActionError("Locations  " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(ATMLocationsAction.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(ATMLocationsAction.class.getName()).log(Level.SEVERE, null, ee);
            }
        }
        return result;
    }

    public String List() {
        System.out.println("called ATMLocationAction : List");
        try {
            //if (inputBean.isSearch()) {

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }
            ATMLocationsDAO dao = new ATMLocationsDAO();

            List<ATMLocationsBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {
                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Location Code", inputBean.getSatmcode())
                        + checkEmptyorNullString("Location Name", inputBean.getSdescription())
                        + checkEmptyorNullString("Status", inputBean.getStatus())
                        + checkEmptyorNullString("Is ATM", inputBean.getSstatusATM())
                        + checkEmptyorNullString("Is CDM", inputBean.getSstatusCDM())
                        + checkEmptyorNullString("Is Branch", inputBean.getSstatusBranch())
                        + checkEmptyorNullString("Is Agent", inputBean.getSstatusAgent())
                        + checkEmptyorNullString("Is Shop", inputBean.getSstatusShop())
                        //                        + checkEmptyorNullString("ATM Type", inputBean.getStype())
                        + "]";

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.ATM_LOCATIONS, SectionVarList.SYSTEM_CONFIG, "Locations search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(ATMLocationsAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " ATM Location");
        }
        return "list";
    }

    public String ViewPopup() {
        String result = "viewpopup";
        System.out.println("called ATMLocations : ViewPopup");
        try {
            if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

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
            addActionError("Locations " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(ATMLocationsAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    private String validateInputs() {
        String message = "";

        if (inputBean.getAtmcode() == null || inputBean.getAtmcode().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_DESCRIPTION;
//        } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
//            message = MessageVarList.ATM_LOCATIONS_INVALID_DESCRIPTION_SPECIAL;
        } else if (inputBean.getAddress() == null || inputBean.getAddress().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ADDRESS;
        } else if (inputBean.getLatitude() == null || inputBean.getLatitude().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_LATITUDE;
        } else if (inputBean.getLongitude() == null || inputBean.getLongitude().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_LONGITUDE;
        } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_STATUS;
        } else if (inputBean.getStatus().equals("0")) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_STATUS;
//        } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
//            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_NAME;
//        } else if (!Validation.isAddressbyChar(inputBean.getAddress())) {
//            message = MessageVarList.ATM_LOCATIONS_INVALID_ADDRESS_NAME;
        } //        else if (inputBean.getType() == null || inputBean.getType().trim().isEmpty()) {
        //            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_TYPE;
        //        } 
        else if (!(inputBean.isIsAtm()
                || inputBean.isIsCdm()
                || inputBean.isIsBranch()
                || inputBean.isIsAgent()
                || inputBean.isIsShop())) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_TYPE;
        } else if (Validation.isLatitudeLongitude(inputBean.getLatitude())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_LATITUDE;
        } else if (Validation.isString(inputBean.getLatitude())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_LATITUDE;
        } else if (Validation.isLatitudeLongitude(inputBean.getLongitude())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_LONGITUDE;
        } else if (Validation.isString(inputBean.getLongitude())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_LONGITUDE;
        }

        return message;
    }

    private String validateInputsForCSV() {
        String message = "";

        if (inputBean.getAtmcode() == null || inputBean.getAtmcode().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_DESCRIPTION;
//        } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
//            message = MessageVarList.ATM_LOCATIONS_INVALID_DESCRIPTION_SPECIAL;
        } else if (inputBean.getAddress() == null || inputBean.getAddress().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ADDRESS;
        } else if (inputBean.getLatitude() == null || inputBean.getLatitude().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_LATITUDE;
        } else if (inputBean.getLongitude() == null || inputBean.getLongitude().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_LONGITUDE;
        } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_STATUS;
        } else if (inputBean.getStatus().equals("0")) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_STATUS;
//        } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
//            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_NAME;
//        } else if (!Validation.isAddressbyChar(inputBean.getAddress())) {
//            message = MessageVarList.ATM_LOCATIONS_INVALID_ADDRESS_NAME;
        } //        else if (inputBean.getType() == null || inputBean.getType().trim().isEmpty()) {
        //            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_TYPE;
        //        } 
        else if (!((inputBean.getIsAtmUp().equals(CommonVarList.STATUS_YES))
                || (inputBean.getIsCdmUp().equals(CommonVarList.STATUS_YES))
                || (inputBean.getIsBranchUp().equals(CommonVarList.STATUS_YES))
                || (inputBean.getIsAgentUp().equals(CommonVarList.STATUS_YES))
                || (inputBean.getIsShopUp().equals(CommonVarList.STATUS_YES)))) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_TYPE_CSV;
        } else if (Validation.isLatitudeLongitude(inputBean.getLatitude())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_LATITUDE;
        } else if (Validation.isString(inputBean.getLatitude())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_LATITUDE;
        } else if (Validation.isLatitudeLongitude(inputBean.getLongitude())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_LONGITUDE;
        } else if (Validation.isString(inputBean.getLongitude())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_LONGITUDE;
        }

        return message;
    }

    private String validateUpdates() {
        String message = "";

        if (inputBean.getUatmcode() == null || inputBean.getUatmcode().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_CODE;
        } else if (inputBean.getUdescription() == null || inputBean.getUdescription().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_DESCRIPTION;
        } else if (inputBean.getUaddress() == null || inputBean.getUaddress().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ADDRESS;
        } else if (inputBean.getUlatitude() == null || inputBean.getUlatitude().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_LATITUDE;
        } else if (inputBean.getUlongitude() == null || inputBean.getUlongitude().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_LONGITUDE;
        } else if (inputBean.getUstatus() == null || inputBean.getUstatus().trim().isEmpty()) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_STATUS;
        } else if (inputBean.getUstatus().equals("0")) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_STATUS;
//        } else if (!Validation.isSpecailCharacter(inputBean.getUdescription())) {
//            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_NAME;
//        } else if (!Validation.isAddressbyChar(inputBean.getUaddress())) {
//            message = MessageVarList.ATM_LOCATIONS_INVALID_ADDRESS_NAME;
        } //        else if (inputBean.getUtype() == null || inputBean.getUtype().trim().isEmpty()) {
        //            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_TYPE;
        //        } 
        else if (!(inputBean.isIsAtm()
                || inputBean.isIsCdm()
                || inputBean.isIsBranch()
                || inputBean.isIsAgent()
                || inputBean.isIsShop())) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_TYPE;
        } else if (Validation.isLatitudeLongitude(inputBean.getUlatitude())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_LATITUDE;
        } else if (Validation.isString(inputBean.getUlatitude())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_LATITUDE;
        } else if (Validation.isLatitudeLongitude(inputBean.getUlongitude())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_LONGITUDE;
        } else if (Validation.isString(inputBean.getUlongitude())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_LONGITUDE;
        }

        return message;
    }

    private String validateUpload() throws Exception {
        String message = "";
        ATMLocationsDAO dao = new ATMLocationsDAO();
        if (inputBean.getAtmcode().length() > 20) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_CODE + "20";
        } else if (!Validation.isSpecailCharacter(inputBean.getAtmcode())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_CODE_SPECIAL;
        } else if (inputBean.getDescription().length() > 128) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_DESCRIPTION + "128";
//        } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
//            message = MessageVarList.ATM_LOCATIONS_INVALID_DESCRIPTION_SPECIAL;
        } else if (inputBean.getAddress().length() > 255) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ADDRESS + "255";
//        } else if (!Validation.isAddressbyChar(inputBean.getAddress())) {
//            message = MessageVarList.ATM_LOCATIONS_INVALID_ADDRESS_SCPECIAL;
        } else if (inputBean.getLatitude().length() > 128) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_MAX_ATM_LATITUDE + "128";
        } else if (Validation.isLatitudeLongitude(inputBean.getLatitude())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_LATITUDE;
        } else if (inputBean.getLongitude().length() > 128) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_MAX_ATM_LONGITUDE + "128";
        } else if (Validation.isLatitudeLongitude(inputBean.getLongitude())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_ATM_LONGITUDE;
        } else if (inputBean.getStatus().length() > 20) {
            message = MessageVarList.ATM_LOCATIONS_EMPTY_ATM_STATUS_INVALID + " 20";
        } else if (!dao.isexsitsStatus(inputBean.getStatus())) {
            message = MessageVarList.MERCHANT_MGT_INVALID_STATUS;
        } else if (!dao.isexsitsStatus(inputBean.getIsAtmUp())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_TYPE_ATM;
        } else if (!dao.isexsitsStatus(inputBean.getIsCdmUp())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_TYPE_CDM;
        } else if (!dao.isexsitsStatus(inputBean.getIsBranchUp())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_TYPE_BRANCH;
        } else if (!dao.isexsitsStatus(inputBean.getIsAgentUp())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_TYPE_AGENT;
        } else if (!dao.isexsitsStatus(inputBean.getIsShopUp())) {
            message = MessageVarList.ATM_LOCATIONS_INVALID_TYPE_SHOP;
        }

        return message;
    }

    public String detail() {
        System.out.println("called ATMLocationsAction: detail");
        AtmLocations atmlocations = null;

        try {
            if (inputBean.getId() != null && !inputBean.getId().isEmpty()) {

                ATMLocationsDAO dao = new ATMLocationsDAO();
                CommonDAO commonDAO = new CommonDAO();
                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

                atmlocations = dao.findATMLocationsById(inputBean.getId());

                inputBean.setUatmcode(atmlocations.getAtmCode());
                inputBean.setUdescription(atmlocations.getDescription());
                inputBean.setUaddress(atmlocations.getAddress());
                inputBean.setUstatus(atmlocations.getStatus().getStatuscode());
                inputBean.setUlatitude(atmlocations.getLatitude());
                inputBean.setUlongitude(atmlocations.getLongitude());
                inputBean.setId(atmlocations.getId().toString());

                if (atmlocations.getStatusByIsAtm().getStatuscode().equals(CommonVarList.STATUS_YES)) {
                    inputBean.setIsAtm(true);
                } else {
                    inputBean.setIsAtm(false);
                }
                if (atmlocations.getStatusByIsCdm().getStatuscode().equals(CommonVarList.STATUS_YES)) {
                    inputBean.setIsCdm(true);
                } else {
                    inputBean.setIsCdm(false);
                }
                if (atmlocations.getStatusByIsBranch().getStatuscode().equals(CommonVarList.STATUS_YES)) {
                    inputBean.setIsBranch(true);
                } else {
                    inputBean.setIsBranch(false);
                }
                if (atmlocations.getStatusByIsAgent().getStatuscode().equals(CommonVarList.STATUS_YES)) {
                    inputBean.setIsAgent(true);
                } else {
                    inputBean.setIsAgent(false);
                }
                if (atmlocations.getStatusByIsShop().getStatuscode().equals(CommonVarList.STATUS_YES)) {
                    inputBean.setIsShop(true);
                } else {
                    inputBean.setIsShop(false);
                }

            } else {
                inputBean.setMessage("Empty ID.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Locations " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(ATMLocationsAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";

    }

    public String update() {

        System.out.println("called ATMLocationsAction : update");
        System.err.println(inputBean.getUatmcode());
        String retType = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            ATMLocationsDAO dao = new ATMLocationsDAO();
            if (inputBean.getUatmcode() != null && !inputBean.getUatmcode().isEmpty()) {

                String message = this.validateUpdates();

                if (message.isEmpty()) {

                    String newV
                            = inputBean.getUatmcode() + "|"
                            + inputBean.getUdescription() + "|"
                            + inputBean.getUaddress() + "|"
                            + inputBean.getUlatitude() + "|"
                            + inputBean.getUlongitude() + "|"
                            + inputBean.getUstatus() + "|" //                            + inputBean.getUtype()
                            ;

                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.ATM_LOCATIONS, SectionVarList.SYSTEM_CONFIG, "Location code " + inputBean.getUatmcode() + " updated", null, null, newV);
                    message = dao.updateATMLocations(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Location " + MessageVarList.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ATMLocationsAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Locations " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String find() {
        System.out.println("called ATMLoactionsAction: find");
        System.err.println("find : " + inputBean.getId());
        AtmLocations atmlocations = null;

        try {
            if (inputBean.getId() != null && !inputBean.getId().isEmpty()) {

                ATMLocationsDAO dao = new ATMLocationsDAO();

                atmlocations = dao.findATMLocationsById(inputBean.getId());

                inputBean.setId(atmlocations.getId().toString());
                inputBean.setUatmcode(atmlocations.getAtmCode());
                inputBean.setUdescription(atmlocations.getDescription());
                inputBean.setUaddress(atmlocations.getAddress());
                inputBean.setUstatus(atmlocations.getStatus().getStatuscode());
                inputBean.setUlatitude(atmlocations.getLatitude());
                inputBean.setUlongitude(atmlocations.getLongitude());

                if (atmlocations.getStatusByIsAtm().getStatuscode().equals(CommonVarList.STATUS_YES)) {
                    inputBean.setIsAtm(true);
                } else {
                    inputBean.setIsAtm(false);
                }
                if (atmlocations.getStatusByIsCdm().getStatuscode().equals(CommonVarList.STATUS_YES)) {
                    inputBean.setIsCdm(true);
                } else {
                    inputBean.setIsCdm(false);
                }
                if (atmlocations.getStatusByIsBranch().getStatuscode().equals(CommonVarList.STATUS_YES)) {
                    inputBean.setIsBranch(true);
                } else {
                    inputBean.setIsBranch(false);
                }
                if (atmlocations.getStatusByIsAgent().getStatuscode().equals(CommonVarList.STATUS_YES)) {
                    inputBean.setIsAgent(true);
                } else {
                    inputBean.setIsAgent(false);
                }
                if (atmlocations.getStatusByIsShop().getStatuscode().equals(CommonVarList.STATUS_YES)) {
                    inputBean.setIsShop(true);
                } else {
                    inputBean.setIsShop(false);
                }
            } else {
                inputBean.setMessage("Empty ID.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Locations " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(ATMLocationsAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String Delete() {
        System.out.println("called ATMLocationsAction : Delete");
        String message = "";
        String retType = "delete";
        AtmLocations atmlocations = null;
        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            ATMLocationsDAO dao = new ATMLocationsDAO();
            atmlocations = dao.findATMLocationsById(inputBean.getId());
            inputBean.setAtmcode(atmlocations.getAtmCode());

            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.ATM_LOCATIONS, SectionVarList.SYSTEM_CONFIG, "Location code " + inputBean.getAtmcode() + " deleted", null);
            message = dao.deleteATMLocation(inputBean, audit);

            if (message.isEmpty()) {
                message = "Location " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);

        } catch (Exception e) {
            Logger.getLogger(ATMLocationsAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String template() {
        System.out.println("called ATMLocationsAction: template");
        try {
            ServletContext context = ServletActionContext.getServletContext();
            String destPath = context.getRealPath("/resouces/csv_temp/atm_locations");
            File fileToDownload = new File(destPath, "ATML.csv");
            inputStream = new FileInputStream(fileToDownload);
            fileName = fileToDownload.getName();
            contentLength = fileToDownload.length();
        } catch (Exception e) {
            Logger.getLogger(AtmLocations.class.getName()).log(Level.SEVERE, null, e);
            addActionError("ATMLocationsAction " + MessageVarList.COMMON_ERROR_PROCESS);
            return "message";
        }
        return "excelreport";
    }

}
