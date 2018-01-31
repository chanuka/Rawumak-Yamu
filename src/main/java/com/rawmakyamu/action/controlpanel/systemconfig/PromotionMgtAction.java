/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.controlpanel.systemconfig;

import com.rawmakyamu.action.systemaudit.LoginHistoryAction;
import com.rawmakyamu.action.systemaudit.SystemAuditAction;
import com.rawmakyamu.bean.controlpanel.systemconfig.PromotionBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.PromotionInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.controlpanel.systemconfig.PromotionDAO;
import com.rawmakyamu.dao.systemaudit.SystemAuditDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import static com.rawmakyamu.util.common.Common.checkEmptyorNullString;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.common.Validation;
import com.rawmakyamu.util.mapping.MsPromotions;
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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

/**
 *
 * @author : dilanka_w
 * @Created on : Jul 9, 2016, 1:07:26 PM
 */
public class PromotionMgtAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    PromotionInputBean inputBean = new PromotionInputBean();
    Map parameterMap = new HashMap();
    InputStream fileInputStream = null;
    InputStream excelStream = null;

    public PromotionInputBean getInputBean() {
        return inputBean;
    }

    public void setInputBean(PromotionInputBean inputBean) {
        this.inputBean = inputBean;
    }

    public Map getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map parameterMap) {
        this.parameterMap = parameterMap;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public Object getModel() {
        return inputBean;
    }

    public String execute() throws Exception {
        System.out.println("Called PromotionMgtAction: execute");
        return SUCCESS;
    }

    public String View() {
        String result = "view";

        try {
            if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setUserLevelList(dao.getUserLevelPromotion());
                inputBean.setSortOrderList(this.getSortOrderList());

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

            System.out.println("called PromotionMgtAction: view");

        } catch (Exception e) {
            addActionError("Campaign " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PromotionMgtAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String ViewPopup() {
        String result = "viewpopup";
        System.out.println("called PromotionMgtAction : ViewPopup");
        try {
            if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
                PromotionDAO promotionDAO = new PromotionDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setPromotionId(Integer.toString(promotionDAO.getPromotionMaxID()));
                inputBean.setUserLevelList(dao.getUserLevelPromotion());
                inputBean.setSortOrderList(this.getSortOrderList());

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

            //System.out.println("called PromotionMgtAction: viewpopup");

        } catch (Exception e) {
            addActionError("Campaign " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PromotionMgtAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String List() {
        System.out.println("called PromotionMgtAction: List");
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
            PromotionDAO dao = new PromotionDAO();
            List<PromotionBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Campaign ID", inputBean.getPromotionIdSearch())
                        + checkEmptyorNullString("Description", inputBean.getDescriptionSearch())
                        + checkEmptyorNullString("Title", inputBean.getTitleSearch())
                        + checkEmptyorNullString("Click Count", inputBean.getClickCountSearch())
                        + checkEmptyorNullString("Share Count", inputBean.getShareCountSearch())
                        + checkEmptyorNullString("Like Count", inputBean.getLikeCountSearch())
                        + checkEmptyorNullString("Status", inputBean.getStatusSearch())
                        + checkEmptyorNullString("Sort Order", inputBean.getSortOrderSearch())
                        + checkEmptyorNullString("User Level", inputBean.getUserLevelSearch())
                        + "]";

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.PROMOTION_MGT_PAGE, SectionVarList.SYSTEM_CONFIG, "Sbank Campaign search using " + searchParameters + " parameters ", null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);
            }

            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                inputBean.setRecords(records);
                inputBean.setGridModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);

                HttpSession session = ServletActionContext.getRequest().getSession(false);
                session.setAttribute(SessionVarlist.PROMOTION_REPORT, inputBean);

            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }
        } catch (Exception e) {
            Logger.getLogger(PromotionMgtAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Campaign " + MessageVarList.COMMON_ERROR_PROCESS);
        }
        return "list";
    }

    public String getImageMobile(String file) {
        String msgEx = "";
        if (file == null) {
            msgEx = "Please choose a file to upload.";
        } else {
            msgEx = Validation.isImageMOB(file);
        }
        return msgEx;
    }

    public String getImageTab(String file) {
        String msgEx = "";
        if (file == null) {
            msgEx = "Please choose a file to upload.";
        } else {
            msgEx = Validation.isImageTAB(file);
        }
        return msgEx;
    }

    public String Add() {
        System.out.println("called PromotionMgtAction: add");
        String result = "add";
        String tabmsg, mobilemsg = "";
        BufferedImage readImage = null;
        File destFile;
        ServletContext context = ServletActionContext.getServletContext();
        String destPath = context.getRealPath("/resouces/images/cardproduct");

        try {
            if (inputBean.getHiddenId() != null) {
                HttpServletRequest request = ServletActionContext.getRequest();

                PromotionDAO dao = new PromotionDAO();
                String message = this.validateInputs();

                if (message.isEmpty()) {

                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.ADD_TASK, PageVarList.PROMOTION_MGT_PAGE, SectionVarList.SYSTEM_CONFIG, "Campaign ID " + inputBean.getPromotionId() + " added ", null, null, null);

                    message = this.getImageTab(this.inputBean.getTabImgFileName()); // get file

                    if (message.isEmpty()) {
                        destFile = new File(destPath, inputBean.getTabImgFileName() + ".jpg");
                        FileUtils.copyFile(inputBean.getTabImg(), destFile);
                        readImage = ImageIO.read(new File(destPath, inputBean.getTabImgFileName() + ".jpg"));

                        int height = readImage.getHeight();
                        int width = readImage.getWidth();

                        System.err.println(height + " " + width);
                        if (width > 1200) {
                            message = "Tab image width should be less than 1200 pixels";
                        }
                        if (height > 800) {
                            message = "Tab image height should be less than 800 pixels";
                        }

                        if (width < 300) {
                            message = "Tab image width should be greater than 300 pixels";
                        }
                        if (height < 350) {
                            message = "Tab image height should be greater than 350 pixels";
                        }

                        if (height < (int) (width / 2)) {
                            message = "Tab image height should be greater than width/2 pixels";
                        }

                        if (height > (int) (width * 2)) {
                            message = "Tab image height should be less than width*2 pixels";
                        }

                    }
                    if (message.isEmpty()) {
                        message = this.getImageMobile(this.inputBean.getMobileImgFileName()); // get file

                        if (message.isEmpty()) {

                            destFile = new File(destPath, inputBean.getMobileImgFileName() + ".jpg");
                            FileUtils.copyFile(inputBean.getMobileImg(), destFile);
                            readImage = ImageIO.read(new File(destPath, inputBean.getMobileImgFileName() + ".jpg"));

                            int height = readImage.getHeight();
                            int width = readImage.getWidth();

                            System.err.println(height + " " + width);
                            if (width > 1200) {
                                message = "Mobile image width should be less than 1200 pixels";
                            }
                            if (height > 800) {
                                message = "Mobile image height should be less than 800 pixels";
                            }
                            if (width < 300) {
                                message = "Mobile image width should be greater than 300 pixels";
                            }
                            if (height < 350) {
                                message = "Mobile image height should be greater than 350 pixels";
                            }

                            if (height < (int) (width / 2)) {
                                message = "Mobile image height should be greater than width/2 pixels";
                            }

                            if (height > (int) (width * 2)) {
                                message = "Mobile image height should be less than width*2 pixels";
                            }
                        }
                    }

//                tabmsg = this.saveTabImage();
//                mobilemsg = this.saveMobileImage();
//
//                if (tabmsg.isEmpty() && mobilemsg.isEmpty()) {
//                    message = dao.insertPromotion(inputBean, audit);
//                    if (message.isEmpty()) {
//                        addActionMessage("Promotion " + MessageVarList.COMMON_SUCC_INSERT);
//                    } else {
//                        addActionError(message);
//                    }
//                } else {
//
//                    if (!tabmsg.isEmpty()) {
//                        addActionError(tabmsg);
//                    } else if (!mobilemsg.isEmpty()) {
//                        addActionError(mobilemsg);
//                    }
//                }
                    if (message.isEmpty()) {
                        message = dao.insertPromotion(inputBean, audit);
                    }

                    if (message.isEmpty()) {
                        addActionMessage("Campaign " + MessageVarList.COMMON_SUCC_INSERT);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception e) {
            addActionError("Campaign " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PromotionMgtAction.class.getName()).log(Level.SEVERE, null, e);
        }

        return result;
    }

    public String Find() {
        System.out.println("called PromotionMgtAction: find");
        MsPromotions promotions = null;

        try {
            if (inputBean.getPromotionId() != null && !inputBean.getPromotionId().isEmpty()) {

                PromotionDAO dao = new PromotionDAO();

                promotions = dao.findPromotionById(inputBean.getPromotionId());

                inputBean.setPromotionId(promotions.getPromotionId().toString());
                inputBean.setDescription(promotions.getDescription());
                inputBean.setTitle(promotions.getTitle());
                inputBean.setStatus(promotions.getStatus().getStatuscode());
                inputBean.setStartDate(Common.getFormattedDate2(promotions.getStartDate()));
                inputBean.setExpDate(Common.getFormattedDate2(promotions.getExpDate()));
                inputBean.setSortOrder(promotions.getSortOrder().toString());
                inputBean.setUserLevel(promotions.getUserLevel().getCode());

//                inputBean.setEdittabImg(this.loadImage(promotions.getTabImgName(), "tab"));
//                inputBean.setEditmobileImg(this.loadImage(promotions.getMobImgName(), "mobile"));
                try {
                    inputBean.setEdittabImg(promotions.getTabimg());
                } catch (Exception ex) {
                }

                try {
                    inputBean.setEditmobileImg(promotions.getMobimg());
                } catch (Exception ex) {
                }

            } else {
                inputBean.setMessage("Empty Campaign ID.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Campaign " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PromotionMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String Detail() {
        System.out.println("called PromotionMgtAction: detail");
        MsPromotions promotions = null;

        try {
            if (inputBean.getPromotionId() != null && !inputBean.getPromotionId().isEmpty()) {

                PromotionDAO dao = new PromotionDAO();
                CommonDAO commonDAO = new CommonDAO();
                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
                inputBean.setUserLevelList(commonDAO.getUserLevelPromotion());
                inputBean.setSortOrderList(this.getSortOrderList());

                promotions = dao.findPromotionById(inputBean.getPromotionId());

                inputBean.setPromotionId(promotions.getPromotionId().toString());
                inputBean.setDescription(promotions.getDescription());
                inputBean.setTitle(promotions.getTitle());
                inputBean.setStatus(promotions.getStatus().getStatuscode());
                inputBean.setStartDate(Common.getFormattedDate2(promotions.getStartDate()));
                inputBean.setExpDate(Common.getFormattedDate2(promotions.getExpDate()));
                inputBean.setSortOrder(promotions.getSortOrder().toString());
                inputBean.setUserLevel(promotions.getUserLevel().getCode());

//                inputBean.setEdittabImg(this.loadImage(promotions.getTabImgName(), "tab"));
//                inputBean.setEditmobileImg(this.loadImage(promotions.getMobImgName(), "mobile"));
                try {
                    inputBean.setEdittabImg(promotions.getTabimg());
                } catch (Exception ex) {
                }

                try {
                    inputBean.setEditmobileImg(promotions.getMobimg());
                } catch (Exception ex) {
                }

            } else {
                inputBean.setMessage("Empty Campaign ID.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Campaign " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(PromotionMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";

    }

    public String Update() {
        System.out.println("called PromotionMgtAction : update");
        String retType = "update";
//        String tabmsg = "", mobilemsg = "";
        BufferedImage readImage = null;
        File destFile;
        ServletContext context = ServletActionContext.getServletContext();
        String destPath = context.getRealPath("/resouces/images/cardproduct");

        try {
            if (inputBean.getHiddenIdEdit() != null) {
                if (inputBean.getPromotionId() != null && !inputBean.getPromotionId().isEmpty()) {

                    //set username get in hidden fileds
                    inputBean.setPromotionId(inputBean.getPromotionId());

                    String message = this.validateInputsForUpdate();

                    if (message.isEmpty()) {

                        HttpServletRequest request = ServletActionContext.getRequest();
                        PromotionDAO dao = new PromotionDAO();

                        Systemaudit audit = Common.makeAudittrace(request, TaskVarList.UPDATE_TASK, PageVarList.PROMOTION_MGT_PAGE, SectionVarList.SYSTEM_CONFIG, "Campaign ID " + inputBean.getPromotionId() + " updated", null, null, null);

                        if (inputBean.getTabImgFileName() != null) {
                            message = this.getImageTab(inputBean.getTabImgFileName());
                            if (message.isEmpty()) {
                                destFile = new File(destPath, inputBean.getTabImgFileName() + ".jpg");
                                FileUtils.copyFile(inputBean.getTabImg(), destFile);
                                readImage = ImageIO.read(new File(destPath, inputBean.getTabImgFileName() + ".jpg"));

                                int height = readImage.getHeight();
                                int width = readImage.getWidth();

                                System.err.println(height + " " + width);
                                if (width > 1200) {
                                    message = "Tab image width should be less than 1200 pixels";
                                }
                                if (height > 800) {
                                    message = "Tab image height should be less than 800 pixels";
                                }

                                if (width < 300) {
                                    message = "Tab image width should be greater than 300 pixels";
                                }
                                if (height < 350) {
                                    message = "Tab image height should be greater than 350 pixels";
                                }

                                if (height < (int) (width / 2)) {
                                    message = "Tab image height should be greater than width/2 pixels";
                                }

                                if (height > (int) (width * 2)) {
                                    message = "Tab image height should be less than width*2 pixels";
                                }

                            }

                        }

                        if (inputBean.getMobileImgFileName() != null) {
                            message = this.getImageMobile(inputBean.getMobileImgFileName());
                            if (message.isEmpty()) {

                                destFile = new File(destPath, inputBean.getMobileImgFileName() + ".jpg");
                                FileUtils.copyFile(inputBean.getMobileImg(), destFile);
                                readImage = ImageIO.read(new File(destPath, inputBean.getMobileImgFileName() + ".jpg"));

                                int height = readImage.getHeight();
                                int width = readImage.getWidth();

                                System.err.println(height + " " + width);
                                if (width > 1200) {
                                    message = "Mobile image width should be less than 1200 pixels";
                                }
                                if (height > 800) {
                                    message = "Mobile image height should be less than 800 pixels";
                                }
                                if (width < 300) {
                                    message = "Mobile image width should be greater than 300 pixels";
                                }
                                if (height < 350) {
                                    message = "Mobile image height should be greater than 350 pixels";
                                }

                                if (height < (int) (width / 2)) {
                                    message = "Mobile image height should be greater than width/2 pixels";
                                }

                                if (height > (int) (width * 2)) {
                                    message = "Mobile image height should be less than width*2 pixels";
                                }
                            }
                        }

                        if (message.isEmpty()) {

                            message = dao.updatePromotion(inputBean, audit);
                            if (message.isEmpty()) {
                                addActionMessage("Campaign " + MessageVarList.COMMON_SUCC_UPDATE);
                            } else {
                                addActionError(message);
                            }
//                            } else if (!tabmsg.isEmpty() && mobilemsg.isEmpty()) {
//                                addActionError(tabmsg);
//                            } else if (tabmsg.isEmpty() && !mobilemsg.isEmpty()) {
//                                addActionError(mobilemsg);
                        } else {
                            addActionError(message);
                        }

                    } else {
                        addActionError(message);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PromotionMgtAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Campaign " + MessageVarList.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String Delete() {
        System.out.println("called PromotionMgtAction : Delete");
        String message = null;
        String retType = "delete";

        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            PromotionDAO dao = new PromotionDAO();

            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.DELETE_TASK, PageVarList.PROMOTION_MGT_PAGE, SectionVarList.SYSTEM_CONFIG, "Campaign ID " + inputBean.getPromotionId() + " deleted", null);
            message = dao.deletePromotion(inputBean, audit);

            if (message.isEmpty()) {
                message = "Campaign " + MessageVarList.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);

        } catch (Exception e) {
            Logger.getLogger(PromotionMgtAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String ReportGenerate() {
        System.out.println("called PromotionMgtAction : reportGeneration");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm a");
        JRSwapFileVirtualizer virtualizer = null;
        JasperPrint jasperPrint = null;
        byte[] outputFile;
        Session hSession = null;
        String retMsg = "view";

        try {
            if (inputBean.getReporttype().equals("pdf")) {
                cal.setTime(CommonDAO.getSystemDateLogin());
//            connection = CommonDAO.getConnection();
                HttpSession session = ServletActionContext.getRequest().getSession(false);
                PromotionInputBean searchBean = (PromotionInputBean) session.getAttribute(SessionVarlist.PROMOTION_REPORT);

                //get path
                ServletContext context = ServletActionContext.getServletContext();
                String imgPath = context.getRealPath("/resouces/images/ntb.png");

                if (inputBean.getPromotionIdSearch() != null && !inputBean.getPromotionIdSearch().isEmpty()) {
                    parameterMap.put("promotionid", inputBean.getPromotionIdSearch().trim());
                } else {
                    parameterMap.put("promotionid", "--");
                }
                if (inputBean.getDescriptionSearch() != null && !inputBean.getDescriptionSearch().isEmpty()) {
                    parameterMap.put("description", inputBean.getDescriptionSearch().trim());
                } else {
                    parameterMap.put("description", "--");
                }
                if (inputBean.getClickCountSearch() != null && !inputBean.getClickCountSearch().isEmpty()) {
                    parameterMap.put("ccount", inputBean.getClickCountSearch().trim());
                } else {
                    parameterMap.put("ccount", "--");
                }
                if (inputBean.getShareCountSearch() != null && !inputBean.getShareCountSearch().isEmpty()) {
                    parameterMap.put("scount", inputBean.getShareCountSearch().trim());
                } else {
                    parameterMap.put("scount", "--");
                }
                if (inputBean.getLikeCountSearch() != null && !inputBean.getLikeCountSearch().isEmpty()) {
                    parameterMap.put("lcount", inputBean.getLikeCountSearch().trim());
                } else {
                    parameterMap.put("lcount", "--");
                }
                if (inputBean.getSortOrderSearch() != null && !inputBean.getSortOrderSearch().isEmpty()) {
                    parameterMap.put("sortorder", inputBean.getSortOrderSearch().trim());
                } else {
                    parameterMap.put("sortorder", "--");
                }
                if (inputBean.getUserLevelSearch() != null && !inputBean.getUserLevelSearch().isEmpty()) {
                    CommonDAO cdoa = new CommonDAO();
                    parameterMap.put("userleveldes", cdoa.getUserlevelByprefix(inputBean.getUserLevelSearch().trim()));
                    parameterMap.put("userlevel", inputBean.getUserLevelSearch().trim());
                } else {
                    parameterMap.put("userlevel", "--");
                    parameterMap.put("userleveldes", "--");
                }
                if (inputBean.getTitleSearch() != null && !inputBean.getTitleSearch().isEmpty()) {
                    parameterMap.put("title", inputBean.getTitleSearch().trim());
                } else {
                    parameterMap.put("title", "--");
                }
                if (inputBean.getStatusSearch() != null && !inputBean.getStatusSearch().isEmpty()) {
                    CommonDAO cdoa = new CommonDAO();
                    inputBean.setStatusDes(cdoa.getStatusByprefix(inputBean.getStatusSearch()));
                    parameterMap.put("status", inputBean.getStatusSearch().trim());
                    System.err.println(inputBean.getStatusSearch().trim());
                    parameterMap.put("statusDes", inputBean.getStatusDes().trim());
                } else {
                    parameterMap.put("status", "--");
                    parameterMap.put("statusDes", "--");
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

                String reportLocation = context.getRealPath("WEB-INF/pages/controlpanel/systemconfig/promotionmgt/report/promotion_report.jasper");

                hSession = HibernateInit.sessionFactory.openSession();
                SessionImplementor sim = (SessionImplementor) hSession;

                jasperPrint = JasperFillManager.fillReport(reportLocation, parameterMap, sim.connection());

                if (virtualizer != null) {
                    virtualizer.setReadOnly(true);
                }

                outputFile = JasperExportManager.exportReportToPdf(jasperPrint);
                fileInputStream = new ByteArrayInputStream(outputFile);

                HttpServletRequest request = ServletActionContext.getRequest();
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.PROMOTION_MGT_PAGE, SectionVarList.SYSTEM_CONFIG, "Sbank campaign PDF report generated", null);
                CommonDAO.saveAudit(audit);

                retMsg = "download";
            } else if (inputBean.getReporttype().trim().equalsIgnoreCase("exel")) {
                System.err.println("EXEL printing");
                PromotionDAO dao = new PromotionDAO();
                retMsg = "excelreport";
                ByteArrayOutputStream outputStream = null;
                try {

                    HttpSession session = ServletActionContext.getRequest().getSession(false);

                    PromotionInputBean searchBean = (PromotionInputBean) session.getAttribute(SessionVarlist.PROMOTION_REPORT);
//                    Audittrace audittrace = Common.makeAudittrace(request, TaskVarList.REPORT_TASK, PageVarList.EXCEPTIONS_RPT_PAGE, this.getSearchParam() + " excel report viewed", null);
//                    Object object = new Object();
                    Object object = dao.generateExcelReport(searchBean);
                    if (object instanceof SXSSFWorkbook) {
                        SXSSFWorkbook workbook = (SXSSFWorkbook) object;
                        outputStream = new ByteArrayOutputStream();
                        workbook.write(outputStream);
                        inputBean.setExcelStream(new ByteArrayInputStream(outputStream.toByteArray()));

                    } else if (object instanceof ByteArrayOutputStream) {
                        outputStream = (ByteArrayOutputStream) object;
                        inputBean.setZipStream(new ByteArrayInputStream(outputStream.toByteArray()));
                        retMsg = "zip";
                    }

                    HttpServletRequest request = ServletActionContext.getRequest();
                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.PROMOTION_MGT_PAGE, SectionVarList.SYSTEM_CONFIG, "Sbank campaign excel report generated ", null);
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

    private void loadPageData() {
        try {
            CommonDAO dao = new CommonDAO();
            inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));
            inputBean.setUserLevelList(dao.getUserLevelPromotion());
            inputBean.setSortOrderList(this.getSortOrderList());

        } catch (Exception e) {
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " SystemAudit");
            Logger.getLogger(SystemAuditAction.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.PROMOTION_MGT_PAGE, request);

        inputBean.setVadd(true);
        inputBean.setVdelete(true);
        inputBean.setVupdatelink(true);
        inputBean.setVgenerate(true);
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
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.GENERATE_TASK)) {
                    inputBean.setVgenerate(false);
                } else if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                }
            }
        }
        inputBean.setVupdatebutt(true);

        return true;
    }

    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.PROMOTION_MGT_PAGE;
        String task = null;

        if ("View".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("List".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Add".equals(method)) {
            task = TaskVarList.ADD_TASK;
        } else if ("Delete".equals(method)) {
            task = TaskVarList.DELETE_TASK;
        } else if ("Find".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("ViewPopup".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Update".equals(method)) {
            task = TaskVarList.UPDATE_TASK;
        } else if ("Detail".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("ReportGenerate".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        }//newly changed
        else if ("activate".equals(method)) {
            task = TaskVarList.UPDATE_TASK;
        }
        if ("execute".equals(method)) {
            status = true;
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            status = new Common().checkMethodAccess(task, page, userRole, request);
        }
        return status;
    }

    private String validateInputs() {
        String message = "";

        if (inputBean.getTabImgFileName() == null || inputBean.getTabImgFileName().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_TAB_IMAGE_EMPTY;
        } else if (inputBean.getMobileImgFileName() == null || inputBean.getMobileImgFileName().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_MOBILE_IMAGE_EMPTY;
        } else if (inputBean.getPromotionId() == null || inputBean.getPromotionId().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_ID_EMPTY;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_DESC_EMPTY;
        } else if (inputBean.getTitle() == null || inputBean.getTitle().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_TITLE_EMPTY;
        } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_STATUS_EMPTY;
        } else if (inputBean.getSortOrder() == null || inputBean.getSortOrder().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_SORT_ORDER_EMPTY;
        } else if (inputBean.getUserLevel() == null || inputBean.getUserLevel().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_USER_LEVEL_EMPTY;
        } else if (inputBean.getStartDate() == null || inputBean.getStartDate().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_START_DATE_EMPTY;
        } else if (inputBean.getExpDate() == null || inputBean.getExpDate().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_EXP_DATE_EMPTY;
        }

        return message;
    }

    private String validateInputsForUpdate() {
        String message = "";

        if (inputBean.getPromotionId() == null || inputBean.getPromotionId().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_ID_EMPTY;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_DESC_EMPTY;
        } else if (inputBean.getTitle() == null || inputBean.getTitle().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_TITLE_EMPTY;
        } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_STATUS_EMPTY;
        } else if (inputBean.getSortOrder() == null || inputBean.getSortOrder().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_SORT_ORDER_EMPTY;
        } else if (inputBean.getUserLevel() == null || inputBean.getUserLevel().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_USER_LEVEL_EMPTY;
        } else if (inputBean.getStartDate() == null || inputBean.getStartDate().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_START_DATE_EMPTY;
        } else if (inputBean.getExpDate() == null || inputBean.getExpDate().trim().isEmpty()) {
            message = MessageVarList.PROMOTION_EXP_DATE_EMPTY;
        }

        return message;
    }

    /**
     * save tab image
     *
     * @return
     */
    private String saveTabImage() {
        String message = "";
        try {
            message = Validation.isImageIB(inputBean.getTabImgFileName());
            if (message.isEmpty()) {
                PromotionDAO dao = new PromotionDAO();
                String filePath = dao.getImageURL();
                String tabFilePath = filePath + "/" + inputBean.getPromotionId() + "/tabImage";

                //String tabImgFileName = inputBean.getPromotionId() + ".jpg";
                File tabfileToCreate = new File(tabFilePath, inputBean.getTabImgFileName());
                FileUtils.copyFile(inputBean.getTabImg(), tabfileToCreate);

                //use for ftp
                //this.sendFile(inputBean.getMobileImg(), inputBean.getMobileImgFileName());
            }

        } catch (Exception ex) {
            message = MessageVarList.COMMON_ERROR_PROCESS;
            Logger.getLogger(PromotionMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

    /**
     * save mobile image
     *
     * @return
     */
    private String saveMobileImage() {
        String message = "";
        try {
            message = Validation.isImageIB(inputBean.getTabImgFileName());
            if (message.isEmpty()) {
                PromotionDAO dao = new PromotionDAO();

                String filePath = dao.getImageURL();
                String mobileFilePath = filePath + "/" + inputBean.getPromotionId() + "/mobileImage";

                //String mobileImgFileName = inputBean.getPromotionId() + ".jpg";
                File mobilefileToCreate = new File(mobileFilePath, inputBean.getMobileImgFileName());
                FileUtils.copyFile(inputBean.getMobileImg(), mobilefileToCreate);
            }

        } catch (Exception ex) {
            message = MessageVarList.COMMON_ERROR_PROCESS;
            Logger.getLogger(PromotionMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

    /**
     * get image
     *
     * @param imageName
     * @param type (Tab image or mobile image)
     * @return
     */
//    public byte[] loadImage(String imageName, String type) {
//
//        File imgPath = null;
//        byte[] imageInByte = null;
//
//        try {
//
//            PromotionDAO dao = new PromotionDAO();
//
//            String filePath = dao.getImageURL();
//
//            String tabFilePath = filePath + "/" + inputBean.getPromotionId() + "/tabImage" + "/" + imageName;
//            String mobileFilePath = filePath + "/" + inputBean.getPromotionId() + "/mobileImage" + "/" + imageName;
//
//            // open image
//            if (type.equals("tab")) {
//                imgPath = new File(tabFilePath);
//            } else {
//                imgPath = new File(mobileFilePath);
//            }
//
//            BufferedImage image = ImageIO.read(imgPath);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(image, "jpg", baos);
//            imageInByte = baos.toByteArray();
//
//        } catch (Exception ex) {
//            Logger.getLogger(PromotionMgtAction.class.getName()).log(Level.SEVERE, null, ex);
//        }finally{
//
//        }
//        return (imageInByte);
//    }
    /**
     * get user level list
     *
     * @return
     */
    private HashMap<String, String> getUserLevelList() {
        HashMap<String, String> userLevel = new HashMap<String, String>();

        userLevel.put("0", "All");
        userLevel.put("1", "Level 1");
        userLevel.put("2", "Level 2");
        userLevel.put("3", "Level 3");

        return userLevel;
    }

    /**
     * get sort order list
     *
     * @return
     */
    private HashMap<String, String> getSortOrderList() {

        HashMap<String, String> sortOrder = new HashMap<String, String>();

        sortOrder.put("0", "0");
        sortOrder.put("1", "1");
        sortOrder.put("2", "2");
        sortOrder.put("3", "3");

        return sortOrder;
    }

//    /**
//     * send file (FTP)
//     *
//     * @param report
//     * @param name
//     */
//    public void sendFile(File report, String name) {
//        FTPClient client = new FTPClient();
//        FileInputStream fis = null;
//
//        try {
//            client.connect("192.168.1.34");
//            client.login("ftpuser", "password");
//            System.out.println("Connected to ftp server");
//
//            // Create an InputStream of the file to be uploaded
//            fis = new FileInputStream(report.getAbsolutePath());
//
//            // Transfer file to server
//            System.out.println("File Transfer Started");
//            if (client.storeFile("/home/ftpuser/" + name, fis)) {
//                System.out.println("File Transfered Successfully");
//
//            } else {
//                System.out.println("File Transfered Failed");
//
//            }
//            client.logout();
//
//        } catch (Exception e) {
//
//        } finally {
//            try {
//                if (fis != null) {
//                    fis.close();
//                    System.out.println("LogOut From The FTP Server");
//                }
//                //Temporaray File Deleted
//                String fileInput = "C:/" + name;
//                deleteFile(fileInput);
//                client.disconnect();
//                System.out.println("**********************************************End*************************************************");
//            } catch (Exception e) {
//                System.out.println("----------- " + e);
//            }
//        }
//    }
//
//    private boolean deleteFile(String fileName) {
//        File file = new File(fileName);
//        if (file.exists()) {
//            boolean result = file.delete();
//            // test if delete of file is success or not
//            if (result) {
//                System.out.println("File " + fileName + " Deleted Successfully");
//            } else {
//                System.out.println("File was not deleted, unknown reason");
//            }
//            return result;
//        } else {
//            System.out.println("File delete failed, file does not exists");
//            return false;
//        }
//    }
}
