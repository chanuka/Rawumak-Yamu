package com.rawmakyamu.util.mapping;
// Generated May 24, 2017 3:53:16 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MsCustomerWalletPush generated by hbm2java
 */
@Entity
@Table(name="MS_CUSTOMER_WALLET_PUSH")
public class MsCustomerWalletPush  implements java.io.Serializable {


     private BigDecimal id;
     private String walletId;
     private String message;
     private String status;
     private Date createTime;
     private Date lastUpdatedTime;
     private String fileName;

    public MsCustomerWalletPush() {
    }

	
    public MsCustomerWalletPush(BigDecimal id, String walletId) {
        this.id = id;
        this.walletId = walletId;
    }
    public MsCustomerWalletPush(BigDecimal id, String walletId, String message, String status, Date createTime, Date lastUpdatedTime, String fileName) {
       this.id = id;
       this.walletId = walletId;
       this.message = message;
       this.status = status;
       this.createTime = createTime;
       this.lastUpdatedTime = lastUpdatedTime;
       this.fileName = fileName;
    }
   
     @Id 

        @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "MS_CUSTOMER_WALLET_PUSH_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
     
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    public BigDecimal getId() {
        return this.id;
    }
    
    public void setId(BigDecimal id) {
        this.id = id;
    }

    
    @Column(name="WALLET_ID", nullable=false, length=10)
    public String getWalletId() {
        return this.walletId;
    }
    
    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    
    @Column(name="MESSAGE", length=2048)
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    
    @Column(name="STATUS", length=5)
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_TIME", length=7)
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="LAST_UPDATED_TIME", length=7)
    public Date getLastUpdatedTime() {
        return this.lastUpdatedTime;
    }
    
    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    
    @Column(name="FILE_NAME", length=128)
    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }




}


