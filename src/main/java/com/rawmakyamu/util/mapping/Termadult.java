/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.util.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author jayana_i
 */


@Entity
@Table(name = "MS_TERMS_ADULT"
)
public class Termadult implements java.io.Serializable {

    private Status status;
    private String terms;
    private String version_no;

    public Termadult() {
    }

    public Termadult(String version_no) {
        this.version_no = version_no;
    }

    public Termadult(String version_no,String terms,Status status) {
        this.version_no = version_no;
        this.terms = terms;
        this.version_no = version_no;
        

    }

    @Id
    @Column(name = "VERSION_NO", unique = true, nullable = false,length = 20)
    
    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    

    @Column(name = "TERMS")
    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    

    

    
    
}

