/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.systemconfig;

import com.rawmakyamu.bean.controlpanel.systemconfig.MobMsgBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.MobMsgInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.MsUserMessage;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.varlist.MessageVarList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author dilanka_w
 */
public class MobMsgMgtDAO {

    public List<MobMsgBean> getSearchList(MobMsgInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<MobMsgBean> dataList = new ArrayList<MobMsgBean>();
        Session session = null;
        try {

            if (orderBy.equals("") || orderBy == null) {
                orderBy = "order by u.createdtime desc";
            }

            long count = 0;
            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(paramcode) from MsUserMessage as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                String sqlSearch = "from MsUserMessage u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    MobMsgBean databean = new MobMsgBean();
                    MsUserMessage userMsg = (MsUserMessage) it.next();

                    try {
                        databean.setParamcode(userMsg.getParamcode());
                    } catch (NullPointerException npe) {
                        databean.setParamcode("--");
                    }
                    try {
                        databean.setDescription(userMsg.getDescription());
                    } catch (NullPointerException npe) {
                        databean.setDescription("--");
                    }
                    try {
                        databean.setValue(userMsg.getParamValue());
                    } catch (NullPointerException npe) {
                        databean.setValue("--");
                    }

                    try {
                        if (userMsg.getCreatedtime().toString() != null && !userMsg.getCreatedtime().toString().isEmpty()) {
                            databean.setCreatedtime(userMsg.getCreatedtime().toString().substring(0, 19));
                        } else {
                            databean.setCreatedtime("--");
                        }
                    } catch (NullPointerException npe) {
                        databean.setCreatedtime("--");
                    }

                    databean.setFullCount(count);

                    dataList.add(databean);
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

    private String makeWhereClause(MobMsgInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getParamCodeSearch() != null && !inputBean.getParamCodeSearch().isEmpty()) {
            where += " and lower(u.paramcode) like lower('%" + inputBean.getParamCodeSearch() + "%')";
        }
        if (inputBean.getDescriptionSearch() != null && !inputBean.getDescriptionSearch().isEmpty()) {
            where += " and lower(u.description) like lower('%" + inputBean.getDescriptionSearch() + "%')";
        }
        if (inputBean.getValueSearch() != null && !inputBean.getValueSearch().isEmpty()) {
            where += " and lower(u.paramValue) like lower('%" + inputBean.getValueSearch() + "%')";
        }
        return where;
    }

    public MsUserMessage findMobMsgByCode(String paramcode) throws Exception {
        MsUserMessage mobMsg = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from MsUserMessage as u where u.paramcode=:paramcode";
            Query query = session.createQuery(sql).setString("paramcode", paramcode.trim());
            mobMsg = (MsUserMessage) query.list().get(0);

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
        return mobMsg;

    }

    public String updateMobMsg(MobMsgInputBean inputBean, Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {

            CommonDAO commonDAO = new CommonDAO();
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = commonDAO.getSystemDate(session);

            MsUserMessage u = (MsUserMessage) session.get(MsUserMessage.class, inputBean.getParamCode().trim());

            if (u != null) {

                String oldValue = u.getParamcode()
                        + "|" + u.getDescription()
                        + "|" + u.getParamValue();

                u.setDescription(inputBean.getDescription().trim());
                u.setParamValue(inputBean.getValue().trim());

                u.setLastupdateduser(audit.getLastupdateduser());
                u.setLastupdatedtime(sysDate);

                String newValue = u.getParamcode()
                        + "|" + u.getDescription()
                        + "|" + u.getParamValue();

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

}
