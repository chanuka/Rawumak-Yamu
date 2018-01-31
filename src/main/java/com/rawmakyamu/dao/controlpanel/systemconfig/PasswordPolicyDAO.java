/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.systemconfig;

import com.rawmakyamu.bean.controlpanel.systemconfig.PasswordPolicyInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.MsUserParam;
import com.rawmakyamu.util.mapping.Passwordpolicy;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Userpassword;
import com.rawmakyamu.util.varlist.MessageVarList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author thushanth
 */
public class PasswordPolicyDAO {

    public Passwordpolicy getPasswordPolicyDetails() throws Exception {
        Passwordpolicy ipgpasswordpolicy = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Passwordpolicy ";

            Query query = session.createQuery(sql);

            ipgpasswordpolicy = (Passwordpolicy) query.list().get(0);

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
        return ipgpasswordpolicy;
    }

    public boolean isExistPasswordPolicy() throws Exception {
        long count = 0;
        boolean status = false;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "select count(passwordpolicyid) from Passwordpolicy";

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

    public Passwordpolicy findPasswordPolicyById(String passwordpolicyid) throws Exception {
        Passwordpolicy passwordpolicy = new Passwordpolicy();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String hql = "from Passwordpolicy as m where m.passwordpolicyid=:passwordpolicyid";
            Query query = session.createQuery(hql).setString("passwordpolicyid", passwordpolicyid);
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

    public String insertPasswordPolicy(PasswordPolicyInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((Passwordpolicy) session.get(Passwordpolicy.class, new Integer(inputBean.getPasswordpolicyid().trim())) == null) {
                txn = session.beginTransaction();

                Passwordpolicy i = new Passwordpolicy();

                /*
                 * private int minimumlength;
                 private int maximumlength;
                 private Integer minimumspecialcharacters;
                 private Integer minimumuppercasecharacters;
                 private Integer minimumnumericalcharacters;
                 private Integer minimumlowercasecharacters;
                 private Integer repeatcharactersallow;
                 private Integer initialpasswordexpirystatus;
                 private int passwordexpiryperiod;
                 private Integer noofhistorypassword;
                 private Integer minimumpasswordchangeperiod;
                 private Integer idleaccountexpiryperiod;
                 private int noofinvalidloginattempt;
                 * */
                i.setPasswordpolicyid(new Integer(inputBean.getPasswordpolicyid()));
                i.setMinimumlength(new Integer(inputBean.getMinimumlength()));
                i.setMaximumlength(new Integer(inputBean.getMaximumlength()));
                i.setMinimumspecialcharacters(new Integer(inputBean.getMinimumspecialcharacters()));
                i.setMinimumuppercasecharacters(new Integer(inputBean.getMinimumuppercasecharacters()));
                i.setMinimumnumericalcharacters(new Integer(inputBean.getMinimumnumericalcharacters()));
                i.setMinimumlowercasecharacters(new Integer(inputBean.getMinimumlowercasecharacters()));
                i.setRepeatcharactersallow(new Integer(inputBean.getRepeatcharactersallow()));
                i.setInitialpasswordexpirystatus("1");
                i.setPasswordexpiryperiod(new Integer(inputBean.getPasswordexpiryperiod()));
                i.setNoofhistorypassword(new Integer(inputBean.getNoofhistorypassword()));
                i.setMinimumpasswordchangeperiod(new Integer(inputBean.getMinimumpasswordchangeperiod()));
                i.setIdleaccountexpiryperiod(new Integer(inputBean.getIdleaccountexpiryperiod()));
                i.setNoofinvalidloginattempt(new Integer(inputBean.getNoofinvalidloginattempt()));

                i.setCreatetime(sysDate);
                i.setLastupdatedtime(sysDate);
                i.setLastupdateduser(audit.getLastupdateduser());

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(i);

                txn.commit();
            } else {
                message = MessageVarList.COMMON_ALREADY_EXISTS;
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

    public void updateHistory(PasswordPolicyInputBean inputBean, Session session) throws Exception {
        List<Userpassword> kj = new ArrayList<Userpassword>();
        List<Object[]> uname = null;

        String hql = "select m.id.username as uname, count(m.id.username) as count from Userpassword as m "
                + "group by m.id.username "
                + "having count(m.id.username) >:noofrec ";

        Query query = session.createQuery(hql).setString("noofrec", inputBean.getNoofhistorypassword());
        uname = query.list();

        for (Object[] var : uname) {
            String hql2 = "from Userpassword as s where s.id.username =:username"
                    + " order by s.lastupdatedtime asc";

            Query query2 = session.createQuery(hql2).setString("username", (String) var[0]);
            query2.setFirstResult(0);
            query2.setMaxResults((int) (long) (Long) var[1] - new Integer(inputBean.getNoofhistorypassword()));
            kj.addAll(query2.list());
        }

//                 String hql2 = "from Userpassword as s where s.id.username in "
//                + "( select m.id.username from Userpassword as m "
//                + "group by m.id.username "
//                + "having count(m.id.username) > 1) "
//                + "order by s.lastupdatedtime asc";
        for (Userpassword j : kj) {
            session.delete(j);
            session.flush();
        }
    }

    public String updatePasswordPolicy(PasswordPolicyInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Passwordpolicy i = (Passwordpolicy) session.get(Passwordpolicy.class, new Integer(inputBean.getPasswordpolicyid().trim()));

            if (i != null) {
                updateHistory(inputBean, session);

                String oldV = i.getPasswordpolicyid() + "|"
                        + i.getMinimumlength() + "|"
                        + i.getMaximumlength() + "|"
                        + i.getMinimumspecialcharacters() + "|"
                        + i.getMinimumuppercasecharacters() + "|"
                        + i.getMinimumlowercasecharacters() + "|"
                        + i.getMinimumnumericalcharacters() + "|"
                        + i.getRepeatcharactersallow() + "|"
                        + i.getPasswordexpiryperiod() + "|"
                        + i.getMinimumpasswordchangeperiod() + "|"
                        + i.getNoofhistorypassword() + "|"
                        + i.getIdleaccountexpiryperiod() + "|"
                        + i.getNoofinvalidloginattempt();

                i.setMinimumlength(new Integer(inputBean.getMinimumlength().trim()));
                i.setMaximumlength(new Integer(inputBean.getMaximumlength().trim()));
                i.setMinimumspecialcharacters(new Integer(inputBean.getMinimumspecialcharacters().trim()));
                i.setMinimumuppercasecharacters(new Integer(inputBean.getMinimumuppercasecharacters().trim()));
                i.setMinimumnumericalcharacters(new Integer(inputBean.getMinimumnumericalcharacters().trim()));
                i.setMinimumlowercasecharacters(new Integer(inputBean.getMinimumlowercasecharacters().trim()));
                i.setRepeatcharactersallow(new Integer(inputBean.getRepeatcharactersallow().trim()));
                i.setInitialpasswordexpirystatus("1");
                i.setPasswordexpiryperiod(new Integer(inputBean.getPasswordexpiryperiod().trim()));
                i.setNoofhistorypassword(new Integer(inputBean.getNoofhistorypassword().trim()));
                i.setMinimumpasswordchangeperiod(new Integer(inputBean.getMinimumpasswordchangeperiod().trim()));
                i.setIdleaccountexpiryperiod(new Integer(inputBean.getIdleaccountexpiryperiod().trim()));
                i.setNoofinvalidloginattempt(new Integer(inputBean.getNoofinvalidloginattempt().trim()));

                i.setCreatetime(sysDate);
                i.setLastupdatedtime(sysDate);
                i.setLastupdateduser(audit.getLastupdateduser());

                String newV = i.getPasswordpolicyid() + "|"
                        + i.getMinimumlength() + "|"
                        + i.getMaximumlength() + "|"
                        + i.getMinimumspecialcharacters() + "|"
                        + i.getMinimumuppercasecharacters() + "|"
                        + i.getMinimumlowercasecharacters() + "|"
                        + i.getMinimumnumericalcharacters() + "|"
                        + i.getRepeatcharactersallow() + "|"
                        + i.getPasswordexpiryperiod() + "|"
                        + i.getMinimumpasswordchangeperiod() + "|"
                        + i.getNoofhistorypassword() + "|"
                        + i.getIdleaccountexpiryperiod() + "|"
                        + i.getNoofinvalidloginattempt();

                audit.setOldvalue(oldV);
                audit.setNewvalue(newV);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                String tooltip = "";

                for (int minx = 0; minx < i.getMinimumlength(); minx++) {

                    for (int x = 0; x < i.getMinimumspecialcharacters(); x++) {
                        if (tooltip.length() < i.getMinimumlength()) {
                            tooltip = tooltip + (char) (42 + x + minx);
                        } else {
                            break;
                        }
                    }
                    for (int x = 0; x < i.getMinimumuppercasecharacters(); x++) {
                        if (tooltip.length() < i.getMinimumlength()) {
                            tooltip = tooltip + (char) (65 + x + minx);
                        } else {
                            break;
                        }
                    }
                    for (int x = 0; x < i.getMinimumlowercasecharacters(); x++) {
                        if (tooltip.length() < i.getMinimumlength()) {
                            tooltip = tooltip + (char) (97 + x + minx);
                        } else {
                            break;
                        }
                    }
                    for (int x = 0; x < i.getMinimumnumericalcharacters(); x++) {
                        if (tooltip.length() < i.getMinimumlength()) {
                            tooltip = tooltip + (char) (48 + x + minx);
                        } else {
                            break;
                        }
                    }

                }
                System.err.println(tooltip);
                System.err.println(this.generateToolTipMessage(i));
                updateToolTip(tooltip);

                session.save(audit);
                session.update(i);

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

    public String generateToolTipMessage(Passwordpolicy ppolicy) {
        
        String tooltip = null;
//                "Minimum length should be equal or greater than "+ppolicy.getMinimumlength()+"<br/>";
        tooltip="Maximum "+ppolicy.getMaximumlength()+" character(s) <br/>";
        tooltip=tooltip+"At least "+ppolicy.getMinimumspecialcharacters()+" special character(s)<br/>";
        tooltip=tooltip+"At least "+ppolicy.getMinimumuppercasecharacters()+" upper case character(s)<br/>";
        tooltip=tooltip+"At least "+ppolicy.getMinimumlowercasecharacters()+" lower case character(s)<br/>";
        tooltip=tooltip+"At least "+ppolicy.getMinimumnumericalcharacters()+" numeric character(s)<br/>";
        if(ppolicy.getRepeatcharactersallow()<2){
            tooltip=tooltip+"Password must not contain repeat characters <br/>";
        }else{
            tooltip=tooltip+"Maximum  "+ppolicy.getRepeatcharactersallow()+" repeat character(s)<br/>";
//        System.err.println(tooltip);
        }
        return tooltip;
    }

    public void updateToolTip(String tooltip) throws Exception {
        Session session = null;
        Transaction txn = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            MsUserParam u = (MsUserParam) session.get(MsUserParam.class, "PWTOOLTIP");
            if (u != null) {
                u.setParamValue("Example : " + tooltip);
                session.update(u);
                txn.commit();
            } else {

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

    }

    public Status getStatusDes(Status statusCode) throws Exception {

        Status status = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Status as s where s.statuscode=:status";
            Query query = session.createQuery(sql).setString("status", statusCode.getStatuscode());
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
}
