package org.wlyyy.itrs.request;

import java.util.Date;

public class RoleQuery {

    private Long id;
    private String roleName;
    private String memo;
    private java.util.Date gmtCreateStart;
    private java.util.Date gmtCreateEnd;
    private java.util.Date gmtModifyStart;
    private java.util.Date gmtModifyEnd;

    public Long getId() {
        return id;
    }

    public RoleQuery setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public RoleQuery setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public String getMemo() {
        return memo;
    }

    public RoleQuery setMemo(String memo) {
        this.memo = memo;
        return this;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public RoleQuery setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
        return this;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public RoleQuery setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
        return this;
    }

    public Date getGmtModifyStart() {
        return gmtModifyStart;
    }

    public RoleQuery setGmtModifyStart(Date gmtModifyStart) {
        this.gmtModifyStart = gmtModifyStart;
        return this;
    }

    public Date getGmtModifyEnd() {
        return gmtModifyEnd;
    }

    public RoleQuery setGmtModifyEnd(Date gmtModifyEnd) {
        this.gmtModifyEnd = gmtModifyEnd;
        return this;
    }
}
