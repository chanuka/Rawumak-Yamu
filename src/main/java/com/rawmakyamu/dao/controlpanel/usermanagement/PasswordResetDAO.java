/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.dao.controlpanel.usermanagement;

import com.rawmakyamu.bean.controlpanel.usermanagement.PasswordResetInputBean;
import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.common.HibernateInit;
import com.rawmakyamu.util.mapping.Passwordpolicy;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Systemuser;
import com.rawmakyamu.util.mapping.Userpassword;
import com.rawmakyamu.util.mapping.UserpasswordId;
import com.rawmakyamu.util.varlist.MessageVarList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @Document : PasswordResetDAO
 * @Created on : Mar 7, 2014, 1:18:40 PM
 * @author : thushanth
 */
public class PasswordResetDAO {

    //password reset user 
    public String UpdatePasswordReset(PasswordResetInputBean inputBean, Systemaudit audit, Passwordpolicy passPolicy) throws Exception {
        Session session = null;
        Transaction txn = null;
        Calendar cal = Calendar.getInstance();  
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            String sql = "from Systemuser as u where u.username=:username";
            Query query = session.createQuery(sql).setString("username", inputBean.getHusername().trim());
            Systemuser u = (Systemuser) query.list().get(0);
            
            String sql2 = "from Userpassword as u where u.id.username=:username order by u.lastupdatedtime asc";
//            String sql2 = "from Userpassword as u where u.id.username=:username";
            Query query2 = session.createQuery(sql2).setString("username", inputBean.getHusername().trim());
            List<Userpassword> upassList = query2.list();
            
            // update password expiry period (inserted) 
            String sql3 = "select passwordexpiryperiod from Passwordpolicy";
            Query query3 = session.createQuery(sql3);
            Iterator itCount = query3.iterate();
            int expiryperiod = (Integer) itCount.next();
            
            // check whether newpassword exists in history
            for(Userpassword up : upassList){
                if(inputBean.getRenewpwd().equals(up.getId().getPassword())){
                    if( upassList.indexOf(up) == 0 && upassList.size() == passPolicy.getNoofhistorypassword() ){                    
                        break;
                    }
                    message=MessageVarList.RESET_PASS_NEW_EXIST;                            
                    return message;
                }                
            }           
            
            
            if (u != null && !inputBean.getCurrpwd().equals(inputBean.getRenewpwd())) {
                u.setPassword(inputBean.getRenewpwd());
                // this is not the first time logged in
                u.setInitialloginstatus("1");
                u.setLastupdateduser(audit.getLastupdateduser());
                u.setLastupdatedtime(sysDate);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);
                
                // update password expiry period (inserted)
                cal.setTime(sysDate);
                cal.add(Calendar.DAY_OF_MONTH, expiryperiod);
                u.setExpirydate(cal.getTime());
                
                session.save(audit);
                session.update(u);
//                 txn.commit();
                session.flush();
//                session.close();              

                
                if (upassList.size() < passPolicy.getNoofhistorypassword()) {
                    if ((Userpassword) session.get(Userpassword.class, new UserpasswordId(inputBean.getHusername(), inputBean.getCurrpwd())) == null) {
                        Userpassword epass = new Userpassword();
                        UserpasswordId upid = new UserpasswordId();
                        upid.setUsername(inputBean.getHusername());
                        upid.setPassword(inputBean.getCurrpwd());
                        epass.setId(upid);
//                        epass.setUserByLastupdateduser(audit.getLastupdateduser());
//                        epass.setLastupdatedtime(sysDate);8888888888888888888888888888888888888888
                        session.save(epass);
                       
                    } else {  
                        //if current password exists in 'smsuserpassword', then update
//                        message = MessageVarList.RESET_PASS_CURRENT_EXIST;                         
                        Userpassword up=(Userpassword) session.get(Userpassword.class, new UserpasswordId(inputBean.getHusername(), inputBean.getCurrpwd()));
//                        up.setLastupdatedtime(sysDate);888888888888888888888888888888888888888888888888
                        session.update(up);
//                        if (txn != null) {
//                            txn.rollback();
//                        }
                    }
                     txn.commit();

                } else if (upassList.size() == passPolicy.getNoofhistorypassword()) {

                    if (passPolicy.getNoofhistorypassword() != 0) {
                        if ((Userpassword) session.get(Userpassword.class, new UserpasswordId(inputBean.getHusername(), inputBean.getCurrpwd())) == null) {
                            Userpassword lpass = upassList.get(0);
                            Userpassword ur = (Userpassword) session.get(Userpassword.class, lpass.getId());

                            session.delete(ur);
                            session.flush();
                            Userpassword lpass2 = new Userpassword();

                            UserpasswordId ddd = new UserpasswordId();
                            ddd.setPassword(inputBean.getCurrpwd());
                            ddd.setUsername(inputBean.getHusername());
                            lpass2.setId(ddd);

//                            lpass2.setUserByLastupdateduser(audit.getUser());
//                            lpass2.setLastupdatedtime(sysDate);88888888888888888888888888888888888888888888
                            session.save(lpass2);

//                            txn.commit();
                        } else {
//                            if current password exists in 'smsuserpassword', then update
//                            message = MessageVarList.RESET_PASS_CURRENT_EXIST;
                        Userpassword up=(Userpassword) session.get(Userpassword.class, new UserpasswordId(inputBean.getHusername(), inputBean.getCurrpwd()));
//                        up.setLastupdatedtime(sysDate);888888888888888888888888888888888888888
                        session.update(up);
                            
//                            if (txn != null) {
//                                txn.rollback();
//                            }
                        }
                        txn.commit();
                    }
                } 
                // if noofhistorypassword exceeds total actual records
//                else {
//                    int numdel = upassList.size() - passPolicy.getNoofhistorypassword();
//
//                    for (int i = 0; i < numdel; i++) {
//                        session.delete(upassList.get(0));
//                        session.flush();
//                        upassList.remove(0);
//                    }
//                    if (passPolicy.getNoofhistorypassword() != 0) {
//                        if ((Userpassword) session.get(Userpassword.class, new UserpasswordId(inputBean.getHusername(), inputBean.getCurrpwd())) == null) {
//                            Userpassword lpass = upassList.get(0);
//                            Userpassword ur = (Userpassword) session.get(Userpassword.class, lpass.getId());
//
//                            session.delete(ur);
//                            session.flush();
//                            Userpassword lpass2 = new Userpassword();
//
//                            UserpasswordId ddd = new UserpasswordId();
//                            ddd.setPassword(inputBean.getCurrpwd());
//                            ddd.setUsername(inputBean.getHusername());
//                            lpass2.setId(ddd);
//
//                            lpass2.setUserByLastupdateduser(audit.getUser());
//                            lpass2.setLastupdatedtime(sysDate);
//                            session.save(lpass2);                       
//
//                        txn.commit();
//                    } else {
//                        message = MessageVarList.RESET_PASS_CURRENT_EXIST;
//                        if (txn != null) {
//                            txn.rollback();
//                        }
//                    }
//                }else{
//                       txn.commit();
//                    }
//                }

            } else {
//                message = MessageVarList.COMMON_ALREADY_EXISTS;
                message = MessageVarList.RESET_PASS_SAME_NEW_CURRENT;                        
            }

        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            try {
//                if(session.isOpen()){
//                    session.close();
//                }
//                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return message;
    }

    //find user by username
    public Systemuser findUserById(String username) throws Exception {

        Systemuser user = new Systemuser();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Systemuser as u join fetch u.status join fetch u.userrole where u.username=:username";
            Query query = session.createQuery(sql).setString("username", username);
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
}
