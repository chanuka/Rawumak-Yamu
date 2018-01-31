/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.customermanagement;

import com.rawmakyamu.bean.controlpanel.custmanagement.BlockNotificationDataBean;
import com.rawmakyamu.bean.controlpanel.custmanagement.BlockNotificationInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.MsCustomerWalletPushBlock;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author prathibha_s
 */
public class BlockNotificationDAO {

    public List<BlockNotificationDataBean> getSearchList(BlockNotificationInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<BlockNotificationDataBean> dataList = new ArrayList<BlockNotificationDataBean>();
        Session session = null;
        try {

            if (orderBy.equals("") || orderBy == null) {
                orderBy = "order by u.createtime desc";
            }

            CommonDAO dao = new CommonDAO();

            long count = 0;
            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(walletId) from MsCustomerWalletPushBlock as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                String sqlSearch = "from MsCustomerWalletPushBlock u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    BlockNotificationDataBean bcknoti = new BlockNotificationDataBean();
                    MsCustomerWalletPushBlock notimap = (MsCustomerWalletPushBlock) it.next();

                    try {
                        bcknoti.setWalletId(notimap.getWalletId());
                    } catch (NullPointerException npe) {
                        bcknoti.setWalletId("--");
                    }

                    try {
                        bcknoti.setStatus(dao.getStatusByCode(notimap.getStatus()).getDescription());
                    } catch (NullPointerException npe) {
                        bcknoti.setStatus("--");
                    }

                    try {
                        bcknoti.setFromdate(notimap.getFromdate().toString());
                    } catch (NullPointerException npe) {
                        bcknoti.setFromdate("--");
                    }

                    try {
                        bcknoti.setTodate(notimap.getTodate().toString());
                    } catch (NullPointerException npe) {
                        bcknoti.setTodate("--");
                    }

                    try {
                        if (notimap.getCreatetime().toString() != null && !notimap.getCreatetime().toString().isEmpty()) {
                            bcknoti.setCreatetime(notimap.getCreatetime().toString().substring(0, 19));
                        } else {
                            bcknoti.setCreatetime("--");
                        }
                    } catch (NullPointerException npe) {
                        bcknoti.setCreatetime("--");
                    }

                    bcknoti.setFullCount(count);

                    dataList.add(bcknoti);
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

    private String makeWhereClause(BlockNotificationInputBean inputBean) throws ParseException {
        String where = "1=1";
        DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");

        if (inputBean.getWalletIdSearch() != null && !inputBean.getWalletIdSearch().isEmpty()) {
            where += " and lower(u.walletId) like lower('%" + inputBean.getWalletIdSearch() + "%')";
        }

//        if (inputBean.getFromdateSearch() != null && !inputBean.getFromdateSearch().isEmpty()) {
//            String inputFromDate = inputBean.getFromdateSearch();
//            Date dateFromDate = parser.parse(inputFromDate);
//            String outputFromDate = formatter.format(dateFromDate);
//            where += " and u.fromdate like '" + outputFromDate.toUpperCase() + "'";
//        }
//
//        if (inputBean.getTodateSearch() != null && !inputBean.getTodateSearch().isEmpty()) {
//            String inputToDate = inputBean.getTodateSearch();
//            Date dateToDate = parser.parse(inputToDate);
//            String outputTodate = formatter.format(dateToDate);
//            where += " and u.todate like '" + outputTodate.toUpperCase() + "'";
//        }
        if (inputBean.getFromdateSearch() != null && !inputBean.getFromdateSearch().isEmpty()) {

            String inputFromDate = inputBean.getFromdateSearch();
            Date dateFromDate = parser.parse(inputFromDate);
            String outputFromDate = formatter.format(dateFromDate);

            if (inputBean.getTodateSearch() != null && !inputBean.getTodateSearch().isEmpty()) {

                String date = inputBean.getTodateSearch();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(inputBean.getTodateSearch()));
                c.add(Calendar.DATE, 1);  // number of days to add
                date = sdf.format(c.getTime());

                String inputToDate = date;
                Date dateToDate = parser.parse(inputToDate);
                String outputTodate = formatter.format(dateToDate);
                where += " and u.fromdate >= '" + outputFromDate.toUpperCase() + "' and u.todate < '" + outputTodate.toUpperCase() + "'";
            } else {
                where += " and u.fromdate like '" + outputFromDate.toUpperCase() + "'";
            }
        } else if (inputBean.getTodateSearch() != null && !inputBean.getTodateSearch().isEmpty()) {

            String inputToDate = inputBean.getTodateSearch();
            Date dateToDate = parser.parse(inputToDate);
            String outputTodate = formatter.format(dateToDate);
            where += " and u.todate like '" + outputTodate.toUpperCase() + "'";
        }

//        if (inputBean.getStatusSearch() != null && !inputBean.getStatusSearch().isEmpty()) {
//            where += " and u.status = '" + inputBean.getStatusSearch() + "'";
//        }
        return where;
    }

    public MsCustomerWalletPushBlock findBNById(String walletId) throws Exception {
        MsCustomerWalletPushBlock block = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from MsCustomerWalletPushBlock as u where u.walletId=:walletId";
            Query query = session.createQuery(sql).setString("walletId", walletId);
            block = (MsCustomerWalletPushBlock) query.list().get(0);

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
        return block;
    }

    public String insertBN(BlockNotificationInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        String outputFromDate = "";
        String outputToDate = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            CommonDAO dao = new CommonDAO();
            DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");

            if ((MsCustomerWalletPushBlock) session.get(MsCustomerWalletPushBlock.class, inputBean.getWalletId().trim()) == null) {
                txn = session.beginTransaction();

                MsCustomerWalletPushBlock block = new MsCustomerWalletPushBlock();
                block.setWalletId(inputBean.getWalletId().trim());
                block.setStatus(CommonVarList.STATUS_BLOCK);

                if (!inputBean.getFromdate().equals("")) {

                    String inputFromDate = inputBean.getFromdate();
                    Date dateFromDate = parser.parse(inputFromDate);
                    outputFromDate = formatter.format(dateFromDate);
                    block.setFromdate(dateFromDate);
                }

                if (!inputBean.getTodate().equals("")) {
                    String inputToDate = inputBean.getTodate();
                    Date dateToDate = parser.parse(inputToDate);
                    outputToDate = formatter.format(dateToDate);
                    block.setTodate(dateToDate);

                }

                block.setCreatetime(sysDate);
                block.setLastupdatedtime(sysDate);
                block.setLastupdateduser(audit.getLastupdateduser());

                String newValue = block.getWalletId()
                        + "|" + dao.getStatusByCode(block.getStatus()).getDescription()
                        + "|" + outputFromDate
                        + "|" + outputToDate;

                audit.setNewvalue(newValue);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(block);

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

    public String updateBN(BlockNotificationInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        String outputFromDate = "";
        String outputToDate = "";
        String fdate = "--";
        String tdate = "--";
        try {

            DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");

            CommonDAO commonDAO = new CommonDAO();
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = commonDAO.getSystemDate(session);

            MsCustomerWalletPushBlock u = (MsCustomerWalletPushBlock) session.get(MsCustomerWalletPushBlock.class, inputBean.getWalletId().trim());

            if (u != null) {

                if (u.getFromdate() != null) {
                    Date dateFromDateold = parser.parse(u.getFromdate().toString());
                    fdate = formatter.format(dateFromDateold);
                }

                if (u.getTodate() != null) {
                    Date dateToDateold = parser.parse(u.getTodate().toString());
                    tdate = formatter.format(dateToDateold);
                }

                if (!inputBean.getFromdate().equals("")) {
                    String inputFromDate = inputBean.getFromdate();
                    Date dateFromDate = parser.parse(inputFromDate);
                    outputFromDate = formatter.format(dateFromDate);
                    u.setFromdate(dateFromDate);

                }

                if (!inputBean.getTodate().equals("")) {
                    String inputToDate = inputBean.getTodate();
                    Date dateToDate = parser.parse(inputToDate);
                    outputToDate = formatter.format(dateToDate);
                    u.setTodate(dateToDate);
                }

                System.out.println("u.getFromdate() " + u.getFromdate());

                String oldValue = u.getWalletId()
                        + "|" + commonDAO.getStatusByCode(u.getStatus()).getDescription()
                        + "|" + fdate
                        + "|" + tdate;

                u.setLastupdateduser(audit.getLastupdateduser());
                u.setLastupdatedtime(sysDate);

                String newValue = u.getWalletId()
                        + "|" + commonDAO.getStatusByCode(u.getStatus()).getDescription()
                        + "|" + outputFromDate
                        + "|" + outputToDate;

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

    public String deleteBN(BlockNotificationInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            MsCustomerWalletPushBlock u = (MsCustomerWalletPushBlock) session.get(MsCustomerWalletPushBlock.class, inputBean.getWalletId().trim());
            if (u != null) {

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);
                // user

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

}
