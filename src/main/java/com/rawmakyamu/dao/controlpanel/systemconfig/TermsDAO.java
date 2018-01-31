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
import com.rawmakyamu.bean.controlpanel.systemconfig.TermsBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.TermsInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Termadult;
import com.rawmakyamu.util.mapping.Termteen;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TermsDAO {

    public String updateTerms(TermsInputBean inputBean, Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            String newValue = null;
            String oldValue = null;
            if (inputBean.getTermsid().equals("ADULT")) {
                Termadult u = (Termadult) session.get(Termadult.class, inputBean.getVersionno());
                String old_status = null;
                String old_status_audit = null;
                try {
                    old_status = u.getStatus().getStatuscode();
                    old_status_audit = u.getStatus().getDescription();
                } catch (Exception ex) {
                    old_status = "DEACT";
                    old_status_audit = "";
                }
                oldValue = "Adult"
                        + "|" + u.getVersion_no()
                        + "|" + old_status_audit;

                if (u != null) {
                    u.setTerms(inputBean.getDescription());
                    Status st = (Status) session.get(Status.class, inputBean.getStatus());
                    u.setStatus(st);

                    audit.setCreatetime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    newValue = "Adult"
                            + "|" + u.getVersion_no()
                            + "|" + u.getStatus().getDescription();

                    audit.setNewvalue(newValue);
                    audit.setOldvalue(oldValue);
                    session.save(audit);
                    session.update(u);

                } else {
                    message = MessageVarList.COMMON_NOT_EXISTS;
                }

                if (message.isEmpty() && inputBean.getStatus().equals("ACT") && old_status.equals("DEACT")) {
                    this.updateStatus(inputBean.getTermsid(), inputBean.getVersionno(), txn, session);
                }

                txn.commit();
            } else if (inputBean.getTermsid().equals("TEEN")) {
                Termteen u = (Termteen) session.get(Termteen.class, inputBean.getVersionno());
                String old_status = null;
                String old_status_audit = null;
                try {
                    old_status = u.getStatus().getStatuscode();
                    old_status_audit = u.getStatus().getDescription();
                } catch (Exception ex) {
                    old_status = "DEACT";
                    old_status_audit = "";
                }
                oldValue = "Teen"
                        + "|" + u.getVersion_no()
                        + "|" + old_status_audit;

                if (u != null) {
                    u.setTerms(inputBean.getDescription());
                    Status st = (Status) session.get(Status.class, inputBean.getStatus());
                    u.setStatus(st);

                    audit.setCreatetime(sysDate);
                    audit.setLastupdatedtime(sysDate);
                    newValue = "Teen"
                            + "|" + u.getVersion_no()
                            + "|" + u.getStatus().getDescription();

                    audit.setNewvalue(newValue);
                    audit.setOldvalue(oldValue);

                    session.save(audit);
                    session.update(u);

                } else {
                    message = MessageVarList.COMMON_NOT_EXISTS;
                }

                if (message.isEmpty() && inputBean.getStatus().equals("ACT") && old_status.equals("DEACT")) {
                    this.updateStatus(inputBean.getTermsid(), inputBean.getVersionno(), txn, session);
                }
                txn.commit();
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

    public String deleteTerms(TermsInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            String sql = "select "
                    + "u.TERMS_VERSION "
                    + "from MS_CUSTOMER_WALLET_NIC u ";
            Query query = session.createSQLQuery(sql);
            List<Object[]> objectArrList = (List<Object[]>) query.list();

            if (inputBean.getTermsid().equals("ADULT")) {
                Termadult u = (Termadult) session.get(Termadult.class, inputBean.getVersionno());

                if (objectArrList.contains(inputBean.getVersionno())) {
                    message = "Version no already in stack";
                } else {
                    audit.setCreatetime(sysDate);
                    audit.setLastupdatedtime(sysDate);
                    session.delete(u);
                    session.save(audit);
                }

            } else if (inputBean.getTermsid().equals("TEEN")) {
                Termteen u = (Termteen) session.get(Termteen.class, inputBean.getVersionno());

                if (objectArrList.contains(inputBean.getVersionno())) {
                    message = "Version no already in stack";
                } else {
                    audit.setCreatetime(sysDate);
                    audit.setLastupdatedtime(sysDate);
                    session.delete(u);
                    session.save(audit);
                }
            }
            
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

    public String insertAdultTerms(TermsInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);
            txn = session.beginTransaction();
            if ((Termadult) session.get(Termadult.class, inputBean.getVersionnoadd()) == null) {

                Termadult city = new Termadult();
                city.setVersion_no(inputBean.getVersionnoadd());
                city.setTerms(inputBean.getDescriptionadd().trim());

                Status st = (Status) session.get(Status.class, inputBean.getStatusadd());
                city.setStatus(st);

                String newValue = "Adult"
                        + "|" + city.getVersion_no()
                        + "|" + city.getStatus().getDescription();

                audit.setNewvalue(newValue);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(city);

            } else {
                message = MessageVarList.COMMON_ALREADY_EXISTS;
            }
            if (message.isEmpty() && inputBean.getStatusadd().equals("ACT")) {
                this.updateStatus("ADULT", inputBean.getVersionnoadd(), txn, session);
            }
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

    public String insertTeenTerms(TermsInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);
            txn = session.beginTransaction();
            if ((Termteen) session.get(Termteen.class, inputBean.getVersionnoadd()) == null) {

                Termteen city = new Termteen();
                city.setVersion_no(inputBean.getVersionnoadd().trim());
                city.setTerms(inputBean.getDescriptionadd().trim());
                Status st = (Status) session.get(Status.class, CommonVarList.STATUS_ACTIVE);
                city.setStatus(st);
                String newValue = "Teen"
                        + "|" + city.getVersion_no()
                        + "|" + city.getStatus().getDescription();

                audit.setNewvalue(newValue);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(city);

            } else {
                message = MessageVarList.COMMON_ALREADY_EXISTS;
            }
            if (message.isEmpty() && inputBean.getStatusadd().equals("ACT")) {
                this.updateStatus("TEEN", inputBean.getVersionnoadd(), txn, session);
            }
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

    public void updateStatus(String type, String versionNo, Transaction txn, Session session) throws Exception {
        try {
            if (type.equals("ADULT")) {
                String hql = "UPDATE Termadult set status = :status WHERE version_no != :version_no ";
                Query query = session.createQuery(hql);
                query.setString("status", CommonVarList.STATUS_DEACTIVE);
                query.setString("version_no", versionNo);
                int result = query.executeUpdate();
            } else if (type.equals("TEEN")) {
                String hql = "UPDATE Termteen set status = :status "
                        + "WHERE version_no != :version_no";
                Query query = session.createQuery(hql);
                query.setString("status", CommonVarList.STATUS_DEACTIVE);
                query.setString("version_no", versionNo);
                int result = query.executeUpdate();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public TermsBean findTermsTeenById(String versionno) throws Exception {
        TermsBean dataBean = new TermsBean();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select "
                    + "u.status,"
                    + "u.terms, "
                    + "u.version_no "
                    + "from ms_terms_teen u where u.version_no='" + versionno + "'";
            Query query = session.createSQLQuery(sql);
//            System.err.println(sql);
            List<Object[]> objectArrList = (List<Object[]>) query.list();
            if (objectArrList.size() > 0) {

                for (Object[] objArr : objectArrList) {

                    try {
                        dataBean.setStatus(objArr[0].toString());
                    } catch (NullPointerException npe) {
                        dataBean.setStatus("");
                    }
                    try {
                        dataBean.setTerms(objArr[1].toString());
                    } catch (NullPointerException npe) {
                        dataBean.setTerms("");
                    }
                    try {
                        dataBean.setVersionno(objArr[2].toString());
                    } catch (NullPointerException npe) {
                        dataBean.setVersionno("");
                    }
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
        return dataBean;

    }

    public TermsBean findTermsAdultById(String versionno) throws Exception {
        TermsBean dataBean = new TermsBean();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "select "
                    + "u.status,"
                    + "u.terms, "
                    + "u.version_no "
                    + "from ms_terms_adult u where u.version_no='" + versionno + "'";
            Query query = session.createSQLQuery(sql);
//            System.err.println(sql);
            List<Object[]> objectArrList = (List<Object[]>) query.list();
            if (objectArrList.size() > 0) {

                for (Object[] objArr : objectArrList) {

                    try {
                        dataBean.setStatus(objArr[0].toString());
                    } catch (NullPointerException npe) {
                        dataBean.setStatus("0");
                    }
                    try {
                        dataBean.setTerms(objArr[1].toString());
                    } catch (NullPointerException npe) {
                        dataBean.setTerms("--");
                    }
                    try {
                        dataBean.setVersionno(objArr[2].toString());
                    } catch (NullPointerException npe) {
                        dataBean.setVersionno("--");
                    }
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
        return dataBean;

    }

    public boolean checkVersionExist(TermsInputBean inputBean) throws Exception {

        Session session = null;
        boolean vExists = false;

        try {
            session = HibernateInit.sessionFactory.openSession();
            if (inputBean.getTermsidadd().equals("ADULT")) {
                Termadult u = (Termadult) session.get(Termadult.class, inputBean.getVersionnoadd());
                if (u != null) {
                    vExists = true;
                } else {
                    vExists = false;
                }
            } else if (inputBean.getTermsidadd().equals("TEEN")) {
                Termteen u = (Termteen) session.get(Termteen.class, inputBean.getVersionnoadd());
                if (u != null) {
                    vExists = true;
                } else {
                    vExists = false;
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
        return vExists;
    }

}
