/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.systemconfig;

import com.rawmakyamu.bean.controlpanel.systemconfig.LoginHistoryBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.LoginHistoryInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.Common;
import com.rawmakyamu.util.common.ExcelCommon;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.Loginhistory;
import com.rawmakyamu.util.mapping.Logintype;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Task;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author jayana_i
 */
public class LoginHistoryDAO {

    private final int columnCount = 9;
    private final int headerRowCount = 9;

    private String TXN_COUNT_SQL = "select "
            + "count(g.HISTORYID) " //0            
            + "from ms_loginhistory g "
            + "left outer join status m on m.STATUSCODE = g.STATUS "
            + "left outer join task t2 on t2.taskcode = g.TASK "
            + "left outer join ms_logintypes lt on lt.id = g.TYPE "
            + "where ";

    private String TXN_ORDER_BY_SQL = " order by g.LOGGEDTIME DESC ";

    private String TXN_SQL = "select "
            + "g.HISTORYID, " //0
            + "g.WALLETID, " //g.STAFFSTATUS //1
            + "s2.DESCRIPTION bb, " //g.CATEGORY //2 // s2.DESCRIPTION bb
            + "g.MOBILENUMBER, " //3
            + "g.NIC, " //4
            + "g.LONGITUDE, " //5
            + "g.LATITUDE, " //6
            + "g.LOGGEDTIME, " //7
            + "g.STATUS, " //g.STATUS //8
            + "t2.DESCRIPTION ttask, " //9
            + "lt.DESCRIPTION ltype " //10
            + "from ms_loginhistory g "
            + "left outer join status s2 on s2.STATUSCODE = g.STATUS "
            + "left outer join task t2 on t2.taskcode = g.TASK "
            + "left outer join ms_logintypes lt on lt.id = g.TYPE "
            + "where ";

