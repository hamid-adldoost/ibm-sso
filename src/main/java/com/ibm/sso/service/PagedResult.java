package com.ibm.sso.service;

import java.io.Serializable;
import java.util.List;

public class PagedResult<T extends Serializable> implements Serializable {

    private List<T> data;
    private Long count;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}