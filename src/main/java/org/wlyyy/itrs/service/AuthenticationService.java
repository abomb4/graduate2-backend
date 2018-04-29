package org.wlyyy.itrs.service;

import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.UserAgent;

/**
 * 统一授权服务
 *
 * @author wly
 */
public interface AuthenticationService {

    /**
     * 登录功能
     *
     * @param userName 用户名
     * @param password 密码，明文
     * @return 成功则返回用户身份信息，失败就失败
     */
    BaseServiceResponse<UserAgent> login(String userName, String password);

    /**
     * 判断一个Session 是否在登录状态
     *
     * @return 是否登录
     */
    BaseServiceResponse<UserAgent> isLogin();

    /**
     * 登出，消除Session
     *
     * @return 成功登出和未登录则true，异常则false
     */
    boolean logout();
}
