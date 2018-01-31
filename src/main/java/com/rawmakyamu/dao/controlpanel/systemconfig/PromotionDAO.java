/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.systemconfig;

import com.rawmakyamu.bean.controlpanel.systemconfig.PromotionBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.PromotionInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.Common;
import com.rawmakyamu.util.common.ExcelCommon;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.MsPromotions;
import com.rawmakyamu.util.mapping.MsUserParam;
import com.rawmakyamu.util.mapping.Promotionuserlevel;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.hibernate.Transaction;

/**
 *
 * @author : dilanka_w
 * @Created on : Jul 9, 2016, 6:07:56 PM
 */
public class PromotionDAO {

    private final int columnCount = 13;
    private final int headerRowCount = 14;

    private String TXN_COUNT_SQL = "select "
            + "count(PROMOTION_ID) " //0            
            + "from MS_PROMOTIONS g "
            + "where ";

    //private String TXN_ORDER_BY_SQL = " order by TIMESTAMP DESC ";
    private String TXN_SQL = "select "
            + "g.PROMOTION_ID, " //0
            + "g.title, " //1
            + "g.description, " //2
            + "s3.description as statusdes, " //3
            + "g.START_DATE,  " //4
            + "g.EXP_DATE, " //5
            + "g.SORT_ORDER, " //6
            + "pl.description as plevel, " //7
            + "g.CLICK_COUNT, " //8          
            + "g.SHARE_COUNT, " //9
            + "g.LIKE_COUNT, " //10
            + "g.UPDATE_SEQUENCE, " //11
            + "g.SHARE_ATTEMPT_COUNT, " //12
            + "g.CREATE_TIME " //13

            + "from MS_PROMOTIONS g "
            + "left outer join status s3 on s3.statuscode = g.status "
            + "left outer join MS_PROMOTION_LEVEL pl on pl.CODE = g.USER_LEVEL "
            + "where ";

    public List<PromotionBean> getSearchList(PromotionInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<PromotionBean> dataList = new ArrayList<PromotionBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = "order by u.createTime desc";
            }
            long count = 0;
            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(promotionId) from MsPromotions as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                String sqlSearch = "from MsPromotions u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    PromotionBean msPromotionBean = new PromotionBean();
                    MsPromotions promotion = (MsPromotions) it.next();

                    try {
                        msPromotionBean.setPromotionId(promotion.getPromotionId().toString());
                    } catch (NullPointerException e) {
                        msPromotionBean.setPromotionId("--");
                    }

                    try {
                        msPromotionBean.setDescription(promotion.getDescription());
                    } catch (NullPointerException e) {
                        msPromotionBean.setDescription("--");
                    }

                    try {
                        msPromotionBean.setStatus(promotion.getStatus().getDescription());
                    } catch (NullPointerException e) {
                        msPromotionBean.setStatus("--");
                    }

                    try {
                        msPromotionBean.setTitle(promotion.getTitle());
                    } catch (NullPointerException e) {
                        msPromotionBean.setTitle("--");
                    }

                    try {
                        msPromotionBean.setSortOrder(promotion.getSortOrder().toString());
                    } catch (NullPointerException e) {
                        msPromotionBean.setSortOrder("--");
                    }

                    try {
                        msPromotionBean.setClickCount(promotion.getClickCount().toString());
                    } catch (NullPointerException e) {
                        msPromotionBean.setClickCount("--");
                    }

                    try {
                        msPromotionBean.setLikeCount(promotion.getLikeCount().toString());
                    } catch (NullPointerException e) {
                        msPromotionBean.setLikeCount("--");
                    }

                    try {
                        msPromotionBean.setShareCount(promotion.getShareCount().toString());
                    } catch (NullPointerException e) {
                        msPromotionBean.setShareCount("--");
                    }

                    try {
                        msPromotionBean.setStartDate(Common.getFormattedDate2(promotion.getStartDate()));
                    } catch (NullPointerException e) {
                        msPromotionBean.setStartDate("--");
                    }

