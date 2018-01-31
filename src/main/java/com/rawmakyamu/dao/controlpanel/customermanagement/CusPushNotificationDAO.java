/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.customermanagement;

import com.rawmakyamu.bean.controlpanel.custmanagement.CusPushNotificationDataBean;
import com.rawmakyamu.bean.controlpanel.custmanagement.CusPushNotificationFileDataBean;
import com.rawmakyamu.bean.controlpanel.custmanagement.CusPushNotificationInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.MsCustomerWalletPush;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.varlist.CommonVarList;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.rawmakyamu.util.mapping.MsCustomerWalletPushFile;
import com.rawmakyamu.util.varlist.MessageVarList;
import java.math.BigInteger;

/**
 *
 * @author prathibha_s
 */
public class CusPushNotificationDAO {

    public List<CusPushNotificationDataBean> getSearchList2(CusPushNotificationInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<CusPushNotificationDataBean> dataList = new ArrayList<CusPushNotificationDataBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by u.createTime desc ";
            }
            long count = 0;

            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(id) from MsCustomerWalletPush as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                String sqlSearch = "from MsCustomerWalletPush u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    CusPushNotificationDataBean pushNotification = new CusPushNotificationDataBean();
                    MsCustomerWalletPush notification = (MsCustomerWalletPush) it.next();

                    try {
                        pushNotification.setId(notification.getId().toString());
                    } catch (NullPointerException e) {
                        pushNotification.setId("--");
                    }

                    try {
                        pushNotification.setWalletId(notification.getWalletId());
                    } catch (NullPointerException e) {
                        pushNotification.setWalletId("--");
                    }

                    try {
                        pushNotification.setMessage(notification.getMessage());
                    } catch (NullPointerException e) {
                        pushNotification.setMessage("--");
                    }

                    try {
                        pushNotification.setFileName(notification.getFileName());
                    } catch (NullPointerException e) {
                        pushNotification.setFileName("--");
                    }

                    try {
                        CommonDAO dao = new CommonDAO();
                        Status stts = dao.getStatusByCode(notification.getStatus());
                        pushNotification.setStatus(stts.getDescription());
                    } catch (NullPointerException e) {
                        pushNotification.setStatus("--");
                    }

                    try {
                        pushNotification.setCreateTime(notification.getCreateTime().toString().substring(0, 19));
                    } catch (Exception e) {
                        pushNotification.setCreateTime("--");
                    }

                    pushNotification.setFullCount(count);
                    dataList.add(pushNotification);
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

    public List<CusPushNotificationFileDataBean> getSearchList(CusPushNotificationInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<CusPushNotificationFileDataBean> dataList = new ArrayList<CusPushNotificationFileDataBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by u.createTime desc ";
            }
            long count = 0;

            String where = this.makeWhereClauseFile(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(id) from MsCustomerWalletPushFile as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                String sqlSearch = "from MsCustomerWalletPushFile u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    CusPushNotificationFileDataBean pushNotification = new CusPushNotificationFileDataBean();
                    MsCustomerWalletPushFile notification = (MsCustomerWalletPushFile) it.next();

                    try {
                        pushNotification.setId(notification.getId().toString());
                    } catch (NullPointerException e) {
                        pushNotification.setId("--");
                    }

                    try {
                        pushNotification.setFileName(notification.getFileName());
                    } catch (NullPointerException e) {
                        pushNotification.setFileName("--");
                    }

                    try {
                        CommonDAO dao = new CommonDAO();
                        pushNotification.setStatus(dao.getStatusByprefix(notification.getStatus()));
                    } catch (NullPointerException e) {
                        pushNotification.setStatus("--");
                    }

                    try {
                        pushNotification.setCreateTime(notification.getCreateTime().toString().substring(0, 19));
                    } catch (Exception e) {
                        pushNotification.setCreateTime("--");
                    }

                    try {
                        pushNotification.setLastUpdatedTime(notification.getLastUpdatedTime().toString().substring(0, 19));
                    } catch (Exception e) {
                        pushNotification.setLastUpdatedTime("--");
                    }

                    pushNotification.setFullCount(count);
                    dataList.add(pushNotification);
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

    private String makeWhereClause(CusPushNotificationInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getWalletId() != null && !inputBean.getWalletId().isEmpty()) {
            where += " and lower(u.walletId) like lower('%" + inputBean.getWalletId().trim() + "%')";
        }
        if (inputBean.getMessages() != null && !inputBean.getMessages().isEmpty()) {
            where += " and lower(u.message) like lower('%" + inputBean.getMessages() + "%')";
        }
        if (inputBean.getFileName() != null && !inputBean.getFileName().isEmpty()) {
            where += " and lower(u.fileName) like lower('%" + inputBean.getFileName() + "%')";
        }
//        if (inputBean.getFileName() != null && !inputBean.getFileName().isEmpty()) {
//            where += " and lower(u.fileName) like lower('%" + inputBean.getFileName() + "%')";
//        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().isEmpty()) {
            where += " and lower(u.status) like lower('%" + inputBean.getStatus() + "%')";

        }

