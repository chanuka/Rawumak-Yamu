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
 * @author yasas
 */

@Entity
@Table(name="MS_USER_PARAM"
)
public class Ibmobuserparam implements java.io.Serializable {
    
    private String paramcode;
    private String description;        
    private String value;
    private String lastupdateduser;
    private Date lastupdatedtime;
    private Date createdtime;
    
    public Ibmobuserparam() {
    }

    public Ibmobuserparam(String paramcode) {
        this.paramcode = paramcode;
    }
   
    
    public Ibmobuserparam(String paramcode,String description,String value,String lastupdateduser,Date lastupdatedtime,Date createdtime) {
        this.paramcode = paramcode;
        this.description = paramcode;
        this.value = paramcode;
        this.lastupdatedtime = lastupdatedtime;
        this.lastupdateduser = paramcode;
        this.createdtime = createdtime;
    }

    @Id 

    
    @Column(name="PARAMCODE", unique=true, nullable=false, length=16)
    public String getParamcode() {
        return paramcode;
    }

    public void setParamcode(String paramcode) {
        this.paramcode = paramcode;
    }

    @Column(name="DESCRIPTION",  nullable=false, length=128)  
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="PARAM_VALUE",  nullable=false, length=20)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name="LASTUPDATEDUSER",  nullable=false, length=64)
    public String getLastupdateduser() {
        return lastupdateduser;
    }

    public void setLastupdateduser(String lastupdateduser) {
        this.lastupdateduser = lastupdateduser;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="LASTUPDATEDTIME", length=7)
    public Date getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(Date lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="CREATEDTIME", length=7)
    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }
    
    
    
}
