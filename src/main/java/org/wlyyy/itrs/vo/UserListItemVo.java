package org.wlyyy.itrs.vo;


import org.wlyyy.itrs.domain.User;

import java.text.SimpleDateFormat;
import java.util.function.Function;

/**
 * 用户展示层
 */
public class UserListItemVo {

    public static UserListItemVo buildFromDomain(
            User source,
            Function<Long, String> findDepartmentNameById  // 根据部门id获取部门名
    ) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final Long id = source.getId();
        final String userName = source.getUserName();
        final String email = source.getEmail();
        final Integer sex = source.getSex();
        final Long departmentId = source.getDepartmentId();
        final String departmentName = findDepartmentNameById.apply(source.getDepartmentId());
        final String realName = source.getRealName();
        final String gmtCreate = formatter.format(source.getGmtCreate());
        final String gmtModify = formatter.format(source.getGmtModify());

        return new UserListItemVo(id, userName, email, sex, departmentId, departmentName, realName, gmtCreate, gmtModify);
    }

    private Long id;
    private String userName;
    private String email;
    private Integer sex;
    private Long departmentId;
    private String departmentName;
    private String realName;
    private String gmtCreate;
    private String gmtModify;

    public UserListItemVo() {
    }

    public UserListItemVo(
            Long id, String userName, String email, Integer sex, Long departmentId, String departmentName,
            String realName, String gmtCreate, String gmtModify
    ) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.sex = sex;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.realName = realName;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(String gmtModify) {
        this.gmtModify = gmtModify;
    }
}
