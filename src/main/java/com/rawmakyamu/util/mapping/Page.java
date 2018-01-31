package com.rawmakyamu.util.mapping;
// Generated Jan 28, 2016 9:45:39 AM by Hibernate Tools 3.6.0

import java.util.Date;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Page generated by hbm2java
 */
@Entity
@Table(name = "PAGE"
)
public class Page implements java.io.Serializable {

    private String pagecode;
    private Status status;
    private String description;
    private String url;
    private Byte sortkey;
    private String lastupdateduser;
    private Date lastupdatedtime;
    private Date createtime;
    private String isDual;
    private String type;
    private Set<Pagetask> pagetasks = new HashSet<Pagetask>(0);
    private Set<Sectionpage> sectionpages = new HashSet<Sectionpage>(0);

    public Page() {
    }

    public Page(String pagecode) {
        this.pagecode = pagecode;
    }

    public Page(Date createtime, Date lastupdatedtime, String lastupdateduser, String pagecode, Status status, String description, String url,
            Byte sortkey, Set<Pagetask> pagetasks, Set<Sectionpage> sectionpages, String isDual, String type) {

//    public Page(String pagecode, Status status, String description, String url, Byte sortkey, Set<Pagetask> pagetasks, Set<Sectionpage> sectionpages) {
        this.pagecode = pagecode;
        this.status = status;
        this.description = description;
        this.url = url;
        this.sortkey = sortkey;
        this.pagetasks = pagetasks;
        this.sectionpages = sectionpages;
        this.lastupdateduser = lastupdateduser;
        this.lastupdatedtime = lastupdatedtime;
        this.createtime = createtime;
        this.isDual = isDual;
        this.type = type;
    }

    @Id

    @Column(name = "PAGECODE", unique = true, nullable = false, length = 20)
    public String getPagecode() {
        return this.pagecode;
    }

    public void setPagecode(String pagecode) {
        this.pagecode = pagecode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS")
    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "DESCRIPTION", length = 64)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "URL", length = 128)
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "SORTKEY", precision = 2, scale = 0)
    public Byte getSortkey() {
        return this.sortkey;
    }

    public void setSortkey(Byte sortkey) {
        this.sortkey = sortkey;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "page")
    public Set<Pagetask> getPagetasks() {
        return this.pagetasks;
    }

    public void setPagetasks(Set<Pagetask> pagetasks) {
        this.pagetasks = pagetasks;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "page")
    public Set<Sectionpage> getSectionpages() {
        return this.sectionpages;
    }

    public void setSectionpages(Set<Sectionpage> sectionpages) {
        this.sectionpages = sectionpages;
    }

    @Column(name = "LASTUPDATEDUSER", length = 64)
    public String getLastupdateduser() {
        return lastupdateduser;
    }

    public void setLastupdateduser(String lastupdateduser) {
        this.lastupdateduser = lastupdateduser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LASTUPDATEDTIME", length = 23)
    public Date getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(Date lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATETIME", length = 23)

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Column(name = "IS_DUAL", length = 20)
    public String getIsDual() {
        return isDual;
    }

    public void setIsDual(String isDual) {
        this.isDual = isDual;
    }

    @Column(name = "TYPE", length = 20)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
