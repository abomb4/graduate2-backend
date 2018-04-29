package org.wlyyy.itrs.request;

import org.springframework.data.domain.Sort;

import java.util.Date;

public class DepartmentQuery {

    private Long id;
    private String departmentName;
    private Long parentId;
    private Date gmtCreateStart;
    private Date gmtCreateEnd;
    private Date gmtModifyStart;
    private Date gmtModifyEnd;
    private Sort sort;

    public Long getId() {
        return id;
    }

    public DepartmentQuery setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public DepartmentQuery setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    public Long getParentId() {
        return parentId;
    }

    public DepartmentQuery setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public DepartmentQuery setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
        return this;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public DepartmentQuery setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
        return this;
    }

    public Date getGmtModifyStart() {
        return gmtModifyStart;
    }

    public DepartmentQuery setGmtModifyStart(Date gmtModifyStart) {
        this.gmtModifyStart = gmtModifyStart;
        return this;
    }

    public Date getGmtModifyEnd() {
        return gmtModifyEnd;
    }

    public DepartmentQuery setGmtModifyEnd(Date gmtModifyEnd) {
        this.gmtModifyEnd = gmtModifyEnd;
        return this;
    }

    public Sort getSort() {
        return sort;
    }

    public DepartmentQuery setSort(Sort sort) {
        this.sort = sort;
        return this;
    }
}
