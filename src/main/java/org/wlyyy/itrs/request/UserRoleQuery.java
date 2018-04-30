package org.wlyyy.itrs.request;

import java.util.Date;

public class UserRoleQuery {

    private Long id;
    private Long userId;
    private Long roleId;
    private java.util.Date gmtCreateStart;
    private java.util.Date gmtCreateEnd;
    private java.util.Date gmtModifyStart;
    private java.util.Date gmtModifyEnd;

    public Long getId() {
        return id;
    }

    public UserRoleQuery setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public UserRoleQuery setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getRoleId() {
        return roleId;
    }

    public UserRoleQuery setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public UserRoleQuery setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
        return this;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public UserRoleQuery setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
        return this;
    }

    public Date getGmtModifyStart() {
        return gmtModifyStart;
    }

    public UserRoleQuery setGmtModifyStart(Date gmtModifyStart) {
        this.gmtModifyStart = gmtModifyStart;
        return this;
    }

    public Date getGmtModifyEnd() {
        return gmtModifyEnd;
    }

    public UserRoleQuery setGmtModifyEnd(Date gmtModifyEnd) {
        this.gmtModifyEnd = gmtModifyEnd;
        return this;
    }
}
