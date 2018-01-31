/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.util.mapping;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jayana_i
 */
@Entity
@Table(name = "MS_LOGINTYPES"
)
public class Logintype implements java.io.Serializable {
    
    private BigDecimal id;
    private String description;
    
    public Logintype() {
    }

    public Logintype(BigDecimal id, String description) {
        this.id = id;
        this.description = description;
    }

     @Id

    @Column(name = "ID", unique = true, nullable = false, length = 20)
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    @Column(name = "DESCRIPTION", length = 20)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
