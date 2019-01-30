package com.ibm.sso.model;

import com.aef3.data.api.DomainEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/* Generated By AEF Generator ( Powered by Dr.Adldoost :D ) */

@Entity
@Table(name = "menu_item")
public class MenuItem implements DomainEntity {


    @Column(name = "address")
    private String address;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "permission")
    private String permission;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "page", referencedColumnName = "id")
    @ManyToOne
    private WebPage page;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @JoinColumn(referencedColumnName = "id", name = "parent_item", nullable = true)
    @ManyToOne
    private MenuItem parentItem;

    @OneToMany(mappedBy = "parentItem", fetch = FetchType.LAZY)
    private List<MenuItem> children;



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
       this.address = address;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
       this.name = name;
    }


    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
       this.permission = permission;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
       this.id = id;
    }


    public WebPage getPage() {
        return page;
    }

    public void setPage(WebPage page) {
       this.page = page;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
       this.title = title;
    }

    public MenuItem getParentItem() {
        return parentItem;
    }

    public void setParentItem(MenuItem parentItem) {
        this.parentItem = parentItem;
    }

    public List<MenuItem> getChildren() {
        return children;
    }

    public void setChildren(List<MenuItem> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(id, menuItem.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                '}';
    }
}