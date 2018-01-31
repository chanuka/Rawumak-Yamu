/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.util.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jayana_i
 */
@Entity
@Table(name = "MS_PROMOTION_LEVEL")
public class Promotionuserlevel implements java.io.Serializable {
    private String code;
    private String description;
    
    public Promotionuserlevel() {
    }

    public Promotionuserlevel(String code) {
        this.code = code;
    }
    
    public Promotionuserlevel(String code,String description) {
        this.code = code;
        this.description = description;
    }
    
    @Id
    @Column(name = "CODE", unique = true, nullable = false, length = 10)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "DESCRIPTION", length = 50)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
