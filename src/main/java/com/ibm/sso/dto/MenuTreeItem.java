
package com.ibm.sso.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuTreeItem {

//    @JsonProperty("url")
    private String address;
    private String name;
    public String icon="email";
    private String permission;
    private Long id;
    private WebPageDto page;
    private String title;

//    @JsonProperty("type")
//    public String grtType() {
//        if (childTreeList != null) {
//            return "collapsable";
//        }
//        return "item";
//    }

//    @JsonProperty("children")
    private List<MenuTreeItem> childTreeList;

//    public String getAddress() {
//        if(this.grtType()=="item")
//        return address;
//        return null;
//    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return "name"+id.toString();
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

    public WebPageDto getPage() {
        return page;
    }

    public void setPage(WebPageDto page) {
        this.page = page;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MenuTreeItem> getChildTreeList() {
        return childTreeList;
    }

    public void setChildTreeList(List<MenuTreeItem> childTreeList) {
        this.childTreeList = childTreeList;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAddress() {
        return address;
    }
}
