/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.usermanagement;

import com.rawmakyamu.bean.controlpanel.usermanagement.UserRoleMgtBean;
import com.rawmakyamu.bean.controlpanel.usermanagement.UserRoleMgtInputBean;
import com.rawmakyamu.bean.controlpanel.usermanagement.UserRoleTaskBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Userrole;
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
 * @author asela
 */
public class UserRoleMgtDAO {

    //start newly changed
    public String activateUrole(UserRoleMgtInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();

            Date sysDate = CommonDAO.getSystemDate(session);

            Userrole u = (Userrole) session.get(Userrole.class, inputBean.getUserRoleCode().trim());
            if (u != null) {

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                u.setDescription(inputBean.getDescription().trim());

//                Userlevel userlevel = new Userlevel();
//                userlevel.setLevelid(Integer.parseInt(inputBean.getUserRoleLevel()));
//                u.setUserlevel(userlevel);
                //Change status to 'Activate'
                Status status = new Status();
                status.setStatuscode(CommonVarList.STATUS_ACTIVE);
                u.setStatus(status);

//                u.setUser(audit.getUser());
//                u.setLastupdatedtime(sysDate);
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

    //end newly changed
    public String insertUserRole(UserRoleMgtInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Userrole) session.get(Userrole.class, inputBean.getUserRoleCode().trim()) == null) {
                txn = session.beginTransaction();

                Userrole userrole = new Userrole();
                userrole.setUserrolecode(inputBean.getUserRoleCode().trim());
                userrole.setDescription(inputBean.getDescription().trim());

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                userrole.setStatus(st);

                userrole.setCreatetime(sysDate);
                userrole.setLastupdatedtime(sysDate);
                userrole.setLastupdateduser(audit.getLastupdateduser());

                session.save(userrole);

                String newValue = userrole.getUserrolecode()
                        + "|" + userrole.getDescription()
                        + "|" + userrole.getStatus().getDescription();

                audit.setNewvalue(newValue);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);

                txn.commit();
            } else {

                long count = 0;

                String sqlCount = "select count(userrolecode) from Userrole as u where u.status.statuscode=:statuscode AND u.userrolecode=:userrole";
                Query queryCount = session.createQuery(sqlCount).setString("statuscode", CommonVarList.STATUS_DELETE)
                        .setString("userrole", inputBean.getUserRoleCode().trim());

                Iterator itCount = queryCount.iterate();
                count = (Long) itCount.next();

                if (count > 0) {
                    message = "$" + inputBean.getUserRoleCode().trim();
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

//    //update user
    public String updateUserRoleMgt(UserRoleMgtInputBean inputBean, Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Userrole u = (Userrole) session.get(Userrole.class, inputBean.getUserRoleCode().trim());

            if (u != null) {

                String oldValue = u.getUserrolecode()
                        + "|" + u.getDescription()
                        + "|" + u.getStatus().getDescription();

                u.setDescription(inputBean.getDescription().trim());

//                Userlevel userlevel = new Userlevel();
//                userlevel.setLevelid(Integer.parseInt(inputBean.getUserRoleLevel()));
//                u.setUserlevel(userlevel);
                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                u.setStatus(st);

                u.setLastupdatedtime(sysDate);
                u.setLastupdateduser(audit.getLastupdateduser());

                session.update(u);

                String newValue = u.getUserrolecode()
                        + "|" + u.getDescription()
                        + "|" + u.getStatus().getDescription();

                audit.setOldvalue(oldValue);
                audit.setNewvalue(newValue);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);

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
    //get search list
//    //delete section

    public String deleteUserRoleMgt(UserRoleMgtInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Userrole u = (Userrole) session.get(Userrole.class, inputBean.getUserRoleCode().trim());
            if (u != null) {

                long count = 0;

                String sqlCount = "select count(userrolecode) from Userrolesection as u where u.userrole.userrolecode=:userrole";
                Query queryCount = session.createQuery(sqlCount).setString("userrole", inputBean.getUserRoleCode().trim());

                Iterator itCount = queryCount.iterate();
                count = (Long) itCount.next();

                if (count > 0) {
                    message = MessageVarList.COMMON_NOT_DELETE;
                } else {
                    String sqlCount2 = "select count(userrolecode) from Sectionpage as u where u.userrole.userrolecode=:userrole";
                    Query queryCount2 = session.createQuery(sqlCount2).setString("userrole", inputBean.getUserRoleCode().trim());

                    Iterator itCount2 = queryCount2.iterate();
                    count = (Long) itCount2.next();
                    if (count > 0) {
                        message = MessageVarList.COMMON_NOT_DELETE;
                    } else {
                        String sqlCount3 = "select count(userrolecode) from Pagetask as u where u.userrole.userrolecode=:userrole";
                        Query queryCount3 = session.createQuery(sqlCount3).setString("userrole", inputBean.getUserRoleCode().trim());

                        Iterator itCount3 = queryCount3.iterate();
                        count = (Long) itCount3.next();
                        if (count > 0) {
                            message = MessageVarList.COMMON_NOT_DELETE;
                        } else {
                            String sqlCount4 = "select count(userrole) from Systemuser as u where u.userrole.userrolecode=:userrole";
                            Query queryCount4 = session.createQuery(sqlCount4).setString("userrole", inputBean.getUserRoleCode().trim());

                            Iterator itCount4 = queryCount4.iterate();
                            count = (Long) itCount4.next();
                            if (count > 0) {
                                message = MessageVarList.COMMON_NOT_DELETE;
                            } else {
                                audit.setCreatetime(sysDate);
                                audit.setLastupdatedtime(sysDate);

                                session.save(audit);
                                session.delete(u);
                                txn.commit();

                            }
                        }
                    }
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

    public List<UserRoleMgtBean> getSearchList(UserRoleMgtInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<UserRoleMgtBean> dataList = new ArrayList<UserRoleMgtBean>();
        Session session = null;
        try {

            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by UR.CREATEDTIME desc ";
            }

            String where = this.makeWhereClause(inputBean);

            BigDecimal count = new BigDecimal(0);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(UR.USERROLECODE) from USERROLE UR where UR.USERROLECODE!='MERCH' and UR.USERROLECODE!='MERCUS' and  " + where;

            Query queryCount = session.createSQLQuery(sqlCount);

            List countList = queryCount.list();
            count = (BigDecimal) countList.get(0);

            if (count.longValue() > 0) {

//                String sqlSearch = " SELECT * from ( select UR.USERROLECODE, UR.DESCRIPTION AS ROLEDESC, UL.DESCRIPTION AS ULDESC, ST.DESCRIPTION AS STDESC, rownum r "
//                        + " from USERROLE UR,STATUS ST, USERLEVEL UL "
//                        + " where UR.STATUSCODE=ST.STATUSCODE AND UR.USERLEVEL=UL.LEVELID AND " 
//                        + where + orderBy + " ) where r > " + first + " and r<= " + max;
                String sqlSearch = " SELECT * from ( select UR.USERROLECODE, UR.DESCRIPTION AS ROLEDESC, ST.DESCRIPTION AS STDESC, UR.CREATEDTIME , "
                        + " row_number() over (" + orderBy + ") as r "
                        + " from USERROLE UR,STATUS ST "
                        + " where UR.STATUSCODE=ST.STATUSCODE AND UR.USERROLECODE!='MERCH' and UR.USERROLECODE!='MERCUS' AND "
                        + where + " ) where r > " + first + " and r<= " + max;

                List<Object[]> chequeList = (List<Object[]>) session.createSQLQuery(sqlSearch).list();

                for (Object[] urBean : chequeList) {

                    UserRoleMgtBean urMgtBean = new UserRoleMgtBean();

                    try {
                        urMgtBean.setUserRoleCode(urBean[0].toString());
                    } catch (NullPointerException npe) {
                        urMgtBean.setUserRoleCode("--");
                    }
                    try {
                        urMgtBean.setDescription(urBean[1].toString());
                    } catch (NullPointerException npe) {
                        urMgtBean.setDescription("--");
                    }
//                    try {
//                        urMgtBean.setUserRoleLevel(urBean[2].toString());
//                    } catch (NullPointerException npe) {
//                        urMgtBean.setUserRoleLevel("--");
//                    }
                    try {
                        urMgtBean.setStatus(urBean[2].toString());
                    } catch (NullPointerException npe) {
                        urMgtBean.setStatus("--");
                    }
                    try {
                        if (urBean[3].toString() != null && !urBean[3].toString().isEmpty()) {
                            urMgtBean.setCreatetime(urBean[3].toString().substring(0, 19));
                        } else {
                            urMgtBean.setCreatetime("--");
                        }

                    } catch (NullPointerException npe) {
                        urMgtBean.setCreatetime("--");
                    }

                    urMgtBean.setFullCount(count.longValue());

                    dataList.add(urMgtBean);
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

    private String makeWhereClause(UserRoleMgtInputBean inputBean) throws Exception {

        String where = "1=1";

        if (inputBean.getS_userrolecode() != null && !inputBean.getS_userrolecode().isEmpty()) {
            where += " and lower(UR.USERROLECODE) like lower('%" + inputBean.getS_userrolecode() + "%')";
        }
        if (inputBean.getS_description() != null && !inputBean.getS_description().isEmpty()) {
            where += " and lower(UR.DESCRIPTION) like lower('%" + inputBean.getS_description() + "%')";
        }
        if (inputBean.getS_status() != null && !inputBean.getS_status().isEmpty()) {
            where += " and UR.STATUSCODE = '" + inputBean.getS_status() + "'";
        }

        return where;
    }

    //find userrole by userrole code
    public Userrole findUserRoleById(String userRoleCode) throws Exception {
        Userrole userrole = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Userrole as u where u.userrolecode=:userrolecode";
            Query query = session.createQuery(sql).setString("userrolecode", userRoleCode);
            userrole = (Userrole) query.list().get(0);

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
        return userrole;

    }

}
