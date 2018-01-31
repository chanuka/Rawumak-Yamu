/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.common;

import com.rawmakyamu.bean.controlpanel.systemconfig.TermsVersionBean;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.Currency;
import com.rawmakyamu.util.mapping.Loginhistory;
import com.rawmakyamu.util.mapping.Logintype;
import com.rawmakyamu.util.mapping.MsCustomerWalletPushFile;
import com.rawmakyamu.util.mapping.Page;
import com.rawmakyamu.util.mapping.Promotionuserlevel;
import com.rawmakyamu.util.mapping.Section;
import com.rawmakyamu.util.mapping.Sectionpage;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Systemuser;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.mapping.Transactiontype;
import com.rawmakyamu.util.mapping.Userlevel;
import com.rawmakyamu.util.mapping.Userrole;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author chanuka
 */
public class CommonDAO {

    public String compareDates(String d1, String d2) {
        String msg = "";
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);

            if (date1.after(date2)) {
                msg = "To-date should be greater than From-date";
            }

            if (date1.before(date2)) {
                msg = "";
            }

            if (date1.equals(date2)) {
                msg = "From-date is equal to To-date";
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return msg;
    }

    public static Date getSystemDate(Session session) throws Exception {
        Date sysDateTime = null;
        try {

            String sql = "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') as a from dual";
            Query query = session.createSQLQuery(sql);
            List l = query.list();
            String stime = (String) l.get(0);
            sysDateTime = Timestamp.valueOf(stime);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
        }
        return sysDateTime;
    }

    public static Date getSystemDateLogin() throws Exception {
        Date sysDateTime = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "select to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss') from dual";
            Query query = session.createSQLQuery(sql);
            List l = query.list();
            String stime = (String) l.get(0);
            sysDateTime = Timestamp.valueOf(stime);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return sysDateTime;
    }

    // get page description
    public Page getPageDescription(String pageCode) throws Exception {

        Page pageBean = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Page as s where s.pagecode =:pagecode";
            Query query = session.createQuery(sql).setString("pagecode", pageCode);
            pageBean = (Page) query.list().get(0);

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
        return pageBean;
    }

    public Section getSectionOfPage(String pageCode, String userRole)
            throws Exception {

        Section sectionBean = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select s.section from Sectionpage as s where s.id.pagecode =:pagecode and s.id.userrolecode=:userrolecode ";
            Query query = session.createQuery(sql)
                    .setString("pagecode", pageCode)
                    .setString("userrolecode", userRole);
            sectionBean = (Section) query.list().get(0);

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
        return sectionBean;
    }

    // get status list
    public List<Status> getDefultStatusList(String statusCode)
            throws Exception {

        List<Status> statusList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Status as s where s.statuscategory.categorycode=:statuscategorycode order by Upper(s.description) asc";
            Query query = session.createQuery(sql).setString(
                    "statuscategorycode", statusCode);
            statusList = query.list();
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
        return statusList;
    }

    public List<Status> getPushNofiticationStatusList(String statusCode)
            throws Exception {

        List<Status> statusList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Status as s where s.statuscategory.categorycode=:statuscategorycode order by Upper(s.description) asc";
            Query query = session.createQuery(sql).setString(
                    "statuscategorycode", statusCode);
            statusList = query.list();
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
        return statusList;
    }

    public List<Promotionuserlevel> getUserLevelPromotion()
            throws Exception {

        List<Promotionuserlevel> statusList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
//            String sql = "from Promotionuserlevel as s order by Upper(s.description) asc";
            String sql = "from Promotionuserlevel as s ";
            Query query = session.createQuery(sql);
            statusList = query.list();
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
        return statusList;
    }

    // get status list
    public String getSectionByRoleAndPage(String userrole, String page)
            throws Exception {

        String section = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Sectionpage as s where s.id.userrolecode=:userrolecode and s.id.pagecode=:pagecode";
            Query query = session.createQuery(sql).setString(
                    "userrolecode", userrole).setString(
                            "pagecode", page);

            Sectionpage Sectionpage = (Sectionpage) query.list().get(0);
            section = Sectionpage.getId().getSectioncode();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return section;
    }

    public List<Logintype> getDefultLogintypeList()
            throws Exception {

        List<Logintype> statusList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Logintype as s order by Upper(s.description) asc";
            Query query = session.createQuery(sql);
            statusList = query.list();
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
        return statusList;
    }

