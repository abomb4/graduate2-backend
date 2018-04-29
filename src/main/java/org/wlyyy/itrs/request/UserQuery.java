package org.wlyyy.itrs.request;

import org.springframework.data.domain.Sort;
import org.wlyyy.itrs.dao.UserRepository;

import java.util.Date;

/**
 * 用户查询类
 */
public class UserQuery {
    private Long id;
    private String userName;
    private String email;
    private Long departmentId;
    private String realName;
    private Date gmtCreateStart;
    private Date gmtCreateEnd;
    private Date gmtModifyStart;
    private Date gmtModifyEnd;
    private Sort sort;

    public Long getId() {
        return id;
    }

    public UserQuery setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserQuery setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserQuery setEmail(String email) {
        this.email = email;
        return this;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public UserQuery setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public UserQuery setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public UserQuery setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
        return this;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public UserQuery setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
        return this;
    }

    public Date getGmtModifyStart() {
        return gmtModifyStart;
    }

    public UserQuery setGmtModifyStart(Date gmtModifyStart) {
        this.gmtModifyStart = gmtModifyStart;
        return this;
    }

    public Date getGmtModifyEnd() {
        return gmtModifyEnd;
    }

    public UserQuery setGmtModifyEnd(Date gmtModifyEnd) {
        this.gmtModifyEnd = gmtModifyEnd;
        return this;
    }

    public Sort getSort() {
        return sort;
    }

    public UserQuery setSort(Sort sort) {
        this.sort = sort;
        return this;
    }
}
