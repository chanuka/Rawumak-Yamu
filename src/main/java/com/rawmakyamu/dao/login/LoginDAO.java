/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.login;

import com.rawmakyamu.bean.login.LoginBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.Page;
import com.rawmakyamu.util.mapping.Passwordpolicy;
import com.rawmakyamu.util.mapping.Section;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.mapping.Systemuser;
import com.rawmakyamu.util.mapping.Pagetask;
import com.rawmakyamu.util.mapping.Sectionpage;
import com.rawmakyamu.util.varlist.CommonVarList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author chanuka
 */
public class LoginDAO {

    public Systemuser findUserbyUsername(String username) throws Exception {
        Systemuser user = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Systemuser as u join fetch u.status join fetch u.userrole where u.username =:username";
            Query query = session.createQuery(sql).setString("username", username);

            user = (Systemuser) query.list().get(0);
        } catch (IndexOutOfBoundsException ibe) {
            user = null;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.flush();
                    session.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return user;
    }

    public boolean updateUser(LoginBean inputBean, Systemaudit audit, boolean login) throws Exception {
        Session session = null;
        Transaction txn = null;
        boolean status = true;
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            String sql = "from Systemuser as u where u.username =:username";
            Query query = session.createQuery(sql).setString("username", inputBean.getUsername().trim());

            Systemuser u = (Systemuser) query.list().get(0);

//            Mpisystemuser u = (Mpisystemuser) session.get(Mpisystemuser.class, inputBean.getUsername().trim());
            if (u != null) {

                if (login) {
                    u.setLoggeddate(sysDate);//set last logged date only in successfull login
                }
                Status s = new Status();
                s.setStatuscode(inputBean.getStatus());

                u.setStatus(s);
                u.setLastupdatedtime(sysDate);
                u.setNoofinvlidattempt(String.valueOf(inputBean.getAttempts()));

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.update(u);

                txn.commit();
            } else {
                status = false;
            }
        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.flush();
                    session.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return status;
    }

    public HashMap<Section, List<Page>> getSectionPages(String userrole) throws Exception {

        List<Sectionpage> sectionPList = null;
        HashMap<Section, List<Page>> secMap = new HashMap<Section, List<Page>>();// key : page code value : task list
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Sectionpage as u join fetch u.section join fetch u.page join fetch u.userrole where u.id.userrolecode =:userrole and u.page.pagecode !='LGPG' and u.section.status.statuscode=:statuscode and u.page.status.statuscode=:pstatuscode order by u.page.sortkey";
            Query query = session.createQuery(sql).setString("userrole", userrole).setString("statuscode", CommonVarList.STATUS_ACTIVE).setString("pstatuscode", CommonVarList.STATUS_ACTIVE);

            sectionPList = query.list();
            //set data to map
            for (Sectionpage bean : sectionPList) {
                List<Page> pageList = secMap.get(bean.getSection());

                if (pageList == null || pageList.isEmpty()) {
                    pageList = new ArrayList<Page>();
                    pageList.add(bean.getPage());
                    secMap.put(bean.getSection(), pageList);
                } else {
                    pageList.add(bean.getPage());
                    secMap.put(bean.getSection(), pageList);
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
        return secMap;
    }

    public HashMap<String, List<Task>> getPageTask(String userrole) throws Exception {

        List<Pagetask> pageList = null;

        HashMap<String, List<Task>> secMap = new HashMap<String, List<Task>>();// key : page code value : task list
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Pagetask as u join fetch u.page join fetch u.task where u.id.userrolecode =:userrolecode and u.task.status.statuscode=:statuscode";
            Query query = session.createQuery(sql).setString("userrolecode", userrole).setString("statuscode", CommonVarList.STATUS_ACTIVE);

            pageList = query.list();
            //set data to map
            for (Pagetask bean : pageList) {
                List<Task> taskList = secMap.get(bean.getPage().getPagecode());
                if (taskList == null || taskList.isEmpty()) {
                    taskList = new ArrayList<Task>();
                    taskList.add(bean.getTask());
                    secMap.put(bean.getPage().getPagecode(), taskList);
                } else {
                    taskList.add(bean.getTask());
                    secMap.put(bean.getPage().getPagecode(), taskList);
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
        return secMap;
    }

    public Passwordpolicy findPasswordPolicy() throws Exception {
        Passwordpolicy passwordpolicy = new Passwordpolicy();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String hql = "from Passwordpolicy as m";
            Query query = session.createQuery(hql);
            passwordpolicy = (Passwordpolicy) query.list().get(0);

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
        return passwordpolicy;
    }
}
