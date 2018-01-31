/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.reportexplorer;

import com.rawmakyamu.bean.reportexplorer.TransDataBean;
import com.rawmakyamu.bean.reportexplorer.TxnDataBean;
import com.rawmakyamu.bean.reportexplorer.TxnExplorerInputBean;
import com.rawmakyamu.bean.reportexplorer.TxnHisDataBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.Common;
import com.rawmakyamu.util.common.ExcelCommon;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.Currency;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Transaction;
import com.rawmakyamu.util.mapping.Transactionhistory;
import com.rawmakyamu.util.mapping.Transactiontype;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
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
 * @author thushanth
 */
public class TransactionExplorerDAO {

    private final int columnCount = 20;
    private final int headerRowCount = 13;

    private String TXN_COUNT_SQL = "select "
            + "count(g.TRANSACTIONID) " //0            
            + "from SBANK_TRANSACTION g "
            + "left outer join status m on m.STATUSCODE = g.STATUS "
            + "where ";

    private String TXN_ORDER_BY_SQL = " order by g.CREATETIME DESC ";

    public List<TxnDataBean> getSearchListbySQL(TxnExplorerInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<TxnDataBean> dataList = new ArrayList<TxnDataBean>();
        Session session = null;

        String TXN_SQL = "select "
                + "g.TRANSACTIONID, " //0 
                + "g.MASKING_CARD, " //1 
                + "g.TO_MASKING_CARD, " //2  
                + "g.PROCESSINGCODE, " //3
                + "g.MID, " //4
                + "g.TID, " //5
                + "tt.DESCRIPTION aa, " //6
                + "g.LISTNERUID, " //7   
                + "g.TRACENUMBER, " //8
                + "cc.DESCRIPTION bb, " //9
                + "g.AMOUNT, " //10
                + "s2.DESCRIPTION dd, " //11    
                + "rc.DESCRIPTION rd, " //12        
                + "g.MOBILENUMBER, " //13
                + "g.INVOICENO, " //14
                + "g.RRN, " //15
                + "g.WALLETID, " //16       
                + "g.CREATETIME, " //17
                + "g.APPROVECODE, " //18
                + "g.TOMOBILE, " //19
                + "g.TOKEN_ID, " //20
                + "g.TO_EMAIL, " //21
                + "s3.DESCRIPTION kk, " //22
                + "g.TOWALLET, " //23
                + "g.PROMO_AMOUNT, " //24
                + "g.COMM_AMOUNT, " //25
                + "g.DEDUCT_AMOUNT, " //26
                + "ct.DESCRIPTION cardt, " //27
                + "cb.DESCRIPTION cardb, " //28
                + "row_number() over ( " + orderBy + ") as r " //29

                + "from SBANK_TRANSACTION g "
                + "left outer join status s2 on s2.STATUSCODE = g.STATUS "
                + "left outer join status s3 on s3.STATUSCODE = g.APPROVAL_STATUS "
                + "left outer join transactiontype tt on tt.TYPECODE = g.TXNTYPECODE "
                + "left outer join currency cc on cc.CURRENCYCODE = g.CURRENCYCODE "
                //            + "left outer join MS_CUSTOMER_WALLET cw on cw.WALLET_ID = g.WALLETID "
                + "left outer join RESPONSE_CODES rc on rc.CODE = g.RESPONCECODE "
                + "left outer join CARD_TYPE ct on ct.CODE = g.CARD_TYPE "
                + "left outer join CARD_BRAND cb on cb.CODE = g.CARD_BRAND "
                + "where ";

        try {

            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by g.CREATETIME desc ";
            }
            inputBean.setAmount(inputBean.getAmount().replace(",", ""));
            String where = this.makeWhereClauseForExcel(inputBean);

            BigDecimal count = new BigDecimal(0);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = TXN_COUNT_SQL + where;

            Query queryCount = session.createSQLQuery(sqlCount);

            List countList = queryCount.list();
            count = (BigDecimal) countList.get(0);
            System.err.println(sqlCount);
            System.err.println(count);
            if (count.longValue() > 0) {

//                String sqlQ = " SELECT * from (" + TXN_SQL + where + orderBy + ") where r > " + first + " and r<= " + max + first;
                String sqlQ = " SELECT * from (" + TXN_SQL + where + ") where r > " + first + " and r<= " + max;

                System.err.println(sqlQ);
                List<Object[]> chequeList = (List<Object[]>) session.createSQLQuery(sqlQ).list();

                for (Object[] stmtBean : chequeList) {

                    TxnDataBean sdblPage = new TxnDataBean();

                    if (stmtBean[0] != null) {
                        sdblPage.setTxnid(String.valueOf(stmtBean[0]));
                    } else {
                        sdblPage.setTxnid("--");
                    }

                    if (stmtBean[1] != null) {
                        sdblPage.setMaskedcard(String.valueOf(stmtBean[1]));
                    } else {
                        sdblPage.setMaskedcard("--");

                    }

                    if (stmtBean[2] != null) {
                        sdblPage.setTomaskedcard(String.valueOf(stmtBean[2]));

                    } else {
                        sdblPage.setTomaskedcard("--");
                    }

                    if (stmtBean[3] != null) {
                        sdblPage.setProcessingcode(String.valueOf(stmtBean[3]));
                    } else {
                        sdblPage.setProcessingcode("--");
                    }

                    if (stmtBean[4] != null) {
                        sdblPage.setMid(String.valueOf(stmtBean[4]));
                    } else {
                        sdblPage.setMid("--");
                    }

                    if (stmtBean[5] != null) {
                        sdblPage.setTid(String.valueOf(stmtBean[5]));
                    } else {
                        sdblPage.setTid("--");
                    }

                    if (stmtBean[6] != null) {
                        sdblPage.setTxntypecode(String.valueOf(stmtBean[6]));
//                        System.err.println("txntype " + stmtBean[6]);
                    } else {
                        sdblPage.setTxntypecode("--");
                    }

                    if (stmtBean[7] != null) {
                        sdblPage.setListeneruid(String.valueOf(stmtBean[7]));

                    } else {
                        sdblPage.setListeneruid("--");
                    }

                    if (stmtBean[8] != null) {
                        sdblPage.setTraceno(String.valueOf(stmtBean[8]));
                    } else {
                        sdblPage.setTraceno("--");
                    }

                    if (stmtBean[9] != null) {
                        sdblPage.setCurrency(String.valueOf(stmtBean[9]));
                    } else {
                        sdblPage.setCurrency("--");
                    }

                    if (stmtBean[10] != null) {
//                        sdblPage.setAmount(Common.toCurrencyFormat(String.valueOf(stmtBean[10])));

                        double d = Double.parseDouble(String.valueOf(stmtBean[10]));
                        sdblPage.setAmount(Common.toCurrencyFormat(String.format("%.2f", new BigDecimal(d))));

                    } else {
                        sdblPage.setAmount("--");
                    }

                    if (stmtBean[11] != null) {
                        sdblPage.setStatucode(String.valueOf(stmtBean[11]));
                    } else {
                        sdblPage.setStatucode("--");
                    }

                    if (stmtBean[12] != null) {
                        sdblPage.setRescode(String.valueOf(stmtBean[12]));
                    } else {
                        sdblPage.setRescode("--");
                    }

                    if (stmtBean[13] != null) {
                        sdblPage.setMobile(String.valueOf(stmtBean[13]));
                    } else {
                        sdblPage.setMobile("--");
                    }

                    if (stmtBean[14] != null) {
                        sdblPage.setInvoiceno(String.valueOf(stmtBean[14]));
                    } else {
                        sdblPage.setInvoiceno("--");
                    }

                    if (stmtBean[15] != null) {
                        sdblPage.setRrn(String.valueOf(stmtBean[15]));
                    } else {
                        sdblPage.setRrn("--");
                    }

                    if (stmtBean[16] != null) {
                        sdblPage.setWalletid(String.valueOf(stmtBean[16]));
                    } else {
                        sdblPage.setWalletid("--");
                    }

                    if (stmtBean[17] != null) {
                        sdblPage.setCreatedtime(String.valueOf(stmtBean[17]).substring(0, 19));
                    } else {
                        sdblPage.setCreatedtime("--");
                    }

                    if (stmtBean[18] != null) {
                        sdblPage.setApprovecode(String.valueOf(stmtBean[18]));
                    } else {
                        sdblPage.setApprovecode("--");
                    }

                    if (stmtBean[19] != null) {
                        sdblPage.setTomobile(String.valueOf(stmtBean[19]));
                    } else {
                        sdblPage.setTomobile("--");
                    }

                    if (stmtBean[20] != null) {
                        sdblPage.setTokenid(String.valueOf(stmtBean[20]));
                    } else {
                        sdblPage.setTokenid("--");
                    }

                    if (stmtBean[21] != null) {
                        sdblPage.setToemail(String.valueOf(stmtBean[21]));
                    } else {
                        sdblPage.setToemail("--");
                    }

                    if (stmtBean[22] != null) {
                        sdblPage.setApprovalstatus(String.valueOf(stmtBean[22]));
                    } else {
                        sdblPage.setApprovalstatus("--");
                    }

                    if (stmtBean[23] != null) {
                        sdblPage.setTowallet(String.valueOf(stmtBean[23]));
                    } else {
                        sdblPage.setTowallet("--");
                    }

                    if (stmtBean[24] != null) {
//                        sdblPage.setPromoAmount(Common.toCurrencyFormat(String.valueOf(stmtBean[24])));

                        double d = Double.parseDouble(String.valueOf(stmtBean[24]));
                        sdblPage.setPromoAmount(Common.toCurrencyFormat(String.format("%.2f", new BigDecimal(d))));

                    } else {
                        sdblPage.setPromoAmount("--");
                    }

                    if (stmtBean[25] != null) {
//                        sdblPage.setCommissionAmount(Common.toCurrencyFormat(String.valueOf(stmtBean[25])));

                        double d = Double.parseDouble(String.valueOf(stmtBean[25]));
                        sdblPage.setCommissionAmount(Common.toCurrencyFormat(String.format("%.2f", new BigDecimal(d))));

                    } else {
                        sdblPage.setCommissionAmount("--");
                    }

                    if (stmtBean[26] != null) {
//                        sdblPage.setDeductAmount(Common.toCurrencyFormat(String.valueOf(stmtBean[26])));

                        double d = Double.parseDouble(String.valueOf(stmtBean[26]));
                        sdblPage.setDeductAmount(Common.toCurrencyFormat(String.format("%.2f", new BigDecimal(d))));

                    } else {
                        sdblPage.setDeductAmount("--");
                    }

                    if (stmtBean[27] != null) {
                        sdblPage.setCardType(String.valueOf(stmtBean[27]));
                    } else {
                        sdblPage.setCardType("--");
                    }

                    if (stmtBean[28] != null) {
                        sdblPage.setCardBrand(String.valueOf(stmtBean[28]));
                    } else {
                        sdblPage.setCardBrand("--");
                    }

                    sdblPage.setFullCount(count.longValue());

                    dataList.add(sdblPage);
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

    public List<TxnDataBean> getSearchList(TxnExplorerInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<TxnDataBean> dataList = new ArrayList<TxnDataBean>();
        Session session = null;
        try {
            String where = this.makeWhereClause(inputBean);
            long count = 0;

            session = HibernateInit.sessionFactory.openSession();
            String sqlCount = "select count(transactionid) from Transaction as p where " + where;
            Query queryCount = session.createQuery(sqlCount);
            queryCount = setDatesToQuery(queryCount, inputBean, session);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {
                System.err.println(count);
                String sqlSearch = " From Transaction as p join fetch p.status  "
                        //                String sqlSearch = " From Transaction as p left outer join status m on m.statuscode = p.status  "
                        + " where " + where + orderBy;

                Query querySearch = session.createQuery(sqlSearch);
                querySearch = setDatesToQuery(querySearch, inputBean, session);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                List<Transaction> txnList = querySearch.list();
//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

//                Iterator itSearch = querySearch.iterate();
                CommonDAO dao = new CommonDAO();

                for (Transaction txnBean : txnList) {

                    TxnDataBean dataBean = new TxnDataBean();

                    try {
                        dataBean.setTxnid(txnBean.getTransactionid());
                    } catch (NullPointerException npe) {
                        dataBean.setTxnid("--");
                    }
                    if (txnBean.getProcessingcode() != null && !txnBean.getProcessingcode().isEmpty()) {
                        dataBean.setProcessingcode(txnBean.getProcessingcode());
                    } else {
                        dataBean.setProcessingcode("--");
                    }
                    if (txnBean.getAmount() != null && !txnBean.getAmount().isEmpty()) {
                        dataBean.setAmount(Common.toCurrencyFormat(txnBean.getAmount()));
                    } else {
                        dataBean.setAmount("--");
                    }

                    if (txnBean.getApprovecode() != null && !txnBean.getApprovecode().isEmpty()) {
                        dataBean.setApprovecode(txnBean.getApprovecode());
                    } else {
                        dataBean.setApprovecode("--");
                    }

                    if (txnBean.getInvoiceno() != null && !txnBean.getInvoiceno().isEmpty()) {
                        dataBean.setInvoiceno(txnBean.getInvoiceno());
                    } else {
                        dataBean.setInvoiceno("--");
                    }

                    if (txnBean.getMaskingcard() != null && !txnBean.getMaskingcard().isEmpty()) {
                        dataBean.setMaskedcard(txnBean.getMaskingcard());
                    } else {
                        dataBean.setMaskedcard("--");
                    }

                    if (txnBean.getTomaskingcard() != null && !txnBean.getTomaskingcard().isEmpty()) {
                        dataBean.setTomaskedcard(txnBean.getTomaskingcard());
                    } else {
                        dataBean.setTomaskedcard("--");
                    }

                    if (txnBean.getMid() != null && !txnBean.getMid().isEmpty()) {
                        dataBean.setMid(txnBean.getMid());
                    } else {
                        dataBean.setMid("--");
                    }

                    if (txnBean.getTid() != null && !txnBean.getTid().isEmpty()) {
                        dataBean.setTid(txnBean.getTid());
                    } else {
                        dataBean.setTid("--");
                    }

                    if (txnBean.getCurrency() != null && !txnBean.getCurrency().isEmpty()) {
                        dataBean.setCurrency(txnBean.getCurrency());
                    } else {
                        dataBean.setCurrency("--");
                    }

                    if (txnBean.getMobile() != null && !txnBean.getMobile().isEmpty()) {
                        dataBean.setMobile(txnBean.getMobile());
                    } else {
                        dataBean.setMobile("--");
                    }

                    if (txnBean.getListeneruid() != null && !txnBean.getListeneruid().isEmpty()) {
                        dataBean.setListeneruid(txnBean.getListeneruid());
                    } else {
                        dataBean.setListeneruid("--");
                    }
                    if (txnBean.getInvoiceno() != null && !txnBean.getInvoiceno().isEmpty()) {
                        dataBean.setInvoiceno(txnBean.getInvoiceno());
                    } else {
                        dataBean.setInvoiceno("--");
                    }
                    if (txnBean.getSwitchresponse() != null && !txnBean.getSwitchresponse().isEmpty()) {
                        dataBean.setRescode(txnBean.getSwitchresponse());
                    } else {
                        dataBean.setRescode("--");
                    }

                    if (txnBean.getCreatetime() != null && !txnBean.getCreatetime().toString().isEmpty()) {
                        dataBean.setCreatedtime(String.valueOf(txnBean.getCreatetime()));
                    } else {
                        dataBean.setCreatedtime("--");
                    }

                    if (txnBean.getRrn() != null && !txnBean.getRrn().isEmpty()) {
                        dataBean.setRrn(txnBean.getRrn());
                    } else {
                        dataBean.setRrn("--");
                    }
                    if (txnBean.getStatus() != null && !txnBean.getStatus().getDescription().isEmpty()) {
                        dataBean.setStatucode(txnBean.getStatus().getDescription());
                    } else {
                        dataBean.setStatucode("--");
                    }

                    if (txnBean.getTracenumber() != null && !txnBean.getTracenumber().isEmpty()) {
                        dataBean.setTraceno(txnBean.getTracenumber());
                    } else {
                        dataBean.setTraceno("--");
                    }

                    if (txnBean.getWalletid() != null && !txnBean.getWalletid().isEmpty()) {
                        dataBean.setWalletid(txnBean.getWalletid());
                    } else {
                        dataBean.setWalletid("--");
                    }
                    try {
                        if (txnBean.getTxntypecode() != null && !txnBean.getTxntypecode().isEmpty()) {
                            dataBean.setTxntypecode(txnBean.getTxntypecode());
                        } else {
                            dataBean.setTxntypecode("--");
                        }
                    } catch (Exception e) {
                        dataBean.setTxntypecode("--");
                    }

                    dataBean.setFullCount(count);

                    dataList.add(dataBean);
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

    public List<TxnHisDataBean> getSearchHistoryList(TxnExplorerInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<TxnHisDataBean> dataList = new ArrayList<TxnHisDataBean>();
        Session session = null;
        try {
            long count = 0;

            session = HibernateInit.sessionFactory.openSession();
            String sqlCount = "select count(transactionhistoryid) from Transactionhistory as p where p.transactionid =:txnid";
            Query queryCount = session.createQuery(sqlCount).setString("txnid", inputBean.getTransactionID());
            queryCount = setDatesToQuery(queryCount, inputBean, session);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            if (count > 0) {

                String sqlSearch = "From Transactionhistory as p where p.transactionid =:txnid " + orderBy;
                Query querySearch = session.createQuery(sqlSearch).setString("txnid", inputBean.getTransactionID());
                querySearch = setDatesToQuery(querySearch, inputBean, session);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                List<Transactionhistory> txnhisList = querySearch.list();

                for (Transactionhistory txnBean : txnhisList) {

                    TxnHisDataBean dataBean = new TxnHisDataBean();
                    if (txnBean.getTransactionid() == null || txnBean.getTransactionid().isEmpty()) {
                        dataBean.setTransactionid("--");
                    } else {
                        dataBean.setTransactionid(txnBean.getTransactionid());
                    }

                    if (txnBean.getTransactionhistoryid() == null || txnBean.getTransactionhistoryid().isEmpty()) {
                        dataBean.setTransactionhistoryid("--");
                    } else {
                        dataBean.setTransactionhistoryid(txnBean.getTransactionhistoryid());
                    }

                    if (txnBean.getStatus() == null || txnBean.getStatus().isEmpty()) {
                        dataBean.setStatuscode("--");
                    } else {
                        CommonDAO commonDao = new CommonDAO();
                        dataBean.setStatuscode(commonDao.getStatusByprefix(txnBean.getStatus()));
                    }

                    if (txnBean.getCreatetime() == null) {
                        dataBean.setCreatedtime("--");
                    } else {
                        dataBean.setCreatedtime(String.valueOf(txnBean.getCreatetime()).substring(0, 19));
                    }

                    dataBean.setFullCount(count);

                    dataList.add(dataBean);
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

    public Currency findTrans(String tid) throws Exception {

        Session session = null;
//        String tranBean = null;
        Currency tran = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "Select u.currencycode from Mservercurrency as u where u.tid=:tid";
            Query query = session.createQuery(sql).setString("tid", tid);

            try {
                tran = (Currency) query.list().get(0);
//            Iterator itCount = query.iterate();
//            tranBean = (String) itCount.next();
            } catch (Exception exc) {

            }
//            tranBean = (Mservercurrency) session.get(Mservercurrency.class, tid);

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
        return tran;
    }

    public TransDataBean findTransById(String txnid) throws Exception {
        String createtime;
        String lastupdatedtime;
        TransDataBean transDatabean;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            Transaction tranBean = (Transaction) session.get(Transaction.class, txnid);

            if (tranBean.getCreatetime() != null && !tranBean.getCreatetime().toString().isEmpty()) {
                createtime = tranBean.getCreatetime().toString().substring(0, 19);
            } else {
                createtime = "--";
            }
            if (tranBean.getLastupdatedtime() != null && !tranBean.getLastupdatedtime().toString().isEmpty()) {
                lastupdatedtime = tranBean.getLastupdatedtime().toString().substring(0, 19);
            } else {
                lastupdatedtime = "--";
            }

            transDatabean = new TransDataBean(tranBean);

            transDatabean.setCreatedtime(createtime);
            transDatabean.setLastupdatedtime(lastupdatedtime);

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
        return transDatabean;
    }

    private String makeWhereClause(TxnExplorerInputBean inputBean) {
        String where = "1=1";
        if ((inputBean.getTransactionID() == null || inputBean.getTransactionID().isEmpty())
                && (inputBean.getAmount() == null || inputBean.getAmount().isEmpty())
                && (inputBean.getApproveCode() == null || inputBean.getApproveCode().isEmpty())
                && (inputBean.getFromdate() == null || inputBean.getFromdate().isEmpty())
                && (inputBean.getTodate() == null || inputBean.getTodate().isEmpty())
                && (inputBean.getCardNo() == null || inputBean.getCardNo().isEmpty())
                && (inputBean.getMobileNo() == null || inputBean.getMobileNo().isEmpty())
                && (inputBean.getTid() == null || inputBean.getTid().isEmpty())
                && (inputBean.getMid() == null || inputBean.getMid().isEmpty())
                && (inputBean.getCurrency() == null || inputBean.getCurrency().isEmpty())
                && (inputBean.getRrn() == null || inputBean.getRrn().isEmpty())
                && (inputBean.getProcessingcode() == null || inputBean.getProcessingcode().isEmpty())
                && (inputBean.getTraceNo() == null || inputBean.getTraceNo().isEmpty())
                && (inputBean.getStatus() == null || inputBean.getStatus().isEmpty())
                && (inputBean.getWalletid() == null || inputBean.getWalletid().isEmpty())
                && (inputBean.getTxnTypeCode() == null || inputBean.getTxnTypeCode().isEmpty())
                && (inputBean.getTocardno() == null || inputBean.getTocardno().isEmpty())
                && (inputBean.getFromcardno() == null || inputBean.getFromcardno().isEmpty())
                && (inputBean.getResCode() == null || inputBean.getResCode().isEmpty())) {

        } else {

            if (inputBean.getFromdate() != null && !inputBean.getFromdate().isEmpty()) {
                where += " and p.lastupdatedtime >= :fromdate";
            }
            if (inputBean.getTodate() != null && !inputBean.getTodate().isEmpty()) {
                where += " and p.lastupdatedtime <= :todate";
            }
            if (inputBean.getAmount() != null && !inputBean.getAmount().isEmpty()) {
                where += " and p.amount='" + inputBean.getAmount().trim() + "'";
            }
            if (inputBean.getTid() != null && !inputBean.getTid().isEmpty()) {
                where += " and p.tid LIKE '%" + inputBean.getTid().trim() + "%'";
            }
            if (inputBean.getMid() != null && !inputBean.getMid().isEmpty()) {
                where += " and p.mid LIKE '%" + inputBean.getMid().trim() + "%'";
            }
            if (inputBean.getCurrency() != null && !inputBean.getCurrency().isEmpty()) {
                where += " and p.currency.currencycode='" + inputBean.getCurrency() + "'";
            }
            if (inputBean.getWalletid() != null && !inputBean.getWalletid().isEmpty()) {
                where += " and p.walletid LIKE '%" + inputBean.getWalletid().trim() + "%'";
            }
            if (inputBean.getTxnTypeCode() != null && !inputBean.getTxnTypeCode().isEmpty()) {
                where += " and p.txntypecode.typecode='" + inputBean.getTxnTypeCode() + "'";
            }
            if (inputBean.getCardNo() != null && !inputBean.getCardNo().isEmpty()) {

                where += " and p.maskingcard like '%" + inputBean.getCardNo().trim() + "%'";
            }
            if (inputBean.getTransactionID() != null && !inputBean.getTransactionID().isEmpty()) {
                where += " and p.transactionid LIKE '%" + inputBean.getTransactionID().trim() + "%'";
            }
            if (inputBean.getProcessingcode() != null && !inputBean.getProcessingcode().isEmpty()) {
                where += " and p.processingcode LIKE '%" + inputBean.getProcessingcode().trim() + "%'";
            }
            if (inputBean.getMobileNo() != null && !inputBean.getMobileNo().isEmpty()) {
                where += " and p.MOBILENUMBER LIKE '%" + inputBean.getMobileNo().trim() + "%'";
            }
            if (inputBean.getToMobileNo() != null && !inputBean.getToMobileNo().isEmpty()) {
                where += " and p.TOMOBILE LIKE '%" + inputBean.getToMobileNo().trim() + "%'";
            }
            if (inputBean.getTraceNo() != null && !inputBean.getTraceNo().isEmpty()) {
                where += " and p.tracenumber LIKE '%" + inputBean.getTraceNo().trim() + "%'";
            }
            if (inputBean.getStatus() != null && !inputBean.getStatus().isEmpty()) {
                if (inputBean.getStatus().equals("TCMP")) {
                    where += " and p.status.statuscode='" + inputBean.getStatus() + "' or p.status.statuscode='TXNS'";
                } else {
                    where += " and p.status.statuscode='" + inputBean.getStatus() + "'";
                }

            }

            if (inputBean.getRrn() != null && !inputBean.getRrn().isEmpty()) {
                where += " and p.rrn LIKE '%" + inputBean.getRrn().trim() + "%'";
            }
            if (inputBean.getResCode() != null && !inputBean.getResCode().isEmpty()) {
                where += " and p.switchresponse.responsecode LIKE '%" + inputBean.getResCode().trim() + "%'";
            }
            if (inputBean.getListeneruid() != null && !inputBean.getListeneruid().isEmpty()) {
                where += " and g.LISTNERUID LIKE '%" + inputBean.getListeneruid() + "%'";
            }
            if (inputBean.getInvoiceno() != null && !inputBean.getInvoiceno().isEmpty()) {
                where += " and g.INVOICENO LIKE '%" + inputBean.getInvoiceno() + "%'";
            }

        }

        return where;
    }

    public String getTxnDesbycode(String code) throws Exception {

        String nic = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Transactiontype as u where u.typecode=:code";
            Query query = session.createQuery(sql).setString("code", code);
            try {
                nic = ((Transactiontype) query.list().get(0)).getDescription();
            } catch (Exception ee) {
                nic = "--";
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
        return nic;
    }

    public String getCurrencyDesbycode(String code) throws Exception {

        String nic = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Currency as u where u.currencycode=:code";
            Query query = session.createQuery(sql).setString("code", code);
            try {
                nic = ((Currency) query.list().get(0)).getDescription();
            } catch (Exception ee) {
                nic = "--";
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
        return nic;
    }

    public Query setDatesToQuery(Query sql, TxnExplorerInputBean inputBean, Session session) throws Exception {

        if ((inputBean.getTransactionID() == null || inputBean.getTransactionID().isEmpty())
                && (inputBean.getAmount() == null || inputBean.getAmount().isEmpty())
                && (inputBean.getApproveCode() == null || inputBean.getApproveCode().isEmpty())
                && (inputBean.getFromdate() == null || inputBean.getFromdate().isEmpty())
                && (inputBean.getTodate() == null || inputBean.getTodate().isEmpty())
                && (inputBean.getCardNo() == null || inputBean.getCardNo().isEmpty())
                && (inputBean.getTid() == null || inputBean.getTid().isEmpty())
                && (inputBean.getMid() == null || inputBean.getMid().isEmpty())
                && (inputBean.getCurrency() == null || inputBean.getCurrency().isEmpty())
                && (inputBean.getInvoiceno() == null || inputBean.getInvoiceno().isEmpty())
                && (inputBean.getRrn() == null || inputBean.getRrn().isEmpty())
                && (inputBean.getProcessingcode() == null || inputBean.getProcessingcode().isEmpty())
                && (inputBean.getTraceNo() == null || inputBean.getTraceNo().isEmpty())
                && (inputBean.getStatus() == null || inputBean.getStatus().isEmpty())
                && (inputBean.getWalletid() == null || inputBean.getWalletid().isEmpty())
                && (inputBean.getTxnTypeCode() == null || inputBean.getTxnTypeCode().isEmpty())
                && (inputBean.getResCode() == null || inputBean.getResCode().isEmpty())) {

        } else {

            if (inputBean.getFromdate() != null && !inputBean.getFromdate().isEmpty()) {
                if ((Common.specialStringtoDate(inputBean.getFromdate()) != null)) {
                    sql.setDate("fromdate", Common.specialStringtoDate(inputBean.getFromdate()));
                }
            }
            if (inputBean.getTodate() != null && !inputBean.getTodate().isEmpty()) {
                if ((Common.specialStringtoDate(inputBean.getTodate()) != null)) {
                    Date d = Common.specialStringtoDate(inputBean.getTodate());
                    int da = d.getDate() + 1;
                    d.setDate(da);
                    sql.setDate("todate", d);

                }
            }
        }
        return sql;
    }

    public String findDesByCode(String status, String txntypecode, String currency, String response) throws Exception {

        Status sta = null;
        Transactiontype de = null;
        Currency c = null;


        String result = "";
        Session session = null;
        // Date sysDate = CommonDAO.getSystemDate(session);
        try {
            session = HibernateInit.sessionFactory.openSession();

            if (!"".equals(status)) {
                String sql = "from Status as u where u.statuscode=:statuscode";
                Query query = session.createQuery(sql).setString("statuscode", status);
                sta = (Status) query.list().get(0);
                result = sta.getDescription();
            }

            if (!"".equals(txntypecode)) {
                String sql3 = "from Transactiontype as u where u.typecode=:status";
                Query query3 = session.createQuery(sql3).setString("status", txntypecode);
                de = (Transactiontype) query3.list().get(0);
                result = de.getDescription();
            }

            if (!"".equals(currency)) {
                String sql3 = "from Currency as u where u.currencycode=:currency";
                Query query3 = session.createQuery(sql3).setString("currency", currency);
                c = (Currency) query3.list().get(0);
                result = c.getDescription();
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
        return result;
    }

    public Object generateExcelReport(TxnExplorerInputBean inputBean) throws Exception {
        Session session = null;
        Object returnObject = null;

        String TXN_SQL_EXCEL = "select "
                + "g.TRANSACTIONID, " //0 
                + "g.MASKING_CARD, " //1 
                + "g.TO_MASKING_CARD, " //2  
                + "g.PROCESSINGCODE, " //3
                + "g.MID, " //4
                + "g.TID, " //5
                + "tt.DESCRIPTION aa, " //6
                + "g.LISTNERUID, " //7   
                + "g.TRACENUMBER, " //8
                + "cc.DESCRIPTION bb, " //9
                + "g.AMOUNT, " //10
                + "s2.DESCRIPTION dd, " //11    
                + "rc.DESCRIPTION rd, " //12        
                + "g.MOBILENUMBER, " //13
                + "g.INVOICENO, " //14
                + "g.RRN, " //15
                + "g.WALLETID, " //16       
                + "to_char(g.CREATETIME,'yyyy-mm-dd hh24:mi:ss') , " //17
                + "g.APPROVECODE, " //18
                + "g.TOMOBILE, " //19
                + "g.TOKEN_ID, " //20
                + "g.TO_EMAIL, " //21
                + "s3.DESCRIPTION kk, " //22
                + "g.TOWALLET, " //23
                + "g.PROMO_AMOUNT, " //24
                + "g.COMM_AMOUNT, " //25
                + "g.DEDUCT_AMOUNT, " //26
                + "ct.DESCRIPTION cardt, " //27
                + "cb.DESCRIPTION cardb, " //28
                + "row_number() over (" + this.TXN_ORDER_BY_SQL + ") as r " //29

                + "from SBANK_TRANSACTION g "
                + "left outer join status s2 on s2.STATUSCODE = g.STATUS "
                + "left outer join status s3 on s3.STATUSCODE = g.APPROVAL_STATUS "
                + "left outer join transactiontype tt on tt.TYPECODE = g.TXNTYPECODE "
                + "left outer join currency cc on cc.CURRENCYCODE = g.CURRENCYCODE "
                //            + "left outer join MS_CUSTOMER_WALLET cw on cw.WALLET_ID = g.WALLETID "
                + "left outer join RESPONSE_CODES rc on rc.CODE = g.RESPONCECODE "
                + "left outer join CARD_TYPE ct on ct.CODE = g.CARD_TYPE "
                + "left outer join CARD_BRAND cb on cb.CODE = g.CARD_BRAND "
                + "where ";

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

                String sql = TXN_SQL_EXCEL + where1;
                System.err.println("------------");
                System.out.println(sql);
                System.err.println("------------");
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
                            TxnDataBean dataBean = new TxnDataBean();

                            try {

                                if (objArr[0] == null) {
                                    dataBean.setTxnid("--");
                                } else {
                                    dataBean.setTxnid(objArr[0].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setTxnid("--");
                            }

                            try {
                                if (objArr[1] == null) {
                                    dataBean.setMaskedcard("--");
                                } else {
                                    dataBean.setMaskedcard(objArr[1].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setMaskedcard("--");
                            }

                            try {
                                if (objArr[2] == null) {
                                    dataBean.setTomaskedcard("--");
                                } else {
                                    dataBean.setTomaskedcard(objArr[2].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setTomaskedcard("--");
                            }

                            try {
                                if (objArr[3] == null) {
                                    dataBean.setProcessingcode("--");
                                } else {
                                    dataBean.setProcessingcode(objArr[3].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setProcessingcode("--");
                            }

                            try {
                                if (objArr[4] == null) {
                                    dataBean.setMid("--");
                                } else {
                                    dataBean.setMid(objArr[4].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setMid("--");
                            }
                            try {
                                if (objArr[5] == null) {
                                    dataBean.setTid("--");
                                } else {
                                    dataBean.setTid(objArr[5].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setTid("--");
                            }
                            try {
                                if (objArr[6] == null) {
                                    dataBean.setTxntypecode("--");
                                } else {
                                    dataBean.setTxntypecode(objArr[6].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setTxntypecode("--");
                            }

                            try {
                                if (objArr[7] == null) {
                                    dataBean.setListeneruid("--");
                                } else {
                                    dataBean.setListeneruid(objArr[7].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setListeneruid("--");
                            }

                            try {
                                if (objArr[8] == null) {
                                    dataBean.setTraceno("--");
                                } else {
                                    dataBean.setTraceno(objArr[8].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setTraceno("--");
                            }
                            try {
                                if (objArr[9] == null) {
                                    dataBean.setCurrency("--");
                                } else {
                                    dataBean.setCurrency(objArr[9].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setCurrency("--");
                            }

                            try {
                                if (objArr[10] == null) {
                                    dataBean.setAmount("--");
                                } else {
//                                    dataBean.setAmount(Common.toCurrencyFormat(objArr[10].toString()));

                                    double d = Double.parseDouble(objArr[10].toString());
                                    dataBean.setAmount(Common.toCurrencyFormat(String.format("%.2f", new BigDecimal(d))));
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setAmount("--");
                            }

                            try {
                                if (objArr[11] == null) {
                                    dataBean.setStatucode("--");
                                } else {
                                    dataBean.setStatucode(objArr[11].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setStatucode("--");
                            }
                            try {
                                if (objArr[12] == null) {
                                    dataBean.setRescode("--");
                                } else {
                                    dataBean.setRescode(objArr[12].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setRescode("--");
                            }
                            try {
                                if (objArr[13] == null) {
                                    dataBean.setMobile("--");
                                } else {
                                    dataBean.setMobile(objArr[13].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setMobile("--");
                            }

                            try {
                                if (objArr[14] == null) {
                                    dataBean.setInvoiceno("--");
                                } else {
                                    dataBean.setInvoiceno(objArr[14].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setInvoiceno("--");
                            }
                            try {

                                if (objArr[15] == null) {
                                    dataBean.setRrn("--");
                                } else {
                                    dataBean.setRrn(objArr[15].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setRrn("--");
                            }

                            try {

                                if (objArr[16] == null) {
                                    dataBean.setWalletid("--");
                                } else {
                                    dataBean.setWalletid(objArr[16].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setWalletid("--");
                            }

                            try {
                                if (objArr[17] == null) {
                                    dataBean.setCreatedtime("--");
                                } else {
                                    dataBean.setCreatedtime(objArr[17].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setCreatedtime("--");
                            }

                            try {
                                if (objArr[18] == null) {
                                    dataBean.setApprovecode("--");
                                } else {
                                    dataBean.setApprovecode(objArr[18].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setApprovecode("--");
                            }

                            try {
                                if (objArr[19] == null) {
                                    dataBean.setTomobile("--");
                                } else {
                                    dataBean.setTomobile(objArr[19].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setTomobile("--");
                            }
                            try {
                                if (objArr[20] == null) {
                                    dataBean.setTokenid("--");
                                } else {
                                    dataBean.setTokenid(objArr[20].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setTokenid("--");
                            }

                            try {
                                if (objArr[21] == null) {
                                    dataBean.setToemail("--");
                                } else {
                                    dataBean.setToemail(objArr[21].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setToemail("--");
                            }

                            try {
                                if (objArr[22] == null) {
                                    dataBean.setApprovalstatus("--");
                                } else {
                                    dataBean.setApprovalstatus(objArr[22].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setApprovalstatus("--");
                            }
                            try {
                                if (objArr[23] == null) {
                                    dataBean.setTowallet("--");
                                } else {
                                    dataBean.setTowallet(objArr[23].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setTowallet("--");
                            }

                            try {
                                if (objArr[24] == null) {
                                    dataBean.setPromoAmount("--");
                                } else {
//                                    dataBean.setPromoAmount(Common.toCurrencyFormat(objArr[24].toString()));

                                    double d = Double.parseDouble(objArr[24].toString());
                                    dataBean.setPromoAmount(Common.toCurrencyFormat(String.format("%.2f", new BigDecimal(d))));
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setPromoAmount("--");
                            }
                            try {
                                if (objArr[25] == null) {
                                    dataBean.setCommissionAmount("--");
                                } else {
//                                    dataBean.setCommissionAmount(Common.toCurrencyFormat(objArr[25].toString()));

                                    double d = Double.parseDouble(objArr[25].toString());
                                    dataBean.setCommissionAmount(Common.toCurrencyFormat(String.format("%.2f", new BigDecimal(d))));

                                }
                            } catch (NullPointerException npe) {
                                dataBean.setCommissionAmount("--");
                            }
                            try {
                                if (objArr[26] == null) {
                                    dataBean.setDeductAmount("--");
                                } else {
//                                    dataBean.setDeductAmount(Common.toCurrencyFormat(objArr[26].toString()));

                                    double d = Double.parseDouble(objArr[26].toString());
                                    dataBean.setDeductAmount(Common.toCurrencyFormat(String.format("%.2f", new BigDecimal(d))));

                                }
                            } catch (NullPointerException npe) {
                                dataBean.setDeductAmount("--");
                            }

                            try {
                                if (objArr[27] == null) {
                                    dataBean.setCardType("--");
                                } else {
                                    dataBean.setCardType(objArr[27].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setCardType("--");
                            }

                            try {
                                if (objArr[28] == null) {
                                    dataBean.setCardBrand("--");
                                } else {
                                    dataBean.setCardBrand(objArr[28].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setCardBrand("--");
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

    private String makeWhereClauseForExcel(TxnExplorerInputBean inputBean) throws ParseException {
        String where = "1=1";

        if (inputBean.getTransactionID() != null && !inputBean.getTransactionID().isEmpty()) {
            where += " and lower(g.TRANSACTIONID) LIKE lower('%" + inputBean.getTransactionID() + "%')";
        }
        if (inputBean.getListeneruid() != null && !inputBean.getListeneruid().isEmpty()) {
            where += " and lower(g.LISTNERUID) LIKE lower('%" + inputBean.getListeneruid() + "%')";
        }
        if (inputBean.getInvoiceno() != null && !inputBean.getInvoiceno().isEmpty()) {
            where += " and lower(g.INVOICENO) LIKE lower('%" + inputBean.getInvoiceno() + "%')";
        }
        if (inputBean.getCardNo() != null && !inputBean.getCardNo().isEmpty()) {
            where += " and lower(g.MASKING_CARD) LIKE lower('%" + inputBean.getCardNo() + "%')";
        }
        if (inputBean.getProcessingcode() != null && !inputBean.getProcessingcode().isEmpty()) {
            where += " and lower(g.PROCESSINGCODE) LIKE lower('%" + inputBean.getProcessingcode() + "%')";
        }
        if (inputBean.getMid() != null && !inputBean.getMid().isEmpty()) {
            where += " and lower(g.MID) LIKE lower('%" + inputBean.getMid() + "%')";
        }
        if (inputBean.getTid() != null && !inputBean.getTid().isEmpty()) {
            where += " and lower(g.TID) LIKE lower('%" + inputBean.getTid() + "%')";
        }
        if (inputBean.getTxnTypeCode() != null && !inputBean.getTxnTypeCode().isEmpty()) {
            where += " and lower(g.TXNTYPECODE) LIKE lower('%" + inputBean.getTxnTypeCode() + "%')";
        }
        if (inputBean.getTraceNo() != null && !inputBean.getTraceNo().isEmpty()) {
            where += " and lower(g.TRACENUMBER) LIKE lower('%" + inputBean.getTraceNo() + "%')";
        }
        if (inputBean.getCurrency() != null && !inputBean.getCurrency().isEmpty()) {
            where += " and g.CURRENCYCODE LIKE '%" + inputBean.getCurrency() + "%'";
        }
        if (inputBean.getAmount() != null && !inputBean.getAmount().isEmpty()) {

            String amount = Common.toCurrencyFormatForSQL(inputBean.getAmount());
            where += " and g.AMOUNT = '" + amount + "'";

        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().isEmpty()) {
            if (inputBean.getStatus().equals("TCMP")) {
                where += " and g.STATUS IN('TCMP','TXNS')";
                System.err.println(inputBean.getStatus());
            } else {
                where += " and g.STATUS LIKE '%" + inputBean.getStatus() + "%'";
            }
        }
        if (inputBean.getResCode() != null && !inputBean.getResCode().isEmpty()) {
            where += " and g.RESPONCECODE LIKE '%" + inputBean.getResCode() + "%'";
        }
        if (inputBean.getMobileNo() != null && !inputBean.getMobileNo().isEmpty()) {
            where += " and lower(g.MOBILENUMBER) LIKE lower('%" + inputBean.getMobileNo() + "%')";
        }
        if (inputBean.getToMobileNo() != null && !inputBean.getToMobileNo().isEmpty()) {
            where += " and lower(g.TOMOBILE) LIKE lower('%" + inputBean.getToMobileNo() + "%')";
        }
        if (inputBean.getRrn() != null && !inputBean.getRrn().isEmpty()) {
            where += " and lower(g.RRN) LIKE lower('%" + inputBean.getRrn() + "%')";
        }

        if (inputBean.getTocardno() != null && !inputBean.getTocardno().isEmpty()) {
            where += " and lower(g.TO_MASKING_CARD) LIKE lower('%" + inputBean.getTocardno().trim() + "%')";
        }
        if (inputBean.getWalletid() != null && !inputBean.getWalletid().isEmpty()) {
            where += " and (lower(g.WALLETID) LIKE lower('%" + inputBean.getWalletid() + "%')"
                    + " or lower(g.TOWALLET) LIKE lower('%" + inputBean.getWalletid() + "%'))";
        }

        if (inputBean.getTokenid() != null && !inputBean.getTokenid().isEmpty()) {
            where += " and lower(g.TOKEN_ID) LIKE lower('%" + inputBean.getTokenid() + "%')";
        }
        if (inputBean.getToEmail() != null && !inputBean.getToEmail().isEmpty()) {
            where += " and lower(g.TO_EMAIL) LIKE lower('%" + inputBean.getToEmail() + "%')";
        }
        if (inputBean.getApprovalstatus() != null && !inputBean.getApprovalstatus().isEmpty()) {

            where += " and g.APPROVAL_STATUS LIKE '%" + inputBean.getApprovalstatus() + "%'";

        }

        DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        String inputFromDate = inputBean.getFromdate();
        Date dateFromDate = parser.parse(inputFromDate);
        String outputFromDate = formatter.format(dateFromDate);

        if (inputBean.getFromdate() != null && !inputBean.getFromdate().isEmpty()) {
            where += " and g.CREATETIME >='" + outputFromDate + "'";
        }

        String date = inputBean.getTodate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(inputBean.getTodate()));
        c.add(Calendar.DATE, 1);  // number of days to add
        date = sdf.format(c.getTime());

        String inputtoDate = date;
        Date dateToDate = parser.parse(inputtoDate);
        String outputToDate = formatter.format(dateToDate);

        if (inputBean.getTodate() != null && !inputBean.getTodate().isEmpty()) {
            where += " and g.CREATETIME <'" + outputToDate + "'";
        }

        return where;
    }

    private SXSSFWorkbook createExcelTopSection(TxnExplorerInputBean inputBean) throws Exception {

        CommonDAO dao = new CommonDAO();

        SXSSFWorkbook workbook = new SXSSFWorkbook(-1);
        Sheet sheet = workbook.createSheet("TransactionExplorer_Report");

        CellStyle fontBoldedUnderlinedCell = ExcelCommon.getFontBoldedUnderlinedCell(workbook);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("FriMi - Digital Life Style Solution");
        cell.setCellStyle(fontBoldedUnderlinedCell);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("Transaction Explorer Report");
        cell.setCellStyle(fontBoldedUnderlinedCell);
//-----------------------------------------------------------------
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

        cell = row.createCell(6);
        cell.setCellValue("Transaction ID");
        cell = row.createCell(7);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getTransactionID()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));
//-------------------------------------------------------------------------
        row = sheet.createRow(5);
        cell = row.createCell(0);
        cell.setCellValue("Wallet ID");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getWalletid()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("From Card No");
        cell = row.createCell(4);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getCardNo()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(6);
        cell.setCellValue("To Card No");
        cell = row.createCell(7);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getTocardno()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

//-------------------------------------------------------------------------------
        row = sheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("From Mobile No");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getMobileNo()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("To Mobile No");
        cell = row.createCell(4);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getToMobileNo()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(6);
        cell.setCellValue("To Email");
        cell = row.createCell(7);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getToEmail()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

//-----------------------------------------------------------------------------------------
        row = sheet.createRow(7);

        cell = row.createCell(0);
        cell.setCellValue("MID");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getMid()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("TID");
        cell = row.createCell(4);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getTid()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(6);
        cell.setCellValue("Transaction Type");
        cell = row.createCell(7);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getTxnTypeCode()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

//-----------------------------------------------------------------------------------------
        row = sheet.createRow(8);

        cell = row.createCell(0);
        cell.setCellValue("Trace No");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getTraceNo()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("Amount");
        cell = row.createCell(4);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(Common.toCurrencyFormat(inputBean.getAmount())));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(6);
        cell.setCellValue("Status");
        cell = row.createCell(7);
        String status = "";
        try {
            status = dao.getStatusByprefix(inputBean.getStatus().toString());
        } catch (NullPointerException ex) {
            status = "-ALL-";
        }
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(status));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

//-----------------------------------------------------------------------------------------------
        row = sheet.createRow(9);

        cell = row.createCell(0);
        cell.setCellValue("Approval Status");
        cell = row.createCell(1);
        String appstatus = "";
        try {
            appstatus = dao.getStatusByprefix(inputBean.getApprovalstatus().toString());
        } catch (NullPointerException ex) {
            appstatus = "-ALL-";
        }
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(appstatus));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("RRN");
        cell = row.createCell(4);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getRrn()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(6);
        cell.setCellValue("Response");
        cell = row.createCell(7);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getResCode()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

//----------------------------------------------------------------------------------------  
        row = sheet.createRow(10);

        cell = row.createCell(0);
        cell.setCellValue("Invoice Number");
        cell = row.createCell(1);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getInvoiceno()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("Processing Code");
        cell = row.createCell(4);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getProcessingcode()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(6);
        cell.setCellValue("Token ID");
        cell = row.createCell(7);
        cell.setCellValue(Common.replaceEmptyorNullStringToALL(inputBean.getTokenid()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

//----------------------------------------------------------------------------------------    
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
        cell.setCellValue("Transaction ID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(2);
        cell.setCellValue("Wallet ID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(3);
        cell.setCellValue("To Wallet ID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(4);
        cell.setCellValue("From Card Number");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(5);
        cell.setCellValue("To Card Number");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(6);
        cell.setCellValue("From Mobile");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(7);
        cell.setCellValue("To Mobile");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(8);
        cell.setCellValue("To Email");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(9);
        cell.setCellValue("MID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(10);
        cell.setCellValue("TID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(11);
        cell.setCellValue("Transaction Type");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(12);
        cell.setCellValue("Trace No");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(13);
        cell.setCellValue("Card Brand");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(14);
        cell.setCellValue("Card Type");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(15);
        cell.setCellValue("Currency");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(16);
        cell.setCellValue("Amount");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(17);
        cell.setCellValue("Promotion Amount");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(18);
        cell.setCellValue("Commission Amount");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(19);
        cell.setCellValue("Deduct Amount");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(20);
        cell.setCellValue("Status");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(21);
        cell.setCellValue("Approval Status");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(22);
        cell.setCellValue("Response");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(23);
        cell.setCellValue("RRN");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(24);
        cell.setCellValue("Token ID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(25);
        cell.setCellValue("Invoice No");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(26);
        cell.setCellValue("Processing Code");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(27);
        cell.setCellValue("Approve Code");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(28);
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
                file = new File(directory + File.separator + "Login History Report_" + fileCount + ".xlsx");
            } else {
                file = new File(directory + File.separator + "Login History Report.xlsx");
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

    private int createExcelTableBodySection(SXSSFWorkbook workbook, TxnDataBean dataBean, int currrow, int rownumber) throws Exception {
        Sheet sheet = workbook.getSheetAt(0);
        CellStyle rowColumnCell = ExcelCommon.getRowColumnCell(workbook);
        Row row = sheet.createRow(currrow++);

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);

        Cell cell = row.createCell(0);
        cell.setCellValue(rownumber);
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(dataBean.getTxnid());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(2);
        cell.setCellValue(dataBean.getWalletid());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(3);
        cell.setCellValue(dataBean.getTowallet());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(4);
        cell.setCellValue(dataBean.getMaskedcard());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(5);
        cell.setCellValue(dataBean.getTomaskedcard());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(6);
        cell.setCellValue(dataBean.getMobile());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(7);
        cell.setCellValue(dataBean.getTomobile());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(8);
        cell.setCellValue(dataBean.getToemail());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(9);
        cell.setCellValue(dataBean.getMid());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(10);
        cell.setCellValue(dataBean.getTid());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(11);
        cell.setCellValue(dataBean.getTxntypecode());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(12);
        cell.setCellValue(dataBean.getTraceno());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(13);
        cell.setCellValue(dataBean.getCardBrand());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(14);
        cell.setCellValue(dataBean.getCardType());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(15);
        cell.setCellValue(dataBean.getCurrency());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(16);
        cell.setCellValue(dataBean.getAmount());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(17);
        cell.setCellValue(dataBean.getPromoAmount());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(18);
        cell.setCellValue(dataBean.getCommissionAmount());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(19);
        cell.setCellValue(dataBean.getDeductAmount());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(20);
        cell.setCellValue(dataBean.getStatucode());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(21);
        cell.setCellValue(dataBean.getApprovalstatus());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(22);
        cell.setCellValue(dataBean.getRescode());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(23);
        cell.setCellValue(dataBean.getRrn());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(24);
        cell.setCellValue(dataBean.getTokenid());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(25);
        cell.setCellValue(dataBean.getInvoiceno());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(26);
        cell.setCellValue(dataBean.getProcessingcode());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(27);
        cell.setCellValue(dataBean.getApprovecode());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(28);
        cell.setCellValue(dataBean.getCreatedtime());
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
        if (date != null && !date.toString().isEmpty()) {
            cell.setCellValue(date.toString().substring(0, 19));
        } else {
            cell.setCellValue("--");
        }
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));
    }

}
