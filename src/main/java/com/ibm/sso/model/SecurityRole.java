package com.ibm.sso.model;

import com.aef3.data.api.DomainEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/* Generated By AEF Generator ( Powered by Dr.Adldoost :D ) */

@Entity
@Table(name = "security_role")
public class SecurityRole implements DomainEntity {


    @JoinColumn(name = "parent", referencedColumnName = "id")
    @ManyToOne
    private SecurityRole parent;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @JoinTable(name = "sec_user_role",
            joinColumns = {@JoinColumn(name = "sec_user", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "sec_role", referencedColumnName = "id", nullable = false)})
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SecurityUser> userList;
    @JoinTable(name = "sec_role_permission",
            joinColumns = {@JoinColumn(name = "sec_permission", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "sec_role", referencedColumnName = "id", nullable = false)})
    @ManyToMany(cascade = CascadeType.ALL)
    private List<SecurityPermission> permissionList;


    public SecurityRole getParent() {
        return parent;
    }

    public void setParent(SecurityRole parent) {
       this.parent = parent;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
       this.name = name;
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

    public List<SecurityUser> getUserList() {
        return userList;
    }

    public void setUserList(List<SecurityUser> userList) {
        this.userList = userList;
    }

    public List<SecurityPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<SecurityPermission> permissionList) {
        this.permissionList = permissionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecurityRole)) return false;
        SecurityRole securityRole = (SecurityRole) o;
        return Objects.equals(id, securityRole.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SecurityRole{" +
                "id=" + id +
                '}';
    }
}