/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.action.reportexplorer;

import com.rawmakyamu.bean.reportexplorer.TransDataBean;
import com.rawmakyamu.bean.reportexplorer.TxnDataBean;
import com.rawmakyamu.bean.reportexplorer.TxnExplorerInputBean;
import com.rawmakyamu.bean.reportexplorer.TxnHisDataBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.dao.reportexplorer.TransactionExplorerDAO;
import com.rawmakyamu.dao.systemaudit.SystemAuditDAO;
import com.rawmakyamu.util.common.AccessControlService;
import com.rawmakyamu.util.common.Common;
import static com.rawmakyamu.util.common.Common.checkEmptyorNullString;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.Status;
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
public class TransactionExplorerAction extends ActionSupport implements ModelDriven<Object>, AccessControlService {

    TxnExplorerInputBean inputBean = new TxnExplorerInputBean();
    Map parameterMap = new HashMap();
    InputStream fileInputStream = null;
    TransDataBean audata = new TransDataBean();

    public TransDataBean getAudata() {
        return audata;
    }

    public Map getParameterMap() {
        return parameterMap;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public Object getModel() {
        return inputBean;
    }

    @Override
    public boolean checkAccess(String method, String userRole) {
        boolean status = false;
        String page = PageVarList.TXN_EXPLORER;
        String task = null;
        if ("View".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("Search".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("List".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("viewDetail".equals(method)) {
            task = TaskVarList.VIEW_TASK;
        } else if ("individualReport".equals(method)) {
            task = TaskVarList.GENERATE_TASK;
        } else if ("reportGenerate".equals(method)) {
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
        List<Task> tasklist = new Common().getUserTaskListByPage(PageVarList.TXN_EXPLORER, request);

        inputBean.setVgenerate(true);
        inputBean.setVsearch(true);
        inputBean.setVgenerateview(true);

        if (tasklist != null && tasklist.size() > 0) {
            for (Task task : tasklist) {
                if (task.getTaskcode().equalsIgnoreCase(TaskVarList.GENERATE_TASK)) {
                    inputBean.setVgenerate(false);
                    inputBean.setVgenerateview(false);
                } else if (task.getTaskcode().equalsIgnoreCase(TaskVarList.SEARCH_TASK)) {
                    inputBean.setVsearch(false);
                } else if (task.getTaskcode().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {
                }
            }
        }

        return true;
    }

    public String View() {

        String result = "view";
        try {
            if (this.applyUserPrivileges()) {
                CommonDAO dao = new CommonDAO();
                inputBean.setTxnTypeList(dao.getTxnTypeCodeList());
                inputBean.setCurrencyList(dao.getCurrencyList());
//                inputBean.setDeviceCategoryList(dao.getDefultDeviceCategoryList());
                inputBean.setStatusList(dao.getDefultStatusList("TRXN"));
//                inputBean.setStatusList(dao.getDefultStatusList("DFLT"));
//                inputBean.setResponseList(dao.getRensponseList());

                /**
                 * for approval status
                 */
                List<Status> approvalStatusList = new ArrayList<Status>();

                approvalStatusList.add(dao.getStatusByCode(CommonVarList.STATUS_TXN_APR_APPROVE));
                approvalStatusList.add(dao.getStatusByCode(CommonVarList.STATUS_TXN_APR_PEND));
                approvalStatusList.add(dao.getStatusByCode(CommonVarList.STATUS_TXN_APR_REJECT));
                approvalStatusList.add(dao.getStatusByCode(CommonVarList.STATUS_TXN_APR_TXTO));

                inputBean.setApprovalStatusList(approvalStatusList);

                HttpSession session = ServletActionContext.getRequest().getSession(false);
                TxnExplorerInputBean data = (TxnExplorerInputBean) session.getAttribute(SessionVarlist.TRANS_EXPOR_SEARCHBEAN);

//                System.out.println("data "+data);
                if (inputBean.getLoad() != null && inputBean.getLoad().equals("yes") && data != null) {

                    TransDataBean bean = new TransDataBean();
                    bean.setAmount(Common.toCurrencyFormat(Common.toCurrencyFormat(data.getAmount())));
                    bean.setApprovecode(data.getApproveCode());
                    bean.setCardno(data.getCardNo());
                    bean.setDeviceid(data.getDeviceID());
                    bean.setTxnid(data.getTransactionID());
//                    bean.setInvoiceno(data.getInvoiceNo());
                    bean.setMobileNo(data.getMobileNo());
                    bean.setRescode(data.getResCode());
                    bean.setWalletid(data.getWalletid());
                    bean.setProcessingcode(data.getProcessingcode());
                    bean.setTraceno(data.getTraceNo());
                    bean.setMid(data.getMid());
                    bean.setTid(data.getTid());
                    bean.setNii(data.getNic());
                    bean.setCurrency(data.getCurrency());
                    bean.setTxntypecode(data.getTxnTypeCode());

                    String[] days = data.getTodate().split("-");
                    bean.setTodate(days[0] + "-" + days[1] + "-" + days[2]);

                    days = data.getFromdate().split("-");
                    bean.setFromdate(days[0] + "-" + days[1] + "-" + days[2]);

                    inputBean.setTransdataBean(bean);
//                    this.Search();
//                    inputBean.setSearch(false);
                }

            } else {
                result = "loginpage";
            }

            System.out.println("called TransactionExplorerAction :view");

        } catch (Exception ex) {
            addActionError("Transaction explorer " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String individualReport() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm a");
        System.out.println("called TransactionExplorerAction: individualReport");
        try {
//            cal.setTime(CommonDAO.getSystemDateLogin()); 
            HttpServletRequest request = ServletActionContext.getRequest();
            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.TXN_EXPLORER, SectionVarList.REPORT_EXPLORER, "Transaction explorer individual report generated", null);
            SystemAuditDAO dao = new SystemAuditDAO();
            dao.saveAudit(audit);
//        
            //get image path
            ServletContext context = ServletActionContext.getServletContext();
            String imgPath = context.getRealPath("/resouces/images/ntb.png");

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            audata = (TransDataBean) session.getAttribute(SessionVarlist.TRANS_EXPOR_INDIVIDUAL_BEAN);

            parameterMap.put("bankaddressheader", CommonVarList.REPORT_SBANK_ADD_HEADER);
//            parameterMap.put("totalrecordcount", new Long(searchBean.getFullCount()).toString());
            parameterMap.put("printeddate", sdf.format(cal.getTime()));
            parameterMap.put("bankaddress", CommonVarList.REPORT_SBANK_ADD);
            parameterMap.put("banktel", CommonVarList.REPORT_SBANK_TEL);
            parameterMap.put("bankmail", CommonVarList.REPORT_SBANK_MAIL);
            parameterMap.put("imageurl", imgPath);

        } catch (Exception e) {
            Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Transaction explorer " + MessageVarList.COMMON_ERROR_PROCESS);
            return "message";
        }
        return "report";
    }

    public String viewDetail() {
        System.out.println("called TransactionExplorerAction :viewDetail");
        try {
            this.applyUserPrivileges();
            TransactionExplorerDAO dao = new TransactionExplorerDAO();
            TransDataBean dataBean = dao.findTransById(inputBean.getTransactionID());

//            dataBean.setResponseDes(dao.getResponseDesbycode(dataBean.getRescode()));
            dataBean.setTxntypeDes(dao.getTxnDesbycode(dataBean.getTxntypecode()));
            dataBean.setCurrencydes(dao.getCurrencyDesbycode(dataBean.getCurrency()));

            inputBean.setTransdataBean(dataBean);

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            session.setAttribute(SessionVarlist.TRANS_EXPOR_INDIVIDUAL_BEAN, dataBean);

            HttpServletRequest request = ServletActionContext.getRequest();
//            Systemaudit audit = Common.makeAudittrace(request, TaskVarList.VIEW_TASK, PageVarList.TXN_EXPLORER, SectionVarList.REPORT_EXPLORER, "Transaction explorer individual view", null, null, null);
//            CommonDAO.saveAudit(audit);
//            int rows = inputBean.getRows();
//                int page = inputBean.getPage();
//                int to = (rows * page);
//                int from = to - rows;
//                long records = 0;
//                String orderBy = "";
//                if (!inputBean.getSidx().isEmpty()) {
//                    orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
//                }
//                
//                List<TxnDataBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);
//            
//                if (!dataList.isEmpty()) {
//
//                    records = dataList.get(0).getFullCount();
//                    inputBean.setRecords(records);
//                    inputBean.setGridModel(dataList);
//                    int total = (int) Math.ceil((double) records / (double) rows);
//                    inputBean.setTotal(total);
//                    
//                    
//                } else {
//                    inputBean.setRecords(0L);
//                    inputBean.setTotal(0);
//                }

        } catch (Exception ex) {
            addActionError("Transaction explorer " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "viewdetail";
    }

    public String List() {

        System.out.println("called TransactionExplorerAction: List");
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
            TransactionExplorerDAO dao = new TransactionExplorerDAO();
            List<TxnHisDataBean> dataList = dao.getSearchHistoryList(inputBean, rows, from, orderBy);

            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                inputBean.setRecords(records);
                inputBean.setGridHisModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);
            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }
            // }
        } catch (Exception e) {
            Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Transaction explorer " + MessageVarList.COMMON_ERROR_PROCESS);
        }
        return "list";
    }

    public String Search() {
        System.out.println("called TransactionExplorerAction : search");
        try {
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            TxnExplorerInputBean data = (TxnExplorerInputBean) session.getAttribute(SessionVarlist.TRANS_EXPOR_SEARCHBEAN);

            if (inputBean.isSearch() == false && inputBean.getLoad() != null && inputBean.getLoad().equals("yes") && data != null) {
                TransactionExplorerDAO dao = new TransactionExplorerDAO();

                inputBean.setFromdate(data.getFromdate());
                inputBean.setTodate(data.getTodate());
                inputBean.setAmount(Common.toCurrencyFormat(data.getAmount()));
                inputBean.setApproveCode(data.getApproveCode());
                inputBean.setCardNo(data.getCardNo());
                inputBean.setDeviceID(data.getDeviceID());
                inputBean.setTransactionID(data.getTransactionID());
//                inputBean.setInvoiceNo(data.getInvoiceNo());
                inputBean.setMobileNo(data.getMobileNo());
//                inputBean.setResCode(dao.getResponseDesbycode(data.getResCode()));
                inputBean.setWalletid(data.getWalletid());
                inputBean.setProcessingcode(data.getProcessingcode());
                inputBean.setTraceNo(data.getTraceNo());
                inputBean.setMid(data.getMid());
                inputBean.setTid(data.getTid());
                inputBean.setCurrency(data.getCurrency());
                inputBean.setTocardno(data.getTocardno());
                inputBean.setFromcardno(data.getFromcardno());
                inputBean.setTocardno(data.getTocardno());
                inputBean.setTxnTypeCode(dao.getTxnDesbycode(data.getTxnTypeCode()));
                inputBean.setToMobileNo(data.getToMobileNo());
                inputBean.setTokenid(data.getTokenid());
                inputBean.setToEmail(data.getToEmail());
                inputBean.setApprovalstatus(data.getApprovalstatus());
                inputBean.setSearch(true);

            }

            if (inputBean.isSearch()) {

                int rows = inputBean.getRows();
                int page = inputBean.getPage();
                int to = (rows * page);
                int from = to - rows;
                long records = 0;
                String orderBy = "";

                if (!inputBean.getSidx().isEmpty() && inputBean.getSidx() != null) {
                    orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
                } else {
                    orderBy = " order by g.CREATETIME desc";
                }

                System.out.println(orderBy);

                TransactionExplorerDAO dao = new TransactionExplorerDAO();

                List<TxnDataBean> dataList = dao.getSearchListbySQL(inputBean, to, from, orderBy);
                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("From Date", inputBean.getFromdate())
                        + checkEmptyorNullString("To Date", inputBean.getTodate())
                        + checkEmptyorNullString("Transaction ID", inputBean.getTransactionID())
                        + checkEmptyorNullString("Wallet ID", inputBean.getWalletid())
                        + checkEmptyorNullString("From Card No", inputBean.getFromcardno())
                        + checkEmptyorNullString("To Card Number", inputBean.getTocardno())
                        + checkEmptyorNullString("From Mobile No", inputBean.getMobileNo())
                        + checkEmptyorNullString("To Mobile No", inputBean.getToMobileNo())
                        + checkEmptyorNullString("To Email", inputBean.getToEmail())
                        + checkEmptyorNullString("Merchant ID", inputBean.getMid())
                        + checkEmptyorNullString("Terminal ID", inputBean.getTid())
                        + checkEmptyorNullString("Transaction Type", inputBean.getTxnTypeCode())
                        + checkEmptyorNullString("Trace No", inputBean.getTraceNo())
                        + checkEmptyorNullString("Amount", inputBean.getAmount())
                        + checkEmptyorNullString("Status", inputBean.getStatus())
                        + checkEmptyorNullString("Approval Status", inputBean.getApprovalstatus())
                        + checkEmptyorNullString("RRN", inputBean.getRrn())
                        + checkEmptyorNullString("Response", inputBean.getResCode())
                        + checkEmptyorNullString("Invoice Number", inputBean.getInvoiceno())
                        + checkEmptyorNullString("Processing Code", inputBean.getProcessingcode())
                        + checkEmptyorNullString("Token ID", inputBean.getTokenid())
                        + "]";

                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.TXN_EXPLORER, SectionVarList.REPORT_EXPLORER, "Transaction explorer  search using " + searchParameters + " parameters ", null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);

                if (!dataList.isEmpty()) {

                    records = dataList.get(0).getFullCount();
                    inputBean.setRecords(records);
                    inputBean.setGridModel(dataList);
                    int total = (int) Math.ceil((double) records / (double) rows);
                    System.out.println("-------------------------- total =" + total + "**=" + records);
                    inputBean.setTotal(total);

//                    HttpSession session = ServletActionContext.getRequest().getSession(false);
                    session.setAttribute(SessionVarlist.TRANS_EXPOR_SEARCHBEAN, inputBean);

                } else {
                    inputBean.setRecords(0L);
                    inputBean.setTotal(0);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Transaction explorer " + MessageVarList.COMMON_ERROR_PROCESS);
        }
        return "search";
    }

    public String ReloadSearch() {
        System.out.println("called TransactionExplorerAction : search");
        try {
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            TxnExplorerInputBean data = (TxnExplorerInputBean) session.getAttribute(SessionVarlist.TRANS_EXPOR_SEARCHBEAN);

            if (inputBean.getLoad().equals("yes")) {

                int rows = inputBean.getRows();
                int page = inputBean.getPage();
                int to = (rows * page);
                int from = to - rows;
                long records = 0;
                String orderBy = "";
                if (!inputBean.getSidx().isEmpty()) {
                    orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
                } else {
                    orderBy = " order by createtime desc";
                }

                System.out.println(orderBy);
                TransactionExplorerDAO dao = new TransactionExplorerDAO();

                List<TxnDataBean> dataList = dao.getSearchListbySQL(inputBean, to, from, orderBy);
                HttpServletRequest request = ServletActionContext.getRequest();
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.SEARCH_TASK, PageVarList.TXN_EXPLORER, SectionVarList.REPORT_EXPLORER, "Transaction explorer search", null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);

                if (!dataList.isEmpty()) {

                    records = dataList.get(0).getFullCount();
                    inputBean.setRecords(records);
                    inputBean.setGridModel(dataList);
                    int total = (int) Math.ceil((double) records / (double) rows);
                    inputBean.setTotal(total);

//                    HttpSession session = ServletActionContext.getRequest().getSession(false);
                    session.setAttribute(SessionVarlist.TRANS_EXPOR_SEARCHBEAN, inputBean);

                } else {
                    inputBean.setRecords(0L);
                    inputBean.setTotal(0);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Transaction explorer " + MessageVarList.COMMON_ERROR_PROCESS);
        }
        return "search";
    }

    public String reportGenerate() {

        System.out.println("called TransactionExplorerAction : reportGeneration");
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

                HttpServletRequest request = ServletActionContext.getRequest();
//            connection = CommonDAO.getConnection();

                HttpSession session = ServletActionContext.getRequest().getSession(false);
                TxnExplorerInputBean searchBean = (TxnExplorerInputBean) session.getAttribute(SessionVarlist.TRANS_EXPOR_SEARCHBEAN);
                Systemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.TXN_EXPLORER, SectionVarList.REPORT_EXPLORER, "Transaction explorer pdf report generated", null);
                //get path
                ServletContext context = ServletActionContext.getServletContext();
                SystemAuditDAO daoaudit = new SystemAuditDAO();
                daoaudit.saveAudit(audit);

                TransactionExplorerDAO dao = new TransactionExplorerDAO();
                //*****************************************
                String imgPath = context.getRealPath("/resouces/images/ntb.png");

                if (searchBean.getTransactionID() != null && !searchBean.getTransactionID().isEmpty()) {
                    parameterMap.put("txnid", searchBean.getTransactionID().trim());
                } else {
                    parameterMap.put("txnid", "--");
                }

                if (searchBean.getCardNo() != null && !searchBean.getCardNo().isEmpty()) {
                    parameterMap.put("fromcard", searchBean.getCardNo().trim());
                } else {
                    parameterMap.put("fromcard", "--");
                }

                if (searchBean.getTocardno() != null && !searchBean.getTocardno().isEmpty()) {
                    parameterMap.put("tocardno", searchBean.getTocardno().trim());
                } else {
                    parameterMap.put("tocardno", "--");
                }

                if (searchBean.getDeviceID() != null && !searchBean.getDeviceID().isEmpty()) {
                    parameterMap.put("deid", searchBean.getDeviceID().trim());
                } else {
                    parameterMap.put("deid", "--");

                }
                if (searchBean.getTxnTypeCode() != null && !searchBean.getTxnTypeCode().isEmpty()) {
                    
                    parameterMap.put("txntypedes", dao.findDesByCode("", searchBean.getTxnTypeCode(), "", ""));
                    parameterMap.put("txntype", searchBean.getTxnTypeCode().trim());
                } else {
                    parameterMap.put("txntype", "--");
                    parameterMap.put("txntypedes", "--");
                }

                if (searchBean.getTodate() != null && !searchBean.getTodate().isEmpty()) {
//                parameterMap.put("tdate", Common.specialStringtoDate(searchBean.getTodate().trim()));
                    parameterMap.put("tdate", searchBean.getTodate().trim());
                } else {
                    parameterMap.put("tdate", "--");
                }

                if (searchBean.getFromdate() != null && !searchBean.getFromdate().isEmpty()) {
//                parameterMap.put("fdate", Common.specialStringtoDate(searchBean.getFromdate().trim()));
                    parameterMap.put("fdate", searchBean.getFromdate().trim());
                } else {
                    parameterMap.put("fdate", "--");
                }

                if (searchBean.getProcessingcode() != null && !searchBean.getProcessingcode().isEmpty()) {
                    parameterMap.put("procode", searchBean.getProcessingcode().trim());
                } else {
                    parameterMap.put("procode", "--");
                }

                if (searchBean.getAmount() != null && !searchBean.getAmount().isEmpty()) {

                    String amount = Common.toCurrencyFormatForSQL(searchBean.getAmount().trim());

                    parameterMap.put("amt", amount);
                    parameterMap.put("amtval", Common.toCurrencyFormat(searchBean.getAmount().trim()));
                  
                    System.err.println(searchBean.getAmount().trim());
                } else {
                    parameterMap.put("amt", "--");
                    parameterMap.put("amtval", "--");
                }

                if (searchBean.getListeneruid() != null && !searchBean.getListeneruid().isEmpty()) {
                    parameterMap.put("listuid", searchBean.getListeneruid().trim());
                } else {
                    parameterMap.put("listuid", "--");
                }
                if (searchBean.getInvoiceno() != null && !searchBean.getInvoiceno().isEmpty()) {
                    parameterMap.put("inno", searchBean.getInvoiceno().trim());
                } else {
                    parameterMap.put("inno", "--");
                }

                if (searchBean.getWalletid() != null && !searchBean.getWalletid().isEmpty()) {
                    parameterMap.put("walletid", searchBean.getWalletid().trim());
                } else {
                    parameterMap.put("walletid", "--");
                }

                if (searchBean.getNic() != null && !searchBean.getNic().isEmpty()) {
                    parameterMap.put("nic", searchBean.getNic().trim());

                    String s1 = inputBean.getWalletist().toString().replace("[", "");
                    String s2 = s1.replace("]", "");
                    String niclist = s2;
                    List<String> li = new ArrayList<String>();
                    li.add(niclist);
                    parameterMap.put("niclist", inputBean.getWalletist());
                    parameterMap.put("nicliststr", niclist);
                    System.err.println("@reportgeneration > " + li);

                } else {
                    parameterMap.put("nic", "--");
                    parameterMap.put("niclist", "--");
                    parameterMap.put("nicliststr", "--");
                }

                if (searchBean.getTid() != null && !searchBean.getTid().isEmpty()) {
                    parameterMap.put("tid", searchBean.getTid().trim());
                } else {
                    parameterMap.put("tid", "--");
                }

                if (searchBean.getMid() != null && !searchBean.getMid().isEmpty()) {
                    parameterMap.put("mid", searchBean.getMid().trim());
                } else {
                    parameterMap.put("mid", "--");
                }

                if (searchBean.getCurrency() != null && !searchBean.getCurrency().isEmpty()) {
                    parameterMap.put("cur", searchBean.getCurrency().trim());

                    parameterMap.put("curdes", dao.findDesByCode("", "", searchBean.getCurrency().trim(), ""));

                } else {
                    parameterMap.put("cur", "--");
                    parameterMap.put("curdes", "--");
                }

                if (searchBean.getRrn() != null && !searchBean.getRrn().isEmpty()) {
                    parameterMap.put("rrn", searchBean.getRrn().trim());
                } else {
                    parameterMap.put("rrn", "--");
                }
                if (searchBean.getMobileNo() != null && !searchBean.getMobileNo().isEmpty()) {
                    parameterMap.put("mobile", searchBean.getMobileNo().trim());
                } else {
                    parameterMap.put("mobile", "--");
                }

                if (searchBean.getResCode() != null && !searchBean.getResCode().isEmpty()) {
                    parameterMap.put("res", searchBean.getResCode().trim());

                    parameterMap.put("resdes", dao.findDesByCode("", "", "", searchBean.getResCode().trim()));

                } else {
                    parameterMap.put("res", "--");
                    parameterMap.put("resdes", "--");
                }

                if (searchBean.getTraceNo() != null && !searchBean.getTraceNo().isEmpty()) {
                    parameterMap.put("traceno", searchBean.getTraceNo().trim());
                } else {
                    parameterMap.put("traceno", "--");
                }

                if (searchBean.getTokenid() != null && !searchBean.getTokenid().isEmpty()) {
                    parameterMap.put("tokenid", searchBean.getTokenid().trim());
                } else {
                    parameterMap.put("tokenid", "--");
                }

                if (searchBean.getToEmail() != null && !searchBean.getToEmail().isEmpty()) {
                    parameterMap.put("toemail", searchBean.getToEmail().trim());
                } else {
                    parameterMap.put("toemail", "--");
                }
                if (searchBean.getToMobileNo() != null && !searchBean.getToMobileNo().isEmpty()) {
                    parameterMap.put("tomobile", searchBean.getToMobileNo().trim());
                } else {
                    parameterMap.put("tomobile", "--");
                }

                if (searchBean.getStatus() != null && !searchBean.getStatus().isEmpty()) {   //statussettle
                    // SystemAuditDAO dao = new SystemAuditDAO();
                    if (searchBean.getStatus().equals("TCMP")) {
//               parameterMap.put("statussettle", searchBean.getStatus().trim());
                        parameterMap.put("statusdes", dao.findDesByCode(searchBean.getStatus(), "", "", ""));
                        parameterMap.put("status", "TCMP' , 'TXNS");

                    } else {
                        parameterMap.put("statusdes", dao.findDesByCode(searchBean.getStatus(), "", "", ""));
                        parameterMap.put("status", searchBean.getStatus().trim());
//                parameterMap.put("statussettle", "--");
                    }
                } else {
                    parameterMap.put("status", "--");
                    parameterMap.put("statusdes", "--");
//                parameterMap.put("statussettle", "--");
                }

                if (searchBean.getApprovalstatus() != null && !searchBean.getApprovalstatus().isEmpty()) {
                    parameterMap.put("appstatusdes", dao.findDesByCode(searchBean.getApprovalstatus(), "", "", ""));
                    parameterMap.put("appstatus", searchBean.getApprovalstatus());

                } else {
                    parameterMap.put("appstatusdes", "--");
                    parameterMap.put("appstatus", "--");
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

                String reportLocation = context.getRealPath("WEB-INF/pages/reportexplorer/report/txn_exception_report.jasper");

                hSession = HibernateInit.sessionFactory.openSession();
                SessionImplementor sim = (SessionImplementor) hSession;

                jasperPrint = JasperFillManager.fillReport(reportLocation, parameterMap, sim.connection());

                if (virtualizer != null) {
                    virtualizer.setReadOnly(true);
                }

                outputFile = JasperExportManager.exportReportToPdf(jasperPrint);
                fileInputStream = new ByteArrayInputStream(outputFile);

                retMsg = "download";
            } else if (inputBean.getReporttype().trim().equalsIgnoreCase("exel")) {
                System.err.println("EXEL printing");
                TransactionExplorerDAO dao = new TransactionExplorerDAO();
                retMsg = "excelreport";
                ByteArrayOutputStream outputStream = null;
                try {

                    HttpSession session = ServletActionContext.getRequest().getSession(false);

                    TxnExplorerInputBean searchBean = (TxnExplorerInputBean) session.getAttribute(SessionVarlist.TRANS_EXPOR_SEARCHBEAN);
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
                    Systemaudit audit = Common.makeAudittrace(request, TaskVarList.GENERATE_TASK, PageVarList.TXN_EXPLORER, SectionVarList.REPORT_EXPLORER, "Transaction explorer excel report generated ", null);
                    CommonDAO.saveAudit(audit);

                } catch (Exception e) {
                    addActionError(MessageVarList.COMMON_ERROR_PROCESS + " exception detail excel report");
                    Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarList.COMMON_ERROR_PROCESS + " Transaction explorer");

            return "message";
        } finally {
            if (virtualizer != null) {
                virtualizer.cleanup();
            }

            if (hSession != null) {
                hSession.close();
            }
        }
        return retMsg;
    }

    // only to display error msg at exception
    private void loadPageData() {
        try {
            CommonDAO dao = new CommonDAO();
            inputBean.setTxnTypeList(dao.getTxnTypeCodeList());
//                inputBean.setDeviceCategoryList(dao.getDefultDeviceCategoryList());
            inputBean.setStatusList(dao.getDefultStatusList("TRXN"));
//            inputBean.setStatusList(dao.getDefultStatusList("TRAN"));

        } catch (Exception e) {
            addActionError("Transaction explorer " + MessageVarList.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionExplorerAction.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
