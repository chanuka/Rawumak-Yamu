package com.rawmakyamu.dao.controlpanel.usermanagement;

import com.rawmakyamu.bean.controlpanel.usermanagement.SectionBean;
import com.rawmakyamu.bean.controlpanel.usermanagement.SectionInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.Section;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author jeevan
 */
public class SectionDao {

    public List<SectionBean> getSearchList(SectionInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<SectionBean> dataList = new ArrayList<SectionBean>();

        Session session = null;

        try {

            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by S.CREATEDTIME desc ";
            }

            String where = this.makeWhereClause(inputBean);

            BigDecimal count = new BigDecimal(0);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(S.SECTIONCODE) from SECTION S where " + where;

            Query queryCount = session.createSQLQuery(sqlCount);

            List countList = queryCount.list();
            count = (BigDecimal) countList.get(0);

            if (count.longValue() > 0) {

                String sqlSearch = " SELECT * from ( select S.SECTIONCODE, S.DESCRIPTION, S.SORTKEY, ST.DESCRIPTION AS STDESC , S.CREATEDTIME ,"
                        + " row_number() over (" + orderBy + ") as r "
                        + " from SECTION S,STATUS ST  "
                        + " where S.STATUS=ST.STATUSCODE AND "
                        + where + " ) where r > " + first + " and r<= " + max;

                List<Object[]> chequeList = (List<Object[]>) session.createSQLQuery(sqlSearch).list();

                for (Object[] secBean : chequeList) {

                    SectionBean secMgtBean = new SectionBean();

                    try {
                        secMgtBean.setSectioncode(secBean[0].toString());
                    } catch (NullPointerException npe) {
                        secMgtBean.setSectioncode("--");
                    }
                    try {
                        secMgtBean.setDescription(secBean[1].toString());
                    } catch (NullPointerException npe) {
                        secMgtBean.setDescription("--");
                    }
                    try {
                        secMgtBean.setSortkey(secBean[2].toString());
                    } catch (NullPointerException npe) {
                        secMgtBean.setSortkey("--");
                    }
                    try {
                        secMgtBean.setStatuscode(secBean[3].toString());
                    } catch (NullPointerException npe) {
                        secMgtBean.setStatuscode("--");
                    }
                    try {
                        if (secBean[4].toString() != null && !secBean[4].toString().isEmpty()) {
                            secMgtBean.setCreatetime(secBean[4].toString().substring(0, 19));
                        } else {
                            secMgtBean.setCreatetime("--");
                        }

                    } catch (NullPointerException npe) {
                        secMgtBean.setCreatetime("--");
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

    private String makeWhereClause(SectionInputBean inputBean) throws Exception {

        String where = "1=1";

        if ((inputBean.getS_sectioncode() == null || inputBean.getS_sectioncode().isEmpty())
                && (inputBean.getS_description() == null || inputBean.getS_description().isEmpty())
                && (inputBean.getS_sortkey() == null || inputBean.getS_sortkey().isEmpty())
                && (inputBean.getS_status() == null || inputBean.getS_status().isEmpty())) {

        } else {

            if (inputBean.getS_sectioncode() != null && !inputBean.getS_sectioncode().isEmpty()) {
                where += " and lower(S.SECTIONCODE) like lower('%" + inputBean.getS_sectioncode() + "%')";
            }
            if (inputBean.getS_description() != null && !inputBean.getS_description().isEmpty()) {
                where += " and lower(S.DESCRIPTION) like lower('%" + inputBean.getS_description() + "%')";
            }
            if (inputBean.getS_sortkey() != null && !inputBean.getS_sortkey().isEmpty()) {
                where += " and S.SORTKEY like '%" + inputBean.getS_sortkey() + "%'";
            }
            if (inputBean.getS_status() != null && !inputBean.getS_status().isEmpty()) {
                where += " and S.STATUS='" + inputBean.getS_status() + "'";
            }
        }

        return where;
    }

    public String insertSection(SectionInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Section) session.get(Section.class, inputBean.getSectionCode().trim()) == null) {
                txn = session.beginTransaction();

                Section section = new Section();
                section.setSectioncode(inputBean.getSectionCode().trim());
                section.setDescription(inputBean.getDescription().trim());
                section.setSortkey(new Byte(inputBean.getSortKey().trim()));

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                section.setStatus(st);

                section.setCreatetime(sysDate);
                section.setLastupdatedtime(sysDate);
                section.setLastupdateduser(audit.getLastupdateduser());

                String newValue = section.getSectioncode()
                        + "|" + section.getDescription()
                        + "|" + section.getSortkey()
                        + "|" + section.getStatus().getDescription();

                audit.setNewvalue(newValue);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(section);

                txn.commit();

            } else {

                long count = 0;

                String sqlCount = "select count(sectioncode) from Section as u where u.status=:statuscode AND u.sectioncode=:sectioncode ";
                Query queryCount = session.createQuery(sqlCount).setString("statuscode", CommonVarList.STATUS_DELETE)
                        .setString("sectioncode", inputBean.getSectionCode().trim());

                Iterator itCount = queryCount.iterate();
                count = (Long) itCount.next();

                if (count > 0) {
                    message = "$" + inputBean.getSectionCode().trim();
                } else {
                    message = MessageVarList.SECTION_CODE_ALREADY_EXISTS;
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
            } catch (HibernateException e) {
                throw e;
            }
        }
        return message;
    }

    public String updateSection(SectionInputBean inputBean, Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            CommonDAO commonDAO = new CommonDAO();
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = commonDAO.getSystemDate(session);

            Section u = (Section) session.get(Section.class, inputBean.getSectionCode().trim());

            if (u != null) {

                if (u.getSortkey().toString().equals(inputBean.getSortKey())) {

                    String oldValue = u.getSectioncode()
                            + "|" + u.getDescription()
                            + "|" + u.getSortkey()
                            + "|" + u.getStatus().getDescription();

                    u.setDescription(inputBean.getDescription().trim());
                    u.setSortkey(new Byte(inputBean.getSortKey().trim()));

                    Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                    u.setStatus(st);

                    u.setLastupdateduser(audit.getLastupdateduser());
                    u.setLastupdatedtime(sysDate);

                    String newValue = u.getSectioncode()
                            + "|" + u.getDescription()
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
                    message = commonDAO.getSectionSortKeyCount(inputBean.getSortKey());
                    if (message.isEmpty()) {
                        String oldValue = u.getSectioncode()
                                + "|" + u.getDescription()
                                + "|" + u.getSortkey()
                                + "|" + u.getStatus().getDescription();

                        u.setDescription(inputBean.getDescription().trim());
                        u.setSortkey(new Byte(inputBean.getSortKey().trim()));

                        Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                        u.setStatus(st);

                        u.setLastupdateduser(audit.getLastupdateduser());
                        u.setLastupdatedtime(sysDate);

                        String newValue = u.getSectioncode()
                                + "|" + u.getDescription()
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

    public String deleteSection(SectionInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Section u = (Section) session.get(Section.class, inputBean.getSectionCode().trim());

            if (u != null) {

                long count = 0;
                long count2 = 0;

                String sqlCount = "select count(sectioncode) from Userrolesection as u where u.section.sectioncode=:sectioncode ";
                Query queryCount = session.createQuery(sqlCount).setString("sectioncode", inputBean.getSectionCode().trim());

                Iterator itCount = queryCount.iterate();
                count = (Long) itCount.next();

                if (count > 0) {
                    message = MessageVarList.COMMON_NOT_DELETE;
                } else {
                    String sqlCount2 = "select count(sectioncode) from Sectionpage as u where u.section.sectioncode=:sectioncode ";
                    Query queryCount2 = session.createQuery(sqlCount2).setString("sectioncode", inputBean.getSectionCode().trim());

                    Iterator itCount2 = queryCount2.iterate();
                    count2 = (Long) itCount2.next();
                    if (count2 > 0) {
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

    public Section findSectionById(String sectionCode) throws Exception {
        Section section = null;
        Session session = null;

        try {

            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Section as u where u.sectioncode=:sectioncode";
            Query query = session.createQuery(sql).setString("sectioncode", sectionCode);
            section = (Section) query.list().get(0);

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (HibernateException e) {
                throw e;
            }
        }
        return section;
    }

}
