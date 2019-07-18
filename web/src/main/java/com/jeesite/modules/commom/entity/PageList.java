package com.jeesite.modules.commom.entity;

import java.io.Serializable;
import java.util.List;

public class PageList<T> implements Serializable {

    private static final long serialVersionUID = 1433729327463356559L;

    /**
     * 页码
     */
    private Integer pageNumber ;
    /**
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 实体数据集合
     */
    private List<T> entities;
    /**
     * 总页码
     */
    private Long count;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
