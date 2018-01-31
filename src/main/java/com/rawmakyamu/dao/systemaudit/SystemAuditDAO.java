/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.systemaudit;

import com.rawmakyamu.bean.systemaudit.AuditDataBean;
import com.rawmakyamu.bean.systemaudit.AuditSearchDTO;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.Common;
import com.rawmakyamu.util.common.ExcelCommon;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.common.PartialList;
import com.rawmakyamu.util.mapping.Page;
import com.rawmakyamu.util.mapping.Section;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.mapping.Userrole;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import sun.audio.AudioData;

/**
 *
 * @author thushanth
 */
public class SystemAuditDAO {

    private final int columnCount = 9;
    private final int headerRowCount = 11;

    private String TXN_COUNT_SQL = "select "
            + "count(g.SYSTEMAUDITID) " //0            
            + "from systemaudit g "
            + "where ";

    private String TXN_ORDER_BY_SQL = " order by g.LASTUPDATEDTIME DESC ";

    private String TXN_SQL = "select "
            + "g.SYSTEMAUDITID, " //0            
            + "ur.DESCRIPTION ud, " //1
            + "g.DESCRIPTION us, " //2            
            + "sc.DESCRIPTION aa, " //3
            + "pg.DESCRIPTION bb, " //4
            + "ts.DESCRIPTION cc, " //5
            + "g.IP ip, " //6
            + "g.LASTUPDATEDUSER, " //7
            + "g.LASTUPDATEDTIME, " //8
            + "g.CREATETIME " //9

            + "from systemaudit g "
            + "left outer join section sc on sc.SECTIONCODE = g.SECTIONCODE "
            + "left outer join page pg on pg.PAGECODE = g.PAGECODE "
            + "left outer join task ts on ts.TASKCODE = g.TASKCODE "
            + "left outer join userrole ur on ur.USERROLECODE = g.USERROLECODE "
            + "where ";

    public PartialList<AuditDataBean> getSearchList(AuditSearchDTO auditSearchDTO, int rows, int from, String sortIndex, String sortOrder) throws Exception {
        List<Systemaudit> searchList = null;
        List<AuditDataBean> dataBeanList = null;
        Session session = null;
        long fullCount = 0;
        if ("".equals(sortIndex.trim())) {
            sortIndex = null;
        }
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Systemaudit.class);
//            criteria.createAlias("user", "ur");

            if (sortIndex != null && sortOrder != null) {
                if (sortOrder.equals("asc")) {
                    criteria.addOrder(Order.asc(sortIndex));
                }
                if (sortOrder.equals("desc")) {
                    criteria.addOrder(Order.desc(sortIndex));
                }

            } else {
                criteria.addOrder(Order.desc("createtime"));
            }

            if (auditSearchDTO.getUser() != null && !auditSearchDTO.getUser().isEmpty()) {
                criteria.add(Restrictions.eq("lastupdateduser", auditSearchDTO.getUser()));
            }

            if (auditSearchDTO.getSection() != null && !auditSearchDTO.getSection().isEmpty()) {
                criteria.add(Restrictions.eq("sectioncode", auditSearchDTO.getSection()));
            }

            if (auditSearchDTO.getSdblpage() != null && !auditSearchDTO.getSdblpage().isEmpty()) {
                criteria.add(Restrictions.eq("pagecode", auditSearchDTO.getSdblpage()));
            }

            if (auditSearchDTO.getTask() != null && !auditSearchDTO.getTask().isEmpty()) {
                criteria.add(Restrictions.eq("taskcode", auditSearchDTO.getTask()));
            }
            
            if (auditSearchDTO.getDescription()!= null && !auditSearchDTO.getDescription().isEmpty()) {
                criteria.add(Restrictions.ilike("description", auditSearchDTO.getDescription(),MatchMode.ANYWHERE));
            }


            if (auditSearchDTO.getFdate() != null && !auditSearchDTO.getFdate().isEmpty()) {
                criteria.add(Restrictions.ge("createtime", Common.specialStringtoDate(auditSearchDTO.getFdate())));
            }

            if (auditSearchDTO.getTdate() != null && !auditSearchDTO.getTdate().isEmpty()) {
                criteria.add(Restrictions.le("createtime", new Date((Common.specialStringtoDate(auditSearchDTO.getTdate().trim())).getTime() + TimeUnit.DAYS.toMillis(1))));
            }

            fullCount = criteria.list().size();