    public List<Status> getDefultStatusList2(String statusCode)
            throws Exception {

        List<Status> statusList = null;
        Session session = null;
        Status st = new Status();
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Status as s where s.statuscategory.categorycode=:statuscategorycode order by Upper(s.description) asc";
            Query query = session.createQuery(sql).setString(
                    "statuscategorycode", statusCode);
            statusList = query.list();
            st.setStatuscode("CWDL");
            st.setDescription("Customer Wallet deleted");
            statusList.add(st);

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
        return statusList;
    }

    public List<Currency> getCurrencyList2()
            throws Exception {

        List<Currency> statusList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Currency as s order by Upper(s.description) asc";
            Query query = session.createQuery(sql);
            statusList = query.list();

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
        return statusList;
    }

    public List<Transactiontype> getTxnTypeCodeList()
            throws Exception {

        List<Transactiontype> txnType = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Transactiontype as s order by Upper(s.description) asc";
            Query query = session.createQuery(sql);
            txnType = query.list();

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
        return txnType;
    }

    public List<Transactiontype> getTxnTypeActCodeList()
            throws Exception {

        List<Transactiontype> txnType = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Transactiontype as s where s.status.statuscode=:status order by s.description asc order by Upper(s.description) asc";
            Query query = session.createQuery(sql).setString("status", CommonVarList.STATUS_ACTIVE);
            txnType = query.list();

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
        return txnType;
    }

    // get user role
    public List<Userrole> getUserRoleList(String statusCode)
            throws Exception {

        List<Userrole> userroleList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Userrole as s where s.status.statuscode =:statuscode and s.userrolecode!='MERCH' and s.userrolecode!='MERCUS' order by Upper(s.description) asc";
            Query query = session.createQuery(sql).setString("statuscode", statusCode);
            userroleList = query.list();

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
        return userroleList;
    }

    public List<Currency> getCurrencyList() throws Exception {

        List<Currency> currencyList = new ArrayList<Currency>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Currency as s order by Upper(s.description) asc";
            Query query = session.createQuery(sql);
            currencyList = query.list();

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
        return currencyList;
    }

    public List<Transactiontype> gettranstypeList() throws Exception {

        List<Transactiontype> currencyList = new ArrayList<Transactiontype>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Transactiontype as s order by Upper(s.description) asc";
            Query query = session.createQuery(sql);
            currencyList = query.list();

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
        return currencyList;
    }

    // get txn type list
    public List<Transactiontype> getTxnTypeList(String statusCode)
            throws Exception {

        List<Transactiontype> txnTypeList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
//            String sql = "from Transactiontype as s where s.riskRequired =:status";
            String sql = "from Transactiontype as s order by Upper(s.description) asc";
//            Query query = session.createQuery(sql).setString("status", statusCode);
            Query query = session.createQuery(sql);
            txnTypeList = query.list();

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
        return txnTypeList;
    }

    public List<Transactiontype> getActiveTxnTypeList(String statusCode)
            throws Exception {

        List<Transactiontype> txnTypeList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
//            String sql = "from Transactiontype as s where s.riskRequired =:status";
            String sql = "from Transactiontype as s where s.status.statuscode=:code order by Upper(s.description) asc";
//            Query query = session.createQuery(sql).setString("status", statusCode);
            Query query = session.createQuery(sql).setString("code", statusCode);
            txnTypeList = query.list();

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
        return txnTypeList;
    }

    public List<Userrole> getUserRoleList() throws Exception {

        List<Userrole> userroleList = new ArrayList<Userrole>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Userrole as s order by Upper(s.description) asc";
            Query query = session.createQuery(sql);
            userroleList = query.list();

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
        return userroleList;
    }

    public List<Userrole> getUserRoleListByStatus(String status) throws Exception {

        List<Userrole> userroleList = new ArrayList<Userrole>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Userrole as u where u.status =:status order by Upper(u.description) asc";
            Query query = session.createQuery(sql).setString("status", status);
            userroleList = query.list();

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
        return userroleList;
    }

    public List<Userrole> getUserRoleListByStatus(String status, String userrolecode) throws Exception {

        List<Userrole> userroleList = new ArrayList<Userrole>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Userrole as u where u.status =:status and u.userrolecode!='MERCH' and u.userrolecode!='MERCUS' order by Upper(u.description) asc";
            Query query = session.createQuery(sql).setString("status", status);
            userroleList = query.list();

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
        return userroleList;
    }