                    try {
                        msPromotionBean.setExpDate(Common.getFormattedDate2(promotion.getExpDate()));
                    } catch (NullPointerException e) {
                        msPromotionBean.setExpDate("--");
                    }

                    try {
                        if (promotion.getCreateTime() != null && !promotion.getCreateTime().toString().isEmpty()) {
                            msPromotionBean.setCreateTime(promotion.getCreateTime().toString().substring(0, 19));
                        } else {
                            msPromotionBean.setCreateTime("--");
                        }

                    } catch (NullPointerException e) {
                        msPromotionBean.setCreateTime("--");
                    }

                    try {
                        msPromotionBean.setUpdateSequence(promotion.getUpdateSequence().toString());
                    } catch (NullPointerException e) {
                        msPromotionBean.setUpdateSequence("--");
                    }

                    try {
                        msPromotionBean.setUserLevel(promotion.getUserLevel().getDescription());
                    } catch (NullPointerException e) {
                        msPromotionBean.setUserLevel("--");
                    }
                    try {
                        msPromotionBean.setShareAttemptCount(promotion.getShareAttemptCount().toString());
                    } catch (NullPointerException e) {
                        msPromotionBean.setShareAttemptCount("--");
                    }

                    msPromotionBean.setFullCount(count);
                    dataList.add(msPromotionBean);

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

    private String getUserLevelDes(String key) {

        HashMap<String, String> userLevel = new HashMap<String, String>();
        userLevel.put("0", "All");
        userLevel.put("1", "Level 1");
        userLevel.put("2", "Level 2");
        userLevel.put("3", "Level 3");

        return userLevel.get(key);
    }