            criteria.setFirstResult(from);
            criteria.setMaxResults(rows);

            searchList = criteria.list();
            dataBeanList = new ArrayList<AuditDataBean>();

            for (Systemaudit m : searchList) {
                AuditDataBean tempBean = new AuditDataBean(m);
                tempBean.setTaskDes(findTaskDescription(m.getTaskcode()));
                tempBean.setPageDes(findPageDescription(m.getPagecode()));
//                tempBean.setSectionDes(new CommonDAO().getSectionByprefix(m.getSection()));
                tempBean.setSectionDes(findSectionDescription(m.getSectioncode()));
                tempBean.setLastUpdatedDate(m.getLastupdatedtime().toString().substring(0, 19));
                tempBean.setCreatetime(m.getCreatetime().toString().substring(0, 19));
                dataBeanList.add(tempBean);
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

        PartialList<AuditDataBean> list = new PartialList<AuditDataBean>();

        list.setList(dataBeanList);
        list.setFullCount(fullCount);

        return list;
    }

    public AuditDataBean findAuditById(String auditId) throws Exception {
        AuditDataBean auditDatabean;
        Section sec = null;
        Page pg = null;
        Task tk = null;
        Userrole ur = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            Systemaudit auditBean = (Systemaudit) session.get(Systemaudit.class, new Long(auditId));

            String sql = "from Section as u where u.sectioncode=:sectioncode";
            Query query = session.createQuery(sql).setString("sectioncode", auditBean.getSectioncode());
            sec = (Section) query.list().get(0);

            String sql2 = "from Page as u where u.pagecode=:pagecode";
            Query query2 = session.createQuery(sql2).setString("pagecode", auditBean.getPagecode());
            pg = (Page) query2.list().get(0);

            String sql3 = "from Task as u where u.taskcode=:taskcode";
            Query query3 = session.createQuery(sql3).setString("taskcode", auditBean.getTaskcode());
            tk = (Task) query3.list().get(0);

            String sql4 = "from Userrole as u where u.userrolecode=:userrole";
            Query query4 = session.createQuery(sql4).setString("userrole", auditBean.getUserrolecode());
            ur = (Userrole) query4.list().get(0);

            auditDatabean = new AuditDataBean(auditBean);
            auditDatabean.setSection(sec.getDescription());
            auditDatabean.setPage(pg.getDescription());
            auditDatabean.setTask(tk.getDescription());
            auditDatabean.setUserrole(ur.getDescription());
            auditDatabean.setLastUpdatedDate(auditBean.getLastupdatedtime().toString().substring(0,19));
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
        return auditDatabean;
    }

