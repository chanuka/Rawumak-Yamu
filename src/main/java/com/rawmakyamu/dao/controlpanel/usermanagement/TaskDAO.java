/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.usermanagement;

import com.rawmakyamu.bean.controlpanel.usermanagement.TaskBean;
import com.rawmakyamu.bean.controlpanel.usermanagement.TaskInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.varlist.CommonVarList;
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
 * @author chanuka
 */
public class TaskDAO {

    //start newly changed
    public String activateTask(TaskInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();

            Date sysDate = CommonDAO.getSystemDate(session);

            Task u = (Task) session.get(Task.class, inputBean.getTaskCode().trim());
            if (u != null) {

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                u.setDescription(inputBean.getDescription().trim());
                u.setSortkey(new Byte(inputBean.getSortKey().trim()));

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
    }//end newly changed

    public String insertPage(TaskInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);
            txn = session.beginTransaction();

            String sql = "from Task as u where lower(u.taskcode)=:taskcode ";
            Query query = session.createQuery(sql).setString("taskcode", inputBean.getTaskCode().trim().toLowerCase());
            if (query.list() == null || query.list().size() < 1) {

                Task task = new Task();
                task.setTaskcode(inputBean.getTaskCode().trim());
                task.setDescription(inputBean.getDescription().trim());
//                task.setSortkey(new Byte(inputBean.getSortKey().trim()));

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                task.setStatus(st);

                task.setCreatetime(sysDate);
                task.setLastupdatedtime(sysDate);
                task.setLastupdateduser(audit.getLastupdateduser());

                String newV = task.getTaskcode()
                        + "|" + task.getDescription()
                        //                        + "|" + task.getSortkey()
                        + "|" + task.getStatus().getDescription();

                audit.setNewvalue(newV);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(task);

                txn.commit();
            } else {

                long count = 0;
                String sqlCount = "select count(taskcode) from Task as u where u.status=:statuscode AND lower(u.taskcode)=:taskcode";
                Query queryCount = session.createQuery(sqlCount).setString("statuscode", CommonVarList.STATUS_DELETE)
                        .setString("taskcode", inputBean.getTaskCode().trim().toLowerCase());

                Iterator itCount = queryCount.iterate();
                count = (Long) itCount.next();

                if (count > 0) {
                    message = "$" + inputBean.getTaskCode().trim();
                } else {
                    message = MessageVarList.COMMON_ALREADY_EXISTS;
                }
            }

//            if ((Task) session.get(Task.class, inputBean.getTaskCode().trim()) == null) {
//                txn = session.beginTransaction();
//
//                Task task = new Task();
//                task.setTaskcode(inputBean.getTaskCode().trim());
//                task.setDescription(inputBean.getDescription().trim());
//                task.setSortkey(new Byte(inputBean.getSortKey().trim()));
//
//                Status st = new Status();
//                st.setStatuscode(CommonVarList.STATUS_ACTIVE);
//                task.setStatus(st);
////                task.setCreatetime(sysDate);
////                task.setLastupdatedtime(sysDate);
////                task.setUser(audit.getUser());
//
//                audit.setCreatetime(sysDate);
//                audit.setLastupdatedtime(sysDate);
//
//                session.save(audit);
//                session.save(task);
//
//                txn.commit();
//            } else {
//                long count = 0;
//
//                System.out.println("****************** " + inputBean.getTaskCode().trim().toLowerCase());
//                String sqlCount = "select count(taskcode) from Task as u where u.status=:statuscode AND lower(u.taskcode)=:taskcode";
//                Query queryCount = session.createQuery(sqlCount).setString("statuscode", CommonVarList.STATUS_DELETE)
//                        .setString("taskcode", inputBean.getTaskCode().trim().toLowerCase());
//
//                Iterator itCount = queryCount.iterate();
//                count = (Long) itCount.next();
//
//                if (count > 0) {
//                    message = "$" + inputBean.getTaskCode().trim();
//                } else {
//                    message = MessageVarList.COMMON_ALREADY_EXISTS;
//                }
//
////                message = MessageVarList.COMMON_ALREADY_EXISTS;
////                Task u = (Task) session.get(Task.class, inputBean.getTaskCode().trim());
////                txn = session.beginTransaction();
////                u.setDescription(inputBean.getDescription());
////                u.setSortkey(new Integer(inputBean.getSortKey()));
////                
////                Status st = new Status();
////                st.setStatuscode(CommonVarList.STATUS_ACTIVE);
////                u.setStatus(st);
////                
////                u.setUser(audit.getUser());
////                u.setLastupdatedtime(sysDate);
////
////                audit.setCreatedtime(sysDate);
////                audit.setLastupdatedtime(sysDate);
////
////                session.save(audit);
////                session.update(u);
////                
////                txn.commit();
//            }
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
    public String updateTask(TaskInputBean inputBean, Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Task u = (Task) session.get(Task.class, inputBean.getTaskCode().trim());

            if (u != null) {

                String oldValue = u.getTaskcode()
                        + "|" + u.getDescription()
                        //                        + "|" + u.getSortkey()
                        + "|" + u.getStatus().getDescription();

                u.setDescription(inputBean.getDescription().trim());
//                u.setSortkey(new Byte(inputBean.getSortKey().trim()));

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                u.setStatus(st);

                u.setLastupdatedtime(sysDate);
                u.setLastupdateduser(audit.getLastupdateduser());

                String newV = u.getTaskcode()
                        + "|" + u.getDescription()
                        //                        + "|" + u.getSortkey()
                        + "|" + u.getStatus().getDescription();

                audit.setOldvalue(oldValue);
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

    //get search list
//    //delete section
    public String deleteTask(TaskInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Task u = (Task) session.get(Task.class, inputBean.getTaskCode().trim());
            if (u != null) {

                long count = 0;

                String sqlCount = "select count(taskcode) from Pagetask as u where u.task.taskcode=:taskcode";
                Query queryCount = session.createQuery(sqlCount).setString("taskcode", inputBean.getTaskCode().trim());

                Iterator itCount = queryCount.iterate();
                count = (Long) itCount.next();

                if (count > 0) {
                    message = MessageVarList.COMMON_NOT_DELETE;
                } else {
                    audit.setCreatetime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.delete(u);
                    txn.commit();
                }

//                audit.setCreatedtime(sysDate);
//                audit.setLastupdatedtime(sysDate);
//
//                //Change status to 'Delete'
//                Status status = new Status();
//                status.setStatuscode(CommonVarList.STATUS_DELETE);
//                u.setStatus(status);
//
//                u.setUser(audit.getUser());
//                u.setLastupdatedtime(sysDate);
//
//                session.save(audit);
//                session.update(u);
////                session.delete(u);
//
//
//                txn.commit();
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

    public List<TaskBean> getSearchList(TaskInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<TaskBean> dataList = new ArrayList<TaskBean>();
        Session session = null;
        try {

            if (orderBy.equals("") || orderBy == null) {
                orderBy = "order by u.createtime desc";
            }

            long count = 0;
            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(taskcode) from Task as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                String sqlSearch = "from Task u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    TaskBean mpiTask = new TaskBean();
                    Task task = (Task) it.next();

                    try {
                        mpiTask.setTaskcode(task.getTaskcode());
                    } catch (NullPointerException npe) {
                        mpiTask.setTaskcode("--");
                    }
                    try {
                        mpiTask.setDescription(task.getDescription());
                    } catch (NullPointerException npe) {
                        mpiTask.setDescription("--");
                    }
                    try {
                        mpiTask.setStatuscode(task.getStatus().getDescription());
                    } catch (NullPointerException npe) {
                        mpiTask.setStatuscode("--");
                    }
                    try {
                        if(task.getCreatetime()!= null && !task.getCreatetime().toString().isEmpty()){
                        mpiTask.setCreatetime(task.getCreatetime().toString().substring(0, 19));
                        }else{
                        mpiTask.setCreatetime("--");
                        }
                        
                    } catch (NullPointerException npe) {
                        mpiTask.setCreatetime("--");
                    }
//                    try {
//                        mpiTask.setSortkey(task.getSortkey().toString());
//                    } catch (NullPointerException npe) {
//                        mpiTask.setSortkey("--");
//                    }

                    mpiTask.setFullCount(count);

                    dataList.add(mpiTask);
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

    //find task by task code
    public Task findTaskById(String taskCode) throws Exception {
        Task task = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Task as u where u.taskcode=:taskcode";
            Query query = session.createQuery(sql).setString("taskcode", taskCode);
            task = (Task) query.list().get(0);

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
        return task;

    }

    private String makeWhereClause(TaskInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getTaskCodeSearch() != null && !inputBean.getTaskCodeSearch().isEmpty()) {
            where += " and lower(u.taskcode) like lower('%" + inputBean.getTaskCodeSearch() + "%')";
        }
        if (inputBean.getDescriptionSearch() != null && !inputBean.getDescriptionSearch().isEmpty()) {
            where += " and lower(u.description) like lower('%" + inputBean.getDescriptionSearch() + "%')";
        }
//        if (inputBean.getSortKeySearch() != null && !inputBean.getSortKeySearch().isEmpty()) {
//            where += " and u.sortkey = '" + inputBean.getSortKeySearch() + "'";
//        }
        if (inputBean.getStatusSearch() != null && !inputBean.getStatusSearch().isEmpty()) {
            where += " and u.status.statuscode = '" + inputBean.getStatusSearch() + "'";
        }
        return where;
    }
}