    public String insertPromotion(PromotionInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        FileInputStream fileInputStream = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if (((MsPromotions) session.get(MsPromotions.class, new BigDecimal(inputBean.getPromotionId().trim())) == null)) {

                txn = session.beginTransaction();

                MsPromotions promotion = new MsPromotions();
                Status status = (Status) session.get(Status.class, inputBean.getStatus());
                Promotionuserlevel userlevel = (Promotionuserlevel) session.get(Promotionuserlevel.class, inputBean.getUserLevel());

                promotion.setStatus(status);
                promotion.setUserLevel(userlevel);

                promotion.setPromotionId(new BigDecimal(inputBean.getPromotionId()));
                promotion.setDescription(inputBean.getDescription());
                promotion.setTitle(inputBean.getTitle());
                promotion.setSortOrder(Integer.parseInt(inputBean.getSortOrder()));
                promotion.setStartDate(Common.convertStringtoDate3(inputBean.getStartDate()));
                promotion.setExpDate(Common.convertStringtoDate3(inputBean.getExpDate()));
                promotion.setTabImgName(inputBean.getTabImgFileName());
                promotion.setMobImgName(inputBean.getMobileImgFileName());
                promotion.setShareAttemptCount(Integer.parseInt("0"));

                File tabimgFile = inputBean.getTabImg();
                byte[] bTabFile = new byte[(int) tabimgFile.length()];
                try {
                    fileInputStream = new FileInputStream(tabimgFile);
                    fileInputStream.read(bTabFile);
                    fileInputStream.close();

                    promotion.setTabimg(bTabFile);
                } catch (Exception ex) {

                } finally {
                    try {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        } else {
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                }

                File mobimgFile = inputBean.getMobileImg();
                byte[] bMobFile = new byte[(int) mobimgFile.length()];
                try {
                    fileInputStream = new FileInputStream(mobimgFile);
                    fileInputStream.read(bMobFile);
                    fileInputStream.close();

                    promotion.setMobimg(bMobFile);
                } catch (Exception ex) {

                } finally {
                    try {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        } else {
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                }

                promotion.setCreateTime(sysDate);
                promotion.setUpdatedTime(sysDate);
                promotion.setImgId(sysDate);

                String newValue = promotion.getTabImgName()
                        + "|" + promotion.getMobImgName()
                        + "|" + promotion.getPromotionId()
                        + "|" + promotion.getDescription()
                        + "|" + promotion.getTitle()
                        + "|" + promotion.getStatus().getDescription()
                        + "|" + promotion.getSortOrder()
                        + "|" + promotion.getUserLevel().getDescription()
                        + "|" + Common.convertStringtoStr2(inputBean.getStartDate())
                        + "|" + Common.convertStringtoStr2(inputBean.getExpDate());

                audit.setNewvalue(newValue);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(promotion);

                txn.commit();
            } else {
                message = MessageVarList.COMMON_ALREADY_EXISTS;
            }
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

    public MsPromotions findPromotionById(String promotionId) throws Exception {
        MsPromotions promotions = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from MsPromotions as u where u.promotionId=:promotionId";
            Query query = session.createQuery(sql).setString("promotionId", promotionId);
            promotions = (MsPromotions) query.list().get(0);

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
        return promotions;

    }

    public String updatePromotion(PromotionInputBean inputBean, Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        FileInputStream fileInputStream = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            MsPromotions u = (MsPromotions) session.get(MsPromotions.class, new BigDecimal(inputBean.getPromotionId().trim()));

            if (u != null) {

                String oldValue = u.getTabImgName()
                        + "|" + u.getMobImgName()
                        + "|" + u.getPromotionId()
                        + "|" + u.getDescription()
                        + "|" + u.getTitle()
                        + "|" + u.getStatus().getDescription()
                        + "|" + u.getSortOrder()
                        + "|" + u.getUserLevel().getDescription()
                        + "|" + u.getStartDate().toString().substring(0, 19)
                        + "|" + u.getExpDate().toString().substring(0, 19);

                u.setPromotionId(new BigDecimal(inputBean.getPromotionId()));
                u.setDescription(inputBean.getDescription().trim());
                u.setTitle(inputBean.getTitle());
                u.setStartDate(Common.convertStringtoDate3(inputBean.getStartDate()));
                u.setExpDate(Common.convertStringtoDate3(inputBean.getExpDate()));
                u.setSortOrder(Integer.parseInt(inputBean.getSortOrder()));
                Status status = (Status) session.get(Status.class, inputBean.getStatus());
                u.setStatus(status);

                Promotionuserlevel userlevel = (Promotionuserlevel) session.get(Promotionuserlevel.class, inputBean.getUserLevel());
                u.setUserLevel(userlevel);

                try {
                    u.setTabImgName(inputBean.getTabImgFileName().toString());
                } catch (NullPointerException e) {

                }

                try {
                    u.setMobImgName(inputBean.getMobileImgFileName().toString());
                } catch (NullPointerException e) {

                }

                try {
                    if (inputBean.getTabImg().length() != 0) {
                        File logoTabFile = inputBean.getTabImg();
                        byte[] bTabFile = new byte[(int) logoTabFile.length()];
                        try {
                            fileInputStream = new FileInputStream(logoTabFile);
                            fileInputStream.read(bTabFile);
                            fileInputStream.close();
                            u.setTabimg(bTabFile);
                            u.setImgId(sysDate);
                        } catch (Exception ex) {

                        }
                    }
                } catch (NullPointerException ex) {

                } finally {
                    try {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        } else {
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                }

                try {
                    if (inputBean.getMobileImg().length() != 0) {
                        File logoMobFile = inputBean.getMobileImg();
                        byte[] bMobFile = new byte[(int) logoMobFile.length()];
                        try {
                            fileInputStream = new FileInputStream(logoMobFile);
                            fileInputStream.read(bMobFile);
                            fileInputStream.close();
                            u.setMobimg(bMobFile);
                            u.setImgId(sysDate);
                        } catch (Exception ex) {

                        }
                    }
                } catch (NullPointerException ex) {
                } finally {
                    try {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        } else {
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                }

                String newValue = u.getTabImgName()
                        + "|" + u.getMobImgName()
                        + "|" + u.getPromotionId()
                        + "|" + u.getDescription()
                        + "|" + u.getTitle()
                        + "|" + u.getStatus().getDescription()
                        + "|" + u.getSortOrder()
                        + "|" + u.getUserLevel().getDescription()
                        + "|" + Common.convertStringtoStr2(inputBean.getStartDate())
                        + "|" + Common.convertStringtoStr2(inputBean.getExpDate());

                audit.setOldvalue(oldValue);
                audit.setNewvalue(newValue);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.update(u);

                txn.commit();
            } else {
                message = MessageVarList.COMMON_NOT_EXISTS;
            }
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

    public String deletePromotion(PromotionInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            MsPromotions u = (MsPromotions) session.get(MsPromotions.class, new BigDecimal(inputBean.getPromotionId().trim()));

            if (u != null) {

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.delete(u);
                txn.commit();

            } else {
                message = MessageVarList.COMMON_NOT_EXISTS;
            }
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

    public Object generateExcelReport(PromotionInputBean inputBean) throws Exception {
        Session session = null;
        Object returnObject = null;
        try {

//            String orderBy = "";
//            if (!inputBean.getSidx().isEmpty()) {
////                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
//            }
            String directory = ServletActionContext.getServletContext().getInitParameter("tmpreportpath");
            File file = new File(directory);
            if (file.exists()) {
                FileUtils.deleteDirectory(file);
            }

            session = HibernateInit.sessionFactory.openSession();
            int count = 0;

            String where = this.makeWhereClauseForExcel(inputBean);
            String sqlCount = this.TXN_COUNT_SQL + where;
            Query queryCount = session.createSQLQuery(sqlCount);
            //queryCount = setDatesToQuery(queryCount, inputBean, session);
            if (queryCount.uniqueResult() != null) {
                count = ((Number) queryCount.uniqueResult()).intValue();
            }

            if (count > 0) {
                long maxRow = Long.parseLong(ServletActionContext.getServletContext().getInitParameter("numberofrowsperexcel"));
                SXSSFWorkbook workbook = this.createExcelTopSection(inputBean);
                Sheet sheet = workbook.getSheetAt(0);

                int currRow = headerRowCount;
                int fileCount = 0;

                currRow = this.createExcelTableHeaderSection(workbook, currRow);

//                String sql = this.TXN_SQL + where + orderBy;
                String sql = this.TXN_SQL + where + " order by g.CREATE_TIME desc";
                System.err.println(">>>>>>>>>>>>>>" + sql);
                int selectRow = Integer.parseInt(ServletActionContext.getServletContext().getInitParameter("numberofselectrows"));
                int numberOfTimes = count / selectRow;
                if ((count % selectRow) > 0) {
                    numberOfTimes += 1;
                }
                int from = 0;
                int listrownumber = 1;

                for (int i = 0; i < numberOfTimes; i++) {

                    Query query = session.createSQLQuery(sql);
                    //query = setDatesToQuery(query, inputBean, session);
                    query.setFirstResult(from);
                    query.setMaxResults(selectRow);

                    List<Object[]> objectArrList = (List<Object[]>) query.list();
                    if (objectArrList.size() > 0) {

                        for (Object[] objArr : objectArrList) {
                            PromotionBean dataBean = new PromotionBean();

                            try {
                                dataBean.setPromotionId(objArr[0].toString());
                            } catch (NullPointerException npe) {
                                dataBean.setPromotionId("--");
                            }
                            try {
                                dataBean.setTitle(objArr[1].toString());
                            } catch (NullPointerException npe) {
                                dataBean.setTitle("--");
                            }

                            try {
                                dataBean.setDescription(objArr[2].toString());
                            } catch (NullPointerException npe) {
                                dataBean.setDescription("--");
                            }

                            try {
                                dataBean.setStatus(objArr[3].toString());
                            } catch (NullPointerException npe) {
                                dataBean.setStatus("--");
                            }
                            try {
                                dataBean.setStartDate(objArr[4].toString());
                            } catch (NullPointerException npe) {
                                dataBean.setStartDate("--");
                            }

                            try {
                                dataBean.setExpDate(objArr[5].toString());
                            } catch (NullPointerException npe) {
                                dataBean.setExpDate("--");
                            }

                            try {
                                dataBean.setSortOrder(objArr[6].toString());
                            } catch (NullPointerException npe) {
                                dataBean.setSortOrder("--");
                            }
                            try {
                                dataBean.setUserLevel(objArr[7].toString());
                            } catch (NullPointerException npe) {
                                dataBean.setUserLevel("--");
                            }

                            try {
                                dataBean.setClickCount(objArr[8].toString());
                            } catch (NullPointerException npe) {
                                dataBean.setClickCount("--");
                            }
                            try {
                                dataBean.setShareCount(objArr[9].toString());
                            } catch (NullPointerException npe) {
                                dataBean.setShareCount("--");
                            }

                            try {
                                dataBean.setLikeCount(objArr[10].toString());
                            } catch (NullPointerException npe) {
                                dataBean.setLikeCount("--");
                            }

                            try {
                                dataBean.setUpdateSequence(objArr[11].toString());
                            } catch (NullPointerException npe) {
                                dataBean.setUpdateSequence("--");
                            }
                            try {
                                dataBean.setShareAttemptCount(objArr[12].toString());
                            } catch (NullPointerException npe) {
                                dataBean.setShareAttemptCount("--");
                            }

                            try {
                                dataBean.setCreateTime(objArr[13].toString());
                            } catch (NullPointerException npe) {
                                dataBean.setCreateTime("--");
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

    private SXSSFWorkbook createExcelTopSection(PromotionInputBean inputBean) throws Exception {

        SXSSFWorkbook workbook = new SXSSFWorkbook(-1);
        Sheet sheet = workbook.createSheet("Campaign_Report");
        CommonDAO cdao = new CommonDAO();
        CellStyle fontBoldedUnderlinedCell = ExcelCommon.getFontBoldedUnderlinedCell(workbook);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("FriMi - Digital Life Style Solution");
        cell.setCellStyle(fontBoldedUnderlinedCell);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("Campaign Report");
        cell.setCellStyle(fontBoldedUnderlinedCell);

        row = sheet.createRow(4);
        cell = row.createCell(0);
        cell.setCellValue("Campaign ID");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getPromotionIdSearch()));

        row = sheet.createRow(5);
        cell = row.createCell(0);
        cell.setCellValue("Description");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getDescriptionSearch()));

        row = sheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("Title");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getTitleSearch()));

        row = sheet.createRow(7);
        cell = row.createCell(0);
        cell.setCellValue("Click Count");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getClickCountSearch()));

        row = sheet.createRow(8);
        cell = row.createCell(0);
        cell.setCellValue("Share Count");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getShareCountSearch()));

        row = sheet.createRow(9);
        cell = row.createCell(0);
        cell.setCellValue("Like Count");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getLikeCountSearch()));

        row = sheet.createRow(10);
        cell = row.createCell(0);
        cell.setCellValue("Status");
        cell = row.createCell(1);
        CommonDAO dao = new CommonDAO();
        String status = "";
        try {
            status = dao.getStatusByprefix(inputBean.getStatusSearch().toString());
        } catch (NullPointerException ex) {
            status = "-ALL-";
        }
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(status));

        row = sheet.createRow(11);
        cell = row.createCell(0);
        cell.setCellValue("Sort Order");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getSortOrderSearch()));