        return where;
    }

    private String makeWhereClauseFile(CusPushNotificationInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getFileId() != null && !inputBean.getFileId().isEmpty()) {
            where += " and lower(u.id) = " + new BigDecimal(inputBean.getFileId().trim());
        }
        if (inputBean.getFileNamef() != null && !inputBean.getFileNamef().isEmpty()) {
            where += " and lower(u.fileName) like lower('%" + inputBean.getFileNamef() + "%')";
        }
        if (inputBean.getStatusFile() != null && !inputBean.getStatusFile().isEmpty()) {
            where += " and lower(u.status) like lower('%" + inputBean.getStatusFile() + "%')";
        }

        return where;
    }

    public String uploadCusPushNoti(CusPushNotificationInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;

        String message = "";

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            txn = session.beginTransaction();

//            MsCustomerWalletPush pushnoti = (MsCustomerWalletPush) session.get(MsCustomerWalletPush.class, in)
            String sqlCount = "select count(id) from MsCustomerWalletPush as u where u.fileName=:fileName and u.walletId=:walletId and u.message=:message";
            Query queryCount = session.createQuery(sqlCount).setString("fileName", inputBean.getFileName())
                    .setParameter("walletId", inputBean.getWalletId())
                    .setParameter("message", inputBean.getMessage());
            Iterator itCount = queryCount.iterate();
            long count = (Long) itCount.next();

            if (count == 0) {

                MsCustomerWalletPush pushnotification = new MsCustomerWalletPush();

                pushnotification.setWalletId(inputBean.getWalletId());
                pushnotification.setMessage(inputBean.getMessage());
                pushnotification.setFileName(inputBean.getFileName());
                pushnotification.setStatus(CommonVarList.STATUS_CUS_PUSH_NOTIFICATION_INITIAL);
                pushnotification.setCreateTime(sysDate);
                pushnotification.setLastUpdatedTime(sysDate);

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(pushnotification);

                txn.commit();
            } else {
                message = "Same walltet id and message duplicated in same file";
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

    public String insetFileName(String File_name, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;

        String message = "";

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            txn = session.beginTransaction();

//            MsCustomerWalletPush pushnoti = (MsCustomerWalletPush) session.get(MsCustomerWalletPush.class, in)
            String sqlCount = "select count(id) from MsCustomerWalletPushFile as u where u.fileName=:fileName";
            Query queryCount = session.createQuery(sqlCount).setString("fileName", File_name);
            Iterator itCount = queryCount.iterate();
            long count = (Long) itCount.next();

            if (count == 0) {

                MsCustomerWalletPushFile pushnotification = new MsCustomerWalletPushFile();

                pushnotification.setFileName(File_name);
                pushnotification.setStatus(CommonVarList.STATUS_CUS_PUSH_NOTIFICATION_INITIAL);
                pushnotification.setCreateTime(sysDate);
                pushnotification.setLastUpdatedTime(sysDate);

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(pushnotification);

                txn.commit();
            } else {
                message = "Recored already exist";
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

    public String setStatus(String fileNameid, Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            CommonDAO commonDAO = new CommonDAO();
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = commonDAO.getSystemDate(session);

            MsCustomerWalletPushFile u = (MsCustomerWalletPushFile) session.get(MsCustomerWalletPushFile.class, new BigDecimal(fileNameid.trim()));

            if (u != null) {
                if (!u.getStatus().equals(CommonVarList.STATUS_CUS_PUSH_NOTIFICATION_SEND)) {

                    u.setStatus(CommonVarList.STATUS_CUS_PUSH_NOTIFICATION_SEND);
                    u.setLastUpdatedTime(sysDate);

                    audit.setOldvalue("Status initial");
                    audit.setNewvalue("Status sent");
                    audit.setCreatetime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.update(u);

                    txn.commit();
                } else {
                    message = "Notifications already sent.";
                }

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
