/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.usermanagement;

import com.rawmakyamu.bean.controlpanel.usermanagement.SystemUserDataBean;
import com.rawmakyamu.bean.controlpanel.usermanagement.SystemUserInputBean;

import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.Common;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.MsUserParam;
import com.rawmakyamu.util.mapping.Passwordpolicy;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Systemuser;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.mapping.Userrole;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author asitha_l
 */
public class SystemUserDAO {

    private String makeWhereClause(SystemUserInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getUsername() != null && !inputBean.getUsername().trim().isEmpty()) {
            where += " and lower(u.username) like lower('%" + inputBean.getUsername().trim() + "%')";
        }
        if (inputBean.getServiceid() != null && !inputBean.getServiceid().trim().isEmpty()) {
            where += " and lower(u.empid) like lower('%" + inputBean.getServiceid().trim() + "%')";
        }
        if (inputBean.getNic() != null && !inputBean.getNic().trim().isEmpty()) {
            where += " and lower(u.nic) like lower('%" + inputBean.getNic().trim() + "%')";
        }
        if (inputBean.getContactno() != null && !inputBean.getContactno().trim().isEmpty()) {
            where += " and lower(u.mobile) like lower('%" + inputBean.getContactno().trim() + "%')";
        }
        if (inputBean.getEmail() != null && !inputBean.getEmail().trim().isEmpty()) {
            where += " and lower(u.email) like lower('%" + inputBean.getEmail().trim() + "%')";
        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().trim().isEmpty()) {
            where += " and u.status.statuscode = '" + inputBean.getStatus().trim() + "'";
        }
        if (inputBean.getUserrole() != null && !inputBean.getUserrole().trim().isEmpty()) {
            where += " and u.userrole.userrolecode = '" + inputBean.getUserrole().trim() + "'";
        }
        where += " and u.userrole.userrolecode != '" + "MERCH" + "'";
        where += " and u.userrole.userrolecode != '" + "MERCUS" + "'";

        if (inputBean.getFullname() != null && !inputBean.getFullname().trim().isEmpty()) {
            where += " and lower(u.fullname) like lower('%" + inputBean.getFullname().trim() + "%')";
        }

