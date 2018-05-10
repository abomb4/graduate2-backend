package org.wlyyy.itrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.UserAgent;
import org.wlyyy.itrs.service.AuthenticationService;
import org.wlyyy.itrs.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/auth/check-status", method = RequestMethod.GET)
    public BaseServiceResponse<UserAgent> checkStatus(HttpServletRequest request) {
        return authService.isLogin();
    }

    @RequestMapping(value = "/myProfile/user/modifyPassword", method = RequestMethod.POST)
    public BaseServiceResponse<String> modifyPassword(final String oldPassword, final String newPassword) {
        // 获取当前登录用户
        UserAgent userAgent = authService.isLogin().getData();
        userService.modifyPassword(oldPassword, newPassword, userAgent.getUserName());
        return null;
    }

}