    public List<LoginHistoryBean> getSearchList(LoginHistoryInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<LoginHistoryBean> dataList = new ArrayList<LoginHistoryBean>();
        Session session = null;
        try {
            if (orderBy.equals(" ") || orderBy == null) {
                orderBy = "asc";
            }
            long count = 0;

            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(id) from Loginhistory as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
//                String sqlSearch = "from Loginhistory u where " + where + orderBy ;
                String sqlSearch = "from Loginhistory u where " + where + " Order by u.id desc ";
                System.err.println(sqlSearch);
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    LoginHistoryBean atmlocations = new LoginHistoryBean();
                    Loginhistory loginhistory = (Loginhistory) it.next();

                    try {

                        atmlocations.setHistoryid(loginhistory.getHistoryid());
                    } catch (NullPointerException e) {
                        atmlocations.setHistoryid("--");
                    }
                    try {
                        atmlocations.setWalletid(loginhistory.getWalletid());
                    } catch (NullPointerException e) {
                        atmlocations.setWalletid("--");
                    }
                    try {
                        atmlocations.setPushid(loginhistory.getPushid());
                    } catch (NullPointerException e) {
                        atmlocations.setPushid("--");
                    }

                    try {
                        atmlocations.setStatus(loginhistory.getStatus().getDescription());
                    } catch (NullPointerException e) {
                        atmlocations.setStatus("--");
                    }

                    try {
                        atmlocations.setMobilenumber(loginhistory.getMobilenumber());
                    } catch (Exception e) {
                        atmlocations.setMobilenumber("--");
                    }

                    try {
                        if (!loginhistory.getXCoordinate().isEmpty()) {
                            atmlocations.setXcoordinate(loginhistory.getXCoordinate());
                        } else {
                            atmlocations.setXcoordinate("--");
                        }

                    } catch (Exception e) {
                        atmlocations.setXcoordinate("--");
                    }

                    try {

                        if (!loginhistory.getXCoordinate().isEmpty()) {
                            atmlocations.setYcoordinate(loginhistory.getYCoordinate());
                        } else {
                            atmlocations.setXcoordinate("--");
                        }
                    } catch (Exception e) {
                        atmlocations.setYcoordinate("--");
                    }
                    try {
                        atmlocations.setSessionkey(loginhistory.getSessionkey());
                    } catch (Exception e) {
                        atmlocations.setSessionkey("--");
                    }
                    try {
                        atmlocations.setLogedtime(loginhistory.getLoggedtime().toString().substring(0, 19));
                    } catch (Exception e) {
                        atmlocations.setLogedtime("--");
                    }
                    try {
                        if (!loginhistory.getNic().isEmpty()) {
                            atmlocations.setNic(loginhistory.getNic());
                        } else {
                            atmlocations.setNic("--");
                        }

                    } catch (Exception e) {
                        atmlocations.setNic("--");
                    }
                    try {
                        atmlocations.setTask(loginhistory.getTask().getDescription());
                    } catch (Exception e) {
                        atmlocations.setTask("--");
                    }
                    try {
                        atmlocations.setLogintype(loginhistory.getLogintype().getDescription());
                    } catch (Exception e) {
                        atmlocations.setLogintype("--");
                    }
                    atmlocations.setFullCount(count);
                    dataList.add(atmlocations);
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return dataList;
    }

    public Object generateExcelReport(LoginHistoryInputBean inputBean) throws Exception {
        Session session = null;
        Object returnObject = null;
        try {

            String directory = ServletActionContext.getServletContext().getInitParameter("tmpreportpath");
            File file = new File(directory);
            if (file.exists()) {
                FileUtils.deleteDirectory(file);
            }

            session = HibernateInit.sessionFactory.openSession();

            int count = 0;
            String where1 = this.makeWhereClauseForExcel(inputBean);
            String sqlCount = this.TXN_COUNT_SQL + where1;
            System.out.println(sqlCount);
            Query queryCount = session.createSQLQuery(sqlCount);
//            queryCount = setDatesToQuery(queryCount, inputBean, session);

//            queryCount = setDatesToQuery(queryCount, inputBean, session);
            if (queryCount.uniqueResult() != null) {
                count = ((Number) queryCount.uniqueResult()).intValue();
                System.err.println("count " + count);
            }

            if (count > 0) {
                long maxRow = Long.parseLong(ServletActionContext.getServletContext().getInitParameter("numberofrowsperexcel"));
                SXSSFWorkbook workbook = this.createExcelTopSection(inputBean);
                Sheet sheet = workbook.getSheetAt(0);

                int currRow = headerRowCount;
                int fileCount = 0;

                currRow = this.createExcelTableHeaderSection(workbook, currRow);

                String sql = this.TXN_SQL + where1 + this.TXN_ORDER_BY_SQL;
                System.out.println(sql);

                int selectRow = Integer.parseInt(ServletActionContext.getServletContext().getInitParameter("numberofselectrows"));
                int numberOfTimes = count / selectRow;
                if ((count % selectRow) > 0) {
                    numberOfTimes += 1;
                }
                int from = 0;
                int listrownumber = 1;

                for (int i = 0; i < numberOfTimes; i++) {

                    Query query = session.createSQLQuery(sql);
                    query.setFirstResult(from);
                    query.setMaxResults(selectRow);

                    List<Object[]> objectArrList = (List<Object[]>) query.list();
                    if (objectArrList.size() > 0) {

                        for (Object[] objArr : objectArrList) {
                            LoginHistoryBean dataBean = new LoginHistoryBean();

                            try {

                                if (objArr[0] == null) {
                                    dataBean.setHistoryid("--");
                                    System.err.println(dataBean.getHistoryid());
                                } else {
                                    dataBean.setHistoryid(objArr[0].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setHistoryid("--");
                            }

                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());

                                if (objArr[1] == null) {
                                    dataBean.setWalletid("--");
                                } else {
                                    dataBean.setWalletid(objArr[1].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setWalletid("--");
                            }

                            //null has to be checked for every foreign keys
                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());

                                if (objArr[2] == null) {
                                    dataBean.setStatus("--");
                                } else {
                                    dataBean.setStatus(objArr[2].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setStatus("--");
                            }

//                            try {
//                                
//                                if(objArr[3]==null){
//                                   dataBean.setCustomerid("--");
//                                }else{
//                                dataBean.setCustomerid(objArr[3].toString());
//                                }
//                            } catch (NullPointerException npe) {
//                                dataBean.setCustomerid("--");
//                            }
                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());
                                dataBean.setMobilenumber(objArr[3].toString());
                                if (objArr[3] == null) {
                                    dataBean.setMobilenumber("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setMobilenumber("--");
                            }

                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());
                                dataBean.setNic(objArr[4].toString());
                                if (objArr[4] == null) {
                                    dataBean.setNic("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setNic("--");
                            }
                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());

                                if (objArr[5] == null) {
                                    dataBean.setXcoordinate("--");
                                } else {
                                    dataBean.setXcoordinate(objArr[5].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setXcoordinate("--");
                            }
                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());

                                if (objArr[6] == null) {
                                    dataBean.setYcoordinate("--");
                                } else {
                                    dataBean.setYcoordinate(objArr[6].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setYcoordinate("--");
                            }
                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());

                                if (objArr[7] == null) {
                                    dataBean.setLogedtime("--");
                                } else {
                                    dataBean.setLogedtime(objArr[7].toString().substring(0, 19));
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setLogedtime("--");
                            }

                            try {
                                if (objArr[9] == null) {
                                    dataBean.setTask("--");
                                } else {
                                    dataBean.setTask(objArr[9].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setTask("--");
                            }
                            try {
                                if (objArr[10] == null) {
                                    dataBean.setLogintype("--");
                                } else {
                                    dataBean.setLogintype(objArr[10].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setLogintype("--");
                            }
                            dataBean.setFullCount(count);

                            if (currRow + 1 > maxRow) {
                                fileCount++;
                                this.writeTemporaryFile(workbook, fileCount, directory);
                                workbook = this.createExcelTopSection(inputBean);
                                sheet = workbook.getSheetAt(0);
                                currRow = headerRowCount;
                                this.createExcelTableHeaderSection(workbook, currRow);
                            }
                            currRow = this.createExcelTableBodySection(workbook, dataBean, currRow, listrownumber);
                            listrownumber++;
                            if (currRow % 100 == 0) {
                                ((SXSSFSheet) sheet).flushRows(100); // retain 100 last rows and flush all others

                                // ((SXSSFSheet)sh).flushRows() is a shortcut for ((SXSSFSheet)sh).flushRows(0),
                                // this method flushes all rows
                            }
                        }
                    }
                    from = from + selectRow;
                }

                Date createdTime = CommonDAO.getSystemDate(session);
                this.createExcelBotomSection(workbook, currRow, count, createdTime);

                if (fileCount > 0) {
                    fileCount++;
                    this.writeTemporaryFile(workbook, fileCount, directory);
                    ByteArrayOutputStream outputStream = Common.zipFiles(file.listFiles());
                    returnObject = outputStream;
                    workbook.dispose();
                } else {
                    for (int i = 0; i < columnCount; i++) {
                        //to auto size all column in the sheet
                        sheet.autoSizeColumn(i);
                    }

                    returnObject = workbook;
                }
            }
        } catch (Exception e) {
//            throw e;
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnObject;
    }

    private String makeWhereClauseForExcel(LoginHistoryInputBean inputBean) throws ParseException {
        String where = "1=1";

        if (inputBean.getHistoryid() != null && !inputBean.getHistoryid().isEmpty()) {
            where += " and g.HISTORYID LIKE '%" + inputBean.getHistoryid() + "%'";
        }
        if (inputBean.getWalletid() != null && !inputBean.getWalletid().isEmpty()) {
            where += " and g.WALLETID LIKE '%" + inputBean.getWalletid() + "%'";
        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().isEmpty()) {
            where += " and g.STATUS LIKE '%" + inputBean.getStatus() + "%'";
        }
        if (inputBean.getLogintype() != null && !inputBean.getLogintype().isEmpty()) {
            where += " and g.TYPE LIKE '%" + inputBean.getLogintype() + "%'";
        }
//        if (inputBean.getCustomerid()!= null && !inputBean.getCustomerid().isEmpty()) {
//            where += " and g.CUSTOMERID LIKE '%" + inputBean.getCustomerid() + "%'";
//        }
        if (inputBean.getMobilenumber() != null && !inputBean.getMobilenumber().isEmpty()) {
            where += " and g.MOBILENUMBER LIKE '%" + inputBean.getMobilenumber() + "%'";
        }
        if (inputBean.getNic() != null && !inputBean.getNic().isEmpty()) {
            where += " and g.NIC LIKE '%" + inputBean.getNic() + "%'";
        }
        if (inputBean.getXcoordinate() != null && !inputBean.getXcoordinate().isEmpty()) {
            where += " and g.LONGITUDE ='" + inputBean.getXcoordinate() + "'";
        }
        if (inputBean.getYcoordinate() != null && !inputBean.getYcoordinate().isEmpty()) {
            where += " and g.LATITUDE ='" + inputBean.getYcoordinate() + "'";
        }
        if (inputBean.getFromdate() != null && !inputBean.getFromdate().isEmpty()) {
            where += " and g.LOGGEDTIME >=TO_DATE('" + inputBean.getFromdate() + "','yyyy-mm-dd')";
        }
        if (inputBean.getTask() != null && !inputBean.getTask().isEmpty()) {
            where += " and g.TASK LIKE '%" + inputBean.getTask() + "%'";
        }

        String date = inputBean.getTodate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(inputBean.getTodate()));
        c.add(Calendar.DATE, 1);  // number of days to add
        date = sdf.format(c.getTime());
        if (inputBean.getTodate() != null && !inputBean.getTodate().isEmpty()) {
            where += " and g.LOGGEDTIME <TO_DATE('" + date + "','yyyy-mm-dd')";
        }

        return where;
    }

    private SXSSFWorkbook createExcelTopSection(LoginHistoryInputBean inputBean) throws Exception {

        SXSSFWorkbook workbook = new SXSSFWorkbook(-1);
        Sheet sheet = workbook.createSheet("MobileLoginHistory_Report");

        CellStyle fontBoldedUnderlinedCell = ExcelCommon.getFontBoldedUnderlinedCell(workbook);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("FriMi - Digital Life Style Solution");
        cell.setCellStyle(fontBoldedUnderlinedCell);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("Mobile Login History Report");
        cell.setCellStyle(fontBoldedUnderlinedCell);

        row = sheet.createRow(4);
        cell = row.createCell(0);
        cell.setCellValue("From Date");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getFromdate()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("To Date");
        cell = row.createCell(4);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getTodate()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(5);
        cell = row.createCell(0);
        cell.setCellValue("Wallet ID");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getWalletid()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

//        row = sheet.createRow(6);
        cell = row.createCell(3);
        cell.setCellValue("Status");
        cell = row.createCell(4);

        CommonDAO dao = new CommonDAO();
        String status = "";
        try {
            status = dao.getStatusByprefix(inputBean.getStatus().toString());
        } catch (NullPointerException ex) {
            status = "-ALL-";
        }
        cell.setCellValue(status);
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("Mobile No");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getMobilenumber()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

//        row = sheet.createRow(7);
        cell = row.createCell(3);
        cell.setCellValue("NIC");
        cell = row.createCell(4);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getNic()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(7);
        cell = row.createCell(0);
        cell.setCellValue("Task");
        cell = row.createCell(1);

        String task = "";
        try {
            task = this.getTaskDescriptionByCode(inputBean.getTask());
        } catch (NullPointerException ex) {
            task = "-ALL-";
        }
        cell.setCellValue(task);
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("Type");
        cell = row.createCell(4);

        String type = "";
        if (inputBean.getLogintype() == null || inputBean.getLogintype().isEmpty()) {
            type = "-ALL-";
        } else {
            type = this.getTypeDescriptionByCode(inputBean.getLogintype().trim());
        }
        cell.setCellValue(type);
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));
        return workbook;
    }

    private int createExcelTableHeaderSection(SXSSFWorkbook workbook, int currrow) throws Exception {
        CellStyle columnHeaderCell = ExcelCommon.getColumnHeadeCell(workbook);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.createRow(currrow++);

        Cell cell = row.createCell(0);
        cell.setCellValue("No");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(1);
        cell.setCellValue("History ID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(2);
        cell.setCellValue("Wallet ID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(3);
        cell.setCellValue("Status");
        cell.setCellStyle(columnHeaderCell);

//        cell = row.createCell(4);
//        cell.setCellValue("Customer ID");
//        cell.setCellStyle(columnHeaderCell);
        cell = row.createCell(4);
        cell.setCellValue("Mobile Number");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(5);
        cell.setCellValue("NIC");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(6);
        cell.setCellValue("Task");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(7);
        cell.setCellValue("Type");
        cell.setCellStyle(columnHeaderCell);

//        cell = row.createCell(8);
//        cell.setCellValue("Longitude");
//        cell.setCellStyle(columnHeaderCell);
//
//        cell = row.createCell(9);
//        cell.setCellValue("Latitude");
//        cell.setCellStyle(columnHeaderCell);
        cell = row.createCell(8);
        cell.setCellValue("Logged Time");
        cell.setCellStyle(columnHeaderCell);

        return currrow;
    }

    private void writeTemporaryFile(SXSSFWorkbook workbook, int fileCount, String directory) throws Exception {
        File file;
        FileOutputStream outputStream = null;
        try {
            Sheet sheet = workbook.getSheetAt(0);
//            for (int i = 0; i < columnCount; i++) {
//                //to auto size all column in the sheet
////                sheet.autoSizeColumn(i);
//            }

            file = new File(directory);
            if (!file.exists()) {
                System.out.println("Directory created or not : " + file.mkdirs());
            }

            if (fileCount > 0) {
                file = new File(directory + File.separator + "Mobile Login History Report_" + fileCount + ".xlsx");
            } else {
                file = new File(directory + File.separator + "Mobile Login History Report.xlsx");
            }
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
        } catch (IOException e) {
            throw e;
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    private int createExcelTableBodySection(SXSSFWorkbook workbook, LoginHistoryBean dataBean, int currrow, int rownumber) throws Exception {
        Sheet sheet = workbook.getSheetAt(0);
        CellStyle rowColumnCell = ExcelCommon.getRowColumnCell(workbook);
        Row row = sheet.createRow(currrow++);

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);

        Cell cell = row.createCell(0);
        cell.setCellValue(rownumber);
        cell.setCellStyle(rowColumnCell);
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(dataBean.getHistoryid());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(2);
        cell.setCellValue(dataBean.getWalletid());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(3);
        cell.setCellValue(dataBean.getStatus());
        cell.setCellStyle(rowColumnCell);

//        cell = row.createCell(4);
//        cell.setCellValue(dataBean.getCustomerid());
//        cell.setCellStyle(rowColumnCell);
        cell = row.createCell(4);
        cell.setCellValue(dataBean.getMobilenumber());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(5);
        cell.setCellValue(dataBean.getNic());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(6);
        cell.setCellValue(dataBean.getTask());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(7);
        cell.setCellValue(dataBean.getLogintype());
        cell.setCellStyle(rowColumnCell);

//        cell = row.createCell(8);
//        cell.setCellValue(dataBean.getXcoordinate());
//        cell.setCellStyle(rowColumnCell);
//
//        cell = row.createCell(9);
//        cell.setCellValue(dataBean.getYcoordinate());
//        cell.setCellStyle(rowColumnCell);
        cell = row.createCell(8);
        cell.setCellValue(dataBean.getLogedtime().substring(0, 19));
        cell.setCellStyle(rowColumnCell);

        return currrow;
    }

    private void createExcelBotomSection(SXSSFWorkbook workbook, int currrow, long count, Date date) throws Exception {

        CellStyle fontBoldedCell = ExcelCommon.getFontBoldedCell(workbook);
        Sheet sheet = workbook.getSheetAt(0);

        currrow++;
        Row row = sheet.createRow(currrow++);
        Cell cell = row.createCell(0);
        cell.setCellValue("Summary");
        cell.setCellStyle(fontBoldedCell);

        row = sheet.createRow(currrow++);
        cell = row.createCell(0);
        cell.setCellValue("Total Record Count");
        cell = row.createCell(1);
        cell.setCellValue(count);
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(currrow++);
        cell = row.createCell(0);
        cell.setCellValue("Report Created Time");
        cell = row.createCell(1);
        cell.setCellValue(date.toString().substring(0, 19));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));
    }

    public String getTaskDescriptionByCode(String taskCode) throws Exception {

        Task st = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            st = (Task) session.get(Task.class, taskCode);
        } catch (Exception he) {
            throw he;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return st.getDescription();
    }

//    public String getTypeDescriptionByCode(String Code) throws Exception {
//        
//        Logintype st = null;
//        Session session = null;
//        try {
//            if(Code!=null){
//            session = HibernateInit.sessionFactory.openSession();
//            st = (Logintype) session.get(Logintype.class,new BigDecimal(Code));
//            }
//            
//        } catch (Exception he) {
//            throw he;
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        return st.getDescription();
//    }
    public String getStatusDescriptionByCode(String statuscode) throws Exception {

        String Rescode = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            if (statuscode.equals("--")) {
                Rescode = "--";
            } else {

                String sql = "from Status as t where t.statuscode =:statusCode";
                Query query = session.createQuery(sql).setString("statusCode", statuscode);
                Rescode = ((Status) query.list().get(0)).getDescription();
            }
        } catch (Exception e) {
            System.err.println("error rescode" + Rescode);
            Rescode = "--";
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;

            }
        }
        return Rescode;
    }

    public String getTypeDescriptionByCode(String statuscode) throws Exception {

        String Rescode = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            if (statuscode.equals("--")) {
                Rescode = "--";
            } else {

                String sql = "from Logintype as t where t.id =:idCode";
                Query query = session.createQuery(sql).setString("idCode", statuscode);
                Rescode = ((Logintype) query.list().get(0)).getDescription();
            }
        } catch (Exception e) {
            System.err.println("error rescode" + Rescode);
            Rescode = "--";
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;

            }
        }
        return Rescode;
    }

    private String makeWhereClause(LoginHistoryInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getWalletid() != null && !inputBean.getWalletid().isEmpty()) {
            where += " and lower(u.walletid) like lower('%" + inputBean.getWalletid().trim() + "%')";
        }
        if (inputBean.getMobilenumber() != null && !inputBean.getMobilenumber().isEmpty()) {
            where += " and lower(u.mobilenumber) like lower('%" + inputBean.getMobilenumber() + "%')";
        }
        if (inputBean.getNic() != null && !inputBean.getNic().isEmpty()) {
            where += " and lower(u.nic) like lower('%" + inputBean.getNic() + "%')";
        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().isEmpty()) {
            where += " and u.status.statuscode = '" + inputBean.getStatus() + "'";
        }
        if (inputBean.getLogintype() != null && !inputBean.getLogintype().isEmpty()) {
            where += " and u.logintype.id = '" + inputBean.getLogintype() + "'";
        }
        if (inputBean.getTask() != null && !inputBean.getTask().isEmpty()) {
            where += " and u.task.taskcode = '" + inputBean.getTask() + "'";
        }
        try {
            String date1 = inputBean.getTodate();                                   // Start date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(date1));
            c.add(Calendar.DATE, 1);                                                // number of days to add
            sdf.applyPattern("dd-MMM-yy");
            date1 = sdf.format(c.getTime());                                        // dt is now the new date

            String datef = inputBean.getFromdate();                                 // Start date
            SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cf = Calendar.getInstance();
            cf.setTime(sdff.parse(datef));
            cf.add(Calendar.DATE, 0);
            sdff.applyPattern("dd-MMM-yy");
            datef = sdff.format(cf.getTime());

            if (inputBean.getTodate() != null && !inputBean.getFromdate().isEmpty()) {
                where += " and u.loggedtime >='" + datef + "'";
            }
            if (date1 != null && !date1.isEmpty()) {
                where += " and u.loggedtime <'" + date1 + "'";
            }
        } catch (Exception ee) {

        }
        return where;
    }
}
