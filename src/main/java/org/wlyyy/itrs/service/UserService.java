package org.wlyyy.itrs.service;

import org.wlyyy.common.domain.BaseServicePageableRequest;
import org.wlyyy.common.domain.BaseServicePageableResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.User;
import org.wlyyy.itrs.request.UserQuery;

/**
 * 用户信息基本管理服务
 */
public interface UserService {

    /**
     * 条件分页查询。
     * 返回结果不包含password字段。
     *
     * @param request 分页查询条件
     * @return 查询结果
     */
    BaseServicePageableResponse<User> findByCondition(BaseServicePageableRequest<UserQuery> request);

    /**
     * 根据id查询。
     * 返回结果不包含password字段。!
     *
     * @param id id
     * @return 用户，没有的话返回null
     */
    User findById(Long id);

    /**
     * 校验密码是否正确。
     *
     * @param userName 用户名
     * @param password 密码，明文
     * @return 校验结果，如果成功则success = true，并返回User对象；校验失败则success = false，没有data
     */
    BaseServiceResponse<User> validateUser(String userName, String password);

    /**
     * 创建用户。需要的字段包括：
     * userName, email, password, salt, sex, departmentId, realName
     * 必须全部包含。
     *
     * @param user 用户信息
     * @return 创建结果，通过{@link BaseServiceResponse#isSuccess()}来判断结果
     */
    BaseServiceResponse<User> createUser(User user);

    /**
     * 修改用户密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param userName 用户名
     * @return Modified
     */
    BaseServiceResponse<User> modifyPassword(String oldPassword, String newPassword, String userName);
}
