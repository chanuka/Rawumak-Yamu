/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.util.mapping;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author prathibha_s
 */
@Entity
@Table(name="MS_CUSTOMER_WALLET_PUSH_BLOCK")
public class MsCustomerWalletPushBlock implements java.io.Serializable {
    
    private String walletId;
    private String status;
    private Date fromdate;
    private Date todate;
    private Date createtime;
    private Date lastupdatedtime;
    private String lastupdateduser;
    
    public MsCustomerWalletPushBlock() {
    }

	
    public MsCustomerWalletPushBlock(String walletId) {
        this.walletId = walletId;
    }
    
    public MsCustomerWalletPushBlock(String walletId, String status, Date fromdate, Date todate, Date createtime, Date lastupdatedtime, String lastupdateduser) {
       this.walletId = walletId;
       this.status = status;
       this.lastupdateduser = lastupdateduser;
       this.lastupdatedtime = lastupdatedtime;
       this.fromdate = fromdate;
       this.todate = todate;
       this.createtime = createtime;
      
    }
    
    @Id
    @Column(name="WALLET_ID", unique=true, nullable=false, length=10)
    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    @Column(name="STATUS", length=5)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="FROMDATE", length=7)
    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="TODATE", length=7)
    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_TIME", length=7)
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LAST_UPDATED_TIME", length=7)
    public Date getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(Date lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    @Column(name="LASTUPDATEDUSER", length=20)
    public String getLastupdateduser() {
        return lastupdateduser;
    }

    public void setLastupdateduser(String lastupdateduser) {
        this.lastupdateduser = lastupdateduser;
    }
    
    
}
