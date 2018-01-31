/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rawmakyamu.util.mapping;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author chathuri_t
 */

@Entity
@Table(name = "USERLEVEL" )

public class Userlevel implements java.io.Serializable{
    
    private int levelid;
    private String description;
    private Set<Userrole> userroles = new HashSet<Userrole>(0);
    
    public Userlevel() {
    }

    public Userlevel(int levelid) {
        this.levelid = levelid;
    }

    public Userlevel(int levelid, String description, Set<Userrole> userroles) {
        this.levelid = levelid;
        this.description = description;
        this.userroles = userroles;
    }
    
    @Id 
    
    @Column(name="LEVELID", unique=true, nullable=false, length=11)
    public int getLevelid() {
        return levelid;
    }

    public void setLevelid(int levelid) {
        this.levelid = levelid;
    }
    
    @Column(name = "DESCRIPTION", length = 64)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="userlevel")
    public Set<Userrole> getUserroles() {
        return userroles;
    }

    public void setUserroles(Set<Userrole> userroles) {
        this.userroles = userroles;
    }  
    
}
