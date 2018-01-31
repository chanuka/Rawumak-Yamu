/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.systemconfig;

import com.rawmakyamu.bean.controlpanel.systemconfig.TransactionTypeBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.TransactionTypeInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Transactiontype;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author chathuri_t
 */
public class TransactionTypeDAO {

    public String insertTransactionType(TransactionTypeInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Transactiontype) session.get(Transactiontype.class, inputBean.getTransactiontypecode().trim()) == null) {
                txn = session.beginTransaction();

                Transactiontype tt = new Transactiontype();
                tt.setTypecode(inputBean.getTransactiontypecode().trim());
                tt.setDescription(inputBean.getDescription().trim());

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                tt.setStatus(st);

                tt.setOtpRequired(inputBean.getOTPReqStatus());
                tt.setRiskRequired(inputBean.getRiskReqStatus());
                tt.setSortOrder(new Byte(inputBean.getSortOrder().trim()));

                String newV = tt.getTypecode() + "|"
                        + tt.getDescription() + "|"
                        + tt.getStatus().getDescription() + "|"
                        + tt.getSortOrder().toString() + "|"
                        + tt.getOtpRequired().replace("YES", "Yes").replace("NO", "No") + "|"
                        + tt.getRiskRequired().replace("YES", "Yes").replace("NO", "No");
                audit.setNewvalue(newV);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(tt);

                txn.commit();
            } else {

                long count = 0;

                String sqlCount = "select count(typecode) from Transactiontype as u where u.status=:statuscode AND u.typecode=:typecode ";
                Query queryCount = session.createQuery(sqlCount).setString("statuscode", CommonVarList.STATUS_DELETE)
                        .setString("typecode", inputBean.getTransactiontypecode().trim());

                Iterator itCount = queryCount.iterate();
                count = (Long) itCount.next();

                if (count > 0) {
                    message = "$" + inputBean.getTransactiontypecode().trim();
                } else {
                    message = MessageVarList.COMMON_ALREADY_EXISTS;
                }
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

    public List<TransactionTypeBean> getSearchList(TransactionTypeInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<TransactionTypeBean> dataList = new ArrayList<TransactionTypeBean>();
        Session session = null;
        try {

            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by TT.TYPECODE desc ";
            }
            String where = this.makeWhereClause(inputBean);

            BigDecimal count = new BigDecimal(0);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(TT.TYPECODE) from TRANSACTIONTYPE TT where " + where;

            Query queryCount = session.createSQLQuery(sqlCount);

            List countList = queryCount.list();
            count = (BigDecimal) countList.get(0);

            if (count.longValue() > 0) {

                String sqlSearch = " SELECT * from ( select TT.TYPECODE, TT.DESCRIPTION, ST.DESCRIPTION AS STATUSDESC,ST1.DESCRIPTION AS ORDESC , ST2.DESCRIPTION AS RRDESC, ST3.DESCRIPTION AS MTTDES , "
                        + " row_number() over (" + orderBy + ") as r "
                        + " from TRANSACTIONTYPE TT,STATUS ST,STATUS ST1,STATUS ST2,MERCHANT_TXN_TYPE ST3 "
                        + " where TT.STATUS=ST.STATUSCODE AND TT.OTP_REQUIRED=ST1.STATUSCODE AND TT.RISK_REQUIRED=ST2.STATUSCODE AND TT.MERCHANT_TXN_TYPE=ST3.TYPE_CODE AND " + where + ") where r > " + first + " and r<= " + max;

                List<Object[]> chequeList = (List<Object[]>) session.createSQLQuery(sqlSearch).list();

                for (Object[] ttBean : chequeList) {

                    TransactionTypeBean trnsactionTypeBean = new TransactionTypeBean();

                    try {
                        trnsactionTypeBean.setTransactiontypecode(ttBean[0].toString());
                    } catch (NullPointerException npe) {
                        trnsactionTypeBean.setTransactiontypecode("--");
                    }
                    try {
                        trnsactionTypeBean.setDescription(ttBean[1].toString());
                    } catch (NullPointerException npe) {
                        trnsactionTypeBean.setDescription("--");
                    }
                    try {
                        trnsactionTypeBean.setStatus(ttBean[2].toString());
                    } catch (NullPointerException npe) {
                        trnsactionTypeBean.setStatus("--");
                    }
                    try {
                        trnsactionTypeBean.setOTPReqStatus(ttBean[3].toString());
                    } catch (NullPointerException npe) {
                        trnsactionTypeBean.setOTPReqStatus("--");
                    }
                    try {
                        trnsactionTypeBean.setRiskReqStatus(ttBean[4].toString());
                    } catch (NullPointerException npe) {
                        trnsactionTypeBean.setRiskReqStatus("--");
                    }
                    try {
                        trnsactionTypeBean.setMerchantTxnType(ttBean[5].toString());
                        System.out.println("<<<<<" + trnsactionTypeBean.getMerchantTxnType() + ">>>>>");
                    } catch (NullPointerException npe) {
                        trnsactionTypeBean.setMerchantTxnType("--");
                    }

                    trnsactionTypeBean.setFullCount(count.longValue());

                    dataList.add(trnsactionTypeBean);
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

    public List<TransactionTypeBean> getSearchListHQL(TransactionTypeInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<TransactionTypeBean> dataList = new ArrayList<TransactionTypeBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by u.typecode desc ";
            }
            String where = this.makeWhereClause(inputBean);

            long count = 0;

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(id) from Transactiontype as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            System.err.println(sqlCount);
            if (count > 0) {
                String sqlSearch = "from Transactiontype u where " + where + orderBy;
//                System.err.println(sqlSearch);
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                CommonDAO daao = new CommonDAO();
                while (it.hasNext()) {
                    TransactionTypeBean ttype = new TransactionTypeBean();
                    Transactiontype txntype = (Transactiontype) it.next();

                    try {
                        ttype.setTransactiontypecode(txntype.getTypecode());
                    } catch (NullPointerException e) {
                        ttype.setTransactiontypecode("--");
                    }
                    try {
                        ttype.setDescription(txntype.getDescription());
                    } catch (NullPointerException e) {
                        ttype.setDescription("--");
                    }
                    try {
                        ttype.setOTPReqStatus(daao.getDescriptionByStatusCode(txntype.getOtpRequired()));
                    } catch (NullPointerException e) {
                        ttype.setOTPReqStatus("--");
                    }

                    //Acquirer risk required status
                    try {
                        if (txntype.getAcqRiskRequired() != null || !txntype.getAcqRiskRequired().isEmpty()) {
                            ttype.setRiskReqStatus(daao.getDescriptionByStatusCode(txntype.getAcqRiskRequired()));
                        } else {
                            ttype.setRiskReqStatus("--");
                        }
                    } catch (NullPointerException e) {
                        ttype.setRiskReqStatus("--");
                    }

                    try {
                        ttype.setStatus(txntype.getStatus().getDescription());
                    } catch (Exception e) {
                        ttype.setStatus("--");
                    }
                    try {
                        ttype.setSortOrder(txntype.getSortOrder().toString());
                    } catch (NullPointerException e) {
                        ttype.setSortOrder("--");
                    }

                    try {
                        ttype.setMerchantTxnType(txntype.getMerchantTxnType());
                    } catch (Exception e) {
                        ttype.setMerchantTxnType("--");
                    }
                    try {
                        ttype.setDescription_receiver(txntype.getDescription_receiver());
                    } catch (Exception e) {
                        ttype.setDescription_receiver("--");
                    }
                    try {
                        ttype.setHistory_visibility(daao.getDescriptionByStatusCode(txntype.getHistory_visibility()));
                    } catch (Exception e) {
                        ttype.setHistory_visibility("--");
                    }
                    ttype.setFullCount(count);
                    dataList.add(ttype);
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

    private String makeWhereClause(TransactionTypeInputBean inputBean) throws Exception {

        String where = "1=1";

        if ((inputBean.getS_transactiontypecode() == null || inputBean.getS_transactiontypecode().isEmpty())
                && (inputBean.getS_description() == null || inputBean.getS_description().isEmpty())
                && (inputBean.getS_status() == null || inputBean.getS_status().isEmpty())
                && (inputBean.getS_OTPReqStatus() == null || inputBean.getS_OTPReqStatus().isEmpty())
                && (inputBean.getS_RiskReqStatus() == null || inputBean.getS_RiskReqStatus().isEmpty())
                && (inputBean.getS_description_receiver() == null || inputBean.getS_description_receiver().isEmpty())
                && (inputBean.getS_history_visibility() == null || inputBean.getS_history_visibility().isEmpty())
                && (inputBean.getS_sortOrder() == null || inputBean.getS_sortOrder().isEmpty())
                && (inputBean.getS_merchantTxnType() == null || inputBean.getS_merchantTxnType().isEmpty())) {

        } else {

            if (inputBean.getS_transactiontypecode() != null && !inputBean.getS_transactiontypecode().isEmpty()) {
                where += " and u.typecode like '%" + inputBean.getS_transactiontypecode() + "%'";
            }
            if (inputBean.getS_description() != null && !inputBean.getS_description().isEmpty()) {
                where += " and lower(u.description) like lower('%" + inputBean.getS_description() + "%')";
            }
            if (inputBean.getS_status() != null && !inputBean.getS_status().isEmpty()) {
                where += " and u.status.statuscode='" + inputBean.getS_status() + "'";
            }
            if (inputBean.getS_OTPReqStatus() != null && !inputBean.getS_OTPReqStatus().isEmpty()) {
                where += " and u.otpRequired='" + inputBean.getS_OTPReqStatus() + "'";
            }
            if (inputBean.getS_RiskReqStatus() != null && !inputBean.getS_RiskReqStatus().isEmpty()) {
                where += " and u.acqRiskRequired='" + inputBean.getS_RiskReqStatus() + "'";
            }
            if (inputBean.getS_description_receiver() != null && !inputBean.getS_description_receiver().isEmpty()) {
                where += " and lower(u.description_receiver) like lower('%" + inputBean.getS_description_receiver() + "%')";
            }
            if (inputBean.getS_sortOrder() != null && !inputBean.getS_sortOrder().isEmpty()) {
                where += " and u.sortOrder like '%" + inputBean.getS_sortOrder() + "%'";
            }
            if (inputBean.getS_history_visibility() != null && !inputBean.getS_history_visibility().isEmpty()) {
                where += " and u.history_visibility='" + inputBean.getS_history_visibility() + "'";
            }
            System.out.println(inputBean.getS_merchantTxnType() + "<<<<<<");
            if (inputBean.getS_merchantTxnType() != null && !inputBean.getS_merchantTxnType().isEmpty()) {
                where += " and u.merchantTxnType='" + inputBean.getS_merchantTxnType() + "'";
            }

        }
        return where;
    }

    public Transactiontype findTransactionTypeById(String ttCode) throws Exception {
        Transactiontype tt = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Transactiontype as u where u.typecode=:typecode";
            Query query = session.createQuery(sql).setString("typecode", ttCode);
            tt = (Transactiontype) query.list().get(0);

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
        return tt;

    }

    public boolean IsSortKeyExist(String sortOrder) throws Exception {
        Transactiontype tt = null;
        Session session = null;
        boolean res = false;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Transactiontype as u where u.sortOrder=:sortOrder";
            Query query = session.createQuery(sql).setString("sortOrder", sortOrder);

            if (!query.list().isEmpty()) {
                res = true;
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
        return res;

    }

    public String updateTransactionType(TransactionTypeInputBean inputBean, Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Transactiontype u = (Transactiontype) session.get(Transactiontype.class, inputBean.getTransactiontypecode().trim());

            if (u != null) {
                String hisvis, otpreq, riskreq, oldSortOrder, newSortOrder;
                try {
                    hisvis = u.getHistory_visibility().replace("YES", "Yes").replace("NO", "No");
                } catch (Exception e) {
                    hisvis = "";
                }
                try {
                    otpreq = u.getOtpRequired().replace("YES", "Yes").replace("NO", "No");
                } catch (Exception e) {
                    otpreq = "";
                }
                try {
                    riskreq = u.getRiskRequired().replace("YES", "Yes").replace("NO", "No");
                } catch (Exception e) {
                    riskreq = "";
                }
                try {
                    if (u.getSortOrder() == null) {
                        oldSortOrder = "";
                    } else {
                        oldSortOrder = u.getSortOrder().toString();
                    }
                } catch (Exception e) {
                    oldSortOrder = "";
                }
                String oldV = u.getTypecode() + "|"
                        + u.getDescription() + "|"
                        + u.getStatus().getDescription() + "|"
                        + oldSortOrder + "|"
                        //                        + u.getOtpRequired().replace("YES", "Yes").replace("NO", "No") + "|"
                        + otpreq + "|"
                        //                        + u.getRiskRequired().replace("YES", "Yes").replace("NO", "No") + "|"
                        + riskreq + "|"
                        + u.getMerchantTxnType() + "|"
                        + u.getDescription_receiver() + "|"
                        //                        + u.getHistory_visibility().replace("YES", "Yes").replace("NO", "No");
                        + hisvis;

                u.setDescription(inputBean.getDescription().trim());

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                u.setStatus(st);

                u.setOtpRequired(inputBean.getOTPReqStatus());
                u.setHistory_visibility(inputBean.getHistory_visibility());
                u.setDescription_receiver(inputBean.getDescription_receiver());
                u.setRiskRequired(inputBean.getRiskReqStatus());
                u.setMerchantTxnType(inputBean.getMerchantTxnType());
                if (!inputBean.getSortOrder().equals("")) {
                    u.setSortOrder(new Byte(inputBean.getSortOrder().trim()));
                } else {
                    u.setSortOrder(null);
                }
                try {
                    if (u.getSortOrder() == null) {
                        newSortOrder = "";
                    } else {
                        newSortOrder = u.getSortOrder().toString();
                    }
                } catch (Exception e) {
                    newSortOrder = "";
                }
                String newV = u.getTypecode() + "|"
                        + u.getDescription() + "|"
                        + u.getStatus().getDescription() + "|"
                        + newSortOrder + "|"
                        + u.getOtpRequired().replace("YES", "Yes").replace("NO", "No") + "|"
                        + u.getRiskRequired().replace("YES", "Yes").replace("NO", "No") + "|"
                        + u.getMerchantTxnType() + "|"
                        + u.getDescription_receiver() + "|"
                        + u.getHistory_visibility().replace("YES", "Yes").replace("NO", "No");
                audit.setOldvalue(oldV);
                audit.setNewvalue(newV);
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

    public String deleteTransactionType(TransactionTypeInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Transactiontype u = (Transactiontype) session.get(Transactiontype.class, inputBean.getTransactiontypecode().trim());
            if (u != null) {

                long count = 0;

                String sqlCount = "select count(typecode) from Transactiontype as u where u.typecode=:typecode";
                Query queryCount = session.createQuery(sqlCount).setString("typecode", inputBean.getTransactiontypecode().trim());

                Iterator itCount = queryCount.iterate();
                count = (Long) itCount.next();

                if (count > 0) {

                    audit.setCreatetime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.delete(u);
                    txn.commit();

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
