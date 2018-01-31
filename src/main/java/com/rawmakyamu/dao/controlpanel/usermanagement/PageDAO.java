/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.usermanagement;

import com.rawmakyamu.bean.controlpanel.usermanagement.PageBean;
import com.rawmakyamu.bean.controlpanel.usermanagement.PageInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.Page;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemaudit;
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
 * @author chalitha
 */
public class PageDAO {

    public List<PageBean> getSearchList(PageInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<PageBean> dataList = new ArrayList<PageBean>();
        Session session = null;
        try {

            if (orderBy.equals("") || orderBy == null) {
                orderBy = "order by u.createtime desc";
            }

            long count = 0;
            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(pagecode) from Page as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                String sqlSearch = "from Page u where " + where + orderBy;
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {

                    PageBean sdblPage = new PageBean();
                    Page Page = (Page) it.next();

                    try {
                        sdblPage.setPageCode(Page.getPagecode());
                    } catch (NullPointerException npe) {
                        sdblPage.setPageCode("--");
                    }
                    try {
                        sdblPage.setDescription(Page.getDescription());
                    } catch (NullPointerException npe) {
                        sdblPage.setDescription("--");
                    }
                    try {
                        sdblPage.setStatus(Page.getStatus().getDescription());
                    } catch (NullPointerException npe) {
                        sdblPage.setStatus("--");
                    }
                    try {
                        sdblPage.setSortKey(Page.getSortkey().toString());
                    } catch (NullPointerException npe) {
                        sdblPage.setSortKey("--");
                    }
                    try {
                        sdblPage.setUrl(Page.getUrl().toString());
                    } catch (NullPointerException npe) {
                        sdblPage.setUrl("--");
                    }
                    try {
                        if (Page.getCreatetime().toString() != null && !Page.getCreatetime().toString().isEmpty()) {
                            sdblPage.setCreatetime(Page.getCreatetime().toString().substring(0, 19));
                        } else {
                            sdblPage.setCreatetime("--");
                        }
                    } catch (NullPointerException npe) {
                        sdblPage.setCreatetime("--");
                    }

                    sdblPage.setFullCount(count);

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

    private String makeWhereClause(PageInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getPageCodeSearch() != null && !inputBean.getPageCodeSearch().isEmpty()) {
            where += " and lower(u.pagecode) like lower('%" + inputBean.getPageCodeSearch() + "%')";
        }
        if (inputBean.getDescriptionSearch() != null && !inputBean.getDescriptionSearch().isEmpty()) {
            where += " and lower(u.description) like lower('%" + inputBean.getDescriptionSearch() + "%')";
        }
        if (inputBean.getUrlSearch() != null && !inputBean.getUrlSearch().isEmpty()) {
            where += " and lower(u.url) like lower('%" + inputBean.getUrlSearch() + "%')";
        }
        if (inputBean.getSortKeySearch() != null && !inputBean.getSortKeySearch().isEmpty()) {
            where += " and u.sortkey = '" + inputBean.getSortKeySearch() + "'";
        }
        if (inputBean.getStatussearch() != null && !inputBean.getStatussearch().isEmpty()) {
            where += " and u.status.statuscode = '" + inputBean.getStatussearch() + "'";
        }
        return where;
    }

    public String insertPage(PageInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Page) session.get(Page.class, inputBean.getPageCode().trim()) == null) {
                txn = session.beginTransaction();

                Page Page = new Page();
                Page.setPagecode(inputBean.getPageCode().trim());
                Page.setDescription(inputBean.getDescription().trim());
                Page.setSortkey(new Byte(inputBean.getSortKey().trim()));
                Page.setUrl(inputBean.getUrl().trim());
                Page.setIsDual("NO");

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                Page.setStatus(st);

                Page.setCreatetime(sysDate);
                Page.setLastupdatedtime(sysDate);
                Page.setLastupdateduser(audit.getLastupdateduser());

                String newValue = Page.getPagecode()
                        + "|" + Page.getDescription()
                        + "|" + Page.getUrl()
                        + "|" + Page.getSortkey()
                        + "|" + Page.getStatus().getDescription();

                audit.setNewvalue(newValue);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(Page);

                txn.commit();

            } else {

                long count = 0;

                String sqlCount = "select count(pagecode) from Page as u where u.status=:statuscode AND u.pagecode=:pagecode ";
                Query queryCount = session.createQuery(sqlCount).setString("statuscode", CommonVarList.STATUS_DELETE)
                        .setString("pagecode", inputBean.getPageCode().trim());

                Iterator itCount = queryCount.iterate();
                count = (Long) itCount.next();

                if (count > 0) {
                    message = "$" + inputBean.getPageCode().trim();
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

    public String updatePage(PageInputBean inputBean, Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {

            CommonDAO commonDAO = new CommonDAO();
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = commonDAO.getSystemDate(session);

            Page u = (Page) session.get(Page.class, inputBean.getPageCode().trim());

            if (u != null) {

                if (u.getSortkey().toString().equals(inputBean.getSortKey())) {

                    String oldValue = u.getPagecode()
                            + "|" + u.getDescription()
                            + "|" + u.getUrl()
                            + "|" + u.getSortkey()
                            + "|" + u.getStatus().getDescription();

                    u.setDescription(inputBean.getDescription().trim());
                    u.setSortkey(new Byte(inputBean.getSortKey().trim()));
                    u.setUrl(inputBean.getUrl().trim());

                    Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                    u.setStatus(st);

                    u.setLastupdateduser(audit.getLastupdateduser());
                    u.setLastupdatedtime(sysDate);

                    String newValue = u.getPagecode()
                            + "|" + u.getDescription()
                            + "|" + u.getUrl()
                            + "|" + u.getSortkey()
                            + "|" + u.getStatus().getDescription();

                    audit.setOldvalue(oldValue);
                    audit.setNewvalue(newValue);
                    audit.setCreatetime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.update(u);

                    txn.commit();
                } else {

                    message = commonDAO.getPageSortKeyCount(inputBean.getSortKey());

                    if (message.isEmpty()) {
                        String oldValue = u.getPagecode()
                                + "|" + u.getDescription()
                                + "|" + u.getUrl()
                                + "|" + u.getSortkey()
                                + "|" + u.getStatus().getDescription();

                        u.setDescription(inputBean.getDescription().trim());
                        u.setSortkey(new Byte(inputBean.getSortKey().trim()));
                        u.setUrl(inputBean.getUrl().trim());

                        Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                        u.setStatus(st);

                        u.setLastupdateduser(audit.getLastupdateduser());
                        u.setLastupdatedtime(sysDate);

                        String newValue = u.getPagecode()
                                + "|" + u.getDescription()
                                + "|" + u.getUrl()
                                + "|" + u.getSortkey()
                                + "|" + u.getStatus().getDescription();

                        audit.setOldvalue(oldValue);
                        audit.setNewvalue(newValue);
                        audit.setCreatetime(sysDate);
                        audit.setLastupdatedtime(sysDate);

                        session.save(audit);
                        session.update(u);

                        txn.commit();
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

    public String deletePage(PageInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Page u = (Page) session.get(Page.class, inputBean.getPageCode().trim());
            if (u != null) {

                long count = 0;

                String sqlCount = "select count(pagecode) from Sectionpage as u where u.page.pagecode=:pagecode ";
                Query queryCount = session.createQuery(sqlCount).setString("pagecode", inputBean.getPageCode().trim());

                Iterator itCount = queryCount.iterate();
                count = (Long) itCount.next();

                if (count > 0) {
                    message = MessageVarList.COMMON_NOT_DELETE;
                } else {
                    String sqlCount2 = "select count(pagecode) from Pagetask as u where u.page.pagecode=:pagecode ";
                    Query queryCount2 = session.createQuery(sqlCount2).setString("pagecode", inputBean.getPageCode().trim());

                    Iterator itCount2 = queryCount2.iterate();
                    count = (Long) itCount2.next();
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

    public Page findPageById(String pageCode) throws Exception {
        Page Page = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Page as u where u.pagecode=:pagecode";
            Query query = session.createQuery(sql).setString("pagecode", pageCode);
            Page = (Page) query.list().get(0);

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
        return Page;

    }

}
