/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.util.varlist;

import java.util.HashMap;

/**
 *
 * @author chanuka
 */
public class CommonVarList {

    public static final int LOGIN_ATTEMPTS = 3;
    public static final String LEVEL_01 = "1";
    public static final String STATUS_ACTIVE = "ACT";
    public static final String STATUS_BLOCK = "BLWL";
    public static final String STATUS_CUS_WAL_ACTIVE = "CWA";
    public static final String STATUS_CUS_WAL_DEACTIVE = "CWD";
    public static final String STATUS_CUS_WAL_CUS_DELETED = "CWDL";
    public static final String STATUS_YES = "YES";
    public static final String STATUS_NO = "NO";
    public static final String STATUS_INITIAL = "INIT";
    public static final String STATUS_INITIAL_DES = "Initial";
    public static final String STATUS_CONFIRMED = "CONF";
    public static final String STATUS_SENT = "SENT";
    public static final String STATUS_SENT_TO_QUEUE = "QUED";
    public static final String STATUS_CATEGORY_GENERAL = "DFLT";
    public static final String STATUS_CATEGORY_LOGIN_HISTORY = "LOGN";
    public static final String STATUS_CATEGORY_CUS_WALLET = "CWS";
    public static final String STATUS_CATEGORY_OTP_REQUEST_INITIATE = "SER";
    public static final String STATUS_CATEGORY_SYSUSER = "SYUS";
    public static final String STATUS_CATEGORY_AUTH = "AUTH";
    public static final String STATUS_CATEGORY_PUSH_NOTIFICATION = "PUSH";
    public static final String STATUS_CATEGORY_PEND = "PEND";
    public static final String STATUS_DEACTIVE = "DEACT";
    public static final String STATUS_TRANSACTION = "TRAN";
    public static final String STATUS_DELETE = "DEL";
    public static final String SMS_BANKING_APPTYPE = "SMSB";
    public static final String TRANSACTION_TYPE = "RDCD";
    public static final String TRANSACTION_STATUS = "TXNS";
    public static final String STATUS_CAT_CONFIRMED = "CONF";
    public static final String SEGMENT_ALERT_MANAGEMENT = "Segment Alert Management";
    public static final String PERSONAL_ALERT_MANAGEMENT = "Personal Alert Management";
    public static final String SPECIAL_ALERT_MANAGEMENT = "Special Alert Management";
    public static final String STAFF_ALERT_MANAGEMENT = "Staff Alert Management";
    public static final String CORPORATE = "Corporate";
    public static final String NON_CORPORATE = "NonCorporate";
    public static final String SPECIAL_ALERT = "Special";
    public static final String STAFF_ALERT = "Staff";
    public static final String PERSONAL_ALERT = "Personal";
    public static final String SEGMENT_ALERT = "Segment";
    public static final String REPORT_SBANK_ADD_HEADER = "Sri Lanka";
    public static final String REPORT_SBANK_ADD = "242 Union Place Colombo 2, Colombo 00200";
    public static final String REPORT_SBANK_TEL = "+94(0)114711411";
    public static final String REPORT_SBANK_MAIL = "customerservice@nationstrust.com ";
    public static final String JNDI_REPORT_CONNECTION = "RDB_JNDI";
    public static HashMap<String, String> SMSMESSAGESMAP = new HashMap<String, String>();
    public static final String IMAGE_URL = "IMGURL";
    public static final String STATUS_REJECT = "CREJ";
    public static final String STATUS_APPROVED = "CAPR";
    public static final String STATUS_CUS_INITIAL = "CINI";
    public static final String STATUS_CUS_PIN_RESET = "CPIN";
//    public static final String STATUS_CUS_FORZEN = "CFRO";//Frozen
    public static final String STATUS_CUS_CREDIT_FREEZE = "CCFZ";
    public static final String STATUS_CUS_DEBIT_FREEZE = "CDFZ";
    public static final String STATUS_CUS_CLOSED = "CLSD";
    public static final String STATUS_CUS_HOLD = "CHLD";
    public static final String STATUS_CUS_WORK_IN_PROGRESS = "CWIP";
    public static final String STATUS_CUS_PUSH_NOTIFICATION_INITIAL = "PNINI";
    public static final String STATUS_CUS_PUSH_NOTIFICATION_SEND = "PNSD";
    public static final String STATUS_PENDING_TASK_REJECT = "PREJ";
    public static final String STATUS_PENDING_TASK_APPROVED = "PAPR";
//    public static final String STATUS_CUS_PEND = "PEND";
    public static final String USER_LEVEL_1 = "L1";
    public static final String USER_LEVEL_3 = "L3";
    public static final String ACQUIRER_RISKPROFILE_TYPE_MERCHANT = "MERCHANT";
    public static final String ACQUIRER_RISKPROFILE_TYPE_TERMINAL = "TERMINAL";
    public static final String SYS_MERCHANT = "MERCH";
    public static final String CURR_SERIES = "CURR_SERIES";
    public static final String CUS_WALLET_MGT_TXN_AUTH_TYPE_1 = "Wallet PIN";
    public static final String CUS_WALLET_MGT_TXN_AUTH_TYPE_2 = "Face Recognition";
    public static final String CUS_WALLET_MGT_TXN_AUTH_TYPE_3 = "Finger Print";
    public static final String CUS_WALLET_MGT_TXN_AUTH_TYPE_4 = "Voice Recognition";
    public static final String CURRENCY_SL_CODE = "144";
    public static final String USER_ROLE_MERCHANT = "MERCH";
    public static final String USER_ROLE_MERCHANT_CUSTOMER = "MERCUS";
    public static final String MSERVER_IP = "MSERVER_IP";
    public static final String MSERVER_PORT = "MSERVER_PORT";
    public static final String TXN_TYPE_LIMIT_CSV_DAILY = "DAILY";
    public static final String TXN_TYPE_LIMIT_CSV_MAX = "MAX";
    public static final String WALLETMGT_LIMIT_CATEGORY_PERMANENT = "Permanent";
    public static final String WALLETMGT_LIMIT_CATEGORY_TEMPORARY = "Temporary";
    public static final String WALLETMGT_LIMIT_TYPE_DAILY = "Daily Limit";
    public static final String WALLETMGT_LIMIT_TYPE_TXNMAX = "Transaction Max Limit";
    public static final String WSDL_SBANK_URL = "WSDL_SBANK";
    public static final String MSERVER_RESPONSE_SUCCESS_CODE = "M00";
    public static final String STATUS_TXN_APR_APPROVE = "CAPR";
    public static final String STATUS_TXN_APR_REJECT = "CREJ";
    public static final String STATUS_TXN_APR_PEND = "PEND";
    public static final String STATUS_TXN_APR_TXTO = "TXTO";

