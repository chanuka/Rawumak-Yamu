/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.systemconfig;

/**
 *
 * @author jayana_i
 */
import com.rawmakyamu.bean.controlpanel.systemconfig.IBMobUserParamBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.IBMobUserParamInputBean;
import com.rawmakyamu.bean.controlpanel.usermanagement.SectionInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.Ibmobuserparam;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.varlist.MessageVarList;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class IBMobUserParamDAO {

    public boolean isExistListnerConfiguration() throws Exception {
        long count = 0;
        boolean status = false;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "select count(paramcode) from Ibmobuserparam";

            Query query = session.createQuery(sql);

            Iterator it = query.iterate();

            while (it.hasNext()) {
                count = (Long) it.next();
                if (count > 0) {
                    status = true;
                    break;
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
        return status;
    }

    public List<Ibmobuserparam> getConnectiontypeList()
            throws Exception {

        List<Ibmobuserparam> txnType = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Ibmobuserparam as s";
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

//    public List<IBMobUserParamBean> getSearchList(IBMobUserParamInputBean inputBean, int max, int first, String orderBy) throws Exception {
//
//        List<IBMobUserParamBean> dataList = new ArrayList<IBMobUserParamBean>();
//        Session session = null;
//        try {
//            if (orderBy.equals("") || orderBy == null) {
//                orderBy = " order by u.createdtime desc ";
//            }
//            long count = 0;
//
//            session = HibernateInit.sessionFactory.openSession();
//
//            String sqlCount = "select count(paramcode) from Ibmobuserparam as u ";
//            Query queryCount = session.createQuery(sqlCount);
//
//            Iterator itCount = queryCount.iterate();
//            count = (Long) itCount.next();
//
//            if (count > 0) {
//
//                String sqlSearch = "from Ibmobuserparam as u " + orderBy;
//
//                Query querySearch = session.createQuery(sqlSearch);
//                querySearch.setMaxResults(max);
//                querySearch.setFirstResult(first);
//
//                Iterator it = querySearch.iterate();
//
//                while (it.hasNext()) {
//
//                    IBMobUserParamBean bean = new IBMobUserParamBean();
//                    Ibmobuserparam tt = (Ibmobuserparam) it.next();
//
//                    try {
//
//                        if (tt.getParamcode() != null || !tt.getParamcode().isEmpty()) {
//                            bean.setParamcode(tt.getParamcode());
//                        } else {
//                            bean.setParamcode("--");
//                        }
//
//                    } catch (NullPointerException npe) {
//                        bean.setParamcode("--");
//                    }
//                    try {
//
//                        if (tt.getDescription() != null || !tt.getDescription().isEmpty()) {
//                            bean.setDescription(tt.getDescription());
//                        } else {
//                            bean.setDescription("--");
//                        }
//                    } catch (NullPointerException npe) {
//                        bean.setDescription("--");
//                    }
//                    try {
//
//                        if (tt.getValue() != null || !tt.getValue().isEmpty()) {
//                            bean.setValue(tt.getValue());
//                        } else {
//                            bean.setValue("--");
//                        }
//                    } catch (NullPointerException npe) {
//                        bean.setValue("--");
//                    }
//                    try {
//
//                        if (tt.getCreatedtime() != null || !tt.getCreatedtime().toString().isEmpty()) {
//                            bean.setCreatedtime(tt.getCreatedtime().toString());
//                        } else {
//                            bean.setCreatedtime("--");
//                        }
//                    } catch (NullPointerException npe) {
//                        bean.setCreatedtime("--");
//                    }
//                    try {
//
//                        if (tt.getLastupdatedtime() != null || !tt.getLastupdatedtime().toString().isEmpty()) {
//                            bean.setLastupdatedtime(tt.getLastupdatedtime().toString());
//                        } else {
//                            bean.setLastupdatedtime("--");
//                        }
//                    } catch (NullPointerException npe) {
//                        bean.setLastupdatedtime("--");
//                    }
//                    try {
//
//                        if (tt.getLastupdateduser() != null || !tt.getLastupdateduser().isEmpty()) {
//                            bean.setLastupdateduser(tt.getLastupdateduser());
//                        } else {
//                            bean.setLastupdateduser("--");
//                        }
//                    } catch (NullPointerException npe) {
//                        bean.setLastupdateduser("--");
//                    }
//
//                    bean.setFullCount(count);
//
//                    dataList.add(bean);
//                }
//            }
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
//        return dataList;
//    }
    public List<IBMobUserParamBean> getSearchList(IBMobUserParamInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<IBMobUserParamBean> dataList = new ArrayList<IBMobUserParamBean>();

        Session session = null;

        try {

            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by S.CREATEDTIME desc ";
            }

            String where = this.makeWhereClause(inputBean);

            BigDecimal count = new BigDecimal(0);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(S.PARAMCODE) from MS_USER_PARAM S where " + where;

            Query queryCount = session.createSQLQuery(sqlCount);

            List countList = queryCount.list();
            count = (BigDecimal) countList.get(0);

            if (count.longValue() > 0) {

                String sqlSearch = " SELECT * from ( select S.PARAMCODE, S.DESCRIPTION, S.PARAM_VALUE,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATEDTIME, "
                        + " row_number() over (" + orderBy + ") as r "
                        + " from MS_USER_PARAM S where "
                        + where + " ) where r > " + first + " and r<= " + max;

                List<Object[]> chequeList = (List<Object[]>) session.createSQLQuery(sqlSearch).list();

                for (Object[] secBean : chequeList) {

                    IBMobUserParamBean secMgtBean = new IBMobUserParamBean();

                    try {
                        secMgtBean.setParamcode(secBean[0].toString());
                    } catch (NullPointerException npe) {
                        secMgtBean.setParamcode("--");
                    }
                    try {
                        secMgtBean.setDescription(secBean[1].toString());
                    } catch (NullPointerException npe) {
                        secMgtBean.setDescription("--");
                    }
                    try {
                        secMgtBean.setValue(secBean[2].toString());
                    } catch (NullPointerException npe) {
                        secMgtBean.setValue("--");
                    }
                    try {
                        secMgtBean.setLastupdateduser(secBean[3].toString());
                    } catch (NullPointerException npe) {
                        secMgtBean.setLastupdateduser("--");
                    }
                    try {
                        secMgtBean.setLastupdatedtime(secBean[4].toString());
                    } catch (NullPointerException npe) {
                        secMgtBean.setLastupdatedtime("--");
                    }
                    try {
                        if (secBean[5].toString() != null && !secBean[5].toString().isEmpty()) {
                            secMgtBean.setCreatedtime(secBean[5].toString().substring(0, 19));
                        } else {
                            secMgtBean.setCreatedtime("--");
                        }
                    } catch (NullPointerException npe) {
                        secMgtBean.setCreatedtime("--");
                    }

                    secMgtBean.setFullCount(count.longValue());

                    dataList.add(secMgtBean);
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

    private String makeWhereClause(IBMobUserParamInputBean inputBean) throws Exception {

        String where = "1=1";

        if ((inputBean.getS_paramcode() == null || inputBean.getS_paramcode().isEmpty())
                && (inputBean.getS_description() == null || inputBean.getS_description().isEmpty())
                && (inputBean.getS_value() == null || inputBean.getS_value().isEmpty())) {

        } else {

            if (inputBean.getS_paramcode() != null && !inputBean.getS_paramcode().isEmpty()) {
                where += " and lower(S.PARAMCODE) like lower('%" + inputBean.getS_paramcode() + "%')";
            }
            if (inputBean.getS_description() != null && !inputBean.getS_description().isEmpty()) {
                where += " and lower(S.DESCRIPTION) like lower('%" + inputBean.getS_description() + "%')";
            }
            if (inputBean.getS_value() != null && !inputBean.getS_value().isEmpty()) {
                where += " and lower(S.PARAM_VALUE) like lower('%" + inputBean.getS_value() + "%')";
            }
        }

        return where;
    }

    public IBMobUserParamBean findListnerConfigurationById(String paramcode) throws Exception {
        Ibmobuserparam listnerconfiguration = new Ibmobuserparam();
        IBMobUserParamBean bean = new IBMobUserParamBean();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Ibmobuserparam as u where u.paramcode=:paramcode";
            Query query = session.createQuery(sql).setString("paramcode", paramcode);
            listnerconfiguration = (Ibmobuserparam) query.list().get(0);

            bean.setParamcode(listnerconfiguration.getParamcode());
            bean.setDescription(listnerconfiguration.getDescription());
            bean.setValue(listnerconfiguration.getValue());
            bean.setLastupdateduser(listnerconfiguration.getLastupdateduser());
            bean.setLastupdatedtime(listnerconfiguration.getLastupdatedtime().toString());
            bean.setCreatedtime(listnerconfiguration.getCreatedtime().toString());

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
        return bean;

    }

    public String updateListnerConfiguration(IBMobUserParamInputBean inputBean, Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Ibmobuserparam u = (Ibmobuserparam) session.get(Ibmobuserparam.class, inputBean.getParamCode().trim());

            if (u != null) {

                String oldValue = u.getParamcode()
                        + "|" + u.getDescription()
                        + "|" + u.getValue();

                u.setParamcode(inputBean.getParamCode());
                u.setDescription(inputBean.getDescription());
                u.setValue(inputBean.getValue());
                u.setLastupdateduser(audit.getLastupdateduser());
                u.setLastupdatedtime(sysDate);
                u.setCreatedtime(sysDate);

                audit.setCreatetime(sysDate);
                audit.setOldvalue(oldValue);
                audit.setLastupdatedtime(sysDate);
                audit.setLastupdateduser(audit.getLastupdateduser());

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
}