    public List<Userrole> getALLUserList()
            throws Exception {

        List<Userrole> userRoleList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Userrole as s order by Upper(s.description) asc";
            Query query = session.createQuery(sql);
            userRoleList = query.list();

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
        return userRoleList;
    }

    public List<Userrole> getALLUserList(String userrolecode)
            throws Exception {

        List<Userrole> userRoleList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Userrole as s where s.userrolecode !=:userrolecode order by Upper(s.description) asc";
            Query query = session.createQuery(sql).setString("userrolecode", userrolecode);
            userRoleList = query.list();

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
        return userRoleList;
    }

    //get userlevellist
    public List<Userlevel> getUserLevelList() throws Exception {
        List<Userlevel> userLevelList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Userlevel as u order by Upper(u.description) asc";
            Query query = session.createQuery(sql);
            userLevelList = query.list();
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
        return userLevelList;
    }

    public List<Systemuser> getUserList() throws Exception {
        // TODO Auto-generated method stub

        List<Systemuser> userRoleList = new ArrayList<Systemuser>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Systemuser u order by Upper(u.username) asc";
            Query query = session.createQuery(sql);
            userRoleList = query.list();

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
        return userRoleList;
    }

    public List<Loginhistory> getLoginHistoryList() throws Exception {
        // TODO Auto-generated method stub

        List<Loginhistory> userRoleList = new ArrayList<Loginhistory>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Loginhistory u order by u.historyid asc";
            Query query = session.createQuery(sql);
            userRoleList = query.list();

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
        return userRoleList;
    }

    public List<Section> getSectionList() throws Exception {
        List<Section> sectionList = new ArrayList<Section>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Section s order by Upper(s.description) asc ";
            Query query = session.createQuery(hql);
            sectionList = query.list();

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
        return sectionList;
    }

    public List<Page> getPageList() throws Exception {
        List<Page> pageList = new ArrayList<Page>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Page as p order by Upper(p.description) asc";
            Query query = session.createQuery(hql);
            pageList = query.list();

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
        return pageList;
    }