    public static final String STATUS_DUALAUTH_PEND = "PEND";

    public static final String MSERVER_STATUS_TYPE_L3_UPGRADE = "28";
    public static final String MSERVER_STATUS_TYPE_L3_DECLINE = "34";
    public static final String MSERVER_STATUS_TYPE_ACTIVE = "35";
    public static final String MSERVER_STATUS_TYPE_DEACTIVE = "36";
    public static final String MSERVER_STATUS_TYPE_CLOSE = "37";
    public static final String MSERVER_STATUS_TYPE_DEBIT_FREEZ = "38";
    public static final String MSERVER_STATUS_TYPE_CREDIT_FREEZ = "39";

    public static final String MOB_MSG_CATEGORY = "PUSH";

    public static final String EFTC_TRUE = "true";

    public static final String PAGE_TYPE_CUSTOMER = "CUSTOMER";
    public static final String PAGE_TYPE_USER = "USER";
    public static final String PAGE_TYPE_PRIVILEGE = "PRIVILEGE";

    public static final String MAPI_EMAIL_BODY = "MAPI_EMAIL_BODY";
    public static final String MAPI_EMAIL_SUBJ = "MAPI_EMAIL_SUBJ";
    public static final String MAPI_EMAIL_LIST = "MAPI_EMAIL_LIST";

}
