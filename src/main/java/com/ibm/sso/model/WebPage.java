package com.ibm.sso.model;

import com.aef3.data.api.DomainEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


/* Generated By AEF Generator ( Powered by Dr.Adldoost :D ) */

@Entity
@Table(name = "web_page")
public class WebPage implements DomainEntity {


    @Column(name = "address")
    private String address;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "content")
    private String content;



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


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
       this.content = content;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebPage)) return false;
        WebPage webPage = (WebPage) o;
        return Objects.equals(id, webPage.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "WebPage{" +
                "id=" + id +
                '}';
    }
}