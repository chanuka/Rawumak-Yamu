/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.systemconfig;

import com.rawmakyamu.bean.controlpanel.systemconfig.ATMLocationsBean;
import com.rawmakyamu.bean.controlpanel.systemconfig.ATMLocationsInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.AtmLocations;
import com.rawmakyamu.util.mapping.Status;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.varlist.CommonVarList;
import com.rawmakyamu.util.varlist.MessageVarList;
import com.rawmakyamu.util.varlist.TaskVarList;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author jayana_i
 */
public class ATMLocationsDAO {

    public String findIdbyAtmcode(String atmcode) throws Exception {

        String taskDes = "";
        Session session = null;
        AtmLocations tk = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            if (atmcode != null) {

                String sql = "from AtmLocations as u where u.atmCode=:atmcode";
                Query query = session.createQuery(sql).setString("atmcode", atmcode);
                tk = (AtmLocations) query.list().get(0);
                taskDes = tk.getId().toString();
            } else {
                taskDes = "--";
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
        return taskDes;
    }

    public List<ATMLocationsBean> getSearchList(ATMLocationsInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<ATMLocationsBean> dataList = new ArrayList<ATMLocationsBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by u.createTime desc ";
            }
            long count = 0;

            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(id) from AtmLocations as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                String sqlSearch = "from AtmLocations u where " + where + orderBy;
                System.err.println(sqlSearch);
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    ATMLocationsBean atmlocations = new ATMLocationsBean();
                    AtmLocations location = (AtmLocations) it.next();

                    try {
                        atmlocations.setAtmcode(location.getAtmCode());
                    } catch (NullPointerException e) {
                        atmlocations.setAtmcode("--");
                    }
                    try {
                        atmlocations.setId(location.getId().toString());
                    } catch (NullPointerException e) {
                        atmlocations.setId("--");
                    }
                    try {
                        atmlocations.setDescription(location.getDescription());
                    } catch (NullPointerException e) {
                        atmlocations.setDescription("--");
                    }

                    try {
                        atmlocations.setStatus(location.getStatus().getDescription());
                    } catch (NullPointerException e) {
                        atmlocations.setStatus("--");
                    }

                    try {
                        atmlocations.setAddress(location.getAddress());
                    } catch (Exception e) {
                        atmlocations.setAddress("--");
                    }

                    try {
                        atmlocations.setLatitude(location.getLatitude());
                    } catch (Exception e) {
                        atmlocations.setLatitude("--");
                    }

                    try {
                        atmlocations.setLongitude(location.getLongitude());
                    } catch (Exception e) {
                        atmlocations.setLongitude("--");
                    }
                    try {
                        atmlocations.setCreatedtime(location.getCreateTime().toString().substring(0, 19));
                    } catch (Exception e) {
                        atmlocations.setCreatedtime("--");
                    }

                    try {
                        atmlocations.setIsAtm(location.getStatusByIsAtm().getDescription());
                    } catch (NullPointerException e) {
                        atmlocations.setIsAtm("--");
                    }
                    try {
                        atmlocations.setIsCdm(location.getStatusByIsCdm().getDescription());
                    } catch (NullPointerException e) {
                        atmlocations.setIsCdm("--");
                    }
                    try {
                        atmlocations.setIsBranch(location.getStatusByIsBranch().getDescription());
                    } catch (NullPointerException e) {
                        atmlocations.setIsBranch("--");
                    }
                    try {
                        atmlocations.setIsAgent(location.getStatusByIsAgent().getDescription());
                    } catch (NullPointerException e) {
                        atmlocations.setIsAgent("--");
                    }
                    try {
                        atmlocations.setIsShop(location.getStatusByIsShop().getDescription());
                    } catch (NullPointerException e) {
                        atmlocations.setIsShop("--");
                    }

                    atmlocations.setFullCount(count);
                    dataList.add(atmlocations);
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

    public boolean isexsitsStatus(String statuscode) throws Exception {
        Session session = null;
        boolean isexsit = false;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sqlCount = "select count(statuscode) from Status as u where u.statuscode=:mid";
            Query queryCount = session.createQuery(sqlCount).setString("mid", statuscode);
            Iterator itCount = queryCount.iterate();
            long count = (Long) itCount.next();
            System.err.println("count status " + count);
            if (count == 1) {
                isexsit = true;
            } else {
                isexsit = false;
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
        return isexsit;
    }

    public String uploadATMLocations(ATMLocationsInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;

        String message = "";

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            String sqlCount = "select count(id) from AtmLocations as u where u.atmCode=:atmcode";
            Query queryCount = session.createQuery(sqlCount).setString("atmcode", inputBean.getAtmcode());
            Iterator itCount = queryCount.iterate();
            long count = (Long) itCount.next();

            if (count == 0) {
//            if(u==null){
//                insertATMLocations(inputBean, audit);
                txn = session.beginTransaction();
                AtmLocations ibnotifyBean = new AtmLocations();

                ibnotifyBean.setAtmCode(inputBean.getAtmcode());
                ibnotifyBean.setDescription(inputBean.getDescription().trim());
                ibnotifyBean.setAddress(inputBean.getAddress());

                ibnotifyBean.setCreateTime(sysDate);
                ibnotifyBean.setLastupdatedTime(sysDate);

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                ibnotifyBean.setStatus(st);

                ibnotifyBean.setLongitude(inputBean.getLongitude());
                ibnotifyBean.setLatitude(inputBean.getLatitude());

                Status stAtm = (Status) session.get(Status.class, inputBean.getIsAtmUp().trim());
                ibnotifyBean.setStatusByIsAtm(stAtm);

                Status stCdm = (Status) session.get(Status.class, inputBean.getIsCdmUp().trim());
                ibnotifyBean.setStatusByIsCdm(stCdm);

                Status stBranch = (Status) session.get(Status.class, inputBean.getIsBranchUp().trim());
                ibnotifyBean.setStatusByIsBranch(stBranch);

                Status stAgent = (Status) session.get(Status.class, inputBean.getIsAgentUp().trim());
                ibnotifyBean.setStatusByIsAgent(stAgent);

                Status stShop = (Status) session.get(Status.class, inputBean.getIsShopUp().trim());
                ibnotifyBean.setStatusByIsShop(stShop);

                String newV = ibnotifyBean.getAtmCode() + "|"
                        + ibnotifyBean.getDescription() + "|"
                        + ibnotifyBean.getAddress() + "|"
                        + ibnotifyBean.getLatitude() + "|"
                        + ibnotifyBean.getLongitude() + "|"
                        + ibnotifyBean.getStatus().getDescription() + "|"
                        + ibnotifyBean.getStatusByIsAtm().getDescription() + "|"
                        + ibnotifyBean.getStatusByIsCdm().getDescription() + "|"
                        + ibnotifyBean.getStatusByIsBranch().getDescription() + "|"
                        + ibnotifyBean.getStatusByIsAgent().getDescription() + "|"
                        + ibnotifyBean.getStatusByIsShop().getDescription();

                audit.setNewvalue(newV);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);
                audit.setDescription("Location code " + inputBean.getAtmcode() + " added via file : " + inputBean.getFilaname() + " by " + audit.getLastupdateduser());

                session.save(audit);
                session.save(ibnotifyBean);

                txn.commit();

            } else {
                inputBean.setId(findATMLocationsByAtmcode(inputBean.getAtmcode()));
                AtmLocations u = (AtmLocations) session.get(AtmLocations.class, new BigDecimal(inputBean.getId().trim()));

//                updateATMLocations(inputBean, audit);
                txn = session.beginTransaction();

                String oldV = u.getAtmCode() + "|"
                        + u.getDescription() + "|"
                        + u.getAddress() + "|"
                        + u.getLatitude() + "|"
                        + u.getLongitude() + "|"
                        + u.getStatus().getDescription() + "|"
                        + u.getStatusByIsAtm().getDescription() + "|"
                        + u.getStatusByIsCdm().getDescription() + "|"
                        + u.getStatusByIsBranch().getDescription() + "|"
                        + u.getStatusByIsAgent().getDescription() + "|"
                        + u.getStatusByIsShop().getDescription();

                u.setAtmCode(inputBean.getAtmcode());
                u.setDescription(inputBean.getDescription().trim());
                u.setAddress(inputBean.getAddress());

                u.setCreateTime(sysDate);
                u.setLastupdatedTime(sysDate);

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                u.setStatus(st);

                u.setLongitude(inputBean.getLongitude());
                u.setLatitude(inputBean.getLatitude());

                Status stAtm = (Status) session.get(Status.class, inputBean.getIsAtmUp().trim());
                u.setStatusByIsAtm(stAtm);

                Status stCdm = (Status) session.get(Status.class, inputBean.getIsCdmUp().trim());
                u.setStatusByIsCdm(stCdm);

                Status stBranch = (Status) session.get(Status.class, inputBean.getIsBranchUp().trim());
                u.setStatusByIsBranch(stBranch);

                Status stAgent = (Status) session.get(Status.class, inputBean.getIsAgentUp().trim());
                u.setStatusByIsAgent(stAgent);

                Status stShop = (Status) session.get(Status.class, inputBean.getIsShopUp().trim());
                u.setStatusByIsShop(stShop);

                String newV = u.getAtmCode() + "|"
                        + u.getDescription() + "|"
                        + u.getAddress() + "|"
                        + u.getLatitude() + "|"
                        + u.getLongitude() + "|"
                        + u.getStatus().getDescription() + "|"
                        + u.getStatusByIsAtm().getDescription() + "|"
                        + u.getStatusByIsCdm().getDescription() + "|"
                        + u.getStatusByIsBranch().getDescription() + "|"
                        + u.getStatusByIsAgent().getDescription() + "|"
                        + u.getStatusByIsShop().getDescription();

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);
                audit.setNewvalue(newV);
                audit.setOldvalue(oldV);
                audit.setTaskcode(TaskVarList.UPDATE_TASK);
                audit.setDescription("Location code " + inputBean.getAtmcode() + " updated via file : " + inputBean.getFilaname() + " by " + audit.getLastupdateduser());

                session.save(audit);
                session.update(u);

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

    public String insertATMLocations(ATMLocationsInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            String sqlCount = "select count(id) from AtmLocations as u where u.atmCode=:atmcode";
            Query queryCount = session.createQuery(sqlCount).setString("atmcode", inputBean.getAtmcode());
            Iterator itCount = queryCount.iterate();
            long count = (Long) itCount.next();

            System.err.println("count : " + count);

            if (count == 0) {
                txn = session.beginTransaction();
                AtmLocations ibnotifyBean = new AtmLocations();

                ibnotifyBean.setAtmCode(inputBean.getAtmcode());
                ibnotifyBean.setDescription(inputBean.getDescription().trim());
                ibnotifyBean.setAddress(inputBean.getAddress());

                ibnotifyBean.setCreateTime(sysDate);
                ibnotifyBean.setLastupdatedTime(sysDate);

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                ibnotifyBean.setStatus(st);

                ibnotifyBean.setLongitude(inputBean.getLongitude());
                ibnotifyBean.setLatitude(inputBean.getLatitude());

                if (inputBean.isIsAtm()) {
                    Status satm = (Status) session.get(Status.class, CommonVarList.STATUS_YES);
                    ibnotifyBean.setStatusByIsAtm(satm);
                } else {
                    Status satm = (Status) session.get(Status.class, CommonVarList.STATUS_NO);
                    ibnotifyBean.setStatusByIsAtm(satm);
                }

                if (inputBean.isIsCdm()) {
                    Status scdm = (Status) session.get(Status.class, CommonVarList.STATUS_YES);
                    ibnotifyBean.setStatusByIsCdm(scdm);
                } else {
                    Status scdm = (Status) session.get(Status.class, CommonVarList.STATUS_NO);
                    ibnotifyBean.setStatusByIsCdm(scdm);
                }

                if (inputBean.isIsBranch()) {
                    Status sbranch = (Status) session.get(Status.class, CommonVarList.STATUS_YES);
                    ibnotifyBean.setStatusByIsBranch(sbranch);
                } else {
                    Status sbranch = (Status) session.get(Status.class, CommonVarList.STATUS_NO);
                    ibnotifyBean.setStatusByIsBranch(sbranch);
                }

                if (inputBean.isIsAgent()) {
                    Status sagent = (Status) session.get(Status.class, CommonVarList.STATUS_YES);
                    ibnotifyBean.setStatusByIsAgent(sagent);
                } else {
                    Status sagent = (Status) session.get(Status.class, CommonVarList.STATUS_NO);
                    ibnotifyBean.setStatusByIsAgent(sagent);
                }

                if (inputBean.isIsShop()) {
                    Status sshop = (Status) session.get(Status.class, CommonVarList.STATUS_YES);
                    ibnotifyBean.setStatusByIsShop(sshop);
                } else {
                    Status sshop = (Status) session.get(Status.class, CommonVarList.STATUS_NO);
                    ibnotifyBean.setStatusByIsShop(sshop);
                }

                String newValue
                        = ibnotifyBean.getAtmCode() + "|"
                        + ibnotifyBean.getDescription() + "|"
                        + ibnotifyBean.getAddress() + "|"
                        + ibnotifyBean.getLatitude() + "|"
                        + ibnotifyBean.getLongitude() + "|"
                        + ibnotifyBean.getStatus().getDescription() + "|"
                        + ibnotifyBean.getStatusByIsAtm().getDescription() + "|"
                        + ibnotifyBean.getStatusByIsCdm().getDescription() + "|"
                        + ibnotifyBean.getStatusByIsBranch().getDescription() + "|"
                        + ibnotifyBean.getStatusByIsAgent().getDescription() + "|"
                        + ibnotifyBean.getStatusByIsShop().getDescription();

                audit.setCreatetime(sysDate);
                audit.setNewvalue(newValue);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.saveOrUpdate(ibnotifyBean);

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

    public AtmLocations findATMLocationsById(String id) throws Exception {
        AtmLocations atmlocation = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from AtmLocations as u where u.id=:id";
            Query query = session.createQuery(sql).setString("id", id);
            atmlocation = (AtmLocations) query.list().get(0);

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
        return atmlocation;

    }

    public String findATMLocationsByAtmcode(String atmcode) throws Exception {

        String txnTypeCode = null;
        Session session = null;
        long count;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from AtmLocations as u where u.atmCode=:atmcode";
            Query query = session.createQuery(sql).setString("atmcode", atmcode);
            count = query.list().size();
            if (count > 0) {
                txnTypeCode = ((AtmLocations) query.list().get(0)).getId().toString();
            } else {
                txnTypeCode = "";

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
        return txnTypeCode;
    }

//    public AtmLocations findATMLocationsByAtmcode(String atmcode) throws Exception {
//        AtmLocations atmlocation = null;
//        Session session = null;
//        try {
//            session = HibernateInit.sessionFactory.openSession();
//
//            String sql = "from AtmLocations as u where u.atmCode=:atmcode";
//            Query query = session.createQuery(sql).setString("atmcode", atmcode);
//            atmlocation = (AtmLocations) query.list().get(0);
//
//            if(atmlocation!=null){
//                
//            }
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            try {
//                session.flush();
//                session.close();
//            } catch (Exception e) {
//                throw e;
//            }
//        }
//        return atmlocation;
//
//    }
    public String updateATMLocations(ATMLocationsInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            inputBean.setId(findIdbyAtmcode(inputBean.getUatmcode()));
            System.err.println(inputBean.getId());

            AtmLocations u = (AtmLocations) session.get(AtmLocations.class, new BigDecimal(inputBean.getId().trim()));

            if (u != null) {

                String oldValue
                        = u.getAtmCode() + "|"
                        + u.getDescription() + "|"
                        + u.getAddress() + "|"
                        + u.getLatitude() + "|"
                        + u.getLongitude() + "|"
                        + u.getStatus().getDescription() + "|"
                        + u.getStatusByIsAtm().getDescription() + "|"
                        + u.getStatusByIsCdm().getDescription() + "|"
                        + u.getStatusByIsBranch().getDescription() + "|"
                        + u.getStatusByIsAgent().getDescription() + "|"
                        + u.getStatusByIsShop().getDescription() + "|";

                u.setAtmCode(inputBean.getUatmcode().trim());
                u.setDescription(inputBean.getUdescription().trim());
                u.setAddress(inputBean.getUaddress());
                u.setLatitude(inputBean.getUlatitude());
                u.setLongitude(inputBean.getUlongitude());

                Status st = (Status) session.get(Status.class, inputBean.getUstatus().trim());
                u.setStatus(st);

                if (inputBean.isIsAtm()) {
                    Status satm = (Status) session.get(Status.class, CommonVarList.STATUS_YES);
                    u.setStatusByIsAtm(satm);
                } else {
                    Status satm = (Status) session.get(Status.class, CommonVarList.STATUS_NO);
                    u.setStatusByIsAtm(satm);
                }

                if (inputBean.isIsCdm()) {
                    Status scdm = (Status) session.get(Status.class, CommonVarList.STATUS_YES);
                    u.setStatusByIsCdm(scdm);
                } else {
                    Status scdm = (Status) session.get(Status.class, CommonVarList.STATUS_NO);
                    u.setStatusByIsCdm(scdm);
                }

                if (inputBean.isIsBranch()) {
                    Status sbranch = (Status) session.get(Status.class, CommonVarList.STATUS_YES);
                    u.setStatusByIsBranch(sbranch);
                } else {
                    Status sbranch = (Status) session.get(Status.class, CommonVarList.STATUS_NO);
                    u.setStatusByIsBranch(sbranch);
                }

                if (inputBean.isIsAgent()) {
                    Status sagent = (Status) session.get(Status.class, CommonVarList.STATUS_YES);
                    u.setStatusByIsAgent(sagent);
                } else {
                    Status sagent = (Status) session.get(Status.class, CommonVarList.STATUS_NO);
                    u.setStatusByIsAgent(sagent);
                }

                if (inputBean.isIsShop()) {
                    Status sshop = (Status) session.get(Status.class, CommonVarList.STATUS_YES);
                    u.setStatusByIsShop(sshop);
                } else {
                    Status sshop = (Status) session.get(Status.class, CommonVarList.STATUS_NO);
                    u.setStatusByIsShop(sshop);
                }

                u.setLastupdatedTime(sysDate);

                String newValue
                        = u.getAtmCode() + "|"
                        + u.getDescription() + "|"
                        + u.getAddress() + "|"
                        + u.getLatitude() + "|"
                        + u.getLongitude() + "|"
                        + u.getStatus().getDescription() + "|"
                        + u.getStatusByIsAtm().getDescription() + "|"
                        + u.getStatusByIsCdm().getDescription() + "|"
                        + u.getStatusByIsBranch().getDescription() + "|"
                        + u.getStatusByIsAgent().getDescription() + "|"
                        + u.getStatusByIsShop().getDescription() + "|";

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);
                audit.setOldvalue(oldValue);
                audit.setNewvalue(newValue);

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

    public String deleteATMLocation(ATMLocationsInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            AtmLocations u = (AtmLocations) session.get(AtmLocations.class, new BigDecimal(inputBean.getId()));

            if (u != null) {

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.delete(u);
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

    private String makeWhereClause(ATMLocationsInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getSatmcode() != null && !inputBean.getSatmcode().isEmpty()) {
            where += " and lower(u.atmCode) like lower('%" + inputBean.getSatmcode().trim() + "%')";
        }
        if (inputBean.getSdescription() != null && !inputBean.getSdescription().isEmpty()) {
            where += " and lower(u.description) like lower('%" + inputBean.getSdescription() + "%')";
        }

        if (inputBean.getStatus() != null && !inputBean.getStatus().isEmpty()) {
            where += " and u.status.statuscode = '" + inputBean.getStatus() + "'";
        }
        if (inputBean.getSstatusATM() != null && !inputBean.getSstatusATM().isEmpty()) {
            where += " and u.statusByIsAtm.statuscode = '" + inputBean.getSstatusATM() + "'";
        }
        if (inputBean.getSstatusCDM() != null && !inputBean.getSstatusCDM().isEmpty()) {
            where += " and u.statusByIsCdm.statuscode = '" + inputBean.getSstatusCDM() + "'";
        }
        if (inputBean.getSstatusBranch() != null && !inputBean.getSstatusBranch().isEmpty()) {
            where += " and u.statusByIsBranch.statuscode = '" + inputBean.getSstatusBranch() + "'";
        }
        if (inputBean.getSstatusAgent() != null && !inputBean.getSstatusAgent().isEmpty()) {
            where += " and u.statusByIsAgent.statuscode = '" + inputBean.getSstatusAgent() + "'";
        }
        if (inputBean.getSstatusShop() != null && !inputBean.getSstatusShop().isEmpty()) {
            where += " and u.statusByIsShop.statuscode = '" + inputBean.getSstatusShop() + "'";
        }

        return where;
    }

}
