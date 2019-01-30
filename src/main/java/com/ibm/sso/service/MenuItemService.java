package com.ibm.sso.service;

import com.aef3.data.api.GenericEntityDAO;
import com.ibm.sso.dao.MenuItemDao;
import com.ibm.sso.dto.MenuItemDto;
import com.ibm.sso.dto.MenuTreeItem;
import com.ibm.sso.dto.WebPageDto;
import com.ibm.sso.model.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuItemService extends GeneralServiceImpl<MenuItemDto, MenuItem, Long> {


    private final MenuItemDao menuItemDao;

    @Autowired
    public MenuItemService(MenuItemDao menuItemDao) {
        this.menuItemDao = menuItemDao;
    }

    @Override
    protected GenericEntityDAO getDAO() {
        return menuItemDao;
    }

    @Override
    public MenuItemDto getDtoInstance() {
        return new MenuItemDto();
    }


    public List<MenuTreeItem> fetchMenuTree() {
        List<MenuItem> rootItems = menuItemDao.findRootItems();
        if(rootItems == null || rootItems.isEmpty())
            return new ArrayList<>();
        List<MenuTreeItem> treeList = new ArrayList<>();
        rootItems.stream().forEach(i -> {
            treeList.add(fillTree(i));
        });
        return treeList;
    }


    private MenuTreeItem fillTree(MenuItem item) {

        MenuTreeItem tree = new MenuTreeItem();
        tree.setAddress(item.getAddress());
        tree.setId(item.getId());
        tree.setName(item.getName());
        tree.setPage(WebPageDto.toDto(item.getPage()));
        tree.setPermission(item.getPermission());
        tree.setTitle(item.getTitle());

        if(item.getChildren() == null || item.getChildren().isEmpty()) {
            return tree;
        }
        List<MenuTreeItem> itemList = new ArrayList<>();
        item.getChildren().stream().forEach(c -> {
            itemList.add(fillTree(c));
        });
        tree.setChildTreeList(itemList);
        return tree;
    }
}
