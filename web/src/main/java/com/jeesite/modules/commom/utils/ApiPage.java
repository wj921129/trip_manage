package com.jeesite.modules.commom.utils;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

public class ApiPage<T> implements Serializable {

    private static final long serialVersionUID = -1550710431377302634L;
    private int currentPage;
    private int pageSize;
    private List<T> entities = Lists.newArrayListWithCapacity(0);
    private int count;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