        return where;
    }

    public List<SystemUserDataBean> getSearchList(SystemUserInputBean inputBean, int max, int first, String orderBy, String username) throws Exception {
        List<SystemUserDataBean> dataList = new ArrayList<SystemUserDataBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = "order by u.createtime desc";
            }
            long count = 0;

            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(username) from Systemuser as u where u.username!=:username and " + where;
            Query queryCount = session.createQuery(sqlCount).setString("username", username);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                String sqlSearch = "from Systemuser u where u.username!=:username and " + where + orderBy;

                Query querySearch = session.createQuery(sqlSearch).setString("username", username);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    SystemUserDataBean systemUser = new SystemUserDataBean();
                    Systemuser user = (Systemuser) it.next();

                    try {
                        systemUser.setUsername(user.getUsername().toString());
                    } catch (NullPointerException e) {
                        systemUser.setUsername("--");
                    }
                    try {
                        systemUser.setUsernameUserrole(user.getUsername() + "|" + user.getUserrole().getDescription().toString());
                    } catch (NullPointerException npe) {
                        systemUser.setUsernameUserrole("--");
                    }

                    try {
                        systemUser.setStatus(user.getStatus().getDescription().toString());
                    } catch (NullPointerException e) {
                        systemUser.setStatus("--");
                    }

                    try {
                        systemUser.setStatus(user.getStatus().getDescription().toString());
                    } catch (NullPointerException e) {
                        systemUser.setStatus("--");
                    }
                    try {
                        systemUser.setUserrole(user.getUserrole().getDescription().toString());
                    } catch (NullPointerException e) {
                        systemUser.setStatus("--");
                    }

                    try {
                        systemUser.setNic(user.getNic().toString());
                    } catch (NullPointerException e) {
                        systemUser.setNic("--");
                    }

                    try {
                        systemUser.setContactNo(user.getMobile().toString());
                    } catch (NullPointerException e) {
                        systemUser.setContactNo("--");
                    }
                    try {
                        systemUser.setFullname(user.getFullname().toString());
                    } catch (NullPointerException e) {
                        systemUser.setFullname("--");
                    }

                    try {
                        systemUser.setEmail(user.getEmail().toString());
                    } catch (NullPointerException e) {
                        systemUser.setEmail("--");
                    }
                    try {
                        systemUser.setServiceId(user.getEmpid().toString());
                    } catch (NullPointerException e) {
                        systemUser.setServiceId("--");
                    }
                    try {
                        if (user.getCreatetime().toString() != null && !user.getCreatetime().toString().isEmpty()) {
                            systemUser.setCreatetime(user.getCreatetime().toString().substring(0, 19));
                        } else {
                            systemUser.setCreatetime("--");
                        }
                    } catch (NullPointerException e) {
                        systemUser.setCreatetime("--");
                    }

                    systemUser.setFullCount(count);
                    dataList.add(systemUser);
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

    public String insertSystemUser(SystemUserInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        Calendar cal = Calendar.getInstance();
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if (!isSystemUserExist(inputBean.getUsername())) {
                txn = session.beginTransaction();

                Systemuser user = new Systemuser();
                user.setUsername(inputBean.getUsername());
                user.setPassword(Common.mpiMd5(inputBean.getPassword()));

                // modified (3/7/2014)
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                String sql = "select passwordexpiryperiod from Passwordpolicy";
                Query query = session.createQuery(sql);
                Iterator itCount = query.iterate();
                int expiryperiod = (Integer) itCount.next();
                cal.setTime(sysDate);
                cal.add(Calendar.DAY_OF_MONTH, expiryperiod);
                user.setExpirydate(cal.getTime());

                Userrole urole = (Userrole) session.get(Userrole.class, inputBean.getUserrole().trim());
                user.setUserrole(urole);

                user.setDualauthuserrole("admin");

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                user.setStatus(st);

                user.setFullname(inputBean.getFullname().trim());
                user.setEmpid(inputBean.getServiceid().trim());
                user.setAddress(inputBean.getAddress1().trim());
                user.setCity(inputBean.getCity().trim());
                user.setMobile(inputBean.getContactno().trim());
                user.setEmail(inputBean.getEmail().trim());
                user.setNic(inputBean.getNic().trim());

                String date_of_birth = null;
                if (!inputBean.getDateofbirth().isEmpty()) {
                    user.setDateofbirth(formatter2.parse(inputBean.getDateofbirth()));
                    date_of_birth = inputBean.getDateofbirth() + " 00:00:00";
                } else {
                    date_of_birth = "";
                }

                user.setNoofinvlidattempt("0");//edited

                String newValue = user.getUsername() + "|"
                        + user.getFullname() + "|"
                        + user.getNic() + "|"
                        + user.getUserrole().getDescription() + "|"
                        + user.getStatus().getDescription() + "|"
                        + user.getMobile() + "|"
                        + user.getEmail() + "|"
                        + user.getAddress() + "|"
                        + user.getCity() + "|"
                        + user.getEmpid() + "|"
                        + inputBean.getExpirydate().substring(0, 19) + "|"
                        + date_of_birth;

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);
                audit.setNewvalue(newValue);
                user.setLastupdateduser(audit.getLastupdateduser());

                user.setLastupdatedtime(sysDate);
                user.setLoggeddate(sysDate);
                user.setInitialloginstatus("0");
                user.setCreatetime(sysDate);
                session.save(audit);
                session.save(user);

                txn.commit();
            } else {

                long count = 0;

                String sqlCount = "select count(username) from Systemuser as u where u.status.statuscode=:statuscode AND u.username=:username";
                Query queryCount = session.createQuery(sqlCount).setString("statuscode", CommonVarList.STATUS_DELETE)
                        .setString("username", inputBean.getUsername().trim());

                Iterator itCount = queryCount.iterate();
                count = (Long) itCount.next();

                if (count > 0) {
                    message = "$" + inputBean.getUsername().trim();
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

    public Passwordpolicy getPasswordPolicyDetails() throws Exception {
        Passwordpolicy passwordpolicy = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Passwordpolicy.class);
            passwordpolicy = (Passwordpolicy) criteria.list().get(0);

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

    public boolean isSystemUserExist(String username) throws Exception {
        List<Systemuser> userList = new ArrayList<Systemuser>();
        Session session = null;
        boolean userCheckStatus = false;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Systemuser.class);
            criteria.add(Restrictions.eq("username", username));
            userList = (List<Systemuser>) criteria.list();

            for (Systemuser user : userList) {
                userCheckStatus = true;
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
        return userCheckStatus;
    }

    public Systemuser getSystemUserByUserName(String username) throws Exception {
        Systemuser user = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Systemuser as su where su.username=:username";

            Query query = session.createQuery(sql);
            query.setString("username", username);

            user = (Systemuser) query.list().get(0);

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
        return user;
    }

    public Systemuser getSystemUserByUserName2(String username, Session session) throws Exception {//call via update method
        Systemuser user = null;
        try {

            String sql = "from Systemuser as su where su.username=:username";

            Query query = session.createQuery(sql);
            query.setString("username", username);

            user = (Systemuser) query.list().get(0);

        } catch (Exception e) {
            throw e;
        } finally {
            try {
            } catch (Exception e) {
                throw e;
            }
        }
        return user;
    }

    public void findUsersByUserRole(SystemUserInputBean inputBean, int currUserLevel) throws Exception {
        List<Systemuser> userList = new ArrayList<Systemuser>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Systemuser.class);
            criteria.createAlias("userrole", "ur")
                    .createAlias("ur.userlevel", "ul");
            criteria.add(Restrictions.le("ul.levelid", currUserLevel));
            userList = (List<Systemuser>) criteria.list();

            for (Systemuser user : userList) {
                inputBean.getDualAuthUserMap().put(user.getUsername(), user.getUsername());
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

    public int getCurrUsersUserLevel(String userrole) throws Exception {
        Session session = null;
        Userrole userRole = null;
        int userLevel = 8;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Userrole.class);
            criteria.add(Restrictions.eq("userrolecode", userrole));
            userRole = (Userrole) criteria.list().get(0);
            userLevel = userRole.getUserlevel().getLevelid();

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
        return userLevel;
    }

    public MsUserParam setTooltip(String sectionCode) throws Exception {
        MsUserParam section = null;
        Session session = null;

        try {

            session = HibernateInit.sessionFactory.openSession();

            String sql = "from MsUserParam as u where u.paramcode=:code";
            Query query = session.createQuery(sql).setString("code", sectionCode);
            section = (MsUserParam) query.list().get(0);

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

    public String updateSystemUser(SystemUserInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        Systemuser user = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            txn = session.beginTransaction();

            user = this.getSystemUserByUserName2(inputBean.getUsername(), session);
            if (user.getAddress() == null) {
                user.setAddress("");
            }
            if (user.getCity() == null) {
                user.setCity("");
            }
            if (user.getEmpid() == null) {
                user.setEmpid("");
            }
            String date_of_birth = null;
            if (user.getDateofbirth() == null) {
                date_of_birth = "";
            } else {
                date_of_birth = user.getDateofbirth().toString().substring(0, 19);

            }

            String oldValue = user.getUsername() + "|"
                    + user.getFullname() + "|"
                    + user.getNic() + "|"
                    + user.getUserrole().getDescription() + "|"
                    + user.getStatus().getDescription() + "|"
                    + user.getMobile() + "|"
                    + user.getEmail() + "|"
                    + user.getAddress() + "|"
                    + user.getCity() + "|"
                    + user.getEmpid() + "|"
                    + user.getExpirydate().toString().substring(0, 19) + "|"
                    + date_of_birth;

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

            user.setDualauthuserrole(inputBean.getDualauthuser());

            user.setDualauthuserrole("admin");

            Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
            user.setStatus(st);

            Userrole urole = (Userrole) session.get(Userrole.class, inputBean.getUserrole().trim());
            user.setUserrole(urole);

            //if 'Active', change noofinvalidattempt to 0 and loggeddate to now
            if ((inputBean.getStatus()).equals(CommonVarList.STATUS_ACTIVE)) {
                user.setNoofinvlidattempt("0");
                user.setLoggeddate(sysDate);
            }

            user.setFullname(inputBean.getFullname().trim());
            user.setEmpid(inputBean.getServiceid().trim());
            user.setAddress(inputBean.getAddress1().trim());
            user.setCity(inputBean.getCity().trim());
            user.setMobile(inputBean.getContactno().trim());
            user.setEmail(inputBean.getEmail().trim());
            user.setNic(inputBean.getNic().trim());

            if (!inputBean.getDateofbirth().isEmpty()) {

                user.setDateofbirth(formatter2.parse(inputBean.getDateofbirth()));
                date_of_birth = inputBean.getDateofbirth() + " 00:00:00";

            } else {
                date_of_birth = "";
            }

            audit.setCreatetime(sysDate);
            audit.setLastupdatedtime(sysDate);

            Systemuser lastUpdatedUser = new Systemuser();
            lastUpdatedUser.setUsername(audit.getLastupdateduser());
            user.setLastupdateduser(audit.getLastupdateduser());

            user.setLastupdatedtime(sysDate);
            String newValue = user.getUsername() + "|"
                    + user.getFullname() + "|"
                    + user.getNic() + "|"
                    + user.getUserrole().getDescription() + "|"
                    + user.getStatus().getDescription() + "|"
                    + user.getMobile() + "|"
                    + user.getEmail() + "|"
                    + user.getAddress() + "|"
                    + user.getCity() + "|"
                    + user.getEmpid() + "|"
                    + user.getExpirydate().toString().substring(0, 19) + "|"
                    + date_of_birth;
            audit.setNewvalue(newValue);
            audit.setOldvalue(oldValue);

            session.save(audit);
            session.update(user);

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

    public String deleteSystemUser(SystemUserInputBean inputBean, Systemuser currentUser, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();

            Date sysDate = CommonDAO.getSystemDate(session);

            Systemuser user = (Systemuser) this.getSystemUserByUserName(inputBean.getUsername());

            // Check whether login user and requested to delete user are same            
            if (user.getUsername().equals(currentUser.getUsername())) {
                message = user.getUsername() + MessageVarList.SYSUSER_DEL_INVALID;
            } else {

                long flag = 0;
                String sql = "select count(systemauditid) from Systemaudit as u where u.lastupdateduser=:user";
                Query query = session.createQuery(sql).setString("user", inputBean.getUsername().trim());
                Iterator itCount1 = query.iterate();
                flag = (Long) itCount1.next();

                if (flag > 0) {
                    message = MessageVarList.COMMON_ALREADY_IN_USE;
                } else {

                    audit.setCreatetime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.delete(user);

                    txn.commit();
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

    public String updatePasswordReset(SystemUserInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Systemuser u = (Systemuser) session.get(Systemuser.class, inputBean.getHusername().trim());

            if (u != null) {

                u.setPassword(inputBean.getCrenewpwd());
//                Systemuser lastUpdatedUser = new Systemuser();
//                lastUpdatedUser.setUsername(audit.getLastupdateduser().getUsername());
                u.setLastupdateduser(audit.getLastupdateduser());
                u.setLastupdatedtime(sysDate);

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

    public String getUserroleDescriptionByUsername(String username) throws Exception {
        Session session = null;
        Transaction txn = null;
        String userrole = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Systemuser user = (Systemuser) session.get(Systemuser.class, username);

            // Check whether login user and requested to delete user are same            
            if (user != null) {
                userrole = user.getUserrole().getDescription();
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
        return userrole;
    }

    public String checkSystemUser(SystemUserInputBean inputBean, Systemuser currentUser) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();

            Systemuser user = (Systemuser) this.getSystemUserByUserName(inputBean.getUsername());

            if (user.getUsername().equals(currentUser.getUsername())) {
                message = user.getUsername() + MessageVarList.SYSUSER_DEL_INVALID;
            } else {

                long flag = 0;
                String sql = "select count(systemauditid) from Systemaudit as u where u.lastupdateduser=:user";
                Query query = session.createQuery(sql).setString("user", inputBean.getUsername().trim());
                Iterator itCount1 = query.iterate();
                flag = (Long) itCount1.next();

                if (flag > 0) {
                    message = MessageVarList.COMMON_ALREADY_IN_USE;
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

    public boolean isTasksExistByUserRole(String userRole) throws Exception {
        List<Task> taskList = null;
        Session session = null;
        boolean isTasks = false;

        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Pagetask as t where t.userrole.userrolecode =:userrolecode";
            Query query = session.createQuery(sql).setString("userrolecode", userRole);
            taskList = (List<Task>) query.list();

            if (taskList != null && !taskList.isEmpty()) {
                isTasks = true;
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
        return isTasks;
    }
}
