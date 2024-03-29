package com.ibm.sso.model;

import com.aef3.data.api.DomainEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


/* Generated By AEF Generator ( Powered by Dr.Adldoost :D ) */

@Entity
@Table(name = "file")
public class FileObject implements DomainEntity {


    @Column(name = "path", nullable = false, length = 500)
    private String path;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "role_object", nullable = false, length = 1000)
    private String roleObject;

    @Column(name = "md5", nullable = false, length = 512)
    private String md5;

    @Column(name = "upload_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;


    public String getPath() {
        return path;
    }

    public void setPath(String address) {
       this.path = address;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
       this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
       this.description = description;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
       this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
       this.title = title;
    }


    public String getRoleObject() {
        return roleObject;
    }

    public void setRoleObject(String roleObject) {
       this.roleObject = roleObject;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileObject)) return false;
        FileObject fileObject = (FileObject) o;
        return Objects.equals(id, fileObject.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FileObject{" +
                "id=" + id +
                '}';
    }
}