    public AuditDataBean findAuditById(String page, String task, String section) throws Exception {
        AuditDataBean auditDatabean = null;
        Section sec = null;
        Page pg = null;
        Task tk = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            auditDatabean = new AuditDataBean();
            if (!"".equals(section)) {
                String sql = "from Section as u where u.sectioncode=:sectioncode";
                Query query = session.createQuery(sql).setString("sectioncode", section);
                sec = (Section) query.list().get(0);
                auditDatabean.setSection(sec.getDescription());
            }
            if (!"".equals(page)) {
                String sql2 = "from Page as u where u.pagecode=:pagecode";
                Query query2 = session.createQuery(sql2).setString("pagecode", page);
                pg = (Page) query2.list().get(0);
                auditDatabean.setSdblpage(pg.getDescription());
            }
            if (!"".equals(task)) {
                String sql3 = "from Task as u where u.taskcode=:taskcode";
                Query query3 = session.createQuery(sql3).setString("taskcode", task);
                tk = (Task) query3.list().get(0);
                auditDatabean.setTask(tk.getDescription());
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
        return auditDatabean;
    }

    public String findTaskDescription(String task) throws Exception {

        String taskDes = "";
        Session session = null;
        Task tk = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            if (task != null) {

                String sql = "from Task as u where u.taskcode=:taskcode";
                Query query = session.createQuery(sql).setString("taskcode", task);
                tk = (Task) query.list().get(0);
                taskDes = tk.getDescription();
            } else {
                taskDes = "--";
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
        return taskDes;
    }

    public String findPageDescription(String page) throws Exception {

        String pageDes = "";
        Session session = null;
        Page pg = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            if (page != null) {

                String sql = "from Page as u where u.pagecode=:pagecode";
                Query query = session.createQuery(sql).setString("pagecode", page);
                pg = (Page) query.list().get(0);
                pageDes = pg.getDescription();
            } else {
                pageDes = "--";
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
        return pageDes;
    }

    public String saveAudit(Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            txn = session.beginTransaction();
            audit.setCreatetime(sysDate);
            audit.setLastupdatedtime(sysDate);
            audit.setLastupdateduser(audit.getLastupdateduser());

            session.save(audit);

            txn.commit();

        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return message;
    }

    public String findSectionDescription(String section) throws Exception {

        String sectionDes = "";
        Session session = null;
        Section sc = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            if (section != null) {

                String sql = "from Section as u where u.sectioncode=:sectioncode";
                Query query = session.createQuery(sql).setString("sectioncode", section);
                sc = (Section) query.list().get(0);
                sectionDes = sc.getDescription();

            } else {
                sectionDes = "--";
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
        return sectionDes;
    }

    public Object generateExcelReport(AuditSearchDTO inputBean) throws Exception {
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
            }
//                System.err.println("Count is "+count);
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
                            AuditDataBean dataBean = new AuditDataBean();

                            try {
                                dataBean.setId(objArr[0].toString());

                                if (objArr[0].equals("")) {
                                    dataBean.setId("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setId("--");
                            }

                            try {
                                dataBean.setUserrole(objArr[1].toString());

                                if (objArr[1].equals("")) {
                                    dataBean.setUserrole("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setUserrole("--");
                            }

                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());
                                dataBean.setDescription(objArr[2].toString());
                                if (objArr[2].equals("")) {

                                    dataBean.setUserrole("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setUserrole("--");
                            }

                            //null has to be checked for every foreign keys
                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());
                                dataBean.setSection(objArr[3].toString());
                                if (objArr[3].equals("")) {

                                    dataBean.setSection("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setSection("--");
                            }

                            try {

                                dataBean.setSdblpage(objArr[4].toString());
                                if (objArr[4].equals("")) {

                                    dataBean.setSdblpage("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setSdblpage("--");
                            }

                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());
                                dataBean.setTask(objArr[5].toString());
                                if (objArr[5].equals("")) {
                                    dataBean.setTask("--");

                                }
                            } catch (NullPointerException npe) {
                                dataBean.setTask("--");
                            }
                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());

                                dataBean.setIp(objArr[6].toString());
                                if (objArr[6].equals("")) {
                                    dataBean.setIp("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setIp("--");
                            }
                            try {
                                dataBean.setUser(objArr[7].toString());
                                if (objArr[7].equals("")) {
                                    dataBean.setUser("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setUser("--");
                            }

                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());
                                dataBean.setLastUpdatedDate(objArr[8].toString().substring(0, 19));
                                if (objArr[8].equals("")) {
                                    dataBean.setLastUpdatedDate("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setLastUpdatedDate("--");
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
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnObject;
    }

    private String makeWhereClauseForExcel(AuditSearchDTO inputBean) throws ParseException {
        String where = "1=1";

        if (inputBean.getSection() != null && !inputBean.getSection().isEmpty()) {
            where += " and g.SECTIONCODE LIKE '%" + inputBean.getSection() + "%'";
        }
        if (inputBean.getSdblpage() != null && !inputBean.getSdblpage().isEmpty()) {
            where += " and g.PAGECODE LIKE '%" + inputBean.getSdblpage() + "%'";
        }
        if (inputBean.getTask() != null && !inputBean.getTask().isEmpty()) {
            where += " and g.TASKCODE LIKE '%" + inputBean.getTask() + "%'";
        }
        if (inputBean.getUser() != null && !inputBean.getUser().isEmpty()) {
            where += " and g.LASTUPDATEDUSER LIKE '%" + inputBean.getUser() + "%'";
        }
        if (inputBean.getDescription()!= null && !inputBean.getDescription().isEmpty()) {
            where += " and g.DESCRIPTION LIKE '%" + inputBean.getDescription() + "%'";
        }

        String date1 = inputBean.getTdate();  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date1));
        c.add(Calendar.DATE, 1);  // number of days to add
        sdf.applyPattern("dd-MMM-yy");
        date1 = sdf.format(c.getTime());  // dt is now the new date

        String datef = inputBean.getFdate();  // Start date
        SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cf = Calendar.getInstance();
        cf.setTime(sdff.parse(datef));
        cf.add(Calendar.DATE, 0);
        sdff.applyPattern("dd-MMM-yy");
        datef = sdff.format(cf.getTime());

        if (inputBean.getFdate() != null && !inputBean.getFdate().isEmpty()) {
            where += " and g.LASTUPDATEDTIME >='" + datef + "'";
        }
        if (date1 != null && !date1.isEmpty()) {
            where += " and g.LASTUPDATEDTIME <'" + date1 + "'";
        }

//        System.err.println(where);
        return where;
    }

    private SXSSFWorkbook createExcelTopSection(AuditSearchDTO inputBean) throws Exception {

        SXSSFWorkbook workbook = new SXSSFWorkbook(-1);
        Sheet sheet = workbook.createSheet("LoginHistory_Report");

        CellStyle fontBoldedUnderlinedCell = ExcelCommon.getFontBoldedUnderlinedCell(workbook);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("FriMi - Digital Life Style Solution");
        cell.setCellStyle(fontBoldedUnderlinedCell);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("Audit Summary Report");
        cell.setCellStyle(fontBoldedUnderlinedCell);

        row = sheet.createRow(4);
        cell = row.createCell(0);
        cell.setCellValue("From Date");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getFdate()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(5);
        cell = row.createCell(0);
        cell.setCellValue("To Date");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getTdate()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(6);
        cell = row.createCell(0);

        cell.setCellValue("Section");
        cell = row.createCell(1);
        if (inputBean.getSection() != null && !inputBean.getSection().isEmpty()) {
            cell.setCellValue(Common.replaceEmptyorNullStringToALL(this.findAuditById("", "", inputBean.getSection()).getSection()));
        } else {
            cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getSection()));
        }
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(7);
        cell = row.createCell(0);
        cell.setCellValue("Page");
        cell = row.createCell(1);
        if (inputBean.getSdblpage()!= null && !inputBean.getSdblpage().isEmpty()) {
            cell.setCellValue(Common.replaceEmptyorNullStringToALL(this.findAuditById(inputBean.getSdblpage(), "","" ).getSdblpage()));
        } else {
            cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getSdblpage()));
        }
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(8);
        cell = row.createCell(0);
        cell.setCellValue("Task");
        cell = row.createCell(1);
        if (inputBean.getTask()!= null && !inputBean.getTask().isEmpty()) {
            cell.setCellValue(Common.replaceEmptyorNullStringToALL(this.findAuditById("", inputBean.getTask(),"" ).getTask()));
        } else {
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getTask()));
        }
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(9);
        cell = row.createCell(0);
        cell.setCellValue("Last Updated User");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getUser()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));
        
        row = sheet.createRow(9);
        cell = row.createCell(0);
        cell.setCellValue("Description");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getDescription()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

//        
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
        cell.setCellValue("ID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(2);
        cell.setCellValue("Username");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(3);
        cell.setCellValue("Description");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(4);
        cell.setCellValue("Section");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(5);
        cell.setCellValue("Page");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(6);
        cell.setCellValue("Task");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(7);
        cell.setCellValue("IP");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(8);
        cell.setCellValue("User Role");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(9);
        cell.setCellValue("Last Updated Time");
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
//                sheet.autoSizeColumn(i);
//            }

            file = new File(directory);
            if (!file.exists()) {
                System.out.println("Directory created or not : " + file.mkdirs());
            }

            if (fileCount > 0) {
                file = new File(directory + File.separator + "System Audit Report_" + fileCount + ".xlsx");
            } else {
                file = new File(directory + File.separator + "System Audit Report.xlsx");
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

    private int createExcelTableBodySection(SXSSFWorkbook workbook, AuditDataBean dataBean, int currrow, int rownumber) throws Exception {
        Sheet sheet = workbook.getSheetAt(0);
        CellStyle rowColumnCell = ExcelCommon.getRowColumnCell(workbook);
        Row row = sheet.createRow(currrow++);

        Cell cell = row.createCell(0);
        cell.setCellValue(rownumber);
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(1);
        cell.setCellValue(dataBean.getId());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(2);
        cell.setCellValue(dataBean.getUser());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(3);
        cell.setCellValue(dataBean.getDescription());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(4);
        cell.setCellValue(dataBean.getSection());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(5);
        cell.setCellValue(dataBean.getSdblpage());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(6);
        cell.setCellValue(dataBean.getTask());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(7);
        cell.setCellValue(dataBean.getIp());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(8);
        cell.setCellValue(dataBean.getUserrole());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(9);
        cell.setCellValue(dataBean.getLastUpdatedDate());
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
}