        row = sheet.createRow(12);
        cell = row.createCell(0);
        cell.setCellValue("User Level");
        cell = row.createCell(1);
        String userlevel = "";
        try {
            userlevel = dao.getUserlevelByprefix(inputBean.getUserLevelSearch().toString());
        } catch (NullPointerException ex) {
            userlevel = "-ALL-";
        }
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(userlevel));

//        if (inputBean.getStatus() != null && !inputBean.getStatus().isEmpty()) {
//            cell.setCellValue(new CommonDAO().getStatusByprefix(inputBean.getStatusSearch()));
//        } else {
//            cell.setCellValue(Common.replaceEmptyorNullStringToNA(inputBean.getStatusSearch()));
//        }
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
        cell.setCellValue("Campaign ID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(2);
        cell.setCellValue("Title");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(3);
        cell.setCellValue("Description");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(4);
        cell.setCellValue("Status");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(5);
        cell.setCellValue("Start Date");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(6);
        cell.setCellValue("Expiry Date");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(7);
        cell.setCellValue("Sort Order");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(8);
        cell.setCellValue("User Level");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(9);
        cell.setCellValue("Click Count");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(10);
        cell.setCellValue("Share Count");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(11);
        cell.setCellValue("Like Count");
        cell.setCellStyle(columnHeaderCell);

//        cell = row.createCell(12);
//        cell.setCellValue("Update Sequence");
//        cell.setCellStyle(columnHeaderCell);
        cell = row.createCell(12);
        cell.setCellValue("Share Attempt Count");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(13);
        cell.setCellValue("Created Time");
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
                file = new File(directory + File.separator + "Promotion_Report_" + fileCount + ".xlsx");
            } else {
                file = new File(directory + File.separator + "Promotion Report.xlsx");
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

    private int createExcelTableBodySection(SXSSFWorkbook workbook, PromotionBean dataBean, int currrow, int rownumber) throws Exception {
        Sheet sheet = workbook.getSheetAt(0);

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);

        CellStyle rowColumnCell = ExcelCommon.getRowColumnCell(workbook);
        Row row = sheet.createRow(currrow++);

        Cell cell = row.createCell(0);
        cell.setCellValue(rownumber);
        cell.setCellStyle(rowColumnCell);
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(dataBean.getPromotionId());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(2);
        cell.setCellValue(dataBean.getTitle());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(3);
        cell.setCellValue(dataBean.getDescription());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(4);
        cell.setCellValue(dataBean.getStatus());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(5);
        cell.setCellValue(dataBean.getStartDate().substring(0, 19));
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(6);
        cell.setCellValue(dataBean.getExpDate().substring(0, 19));
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(7);
        cell.setCellValue(dataBean.getSortOrder());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(8);
        cell.setCellValue(dataBean.getUserLevel());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(9);
        cell.setCellValue(dataBean.getClickCount());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(10);
        cell.setCellValue(dataBean.getShareCount());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(11);
        cell.setCellValue(dataBean.getLikeCount());
        cell.setCellStyle(rowColumnCell);

//        cell = row.createCell(12);
//        cell.setCellValue(dataBean.getUpdateSequence());
//        cell.setCellStyle(rowColumnCell);
        cell = row.createCell(12);
        cell.setCellValue(dataBean.getShareAttemptCount());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(13);
        cell.setCellValue(dataBean.getCreateTime().substring(0, 19));
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

    /**
     * get promotion table maximum id
     *
     * @return promotion maximum id + 1 (if max id=0 return 1)
     * @throws Exception
     */
    public int getPromotionMaxID() throws Exception {
        Session session = null;
        int maxid = 0;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sqlMaxId = "Select MAX(PROMOTION_ID) From MS_PROMOTIONS";
            Query queryMaxId = session.createSQLQuery(sqlMaxId);
            if (queryMaxId.uniqueResult() != null) {
                maxid = Integer.parseInt(queryMaxId.uniqueResult().toString());
            }

            if (maxid != 0) {
                maxid = maxid + 1;
            } else {
                maxid = 1;
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
        return maxid;
    }

    public String getImageURL() throws Exception {
        String imageURL = "";
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from MsUserParam as u where u.paramcode =:paramcode";
            Query query = session.createQuery(sql).setString("paramcode", CommonVarList.IMAGE_URL);
            imageURL = ((MsUserParam) query.list().get(0)).getParamValue();

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
        return imageURL;
    }

    private String makeWhereClause(PromotionInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getPromotionIdSearch() != null && !inputBean.getPromotionIdSearch().isEmpty()) {
            where += " and u.promotionId like '%" + inputBean.getPromotionIdSearch() + "%'";
        }
        if (inputBean.getDescriptionSearch() != null && !inputBean.getDescriptionSearch().isEmpty()) {
            where += " and lower(u.description) like lower('%" + inputBean.getDescriptionSearch() + "%')";
        }
        if (inputBean.getTitleSearch() != null && !inputBean.getTitleSearch().isEmpty()) {
            where += " and lower(u.title) like lower('%" + inputBean.getTitleSearch() + "%')";
        }
        if (inputBean.getClickCountSearch() != null && !inputBean.getClickCountSearch().isEmpty()) {
            where += " and u.clickCount like '%" + inputBean.getClickCountSearch() + "%'";
        }
        if (inputBean.getShareCountSearch() != null && !inputBean.getShareCountSearch().isEmpty()) {
            where += " and u.shareCount like '%" + inputBean.getShareCountSearch() + "%'";
        }
        if (inputBean.getLikeCountSearch() != null && !inputBean.getLikeCountSearch().isEmpty()) {
            where += " and u.likeCount like '%" + inputBean.getLikeCountSearch() + "%'";
        }
        if (inputBean.getStatusSearch() != null && !inputBean.getStatusSearch().isEmpty()) {
            where += " and u.status.statuscode = '" + inputBean.getStatusSearch() + "'";
        }
        if (inputBean.getSortOrderSearch() != null && !inputBean.getSortOrderSearch().isEmpty()) {
            where += " and u.sortOrder = '" + inputBean.getSortOrderSearch() + "'";
        }
        if (inputBean.getUserLevelSearch() != null && !inputBean.getUserLevelSearch().isEmpty()) {
            where += " and u.userLevel = '" + inputBean.getUserLevelSearch() + "'";
        }
        return where;
    }

    private String makeWhereClauseForExcel(PromotionInputBean inputBean) throws ParseException, Exception {
        String where = "1=1";

        if (inputBean.getPromotionIdSearch() != null && !inputBean.getPromotionIdSearch().isEmpty()) {
            where += " and g.PROMOTION_ID like '%" + inputBean.getPromotionIdSearch() + "%'";
        }
        if (inputBean.getTitleSearch() != null && !inputBean.getTitleSearch().isEmpty()) {
            where += " and lower(g.title) like lower('%" + inputBean.getTitleSearch() + "%')";
        }
        if (inputBean.getDescriptionSearch() != null && !inputBean.getDescriptionSearch().isEmpty()) {
            where += " and lower(g.description) like lower('%" + inputBean.getDescriptionSearch() + "%')";
        }
        if (inputBean.getStatusSearch() != null && !inputBean.getStatusSearch().isEmpty()) {
            where += " and g.status = '" + inputBean.getStatusSearch() + "'";
        }
        if (inputBean.getSortOrderSearch() != null && !inputBean.getSortOrderSearch().isEmpty()) {
            where += " and g.SORT_ORDER like '%" + inputBean.getSortOrderSearch() + "%'";
        }
        if (inputBean.getUserLevelSearch() != null && !inputBean.getUserLevelSearch().isEmpty()) {
            where += " and g.USER_LEVEL like '%" + inputBean.getUserLevelSearch() + "%'";
        }
        if (inputBean.getClickCountSearch() != null && !inputBean.getClickCountSearch().isEmpty()) {
            where += " and g.CLICK_COUNT like '%" + inputBean.getClickCountSearch() + "%'";
        }
        if (inputBean.getShareCountSearch() != null && !inputBean.getShareCountSearch().isEmpty()) {
            where += " and g.SHARE_COUNT like '%" + inputBean.getShareCountSearch() + "%'";
        }
        if (inputBean.getLikeCountSearch() != null && !inputBean.getLikeCountSearch().isEmpty()) {
            where += " and g.LIKE_COUNT  like '%" + inputBean.getLikeCountSearch() + "%'";
        }

        return where;
    }
}
