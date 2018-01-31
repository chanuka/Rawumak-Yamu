/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.usermanagement;

import com.rawmakyamu.bean.controlpanel.usermanagement.UserRolePrivilegeInputBean;
import com.rawmakyamu.bean.controlpanel.usermanagement.UserRoleTaskBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.Page;
import com.rawmakyamu.util.mapping.Section;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.mapping.Pagetask;
import com.rawmakyamu.util.mapping.PagetaskId;
import com.rawmakyamu.util.mapping.Userrolesection;
import com.rawmakyamu.util.mapping.UserrolesectionId;
import com.rawmakyamu.util.mapping.Sectionpage;
import com.rawmakyamu.util.mapping.SectionpageId;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemuser;
import com.rawmakyamu.util.mapping.Userrole;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import com.rawmakyamu.util.varlist.PageVarList;
import com.rawmakyamu.util.varlist.TaskVarList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @Document : UserRolePrivilegeDAO
 * @Created on : Jan 7, 2014, 10:37:24 AM
 * @author : thushanth
 */
public class UserRolePrivilegeDAO {

    public void findSecByUserRole(UserRolePrivilegeInputBean bean)
            throws Exception {

        String userRole = bean.getUserRole();
        List<Section> newList = new ArrayList<Section>();
        List<Section> currentList = new ArrayList<Section>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql1 = "from Section as t where t.status.statuscode=:statuscode and sectioncode not in (select mp.id.sectioncode from Userrolesection mp where mp.id.userrolecode=:userrole) order by t.description asc";
            String sql2 = "from Section as t where t.status.statuscode=:statuscode and sectioncode in (select mp.id.sectioncode from Userrolesection mp where mp.id.userrolecode=:userrole) order by t.description asc";

            Query query1 = session.createQuery(sql1)
                    .setString("statuscode", CommonVarList.STATUS_ACTIVE)
                    .setString("userrole", userRole);
            Query query2 = session.createQuery(sql2)
                    .setString("statuscode", CommonVarList.STATUS_ACTIVE)
                    .setString("userrole", userRole);

            newList = (List<Section>) query1.list();
            currentList = (List<Section>) query2.list();

            for (Iterator<Section> it = newList.iterator(); it.hasNext();) {

                Section usersection = it.next();
//                bean.getCurrentList().put(usersection.getSectioncode(), usersection.getDescription());
                UserRoleTaskBean userRoleTaskBean = new UserRoleTaskBean();
                userRoleTaskBean.setKey(usersection.getSectioncode());
                userRoleTaskBean.setValue(usersection.getDescription());
                bean.getCurrentList().add(userRoleTaskBean);
            }

            for (Iterator<Section> it = currentList.iterator(); it.hasNext();) {

                Section usersection = it.next();
//                bean.getNewList().put(usersection.getSectioncode(), usersection.getDescription());
                UserRoleTaskBean userRoleTaskBean = new UserRoleTaskBean();
                userRoleTaskBean.setKey(usersection.getSectioncode());
                userRoleTaskBean.setValue(usersection.getDescription());
                bean.getNewList().add(userRoleTaskBean);
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
    }

    public void getPageListBySection(UserRolePrivilegeInputBean inputBean) throws Exception {

        String code = inputBean.getSection();
        String userrole = inputBean.getUserRole();
        List<Page> newList = new ArrayList<Page>();
        List<Page> currentList = new ArrayList<Page>();
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql1 = "from Page as t where t.status.statuscode=:status and t.pagecode not in (select mp.id.pagecode from Sectionpage mp where mp.id.userrolecode=:userrolecode) order by lower(t.description) asc";
            String sql2 = "from Page as t where t.status.statuscode=:status and t.pagecode in (select mp.id.pagecode from Sectionpage mp where mp.id.sectioncode=:sectioncode and mp.id.userrolecode=:userrolecode) order by lower(t.description) asc";

            Query query1 = session.createQuery(sql1).setString("status", CommonVarList.STATUS_ACTIVE).setString("userrolecode", userrole);
            Query query2 = session.createQuery(sql2).setString("status", CommonVarList.STATUS_ACTIVE).setString("sectioncode", code).setString("userrolecode", userrole);

            newList = (List<Page>) query1.list();
            currentList = (List<Page>) query2.list();

            for (Iterator<Page> it = newList.iterator(); it.hasNext();) {

                Page page = it.next();
//                inputBean.getCurrentList().put(page.getPagecode(), page.getDescription());
                UserRoleTaskBean userRoleTaskBean = new UserRoleTaskBean();
                userRoleTaskBean.setKey(page.getPagecode());
                userRoleTaskBean.setValue(page.getDescription());
                inputBean.getCurrentList().add(userRoleTaskBean);
            }

            for (Iterator<Page> it = currentList.iterator(); it.hasNext();) {

                Page page = it.next();
//                inputBean.getNewList().put(page.getPagecode(), page.getDescription());
                UserRoleTaskBean userRoleTaskBean = new UserRoleTaskBean();
                userRoleTaskBean.setKey(page.getPagecode());
                userRoleTaskBean.setValue(page.getDescription());
                inputBean.getNewList().add(userRoleTaskBean);
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
    }

    public void findTask(UserRolePrivilegeInputBean bean) throws Exception {

        List<Task> currentList = new ArrayList<Task>();
        List<Task> newList = new ArrayList<Task>();

        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql1 = "from Task as t where t.status.statuscode=:status and t.taskcode in ( select pt.id.taskcode from Pagetask as pt where pt.id.userrolecode=:userrolecode and pt.id.pagecode=:pagecode) order by Upper(t.description) asc";
            String sql2 = "from Task as t where t.status.statuscode=:status and t.taskcode in (select tt.id.taskcode from PagetaskTemplate as tt where tt.id.pagecode=:pagecodetemplate) and "
                    + "t.taskcode not in ( select pt.id.taskcode from Pagetask as pt where pt.id.userrolecode=:userrolecode and pt.id.pagecode=:pagecode) order by Upper(t.description) asc";

            Query query1 = session.createQuery(sql1).setString("status", CommonVarList.STATUS_ACTIVE).setString("userrolecode", bean.getUserRole()).setString("pagecode", bean.getPage());
            Query query2 = session.createQuery(sql2).setString("status", CommonVarList.STATUS_ACTIVE).setString("pagecodetemplate", bean.getPage()).setString("userrolecode", bean.getUserRole()).setString("pagecode", bean.getPage());

            currentList = (List<Task>) query1.list();
            newList = (List<Task>) query2.list();

            for (Iterator<Task> it = currentList.iterator(); it.hasNext();) {

                Task mpitask = it.next();
//                bean.getNewList().put(mpitask.getTaskcode(), mpitask.getDescription());

                UserRoleTaskBean userRoleTaskBean = new UserRoleTaskBean();
                userRoleTaskBean.setKey(mpitask.getTaskcode());
                userRoleTaskBean.setValue(mpitask.getDescription());
                bean.getNewList().add(userRoleTaskBean);

            }
            for (Iterator<Task> it = newList.iterator(); it.hasNext();) {

                Task mpitask = it.next();
//                bean.getCurrentList().put(mpitask.getTaskcode(), mpitask.getDescription());
                UserRoleTaskBean userRoleTaskBean = new UserRoleTaskBean();
                userRoleTaskBean.setKey(mpitask.getTaskcode());
                userRoleTaskBean.setValue(mpitask.getDescription());
                bean.getCurrentList().add(userRoleTaskBean);
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
    }

    public List<Section> getSectionListByUserRole(String userRole) throws Exception {
        List<Section> sectionList = new ArrayList<Section>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Section as t where t.status.statuscode=:status and sectioncode in (select mp.id.sectioncode from Userrolesection mp where mp.id.userrolecode=:userrolecode) order by lower(t.description) asc";
            Query query = session.createQuery(sql).setString("status", CommonVarList.STATUS_ACTIVE).setString("userrolecode", userRole);
            sectionList = (List<Section>) query.list();

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

    public List<Page> findpageByUserRoleSection(UserRolePrivilegeInputBean bean) throws Exception {

        List<Page> pageList = new ArrayList<Page>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql1 = "from Page as p where p.status.statuscode=:status and p.pagecode in (select sp.id.pagecode from Sectionpage as sp where sp.id.userrolecode=:userrolecode and sp.id.sectioncode=:sectioncode) order by lower(p.description) asc";

            Query query1 = session.createQuery(sql1).setString("status", CommonVarList.STATUS_ACTIVE).setString("userrolecode", bean.getUserRole()).setString("sectioncode", bean.getSectionpage());

            pageList = (List<Page>) query1.list();

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

    public String assignSection(UserRolePrivilegeInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        UserrolesectionId userrolesectionid = null;
        long count = 0;
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            Userrolesection userrolesection = null;

            Userrole ur = (Userrole) session.get(Userrole.class, inputBean.getUserRole().trim());
            String newV = ur.getDescription() + "|"
                    + inputBean.getNewBox();

            String sql = "from Userrolesection as u where u.id.userrolecode=:userrolecode";
            Query query = session.createQuery(sql).setString("userrolecode", inputBean.getUserRole());
            List<Userrolesection> userRoleList = query.list();
            List<String> newBoxHas = inputBean.getNewBox();
//            int count = userRoleList.size();

            for (Userrolesection mr : userRoleList) {

                if (newBoxHas.contains(mr.getId().getSectioncode())) {

                    mr.setLastupdatedtime(sysDate);
                    mr.setLastupdateduser(audit.getLastupdateduser());
                    session.update(mr);

                    newBoxHas.remove(mr.getId().getSectioncode());
                } else {

                    // check whether any pages were assigned to the section
                    String sql2 = "select count(userrolecode) from Sectionpage as pt where pt.id.userrolecode =:userrolecode and pt.id.sectioncode =:sectioncode";
                    Query query2 = session.createQuery(sql2).setString("userrolecode", inputBean.getUserRole()).setString("sectioncode", mr.getSection().getSectioncode());
                    Iterator itCount = query2.iterate();
                    count = (Long) itCount.next();

                    if (count > 0) {
                        message = MessageVarList.USER_ROLE_PRI_SEC_DEPEND;
                        return message;
                    } else {
                        session.delete(mr);
                        session.flush();
                    }
                }
            }

            for (String sections : newBoxHas) {

                userrolesection = new Userrolesection();
                userrolesectionid = new UserrolesectionId();
                userrolesectionid.setUserrolecode(inputBean.getUserRole());
                userrolesectionid.setSectioncode(sections);
                userrolesection.setId(userrolesectionid);
                userrolesection.setCreatetime(sysDate);
                userrolesection.setLastupdatedtime(sysDate);
                userrolesection.setLastupdateduser(audit.getLastupdateduser());
                session.save(userrolesection);
            }

            audit.setNewvalue(newV);
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

    public String assignSectionPages(UserRolePrivilegeInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        long count = 0;

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            Sectionpage pt = null;

            String sql = "from Sectionpage as pt where pt.id.userrolecode =:userrolecode and pt.id.sectioncode =:sectioncode";
            Query query = session.createQuery(sql).setString("userrolecode", inputBean.getUserRole()).setString("sectioncode", inputBean.getSection());

            Userrole ur = (Userrole) session.get(Userrole.class, inputBean.getUserRole().trim());
            Section se = (Section) session.get(Section.class, inputBean.getSection().trim());

            String newV = ur.getDescription() + "|"
                    + se.getDescription() + "|"
                    + inputBean.getNewBox();

            List<Sectionpage> sectionPageList = query.list();
            List<String> assignPageCodeList = inputBean.getNewBox();

            for (Sectionpage pst : sectionPageList) {

                if (assignPageCodeList.contains(pst.getId().getPagecode())) {

                    pst.setLastupdatedtime(sysDate);
                    pst.setLastupdateduser(audit.getLastupdateduser());
                    session.update(pst);

                    assignPageCodeList.remove(pst.getId().getPagecode());

                } else {

                    // check whether any tasks were assigned to the page
                    String sql2 = "select count(userrolecode) from Pagetask as pt where pt.id.userrolecode =:userrolecode and pt.id.pagecode =:pagecode ";
                    Query query2 = session.createQuery(sql2).setString("userrolecode", inputBean.getUserRole()).setString("pagecode", pst.getPage().getPagecode());
                    Iterator itCount = query2.iterate();
                    count = (Long) itCount.next();

                    if (count > 0) {
                        message = MessageVarList.USER_ROLE_PRI_PAGE_DEPEND;
                        return message;
                    } else {

                        session.delete(pst);
                        session.flush();

                    }
                }
            }

            for (String pageCode : assignPageCodeList) {

                pt = new Sectionpage();
                SectionpageId ptId = new SectionpageId(inputBean.getUserRole(), inputBean.getSection(), pageCode);
                pt.setId(ptId);
                pt.setCreatetime(sysDate);
                pt.setLastupdatedtime(sysDate);
                pt.setLastupdateduser(audit.getLastupdateduser());
                session.save(pt);

            }

            audit.setNewvalue(newV);

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

    public String assignTask(UserRolePrivilegeInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Page pg = (Page) session.get(Page.class, inputBean.getPage().trim());
            Userrole ur = (Userrole) session.get(Userrole.class, inputBean.getUserRole().trim());
            Section se = (Section) session.get(Section.class, inputBean.getSectionpage().trim());

            String newV = ur.getDescription() + "|"
                    + se.getDescription() + "|"
                    + pg.getDescription() + "|"
                    + inputBean.getNewBox();

            String sql = "from Pagetask as pt where pt.id.userrolecode =:userrolecode and pt.id.pagecode =:pagecode ";
            Query query = session.createQuery(sql).setString("userrolecode", inputBean.getUserRole()).setString("pagecode", inputBean.getPage());

            List<Pagetask> userSectionList = query.list();
            List<String> currSectionCodeList = inputBean.getNewBox();

            for (Pagetask pt : userSectionList) {

                if (currSectionCodeList.contains(pt.getId().getTaskcode().toString())) {

                    pt.setLastupdatedtime(sysDate);
                    pt.setLastupdateduser(audit.getLastupdateduser());
                    session.update(pt);

                    currSectionCodeList.remove(pt.getId().getTaskcode().toString());

                } else {

                    session.delete(pt);
                    session.flush();
                }
            }

            for (String taskcode : currSectionCodeList) {

                PagetaskId ptId = new PagetaskId(inputBean.getUserRole(), inputBean.getPage(), taskcode);

                Pagetask pt = new Pagetask();
                pt.setId(ptId);
                pt.setCreatetime(sysDate);
                pt.setLastupdatedtime(sysDate);
                pt.setLastupdateduser(audit.getLastupdateduser());
                session.save(pt);

            }

            audit.setNewvalue(newV);
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

//    public String insertDualAuthRecord(UserRolePrivilegeInputBean inputBean, Systemaudit audit) throws Exception {
//        String message = "";
//        String fields = "";
//        String fieldId = "";
//        Session session = null;
//        Transaction txn = null;
//        try {
//
//            session = HibernateInit.sessionFactory.openSession();
//            txn = session.beginTransaction();
//            Date sysDate = CommonDAO.getSystemDate(session);
//
//            String sql = "from Pendingtask as u "
//                    + "where "
//                    + "u.PKey=:p_key and "
//                    + "u.PKey1=:p_key1 and "
//                    + "u.page.pagecode=:pagecode and "
//                    + "u.status.statuscode=:p_status ";
//
//            Query query = session.createQuery(sql)
//                    .setString("p_key", inputBean.getUserRole())
//                    .setString("p_key1", inputBean.getPage())
//                    .setString("pagecode", PageVarList.USER_ROLE_PRIVILEGE_MGT)
//                    .setString("p_status", CommonVarList.STATUS_DUALAUTH_PEND);
//
//            if (query.list().isEmpty()) {
//
//                Pendingtask dualAuth = new Pendingtask();
//
//                Page page = (Page) session.get(Page.class, PageVarList.USER_ROLE_PRIVILEGE_MGT);
//                dualAuth.setPage(page);
//
//                Status status = (Status) session.get(Status.class, CommonVarList.STATUS_DUALAUTH_PEND);
//                dualAuth.setStatus(status);
//
//                Task task = (Task) session.get(Task.class, TaskVarList.ASSIGN_TASK);
//                dualAuth.setTask(task);
//
//                fields = inputBean.getSectionpage();
//
//                dualAuth.setFields(fields);
//                dualAuth.setPKey(inputBean.getUserRole());
//                dualAuth.setPKey1(inputBean.getPage());
//
//                fieldId = this.createDualAuthFieldId();
//
//                dualAuth.setFieldId(fieldId);
//                dualAuth.setCreatedtime(sysDate);
//                dualAuth.setLastupdatedtime(sysDate);
//
//                Systemuser sysUser = (Systemuser) session.get(Systemuser.class, audit.getLastupdateduser().trim());
//                dualAuth.setCreateduser(sysUser);
//
//                /**
//                 * Insert task list to pending page task
//                 */
//                List<String> taskCodeList = inputBean.getNewBox();
//
//                for (String taskcode : taskCodeList) {
//
//                    PendingPagetask ppt = new PendingPagetask();
//
//                    Page ppage = (Page) session.get(Page.class, inputBean.getPage().trim());
//                    ppt.setPage(ppage);
//
//                    Task ptask = (Task) session.get(Task.class, taskcode.trim());
//                    ppt.setTask(ptask);
//
//                    Userrole purole = (Userrole) session.get(Userrole.class, inputBean.getUserRole().trim());
//                    ppt.setUserrole(purole);
//
//                    ppt.setFieldId(fieldId);
//
//                    session.save(ppt);
//
//                }
//
//                if (audit != null) {
//
//                    audit.setDescription(audit.getDescription() + MessageVarList.COMMON_MSG_AUTHORIZATION_PENDING);
//                    audit.setCreatetime(sysDate);
//                    audit.setLastupdatedtime(sysDate);
//                    session.save(audit);
//                }
//
//                session.save(dualAuth);
//                txn.commit();
//            } else {
//                message = MessageVarList.COMMON_PENDING_AVAILABLE;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (txn != null) {
//                txn.rollback();
//            }
//            throw e;
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        return message;
//    }

    private String createDualAuthFieldId() throws Exception {

        String fieldID;
        try {
            Random randomgenerator = new Random();
            Date nowDate = new Date();

            long date = nowDate.getTime();
            fieldID = (date + "" + randomgenerator.nextInt(1000));
        } catch (Exception ex) {
            throw ex;
        }

        return fieldID;
    }

    public List<String> getPageListByPageType(String type) throws Exception {

        List<String> userPageList = new ArrayList<String>();
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String userTypeSQL = "select pagecode from Page as p where p.type=:type";
            Query userTypeQuery = session.createQuery(userTypeSQL).setString("type", type);
            userPageList = userTypeQuery.list();

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
        return userPageList;
    }

    public List<String> getPageListByUserRoleAndSection(String userrole, String section) throws Exception {

        List<String> allPageList = new ArrayList<String>();
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String allpagesql = "select pt.id.pagecode from Sectionpage as pt where pt.id.userrolecode =:userrolecode and pt.id.sectioncode!=:sectioncode ";
            Query allquery = session.createQuery(allpagesql).setString("userrolecode", userrole).setString("sectioncode", section);
            allPageList = allquery.list();

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
        return allPageList;
    }
}