    public List<Task> getTaskList() throws Exception {
        List<Task> taskList = new ArrayList<Task>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from Task as t order by Upper(t.description) asc";
            Query query = session.createQuery(hql);
            taskList = query.list();

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
        return taskList;
    }

//    public List<Status> getSmsAlertStatusList(String statusCategory) throws Exception {
//        List<Status> statusList = null;
//        Session session = null;
//        try {
//            session = HibernateInit.sessionFactory.openSession();
//            session.beginTransaction();
//            Criteria criteria = session.createCriteria(Status.class);
//            criteria.add(Restrictions.eq("statuscategory.categorycode", statusCategory));
//            statusList = (List<Status>) criteria.list();
//
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            try {
//                session.flush();
//                session.close();
//            } catch (Exception e) {
//                throw e;
//            }
//        }
//        return statusList;
//    }
    // use JNDI for connection
    public static Connection getConnection() throws Exception {
        Connection con = null;
        try {
            InitialContext context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup(CommonVarList.JNDI_REPORT_CONNECTION);
            con = dataSource.getConnection();
        } catch (Exception e) {
            throw e;
        }
        return con;
    }

    public String getStatusByprefix(String srefix) throws Exception {
        Status st = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            st = (Status) session.get(Status.class, srefix);
        } catch (Exception he) {
            throw he;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return st.getDescription();
    }

    public String getSectionByprefix(String appprefix) throws Exception {
        Section sec = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            sec = (Section) session.get(Section.class, appprefix);
        } catch (Exception he) {
            throw he;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return sec.getDescription();
    }

    public String getPageByprefix(String appprefix) throws Exception {
        Page page = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            page = (Page) session.get(Page.class, appprefix);
        } catch (Exception he) {
            throw he;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return page.getDescription();
    }

    public String getUserlevelByprefix(String appprefix) throws Exception {
        Promotionuserlevel sec = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            sec = (Promotionuserlevel) session.get(Promotionuserlevel.class, appprefix);
        } catch (Exception he) {
            throw he;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return sec.getDescription();
    }

    public static String saveAudit(Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            txn = session.beginTransaction();
            audit.setCreatetime(sysDate);
            audit.setLastupdatedtime(sysDate);

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

    // get txn type list
    public List<Transactiontype> getAllTxnTypes()
            throws Exception {

        List<Transactiontype> txnTypeList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Transactiontype as s order by Upper(s.description) asc";
            Query query = session.createQuery(sql);
            txnTypeList = query.list();

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
        return txnTypeList;
    }

    public Transactiontype getTxnDescription(String txnTypeCode) throws Exception {

        Transactiontype txnType = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Transactiontype as s where s.typecode =:typecode";
            Query query = session.createQuery(sql).setString("typecode", txnTypeCode);
            txnType = (Transactiontype) query.list().get(0);

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
        return txnType;
    }

    public String getTxnCodeByDescription(String description) throws Exception {

        String txnTypeCode = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Transactiontype as s where s.description =:description";
            Query query = session.createQuery(sql).setString("description", description);
            txnTypeCode = ((Transactiontype) query.list().get(0)).getTypecode();

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
        return txnTypeCode;
    }

    public String getTaskSortKeyCount(String sortkey) throws Exception {

        String des = null;
//        boolean sortkey_valid;
        long count;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Task as s where s.sortkey =:sortkey";
            Query query = session.createQuery(sql).setString("sortkey", sortkey);
            count = query.list().size();
            if (count == 0) {
                des = "";
            } else {

                des = MessageVarList.TASK_MGT_SORTKEY_ALREADY_EXSISTS;
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
        return des;
    }

    public String getTaskSortKeyCountUpdate(String sortkey, String oldsortkey) throws Exception {

        String des = null;
//        boolean sortkey_valid;
        long count;
//        long count2;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Task as u where u.sortkey=:sortkey and u.sortkey!=:oldsortkey";
//          
            Query query = session.createQuery(sql).setString("sortkey", sortkey).setString("oldsortkey", oldsortkey);
            count = query.list().size();

            if (count == 0) {
                des = "";
            } else {

                des = MessageVarList.TASK_MGT_SORTKEY_ALREADY_EXSISTS;
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
        return des;
    }

    public String getPageSortKeyCountUpdate(String sortkey, String oldsortkey) throws Exception {

        String des = null;
//        boolean sortkey_valid;
        long count;
//        long count2;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Page as u where u.sortkey=:sortkey and u.sortkey!=:oldsortkey";
//          
            Query query = session.createQuery(sql).setString("sortkey", sortkey).setString("oldsortkey", oldsortkey);
            count = query.list().size();

            if (count == 0) {
                des = "";
            } else {

                des = MessageVarList.TASK_MGT_SORTKEY_ALREADY_EXSISTS;
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
        return des;
    }

    public String getSectionSortKeyCountUpdate(String sortkey, String oldsortkey) throws Exception {

        String des = null;
//        boolean sortkey_valid;
        long count;
//        long count2;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Section as u where u.sortkey=:sortkey and u.sortkey!=:oldsortkey";
//          
            Query query = session.createQuery(sql).setString("sortkey", sortkey).setString("oldsortkey", oldsortkey);
            count = query.list().size();

            if (count == 0) {
                des = "";
            } else {

                des = MessageVarList.SECTION_SORT_KEY_ALREADY_EXISTS;
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
        return des;
    }

    public String getPageSortKeyCount(String sortkey) throws Exception {

        String des = "";
//        boolean sortkey_valid;
        long count;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Page as s where s.sortkey =:sortkey";
            Query query = session.createQuery(sql).setString("sortkey", sortkey);
            count = query.list().size();
            if (count == 0) {
                des = "";
            } else {

                des = MessageVarList.PAGE_MGT_ERROR_SORT_KEY_ALREADY_EXSITS;
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
        return des;
    }

    public String getSectionSortKeyCount(String sortkey) throws Exception {

        String des = "";
//        boolean sortkey_valid;
        long count;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Section as s where s.sortkey =:sortkey";
            Query query = session.createQuery(sql).setString("sortkey", sortkey);
            count = query.list().size();
            if (count == 0) {
                des = "";
            } else {

                des = MessageVarList.SECTION_SORT_KEY_ALREADY_EXISTS;
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
        return des;
    }

    public String getSectionSortKeyCountForUpdate(String sortkey, String oldsortkey) throws Exception {

        String des = null;
//        boolean sortkey_valid;
        long count;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Section as s where s.sortkey =:sortkey and s.sortkey!=:oldsortkey";
            Query query = session.createQuery(sql).setString("sortkey", sortkey).setString("oldsortkey", oldsortkey);
            count = query.list().size();
            if (count == 0) {
                des = "";
            } else {

                des = MessageVarList.SECTION_SORT_KEY_ALREADY_EXISTS;
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
        return des;
    }

    public String isTaskSortKeyExsits(String sortkey) throws Exception {

        String txnTypeCode = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Task as s where s.sortkey =:sortkey";
            Query query = session.createQuery(sql).setString("sortkey", sortkey);
            txnTypeCode = ((Task) query.list().get(0)).getSortkey().toString();

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
        return txnTypeCode;
    }

    public String getCurrencyDesByCode(String code) throws Exception {

        String description = "";
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select description from CURRENCY where CURRENCYCODE=:code";
            Query query = session.createSQLQuery(sql).setParameter("code", code);
            description = (String) query.list().get(0);

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
        return description;
    }

    public List<Transactiontype> getTransactionTypeList() throws Exception {
        List<Transactiontype> transactiontypeList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Transactiontype as u order by Upper(u.description) asc";
            Query query = session.createQuery(sql);
            transactiontypeList = query.list();
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
        return transactiontypeList;
    }

    public String getDescriptionByTxnCode(String typecode) throws Exception {

        String description = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Transactiontype as s where s.typecode =:typecode";
            Query query = session.createQuery(sql).setString("typecode", typecode);
            description = ((Transactiontype) query.list().get(0)).getDescription();

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
        return description;
    }

    public String getDescriptionUsingTxnCode(Session session, String typecode) throws Exception {

        String description = null;

        try {
            String sql = "from Transactiontype as s where s.typecode =:typecode";
            Query query = session.createQuery(sql).setString("typecode", typecode);
            description = ((Transactiontype) query.list().get(0)).getDescription();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
        }
        return description;
    }

    public String getDescriptionByStatusCode(String typecode) throws Exception {

        String description = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Status as s where s.statuscode =:typecode";
            Query query = session.createQuery(sql).setString("typecode", typecode);
            description = ((Status) query.list().get(0)).getDescription();

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
        return description;
    }

    public Status getStatusByCode(String statusCode) throws Exception {

        Status status = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Status as s where s.statuscode =:statuscode ";
            Query query = session.createQuery(sql).setString("statuscode", statusCode);
            status = (Status) query.list().get(0);

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
        return status;
    }

    public String getStatusCodeByDescription(String status) throws Exception {

        String code = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select STATUSCODE from STATUS where DESCRIPTION=:description";
            Query query = session.createSQLQuery(sql).setParameter("description", status);
            code = (String) query.list().get(0);

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
        return code;
    }

    public List<TermsVersionBean> getVersionAdultList(String category) throws Exception {
        Session session = null;
        List<TermsVersionBean> userLevel = new ArrayList<TermsVersionBean>();

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select "
                    + "u.version_no, "
                    + "u.status "
                    + "from ms_terms_adult u where u.category =:category";
            Query query = session.createSQLQuery(sql).setString("category", category);;
            List<Object[]> objectArrList = (List<Object[]>) query.list();
            if (objectArrList.size() > 0) {

                for (Object[] objArr : objectArrList) {
                    TermsVersionBean bean = new TermsVersionBean();
                    try {
                        bean.setKey(objArr[0].toString());
                    } catch (NullPointerException npe) {
                        bean.setKey("--");
                    }
                    try {
                        bean.setValue(objArr[0].toString());
                    } catch (NullPointerException npe) {
                        bean.setKey("--");
                    }
                    userLevel.add(bean);
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
        return userLevel;
    }

    public List<TermsVersionBean> getVersionTeenList(String category) throws Exception {
        Session session = null;
        List<TermsVersionBean> userLevel = new ArrayList<TermsVersionBean>();

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select "
                    + "u.version_no, "
                    + "u.status "
                    + "from ms_terms_teen u where u.category =:category";
            Query query = session.createSQLQuery(sql).setString("category", category);
            List<Object[]> objectArrList = (List<Object[]>) query.list();
            if (objectArrList.size() > 0) {

                for (Object[] objArr : objectArrList) {
                    TermsVersionBean bean = new TermsVersionBean();
                    try {
                        bean.setKey(objArr[0].toString());
                    } catch (NullPointerException npe) {
                        bean.setKey("--");
                    }
                    try {
                        bean.setValue(objArr[0].toString());
                    } catch (NullPointerException npe) {
                        bean.setKey("--");
                    }
                    userLevel.add(bean);
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
        return userLevel;
    }

    public String getActiveStatusAdult(String code) throws Exception {

        String description = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select STATUS from MS_TERMS_ADULT where VERSION_NO=:code";
            Query query = session.createSQLQuery(sql).setParameter("code", code);
            description = (String) query.list().get(0);

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
        return description;
    }

    public String getActiveStatusTeen(String code) throws Exception {
        String description = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select STATUS from MS_TERMS_TEEN where VERSION_NO=:code";
            Query query = session.createSQLQuery(sql).setParameter("code", code);
            description = (String) query.list().get(0);
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
        return description;
    }

    public String getCommonConfigByCode(String code) throws Exception {

        String description = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select PARAM_VALUE from MS_USER_PARAM where PARAMCODE=:code";
            Query query = session.createSQLQuery(sql).setParameter("code", code);
            description = (String) query.list().get(0);
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
        return description;
    }

    public int getPasswordexpiryperiod() throws Exception {

        int passwordexpiryperiod = 0;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "select passwordexpiryperiod from Passwordpolicy";
            Query query = session.createQuery(sql);
            Iterator itCount = query.iterate();
            passwordexpiryperiod = (Integer) itCount.next();

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
        return passwordexpiryperiod;
    }

    public String getUserRoleDesBycode(String userrolecode) throws Exception {

        String description = "";
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select DESCRIPTION from USERROLE where USERROLECODE=:userrolecode";
            Query query = session.createSQLQuery(sql).setParameter("userrolecode", userrolecode);
            description = (String) query.list().get(0);

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
        return description;
    }

    public List<Systemuser> getUserListByUserRole(String userrolecode) throws Exception {

        List<Systemuser> userList = new ArrayList<Systemuser>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Systemuser u where u.userrole.userrolecode=:userrolecode order by Upper(u.username) asc";
            Query query = session.createQuery(sql).setString("userrolecode", userrolecode);
            userList = query.list();

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
        return userList;
    }

    public List<TermsVersionBean> getVersionAdultList() throws Exception {
        Session session = null;
        List<TermsVersionBean> userLevel = new ArrayList<TermsVersionBean>();

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select "
                    + "u.version_no, "
                    + "u.status "
                    + "from ms_terms_adult u ";
            Query query = session.createSQLQuery(sql);
            List<Object[]> objectArrList = (List<Object[]>) query.list();
            if (objectArrList.size() > 0) {

                for (Object[] objArr : objectArrList) {
                    TermsVersionBean bean = new TermsVersionBean();
                    try {
                        bean.setKey(objArr[0].toString());
                    } catch (NullPointerException npe) {
                        bean.setKey("--");
                    }
                    try {
                        bean.setValue(objArr[0].toString());
                    } catch (NullPointerException npe) {
                        bean.setKey("--");
                    }
                    userLevel.add(bean);
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
        return userLevel;
    }

    public List<TermsVersionBean> getVersionTeenList() throws Exception {
        Session session = null;
        List<TermsVersionBean> userLevel = new ArrayList<TermsVersionBean>();

        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select "
                    + "u.version_no, "
                    + "u.status "
                    + "from ms_terms_teen u ";
            Query query = session.createSQLQuery(sql);
            List<Object[]> objectArrList = (List<Object[]>) query.list();
            if (objectArrList.size() > 0) {

                for (Object[] objArr : objectArrList) {
                    TermsVersionBean bean = new TermsVersionBean();
                    try {
                        bean.setKey(objArr[0].toString());
                    } catch (NullPointerException npe) {
                        bean.setKey("--");
                    }
                    try {
                        bean.setValue(objArr[0].toString());
                    } catch (NullPointerException npe) {
                        bean.setKey("--");
                    }
                    userLevel.add(bean);
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
        return userLevel;
    }

    public MsCustomerWalletPushFile getFileNameFromID(String id) throws Exception {

        MsCustomerWalletPushFile pushfile = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from MsCustomerWalletPushFile as s where s.id =:id ";
            Query query = session.createQuery(sql).setString("id", id);
            pushfile = (MsCustomerWalletPushFile) query.list().get(0);

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
        return pushfile;
    }

}
