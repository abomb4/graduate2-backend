package org.wlyyy.itrs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * 用户登录信息
 */
public class UserAgent implements UserDetails, Serializable {
    private boolean success;
    private String sessionKey;
    private Long id;
    private String userName;
    private String email;
    private Set<Role> roles;
    private Integer sex;
    private Long departmentId;
    private String departmentName;
    private String realName;
    private String remoteIp;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refreshTime;

    /**
     * 判断该用户是否属于某个角色
     *
     * @param role 角色
     * @return 是否属于
     */
    public boolean haveRole(Role role) {
        return roles.contains(role);
    }

    public boolean isSuccess() {
        return success;
    }

    public UserAgent setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public UserAgent setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserAgent setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserAgent setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserAgent setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public UserAgent setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Integer getSex() {
        return sex;
    }

    public UserAgent setSex(Integer sex) {
        this.sex = sex;
        return this;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public UserAgent setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public UserAgent setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public UserAgent setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public UserAgent setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
        return this;
    }

    public LocalDateTime getRefreshTime() {
        return refreshTime;
    }

    public UserAgent setRefreshTime(LocalDateTime refreshTime) {
        this.refreshTime = refreshTime;
        return this;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "sessionKey='" + sessionKey + '\'' +
                ", id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", sex=" + sex +
                ", departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", realName='" + realName + '\'' +
                ", loginTime=" + loginTime +
                ", refreshTime=" + refreshTime +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

